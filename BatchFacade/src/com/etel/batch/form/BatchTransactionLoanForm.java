/**
 * 
 */
package com.etel.batch.form;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ETEL-LAPTOP07
 *
 */
public class BatchTransactionLoanForm {
	private String accountno;
	private Date valuedate;
	private String txcode;
	private BigDecimal amount;
	private BigDecimal lpc;
	private BigDecimal ar;
	private BigDecimal interest;
	private BigDecimal principal;
	private String reason;
	private String remarks;
	private String txrefno;
	private String result;

	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public Date getValuedate() {
		return valuedate;
	}

	public void setValuedate(Date valuedate) {
		this.valuedate = valuedate;
	}

	public String getTxcode() {
		return txcode;
	}

	public void setTxcode(String txcode) {
		this.txcode = txcode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getLpc() {
		return lpc;
	}

	public void setLpc(BigDecimal lpc) {
		this.lpc = lpc;
	}

	public BigDecimal getAr() {
		return ar;
	}

	public void setAr(BigDecimal ar) {
		this.ar = ar;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public BigDecimal getPrincipal() {
		return principal;
	}

	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTxrefno() {
		return txrefno;
	}

	public void setTxrefno(String txrefno) {
		this.txrefno = txrefno;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
