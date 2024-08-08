package com.etel.depedcodes;

import java.util.List;

import com.cifsdb.data.Tbcodetable;
import com.cifsdb.data.Tbdepedcodes;
import com.etel.codetable.forms.CodetableForm;
import com.etel.depedcodes.form.DepEdCodes;
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
public class DepEdCodesFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public DepEdCodesFacade() {
      // super(INFO);
    }
    DepEdCodesService service = new DepEdCodesServiceImpl();
    public List<DepEdCodes> listDepEdCodes() {
    	return service.listDepEdCodes();
    }
    public List<DepEdCodes> listDepEdCodesByRegion(String region) {
    	return service.listDepEdCodesByRegion(region);
    }
    public List<DepEdCodes> listDepEdCodesByDivision(String division) {
    	return service.listDepEdCodesByDivision(division);
    }
    public List<Tbdepedcodes> searchDepEdCodes(String region, String division, String station) {
    	return service.searchDepEdCodes(region, division, station);
    }    
    public String saveOrupdateDepedCode(Tbdepedcodes data, String saveOrupdate) {
    	return service.saveOrupdateDepedCode(data, saveOrupdate);
    }
    
    /************************************************************************************* 
     *************************************************************************************START OF DEPED CODES*******/
   public String saveOrupdateRegion(Tbcodetable data, String addOrupdate){
	   return service.saveOrupdateRegion(data, addOrupdate);
   }
   public List<Tbcodetable> searchRegion(String regionname){
	   return service.searchRegion(regionname);
   }
   public String saveOrupdateDivision(Tbcodetable data, String addOrupdate){
	   return service.saveOrupdateDivision(data, addOrupdate);
   }       
   public List<CodetableForm> searchDivisionByRegionCode(String regioncode){
	   return service.searchDivisionByRegionCode(regioncode);
   }  
   public List<Tbcodetable> searchDivision(String regioncode, String divisioncode){
	   return service.searchDivision(regioncode, divisioncode);
   }  
    
}
