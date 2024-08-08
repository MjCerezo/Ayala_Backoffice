package com.etel.lmsreport.form;

import java.math.BigDecimal;
import java.util.Date;

public class LMSExceptionReportForm {

	Date transactionValueDate;
	String transactionRefNo;
	String transactioncode;
	String pnNo;
	String loanAcctNo;
	String acctName;
	BigDecimal transactionAmount;
	String transtat;
	String solicitingOfficer;
	String particulars;
	String branch;
	
	//LMS_List of Waived Interests, Penalties & Other Charges
	Date transDate;
	String transType;
	BigDecimal amountWaived;
	String loanProduct;
	String postedBy;
	String approvedBy;
	
	//FATCA
	Date dateReleased;
	BigDecimal amtReleased;
	BigDecimal intRate;
	Date maturityDate;
	String status;
	String q1;
	String q2;
	String q3;
	String q4;
	BigDecimal outBal;
	
	//DOSRI
	String positionRank;
	
	
	
	public Date getDateReleased() {
		return dateReleased;
	}
	public void setDateReleased(Date dateReleased) {
		this.dateReleased = dateReleased;
	}
	public BigDecimal getAmtReleased() {
		return amtReleased;
	}
	public void setAmtReleased(BigDecimal amtReleased) {
		this.amtReleased = amtReleased;
	}
	public BigDecimal getIntRate() {
		return intRate;
	}
	public void setIntRate(BigDecimal intRate) {
		this.intRate = intRate;
	}
	public Date getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getQ1() {
		return q1;
	}
	public void setQ1(String q1) {
		this.q1 = q1;
	}
	public String getQ2() {
		return q2;
	}
	public void setQ2(String q2) {
		this.q2 = q2;
	}
	public String getQ3() {
		return q3;
	}
	public void setQ3(String q3) {
		this.q3 = q3;
	}
	public String getQ4() {
		return q4;
	}
	public void setQ4(String q4) {
		this.q4 = q4;
	}
	public BigDecimal getOutBal() {
		return outBal;
	}
	public void setOutBal(BigDecimal outBal) {
		this.outBal = outBal;
	}
	public String getPositionRank() {
		return positionRank;
	}
	public void setPositionRank(String positionRank) {
		this.positionRank = positionRank;
	}
	public Date getTransactionValueDate() {
		return transactionValueDate;
	}
	public void setTransactionValueDate(Date transactionValueDate) {
		this.transactionValueDate = transactionValueDate;
	}
	public String getTransactionRefNo() {
		return transactionRefNo;
	}
	public void setTransactionRefNo(String transactionRefNo) {
		this.transactionRefNo = transactionRefNo;
	}
	public String getTransactioncode() {
		return transactioncode;
	}
	public void setTransactioncode(String transactioncode) {
		this.transactioncode = transactioncode;
	}
	public String getPnNo() {
		return pnNo;
	}
	public void setPnNo(String pnNo) {
		this.pnNo = pnNo;
	}
	public String getLoanAcctNo() {
		return loanAcctNo;
	}
	public void setLoanAcctNo(String loanAcctNo) {
		this.loanAcctNo = loanAcctNo;
	}
	public String getAcctName() {
		return acctName;
	}
	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}
	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public String getTranstat() {
		return transtat;
	}
	public void setTranstat(String transtat) {
		this.transtat = transtat;
	}
	public String getSolicitingOfficer() {
		return solicitingOfficer;
	}
	public void setSolicitingOfficer(String solicitingOfficer) {
		this.solicitingOfficer = solicitingOfficer;
	}
	public String getParticulars() {
		return particulars;
	}
	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public BigDecimal getAmountWaived() {
		return amountWaived;
	}
	public void setAmountWaived(BigDecimal amountWaived) {
		this.amountWaived = amountWaived;
	}
	public String getLoanProduct() {
		return loanProduct;
	}
	public void setLoanProduct(String loanProduct) {
		this.loanProduct = loanProduct;
	}
	public String getPostedBy() {
		return postedBy;
	}
	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	
	
	
}
