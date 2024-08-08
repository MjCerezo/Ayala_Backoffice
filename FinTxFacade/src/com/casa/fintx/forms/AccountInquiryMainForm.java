package com.casa.fintx.forms;

import java.util.List;

public class AccountInquiryMainForm { 

	private AccountInquiryForm inquiry;
	private AccountInquiryForm atainq;
	private List<AccountInquiryJournalForm> histlist;
	private String result;
	
	public AccountInquiryForm getInquiry() {
		return inquiry;
	}
	public void setInquiry(AccountInquiryForm inquiry) {
		this.inquiry = inquiry;
	}
	public AccountInquiryForm getAtainq() {
		return atainq;
	}
	public void setAtainq(AccountInquiryForm atainq) {
		this.atainq = atainq;
	}
	public List<AccountInquiryJournalForm> getHistlist() {
		return histlist;
	}
	public void setHistlist(List<AccountInquiryJournalForm> histlist) {
		this.histlist = histlist;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

}
