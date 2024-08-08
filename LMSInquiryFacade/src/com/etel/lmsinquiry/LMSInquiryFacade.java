package com.etel.lmsinquiry;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.coopdb.data.Tbcoa;
import com.coopdb.data.Tblntxjrnl;
import com.coopdb.data.Tbloanfin;
import com.etel.lms.forms.LoanAccountForm;
import com.etel.lmsinquiry.forms.CustomerInfoForm;
import com.etel.lmsinquiry.forms.LoanAccountInquiryForm;
import com.etel.lmsinquiry.forms.LoanTransactionHistory;
import com.etel.lmsinquiry.forms.PaymentBreakdownForm;
import com.etel.lmsinquiry.forms.PaymentScheduleForm;
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
public class LMSInquiryFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level;
	 * recommend changing this to FATAL or ERROR before deploying. For info on these
	 * levels, look for tomcat/log4j documentation
	 */
	public LMSInquiryFacade() {
		super(INFO);
	}

	LMSInquiryService srvc = new LMSInquiryServiceImpl();

	public List<LoanAccountForm> getLoanAccounts(String acctno, String clientname, String cifno) {

		return srvc.getLoanAccounts(acctno, clientname, cifno);

	}

	public LoanAccountInquiryForm accountform(String accountno) {

		LoanAccountInquiryForm form = new LoanAccountInquiryForm();
		form = srvc.accountform(accountno);
		return form;
	}

	public List<LoanTransactionHistory> getTransactionHistory(String acctno) {

		return srvc.getTransactionHistory(acctno);
	}

	public CustomerInfoForm getCustomerInformation(String cifno) {
		return srvc.getCustomerInformation(cifno);

	}

	public PaymentScheduleForm getPaymentSchedPerAcct(String pnno) {
		return srvc.getPaymentSchedPerAcct(pnno);
	}

	public List<Tbloanfin> getPaymentTransactionInquiry(String clientname, String pnno, String transno, String orno,
			String status) {
		return srvc.getPaymentTransactionInquiry(clientname, pnno, transno, orno, status);
	}

	public PaymentBreakdownForm getPaymentBreakdown(String accountno, Date txvaldt, BigDecimal txamount,
			BigDecimal txinterest, BigDecimal txlpc, BigDecimal txar) {
		return srvc.getPaymentBreakdown(accountno, txvaldt, txamount, txinterest, txlpc, txar);
	}
	
	public List<Tbcoa> getAndListTbcoa (String acctno) {
		return srvc.getAndListTbcoa (acctno);
	}
	public Tbloanfin getLastTransaction(String acctno) {
		return srvc.getLastTransaction(acctno);
	}
}
