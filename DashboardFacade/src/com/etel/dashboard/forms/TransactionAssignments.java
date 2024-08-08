package com.etel.dashboard.forms;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionAssignments {
	private String name;
	private String membershipappid;
	private Date applicationdate;
	private String orno;
	private String membershipappstatus;
	private BigDecimal amount;
	private String accountofficer;
	private String encodedby;
	private String membershiptype;
	private String membershipstatus;
	private String servicestatus;

	public String getName() {
		return name;
	}

	public String getMembershipappid() {
		return membershipappid;
	}

	public Date getApplicationdate() {
		return applicationdate;
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

	public String getMembershipappstatus() {
		return membershipappstatus;
	}

	public void setMembershipappstatus(String membershipappstatus) {
		this.membershipappstatus = membershipappstatus;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getOrno() {
		return orno;
	}

	public void setOrno(String orno) {
		this.orno = orno;
	}

	public String getAccountofficer() {
		return accountofficer;
	}

	public void setAccountofficer(String accountofficer) {
		this.accountofficer = accountofficer;
	}

	public String getEncodedby() {
		return encodedby;
	}

	public void setEncodedby(String encodedby) {
		this.encodedby = encodedby;
	}

	public String getMembershiptype() {
		return membershiptype;
	}

	public void setMembershiptype(String membershiptype) {
		this.membershiptype = membershiptype;
	}

	public String getMembershipstatus() {
		return membershipstatus;
	}

	public void setMembershipstatus(String membershipstatus) {
		this.membershipstatus = membershipstatus;
	}

	public String getServicestatus() {
		return servicestatus;
	}

	public void setServicestatus(String servicestatus) {
		this.servicestatus = servicestatus;
	}
}
