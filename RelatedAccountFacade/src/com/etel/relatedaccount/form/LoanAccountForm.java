package com.etel.relatedaccount.form;
import java.math.BigDecimal;
import java.util.Date;

public class LoanAccountForm {

	String pnno;
	String loanAccountNo;
	String loanProduct;
	String subProdType;
	Date dateBook;
	Date matDate;
	BigDecimal intRate;
	String branch;
	String solicitingOffice;
	String acctsts;
	BigDecimal origAmount;
	BigDecimal prinBal;
	BigDecimal loanBal;
	
	//Get Loan PAyment
	String fullname;
	String cifno;
	BigDecimal totalDebit;
	BigDecimal totalPayment;
	BigDecimal totalCredit;
	
	
	
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getCifno() {
		return cifno;
	}
	public void setCifno(String cifno) {
		this.cifno = cifno;
	}
	public BigDecimal getTotalDebit() {
		return totalDebit;
	}
	public void setTotalDebit(BigDecimal totalDebit) {
		this.totalDebit = totalDebit;
	}
	public BigDecimal getTotalPayment() {
		return totalPayment;
	}
	public void setTotalPayment(BigDecimal totalPayment) {
		this.totalPayment = totalPayment;
	}
	public BigDecimal getTotalCredit() {
		return totalCredit;
	}
	public void setTotalCredit(BigDecimal totalCredit) {
		this.totalCredit = totalCredit;
	}
	public String getPnno() {
		return pnno;
	}
	public void setPnno(String pnno) {
		this.pnno = pnno;
	}
	public String getLoanAccountNo() {
		return loanAccountNo;
	}
	public void setLoanAccountNo(String loanAccountNo) {
		this.loanAccountNo = loanAccountNo;
	}
	public String getLoanProduct() {
		return loanProduct;
	}
	public void setLoanProduct(String loanProduct) {
		this.loanProduct = loanProduct;
	}
	public String getSubProdType() {
		return subProdType;
	}
	public void setSubProdType(String subProdType) {
		this.subProdType = subProdType;
	}
	public Date getDateBook() {
		return dateBook;
	}
	public void setDateBook(Date dateBook) {
		this.dateBook = dateBook;
	}
	public Date getMatDate() {
		return matDate;
	}
	public void setMatDate(Date matDate) {
		this.matDate = matDate;
	}
	public BigDecimal getIntRate() {
		return intRate;
	}
	public void setIntRate(BigDecimal intRate) {
		this.intRate = intRate;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getSolicitingOffice() {
		return solicitingOffice;
	}
	public void setSolicitingOffice(String solicitingOffice) {
		this.solicitingOffice = solicitingOffice;
	}
	public String getAcctsts() {
		return acctsts;
	}
	public void setAcctsts(String acctsts) {
		this.acctsts = acctsts;
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
	public BigDecimal getLoanBal() {
		return loanBal;
	}
	public void setLoanBal(BigDecimal loanBal) {
		this.loanBal = loanBal;
	}
	
	
}
