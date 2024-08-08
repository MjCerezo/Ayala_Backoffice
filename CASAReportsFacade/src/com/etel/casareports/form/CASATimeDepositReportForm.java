package com.etel.casareports.form;

import java.math.BigDecimal;
import java.util.Date;

public class CASATimeDepositReportForm {
	//Masterlist Time Deposit
	String branch;
	String accountName;
	Date lasttxdate;
	Date txvaldt;
	String accountNo;
	Date effectiveDate;
	Boolean bookBalance;
	BigDecimal availbleBalance;
	BigDecimal unclearAmount;
	BigDecimal holdAmount;
	BigDecimal earMarked;
	BigDecimal accruedInterest;
	BigDecimal placeHoldAmount;
	BigDecimal interestearned;
	BigDecimal lastpostedinterest;
	Integer lastwtaxamt;
	String acctsts;
	Date placedate;
	//DailyListofAccuredInterestPayable
	String tdcno;
	Date bookingdate;
	Date maturitydate;
	BigDecimal placementamt;
	Integer termindays;
	BigDecimal intrate;
	Integer noofdaysaccrued;
	BigDecimal interestpaid;
	BigDecimal interestbalance;
	//DailyListofMaturedbutUnwithdrawnAccounts
	BigDecimal accountBal;
	//DailyListofNewPlacements
	String ctdNumber;
	Date placementDate;
	Integer term;
	BigDecimal totalMatAmount;
	//TDDailyListPretermTimeAcctsReport
	BigDecimal matvalue;
	Date lastintcreditdate;
	BigDecimal wtaxamt;
	BigDecimal wtaxrate;
	BigDecimal docstamps;
	//DailyListofRollOvers
	BigDecimal originalPlacement; 
	BigDecimal withholdingTax;
	BigDecimal placeamt;
	Date dtmat;
	BigDecimal outbal;
	String ctdNo;
	String subProdName;
	
	
	
	public Date getPlacedate() {
		return placedate;
	}
	public void setPlacedate(Date placedate) {
		this.placedate = placedate;
	}
	public BigDecimal getOutbal() {
		return outbal;
	}
	public void setOutbal(BigDecimal outbal) {
		this.outbal = outbal;
	}
	public Date getTxvaldt() {
		return txvaldt;
	}
	public void setTxvaldt(Date txvaldt) {
		this.txvaldt = txvaldt;
	}
	public String getSubProdName() {
		return subProdName;
	}
	public void setSubProdName(String subProdName) {
		this.subProdName = subProdName;
	}
	public String getCtdNo() {
		return ctdNo;
	}
	public void setCtdNo(String ctdNo) {
		this.ctdNo = ctdNo;
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
	public BigDecimal getPlaceHoldAmount() {
		return placeHoldAmount;
	}
	public void setPlaceHoldAmount(BigDecimal placeHoldAmount) {
		this.placeHoldAmount = placeHoldAmount;
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
	public String getTdcno() {
		return tdcno;
	}
	public void setTdcno(String tdcno) {
		this.tdcno = tdcno;
	}
	public Date getBookingdate() {
		return bookingdate;
	}
	public void setBookingdate(Date bookingdate) {
		this.bookingdate = bookingdate;
	}
	public Date getMaturitydate() {
		return maturitydate;
	}
	public void setMaturitydate(Date maturitydate) {
		this.maturitydate = maturitydate;
	}
	public BigDecimal getPlacementamt() {
		return placementamt;
	}
	public void setPlacementamt(BigDecimal placementamt) {
		this.placementamt = placementamt;
	}
	public Integer getTermindays() {
		return termindays;
	}
	public void setTermindays(Integer termindays) {
		this.termindays = termindays;
	}
	public BigDecimal getIntrate() {
		return intrate;
	}
	public void setIntrate(BigDecimal intrate) {
		this.intrate = intrate;
	}
	public Integer getNoofdaysaccrued() {
		return noofdaysaccrued;
	}
	public void setNoofdaysaccrued(Integer noofdaysaccrued) {
		this.noofdaysaccrued = noofdaysaccrued;
	}
	public BigDecimal getInterestpaid() {
		return interestpaid;
	}
	public void setInterestpaid(BigDecimal interestpaid) {
		this.interestpaid = interestpaid;
	}
	public BigDecimal getInterestbalance() {
		return interestbalance;
	}
	public void setInterestbalance(BigDecimal interestbalance) {
		this.interestbalance = interestbalance;
	}
	public BigDecimal getAccountBal() {
		return accountBal;
	}
	public void setAccountBal(BigDecimal accountBal) {
		this.accountBal = accountBal;
	}
	public String getCtdNumber() {
		return ctdNumber;
	}
	public void setCtdNumber(String ctdNumber) {
		this.ctdNumber = ctdNumber;
	}
	public Date getPlacementDate() {
		return placementDate;
	}
	public void setPlacementDate(Date placementDate) {
		this.placementDate = placementDate;
	}
	public Integer getTerm() {
		return term;
	}
	public void setTerm(Integer term) {
		this.term = term;
	}
	public BigDecimal getTotalMatAmount() {
		return totalMatAmount;
	}
	public void setTotalMatAmount(BigDecimal totalMatAmount) {
		this.totalMatAmount = totalMatAmount;
	}
	public BigDecimal getMatvalue() {
		return matvalue;
	}
	public void setMatvalue(BigDecimal matvalue) {
		this.matvalue = matvalue;
	}
	public Date getLastintcreditdate() {
		return lastintcreditdate;
	}
	public void setLastintcreditdate(Date lastintcreditdate) {
		this.lastintcreditdate = lastintcreditdate;
	}
	public BigDecimal getWtaxamt() {
		return wtaxamt;
	}
	public void setWtaxamt(BigDecimal wtaxamt) {
		this.wtaxamt = wtaxamt;
	}
	public BigDecimal getWtaxrate() {
		return wtaxrate;
	}
	public void setWtaxrate(BigDecimal wtaxrate) {
		this.wtaxrate = wtaxrate;
	}
	public BigDecimal getDocstamps() {
		return docstamps;
	}
	public void setDocstamps(BigDecimal docstamps) {
		this.docstamps = docstamps;
	}
	public BigDecimal getOriginalPlacement() {
		return originalPlacement;
	}
	public void setOriginalPlacement(BigDecimal originalPlacement) {
		this.originalPlacement = originalPlacement;
	}
	public BigDecimal getWithholdingTax() {
		return withholdingTax;
	}
	public void setWithholdingTax(BigDecimal withholdingTax) {
		this.withholdingTax = withholdingTax;
	}
	public BigDecimal getPlaceamt() {
		return placeamt;
	}
	public void setPlaceamt(BigDecimal placeamt) {
		this.placeamt = placeamt;
	}
	public Date getDtmat() {
		return dtmat;
	}
	public void setDtmat(Date dtmat) {
		this.dtmat = dtmat;
	}
	
	
}
