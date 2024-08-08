package com.etel.eod;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbaccountinfo;
import com.coopdb.data.Tbbranch;
import com.coopdb.data.Tbchecksforclearing;
import com.coopdb.data.Tblmslogs;
import com.coopdb.data.Tblntxjrnl;
import com.coopdb.data.Tbloanfin;
import com.coopdb.data.Tbloanproduct;
import com.coopdb.data.Tbloans;
//import com.coopdb.data.Tblogs;
import com.coopdb.data.Tbpaymentsched;
import com.coopdb.data.TbpaymentschedId;
import com.coopdb.data.Tbpaysched;
//import com.coopdb.data.Tbprocessingdate;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
//import com.etel.common.service.DBServiceImplLOS;
import com.etel.lms.forms.LoanAccountForm;
import com.etel.lms.forms.LoanTransactionForm;
import com.etel.util.INITUtil;
import com.etel.utils.DateTimeUtil;
import com.etel.utils.HQLUtil;
//import com.loansdb.data.Tbaccountinfo;
//import com.loansdb.data.Tbapprovedcf;
//import com.loansdb.data.Tblntxjrnl;
//import com.loansdb.data.Tbloanproduct;
//import com.loansdb.data.Tbloans;
//import com.loansdb.data.Tblogs;
//
//import com.loansdb.data.Tbpaymentsched;
//import com.loansdb.data.TbpaymentschedId;
//import com.loansdb.data.Tbprocessingdate;
//import com.loansdb.data.Tbpaysched;

@SuppressWarnings("unchecked")
public class EODServiceImpl implements EODService {

	private static DBService dbSrvc = new DBServiceImpl();
	private static Map<String, Object> params = HQLUtil.getMap();
	private static Tblmslogs log = new Tblmslogs();
	private static Tbbranch prcdate = (Tbbranch) dbSrvc.executeUniqueHQLQuery("FROM Tbbranch WHERE branchcode='001'", null);

