package com.etel.lmsreport.form;

import java.math.BigDecimal;
import java.util.Date;

public class LMSBalancesReportForm {

	//ListofLoanAccounts
	String pnNo;
	String loanNo;
	String accountName;
	String loanProduct;
	Date dateAvailed;
	Date maturityDate;
	BigDecimal intRate;
	BigDecimal term;
	BigDecimal origAmount;
	BigDecimal prinBal;
	BigDecimal outLoanBal;
	BigDecimal uidBal;
	BigDecimal airBal;
	BigDecimal totalAmountToUpdate;
	String acctsts;
	String solicitingOff;
	String branch;
	
	
	//ScheduleofDueAmortizationsForthePeriod
	Date amortzDate;
	String applicationNo;
	BigDecimal amortzAmount;
	Integer installmentNo;
	Integer totalNoOfInstal;
	
	//ScheduleofOutstandingAccruedInterestReceivables
	BigDecimal outsAccruedInt;
	
	//ScheduleofOutstandingUnearnedInterestDiscounts
	BigDecimal outsUnearnedInt;
	
	//ScheduleofLoanAccountsfromOldtoCurrent
	Date reclassDate;
	String fromStatus;
	String toStatus;
	
	//Schedule of Loan Releases per Branch
	String productname;
	Integer totalCountPerProdHeadOffice;
	BigDecimal totalAmountPerProdHeadOffice;
	//Schedule of Loan Accounts with Accounts Receivable Balance
	BigDecimal loanOutBal;
	BigDecimal arBal;
	//Schedule of Loan Accounts with Outstanding LPC
	BigDecimal lpcBal;
	//List of Loan Accounts with Excess Payment Balance
	String loanAccountNo;
	BigDecimal outBal;
	BigDecimal exessPayOutsBal;
	Date dateOfPayment;
	String status;
	String approvedBy;
	
	//Schedule of Loan Releases per Soliciting Officer
	String username;
	Integer totalCountPerSoliciting;
	BigDecimal totalAmountPerSoliciting;
	
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getTotalCountPerSoliciting() {
		return totalCountPerSoliciting;
	}

	public void setTotalCountPerSoliciting(Integer totalCountPerSoliciting) {
		this.totalCountPerSoliciting = totalCountPerSoliciting;
	}

	public BigDecimal getTotalAmountPerSoliciting() {
		return totalAmountPerSoliciting;
	}

	public void setTotalAmountPerSoliciting(BigDecimal totalAmountPerSoliciting) {
		this.totalAmountPerSoliciting = totalAmountPerSoliciting;
	}

	public Date getReclassDate() {
		return reclassDate;
	}

	public void setReclassDate(Date reclassDate) {
		this.reclassDate = reclassDate;
	}

	public String getFromStatus() {
		return fromStatus;
	}

	public void setFromStatus(String fromStatus) {
		this.fromStatus = fromStatus;
	}

	public String getToStatus() {
		return toStatus;
	}

