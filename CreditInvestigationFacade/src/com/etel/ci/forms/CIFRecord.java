package com.etel.ci.forms;

import java.util.Date;

public class CIFRecord {
	
	private String cifno;
	private String customername;
	private String customertype;
	private String firstname;
	private String middlename;
	private String lastname;
	private String suffix;
	private Date dateofbirth;
	private Date dateofincorporation;
	private String cifstatus;
	
	
	public String getCifstatus() {
		return cifstatus;
	}
	public void setCifstatus(String cifstatus) {
		this.cifstatus = cifstatus;
	}
	public String getCifno() {
		return cifno;
	}
	public void setCifno(String cifno) {
		this.cifno = cifno;
	}
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public String getCustomertype() {
		return customertype;
	}
	public void setCustomertype(String customertype) {
		this.customertype = customertype;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getMiddlename() {
		return middlename;
	}
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public Date getDateofbirth() {
		return dateofbirth;
	}
	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	public Date getDateofincorporation() {
		return dateofincorporation;
	}
	public void setDateofincorporation(Date dateofincorporation) {
		this.dateofincorporation = dateofincorporation;
	}
	
	
}
