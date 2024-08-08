package com.etel.company.forms;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;

public class CooperativeForm {
    
	private String coopcode;
    private String coopname;
    private String address;
    private String phoneno;
    private String faxno;
    private String emailaddress;
    private String website;
    private Boolean coopstatus;
    private Blob logo;
    private Date datecreated;
    private String createdby;
    private Date dateupdated;
    private String updatedby;
    private BigDecimal membershipfee;
    private BigDecimal sharecapitalparval;
    private String coopsize;
    private BigDecimal finefee;
    
	public String getCoopcode() {
		return coopcode;
	}
	public void setCoopcode(String coopcode) {
		this.coopcode = coopcode;
	}
	public String getCoopname() {
		return coopname;
	}
	public void setCoopname(String coopname) {
		this.coopname = coopname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	public String getFaxno() {
		return faxno;
	}
	public void setFaxno(String faxno) {
		this.faxno = faxno;
	}
	public String getEmailaddress() {
		return emailaddress;
	}
	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public Boolean getCoopstatus() {
		return coopstatus;
	}
	public void setCoopstatus(Boolean coopstatus) {
		this.coopstatus = coopstatus;
	}
	public Blob getLogo() {
		return logo;
	}
	public void setLogo(Blob logo) {
		this.logo = logo;
	}
	public Date getDatecreated() {
		return datecreated;
	}
	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public Date getDateupdated() {
		return dateupdated;
	}
	public void setDateupdated(Date dateupdated) {
		this.dateupdated = dateupdated;
	}
	public String getUpdatedby() {
		return updatedby;
	}
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}
	public BigDecimal getMembershipfee() {
		return membershipfee;
	}
	public void setMembershipfee(BigDecimal membershipfee) {
		this.membershipfee = membershipfee;
	}
	public BigDecimal getSharecapitalparval() {
		return sharecapitalparval;
	}
	public void setSharecapitalparval(BigDecimal sharecapitalparval) {
		this.sharecapitalparval = sharecapitalparval;
	}
	public String getCoopsize() {
		return coopsize;
	}
	public void setCoopsize(String coopsize) {
		this.coopsize = coopsize;
	}
	public BigDecimal getFinefee() {
		return finefee;
	}
	public void setFinefee(BigDecimal finefee) {
		this.finefee = finefee;
	}
    
    
}
