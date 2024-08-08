package com.etel.codetablecasa;

import java.util.List;

import com.coopdb.data.Tbcodetablecasa;
import com.etel.codetable.forms.CodetableForm;
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
public class CodetableCASAFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public CodetableCASAFacade() {
       super(INFO);
    }
    
    /**Get List of Codes or Values per Codename*/
    public List<CodetableForm> getListofCodesPerCodename(String codename) {
    	CodetableCASAService codesrvc = new CodetableCASAServiceImpl();
    	return codesrvc.getListofCodesPerCodename(codename);
    }
    /**Get List of Codes or Values per Codename*/
    public List<CodetableForm> getListofCodesPerCodenameCIF(String codename) {
    	CodetableCASAService codesrvc = new CodetableCASAServiceImpl();
    	return codesrvc.getListofCodesPerCodenameCIF(codename);
    }
    
    /** Get List of Codes or Values per Codename and Desc1 */
    public List<CodetableForm> getListofCodesPerCodenameAndDesc2(String codename, String desc2) {
    	CodetableCASAService codesrvc = new CodetableCASAServiceImpl();
    	return codesrvc.getListofCodesPerCodenameAndDesc2(codename, desc2);
    }
    
    /** Insert new data to TBCODETABLE */
    public String addCodetable(CodetableForm form) {
    	CodetableCASAService codesrvc = new CodetableCASAServiceImpl();
    	return codesrvc.addCodetable(form);
   	
    }
    
    /** Update data from TBCODETABLE */
    public String updateCodetable(CodetableForm form) {
    	CodetableCASAService codesrvc = new CodetableCASAServiceImpl();
    	return codesrvc.updateCodetable(form);
   	
    }
    /** Get CODEVALUE LIST from TBCODETABLE */
    public List<Tbcodetablecasa> getCodevalueList(String codename){
    	CodetableCASAService codesrvc = new CodetableCASAServiceImpl();
    	return codesrvc.getCodevalueList(codename);
    }
    
    /**CODETABLE - Get List of All Codenames from TBCODETABLE*/
    
    public List<String> getAllCodenameList(){
    	CodetableCASAService codesrvc = new CodetableCASAServiceImpl();
    	return codesrvc.getAllCodenameList();
    }
    
    /**CODETABLE - Delete Codename from TBCODETABLE */ 
    
    public String deleteCodename (CodetableForm form){
		String flag = "";
		CodetableCASAService codesrvc = new CodetableCASAServiceImpl();
		flag = codesrvc.deleteCodename(form);
		return flag;	
    }
    
    /** CODENAME - Add Codename to TBCODENAME */
    public String addCodeName(String codename, String remarks, Boolean iseditable) {
    	CodetableCASAService codesrvc = new CodetableCASAServiceImpl();
    	return codesrvc.addCodeName(codename, remarks, iseditable);
   	
    }
    
    /** CODENAME - Search All to TBCODETABLE*/
    public List<CodetableForm> searchCodetable(String search){
    	CodetableCASAService codesrvc = new CodetableCASAServiceImpl();
    	return codesrvc.searchCodetable (search);
    }
    
    /** CODENAME - Search CODENAME to TBCODETABLE*/
    public List<CodetableForm> searchCodename(String codename, String search){
    	CodetableCASAService codesrvc = new CodetableCASAServiceImpl();
    	return codesrvc.searchCodename (codename, search);
    }

}
