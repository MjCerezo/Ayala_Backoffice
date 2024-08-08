package com.etel.processflow;

import java.util.List;

import com.etel.processflow.form.ProcessFlowForm;
import com.etel.processflow.form.TabAccessForm;
import com.coopdb.data.Tbworkflowprocess;

public interface ProcessFlowService {

	List<Tbworkflowprocess> getListProcessflow(Integer workflowid);

	String AddPflow(ProcessFlowForm form);

	String updateProcess(ProcessFlowForm form);

	String deletePflow(ProcessFlowForm form);

	String deleteProcess(ProcessFlowForm form);

	String updateTabAccess(TabAccessForm form);

	List<ProcessFlowForm> getProcessPerWorkFlowID(String workflowid);

}
