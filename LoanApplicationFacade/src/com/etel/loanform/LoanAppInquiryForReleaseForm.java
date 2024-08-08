package com.etel.loanform;

import java.math.BigDecimal;
import java.util.Date;

public class LoanAppInquiryForReleaseForm {
	
	private String transNo;
	private Date statusDate;
	private String memberId;
	private String employeeIdNo;
	private String memberName;
	private String companyName;
	private String cluster;
	private String loanType;
	private BigDecimal loanAmount;
	private String depositAccountNo;
	private int term;
	private BigDecimal intRate;
	private BigDecimal amortization;
	private BigDecimal netProceeds;
	private String modeRelease;
	private boolean deviationFlag;
	
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	public Date getStatusDate() {
		return statusDate;
	}
	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getEmployeeIdNo() {
		return employeeIdNo;
	}
	public void setEmployeeIdNo(String employeeIdNo) {
		this.employeeIdNo = employeeIdNo;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCluster() {
		return cluster;
	}
	public void setCluster(String cluster) {
		this.cluster = cluster;
	}
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public BigDecimal getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getDepositAccountNo() {
		return depositAccountNo;
	}
	public void setDepositAccountNo(String depositAccountNo) {
		this.depositAccountNo = depositAccountNo;
	}
	public int getTerm() {
		return term;
	}
	public void setTerm(int term) {
		this.term = term;
	}
	public BigDecimal getIntRate() {
		return intRate;
	}
	public void setIntRate(BigDecimal intRate) {
		this.intRate = intRate;
	}
	public BigDecimal getAmortization() {
		return amortization;
	}
	public void setAmortization(BigDecimal amortization) {
		this.amortization = amortization;
	}
	public BigDecimal getNetProceeds() {
		return netProceeds;
	}
	public void setNetProceeds(BigDecimal netProceeds) {
		this.netProceeds = netProceeds;
	}
	public String getModeRelease() {
		return modeRelease;
	}
	public void setModeRelease(String modeRelease) {
		this.modeRelease = modeRelease;
	}
	public boolean isDeviationFlag() {
		return deviationFlag;
	}
	public void setDeviationFlag(boolean deviationFlag) {
		this.deviationFlag = deviationFlag;
	}

	
}
