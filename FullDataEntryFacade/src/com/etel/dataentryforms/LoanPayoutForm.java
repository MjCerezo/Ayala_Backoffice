package com.etel.dataentryforms;

import java.math.BigDecimal;

public class LoanPayoutForm {
	
	private String pnno;
	private String loanproduct;
	private BigDecimal outstandingbal;
	private BigDecimal paidoffamount;
	private String acctstat;
	public String getPnno() {
		return pnno;
	}
	public String getLoanproduct() {
		return loanproduct;
	}
	public BigDecimal getOutstandingbal() {
		return outstandingbal;
	}
	public BigDecimal getPaidoffamount() {
		return paidoffamount;
	}
	public String getAcctstat() {
		return acctstat;
	}
	public void setPnno(String pnno) {
		this.pnno = pnno;
	}
	public void setLoanproduct(String loanproduct) {
		this.loanproduct = loanproduct;
	}
	public void setOutstandingbal(BigDecimal outstandingbal) {
		this.outstandingbal = outstandingbal;
	}
	public void setPaidoffamount(BigDecimal paidoffamount) {
		this.paidoffamount = paidoffamount;
	}
	public void setAcctstat(String acctstat) {
		this.acctstat = acctstat;
	}
}
	
	        