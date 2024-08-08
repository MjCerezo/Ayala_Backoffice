package com.etel.teller.form;

import java.math.BigDecimal;

public class TellerForm {
	private String username;
	private String userid;
	private boolean isopen;
	private String branchid;
	private BigDecimal runningbalance;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public boolean isIsopen() {
		return isopen;
	}
	public void setIsopen(boolean isopen) {
		this.isopen = isopen;
	}
	public String getBranchid() {
		return branchid;
	}
	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}
	public BigDecimal getRunningbalance() {
		return runningbalance;
	}
	public void setRunningbalance(BigDecimal runningbalance) {
		this.runningbalance = runningbalance;
	}
}
