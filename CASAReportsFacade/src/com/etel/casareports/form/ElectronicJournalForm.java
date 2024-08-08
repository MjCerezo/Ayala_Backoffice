/**
 * 
 */
package com.etel.casareports.form;

import java.util.Date;

/**
 * @author ETEL-LAPTOP19
 *
 */
public class ElectronicJournalForm {
	
	Date txdate;
	String userid;
	String name;
	String branchcode;
	String txcode;
	String txstatus;
	Boolean errorcorrectind;

	public Date getTxdate() {
		return txdate;
	}
	public void setTxdate(Date txdate) {
		this.txdate = txdate;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBranchcode() {
		return branchcode;
	}
	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}
	public String getTxcode() {
		return txcode;
	}
	public void setTxcode(String txcode) {
		this.txcode = txcode;
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
}
