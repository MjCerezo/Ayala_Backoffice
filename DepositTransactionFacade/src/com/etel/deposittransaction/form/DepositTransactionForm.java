/**
 * 
 */
package com.etel.deposittransaction.form;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.coopdb.data.Tbchecksforclearing;

/**
 * @author ETEL-LAPTOP19
 *
 */
public class DepositTransactionForm {

	String batchcode;
	String accountno;
	String txcode;
	Date valuedate;
	BigDecimal txamount;
	String txbranch;
	String username;
	String reason;
	String remarks;
	String txmode;
	String currency;
	List<Tbchecksforclearing> checks;
	boolean interesttransaction = false;
	String accountnoto;
	String userid;
	String txstatus = "2";
	boolean errorcorrect = false;
	String errorcorrecttxrefno;
	String transfertxrefno;
	String txrefno;
	String overridestatus = "0";
	String overrideusername;
	String overridepassword;

	public String getBatchcode() {
		return batchcode;
	}

	public void setBatchcode(String batchcode) {
		this.batchcode = batchcode;
	}

	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public String getTxcode() {
		return txcode;
	}

	public void setTxcode(String txcode) {
		this.txcode = txcode;
	}

	public Date getValuedate() {
		return valuedate;
	}

	public void setValuedate(Date valuedate) {
		this.valuedate = valuedate;
	}

	public BigDecimal getTxamount() {
		return txamount;
	}

	public void setTxamount(BigDecimal txamount) {
		this.txamount = txamount;
	}

	public String getTxbranch() {
		return txbranch;
	}

	public void setTxbranch(String txbranch) {
		this.txbranch = txbranch;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getTxmode() {
		return txmode;
	}

	public void setTxmode(String txmode) {
		this.txmode = txmode;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public List<Tbchecksforclearing> getChecks() {
		return checks;
	}

	public void setChecks(List<Tbchecksforclearing> checks) {
		this.checks = checks;
	}

	public boolean isInteresttransaction() {
		return interesttransaction;
	}

	public void setInteresttransaction(boolean interesttransaction) {
		this.interesttransaction = interesttransaction;
	}

	public String getAccountnoto() {
		return accountnoto;
	}

	public void setAccountnoto(String accountnoto) {
		this.accountnoto = accountnoto;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getTxstatus() {
		return txstatus;
	}

	public void setTxstatus(String txstatus) {
		this.txstatus = txstatus;
	}

	public boolean isErrorcorrect() {
		return errorcorrect;
	}

	public void setErrorcorrect(boolean errorcorrect) {
		this.errorcorrect = errorcorrect;
	}

	public String getErrorcorrecttxrefno() {
		return errorcorrecttxrefno;
	}

	public void setErrorcorrecttxrefno(String errorcorrecttxrefno) {
		this.errorcorrecttxrefno = errorcorrecttxrefno;
	}

	public String getTransfertxrefno() {
		return transfertxrefno;
	}

	public void setTransfertxrefno(String transfertxrefno) {
		this.transfertxrefno = transfertxrefno;
	}

	public String getTxrefno() {
		return txrefno;
	}

	public void setTxrefno(String txrefno) {
		this.txrefno = txrefno;
	}

	public String getOverridestatus() {
		return overridestatus;
	}

	public void setOverridestatus(String overridestatus) {
		this.overridestatus = overridestatus;
	}

	public String getOverrideusername() {
		return overrideusername;
	}

	public void setOverrideusername(String overrideusername) {
		this.overrideusername = overrideusername;
	}

	public String getOverridepassword() {
		return overridepassword;
	}

	public void setOverridepassword(String overridepassword) {
		this.overridepassword = overridepassword;
	}

	@Override
	public String toString() {
		return "DepositTransactionForm [batchcode=" + batchcode + ", accountno=" + accountno + ", txcode=" + txcode
				+ ", valuedate=" + valuedate + ", txamount=" + txamount + ", txbranch=" + txbranch + ", username="
				+ username + ", reason=" + reason + ", remarks=" + remarks + ", txmode=" + txmode + ", currency="
				+ currency + ", checks=" + checks + ", interesttransaction=" + interesttransaction + ", accountnoto="
				+ accountnoto + ", userid=" + userid + ", txstatus=" + txstatus + ", errorcorrect=" + errorcorrect
				+ ", errorcorrecttxrefno=" + errorcorrecttxrefno + ", transfertxrefno=" + transfertxrefno + ", txrefno="
				+ txrefno + ", overridestatus=" + overridestatus + ", overrideusername=" + overrideusername
				+ ", overridepassword=" + overridepassword + "]";
	}

}
