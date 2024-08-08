package com.etel.casareports.form;

import java.math.BigDecimal;
import java.util.Date;

public class TellersBlotterForm {
	
	
	String txrefno; // transrefno;
	String txbrrefno; // ormediano;
	String txcode; // transtype;	
	String remarks;
	String cifno;
	String accountname; // cifname;
	String checkacctno; // acctno;
	String productname;
	BigDecimal cashDebit;
	BigDecimal cashCredit;
	BigDecimal cociDebit;
	BigDecimal cociCredit;
	String createdby;
	Date txvaldt;
	Date txdate;
	
	public String getTxrefno() {
		return txrefno;
	}
	public void setTxrefno(String txrefno) {
		this.txrefno = txrefno;
	}
	public String getTxbrrefno() {
		return txbrrefno;
	}
	public void setTxbrrefno(String txbrrefno) {
		this.txbrrefno = txbrrefno;
	}
	public String getTxcode() {
		return txcode;
	}
	public void setTxcode(String txcode) {
		this.txcode = txcode;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCifno() {
		return cifno;
	}
	public void setCifno(String cifno) {
		this.cifno = cifno;
	}
	public String getAccountname() {
		return accountname;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	public String getCheckacctno() {
		return checkacctno;
	}
	public void setCheckacctno(String checkacctno) {
		this.checkacctno = checkacctno;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public BigDecimal getCashDebit() {
		return cashDebit;
	}
	public void setCashDebit(BigDecimal cashDebit) {
		this.cashDebit = cashDebit;
	}
	public BigDecimal getCashCredit() {
		return cashCredit;
	}
	public void setCashCredit(BigDecimal cashCredit) {
		this.cashCredit = cashCredit;
	}
	public BigDecimal getCociDebit() {
		return cociDebit;
	}
	public void setCociDebit(BigDecimal cociDebit) {
		this.cociDebit = cociDebit;
	}
	public BigDecimal getCociCredit() {
		return cociCredit;
	}
	public void setCociCredit(BigDecimal cociCredit) {
		this.cociCredit = cociCredit;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public Date getTxvaldt() {
		return txvaldt;
	}
	public void setTxvaldt(Date txvaldt) {
		this.txvaldt = txvaldt;
	}
	public Date getTxdate() {
		return txdate;
	}
	public void setTxdate(Date txdate) {
		this.txdate = txdate;
	}
	
	

	
}
