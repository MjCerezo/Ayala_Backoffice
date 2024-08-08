package com.etel.dashboard.forms;

import java.util.Date;

public class DashboardListFormCIF {
	
	private int id;
	private String cifno;
	private String fullname;
	private Date dateofbirth;
	private String cifstatus;
	private String assignedto;
	private String customertype;
	private Date dateofincorporation;
	
	public Date getDateofincorporation() {
		return dateofincorporation;
	}
	public void setDateofincorporation(Date dateofincorporation) {
		this.dateofincorporation = dateofincorporation;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCifno() {
		return cifno;
	}
	public void setCifno(String cifno) {
		this.cifno = cifno;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public Date getDateofbirth() {
		return dateofbirth;
	}
	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	public String getCifstatus() {
		return cifstatus;
	}
	public void setCifstatus(String ciftstatus) {
		this.cifstatus = ciftstatus;
	}
	public String getAssignedto() {
		return assignedto;
	}
	public void setAssignedto(String assignedto) {
		this.assignedto = assignedto;
	}
	public String getCustomertype() {
		return customertype;
	}
	public void setCustomertype(String customertype) {
		this.customertype = customertype;
	}
	
	
}
