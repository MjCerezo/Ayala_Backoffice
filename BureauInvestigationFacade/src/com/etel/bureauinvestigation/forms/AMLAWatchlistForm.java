package com.etel.bureauinvestigation.forms;

import java.util.Date;

public class AMLAWatchlistForm {
	private String amlalistid;
    private String cifno;
    private String fullname;
    private String description;
    private String status;
    private Date startdate;
    private Date enddate;
    private String source;
    
	public String getAmlalistid() {
		return amlalistid;
	}
	public void setAmlalistid(String amlalistid) {
		this.amlalistid = amlalistid;
	}
	public String getCifno() {
		return cifno;
	}
	public void setCifno(String cifno) {
		this.cifno = cifno;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
    
}
