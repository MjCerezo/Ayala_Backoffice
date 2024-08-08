package com.etel.accessrights.forms;
public class StatusAndRoles {
	
	private String status;
	private boolean encoder = false;
	private boolean officer = false;
	private boolean user = false;
	private boolean secadmin = false;
	private boolean sysadmin = false;
	private boolean blacklistUser = false;
	private boolean blacklistApprover = false;
	private boolean amlaUser = false;
	private boolean amlaApprover = false;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isEncoder() {
		return encoder;
	}
	public void setEncoder(boolean encoder) {
		this.encoder = encoder;
	}
	public boolean isOfficer() {
		return officer;
	}
	public void setOfficer(boolean officer) {
		this.officer = officer;
	}
	public boolean isUser() {
		return user;
	}
	public void setUser(boolean user) {
		this.user = user;
	}
	public boolean isSecadmin() {
		return secadmin;
	}
	public void setSecadmin(boolean secadmin) {
		this.secadmin = secadmin;
	}
	public boolean isSysadmin() {
		return sysadmin;
	}
	public void setSysadmin(boolean sysadmin) {
		this.sysadmin = sysadmin;
	}
	public boolean isBlacklistUser() {
		return blacklistUser;
	}
	public void setBlacklistUser(boolean blacklistUser) {
		this.blacklistUser = blacklistUser;
	}
	public boolean isBlacklistApprover() {
		return blacklistApprover;
	}
	public void setBlacklistApprover(boolean blacklistApprover) {
		this.blacklistApprover = blacklistApprover;
	}
	public boolean isAmlaUser() {
		return amlaUser;
	}
	public void setAmlaUser(boolean amlaUser) {
		this.amlaUser = amlaUser;
	}
	public boolean isAmlaApprover() {
		return amlaApprover;
	}
	public void setAmlaApprover(boolean amlaApprover) {
		this.amlaApprover = amlaApprover;
	}
	

}
