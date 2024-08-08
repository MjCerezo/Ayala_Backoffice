package com.etel.workflowmaintenance.forms;

import java.util.Date;

public class WorkflowMaintenanceForm {
	
	private int workflowid;
	private String workflowname;
	private int sequenceno;
	private Boolean isactive;
	private String remarks;
	private String createdby;
	private Date datecreated;
	private String updatedby;
	private Date dateupdated;
	
	public int getWorkflowid() {
		return workflowid;
	}
	public void setWorkflowid(int workflowid) {
		this.workflowid = workflowid;
	}
	public String getWorkflowname() {
		return workflowname;
	}
	public void setWorkflowname(String workflowname) {
		this.workflowname = workflowname;
	}
	public int getSequenceno() {
		return sequenceno;
	}
	public void setSequenceno(int sequenceno) {
		this.sequenceno = sequenceno;
	}
	public Boolean getIsactive() {
		return isactive;
	}
	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public Date getDatecreated() {
		return datecreated;
	}
	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}
	public String getUpdatedby() {
		return updatedby;
	}
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}
	public Date getDateupdated() {
		return dateupdated;
	}
	public void setDateupdated(Date dateupdated) {
		this.dateupdated = dateupdated;
	}
	
	
	
	
	
	
	
	

}
