package com.etel.casareports.form;

import java.math.BigDecimal;
import java.util.Date;

public class CASASavingReportForm {
	//Masterlist Saving
	
	String branch;
	String accountName;
	Date lasttxdate;
	String accountNo;
	Date effectiveDate;
	Boolean bookBalance;
	BigDecimal availbleBalance;
	BigDecimal unclearAmount;
	BigDecimal holdAmount;
	BigDecimal earMarked;
	BigDecimal accruedInterest;
	BigDecimal lastpostedinterest;
	BigDecimal interestearned; //wala 
	Integer lastwtaxamt;
	String acctsts;
	BigDecimal AverageDailyBalance;
	BigDecimal Txamount;
	String subProdsName;
	//DailyListofAccountsBelowMinimumBalance
	String txname;
	BigDecimal interestbalance;
	BigDecimal intRate;
	BigDecimal accountBal;
	//DailyListofClosedAccounts
	BigDecimal interestpaid;
	BigDecimal wTaxRate;
	Integer servicecharge;
	Integer closingbalance;
	//DailyListofDormantAccounts5Years || 10Years || 2 MONTHS
	Integer amount;
	BigDecimal outbal;
	Integer penalty;
	//DailyListofNewAccounts
	Date bookDate; //wala 
	Date statusdate; //wala 
	
	//SavingsandPremiumSavingsDeposit
	String name;
	Integer totalcountactive;
	BigDecimal totalamountactive;
	Integer totalcountdormant;
	BigDecimal totalamountdormant;
	Integer totalcount;
	BigDecimal totalamount;
	//SASummaryofDailyTransactionReport
	String productcode;
	String productdescription;
	Integer noofdebits;
	BigDecimal totaldebits;
	Integer noofcredits;
	BigDecimal totalcredits;
	String subProdName;
	
	
	
	public String getSubProdsName() {
		return subProdsName;
	}
	public void setSubProdsName(String subProdsName) {
		this.subProdsName = subProdsName;
	}
	public BigDecimal getAverageDailyBalance() {
		return AverageDailyBalance;
	}
	public void setAverageDailyBalance(BigDecimal averageDailyBalance) {
		AverageDailyBalance = averageDailyBalance;
	}
	public BigDecimal getTxamount() {
		return Txamount;
	}
	public void setTxamount(BigDecimal txamount) {
		Txamount = txamount;
	}
	public String getSubProdName() {
		return subProdName;
	}
	public void setSubProdName(String subProdName) {
		this.subProdName = subProdName;
	}
	
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public Date getLasttxdate() {
		return lasttxdate;
	}
	public void setLasttxdate(Date lasttxdate) {
		this.lasttxdate = lasttxdate;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public Boolean getBookBalance() {
		return bookBalance;
	}
	public void setBookBalance(Boolean bookBalance) {
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
	public BigDecimal getLastpostedinterest() {
		return lastpostedinterest;
	}
	public void setLastpostedinterest(BigDecimal lastpostedinterest) {
		this.lastpostedinterest = lastpostedinterest;
	}
	public BigDecimal getInterestearned() {
		return interestearned;
	}
	public void setInterestearned(BigDecimal interestearned) {
		this.interestearned = interestearned;
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
	public BigDecimal getInterestpaid() {
		return interestpaid;
	}
	public void setInterestpaid(BigDecimal interestpaid) {
		this.interestpaid = interestpaid;
	}
	public BigDecimal getwTaxRate() {
		return wTaxRate;
	}
	public void setwTaxRate(BigDecimal wTaxRate) {
		this.wTaxRate = wTaxRate;
	}
	public Integer getServicecharge() {
		return servicecharge;
	}
	public void setServicecharge(Integer servicecharge) {
		this.servicecharge = servicecharge;
	}
	public Integer getClosingbalance() {
		return closingbalance;
	}
	public void setClosingbalance(Integer closingbalance) {
		this.closingbalance = closingbalance;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public BigDecimal getOutbal() {
		return outbal;
	}
	public void setOutbal(BigDecimal outbal) {
		this.outbal = outbal;
	}
	public Integer getPenalty() {
		return penalty;
	}
	public void setPenalty(Integer penalty) {
		this.penalty = penalty;
	}
	public Date getBookDate() {
		return bookDate;
	}
	public void setBookDate(Date bookDate) {
		this.bookDate = bookDate;
	}
	public Date getStatusdate() {
		return statusdate;
	}
	public void setStatusdate(Date statusdate) {
		this.statusdate = statusdate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getTotalcountactive() {
		return totalcountactive;
	}
	public void setTotalcountactive(Integer totalcountactive) {
		this.totalcountactive = totalcountactive;
	}
	public BigDecimal getTotalamountactive() {
		return totalamountactive;
	}
	public void setTotalamountactive(BigDecimal totalamountactive) {
		this.totalamountactive = totalamountactive;
	}
	public Integer getTotalcountdormant() {
		return totalcountdormant;
	}
	public void setTotalcountdormant(Integer totalcountdormant) {
		this.totalcountdormant = totalcountdormant;
	}
	public BigDecimal getTotalamountdormant() {
		return totalamountdormant;
	}
	public void setTotalamountdormant(BigDecimal totalamountdormant) {
		this.totalamountdormant = totalamountdormant;
	}
	public Integer getTotalcount() {
		return totalcount;
	}
	public void setTotalcount(Integer totalcount) {
		this.totalcount = totalcount;
	}
	public BigDecimal getTotalamount() {
		return totalamount;
	}
	public void setTotalamount(BigDecimal totalamount) {
		this.totalamount = totalamount;
	}
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public String getProductdescription() {
		return productdescription;
	}
	public void setProductdescription(String productdescription) {
		this.productdescription = productdescription;
	}
	public Integer getNoofdebits() {
		return noofdebits;
	}
	public void setNoofdebits(Integer noofdebits) {
		this.noofdebits = noofdebits;
	}
	public BigDecimal getTotaldebits() {
		return totaldebits;
	}
	public void setTotaldebits(BigDecimal totaldebits) {
		this.totaldebits = totaldebits;
	}
	public Integer getNoofcredits() {
		return noofcredits;
	}
	public void setNoofcredits(Integer noofcredits) {
		this.noofcredits = noofcredits;
	}
	public BigDecimal getTotalcredits() {
		return totalcredits;
	}
	public void setTotalcredits(BigDecimal totalcredits) {
		this.totalcredits = totalcredits;
	}
}
