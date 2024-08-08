/**
 * 
 */
package com.etel.multitx;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.casa.misc.MiscTxService;
import com.casa.misc.MiscTxServiceImpl;
import com.coopdb.data.Tbbillspayment;
import com.coopdb.data.Tbchecksforclearing;
import com.coopdb.data.Tbmisctx;
import com.coopdb.data.Tbmultipletransaction;
import com.coopdb.data.Tbtransactioncode;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.deposittransaction.DepositTransactionService;
import com.etel.deposittransaction.DepositTransactionServiceImpl;
import com.etel.deposittransaction.form.DepositTransactionForm;
import com.etel.deposittransaction.form.DepositTransactionResultForm;
import com.etel.inquiry.forms.CIFInquiryForm;
import com.etel.lmsinquiry.forms.PaymentBreakdownForm;
import com.etel.multitx.form.MultipleTransDataForm;
import com.etel.multitx.form.MultipleTransactionAccountForm;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;

/**
 * @author ETEL-LAPTOP07
 * @TransactionStatus {0 = data entry, 1 = posted, 2 = error, 3 = deleted}
 * @ModeofPayment {1 = Cash, 2 = Check}
 * @Prodcode {01 - 98 = Deposit, 99 = Loans}
 */
@SuppressWarnings("unchecked")
public class MultipleTransactionServiceImpl implements MultipleTransactionService {
	private Map<String, Object> param = HQLUtil.getMap();
	DBService dbService = new DBServiceImpl();

