package com.etel.inquiry.forms;

import java.util.Date;

public class CompanyListPerStagesForm {
	private String branch;
	private String companyNo;
	private String companyName;
	private String companyAddress;
	private String applicationStatus;
	private Date dateEncoded;
	
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getCompanyNo() {
		return companyNo;
	}
	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	public Date getDateEncoded() {
		return dateEncoded;
	}
	public void setDateEncoded(Date dateEncoded) {
		this.dateEncoded = dateEncoded;
	}
	
	

}
