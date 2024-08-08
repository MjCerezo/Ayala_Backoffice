package com.etel.api.forms;

public class TransactionDetailsJournal {
	private String LineDetails;
	private String AccountCode;
	private String SubAccountCode;
	private String Debit;
	private String Credit;
	
	public String getLineDetails() {
		return LineDetails;
	}
	public void setLineDetails(String lineDetails) {
		LineDetails = lineDetails;
	}
	public String getAccountCode() {
		return AccountCode;
	}
	public void setAccountCode(String accountCode) {
		AccountCode = accountCode;
	}
	public String getSubAccountCode() {
		return SubAccountCode;
	}
	public void setSubAccountCode(String subAccountCode) {
		SubAccountCode = subAccountCode;
	}
	public String getDebit() {
		return Debit;
	}
	public void setDebit(String debit) {
		Debit = debit;
	}
	public String getCredit() {
		return Credit;
	}
	public void setCredit(String credit) {
		Credit = credit;
	}
	
	
}