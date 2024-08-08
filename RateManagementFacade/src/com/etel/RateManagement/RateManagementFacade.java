package com.etel.RateManagement;

import java.util.List;

import com.coopdb.data.Tbrates;
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
public class RateManagementFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public RateManagementFacade() {
       super(INFO);
    }
    RateManagementService srvc = new RateManagementServiceImpl();

    public String sampleJavaOperation() {
       String result  = null;
       try {
          log(INFO, "Starting sample operation");
          result = "Hello World";
          log(INFO, "Returning " + result);
       } catch(Exception e) {
          log(ERROR, "The sample java service operation has failed", e);
       }
       return result;
    }
    public List<Tbrates> getListRates(){
    	return srvc.getListRates();
    }
    public String addOrupdateRates(Tbrates rate){
    	return srvc.addOrupdateRates(rate);
    }
    public String deleteRates(int id){
    	return srvc.deleteRates(id);
    }

}
