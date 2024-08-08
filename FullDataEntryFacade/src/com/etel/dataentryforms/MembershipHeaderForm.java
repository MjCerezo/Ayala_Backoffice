package com.etel.dataentryforms;

import java.util.Date;

public class MembershipHeaderForm {
	
	private String branch;
	private String cifNo;
	private String fullName;
	private Date dateOfBirth;
	private String tin;
	private String sss;
	private Date dateEncoded;
	private String memberType;
	private String applicationStatus;
	private String encodedBy;
	private String bankAccountNumber;
	private String typeOfAccount;
	private String solicitor;
	private String memberId;
	private Date dateApproved;
	
	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public Date getDateApproved() {
		return dateApproved;
	}
	public void setDateApproved(Date dateApproved) {
		this.dateApproved = dateApproved;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getCifNo() {
		return cifNo;
	}
	public void setCifNo(String cifNo) {
		this.cifNo = cifNo;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getTin() {
		return tin;
	}
	public void setTin(String tin) {
		this.tin = tin;
	}
	public String getSss() {
		return sss;
	}
	public void setSss(String sss) {
		this.sss = sss;
	}
	public Date getDateEncoded() {
		return dateEncoded;
	}
	public void setDateEncoded(Date dateEncoded) {
		this.dateEncoded = dateEncoded;
	}
	public String getMemberType() {
		return memberType;
	}
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	public String getEncodedBy() {
		return encodedBy;
	}
	public void setEncodedBy(String encodedBy) {
		this.encodedBy = encodedBy;
	}
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}
	public String getTypeOfAccount() {
		return typeOfAccount;
	}
	public void setTypeOfAccount(String typeOfAccount) {
		this.typeOfAccount = typeOfAccount;
	}
	public String getSolicitor() {
		return solicitor;
	}
	public void setSolicitor(String solicitor) {
		this.solicitor = solicitor;
	}
}
