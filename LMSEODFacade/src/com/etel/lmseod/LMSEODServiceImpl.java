/**
 * 
 */
package com.etel.lmseod;

import java.math.BigDecimal;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbaccountinfo;
import com.coopdb.data.Tbapprovedcf;
import com.coopdb.data.Tbbranch;
import com.coopdb.data.Tbchecksforclearing;
import com.coopdb.data.Tbdeposit;
import com.coopdb.data.Tblntxjrnl;
import com.coopdb.data.Tbloandeduction;
import com.coopdb.data.Tbloanfin;
import com.coopdb.data.Tbloanoffset;
import com.coopdb.data.Tbloanproduct;
import com.coopdb.data.Tbloanreleaseinst;
import com.coopdb.data.Tbloans;
import com.coopdb.data.Tblogs;
import com.coopdb.data.Tblstapp;
import com.coopdb.data.Tbpaymentsched;
import com.coopdb.data.TbpaymentschedId;
import com.coopdb.data.Tbpaysched;
import com.coopdb.data.Tbprocessingdate;
import com.coopdb.data.Tbtransactioncode;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.deposittransaction.DepositTransactionService;
import com.etel.deposittransaction.DepositTransactionServiceImpl;
import com.etel.deposittransaction.form.DepositTransactionForm;
import com.etel.glentries.GLEntriesService;
import com.etel.glentries.GLEntriesServiceImpl;
import com.etel.lms.LMSDashboardService;
import com.etel.lms.LMSDashboardServiceImpl;
import com.etel.lms.TransactionService;
import com.etel.lms.TransactionServiceImpl;
import com.etel.lms.forms.LoanAccountForm;
import com.etel.lms.forms.LoanTransactionForm;
import com.etel.util.INITUtil;
import com.etel.utils.DateTimeUtil;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;

/**
 * @author ETEL-LAPTOP19
 *
 */
public class LMSEODServiceImpl implements LMSEODService {

