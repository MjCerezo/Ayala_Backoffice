/**
 * 
 */
package com.etel.deposittransaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.casa.user.UserInfoService;
import com.casa.user.UserInfoServiceImpl;
import com.coopdb.data.Tbbranch;
import com.coopdb.data.Tbchecksforclearing;
import com.coopdb.data.Tbdeposit;
import com.coopdb.data.Tbdeptxjrnl;
import com.coopdb.data.Tbfintxjrnl;
import com.coopdb.data.Tbnetamt;
import com.coopdb.data.Tboverriderequest;
import com.coopdb.data.Tbtransactioncode;
import com.coopdb.data.Tbuser;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.deposittransaction.form.DepositTransactionForm;
import com.etel.deposittransaction.form.DepositTransactionResultForm;
import com.etel.override.OverrideService;
import com.etel.override.OverrideServiceImpl;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

/**
 * @author ETEL-LAPTOP19
 *
 */
@SuppressWarnings("unchecked")
public class DepositTransactionServiceImpl implements DepositTransactionService {

	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");

	@Override
	public Tbdeptxjrnl createDepJrnl(DepositTransactionForm form) {
		Tbdeptxjrnl jrnl = new Tbdeptxjrnl();
		try {
			jrnl.setAccountno(form.getAccountno());
			jrnl.setTxbranch(form.getTxbranch());
			jrnl.setCreatedBy(form.getUserid());
			jrnl.setCreationDate(new Date());
			jrnl.setReason(form.getReason() == null || form.getReason().isEmpty() ? "9" : form.getReason());
			jrnl.setTxAmount(form.getTxamount());
			jrnl.setTxBatch(form.getBatchcode());
			jrnl.setTxcode(form.getTxcode());
//			jrnl.setTxdate(form.getValuedate());
			jrnl.setTxdate(new Date());
			jrnl.setTxmode(form.getTxmode());
			jrnl.setTxvaldt(form.getValuedate());
			jrnl.setTxStatus(form.getTxstatus());
			jrnl.setErrorcorrectind(form.isErrorcorrect());
			jrnl.setErrorcorrecttxrefno(form.getErrorcorrecttxrefno());
			jrnl.setTransfertxrefno(form.getTransfertxrefno());
			jrnl.setRemarks(form.getRemarks());
			jrnl.setOverridestatus(form.getOverridestatus());
			jrnl.setTxBatch(form.getBatchcode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jrnl;
	}

	public Tbfintxjrnl createFinJrnl(DepositTransactionForm form) {
		Tbfintxjrnl jrnl = new Tbfintxjrnl();
		try {
			jrnl.setAccountno(form.getAccountno());
			jrnl.setAcctname(form.getTxbranch());
			jrnl.setReasoncode(form.getReason() == null || form.getReason().isEmpty() ? "9" : form.getReason());
			jrnl.setTxamount(form.getTxamount());
			jrnl.setTxbatch(form.getBatchcode());
			jrnl.setTxcode(form.getTxcode());
			jrnl.setTxdate(new Date());
			jrnl.setTxmode(form.getTxmode());
			jrnl.setTxvaldt(form.getValuedate());
			jrnl.setCurrency(form.getCurrency());
			jrnl.setRemarks(form.getRemarks());
			jrnl.setTxby(form.getUsername());
			jrnl.setUnit(form.getTxbranch());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jrnl;
	}

	@Override
	public DepositTransactionResultForm creditTransaction(DepositTransactionForm form, Tbtransactioncode tx) {
		DepositTransactionResultForm result = new DepositTransactionResultForm();
		Tbdeptxjrnl jrnl = new Tbdeptxjrnl();
		Tbdeposit dep = new Tbdeposit();
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("acctno", form.getAccountno());
		BigDecimal checkAmt = BigDecimal.ZERO;
		BigDecimal checkAmtForClearing = BigDecimal.ZERO;
		try {
			if (form.getTxrefno() == null || form.getTxrefno().equals("")) {
				jrnl = createDepJrnl(form);
				jrnl.setTxoper(2);
				jrnl.setTxRefNo((String) dbsrvc.executeUniqueSQLQuery(
						"DECLARE @txrefno VARCHAR(20) EXEC SEQGENERATE @txrefno OUTPUT SELECT @txrefno", param));
				jrnl.setTxStatus("1");
				jrnl.setTxStatusDate(new Date());
				dbsrvc.save(jrnl);
			} else {
				jrnl.setTxRefNo(form.getTxrefno());
			}
			param.put("txrefno", jrnl.getTxRefNo());
			jrnl = (Tbdeptxjrnl) dbsrvc
					.executeUniqueHQLQuery("FROM Tbdeptxjrnl WHERE txrefno=:txrefno and accountno=:acctno", param);
			if (jrnl == null) {
				jrnl = createDepJrnl(form);
				jrnl.setTxoper(2);
				jrnl.setTxStatus("1");
				jrnl.setTxStatusDate(new Date());
				jrnl.setTxRefNo(form.getTxrefno());
				dbsrvc.save(jrnl);
				jrnl = (Tbdeptxjrnl) dbsrvc
						.executeUniqueHQLQuery("FROM Tbdeptxjrnl WHERE txrefno=:txrefno and accountno=:acctno", param);
			}
			dep = (Tbdeposit) dbsrvc.executeUniqueHQLQuery("FROM Tbdeposit WHERE accountNo=:acctno", param);
			if (dep == null) {
				jrnl.setTxStatus("3");
				jrnl.setTxStatusDate(new Date());
				jrnl.setRemarks("Account not found");
				dbsrvc.saveOrUpdate(jrnl);
				result.setResult("error");
				result.setMessage(jrnl.getRemarks());
				return result;
			} else {
				jrnl.setAcctsts(String.valueOf(dep.getAccountStatus()));
				jrnl.setTdcno(dep.getTdcno());
			}
			if (dep.getFreezeind() != null && dep.getFreezeind() == 1) {
				jrnl.setTxStatus("3");
				jrnl.setTxStatusDate(new Date());
				jrnl.setRemarks("Freeze Account");
				dbsrvc.saveOrUpdate(jrnl);
				result.setResult("error");
				result.setMessage(jrnl.getRemarks());
				return result;
			}
			if (dep.getAccountStatus() == 5) {
				jrnl.setTxStatus("3");
				jrnl.setTxStatusDate(new Date());
				jrnl.setRemarks("Closed Account");
				dbsrvc.saveOrUpdate(jrnl);
				result.setResult("error");
				result.setMessage(jrnl.getRemarks());
				return result;
			}
			if (form.getOverridestatus().equals("3") || form.getOverridestatus().equals("6")) {
				jrnl.setTxStatus("4");
				jrnl.setTxStatusDate(new Date());
				jrnl.setOverridestatus(form.getOverridestatus());
				dbsrvc.saveOrUpdate(jrnl);
			} else if (form.getOverridestatus().equals("7")) {
				jrnl.setTxStatus("3");
				jrnl.setTxStatusDate(new Date());
				jrnl.setOverridestatus(form.getOverridestatus());
				dbsrvc.saveOrUpdate(jrnl);
			} else if (form.getOverridestatus().equals("8")) {
				jrnl.setTxStatus("4");
				jrnl.setTxStatusDate(new Date());
				dbsrvc.saveOrUpdate(jrnl);
			} else {
				if (form.getChecks() != null && form.getChecks().size() > 0) {
					for (int i = 0; i < form.getChecks().size(); i++) {
						Tbchecksforclearing check = form.getChecks().get(i);
						Calendar cal = Calendar.getInstance();
						cal.setTime(check.getCheckdate());
						cal.add(Calendar.DAY_OF_MONTH, check.getClearingdays() - 1);
						check.setClearingdate(cal.getTime());
						if (!jrnl.getErrorcorrectind()) {
							check.setTxrefno(jrnl.getTxRefNo());
							if (check.getIslateclearing()) {
								check.setClearingdays(check.getClearingdays() + 1);
								cal.setTime(check.getClearingdate());
								cal.add(Calendar.DAY_OF_MONTH, 1);
								check.setClearingdate(cal.getTime());
								jrnl.setIslatecheck(true);
							}
							param.put("clearingdate", check.getClearingdate());
							check.setClearingdate((Date) dbsrvc.execStoredProc(
									"select dbo.duedategenerator(999,:clearingdate,'1',1,:clearingdate,1,1,1)", param,
									null, 0, null));
						} else {
							check.setStatus("1");
						}
						if (check.getClearingdays() == 0) {
							check.setStatus("2");
						} else {
							checkAmtForClearing = checkAmt.add(check.getCheckamount());
						}
						checkAmt = checkAmt.add(check.getCheckamount());
						form.getChecks().set(i, check);
					}
					if (checkAmt.compareTo(form.getTxamount()) != 0 && !jrnl.getErrorcorrectind()) {
						jrnl.setTxStatus("3");
						jrnl.setTxStatusDate(new Date());
						jrnl.setRemarks("Total check amount is not equal to amount inputted.");
						dbsrvc.saveOrUpdate(jrnl);
						result.setResult("error");
						result.setMessage(jrnl.getRemarks());
						return result;
					}
					dep.setFloatAmount(dep.getFloatAmount().add(checkAmtForClearing));
				}
				if (tx.getIsinteresttx()) {
					if (tx.getTxcode().equals("911401")) { // INTEREST CREDIT
						dep.setInterestbalance(dep.getInterestbalance().subtract(form.getTxamount()));
						dep.setAccountBalance(dep.getAccountBalance().add(form.getTxamount()));
					} else {
						dep.setInterestbalance(dep.getInterestbalance().add(form.getTxamount()));
						dep.setInterestearned(dep.getInterestearned().add(form.getTxamount()));
					}
				} else {
					dep.setAccountBalance(dep.getAccountBalance().add(form.getTxamount()));
				}
				jrnl.setOutBal(dep.getAccountBalance());
				jrnl.setCredit(form.getTxamount());
				if (form.getOverridestatus().equals("0") || form.getOverridestatus().equals("2")
						|| form.getOverridestatus().equals("5")) {
					jrnl.setTxStatus("2");
					jrnl.setTxStatusDate(new Date());
					jrnl.setOverridestatus(form.getOverridestatus());
					if (tx.getWcash()) {
						dep.setTotalNoCredits(dep.getTotalNoCredits() + 1);
						dep.setMtdcredits(dep.getMtdcredits().add(form.getTxamount()));
						dep.setYtdcredits(dep.getYtdcredits().add(form.getTxamount()));
						dep.setBtdcredits(dep.getBtdcredits().add(form.getTxamount()));
						dep.setLasttxcode(form.getTxcode());
//						dep.setLasttxdate(jrnl.getTxvaldt());
						param.put("txamt", form.getTxamount());
						param.put("userid", form.getUserid());
						param.put("currency", form.getCurrency() == null ? "PHP" : form.getCurrency());
//						dbsrvc.executeUpdate("UPDATE TBNETAMT SET userbalance = userbalance +:txamt "
//								+ "WHERE userid =:userid and currency =:currency and transfertype=1", param);
						Tbnetamt netamt = (Tbnetamt) dbsrvc.executeUniqueHQLQuery(
								"FROM Tbnetamt WHERE userid =:userid and currency =:currency and transfertype=1",
								param);
						netamt.setUserbalance(
								netamt.getUserbalance().add(form.getTxamount().subtract(checkAmtForClearing)));
						dbsrvc.saveOrUpdate(netamt);
					}
					// Correction Advise
					if (!jrnl.getReason().equals("199")) {
						dep.setLasttxdate(jrnl.getTxvaldt());
						// REMOVE DORMANT
						if (dep.getAccountStatus() == 3) {
							dep.setAccountStatus(1);
							dep.setOldstatus(3);
							dep.setOldStatusDate(dep.getStatusDate());
							dep.setStatusDate(jrnl.getTxvaldt());
							jrnl.setOldacctsts("3");
							jrnl.setAcctsts("1");
						}
					}
					// dep.setStatusDate(jrnl.getTxvaldt());
					if (dbsrvc.saveOrUpdate(dep) && dbsrvc.saveOrUpdate(jrnl)) {
						if (form.getChecks() != null && form.getChecks().size() > 0) {
							for (Tbchecksforclearing check : form.getChecks()) {
								dbsrvc.save(check);
							}
						}
						param.put("txrefno", jrnl.getTxRefNo());
						dbsrvc.executeUpdate(
								"UPDATE Tbdeposit SET jrnlno=(select id from tbdeptxjrnl where txrefno=:txrefno and accountno=:acctno) where accountno=:acctno",
								param);
//					finjrnl = createFinJrnl(form);
//					finjrnl.setTxrefmain(jrnl.getTxRefNo());
//					dbsrvc.save(finjrnl);
					}
					if (jrnl.getErrorcorrectind()) {
						param.put("ectxrefno", jrnl.getErrorcorrecttxrefno());
						dbsrvc.executeUpdate(
								"UPDATE Tbdeptxjrnl SET errorcorrectind = 1, errorcorrecttxrefno =:txrefno where txrefno =:ectxrefno and accountno=:acctno",
								param);
					}
				}
			}
			result.setResult("success");
			result.setMessage(jrnl.getTxRefNo());
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public DepositTransactionResultForm debitTransaction(DepositTransactionForm form, Tbtransactioncode tx) {
		DepositTransactionResultForm result = new DepositTransactionResultForm();
		Tbdeptxjrnl jrnl = new Tbdeptxjrnl();
		Tbdeposit dep = new Tbdeposit();
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("acctno", form.getAccountno());
		try {
			if (form.getTxrefno() == null || form.getTxrefno().equals("")) {
				jrnl = createDepJrnl(form);
				jrnl.setTxoper(1);
				jrnl.setTxRefNo((String) dbsrvc.executeUniqueSQLQuery(
						"DECLARE @txrefno VARCHAR(20) EXEC SEQGENERATE @txrefno OUTPUT SELECT @txrefno", param));
				jrnl.setTxStatus("1");
				jrnl.setTxStatusDate(new Date());
				dbsrvc.save(jrnl);
			} else {
				jrnl.setTxRefNo(form.getTxrefno());
			}
			param.put("txrefno", jrnl.getTxRefNo());
			jrnl = (Tbdeptxjrnl) dbsrvc
					.executeUniqueHQLQuery("FROM Tbdeptxjrnl WHERE txrefno=:txrefno and accountno=:acctno", param);
			dep = (Tbdeposit) dbsrvc.executeUniqueHQLQuery("FROM Tbdeposit WHERE accountNo=:acctno", param);
			if (dep == null) {
				jrnl.setTxStatus("3");
				jrnl.setTxStatusDate(new Date());
				jrnl.setRemarks("Account not found");
				dbsrvc.saveOrUpdate(jrnl);
				result.setResult("error");
				result.setMessage(jrnl.getRemarks());
				return result;
			} else {
				jrnl.setAcctsts(String.valueOf(dep.getAccountStatus()));
				jrnl.setTdcno(dep.getTdcno());
			}
			if (dep.getFreezeind() != null && dep.getFreezeind() == 1) {
				jrnl.setTxStatus("3");
				jrnl.setTxStatusDate(new Date());
				jrnl.setRemarks("Freeze Account");
				dbsrvc.saveOrUpdate(jrnl);
				result.setResult("error");
				result.setMessage(jrnl.getRemarks());
				return result;
			}
			if (dep.getAccountStatus() == 5) {
				jrnl.setTxStatus("3");
				jrnl.setTxStatusDate(new Date());
				jrnl.setRemarks("Closed Account");
				dbsrvc.saveOrUpdate(jrnl);
				result.setResult("error");
				result.setMessage(jrnl.getRemarks());
				return result;
			}
			if (form.getOverridestatus().equals("3") || form.getOverridestatus().equals("6")) {
				jrnl.setTxStatus("4");
				jrnl.setTxStatusDate(new Date());
				jrnl.setOverridestatus(form.getOverridestatus());
				dbsrvc.saveOrUpdate(jrnl);
			} else if (form.getOverridestatus().equals("7")) {
				jrnl.setTxStatus("3");
				jrnl.setTxStatusDate(new Date());
				jrnl.setOverridestatus(form.getOverridestatus());
				dbsrvc.saveOrUpdate(jrnl);
			} else if (form.getOverridestatus().equals("8")) {
				jrnl.setTxStatus("4");
				jrnl.setTxStatusDate(new Date());
				dbsrvc.saveOrUpdate(jrnl);
			} else {
				if (tx.getWcash()) {
					UserInfoService uinfosrvc = new UserInfoServiceImpl();
					if ((form.getAccountnoto() == null || form.getAccountnoto().equals("")) && uinfosrvc
							.getUnitBalance(form.getUserid(), "PHP", "1").compareTo(form.getTxamount()) == -1) {
						jrnl.setTxStatus("3");
						jrnl.setTxStatusDate(new Date());
						jrnl.setRemarks("You do not have enough cash to complete this transaction.");
						dbsrvc.saveOrUpdate(jrnl);
						result.setResult("error");
						result.setMessage(jrnl.getRemarks());
						return result;
					}
				}
				if (tx.getWcheck() && form.getChecks() == null) {
					form.setChecks(new ArrayList<Tbchecksforclearing>());
					Tbchecksforclearing check = new Tbchecksforclearing();
					check.setCheckamount(form.getTxamount());
					form.getChecks().add(check);
				}
				if (form.getChecks() != null && form.getChecks().size() > 0) {
					BigDecimal checkAmt = BigDecimal.ZERO;
//					String checkQry = "SELECT TOP 1 * FROM Tbchecksforclearing WHERE checkamount =:checkamt and accountnumber=:acctno AND status='1'";
					for (int i = 0; i < form.getChecks().size(); i++) {
//						param.put("checkamt", form.getChecks().get(i).getCheckamount());
//						if (jrnl.getErrorcorrecttxrefno() != null && !jrnl.getErrorcorrecttxrefno().equals("")) {
//							param.put("txrefno", jrnl.getErrorcorrecttxrefno());
//							checkQry = "SELECT TOP 1 * FROM Tbchecksforclearing WHERE checkamount =:checkamt and accountnumber=:acctno and txrefno=:txrefno";
//						}
//						form.getChecks().set(i, (Tbchecksforclearing) dbsrvc.execStoredProc(checkQry, param,
//								Tbchecksforclearing.class, 0, null));
//						if (form.getChecks().get(i) == null) {
//							jrnl.setTxStatus("3");
//							jrnl.setTxStatusDate(new Date());
//							jrnl.setRemarks("No check found with inputted amount.");
//							dbsrvc.saveOrUpdate(jrnl);
//							result.setResult("error");
//							result.setMessage(jrnl.getRemarks());
//							return result;
//						}
						jrnl.setChecknumber(form.getChecks().get(i).getChecknumber());
						form.getChecks().get(i).setStatus("3");
						checkAmt = checkAmt.add(form.getChecks().get(i).getCheckamount());
					}
					if (checkAmt.compareTo(form.getTxamount()) != 0 && !jrnl.getErrorcorrectind()) {
						jrnl.setTxStatus("3");
						jrnl.setTxStatusDate(new Date());
						jrnl.setRemarks("Check amount is not equal to amount inputted.");
						dbsrvc.saveOrUpdate(jrnl);
						result.setResult("error");
						result.setMessage(jrnl.getRemarks());
						return result;
					}
					if (dep.getFloatAmount().compareTo(checkAmt) == -1) {
						jrnl.setTxStatus("3");
						jrnl.setTxStatusDate(new Date());
						jrnl.setRemarks("Total check amount cannot be greater than the float amount.");
						dbsrvc.saveOrUpdate(jrnl);
						result.setResult("error");
						result.setMessage(jrnl.getRemarks());
						return result;
					}
					dep.setFloatAmount(dep.getFloatAmount().subtract(checkAmt));
				}
				if (tx.getIsinteresttx()) {
					if (form.getTxamount().compareTo(dep.getInterestbalance()) == 1) {
						jrnl.setTxStatus("3");
						jrnl.setTxStatusDate(new Date());
						jrnl.setRemarks("Insufficient interest balance.");
						dbsrvc.saveOrUpdate(jrnl);
						result.setResult("error");
						result.setMessage(jrnl.getRemarks());
						return result;
					} else {
						dep.setInterestpaid(dep.getInterestpaid().add(form.getTxamount()));
						dep.setInterestbalance(dep.getInterestbalance().subtract(form.getTxamount()));
						dep.setAccountBalance(dep.getAccountBalance().add(form.getTxamount()));
					}
				} else {
					if (form.getTxamount().compareTo(dep.getAccountBalance()) == 1) {
						jrnl.setTxStatus("3");
						jrnl.setTxStatusDate(new Date());
						jrnl.setRemarks("Insufficient balance.");
						dbsrvc.saveOrUpdate(jrnl);
						result.setResult("error");
						result.setMessage(jrnl.getRemarks());
						return result;
					} else if (form.getChecks() == null && form.getTxamount()
							.compareTo(dep.getAccountBalance()
									.subtract((tx.getWcheck() ? BigDecimal.ZERO : dep.getFloatAmount())
											.add(dep.getPlaceholdAmt().add(dep.getEarmarkbal()
													.add(dep.getPlacementAmt().add(dep.getGarnishedbal())))))) == 1) {
						jrnl.setTxStatus("3");
						jrnl.setTxStatusDate(new Date());
						jrnl.setRemarks("Insufficient Withdrawable Balance due to uncollectible deposits.");
						dbsrvc.saveOrUpdate(jrnl);
						result.setResult("error");
						result.setMessage(jrnl.getRemarks());
						return result;
					}
					dep.setAccountBalance(dep.getAccountBalance().subtract(form.getTxamount()));
				}
				jrnl.setOutBal(dep.getAccountBalance());
				jrnl.setDebit(form.getTxamount());
				if (form.getOverridestatus().equals("0") || form.getOverridestatus().equals("2")
						|| form.getOverridestatus().equals("5")) {
					if (tx.getWcash()) {
						dep.setTotalNoDebits(dep.getTotalNoDebits() + 1);
						dep.setMtddebits(dep.getMtddebits().add(form.getTxamount()));
						dep.setYtddebits(dep.getYtddebits().add(form.getTxamount()));
						dep.setBtddebits(dep.getBtddebits().add(form.getTxamount()));
						dep.setLasttxcode(form.getTxcode());
//						dep.setLasttxdate(jrnl.getTxvaldt());
						param.put("txamt", form.getTxamount());
						param.put("userid", form.getUserid());
						param.put("currency", form.getCurrency() == null ? "PHP" : form.getCurrency());
						param.put("txcode", form.getTxcode());
//						dbsrvc.executeUpdate("UPDATE TBNETAMT SET userbalance = userbalance -:txamt "
//								+ "WHERE userid =:userid and currency =:currency and transfertype=1", param);
						Tbnetamt netamt = (Tbnetamt) dbsrvc.executeUniqueHQLQuery(
								"FROM Tbnetamt WHERE userid =:userid and currency =:currency and transfertype=1",
								param);
						netamt.setUserbalance(netamt.getUserbalance().subtract(form.getTxamount()));
						dbsrvc.saveOrUpdate(netamt);
					}
					// Correction Advise
					if (!jrnl.getReason().equals("199")) {
						dep.setLasttxdate(jrnl.getTxvaldt());
					}
					jrnl.setTxStatus("2");
					jrnl.setTxStatusDate(new Date());
					jrnl.setOverridestatus(form.getOverridestatus());
//					dep.setStatusDate(jrnl.getTxvaldt());
					// REMOVE DORMANT
					if (dep.getAccountStatus() == 3) {
						dep.setAccountStatus(1);
						dep.setOldstatus(3);
						dep.setOldStatusDate(dep.getStatusDate());
						dep.setStatusDate(jrnl.getTxvaldt());
						jrnl.setOldacctsts("3");
						jrnl.setAcctsts("1");
					}
					if (dbsrvc.saveOrUpdate(dep) && dbsrvc.saveOrUpdate(jrnl)) {
						param.put("txrefno", jrnl.getTxRefNo());
						dbsrvc.executeUpdate(
								"UPDATE Tbdeposit SET jrnlno=(select id from tbdeptxjrnl where txrefno=:txrefno and accountno=:acctno) where accountno=:acctno",
								param);
//					finjrnl = createFinJrnl(form);
//					finjrnl.setTxrefmain(jrnl.getTxRefNo());
//					dbsrvc.save(finjrnl);
						if (form.getChecks() != null && form.getChecks().size() > 0) {
							for (Tbchecksforclearing check : form.getChecks()) {
								dbsrvc.update(check);
							}
						}
					}
					if (jrnl.getErrorcorrectind()) {
						param.put("ectxrefno", jrnl.getErrorcorrecttxrefno());
						dbsrvc.executeUpdate(
								"UPDATE Tbdeptxjrnl SET errorcorrectind = 1, errorcorrecttxrefno =:txrefno where txrefno =:ectxrefno and accountno=:acctno",
								param);
					}
				}
			}
			result.setResult("success");
			result.setMessage(jrnl.getTxRefNo());
			return result;
		} catch (

		Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public DepositTransactionResultForm casaTransaction(DepositTransactionForm form, Tbtransactioncode tx,
			List<Tboverriderequest> requests) {
		DepositTransactionResultForm result = new DepositTransactionResultForm();
		result.setMessage("Unable to process at this moment.");
		result.setResult("error");
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			form.setUserid(UserUtil.getUserByUsername(secservice.getUserName()).getUserid());
			if (form.getOverridestatus().equals("2") || form.getOverridestatus().equals("3")
					|| form.getOverridestatus().equals("5") || form.getOverridestatus().equals("6")
					|| form.getOverridestatus().equals("7")) {
				OverrideService osrvc = new OverrideServiceImpl();
				result.setMessage(osrvc.updateOverride(form.getTxrefno(), form.getOverridestatus(),
						form.getOverrideusername(), form.getOverridepassword()));
				if (!result.getMessage().equals("success")) {
					return result;
				}
			}
			if (tx.getTxoper() == 1) {
				result = debitTransaction(form, tx);
			} else {
				result = creditTransaction(form, tx);
			}
			if (result.getResult().equals("success")) {
				if ((form.getOverridestatus().equals("1") || form.getOverridestatus().equals("4"))) {// Request for
					form.setTxrefno(result.getMessage());
					for (Tboverriderequest req : requests) {
						req.setTxrefno(result.getMessage());
					}
					OverrideService osrvc = new OverrideServiceImpl();
					osrvc.requestOverride(form, requests);
					form.setTxrefno("");
				}
			}
			if (form.getAccountnoto() != null && !form.getAccountnoto().isEmpty()
					&& result.getResult().equals("success")) {
				DepositTransactionResultForm transferresult = new DepositTransactionResultForm();
				transferresult.setMessage("Unable to process at this moment.");
				transferresult.setResult("error");
				param.put("acctno", form.getAccountno());
				param.put("txrefno", result.getMessage());
				Tbdeptxjrnl jrnl = (Tbdeptxjrnl) dbsrvc
						.executeUniqueHQLQuery("FROM Tbdeptxjrnl WHERE accountno=:acctno and txrefno =:txrefno", param);
				if (form.isErrorcorrect()) {
					param.put("ectxrefno", form.getErrorcorrecttxrefno());
					form.setErrorcorrecttxrefno((String) dbsrvc.executeUniqueHQLQuery(
							"SELECT transfertxrefno FROM Tbdeptxjrnl WHERE txrefno =:ectxrefno and accountno=:acctno",
							param));
				}
				form.setAccountno(form.getAccountnoto());
				form.setAccountnoto("");
				form.setTransfertxrefno(result.getMessage());
				form.setTxrefno(result.getMessage());
				if (tx.getTxoper() == 1) {
					transferresult = creditTransaction(form, tx);
				} else {
					transferresult = debitTransaction(form, tx);
				}
				if (transferresult.getResult().equals("success")) {
					jrnl.setTransfertxrefno(transferresult.getMessage());
					dbsrvc.saveOrUpdate(jrnl);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public DepositTransactionResultForm errorCorrect(String acctno, String txrefno, String overridetxrefno,
			String overridestatus, String username, String password) {
		DepositTransactionResultForm result = new DepositTransactionResultForm();
		DepositTransactionForm form = new DepositTransactionForm();
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		Tbdeptxjrnl jrnl = new Tbdeptxjrnl();
		Tbdeptxjrnl ecjrnl = new Tbdeptxjrnl();
		Tbbranch branch = new Tbbranch();
		param.put("acctno", acctno);
		param.put("txrefno", txrefno);
		param.put("branchcode", UserUtil.getUserByUsername(secservice.getUserName()).getBranchcode());
		try {
			form.setUserid(UserUtil.getUserByUsername(secservice.getUserName()).getUserid());
			branch = (Tbbranch) dbsrvc.executeUniqueHQLQuery("FROM Tbbranch WHERE branchcode=:branchcode", param);
			ecjrnl = (Tbdeptxjrnl) dbsrvc
					.executeUniqueHQLQuery("FROM Tbdeptxjrnl WHERE accountno=:acctno and txrefno =:txrefno", param);
			if (overridetxrefno == null || overridetxrefno.equals("")) {
				jrnl = (Tbdeptxjrnl) dbsrvc
						.executeUniqueHQLQuery("FROM Tbdeptxjrnl WHERE accountno=:acctno and txrefno =:txrefno", param);
				form.setAccountno(jrnl.getAccountno());
				form.setBatchcode(jrnl.getTxBatch());
				form.setChecks(null);
				form.setCurrency("PHP");
				form.setInteresttransaction(false);
				form.setReason("EC");
				form.setRemarks("Error Correct");
				form.setTxamount(jrnl.getTxAmount());
				form.setTxbranch(jrnl.getTxbranch());
				form.setTxmode("");
				form.setTxstatus("2");
				form.setUserid(UserUtil.getUserByUsername(secservice.getUserName()).getUserid());
				form.setUsername(secservice.getUserName());
				form.setValuedate(branch.getCurrentbusinessdate());
				form.setErrorcorrecttxrefno(jrnl.getTxRefNo());
				form.setErrorcorrect(true);
//				form.setTransfertxrefno(jrnl.getTransfertxrefno());
				form.setOverridestatus("1");
				form.setTxcode("134404");
				if (jrnl.getTransfertxrefno() != null && !jrnl.getTransfertxrefno().equals("")) {
					param.put("transfertxrefno", jrnl.getTransfertxrefno());
					form.setAccountnoto((String) dbsrvc.executeUniqueHQLQuery(
							"SELECT accountno FROM Tbdeptxjrnl WHERE txrefno =:transfertxrefno", param));
				}
//				form.setTransfertxrefno(jrnl.getTransfertxrefno());
				param.put("txcode", ecjrnl.getTxcode());
				Tbtransactioncode tx = (Tbtransactioncode) dbsrvc
						.executeUniqueHQLQuery("FROM Tbtransactioncode WHERE txcode=:txcode", param);
				tx.setTxoper(jrnl.getTxoper() == 1 ? 2 : 1);
				if (tx.getWcheck() && tx.getTxoper() == 1) {
					param.put("txrefno", ecjrnl.getTxRefNo());
					form.setChecks((List<Tbchecksforclearing>) dbsrvc.executeListHQLQuery(
							"FROM Tbchecksforclearing WHERE txrefno=:txrefno and status = '1'", param));
				}
				List<Tboverriderequest> requests = new ArrayList<Tboverriderequest>();
				Tboverriderequest request = new Tboverriderequest();
				request.setAccountno(jrnl.getAccountno());
				request.setOverridemessage("Error Correct");
				request.setOverriderule("EC");
				request.setRequestdate(branch.getCurrentbusinessdate());
				request.setRequestedby(form.getUserid());
				request.setStatus("1");
				request.setStatusdate(new Date());
				request.setTxcode(jrnl.getTxcode());
				requests.add(request);
				result = casaTransaction(form, tx, requests);
			} else {
				param.put("txrefno", overridetxrefno);
				jrnl = (Tbdeptxjrnl) dbsrvc
						.executeUniqueHQLQuery("FROM Tbdeptxjrnl WHERE accountno=:acctno and txrefno =:txrefno", param);
				form.setAccountno(jrnl.getAccountno());
				form.setBatchcode(jrnl.getTxBatch());
				form.setChecks(null);
				form.setCurrency("PHP");
				form.setInteresttransaction(false);
				form.setReason(jrnl.getReason());
				form.setRemarks(jrnl.getRemarks());
				form.setTxamount(jrnl.getTxAmount());
				form.setTxbranch(jrnl.getTxbranch());
				form.setTxmode("");
				form.setTxstatus("2");
				form.setUserid(UserUtil.getUserByUsername(secservice.getUserName()).getUserid());
				form.setUsername(secservice.getUserName());
				form.setValuedate(jrnl.getTxvaldt());
				form.setErrorcorrecttxrefno(jrnl.getTxRefNo());
				form.setErrorcorrect(jrnl.getErrorcorrectind());
				form.setOverridestatus(overridestatus);
				form.setTxcode(jrnl.getTxcode());
				if (jrnl.getTransfertxrefno() != null && !jrnl.getTransfertxrefno().equals("")) {
					param.put("transfertxrefno", jrnl.getTransfertxrefno());
					form.setAccountnoto((String) dbsrvc.executeUniqueHQLQuery(
							"SELECT accountno FROM Tbdeptxjrnl WHERE txrefno =:transfertxrefno", param));
				}
				form.setTxrefno(overridetxrefno);
				form.setOverrideusername(username);
				form.setOverridepassword(password);
				param.put("txcode", ecjrnl.getTxcode());
				Tbtransactioncode tx = (Tbtransactioncode) dbsrvc
						.executeUniqueHQLQuery("FROM Tbtransactioncode WHERE txcode=:txcode", param);
				tx.setTxoper(jrnl.getTxoper());
				if (tx.getWcheck() && tx.getTxoper() == 1) {
					param.put("txrefno", ecjrnl.getTxRefNo());
					form.setChecks((List<Tbchecksforclearing>) dbsrvc.executeListHQLQuery(
							"FROM Tbchecksforclearing WHERE txrefno=:txrefno and status = '1'", param));
				}
				if (tx.getWcheck() && tx.getTxoper() == 2) {
					param.put("checknumber", ecjrnl.getChecknumber());
					form.setChecks((List<Tbchecksforclearing>) dbsrvc.executeListHQLQuery(
							"FROM Tbchecksforclearing WHERE accountnumber=:acctno and checknumber=:checknumber and status = '3'",
							param));
				}
				result = casaTransaction(form, tx, null);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public DepositTransactionResultForm clearChecks(List<Tbchecksforclearing> checks, String accountno) {
		// TODO Auto-generated method stub
		DepositTransactionResultForm result = new DepositTransactionResultForm();
		result.setMessage("Failed");
		result.setResult("error");
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (checks != null && !checks.isEmpty()) {
				Tbuser user = UserUtil.getUserByUsername(secservice.getUserName());
				param.put("acctno", accountno);
				param.put("branchcode", user.getBranchcode());
				Tbdeptxjrnl jrnl = new Tbdeptxjrnl();
				Tbdeposit dep = (Tbdeposit) dbsrvc.executeUniqueHQLQuery("FROM Tbdeposit WHERE accountno=:acctno",
						param);
				Tbbranch branch = (Tbbranch) dbsrvc.executeUniqueHQLQuery("FROM Tbbranch WHERE branchcode=:branchcode",
						param);
				jrnl.setAccountno(dep.getAccountNo());
				jrnl.setBrstn(branch.getBranchcode());
				jrnl.setTxcode("112243");
				jrnl.setTxoper(2);
				jrnl.setTxvaldt(branch.getCurrentbusinessdate());
				jrnl.setTxStatus("2");
				jrnl.setTxStatusDate(new Date());
				jrnl.setCreatedBy(user.getUserid());
				jrnl.setCreationDate(new Date());
				jrnl.setReason("9");
				jrnl.setTxbranch(branch.getBranchcode());
				jrnl.setOverridestatus("0");
				jrnl.setErrorcorrectind(false);
				jrnl.setRemarks("Force Clear");
				jrnl.setOutBal(dep.getAccountBalance());
				jrnl.setTxdate(new Date());
				jrnl.setTxmode("2");
				for (Tbchecksforclearing check : checks) {
					jrnl.setTxRefNo((String) dbsrvc.executeUniqueSQLQuery(
							"DECLARE @txrefno VARCHAR(20) " + "EXEC SEQGENERATE @txrefno OUTPUT " + "SELECT @txrefno",
							param));
					jrnl.setTxAmount(check.getCheckamount());
					jrnl.setCredit(check.getCheckamount());
					check.setStatus("2");
					check.setTxstatusdate(new Date());
					check.setUpdatedby(UserUtil.getUserByUsername(secservice.getUserName()).getUserid());
					check.setTxstatusdate(new Date());
					check.setClearingdate(branch.getCurrentbusinessdate());
					dep.setFloatAmount(dep.getFloatAmount().subtract(check.getCheckamount()));
					dbsrvc.saveOrUpdate(check);
					dbsrvc.saveOrUpdate(dep);
					dbsrvc.save(jrnl);
				}
//				if (dbsrvc.saveOrUpdate(checks) && dbsrvc.saveOrUpdate(dep)) {
				result.setResult("success");
				result.setMessage("Check/s cleared!");
//				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Tbtransactioncode> getTransactionCodes(String txcode) {
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("txcode", "%" + txcode == null ? "" : txcode + "%");
		try {
			return (List<Tbtransactioncode>) dbsrvc
					.executeListHQLQuery("FROM Tbtransactioncode WHERE txcode like :txcode", param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public DepositTransactionForm getTransactionDetails(String txcode, String txrefno) {
		DepositTransactionForm form = new DepositTransactionForm();
//		DBService dbsrvc = new DBServiceImpl();
//		Map<String, Object> param = HQLUtil.getMap();
		return form;
	}
}
