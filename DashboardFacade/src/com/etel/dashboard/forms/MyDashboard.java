package com.etel.dashboard.forms;

public class MyDashboard {
	
	private MyMembership membership;
	private MyLoans loans;
	private MyDeposits deposits;
	private MyGeneralLedger generalledger;
	
	public MyMembership getMembership() {
		return membership;
	}
	public MyLoans getLoans() {
		return loans;
	}
	public MyDeposits getDeposits() {
		return deposits;
	}
	public MyGeneralLedger getGeneralledger() {
		return generalledger;
	}
	public void setMembership(MyMembership membership) {
		this.membership = membership;
	}
	public void setLoans(MyLoans loans) {
		this.loans = loans;
	}
	public void setDeposits(MyDeposits deposits) {
		this.deposits = deposits;
	}
	public void setGeneralledger(MyGeneralLedger generalledger) {
		this.generalledger = generalledger;
	}
	
	
}
