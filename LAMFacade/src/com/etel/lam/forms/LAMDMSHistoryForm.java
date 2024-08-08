package com.etel.lam.forms;

import java.util.Date;

public class LAMDMSHistoryForm {

	private String appno;
	private String cfrefno;
	private Integer evalreportid;
	private String dmsid;
	private Date dateuploaded;
	private String uploadedby;

	public String getAppno() {
		return appno;
	}

	public void setAppno(String appno) {
		this.appno = appno;
	}

	public String getCfrefno() {
		return cfrefno;
	}

	public void setCfrefno(String cfrefno) {
		this.cfrefno = cfrefno;
	}

	public Integer getEvalreportid() {
		return evalreportid;
	}

	public void setEvalreportid(Integer evalreportid) {
		this.evalreportid = evalreportid;
	}

	public String getDmsid() {
		return dmsid;
	}

	public void setDmsid(String dmsid) {
		this.dmsid = dmsid;
	}

	public Date getDateuploaded() {
		return dateuploaded;
	}

	public void setDateuploaded(Date dateuploaded) {
		this.dateuploaded = dateuploaded;
	}

	public String getUploadedby() {
		return uploadedby;
	}

	public void setUploadedby(String uploadedby) {
		this.uploadedby = uploadedby;
	}
}
