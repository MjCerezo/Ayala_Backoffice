package com.etel.lasform;

import java.math.BigDecimal;
import java.util.Date;

public class SearchLOSForm {
	
	private int id;
	
	private String cifname;
	private String appno;
	private String cifno;
	private String fulladdress1;
	private String createdby;
	
	private Date dateofbirth;
	private Date dateofincorporation;
	
	private BigDecimal faceamt;
	private String applicationstatus;
	private String loanproduct;
	private String branch;
	
	
	
	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getLoanproduct() {
		return loanproduct;
	}

	public void setLoanproduct(String loanproduct) {
		this.loanproduct = loanproduct;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCifname() {
		return cifname;
	}

	public void setCifname(String cifname) {
		this.cifname = cifname;
	}

	public String getAppno() {
		return appno;
	}

	public void setAppno(String appno) {
		this.appno = appno;
	}

	public String getCifno() {
		return cifno;
	}

	public void setCifno(String cifno) {
		this.cifno = cifno;
	}

	public String getFulladdress1() {
		return fulladdress1;
	}

	public void setFulladdress1(String fulladdress1) {
		this.fulladdress1 = fulladdress1;
	}

	public Date getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public Date getDateofincorporation() {
		return dateofincorporation;
	}

	public void setDateofincorporation(Date dateofincorporation) {
		this.dateofincorporation = dateofincorporation;
	}

	public BigDecimal getFaceamt() {
		return faceamt;
	}

	public void setFaceamt(BigDecimal faceamt) {
		this.faceamt = faceamt;
	}

	public String getApplicationstatus() {
		return applicationstatus;
	}

	public void setApplicationstatus(String applicationstatus) {
		this.applicationstatus = applicationstatus;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	
	
	

}
