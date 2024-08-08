package com.etel.changecifdetails.form;

import java.math.BigDecimal;
import java.util.Date;

public class ChangeCIFDetailsForm {
	
	Integer processid;
	String processname;
	Date daterequested;
	String requestid;
	String cifno;
	String changetype;
	String changefrom;
	String changeto;
	String requestedby;
	String remarks;
	
	
	public Integer getProcessid() {
		return processid;
	}
	public void setProcessid(Integer processid) {
		this.processid = processid;
	}
	public String getProcessname() {
		return processname;
	}
	public void setProcessname(String processname) {
		this.processname = processname;
	}
	public Date getDaterequested() {
		return daterequested;
	}
	public void setDaterequested(Date daterequested) {
		this.daterequested = daterequested;
	}
	public String getRequestid() {
		return requestid;
	}
	public void setRequestid(String requestid) {
		this.requestid = requestid;
	}
	public String getCifno() {
		return cifno;
	}
	public void setCifno(String cifno) {
		this.cifno = cifno;
	}
	public String getChangetype() {
		return changetype;
	}
	public void setChangetype(String changetype) {
		this.changetype = changetype;
	}
	public String getChangefrom() {
		return changefrom;
	}
	public void setChangefrom(String changefrom) {
		this.changefrom = changefrom;
	}
	public String getChangeto() {
		return changeto;
	}
	public void setChangeto(String changeto) {
		this.changeto = changeto;
	}
	public String getRequestedby() {
		return requestedby;
	}
	public void setRequestedby(String requestedby) {
		this.requestedby = requestedby;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
}
