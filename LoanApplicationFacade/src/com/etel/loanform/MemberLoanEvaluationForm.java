package com.etel.loanform;

import java.math.BigDecimal;

public class MemberLoanEvaluationForm {
	
	private String rank;
	private int tenure;
	private String position;
	private BigDecimal maxLoanAmount;
	private BigDecimal nthp;
	private int maxNoLoan;
	private int totalLoanAvailed;
	private int totalLoanExempted;
	
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public int getTenure() {
		return tenure;
	}
	public void setTenure(int tenure) {
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
	public int getTotalLoanAvailed() {
		return totalLoanAvailed;
	}
	public void setTotalLoanAvailed(int totalLoanAvailed) {
		this.totalLoanAvailed = totalLoanAvailed;
	}
	public int getTotalLoanExempted() {
		return totalLoanExempted;
	}
	public void setTotalLoanExempted(int totalLoanExempted) {
		this.totalLoanExempted = totalLoanExempted;
	}
	
	
	
}
