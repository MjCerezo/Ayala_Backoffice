/**
 * 
 */
package com.etel.depositprint.form;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ETEL-LAPTOP19
 *
 */
public class ValidationSlipForm {

	String accountno;
	String accountname;
	String txname;
	String currency;
	String txrefno;
	Date txdate;
	BigDecimal txamount;
	public String getAccountno() {
		return accountno;
	}
	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}
	public String getAccountname() {
		return accountname;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	public String getTxname() {
		return txname;
	}
	public void setTxname(String txname) {
		this.txname = txname;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getTxrefno() {
		return txrefno;
	}
	public void setTxrefno(String txrefno) {
		this.txrefno = txrefno;
	}
	public Date getTxdate() {
		return txdate;
	}
	public void setTxdate(Date txdate) {
		this.txdate = txdate;
	}
	public BigDecimal getTxamount() {
		return txamount;
	}
	public void setTxamount(BigDecimal txamount) {
		this.txamount = txamount;
	}
}
