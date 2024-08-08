package com.etel.workflowmaintenance;

import java.util.List;

import com.etel.workflowmaintenance.forms.WorkflowMaintenanceForm;
import com.coopdb.data.Tbworkflow;

public interface WorkflowMaintenanceService {

	List<Tbworkflow> getListWorkflow(Boolean isactive);

	String AddWflow(WorkflowMaintenanceForm form);

	String updateWflow(WorkflowMaintenanceForm form);

	String deleteWflow(WorkflowMaintenanceForm form);

	Tbworkflow getWorkflowRecord(Integer workflowid);

}