	public void setToStatus(String toStatus) {
		this.toStatus = toStatus;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public Integer getTotalCountPerProdHeadOffice() {
		return totalCountPerProdHeadOffice;
	}

	public void setTotalCountPerProdHeadOffice(Integer totalCountPerProdHeadOffice) {
		this.totalCountPerProdHeadOffice = totalCountPerProdHeadOffice;
	}

	public BigDecimal getTotalAmountPerProdHeadOffice() {
		return totalAmountPerProdHeadOffice;
	}

	public void setTotalAmountPerProdHeadOffice(BigDecimal totalAmountPerProdHeadOffice) {
		this.totalAmountPerProdHeadOffice = totalAmountPerProdHeadOffice;
	}

	public BigDecimal getLoanOutBal() {
		return loanOutBal;
	}

	public void setLoanOutBal(BigDecimal loanOutBal) {
		this.loanOutBal = loanOutBal;
	}

	public BigDecimal getArBal() {
		return arBal;
	}

	public void setArBal(BigDecimal arBal) {
		this.arBal = arBal;
	}

	public BigDecimal getLpcBal() {
		return lpcBal;
	}

	public void setLpcBal(BigDecimal lpcBal) {
		this.lpcBal = lpcBal;
	}

	public String getLoanAccountNo() {
		return loanAccountNo;
	}

	public void setLoanAccountNo(String loanAccountNo) {
		this.loanAccountNo = loanAccountNo;
	}

	public BigDecimal getOutBal() {
		return outBal;
	}

	public void setOutBal(BigDecimal outBal) {
		this.outBal = outBal;
	}

	public BigDecimal getExessPayOutsBal() {
		return exessPayOutsBal;
	}

	public void setExessPayOutsBal(BigDecimal exessPayOutsBal) {
		this.exessPayOutsBal = exessPayOutsBal;
	}

	public Date getDateOfPayment() {
		return dateOfPayment;
	}

	public void setDateOfPayment(Date dateOfPayment) {
		this.dateOfPayment = dateOfPayment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getPnNo() {
		return pnNo;
	}

	public void setPnNo(String pnNo) {
		this.pnNo = pnNo;
	}

	public String getLoanNo() {
		return loanNo;
	}

	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getLoanProduct() {
		return loanProduct;
	}

	public void setLoanProduct(String loanProduct) {
		this.loanProduct = loanProduct;
	}

	public Date getDateAvailed() {
		return dateAvailed;
	}

	public void setDateAvailed(Date dateAvailed) {
		this.dateAvailed = dateAvailed;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public BigDecimal getIntRate() {
		return intRate;
	}

	public void setIntRate(BigDecimal intRate) {
		this.intRate = intRate;
	}

	public BigDecimal getTerm() {
		return term;
	}

	public void setTerm(BigDecimal term) {
		this.term = term;
	}

	public BigDecimal getOrigAmount() {
		return origAmount;
	}

	public void setOrigAmount(BigDecimal origAmount) {
		this.origAmount = origAmount;
	}

	public BigDecimal getPrinBal() {
		return prinBal;
	}

	public void setPrinBal(BigDecimal prinBal) {
		this.prinBal = prinBal;
	}

	public BigDecimal getOutLoanBal() {
		return outLoanBal;
	}

	public void setOutLoanBal(BigDecimal outLoanBal) {
		this.outLoanBal = outLoanBal;
	}

	public BigDecimal getUidBal() {
		return uidBal;
	}

	public void setUidBal(BigDecimal uidBal) {
		this.uidBal = uidBal;
	}

	public BigDecimal getAirBal() {
		return airBal;
	}

	public void setAirBal(BigDecimal airBal) {
		this.airBal = airBal;
	}

	public BigDecimal getTotalAmountToUpdate() {
		return totalAmountToUpdate;
	}

	public void setTotalAmountToUpdate(BigDecimal totalAmountToUpdate) {
		this.totalAmountToUpdate = totalAmountToUpdate;
	}

	public String getAcctsts() {
		return acctsts;
	}

	public void setAcctsts(String acctsts) {
		this.acctsts = acctsts;
	}

	public String getSolicitingOff() {
		return solicitingOff;
	}

	public void setSolicitingOff(String solicitingOff) {
		this.solicitingOff = solicitingOff;
	}

	public Date getAmortzDate() {
		return amortzDate;
	}

	public void setAmortzDate(Date amortzDate) {
		this.amortzDate = amortzDate;
	}

	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public BigDecimal getAmortzAmount() {
		return amortzAmount;
	}

	public void setAmortzAmount(BigDecimal amortzAmount) {
		this.amortzAmount = amortzAmount;
	}

	public Integer getInstallmentNo() {
		return installmentNo;
	}

	public void setInstallmentNo(Integer installmentNo) {
		this.installmentNo = installmentNo;
	}

	public Integer getTotalNoOfInstal() {
		return totalNoOfInstal;
	}

	public void setTotalNoOfInstal(Integer totalNoOfInstal) {
		this.totalNoOfInstal = totalNoOfInstal;
	}

	public BigDecimal getOutsAccruedInt() {
		return outsAccruedInt;
	}

	public void setOutsAccruedInt(BigDecimal outsAccruedInt) {
		this.outsAccruedInt = outsAccruedInt;
	}

	public BigDecimal getOutsUnearnedInt() {
		return outsUnearnedInt;
	}

	public void setOutsUnearnedInt(BigDecimal outsUnearnedInt) {
		this.outsUnearnedInt = outsUnearnedInt;
	}
	
	
}
