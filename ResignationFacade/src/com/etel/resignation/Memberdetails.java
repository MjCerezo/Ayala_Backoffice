package com.etel.resignation;

import java.util.Date;

public class Memberdetails {
	private String firstname;
	private Date effectivitydate;
	private Boolean cooponly;
	private Boolean bothcoopandcompany;
	private String membershipid;
	private String message;
	private Boolean resigning;
	private String membershipclass;
	private Date membershipdate;
	private String membershipstatus;
	private String cooperative;
	private String chapter;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String fistname) {
		this.firstname = fistname;
	}

	public Date getEffectivitydate() {
		return effectivitydate;
	}

	public void setEffectivitydate(Date effectivitydate) {
		this.effectivitydate = effectivitydate;
	}

	public boolean isCooponly() {
		return cooponly;
	}

	public void setCooponly(boolean cooponly) {
		this.cooponly = cooponly;
	}

	public boolean isCoopandcompany() {
		return bothcoopandcompany;
	}

	public void setCoopandcompany(boolean coopandcompany) {
		this.bothcoopandcompany = coopandcompany;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMembershipid() {
		return membershipid;
	}

	public void setMembershipid(String membershipid) {
		this.membershipid = membershipid;
	}

	public Boolean getResigning() {
		return resigning;
	}

	public void setResigning(Boolean resigning) {
		this.resigning = resigning;
	}

	public String getChapter() {
		return chapter;
	}

	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	public String getCooperative() {
		return cooperative;
	}

	public void setCooperative(String cooperative) {
		this.cooperative = cooperative;
	}

	public String getMembershipstatus() {
		return membershipstatus;
	}

	public void setMembershipstatus(String membershipstatus) {
		this.membershipstatus = membershipstatus;
	}

	public Date getMembershipdate() {
		return membershipdate;
	}

	public void setMembershipdate(Date membershipdate) {
		this.membershipdate = membershipdate;
	}

	public String getMembershipclass() {
		return membershipclass;
	}

	public void setMembershipclass(String membershipclass) {
		this.membershipclass = membershipclass;
	}

}
