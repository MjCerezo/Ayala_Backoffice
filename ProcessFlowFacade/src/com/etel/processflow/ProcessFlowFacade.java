package com.etel.processflow;

import java.util.List;

import com.etel.processflow.form.ProcessFlowForm;
import com.etel.processflow.form.TabAccessForm;
import com.coopdb.data.Tbworkflowprocess;
import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
import com.wavemaker.runtime.service.annotations.ExposeToClient;

/**
 * This is a client-facing service class. All public methods will be exposed to
 * the client. Their return values and parameters will be passed to the client
 * or taken from the client, respectively. This will be a singleton instance,
 * shared between all requests.
 * 
 * To log, call the superclass method log(LOG_LEVEL, String) or log(LOG_LEVEL,
 * String, Exception). LOG_LEVEL is one of FATAL, ERROR, WARN, INFO and DEBUG to
 * modify your log level. For info on these levels, look for tomcat/log4j
 * documentation
 */
@ExposeToClient
public class ProcessFlowFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log
	 * level; recommend changing this to FATAL or ERROR before deploying. For
	 * info on these levels, look for tomcat/log4j documentation
	 */
	public ProcessFlowFacade() {
		super(INFO);
	}

	public List<Tbworkflowprocess> getListProcessflow(Integer workflowid) {
		ProcessFlowService srvc = new ProcessFlowServiceImpl();
		return srvc.getListProcessflow(workflowid);
	}

	/** Add Processflow to TBWORKFLOWPROCESS */
	public String AddPflow(ProcessFlowForm form) {
		ProcessFlowService srvc = new ProcessFlowServiceImpl();
		return srvc.AddPflow(form);
	}

	/** Update Processflow to TBWORKFLOWPROCESS */
	public String updateProcess(ProcessFlowForm form) {
		ProcessFlowService srvc = new ProcessFlowServiceImpl();
		return srvc.updateProcess(form);
	}

	/** Delete Processflow to TBWORKFLOWPROCESS */
	public String deletePflow(ProcessFlowForm form) {
		ProcessFlowService srvc = new ProcessFlowServiceImpl();
		return srvc.deletePflow(form);
	}
	
	public String deleteProcess (ProcessFlowForm form){
		ProcessFlowService srvc = new ProcessFlowServiceImpl();
		return srvc.deleteProcess(form);
	}
	
	public String updateTabAccess (TabAccessForm form){
		ProcessFlowService srvc = new ProcessFlowServiceImpl();
		return srvc.updateTabAccess(form);
	}
	
	public List<ProcessFlowForm> getProcessPerWorkFlowID(String workflowid){
		ProcessFlowService srvc = new ProcessFlowServiceImpl();
		return srvc.getProcessPerWorkFlowID(workflowid);
	}
}
