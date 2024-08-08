package com.casa.misc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.cloudfoundry.org.codehaus.jackson.map.ObjectMapper;

import com.casa.misc.forms.MerchantForm;
import com.casa.user.UserInfoService;
import com.casa.user.UserInfoServiceImpl;
import com.coopdb.data.Tbbillspayment;
import com.coopdb.data.Tbbranch;
import com.coopdb.data.Tbcheckbook;
import com.coopdb.data.Tbchecksforclearing;
import com.coopdb.data.Tbcooperative;
import com.coopdb.data.Tbdeposit;
import com.coopdb.data.Tbloanfin;
import com.coopdb.data.Tbloans;
import com.coopdb.data.Tbmerchant;
import com.coopdb.data.Tbmisctx;
import com.coopdb.data.Tbnetamt;
import com.coopdb.data.Tbpassbookissuance;
import com.coopdb.data.Tbprodmatrix;
import com.coopdb.data.Tbtransactioncode;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.deposittransaction.DepositTransactionService;
import com.etel.deposittransaction.DepositTransactionServiceImpl;
import com.etel.deposittransaction.form.DepositTransactionForm;
import com.etel.glentries.GLEntriesService;
import com.etel.glentries.GLEntriesServiceImpl;
import com.etel.lms.TransactionService;
import com.etel.lms.TransactionServiceImpl;
import com.etel.lmsinquiry.LMSInquiryService;
import com.etel.lmsinquiry.LMSInquiryServiceImpl;
import com.etel.lmsinquiry.forms.PaymentBreakdownForm;
import com.etel.util.ConfigPropertyUtil;
import com.etel.util.Connection;
import com.etel.util.HQLUtil;
import com.etel.util.SequenceGenerator;
import com.etel.utils.UserUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class MiscTxServiceImpl implements MiscTxService {

	DBService dbService = new DBServiceImpl();
	Map<String, Object> param = HQLUtil.getMap();
	SecurityService service = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private String result = "0";
	private static boolean check = Connection.connectionCheck();
	private String wsurl = ConfigPropertyUtil.getPropertyValue("ws_url");

	@SuppressWarnings("unchecked")
	@Override
	public List<MerchantForm> getMerchantList() {
		// TODO Auto-generated method stub
		List<MerchantForm> list = null;
		try {
			list = (List<MerchantForm>) dbService.execStoredProc(
					"SELECT merchantcode, merchantname, accountno as merchantacctno FROM TBMERCHANT", null,
					MerchantForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String createPayment(Tbbillspayment payment, List<Tbchecksforclearing> checks,
			PaymentBreakdownForm paymentbreakdown) {
		try {
			if (payment.getTxrefno() == null) {
				payment.setTxrefno((String) dbService.executeUniqueSQLQuery(
						"DECLARE @txrefno VARCHAR(20) EXEC SEQGENERATE @txrefno OUTPUT SELECT @txrefno", param));
			}
			if (payment.getPaymmode().equals("3")) {
				if (payment.getDebitaccountno() == null || payment.getDebitaccountno().trim().isEmpty()) {
					return "Invalid Deposit Account Number";
				}
				param.put("debitaccountno", payment.getDebitaccountno());
				Tbdeposit dep = (Tbdeposit) dbService
						.executeUniqueHQLQuery("FROM Tbdeposit WHERE accountno=:debitaccountno", param);
				if (dep == null) {
					return "Invalid Deposit Account Number";
				}
				if (dep.getAccountBalance()
						.subtract(dep.getFloatAmount().add(dep.getPlaceholdAmt()).add(dep.getEarmarkbal())
								.add(dep.getGarnishedbal()).add(dep.getPlacementAmt()))
						.compareTo(payment.getAmount()) == -1) {
					return "Insufficient Deposit Account Available Balance.";
				}
			}
			payment.setDatecreated(new Date());
			payment.setUnit(UserUtil.getUserByUsername(service.getUserName()).getBranchcode());
			if ((Integer) dbService.execStoredProc(null, null, null, 3, payment) > 0) {
//				param.put("txrefno", payment.getTxrefno());
//				System.out.println(param.get("txrefno"));
//				result = String.valueOf(dbService.execStoredProc("DECLARE @result VARCHAR(3) "
//						+ "EXEC MISCTX_SP @txrefno=:txrefno, @misctype='BILLS', @resultout = @result OUTPUT "
//						+ "SELECT @result ", param, null, 0, null));
				param.put("txamt", payment.getAmount());
				param.put("userid", payment.getTxby());
				param.put("currency", "PHP");
				if (checks != null && !checks.isEmpty()) {
					for (int i = 0; i < checks.size(); i++) {
						Tbchecksforclearing check = checks.get(i);
						Calendar cal = Calendar.getInstance();
						cal.setTime(check.getCheckdate());
						cal.add(Calendar.DAY_OF_MONTH, check.getClearingdays() - 1);
						check.setClearingdate(cal.getTime());
						if (check.getIslateclearing()) {
							check.setClearingdays(check.getClearingdays() + 1);
							cal.setTime(check.getClearingdate());
							cal.add(Calendar.DAY_OF_MONTH, 1);
							check.setClearingdate(cal.getTime());
							checks.set(i, check);
						}
						param.put("clearingdate", check.getClearingdate());
						check.setClearingdate((Date) dbService.execStoredProc(
								"select dbo.duedategenerator(999,:clearingdate,'1',1,:clearingdate,1,1,1)", param, null,
								0, null));
						check.setTxrefno(payment.getTxrefno());
						dbService.save(check);
					}
				}
				if (payment.getPaymmode().equals("1")) {
					Tbnetamt netamt = (Tbnetamt) dbService.executeUniqueHQLQuery(
							"FROM Tbnetamt WHERE userid =:userid and currency =:currency and transfertype=1", param);
					netamt.setUserbalance(netamt.getUserbalance().add(payment.getAmount()));
					dbService.saveOrUpdate(netamt);
//					dbService.executeUpdate("UPDATE TBNETAMT SET userbalance = userbalance +:txamt "
//							+ "WHERE userid =:userid and currency =:currency and transfertype=1", param);
				}
				if (payment.getPaymmode().equals("3")) { // DEBIT TO CASA ACCOUNT
					DepositTransactionService depSrvc = new DepositTransactionServiceImpl();
					// DEBIT MEMO
					Tbtransactioncode tx = (Tbtransactioncode) dbService
							.executeUniqueHQLQuery("FROM Tbtransactioncode WHERE txcode ='122023'", param);
					DepositTransactionForm depForm = new DepositTransactionForm();
					depForm.setAccountno(payment.getDebitaccountno());
					depForm.setTxcode(tx.getTxcode());
					depForm.setValuedate(payment.getTxdate());
					depForm.setReason("9");
					depForm.setOverridestatus("0");
					depForm.setTxbranch(payment.getUnit());
					depForm.setAccountnoto("");
					depForm.setErrorcorrect(false);
					depForm.setTxamount(payment.getAmount());
					depForm.setTxmode("3");
					depSrvc.casaTransaction(depForm, tx, null);
				}
				if (payment.getTypepayment().equals("L")) {// LOANS PAYMENT
					Tbloans loan = null;
					LMSInquiryService loanSrvc = new LMSInquiryServiceImpl();
					loan = loanSrvc.accountform(payment.getSubsaccountno()).getAccount();
					if (loan != null) {
						Tbloanfin fin = new Tbloanfin();
						fin.setAccountno(loan.getAccountno());
						fin.setCifno(loan.getPrincipalNo());
						fin.setClientname(loan.getFullname());
						fin.setCreatedby(UserUtil.securityService.getUserName());
						fin.setCreationdate(new Date());
						fin.setEmployeeno(loan.getPrincipalNo());
						fin.setParticulars(payment.getRemarks());
						fin.setPnno(loan.getPnno());
						fin.setReason("8");
						fin.setRemarks(payment.getRemarks());
						fin.setSlaidno(loan.getPrincipalNo());
						fin.setTxamount(payment.getAmount());
						fin.setTxamtbal(payment.getAmount());
						fin.setTxdate(new Date());
						fin.setTxmode(payment.getPaymmode());
						fin.setTxoper(2);
						fin.setTxor(payment.getOrno());
						fin.setTxprin(paymentbreakdown.getPrincipal());
						fin.setTxint(paymentbreakdown.getInterest());
						fin.setTxlpc(paymentbreakdown.getPenalty());
						fin.setTxstatus("9");
						fin.setTxstatusdate(new Date());
						fin.setTxvaldt(payment.getTxdate());
						fin.setTxcode("40");
						fin.setRemarks(payment.getTxrefno());
						fin.setParticulars(payment.getTxrefno());
						TransactionService txsrvc = new TransactionServiceImpl();
						String txrefno = txsrvc.addEntry(fin, null);
						if (txrefno.equals("Failed")) {
							return "Loan payment not created.";
						}
						GLEntriesService glSrvc = new GLEntriesServiceImpl();
						glSrvc.getGLEntriesByPnnoAndTxCode(loan.getAccountno(), "100", txrefno, true, false);
//					if (!txsrvc.postSinglePayment(txrefno).equals("Successfully Posted the Transaction.")) {
//						return "5";
//					}
						// Ced 11-08-2022
						if (!txsrvc.postSinglePayment(txrefno).equals("Successfully Posted the Transaction.")) {
							return "5";
						}
						// Ced 11-08-2022

					}
				}
				return payment.getTxrefno();
			}
		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return "Unable to process at the moment.";
	}

	@Override
	public String createMiscTx(Tbmisctx misc, List<Tbchecksforclearing> checks) {
		// TODO Auto-generated method stub
		UserInfoService uinfosrvc = new UserInfoServiceImpl();
		result = "0";
		try {
			if (misc.getTxcode().equals("195152")
					&& uinfosrvc.getUnitBalance(misc.getCreatedby(), "PHP", "1").compareTo(misc.getTxamount()) == -1) {
				return "You do not have enough cash to complete this transaction.";
			}
			misc.setDatecreated(new Date());
			misc.setTxdate(new Date());
			if (misc.getTxrefno() == null) {
				misc.setTxrefno((String) dbService.executeUniqueSQLQuery(
						"DECLARE @txrefno VARCHAR(20) EXEC SEQGENERATE @txrefno OUTPUT SELECT @txrefno", param));
			}
			misc.setBranchcode(UserUtil.getUserByUsername(service.getUserName()).getBranchcode());
			if ((Integer) dbService.execStoredProc("", null, null, 3, misc) > 0) {
				param.put("txamt", misc.getTxamount());
				param.put("userid", misc.getCreatedby());
				param.put("currency", "PHP");
				if (checks != null && !checks.isEmpty()) {
					for (int i = 0; i < checks.size(); i++) {
						Tbchecksforclearing check = checks.get(i);
						Calendar cal = Calendar.getInstance();
						cal.setTime(check.getCheckdate());
						cal.add(Calendar.DAY_OF_MONTH, check.getClearingdays() - 1);
						check.setClearingdate(cal.getTime());
						if (check.getIslateclearing()) {
							check.setClearingdays(check.getClearingdays() + 1);
							cal.setTime(check.getClearingdate());
							cal.add(Calendar.DAY_OF_MONTH, 1);
							check.setClearingdate(cal.getTime());
							checks.set(i, check);
						}
						param.put("clearingdate", check.getClearingdate());
						check.setClearingdate((Date) dbService.execStoredProc(
								"select dbo.duedategenerator(999,:clearingdate,'1',1,:clearingdate,1,1,1)", param, null,
								0, null));
						check.setTxrefno(misc.getTxrefno());
						dbService.save(check);
					}
				}
				if (misc.getTxcode().equals("194142") && misc.getPaymode().equals("1")) {
					Tbnetamt netamt = (Tbnetamt) dbService.executeUniqueHQLQuery(
							"FROM Tbnetamt WHERE userid =:userid and currency =:currency and transfertype=1", param);
					netamt.setUserbalance(netamt.getUserbalance().add(misc.getTxamount()));
					dbService.saveOrUpdate(netamt);
//					dbService.executeUpdate("UPDATE TBNETAMT SET userbalance = userbalance+:txamt "
//							+ "WHERE userid =:userid and currency =:currency and transfertype=1", param);
				} else if (misc.getTxcode().equals("195152") && misc.getPaymode().equals("1")) {
					Tbnetamt netamt = (Tbnetamt) dbService.executeUniqueHQLQuery(
							"FROM Tbnetamt WHERE userid =:userid and currency =:currency and transfertype=1", param);
					netamt.setUserbalance(netamt.getUserbalance().subtract(misc.getTxamount()));
					dbService.saveOrUpdate(netamt);
//					dbService.executeUpdate("UPDATE TBNETAMT SET userbalance = userbalance -:txamt "
//							+ "WHERE userid =:userid and currency =:currency and transfertype=1", param);
				}
				result = misc.getTxrefno();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String addMerchant(Tbmerchant merch) {
		// TODO Auto-generated method stub
		try {
			/***
			 * result 0 = account not found/exist result 1 = success result 2 = error in
			 * saving
			 ***/
			if (merch.getAccountno() != null) {
				param.put("acctno", merch.getAccountno());
				if ((Integer) dbService.execStoredProc("SELECT COUNT(*) FROM TBDEPOSIT WHERE AccountNo=:acctno", param,
						null, 0, null) > 0) {
					merch.setMerchantcode(
							SequenceGenerator.generateMerchSequence(merch.getUnit(), merch.getInstcode()));
					if ((Integer) dbService.execStoredProc(null, null, null, 3, merch) > 0) {
						result = "1";
					} else {
						result = "2";
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String checkbookIssuance(Tbcheckbook data) {
		// TODO Auto-generated method stub
		/***
		 * result 0 = account not found/exist, result 1 = success, result 999 = error in
		 * routine, result 503 = no connection
		 ***/
		try {
			if (check) {
				URL url = new URL(wsurl + "/csr/checkbook-issuance/release");
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setDoOutput(true);
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Type", "application/json");
				ObjectMapper mapper = new ObjectMapper();
				String jsonData = mapper.writeValueAsString(data);
				OutputStream os = con.getOutputStream();
				os.write(jsonData.getBytes());
				os.flush();
				if (con.getResponseCode() != HttpURLConnection.HTTP_ACCEPTED) {
					result = "999";
				} else {
					BufferedReader in = new BufferedReader(new InputStreamReader((con.getInputStream())));
					result = mapper.readValue(in.readLine(), String.class);
				}
				con.disconnect();
			} else {
				result = "503";
			}
			System.out.println("ISSUANCE OF CHECKBOOK RESULT : " + result);
			// param.put("acctno", data.getAccountno());
			// if ((Integer) dbService.execStoredProc("SELECT COUNT(*) FROM TBDEPOSIT WHERE
			// AccountNo=:acctno", param,
			// null, 0, null) > 0) {
			// dbService.execStoredProc(null, null, null, 3, data);
			// result = "1";
			// }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = "999";
		}
		return result;
	}

	@Override
	public String passbookIssuance(Tbpassbookissuance pbissuance) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			pbissuance.setTxby(service.getUserName());
			pbissuance.setTxdate(new Date());
			param.put("acctno", pbissuance.getAccountno());
			param.put("newpbn", pbissuance.getNewpssbksn());
			if ((Integer) dbService.execStoredProc("SELECT COUNT(*) FROM Tbpassbookissuance WHERE newpssbksn=:newpbn",
					param, null, 0, null) == 1) {
				return "New Passbook Number is already issued!";
			}

			if (!pbissuance.getIssuancetype().equals("1")) {
				param.put("oldpb", pbissuance.getOldpassbksn());
				Tbpassbookissuance oldpb = (Tbpassbookissuance) dbService.execStoredProc(
						"SELECT * FROM Tbpassbookissuance WHERE accountno=:acctno and newpssbksn=:oldpb", param,
						Tbpassbookissuance.class, 0, null);
				if (oldpb == null) {
					return "Incorrect Old Passbook Number!";
				}
			}
			dbService.save(pbissuance);
//				List<String> datatoprint = new ArrayList<String>();
//				Tbdeposit deposit = (Tbdeposit) dbService
//						.executeUniqueHQLQuery("FROM Tbdeposit WHERE accountno=:acctno", param);
//				param.put("brcode", deposit.getUnit());
//				datatoprint.add("					" + pbissuance.getAccountno());
//				datatoprint.add("					" + deposit.getAccountName());
//				datatoprint.add("					"
//						+ (String) dbService.execStoredProc("SELECT branchname FROM Tbbranch WHERE branchcode =:brcode",
//								param, null, 0, null));
//				PrinterUtil.printerUtil(datatoprint, 1);
			return "Passbook Number Issued.";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Tbdeposit getAcctDetails(String acctno) {
		Tbdeposit dep = new Tbdeposit();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			param.put("acctno", acctno);
			if (acctno != null) {
				dep = (Tbdeposit) dbService.executeUniqueHQLQuery("FROM Tbdeposit WHERE accountno=:acctno", param);
			}
			if (dep != null) {
				param.put("accountno", acctno);
				param.put("bookdate", dep.getBookDate());
				// Interest Accrued
				dep.setInterestbalance(dbService.getSQLAmount(
						"SELECT ISNULL(SUM(txamount),0) FROM Tbdeptxjrnl WHERE accountno=:accountno AND txcode='911400' AND txvaldt>:bookdate",
						param));
				// Interest Credited
				dep.setInterestpaid(dbService.getSQLAmount(
						"SELECT ISNULL(SUM(txamount),0) FROM Tbdeptxjrnl WHERE accountno=:accountno AND txcode='911401' AND txvaldt>:bookdate",
						param));
				// Interest Accrued - Interest Credited
				dep.setInterestbalance(dep.getInterestbalance().subtract(dep.getInterestpaid()));
				// Withholding Tax
				dep.setBtddebits(dbService.getSQLAmount(
						"SELECT ISNULL(SUM(txamount),0) FROM Tbdeptxjrnl WHERE accountno=:accountno AND txcode='912401' AND txvaldt>:bookdate",
						param));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dep;
	}

	@Override
	public int checkFreeze(String acctno) {
		// TODO Auto-generated method stub
		int chk = 0;
		try {
			param.put("acctno", acctno);
			Integer count = (Integer) dbService.executeUniqueSQLQuery(
					"SELECT COUNT(*) FROM Tbdeposit WHERE accountno=:acctno AND freezeind is not null and freezeind = 1",
					param);
			if (count > 0) {
				chk = 1;
			} else {
				chk = 0;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return chk;
	}

	@Override
	public Tbcooperative getMemfeeAmount(String coopcode) {
		// TODO Auto-generated method stub
		Tbcooperative coop = new Tbcooperative();
		try {
			param.put("coopcode", coopcode);
			coop = (Tbcooperative) dbService.executeUniqueHQLQuery("FROM Tbcooperative WHERE coopcode=:coopcode",
					param);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return coop;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbpassbookissuance> getPassBook(String accountno, String issuancetype) {
		// TODO Auto-generated method stub
		List<Tbpassbookissuance> pb = new ArrayList<Tbpassbookissuance>();
		param.put("acctno", accountno);
		String qry = "FROM Tbpassbookissuance WHERE accountno=:acctno";
		try {
			if (issuancetype != null) {
				param.put("issuancetype", issuancetype);
				qry += " AND issuancetype=:issuancetype";
			}
			qry += " ORDER BY txdate desc";
			pb = (List<Tbpassbookissuance>) dbService.executeListHQLQuery(qry, param);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return pb;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbdeposit> getAcctDetailsList(String acctno, String clientname) {
		// System.out.println(">> running getAcctDetailsList <<<");
		List<Tbdeposit> dep = new ArrayList<Tbdeposit>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			// System.out.println(">> acctno = " + acctno);
			// System.out.println(">> clientname = " + clientname);

			if (acctno != null && clientname != null) {
				params.put("acctno", acctno);
				params.put("clientname", "%" + clientname + "%");
				dep = (List<Tbdeposit>) dbService.executeListHQLQuery(
						"FROM Tbdeposit WHERE accountno=:acctno AND accountName LIKE :clientname", params);
			} else if (acctno == null && clientname != null) {
				params.put("clientname", "%" + clientname + "%");
				dep = (List<Tbdeposit>) dbService
						.executeListHQLQuery("FROM Tbdeposit WHERE accountName LIKE :clientname", params);
			} else if (acctno != null && clientname == null) {
				params.put("acctno", acctno);
				dep = (List<Tbdeposit>) dbService.executeListHQLQuery("FROM Tbdeposit WHERE accountno=:acctno", params);
			}
			if (dep != null) {
				for (Tbdeposit d : dep) {
					params.put("subproductcode", d.getSubProductCode());
					Tbprodmatrix row = (Tbprodmatrix) dbService
							.executeUniqueHQLQuery("FROM Tbprodmatrix WHERE prodcode=:subproductcode ", params);
					if (row != null) {
						d.setSubProductCode(row.getProdname());
					}
					if (d.getUnit() != null) {
						params.put("unit", d.getUnit());
						Tbbranch b = (Tbbranch) dbService.executeUniqueHQLQuery("FROM Tbbranch WHERE branchcode=:unit ",
								params);
						if (b != null) {
							d.setUnit(b.getBranchname());
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dep;
	}

	// 11-17-2022 MAR
	@Override
	public Tbmisctx getMiscTxDetails(String txrefno, String mediaNo) {
		Tbmisctx mis = new Tbmisctx();
		try {
			param.put("mediaNo", mediaNo);
			mis = (Tbmisctx) dbService.executeUniqueHQLQuery("FROM Tbmisctx WHERE medianumber=:mediaNo", param);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return mis;
	}

	@Override
	public String errorCorrectMiscTx(Tbmisctx misc, List<Tbchecksforclearing> checks, String txrefno) {
		UserInfoService uinfosrvc = new UserInfoServiceImpl();
//		Tbchecksforclearing c = new Tbchecksforclearing();
		Tbmisctx mt = new Tbmisctx();
		Tbmisctx m = new Tbmisctx();
		result = "0";
		try {
			param.put("txrefno", txrefno);
			m = (Tbmisctx) dbService.executeUniqueHQLQuery("FROM Tbmisctx WHERE txrefno=:txrefno", param);

//			if (m.getTxcode().equals("195152")) {
//				c = (Tbchecksforclearing) dbService
//						.executeUniqueHQLQuery("FROM Tbchecksforclearing WHERE txrefno=:txrefno", param);
//			}
			if (m.getTxcode().equals("195152")
					&& uinfosrvc.getUnitBalance(m.getCreatedby(), "PHP", "1").compareTo(m.getTxamount()) == -1) {
				return "You do not have enough cash to complete this transaction.";
			}
			String refno = (String) dbService.executeUniqueSQLQuery(
					"DECLARE @txrefno VARCHAR(20) EXEC SEQGENERATE @txrefno OUTPUT SELECT @txrefno", param);
			mt.setTxrefno(refno);
			mt.setTxcode(m.getTxcode());
			mt.setMediatype(m.getMediatype());
			mt.setMedianumber(m.getMedianumber());
			mt.setTxcode(m.getTxcode());
			mt.setTxamount(m.getTxamount());
			mt.setCreatedby(m.getCreatedby());
			mt.setDatecreated(new Date());
			mt.setRemarks("EC");
			mt.setPaymode(m.getPaymode());
			mt.setIslateclearing(m.getIslateclearing());

			mt.setTxdate(new Date());
			mt.setTxvaldt(m.getTxvaldt());
			mt.setFeecode(m.getFeecode());
			mt.setBranchcode(m.getBranchcode());
			mt.setCifno(m.getCifno());
			mt.setErrorcorrectind(true);
			mt.setErrorcorrecttxrefno(m.getTxrefno());

			param.put("txrefnoThatEC", m.getTxrefno());
			dbService.executeUpdate("update Tbmisctx set errorcorrectind = 'true' where txrefno=:txrefnoThatEC", param);
			dbService.executeUpdate("update Tbchecksforclearing set status = '3' where txrefno=:txrefnoThatEC", param);

			param.put("txamt", mt.getTxamount());
			param.put("userid", mt.getCreatedby());
			param.put("currency", "PHP");
			if (mt.getTxcode().equals("194142") && m.getPaymode().equals("1")) {
				Tbnetamt netamt = (Tbnetamt) dbService.executeUniqueHQLQuery(
						"FROM Tbnetamt WHERE userid =:userid and currency =:currency and transfertype=1", param);
				netamt.setUserbalance(netamt.getUserbalance().subtract(mt.getTxamount()));
				dbService.saveOrUpdate(netamt);
//						dbService.executeUpdate("UPDATE TBNETAMT SET userbalance = userbalance+:txamt "
//								+ "WHERE userid =:userid and currency =:currency and transfertype=1", param);
			} else if (mt.getTxcode().equals("195152") && m.getPaymode().equals("1")) {
				Tbnetamt netamt = (Tbnetamt) dbService.executeUniqueHQLQuery(
						"FROM Tbnetamt WHERE userid =:userid and currency =:currency and transfertype=1", param);
				netamt.setUserbalance(netamt.getUserbalance().add(mt.getTxamount()));
				dbService.saveOrUpdate(netamt);
//						dbService.executeUpdate("UPDATE TBNETAMT SET userbalance = userbalance -:txamt "
//								+ "WHERE userid =:userid and currency =:currency and transfertype=1", param);
			}
			result = mt.getTxrefno();
			dbService.saveOrUpdate(mt);

			System.out.print("MAR >>>>>>>>>>>>>>>>>>>>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
