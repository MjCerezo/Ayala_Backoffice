/**
 * 
 */
package com.etel.casareports.form;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ETEL-LAPTOP19
 *
 */
public class ElectronicJournalData {
	
	Date txdate;
	String accountno;
	String accountname;
	String txcode;
	String txname;
	BigDecimal txamount;
	String txrefno;
	String txstatus;
	Boolean errorcorrectind;
	String userid;
	Boolean override;
	String remarks;
	

	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getTxdate() {
		return txdate;
	}
	public void setTxdate(Date txdate) {
		this.txdate = txdate;
	}
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
	public String getTxcode() {
		return txcode;
	}
	public void setTxcode(String txcode) {
		this.txcode = txcode;
	}
	public String getTxname() {
		return txname;
	}
	public void setTxname(String txname) {
		this.txname = txname;
	}
	public BigDecimal getTxamount() {
		return txamount;
	}
	public void setTxamount(BigDecimal txamount) {
		this.txamount = txamount;
	}
	public String getTxrefno() {
		return txrefno;
	}
	public void setTxrefno(String txrefno) {
		this.txrefno = txrefno;
	}
	public String getTxstatus() {
		return txstatus;
	}
	public void setTxstatus(String txstatus) {
		this.txstatus = txstatus;
	}
	public Boolean getErrorcorrectind() {
		return errorcorrectind;
	}
	public void setErrorcorrectind(Boolean errorcorrectind) {
		this.errorcorrectind = errorcorrectind;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Boolean getOverride() {
		return override;
	}
	public void setOverride(Boolean override) {
		this.override = override;
	}
}
