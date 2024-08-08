package com.casa.acct.forms;

import java.util.Date;

public class StopPaymentOrderForm {

	private Integer id;
	private String acctno;
	private String chkno;
	private String reason;
	private Date validitydate;
	private String holdby;
	private Date datehold;
	private String name;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAcctno() {
		return acctno;
	}
	public void setAcctno(String acctno) {
		this.acctno = acctno;
	}
	public String getChkno() {
		return chkno;
	}
	public void setChkno(String chkno) {
		this.chkno = chkno;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Date getValiditydate() {
		return validitydate;
	}
	public void setValiditydate(Date validitydate) {
		this.validitydate = validitydate;
	}
	public String getHoldby() {
		return holdby;
	}
	public void setHoldby(String holdby) {
		this.holdby = holdby;
	}
	public Date getDatehold() {
		return datehold;
	}
	public void setDatehold(Date datehold) {
		this.datehold = datehold;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
