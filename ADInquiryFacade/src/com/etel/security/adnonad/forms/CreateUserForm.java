package com.etel.security.adnonad.forms;

public class CreateUserForm {
	private String flag = "failed";
	private String returnMessage;
	private String smtpFlag = "failed";
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getReturnMessage() {
		return returnMessage;
	}
	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}
	public String getSmtpFlag() {
		return smtpFlag;
	}
	public void setSmtpFlag(String smtpFlag) {
		this.smtpFlag = smtpFlag;
	}
	
	
}
