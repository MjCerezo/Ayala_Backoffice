/**
 * 
 */
package com.etel.casareports.form;

import java.util.Date;

/**
 * @author ETEL-LAPTOP19
 *
 */
public class DormatForm {
	
	Date txDate;
	String userId;
	String branchCode;
	String reportCode;
	public Date getTxDate() {
		return txDate;
	}
	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getReportCode() {
		return reportCode;
	}
	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}
	
}
