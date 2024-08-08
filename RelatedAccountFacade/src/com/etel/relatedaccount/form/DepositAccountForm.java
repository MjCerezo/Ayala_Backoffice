package com.etel.relatedaccount.form;
import java.math.BigDecimal;
import java.util.Date;

public class DepositAccountForm {

	String accountNo;
	String prodName;
	String subProdName;
	String acctType;
	Date bookDate;
	BigDecimal intRate;
	String branch;
	String solicitingofficer;
	String accountStatus;
	BigDecimal mADB;
	BigDecimal yTD;
	BigDecimal availBal;
	BigDecimal bookBal;
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getSubProdName() {
		return subProdName;
	}
	public void setSubProdName(String subProdName) {
		this.subProdName = subProdName;
	}
	public String getAcctType() {
		return acctType;
	}
	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}
	public Date getBookDate() {
		return bookDate;
	}
	public void setBookDate(Date bookDate) {
		this.bookDate = bookDate;
	}
	public BigDecimal getIntRate() {
		return intRate;
	}
	public void setIntRate(BigDecimal intRate) {
		this.intRate = intRate;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getSolicitingofficer() {
		return solicitingofficer;
	}
	public void setSolicitingofficer(String solicitingofficer) {
		this.solicitingofficer = solicitingofficer;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public BigDecimal getmADB() {
		return mADB;
	}
	public void setmADB(BigDecimal mADB) {
		this.mADB = mADB;
	}
	public BigDecimal getyTD() {
		return yTD;
	}
	public void setyTD(BigDecimal yTD) {
		this.yTD = yTD;
	}
	public BigDecimal getAvailBal() {
		return availBal;
	}
	public void setAvailBal(BigDecimal availBal) {
		this.availBal = availBal;
	}
	public BigDecimal getBookBal() {
		return bookBal;
	}
	public void setBookBal(BigDecimal bookBal) {
		this.bookBal = bookBal;
	}
	
}
