package com.etel.loancalc;

import java.math.BigDecimal;
import java.util.List;

import com.coopdb.data.Tbaccountinfo;
import com.coopdb.data.Tbirregsched;
import com.coopdb.data.Tbloanoffset;
import com.coopdb.data.Tbloans;
import com.coopdb.data.Tbpaysched;
import com.coopdb.data.Tbpdc;
import com.etel.loancalc.forms.LoanCalculatorForm;
import com.etel.loancalc.forms.PaymentScheduleForm;
import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
import com.wavemaker.runtime.service.annotations.ExposeToClient;

/**
 * This is a client-facing service class. All public methods will be exposed to
 * the client. Their return values and parameters will be passed to the client
 * or taken from the client, respectively. This will be a singleton instance,
 * shared between all requests.
 * 
 * To log, call the superclass method log(LOG_LEVEL, String) or log(LOG_LEVEL,
 * String, Exception). LOG_LEVEL is one of FATAL, ERROR, WARN, INFO and DEBUG to
 * modify your log level. For info on these levels, look for tomcat/log4j
 * documentation
 */
@ExposeToClient
public class LoanCalculatorFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level;
	 * recommend changing this to FATAL or ERROR before deploying. For info on these
	 * levels, look for tomcat/log4j documentation
	 */

	LoanCalculatorService srvc = new LoanCalculatorServiceImpl();

	public LoanCalculatorFacade() {
		// super(INFO);
	}

	public Tbaccountinfo getAccountInfoByAppno(String appno) {
		return srvc.getAccountInfoByAppno(appno);
	}

	public LoanCalculatorForm computeLoan(LoanCalculatorForm loancalc) {
		LoanCalculatorServiceNew srvcNew = new LoanCalculatorServiceImplNew();
		return srvcNew.computeLoan(loancalc);
	}

	public List<PaymentScheduleForm> getPaymentSched(LoanCalculatorForm loancalc) {
		LoanCalculatorServiceNew srvcNew = new LoanCalculatorServiceImplNew();
		return srvcNew.computeLoan(loancalc).getPaymentsched();
	}

	public String saveLoanDetails(LoanCalculatorForm loancalc) {
		LoanCalculatorServiceNew srvcNew = new LoanCalculatorServiceImplNew();
		return srvcNew.saveLoanDetails(loancalc);
	}

	public List<Tbirregsched> getIrregSchedList(String loanappno) {
		return srvc.getIrregSchedList(loanappno);
	}

	public String addIrregSched(Tbirregsched sched) {
		return srvc.addIrregSched(sched);
	}

	public String addCheckRecord(Tbpdc pdc) {
		String result = srvc.addCheckRecord(pdc);
		return result;
	}

	public List<Tbpdc> getCheckList(String loanappno) {
		return srvc.getCheckList(loanappno);
	}

	public BigDecimal getTotalCheckAmount(String loanappno, List<Tbpdc> pdcs) {
		return srvc.getTotalCheckAmount(loanappno, pdcs);
	}

	public List<Tbpaysched> getPayschedList(String loanappno) {
		return srvc.getPayschedList(loanappno);
	}

	public List<Tbloans> getExistingLoansByCIFNo(String cifno) {
		return srvc.getExistingLoansByCIFNo(cifno);
	}

	public String addLoanAccountForOffset(List<Tbloans> tbloans, String appno) {
		return srvc.addLoanAccountForOffset(tbloans, appno);
	}

	public String deleteLoanAccountForOffset(Tbloanoffset loanoffset) {
		return srvc.deleteLoanAccountForOffset(loanoffset);
	}

	public List<Tbloanoffset> getListLoanAcctForOffset(String cifno) {
		return srvc.getListLoanAcctForOffset(cifno);
	}

	public String deleteCheckRecord(Tbpdc pdc) {
		String result = srvc.deleteCheckRecord(pdc);
		return result;
	}

	public String deleteIrregSched(Tbirregsched sched) {
		return srvc.deleteIrregSched(sched);
	}

	public BigDecimal getTotalReceivablesByAppNo(String appno) {
		return srvc.getTotalReceivablesByAppNo(appno);
	}

	public Tbloans getLoanAccount(String pnno) {
		return srvc.getLoanAccount(pnno);
	}
}
