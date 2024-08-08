/**
 * 
 */
package com.etel.casareports.form;

import java.util.Date;
import java.util.List;

/**
 * @author ETEL-LAPTOP19
 *
 */
public class ElectronicJournalResponse {
	
	String branchcode;
	Date txdate;
	String userid;
	String name;
	String terminalno;
	List<ElectronicJournalData> jrnl;

	public String getBranchcode() {
		return branchcode;
	}
	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}
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
	public String getTerminalno() {
		return terminalno;
	}
	public void setTerminalno(String terminalno) {
		this.terminalno = terminalno;
	}
	public List<ElectronicJournalData> getJrnl() {
		return jrnl;
	}
	public void setJrnl(List<ElectronicJournalData> jrnl) {
		this.jrnl = jrnl;
	}
}
