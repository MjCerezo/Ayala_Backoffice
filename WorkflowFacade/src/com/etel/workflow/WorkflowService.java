package com.etel.workflow;


import java.util.List;

import com.etel.forms.ReturnForm;
import com.etel.workflow.forms.SubmitOptionForm;
import com.etel.workflow.forms.WorkflowDashboardForm;
import com.etel.workflow.forms.WorkflowProcessForm;

public interface WorkflowService {

	/**
	 * Submit application for cooperative.
	 * */
	ReturnForm submitApplication(String flow, String generatedno, String submitoption, String remarks, String boardresno, String boardremarks);
	/**
	 * File for resignation.
	 * */
	ReturnForm submitResignation(String flow, String memberid, String submitoption, String boardresno,
			String boardno);
	Boolean validateInstructionForm(String appno);
	SubmitOptionForm getSubmitOption(String appno);
	ReturnForm returnApplication(String appno, String returnoption);
	String updateAppStatus(String appno, Integer applicationstatus);
	ReturnForm submitInvestigation(String appno, String investigationtype);
	List<WorkflowDashboardForm> getActiveWorkflowList(String viewby, String company);
	List<WorkflowProcessForm> getStatusByApplicationType(Integer workflowid);
	String cancelApplication(String appno, Integer appstatus, Boolean iscancelled, String reasonforcancellation);
	List<WorkflowProcessForm> getWorkFlow(Integer workflowid);
}
