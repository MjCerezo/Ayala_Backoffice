package com.etel.audittrailreport.form;

import java.math.BigDecimal;
import java.util.Date;

public class AuditTrailReportForm {
	String eventname;
	String eventdescription;
	String username;
	String module;
	Date eventdatetime;
	String ipaddress;
	public String getEventname() {
		return eventname;
	}
	public void setEventname(String eventname) {
		this.eventname = eventname;
	}
	public String getEventdescription() {
		return eventdescription;
	}
	public void setEventdescription(String eventdescription) {
		this.eventdescription = eventdescription;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public Date getEventdatetime() {
		return eventdatetime;
	}
	public void setEventdatetime(Date eventdatetime) {
		this.eventdatetime = eventdatetime;
	}
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	
	
}
