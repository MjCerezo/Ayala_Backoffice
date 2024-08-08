package com.etel.casareports.form;

import java.math.BigDecimal;
import java.util.Date;

public class CASAExceptionalReportForm {
	//List of Activated Dormant Accounts
	String branch;
	String accountNo;
	String accountName;
	Date txdate;
	String prodName;
	String subProdName;
	BigDecimal outbal;
	String acctsts;
	String acctType;
	String requestBy;
	String overrideBy;
	//List of Accounts with >= P500t Transactions
	String txcode;
	Date txvaldt;
	String transType;
	Date lastTransDate;
	BigDecimal txamount;
	String remarks;
	String createdBy;
	
	
	String paymentMode;
	String paymenType;
	String media;
	String mediaNo;
	String merchant;
	String paymentSlipNo;
	String orNo;

	
	
	//List of Error Corrected Transactions for the Period
	String txrefNo;
	String approvedBy;
	String transStat;
	String prevTransType;
	//List of Rejected Transactions for the Period
	//List of Timeout Transactions WAS
	//List of Override Transactions for the Period (per Teller) 
	
	//getOtherBankReturnCheck
	BigDecimal checkamount;
	Date businessDate;
	String bankName;
	String brstn;
	String checknumber;
	
	//List of Accounts Classified to Dormant for the Day
	
	//List of Force Clear Transactions
	Date clearingdate;

	
	
	
	public Date getTxvaldt() {
		return txvaldt;
	}
	public void setTxvaldt(Date txvaldt) {
		this.txvaldt = txvaldt;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getPaymenType() {
		return paymenType;
	}
	public void setPaymenType(String paymenType) {
		this.paymenType = paymenType;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getAcctType() {
		return acctType;
	}
	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}
	public String getMedia() {
		return media;
	}
	public void setMedia(String media) {
		this.media = media;
	}
	public String getMediaNo() {
		return mediaNo;
	}
	public void setMediaNo(String mediaNo) {
		this.mediaNo = mediaNo;
	}
	public String getMerchant() {
		return merchant;
	}
	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}
	public String getPaymentSlipNo() {
		return paymentSlipNo;
	}
	public void setPaymentSlipNo(String paymentSlipNo) {
		this.paymentSlipNo = paymentSlipNo;
	}
	public String getOrNo() {
		return orNo;
	}
	public void setOrNo(String orNo) {
		this.orNo = orNo;
	}
	public String getPrevTransType() {
		return prevTransType;
	}
	public void setPrevTransType(String prevTransType) {
		this.prevTransType = prevTransType;
	}
	public Date getClearingdate() {
		return clearingdate;
	}
	public void setClearingdate(Date clearingdate) {
		this.clearingdate = clearingdate;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public Date getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBrstn() {
		return brstn;
	}
	public void setBrstn(String brstn) {
		this.brstn = brstn;
	}
	public String getChecknumber() {
		return checknumber;
	}
	public void setChecknumber(String checknumber) {
		this.checknumber = checknumber;
	}
	public BigDecimal getCheckamount() {
		return checkamount;
	}
	public void setCheckamount(BigDecimal checkamount) {
		this.checkamount = checkamount;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public Date getTxdate() {
		return txdate;
	}
	public void setTxdate(Date txdate) {
		this.txdate = txdate;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getSubProdName() {
		return subProdName;
	}
	public void setSubProdName(String subProdName) {
		this.subProdName = subProdName;
	}
	public BigDecimal getOutbal() {
		return outbal;
	}
	public void setOutbal(BigDecimal outbal) {
		this.outbal = outbal;
	}
	public String getAcctsts() {
		return acctsts;
	}
	public void setAcctsts(String acctsts) {
		this.acctsts = acctsts;
	}
	public String getRequestBy() {
		return requestBy;
	}
	public void setRequestBy(String requestBy) {
		this.requestBy = requestBy;
	}
	public String getOverrideBy() {
		return overrideBy;
	}
	public void setOverrideBy(String overrideBy) {
		this.overrideBy = overrideBy;
	}
	public String getTxcode() {
		return txcode;
	}
	public void setTxcode(String txcode) {
		this.txcode = txcode;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public Date getLastTransDate() {
		return lastTransDate;
	}
	public void setLastTransDate(Date lastTransDate) {
		this.lastTransDate = lastTransDate;
	}
	public BigDecimal getTxamount() {
		return txamount;
	}
	public void setTxamount(BigDecimal txamount) {
		this.txamount = txamount;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getTxrefNo() {
		return txrefNo;
	}
	public void setTxrefNo(String txrefNo) {
		this.txrefNo = txrefNo;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public String getTransStat() {
		return transStat;
	}
	public void setTransStat(String transStat) {
		this.transStat = transStat;
	}
	
}