	@Override
	public List<Tbmultipletransaction> listTransactions(String multitxrefno) {
		Map<String, Object> param = new HashMap<String, Object>();
		DBService dbsrvc = new DBServiceImpl();
		param.put("multitxrefno", multitxrefno);
		try {
			return (List<Tbmultipletransaction>) dbsrvc
					.executeListHQLQuery("FROM Tbmultipletransaction WHERE multitxrefno=:multitxrefno", param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String addTransaction(Tbmultipletransaction transaction) {
		DBService dbsrvc = new DBServiceImpl();
		try {
			transaction.setTxdate(new Date());
			transaction.setTxstatus("0");
			transaction.setStatusdate(new Date());
			transaction.setCreatedby(UserUtil.securityService.getUserName());
			transaction.setDatecreated(new Date());
			transaction.setId(null);
			dbsrvc.save(transaction);
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public String editTransaction(Tbmultipletransaction transaction) {
		DBService dbsrvc = new DBServiceImpl();
		try {
			transaction.setStatusdate(new Date());
			dbsrvc.saveOrUpdate(transaction);
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public String postTransaction(List<Tbmultipletransaction> multipletransactions,
			List<PaymentBreakdownForm> paymentbreakdown) {
		System.out.println(">>> running postTransaction");
		Map<String, Object> param = new HashMap<String, Object>();
		DBService dbsrvc = new DBServiceImpl();
		DBService dbsrvcCIF = new DBServiceImplCIF();
		DepositTransactionService depsrvc = new DepositTransactionServiceImpl();
		List<Tbchecksforclearing> checks = new ArrayList<Tbchecksforclearing>();
		try {
			if (multipletransactions == null || multipletransactions.size() < 1) {
				return "empty";
			}

			// generate sequence
			String multitxrefno = (String) dbsrvc.executeUniqueSQLQuery(
					"DECLARE @txrefno VARCHAR(20) EXEC SEQGENERATE_PROD 'MTX', @txrefno OUTPUT", param);

			// Cash Deposit
			Tbtransactioncode txcash = (Tbtransactioncode) dbsrvc
					.executeUniqueHQLQuery("FROM Tbtransactioncode where txcode='110111'", param);

			// Other Bank Check Deposit
			Tbtransactioncode txcheck = (Tbtransactioncode) dbsrvc
					.executeUniqueHQLQuery("FROM Tbtransactioncode where txcode='111212'", param);

			// list
			System.out.println(">>> multipletransactions size" + multipletransactions.size());
			for (Tbmultipletransaction transaction : multipletransactions) {
				// GET CIF NAME
				param.put("cifno", transaction.getCifno());
				String cifname = (String) dbsrvcCIF
						.executeUniqueSQLQuery("SELECT fullname FROM Tbcifmain WHERE cifno=:cifno", param);
				transaction.setMultitxrefno(multitxrefno);

				System.out.println(">>> transaction.getTxcode() " + transaction.getTxcode());
				System.out.println(">>> transaction.getProdcode() " + transaction.getProdcode());

				if (transaction.getTxcode() != null && transaction.getTxcode().equals("40")) {
					System.out.println(">>> 3");
					// Loan Payment
					Tbbillspayment loanpayment = new Tbbillspayment();
					loanpayment.setAmount(transaction.getTxamount());
					loanpayment.setCurrency("PHP");
//							loanpayment.setInstcode(multitxrefno);
					loanpayment.setOrno(transaction.getTxor());
					loanpayment.setPaymentslipno(multitxrefno);
					loanpayment.setPaymmode(transaction.getTxmode());
					loanpayment.setRemarks(transaction.getRemarks());
					loanpayment.setSubsaccountno(transaction.getAccountno());
					loanpayment.setSubsname(cifname);
					loanpayment.setTxby(UserUtil.getUserByUsername(UserUtil.securityService.getUserName()).getUserid());
					loanpayment.setTxdate(transaction.getTxvaldt());
					loanpayment.setTxstatus("1");
					loanpayment.setTypepayment("L");
					loanpayment.setIslatecheck(transaction.getIslatecheck());
					loanpayment.setInstcode(
							UserUtil.getUserByUsername(UserUtil.securityService.getUserName()).getCoopcode());
//					LMSInquiryService lmsInqSrvc = new LMSInquiryServiceImpl();
//					PaymentBreakdownForm paymentBreakdownForm = lmsInqSrvc.getPaymentBreakdown(
//							transaction.getAccountno(), transaction.getTxvaldt(), transaction.getTxamount(), null, null,
//							null);
					loanpayment.setTxrefno((String) dbService.executeUniqueSQLQuery(
							"DECLARE @txrefno VARCHAR(20) EXEC SEQGENERATE @txrefno OUTPUT SELECT @txrefno", param));
					PaymentBreakdownForm paymentBreakdownForm = new PaymentBreakdownForm();
					for (PaymentBreakdownForm payment : paymentbreakdown) {
						if (payment.getAccountno().equals(transaction.getAccountno()))
							paymentBreakdownForm = payment;
					}
					checks = new ArrayList<Tbchecksforclearing>();
					Tbchecksforclearing check = new Tbchecksforclearing();
					if (transaction.getTxmode().equals("2")) {
						System.out.println(">>> 3.3");
						check.setAccountnumber(transaction.getAccountno());
						check.setCheckamount(transaction.getTxamount());
						check.setBrstn(transaction.getBrstn());
						check.setCheckdate(transaction.getTxvaldt());
						check.setChecknumber(transaction.getCheckno());
						check.setClearingdays(transaction.getClearingdays());
						check.setClearingtype(transaction.getClearingtype());
						check.setIslateclearing(transaction.getIslatecheck());
						check.setStatus("1");
						check.setTxstatusdate(new Date());
						Calendar cal = Calendar.getInstance();
						cal.setTime(check.getCheckdate());
						cal.add(Calendar.DAY_OF_MONTH, check.getClearingdays() - 1);
						check.setClearingdate(cal.getTime());
						checks.add(check);
					}
					MiscTxService txsrvc = new MiscTxServiceImpl();
					txsrvc.createPayment(loanpayment, checks, paymentBreakdownForm);
					addTransaction(transaction); // call method
				} else if (transaction.getTxcode() != null && transaction.getTxcode().equals("194142")) { // Miscellaneous
					// Other Payment - Miscellaneous
					System.out.println(">>> 1");
					System.out.println(transaction.getTxor());
					MiscTxService miscSrvc = new MiscTxServiceImpl();
					Tbmisctx misctx = new Tbmisctx();
					misctx.setTxamount(transaction.getTxamount());
					misctx.setCreatedby(UserUtil.getUserByUsername(UserUtil.securityService.getUserName()).getUserid());
					misctx.setFeecode(transaction.getFeecode());
//					misctx.setMedianumber(multitxrefno);
					misctx.setMediatype("2");
					misctx.setPaymode(transaction.getTxmode());
					misctx.setRemarks(transaction.getRemarks());
					misctx.setTxcode(transaction.getTxcode());
					misctx.setTxvaldt(transaction.getTxvaldt());
					misctx.setCifno(transaction.getCifno());
					misctx.setIslateclearing(transaction.getIslatecheck());
					misctx.setMedianumber(transaction.getTxor());
					misctx.setTxrefno((String) dbService.executeUniqueSQLQuery(
							"DECLARE @txrefno VARCHAR(20) EXEC SEQGENERATE @txrefno OUTPUT SELECT @txrefno", param));
					checks = new ArrayList<Tbchecksforclearing>();
					Tbchecksforclearing check = new Tbchecksforclearing();
					if (transaction.getTxmode().equals("2")) {
						check = new Tbchecksforclearing();
						check.setAccountnumber(transaction.getAccountno());
						check.setCheckamount(transaction.getTxamount());
						check.setBrstn(transaction.getBrstn());
						check.setCheckdate(transaction.getTxvaldt());
						check.setChecknumber(transaction.getCheckno());
						check.setClearingdays(transaction.getClearingdays());
						check.setClearingtype(transaction.getClearingtype());
						check.setIslateclearing(transaction.getIslatecheck());
						check.setTxstatusdate(new Date());
						check.setStatus("1");
						Calendar cal = Calendar.getInstance();
						cal.setTime(check.getCheckdate());
						cal.add(Calendar.DAY_OF_MONTH, check.getClearingdays() - 1);
						check.setClearingdate(cal.getTime());
						checks.add(check);
					}
					miscSrvc.createMiscTx(misctx, checks); // call method
					addTransaction(transaction);
				} else {
					// Deposit
					System.out.println(">>> 2");
					DepositTransactionResultForm depresultform = new DepositTransactionResultForm();
					DepositTransactionForm depform = new DepositTransactionForm();
					depform.setBatchcode(transaction.getMultitxrefno());
					depform.setAccountno(transaction.getAccountno());
					depform.setCurrency("PHP");
					depform.setRemarks(transaction.getRemarks());
					depform.setTxcode(txcash.getTxcode());
					depform.setErrorcorrect(false);
					depform.setOverridestatus("0");
					depform.setTxamount(transaction.getTxamount());
					depform.setValuedate(transaction.getTxvaldt());
					depform.setTxcode(txcash.getTxcode());
					depform.setTxbranch(
							UserUtil.getUserByUsername(UserUtil.securityService.getUserName()).getBranchcode());
					depform.setRemarks(transaction.getTxor());
					depform.setTxmode(transaction.getTxmode());
					depform.setTxrefno((String) dbsrvc.executeUniqueSQLQuery(
							"DECLARE @txrefno VARCHAR(20) EXEC SEQGENERATE @txrefno OUTPUT SELECT @txrefno", param));
					checks = new ArrayList<Tbchecksforclearing>();
					Tbchecksforclearing check = new Tbchecksforclearing();
					if (transaction.getTxmode().equals("2")) {
						System.out.println(">>> 2.1");
						depform.setTxcode(txcheck.getTxcode());
						check.setAccountnumber(depform.getAccountno());
						check.setCheckamount(transaction.getTxamount());
						check.setBrstn(transaction.getBrstn());
						check.setCheckdate(transaction.getTxvaldt());
						check.setChecknumber(transaction.getCheckno());
						check.setClearingdays(transaction.getClearingdays());
						check.setClearingtype(transaction.getClearingtype());
						check.setIslateclearing(transaction.getIslatecheck());
						check.setStatus("1");
						check.setTxstatusdate(new Date());
						Calendar cal = Calendar.getInstance();
						cal.setTime(check.getCheckdate());
						cal.add(Calendar.DAY_OF_MONTH, check.getClearingdays() - 1);
						check.setClearingdate(cal.getTime());
						checks.add(check);
						System.out.println(checks.size());
						depform.setChecks(checks);
						depresultform = depsrvc.casaTransaction(depform, txcheck, null); // call method
					} else {
						depresultform = depsrvc.casaTransaction(depform, txcash, null); // call method
					}
					if (depresultform.getResult().equals("success")) {
						transaction.setTxstatus("1");
					} else {
						transaction.setTxstatus("3");
						System.out.println(depresultform.getMessage());
					}
					transaction.setTxcode(depform.getTxcode());
					transaction.setTxrefno(depform.getTxrefno());
					addTransaction(transaction); // call method add to Tbmultipletransaction
				}
			}
			return multitxrefno;
		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return "error";
	}

	@Override
	public List<MultipleTransactionAccountForm> listAccounts(String cifno) {
		Map<String, Object> param = new HashMap<String, Object>();
		DBService dbsrvc = new DBServiceImpl();
		List<MultipleTransactionAccountForm> accounts = new ArrayList<MultipleTransactionAccountForm>();

		try {
			// System.out.println(">>> running listAccounts <<< ");
			// System.out.println(">>> cifno <<< " + cifno);
			if (cifno != null) {
				param.put("cifno", cifno);
				accounts = (List<MultipleTransactionAccountForm>) dbsrvc.execSQLQueryTransformer(
						"select a.accountno,'01' as accounttype,b.productcode,d.desc2 as productname,b.subproductcode,"
								+ "c.prodname as subproductname,"
								+ "CAST(IIF((SELECT COUNT(*) FROM Tbpassbookissuance WHERE accountno=b.accountno) != 0,1,0) AS BIT) as wpassbook,"
								+ "b.accountName as name,b.accountbalance,0 as availablebalance,b.placementamt as placementamount "
								+ "FROM Tbdepositcif a left join Tbdeposit b on a.accountno = b.AccountNo "
								+ "left join TBPRODMATRIX c on b.SubProductCode = c.prodcode "
								+ "left join TBCODETABLECASA d on b.ProductCode = d.codevalue where "
								+ "d.codename='productgroup' and a.cifno =:cifno and accountstatus in ('1','3') "
								+ "and ((b.PlacementAmt > 0 and b.accountbalance < b.placementamt) or (b.PlacementAmt=0)) "
								+ "union all "
								+ "select a.accountno,'03',a.prodcode,b.productname,a.prodcode,b.productname,"
								+ "CAST(0 AS BIT),fullname,idealprinbal,prinbal,0 "
								+ "FROM Tbloans a left join Tbloanproduct b on a.prodcode=b.productcode "
								+ "where a.PrincipalNo=:cifno and acctsts < 5",
						param, MultipleTransactionAccountForm.class, 1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return accounts;
	}

	@Override
	public List<CIFInquiryForm> listCIF(String cifno, String cifname) {
		Map<String, Object> param = new HashMap<String, Object>();
		DBService dbsrvc = new DBServiceImplCIF();
		List<CIFInquiryForm> cifList = new ArrayList<CIFInquiryForm>();
		String qry = "SELECT cifno,fullname,b.desc1 as  cifstatus,dateofbirth as birthdate,c.desc1 as ciftype "
				+ "FROM Tbcifmain a left join TBCODETABLE b on a.cifstatus = b.codevalue "
				+ "left join TBCODETABLE c on a.ciftype = c.codevalue where  "
				+ "b.codename = 'cifstatus' and c.codename = 'ciftype'";
		if (cifno != null && !cifno.trim().equals("")) {
			param.put("cifno", cifno);
			qry += " AND a.cifno =:cifno";
		}
		if (cifname != null && !cifname.trim().equals("")) {
			param.put("cifname", "%" + cifname + "%");
			qry += " AND a.fullname like:cifname";
		}
		try {
			cifList = (List<CIFInquiryForm>) dbsrvc.execSQLQueryTransformer(qry, param, CIFInquiryForm.class, 1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return cifList;
	}

	@Override
	public List<MultipleTransDataForm> getMultipleTransaction(Date startDate, Date endDate, String branch,
			String teller) {
		List<MultipleTransDataForm> list = new ArrayList<MultipleTransDataForm>();
		try {
			param.put("startDate", startDate == null ? "" : startDate);
			param.put("endDate", endDate == null ? "" : endDate);
			param.put("branch", branch == null ? "" : branch);
			param.put("teller", teller == null ? "" : teller);

			list = (List<MultipleTransDataForm>) dbService.execStoredProc(
					"EXEC sp_GetMultipleTransactions :startDate, :endDate, :branch, :teller", param,
					MultipleTransDataForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

}
