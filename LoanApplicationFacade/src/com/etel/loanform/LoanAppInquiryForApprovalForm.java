package com.etel.loanform;

import java.math.BigDecimal;
import java.util.Date;

public class LoanAppInquiryForApprovalForm {
	
	private String transNo;
	private Date statusDate;
	private String memberId;
	private String employeeIdNo;
	private String employeeName;
	private String companyName;
	private String cluster;
	private String loanType;
	private BigDecimal loanAmount;
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
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
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
	public boolean isDeviationFlag() {
		return deviationFlag;
	}
	public void setDeviationFlag(boolean deviationFlag) {
		this.deviationFlag = deviationFlag;
	}

	
}
