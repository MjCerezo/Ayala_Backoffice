package com.casa.misc.forms;

import java.math.BigDecimal;

public class AcctDetailsForm {
	
	private String accountNo;
	private String accountName;
	private int result;
	private BigDecimal accountBalance;
	private String accountStatus;
	
	
	public String getAccountNo() {
		return accountNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public int getResult() {
		return result;
	}
	public BigDecimal getAccountBalance() {
		return accountBalance;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	
	

}
