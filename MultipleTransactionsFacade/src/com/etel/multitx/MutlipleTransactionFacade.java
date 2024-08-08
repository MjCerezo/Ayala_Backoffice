package com.etel.multitx;

import java.util.Date;
import java.util.List;

import com.coopdb.data.Tbmultipletransaction;
import com.etel.inquiry.forms.CIFInquiryForm;
import com.etel.lmsinquiry.forms.PaymentBreakdownForm;
import com.etel.multitx.form.MultipleTransDataForm;
import com.etel.multitx.form.MultipleTransactionAccountForm;
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
public class MutlipleTransactionFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level;
	 * recommend changing this to FATAL or ERROR before deploying. For info on these
	 * levels, look for tomcat/log4j documentation
	 */
	public MutlipleTransactionFacade() {
		super(INFO);
	}

	MultipleTransactionService mutltxservice = new MultipleTransactionServiceImpl();

	public List<Tbmultipletransaction> listTransactions(String multitxrefno) {
		return mutltxservice.listTransactions(multitxrefno);
	}

	public String addTransaction(Tbmultipletransaction transaction) {
		return mutltxservice.addTransaction(transaction);
	}

	public String editTransaction(Tbmultipletransaction transaction) {
		return mutltxservice.editTransaction(transaction);
	}

	public String postTransaction(List<Tbmultipletransaction> multipletransactions,
			List<PaymentBreakdownForm> paymentbreakdown) {
		return mutltxservice.postTransaction(multipletransactions, paymentbreakdown);
	}

	public List<MultipleTransactionAccountForm> listAccounts(String cifno) {
		return mutltxservice.listAccounts(cifno);
	}

	public List<CIFInquiryForm> listCIF(String cifno, String cifname) {
		return mutltxservice.listCIF(cifno, cifname);
	}
	
	//Mar 
	public List<MultipleTransDataForm> getMultipleTransaction(Date startDate, Date endDate, String branch, String teller){
		return mutltxservice.getMultipleTransaction(startDate,endDate,branch,teller);
	}
}
