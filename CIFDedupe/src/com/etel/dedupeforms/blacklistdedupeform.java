package com.etel.dedupeforms;

import java.util.Date;

public class blacklistdedupeform {
	
	private int id;	
    private String blacklistid;
    private String cifno;
    private String fullname;
    private Date createddate;
    private String blacklistsource;
    private String status;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBlacklistid() {
		return blacklistid;
	}
	public void setBlacklistid(String blacklistid) {
		this.blacklistid = blacklistid;
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
	public Date getCreateddate() {
		return createddate;
	}
	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}
	public String getBlacklistsource() {
		return blacklistsource;
	}
	public void setBlacklistsource(String blacklistsource) {
		this.blacklistsource = blacklistsource;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    
    
    
}
