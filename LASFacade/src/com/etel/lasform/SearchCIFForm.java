package com.etel.lasform;

import java.util.Date;

public class SearchCIFForm {

	private int id;
	
	private String fullname;
	private String cifno;
	private String fulladdress1;
	
	private Date dateofbirth;
	private Date dateofincorporation;
	
	private String branch;
	
	
	
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
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
	
	
	
	
}
