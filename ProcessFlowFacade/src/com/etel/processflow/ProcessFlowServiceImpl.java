package com.etel.processflow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.processflow.form.ProcessFlowForm;
import com.etel.processflow.form.TabAccessForm;
import com.etel.utils.HQLUtil;
import com.coopdb.data.Tbworkflowprocess;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class ProcessFlowServiceImpl implements ProcessFlowService {

	SecurityService serviceS = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbworkflowprocess> getListProcessflow(Integer workflowid) {
		List<Tbworkflowprocess> pflow = new ArrayList<Tbworkflowprocess>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("workflowid", workflowid);
		try {
			pflow = (List<Tbworkflowprocess>) dbService.executeListHQLQuery("FROM Tbworkflowprocess WHERE workflowid=:workflowid", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pflow;
	}

	@Override
	public String AddPflow(ProcessFlowForm form) {
		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		try {
			System.out.println("SEQUENCENO: "+form.getSequenceno());
			String workflowid = form.getWorkflowid() == null ? null : "'" + form.getWorkflowid() + "'";
			String sequenceno = form.getSequenceno() == null ? null : "'" + form.getSequenceno() + "'";
			String processname = form.getProcessname() == null ? null : "'" + form.getProcessname() + "'";
			String submitcode = form.getSubmitcode() == null ? null : "'" + form.getSubmitcode() + "'";
			String submitoption1 = form.getSubmitoption1() == null ? null : "'" + form.getSubmitoption1() + "'";
			String submitoption2 = form.getSubmitoption2() == null ? null : "'" + form.getSubmitoption2() + "'";
			String submitoption3 = form.getSubmitoption3() == null ? null : "'" + form.getSubmitoption3() + "'";
			String returncode = form.getReturncode() == null ? null : "'" + form.getReturncode() + "'";
			String returnoption = form.getReturnoption() == null ? null : "'" + form.getReturnoption() + "'";
			String iscancelprocess = form.getIscancelprocess() == null ? null : "'" + form.getIscancelprocess() + "'";
			String isrejectprocess = form.getIsrejectprocess() == null ? null : "'" + form.getIsrejectprocess() + "'";
			String isbookprocess = form.getIsbookprocess() == null ? null : "'" + form.getIsbookprocess() + "'";
			String remarks = form.getRemarks() == null ? null : "'" + form.getRemarks() + "'";
			String isvisibleindb = form.getIsvisibleindb() == null ? null : "'" + form.getIsvisibleindb() + "'";
			String createdby = "'" + serviceS.getUserName() + "'";
			String datecreated = "'" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "'";
			
//			System.out.println("query: "+"INSERT INTO TBWORKFLOWPROCESS(workflowid ,processname ,sequenceno,submitcode "
//					+ ",submitoption1,submitoption2 ,"
//					+ "submitoption3,returncode,returnoption,isbookprocess,iscancelprocess,"
//					+ "isrejectprocess ,createdby,datecreated ,isvisibleindb,remarks)"
//					+ "VALUES("+workflowid+","+processname+","+sequenceno+","+submitcode+","+submitoption1+","+submitoption2+","
//					+ ""+submitoption3+","+returncode+","+returnoption+","+isbookprocess+","+iscancelprocess+","
//					+ ""+isrejectprocess+","+createdby+","+datecreated+","+isvisibleindb+","+remarks+")");
//			
			
			int res = dbService.executeUpdate("INSERT INTO TBWORKFLOWPROCESS(workflowid ,processname ,sequenceno,submitcode "
					+ ",submitoption1,submitoption2 ,"
					+ "submitoption3,returncode,returnoption,isbookprocess,iscancelprocess,"
					+ "isrejectprocess ,createdby,datecreated ,isvisibleindb,remarks)"
					+ "VALUES("+workflowid+","+processname+","+sequenceno+","+submitcode+","+submitoption1+","+submitoption2+","
					+ ""+submitoption3+","+returncode+","+returnoption+","+isbookprocess+","+iscancelprocess+","
					+ ""+isrejectprocess+","+createdby+","+datecreated+","+isvisibleindb+","+remarks+")", null);
			if (res > 0) {
				flag = "success";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String updateProcess(ProcessFlowForm form) {
		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("processid", Integer.valueOf(String.valueOf(form.getProcessid())));
		params.put("workflowid", Integer.valueOf(String.valueOf(form.getWorkflowid())));
		params.put("sequenceno", Integer.valueOf(String.valueOf(form.getSequenceno())));
		try {
			Tbworkflowprocess pflow = (Tbworkflowprocess) dbService.executeUniqueHQLQuery(
					"FROM Tbworkflowprocess a WHERE a.id.processid=:processid AND a.id.workflowid=:workflowid AND a.id.sequenceno=:sequenceno",
					params);
			System.out.println(pflow);
			if (pflow != null) {
				pflow.setIsbookprocess(form.getIsbookprocess());
				pflow.setIscancelprocess(form.getIscancelprocess());
				pflow.setIsrejectprocess(form.getIsrejectprocess());
				pflow.setIsvisibleindb(form.getIsvisibleindb());
				pflow.setProcessname(form.getProcessname());
				pflow.setRemarks(form.getRemarks());
				pflow.setReturncode(form.getReturncode());
				if(form.getReturnoption() != null){
					pflow.setReturnoption(Integer.valueOf(String.valueOf(form.getReturnoption())));
				}
				pflow.setSubmitcode(form.getSubmitcode());
				if(form.getSubmitoption1() != null){
					pflow.setSubmitoption1(Integer.valueOf(String.valueOf(form.getSubmitoption1())));
				}else{
					pflow.setSubmitoption1(null);
				}
				if(form.getSubmitoption2() != null){
					pflow.setSubmitoption2(Integer.valueOf(String.valueOf(form.getSubmitoption2())));
				}else{
					pflow.setSubmitoption2(null);
				}
				if(form.getSubmitoption3() != null){
					pflow.setSubmitoption3(Integer.valueOf(String.valueOf(form.getSubmitoption3())));
				}else{
					pflow.setSubmitoption3(null);
				}
				pflow.setDateupdated(new Date());
				pflow.setUpdatedby(serviceS.getUserName());
				if (dbService.saveOrUpdate(pflow)) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String deletePflow(ProcessFlowForm form) {
		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("processid", Integer.valueOf(String.valueOf(form.getProcessid())));
		params.put("workflowid",Integer.valueOf(String.valueOf( form.getWorkflowid())));
		params.put("sequenceno",Integer.valueOf(String.valueOf( form.getSequenceno())));
		Tbworkflowprocess record= new Tbworkflowprocess();
		try {
			record = (Tbworkflowprocess) dbService.executeUniqueHQLQuery("FROM Tbworkflowprocess a WHERE a.id.processid=:processid",
					params);
			System.out.println(record);
			if (dbService.delete(record)) {
				flag = "successful";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String deleteProcess(ProcessFlowForm form) {
		// TODO Auto-generated method stub
		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("processid", Integer.valueOf(String.valueOf(form.getProcessid())));
		
		System.out.println(form.getProcessid());
		Tbworkflowprocess record= new Tbworkflowprocess();
		try {
			record = (Tbworkflowprocess) dbService.executeUniqueHQLQuery("FROM Tbworkflowprocess a WHERE a.id.processid=:processid",
					params);
			System.out.println(record);
			if (dbService.delete(record)) {
				flag = "successful";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String updateTabAccess(TabAccessForm form) {
		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("processid", form.getProcessid());
		params.put("workflowid",form.getWorkflowid());
		params.put("sequenceno",form.getSequenceno());
		try {
			Tbworkflowprocess pflow = (Tbworkflowprocess) dbService.executeUniqueHQLQuery(
					"FROM Tbworkflowprocess a WHERE a.id.processid=:processid AND a.id.workflowid=:workflowid AND a.id.sequenceno=:sequenceno",
					params);
			if(pflow != null){
				pflow.setTabencoding(form.getTabencoding());
			    pflow.setTabinvestigationandappraisal(form.getTabinvestigationandappraisal());
			    pflow.setTabevaluation(form.getTabevaluation());
			    pflow.setTabrecommendation(form.getTabrecommendation());
			    pflow.setTabcreditapproval(form.getTabcreditapproval());
			    pflow.setTabclientacceptance(form.getTabclientacceptance());
			    pflow.setTabdocumentinsurance(form.getTabdocumentinsurance());
			    pflow.setTabreleasingapproval(form.getTabreleasingapproval());
			    pflow.setTabbookandreleasing(form.getTabbookandreleasing());
			    pflow.setTabbooked(form.getTabbooked());
			    pflow.setTabbookeddocpending(form.getTabbookeddocpending());
			    pflow.setTabcancelled(form.getTabcancelled());
			    pflow.setTabrejected(form.getTabrejected());
			    pflow.setTabapprovedlines(form.getTabapprovedlines());
			    pflow.setTabapprovedlinesdocpending(form.getTabapprovedlinesdocpending());
			    pflow.setTabboardapproval(form.getTabboardapproval());
			    pflow.setTablinecreation(form.getTablinecreation());
			    pflow.setTablineupdate(form.getTablineupdate());
			    pflow.setTabnotes(form.getTabnotes());
			    pflow.setTabhistory(form.getTabhistory());
			    if(dbService.saveOrUpdate(pflow)){
			    	flag = "success";
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcessFlowForm> getProcessPerWorkFlowID(String workflowid) {
		// TODO Auto-generated method stub
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("id", Integer.parseInt(workflowid));
			List<ProcessFlowForm> list = (List<ProcessFlowForm>) dbService.execSQLQueryTransformer(
					"SELECT processid, workflowid, processname, sequenceno, submitcode, submitoption1, submitoption2, submitoption3, returncode, returnoption FROM Tbworkflowprocess WHERE workflowid=:id",
					params, ProcessFlowForm.class, 1);
			if (list != null) {
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
