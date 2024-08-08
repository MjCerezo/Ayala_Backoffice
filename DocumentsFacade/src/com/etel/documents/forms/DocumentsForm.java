package com.etel.documents.forms;

import java.util.Date;

public class DocumentsForm {
	
	private Integer docid;
	private String dmsid;
	private String cifno;
//	private String pnno;
//	private String linerefno;
//	private String name;
//	private String signatoryname;
//	private String collateralno;
	private String doccategory;
	private String doctype;
	private Date datereceived;
	private String docstatus;
	private String updatecycle;
	private String remarks;
//	private String idtype;
//	private String idno;
//	private Date issuedate;
//	private String issuecountry;
//	private Date expirydate;
//	private String issuedby;
//	private String coveredyear;
//	private Date datefiledbir;
//	private Date lineeffectivitydate;
//	private Date lineexpirydate;
//	private String chassisno;
//	private String description;
//	private String tctno;
//	private String suretyno;
//	private String fundercifno;
//	private String loitype;
//	private String proofdoctype;
//	private Date dateserved;
	private String uploadedby;
	private Date dateuploaded;
	private String updatedby;
	private Date dateupdated;
	private Date dateadded;
//	private Date incorporationdate;
//	private String registrationno;
//	private String registrationtype;
//	private Date dateofmeeting;
//	private Date secsubmitteddate;
//	private String tatussecsubmission;
//	private String loanappno;
//	private Date dateofapplication;
//	private String pqtype;
	public Integer getDocid() {
		return docid;
	}
	public void setDocid(Integer docid) {
		this.docid = docid;
	}
	public String getDmsid() {
		return dmsid;
	}
	public void setDmsid(String dmsid) {
		this.dmsid = dmsid;
	}
	public String getCifno() {
		return cifno;
	}
	public void setCifno(String cifno) {
		this.cifno = cifno;
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
	public Date getDatereceived() {
		return datereceived;
	}
	public void setDatereceived(Date datereceived) {
		this.datereceived = datereceived;
	}
	public String getDocstatus() {
		return docstatus;
	}
	public void setDocstatus(String docstatus) {
		this.docstatus = docstatus;
	}
	public String getUpdatecycle() {
		return updatecycle;
	}
	public void setUpdatecycle(String updatecycle) {
		this.updatecycle = updatecycle;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getUploadedby() {
		return uploadedby;
	}
	public void setUploadedby(String uploadedby) {
		this.uploadedby = uploadedby;
	}
	public Date getDateuploaded() {
		return dateuploaded;
	}
	public void setDateuploaded(Date dateuploaded) {
		this.dateuploaded = dateuploaded;
	}
	public String getUpdatedby() {
		return updatedby;
	}
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}
	public Date getDateupdated() {
		return dateupdated;
	}
	public void setDateupdated(Date dateupdated) {
		this.dateupdated = dateupdated;
	}
	public Date getDateadded() {
		return dateadded;
	}
	public void setDateadded(Date dateadded) {
		this.dateadded = dateadded;
	}


}
