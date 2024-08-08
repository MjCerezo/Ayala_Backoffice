package com.etel.casareports.form;

import java.math.BigDecimal;
import java.util.Date;

public class CASATransactionalReportForm {
	//Teller’s List of Transactions for the Day (Teller’s Name)As of
	String branch;
	String txrefNo;
	Date txdate;
	String accountname;
	String accountno;
	String prodName;
	String subProdName;
	Date lastTransDate;
	String transType;
	BigDecimal txamount;
	BigDecimal prevBal;
	BigDecimal currentBal;
	String transStat;
	String remarks;
	String overrideRequest;
	String overrideBy;
	String passbookPosted;
	
	Date txvaldt;
	String txcode;
	String createdBy;
	
	
	//List of Newly Opened Accounts for the Day 
	String acctType;
	BigDecimal initAmount;
	BigDecimal accountBal;
	BigDecimal intRate;
	String passbookNo;
	String checknumber;
	String ctdNo;
	Integer term;
	BigDecimal taxRateAmt;
	Date bookingdate;
	String solicitingofficer;
	String referralofficer;
	String campaign;
	
	
	
	//List of Closed / Terminated / Pre-terminated Accounts for the Day 
	String closeBy;
	
	//Branch Transaction List for the Day – Financial (SAVINGS / CHECKING )
	String dispositiontype;
	BigDecimal totalint;
	BigDecimal wtaxrate;
	String docstamps;
	
	//Branch Transaction List for the Day – Financial – Term Products
	Date matDate;
	BigDecimal placementAmt;
	String disposition; 
	//wala pa
	BigDecimal docStampCost;
	BigDecimal totalIntRate;
	BigDecimal totalIntWithdraw;
	BigDecimal totalIntEarned;
	BigDecimal totalIntDue;
	BigDecimal wHoldTax;
	BigDecimal docStapTaxCost;
	Integer totalNoOfDays;
	//end
	BigDecimal matValue;
	BigDecimal amoutPaid;
	Integer totalNoDays;
	//Cash Transfer Movements for the Day
	String transCode;
	String transName;
	String source;
	String destination;
	String currency;
	//List of Issued CTD/PASSBOOK/CKBOOK for the Day WAS
	String docIssued;
	String docno;
	String seriesFromNo;
	String seriesToNo;
	Date issueDate;
	
	
	//List of Late Check Deposits for the Day (per Teller / per Branch) WAS
	String bankName;
	String checkBrstn;
	String terminalNo;
	Date checkdate;
	BigDecimal checkamount;
	
	//List of Closed Accounts for the Day / Period
	BigDecimal closingBal;
	String acctsts;
	
	//List of Escheated Accounts for the Day / Period WAS
	Integer daysEscheat;
	BigDecimal amountEscheated;
	Date lasttxdate;
	
	//List of Auto Renew or Auto Roll Term Deposit Placements For the Day
	String prodType;
	BigDecimal placeAmount;
	BigDecimal tolalint;
	BigDecimal wtaxamt;
	BigDecimal newFaceAmount;
	BigDecimal interestamt;
	BigDecimal wTaxRate;
	Date bookDate;
	Integer newTerm;
	Date newBookDate;
	Date newMatDate;
	Date dtbook;
	Date dtmat;
	
