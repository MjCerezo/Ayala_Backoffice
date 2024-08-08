/**
 * 
 */
package com.etel.lmseod;

import java.math.BigDecimal;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tblntxjrnl;
import com.coopdb.data.Tbloans;
import com.coopdb.data.Tblogs;
import com.coopdb.data.Tbprocessingdate;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.lms.LMSDashboardService;
import com.etel.lms.LMSDashboardServiceImpl;
import com.etel.lms.forms.LoanTransactionForm;
import com.etel.util.INITUtil;
import com.etel.utils.DateTimeUtil;
import com.etel.utils.HQLUtil;

/**
 * @author ETEL-COMP05
 *
 */
@SuppressWarnings("unchecked")
public class PaymentAnniversary {

	private static DBService dbSrvc = new DBServiceImpl();
	private static Map<String, Object> params = HQLUtil.getMap();
	private static Tblogs log = new Tblogs();
//	private static List<Tbloanfin> finList = new ArrayList<Tbloanfin>();
//	private static Tbprocessingdate prcdate = (Tbprocessingdate) dbSrvc.executeUniqueHQLQuery("FROM Tbprocessingdate WHERE id = 1 ", null);
	private static Tbprocessingdate prcdate = (Tbprocessingdate) dbSrvc.executeUniqueHQLQuery("FROM Tbprocessingdate WHERE id='1'", null);
	
	private static BigDecimal wrkNtc = BigDecimal.ZERO;
	private static BigDecimal wrkUnpd = BigDecimal.ZERO;
	private static Double wrkUnpdRate = 0.00;
	private static int wrkPrd = 0;
	private static Tbloans loan = new Tbloans();
//	private static Tbmember member = new Tbmember();
	private static Tblntxjrnl jrnl = new Tblntxjrnl();
	@SuppressWarnings("unused")
	private static Clob clob;
	public static void main() {
		// TODO Auto-generated method stub
		paymentAnniversary();
	}

