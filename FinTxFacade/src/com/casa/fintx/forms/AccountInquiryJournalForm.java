package com.casa.fintx.forms;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class AccountInquiryJournalForm {

	private BigInteger sequenceNo;
	private String txrefno;
	private Date txvaldt;
	private BigDecimal debit;
	private BigDecimal credit;
	private BigDecimal txamt;
	private BigDecimal outbal;
	private String txcode;
	private String errorcorrect;
	private String txbrrefno;
	private String unit;
	private String checkacctno;
	private Date txdate;
	private String createdby;
	private String accountname;
	
	
	public BigInteger getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(BigInteger sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

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

	public BigDecimal getDebit() {
		return debit;
	}

	public void setDebit(BigDecimal debit) {
		this.debit = debit;
	}

	public BigDecimal getCredit() {
		return credit;
	}

	public void setCredit(BigDecimal credit) {
		this.credit = credit;
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

	public String getErrorcorrect() {
		return errorcorrect;
	}

	public void setErrorcorrect(String errorcorrect) {
		this.errorcorrect = errorcorrect;
	}

	public String getTxbrrefno() {
		return txbrrefno;
	}

	public void setTxbrrefno(String txbrrefno) {
		this.txbrrefno = txbrrefno;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCheckacctno() {
		return checkacctno;
	}

	public void setCheckacctno(String checkacctno) {
		this.checkacctno = checkacctno;
	}

	public Date getTxdate() {
		return txdate;
	}

	public void setTxdate(Date txdate) {
		this.txdate = txdate;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
}
