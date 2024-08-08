package com.etel.relatedaccount.form;

import java.math.BigDecimal;

public class AccountProfitabilityForm {

	String fullname;
	String cifno;
	
	//DEPOSIT
	BigDecimal yTDTotal;
	BigDecimal aDBTotal;
	
	//LAON
	BigDecimal totalLoanAmount;
	BigDecimal rr4savingsdeposit;
	BigDecimal rr4termdeposit;
	BigDecimal rr4checkingdeposit;
	BigDecimal transferpoolrate;
	BigDecimal currentratesbl;
	Integer netunimpairedcapital;
	
	//Get Loan PAyment
	BigDecimal totalDebit;
	BigDecimal totalPayment;
	BigDecimal totalCredit;
	
	
	
	
	public BigDecimal getTotalLoanAmount() {
		return totalLoanAmount;
	}
	public void setTotalLoanAmount(BigDecimal totalLoanAmount) {
		this.totalLoanAmount = totalLoanAmount;
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
	public BigDecimal getyTDTotal() {
		return yTDTotal;
	}
	public void setyTDTotal(BigDecimal yTDTotal) {
		this.yTDTotal = yTDTotal;
	}
	public BigDecimal getaDBTotal() {
		return aDBTotal;
	}
	public void setaDBTotal(BigDecimal aDBTotal) {
		this.aDBTotal = aDBTotal;
	}
	public BigDecimal getTotalOrigAmount() {
		return totalLoanAmount;
	}
	public void setTotalOrigAmount(BigDecimal totalOrigAmount) {
		this.totalLoanAmount = totalOrigAmount;
	}
	public BigDecimal getRr4savingsdeposit() {
		return rr4savingsdeposit;
	}
	public void setRr4savingsdeposit(BigDecimal rr4savingsdeposit) {
		this.rr4savingsdeposit = rr4savingsdeposit;
	}
	public BigDecimal getRr4termdeposit() {
		return rr4termdeposit;
	}
	public void setRr4termdeposit(BigDecimal rr4termdeposit) {
		this.rr4termdeposit = rr4termdeposit;
	}
	public BigDecimal getRr4checkingdeposit() {
		return rr4checkingdeposit;
	}
	public void setRr4checkingdeposit(BigDecimal rr4checkingdeposit) {
		this.rr4checkingdeposit = rr4checkingdeposit;
	}
	public BigDecimal getTransferpoolrate() {
		return transferpoolrate;
	}
	public void setTransferpoolrate(BigDecimal transferpoolrate) {
		this.transferpoolrate = transferpoolrate;
	}
	public BigDecimal getCurrentratesbl() {
		return currentratesbl;
	}
	public void setCurrentratesbl(BigDecimal currentratesbl) {
		this.currentratesbl = currentratesbl;
	}
	public Integer getNetunimpairedcapital() {
		return netunimpairedcapital;
	}
	public void setNetunimpairedcapital(Integer netunimpairedcapital) {
		this.netunimpairedcapital = netunimpairedcapital;
	}
	
	
}
