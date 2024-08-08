package com.etel.CollectionManagement;

import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
import com.wavemaker.runtime.service.annotations.ExposeToClient;
import com.coopdb.data.Tbcollectionmanagement;
import java.util.List;

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
public class CollectionManagementFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
	private CollectionManagementFacadeService srvc = new CollectionManagementFacadeServiceImpl();
	
    public CollectionManagementFacade() {
       super(INFO);
    }
    
    public Tbcollectionmanagement getCollectionDetails(String accountno){
    	return srvc.getCollectionDetails(accountno);
    }
    
    public String saveCollectionDetails(Tbcollectionmanagement collection){
    	return srvc.saveCollectionDetails(collection);
    }
    
}
