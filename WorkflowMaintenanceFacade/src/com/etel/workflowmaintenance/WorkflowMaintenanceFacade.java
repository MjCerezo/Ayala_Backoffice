package com.etel.workflowmaintenance;

import java.util.List;
import com.etel.workflowmaintenance.WorkflowMaintenanceService;
import com.etel.workflowmaintenance.forms.WorkflowMaintenanceForm;
import com.etel.workflowmaintenance.WorkflowMaintenanceServiceImpl;
import com.coopdb.data.Tbworkflow;
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
public class WorkflowMaintenanceFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log
	 * level; recommend changing this to FATAL or ERROR before deploying. For
	 * info on these levels, look for tomcat/log4j documentation
	 */

	public WorkflowMaintenanceFacade() {
		super(INFO);
	}

	/** Get List of Workflow to TBWORKFLOW */
	public List<Tbworkflow> getListWorkflow(Boolean isactive) {
		WorkflowMaintenanceService srvc = new WorkflowMaintenanceServiceImpl();
		return srvc.getListWorkflow(isactive);

	}

	/** Add Workflow to TBWORKFLOW */
	public String AddWflow(WorkflowMaintenanceForm form) {
		WorkflowMaintenanceService srvc = new WorkflowMaintenanceServiceImpl();
		return srvc.AddWflow(form);
	}

	/** Update Workflow to TBWORKFLOW */
	public String updateWflow(WorkflowMaintenanceForm form) {
		WorkflowMaintenanceService srvc = new WorkflowMaintenanceServiceImpl();
		return srvc.updateWflow(form);
	}
	
	/** Delete Workflow to TBWORKFLOW */
	public String deleteWflow(WorkflowMaintenanceForm form) {
		WorkflowMaintenanceService srvc = new WorkflowMaintenanceServiceImpl();
		return srvc.deleteWflow(form);
	}
	
	public Tbworkflow getWorkflowRecord (Integer workflowid){
		WorkflowMaintenanceService srvc = new WorkflowMaintenanceServiceImpl();
		return srvc.getWorkflowRecord(workflowid);
	}

}
