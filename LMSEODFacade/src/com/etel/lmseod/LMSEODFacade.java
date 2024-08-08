package com.etel.lmseod;

import java.util.List;

import com.coopdb.data.Tbprocessingdate;
import com.etel.lms.forms.LoanAccountForm;
import com.etel.lms.forms.LoanTransactionForm;
import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
import com.wavemaker.runtime.service.annotations.ExposeToClient;

/**
 * This is a client-facing service class.  All
 * public methods will be exposed to the client.  Their return
 * values and parameters will be passed to the client or taken
 * from the client, respectively.  This will be a singleton
 * instance, shared between all requests. 
 * 
 * To log, call the superclass method log(LOG_LEVEL, String) or log(LOG_LEVEL, String, Exception).
 * LOG_LEVEL is one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level.
 * For info on these levels, look for tomcat/log4j documentation
 */
@ExposeToClient
public class LMSEODFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public LMSEODFacade() {
       super(INFO);
    }

    LMSEODService eodSrvc = new LMSEODServiceImpl(); 
    
    public String loanBooking(List<LoanAccountForm> loanTx) {
		String result  = null;
		try {
			log(INFO, "Starting loanBooking");
			result = eodSrvc.loanBooking(loanTx);
			log(INFO, "Finished loanBooking" + result);
		} catch(Exception e) {
			log(ERROR, "The loanBooking service operation has failed", e);
		}
		return result;
	} 
    
    public String transactionPosting(List<LoanTransactionForm> txlist) {
    	String result  = null;
		try {
			log(INFO, "Starting Transaction Posting");
			result = eodSrvc.transactionPosting(txlist);
			log(INFO, "Finished Transaction Posting" + result);
		} catch(Exception e) {
			log(ERROR, "The Transaction Posting service operation has failed", e);
		}
		return result;
    }
    
    public String singletransactionPosting(LoanTransactionForm tx) {
    	String result  = null;
		try {
			log(INFO, "Starting loanBooking");
			result = eodSrvc.singletransactionPosting(tx);
			log(INFO, "Finished loanBooking" + result);
		} catch(Exception e) {
			log(ERROR, "The loanBooking service operation has failed", e);
		}
		return result;
    }

	public String runLMSEOD_new() {
		String result = null;
		try {
			result = eodSrvc.runLMSEOD_new();
		}catch(Exception e) {
			log(ERROR, "The LMS EOD service operation has failed", e);
		}
		return result;
	}
	
	public Tbprocessingdate getProcessingDate() {
    	return eodSrvc.getProcessingDate();
    }
	
	public String updateProcessEndDate(Tbprocessingdate processdate)
	{
		return eodSrvc.updateProcessEndDate(processdate);
	}
}
