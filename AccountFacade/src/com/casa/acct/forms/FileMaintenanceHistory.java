/**
 * 
 */
package com.casa.acct.forms;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ETEL-LAPTOP19
 *
 */
public class FileMaintenanceHistory {
	
	String accountno;
	String txname;
	Date datecreated;
	Date effectivitydate;
	Date expirydate;
	String createdby;
	String remarks;
	BigDecimal txamount;
	
	public String getAccountno() {
		return accountno;
	}
	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}
	public String getTxname() {
		return txname;
	}
	public void setTxname(String txname) {
		this.txname = txname;
	}
	public Date getDatecreated() {
		return datecreated;
	}
	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}
	public Date getEffectivitydate() {
		return effectivitydate;
	}
	public void setEffectivitydate(Date effectivitydate) {
		this.effectivitydate = effectivitydate;
	}
	public Date getExpirydate() {
		return expirydate;
	}
	public void setExpirydate(Date expirydate) {
		this.expirydate = expirydate;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public BigDecimal getTxamount() {
		return txamount;
	}
	public void setTxamount(BigDecimal txamount) {
		this.txamount = txamount;
	}
}
