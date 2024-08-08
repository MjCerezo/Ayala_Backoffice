package com.etel.codetable;

import java.util.List;

import com.etel.codetable.forms.AOForm;
import com.etel.codetable.forms.CodetableForm;
import com.coopdb.data.Tbcodetable;
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
public class CodetableFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public CodetableFacade() {
       super(ERROR);
    }
    public List<AOForm> getListofAO(String aocode) {
    	CodetableService codesrvc = new CodetableServiceImpl();
    	return codesrvc.getListofAO(aocode);
    }
    
    /**Get List of Codes or Values per Codename*/
    public List<CodetableForm> getListofCodesPerCodename(String codename) {
    	CodetableService codesrvc = new CodetableServiceImpl();
    	return codesrvc.getListofCodesPerCodename(codename);
    }
    /**Get List of Codes or Values per Codename*/
    public List<CodetableForm> getListofCodesPerCodenameCIF(String codename) {
    	CodetableService codesrvc = new CodetableServiceImpl();
    	return codesrvc.getListofCodesPerCodenameCIF(codename);
    }
    
    /** Get List of Codes or Values per Codename and Desc1 */
    public List<CodetableForm> getListofCodesPerCodenameAndDesc2(String codename, String desc2) {
    	CodetableService codesrvc = new CodetableServiceImpl();
    	return codesrvc.getListofCodesPerCodenameAndDesc2(codename, desc2);
    }
    
    /** Insert new data to TBCODETABLE */
    public String addCodetable(CodetableForm form) {
    	CodetableService codesrvc = new CodetableServiceImpl();
    	return codesrvc.addCodetable(form);
   	
    }
    
    /** Update data from TBCODETABLE */
    public String updateCodetable(CodetableForm form) {
    	CodetableService codesrvc = new CodetableServiceImpl();
    	return codesrvc.updateCodetable(form);
   	
    }
    /** Get CODEVALUE LIST from TBCODETABLE */
    public List<Tbcodetable> getCodevalueList(String codename){
    	CodetableService codesrvc = new CodetableServiceImpl();
    	return codesrvc.getCodevalueList(codename);
    }
    
    /**CODETABLE - Get List of All Codenames from TBCODETABLE*/
    
    public List<String> getAllCodenameList(){
    	CodetableService codesrvc = new CodetableServiceImpl();
    	return codesrvc.getAllCodenameList();
    }
    
    /**CODETABLE - Delete Codename from TBCODETABLE */ 
    
    public String deleteCodename (CodetableForm form){
		String flag = "";
		CodetableService codesrvc = new CodetableServiceImpl();
		flag = codesrvc.deleteCodename(form);
		return flag;	
    }
    
    /** CODENAME - Add Codename to TBCODENAME */
    public String addCodeName(String codename, String remarks, Boolean iseditable) {
    	CodetableService codesrvc = new CodetableServiceImpl();
    	return codesrvc.addCodeName(codename, remarks, iseditable);
   	
    }
    
    /** CODENAME - Search All to TBCODETABLE*/
    public List<CodetableForm> searchCodetable(String search){
    	CodetableService codesrvc = new CodetableServiceImpl();
    	return codesrvc.searchCodetable (search);
    }
    
    /** CODENAME - Search CODENAME to TBCODETABLE*/
    public List<CodetableForm> searchCodename(String codename, String search){
    	CodetableService codesrvc = new CodetableServiceImpl();
    	return codesrvc.searchCodename (codename, search);
    }
    

}
