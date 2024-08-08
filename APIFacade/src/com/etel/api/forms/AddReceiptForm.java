package com.etel.api.forms;

import java.util.List;

public class AddReceiptForm {
	private String RefType;
	private String RefNbr;
	private String RefDate;
	private String CustomerID;
	private String Details;
	private String BankID;
	private String Currency;
	private String ExchangeRate;
	private String DocumentStatus;
	private String TotalCollectionAmt;
	private String TaxableAmt;
	private String VATAmt;
	private String VATID;
	private String WTaxID;
	private String VATWHID;
	private String WTaxAmt;
	private String VATWAmt;
	private String NetCollection;
	private String OrigCompanyID;
	
	private List<TransactionDetailsReceipt> TransactionDetails;
	private List<GLEntries> GLEntries;
	private List<PaymentDetails> PaymentDetails;
	
	
	public String getOrigCompanyID() {
		return OrigCompanyID;
	}
	public void setOrigCompanyID(String origCompanyID) {
		OrigCompanyID = origCompanyID;
	}
	public String getRefType() {
		return RefType;
	}
	public void setRefType(String refType) {
		RefType = refType;
	}
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
	public String getCustomerID() {
		return CustomerID;
	}
	public void setCustomerID(String customerID) {
		CustomerID = customerID;
	}
	public String getDetails() {
		return Details;
	}
	public void setDetails(String details) {
		Details = details;
	}
	public String getBankID() {
		return BankID;
	}
	public void setBankID(String bankID) {
		BankID = bankID;
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
	public String getDocumentStatus() {
		return DocumentStatus;
	}
	public void setDocumentStatus(String documentStatus) {
		DocumentStatus = documentStatus;
	}
	public String getTotalCollectionAmt() {
		return TotalCollectionAmt;
	}
	public void setTotalCollectionAmt(String totalCollectionAmt) {
		TotalCollectionAmt = totalCollectionAmt;
	}
	public String getTaxableAmt() {
		return TaxableAmt;
	}
	public void setTaxableAmt(String taxableAmt) {
		TaxableAmt = taxableAmt;
	}
	public String getVATAmt() {
		return VATAmt;
	}
	public void setVATAmt(String vATAmt) {
		VATAmt = vATAmt;
	}
	public String getVATID() {
		return VATID;
	}
	public void setVATID(String vATID) {
		VATID = vATID;
	}
	public String getWTaxID() {
		return WTaxID;
	}
	public void setWTaxID(String wTaxID) {
		WTaxID = wTaxID;
	}
	public String getVATWHID() {
		return VATWHID;
	}
	public void setVATWHID(String vATWHID) {
		VATWHID = vATWHID;
	}
	public String getWTaxAmt() {
		return WTaxAmt;
	}
	public void setWTaxAmt(String wTaxAmt) {
		WTaxAmt = wTaxAmt;
	}
	public String getVATWAmt() {
		return VATWAmt;
	}
	public void setVATWAmt(String vATWAmt) {
		VATWAmt = vATWAmt;
	}
	public String getNetCollection() {
		return NetCollection;
	}
	public void setNetCollection(String netCollection) {
		NetCollection = netCollection;
	}
	public List<TransactionDetailsReceipt> getTransactionDetails() {
		return TransactionDetails;
	}
	public void setTransactionDetails(List<TransactionDetailsReceipt> transactionDetails) {
		TransactionDetails = transactionDetails;
	}
	public List<GLEntries> getGLEntries() {
		return GLEntries;
	}
	public void setGLEntries(List<GLEntries> gLEntries) {
		GLEntries = gLEntries;
	}
	public List<PaymentDetails> getPaymentDetails() {
		return PaymentDetails;
	}
	public void setPaymentDetails(List<PaymentDetails> paymentDetails) {
		PaymentDetails = paymentDetails;
	}
	
	
	
	
}
