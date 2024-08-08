package com.etel.creditfacility.forms;

import java.util.Date;

public class CorpSubsidiaryForm {

	private String cifno;
    private String corporatename;
    private String cifstatus;
    private Date dateofincorporation;
    private String businesstype;
    private String tin;
    private String originatingteam;
    private Date encodeddate;
    private String fulladdress1;
    
	public String getFulladdress1() {
		return fulladdress1;
	}
	public void setFulladdress1(String fulladdress1) {
		this.fulladdress1 = fulladdress1;
	}
	public String getTin() {
		return tin;
	}
	public void setTin(String tin) {
		this.tin = tin;
	}
	public String getOriginatingteam() {
		return originatingteam;
	}
	public void setOriginatingteam(String originatingteam) {
		this.originatingteam = originatingteam;
	}
	public Date getEncodeddate() {
		return encodeddate;
	}
	public void setEncodeddate(Date encodeddate) {
		this.encodeddate = encodeddate;
	}
	public String getCifno() {
		return cifno;
	}
	public void setCifno(String cifno) {
		this.cifno = cifno;
	}
	public String getCorporatename() {
		return corporatename;
	}
	public void setCorporatename(String corporatename) {
		this.corporatename = corporatename;
	}
	public String getCifstatus() {
		return cifstatus;
	}
	public void setCifstatus(String cifstatus) {
		this.cifstatus = cifstatus;
	}
	public Date getDateofincorporation() {
		return dateofincorporation;
	}
	public void setDateofincorporation(Date dateofincorporation) {
		this.dateofincorporation = dateofincorporation;
	}
	public String getBusinesstype() {
		return businesstype;
	}
	public void setBusinesstype(String businesstype) {
		this.businesstype = businesstype;
	}
    
}
