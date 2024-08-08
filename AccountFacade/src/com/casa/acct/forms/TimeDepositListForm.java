package com.casa.acct.forms;

import java.math.BigDecimal;
import java.util.Date;

public class TimeDepositListForm {

	private String accountno;
	private String accountname;
	private BigDecimal accountbal;
	private int term;
	private Date matdt;
	
	public String getAccountno() {
		return accountno;
	}
	public String getAccountname() {
		return accountname;
	}
	public BigDecimal getAccountbal() {
		return accountbal;
	}
	public int getTerm() {
		return term;
	}
	public Date getMatdt() {
		return matdt;
	}
	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	public void setAccountbal(BigDecimal accountbal) {
		this.accountbal = accountbal;
	}
	public void setTerm(int term) {
		this.term = term;
	}
	public void setMatdt(Date matdt) {
		this.matdt = matdt;
	}
	
}
