package com.etel.casareports.form;

import java.util.Date;

public class CASAParametersForm {

	Date asOf;
	Date matDate;
	Date from;
	Date to;
	Date transDate;
	Date businessDate;
	String prodType;
	String subProdSA;
	String subProdTD;
	String accountStatus;
	String tellersName;
	Boolean noFilter;
	String cifNo;
	String accountName;
	String dispositionType;
	String cifDbLink;
	String losDbLink;
	String branch;
	String transType;
	String prodCode;
	String dateFilter;
	
	public String getDateFilter() {
		return dateFilter;
	}

	public void setDateFilter(String dateFilter) {
		this.dateFilter = dateFilter;
	}

	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getSubProdSA() {
		return subProdSA;
	}

	public void setSubProdSA(String subProdSA) {
		this.subProdSA = subProdSA;
	}

	public String getSubProdTD() {
		return subProdTD;
	}

	public void setSubProdTD(String subProdTD) {
		this.subProdTD = subProdTD;
	}

	public Date getMatDate() {
		return matDate;
	}

	public void setMatDate(Date matDate) {
		this.matDate = matDate;
	}

	public Date getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
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


	public String getDispositionType() {
		return dispositionType;
	}


	public void setDispositionType(String dispositionType) {
		this.dispositionType = dispositionType;
	}


	public String getCifNo() {
		return cifNo;
	}


	public void setCifNo(String cifNo) {
		this.cifNo = cifNo;
	}


	public String getAccountName() {
		return accountName;
	}


	public void setAccountName(String accountName) {
		this.accountName = accountName;
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


	public Date getAsOf() {
		return asOf;
	}
	
	
	public void setAsOf(Date asOf) {
		this.asOf = asOf;
	}
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getTellersName() {
		return tellersName;
	}
	public void setTellersName(String tellersName) {
		this.tellersName = tellersName;
	}
	public Boolean getNoFilter() {
		return noFilter;
	}
	public void setNoFilter(Boolean noFilter) {
		this.noFilter = noFilter;
	}
	
	
}
