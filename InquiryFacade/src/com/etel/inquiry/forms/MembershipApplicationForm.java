package com.etel.inquiry.forms;

import java.util.Date;

public class MembershipApplicationForm {
	private String membershipappid;
	private String firstname;
	private String lastname;
	private String middlename;
	private String employeeid;
	private String originatingbranch;
	private String branchofservice;
	private String membershiptype;
	private String accountofficer;
	private String encoder;
	private Date encodeddate;
	private String applicationstatus;
	
	public String getMembershipappid() {
		return membershipappid;
	}
	public void setMembershipappid(String membershipappid) {
		this.membershipappid = membershipappid;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getMiddlename() {
		return middlename;
	}
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	public String getEmployeeid() {
		return employeeid;
	}
	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}
	public String getOriginatingbranch() {
		return originatingbranch;
	}
	public void setOriginatingbranch(String originatingbranch) {
		this.originatingbranch = originatingbranch;
	}
	public String getBranchofservice() {
		return branchofservice;
	}
	public void setBranchofservice(String branchofservice) {
		this.branchofservice = branchofservice;
	}
	public String getMembershiptype() {
		return membershiptype;
	}
	public void setMembershiptype(String membershiptype) {
		this.membershiptype = membershiptype;
	}
	public String getAccountofficer() {
		return accountofficer;
	}
	public void setAccountofficer(String accountofficer) {
		this.accountofficer = accountofficer;
	}
	public String getEncoder() {
		return encoder;
	}
	public void setEncoder(String encoder) {
		this.encoder = encoder;
	}
	public Date getEncodeddate() {
		return encodeddate;
	}
	public void setEncodeddate(Date encodeddate) {
		this.encodeddate = encodeddate;
	}
	public String getApplicationstatus() {
		return applicationstatus;
	}
	public void setApplicationstatus(String applicationstatus) {
		this.applicationstatus = applicationstatus;
	}
	
}
