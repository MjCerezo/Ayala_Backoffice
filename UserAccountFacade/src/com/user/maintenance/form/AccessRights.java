package com.user.maintenance.form;

public class AccessRights {

	private String module;
	private String modulename;
	private Integer level;
	private Integer subcount;
	private String page;
	
	public String getModule() {
		return module;
	}
	public String getModulename() {
		return modulename;
	}
	public Integer getLevel() {
		return level;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public void setModulename(String modulename) {
		this.modulename = modulename;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getSubcount() {
		return subcount;
	}
	public void setSubcount(Integer subcount) {
		this.subcount = subcount;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	
}
