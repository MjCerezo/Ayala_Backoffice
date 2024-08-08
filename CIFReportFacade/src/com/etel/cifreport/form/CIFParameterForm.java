package com.etel.cifreport.form;

import java.util.Date;

public class CIFParameterForm {
	
	Boolean noFilter;
	Date asOf;
	Date startDate;
	Date endDate;
	String gender;
	String civilStat;
	String nationality;
	String cifStat;
	String riskRating;
	String registerTin;
	String businessType;
	String paidUpCapital;
	String firmSize;
	String cifDbLink;
	String losDbLink;
	String branch;
	String tellerName;
	
	
	public String getTellerName() {
		return tellerName;
	}
	public void setTellerName(String tellerName) {
		this.tellerName = tellerName;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
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
	public Boolean getNoFilter() {
		return noFilter;
	}
	public void setNoFilter(Boolean noFilter) {
		this.noFilter = noFilter;
	}
	public Date getAsOf() {
		return asOf;
	}
	public void setAsOf(Date asOf) {
		this.asOf = asOf;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCivilStat() {
		return civilStat;
	}
	public void setCivilStat(String civilStat) {
		this.civilStat = civilStat;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getCifStat() {
		return cifStat;
	}
	public void setCifStat(String cifStat) {
		this.cifStat = cifStat;
	}
	public String getRiskRating() {
		return riskRating;
	}
	public void setRiskRating(String riskRating) {
		this.riskRating = riskRating;
	}
	public String getRegisterTin() {
		return registerTin;
	}
	public void setRegisterTin(String registerTin) {
		this.registerTin = registerTin;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getPaidUpCapital() {
		return paidUpCapital;
	}
	public void setPaidUpCapital(String paidUpCapital) {
		this.paidUpCapital = paidUpCapital;
	}
	public String getFirmSize() {
		return firmSize;
	}
	public void setFirmSize(String firmSize) {
		this.firmSize = firmSize;
	}
}
