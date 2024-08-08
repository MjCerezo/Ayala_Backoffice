/**
 * 
 */
package com.etel.deposit.form;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ETEL-COMP05
 *
 */
public class DepositLedgerForm {
	
	private String acctno;
    private String txrefno;
    private Date txdate;
    private int txoper;
    private String txcode;
    private BigDecimal txamt;
    private BigDecimal outbal;
	public String getAcctno() {
		return acctno;
	}
	public void setAcctno(String acctno) {
		this.acctno = acctno;
	}
	public String getTxrefNo() {
		return txrefno;
	}
	public void setTxrefNo(String txrefNo) {
		this.txrefno = txrefNo;
	}
	public Date getTxdate() {
		return txdate;
	}
	public void setTxdate(Date txdate) {
		this.txdate = txdate;
	}
	public int getTxoper() {
		return txoper;
	}
	public void setTxoper(int txoper) {
		this.txoper = txoper;
	}
	public String getTxcode() {
		return txcode;
	}
	public void setTxcode(String txcode) {
		this.txcode = txcode;
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
}
