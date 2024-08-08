package com.etel.blacklist.forms;

import java.util.Date;

public class BlacklistInquiryForm {
	
   private String fullname;
   private Date dateofbirth;
   private Date dateofincorporation;
   private String status;
   private String customertype;
   
   
public String getFullname() {
	return fullname;
}
public void setFullname(String fullname) {
	this.fullname = fullname;
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
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getCustomertype() {
	return customertype;
}
public void setCustomertype(String customertype) {
	this.customertype = customertype;
}
   
}
