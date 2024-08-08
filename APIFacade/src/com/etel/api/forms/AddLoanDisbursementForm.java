package com.etel.api.forms;

import java.util.List;

public class AddLoanDisbursementForm {
	private String RefType;
	private String RefNbr;
	private String RefDate;
	private String VendorID;
	private String VendorName;
	private String TotalTaxableAmt;
	private String TotalVATAmt;
	private String TotalWTaxAmt;
	private String TotalVATWHAmt;
	private String TotalAmt;
	private String OrigCompanyID;
	
	private List<TransactionDetails> TransactionDetails;
	private List<PDC> PDC;
	private List<GLEntries> GLEntries;
	
	public List<GLEntries> getGLEntries() {
		return GLEntries;
	}
	public void setGLEntries(List<GLEntries> gLEntries) {
		GLEntries = gLEntries;
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
	public String getVendorID() {
		return VendorID;
	}
	public void setVendorID(String vendorID) {
		VendorID = vendorID;
	}
	public String getVendorName() {
		return VendorName;
	}
	public void setVendorName(String vendorName) {
		VendorName = vendorName;
	}
	public String getTotalTaxableAmt() {
		return TotalTaxableAmt;
	}
	public void setTotalTaxableAmt(String totalTaxableAmt) {
		TotalTaxableAmt = totalTaxableAmt;
	}
	public String getTotalVATAmt() {
		return TotalVATAmt;
	}
	public void setTotalVATAmt(String totalVATAmt) {
		TotalVATAmt = totalVATAmt;
	}
	public String getTotalWTaxAmt() {
		return TotalWTaxAmt;
	}
	public void setTotalWTaxAmt(String totalWTaxAmt) {
		TotalWTaxAmt = totalWTaxAmt;
	}
	public String getTotalVATWHAmt() {
		return TotalVATWHAmt;
	}
	public void setTotalVATWHAmt(String totalVATWHAmt) {
		TotalVATWHAmt = totalVATWHAmt;
	}
	public String getTotalAmt() {
		return TotalAmt;
	}
	public void setTotalAmt(String totalAmt) {
		TotalAmt = totalAmt;
	}
	public String getOrigCompanyID() {
		return OrigCompanyID;
	}
	public void setOrigCompanyID(String origCompanyID) {
		OrigCompanyID = origCompanyID;
	}
	public List<TransactionDetails> getTransactionDetails() {
		return TransactionDetails;
	}
	public void setTransactionDetails(List<TransactionDetails> transactionDetails) {
		TransactionDetails = transactionDetails;
	}
	public List<PDC> getPDC() {
		return PDC;
	}
	public void setPDC(List<PDC> pDC) {
		PDC = pDC;
	}
	
	
}
