package com.etel.casareports.form;

import java.math.BigDecimal;
import java.util.Date;

public class CASAAllProductReportForm {
	
	//Masterlist of Accounts - Active
	Date txdate;
	BigDecimal outbal;
	String accountNo;
	String branch;
	String accountName;
	String prodName;
	Date lasttxdate;
	Date effectiveDate;
	String ctdNo;
	BigDecimal bookBalance;
	BigDecimal availbleBalance;
	BigDecimal unclearAmount;
	BigDecimal holdAmount;
	
	BigDecimal earMarked;
	BigDecimal accruedInterest;
	BigDecimal totalIntPosted;
	BigDecimal totalwTaxPosted;
	
	BigDecimal placeholdAmt;
	BigDecimal interestearned;
	BigDecimal lastpostedinterest;
	Integer lastwtaxamt;
	String acctsts;
	
	//Masterlist of Accounts - Below Minimum Balance
	String txname;
	BigDecimal interestbalance;
	BigDecimal intRate;
	BigDecimal accountBal;
	
	//Master List of Accounts - Dormant (5 years / 10 years)
	Integer amount;
	BigDecimal outBal;
	Integer penalty;
	Integer yearsDormat;
	
	//Schedule of Accrued Interest Payable as of (time deposit / per product)
	String subProdName;
	String acctType;
	String tdcno;
	String passBookNo;
	BigDecimal aipOutsBal;
	Date dateLastTransSetup;
	
	String dateString ;

