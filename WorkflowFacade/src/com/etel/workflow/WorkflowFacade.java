package com.etel.workflow;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cifsdb.data.AuditTrail;
import com.cifsdb.data.Tbcifmain;
import com.cifsdb.data.Tbcodetable;
import com.coopdb.data.Tblosmain;
import com.etel.audittrail.AuditTrailFacade;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.dataentry.FullDataEntryServiceImpl;
import com.etel.dataentryutil.ValidateDataEntry;
import com.etel.email.EmailCode;
import com.etel.email.EmailFacade;
import com.etel.email.forms.EmailForm;
import com.etel.forms.ReturnForm;
import com.etel.history.HistoryService;
import com.etel.history.HistoryServiceImpl;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;
import com.etel.workflow.forms.SubmitOptionForm;
import com.etel.workflow.forms.WorkflowDashboardForm;
import com.etel.workflow.forms.WorkflowProcessForm;
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
public class WorkflowFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
	private WorkflowService srvc = new WorkflowServiceImpl();
	
    public WorkflowFacade() {
       //super(INFO);
    }
    
    public String workflow(String cifno, String status, String remarks){
    	String flag = "failed";
    	Map<String, Object> params = HQLUtil.getMap();
    	DBService dbservice = new DBServiceImpl();
    	DBService dbserviceCIF = new DBServiceImplCIF();
    	AuditTrailFacade auditTrailFacade = new AuditTrailFacade();
		AuditTrail auditTrail = new AuditTrail();
    	params.put("cifno", cifno);
    	String username = UserUtil.securityService.getUserName();
    	try {

			if(cifno!=null){
				Tbcifmain main = (Tbcifmain) dbserviceCIF.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno=:cifno",
						params);
				Integer docsSubmitted = (Integer) dbserviceCIF.executeUniqueSQLQuery("SELECT count(*) FROM Tbdocdetails WHERE cifno=:cifno", params);
				Integer totalDocsRequired = (Integer) dbservice.executeUniqueSQLQuery("SELECT count(*) FROM TBGENERALDOCS a WHERE a.doccategory = 'CIF' AND appytoindiv = '1'", null);
				
				if (main != null) {
						
					if (main.getCustomertype().equals("1")) {
						
//						COMPANY
//						COMPANYSTATUS	1	For Encoding
//						COMPANYSTATUS	2	For Approval of Cluster Head
//						COMPANYSTATUS	3	For Approval
//						COMPANYSTATUS	4	Approved
//						COMPANYSTATUS	5	Cancelled
						if (status != null) {
							
							//For Encoding
							if (status.equals("1")){
								main.setCifstatus("2");
							}
							//For Approval of Cluster Head
							if(status.equals("2")) {
								main.setCifstatus("3");
							}
						}
						
					}else if(main.getCustomertype().equals("2")) {
						
//						MEMBERSHIPAPPLICATIONSTATUS
//			    		MEMBERSHIPAPPLICATIONSTATUS	1	For Encoding
//			    		MEMBERSHIPAPPLICATIONSTATUS	2	For Document Submission
//			    		MEMBERSHIPAPPLICATIONSTATUS	3	For HR Review
//			    		MEMBERSHIPAPPLICATIONSTATUS	4	For Approval
//			    		MEMBERSHIPAPPLICATIONSTATUS	5	Approved
//			    		MEMBERSHIPAPPLICATIONSTATUS	6	Cancelled
						if (status != null) {
							
							//For Encoding
							if (status.equals("1")){
								if(docsSubmitted >= totalDocsRequired) {
									main.setCifstatus("3");
								}else {
									main.setCifstatus("2");
								}
							}
							//For Document Submission
							if(status.equals("2")) {
								main.setCifstatus("3");
							}
							//For HR Review
							if (status.equals("3")){
								main.setCifstatus("4");
							}
						}
						
					}
					
					main.setDateupdated(new Date());
					if(dbserviceCIF.saveOrUpdate(main)){
						flag = "success";

						if (main.getCustomertype().equals("1")) {
							
							auditTrail.setTransactionNumber(cifno);
							auditTrail.setEventType("2");
							auditTrail.setIpaddress(UserUtil.getUserIp());
							
							if (status.equals("1")){
								//Audit Trail TBCODETABLE CODE = AuditTrail
								auditTrail.setEventName("2");
								auditTrail.setEventDescription(main.getFullname()+" Submitted to Status: For Approval of Cluster Head");
								auditTrailFacade.saveAudit(auditTrail);
							}
							if (status.equals("2")){
								//Audit Trail TBCODETABLE CODE = AuditTrail
								auditTrail.setEventName("3");
								auditTrail.setEventDescription(main.getFullname()+" Submitted to Status: For Approval");
								auditTrailFacade.saveAudit(auditTrail);
							}
							
						}else if(main.getCustomertype().equals("2")) {
							
							auditTrail.setTransactionNumber(cifno);
							auditTrail.setEventType("3");
							auditTrail.setIpaddress(UserUtil.getUserIp());
							
							if (status.equals("1")){
								//Audit Trail TBCODETABLE CODE = AuditTrail
								auditTrail.setEventName("2");
								auditTrail.setEventDescription(main.getFullname()+" Submitted to Status: For Document Submission");
								auditTrailFacade.saveAudit(auditTrail);
							}
							if (status.equals("2")){
								if(docsSubmitted >= totalDocsRequired) {
									//Audit Trail TBCODETABLE CODE = AuditTrail
									auditTrail.setEventName("4");
									auditTrail.setEventDescription(main.getFullname()+" Submitted to Status: For HR Review");
									auditTrailFacade.saveAudit(auditTrail);
								}else {
									//Audit Trail TBCODETABLE CODE = AuditTrail
									auditTrail.setEventName("3");
									auditTrail.setEventDescription(main.getFullname()+" Submitted to Status: For Document Submission");
									auditTrailFacade.saveAudit(auditTrail);
								}
							}
							if (status.equals("3")){
								//Audit Trail TBCODETABLE CODE = AuditTrail
								auditTrail.setEventName("4");
								auditTrail.setEventDescription(main.getFullname()+" Submitted to Status: For Approval");
								auditTrailFacade.saveAudit(auditTrail);
							}						
						}
						

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return flag;
    }
    
    public ReturnForm submitApplication(String flow, String generatedno, String submitoption, String remarks, String boardresno, String boardremarks) {
    	return srvc.submitApplication(flow, generatedno, submitoption, remarks, boardresno, boardremarks);
    }
    
    public ReturnForm submitResignation(String flow, String memberid, String submitoption, String boardremarks, String boardno){
    	return srvc.submitResignation(flow, memberid, submitoption, boardremarks, boardno);
    }
    public Boolean validateInstructionForm(String appno) {
    	return srvc.validateInstructionForm(appno);
    }
    public SubmitOptionForm getSubmitOption(String appno) {
    	return srvc.getSubmitOption(appno);
    }
	public ReturnForm returnApplication(String appno, String returnoption) {
		return srvc.returnApplication(appno, returnoption);
	}
	public String updateAppStatus(String appno, Integer applicationstatus) {
		return srvc.updateAppStatus(appno, applicationstatus);
	}
	public ReturnForm submitInvestigation(String appno, String investigationtype) {
		return srvc.submitInvestigation(appno, investigationtype);
	}
	
	public ReturnForm validate(String membershipappid) {
		ValidateDataEntry validate = new ValidateDataEntry();
		return validate.validateMembershipAppEncoding(membershipappid);
	}
	public List<WorkflowDashboardForm> getActiveWorkflowList(String viewby, String company) {
		return srvc.getActiveWorkflowList(viewby, company);
	}
	public List<WorkflowProcessForm> getStatusByApplicationType(Integer workflowid){
		return srvc.getStatusByApplicationType(workflowid);		
	}
	public String cancelApplication(String appno, Integer appstatus, Boolean iscancelled, String reasonforcancellation){
		return srvc.cancelApplication(appno, appstatus, iscancelled, reasonforcancellation);
	}
	
	//MAR 03-30-2021
    public String workflowRB(String cifno, String status, String remarks){
    	String flag = "failed";
    	Map<String, Object> params = HQLUtil.getMap();
    	DBService dbserviceRB = new DBServiceImpl();
    	params.put("cifno", cifno);
    	String username = UserUtil.securityService.getUserName();
    	try {
//    		codename	codevalue	desc1
//    		CIFSTATUS	0			CANCELLED
//    		CIFSTATUS	1			FOR ENCODING
//    		CIFSTATUS	2			FOR APPROVAL
//    		CIFSTATUS	3			APPROVED
//    		CIFSTATUS	4			FOR EDITING
			if(cifno!=null){
				Tblosmain main = (Tblosmain) dbserviceRB.executeUniqueHQLQuery("FROM Tblosmain WHERE cifno=:cifno",
						params);
				if (main != null) {

					if (status != null) {
						//CANCELLED
						if (status.equals("0")){
							main.setApprovalcode("2");
						}
						//FOR APPROVAL
						if (status.equals("2")){
							//
							if(main.getCifstatus().equals("2")){
								//Edited
								main.setApprovalcode("1");
							}
							if(main.getCifstatus().equals("1")){
								//New
								main.setApprovalcode("0");
							}
							if(main.getCifstatus().equals("4")){
								//Edited
								main.setApprovalcode("1");
							}
						}
						//APPROVED
						if (status.equals("3")){
							main.setCifapprovedby(username);
							main.setCifapproveddate(new Date());
						}
						//FOR EDITING
						if (status.equals("4")){
							main.setApprovalcode("1");
							main.setCifreturnedby(username);
						}
						main.setCifstatus(status);
						main.setDateupdated(new Date());
					}
					if(dbserviceRB.saveOrUpdate(main)){
						flag = "success";
						EmailFacade email = new EmailFacade();
						EmailForm emailform = new EmailForm();
						main = (Tblosmain) dbserviceRB.executeUniqueHQLQuery("FROM Tblosmain WHERE cifno=:cifno", params);
						//FOR APPROVAL
						if(main.getCifstatus().equals("2")){
							//Save Email to TBSMTP
							//emailform.setCifno(cifno);
							//emailform.setEmailcode(EmailCode.SUBMIT_FOR_APPROVAL);
							//email.saveEmailSMTP(emailform);
						}
						
						//APPROVED
						if(main.getCifstatus().equals("3")){
							//Save Email to TBSMTP
							//emailform.setCifno(cifno);
							//emailform.setEmailcode(EmailCode.APPROVED_CIF);
							//email.saveEmailSMTP(emailform);
						}
						
						//FOR EDITING
						if(main.getCifstatus().equals("4")){
							//Save Email to TBSMTP
							//emailform.setCifno(cifno);
							//emailform.setEmailcode(EmailCode.RETURNED_CIF_RECORD);
							//email.saveEmailSMTP(emailform);
						}
						
						//History
						params.put("codevalue", main.getCifstatus());
						Tbcodetable stat = (Tbcodetable)dbserviceRB.executeUniqueHQLQuery("FROM Tbcodetable WHERE id.codename='CIFSTATUS' AND id.codevalue=:codevalue", params);
						HistoryService h = new HistoryServiceImpl();
						if(remarks == null){
							h.addHistory(cifno, "CIF Submitted to Status: " + "<b>" + stat.getDesc1(), null);
						}else{
							h.addHistory(cifno, "CIF Submitted to Status: " + "<b>" + stat.getDesc1(), remarks);						
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return flag;
    }
    //MAR 08-16-2021
	public List<WorkflowProcessForm> getWorkFlow(Integer workflowid){
		return srvc.getWorkFlow(workflowid);		
	}
}
