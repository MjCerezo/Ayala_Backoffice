package com.etel.loan;

import java.util.ArrayList;
import java.util.List;

import com.coopdb.data.Tbloanevaluationtable;
import com.etel.loanform.LoanAppInquiryForApprovalForm;
import com.etel.loanform.LoanAppInquiryForReleaseForm;
import com.etel.loanform.LoanEvaluationResultForm;
import com.etel.loanform.LoanObligationForm;
import com.etel.loanform.LoanRuleForm;
import com.etel.loanform.MemberLoanEvaluationForm;
import com.etel.loanform.MemberNetCappingForm;
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
public class LoanApplicationFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public LoanApplicationFacade() {
       super(INFO);
    }

    LoanApplicationService srvc = new LoanApplicationServiceImpl();
    
    public List<LoanAppInquiryForApprovalForm> searchLoanApplicationForApproval(String tat, String emp) {
    	List<LoanAppInquiryForApprovalForm> appList = new ArrayList<LoanAppInquiryForApprovalForm>();
    	LoanApplicationService loanappsrvc = new LoanApplicationServiceImpl();
    	try {
    		log(INFO, "Get Details of Membership Application For Approval..");
    		appList = loanappsrvc.searchLoanApplicationForApproval(tat, emp);
    		log(INFO, "Returning... " + appList.size());
    	} catch (Exception e) {
    		log(ERROR, "The getMembershipApplicationPerStatus java service operation has failed", e);
    	}
		return appList;
    }
    
    public List<LoanAppInquiryForReleaseForm> searchLoanApplicationForRelease(String tat, String emp) {
    	List<LoanAppInquiryForReleaseForm> appList = new ArrayList<LoanAppInquiryForReleaseForm>();
    	LoanApplicationService loanappsrvc = new LoanApplicationServiceImpl();
    	try {
    		log(INFO, "Get Details of Membership Application For Release..");
    		appList = loanappsrvc.searchLoanApplicationForRelease(tat, emp);
    		log(INFO, "Returning... " + appList.size());
    	} catch (Exception e) {
    		log(ERROR, "The getMembershipApplicationPerStatus java service operation has failed", e);
    	}
		return appList;
    }
    
    public List<LoanAppInquiryForReleaseForm> searchLoanApplicationForBooking(String tat, String emp) {
    	List<LoanAppInquiryForReleaseForm> appList = new ArrayList<LoanAppInquiryForReleaseForm>();
    	LoanApplicationService loanappsrvc = new LoanApplicationServiceImpl();
    	try {
    		log(INFO, "Get Details of Membership Application For Booking..");
    		appList = loanappsrvc.searchLoanApplicationForBooking(tat, emp);
    		log(INFO, "Returning... " + appList.size());
    	} catch (Exception e) {
    		log(ERROR, "The getMembershipApplicationPerStatus java service operation has failed", e);
    	}
		return appList;
    }   
    
    public List<LoanAppInquiryForApprovalForm> searchLoanApplication(String tat, String emp, Integer status) {
    	List<LoanAppInquiryForApprovalForm> appList = new ArrayList<LoanAppInquiryForApprovalForm>();
    	LoanApplicationService loanappsrvc = new LoanApplicationServiceImpl();
    	try {
    		String statusDes = "";
    		if(status == 1) {
    			statusDes = "For Loan Encoding";
    		}else if(status == 2) {
    			statusDes = "For Document Submission";
    		}else if(status == 3) {
    			statusDes = "For HR Endorsement";
    		}
    		log(INFO, "Get Details of Membership Application " + statusDes + "..");
    		appList = loanappsrvc.searchLoanApplication(tat, emp, status);
    		log(INFO, "Returning... " + appList.size());
    	} catch (Exception e) {
    		log(ERROR, "The getMembershipApplicationPerStatus java service operation has failed", e);
    	}
		return appList;
    }      
    
    public String saveOrUpdateEvaluationTable(Tbloanevaluationtable d) {
		return srvc.saveOrUpdateEvaluationTable(d);
    }       
    
    public LoanRuleForm getLoanRule(String appno) {
    	LoanRuleForm form = new LoanRuleForm();
    	LoanApplicationService loanappsrvc = new LoanApplicationServiceImpl();
    	try {
    		log(INFO, "Get Loan Rule " + appno);
    		form = loanappsrvc.getLoanRule(appno);
    		log(INFO, "Returning Loan Rule >>> Rank... " + form.getRank());
    	} catch (Exception e) {
    		log(ERROR, "The getLoanRule java service operation has failed", e);
    	}
    	return form;
    }   
    
    public MemberLoanEvaluationForm getMemberEvalForm(String appno, String cifno) {
    	MemberLoanEvaluationForm form = new MemberLoanEvaluationForm();
    	LoanApplicationService loanappsrvc = new LoanApplicationServiceImpl();
    	
    	try {
    		log(INFO, "Get Member Evaluation Data " + appno);
    		form = loanappsrvc.getMemberEvalForm(appno, cifno);
    		log(INFO, "Returning Member Evaluation >>> Rank... " + form.getRank());
    	} catch (Exception e) {
    		log(ERROR, "The getMemberEvalForm java service operation has failed", e);
    	}
		return form;
    }   
    
    public LoanEvaluationResultForm getLoanEvaluationResult(String appno, String cifno, LoanRuleForm ruleForm, MemberLoanEvaluationForm memberForm) {
    	LoanEvaluationResultForm form = new LoanEvaluationResultForm();
    	LoanApplicationService loanappsrvc = new LoanApplicationServiceImpl();
    	try {
    		log(INFO, "Get Results Evaluation Data " + appno);
    		form = loanappsrvc.getLoanEvaluationResult(appno, cifno, ruleForm, memberForm);
    		log(INFO, "Returning Results Evaluation >>> Rank... " + form.getRank());
    	} catch (Exception e) {
    		log(ERROR, "The getLoanEvaluationResults java service operation has failed", e);
    	}
		return form;
    }   
    
    public List<Tbloanevaluationtable> listEvaluationTable(String template) {
		return srvc.listEvaluationTable(template);
    }    
    
    public String saveOrUpdateLoanEvaluationResult(String appno, String cifno) {
    	String flag = "failed";
    	LoanApplicationService loanappsrvc = new LoanApplicationServiceImpl();
    	try {
    		log(INFO, "SaveOrUpdate Results Evaluation Data " + appno);
    		flag = loanappsrvc.saveOrUpdateLoanEvaluationResult(appno, cifno);
    		log(INFO, "Returning SaveOrUpdate Results Evaluation >>> Appno... " + appno);
    	} catch (Exception e) {
    		log(ERROR, "The saveOrUpdateLoanEvaluationResults java service operation has failed", e);
    	}
		return flag;
    }     
    
    public MemberNetCappingForm getMemberNetCappingParameters(String appno, String cifno, MemberNetCappingForm f) {
    	MemberNetCappingForm form = new MemberNetCappingForm();
    	LoanApplicationService loanappsrvc = new LoanApplicationServiceImpl();
    	
    	try {
    		log(INFO, "Get Member Net Capping Data " + appno);
    		form = loanappsrvc.getMemberNetCappingParameters(appno, cifno, f);
    		log(INFO, "Returning Member Net Capping >>> Appno... " + appno);
    	} catch (Exception e) {
    		log(ERROR, "The getMemberNetCappingForm java service operation has failed", e);
    	}
		return form;
    }     
    
    public List<LoanObligationForm> listLoanObligation(String appno) {
		return srvc.listLoanObligation(appno);
    }    
       
    
}
