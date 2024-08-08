package com.etel.api.forms;

import java.util.List;

public class AddJournalForm {
	private String RefNbr;
	private String RefDate;
	private String DocumentStatus;
	private String Details;
	private String BranchID;
	private String Currency;
	private String ExchangeRate;
	
	private List<TransactionDetailsJournal> TransactionDetails;

	public String getRefNbr() {
		return RefNbr;
	}

	public void setRefNbr(String refNbr) {
		RefNbr = refNbr;
	}

	public String getRefDate() {
		return RefDate;
	}

	public void setRefDate(String refDate) {
		RefDate = refDate;
	}

	public String getDocumentStatus() {
		return DocumentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		DocumentStatus = documentStatus;
	}

	public String getDetails() {
		return Details;
	}

	public void setDetails(String details) {
		Details = details;
	}

	public String getBranchID() {
		return BranchID;
	}

	public void setBranchID(String branchID) {
		BranchID = branchID;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

	public String getExchangeRate() {
		return ExchangeRate;
	}

	public void setExchangeRate(String exchangeRate) {
		ExchangeRate = exchangeRate;
	}

	public List<TransactionDetailsJournal> getTransactionDetails() {
		return TransactionDetails;
	}

	public void setTransactionDetails(List<TransactionDetailsJournal> transactionDetails) {
		TransactionDetails = transactionDetails;
	}
	
}
