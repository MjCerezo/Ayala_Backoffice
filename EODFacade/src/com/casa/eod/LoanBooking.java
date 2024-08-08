/**
 * 
 */
package com.casa.eod;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbaccountinfo;
import com.coopdb.data.Tbbranch;
import com.coopdb.data.Tblntxjrnl;
import com.coopdb.data.Tbloans;
import com.coopdb.data.Tblogs;
import com.coopdb.data.Tbpaysched;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.util.INITUtil;
import com.etel.utils.DateTimeUtil;
import com.etel.utils.HQLUtil;

/**
 * @author ETEL-COMP05
 *
 */
@SuppressWarnings("unchecked")
public class LoanBooking {

	/**
	 * @param args
	 */
	private static DBService dbSrvc = new DBServiceImpl();
	private static Map<String, Object> params = HQLUtil.getMap();
	private static Tblogs log = new Tblogs();
//	private static Tbprocessingdate prcdate = (Tbprocessingdate) dbSrvc.executeUniqueHQLQuery("FROM Tbprocessingdate WHERE id = 1 ", null);
	private static EODService eodSrvc = new EODServiceImpl();
	private static Tbbranch prcdate = eodSrvc.getMainBranch();
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static String loanBooking(List<Tbaccountinfo> newloans) {
		
		// TODO Auto-generated method stub
		System.out.println(">> EOD PROCESS : START LOAN BOOKING "+new Date()+" >>");
		log.setCurrentdate(prcdate.getCurrentbusinessdate());
		log.setNextdate(prcdate.getNextbusinessdate());
		log.setModulename("LOAN BOOKING");
		log.setEventdate(new Date());
		log.setEventname("START");
		log.setDescription("Start of Loan Booking");
		log.setUniquekey("");
		dbSrvc.save(log);
		String result = "Success";
//		Tbmember member = new Tbmember();
//		Tbloanproduct prdmtrx = new Tbloanproduct();
		Tbloans account = new Tbloans();
		Tbaccountinfo application = new Tbaccountinfo();
//		Tbpaysched amortsched = new Tbpaysched();
//		Tbloanfin payment = new Tbloanfin();
		String accountno = "";
		String txrefno = "";
//		LoanCalculatorForm loanform = new LoanCalculatorForm();
//		LoanCalculatorService calcSrvc = new LoanCalculatorServiceImpl();
		int count = 0;
		
			//			newloans = executeListHQLQuery("FROM Tbnewloan WHERE txStatus ='8' AND txRemarks = 'FOR BOOKING' order by id", params);
			if(newloans.size()>0){
				for(Tbaccountinfo row: newloans){
					try {
					count++;
					txrefno = row.getApplno();
					System.out.println(txrefno);
					application = row;
					accountno ="";
					params.put("txrefno", application.getApplno());
					params.put("empno", application.getClientid());
//					member = (Tbmember) dbSrvc.executeUniqueHQLQuery("FROM Tbmember WHERE employeeno =:empno and slastatus ='A'", params);
//					params.put("rank", Integer.valueOf(member.getRank()));
					params.put("subprd", application.getProduct());
//					if(application.getProduct().equals("90") || application.getProduct().equals("59001"))
//						prdmtrx = (Tbloanproduct) dbSrvc.executeUniqueHQLQuery("FROM Tbloanproduct WHERE productcode =:subprd", params);
//					else
//						prdmtrx = (Tbloanproduct) dbSrvc.executeUniqueHQLQuery("FROM Tbproductmatrix WHERE subTypeCode =:subprd AND rankMin<=:rank AND rankMax>=:rank", params);
					//					application.setTotaloffsetamt(getSQLAmount("SELECT SUM(txamount) FROM Tbloanfin WHERE txrefno=:txrefno", params));
					//					application.setNetproceeds(
					//							application.getLoanAmount().subtract(application.getTotaloffsetamt())
					//							.subtract(prdmtrx.getIndPfdeduct() == null || !prdmtrx.getIndPfdeduct()?BigDecimal.ZERO:prdmtrx.getProcFee()));
					accountno = HQLUtil.generateAccountNo(application.getProduct());
					params.put("acctno", accountno);
//					loanform.setDownpayment(BigDecimal.ZERO);
//					loanform.setAdvanceinterest(BigDecimal.ZERO);
//					loanform.setCycle(application.getTermcyc());
//					loanform.setBookingdate(prcdate.getStartdate());
//					loanform.setFirstbookingdate(application.getFduedt());
//					loanform.setLoanamount(application.getPnamt());
//					loanform.setDocstamp(BigDecimal.ZERO);
//					loanform.setBkintey(BigDecimal.ZERO);
//					loanform.setPrinbal(BigDecimal.ZERO);
//					loanform.setAmountfinanced(application.getPnamt());
//					loanform.setTotalcharges(BigDecimal.ZERO);
//					loanform.setInterestdue(BigDecimal.ZERO);
//					loanform.setAmortfee(BigDecimal.ZERO);
//					loanform.setRate(application.getNominal());
//					loanform.setOthercharges(BigDecimal.ZERO);
//					loanform.setProducttype(application.getProduct());
//					loanform.setTerm(application.getPpynum().doubleValue());
//					loanform.setPpynum(application.getPpynum());
//					loanform.setRepaytype(application.getPpytype());
//					loanform.setInttype(application.getInttyp());
//					loanform.setNetproceeds(application.getNetprcds());
//					loanform.setTermcycle(application.getTermcyc());
//					loanform.setIntcycle(application.getIntpycyc());
//					loanform = calcSrvc.computeLoan(loanform);
//					for(PaymentScheduleForm amort: loanform.getPaymentsched()){
//						if(amort.getTransType().equals("0")){
//							application.setUidBal(amort.getIntEy());
//							dbSrvc.saveOrUpdate(application);
//						}
//						amortsched.setAccountno(accountno);
//						amortsched.setIlamt(amort.getTransAmount());
//						amortsched.setIlduedt(amort.getTransDate());
//						amortsched.setIlint(amort.getIntEy());
//						amortsched.setLoanbal(amort.getLoanbal());
//						amortsched.setIlno(amort.getTransType().equals("Booking")?0:Integer.valueOf(amort.getTransType()));
//						amortsched.setIlprin(amort.getPrincipal());
//						amortsched.setPrinbal(amort.getPrinbal());
//						amortsched.setUidbal(amort.getUidey());
//						amortsched.setIlintrate(BigDecimal.valueOf(application.getNominal()));
//						amortsched.setIlrppd(BigDecimal.ZERO);
//						amortsched.setIltax(BigDecimal.ZERO);
//						amortsched.setIltax(BigDecimal.ZERO);
//						amortsched.setIsPaid(false);
//						amortsched.setLoanno(accountno);
//						amortsched.setTxmkr(UserUtil.securityService.getUserName());
//						amortsched.setTxoff("");
//						amortsched.setDaysdiff(amort.getDaysdiff().intValue());
//						dbSrvc.save(amortsched);
//					}
//					dbSrvc.executeUpdate("UPDATE Tbpaysched SET accountno =:acctno WHERE accountno=:txrefno", params);
//					application.setFduedt(loanform.getFirstbookingdate());
//					application.setMatdt(loanform.getMaturitydate());
//					application.setUidBal(loanform.getBkintey());
//					application.setAmortfee(loanform.getAmortfee());
//					application.setEir(loanform.getEir());
//					application.setEyrate(BigDecimal.valueOf(loanform.getEyrate()));
//					application.setAer(BigDecimal.valueOf(loanform.getEyrate())); noinfo
					List<Tbpaysched> paysched = (List<Tbpaysched>) dbSrvc.executeListHQLQuery("FROM Tbpaysched WHERE accountno=:acctno", params);
					account.setAccountno(accountno);
					account.setAccrtype(application.getAccrtype());
					account.setAcctsts(1);
					account.setAcctstsDate(new Date());
					account.setAir(BigDecimal.ZERO);
					account.setAer(BigDecimal.ZERO);// noinfo application.getAer());
					account.setAmortizationAmt(application.getAmortfee());
					account.setApplno(application.getApplno());
					account.setAr1(BigDecimal.ZERO);//prdmtrx.getIndPfdeduct() == null || prdmtrx.getIndPfdeduct() ?BigDecimal.ZERO:prdmtrx.getProcFee());
					account.setAr2(BigDecimal.ZERO);
					account.setAr1esc(""); // noinfo prdmtrx.getIndPfdeduct()?"":"1");
					account.setAr2esc("");
					account.setBtdprinpd(BigDecimal.ZERO);
					account.setBtdintpd(BigDecimal.ZERO);
					account.setChk4clr(BigDecimal.ZERO);
					account.setCollAgency("");
					account.setCollCollector("");
					account.setCollZone("");
					account.setCoMaker1("");// noinfo application.getc application.getComaker1empId() == null?"":application.getComaker1empId());
					account.setCoMaker2("");// noinfo application.getComaker2empId() == null?"":application.getComaker2empId());
					account.setDdlq(0);
					account.setDdlqBucket(0);
					account.setDdlqBucketPrev(0);
					account.setDlylpc(BigDecimal.ZERO);
					account.setDtbook(prcdate.getCurrentbusinessdate());
					account.setFaceamt(application.getFaceamt());
					account.setFactorRate(application.getEir());
					account.setFduedt(application.getFduedt());
					account.setEffyield(application.getEyrate());
					account.setEir(application.getEir());
					account.setExcessbal(BigDecimal.ZERO);
					account.setGraceprd(2);
					account.setIdealloanbal(account.getFaceamt());
					account.setIdealprinbal(application.getFaceamt());
					account.setIenc(BigDecimal.ZERO);
					account.setIltogo(application.getPpynum());
					account.setInterestAmt(application.getUidBal());
					account.setInttyp("4");
					account.setLegbranch(application.getTxbranch());
					account.setLegveh(application.getLegveh());
					account.setLoanbal(application.getPnamt());
					account.setLoanoff(application.getLoanoff());
					account.setLoanpur(application.getLoanpur());
					account.setLoantype(application.getProduct());
					account.setLpcbal(BigDecimal.ZERO);
					account.setLpcrate(application.getLpcrate());
					account.setLpdduedt(null);
					account.setLpdilno(0);
					account.setLstaccrdt(null);
					account.setLstpyamt(BigDecimal.ZERO);
					account.setLstpydt(null);
					account.setLsttxcod("");
					account.setLsttxdt(null);
					account.setMatdt(application.getMatdt());
					account.setNxtduedt(application.getFduedt());
					account.setNxtilamt(application.getAmortfee());
					account.setNxtilno(1);
					account.setNxtintamt(paysched.isEmpty()?BigDecimal.ZERO:paysched.get(1).getIlint());
					account.setNxtprinamt(paysched.isEmpty()?BigDecimal.ZERO:paysched.get(1).getIlprin());
					account.setNxtdueilno(1);
					account.setNxtdueilamt(application.getAmortfee());
					account.setNxtdueilduedt(application.getFduedt());
					account.setNxtdueilprin(paysched.isEmpty()?BigDecimal.ZERO:paysched.get(1).getIlprin());
					account.setNxtdueilint(paysched.isEmpty()?BigDecimal.ZERO:paysched.get(1).getIlint());
					account.setOuid(application.getUidBal());
					account.setPartialint(BigDecimal.ZERO);
					account.setPartialprin(BigDecimal.ZERO);
					account.setPaymentCycle(Integer.valueOf(application.getPpycyc()));
					account.setPdcctr(0);
					account.setPnamt(application.getPnamt());
					account.setPnilno(application.getPpynum());
					account.setPnilnocyc(Integer.valueOf(application.getPpycyc()));
					account.setPnintmethod("");
					account.setPnintrate(application.getNominal());
					account.setPnno(application.getPpynum().toString());
					account.setPnterm(BigDecimal.valueOf(application.getTerm()));
					account.setPntermcyc(application.getPpycyc().toString());
					account.setPrinbal(application.getFaceamt());
					account.setPrincipalNo(application.getClientid());
					account.setProdcode(application.getProduct());
					account.setProductGroup(application.getProductGroup());
					account.setProdPriorityCode(0);
					account.setRemedsts(0);
					account.setReviewdate(null);
					account.setReviewdays(0);
					account.setSeccode("0");
					account.setSlaidno(application.getClientid());
//					account.setSubprd1(Integer.valueOf(application.getSubprd()));
					//account.setSubprd2(0);
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
					account.setUiadv(BigDecimal.ZERO);
					account.setUiadvno(0);
					account.setUidbal(application.getUidBal());
					account.setUnpaidint(BigDecimal.ZERO);
					account.setUnpaidprin(BigDecimal.ZERO);
					account.setXpd1(0);
					account.setXpd30(0);
					account.setXpd60(0);
					account.setXpd90(0);
					account.setYtdprinpd(BigDecimal.ZERO);
					account.setYtdintpd(BigDecimal.ZERO);
					account.setFullname(row.getName());
					account.setRefno(row.getApplno());
					account.setFacilityid("");
					account.setCurrdailyint(account.getNxtdueilint());
					account.setCurruidamt(account.getNxtdueilint());
//					account.setCurruidctr(account.getDtbook());
					account.setMemint(BigDecimal.ZERO);
					account.setMtdint(BigDecimal.ZERO);
					account.setIntpaytype(application.getIntpytype());
					account.setIntpycomp("");//noinfo
					dbSrvc.save(account);
					Tblntxjrnl lntxjrnl = INITUtil.initialLoanTxJournal(account, DateTimeUtil.convertDateToString(account.getDtbook(), DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
					lntxjrnl.setTxcode("10");
					lntxjrnl.setTxvaldt(null);
					lntxjrnl.setDuedtpd(null);
					lntxjrnl.setTxlpcprin(account.getLpcbal());
					lntxjrnl.setTxint(account.getUidbal());
					lntxjrnl.setTxprin(account.getPrinbal());
					lntxjrnl.setTxloanbal(account.getLoanbal());
					lntxjrnl.setTxuidb(account.getUidbal());
					lntxjrnl.setTxprinbal(account.getPrinbal());
					lntxjrnl.setTxar1(account.getAr1());
					lntxjrnl.setTxaresc1(account.getAr1esc());
					lntxjrnl.setTxamt(application.getNetprcds());
					lntxjrnl.setReason("4");
					lntxjrnl.setTxmode("4");
					lntxjrnl.setTxseqno(application.getApplno());
					dbSrvc.save(lntxjrnl);
					//					if(!application.getTxmode().equals("9"))
					//						inputCreditEntry(application, application.getTxcode());
					//					offsets = executeListHQLQuery("FROM Tbloanoffset where txrefno =:txrefno and (pretermbymember=true OR pretermbysla=true)", params);
					//					if(offsets.isEmpty() || offsets == null){
					////						System.out.println("No offsets.");
					//					}else{
					//						for(Tbloanoffset offset: offsets){
					//							params.put("acctno1", offset.getAcctno());
					//							account = new Tbloans();
					//							lntxjrnl = new Tblntxjrnl();
					//							account = (Tbloans) executeUniqueHQLQuery("FROM Tbloans WHERE accountno =:acctno1", params);
					//							payment.setTxBatch(account.getLegbranch());
					//							payment.setTxoper(2);
					//							payment.setAccountno(offset.getAcctno());
					//							payment.setCreatedBy(application.getApprovedBy());
					//							payment.setCreationDate(new Date());
					//							payment.setApprovalDate(new Date());
					//							payment.setApprovedBy(application.getApprovedBy());
					//							payment.setEmployeeNo(account.getPrincipalNo());
					//							payment.setSlaidNo(account.getSlaidno());
					//							payment.setFirstname(application.getFirstName());
					//							payment.setMiddlename(application.getMiddleName());
					//							payment.setLastname(application.getLastname());
					//							payment.setTxAmount(offset.getOutstandingbal().add(account.getLpcbal().add(account.getAr1())).add(account.getSubprd1()==59001?offset.getAmttoupdate():BigDecimal.ZERO));
					//							payment.setParticulars("Pre-terminated");
					//							payment.setReason("4");
					//							payment.setTxAmtBal(BigDecimal.ZERO);
					//							payment.setTxcode("232");
					//							payment.setTxdate(new Date());
					//							payment.setTxInt(BigDecimal.ZERO);
					//							payment.setTxLpc(BigDecimal.ZERO);
					//							payment.setTxMisc(BigDecimal.ZERO);
					//							payment.setTxmode("2");
					//							payment.setTxPrin(BigDecimal.ZERO);
					//							payment.setTxRefNo(application.getTxRefNo());
					//							payment.setTxStatus("P");
					//							payment.setTxStatusDate(new Date());
					//							payment.setTxvaldt(new Date());
					//							payment.setFlag(0);
					//							save(payment);
					//							account.setOldsts(account.getAcctsts());
					//							account.setAcctsts(9);
					//							lntxjrnl = INITUtil.initialLoanTxJournal(account, DateTimeUtil.strRunDateEndDate(account.getDtbook()), member);
					//							lntxjrnl.setTxcode(payment.getTxcode());
					//							lntxjrnl.setIlnopd(Integer.valueOf(account.getPnno()));
					//							lntxjrnl.setTxvaldt(new Date());
					//							lntxjrnl.setTxdate(new Date());
					//							lntxjrnl.setDuedtpd(account.getMatdt());
					//							lntxjrnl.setTxlpcprin(account.getLpcbal());
					//							lntxjrnl.setTxint(account.getSubprd1()==59001?offset.getAmttoupdate():account.getUidbal());
					//							lntxjrnl.setTxprin(account.getPrinbal());
					//							lntxjrnl.setTxloanbal(BigDecimal.ZERO);
					//							lntxjrnl.setTxuidb(BigDecimal.ZERO);
					//							lntxjrnl.setTxprinbal(BigDecimal.ZERO);
					//							lntxjrnl.setTxar1(account.getAr1());
					//							lntxjrnl.setTxaresc1(account.getAr1esc());
					//							lntxjrnl.setTxamt(payment.getTxAmount());
					//							lntxjrnl.setReason("4");
					//							lntxjrnl.setTxmode("2");
					//							lntxjrnl.setTxseqno(application.getTxRefNo());
					//							lntxjrnl.setTxacctsts("9");
					//							lntxjrnl.setOldstat(account.getAcctsts().toString());
					//							lntxjrnl.setTxchkno(account.getSlaidno());
					//							save(lntxjrnl);
					//							account.setLoanbal(BigDecimal.ZERO);
					//							account.setLpcbal(BigDecimal.ZERO);
					//							account.setPrinbal(BigDecimal.ZERO);
					//							account.setUidbal(BigDecimal.ZERO);
					//							account.setUnpaidInt(BigDecimal.ZERO);
					//							account.setUnpaidPrin(BigDecimal.ZERO);
					//							account.setTpdilamt(account.getIdealloanbal());
					//							account.setTpdilno(Integer.valueOf(account.getPnno()));
					//							account.setTpdprinamt(account.getIdealprinbal());
					//							account.setTpdintamt(account.getInterestAmt());
					//							account.setIltogo(0);
					//							account.setPartialInt(BigDecimal.ZERO);
					//							account.setPartialPrin(BigDecimal.ZERO);
					//							account.setAcctstsDate(new Date());
					//							saveOrUpdate(account);
					//						}
					//					}
					application.setTxstat("10");
//					application.setTx("TRANSACTION IS MOVED FOR CREDITED/RELEASE & BOOKED");
					dbSrvc.saveOrUpdate(application);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
//						log.setCurrentdate(prcdate.getStartdate());
//						log.setNextdate(prcdate.getEnddate());
//						log.setEventdate(new Date());
//						log.setEventname("ERROR");
//						log.setDescription("Class : " + e.getStackTrace()[0].getClassName() + " Method : " + e.getStackTrace()[0].getMethodName() + " Line : " + e.getStackTrace()[0].getLineNumber());
//						dbSrvc.save(log);
						result = "Loan Booking Failed.";
						application.setTxstat("D");
//						application.setTxRemarks("System Error.");
						dbSrvc.executeHQLUpdate("DELETE FROM Tbloans WHERE accountno=:acctno", params);
						dbSrvc.executeHQLUpdate("DELETE FROM Tblntxjrnl WHERE accountno=:acctno", params);
						dbSrvc.executeHQLUpdate("UPDATE Tbpaysched SET accountno=:txrefno WHERE accountno=:acctno", params);
//						dbSrvc.executeHQLUpdate("DELETE FROM Tbcredit WHERE txrefno=:txrefno", params);
						dbSrvc.saveOrUpdate(application);
					}
				}
			}else{
				result = "No Data to process.";
				log.setCurrentdate(prcdate.getCurrentbusinessdate());
				log.setNextdate(prcdate.getNextbusinessdate());
				log.setEventdate(new Date());
				log.setEventname("ERROR");
				log.setDescription("No Data to process.");
				log.setUniquekey("");
				dbSrvc.save(log);
			}
		System.out.println(">> EOD PROCESS : END LOAN BOOKING "+new Date()+" >>");
		log.setCurrentdate(prcdate.getCurrentbusinessdate());
		log.setNextdate(prcdate.getNextbusinessdate());
		log.setModulename("CHECK CLEARING");
		log.setEventdate(new Date());
		log.setEventname("END");
		log.setUniquekey("");
		log.setDescription(String.valueOf(count) + " loans Booked.");
		dbSrvc.save(log);
		return result;
	}


}
