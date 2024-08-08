package com.etel.dashboard.forms;

import java.util.Date;

public class DashBoardDocumentsForm {

	private String cifno;
	private Date dateofsubmission;
	private String fullaname;
	private String documentname;
	private String customertype;
	private String appno;
	
	public String getCifno() {
		return cifno;
	}
	public void setCifno(String cifno) {
		this.cifno = cifno;
	}
	
	public String getAppno() {
		return appno;
	}
	public void setAppno(String appno) {
		this.appno = appno;
	}
	public Date getDateofsubmission() {
		return dateofsubmission;
	}
	public void setDateofsubmission(Date dateofsubmission) {
		this.dateofsubmission = dateofsubmission;
	}
	public String getFullaname() {
		return fullaname;
	}
	public void setFullaname(String fullaname) {
		this.fullaname = fullaname;
	}
	public String getDocumentname() {
		return documentname;
	}
	public void setDocumentname(String documentname) {
		this.documentname = documentname;
	}
	public String getCustomertype() {
		return customertype;
	}
	public void setCustomertype(String customertype) {
		this.customertype = customertype;
	}
	

}
