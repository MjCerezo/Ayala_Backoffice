package com.etel.api.forms;

public class PaymentDetails {
	private String PayType;
	private String DraweeBank;
	private String CheckNo;
	private String CheckDate;
	private String Amount;
	
	public String getAmount() {
		return Amount;
	}
	public void setAmount(String amount) {
		Amount = amount;
	}
	public String getPayType() {
		return PayType;
	}
	public void setPayType(String payType) {
		PayType = payType;
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
	public String getCheckDate() {
		return CheckDate;
	}
	public void setCheckDate(String checkDate) {
		CheckDate = checkDate;
	}
	
}