	private static DBService dbSrvc = new DBServiceImpl();
	private static Map<String, Object> params = HQLUtil.getMap();
	private static Tblogs log = new Tblogs();
	private static Tbprocessingdate prcdate = (Tbprocessingdate) dbSrvc
			.executeUniqueHQLQuery("FROM Tbprocessingdate WHERE id='1'", null);
	private static Tbloanfin loanfin = new Tbloanfin();
	private static Tbloans loans = new Tbloans();
	private static Tblntxjrnl jrnl = new Tblntxjrnl();
//	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@SuppressWarnings("unchecked")
	public String loanBooking(List<LoanAccountForm> newloans) {
		// TODO Auto-generated method stub
//		log.setAfter(DateTimeUtil.convertDateToString(prcdate.getEnddate(), DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
//		log.setAmount(BigDecimal.ZERO);
//		log.setBefore(DateTimeUtil.convertDateToString(prcdate.getStartdate(), DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
//		System.out.println(">> EOD PROCESS : START LOAN BOOKING " + new Date() + " >>");
//		log.setFiletype("EOD Loan Booking");
//		log.setTimestamp(new Date());
//		log.setChangetype("START");
//		log.setFilename("");
//		log.setRowId("");
//		log.setReason("Start");
//		dbSrvc.save(log);
		String result = "Failed";
		Tbloanproduct prdmtrx = new Tbloanproduct();
		Tbloans account = null;
		Tbaccountinfo application = new Tbaccountinfo();
		String accountno = "";
		String txrefno = "";

		DBService dbSrvc = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		// int count = 0;

		try {
			if (newloans.size() > 0) {
				for (LoanAccountForm row : newloans) {
					try {
						params.put("pnno", row.getPnno());
						account = (Tbloans) dbSrvc.executeUniqueHQLQuery("FROM Tbloans WHERE pnno=:pnno", params);
						if (account == null) {
							account = new Tbloans();
						}
//						Tblmslogs bookinglogs = new Tblmslogs();
//						bookinglogs.setAfter(DateTimeUtil.convertDateToString(prcdate.getNextbusinessdate(), DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
//						bookinglogs.setAmount(row.getLoanamount());
//						bookinglogs.setBefore(DateTimeUtil.convertDateToString(prcdate.getCurrentbusinessdate(), DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
//						bookinglogs.setFiletype("Loan Booking - " + row.getPnno());
//						bookinglogs.setTimestamp(new Date());
//						bookinglogs.setChangetype("START");
//						bookinglogs.setFilename("");
//						bookinglogs.setRowId(row.getPnno());
//						bookinglogs.setReason("Start");
//						dbSrvc.save(bookinglogs);
						// bookinglogs.setre
						// count++;
						txrefno = row.getApplno();
//						System.out.println(txrefno);
						params.put("txrefno", row.getApplno());
						Tblstapp lstapp = (Tblstapp) dbSrvc.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:txrefno",
								params);
						application = (Tbaccountinfo) dbSrvc
								.executeUniqueHQLQuery("FROM Tbaccountinfo WHERE applno=:txrefno", params);
						accountno = "";
						params.put("txrefno", application.getApplno());
						params.put("subprd", application.getProduct());
						// get product info
						prdmtrx = (Tbloanproduct) dbSrvc
								.executeUniqueHQLQuery("FROM Tbloanproduct WHERE productcode =:subprd", params);
						accountno = application.getPnno();
						params.put("acctno", accountno);
						dbSrvc.executeUpdate("UPDATE Tbpaysched SET accountno =:acctno WHERE applno=:txrefno", params);
						// copy tbpaysched to tbpaymentsched
						List<Tbpaysched> paysched = new ArrayList<Tbpaysched>();

						paysched = (List<Tbpaysched>) dbSrvc
								.executeListHQLQuery("FROM Tbpaysched WHERE applno=:txrefno", params);
//						System.out.println(paysched.size());

						for (Tbpaysched payment : paysched) {
							Tbpaymentsched newpaysched = new Tbpaymentsched();
							TbpaymentschedId newpayschedid = new TbpaymentschedId();
							newpayschedid.setAccountno(accountno);
							newpayschedid.setAmortid(payment.getIlno().intValue());
							newpaysched.setId(newpayschedid);
							newpaysched.setApplno(payment.getId().getApplno());
							newpaysched.setDaysdiff(payment.getDaysdiff());
							newpaysched.setIlamt(payment.getIlamt());
							newpaysched.setIlduedt(payment.getIlduedt());
							newpaysched.setIlint(payment.getIlint());
							newpaysched.setIlintrate(payment.getIlintrate());
							newpaysched.setIlno(payment.getIlno());
							newpaysched.setIlprin(payment.getIlprin());
							newpaysched.setIlrppd(payment.getIlrppd());
							newpaysched.setIltax(payment.getIltax());
							newpaysched.setIltaxrate(payment.getIltaxrate());
							newpaysched.setIsPaid(false);
							newpaysched.setLoanbal(payment.getLoanbal());
							newpaysched.setLoanno(row.getPnno());
							newpaysched.setOthers(BigDecimal.ZERO);
							newpaysched.setPrinbal(payment.getPrinbal());
							newpaysched.setTxmkr(payment.getTxmkr());
							newpaysched.setTxoff(payment.getTxoff());
//							newpaysched.setUidbal(BigDecimal.ZERO);
							newpaysched.setUidbal(payment.getUidbal());
							dbSrvc.saveOrUpdate(newpaysched);

						}

						Tbpaymentsched nxtpaysched = (Tbpaymentsched) dbSrvc.executeUniqueHQLQuery(
								"FROM Tbpaymentsched WHERE accountno=:acctno AND ilno=1", params);
						account.setAccountno(accountno);
						account.setAccrtype(application.getAccrtype());
						account.setAcctsts(1);
						account.setAcctstsDate(new Date());
						account.setAir(application.getAir());
						account.setAmortizationAmt(application.getAmortfee());
						account.setApplno(application.getApplno());
						account.setAr1(application.getTotalfeesandcharges());
						account.setAr2(BigDecimal.ZERO);
						account.setAr1esc("");// fix
						account.setAr2esc("");
						account.setBtdprinpd(BigDecimal.ZERO);
						account.setBtdintpd(BigDecimal.ZERO);
						account.setChk4clr(BigDecimal.ZERO);
						account.setCollAgency("");
						account.setCollCollector("");
						account.setCollZone("");
						account.setDdlq(0);
						account.setDdlqBucket(0);
						account.setDdlqBucketPrev(0);
						account.setDlylpc(BigDecimal.ZERO);
						account.setDtbook(application.getDtbook());
						account.setFaceamt(application.getPnamt().add(application.getInterestdue()));
						account.setFactorRate(application.getEir());
						// account.setEmployeeno(application.getem);
						account.setFduedt(application.getFduedt());
						account.setEffyield(application.getEyrate());
						account.setEir(application.getEir());
						account.setExcessbal(BigDecimal.ZERO);
						account.setGraceprd(0);
						account.setIncomerecognition(application.getIncomerecognition()); // Ced 07292022
						account.setIdealloanbal(application.getPnamt().add(application.getInterestdue()));
						account.setIdealprinbal(application.getPnamt());
						account.setIenc(BigDecimal.ZERO);
						account.setIltogo(application.getPpynum());
						account.setInterestAmt(application.getInterestdue());
						account.setInttyp(application.getInttyp()); // Ced 07292022
						account.setIntpaytype(application.getIntpytype());
						account.setLegbranch(application.getOrigbr());
						account.setLegveh(lstapp.getCompanycode());
						account.setLoanbal(application.getPnamt().add(application.getInterestdue()));
						account.setLoanoff(application.getLoanoff());
						account.setLoanpur(application.getLoanpur());
						account.setLoantype(application.getProduct());
						account.setLpcbal(BigDecimal.ZERO);
						account.setLpcrate(BigDecimal.ZERO);
						account.setLpdduedt(null);
						account.setLpdilno(0);
						account.setLstaccrdt(application.getDtbook());
						account.setLstpyamt(BigDecimal.ZERO);
						// account.setLstpydt(application.getDtbook());
						account.setLsttxcod("10");
						account.setLsttxdt(application.getDtbook());
						account.setMatdt(application.getMatdt());
						account.setNxtduedt(
								application.getFduedt() == null ? application.getMatdt() : application.getFduedt());
						account.setNxtilamt(nxtpaysched == null ? BigDecimal.ZERO : nxtpaysched.getIlamt());
						account.setNxtilno(1);
						account.setNxtintamt(nxtpaysched == null ? BigDecimal.ZERO : nxtpaysched.getIlint());
						account.setNxtprinamt(nxtpaysched == null ? BigDecimal.ZERO : nxtpaysched.getIlprin());
						account.setNxtdueilno(1);
						account.setNxtdueilamt(application.getAmortfee());
						account.setNxtdueilduedt(application.getFduedt());
						account.setNxtdueilprin(nxtpaysched == null ? BigDecimal.ZERO : nxtpaysched.getIlprin());
						account.setNxtdueilint(nxtpaysched == null ? BigDecimal.ZERO : nxtpaysched.getIlint());
						account.setOuid(application.getOuid());
						account.setPartialint(BigDecimal.ZERO);
						account.setPartialprin(BigDecimal.ZERO);
						String paycycle = (application.getPrinpaycycle().equals("M") ? "12"
								: application.getPrinpaycycle());
						account.setPaymentCycle(Integer.valueOf(paycycle));
						account.setPdcctr(application.getPdcctr());
						account.setPnamt(application.getPnamt());
						account.setPnilno(application.getPpynum());
						account.setPnilnocyc(Integer.valueOf(paycycle));
						account.setPnintmethod(application.getIntcompmethod());
						account.setPnintrate(application.getNominal());
						account.setPnno(application.getPnno());
						account.setPnterm(BigDecimal.valueOf(application.getTerm()));
						account.setPntermcyc(application.getTermcyc());
						account.setPrinbal(application.getPnamt());
						account.setPrincipalNo(application.getClientid());
						account.setProdcode(application.getProduct());
						account.setProductGroup(application.getProductGroup());
						account.setPymtplan(application.getPymntPlan());
						account.setSlaidno(application.getClientid());
						account.setBaseyear(application.getBaseyear());
						account.setExcesspaymenthandling(prdmtrx.getExcesspaymenthandling());
						account.setDdlqtype(prdmtrx.getDdlqtype());
						account.setIncomerecognition(prdmtrx.getIncomerecognition());
						account.setAccrtype(prdmtrx.getAccrtype());
						account.setLpcrate(prdmtrx.getLpcrate());
						account.setGraceprd(prdmtrx.getLpcgraceperiod());
						/// stop here

						// MAR 06-07-2022
						// account.setAddtnlint(application.getIntchargeamt());
						// MAR END

						account.setRemedsts(0); // is this repricing status?
						account.setReviewdate(null);
						account.setReviewdays(0);
						account.setSeccode(application.getSeccode());
						account.setSubprd1(prdmtrx.getProducttype2());
						account.setSubprd2("");
						account.setTdueilamt(BigDecimal.ZERO);
						account.setTdueilduedt(null);
						account.setTdueilno(0);
						account.setTdueintamt(BigDecimal.ZERO);
						account.setTdueprinamt(BigDecimal.ZERO);
						account.setTpdilamt(BigDecimal.ZERO);
						account.setTpdilduedt(null);
						account.setTpdilno(0);
						account.setTpdintamt(BigDecimal.ZERO);
						account.setTpdprinamt(BigDecimal.ZERO);
						account.setUiadv(application.getUidAdv());
						account.setUiadvno(0);
						account.setUidbal(account.getOuid());
						account.setUnpaidint(BigDecimal.ZERO);
						account.setUnpaidprin(BigDecimal.ZERO);
						account.setXpd1(0);
						account.setXpd30(0);
						account.setXpd60(0);
						account.setXpd90(0);
						account.setYtdprinpd(BigDecimal.ZERO);
						account.setYtdintpd(BigDecimal.ZERO);
						account.setFullname(application.getName());
						account.setRefno(row.getApplno());
						account.setCurruidamt(nxtpaysched == null ? BigDecimal.ZERO : nxtpaysched.getIlint());
						Calendar dueDate = Calendar.getInstance();
						dueDate.setTime(account.getNxtduedt());
						Calendar bookDate = Calendar.getInstance();
						bookDate.setTime(account.getDtbook());
						account.setCurruidctr(BigDecimal
								.valueOf(DateTimeUtil.daysdiffV2(account.getDtbook(), account.getNxtduedt())));
						account.setCurrdailyint(BigDecimal.ZERO);
						account.setMemint(BigDecimal.ZERO);
						account.setMtdint(BigDecimal.ZERO);
						account.setIntpycomp(application.getInttyp());
						account.setCfrefno1(application.getCfrefno1());
						account.setCfrefno2(application.getCfrefno2());
						account.setIntcycdesc(application.getIntcycdesc());
						account.setTermcycdesc(application.getTermcycdesc());
						account.setWtaxflag(true);
						dbSrvc.saveOrUpdate(account);

						application.setLoanno(accountno);
						dbSrvc.update(application);

						GLEntriesService glSrvc = new GLEntriesServiceImpl();
						glSrvc.getGLEntriesByPnnoAndTxCode(account.getAccountno(), "10", application.getApplno(), false,
								true);

						// update credit line info if COmmercial

						if (application.getCfrefno1() != null) {

							params.put("cfrefno1", application.getCfrefno1());
							Tbapprovedcf cfdetails1 = (Tbapprovedcf) dbSrvc
									.executeUniqueHQLQuery("FROM Tbapprovedcf where cfrefnoconcat=:cfrefno1", params);
							if (cfdetails1 != null) {
								cfdetails1.setCfavailed(cfdetails1.getCfavailed().add(application.getPnamt()));
								cfdetails1.setCfearmarked(cfdetails1.getCfearmarked().subtract(application.getPnamt()));
								dbSrvc.update(cfdetails1);
							}
							if (application.getCfrefno2() != null) {
								params.put("cfrefno2", application.getCfrefno2());
								Tbapprovedcf cfdetails2 = (Tbapprovedcf) dbSrvc.executeUniqueHQLQuery(
										"FROM Tbapprovedcf where cfrefnoconcat=:cfrefno2", params);
								if (cfdetails2 != null) {
									cfdetails2.setCfavailed(cfdetails2.getCfavailed().add(application.getPnamt()));
									cfdetails2.setCfearmarked(
											cfdetails2.getCfearmarked().subtract(application.getPnamt()));
									dbSrvc.update(cfdetails2);
								}
							}

						}

						// add booking transaction journal

						Tblntxjrnl lntxjrnl = (Tblntxjrnl) dbSrvc
								.executeUniqueHQLQuery("FROM Tblntxjrnl where pnno=:pnno", params);
						if (lntxjrnl == null) {
							lntxjrnl = INITUtil.initialLoanTxJournal(account, DateTimeUtil
									.convertDateToString(account.getDtbook(), DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
						}
						lntxjrnl.setTxcode("10");
						lntxjrnl.setTxvaldt(application.getTxvaldt());
						lntxjrnl.setDuedtpd(null);
						lntxjrnl.setTxlpcprin(BigDecimal.ZERO);
						lntxjrnl.setTxint(account.getInterestAmt());
						lntxjrnl.setTxprin(account.getPrinbal());
						lntxjrnl.setTxloanbal(account.getLoanbal());
						lntxjrnl.setTxuidb(account.getUidbal());
						lntxjrnl.setTxprinbal(account.getPnamt());
						lntxjrnl.setTxar1(BigDecimal.ZERO);
						lntxjrnl.setTxaresc1(null);
						lntxjrnl.setTxamt(application.getPnamt()); // fix netproceeds
						lntxjrnl.setReason("LOS");
						lntxjrnl.setTxmode("9");
						lntxjrnl.setTxseqno(String.valueOf(1));
						lntxjrnl.setTxrefno(application.getApplno());
						lntxjrnl.setTxdate(new Date());
						lntxjrnl.setPnno(accountno);
						lntxjrnl.setTxoper("1");
						lntxjrnl.setTxar1(application.getTotalfeesandcharges());
						lntxjrnl.setTxmkr(
								UserUtil.getUserByUsername(UserUtil.securityService.getUserName()).getUserid());
						dbSrvc.saveOrUpdate(lntxjrnl);

						application.setTxstat("10");
						dbSrvc.saveOrUpdate(application);
						result = "Success";
						if (account.getIntpaytype().equals("1")) {
							// create Advance Interest CR Adjustment
							Tbloanfin txCR = new Tbloanfin();
							txCR.setAccountno(accountno);
							// txCR.setAddtnlint(BigDecimal.ZERO);
							// MAR 06-07-2022
							// txCR.setAddtnlint(application.getIntchargeamt());
							// MAR END
							txCR.setApprovaldate(account.getDtbook());
							txCR.setApprovedby(account.getLoanoff());
							txCR.setArtype(null);
							txCR.setCifno(account.getPrincipalNo());
							txCR.setClientname(account.getFullname());
							txCR.setCommrate(BigDecimal.ZERO);
							txCR.setCommamt(BigDecimal.ZERO);
							txCR.setCreatedby(account.getLoanoff());
							txCR.setCreationdate(account.getDtbook());
							txCR.setLegveh(account.getLegveh());
							txCR.setPnno(accountno);
							txCR.setSlaidno(account.getPrincipalNo());
							txCR.setTxamount(account.getUiadv());
							txCR.setTxamtbal(account.getUiadv());
							txCR.setTxcode("50"); // credit adjustment
							txCR.setTxdate(account.getDtbook());
							txCR.setTxmode("6");
							txCR.setTxoper(2);
							txCR.setTxor("");
							txCR.setTxordate(account.getDtbook());
							txCR.setTxposteddate(account.getDtbook());
							txCR.setTxprin(BigDecimal.ZERO);
							txCR.setTxint(account.getUiadv());
							txCR.setTxlpc(BigDecimal.ZERO);
							txCR.setTxmisc(BigDecimal.ZERO);
							txCR.setTxrefno(HQLUtil.generateTransactionRefNoDate());
							txCR.setTxsrce("LOS");
							txCR.setTxstatus("9");
							txCR.setTxstatusdate(account.getDtbook());
							txCR.setTxvaldt(account.getDtbook());
							txCR.setWtaxamt(BigDecimal.ZERO);
							txCR.setWtaxrate(BigDecimal.ZERO);
							dbSrvc.saveOrUpdate(txCR);

							// post adjustment
							params.put("txno", txCR.getTxrefno());
							params.put("branchcode",
									UserUtil.getUserByUsername(UserUtil.securityService.getUserName()).getBranchcode());
							Tbbranch branch = (Tbbranch) dbSrvc
									.executeUniqueHQLQuery("FROM Tbbranch WHERE branchcode=:branchcode", params);
							params.put("businessdate", branch.getCurrentbusinessdate());
							dbSrvc.execStoredProc(
									"DECLARE @result varchar(3000) EXEC sp_LMSSingleTxPosting :txno,:businessdate,@result OUTPUT SELECT @result",
									params, null, 0, null);

							Tblntxjrnl jrnl = (Tblntxjrnl) dbSrvc
									.executeUniqueHQLQuery("FROM Tblntxjrnl where txrefno=:txno", params);
							params.put("txcode", jrnl.getTxcode());
							if (jrnl != null) {
								// generate entries
								glSrvc.getGLEntriesByPnnoAndTxCode(txCR.getAccountno(), txCR.getTxcode(),
										txCR.getTxrefno(), true, true);
							}

							// send tx to helix
//							List<Tbglentries> jrnlentries = (List<Tbglentries>) dbSrvc.executeListHQLQuery(
//									"FROM Tbglentries WHERE txseqno=:txno and txcode=:txcode", params);
//							if (jrnlentries != null) {
//								System.out.println(jrnlentries.size());
//							APIService api = new APIServiceImpl();
//								AddJournalForm jrnlform = new AddJournalForm();
//								jrnlform.setBranchID(jrnl.getTxbr());
//								jrnlform.setCurrency("PHP");
//								jrnlform.setDetails("Loan Transaction for " + jrnl.getPnno());
//								jrnlform.setDocumentStatus("APPROVED");
//								jrnlform.setExchangeRate("1");
//								// jrnlform.setOrigCompanyID(jrnl.getTxlegveh());
//								jrnlform.setRefDate(sdf.format(jrnl.getTxvaldt()));
//								jrnlform.setRefNbr(jrnl.getTxrefno());
//								List<TransactionDetailsJournal> txjrnl = new ArrayList<TransactionDetailsJournal>();
//								for (Tbglentries entry : jrnlentries) {
//									TransactionDetailsJournal item = new TransactionDetailsJournal();
//									item.setAccountCode(entry.getGlsl());
//									item.setSubAccountCode(txCR.getCifno());
//									item.setLineDetails(jrnl.getPnno());
//									item.setCredit(entry.getCredit() == null ? ""
//											: String.valueOf(entry.getCredit().setScale(2, RoundingMode.HALF_UP)));
//									item.setDebit(entry.getDebit() == null ? ""
//											: String.valueOf(entry.getDebit().setScale(2, RoundingMode.HALF_UP)));
//									txjrnl.add(item);
//								}
//								jrnlform.setTransactionDetails(txjrnl);
//							ReturnForm form = api.addJournal(jrnl.getTxrefno(), jrnlform);
//							System.out.println(form.getMessage());
//								 apiflag = form.getMessage();
//							}
						}
						// create ROLLOVER
						List<Tbloanreleaseinst> rollover = (List<Tbloanreleaseinst>) dbSrvc.executeListHQLQuery(
								"FROM Tbloanreleaseinst WHERE pnno=:acctno and disposition='6'", params);

						if (rollover != null) {
							for (Tbloanreleaseinst ro : rollover) {
								Tbloanfin loanfin = new Tbloanfin();
								loanfin.setAccountno(ro.getRolloverpn());
								loanfin.setAddtnlint(BigDecimal.ZERO);
								loanfin.setApprovaldate(account.getDtbook());
								loanfin.setApprovedby(ro.getCreatedby());
								loanfin.setArtype(null);
								loanfin.setCifno(account.getPrincipalNo());
								loanfin.setClientname(account.getFullname());
								loanfin.setCommrate(BigDecimal.ZERO);
								loanfin.setCommamt(BigDecimal.ZERO);
								loanfin.setCreatedby(ro.getCreatedby());
								loanfin.setCreationdate(ro.getDatecreated());
								loanfin.setLegveh(account.getLegveh());
								loanfin.setPnno(ro.getRolloverpn());
								loanfin.setSlaidno(account.getPrincipalNo());
								loanfin.setTxamount(ro.getAmount());
								loanfin.setTxamtbal(ro.getAmount());
								loanfin.setTxcode("50"); // credit adjustment
								loanfin.setTxdate(account.getDtbook());
								loanfin.setTxmode("4");
								loanfin.setTxoper(2);
								loanfin.setTxor("");
								loanfin.setTxordate(account.getDtbook());
								loanfin.setTxposteddate(account.getDtbook());
								loanfin.setTxprin(ro.getAmount());
								loanfin.setTxint(BigDecimal.ZERO);
								loanfin.setTxlpc(BigDecimal.ZERO);
								loanfin.setTxmisc(BigDecimal.ZERO);
								loanfin.setTxrefno("RO-" + HQLUtil.generateTransactionRefNoDate());
								loanfin.setTxsrce(ro.getPnno());
								loanfin.setTxstatus("10");
								loanfin.setTxstatusdate(account.getDtbook());
								loanfin.setTxvaldt(account.getDtbook());
								loanfin.setWtaxamt(BigDecimal.ZERO);
								loanfin.setWtaxrate(BigDecimal.ZERO);
								dbSrvc.saveOrUpdate(loanfin);

								// post RO Transactions
								params.put("txrefno", loanfin.getTxrefno());
								params.put("branchcode", UserUtil
										.getUserByUsername(UserUtil.securityService.getUserName()).getBranchcode());
								Tbbranch branch = (Tbbranch) dbSrvc
										.executeUniqueHQLQuery("FROM Tbbranch WHERE branchcode=:branchcode", params);
								params.put("businessdate", branch.getCurrentbusinessdate());
								dbSrvc.execStoredProc(
										"DECLARE @result varchar(3000) EXEC sp_LMSSingleTxPosting :txrefno,:businessdate,@result OUTPUT SELECT @result",
										params, null, 0, null);

								Tblntxjrnl jrnl1 = (Tblntxjrnl) dbSrvc
										.executeUniqueHQLQuery("FROM Tblntxjrnl where txrefno=:txrefno", params);
								params.put("txcode", jrnl1.getTxcode());
								if (jrnl1 != null) {
									glSrvc.getGLEntriesByPnnoAndTxCode(loanfin.getAccountno(), loanfin.getTxcode(),
											loanfin.getTxrefno(), true, true);
								}

//								List<Tbglentries> jrnlentries1 = (List<Tbglentries>) dbSrvc.executeListHQLQuery(
//										"FROM Tbglentries WHERE txseqno=:txrefno and txcode=:txcode", params);
//								if (jrnlentries1 != null) {
//									System.out.println(jrnlentries1.size());
//									APIService api = new APIServiceImpl();
//									AddJournalForm jrnlform = new AddJournalForm();
//									jrnlform.setBranchID(jrnl1.getTxbr());
//									jrnlform.setCurrency("PHP");
//									jrnlform.setDetails("Loan Transaction for " + jrnl1.getPnno());
//									jrnlform.setDocumentStatus("APPROVED");
//									jrnlform.setExchangeRate("1");
//									// jrnlform.setOrigCompanyID(jrnl1.getTxlegveh());
//									jrnlform.setRefDate(sdf.format(jrnl1.getTxvaldt()));
//									jrnlform.setRefNbr(jrnl1.getTxrefno());
//									List<TransactionDetailsJournal> txjrnl1 = new ArrayList<TransactionDetailsJournal>();
//									for (Tbglentries entry : jrnlentries1) {
//										TransactionDetailsJournal item = new TransactionDetailsJournal();
//										item.setAccountCode(entry.getGlsl());
//										item.setSubAccountCode(loanfin.getCifno());
//										item.setLineDetails(jrnl1.getPnno());
//										item.setCredit(entry.getCredit() == null ? ""
//												: String.valueOf(entry.getCredit().setScale(2, RoundingMode.HALF_UP)));
//										item.setDebit(entry.getDebit() == null ? ""
//												: String.valueOf(entry.getDebit().setScale(2, RoundingMode.HALF_UP)));
//										txjrnl1.add(item);
//									}
//									jrnlform.setTransactionDetails(txjrnl1);
//									ReturnForm form = api.addJournal(jrnl1.getTxrefno(), jrnlform);
//									System.out.println(form.getMessage());
//									 apiflag = form.getMessage();
//								}
							}
						}
						// CREDIT TO DEPOSIT ACCOUNT FROM DISPOSITION
						List<Tbloanreleaseinst> creditToAccount = (List<Tbloanreleaseinst>) dbSrvc.executeListHQLQuery(
								"FROM Tbloanreleaseinst WHERE pnno=:acctno and disposition='3' AND status='0'", params); // status
																															// 2
																															// =
																															// deleted,
																															// 0
																															// =
																															// not
						if (creditToAccount != null && creditToAccount.size() > 0) {
							for (Tbloanreleaseinst acct : creditToAccount) {
								params.put("creditacctno", acct.getCreditacctno());
								Tbdeposit dep = (Tbdeposit) dbSrvc
										.executeUniqueHQLQuery("FROM Tbdeposit WHERE accountno=:creditacctno", params);
								if (dep != null) {
									DepositTransactionService depSrvc = new DepositTransactionServiceImpl();
									// CREDIT TO ACCOUNT
									Tbtransactioncode tx = (Tbtransactioncode) dbSrvc.executeUniqueHQLQuery(
											"FROM Tbtransactioncode WHERE txcode ='113031'", params);
									DepositTransactionForm depForm = new DepositTransactionForm();
									depForm.setAccountno(dep.getAccountNo());
									depForm.setTxcode(tx.getTxcode());
									depForm.setValuedate(account.getDtbook());
									depForm.setReason("9");
									depForm.setOverridestatus("0");
									depForm.setTxbranch(account.getLegbranch());
									depForm.setAccountnoto("");
									depForm.setErrorcorrect(false);
									depForm.setTxamount(acct.getAmount());
									depForm.setTxmode("3");
									depForm.setRemarks(txrefno);
									depSrvc.casaTransaction(depForm, tx, null);

								}
							}
						}
//						LOAN OFFSET
						params.put("appno", account.getApplno());
						List<Tbloanoffset> loansForOffset = (List<Tbloanoffset>) dbSrvc
								.executeListHQLQuery("FROM Tbloanoffset a WHERE a.id.appno=:appno", params);
						if (loansForOffset != null && loansForOffset.size() > 0) {
							TransactionService txSrvc = new TransactionServiceImpl();
							for (Tbloanoffset loanForOffset : loansForOffset) {
//								CREATE LOAN CREDIT ADJUSTMENT
								Tbloanfin loanfinForOffset = new Tbloanfin();
								loanfinForOffset.setAccountno(loanForOffset.getId().getAccountno());
								loanfinForOffset.setAddtnlint(BigDecimal.ZERO);
								loanfinForOffset.setApprovaldate(account.getDtbook());
								loanfinForOffset.setApprovedby(lstapp.getCreatedby());
								loanfinForOffset.setArtype(null);
								loanfinForOffset.setCifno(account.getPrincipalNo());
								loanfinForOffset.setClientname(account.getFullname());
								loanfinForOffset.setCommrate(BigDecimal.ZERO);
								loanfinForOffset.setCommamt(BigDecimal.ZERO);
								loanfinForOffset.setCreatedby(lstapp.getCreatedby());
								loanfinForOffset.setCreationdate(new Date());
								loanfinForOffset.setLegveh(account.getLegveh());
								loanfinForOffset.setPnno(loanForOffset.getId().getAccountno());
								loanfinForOffset.setSlaidno(account.getPrincipalNo());
								loanfinForOffset.setTxamount(loanForOffset.getLoanbal());
								loanfinForOffset.setTxamtbal(loanForOffset.getLoanbal());
								loanfinForOffset.setTxcode("50"); // credit adjustment
								loanfinForOffset.setTxdate(account.getDtbook());
								loanfinForOffset.setTxmode("3");
								loanfinForOffset.setTxoper(2);
								loanfinForOffset.setTxor("");
								loanfinForOffset.setTxordate(account.getDtbook());
								loanfinForOffset.setTxposteddate(account.getDtbook());
								loanfinForOffset.setTxprin(loanForOffset.getPrinbal());
								loanfinForOffset.setTxint(loanForOffset.getUidbal());
								loanfinForOffset.setTxlpc(loanForOffset.getLpc());
								loanfinForOffset.setTxmisc(loanForOffset.getOthercharges());
								loanfinForOffset.setTxsrce(account.getPnno());
								loanfinForOffset.setTxstatus("9");
								loanfinForOffset.setTxstatusdate(account.getDtbook());
								loanfinForOffset.setTxvaldt(account.getDtbook());
								loanfinForOffset.setWtaxamt(BigDecimal.ZERO);
								loanfinForOffset.setWtaxrate(BigDecimal.ZERO);
								loanfinForOffset.setReason("7");
								String loanForOffsetTxrefno = txSrvc.addEntry(loanfinForOffset, null);
								txSrvc.postSinglePayment(loanForOffsetTxrefno);
							}
						}
						// CREDIT TO DEPOSIT ACCOUNT FROM LOAN WORKSHEET
						List<Tbloandeduction> loanDeductions = (List<Tbloandeduction>) dbSrvc
								.executeListHQLQuery("FROM Tbloandeduction WHERE appno=:appno", params);
						if (loanDeductions != null && loanDeductions.size() > 0) {
							for (Tbloandeduction loanDeduction : loanDeductions) {
								params.put("creditacctno", loanDeduction.getCredittoaccountnumber());
								Tbdeposit dep = (Tbdeposit) dbSrvc
										.executeUniqueHQLQuery("FROM Tbdeposit WHERE accountno=:creditacctno", params);
								if (dep != null) {
									DepositTransactionService depSrvc = new DepositTransactionServiceImpl();
									// CREDIT TO ACCOUNT
									Tbtransactioncode tx = (Tbtransactioncode) dbSrvc.executeUniqueHQLQuery(
											"FROM Tbtransactioncode WHERE txcode ='113031'", params);
									DepositTransactionForm depForm = new DepositTransactionForm();
									depForm.setAccountno(dep.getAccountNo());
									depForm.setTxcode(tx.getTxcode());
									depForm.setValuedate(account.getDtbook());
									depForm.setReason("9");
									depForm.setOverridestatus("0");
									depForm.setTxbranch(account.getLegbranch());
									depForm.setAccountnoto("");
									depForm.setErrorcorrect(false);
									depForm.setTxamount(loanDeduction.getDeductionamount());
									depForm.setTxmode("3");
									depForm.setRemarks(txrefno);
									depSrvc.casaTransaction(depForm, tx, null);

								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
//					log.setCurrentdate(prcdate.getCurrentbusinessdate());
//					log.setNextdate(prcdate.getNextbusinessdate());
//					log.setEventdate(new Date());
//					log.setEventname("ERROR");
//					log.setUniquekey(txrefno);
//					log.setDescription("Class : " + e.getStackTrace()[0].getClassName() + " Method : " + e.getStackTrace()[0].getMethodName() + " Line : " + e.getStackTrace()[0].getLineNumber());
//					dbSrvc.save(log);
						result = "Failed";
						application.setTxstat("9");
						dbSrvc.saveOrUpdate(application);
						dbSrvc.executeHQLUpdate("DELETE FROM Tbloans WHERE accountno=:acctno", params);
						dbSrvc.executeHQLUpdate("DELETE FROM Tblntxjrnl WHERE accountno=:acctno", params);
						dbSrvc.executeHQLUpdate("DELETE FROM Tbpaymentsched WHERE accountno=:acctno", params);
					}
				}
			}
		} catch (

		Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String transactionPosting(List<LoanTransactionForm> finList) {
		// TODO Auto-generated method stub
		String flag = "failed";
		prcdate = (Tbprocessingdate) dbSrvc.executeUniqueHQLQuery("FROM Tbprocessingdate WHERE id = '1'", params);
		System.out.println(">> START LOAN POSTING " + new Date() + " >>");
//		log.setCurrentdate(prcdate.getCurrentbusinessdate());
//		log.setNextdate(prcdate.getNextbusinessdate());
//		log.setModulename("LOAN POSTING");
//		log.setEventdate(new Date());
//		log.setEventname("START");
//		log.setDescription("Start of Loan Posting");
//		log.setUniquekey("");
		dbSrvc.save(log);
		// List<Tbloanfin> finList = new ArrayList<Tbloanfin>();
		String runDate = DateTimeUtil.convertDateToString(prcdate.getStartdate(), DateTimeUtil.DATE_FORMAT_MM_DD_YYYY);
		Date dueDt = DateTimeUtil.getDueDate(DateTimeUtil.convertToDate(runDate, DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
		params.put("dueDt", dueDt);
		params.put("dt", runDate);
		int count = 0;
		for (LoanTransactionForm row : finList) {
			try {
				count++;
				System.out.println("acctno >>> " + row.getAccountno());
				params.put("acctno", row.getAccountno());
				params.put("txrefno", row.getTxrefno());

				loanfin = (Tbloanfin) dbSrvc.executeUniqueHQLQuery("FROM Tbloanfin WHERE txrefno=:txrefno", params);
				params.put("empno", loanfin.getCifno());

				System.out.println("acctno >>> " + loanfin.getAccountno());
				System.out.println("empno >>> " + loanfin.getCifno());
				if (loanfin.getTxrefno().equals(null) || loanfin.getTxrefno().equals("")
						|| loanfin.getTxrefno().isEmpty()) {
					loanfin.setTxrefno(HQLUtil.generateTransactionRefNoDate());
				}
				loans = (Tbloans) dbSrvc.executeUniqueHQLQuery("FROM Tbloans WHERE accountno =:acctno", params);
				jrnl = new Tblntxjrnl();
				jrnl = INITUtil.initialLoanTxJournal(loans, runDate);
				List<Tblntxjrnl> txlist = (List<Tblntxjrnl>) dbSrvc
						.executeListHQLQuery("FROM Tblntxjrnl WHERE accountno=:acctno", params);
				jrnl.setTxseqno(String.valueOf(txlist.size() + 1));
				jrnl.setTxcode(loanfin.getTxcode());
				jrnl.setTxvaldt(loanfin.getTxvaldt());
				jrnl.setTxrefno(row.getTxrefno());
				if (loanfin.getTxcode().equals("212") || loanfin.getTxcode().equals("222")
						|| loanfin.getTxcode().equals("40")) {
					applyPayment();
					creditTableUpdate();
					commonTableUpdate();
				} else if (loanfin.getTxcode().equals("232") || loanfin.getTxcode().equals("50")) {
					applyCredit();
					creditTableUpdate();
					commonTableUpdate();
				} else if (loanfin.getTxcode().equals("241") || loanfin.getTxcode().equals("20")) {
					applyDebit();
					debitTableUpdate();
					commonTableUpdate();
				} else if (loanfin.getTxcode().equals("251") || loanfin.getTxcode().equals("30")) {
					applyDebit();
					debitTableUpdate();
					commonTableUpdate();
				} else {
					System.out.println("Invalid TXCODE");
				}
			} catch (Exception e) {
//				log.setCurrentdate(prcdate.getCurrentbusinessdate());
//				log.setNextdate(prcdate.getNextbusinessdate());
//				log.setEventdate(new Date());
//				log.setEventname("ERROR");
//				log.setUniquekey(row.getTxrefno()==null?row.getAccountno():row.getTxrefno());
//				log.setDescription("Class : " + e.getStackTrace()[0].getClassName() + " Method : " + e.getStackTrace()[0].getMethodName() + " Line : " + e.getStackTrace()[0].getLineNumber());
//				dbSrvc.save(log);
				e.printStackTrace();
			}
		}
//		log.setCurrentdate(prcdate.getCurrentbusinessdate());
//		log.setNextdate(prcdate.getNextbusinessdate());
//		log.setEventdate(new Date());
//		log.setEventname("END");
//		log.setUniquekey("");
//		log.setDescription(String.valueOf(count) +  " transactions posted.");
//		dbSrvc.save(log);
		flag = "success";
		System.out.println(String.valueOf(count) + " transactions posted.");
		System.out.println(">> EOD PROCESS : END LOAN POSTING" + new Date() + " >>");
		return flag;
	}

	public void applyPayment() {

		try {
			// check AR
			if (loans.getAr1().compareTo(BigDecimal.ZERO) == 1) {
				if (loanfin.getTxamtbal().compareTo(loans.getAddtnlint()) == 1) {
					jrnl.setTxaddtnlint(loans.getAddtnlint());
					loanfin.setTxamtbal(loanfin.getTxamount().subtract(jrnl.getTxaddtnlint()));
					loans.setAddtnlint(BigDecimal.ZERO);
				} else {
					jrnl.setTxaddtnlint(loanfin.getTxamtbal());
					loanfin.setTxamtbal(BigDecimal.ZERO);
					loans.setAddtnlint(loans.getAddtnlint().subtract(jrnl.getTxaddtnlint()));
				}
			}
			// check additional interest
			if (loans.getAddtnlint().compareTo(BigDecimal.ZERO) == 1) {
				if (loanfin.getTxamtbal().compareTo(loans.getAr1()) == 1) {
					jrnl.setTxar1(loans.getAr1());
					loanfin.setTxamtbal(loanfin.getTxamount().subtract(jrnl.getTxar1()));
					loans.setAr1(BigDecimal.ZERO);
				} else {
					jrnl.setTxar1(loanfin.getTxamtbal());
					loanfin.setTxamtbal(BigDecimal.ZERO);
					loans.setAr1(loans.getAr1().subtract(jrnl.getTxar1()));
				}
			}

			// check LPC
			if (loans.getLpcbal().compareTo(BigDecimal.ZERO) == 1) {
				if (loanfin.getTxamtbal().compareTo(loans.getLpcbal()) == 1) {
					jrnl.setTxlpcprin(loans.getLpcbal());
					loanfin.setTxamtbal(loanfin.getTxamount().subtract(jrnl.getTxlpcprin()));
					loans.setLpcbal(BigDecimal.ZERO);
				} else {
					jrnl.setTxlpcprin(loanfin.getTxamtbal());
					loanfin.setTxamtbal(BigDecimal.ZERO);
					loans.setLpcbal(loans.getLpcbal().subtract(jrnl.getTxlpcprin()));
				}
			}

			while (loanfin.getTxamtbal().compareTo(BigDecimal.ZERO) == 1) {
				preComputedIlNoPaid();
			}
			if (loanfin.getTxmode().equals("1")) {
				loans.setChk4clr(loans.getChk4clr().add(BigDecimal.ONE));
				insertCheckForClearing();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void applyCredit() {
		try {
			if (loanfin.getTxmisc().compareTo(loans.getAr1()) == 1) {
				loanfin.setTxstatus("R");
				loanfin.setTxstatusdate(prcdate.getStartdate());
				loanfin.setParticulars("AR Overpayment");
				dbSrvc.saveOrUpdate(loanfin);
			} else {
				loanfin.setTxamtbal(loanfin.getTxamtbal().subtract(loanfin.getTxmisc()));
				loans.setAr1(loans.getAr1().subtract(loanfin.getTxmisc()));
				jrnl.setTxar1(loanfin.getTxmisc());
				loanfin.setTxmisc(BigDecimal.ZERO);
			}
			if (loanfin.getTxlpc().compareTo(loans.getLpcbal()) == 1) {
				loanfin.setTxstatus("R");
				loanfin.setTxstatusdate(prcdate.getStartdate());
				loanfin.setParticulars("LPC Overpayment");
				loanfin.setTxamtbal(loanfin.getTxamount());
				loanfin.setTxmisc(jrnl.getTxar1());
				dbSrvc.saveOrUpdate(loanfin);
			} else {
				loanfin.setTxamtbal(loanfin.getTxamtbal().subtract(loanfin.getTxlpc()));
				loans.setLpcbal(loans.getLpcbal().subtract(loanfin.getTxlpc()));
				jrnl.setTxlpcprin(loanfin.getTxlpc());
				loanfin.setTxlpc(BigDecimal.ZERO);
			}
			// compute ilno () until txamtbal > 0
			while (loanfin.getTxamtbal().compareTo(BigDecimal.ZERO) == 1) {
				preComputedIlNoPaid();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void applyDebit() {
		try {
			if (loanfin.getTxmisc().compareTo(BigDecimal.ZERO) == 1) {
				jrnl.setTxar1(loanfin.getTxmisc());
				loanfin.setTxamtbal(loanfin.getTxamtbal().subtract(loanfin.getTxmisc()));
				loans.setAr1(loans.getAr1().add(loanfin.getTxmisc()));
			}
			if (loanfin.getTxlpc().compareTo(BigDecimal.ZERO) == 1) {
				jrnl.setTxlpcprin(loanfin.getTxlpc());
				loanfin.setTxamtbal(loanfin.getTxamtbal().subtract(loanfin.getTxlpc()));
				loans.setAr1(loans.getLpcbal().add(loanfin.getTxlpc()));
			}
			computIlNoReverse();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void computIlNoPaid() {
		try {
			if (loans.getInttyp().equals("4") || loans.getInttyp().equals("P")) {
				preComputedIlNoPaid();
			} else if (loans.getInttyp().equals("1") || loans.getInttyp().equals("A")) {
				preComputedIlNoPaid();
			} else {
				preComputedIlNoPaid();
				System.out.println("Invalid Inttype");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void computIlNoReverse() {
		try {
			if (loans.getInttyp().equals("4") || loans.getInttyp().equals("P")) {
				if (loanfin.getTxamtbal().compareTo(BigDecimal.ZERO) == 1
						&& loans.getPartialprin().compareTo(BigDecimal.ZERO) == 1) {
					if (loanfin.getTxamtbal().compareTo(loans.getPartialprin()) >= 0) {
						loanfin.setTxamtbal(loanfin.getTxamtbal().subtract(loans.getPartialprin()));
						jrnl.setTxprin(jrnl.getTxprin().add(loans.getPartialprin()));
						loans.setPartialprin(BigDecimal.ZERO);
					} else {
						loans.setPartialprin(loans.getPartialprin().subtract(loanfin.getTxamtbal()));
						jrnl.setTxprin(jrnl.getTxprin().add(loanfin.getTxamtbal()));
						loanfin.setTxamtbal(BigDecimal.ZERO);
					}
				}
				if (loanfin.getTxamtbal().compareTo(BigDecimal.ZERO) == 1
						&& loans.getPartialint().compareTo(BigDecimal.ZERO) == 1) {
					if (loanfin.getTxamtbal().compareTo(loans.getPartialint()) >= 0) {
						loanfin.setTxamtbal(loanfin.getTxamtbal().subtract(loans.getPartialint()));
						jrnl.setTxint(jrnl.getTxint().add(loans.getPartialint()));
						loans.setPartialint(BigDecimal.ZERO);
					} else {
						loans.setPartialint(loans.getPartialint().subtract(loanfin.getTxamtbal()));
						jrnl.setTxint(jrnl.getTxint().add(loanfin.getTxamtbal()));
						loanfin.setTxamtbal(BigDecimal.ZERO);
					}
				}
				loans.setTpdilamt(loans.getTpdilamt().subtract(jrnl.getTxint().add(jrnl.getTxprin())));
				if (loanfin.getTxamtbal().compareTo(BigDecimal.ZERO) == 1) {
					if (loans.getTpdilno() > 0) {
						while (loanfin.getTxamtbal().compareTo(BigDecimal.ZERO) == 1
								&& loans.getTpdilamt().compareTo(BigDecimal.ZERO) == 1) {
							preComputedIlNoReverse();
						}
					} else {
						loanfin.setTxstatus("U");
						loanfin.setTxstatusdate(prcdate.getStartdate());
						loanfin.setParticulars("Over Reversal");
//						loanfin.setTxLpc(jrnl.getTxlpcprin());
//						loanfin.setTxMisc(jrnl.getTxar1());
//						loanfin.setTxAmtBal(loanfin.getTxAmount());
						dbSrvc.saveOrUpdate(loanfin);
						System.out.println("Over Reversal");
					}
				}
			} else if (loans.getInttyp().equals("1") || loans.getInttyp().equals("A")) {
				preComputedIlNoReverse();
			} else {
				System.out.println("Invalid Inttype");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void preComputedIlNoPaid() {
		try {
			int wrkilno = loans.getTpdilno() + 1;
			params.put("wrkilno", wrkilno);
			Tbpaymentsched sched = (Tbpaymentsched) dbSrvc
					.executeUniqueHQLQuery("FROM Tbpaymentsched WHERE accountno =:acctno and ilno=:wrkilno", params);
			if (loanfin.getTxamtbal().compareTo(BigDecimal.ZERO) == 1 && wrkilno > loans.getTdueilno() + 1) {
				loans.setExcessbal(loans.getExcessbal().add(loanfin.getTxamtbal()));
				jrnl.setTxexcess(loanfin.getTxamtbal());
				loanfin.setTxamtbal(BigDecimal.ZERO);
			}
			BigDecimal wrkInt = sched.getIlint().subtract(loans.getPartialint());
			if (loanfin.getTxamtbal().compareTo(wrkInt) == -1) {
				jrnl.setTxint(jrnl.getTxint().add(loanfin.getTxamtbal()));
				loans.setPartialint(loans.getPartialint().add(loanfin.getTxamtbal()));
				loanfin.setTxamtbal(BigDecimal.ZERO);
			} else {
				loanfin.setTxamtbal(loanfin.getTxamtbal().subtract(wrkInt));
				jrnl.setTxint(jrnl.getTxint().add(wrkInt));
				loans.setPartialint(sched.getIlint());
				BigDecimal wrkPrin = sched.getIlprin().subtract(loans.getPartialprin());
				if (loanfin.getTxamtbal().compareTo(wrkPrin) == -1) {
					jrnl.setTxprin(jrnl.getTxprin().add(loanfin.getTxamtbal()));
					loans.setPartialprin(loans.getPartialprin().add(loanfin.getTxamtbal()));
					loanfin.setTxamtbal(BigDecimal.ZERO);
				} else {
					loanfin.setTxamtbal(loanfin.getTxamtbal().subtract(wrkPrin));
					jrnl.setTxprin(jrnl.getTxprin().add(wrkPrin));
					loans.setPartialint(BigDecimal.ZERO);
					loans.setPartialprin(BigDecimal.ZERO);
					loans.setTpdilno(wrkilno);
					loans.setTpdilduedt(sched.getIlduedt());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void preComputedIlNoReverse() {
		try {
			if (loanfin.getTxamtbal().compareTo(BigDecimal.ZERO) == 1
					&& loans.getExcessbal().compareTo(BigDecimal.ZERO) == 1) {
				if (loanfin.getTxamtbal().compareTo(loans.getExcessbal()) == -1) {
					loans.setExcessbal(loans.getExcessbal().subtract(loanfin.getTxamtbal()));
					jrnl.setTxexcess(loanfin.getTxamtbal());
					loanfin.setTxamtbal(BigDecimal.ZERO);
					return;
				} else {
					loanfin.setTxamtbal(loanfin.getTxamtbal().subtract(loans.getExcessbal()));
					jrnl.setTxexcess(loans.getExcessbal());
					loans.setExcessbal(BigDecimal.ZERO);
					System.out.println(loanfin.getTxamtbal());
				}
			}
			int wrkIlno = loans.getTpdilno();
			params.put("wrkilno", wrkIlno);
			Tbpaymentsched sched = (Tbpaymentsched) dbSrvc
					.executeUniqueHQLQuery("FROM Tbpaymentsched WHERE accountno =:acctno and ilno=:wrkilno", params);
			System.out.println(sched.getIlno() + " <<< ilno");
			loans.setPartialprin(sched.getIlprin());
			loans.setPartialint(sched.getIlint());
			if (loans.getPartialprin().compareTo(BigDecimal.ZERO) == 1
					&& loans.getPartialint().compareTo(BigDecimal.ZERO) == 1) {
				if (loanfin.getTxamtbal().compareTo(loans.getPartialprin()) >= 0) {
					loanfin.setTxamtbal(loanfin.getTxamtbal().subtract(loans.getPartialprin()));
					jrnl.setTxprin(jrnl.getTxprin().add(loans.getPartialprin()));
					loans.setPartialprin(BigDecimal.ZERO);
				} else {
					jrnl.setTxprin(jrnl.getTxprin().add(loanfin.getTxamtbal()));
					loans.setPartialprin(loans.getPartialprin().subtract(loanfin.getTxamtbal()));
					loanfin.setTxamtbal(BigDecimal.ZERO);
				}

				if (loanfin.getTxamtbal().compareTo(loans.getPartialint()) >= 0) {
					loanfin.setTxamtbal(loanfin.getTxamtbal().subtract(loans.getPartialint()));
					jrnl.setTxint(jrnl.getTxint().add(loans.getPartialint()));
					loans.setPartialint(BigDecimal.ZERO);
					loans.setPartialprin(BigDecimal.ZERO);
				} else {
					jrnl.setTxint(loanfin.getTxamtbal());
					loans.setPartialint(loans.getPartialint().subtract(loanfin.getTxamtbal()));
					loanfin.setTxamtbal(BigDecimal.ZERO);
				}
			}
			if (loans.getPartialprin().compareTo(sched.getIlprin()) == -1) {
				loans.setTpdilno(loans.getTpdilno() - 1);
				loans.setIltogo(loans.getIltogo() + 1);
				if (loans.getTpdilno() == 0) {
					loans.setTpdilduedt(null);
				} else {
					params.put("tpdilno", loans.getTpdilno());
					Tbpaymentsched paidSched = (Tbpaymentsched) dbSrvc.executeUniqueHQLQuery(
							"FROM Tbpaymentsched WHERE accountno =:acctno and ilno =:tpdilno", params);
					loans.setTpdilduedt(paidSched.getIlduedt());
				}
			}
			loans.setTpdilamt(loans.getTpdilamt().subtract(jrnl.getTxint().add(jrnl.getTxprin())));
			if (loans.getTpdilamt().compareTo(BigDecimal.ZERO) == 0) {
				loanfin.setTxstatus("U");
				loanfin.setTxstatusdate(prcdate.getStartdate());
				loanfin.setParticulars("Over Reversal");
//				loanfin.setTxLpc(jrnl.getTxlpcprin());
//				loanfin.setTxMisc(jrnl.getTxar1());
//				loanfin.setTxAmtBal(loanfin.getTxAmount());
				dbSrvc.saveOrUpdate(loanfin);
				System.out.println("Over Reversal");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void creditTableUpdate() {
		try {
			loans.setTpdintamt(loans.getTpdintamt().add(jrnl.getTxint()));
			loans.setTpdprinamt(loans.getTpdprinamt().add(jrnl.getTxprin()));
			loans.setTpdilamt(loans.getTpdilamt().add(jrnl.getTxint().add(jrnl.getTxprin())));
			loans.setYtdintpd(loans.getYtdintpd().add(jrnl.getTxint()));
			loans.setBtdintpd(loans.getBtdintpd().add(jrnl.getTxint()));
			loans.setYtdprinpd(loans.getYtdprinpd().add(jrnl.getTxprin()));
			loans.setBtdprinpd(loans.getBtdprinpd().add(jrnl.getTxprin()));
			if (loans.getIntpaytype().equals("0")) {
				loans.setAir(loans.getAir().subtract(jrnl.getTxint()));
			} else {
				if (jrnl.getTxint().compareTo(loans.getAir()) == 1) {
					jrnl.setTxair(loans.getAir());
//					jrnl.setTxienc(jrnl.getTxint().subtract(loans.getAir()));
					loans.setMtdint(loans.getMtdint().subtract(jrnl.getTxair()));
					loans.setAir(BigDecimal.ZERO);
				} else {
					jrnl.setTxair(jrnl.getTxint());
					loans.setAir(loans.getAir().subtract(jrnl.getTxint()));
				}
			}
			loans.setPrinbal(loans.getPrinbal().subtract(jrnl.getTxprin()));
//			loans.setUidbal(loans.getUidbal().subtract(jrnl.getTxint())); // added
			loans.setLoanbal(loans.getLoanbal().subtract(jrnl.getTxprin().add(jrnl.getTxint())));
//			loans.setLoanbal(loans.getLoanbal().subtract(jrnl.getTxint()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void debitTableUpdate() {
		try {
			loans.setTpdintamt(loans.getTpdintamt().subtract(jrnl.getTxint()));
			loans.setTpdilamt(loans.getTpdilamt().subtract(jrnl.getTxint()));
			loans.setTpdprinamt(loans.getTpdprinamt().subtract(jrnl.getTxprin()));
			loans.setTpdilamt(loans.getTpdilamt().subtract(jrnl.getTxprin())); // updated for while loop
			loans.setYtdintpd(loans.getYtdintpd().subtract(jrnl.getTxint()));
			loans.setBtdintpd(loans.getBtdintpd().subtract(jrnl.getTxint()));
			loans.setYtdprinpd(loans.getYtdprinpd().subtract(jrnl.getTxprin()));
			loans.setBtdprinpd(loans.getBtdprinpd().subtract(jrnl.getTxprin()));
			if (loans.getIntpaytype().equals("0")) {
				loans.setAir(loans.getAir().add(jrnl.getTxint()));
			}
			loans.setPrinbal(loans.getPrinbal().add(jrnl.getTxprin()));
//			loans.setUidbal(loans.getUidbal().add(jrnl.getTxint())); removed 7/2/2019
			loans.setLoanbal(loans.getLoanbal().add(jrnl.getTxprin()));
//			loans.setLoanbal(loans.getLoanbal().add(jrnl.getTxint())); removed 7/2/2019
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void commonTableUpdate() {
		try {
			if (loans.getTdueilamt().compareTo(loans.getTpdilamt()) == 1) {
				loans.setUnpaidint(loans.getTdueintamt().subtract(loans.getTpdintamt()));
				loans.setUnpaidprin(loans.getTdueprinamt().subtract(loans.getTpdprinamt()));
			} else {
				loans.setUnpaidint(BigDecimal.ZERO);
				loans.setUnpaidprin(BigDecimal.ZERO);
			}
			loans.setIltogo(Integer.valueOf(loans.getPnilno() - loans.getTpdilno()));
//			BigDecimal wrkNTC = loans.getAr1().add(loans.getLpcbal()).add(loans.getIenc()).add(loans.getLoanbal());
//			if(wrkNTC.compareTo(BigDecimal.ONE)==1 && loans.getAcctsts()==5) {
//				loans.setOldsts(loans.getAcctsts());
//				loans.setAcctsts(1);
//				loans.setAcctstsDate(prcdate.getStartdate());
//			} else if(wrkNTC.compareTo(BigDecimal.ONE)==-1 && loanfin.getReason().equals("5")){
//				loans.setOldsts(loans.getAcctsts());
//				loans.setAcctsts(8);
//				loans.setAcctstsDate(prcdate.getStartdate());
//			} else if(wrkNTC.compareTo(BigDecimal.ONE)==-1 && loans.getChk4clr().compareTo(BigDecimal.ZERO)==0){
//				loans.setOldsts(loans.getAcctsts());
//				loans.setAcctsts(9);
//				loans.setAcctstsDate(prcdate.getStartdate());
//			} else if(wrkNTC.compareTo(BigDecimal.ONE)==-1 && loans.getChk4clr().compareTo(BigDecimal.ZERO)==1){
//				loans.setOldsts(loans.getAcctsts());
//				loans.setAcctsts(5);
//				loans.setAcctstsDate(prcdate.getStartdate());
//			} if(loans.getAcctsts()==1 && loans.getTpdilno()<loans.getTdueilno()){
//				loans.setOldsts(loans.getAcctsts());
//				loans.setAcctsts(2);
//				loans.setAcctstsDate(prcdate.getStartdate());
//			} else if(loans.getAcctsts()==2 && loans.getTpdilno()>=loans.getTdueilno()){
//				loans.setOldsts(loans.getAcctsts());
//				loans.setAcctsts(1);
//				loans.setAcctstsDate(prcdate.getStartdate());
//			}
			if (loans.getTpdilno() < Integer.valueOf(loans.getPnilno())) {
				params.put("nxtIlno", loans.getTpdilno() + 1);
				Tbpaymentsched nxtsched = (Tbpaymentsched) dbSrvc.executeUniqueHQLQuery(
						"FROM Tbpaymentsched WHERE accountno =:acctno AND ilno =:nxtIlno", params);
				loans.setNxtilno(nxtsched.getIlno());
				loans.setNxtprinamt(nxtsched.getIlprin());
				loans.setNxtintamt(nxtsched.getIlint());
				loans.setNxtilamt(nxtsched.getIlamt());
				loans.setNxtduedt(nxtsched.getIlduedt());
			}
			loans.setLstpydt(jrnl.getTxdate());
			loans.setLsttxdt(jrnl.getTxdate());
			loans.setLstpyamt(jrnl.getTxamt());
			loans.setLsttxcod(jrnl.getTxcode());
			loans.setLpdduedt(jrnl.getDuedtpd());
			loans.setLpdilno(jrnl.getIlnopd());
			jrnl.setTxamt(jrnl.getTxint().add(jrnl.getTxprin().add(jrnl.getTxar1().add(jrnl.getTxlpcprin()))));
			jrnl.setIlnopd(loans.getTpdilno());
			jrnl.setDuedtpd(loans.getTpdilduedt());
			jrnl.setTxmode(loanfin.getTxmode());
			jrnl.setTxloanbal(loans.getLoanbal());
			jrnl.setTxprinbal(loans.getPrinbal());
			jrnl.setTxuidb(BigDecimal.ZERO);// jrnl.setTxuidb(loans.getUidbal());
			jrnl.setTxacctsts(loans.getAcctsts().toString());
			jrnl.setOldstat(loans.getOldsts() == null ? null : loans.getOldsts().toString());
			jrnl.setTxexcessbal(loans.getExcessbal());
			if (loanfin.getTxstatus() != "6") {
				dbSrvc.saveOrUpdate(loans);
				dbSrvc.save(jrnl);
				if (loanfin.getTxamtbal().compareTo(BigDecimal.ZERO) == 0) {
					loanfin.setTxstatus("10");
				}
				dbSrvc.saveOrUpdate(loanfin);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String insertCheckForClearing() {
		String flag = "failed";
		try {
			Tbchecksforclearing check = new Tbchecksforclearing();
			check.setAccountnumber(loanfin.getAccountno());
			check.setBrstn(loanfin.getTxbrstn());
			check.setCheckamount(loanfin.getTxamount());
			check.setCheckdate(loanfin.getTxvaldt());
			check.setChecknumber(loanfin.getTxchkno());
			check.setChecktype(loanfin.getTxchecktype() == null ? null : Integer.valueOf(loanfin.getTxchecktype()));
			Calendar cal = Calendar.getInstance();
			cal.setTime(loanfin.getTxvaldt());
			cal.add(Calendar.DAY_OF_MONTH, 5);
			check.setCheckdate(loanfin.getTxvaldt());
			check.setClearingdate(cal.getTime());
			check.setClearingdays(5);
			check.setStatus("1");
			if (dbSrvc.save(check)) {
				flag = "Success";
			} else {
				loanfin.setTxstatus("9");
				loanfin.setParticulars("Unable to save check details.");
				loanfin.setTxstatusdate(prcdate.getStartdate());
				dbSrvc.saveOrUpdate(loanfin);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String checkClearing() {
		// TODO Auto-generated method stub
		String flag = "Failed";
		try {
//			flag = (String) 
			Clob clob = (Clob) dbSrvc.execStoredProc("DECLARE @result varchar(MAX) EXEC sp_checkClearing "
					+ "@result OUTPUT SELECT CAST(@result AS VARCHAR(MAX))", params, null, 0, null);
			flag = clob.getSubString(1, (int) clob.length());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return flag;
	}

	@Override
	public void runLMSEOD() {
		// TODO Auto-generated method stub
		try {
			LMSDashboardService lmsDashboard = new LMSDashboardServiceImpl();
			System.out.println(">> EOD PROCESS : START CHECK CLEARING " + new Date() + " >>");
//			log.setCurrentdate(prcdate.getStartdate());
//			log.setNextdate(prcdate.getEnddate());
//			log.setModulename("LOAN EOD");
//			log.setEventdate(new Date());
//			log.setEventname("START");
//			log.setDescription("Loan End Of Day");
//			log.setUniquekey("");
//			dbSrvc.save(log);
//			log.setCurrentdate(prcdate.getCurrentbusinessdate());
//			log.setNextdate(prcdate.getNextbusinessdate());
//			log.setModulename("CHECK CLEARING");
//			log.setEventdate(new Date());
//			log.setEventname("START");
//			log.setUniquekey("");
//			log.setDescription("Start of Check Clearing Routine");
//			dbSrvc.save(log);
//			log.setCurrentdate(prcdate.getCurrentbusinessdate());
//			log.setNextdate(prcdate.getNextbusinessdate());
//			log.setModulename("CHECK CLEARING");
//			log.setEventdate(new Date());
//			log.setEventname("END");
//			log.setDescription(checkClearing());
//			log.setUniquekey("");
//			dbSrvc.save(log);
			checkClearing();
			System.out.println(">> EOD PROCESS : END CHECK CLEARING " + new Date() + " >>");
			List<LoanAccountForm> newloans = lmsDashboard.getLoanReleases("9");
			if (newloans != null) {
				loanBooking(newloans);
			}
			List<LoanTransactionForm> finTx = lmsDashboard.getLoanTransactionbyStatus("9");
			if (finTx != null) {
				transactionPosting(finTx);
			}
			PaymentAnniversary.main();
//			log.setModulename("LOAN EOD");
//			log.setEventdate(new Date());
//			log.setEventname("END");
//			log.setDescription("Loan End Of Day");
//			log.setUniquekey("");
//			dbSrvc.save(log);
//			prcdate.setStartdate(prcdate.getNextbusinessdate());
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(prcdate.getNextbusinessdate());
//			cal.add(Calendar.DAY_OF_MONTH, 1);
//			prcdate.setEnddate(cal.getTime());
//			dbSrvc.saveOrUpdate(prcdate);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public Tbprocessingdate getProcessingDate() {
		Tbprocessingdate processdate = new Tbprocessingdate();
		DBService dbSrvcLOS = new DBServiceImpl();
		processdate = (Tbprocessingdate) dbSrvcLOS.executeUniqueHQLQuery("FROM  Tbprocessingdate where id = 1", null);
		return processdate;
	}

	@Override
	public String updateProcessEndDate(Tbprocessingdate processdate) {
		String flag = "failed";
		Tbprocessingdate date = new Tbprocessingdate();
		DBService dbSrvcLOS = new DBServiceImpl();
		try {
			date = (Tbprocessingdate) dbSrvcLOS.executeUniqueHQLQuery("FROM Tbprocessingdate where id = 1", null);
			if (date == null) {
				dbSrvcLOS.executeUpdate("TRUNCATE TABLE Tbprocessingdate", null);
				date = new Tbprocessingdate();
				date.setEnddate(processdate.getEnddate());
				date.setStartdate(processdate.getStartdate());
				dbSrvcLOS.save(date);
				flag = "success";
				return flag;
			} else {
				date.setEnddate(processdate.getEnddate());
				date.setStartdate(processdate.getStartdate());
				dbSrvcLOS.saveOrUpdate(date);
				flag = "success";
				return flag;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public String singletransactionPosting(LoanTransactionForm tx) {
		// TODO Auto-generated method stub
		String flag = "Failed";
		// prcdate = (Tbprocessingdate) dbSrvc.executeUniqueHQLQuery("FROM
		// Tbprocessingdate WHERE id = '1'", params);
		System.out.println(">> START SINGLE LOAN POSTING " + new Date() + " >>");

		System.out.println("acctno >>> " + tx.getAccountno());
		params.put("acctno", tx.getAccountno());
		params.put("txrefno", tx.getTxrefno());
		loanfin = (Tbloanfin) dbSrvc.executeUniqueHQLQuery("FROM Tbloanfin WHERE txrefno=:txrefno", params);
		params.put("empno", loanfin.getCifno());
		String runDate = DateTimeUtil.convertDateToString(loanfin.getTxvaldt(), DateTimeUtil.DATE_FORMAT_MM_DD_YYYY);
		Date dueDt = DateTimeUtil.getDueDate(DateTimeUtil.convertToDate(runDate, DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
		params.put("dueDt", dueDt);
		params.put("dt", runDate);

		System.out.println("acctno >>> " + loanfin.getAccountno());
		System.out.println("empno >>> " + loanfin.getCifno());

		loans = (Tbloans) dbSrvc.executeUniqueHQLQuery("FROM Tbloans WHERE accountno =:acctno", params);
		jrnl = new Tblntxjrnl();
		jrnl = INITUtil.initialLoanTxJournal(loans, runDate);
		@SuppressWarnings("unchecked")
		List<Tblntxjrnl> txlist = (List<Tblntxjrnl>) dbSrvc
				.executeListHQLQuery("FROM Tblntxjrnl WHERE accountno=:acctno", params);
		jrnl.setTxseqno(String.valueOf(txlist.size() + 1));
		jrnl.setTxcode(loanfin.getTxcode());
		jrnl.setTxvaldt(loanfin.getTxvaldt());
		jrnl.setTxrefno(tx.getTxrefno());
		if (loanfin.getTxcode().equals("212") || loanfin.getTxcode().equals("222")
				|| loanfin.getTxcode().equals("40")) {
			applyPayment();
			creditTableUpdate();
			commonTableUpdate();
		} else if (loanfin.getTxcode().equals("232") || loanfin.getTxcode().equals("50")) {
			applyCredit();
			creditTableUpdate();
			commonTableUpdate();
		} else if (loanfin.getTxcode().equals("241") || loanfin.getTxcode().equals("20")) {
			applyDebit();
			debitTableUpdate();
			commonTableUpdate();
		} else if (loanfin.getTxcode().equals("251") || loanfin.getTxcode().equals("30")) {
			applyDebit();
			debitTableUpdate();
			commonTableUpdate();
		} else {
			System.out.println("Invalid TXCODE");
		}

		return flag;
	}

	@Override
	public String runLMSEOD_new() {
		// TODO Auto-generated method stub
		String flag = "Failed";
		try {
//			flag = (String) 
			String logs = (String) dbSrvc.execStoredProc("DECLARE @result varchar(8000) EXEC sp_LMSEODRUN "
					+ "@result OUTPUT SELECT CAST(@result AS VARCHAR(8000))", params, null, 0, null);

//			List<Tblntxjrnl> jrnl = (List<Tblntxjrnl>) dbSrvc.executeListHQLQuery(
//					"FROM Tblntxjrnl where txcode='81' and txamt>0 and txremarks<>'transmitted'", params);

//			for (Tblntxjrnl tx : jrnl) {
//				params.put("txrefno", tx.getTxrefno());
//				params.put("txcode", tx.getTxcode());
//				List<Tbglentries> jrnlentries = (List<Tbglentries>) dbSrvc
//						.executeListHQLQuery("FROM Tbglentries WHERE txseqno=:txrefno and txcode=:txcode", params);
//				if (jrnlentries != null) {
////					APIService api = new APIServiceImpl();
//					AddJournalForm jrnlform = new AddJournalForm();
//					jrnlform.setBranchID(tx.getTxbr());
//					jrnlform.setCurrency("PHP");
//					jrnlform.setDetails("INTEREST ACCRUAL for " + tx.getPnno());
//					jrnlform.setDocumentStatus("APPROVED");
//					jrnlform.setExchangeRate("1");
//					// jrnlform.setOrigCompanyID(tx.getTxlegveh());
//					jrnlform.setRefDate(sdf.format(tx.getTxvaldt()));
//					jrnlform.setRefNbr(tx.getTxrefno());
//					List<TransactionDetailsJournal> txjrnl = new ArrayList<TransactionDetailsJournal>();
//					for (Tbglentries entry : jrnlentries) {
//						TransactionDetailsJournal item = new TransactionDetailsJournal();
//						item.setAccountCode(entry.getGlsl());
//						item.setSubAccountCode("");
//						item.setLineDetails("INTEREST ACCRUAL for " + tx.getPnno());
//						item.setCredit(entry.getCredit() == null ? ""
//								: String.valueOf(entry.getCredit().setScale(2, RoundingMode.HALF_UP)));
//						item.setDebit(entry.getDebit() == null ? ""
//								: String.valueOf(entry.getDebit().setScale(2, RoundingMode.HALF_UP)));
//						txjrnl.add(item);
//					}
//					jrnlform.setTransactionDetails(txjrnl);
////					ReturnForm form = api.addJournal(tx.getTxrefno(), jrnlform);
////					if (form.getFlag().equals("success")) {
////						tx.setTxremarks("transmitted");
////					}
//				}
//			}

			flag = logs.toString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return flag;
	}

}
