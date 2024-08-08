package com.etel.creditfacility.forms;

import java.math.BigDecimal;
import java.util.Date;

public class CoObligorForm {

	private String cfrefno;
    private String cfcifno;
    private String cfappno;
    private Integer cfshareseq;
    private String cfcifname;
    private BigDecimal cfamount;
    private String cfcifstatus;
    private Date cfcreateddate;
    private Date cfupdated;
    private String cfaddedby;
    private String cfupdatedby;
    private String remarks;
    private String businesstype;
    private Date dateofincorporation;
    private BigDecimal cfproposedamt;
    
	public String getBusinesstype() {
		return businesstype;
	}
	public void setBusinesstype(String businesstype) {
		this.businesstype = businesstype;
	}
	public Date getDateofincorporation() {
		return dateofincorporation;
	}
	public void setDateofincorporation(Date dateofincorporation) {
		this.dateofincorporation = dateofincorporation;
	}
	public BigDecimal getCfproposedamt() {
		return cfproposedamt;
	}
	public void setCfproposedamt(BigDecimal cfproposedamt) {
		this.cfproposedamt = cfproposedamt;
	}
	public String getCfrefno() {
		return cfrefno;
	}
	public void setCfrefno(String cfrefno) {
		this.cfrefno = cfrefno;
	}
	public String getCfcifno() {
		return cfcifno;
	}
	public void setCfcifno(String cfcifno) {
		this.cfcifno = cfcifno;
	}
	public String getCfappno() {
		return cfappno;
	}
	public void setCfappno(String cfappno) {
		this.cfappno = cfappno;
	}
	public Integer getCfshareseq() {
		return cfshareseq;
	}
	public void setCfshareseq(Integer cfshareseq) {
		this.cfshareseq = cfshareseq;
	}
	public String getCfcifname() {
		return cfcifname;
	}
	public void setCfcifname(String cfcifname) {
		this.cfcifname = cfcifname;
	}
	public BigDecimal getCfamount() {
		return cfamount;
	}
	public void setCfamount(BigDecimal cfamount) {
		this.cfamount = cfamount;
	}
	public String getCfcifstatus() {
		return cfcifstatus;
	}
	public void setCfcifstatus(String cfcifstatus) {
		this.cfcifstatus = cfcifstatus;
	}
	public Date getCfcreateddate() {
		return cfcreateddate;
	}
	public void setCfcreateddate(Date cfcreateddate) {
		this.cfcreateddate = cfcreateddate;
	}
	public Date getCfupdated() {
		return cfupdated;
	}
	public void setCfupdated(Date cfupdated) {
		this.cfupdated = cfupdated;
	}
	public String getCfaddedby() {
		return cfaddedby;
	}
	public void setCfaddedby(String cfaddedby) {
		this.cfaddedby = cfaddedby;
	}
	public String getCfupdatedby() {
		return cfupdatedby;
	}
	public void setCfupdatedby(String cfupdatedby) {
		this.cfupdatedby = cfupdatedby;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
    
}
