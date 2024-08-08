package com.etel.dashboard.forms;

public class MyDeposits {
	private Integer cashiering = 0;
	private Integer savings = 0;
	private Integer witdrawal = 0;
	private Integer timedeposits = 0;
	private Integer capitalcontribution = 0;
	
	public Integer getCashiering() {
		return cashiering;
	}
	public Integer getSavings() {
		return savings;
	}
	public Integer getWitdrawal() {
		return witdrawal;
	}
	public Integer getTimedeposits() {
		return timedeposits;
	}
	public Integer getCapitalcontribution() {
		return capitalcontribution;
	}
	public void setCashiering(Integer cashiering) {
		this.cashiering = cashiering;
	}
	public void setSavings(Integer savings) {
		this.savings = savings;
	}
	public void setWitdrawal(Integer witdrawal) {
		this.witdrawal = witdrawal;
	}
	public void setTimedeposits(Integer timedeposits) {
		this.timedeposits = timedeposits;
	}
	public void setCapitalcontribution(Integer capitalcontribution) {
		this.capitalcontribution = capitalcontribution;
	}
}
