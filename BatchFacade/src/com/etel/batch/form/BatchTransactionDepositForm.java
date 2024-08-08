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
public class BatchTransactionDepositForm {

	private String accountno;
	private Date valuedate;
	private String txcode;
	private BigDecimal amount;
	private String reason;
	private String remarks;
	private String txrefno;
	private String checknumber;
	private String brstn;
	private int clearingdays;
	private Date clearingdate;
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

	public String getChecknumber() {
		return checknumber;
	}

	public void setChecknumber(String checknumber) {
		this.checknumber = checknumber;
	}

	public String getBrstn() {
		return brstn;
	}

	public void setBrstn(String brstn) {
		this.brstn = brstn;
	}

	public int getClearingdays() {
		return clearingdays;
	}

	public void setClearingdays(int clearingdays) {
		this.clearingdays = clearingdays;
	}

	public Date getClearingdate() {
		return clearingdate;
	}

	public void setClearingdate(Date clearingdate) {
		this.clearingdate = clearingdate;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
