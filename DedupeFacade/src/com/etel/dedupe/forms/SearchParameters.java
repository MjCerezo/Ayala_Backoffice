package com.etel.dedupe.forms;

import java.util.Date;

public class SearchParameters {
	
	private String firstname;
	private String lastname;
	private String middlename;
	private Date dateofbirth;
	private String employeeid;
	private String companycode;
	private String referror;
	private String relationship;
	private String referrorcompany;
	
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
	public Date getDateofbirth() {
		return dateofbirth;
	}
	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	public String getEmployeeid() {
		return employeeid;
	}
	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}
	public String getCompanycode() {
		return companycode;
	}
	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}
	public String getReferror() {
		return referror;
	}
	public void setReferror(String referror) {
		this.referror = referror;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	@Override
	public String toString() {
		return "SearchParameters [firstname=" + firstname + ", lastname=" + lastname + ", middlename=" + middlename
				+ ", dateofbirth=" + dateofbirth + ", employeeid=" + employeeid + ", companycode=" + companycode
				+ ", referror=" + referror + ", relationship=" + relationship + "]";
	}
	public String getReferrorcompany() {
		return referrorcompany;
	}
	public void setReferrorcompany(String referrorcompany) {
		this.referrorcompany = referrorcompany;
	}
	
	
}
