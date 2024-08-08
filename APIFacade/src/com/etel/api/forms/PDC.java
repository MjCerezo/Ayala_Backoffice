package com.etel.api.forms;

public class PDC {
	private String DraweeBank;
	private String CheckNo;
	private String Amount;
	private String CheckType;
	private String CheckDate;
	
	public String getCheckDate() {
		return CheckDate;
	}
	public void setCheckDate(String checkDate) {
		CheckDate = checkDate;
	}
	public String getDraweeBank() {
		return DraweeBank;
	}
	public void setDraweeBank(String draweeBank) {
		DraweeBank = draweeBank;
	}
	public String getCheckNo() {
		return CheckNo;
	}
	public void setCheckNo(String checkNo) {
		CheckNo = checkNo;
	}
	public String getAmount() {
		return Amount;
	}
	public void setAmount(String amount) {
		Amount = amount;
	}
	public String getCheckType() {
		return CheckType;
	}
	public void setCheckType(String checkType) {
		CheckType = checkType;
	}
	
}