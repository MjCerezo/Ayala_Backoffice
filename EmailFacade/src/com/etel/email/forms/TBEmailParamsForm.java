package com.etel.email.forms;

import java.util.Date;

public class TBEmailParamsForm {

	private String emailtype;
    private String emailcode;
    private String username;
	private String emailadd;
    private Date createddate;
    private String createdby;
    
	public String getEmailtype() {
		return emailtype;
	}
	public void setEmailtype(String emailtype) {
		this.emailtype = emailtype;
	}
	public String getEmailcode() {
		return emailcode;
	}
	public void setEmailcode(String emailcode) {
		this.emailcode = emailcode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmailadd() {
		return emailadd;
	}
	public void setEmailadd(String emailadd) {
		this.emailadd = emailadd;
	}
	public Date getCreateddate() {
		return createddate;
	}
	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
    
    
}
