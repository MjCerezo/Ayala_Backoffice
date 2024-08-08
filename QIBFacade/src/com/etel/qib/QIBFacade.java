package com.etel.qib;

import java.util.List;

import com.cifsdb.data.Tbqibhistory;
import com.cifsdb.data.Tbqibinfo;
import com.etel.codetable.forms.CodetableForm;
import com.etel.forms.FormValidation;
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
public class QIBFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public QIBFacade() {
       super(INFO);
    }
    
    private QIBService service = new QIBServiceImpl();
    
    public FormValidation saveOrUpdateQIB(Tbqibinfo info, String cifno) {
    	return service.saveOrUpdateQIB(info, cifno);
    }
    
    public Tbqibinfo getQIBInfo(String cifno){
    	return service.getQIBInfo(cifno);
    }

    public String saveQIBHistory(String cifno, String status) {
    	return service.saveQIBHistory(cifno, status);
    }    
    
	public List<Tbqibhistory> qibHistory(String cifno) {
		return service.qibHistory(cifno); 									
	}	
	
	public FormValidation validateQIBInfo(String cifno){
		return service.validateQIBInfo(cifno); 			
	}	
    
	public String deleteQIB(String cifno){
		return service.deleteQIB(cifno);
	}	
	
    /**Get List of Codes or Values per Codename*/
    public List<CodetableForm> getListofCodesPerCodename(String codename) {
    	return service.getListofCodesPerCodename(codename);
    }	
    /**Get List of Codes or Values per Codename*/
    public List<CodetableForm> getListofCodesPerCodename2(String codename) {
    	return service.getListofCodesPerCodename2(codename);
    }	
	
}
