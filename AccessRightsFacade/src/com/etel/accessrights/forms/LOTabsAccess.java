package com.etel.accessrights.forms;
/*
 * Daniel - August 24, 2018
 * */
public class LOTabsAccess {
	
	private Boolean comaker = false;
	private Boolean coborrower = false;
	private Boolean collateral = false;
	private Boolean surety = false;
	private Boolean attyinfact = false;
	private Integer reqcomaker;
	private Boolean withReceivable = false;
	
	public Integer getReqcomaker() {
		return reqcomaker;
	}
	public void setReqcomaker(Integer reqcomaker) {
		this.reqcomaker = reqcomaker;
	}
	public Boolean getComaker() {
		return comaker;
	}
	public void setComaker(Boolean comaker) {
		this.comaker = comaker;
	}
	public Boolean getCoborrower() {
		return coborrower;
	}
	public void setCoborrower(Boolean coborrower) {
		this.coborrower = coborrower;
	}
	public Boolean getCollateral() {
		return collateral;
	}
	public void setCollateral(Boolean collateral) {
		this.collateral = collateral;
	}
	public Boolean getSurety() {
		return surety;
	}
	public void setSurety(Boolean surety) {
		this.surety = surety;
	}
	public Boolean getAttyinfact() {
		return attyinfact;
	}
	public void setAttyinfact(Boolean attyinfact) {
		this.attyinfact = attyinfact;
	}
	public Boolean getWithReceivable() {
		return withReceivable;
	}
	public void setWithReceivable(Boolean withReceivable) {
		this.withReceivable = withReceivable;
	}
	

}
