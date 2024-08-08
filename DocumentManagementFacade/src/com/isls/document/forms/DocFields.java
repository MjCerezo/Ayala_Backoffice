package com.isls.document.forms;

import java.sql.Clob;
import java.util.Date;

public class DocFields {
	
	int id;
	String documentname; 
	String remarks;
	Date datereqsubmission;
	Date datesubmitted;
	String membername;
	String doccategory;
	Clob docbasecode;
	String filename;
	String membershipid;
	
	
	


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDocumentname() {
		return documentname;
	}
	public void setDocumentname(String documentname) {
		this.documentname = documentname;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getDatereqsubmission() {
		return datereqsubmission;
	}
	public void setDatereqsubmission(Date datereqsubmission) {
		this.datereqsubmission = datereqsubmission;
	}
	public Date getDatesubmitted() {
		return datesubmitted;
	}
	public void setDatesubmitted(Date datesubmitted) {
		this.datesubmitted = datesubmitted;
	}
	public String getMembername() {
		return membername;
	}
	public void setMembername(String membername) {
		this.membername = membername;
	}
	public String getDoccategory() {
		return doccategory;
	}
	public void setDoccategory(String doccategory) {
		this.doccategory = doccategory;
	}

	public Clob getDocbasecode() {
		return docbasecode;
	}
	public void setDocbasecode(Clob docbasecode) {
		this.docbasecode = docbasecode;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getMembershipid() {
		return membershipid;
	}
	public void setMembershipid(String membershipid) {
		this.membershipid = membershipid;
	}
}
