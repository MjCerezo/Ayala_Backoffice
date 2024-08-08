/**
 * 
 */
package com.casa.util.forms;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ETEL-COMP05
 *
 */
public class PassBookFrom {
	
	int line;
	Date txdate;
	String txcode;
	BigDecimal debit;
	BigDecimal credit;
	BigDecimal outBal;
	
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public Date getTxdate() {
		return txdate;
	}
	public void setTxdate(Date txdate) {
		this.txdate = txdate;
	}
	public String getTxcode() {
		return txcode;
	}
	public void setTxcode(String txcode) {
		this.txcode = txcode;
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
	public BigDecimal getOutBal() {
		return outBal;
	}
	public void setOutBal(BigDecimal outBal) {
		this.outBal = outBal;
	}
	
	
}
