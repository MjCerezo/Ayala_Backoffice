/**
 * 
 */
package com.casa.eod;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbbranch;
import com.coopdb.data.Tbchecksforclearing;
import com.coopdb.data.Tblntxjrnl;
import com.coopdb.data.Tbloanfin;
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
public class LoanPosting {

	/**
	 * @param args
	 */
	private static DBService dbSrvc = new DBServiceImpl();
	private static Map<String, Object> params = HQLUtil.getMap();
	private static Tblogs log = new Tblogs();
	private static Tbloanfin loanfin = new Tbloanfin();
	private static Tbloans loans = new Tbloans();
//	private static Tbmember member = new Tbmember();
	private static Tblntxjrnl jrnl = new Tblntxjrnl();
	private static EODService eodSrvc = new EODServiceImpl();
	private static Tbbranch prcdate = eodSrvc.getMainBranch();
	public static String main(List<Tbloanfin> finList) {
		// TODO Auto-generated method stub
		return loanPostingTest(finList);

	}
	public static String loanPostingTest(List<Tbloanfin> finList) {
		String flag = "failed";
//		prcdate = (Tbprocessingdate) dbSrvc.executeUniqueHQLQuery("FROM Tbprocessingdate WHERE id = 1", params);
		System.out.println(">> START LOAN POSTING "+new Date()+" >>");	
		log.setCurrentdate(prcdate.getCurrentbusinessdate());
		log.setNextdate(prcdate.getNextbusinessdate());
		log.setModulename("LOAN POSTING");
		log.setEventdate(new Date());
		log.setEventname("START");
		log.setDescription("Start of Loan Posting");
		log.setUniquekey("");
		dbSrvc.save(log);
		//		List<Tbloanfin> finList = new ArrayList<Tbloanfin>();
		String runDate = DateTimeUtil.convertDateToString(prcdate.getCurrentbusinessdate(), DateTimeUtil.DATE_FORMAT_MM_DD_YYYY);
		Date dueDt = DateTimeUtil.getDueDate(DateTimeUtil.convertToDate(runDate, DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
		params.put("dueDt", dueDt);
		params.put("dt", runDate);
		int count = 0;
		if(finList==null) {
			finList = (List<Tbloanfin>) dbSrvc.executeListHQLQuery("FROM Tbloanfin WHERE txstatus ='F'", null);
		}
		for(Tbloanfin row: finList) {
			try {
				count++;
				loanfin = row;
				System.out.println("acctno >>> " + loanfin.getAccountno());
				System.out.println("empno >>> " + loanfin.getEmployeeno());
				if(loanfin.getTxrefno().equals(null) || loanfin.getTxrefno().equals("") || loanfin.getTxrefno().isEmpty()) {
					loanfin.setTxrefno(HQLUtil.generateTransactionRefNoDate());
				}
				params.put("acctno", loanfin.getAccountno());
				params.put("empno", loanfin.getEmployeeno());
				loans = (Tbloans) dbSrvc.executeUniqueHQLQuery("FROM Tbloans WHERE accountno =:acctno", params);
//				member = (Tbmember) dbSrvc.executeUniqueHQLQuery("FROM Tbmember WHERE employeeno =:empno AND slastatus = 'A'", params);
				jrnl = INITUtil.initialLoanTxJournal(loans, runDate);
				jrnl.setTxseqno(loanfin.getTxrefno());
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

	public static void applyPayment() {
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

	public static void applyCredit() {
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

	public static void applyDebit() {
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
			Tbpaysched sched = (Tbpaysched) dbSrvc.executeUniqueHQLQuery("FROM Tbpaysched WHERE accountno =:acctno and ilno=:wrkilno", params);
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
			Tbpaysched sched = (Tbpaysched) dbSrvc.executeUniqueHQLQuery("FROM Tbpaysched WHERE accountno =:acctno and ilno=:wrkilno", params);
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
					Tbpaysched paidSched =  (Tbpaysched) dbSrvc.executeUniqueHQLQuery("FROM Tbpaysched WHERE accountno =:acctno and ilno =:tpdilno", params);
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
			loans.setIltogo(Integer.valueOf(loans.getPnno()) - loans.getTpdilno());
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
			if(loans.getTpdilno()<Integer.valueOf(loans.getPnno())) {
				params.put("nxtIlno", loans.getTpdilno()+1);
				Tbpaysched nxtsched = (Tbpaysched) dbSrvc.executeUniqueHQLQuery("FROM Tbpaysched WHERE accountno =:acctno AND ilno =:nxtIlno", params);
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
			if(loanfin.getTxstatus()!="R") {
				dbSrvc.saveOrUpdate(loans);
				dbSrvc.save(jrnl);
				if(loanfin.getTxamtbal().compareTo(BigDecimal.ZERO)==0) {
					loanfin.setTxstatus("P");
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
			check.setChecknumber(loanfin.getTxchkno());
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
				loanfin.setTxstatus("U");
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