	public static void paymentAnniversary() {
		try {
			LMSDashboardService lmsDashboard = new LMSDashboardServiceImpl();
//			log.setAfter(DateTimeUtil.convertDateToString(prcdate.getEnddate(), DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
//			log.setAmount(BigDecimal.ZERO);
//			log.setBefore(DateTimeUtil.convertDateToString(prcdate.getStartdate(), DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
//			log.setFiletype("PAYMENT ANNIVERSARY");
//			log.setTimestamp(new Date());
//			log.setChangetype("START");
//			log.setFilename("");
//			log.setRowId("");
//			log.setReason("Start");
//			dbSrvc.save(log);
			System.out.println(">> EOD PROCESS : START PAYMENT ANNIVERSARY "+new Date()+" >>");
			clob = (Clob) dbSrvc.execStoredProc("DECLARE @result varchar(MAX) "
					+ "EXEC sp_paymentAnniversary @result OUTPUT SELECT @result", params,null, 0, null);
			List<LoanTransactionForm> finTx = lmsDashboard.getLoanTransactionbyStatus("9");
			if(finTx!=null) {
				LMSEODService lmsEOD = new LMSEODServiceImpl();
				lmsEOD.transactionPosting(finTx);
			}
//			log.setCurrentdate(prcdate.getStartdate());
//			log.setNextdate(prcdate.getNextbusinessdate());
//			log.setModulename("PAYMENT ANNIVERSARY");
//			log.setEventdate(new Date());
//			log.setEventname("END");
//			log.setDescription(clob.getSubString(1, (int) clob.length()));
//			log.setUniquekey("");
//			dbSrvc.save(log);
			System.out.println(">> EOD PROCESS : END PAYMENT ANNIVERSARY "+new Date()+" >>");
			System.out.println(">> EOD PROCESS : START DELINQUENCY PROCESSING "+new Date()+" >>");
//			log.setCurrentdate(prcdate.getStartdate());
//			log.setNextdate(prcdate.getNextbusinessdate());
//			log.setModulename("DELINQUENCY PROCESSING");
//			log.setEventdate(new Date());
//			log.setEventname("START");
//			log.setDescription("Start of Delinquenct Processing Routine");
//			log.setUniquekey("");
//			dbSrvc.save(log);
			clob = (Clob) dbSrvc.execStoredProc("DECLARE @result varchar(MAX) "
					+ "EXEC sp_ddlqProcessing @result OUTPUT SELECT @result", params,null, 0, null);
//			log.setCurrentdate(prcdate.getStartdate());
//			log.setNextdate(prcdate.getNextbusinessdate());
//			log.setEventdate(new Date());
//			log.setEventname("END");
//			log.setDescription(clob.getSubString(1, (int) clob.length()));
//			log.setUniquekey("");
//			dbSrvc.save(log);
			System.out.println(">> EOD PROCESS : END DELINQUENCY PROCESSING "+new Date()+" >>");
			System.out.println(">> EOD PROCESS : START STATUS RECLASS "+new Date()+" >>");
//			log.setCurrentdate(prcdate.getStartdate());
//			log.setNextdate(prcdate.getNextbusinessdate());
//			log.setModulename("STATUS RECLASS");
//			log.setEventdate(new Date());
//			log.setEventname("START");
//			log.setDescription("Start of Status Reclass");
//			log.setUniquekey("");
//			dbSrvc.save(log);
			statusReclass();
//			log.setCurrentdate(prcdate.getStartdate());
//			log.setNextdate(prcdate.getNextbusinessdate());
//			log.setEventdate(new Date());
//			log.setEventname("END");
//			log.setDescription("End of Status Reclass");
//			log.setUniquekey("");
//			dbSrvc.save(log);
			System.out.println(">> EOD PROCESS : END STATUS RECLASS "+new Date()+" >>");
//			System.out.println(">> EOD PROCESS : START INTEREST ACCRUAL ROUTINE "+new Date()+" >>");
//			log.setChangetype("START");
//			log.setFiletype("EOD Interest Accrual Routine");
//			log.setTimestamp(new Date());
//			log.setFilename("");
//			log.setRowId("");
//			log.setReason("Start");
//			dbSrvc.save(log);
//			clob = (Clob) dbSrvc.execStoredProc("DECLARE @result varchar(MAX) "
//					+ "EXEC sp_interestAccrual @result OUTPUT SELECT @result", params,null, 0, null);
//			log.setChangetype("END");
//			log.setTimestamp(new Date());
//			log.setFilename("");
//			log.setRowId("");
//			log.setReason(clob.getSubString(1, (int) clob.length()));
//			dbSrvc.save(log);
//			System.out.println(">> EOD PROCESS : END INTEREST ACCRUAL ROUTINE "+new Date()+" >>");
//			log.setCurrentdate(prcdate.getStartdate());
//			log.setNextdate(prcdate.getNextbusinessdate());
//			log.setModulename("PAYMENT ANNIVERSARY");
//			log.setEventdate(new Date());
//			log.setEventname("END");
//			log.setDescription("Start of Payment Anniversary");
//			log.setUniquekey("");
//			dbSrvc.save(log);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static void statusReclass() {
		List<Tbloans> loanList = new ArrayList<Tbloans>();
		loanList = (List<Tbloans>) dbSrvc.executeListHQLQuery("FROM Tbloans WHERE acctsts<=5", params);
		for(Tbloans row: loanList) {
			try {
				
			
			loan = row;
			wrkNtc = loan.getPrinbal().add(loan.getAr1().add(loan.getAr2().add(loan.getLpcbal().add(loan.getMtdint().add(loan.getAir().add(loan.getMemint()))))));
			wrkUnpd = loan.getUnpaidint().add(loan.getUnpaidprin());
			wrkUnpdRate = 0.00;
			wrkPrd = 0;	
			if(wrkNtc.compareTo(BigDecimal.valueOf(1.1))==-1) {
				loan.setOldsts(loan.getAcctsts());
				loan.setAcctsts(5);
				loan.setAcctstsDate(prcdate.getStartdate());
			} else if (loan.getAcctsts()==5) {
				loan.setOldsts(loan.getAcctsts());
				loan.setAcctsts(1);
				loan.setAcctstsDate(prcdate.getStartdate());
			}

			if(loan.getAcctsts()<3 && loan.getTdueilno()>loan.getTpdilno()) {
				currenttoPDOStatusCheck();
			} else if (loan.getAcctsts()==3 && loan.getTdueilno()<=loan.getTpdilno()) {
				pdoToCurrentStatusCheck();
			} else if (loan.getAcctsts()==5) {
				paidOfftoCloseStatusCheck();
			}
			dbSrvc.saveOrUpdate(loan);
			} catch (Exception e) {
				// TODO: handle exception
//				log.setDescription("Class : " + e.getStackTrace()[0].getClassName() + " Method : " + e.getStackTrace()[0].getMethodName() + " Line : " + e.getStackTrace()[0].getLineNumber());
//				log.setEventdate(new Date());
//				log.setEventname("ERROR");
//				log.setUniquekey(loan.getAccountno()==null?loan.getId().toString():loan.getAccountno());
//				dbSrvc.save(log);
				e.printStackTrace();
			}
		}

	}

	public static void currenttoPDOStatusCheck() {
		if(wrkUnpd.compareTo(BigDecimal.ZERO)==1) {
			wrkUnpdRate = (wrkUnpd.doubleValue()/loan.getLoanbal().doubleValue())/100.00;
		} else {
			wrkUnpdRate = 0.00;
		}
		if(loan.getTdueilno()>loan.getTpdilno()) {
			wrkPrd = loan.getTdueilno()-loan.getTpdilno();
		} else {
			wrkPrd = 0;
		}
		if(wrkUnpdRate>19.99 
				|| ((loan.getPaymentCycle()==0 || loan.getPaymentCycle()==1 || loan.getPaymentCycle()==3) && wrkUnpdRate>9.99)
				|| (loan.getPaymentCycle()==3 && wrkPrd>2) 
				|| ((loan.getPaymentCycle()==5 || loan.getPaymentCycle()==6 || loan.getPaymentCycle()==7) && wrkPrd>1)) {
			pdoReclass();
		}
	}
	
	public static void pdoReclass() {
		params.put("empno", loan.getPrincipalNo());
//		member = (Tbmember) dbSrvc.executeUniqueHQLQuery("FROM Tbmember WHERE employeeno=:empno and slastatus ='A'", params);
		jrnl = INITUtil.initialLoanTxJournal(loan, DateTimeUtil.convertDateToString(prcdate.getStartdate(), DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
		jrnl.setTxcode("70");
		jrnl.setTxprin(loan.getLoanbal());
		if(loan.getIntpaytype().equals("1")) {
			jrnl.setTxint(loan.getUidbal());
		}
		loan.setOldsts(loan.getAcctsts());
		loan.setAcctsts(3);
		loan.setAcctstsDate(prcdate.getStartdate());
		jrnl.setTxacctsts(String.valueOf(loan.getAcctsts()));
		jrnl.setOldstat(String.valueOf(loan.getOldsts()));
		jrnl.setTxamt(jrnl.getTxprin().add(jrnl.getTxint()));
		dbSrvc.save(jrnl);
	}
	
	public static void pdoToCurrentStatusCheck() {
		if((loan.getPaymentCycle()==3 && wrkPrd<3) ||
				((loan.getPaymentCycle()==0 || loan.getPaymentCycle()==1 || loan.getPaymentCycle()==8) && wrkUnpdRate<10.00)) {
			currentReclass();
		}
	}
	
	public static void currentReclass() {
		params.put("empno", loan.getPrincipalNo());
//		member = (Tbmember) dbSrvc.executeUniqueHQLQuery("FROM Tbmember WHERE employeeno=:empno and slastatus ='A'", params);
		jrnl = INITUtil.initialLoanTxJournal(loan, DateTimeUtil.convertDateToString(prcdate.getStartdate(), DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
		jrnl.setTxcode("81");
		jrnl.setTxprin(loan.getLoanbal());
		if(loan.getIntpaytype().equals("1")) {
			jrnl.setTxint(loan.getUidbal());
		}
		loan.setOldsts(loan.getAcctsts());
		loan.setAcctsts(1);
		loan.setAcctstsDate(prcdate.getStartdate());
		jrnl.setTxacctsts(String.valueOf(loan.getAcctsts()));
		jrnl.setOldstat(String.valueOf(loan.getOldsts()));
		jrnl.setTxamt(jrnl.getTxprin().add(jrnl.getTxint()));
		dbSrvc.save(jrnl);
	}
	
	public static void paidOfftoCloseStatusCheck() {
		if(loan.getChk4clr().compareTo(BigDecimal.ZERO)==0){
			params.put("empno", loan.getPrincipalNo());
//			member = (Tbmember) dbSrvc.executeUniqueHQLQuery("FROM Tbmember WHERE employeeno=:empno and slastatus ='A'", params);
			jrnl = INITUtil.initialLoanTxJournal(loan, DateTimeUtil.convertDateToString(prcdate.getStartdate(), DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
			jrnl.setTxcode("81");
			jrnl.setTxprin(loan.getLoanbal());
			jrnl.setTxar1(loan.getAr1());
			jrnl.setTxar2(loan.getAr2());
			jrnl.setTxienc(loan.getUidbal().add(loan.getMtdint()));
			jrnl.setTxint(loan.getAir());
			jrnl.setTxexcess(loan.getExcessbal());
			loan.setAr1(BigDecimal.ZERO);
			loan.setAr2(BigDecimal.ZERO);
			loan.setAir(BigDecimal.ZERO);
			loan.setExcessbal(BigDecimal.ZERO);
			loan.setUidbal(BigDecimal.ZERO);
			loan.setLoanbal(BigDecimal.ZERO);
			loan.setOldsts(loan.getAcctsts());
			loan.setAcctsts(9);
			loan.setAcctstsDate(prcdate.getStartdate());
			jrnl.setTxacctsts(String.valueOf(loan.getAcctsts()));
			jrnl.setOldstat(String.valueOf(loan.getOldsts()));
			dbSrvc.save(jrnl);
		}
	}
	
}
