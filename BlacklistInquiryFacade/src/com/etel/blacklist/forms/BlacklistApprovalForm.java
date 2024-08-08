package com.etel.blacklist.forms;

import java.util.Date;

public class BlacklistApprovalForm {
	
   private String requesttype;
   private String customername;
   private String customertype;
   private String requeststatus;
   private String source;
   private Date dateuploaded;
   
public String getRequesttype() {
	return requesttype;
}
public void setRequesttype(String requesttype) {
	this.requesttype = requesttype;
}
public String getCustomername() {
	return customername;
}
public void setCustomername(String customername) {
	this.customername = customername;
}
public String getCustomertype() {
	return customertype;
}
public void setCustomertype(String customertype) {
	this.customertype = customertype;
}
public String getRequeststatus() {
	return requeststatus;
}
public void setRequeststatus(String requeststatus) {
	this.requeststatus = requeststatus;
}
public String getSource() {
	return source;
}
public void setSource(String source) {
	this.source = source;
}
public Date getDateuploaded() {
	return dateuploaded;
}
public void setDateuploaded(Date dateuploaded) {
	this.dateuploaded = dateuploaded;
}



}
