package com.etel.inquiry.forms;

import java.util.Date;

public class CIFInquiryForm {
	private String cifno;
	private String fullname;
	private String tin;
	private Date birthdate;
	private Date encodeddate;
	private String encodedby;
	private Date cifapproveddate;
	private String cifapprovedby;
	private String ciftype;
	private String cifstatus;
	private String borrowerfundertype;
	private Date dateofincorporation;
	private String customertype;
	private String fulladdress1;
	private String fulladdress2;
	private String assignedto;
	private String cifpurpose;
	
	public String getTin() {
		return tin;
	}
	public void setTin(String tin) {
		this.tin = tin;
	}
	public String getCifpurpose() {
		return cifpurpose;
	}
	public void setCifpurpose(String cifpurpose) {
		this.cifpurpose = cifpurpose;
	}
	public String getAssignedto() {
		return assignedto;
	}
	public void setAssignedto(String assignedto) {
		this.assignedto = assignedto;
	}
	public String getFulladdress1() {
		return fulladdress1;
	}
	public void setFulladdress1(String fulladdress1) {
		this.fulladdress1 = fulladdress1;
	}
	public String getFulladdress2() {
		return fulladdress2;
	}
	public void setFulladdress2(String fulladdress2) {
		this.fulladdress2 = fulladdress2;
	}
	public String getCustomertype() {
		return customertype;
	}
	public void setCustomertype(String customertype) {
		this.customertype = customertype;
	}
	public String getBorrowerfundertype() {
		return borrowerfundertype;
	}
	public void setBorrowerfundertype(String borrowerfundertype) {
		this.borrowerfundertype = borrowerfundertype;
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
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	public Date getEncodeddate() {
		return encodeddate;
	}
	public void setEncodeddate(Date encodeddate) {
		this.encodeddate = encodeddate;
	}
	public String getEncodedby() {
		return encodedby;
	}
	public void setEncodedby(String encodedby) {
		this.encodedby = encodedby;
	}
	public Date getCifapproveddate() {
		return cifapproveddate;
	}
	public void setCifapproveddate(Date cifapproveddate) {
		this.cifapproveddate = cifapproveddate;
	}
	public String getCifapprovedby() {
		return cifapprovedby;
	}
	public void setCifapprovedby(String cifapprovedby) {
		this.cifapprovedby = cifapprovedby;
	}
	public String getCiftype() {
		return ciftype;
	}
	public void setCiftype(String ciftype) {
		this.ciftype = ciftype;
	}
	public String getCifstatus() {
		return cifstatus;
	}
	public void setCifstatus(String cifstatus) {
		this.cifstatus = cifstatus;
	}
	public Date getDateofincorporation() {
		return dateofincorporation;
	}
	public void setDateofincorporation(Date dateofincorporation) {
		this.dateofincorporation = dateofincorporation;
	}
	
	
}
