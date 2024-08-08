package com.etel.team.forms;

import java.util.Date;

public class UserrolesForm {
	
	private String username;
	private String roleid;
	private String rolename;
	private Date assigneddate;
	private String assignedby;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
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
	