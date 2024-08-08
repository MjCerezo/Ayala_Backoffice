package com.etel.qde;

import java.util.Date;

public class QDEParameters {
	private String firstname;
	private String middlename;
	private String lastname;
	private Date dateofbirth;
	private String applicationtype;
	private String membershipid;
	private String employeeid;
	private String membershipappid;
	private String branchcode;
	private String companycode;
	private String membershiptype;
	private String servicestatus;
	
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
	public Date getDateofbirth() {
		return dateofbirth;
	}
	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	public String getApplicationtype() {
		return applicationtype;
	}
	public void setApplicationtype(String applicationtype) {
		this.applicationtype = applicationtype;
	}
	public String getMembershipid() {
		return membershipid;
	}
	public void setMembershipid(String membershipid) {
		this.membershipid = membershipid;
	}
	public String getEmployeeid() {
		return employeeid;
	}
	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}
	public String getMembershipappid() {
		return membershipappid;
	}
	public void setMembershipappid(String membershipappid) {
		this.membershipappid = membershipappid;
	}
	public String getBranchcode() {
		return branchcode;
	}
	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}
	public String getCompanycode() {
		return companycode;
	}
	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}
	public String getMembershiptype() {
		return membershiptype;
	}
	public void setMembershiptype(String membershiptype) {
		this.membershiptype = membershiptype;
	}
	@Override
	public String toString() {
		return "QDEParameters [firstname=" + firstname + ", middlename=" + middlename + ", lastname=" + lastname
				+ ", dateofbirth=" + dateofbirth + ", applicationtype=" + applicationtype + ", membershipid="
				+ membershipid + ", employeeid=" + employeeid + ", membershipappid=" + membershipappid + ", branchcode="
				+ branchcode + ", companycode=" + companycode + ", membershiptype=" + membershiptype + "]";
	}
	public String getServicestatus() {
		return servicestatus;
	}
	public void setServicestatus(String servicestatus) {
		this.servicestatus = servicestatus;
	}
}
