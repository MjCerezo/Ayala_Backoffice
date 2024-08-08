package com.casa.acct.forms;

import java.math.BigDecimal;
import java.util.Date;

public class AccountClosureForm {

	private String accountno;
	private String accountname;
	private String accountstatus;
	private Date dtlastupdate;
	private String posttx;
	private BigDecimal currentbal;
	private BigDecimal floatamt;
	private BigDecimal holdamt;
	private BigDecimal availablebal;
	private BigDecimal unpostedint;
	private BigDecimal wtaxamt;
	private BigDecimal closingbal;

	public String getAccountno() {
		return accountno;
	}
	public String getAccountname() {
		return accountname;
	}
	public String getAccountstatus() {
		return accountstatus;
	}
	public Date getDtlastupdate() {
		return dtlastupdate;
	}
	public String getPosttx() {
		return posttx;
	}
	public BigDecimal getCurrentbal() {
		return currentbal;
	}
	public BigDecimal getFloatamt() {
		return floatamt;
	}
	public BigDecimal getHoldamt() {
		return holdamt;
	}
	public BigDecimal getAvailablebal() {
		return availablebal;
	}
	public BigDecimal getUnpostedint() {
		return unpostedint;
	}
	public BigDecimal getWtaxamt() {
		return wtaxamt;
	}
	public BigDecimal getClosingbal() {
		return closingbal;
	}
	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	public void setAccountstatus(String accountstatus) {
		this.accountstatus = accountstatus;
	}
	public void setDtlastupdate(Date dtlastupdate) {
		this.dtlastupdate = dtlastupdate;
	}
	public void setPosttx(String posttx) {
		this.posttx = posttx;
	}
	public void setCurrentbal(BigDecimal currentbal) {
		this.currentbal = currentbal;
	}
	public void setFloatamt(BigDecimal floatamt) {
		this.floatamt = floatamt;
	}
	public void setHoldamt(BigDecimal holdamt) {
		this.holdamt = holdamt;
	}
	public void setAvailablebal(BigDecimal availablebal) {
		this.availablebal = availablebal;
	}
	public void setUnpostedint(BigDecimal unpostedint) {
		this.unpostedint = unpostedint;
	}
	public void setWtaxamt(BigDecimal wtaxamt) {
		this.wtaxamt = wtaxamt;
	}
	public void setClosingbal(BigDecimal closingbal) {
		this.closingbal = closingbal;
	}
	
}
