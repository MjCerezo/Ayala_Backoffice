package com.casa.acct.forms;

import java.math.BigDecimal;
import java.util.Date;

public class PlaceHoldForm {

	private int id;
	private String accountno;
	private String accountname;
	private String holdreason;
	private Date datehold;
	private Date expirydate;
	private Date validitydate;
	private BigDecimal acctbal;
	private BigDecimal amount;
	private BigDecimal availbal;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccountno() {
		return accountno;
	}
	public String getAccountname() {
		return accountname;
	}
	public String getHoldreason() {
		return holdreason;
	}
	public Date getDatehold() {
		return datehold;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	public void setHoldreason(String holdreason) {
		this.holdreason = holdreason;
	}
	public void setDatehold(Date datehold) {
		this.datehold = datehold;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Date getExpirydate() {
		return expirydate;
	}
	public Date getValiditydate() {
		return validitydate;
	}
	public BigDecimal getAcctbal() {
		return acctbal;
	}
	public void setExpirydate(Date expirydate) {
		this.expirydate = expirydate;
	}
	public void setValiditydate(Date validitydate) {
		this.validitydate = validitydate;
	}
	public void setAcctbal(BigDecimal acctbal) {
		this.acctbal = acctbal;
	}
	public BigDecimal getAvailbal() {
		return availbal;
	}
	public void setAvailbal(BigDecimal availbal) {
		this.availbal = availbal;
	}
	
}
