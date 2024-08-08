/**
 * 
 */
package com.etel.override.form;

import java.math.BigDecimal;

/**
 * @author ETEL-LAPTOP19
 *
 */
public class OverrideRequestForm {

	private String txcode;
	private String prodcode;
	private String subprodcode;
	private String accountno;
	private BigDecimal amount;
	private String accountbranch;
	private String transactingbranch;

	public String getTxcode() {
		return txcode;
	}
	public void setTxcode(String txcode) {
		this.txcode = txcode;
	}
	public String getProdcode() {
		return prodcode;
	}
	public void setProdcode(String prodcode) {
		this.prodcode = prodcode;
	}
	public String getSubprodcode() {
		return subprodcode;
	}
	public void setSubprodcode(String subprodcode) {
		this.subprodcode = subprodcode;
	}
	public String getAccountno() {
		return accountno;
	}
	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getAccountbranch() {
		return accountbranch;
	}
	public void setAccountbranch(String accountbranch) {
		this.accountbranch = accountbranch;
	}
	public String getTransactingbranch() {
		return transactingbranch;
	}
	public void setTransactingbranch(String transactingbranch) {
		this.transactingbranch = transactingbranch;
	}
}
