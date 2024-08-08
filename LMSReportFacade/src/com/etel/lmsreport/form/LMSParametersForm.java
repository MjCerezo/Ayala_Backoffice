package com.etel.lmsreport.form;
import java.math.BigDecimal;
import java.util.Date;

public class LMSParametersForm {

	Boolean noFilter;
	Date startDate;
	Date asOf;
	Date endDate;
	Date transDate;
	String borrowerType;
	String cifno;
	String accountno;
	String accountname;
	String loanProduct;
	String transType;
	String officer;
	String accountStatus;
	String transStat;
	String decision;
	String lastName;
	String firstName;
	String businessName;
	String fromAccountStat;
	String toAccountStat;
	String cifDbLink;
	String losDbLink;
	String branch;
	String sourceOfPayment;
	String loanOfficer;
	Date from;
	Date to;
	String dateFilter;
	String agingType;
	Integer enterTargetCount;
	BigDecimal enterTargetAmount;
	Integer enterStoredCount;
	BigDecimal enterStoredAmount;
	
	
	public String getCifno() {
		return cifno;
	}
	public void setCifno(String cifno) {
		this.cifno = cifno;
	}
	public String getAccountno() {
		return accountno;
	}
	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}
	public String getAccountname() {
		return accountname;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	public String getAgingType() {
		return agingType;
	}
	public void setAgingType(String agingType) {
		this.agingType = agingType;
	}
	public Date getFrom() {
		return from;
	}
	public void setFrom(Date from) {
		this.from = from;
	}
	public Date getTo() {
		return to;
	}
	public void setTo(Date to) {
		this.to = to;
	}
	public String getDateFilter() {
		return dateFilter;
	}
	public void setDateFilter(String dateFilter) {
		this.dateFilter = dateFilter;
	}
	public Integer getEnterTargetCount() {
		return enterTargetCount;
	}
	public void setEnterTargetCount(Integer enterTargetCount) {
		this.enterTargetCount = enterTargetCount;
	}
	public BigDecimal getEnterTargetAmount() {
		return enterTargetAmount;
	}
	public void setEnterTargetAmount(BigDecimal enterTargetAmount) {
		this.enterTargetAmount = enterTargetAmount;
	}
	public Integer getEnterStoredCount() {
		return enterStoredCount;
	}
	public void setEnterStoredCount(Integer enterStoredCount) {
		this.enterStoredCount = enterStoredCount;
	}
	public BigDecimal getEnterStoredAmount() {
		return enterStoredAmount;
	}
	public void setEnterStoredAmount(BigDecimal enterStoredAmount) {
		this.enterStoredAmount = enterStoredAmount;
	}
	public String getLoanOfficer() {
		return loanOfficer;
	}
	public void setLoanOfficer(String loanOfficer) {
		this.loanOfficer = loanOfficer;
	}
	public Date getAsOf() {
		return asOf;
	}
	public void setAsOf(Date asOf) {
		this.asOf = asOf;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getSourceOfPayment() {
		return sourceOfPayment;
	}
	public void setSourceOfPayment(String sourceOfPayment) {
		this.sourceOfPayment = sourceOfPayment;
	}
	public String getCifDbLink() {
		return cifDbLink;
	}
	public void setCifDbLink(String cifDbLink) {
		this.cifDbLink = cifDbLink;
	}
	public String getLosDbLink() {
		return losDbLink;
	}
	public void setLosDbLink(String losDbLink) {
		this.losDbLink = losDbLink;
	}
	public String getFromAccountStat() {
		return fromAccountStat;
	}
	public void setFromAccountStat(String fromAccountStat) {
		this.fromAccountStat = fromAccountStat;
	}
	public String getToAccountStat() {
		return toAccountStat;
	}
	public void setToAccountStat(String toAccountStat) {
		this.toAccountStat = toAccountStat;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	public Boolean getNoFilter() {
		return noFilter;
	}
	public void setNoFilter(Boolean noFilter) {
		this.noFilter = noFilter;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	public String getBorrowerType() {
		return borrowerType;
	}
	public void setBorrowerType(String borrowerType) {
		this.borrowerType = borrowerType;
	}
	public String getLoanProduct() {
		return loanProduct;
	}
	public void setLoanProduct(String loanProduct) {
		this.loanProduct = loanProduct;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getOfficer() {
		return officer;
	}
	public void setOfficer(String officer) {
		this.officer = officer;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getTransStat() {
		return transStat;
	}
	public void setTransStat(String transStat) {
		this.transStat = transStat;
	}
	
	
}
