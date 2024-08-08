package com.etel.instruction;

import java.util.List;

import com.etel.forms.ReturnForm;
import com.etel.instruction.forms.InstructionAccessRightsForm;
import com.etel.instruction.forms.InvestigationForm;
import com.etel.instruction.forms.InvestigationFormList;
import com.etel.instruction.forms.RequestValidationForm;
import com.coopdb.data.Tbappraisalreportmain;
import com.coopdb.data.Tbbireportmain;
import com.coopdb.data.Tbcireportmain;
import com.coopdb.data.Tblstapp;
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
public class InstructionFormFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public InstructionFormFacade() {
       //super(INFO);
    }
    private InstructionFormService srvc = new InstructionFormServiceImpl();
    
    public String generateInvestigation(String appno, String investntype){
    	return srvc.generateInvestigation(appno,investntype);
    }
    
    public InvestigationFormList getInvestigationList(String appno,String investigationtype) {
    	return srvc.getInvestigationList(appno,investigationtype);
    }
    public List<InvestigationForm> list(List<InvestigationForm> list) {
    	return list;
    }
    public List<Tbbireportmain> getBIHistory(String cifno) {
    	return srvc.getBIHistory(cifno);
    }
    public List<Tbcireportmain> getCIHistory(String cifno) {
    	return srvc.getCIHistory(cifno);
    }   
    public Tblstapp getLstapp(String appno) {
    	return srvc.getLstapp(appno);
    }
    public String saveInvestigation(String appno, String cifno, String investigationtype, String instruction, String aoremarks, String assignedsupervisor, String supervisorremarks){
    	return srvc.saveInvestigation(appno, cifno, investigationtype, instruction, aoremarks, assignedsupervisor, supervisorremarks);
    }
    public InstructionAccessRightsForm getInstructionAccessRights(String appno, String type){
    	return srvc.getInstructionAccessRights(appno, type);
    }
    public InvestigationFormList getInvestigationListCA(String appno, String investigationtype, String collateralcategory){
    	return srvc.getInvestigationListCA(appno, investigationtype, collateralcategory);
    }
    public List<InvestigationForm> getInvestigationListCASingle(String appno, String investigationtype, String collateralcategory){
    	return srvc.getInvestigationListCASingle(appno, investigationtype, collateralcategory);
    }
    public List<InvestigationForm> getInvestigationListCAGroup(String appno, String investigationtype, String collateralcategory){
    	return srvc.getInvestigationListCAGroup(appno, investigationtype, collateralcategory);
    }    
    public String generateInvestigationCA(String appno){
    	return srvc.generateInvestigationCA(appno);
    }
    public List<Tbappraisalreportmain> getCAHistory(String cifno, String collateraltype, String referenceno) {
    	return srvc.getCAHistory(cifno, collateraltype, referenceno);
    }
    public String saveInvestigationCA(String appno, String cifno, String investigationtype, String instruction, String aoremarks, String assignedsupervisor, String supervisorremarks,String colid){
    	return srvc.saveInvestigationCA(appno, cifno, investigationtype, instruction, aoremarks, assignedsupervisor, supervisorremarks,colid);
    }
    //MMM
    //validation for submit button
    //checker of mandatory fields for all instruction records
    
    public String checkInstructionSheet(String appno) {
    	return srvc.checkInstructionSheet(appno);
    }
    public Boolean isOpenedBySupervisor(String appno, String cifno, String investigationtype, String collateralid){
    	return srvc.isOpenedBySupervisor(appno, cifno, investigationtype, collateralid);
    }
    public Boolean checkIfReportIsOpened(String reportid, String status, String type) {
		return srvc.checkIfReportIsOpened(reportid, status, type);
	}
    public String startReport(String reportid, String status, String type) {
		return srvc.startReport(reportid, status, type);
	}
    public String submitReport(String reportid, String status, String type) {
		return srvc.submitReport(reportid, status, type);
	}
    public String doneReviewReport(String reportid, String status, String type) {
		return srvc.doneReviewReport(reportid, status, type);
	}
    public ReturnForm validateRequest(RequestValidationForm requestform){
    	return srvc.validateRequest(requestform);
    }
}
