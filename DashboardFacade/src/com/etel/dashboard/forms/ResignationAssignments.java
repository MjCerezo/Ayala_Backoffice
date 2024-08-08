package com.etel.dashboard.forms;

import java.util.Date;

public class ResignationAssignments {
	private String membershipid;
	private String firstname;
	private Date creationdate;
	private String createdby;
    private Integer txrefno;
    
	public String getMembershipid() {
		return membershipid;
	}
	public void setMembershipid(String membershipid) {
		this.membershipid = membershipid;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public Date getCreationdate() {
		return creationdate;
	}
	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public Integer getTxrefno() {
		return txrefno;
	}
	public void setTxrefno(Integer txrefno) {
		this.txrefno = txrefno;
	}
	
	

}