	//File Maintenance
	String txname;
	Date effectivitydate;
	Date expirydate;
	String reason;
	String overrideby;
	String createdby;
	String oldAcctsts;

	
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getTxname() {
		return txname;
	}
	public void setTxname(String txname) {
		this.txname = txname;
	}
	public Date getEffectivitydate() {
		return effectivitydate;
	}
	public void setEffectivitydate(Date effectivitydate) {
		this.effectivitydate = effectivitydate;
	}
	public Date getExpirydate() {
		return expirydate;
	}
	public void setExpirydate(Date expirydate) {
		this.expirydate = expirydate;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getOverrideby() {
		return overrideby;
	}
	public void setOverrideby(String overrideby) {
		this.overrideby = overrideby;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getOldAcctsts() {
		return oldAcctsts;
	}
	public void setOldAcctsts(String oldAcctsts) {
		this.oldAcctsts = oldAcctsts;
	}
	public String getTerminalNo() {
		return terminalNo;
	}
	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}
	public BigDecimal getTolalint() {
		return tolalint;
	}
	public void setTolalint(BigDecimal tolalint) {
		this.tolalint = tolalint;
	}
	public Date getBookDate() {
		return bookDate;
	}
	public void setBookDate(Date bookDate) {
		this.bookDate = bookDate;
	}
	public Integer getNewTerm() {
		return newTerm;
	}
	public void setNewTerm(Integer newTerm) {
		this.newTerm = newTerm;
	}
	public Date getNewBookDate() {
		return newBookDate;
	}
	public void setNewBookDate(Date newBookDate) {
		this.newBookDate = newBookDate;
	}
	public Date getNewMatDate() {
		return newMatDate;
	}
	public void setNewMatDate(Date newMatDate) {
		this.newMatDate = newMatDate;
	}
	public BigDecimal getDocStampCost() {
		return docStampCost;
	}
	public void setDocStampCost(BigDecimal docStampCost) {
		this.docStampCost = docStampCost;
	}
	public BigDecimal getTotalIntRate() {
		return totalIntRate;
	}
	public void setTotalIntRate(BigDecimal totalIntRate) {
		this.totalIntRate = totalIntRate;
	}
	public BigDecimal getDocStapTaxCost() {
		return docStapTaxCost;
	}
	public void setDocStapTaxCost(BigDecimal docStapTaxCost) {
		this.docStapTaxCost = docStapTaxCost;
	}
	public String getCloseBy() {
		return closeBy;
	}
	public void setCloseBy(String closeBy) {
		this.closeBy = closeBy;
	}
	public BigDecimal getPrevBal() {
		return prevBal;
	}
	public void setPrevBal(BigDecimal prevBal) {
		this.prevBal = prevBal;
	}
	public BigDecimal getInitAmount() {
		return initAmount;
	}
	public void setInitAmount(BigDecimal initAmount) {
		this.initAmount = initAmount;
	}
	public String getDocIssued() {
		return docIssued;
	}
	public void setDocIssued(String docIssued) {
		this.docIssued = docIssued;
	}
	public String getDocno() {
		return docno;
	}
	public void setDocno(String docno) {
		this.docno = docno;
	}
	public String getSeriesFromNo() {
		return seriesFromNo;
	}
	public void setSeriesFromNo(String seriesFromNo) {
		this.seriesFromNo = seriesFromNo;
	}
	public String getSeriesToNo() {
		return seriesToNo;
	}
	public void setSeriesToNo(String seriesToNo) {
		this.seriesToNo = seriesToNo;
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	public Date getTxvaldt() {
		return txvaldt;
	}
	public void setTxvaldt(Date txvaldt) {
		this.txvaldt = txvaldt;
	}
	public Integer getTotalNoDays() {
		return totalNoDays;
	}
	public void setTotalNoDays(Integer totalNoDays) {
		this.totalNoDays = totalNoDays;
	}
	public String getCheckBrstn() {
		return checkBrstn;
	}
	public void setCheckBrstn(String checkBrstn) {
		this.checkBrstn = checkBrstn;
	}
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	public BigDecimal getPlaceAmount() {
		return placeAmount;
	}
	public void setPlaceAmount(BigDecimal placeAmount) {
		this.placeAmount = placeAmount;
	}
	public BigDecimal getWtaxamt() {
		return wtaxamt;
	}
	public void setWtaxamt(BigDecimal wtaxamt) {
		this.wtaxamt = wtaxamt;
	}
	public BigDecimal getNewFaceAmount() {
		return newFaceAmount;
	}
	public void setNewFaceAmount(BigDecimal newFaceAmount) {
		this.newFaceAmount = newFaceAmount;
	}
	public BigDecimal getInterestamt() {
		return interestamt;
	}
	public void setInterestamt(BigDecimal interestamt) {
		this.interestamt = interestamt;
	}
	public BigDecimal getwTaxRate() {
		return wTaxRate;
	}
	public void setwTaxRate(BigDecimal wTaxRate) {
		this.wTaxRate = wTaxRate;
	}
	public Date getDtbook() {
		return dtbook;
	}
	public void setDtbook(Date dtbook) {
		this.dtbook = dtbook;
	}
	public Date getDtmat() {
		return dtmat;
	}
	public void setDtmat(Date dtmat) {
		this.dtmat = dtmat;
	}
	public String getDispositiontype() {
		return dispositiontype;
	}
	public void setDispositiontype(String dispositiontype) {
		this.dispositiontype = dispositiontype;
	}
	public BigDecimal getTotalint() {
		return totalint;
	}
	public void setTotalint(BigDecimal totalint) {
		this.totalint = totalint;
	}
	public BigDecimal getWtaxrate() {
		return wtaxrate;
	}
	public void setWtaxrate(BigDecimal wtaxrate) {
		this.wtaxrate = wtaxrate;
	}
	public String getDocstamps() {
		return docstamps;
	}
	public void setDocstamps(String docstamps) {
		this.docstamps = docstamps;
	}
	public Integer getDaysEscheat() {
		return daysEscheat;
	}
	public void setDaysEscheat(Integer daysEscheat) {
		this.daysEscheat = daysEscheat;
	}
	public BigDecimal getAmountEscheated() {
		return amountEscheated;
	}
	public void setAmountEscheated(BigDecimal amountEscheated) {
		this.amountEscheated = amountEscheated;
	}
	public Date getLasttxdate() {
		return lasttxdate;
	}
	public void setLasttxdate(Date lasttxdate) {
		this.lasttxdate = lasttxdate;
	}
	public Date getTxdate() {
		return txdate;
	}
	public BigDecimal getCheckamount() {
		return checkamount;
	}
	public void setCheckamount(BigDecimal checkamount) {
		this.checkamount = checkamount;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public Date getCheckdate() {
		return checkdate;
	}
	public void setCheckdate(Date checkdate) {
		this.checkdate = checkdate;
	}
	public void setTxdate(Date txdate) {
		this.txdate = txdate;
	}
	public String getTxcode() {
		return txcode;
	}
	public void setTxcode(String txcode) {
		this.txcode = txcode;
	}
	public String getAccountname() {
		return accountname;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	public String getAccountno() {
		return accountno;
	}
	public void setAccountno(String accountno) {
		this.accountno = accountno;
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
	public Date getLastTransDate() {
		return lastTransDate;
	}
	public void setLastTransDate(Date lastTransDate) {
		this.lastTransDate = lastTransDate;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public BigDecimal getTxamount() {
		return txamount;
	}
	public void setTxamount(BigDecimal txamount) {
		this.txamount = txamount;
	}
	public BigDecimal getCurrentBal() {
		return currentBal;
	}
	public void setCurrentBal(BigDecimal currentBal) {
		this.currentBal = currentBal;
	}
	public String getTransStat() {
		return transStat;
	}
	public void setTransStat(String transStat) {
		this.transStat = transStat;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getOverrideRequest() {
		return overrideRequest;
	}
	public void setOverrideRequest(String overrideRequest) {
		this.overrideRequest = overrideRequest;
	}
	public String getOverrideBy() {
		return overrideBy;
	}
	public void setOverrideBy(String overrideBy) {
		this.overrideBy = overrideBy;
	}
	public String getPassbookPosted() {
		return passbookPosted;
	}
	public void setPassbookPosted(String passbookPosted) {
		this.passbookPosted = passbookPosted;
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
	public String getAcctType() {
		return acctType;
	}
	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}
	public BigDecimal getAccountBal() {
		return accountBal;
	}
	public void setAccountBal(BigDecimal accountBal) {
		this.accountBal = accountBal;
	}
	public BigDecimal getIntRate() {
		return intRate;
	}
	public void setIntRate(BigDecimal intRate) {
		this.intRate = intRate;
	}
	public String getPassbookNo() {
		return passbookNo;
	}
	public void setPassbookNo(String passbookNo) {
		this.passbookNo = passbookNo;
	}
	public String getChecknumber() {
		return checknumber;
	}
	public void setChecknumber(String checknumber) {
		this.checknumber = checknumber;
	}
	public String getCtdNo() {
		return ctdNo;
	}
	public void setCtdNo(String ctdNo) {
		this.ctdNo = ctdNo;
	}
	public Integer getTerm() {
		return term;
	}
	public void setTerm(Integer term) {
		this.term = term;
	}
	public BigDecimal getTaxRateAmt() {
		return taxRateAmt;
	}
	public void setTaxRateAmt(BigDecimal taxRateAmt) {
		this.taxRateAmt = taxRateAmt;
	}
	public Date getBookingdate() {
		return bookingdate;
	}
	public void setBookingdate(Date bookingdate) {
		this.bookingdate = bookingdate;
	}
	public String getSolicitingofficer() {
		return solicitingofficer;
	}
	public void setSolicitingofficer(String solicitingofficer) {
		this.solicitingofficer = solicitingofficer;
	}
	public String getReferralofficer() {
		return referralofficer;
	}
	public void setReferralofficer(String referralofficer) {
		this.referralofficer = referralofficer;
	}
	public String getCampaign() {
		return campaign;
	}
	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}
	public Date getMatDate() {
		return matDate;
	}
	public void setMatDate(Date matDate) {
		this.matDate = matDate;
	}
	public BigDecimal getPlacementAmt() {
		return placementAmt;
	}
	public void setPlacementAmt(BigDecimal placementAmt) {
		this.placementAmt = placementAmt;
	}
	public String getDisposition() {
		return disposition;
	}
	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}
	public BigDecimal getTotalIntEarned() {
		return totalIntEarned;
	}
	public void setTotalIntEarned(BigDecimal totalIntEarned) {
		this.totalIntEarned = totalIntEarned;
	}
	public BigDecimal getTotalIntWithdraw() {
		return totalIntWithdraw;
	}
	public void setTotalIntWithdraw(BigDecimal totalIntWithdraw) {
		this.totalIntWithdraw = totalIntWithdraw;
	}
	public Integer getTotalNoOfDays() {
		return totalNoOfDays;
	}
	public void setTotalNoOfDays(Integer totalNoOfDays) {
		this.totalNoOfDays = totalNoOfDays;
	}
	public BigDecimal getTotalIntDue() {
		return totalIntDue;
	}
	public void setTotalIntDue(BigDecimal totalIntDue) {
		this.totalIntDue = totalIntDue;
	}
	public BigDecimal getwHoldTax() {
		return wHoldTax;
	}
	public void setwHoldTax(BigDecimal wHoldTax) {
		this.wHoldTax = wHoldTax;
	}

	public BigDecimal getMatValue() {
		return matValue;
	}
	public void setMatValue(BigDecimal matValue) {
		this.matValue = matValue;
	}
	public BigDecimal getAmoutPaid() {
		return amoutPaid;
	}
	public void setAmoutPaid(BigDecimal amoutPaid) {
		this.amoutPaid = amoutPaid;
	}
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public String getTransName() {
		return transName;
	}
	public void setTransName(String transName) {
		this.transName = transName;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public BigDecimal getClosingBal() {
		return closingBal;
	}
	public void setClosingBal(BigDecimal closingBal) {
		this.closingBal = closingBal;
	}
	public String getAcctsts() {
		return acctsts;
	}
	public void setAcctsts(String acctsts) {
		this.acctsts = acctsts;
	}
}
