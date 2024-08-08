/**
 * 
 */
package com.etel.financial;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbapd;
import com.coopdb.data.Tbloanproduct;
import com.coopdb.data.Tbloans;
import com.coopdb.data.Tbmemberemployment;
import com.ete.collateral.CollateralService;
import com.ete.collateral.CollateralServiceImpl;
import com.etel.codetable.forms.CodetableForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.deposit.DepositService;
import com.etel.deposit.DepositServiceImpl;
import com.etel.deposit.form.DepositAccountForm;
import com.etel.financial.form.CollateralLoanableForm;
import com.etel.financial.form.MLACForm;
import com.etel.financial.form.MaxLoanAmountForm;
import com.etel.loancalc.LoanCalculatorService;
import com.etel.loancalc.LoanCalculatorServiceImpl;
import com.etel.loanproduct.LoanProductService;
import com.etel.loanproduct.LoanProductServiceImpl;
import com.etel.utils.HQLUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

/**
 * @author ETEL-LAPTOP19
 *
 */
public class FinancialServiceImpl implements FinancialService {

	private DBService dbService = new DBServiceImpl();
	private Map<String, Object> param = HQLUtil.getMap();
	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");

	@Override
	public BigDecimal computeMLA(MLACForm form) {
		BigDecimal amount = BigDecimal.ZERO;
		try {
			switch (Integer.valueOf(form.getParticular())) {
			case 1: // MLA Product
				LoanProductService loanprodsrvc = new LoanProductServiceImpl();
				Tbloanproduct product = loanprodsrvc.getLoanProductByProductcode(form.getProduct());
				amount = product.getMaxloanamount();
				break;
			case 2:// APD
				if (form.getTerm().compareTo(BigDecimal.ZERO) == 1) {
					BigDecimal multiplier = BigDecimal.valueOf(12);
					BigDecimal first = form.getEir().multiply(form.getTerm().divide(multiplier)).divide(form.getTerm(),
							9, RoundingMode.HALF_UP);
					BigDecimal second = BigDecimal.ONE.add(first).pow(form.getTerm().intValue());
					BigDecimal third = second.subtract(BigDecimal.ONE);
					BigDecimal apd = form.getNthp().subtract(form.getGaa());
					amount = apd.divide(first.multiply(second).divide(third, 9, RoundingMode.HALF_UP), 2,
							RoundingMode.UP);
				}
				break;
			case 3:// SBL
				BigDecimal totalDeposits = BigDecimal.ZERO;
				BigDecimal totalLoans = BigDecimal.ZERO;
				BigDecimal totalCollateral = BigDecimal.ZERO;
				if (form.getMemberid() != null && form.getMemberid().equals("")) {
					DepositService depsrvc = new DepositServiceImpl();
					CollateralService colsrvc = new CollateralServiceImpl();
					LoanCalculatorService loancalcsrvc = new LoanCalculatorServiceImpl();
					List<DepositAccountForm> deposits = depsrvc.listDeposits(form.getMemberid());
					List<Tbloans> loans = loancalcsrvc.getExistingLoansByCIFNo(form.getMemberid());
					List<CollateralLoanableForm> collaterals = new ArrayList<CollateralLoanableForm>();
					if (deposits != null && deposits.size() > 0) {
						for (DepositAccountForm dep : deposits) {
							totalDeposits = totalDeposits.add(dep.getAcctbalance());
						}
					}
					if (form.getAppno() != null && !form.getAppno().equals("")) {
						collaterals = colsrvc.listLoanCollateral(form.getAppno(), null);
						if (collaterals != null && collaterals.size() > 0) {
							for (CollateralLoanableForm col : collaterals) {
								totalCollateral = totalCollateral.add(col.getLoanablevalue());
							}
						}
					}
					if (loans != null && loans.size() > 0) {
						for (Tbloans loan : loans) {
							totalLoans = totalLoans.add(loan.getLoanbal());
						}
					}
					form.setDeposits(deposits);
					form.setCollaterals(collaterals);
					form.setLoans(loans);
				}
				form.setTotaldeposit(totalDeposits);
				form.setTotalcollateral(totalCollateral);
				form.setTotalloan(totalLoans);
				System.out.println(form.toString());
				amount = form.getSalary()
						.multiply(form.getServicestatus().equals("1") ? BigDecimal.valueOf(13) : BigDecimal.valueOf(12))
						.add(form.getTotaldeposit().add(form.getTotalcollateral())).subtract(form.getTotalloan());
				break;
			default:
				System.out.println("Invalid Particular >> " + form.getParticular());
				break;
			}

//				Tbmlacperloanapp mla = (Tbmlacperloanapp) dbService.executeUniqueHQLQuery(
//						"FROM Tbmlacperloanapp WHERE appno=:appno AND particulars =:particular", param);
//				if (mla != null) {
//					mla.setAmount(amount);
//					dbService.saveOrUpdate(mla);
//				}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return amount;
	}

	@Override
	public Tbapd getLatestPayslip(Tbapd apd) {
//		Tbapd apd = new Tbapd();
//		param.put("appno", appno == null ? "" : appno);
		param.put("memberid", apd.getId().getMemberid() == null ? "" : apd.getId().getMemberid());
		param.put("payslipperiod", apd.getId().getPayslipperiod());
		String q = "FROM Tbapd WHERE memberid =:memberid ";
		q += apd.getId().getPayslipperiod() != null ? "AND CAST(payslipperiod AS DATE) =CAST(:payslipperiod AS DATE)" : "";
		q += "ORDER BY payslipperiod desc";
		try {
			if (apd.getId().getMemberid() != null && !apd.getId().getMemberid().equals("")) {
				apd = (Tbapd) dbService.executeUniqueHQLQueryMaxResultOne(q, param);
				if (apd == null) {
					apd = new Tbapd();
//				Tbmember member = (Tbmember) dbService.executeUniqueHQLQuery("FROM Tbmember WHERE membershipid =:memberid",
//						param);
					Tbmemberemployment employment = (Tbmemberemployment) dbService.executeUniqueHQLQueryMaxResultOne(
							"FROM Tbmemberemployment WHERE membershipid =:memberid order by datehiredfrom desc", param);
//				param.put("servicestatus", member.getServicestatus());
//				param.put("boscode", member.getCompanycode());
					apd.setGmp(employment.getMonthlysalary());
//				apd.setGaa(dbService.getSQLAmount(
//						"SELECT amount FROM Tbgaaperbos WHERE boscode=:boscode and servicestatus =:servicestatus ", param));
//				apd.setNetnthpgaa(apd.getGaa());
					apd.setNthp(employment.getNetpay());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return apd;
	}

	@Override
	public String addPayslip(Tbapd apd) {
		String flag = "failed";
		try {
			if (dbService.saveOrUpdate(apd))
				flag = "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String deletePayslip(Tbapd apd) {
		String flag = "failed";
		try {
			if (dbService.delete(apd))
				flag = "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

//	@SuppressWarnings("unchecked")
	@Override
	public List<MaxLoanAmountForm> listMLA(MLACForm form) {
		List<MaxLoanAmountForm> mlaList = new ArrayList<MaxLoanAmountForm>();
		LoanProductService lpservice = new LoanProductServiceImpl();
		param.put("productcode", form.getProduct());
		try {
			List<CodetableForm> mlacList = lpservice.getSavedParticulars(form.getProduct(), "", "");
			for (CodetableForm mlac : mlacList) {
				MaxLoanAmountForm mla = new MaxLoanAmountForm();
				form.setParticular(mlac.getCodevalue());
				mla.setCondition(mlac.getDesc2());
				mla.setLoanableamount(computeMLA(form));
				mla.setMlatype(mlac.getCodevalue());
				mlaList.add(mla);
			}
//			String condition = (String) dbService.executeUniqueSQLQuery(
//					"SELECT TOP 1 conditioncode FROM Tbmlacperloanapp WHERE appno=:appno", param);
//			mla = (List<MaxLoanAmountForm>) dbService.execSQLQueryTransformer(
//					"SELECT a.particulars as mlatype,a.amount as loanableamount,b.desc1 as condition,'' as result "
//							+ "FROM Tbmlacperloanapp a LEFT JOIN Tbcodetable b on a.conditioncode = b.codevalue WHERE appno=:appno AND b.codename = 'MLACONDITION' "
//							+ "ORDER BY a.amount " + (condition != null && condition.equals("1") ? "asc" : "desc"),
//					param, MaxLoanAmountForm.class, 1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return mlaList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbapd> listPayslip(String memberid) {
		List<Tbapd> apd = new ArrayList<Tbapd>();
		param.put("memberid", memberid);
		try {
			apd = (List<Tbapd>) dbService.executeListHQLQuery("FROM Tbapd WHERE memberid =:memberid", param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return apd;
	}

//	@SuppressWarnings("unchecked")
	@Override
	public String recomputeMLA(String appno) {
		String flag = "failed";
//		List<Tbmlacperloanapp> mla = new ArrayList<Tbmlacperloanapp>();
		param.put("appno", appno);
		try {
//			mla = (List<Tbmlacperloanapp>) dbService.executeListHQLQuery("FROM Tbmlacperloanapp WHERE appno =:appno",
//					param);
//			for (Tbmlacperloanapp mlac : mla) {
//				computeMLA(appno, mlac.getId().getParticulars());
//			}
			flag = "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Tbapd computeAPD(String appno) {
		Tbapd apd = new Tbapd();
//		Tblstapp lstapp = new Tblstapp();
		param.put("appno", appno);
		try {
//			lstapp = (Tblstapp) dbService.executeUniqueHQLQuery("FROM Tblstapp WHERE appno =:appno", param);
//			param.put("memberid", lstapp.getCifno());
//			Tbmember member = (Tbmember) dbService.executeUniqueHQLQuery("FROM Tbmember WHERE membershipid =:memberid",
//					param);
//			param.put("servicestatus", member.getServicestatus());
//			param.put("boscode", lstapp.getCompanycode());
//			apd = getLatestPayslip(appno, lstapp.getCifno(), null);
//			BigDecimal gaa = dbService.getSQLAmount(
//					"SELECT amount FROM Tbgaaperbos WHERE boscode=:boscode and servicestatus=:servicestatus", param);
//			apd.setGaa(gaa);
////			apd.getNthp().subtract(gaa);
//			dbService.saveOrUpdate(apd);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return apd;
	}

	@Override
	public String checkIfMinimumMaintainingBalLessen(String acctno, BigDecimal transamt, String prodcode) {
		String flag = "nooverride";
		DBService dbServiceCoop = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(acctno!=null && transamt!=null && prodcode!=null) {
				params.put("acctno", acctno);
				params.put("prodcode", prodcode);
				System.out.println(">>> acctno - " + acctno);
				System.out.println(">>> prodcode - " + prodcode);
				
				// get the minimum maintaining bal at deposit product maintenance
				BigDecimal minimumMaintainingBal = (BigDecimal) dbServiceCoop.getSQLAmount
						("SELECT rbmminmainbal FROM TBPRODMATRIX WHERE prodcode=:prodcode", params);
				System.out.println(">>> minimumMaintainingBal - " + minimumMaintainingBal);
				
				// get the available balance
				BigDecimal availBal = (BigDecimal) dbServiceCoop.executeUniqueSQLQuery("select dbo.fn_getavailbal(:acctno)", params);
				System.out.println(">>> availBal - " + availBal);
				
				// compare if already "available balance" is already below with the "minimum maintaining balance"
				// -1 less than, 0 equal, 1 greater than
				int a = minimumMaintainingBal.compareTo(availBal);
				System.out.println(">>> a - " + a);
				
				if(a>0) {
					// automatic override - already lessen the minimum maintaining balance
					return flag = "override";
				}else if(a==0) {
					// needs also override - transaction will use the minimum maintaining balance
					return flag = "override";
				}else {
					// less than 
					// check if transactions is using the minimum maintaining balance
					BigDecimal curBal = availBal.subtract(minimumMaintainingBal);
					System.out.println(">>> curBal - " + curBal);
					
					int b =  transamt.compareTo(curBal);
					System.out.println(">>> b - " + b);
					
					if(b>0) { // greater than
						return flag = "override";
					}else {
						return flag = "nooverride";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public BigDecimal getAvailBalance(String acctno) {
		BigDecimal availBal = BigDecimal.ZERO;
		DBService dbServiceCoop = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(acctno!=null) {
				params.put("acctno", acctno);
				availBal = (BigDecimal) dbServiceCoop.executeUniqueSQLQuery("select dbo.fn_getavailbal(:acctno)", params);
				//System.out.println(">>> acctno - " + acctno);
				//System.out.println(">>> availBal - " + availBal);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return availBal;
	}
	
	
	
	
	
	
	
	
	

}