	private static Tbloanfin loanfin = new Tbloanfin();
	private static Tbloans loans = new Tbloans();
	private static Tblntxjrnl jrnl = new Tblntxjrnl();
	public String loanBooking(List<LoanAccountForm> newloans) {
		// TODO Auto-generated method stub
		
		log.setAfter(DateTimeUtil.convertDateToString(prcdate.getNextbusinessdate(), DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
		log.setAmount(BigDecimal.ZERO);
		log.setBefore(DateTimeUtil.convertDateToString(prcdate.getCurrentbusinessdate(), DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
		System.out.println(">> EOD PROCESS : START LOAN BOOKING "+new Date()+" >>");
		log.setFiletype("EOD Loan Booking");
		log.setTimestamp(new Date());
		log.setChangetype("START");
		log.setFilename("");
		log.setRowId("");
		log.setReason("Start");
		dbSrvc.save(log);
		String result = "Failed";
		Tbloanproduct prdmtrx = new Tbloanproduct();
		Tbloans account = new Tbloans();
		Tbaccountinfo application = new Tbaccountinfo();
		String accountno = "";
		String txrefno = "";
		
		int count = 0;
		
		if(newloans.size() > 0) {
			for(LoanAccountForm row: newloans) {
				try {
//				Tblmslogs bookinglogs = new Tblmslogs();
//				bookinglogs.setAfter(DateTimeUtil.convertDateToString(prcdate.getNextbusinessdate(), DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
//				bookinglogs.setAmount(row.getLoanamount());
//				bookinglogs.setBefore(DateTimeUtil.convertDateToString(prcdate.getCurrentbusinessdate(), DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
//				bookinglogs.setFiletype("Loan Booking - " + row.getPnno());
//				bookinglogs.setTimestamp(new Date());
//				bookinglogs.setChangetype("START");
//				bookinglogs.setFilename("");
//				bookinglogs.setRowId(row.getPnno());
//				bookinglogs.setReason("Start");
//				dbSrvc.save(bookinglogs);
				//bookinglogs.setre
				count++;
				txrefno = row.getApplno();
				System.out.println(txrefno);
				params.put("txrefno", row.getApplno());
				application = (Tbaccountinfo) dbSrvc.executeUniqueHQLQuery("FROM Tbaccountinfo WHERE applno=:txrefno", params);
				accountno ="";
				params.put("txrefno", application.getApplno());
				params.put("subprd", application.getProduct());
				//get product info
				prdmtrx = (Tbloanproduct) dbSrvc.executeUniqueHQLQuery("FROM Tbloanproduct WHERE productcode =:subprd", params);
				accountno = HQLUtil.generateAccountNo(prdmtrx.getProductcode());
				params.put("acctno", accountno);
				dbSrvc.executeUpdate("UPDATE Tbpaysched SET accountno =:acctno WHERE applno=:txrefno", params);
				//copy tbpaysched to tbpaymentsched
				List<Tbpaysched> paysched = new ArrayList<Tbpaysched>();
		
				paysched = (List<Tbpaysched>) dbSrvc.executeListHQLQuery("FROM Tbpaysched WHERE applno=:txrefno", params);
				System.out.println(paysched.size());
				
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
					newpaysched.setUidbal(payment.getUidbal());
					dbSrvc.saveOrUpdate(newpaysched);
					
				}
				
				Tbpaymentsched nxtpaysched = (Tbpaymentsched) dbSrvc.executeUniqueHQLQuery("FROM Tbpaymentsched WHERE accountno=:acctno AND ilno=1",  params);
				account.setAccountno(accountno);
				account.setAccrtype(application.getAccrtype());
				account.setAcctsts(1);
				account.setAcctstsDate(new Date());
				account.setAir(application.getAir());
				account.setAmortizationAmt(application.getAmortfee());
				account.setApplno(application.getApplno());
				account.setAr1(BigDecimal.ZERO);
				account.setAr2(BigDecimal.ZERO);
				account.setAr1esc("");// fix
				account.setAr2esc("");
				account.setBtdprinpd(BigDecimal.ZERO);
				account.setBtdintpd(BigDecimal.ZERO);
				account.setChk4clr(BigDecimal.valueOf(application.getPdcctr()));
				account.setCollAgency("");
				account.setCollCollector("");
				account.setCollZone("");
				account.setDdlq(0);
				account.setDdlqBucket(0);
				account.setDdlqBucketPrev(0);
				account.setDlylpc(BigDecimal.ZERO);
				account.setDtbook(application.getDtbook());
				account.setFaceamt(application.getFaceamt().add(application.getIntchargeamt()));
				account.setFactorRate(application.getEir());
				//account.setEmployeeno(application.getem);
				account.setFduedt(application.getFduedt());
				account.setEffyield(application.getEyrate());
				account.setEir(application.getEir());
				account.setExcessbal(BigDecimal.ZERO);
				account.setGraceprd(0);
				account.setIdealloanbal(account.getFaceamt());
				account.setIdealprinbal(application.getFaceamt());
				account.setIenc(BigDecimal.ZERO);
				account.setIltogo(application.getPpynum());
				account.setInterestAmt(application.getInterestdue());
				account.setInttyp(application.getIntcompmethod());
				account.setIntpaytype(application.getIntpytype());
				account.setLegbranch(application.getOrigbr());
				account.setLegveh(application.getApprbr());
				account.setLoanbal(application.getFaceamt());
				account.setLoanoff(application.getLoanoff());
				account.setLoanpur(application.getLoanpur());
				account.setLoantype(application.getProduct());
				account.setLpcbal(BigDecimal.ZERO);
				account.setLpcrate(BigDecimal.valueOf(1D));
				account.setLpdduedt(null);
				account.setLpdilno(0);
				account.setLstaccrdt(null);
				account.setLstpyamt(BigDecimal.ZERO);
				//account.setLstpydt(application.getDtbook());
				account.setLsttxcod("10");
				account.setLsttxdt(new Date());
				account.setMatdt(application.getMatdt());
				account.setNxtduedt(application.getFduedt());
				account.setNxtilamt(nxtpaysched.getIlamt());
				account.setNxtilno(1);
				account.setNxtintamt(nxtpaysched==null?BigDecimal.ZERO:nxtpaysched.getIlint());
				account.setNxtprinamt(nxtpaysched==null?BigDecimal.ZERO:nxtpaysched.getIlint());
				account.setNxtdueilno(1);
				account.setNxtdueilamt(application.getAmortfee());
				account.setNxtdueilduedt(application.getFduedt());
				account.setNxtdueilprin(paysched.isEmpty()?BigDecimal.ZERO:paysched.get(0).getIlprin());
				account.setNxtdueilint(paysched.isEmpty()?BigDecimal.ZERO:paysched.get(0).getIlint());
				account.setOuid(application.getIntchargeamt());
				account.setPartialint(BigDecimal.ZERO);
				account.setPartialprin(BigDecimal.ZERO);
				account.setPaymentCycle(Integer.valueOf(application.getPrinpaycycle()));
				account.setPdcctr(application.getPdcctr());
				account.setPnamt(application.getPnamt());
				account.setPnilno(application.getPpynum());
				account.setPnilnocyc(Integer.valueOf(application.getPrinpaycycle()));
				account.setPnintmethod("");
				account.setPnintrate(application.getNominal());
				account.setPnno(application.getPnno());
				account.setPnterm(BigDecimal.valueOf(application.getTerm()));
				account.setPntermcyc(account.getPaymentCycle().toString());
				account.setPrinbal(application.getFaceamt());
				account.setPrincipalNo(application.getClientid());
				account.setProdcode(application.getProduct());
				account.setProductGroup(application.getProductGroup());
				account.setSlaidno(application.getClientid());
			
				/// stop here
				
				account.setRemedsts(0);  // is this repricing status?
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
				account.setUidbal(application.getUidBal());
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
				account.setCurruidamt(nxtpaysched.getIlint());
				Calendar dueDate = Calendar.getInstance();
				dueDate.setTime(account.getNxtduedt());
				Calendar bookDate = Calendar.getInstance();
				bookDate.setTime(account.getDtbook());
				account.setCurruidctr(BigDecimal.valueOf(DateTimeUtil.daysdiffV2(account.getDtbook(), account.getNxtduedt())));
				account.setCurrdailyint(account.getCurruidamt().divide(account.getCurruidctr(),2, RoundingMode.HALF_EVEN));
				account.setMemint(BigDecimal.ZERO);
				account.setMtdint(BigDecimal.ZERO);
				account.setIntpycomp(application.getInttyp());
				account.setCfrefno1(application.getCfrefno1());
				account.setCfrefno2(application.getCfrefno2());
				account.setIntcycdesc(application.getIntcycdesc());
				account.setTermcycdesc(application.getTermcycdesc());
				dbSrvc.save(account);
				
				
				application.setLoanno(accountno);
				dbSrvc.update(application);
				
				// update credit line info if COmmercial
				
//				if(application.getCfrefno1() != null) {
//					
//					params.put("cfrefno1", application.getCfrefno1());
//					Tbapprovedcf cfdetails1 = (Tbapprovedcf) dbSrvc.executeUniqueHQLQuery("FROM Tbapprovedcf where cfrefnoconcat=:cfrefno1", params);
//					
//					cfdetails1.setCfavailed(cfdetails1.getCfavailed().add(application.getPnamt()));
//					cfdetails1.setCfbalance(cfdetails1.getCfbalance().subtract(application.getPnamt()));
//					cfdetails1.setCfearmarked(cfdetails1.getCfearmarked().subtract(application.getPnamt()));
//					dbSrvc.update(cfdetails1);
//					if(application.getCfrefno2() != null) {
//						params.put("cfrefno2", application.getCfrefno2());
//						Tbapprovedcf cfdetails2 = (Tbapprovedcf) dbSrvc.executeUniqueHQLQuery("FROM Tbapprovedcf where cfrefnoconcat=:cfrefno2", params);
//						
//						cfdetails2.setCfavailed(cfdetails2.getCfavailed().add(application.getPnamt()));
//						cfdetails2.setCfbalance(cfdetails2.getCfbalance().subtract(application.getPnamt()));
//						cfdetails2.setCfearmarked(cfdetails2.getCfearmarked().subtract(application.getPnamt()));
//						dbSrvc.update(cfdetails2);
//					}
//					
//				}
				
				// add transaction journal
				
				Tblntxjrnl lntxjrnl = INITUtil.initialLoanTxJournal(account, DateTimeUtil.convertDateToString(account.getDtbook(), DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
				lntxjrnl.setTxcode("10");
				lntxjrnl.setTxvaldt(application.getTxvaldt());
				lntxjrnl.setDuedtpd(null);
				lntxjrnl.setTxlpcprin(BigDecimal.ZERO);
				lntxjrnl.setTxint(account.getInterestAmt());
				lntxjrnl.setTxprin(account.getPrinbal());
				lntxjrnl.setTxloanbal(account.getLoanbal());
				lntxjrnl.setTxuidb(account.getUidbal());
				lntxjrnl.setTxprinbal(account.getPrinbal());
				lntxjrnl.setTxar1(BigDecimal.ZERO);
				lntxjrnl.setTxaresc1(null);
				lntxjrnl.setTxamt(application.getAmtfinance()); // fix netproceeds
				lntxjrnl.setReason("LOS");
				lntxjrnl.setTxmode("9");
				lntxjrnl.setTxseqno(String.valueOf(1));
				lntxjrnl.setTxrefno(HQLUtil.generateLMSTransactionRefNoDate());
				lntxjrnl.setTxdate(new Date());
				dbSrvc.save(lntxjrnl);
				application.setTxstat("10");
				dbSrvc.saveOrUpdate(application);
				result = "Success";
				} catch (Exception e) {
					e.printStackTrace();
					log.setRowId(application.getPnno());
					log.setReason("Class : " + e.getStackTrace()[0].getClassName() + " Method : " + e.getStackTrace()[0].getMethodName() + " Line : " + e.getStackTrace()[0].getLineNumber());
					log.setTimestamp(new Date());
					log.setChangetype("ERROR");
					dbSrvc.save(log);
					result = "Failed";
					application.setTxstat("9");
					dbSrvc.saveOrUpdate(application);
					dbSrvc.executeHQLUpdate("DELETE FROM Tbloans WHERE accountno=:acctno", params);
					dbSrvc.executeHQLUpdate("DELETE FROM Tblntxjrnl WHERE accountno=:acctno", params);
					dbSrvc.executeHQLUpdate("DELETE FROM Tbpaymentsched WHERE accountno=:acctno", params);
				}
			}
		}
				
		return result;
	}

	@Override
	public String generatePaysched(List<String> pns) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String transactionPosting(List<LoanTransactionForm> finList) {
		// TODO Auto-generated method stub
		String flag = "failed";
		prcdate = (Tbbranch) dbSrvc.executeUniqueHQLQuery("FROM Tbbranch WHERE id = '001'", params);
		System.out.println(">> START LOAN POSTING "+new Date()+" >>");	
//		log.setCurrentdate(prcdate.getStartdate());
//		log.setNextdate(prcdate.getEnddate());
//		log.setModulename("LOAN POSTING");
//		log.setEventdate(new Date());
//		log.setEventname("START");
//		log.setDescription("Start of Loan Posting");
//		log.setUniquekey("");
//		dbSrvc.save(log);
		//		List<Tbloanfin> finList = new ArrayList<Tbloanfin>();
		String runDate = DateTimeUtil.convertDateToString(prcdate.getCurrentbusinessdate(), DateTimeUtil.DATE_FORMAT_MM_DD_YYYY);
		Date dueDt = DateTimeUtil.getDueDate(DateTimeUtil.convertToDate(runDate, DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
		params.put("dueDt", dueDt);
		params.put("dt", runDate);
		int count = 0;
		
		for(LoanTransactionForm row: finList) {
			try {
				count++;
				System.out.println("acctno >>> " + row.getAccountno());
				params.put("acctno", row.getAccountno());
				
				loanfin = (Tbloanfin) dbSrvc.executeUniqueHQLQuery("FROM Tbloanfin WHERE accountno=:acctno", params);
				params.put("empno", loanfin.getCifno());
				
				System.out.println("acctno >>> " + loanfin.getAccountno());
				System.out.println("empno >>> " + loanfin.getCifno());
				if(loanfin.getTxrefno().equals(null) || loanfin.getTxrefno().equals("") || loanfin.getTxrefno().isEmpty()) {
					loanfin.setTxrefno(HQLUtil.generateLMSTransactionRefNoDate());
				}
				loans = (Tbloans) dbSrvc.executeUniqueHQLQuery("FROM Tbloans WHERE accountno =:acctno", params);
//				member = (Tbmember) dbSrvc.executeUniqueHQLQuery("FROM Tbmember WHERE employeeno =:empno AND slastatus = 'A'", params);
				jrnl = new Tblntxjrnl();
				jrnl = INITUtil.initialLoanTxJournal(loans, runDate);
				List<Tblntxjrnl> txlist = (List<Tblntxjrnl>) dbSrvc.executeListHQLQuery("FROM Tblntxjrnl WHERE accountno=:acctno", params);
				jrnl.setTxseqno(String.valueOf(txlist.size() + 1));
				jrnl.setTxcode(loanfin.getTxcode());
				jrnl.setTxvaldt(loanfin.getTxvaldt());
				if(loanfin.getTxcode().equals("212") || loanfin.getTxcode().equals("222") || loanfin.getTxcode().equals("40")) {
					applyPayment();
					creditTableUpdate();
					commonTableUpdate();
				} else if(loanfin.getTxcode().equals("232") || loanfin.getTxcode().equals("50")) {
					applyCredit();
					creditTableUpdate();
					commonTableUpdate();
				} else if(loanfin.getTxcode().equals("241") || loanfin.getTxcode().equals("20")){
					applyDebit();
					debitTableUpdate();
					commonTableUpdate();
				} else if(loanfin.getTxcode().equals("251") || loanfin.getTxcode().equals("30")) {
					applyDebit();
					debitTableUpdate();
					commonTableUpdate();
				} else {
					System.out.println("Invalid TXCODE");
				}
			}catch (Exception e) {

//				log.setCurrentdate(prcdate.getStartdate());
//				log.setNextdate(prcdate.getEnddate());
//				log.setEventdate(new Date());
//				log.setEventname("ERROR");
//				log.setUniquekey(row.getTxRefNo()==null?row.getId().toString():row.getTxRefNo());
//				log.setDescription("Class : " + e.getStackTrace()[0].getClassName() + " Method : " + e.getStackTrace()[0].getMethodName() + " Line : " + e.getStackTrace()[0].getLineNumber());
//				dbSrvc.save(log);
				e.printStackTrace();
			}
		}
//		log.setCurrentdate(prcdate.getStartdate());
//		log.setNextdate(prcdate.getEnddate());
//		log.setEventdate(new Date());
//		log.setEventname("END");
//		log.setUniquekey("");
//		log.setDescription(String.valueOf(count) +  " transactions posted.");
//		dbSrvc.save(log);
		flag = "success";
		System.out.println(String.valueOf(count) +  " transactions posted.");
		System.out.println(">> EOD PROCESS : END LOAN POSTING"+new Date()+" >>");
		return flag;
	}
	
	public void applyPayment() {
		
		try {
			if(loans.getAr1().compareTo(BigDecimal.ZERO)==1) {
				if(loanfin.getTxamtbal().compareTo(loans.getAr1())==1) {
					jrnl.setTxar1(loans.getAr1());
					loanfin.setTxamtbal(loanfin.getTxamount().subtract(jrnl.getTxar1()));
					loans.setAr1(BigDecimal.ZERO);
				}else {
					jrnl.setTxar1(loanfin.getTxamtbal());
					loanfin.setTxamtbal(BigDecimal.ZERO);
					loans.setAr1(loans.getAr1().subtract(jrnl.getTxar1()));
				}
			}
			if(loans.getLpcbal().compareTo(BigDecimal.ZERO)==1) {
				if(loanfin.getTxamtbal().compareTo(loans.getLpcbal())==1) {
					jrnl.setTxlpcprin(loans.getLpcbal());
					loanfin.setTxamtbal(loanfin.getTxamount().subtract(jrnl.getTxlpcprin()));
					loans.setLpcbal(BigDecimal.ZERO);
				} else {
					jrnl.setTxlpcprin(loanfin.getTxamtbal());
					loanfin.setTxamtbal(BigDecimal.ZERO);
					loans.setLpcbal(loans.getLpcbal().subtract(jrnl.getTxlpcprin()));
				}
			}
//			System.out.println(loanfin.toString());
//			System.out.println(loans.toString());
//			System.out.println(jrnl.toString());
			//compute ilno () until txamtbal > 0
			while(loanfin.getTxamtbal().compareTo(BigDecimal.ZERO)==1) {
				preComputedIlNoPaid();
			}
			if(loanfin.getTxmode().equals("1")) {
				loans.setChk4clr(loans.getChk4clr().add(BigDecimal.ONE));
				insertCheckForClearing();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void applyCredit() {
		try {
			if(loanfin.getTxmisc().compareTo(loans.getAr1())==1) {
				loanfin.setTxstatus("R");
				loanfin.setTxstatusdate(prcdate.getCurrentbusinessdate());
				loanfin.setParticulars("AR Overpayment");
				dbSrvc.saveOrUpdate(loanfin);
			} else {
				loanfin.setTxamtbal(loanfin.getTxamtbal().subtract(loanfin.getTxmisc()));
				loans.setAr1(loans.getAr1().subtract(loanfin.getTxmisc()));
				jrnl.setTxar1(loanfin.getTxmisc());
				loanfin.setTxmisc(BigDecimal.ZERO);
			}
			if (loanfin.getTxlpc().compareTo(loans.getLpcbal())==1) {
				loanfin.setTxstatus("R");
				loanfin.setTxstatusdate(prcdate.getCurrentbusinessdate());
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
			//compute ilno () until txamtbal > 0
			while(loanfin.getTxamtbal().compareTo(BigDecimal.ZERO)==1) {
				preComputedIlNoPaid();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void applyDebit() {
		try {
			if(loanfin.getTxmisc().compareTo(BigDecimal.ZERO)==1) {
				jrnl.setTxar1(loanfin.getTxmisc());
				loanfin.setTxamtbal(loanfin.getTxamtbal().subtract(loanfin.getTxmisc()));
				loans.setAr1(loans.getAr1().add(loanfin.getTxmisc()));
			}
			if(loanfin.getTxlpc().compareTo(BigDecimal.ZERO)==1) {
				jrnl.setTxlpcprin(loanfin.getTxlpc());
				loanfin.setTxamtbal(loanfin.getTxamtbal().subtract(loanfin.getTxlpc()));
				loans.setAr1(loans.getLpcbal().add(loanfin.getTxlpc()));
			}
			computIlNoReverse();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void computIlNoPaid() {
		try {
			if(loans.getInttyp().equals("4")) {
				preComputedIlNoPaid();
			}else if (loans.getInttyp().equals("1")) {
				preComputedIlNoPaid();
			}else {
				System.out.println("Invalid Inttype");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void computIlNoReverse() {
		try {
			if(loans.getInttyp().equals("4")) {
				if (loanfin.getTxamtbal().compareTo(BigDecimal.ZERO)==1 && loans.getPartialprin().compareTo(BigDecimal.ZERO)==1) {
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
				if (loanfin.getTxamtbal().compareTo(BigDecimal.ZERO)==1 && loans.getPartialint().compareTo(BigDecimal.ZERO)==1) {
					if (loanfin.getTxamtbal().compareTo(loans.getPartialint())>=0) {
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
				if(loanfin.getTxamtbal().compareTo(BigDecimal.ZERO)==1) {
					if(loans.getTpdilno()>0) {
						while(loanfin.getTxamtbal().compareTo(BigDecimal.ZERO)==1 && loans.getTpdilamt().compareTo(BigDecimal.ZERO)==1) {
							preComputedIlNoReverse();
						}
					} else {
						loanfin.setTxstatus("U");
						loanfin.setTxstatusdate(prcdate.getCurrentbusinessdate());
						loanfin.setParticulars("Over Reversal");
//						loanfin.setTxLpc(jrnl.getTxlpcprin());
//						loanfin.setTxMisc(jrnl.getTxar1());
//						loanfin.setTxAmtBal(loanfin.getTxAmount());
						dbSrvc.saveOrUpdate(loanfin);
						System.out.println("Over Reversal");
					}
				}
			}else if (loans.getInttyp().equals("1")) {
				preComputedIlNoReverse();
			}else {
				System.out.println("Invalid Inttype");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void preComputedIlNoPaid() {
		try {
			int wrkilno = loans.getTpdilno()+1;
			params.put("wrkilno", wrkilno);
			Tbpaymentsched sched = (Tbpaymentsched) dbSrvc.executeUniqueHQLQuery("FROM Tbpaymentsched WHERE accountno =:acctno and ilno=:wrkilno", params);
			if(loanfin.getTxamtbal().compareTo(BigDecimal.ZERO)==1 && wrkilno>loans.getTdueilno()+1) {
				loans.setExcessbal(loans.getExcessbal().add(loanfin.getTxamtbal()));
				jrnl.setTxexcess(loanfin.getTxamtbal());
				loanfin.setTxamtbal(BigDecimal.ZERO);
			}
			BigDecimal wrkInt = sched.getIlint().subtract(loans.getPartialint());
			if(loanfin.getTxamtbal().compareTo(wrkInt)==-1) {
				jrnl.setTxint(jrnl.getTxint().add(loanfin.getTxamtbal()));
				loans.setPartialint(loans.getPartialint().add(loanfin.getTxamtbal()));
				loanfin.setTxamtbal(BigDecimal.ZERO);
			}else {
				loanfin.setTxamtbal(loanfin.getTxamtbal().subtract(wrkInt));
				jrnl.setTxint(jrnl.getTxint().add(wrkInt));
				loans.setPartialint(sched.getIlint());
				BigDecimal wrkPrin = sched.getIlprin().subtract(loans.getPartialprin());
				if(loanfin.getTxamtbal().compareTo(wrkPrin)==-1) {
					jrnl.setTxprin(jrnl.getTxprin().add(loanfin.getTxamtbal()));
					loans.setPartialprin(loans.getPartialprin().add(loanfin.getTxamtbal()));
					loanfin.setTxamtbal(BigDecimal.ZERO);
				}else {
					loanfin.setTxamtbal(loanfin.getTxamtbal().subtract(wrkPrin));
					jrnl.setTxprin(jrnl.getTxprin().add(wrkPrin));
					loans.setPartialint(BigDecimal.ZERO);
					loans.setPartialprin(BigDecimal.ZERO);
					loans.setTpdilno(wrkilno);
					loans.setTpdilduedt(sched.getIlduedt());
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void preComputedIlNoReverse() {
		try {
			if(loanfin.getTxamtbal().compareTo(BigDecimal.ZERO)==1 && loans.getExcessbal().compareTo(BigDecimal.ZERO)==1) {
				if(loanfin.getTxamtbal().compareTo(loans.getExcessbal())==-1){
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
			Tbpaymentsched sched = (Tbpaymentsched) dbSrvc.executeUniqueHQLQuery("FROM Tbpaymentsched WHERE accountno =:acctno and ilno=:wrkilno", params);
			System.out.println(sched.getIlno() + " <<< ilno");
			loans.setPartialprin(sched.getIlprin());
			loans.setPartialint(sched.getIlint());
			if(loans.getPartialprin().compareTo(BigDecimal.ZERO)==1 && loans.getPartialint().compareTo(BigDecimal.ZERO)==1) {
				if (loanfin.getTxamtbal().compareTo(loans.getPartialprin())>=0) {
					loanfin.setTxamtbal(loanfin.getTxamtbal().subtract(loans.getPartialprin()));
					jrnl.setTxprin(jrnl.getTxprin().add(loans.getPartialprin()));
					loans.setPartialprin(BigDecimal.ZERO);
				} else {
					jrnl.setTxprin(jrnl.getTxprin().add(loanfin.getTxamtbal()));
					loans.setPartialprin(loans.getPartialprin().subtract(loanfin.getTxamtbal()));
					loanfin.setTxamtbal(BigDecimal.ZERO);
				}

				if (loanfin.getTxamtbal().compareTo(loans.getPartialint())>=0) {
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
			if(loans.getPartialprin().compareTo(sched.getIlprin())==-1) {
				loans.setTpdilno(loans.getTpdilno()-1);
				loans.setIltogo(loans.getIltogo()+1);
				if(loans.getTpdilno()==0) {
					loans.setTpdilduedt(null);
				} else {
					params.put("tpdilno", loans.getTpdilno());
					Tbpaymentsched paidSched =  (Tbpaymentsched) dbSrvc.executeUniqueHQLQuery("FROM Tbpaymentsched WHERE accountno =:acctno and ilno =:tpdilno", params);
					loans.setTpdilduedt(paidSched.getIlduedt());
				}
			}
			loans.setTpdilamt(loans.getTpdilamt().subtract(jrnl.getTxint().add(jrnl.getTxprin())));
			if(loans.getTpdilamt().compareTo(BigDecimal.ZERO)==0) {
				loanfin.setTxstatus("U");
				loanfin.setTxstatusdate(prcdate.getCurrentbusinessdate());
				loanfin.setParticulars("Over Reversal");
//				loanfin.setTxLpc(jrnl.getTxlpcprin());
//				loanfin.setTxMisc(jrnl.getTxar1());
//				loanfin.setTxAmtBal(loanfin.getTxAmount());
				dbSrvc.saveOrUpdate(loanfin);
				System.out.println("Over Reversal");
			}
		}catch (Exception e) {
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
			if(loans.getIntpaytype().equals("0")) {
				loans.setAir(loans.getAir().subtract(jrnl.getTxint()));
			} else {
				if(jrnl.getTxint().compareTo(loans.getAir())==1) {
					jrnl.setTxienc(jrnl.getTxint().subtract(loans.getAir()));
					loans.setAir(BigDecimal.ZERO);
					loans.setMtdint(loans.getMtdint().subtract(jrnl.getTxienc()));
				}
			}
			loans.setPrinbal(loans.getPrinbal().subtract(jrnl.getTxprin()));
			loans.setUidbal(loans.getUidbal().subtract(jrnl.getTxint())); // added 
			loans.setLoanbal(loans.getLoanbal().subtract(jrnl.getTxprin()));
			loans.setLoanbal(loans.getLoanbal().subtract(jrnl.getTxint()));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void debitTableUpdate() {
		try {
			loans.setTpdintamt(loans.getTpdintamt().subtract(jrnl.getTxint()));
//			loans.setTpdilamt(loans.getTpdilamt().subtract(jrnl.getTxint()));
			loans.setTpdprinamt(loans.getTpdprinamt().subtract(jrnl.getTxprin()));
//			loans.setTpdilamt(loans.getTpdilamt().subtract(jrnl.getTxprin())); // updated for while loop
			loans.setYtdintpd(loans.getYtdintpd().subtract(jrnl.getTxint()));
			loans.setBtdintpd(loans.getBtdintpd().subtract(jrnl.getTxint()));
			loans.setYtdprinpd(loans.getYtdprinpd().subtract(jrnl.getTxprin()));
			loans.setBtdprinpd(loans.getBtdprinpd().subtract(jrnl.getTxprin()));
			if(loans.getIntpaytype().equals("0")) {
				loans.setAir(loans.getAir().add(jrnl.getTxint()));
			}
			loans.setPrinbal(loans.getPrinbal().add(jrnl.getTxprin()));
			loans.setUidbal(loans.getUidbal().add(jrnl.getTxint()));
			loans.setLoanbal(loans.getLoanbal().add(jrnl.getTxprin()));
			loans.setLoanbal(loans.getLoanbal().add(jrnl.getTxint()));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void commonTableUpdate() {
		try {
			if(loans.getTdueilamt().compareTo(loans.getTpdilamt())==1) {
				loans.setUnpaidint(loans.getTdueintamt().subtract(loans.getTpdintamt()));
				loans.setUnpaidprin(loans.getTdueprinamt().subtract(loans.getTpdprinamt()));
			} else {
				loans.setUnpaidint(BigDecimal.ZERO);
				loans.setUnpaidprin(BigDecimal.ZERO);
			}
			loans.setIltogo(Integer.valueOf(loans.getPnilno()- loans.getTpdilno()));
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
			if(loans.getTpdilno()<Integer.valueOf(loans.getPnilno())) {
				params.put("nxtIlno", loans.getTpdilno()+1);
				Tbpaymentsched nxtsched = (Tbpaymentsched) dbSrvc.executeUniqueHQLQuery("FROM Tbpaymentsched WHERE accountno =:acctno AND ilno =:nxtIlno", params);
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
			jrnl.setTxuidb(loans.getUidbal());
			jrnl.setTxacctsts(loans.getAcctsts().toString());
			jrnl.setOldstat(loans.getOldsts()==null?null:loans.getOldsts().toString());
			jrnl.setTxexcessbal(loans.getExcessbal());
			if(loanfin.getTxstatus()!="6") {
				dbSrvc.saveOrUpdate(loans);
				dbSrvc.save(jrnl);
				if(loanfin.getTxamtbal().compareTo(BigDecimal.ZERO)==0) {
					loanfin.setTxstatus("10");
				}
				dbSrvc.saveOrUpdate(loanfin);
			}
		}catch (Exception e) {
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
			check.setChecktype(loanfin.getTxchecktype()==null?null:Integer.valueOf(loanfin.getTxchecktype()));
			Calendar cal = Calendar.getInstance();
			cal.setTime(loanfin.getTxvaldt());
			cal.add(Calendar.DAY_OF_MONTH, 5);
			check.setCheckdate(loanfin.getTxvaldt());
			check.setClearingdate(cal.getTime());
			check.setClearingdays(5);
			check.setStatus("1");
			if(dbSrvc.save(check)) {
				flag = "Success";
			}else {
				loanfin.setTxstatus("7");
				loanfin.setParticulars("Unable to save check details.");
				loanfin.setTxstatusdate(prcdate.getCurrentbusinessdate());
				dbSrvc.saveOrUpdate(loanfin);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}
}
