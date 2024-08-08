package com.casa.util.forms;

import java.util.Date;

public class BranchInfoForm {

	private String brstatus;
	private Date businessdt;
	private Date nxtbusinessdt;
	private boolean branchstatus;
	private String branchclassification;
	
	
	
	public String getBranchclassification() {
		return branchclassification;
	}
	public void setBranchclassification(String branchclassification) {
		this.branchclassification = branchclassification;
	}
	public String getBrstatus() {
		return brstatus;
	}
	public void setBrstatus(String brstatus) {
		this.brstatus = brstatus;
	}
	public Date getBusinessdt() {
		return businessdt;
	}
	public void setBusinessdt(Date businessdt) {
		this.businessdt = businessdt;
	}
	public Date getNxtbusinessdt() {
		return nxtbusinessdt;
	}
	public void setNxtbusinessdt(Date nxtbusinessdt) {
		this.nxtbusinessdt = nxtbusinessdt;
	}
	public boolean isBranchstatus() {
		return branchstatus;
	}
	public void setBranchstatus(boolean branchstatus) {
		this.branchstatus = branchstatus;
	}
	
}
