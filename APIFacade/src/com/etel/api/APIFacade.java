package com.etel.api;

import com.coopdb.data.Tbapilogs;
import com.etel.api.forms.AddJournalForm;
import com.etel.api.forms.AddReceiptForm;
import com.etel.api.util.APIUtil;
import com.etel.forms.ReturnForm;
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
public class APIFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public APIFacade() {
//       super(INFO);
    }

   private APIService srvc = new APIServiceImpl();
   
   public ReturnForm addLoanDisbursement(String appno){
	   return srvc.addLoanDisbursement(appno);
   }
   
   public Boolean checkIfUrlIsReachable(String url){
	   if(url != null){
		   return APIUtil.isURLReachable(url);
	   }
	   return false;
   }
   public Tbapilogs getLatestApiLogsByTypeAndAppNo(String appno, String apitype){
	   return APIUtil.getLatestApiLogsByTypeAndAppNo(appno, apitype);
   }
   public ReturnForm addReceipt(String appno, AddReceiptForm receipt){
	   return srvc.addReceipt(appno, receipt);
   }
   public ReturnForm addJournal(String appno, AddJournalForm journal){
	   return srvc.addJournal(appno, journal);
   }
}
