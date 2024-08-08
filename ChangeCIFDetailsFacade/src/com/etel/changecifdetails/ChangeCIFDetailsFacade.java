package com.etel.changecifdetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cifsdb.data.Tbchangecifdetailsrequest;
import com.cifsdb.data.Tbcifindividual;
import com.cifsdb.data.Tbcifmain;
import com.cifsdb.data.Tbfatca;
import com.etel.changecifdetails.form.ChangeCIFDetailsForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.lmsreport.form.LMSReportForms;
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
public class ChangeCIFDetailsFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
	private Map<String, Object> params = new HashMap<String, Object>();
    public ChangeCIFDetailsFacade() {
       super(INFO);
    }

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
    
    //Renz
	public String saveOrUpdateChangeDetails(Tbchangecifdetailsrequest ref) {
		ChangeCIFDetailsService srvc = new ChangeCIFDetailsServiceImpl();
  		return srvc.saveOrUpdateChangeDetails(ref);
  	}
	
	public String updateCIFIndividual(Tbcifindividual ref, Tbcifmain refmain, String changetype,String remarks) {
		ChangeCIFDetailsService srvc = new ChangeCIFDetailsServiceImpl();
  		return srvc.updateCIFIndividual(ref,refmain,changetype,remarks);
  	}
	
	public String updateCIFFatca(Tbfatca ref,String changetype,String remarks) {
		ChangeCIFDetailsService srvc = new ChangeCIFDetailsServiceImpl();
  		return srvc.updateCIFFatca(ref,changetype,remarks);
  	}
	
	public List<Tbchangecifdetailsrequest> listOfChangeDetailsHistory(String cifno, String cifname) {
		ChangeCIFDetailsService srvc = new ChangeCIFDetailsServiceImpl();
  		return srvc.listOfChangeDetailsHistory(cifno,cifname);
  	}
	
	public List<ChangeCIFDetailsForm> listOfCIFChangeHistory(String cifno, String losLink, String cifLink) {
		ChangeCIFDetailsService srvc = new ChangeCIFDetailsServiceImpl();
  		return srvc.listOfCIFChangeHistory(cifno, losLink, cifLink);
  	}
	
	@SuppressWarnings("unchecked")
	public List<ChangeCIFDetailsForm> getCIFStatus(String workflowid) {
		List<ChangeCIFDetailsForm> form = new ArrayList<ChangeCIFDetailsForm>();
		DBService dbService = new DBServiceImpl();
		params.put("workflowid", workflowid);
		try {
			form = (List<ChangeCIFDetailsForm>) dbService
					.execSQLQueryTransformer("SELECT processid,processname FROM TBWORKFLOWPROCESS WHERE workflowid =:workflowid", params, ChangeCIFDetailsForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

}
