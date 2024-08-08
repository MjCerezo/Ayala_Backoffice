package com.etel.managementforms;

import java.util.Date;

public class ManagementForm {
	
	private String cifno;
	private String lname;
	private String fname;
	private String mname;
	private String suffix;
	private Date date;
	private String pcode;	
	private String relationcifno;	
	private String nationality;
	private String businesstype;
	
	// 11-09-17
	private String customertype;
	private String corporatename;
	private Date dateofincorporation;
	
	public String getCustomertype() {
		return customertype;
	}
	public void setCustomertype(String customertype) {
		this.customertype = customertype;
	}
	public String getCorporatename() {
		return corporatename;
	}
	public void setCorporatename(String corporatename) {
		this.corporatename = corporatename;
	}
	public Date getDateofincorporation() {
		return dateofincorporation;
	}
	public void setDateofincorporation(Date dateofincorporation) {
		this.dateofincorporation = dateofincorporation;
	}
	public String getBusinesstype() {
		return businesstype;
	}
	public void setBusinesstype(String businesstype) {
		this.businesstype = businesstype;
	}
	public String getCifno() {
		return cifno;
	}
	public void setCifno(String cifno) {
		this.cifno = cifno;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	public String getRelationcifno() {
		return relationcifno;
	}
	public void setRelationcifno(String relationcifno) {
		this.relationcifno = relationcifno;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
}
