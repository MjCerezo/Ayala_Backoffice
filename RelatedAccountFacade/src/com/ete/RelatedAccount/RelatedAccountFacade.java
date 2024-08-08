package com.ete.RelatedAccount;

import java.util.List;

import com.etel.relatedaccount.form.AccountProfitabilityForm;
import com.etel.relatedaccount.form.DepositAccountForm;
import com.etel.relatedaccount.form.LoanAccountForm;
import com.etel.relatedaccount.form.RelatedAccountParameterForm;
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
public class RelatedAccountFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public RelatedAccountFacade() {
       super(INFO);
    }

    private RelatedAccountService srvc = new RelatedAccountServiceImpl();
    
    public List<DepositAccountForm> getDepositAccount (RelatedAccountParameterForm forms){
		return srvc.getDepositAccount(forms);
	}
    
    public List<LoanAccountForm> getLoanAccount (RelatedAccountParameterForm forms){
		return srvc.getLoanAccount(forms);
	}
    public List<AccountProfitabilityForm> getAccountProfitability (RelatedAccountParameterForm forms){
		return srvc.getAccountProfitability(forms);
	}
    
    public List<LoanAccountForm> getLoanPayment (RelatedAccountParameterForm forms){
		return srvc.getLoanPayment(forms);
	}

}
