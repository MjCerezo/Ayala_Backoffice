package com.etel.dashboard.forms;

import java.util.Date;

public class DashboardListForm {
	private int id;
	private String appno;
	private String cifno;
	private String cifname;
	private String typeofline;
	private String loanproduct;
	private String accountofficer;
	private String losoriginatingteam;
	private String companycode;
	private Object applicationstatus;
	private Object applicationtype;
	private Date datecreated;
	private String existinglinerefno;
	private String existingaccountno;
	private String existingpnno;
	private String customertype;
	
	public String getCifno() {
		return cifno;
	}
	public void setCifno(String cifno) {
		this.cifno = cifno;
	}
	public String getCustomertype() {
		return customertype;
	}
	public void setCustomertype(String customertype) {
		this.customertype = customertype;
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
	public String getTypeofline() {
		return typeofline;
	}
	public void setTypeofline(String typeofline) {
		this.typeofline = typeofline;
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
	public Object getApplicationstatus() {
		return applicationstatus;
	}
	public void setApplicationstatus(Object applicationstatus) {
		this.applicationstatus = applicationstatus;
	}
	public Object getApplicationtype() {
		return applicationtype;
	}
	public void setApplicationtype(Object applicationtype) {
		this.applicationtype = applicationtype;
	}
	public Date getDatecreated() {
		return datecreated;
	}
	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}
	public String getExistinglinerefno() {
		return existinglinerefno;
	}
	public void setExistinglinerefno(String existinglinerefno) {
		this.existinglinerefno = existinglinerefno;
	}
	public String getExistingaccountno() {
		return existingaccountno;
	}
	public void setExistingaccountno(String existingaccountno) {
		this.existingaccountno = existingaccountno;
	}
	public String getExistingpnno() {
		return existingpnno;
	}
	public void setExistingpnno(String existingpnno) {
		this.existingpnno = existingpnno;
	}
	
}
