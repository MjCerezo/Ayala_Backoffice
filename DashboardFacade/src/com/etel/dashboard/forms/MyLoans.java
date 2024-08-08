package com.etel.dashboard.forms;

public class MyLoans {
	private Integer newloanapp = 0;
	private Integer loanpayments = 0;
	private Integer adjustments = 0;
	private Integer loanforposting = 0;
	
	public Integer getNewloanapp() {
		return newloanapp;
	}
	public Integer getLoanpayments() {
		return loanpayments;
	}
	public Integer getAdjustments() {
		return adjustments;
	}
	public void setNewloanapp(Integer newloanapp) {
		this.newloanapp = newloanapp;
	}
	public void setLoanpayments(Integer loanpayments) {
		this.loanpayments = loanpayments;
	}
	public void setAdjustments(Integer adjustments) {
		this.adjustments = adjustments;
	}
	public Integer getLoanforposting() {
		return loanforposting;
	}
	public void setLoanforposting(Integer loanforposting) {
		this.loanforposting = loanforposting;
	}
}
