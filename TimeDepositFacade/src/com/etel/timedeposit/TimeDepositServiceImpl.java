/**
 * 
 */
package com.etel.timedeposit;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.casa.acct.AccountService;
import com.casa.acct.AccountServiceImpl;
import com.coopdb.data.Tbbranch;
import com.coopdb.data.Tbdeposit;
import com.coopdb.data.Tbdepositcif;
import com.coopdb.data.Tbholiday;
import com.coopdb.data.Tbtimedeposit;
import com.coopdb.data.Tbtransactioncode;
import com.coopdb.data.Tbuser;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.deposittransaction.DepositTransactionService;
import com.etel.deposittransaction.DepositTransactionServiceImpl;
import com.etel.deposittransaction.form.DepositTransactionForm;
import com.etel.deposittransaction.form.DepositTransactionResultForm;
import com.etel.utils.DateTimeUtil;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

/**
 * @author ETEL-LAPTOP19
 *
 */
public class TimeDepositServiceImpl implements TimeDepositService {

	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");

	@Override
	public Tbtimedeposit getTimeDeposit(String acctno) {
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("acctno", acctno);
		try {
			if (acctno != null && !acctno.isEmpty())
				return (Tbtimedeposit) dbsrvc.executeUniqueHQLQuery("FROM Tbtimedeposit WHERE accountno=:acctno",
						param);
			else
				return null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String terminateAccount(String accountno, String disposition, String intdisposition,
			String prindisposition) {
		String result = "failed";
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		AccountService acctSrvc = new AccountServiceImpl();
		Calendar cal = Calendar.getInstance();
		Tbdeposit dep = new Tbdeposit();
		Tbtimedeposit timedep = new Tbtimedeposit();
		List<Tbdepositcif> cifList = new ArrayList<Tbdepositcif>();
		List<Tbholiday> holidays = new ArrayList<Tbholiday>();
		try {
			dep = (Tbdeposit) dbService.executeUniqueHQLQuery("FROM Tbdeposit WHERE accountno=:acctno", param);
			timedep = (Tbtimedeposit) dbService.execStoredProc("SELECT * FROM Tbtimedeposit WHERE accountno=:acctno",
					param, Tbtimedeposit.class, 0, null);
			cifList = (List<Tbdepositcif>) dbService.executeListHQLQuery("FROM Tbdepositcif WHERE accountno =:acctno",
					param);
			dep.setId(null);
			timedep.setId(null);
			dep.setPlacementAmt(timedep.getIntdispo() == null || !timedep.getIntdispo().equals("4") ? dep.getMatAmt()
					: dep.getPlacementAmt());
			dep.setTdcreleaseind(0);
			dep.setAccountStatus(1);
			dep.setBookDate(dep.getMaturityDate());
			dep.setLasttxdate(null);
//			dep.setStatusDate(dep.getBookDate());
			cal.setTime(DateTimeUtil.dtRunDateEndDateD(dep.getMaturityDate()));
			cal.add(Calendar.DAY_OF_MONTH, dep.getTerm());
			dep.setMaturityDate(cal.getTime());
			if (disposition.equals("TD01")) {
//				acctSrvc.createAccount(dep, ciflist);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public int renewTimeDeposit(String accountno) {
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		AccountService acctSrvc = new AccountServiceImpl();
		Calendar cal = Calendar.getInstance();
		Tbdeposit dep = new Tbdeposit();
		Tbtimedeposit timedep = new Tbtimedeposit();
		List<Tbdepositcif> cifList = new ArrayList<Tbdepositcif>();
		List<Tbholiday> holidays = new ArrayList<Tbholiday>();
		int result = 0;
		try {
			holidays = (List<Tbholiday>) dbService.executeListHQLQuery("FROM Tbholiday", null);
			dep = (Tbdeposit) dbService.executeUniqueHQLQuery("FROM Tbdeposit WHERE accountno=:acctno", param);
			timedep = (Tbtimedeposit) dbService.execStoredProc("SELECT * FROM Tbtimedeposit WHERE accountno=:acctno",
					param, Tbtimedeposit.class, 0, null);
			cifList = (List<Tbdepositcif>) dbService.executeListHQLQuery("FROM Tbdepositcif WHERE accountno =:acctno",
					param);
			dep.setId(null);
			timedep.setId(null);
			dep.setPlacementAmt(timedep.getIntdispo() == null || !timedep.getIntdispo().equals("4") ? dep.getMatAmt()
					: dep.getPlacementAmt());
			dep.setTdcreleaseind(0);
			dep.setAccountStatus(1);
			dep.setBookDate(dep.getMaturityDate());
			dep.setLasttxdate(null);
//			dep.setStatusDate(dep.getBookDate());
			cal.setTime(DateTimeUtil.dtRunDateEndDateD(dep.getMaturityDate()));
			cal.add(Calendar.DAY_OF_MONTH, dep.getTerm());
			dep.setMaturityDate(cal.getTime());
			boolean isTermValid = false;
			while (!isTermValid) {
				boolean isHoliday = false;
				for (Tbholiday hol : holidays) {
					if (cal.getTime().compareTo(DateTimeUtil.dtRunDateEndDateD(hol.getHolDate())) == 0) {
						isHoliday = true;
					}
				}
				if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
						|| cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || isHoliday) {
					cal.add(Calendar.DAY_OF_MONTH, 1);
					dep.setTerm(dep.getTerm() + 1);
					dep.setMaturityDate(cal.getTime());
				} else {
					isTermValid = true;
				}
			}
			timedep.setGrossint(BigDecimal
					.valueOf(dep.getPlacementAmt().doubleValue() * dep.getIntRate().doubleValue() * dep.getTerm() / 360)
					.setScale(2, RoundingMode.HALF_EVEN));
			dep.setLessWtaxAmt(BigDecimal.valueOf(timedep.getGrossint().doubleValue() * dep.getWtaxrate().doubleValue())
					.setScale(2, RoundingMode.HALF_EVEN));
			timedep.setLessdocstamp(
					BigDecimal.valueOf(((dep.getPlacementAmt().doubleValue() / 200) * 1.50) * dep.getTerm() / 360)
							.setScale(2, RoundingMode.HALF_EVEN));
			dep.setMatAmt(BigDecimal.valueOf((dep.getPlacementAmt().doubleValue() + timedep.getGrossint().doubleValue())
					- dep.getLessWtaxAmt().doubleValue()).setScale(2, RoundingMode.HALF_EVEN));
			timedep.setPlaceamt(dep.getPlacementAmt());
			timedep.setTerm(dep.getTerm());
			timedep.setNetint(timedep.getGrossint().subtract(timedep.getLesswtaxamt()));
			timedep.setDtbook(dep.getBookDate());
			timedep.setDtmat(dep.getMaturityDate());
			timedep.setLesswtaxamt(dep.getLessWtaxAmt());
			timedep.setMatvalue(dep.getMatAmt());
			dep.setAccountBalance(timedep.getPlaceamt());
			timedep.setAccountno(acctSrvc.createAccount(dep, cifList).getValue());
			dbService.save(timedep);
			dbService.executeUpdate("UPDATE TBDEPOSIT SET AccountStatus ='5',AccountBalance=0 WHERE accountno =:acctno",
					param);
			result = 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbtimedeposit> listMaturingAccounts(Date startdate, Date enddate, String dispo) {
		List<Tbtimedeposit> tdList = new ArrayList<Tbtimedeposit>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("startdate", startdate);
		param.put("enddate", enddate);
		param.put("dispo", dispo);
		param.put("branchcode", UserUtil.getUserByUsername(UserUtil.securityService.getUserName()).getBranchcode());
		String q = "select a.interestbalance as intamt,a.intrate as totalint, matvalue as matvalue,b.* "
				+ "from Tbdeposit a left join Tbtimedeposit b on a.accountno = b.accountno WHERE a.accountstatus < 5 and a.unit=:branchcode ";
		try {
			System.out.println(dispo + " << DISPO");
			if (startdate != null) {
				q += "AND maturityDate >=:startdate ";
			}
			if (startdate != null) {
				q += "AND maturityDate <=:enddate ";
			}
			if (dispo != null) {
				q += "AND dispositiontype =:dispo ";
			}
			q += " ORDER BY a.maturityDate ";
			System.out.println(q);
			tdList = (List<Tbtimedeposit>) dbService.executeListSQLQueryWithClass(q, param, Tbtimedeposit.class);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return tdList;
	}

	@Override
	public String terminateTDAccount(String accountno, String credittoacctno) {
		String result = "failed";
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("acctno", accountno);
		param.put("credittoacctno", credittoacctno);
		param.put("username", secservice.getUserName());
		try {
			dbService.executeUpdate("exec sp_preterm :acctno,:credittoacctno,:username", param);
			result = "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String changeDisposition(String accountno, String newdispo, String credittoacctno, String intdispo,
			BigDecimal placementamt, int term, BigDecimal intRate, BigDecimal wTaxRate, Date bookDate, Date matDate,
			String tdcno, String passbookno) {
		String result = "failed";
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("acctno", accountno);
		param.put("newdispo", newdispo);
		param.put("intdispo", intdispo);
		param.put("credittoacctno", credittoacctno);
		param.put("placementamt", placementamt);
		param.put("term", term);
		param.put("intRate", intRate);
		param.put("wTaxRate", wTaxRate);
		param.put("bookDate", bookDate);
		param.put("matDate", matDate);
		param.put("tdcno", tdcno);
		param.put("passbookno", passbookno);
		param.put("username", secservice.getUserName());
		try {
			dbService.executeUpdate("exec sp_changedisposition :acctno, :newdispo, :intdispo, :credittoacctno, "
					+ ":placementamt, :term, :intRate, :wTaxRate, :bookDate, :matDate, :tdcno, :passbookno, "
					+ ":username", param);
			result = "success";
		} catch (Exception e) {
			System.out.println(param);
			e.printStackTrace();
		}
		return result;
	}

//	Ced 05-06-2021 - Removed interest credit and wtax
	@Override
	public String interestWithdrawal(String accountno, BigDecimal amount, String modeofrelease,
			String credtitoaccountno, BigDecimal amttocredit) {
		String result = "failed";
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		DepositTransactionService deptranssrvc = new DepositTransactionServiceImpl();
		DepositTransactionForm form = new DepositTransactionForm();
		param.put("username", secservice.getUserName());
		try {
			Tbtransactioncode tx = (Tbtransactioncode) dbService
					.executeUniqueHQLQuery("FROM Tbtransactioncode WHERE txcode='921391'", param);
			Tbuser user = (Tbuser) dbService.executeUniqueHQLQuery("FROM Tbuser WHERE username =:username", param);
			param.put("branchcode", user.getBranchcode());
			Tbbranch branch = (Tbbranch) dbService.executeUniqueHQLQuery("FROM Tbbranch WHERE branchcode =:branchcode",
					param);
			param.put("acctno", accountno);
			Tbdeposit dep = (Tbdeposit) dbService.executeUniqueHQLQuery("FROM Tbdeposit WHERE accountno =:acctno",
					param);
			// Interest Credit
//			form = new DepositTransactionForm();
//			form.setAccountno(credtitoaccountno);
//			form.setBatchcode("CASA");
//			form.setCurrency("PHP");
//			form.setReason("102");
//			form.setRemarks("");
//			form.setTxamount(amttocredit);
//			form.setTxbranch(user.getBranchcode());
//			form.setTxcode("911391");
//			form.setTxmode(modeofrelease);
//			form.setUsername(user.getUsername());
//			form.setValuedate(branch.getCurrentbusinessdate());
//			form.setInteresttransaction(true);
//			deptranssrvc.creditTransaction(form, tx);
			// Withholding tax
//			if (dep.getWtaxrate().compareTo(BigDecimal.ZERO) == 1) {
//				form = new DepositTransactionForm();
//				form.setAccountno(credtitoaccountno);
//				form.setBatchcode("CASA");
//				form.setCurrency("PHP");
//				form.setReason("102");
//				form.setRemarks("");
//				form.setTxamount(amttocredit.multiply(dep.getWtaxrate()));
//				form.setTxbranch(user.getBranchcode());
//				form.setTxcode("912392");
//				form.setTxmode(modeofrelease);
//				form.setUsername(user.getUsername());
//				form.setValuedate(branch.getCurrentbusinessdate());
//				form.setInteresttransaction(true);
//				deptranssrvc.debitTransaction(form, tx);
//			}
			// Interest Withdrawal
			form.setAccountno(accountno);
			form.setBatchcode("CASA");
			form.setCurrency("PHP");
			form.setReason("102");
			form.setRemarks("");
			form.setTxamount(amount);
			form.setTxbranch(user.getBranchcode());
			form.setTxcode("921391");
			form.setTxmode(modeofrelease);
			form.setUsername(user.getUsername());
			form.setUserid(user.getUserid());
			form.setValuedate(branch.getCurrentbusinessdate());
			form.setInteresttransaction(true);
			DepositTransactionResultForm res = deptranssrvc.debitTransaction(form, tx);
			if (res.getResult().equals("success")) {
				if (credtitoaccountno != null && !credtitoaccountno.equals("")) {
					form = new DepositTransactionForm();
					form.setAccountno(credtitoaccountno);
					form.setBatchcode("CASA");
					form.setCurrency("PHP");
					form.setReason("102");
					form.setRemarks("");
					form.setTxamount(amount);
					form.setTxbranch(user.getBranchcode());
					form.setTxcode("113031");
					form.setTxmode(modeofrelease);
					form.setUsername(user.getUsername());
					form.setUserid(user.getUserid());
					form.setValuedate(branch.getCurrentbusinessdate());
					form.setInteresttransaction(true);
					deptranssrvc.creditTransaction(form, tx);
				}
			} else {
				return res.getMessage();
			}
			result = "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Date computeMaturityDate(Date placementdate, int term, String termFreq, int skipWeekend, int skipHoliday) {
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("placementdate", placementdate);
		param.put("term", term);
		param.put("termFreq", termFreq);
		param.put("skipWeekend", skipWeekend);
		param.put("skipHoliday", skipHoliday);
		try {
			return (Date) dbService.executeUniqueSQLQuery(
					"SELECT dbo.fn_maturitydate(cast(:placementdate as date),:term,:termFreq,:skipWeekend,:skipHoliday)",
					param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
