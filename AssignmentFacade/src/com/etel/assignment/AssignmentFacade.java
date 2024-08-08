package com.etel.assignment;

import java.util.ArrayList;
import java.util.List;

import com.coopdb.data.Tbappraisalreportmain;
import com.coopdb.data.Tbmember;
import com.coopdb.data.Tbuser;
import com.etel.assignment.forms.BIReportAssignmentForm;
import com.etel.assignment.forms.CIFAssignmentForm;
import com.etel.assignment.forms.CIReportAssignmentForm;
import com.etel.assignment.forms.UnassignedAppReview;
import com.etel.forms.TblstappForm;
import com.etel.utils.UserUtil;
import com.isls.document.forms.DocFields;
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
public class AssignmentFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log
	 * level; recommend changing this to FATAL or ERROR before deploying. For
	 * info on these levels, look for tomcat/log4j documentation
	 */

	AssignmentService srvc = new AssignmentServiceImpl();

	public AssignmentFacade() {
		//super(INFO);
	}
	public List<UnassignedAppReview> listUnassignedAppReview(Integer page, Integer maxresult) {
		return srvc.listUnassignedAppReview(page, maxresult);
	}
	
	public Integer listUnassignedAppReviewTotal() {
		return srvc.listUnassignedAppReviewTotal();
	}
	
	public String grabUnassignedApp(String memappid) {
		return srvc.grabUnassignedApp(memappid);
	}
	
	public String batchApproval(String[] forapproval, String approvalstage, String approvalstatus, String remarks, String boardbatchremarks, String boardbatchresno) {
		return srvc.batchApproval(forapproval, approvalstage, approvalstatus, remarks, boardbatchremarks, boardbatchresno);
	}
	public List<TblstappForm> getMyAssignmentAppList (String search, Integer page, Integer maxResult){
		return srvc.getMyAssignmentAppList(search, page, maxResult);
	}
	public Integer getMyAssignmentAppTotal(String search) {
		return srvc.getMyAssignmentAppTotal(search);
	}
	public List<BIReportAssignmentForm> getMyAssignmentBiReportList (String search, Integer page, Integer maxResult){
		List<BIReportAssignmentForm> list = new ArrayList<BIReportAssignmentForm>();
		list = srvc.getMyAssignmentBiReportList(search, page, maxResult);
		return list;
	}
	public Integer getMyAssignmentBiReportTotal(String search) {
		return srvc.getMyAssignmentBiReportTotal(search);
	}
	public List<CIReportAssignmentForm> getMyAssignmentCiReportList (String search, Integer page, Integer maxResult){
		List<CIReportAssignmentForm> list = new ArrayList<CIReportAssignmentForm>();
		list = srvc.getMyAssignmentCiReportList(search, page, maxResult);
		return list;
	}
	public Integer getMyAssignmentCiReportTotal(String search) {
		return srvc.getMyAssignmentCiReportTotal(search);
	}
	public List<Tbappraisalreportmain> getMyAssignmentCaReportList(String search, Integer page, Integer maxResult){
		return srvc.getMyAssignmentCaReportList(search,page,maxResult);
		
	}
	public Integer getMyAssignmentCaReportTotal(String search){
		return srvc.getMyAssignmentCaReportTotal(search);
	}
	
	public boolean hasAccess(String roleid){
		return UserUtil.hasRole(roleid);
	}
	
    public List<Tbmember> searchMember(String search)
    {
    	return srvc.searchMember(search);
    } 
    public List<Tbmember> getAllRecords()
    {
    	return srvc.getAllRecords();
    } 
    public String updateAO(String membershipid,String aocode){
    	return srvc.updateAO(membershipid,aocode);
    }
    
    // 02.09.2023 CIF MANUAL ASSIGNMENT
	public CIFAssignmentForm listCIFAssignmentPerBranch(String search, Integer page, Integer maxResult) {
		return srvc.listCIFAssignmentPerBranch(search, page, maxResult);
	}
    public String assignCIFEncoder (String cifno, String cifencoder) {
    	return srvc.assignCIFEncoder(cifno, cifencoder);
    }
    public List<Tbuser> getCIFEncoder(){
    	return srvc.getCIFEncoder();
    } 
    
}