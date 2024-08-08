package com.casa.acct.forms;

import java.util.Date;

public class InquiryCIFNameList {
	
	private String name;
	private String cifno;
	private String custtype;
	private Date dob;
	private String maidenname;
	private String cifstatus;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCifno() {
		return cifno;
	}
	public void setCifno(String cifno) {
		this.cifno = cifno;
	}
	public String getCusttype() {
		return custtype;
	}
	public void setCusttype(String custtype) {
		this.custtype = custtype;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getMaidenname() {
		return maidenname;
	}
	public void setMaidenname(String maidenname) {
		this.maidenname = maidenname;
	}
	public String getCifstatus() {
		return cifstatus;
	}
	public void setCifstatus(String cifstatus) {
		this.cifstatus = cifstatus;
	}
}
