package com.etel.passbook.form;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class PassbookDataForm {
	Integer lineNumber;
	BigInteger rowNumber;
	BigInteger sequenceNo;
	Date txvaldt;
	String mnemonic;
	BigDecimal withdrawal;
	BigDecimal deposit;
	BigDecimal outBal;
	
	
	public Integer getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}
	public BigInteger getSequenceNo() {
		return sequenceNo;
	}
	public void setSequenceNo(BigInteger sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	public BigInteger getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(BigInteger rowNumber) {
		this.rowNumber = rowNumber;
	}
	public Date getTxvaldt() {
		return txvaldt;
	}
	public void setTxvaldt(Date txvaldt) {
		this.txvaldt = txvaldt;
	}
	public String getMnemonic() {
		return mnemonic;
	}
	public void setMnemonic(String mnemonic) {
		this.mnemonic = mnemonic;
	}
	public BigDecimal getWithdrawal() {
		return withdrawal;
	}
	public void setWithdrawal(BigDecimal withdrawal) {
		this.withdrawal = withdrawal;
	}
	public BigDecimal getDeposit() {
		return deposit;
	}
	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
	}
	public BigDecimal getOutBal() {
		return outBal;
	}
	public void setOutBal(BigDecimal outBal) {
		this.outBal = outBal;
	}
	
	
}
