package com.etel.cifreport;

import java.util.List;

import com.etel.cifreport.form.AllUserForm;
import com.etel.cifreport.form.CIFComprehensiveForm;
import com.etel.cifreport.form.CIFExceptionalReportForm;
import com.etel.cifreport.form.CIFParameterForm;
import com.etel.cifreport.form.CIFSchedulesReportForm;
import com.etel.cifreport.form.CIFTransactionalReportForm;
import com.etel.cifreport.form.CIFWorkFlowForm;
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
public class CIFReportFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public CIFReportFacade() {
       super(INFO);
    }
    
    private CIFReportService srvc = new CIFReportServiceImpl();
    
	//MAr 09-13-2021
    public List<CIFTransactionalReportForm> getLOSLink (){
		return srvc.getLOSLink();
	}
    public List<CIFTransactionalReportForm> getCIFLink (){
		return srvc.getCIFLink();
	}
    
    public List<CIFWorkFlowForm>getCIFWorkflow(String workflowid){
    	return srvc.getCIFWorkflow(workflowid);
    }
    
	public List<CIFTransactionalReportForm> getListofCIFEnrollmentTransactionsIndividual (CIFParameterForm forms){
		return srvc.getListofCIFEnrollmentTransactionsIndividual(forms);
	}

	public List<CIFTransactionalReportForm> getListofCIFEnrollmentTransactionsBusiness (CIFParameterForm forms){
		return srvc.getListofCIFEnrollmentTransactionsBusiness(forms);
	}

	public List<CIFTransactionalReportForm> getListofClientProfileUpdate (CIFParameterForm forms){
		return srvc.getListofClientProfileUpdate(forms);
	}

	
	public List<CIFSchedulesReportForm> getListofCustomersIndividual (CIFParameterForm forms){
		return srvc.getListofCustomersIndividual(forms);
	}

	public List<CIFSchedulesReportForm> getListofCustomersBusiness (CIFParameterForm forms){
		return srvc.getListofCustomersBusiness(forms);
	}
	
	public List<CIFExceptionalReportForm> getListofCustomersFATCAIndividual (CIFParameterForm forms){
		return srvc.getListofCustomersFATCAIndividual(forms);
	}
	
	public List<CIFExceptionalReportForm> getListofCustomersFATCABusiness (CIFParameterForm forms){
		return srvc.getListofCustomersFATCABusiness(forms);
	}
	
	public List<CIFExceptionalReportForm> getListofCustomersPEPIndividual (CIFParameterForm forms){
		return srvc.getListofCustomersPEPIndividual(forms);
	}
	
	public List<CIFExceptionalReportForm> getListofCustomersPEPBusiness (CIFParameterForm forms){
		return srvc.getListofCustomersPEPBusiness(forms);
	}
	
	public List<CIFExceptionalReportForm> getListofCustomersDOSRIIndividual (CIFParameterForm forms){
		return srvc.getListofCustomersDOSRIIndividual(forms);
	}
	
	public List<CIFExceptionalReportForm> getListofCustomersDOSRIBusiness (CIFParameterForm forms){
		return srvc.getListofCustomersDOSRIBusiness(forms);
	}
	
	public List<CIFExceptionalReportForm> getListofExceptionalAccountsFATCAIndividual (CIFParameterForm forms){
		return srvc.getListofExceptionalAccountsFATCAIndividual(forms);
	}
	
	public List<CIFExceptionalReportForm> getListofExceptionalAccountsFATCABusiness (CIFParameterForm forms){
		return srvc.getListofExceptionalAccountsFATCABusiness(forms);
	}
	public List<CIFExceptionalReportForm> getListofExceptionalAccountsPEPIndividual (CIFParameterForm forms){
		return srvc.getListofExceptionalAccountsPEPIndividual(forms);
	}
	
	public List<CIFExceptionalReportForm> getListofExceptionalAccountsPEPBusiness (CIFParameterForm forms){
		return srvc.getListofExceptionalAccountsPEPBusiness(forms);
	}
	
	public List<CIFExceptionalReportForm> getListofExceptionalAccountsDOSRIIndividual (CIFParameterForm forms){
		return srvc.getListofExceptionalAccountsDOSRIIndividual(forms);
	}
	
	public List<CIFExceptionalReportForm> getListofExceptionalAccountsDOSRIBusiness (CIFParameterForm forms){
		return srvc.getListofExceptionalAccountsDOSRIBusiness(forms);
	}
	
	public List<AllUserForm> getListofUser (String branchcode, String companycode){
		return srvc.getListofUser(branchcode,companycode);
	}

	public List<CIFComprehensiveForm> getCIFComprehensive (CIFParameterForm forms){
		return srvc.getCIFComprehensive(forms);
	}

}
