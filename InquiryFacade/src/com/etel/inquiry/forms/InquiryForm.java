package com.etel.inquiry.forms;

import java.util.Date;

public class InquiryForm {
	
	private int id;
	private String appno;
	private String cifname;
	private String cifno;
	private String loanproduct;
	private String accountofficer;
	private String losoriginatingteam;
	private String companycode;
	private String applicationstatus;
	private String applicationtype;
	private Date datecreated;
	private Date statusdatetime;
	
	
	public String getCifno() {
		return cifno;
	}
	public void setCifno(String cifno) {
		this.cifno = cifno;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAppno() {
		return appno;
	}
	public void setAppno(String appno) {
		this.appno = appno;
	}
	public String getCifname() {
		return cifname;
	}
	public void setCifname(String cifname) {
		this.cifname = cifname;
	}
	public String getLoanproduct() {
		return loanproduct;
	}
	public void setLoanproduct(String loanproduct) {
		this.loanproduct = loanproduct;
	}
	public String getAccountofficer() {
		return accountofficer;
	}
	public void setAccountofficer(String accountofficer) {
		this.accountofficer = accountofficer;
	}
	public String getLosoriginatingteam() {
		return losoriginatingteam;
	}
	public void setLosoriginatingteam(String losoriginatingteam) {
		this.losoriginatingteam = losoriginatingteam;
	}
	public String getCompanycode() {
		return companycode;
	}
	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}
	public String getApplicationstatus() {
		return applicationstatus;
	}
	public void setApplicationstatus(String applicationstatus) {
		this.applicationstatus = applicationstatus;
	}
	public String getApplicationtype() {
		return applicationtype;
	}
	public void setApplicationtype(String applicationtype) {
		this.applicationtype = applicationtype;
	}
	public Date getDatecreated() {
		return datecreated;
	}
	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}
	public Date getStatusdatetime() {
		return statusdatetime;
	}
	public void setStatusdatetime(Date statusdatetime) {
		this.statusdatetime = statusdatetime;
	}
	
	
	

}
