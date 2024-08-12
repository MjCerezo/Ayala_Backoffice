package com.etel.loanform;

import java.math.BigDecimal;

public class LoanRuleForm {

	private String rank;
	private String tenure;
	private String position;
	private BigDecimal maxLoanAmount;
	private BigDecimal nthp;
	private int maxNoLoan;
	
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getTenure() {
		return tenure;
	}
	public void setTenure(String tenure) {
		this.tenure = tenure;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public BigDecimal getMaxLoanAmount() {
		return maxLoanAmount;
	}
	public void setMaxLoanAmount(BigDecimal maxLoanAmount) {
		this.maxLoanAmount = maxLoanAmount;
	}
	public BigDecimal getNthp() {
		return nthp;
	}
	public void setNthp(BigDecimal nthp) {
		this.nthp = nthp;
	}
	public int getMaxNoLoan() {
		return maxNoLoan;
	}
	public void setMaxNoLoan(int maxNoLoan) {
		this.maxNoLoan = maxNoLoan;
	}
	
	
}
