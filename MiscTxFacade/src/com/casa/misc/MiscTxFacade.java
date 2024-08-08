package com.casa.misc;

import java.util.List;

import com.casa.misc.forms.MerchantForm;
import com.coopdb.data.Tbbillspayment;
import com.coopdb.data.Tbcheckbook;
import com.coopdb.data.Tbchecksforclearing;
import com.coopdb.data.Tbcooperative;
import com.coopdb.data.Tbdeposit;
//import com.smslai_eoddb.data.Tbcheckbooklist;
import com.coopdb.data.Tbmerchant;
import com.coopdb.data.Tbmisctx;
import com.coopdb.data.Tbpassbookissuance;
import com.etel.lmsinquiry.forms.PaymentBreakdownForm;
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
public class MiscTxFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level;
	 * recommend changing this to FATAL or ERROR before deploying. For info on these
	 * levels, look for tomcat/log4j documentation
	 */
	public MiscTxFacade() {
		super(INFO);
	}

	MiscTxService miscService = new MiscTxServiceImpl();

	public List<MerchantForm> getMerchantList() {
		List<MerchantForm> list = miscService.getMerchantList();
		return list;
	}

	public String createPayment(Tbbillspayment payment, List<Tbchecksforclearing> checks, PaymentBreakdownForm paymentbreakdown) {
		return miscService.createPayment(payment, checks, paymentbreakdown);
	}

	public String createMiscTx(Tbmisctx misc, List<Tbchecksforclearing> checks) {
		return miscService.createMiscTx(misc, checks);
	}

	public String addMerchant(Tbmerchant merch) {
		return miscService.addMerchant(merch);
	}

	public String checkbookIssuance(Tbcheckbook data) {
		MiscTxService miscService = new MiscTxServiceImpl();
		return miscService.checkbookIssuance(data);
	}

	public String passbookIssuance(Tbpassbookissuance pbissuance) {
		return miscService.passbookIssuance(pbissuance);

	}

	public Tbdeposit getAcctDetails(String acctno) {
		return miscService.getAcctDetails(acctno);
	}

	public int checkFreeze(String acctno) {
		return miscService.checkFreeze(acctno);
	}

	public Tbcooperative getMemfeeAmount(String coopcode) {
		return miscService.getMemfeeAmount(coopcode);
	}

	public List<Tbpassbookissuance> getPassBook(String accountno, String issuancetype) {
		return miscService.getPassBook(accountno, issuancetype);
	}
	
	// Added By Wel 10/14/2022
	public List<Tbdeposit> getAcctDetailsList(String acctno, String clientname) {
		return miscService.getAcctDetailsList(acctno, clientname);
	}	

	public Tbmisctx getMiscTxDetails(String txrefno, String mediaNo) {
		return miscService.getMiscTxDetails(txrefno, mediaNo);
	}
	
	public String errorCorrectMiscTx(Tbmisctx misc, List<Tbchecksforclearing> checks, String txrefno) {
		return miscService.errorCorrectMiscTx(misc, checks, txrefno);
	}
}
