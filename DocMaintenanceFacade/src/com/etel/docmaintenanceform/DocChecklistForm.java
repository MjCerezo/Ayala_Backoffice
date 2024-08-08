package com.etel.docmaintenanceform;

import java.util.Date;

public class DocChecklistForm {
	
	private String documentname;
	private String docstatus;
	private String remarks;
	private String docanalystremarks;
	private Date dateuploaded;
	private String doccategory;
	private String doctype;
	private boolean appytoindiv;
	private boolean applytocorp;
	
	public String getDocumentname() {
		return documentname;
	}
	public void setDocumentname(String documentname) {
		this.documentname = documentname;
	}
	public String getDocstatus() {
		return docstatus;
	}
	public void setDocstatus(String docstatus) {
		this.docstatus = docstatus;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDocanalystremarks() {
		return docanalystremarks;
	}
	public void setDocanalystremarks(String docanalystremarks) {
		this.docanalystremarks = docanalystremarks;
	}
	public Date getDateuploaded() {
		return dateuploaded;
	}
	public void setDateuploaded(Date dateuploaded) {
		this.dateuploaded = dateuploaded;
	}
	public String getDoccategory() {
		return doccategory;
	}
	public void setDoccategory(String doccategory) {
		this.doccategory = doccategory;
	}
	public String getDoctype() {
		return doctype;
	}
	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}
	public boolean isAppytoindiv() {
		return appytoindiv;
	}
	public void setAppytoindiv(boolean appytoindiv) {
		this.appytoindiv = appytoindiv;
	}
	public boolean isApplytocorp() {
		return applytocorp;
	}
	public void setApplytocorp(boolean applytocorp) {
		this.applytocorp = applytocorp;
	}
	
}
