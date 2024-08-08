package com.etel.dashboard.forms;

import java.util.Date;

public class UpdateProfileRequestAssignment {
	
	private String memberid;
	private Integer txrefno;
	private String changecategorytype;
	private String membername;
	private String requestedby;
	private Date daterequested;
	
	public String getMembershipid() {
		return memberid;
	}
	public void setMembershipid(String memberid) {
		this.memberid = memberid;
	}
	public Integer getTxrefno() {
		return txrefno;
	}
	public void setTxrefno(Integer txrefno) {
		this.txrefno = txrefno;
	}
	public String getChangecategory() {
		return changecategorytype;
	}
	public void setChangecategory(String changecategorytype) {
		this.changecategorytype = changecategorytype;
	}
	public String getMembername() {
		return membername;
	}
	public void setMembername(String membername) {
		this.membername = membername;
	}
	public String getRequestedby() {
		return requestedby;
	}
	public void setRequestedby(String requestedby) {
		this.requestedby = requestedby;
	}
	public Date getRequestdate() {
		return daterequested;
	}
	public void setRequestdate(Date daterequested) {
		this.daterequested = daterequested;
	}
	
}
