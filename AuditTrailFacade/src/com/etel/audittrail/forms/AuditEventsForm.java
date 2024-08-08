package com.etel.audittrail.forms;

public class AuditEventsForm {
	private int eventid;
	private String eventname;
	private String moduleid;
	private String codename;
	private int codevalue;
	private String desc1;
	
	public int getEventid() {
		return eventid;
	}
	public void setEventid(int eventid) {
		this.eventid = eventid;
	}
	public String getEventname() {
		return eventname;
	}
	public void setEventname(String eventname) {
		this.eventname = eventname;
	}
	public String getModuleid() {
		return moduleid;
	}
	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}
	public String getCodename() {
		return codename;
	}
	public void setCodename(String codename) {
		this.codename = codename;
	}
	public int getCodevalue() {
		return codevalue;
	}
	public void setCodevalue(int codevalue) {
		this.codevalue = codevalue;
	}
	public String getDesc1() {
		return desc1;
	}
	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}
}
