package com.etel.lms;

import java.util.ArrayList;
import java.util.List;

import com.etel.lms.LMSDashboardService;
import com.etel.lms.LMSDashboardServiceImpl;
import com.etel.lms.forms.LMSDashboardForm;
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
public class LMSDashboardFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public LMSDashboardFacade() {
       super(INFO);
    }
    
    public List<LMSDashboardForm> getDashBoard(String filter, String month, String year) {
        List<LMSDashboardForm> form = new ArrayList<LMSDashboardForm>();
        LMSDashboardService dashService = new LMSDashboardServiceImpl();
        try {
           log(INFO, "Starting getDashBoard()");
           form = dashService.getDashBoard(filter, month, year);
        } catch(Exception e) {
           log(ERROR, "The getDashBoard service operation has failed", e);
        }
        return form;
     }

    public List<LoanAccountForm> getLoanReleases(String txstat) {
    	List<LoanAccountForm> listOfLoans = new ArrayList<LoanAccountForm>();
        LMSDashboardService dashService = new LMSDashboardServiceImpl();
        try {
           log(INFO, "Starting getLoanTransactions");
           listOfLoans = dashService.getLoanReleases(txstat);
        } catch(Exception e) {
           log(ERROR, "The getLoanTransactions service operation has failed", e);
        }
		return listOfLoans;
    }
    
    public List<LoanTransactionForm> getLoanTransactionbyStatus(String txstat) {
    	List<LoanTransactionForm> listOfTrans = new ArrayList<LoanTransactionForm>();
        LMSDashboardService dashService = new LMSDashboardServiceImpl();
        try {
           log(INFO, "Starting getLoanTransactions()");
           listOfTrans = dashService.getLoanTransactionbyStatus(txstat);
        } catch(Exception e) {
           log(ERROR, "The getLoanTransactions service operation has failed", e);
        }
		return listOfTrans;
    }
    public List<LoanTransactionForm> getLoanTransactionbyStatusAndTxcode(String txstat, String txcode) {
    	List<LoanTransactionForm> listOfTrans = new ArrayList<LoanTransactionForm>();
        LMSDashboardService dashService = new LMSDashboardServiceImpl();
        try {
           log(INFO, "Starting getLoanTransactions()");
           listOfTrans = dashService.getLoanTransactionbyStatusAndTxcode(txstat, txcode);
        } catch(Exception e) {
           log(ERROR, "The getLoanTransactions service operation has failed", e);
        }
		return listOfTrans;
    }
}
