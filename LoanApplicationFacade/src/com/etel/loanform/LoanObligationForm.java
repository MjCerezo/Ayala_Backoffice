package com.etel.loanform;

import java.math.BigDecimal;
import java.util.Date;

public class LoanObligationForm {
	
	String loanProduct;
	BigDecimal outstandingBal;
	Date dateGranted;
	Date maturityDate;
	BigDecimal interestRate;
	BigDecimal monthlyAmort;
	String status;
	
	public String getLoanProduct() {
		return loanProduct;
	}
	public void setLoanProduct(String loanProduct) {
		this.loanProduct = loanProduct;
	}
	public BigDecimal getOutstandingBal() {
		return outstandingBal;
	}
	public void setOutstandingBal(BigDecimal outstandingBal) {
		this.outstandingBal = outstandingBal;
	}
	public Date getDateGranted() {
		return dateGranted;
	}
	public void setDateGranted(Date dateGranted) {
		this.dateGranted = dateGranted;
	}
	public Date getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}
	public BigDecimal getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}
	public BigDecimal getMonthlyAmort() {
		return monthlyAmort;
	}
	public void setMonthlyAmort(BigDecimal monthlyAmort) {
		this.monthlyAmort = monthlyAmort;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
