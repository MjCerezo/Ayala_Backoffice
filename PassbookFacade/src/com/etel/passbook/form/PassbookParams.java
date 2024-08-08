package com.etel.passbook.form;

import java.math.BigDecimal;
import java.util.Date;

public class PassbookParams {
	String passbookType;
	String accountNo;
	String totalLineNumber;
	String lineno;
	
	public String getPassbookType() {
		return passbookType;
	}
	public void setPassbookType(String passbookType) {
		this.passbookType = passbookType;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getTotalLineNumber() {
		return totalLineNumber;
	}
	public void setTotalLineNumber(String totalLineNumber) {
		this.totalLineNumber = totalLineNumber;
	}
	public String getLineno() {
		return lineno;
	}
	public void setLineno(String lineno) {
		this.lineno = lineno;
	}
	
	
}
