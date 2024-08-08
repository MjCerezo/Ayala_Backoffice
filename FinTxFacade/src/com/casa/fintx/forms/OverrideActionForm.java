package com.casa.fintx.forms;

import java.math.BigDecimal;

public class OverrideActionForm {

	private String acctno;
	private BigDecimal amount;
	private String txby;
	private String txcode;
	private String txrefno;
	private String source;
	
	public String getAcctno() {
		return acctno;
	}
	public void setAcctno(String acctno) {
		this.acctno = acctno;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getTxby() {
		return txby;
	}
	public void setTxby(String txby) {
		this.txby = txby;
	}
	public String getTxcode() {
		return txcode;
	}
	public void setTxcode(String txcode) {
		this.txcode = txcode;
	}
	public String getTxrefno() {
		return txrefno;
	}
	public void setTxrefno(String txrefno) {
		this.txrefno = txrefno;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
}
