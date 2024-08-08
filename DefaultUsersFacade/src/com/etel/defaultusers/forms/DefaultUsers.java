package com.etel.defaultusers.forms;

import java.util.Date;
import java.util.Map;

import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;
import com.coopdb.data.Tbdefaultusers;

public class DefaultUsers {

    private String companycode;
    private String bisupervisor;
    private String cisupervisor;
    private String appraisalsupervisor;
    private String evaluatorheadr;
    private String evaluatorheadc;
    private String documentationhead;
    private String bookingofficer;
    private String releasingapprover;
    private String systemadmin;
    private String secadmin;
    private Date dateupdated;
    private String updatedby;
    
    public DefaultUsers(){
    	
    }
    
    
	public DefaultUsers(String company) {
		super();
		DBService dbserviceLOS = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("company", company);
		Tbdefaultusers u = (Tbdefaultusers)dbserviceLOS.executeUniqueHQLQuery("FROM Tbdefaultusers WHERE companycode=:company", params);
		if(company == null){
			new DefaultUsers();
		}
		if(u != null){
			this.companycode = u.getCompanycode();
			this.bisupervisor = u.getBisupervisor();
			this.cisupervisor = u.getCisupervisor();
			this.appraisalsupervisor = u.getAppraisalsupervisor();
			this.evaluatorheadr = u.getEvaluatorheadr();
			this.evaluatorheadc = u.getEvaluatorheadc();
			this.documentationhead = u.getDocumentationhead();
			this.bookingofficer = u.getBookingofficer();
			this.releasingapprover = u.getReleasingapprover();
			this.systemadmin = u.getSystemadmin();
			this.secadmin = u.getSecadmin();
			this.dateupdated = u.getDateupdated();
			this.updatedby = u.getUpdatedby();	
		}else{
			new DefaultUsers();
		}
	}
	
	public String getCompanycode() {
		return companycode;
	}
	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}
	public String getBisupervisor() {
		return bisupervisor;
	}
	public void setBisupervisor(String bisupervisor) {
		this.bisupervisor = bisupervisor;
	}
	public String getCisupervisor() {
		return cisupervisor;
	}
	public void setCisupervisor(String cisupervisor) {
		this.cisupervisor = cisupervisor;
	}
	public String getAppraisalsupervisor() {
		return appraisalsupervisor;
	}
	public void setAppraisalsupervisor(String appraisalsupervisor) {
		this.appraisalsupervisor = appraisalsupervisor;
	}
	public String getEvaluatorheadr() {
		return evaluatorheadr;
	}
	public void setEvaluatorheadr(String evaluatorheadr) {
		this.evaluatorheadr = evaluatorheadr;
	}
	public String getEvaluatorheadc() {
		return evaluatorheadc;
	}
	public void setEvaluatorheadc(String evaluatorheadc) {
		this.evaluatorheadc = evaluatorheadc;
	}
	public String getDocumentationhead() {
		return documentationhead;
	}
	public void setDocumentationhead(String documentationhead) {
		this.documentationhead = documentationhead;
	}
	public String getBookingofficer() {
		return bookingofficer;
	}
	public void setBookingofficer(String bookingofficer) {
		this.bookingofficer = bookingofficer;
	}
	public String getReleasingapprover() {
		return releasingapprover;
	}
	public void setReleasingapprover(String releasingapprover) {
		this.releasingapprover = releasingapprover;
	}
	public String getSystemadmin() {
		return systemadmin;
	}
	public void setSystemadmin(String systemadmin) {
		this.systemadmin = systemadmin;
	}
	public String getSecadmin() {
		return secadmin;
	}
	public void setSecadmin(String secadmin) {
		this.secadmin = secadmin;
	}
	public Date getDateupdated() {
		return dateupdated;
	}
	public void setDateupdated(Date dateupdated) {
		this.dateupdated = dateupdated;
	}
	public String getUpdatedby() {
		return updatedby;
	}
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}
    
    
	
}
