package com.etel.cicreports.form;

import java.util.Date;

public class CICParametersForm {

	Date from;
	Date to;
	String cifDbLink;
	String losDbLink;
	
	
	public String getCifDbLink() {
		return cifDbLink;
	}
	public void setCifDbLink(String cifDbLink) {
		this.cifDbLink = cifDbLink;
	}
	public String getLosDbLink() {
		return losDbLink;
	}
	public void setLosDbLink(String losDbLink) {
		this.losDbLink = losDbLink;
	}
	public Date getFrom() {
		return from;
	}
	public void setFrom(Date from) {
		this.from = from;
	}
	public Date getTo() {
		return to;
	}
	public void setTo(Date to) {
		this.to = to;
	}
}
