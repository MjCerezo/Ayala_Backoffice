package com.etel.ci;

import java.util.Date;
import java.util.List;

import com.coopdb.data.Tbcibankcheck;
import com.coopdb.data.Tbcibvr;
import com.coopdb.data.Tbcicreditcheck;
import com.coopdb.data.Tbcidependents;
import com.coopdb.data.Tbcievr;
import com.coopdb.data.Tbcipdrn;
import com.coopdb.data.Tbcipdrnresidence;
import com.coopdb.data.Tbcipdrnverhighlights;
import com.coopdb.data.Tbcireportmain;
import com.coopdb.data.Tbcirequest;
import com.coopdb.data.Tbcitradecheck;
import com.coopdb.data.Tbcitraderefcorp;
import com.coopdb.data.Tbdeskciactivity;
import com.coopdb.data.Tbdeskcidetails;
import com.etel.ci.forms.CIAppDetails;
import com.etel.ci.forms.CIEvalForm;
import com.etel.ci.forms.CIFRecord;
import com.etel.ci.forms.CIReportAccessRights;
import com.etel.ci.forms.CIRequestAccessRights;
import com.etel.ci.forms.CreditInvestigator;
import com.etel.ci.report.CIReportService;
import com.etel.ci.report.CIReportServiceImpl;
import com.etel.ci.report.desk.EvrBvrActivities;
import com.etel.ci.report.field.PDRNField;
import com.etel.utils.DateTimeUtil;
import com.etel.utils.UserUtil;
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
public class CreditInvestigationFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public CreditInvestigationFacade() {
       super(INFO);
    }
	CreditInvestigationService service = new CreditInvestigationServiceImpl();
	CIReportService cirptsrvc = new CIReportServiceImpl();
	
	public int age(Date dob){
		if(dob != null){
			int age = DateTimeUtil.getAge(dob);	
			return age;
		}
		return 0;
	}
	
	public String saveUpdateCIRequest(Tbcirequest request) {
		return service.saveUpdateCIRequest(request);
	}
	
	public Tbcirequest getCIRequest(String requestID) {
		return service.getCIRequest(requestID);
	}
	
    public CIFRecord getCIFRecord(String cifno) {
    	return service.getCIFRecord(cifno);
    }
    
    public String submitCIRequest(String requestID, String status) {
    	return service.submitCIRequest(requestID, status);
    }
    
    public List<CreditInvestigator> listCI() {
    	return service.listCI();
    }
    
    public String fullnameOfRequestedBy(String username){
    	return UserUtil.getUserFullname(username);
    }
    
    public CIAppDetails getAppdetailByAppno(String appno) {
    	return service.getAppdetailByAppno(appno);
    }
    
    public boolean isCISupervisorByCompanycode(String companycode) {
    	return service.isCISupervisorByCompanycode(companycode);
    }
    public List<Tbcirequest>  getListofCiRequest(String appno, String lastname, String firstname,
			String middlename, String corporatename, String customertype, String cirequestid, String cifno, String requeststatus,
			Integer page, Integer maxResult, String assigneduser, Boolean viewflag) {
		return service.getListofCiRequest(appno, lastname, firstname, middlename, corporatename, customertype, cirequestid,
				cifno, requeststatus, page, maxResult, assigneduser, viewflag);
	}
    public int getCiRequestTotal(String appno, String lastname, String firstname,
			String middlename, String corporatename, String customertype, String cirequestid, String cifno, String requeststatus,
			Integer page, Integer maxResult, String assigneduser, Boolean viewflag) {
		return service.getCiRequestTotal(appno, lastname, firstname, middlename, corporatename, customertype, cirequestid,
				cifno, requeststatus, assigneduser,  viewflag);
	}
    
    public CIRequestAccessRights ciRequestAccessRigths(String requestid, String appno){
    	CIRequestAccessRights ar = new CIRequestAccessRights(requestid, appno);
    	return ar;
    }
    
    public String createReport(Tbcireportmain rptmain) {
    	return cirptsrvc.createReport(rptmain);
    }
    
    public List<Tbdeskcidetails> getDeskCIDetails(String rptid, String activity, Integer emporbusid) {
    	return cirptsrvc.getDeskCIDetails(rptid, activity, emporbusid);
    }
    
    public String saveOrUpdateDeskCIDetails(Tbdeskcidetails d) {
    	return cirptsrvc.saveOrUpdateDeskCIDetails(d);
    }
    
    public String saveOrUpdateDeskCIActivity(Tbdeskciactivity ciactivity) {
    	return cirptsrvc.saveOrUpdateDeskCIActivity(ciactivity);
    }
    
    public List<EvrBvrActivities> listEvrBvrActivities(String rptid, String activitytype) {
    	return cirptsrvc.listEvrBvrActivities(rptid, activitytype);
    }
    
    public Tbdeskciactivity getDeskCIActivity(String rptid, String activity, Integer activityID) {
    	return cirptsrvc.getDeskCIActivity(rptid, activity, activityID);
    }
    public Tbcireportmain getCIReport(String rptID) {
    	return cirptsrvc.getCIReport(rptID);
    }
    public String getCIFNoByReportID(String rptID){
		return service.getCIFNoByReportID(rptID);
    }
    
    public String saveOrUpdateBankCheck(Tbcibankcheck bank, Tbdeskciactivity act) {
    	return cirptsrvc.saveOrUpdateBankCheck(bank, act);
    }
    
    public String saveOrUpdateCreditCheck(Tbcicreditcheck credit, Tbdeskciactivity act) {
    	return cirptsrvc.saveOrUpdateCreditCheck(credit, act);
    }
    
    public String saveOrUpdateTradeCheck(Tbcitradecheck trade, Tbdeskciactivity act) {
    	return cirptsrvc.saveOrUpdateTradeCheck(trade, act);
    }
    
    public List<Tbcibankcheck> listBankCheck(String rptid) {
    	return cirptsrvc.listBankCheck(rptid);
    }
    
    public List<Tbcicreditcheck> listCreditCheck(String rptid) {
    	return cirptsrvc.listCreditCheck(rptid);
    }
    
    public List<Tbcitradecheck> listTradeCheck(String rptid) {
    	return cirptsrvc.listTradeCheck(rptid);
    }
    
    public String saveOrUpdatePDRNField(Tbcipdrn pdrn, Tbcipdrnresidence res, Tbcipdrnverhighlights h, String rptid) {
    	return cirptsrvc.saveOrUpdatePDRNField(pdrn, res, h, rptid);
    }
    
    public PDRNField getPDRNField(String rptid) {
    	return cirptsrvc.getPDRNField(rptid);
    }
    
    public List<Tbcidependents> listDependents(String rptid) {
    	return cirptsrvc.listDependents(rptid);
    }
    
    public String saveOrUpdateDependents(Tbcidependents dep) {
    	return cirptsrvc.saveOrUpdateDependents(dep);
    }
    
    public String saveOrUpdateEVRField(Tbcievr evr) {
    	return cirptsrvc.saveOrUpdateEVRField(evr);
    }
    
    public String saveOrUpdateBVRField(Tbcibvr bvr) {
    	return cirptsrvc.saveOrUpdateBVRField(bvr);
    }
    
    public List<Tbcievr> listEVRField(String rptid) {
    	return cirptsrvc.listEVRField(rptid);
    }
    
    public List<Tbcibvr> listBVRField(String rptid) {
    	return cirptsrvc.listBVRField(rptid);
    }
    
    public String deleteItem(Integer dependentsID, Integer employmentID, Integer businessID, Integer traderefID) {
    	return cirptsrvc.deleteItem(dependentsID, employmentID, businessID, traderefID);
    }
    
    public Tbcievr getEVRField(Integer eid) {
    	return cirptsrvc.getEVRField(eid);
    }
    
    public Tbcibvr getBVRField(Integer bid, String rptid) {
    	return cirptsrvc.getBVRField(bid, rptid);
    }
    
    public List<Tbcitraderefcorp> listTradeReference(String rptid) {
    	return cirptsrvc.listTradeReference(rptid);
    }
    
    public String saveOrUpdateTradeReference(Tbcitraderefcorp t) {
    	return cirptsrvc.saveOrUpdateTradeReference(t);
    }
    
    public String submitCIReport(String rptstatus, String rptid, String reasonforreturn) {
    	return cirptsrvc.submitCIReport(rptstatus, rptid, reasonforreturn);
    }
    
    public CIReportAccessRights ciReportAccessRights(String rptid){
    	CIReportAccessRights ar = new CIReportAccessRights(rptid);
    	return ar;
    }
    
    public List<Tbcireportmain> getCiReportListByAppno(String appno) {
    	return cirptsrvc.getCiReportListByAppno(appno);
    }
    
    public List<Tbcireportmain> getCiReportListByRequestId(String rqstid) {
    	return cirptsrvc.getCiReportListByRequestId(rqstid);
    }
    public String deleteCIDeskItem(Integer bankchkID, Integer creditchkID, Integer tradechkID) {
    	return cirptsrvc.deleteCIDeskItem(bankchkID, creditchkID, tradechkID);
    }
    public List<Tbdeskcidetails> getDeskCIDetailsPDRN(String rptid, Integer emporbusid) {
    	return cirptsrvc.getDeskCIDetailsPDRN(rptid,  emporbusid);
    }
    public List<Tbdeskcidetails> getDeskCIDetailsEVR(String rptid, Integer emporbusid) {
    	return cirptsrvc.getDeskCIDetailsEVR(rptid, emporbusid);
    }
    public List<Tbdeskcidetails> getDeskCIDetailsBVR(String rptid, Integer emporbusid) {
    	return cirptsrvc.getDeskCIDetailsBVR(rptid, emporbusid);
    }
    public List<CIEvalForm> getAllCIReportperAppNo(String appno, Integer evalreportid) {
    	return service.getAllCIReportperAppNo(appno, evalreportid);
    }
    //MAR
    public String setEvalIDForCIEvalFromEval(String appno){
    	return cirptsrvc.setEvalIDForCIEvalFromEval(appno);
    }  
}
