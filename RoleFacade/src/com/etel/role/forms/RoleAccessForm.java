package com.etel.role.forms;

import java.util.Date;

public class RoleAccessForm {
    private String roleid;
    private String accessname;
    private String modulename;
    private Date assigneddate;
    private String assignedby;
    
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getAccessname() {
		return accessname;
	}
	public void setAccessname(String accessname) {
		this.accessname = accessname;
	}
	public String getModulename() {
		return modulename;
	}
	public void setModulename(String modulename) {
		this.modulename = modulename;
	}
	public Date getAssigneddate() {
		return assigneddate;
	}
	public void setAssigneddate(Date assigneddate) {
		this.assigneddate = assigneddate;
	}
	public String getAssignedby() {
		return assignedby;
	}
	public void setAssignedby(String assignedby) {
		this.assignedby = assignedby;
	}
    
}
