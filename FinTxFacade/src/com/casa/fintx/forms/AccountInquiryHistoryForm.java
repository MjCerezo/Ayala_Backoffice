package com.casa.fintx.forms;

import java.math.BigDecimal;
import java.util.Date;

public class AccountInquiryHistoryForm {

	private String txrefno;
	private Date txvaldt;
	private BigDecimal txamt;
	private BigDecimal outbal;
	private String txcode;
	
	public String getTxrefno() {
		return txrefno;
	}
	public void setTxrefno(String txrefno) {
		this.txrefno = txrefno;
	}
	public Date getTxvaldt() {
		return txvaldt;
	}
	public void setTxvaldt(Date txvaldt) {
		this.txvaldt = txvaldt;
	}
	public BigDecimal getTxamt() {
		return txamt;
	}
	public void setTxamt(BigDecimal txamt) {
		this.txamt = txamt;
	}
	public BigDecimal getOutbal() {
		return outbal;
	}
	public void setOutbal(BigDecimal outbal) {
		this.outbal = outbal;
	}
	public String getTxcode() {
		return txcode;
	}
	public void setTxcode(String txcode) {
		this.txcode = txcode;
	}
	
}
