package com.etel.role.forms;

import java.util.Date;

public class AccessRightsForm {
    private Integer accessid;
    private String accessname;
	private String modulename;
    private String accesstype;
    private String submodulename;
    private String createdby;
    private Date createddate;
    private String updatedby;
    private Date dateupdated;
    private String description;
    private String navorder;
    private String navparent;
    private String parentsubmodulename;
    private String parentnavorder;
    
	public Integer getAccessid() {
		return accessid;
	}
	public void setAccessid(Integer accessid) {
		this.accessid = accessid;
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
	public String getAccesstype() {
		return accesstype;
	}
	public void setAccesstype(String accesstype) {
		this.accesstype = accesstype;
	}
	public String getSubmodulename() {
		return submodulename;
	}
	public void setSubmodulename(String submodulename) {
		this.submodulename = submodulename;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public Date getCreateddate() {
		return createddate;
	}
	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNavorder() {
		return navorder;
	}
	public void setNavorder(String navorder) {
		this.navorder = navorder;
	}
	public String getNavparent() {
		return navparent;
	}
	public void setNavparent(String navparent) {
		this.navparent = navparent;
	}
	public String getParentsubmodulename() {
		return parentsubmodulename;
	}
	public void setParentsubmodulename(String parentsubmodulename) {
		this.parentsubmodulename = parentsubmodulename;
	}
	
	public String getParentnavorder() {
		return parentnavorder;
	}
	
	public void setParentnavorder(String parentnavorder) {
		this.parentnavorder = parentnavorder;
	}
    
}
