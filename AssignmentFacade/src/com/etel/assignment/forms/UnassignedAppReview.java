package com.etel.assignment.forms;

import java.util.Date;

public class UnassignedAppReview {
	private String name;
	private String membershipappid;
	private Date applicationdate;
	private String encodedby;
	private Date encodeddate;
	
	public String getName() {
		return name;
	}
	public String getMembershipappid() {
		return membershipappid;
	}
	public Date getApplicationdate() {
		return applicationdate;
	}
	public String getEncodedby() {
		return encodedby;
	}
	public Date getEncodeddate() {
		return encodeddate;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setMembershipappid(String membershipappid) {
		this.membershipappid = membershipappid;
	}
	public void setApplicationdate(Date applicationdate) {
		this.applicationdate = applicationdate;
	}
	public void setEncodedby(String encodedby) {
		this.encodedby = encodedby;
	}
	public void setEncodeddate(Date encodeddate) {
		this.encodeddate = encodeddate;
	}
}
