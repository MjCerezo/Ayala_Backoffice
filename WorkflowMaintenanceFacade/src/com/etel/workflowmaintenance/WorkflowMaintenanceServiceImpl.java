package com.etel.workflowmaintenance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.company.CompanyServiceImpl;
import com.etel.utils.HQLUtil;
import com.etel.workflowmaintenance.forms.WorkflowMaintenanceForm;
import com.coopdb.data.Tbworkflow;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class WorkflowMaintenanceServiceImpl implements WorkflowMaintenanceService {

	SecurityService serviceS = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private static final Logger logger = Logger.getLogger(CompanyServiceImpl.class);

	@SuppressWarnings("unchecked")
	public List<Tbworkflow> getListWorkflow(Boolean isactive) {
		List<Tbworkflow> wflow = new ArrayList<Tbworkflow>();
		DBService dbService = new DBServiceImpl();

		try {
			wflow = (List<Tbworkflow>) dbService.executeListHQLQuery("FROM Tbworkflow WHERE isactive = 'True'", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wflow;
	}


	public String AddWflow(WorkflowMaintenanceForm form) {

		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		try {
			Tbworkflow flow = new Tbworkflow();
//			flow.setWorkflowid(form.getWorkflowid());
			flow.setWorkflowname(form.getWorkflowname());
			flow.setSequenceno(form.getSequenceno());
			flow.setRemarks(form.getRemarks());
			flow.setCreatedby(serviceS.getUserName());
			flow.setIsactive(form.getIsactive());
			flow.setDatecreated(new Date());
			if (dbService.save(flow)) {
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR" + e.toString());
		}

		return flag;
	}

	@Override
	public String updateWflow(WorkflowMaintenanceForm form) {
		// TODO Auto-generated method stub
		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("workflowid", form.getWorkflowid());

		try {
			Tbworkflow wflow = (Tbworkflow) dbService.executeUniqueHQLQuery(
					"FROM Tbworkflow WHERE workflowid=:workflowid", params);
			if (wflow != null) {
				wflow.setWorkflowname(form.getWorkflowname());
				wflow.setSequenceno(form.getSequenceno());
				wflow.setIsactive(form.getIsactive());
				wflow.setRemarks(form.getRemarks());
				wflow.setDateupdated(new Date());
				wflow.setUpdatedby(serviceS.getUserName());

				if (dbService.update(wflow)) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR" + e.toString());
		}

		return flag;

	}

	@Override
	public String deleteWflow(WorkflowMaintenanceForm form) {
		// TODO Auto-generated method stub
		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("workflowid", form.getWorkflowid());
		Tbworkflow record= new Tbworkflow();
		try {
			record = (Tbworkflow) dbService.executeUniqueHQLQuery("FROM Tbworkflow WHERE workflowid=:workflowid",params);
			if (dbService.delete(record)) {
				flag = "successful";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Tbworkflow getWorkflowRecord(Integer workflowid) {
		// TODO Auto-generated method stub

		Tbworkflow wflow = new Tbworkflow();
		DBService dbService = new DBServiceImpl();

		Map<String, Object> params = HQLUtil.getMap();
		params.put("workflowid", workflowid);
		try {
			wflow = (Tbworkflow) dbService.executeUniqueHQLQuery("FROM Tbworkflow WHERE workflowid=:workflowid",params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wflow;
	}
}