	//Customer’s List of Deposit Accounts
	Date bookDate;
	Date matDate;
	//Schedule of Accounts with Negative Balance / Temporary Overdraft
	BigDecimal txamount;
	String transType;
	String createdBy;
	String overrideBy;
	String transStat;
	
	
	public BigDecimal getOutbal() {
		return outbal;
	}
	public void setOutbal(BigDecimal outbal) {
		this.outbal = outbal;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public Date getTxdate() {
		return txdate;
	}
	public void setTxdate(Date txdate) {
		this.txdate = txdate;
	}
	public String getAccountNo() {
		return accountNo;
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
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public Date getLasttxdate() {
		return lasttxdate;
	}
	public void setLasttxdate(Date lasttxdate) {
		this.lasttxdate = lasttxdate;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getCtdNo() {
		return ctdNo;
	}
	public void setCtdNo(String ctdNo) {
		this.ctdNo = ctdNo;
	}
	public BigDecimal getBookBalance() {
		return bookBalance;
	}
	public void setBookBalance(BigDecimal bookBalance) {
		this.bookBalance = bookBalance;
	}
	public BigDecimal getAvailbleBalance() {
		return availbleBalance;
	}
	public void setAvailbleBalance(BigDecimal availbleBalance) {
		this.availbleBalance = availbleBalance;
	}
	public BigDecimal getUnclearAmount() {
		return unclearAmount;
	}
	public void setUnclearAmount(BigDecimal unclearAmount) {
		this.unclearAmount = unclearAmount;
	}
	public BigDecimal getHoldAmount() {
		return holdAmount;
	}
	public void setHoldAmount(BigDecimal holdAmount) {
		this.holdAmount = holdAmount;
	}
	public BigDecimal getEarMarked() {
		return earMarked;
	}
	public void setEarMarked(BigDecimal earMarked) {
		this.earMarked = earMarked;
	}
	public BigDecimal getAccruedInterest() {
		return accruedInterest;
	}
	public void setAccruedInterest(BigDecimal accruedInterest) {
		this.accruedInterest = accruedInterest;
	}
	public BigDecimal getTotalIntPosted() {
		return totalIntPosted;
	}
	public void setTotalIntPosted(BigDecimal totalIntPosted) {
		this.totalIntPosted = totalIntPosted;
	}
	public BigDecimal getTotalwTaxPosted() {
		return totalwTaxPosted;
	}
	public void setTotalwTaxPosted(BigDecimal totalwTaxPosted) {
		this.totalwTaxPosted = totalwTaxPosted;
	}
	public BigDecimal getPlaceholdAmt() {
		return placeholdAmt;
	}
	public void setPlaceholdAmt(BigDecimal placeholdAmt) {
		this.placeholdAmt = placeholdAmt;
	}
	public BigDecimal getInterestearned() {
		return interestearned;
	}
	public void setInterestearned(BigDecimal interestearned) {
		this.interestearned = interestearned;
	}
	public BigDecimal getLastpostedinterest() {
		return lastpostedinterest;
	}
	public void setLastpostedinterest(BigDecimal lastpostedinterest) {
		this.lastpostedinterest = lastpostedinterest;
	}
	public Integer getLastwtaxamt() {
		return lastwtaxamt;
	}
	public void setLastwtaxamt(Integer lastwtaxamt) {
		this.lastwtaxamt = lastwtaxamt;
	}
	public String getAcctsts() {
		return acctsts;
	}
	public void setAcctsts(String acctsts) {
		this.acctsts = acctsts;
	}
	public String getTxname() {
		return txname;
	}
	public void setTxname(String txname) {
		this.txname = txname;
	}
	public BigDecimal getInterestbalance() {
		return interestbalance;
	}
	public void setInterestbalance(BigDecimal interestbalance) {
		this.interestbalance = interestbalance;
	}
	public BigDecimal getIntRate() {
		return intRate;
	}
	public void setIntRate(BigDecimal intRate) {
		this.intRate = intRate;
	}
	public BigDecimal getAccountBal() {
		return accountBal;
	}
	public void setAccountBal(BigDecimal accountBal) {
		this.accountBal = accountBal;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public BigDecimal getOutBal() {
		return outBal;
	}
	public void setOutBal(BigDecimal outBal) {
		this.outBal = outBal;
	}
	public Integer getPenalty() {
		return penalty;
	}
	public void setPenalty(Integer penalty) {
		this.penalty = penalty;
	}
	public Integer getYearsDormat() {
		return yearsDormat;
	}
	public void setYearsDormat(Integer yearsDormat) {
		this.yearsDormat = yearsDormat;
	}
	public String getSubProdName() {
		return subProdName;
	}
	public void setSubProdName(String subProdName) {
		this.subProdName = subProdName;
	}
	public String getAcctType() {
		return acctType;
	}
	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}
	public String getTdcno() {
		return tdcno;
	}
	public void setTdcno(String tdcno) {
		this.tdcno = tdcno;
	}
	public String getPassBookNo() {
		return passBookNo;
	}
	public void setPassBookNo(String passBookNo) {
		this.passBookNo = passBookNo;
	}
	public BigDecimal getAipOutsBal() {
		return aipOutsBal;
	}
	public void setAipOutsBal(BigDecimal aipOutsBal) {
		this.aipOutsBal = aipOutsBal;
	}
	public Date getDateLastTransSetup() {
		return dateLastTransSetup;
	}
	public void setDateLastTransSetup(Date dateLastTransSetup) {
		this.dateLastTransSetup = dateLastTransSetup;
	}
	public String getDateString() {
		return dateString;
	}
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
	public Date getBookDate() {
		return bookDate;
	}
	public void setBookDate(Date bookDate) {
		this.bookDate = bookDate;
	}
	public Date getMatDate() {
		return matDate;
	}
	public void setMatDate(Date matDate) {
		this.matDate = matDate;
	}
	public BigDecimal getTxamount() {
		return txamount;
	}
	public void setTxamount(BigDecimal txamount) {
		this.txamount = txamount;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getOverrideBy() {
		return overrideBy;
	}
	public void setOverrideBy(String overrideBy) {
		this.overrideBy = overrideBy;
	}
	public String getTransStat() {
		return transStat;
	}
	public void setTransStat(String transStat) {
		this.transStat = transStat;
	}

	
}
