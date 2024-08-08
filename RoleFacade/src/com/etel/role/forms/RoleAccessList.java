package com.etel.role.forms;
import java.util.List;

import com.coopdb.data.TbroleaccessId;

public class RoleAccessList {
	private List<TbroleaccessId> accessname;
	private String roleid;
	private String module;
	public List<TbroleaccessId> getAccessname() {
		return accessname;
	}
	public void setAccessname(List<TbroleaccessId> accessname) {
		this.accessname = accessname;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	
	
}
