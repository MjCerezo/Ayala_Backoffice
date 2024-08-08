package com.etel.reports.forms;

import java.util.Date;

public class TimeDepParam {
	private Date txdate;
	private String branchcode;
	private String filetype;
	private String reporttype;
	
	public Date getTxdate() {
		return txdate;
	}
	public void setTxdate(Date txdate) {
		this.txdate = txdate;
	}
	public String getBranchcode() {
		return branchcode;
	}
	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}
	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	public String getReporttype() {
		return reporttype;
	}
	public void setReporttype(String reporttype) {
		this.reporttype = reporttype;
	}

}
