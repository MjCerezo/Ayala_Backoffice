package com.etel.bureauinvestigation.forms;

public class BIAccessRightsForm {
	private boolean btnSave = false;
	private boolean btnCancel = false;
	private boolean readOnly = true;
	private boolean btnSubmit = false;
	
	
	//BiRequest
	private boolean btnCreateReport = false;
	private boolean slcCompany = true;
	private boolean slcAssignedBi = true;
	
	//BiReport
	private boolean btnReturn = false;
	private boolean btnReview = false;
	private boolean btnDelete = false;
	private boolean btnStartRpt = false;
	
	public boolean isBtnStartRpt() {
		return btnStartRpt;
	}
	public void setBtnStartRpt(boolean btnStartRpt) {
		this.btnStartRpt = btnStartRpt;
	}
	public boolean isBtnReturn() {
		return btnReturn;
	}
	public void setBtnReturn(boolean btnReturn) {
		this.btnReturn = btnReturn;
	}
	public boolean isBtnSave() {
		return btnSave;
	}
	public void setBtnSave(boolean btnSave) {
		this.btnSave = btnSave;
	}
	public boolean isBtnCancel() {
		return btnCancel;
	}
	public void setBtnCancel(boolean btnCancel) {
		this.btnCancel = btnCancel;
	}
	public boolean isReadOnly() {
		return readOnly;
	}
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	public boolean isBtnSubmit() {
		return btnSubmit;
	}
	public void setBtnSubmit(boolean btnSubmit) {
		this.btnSubmit = btnSubmit;
	}
	public boolean isBtnCreateReport() {
		return btnCreateReport;
	}
	public void setBtnCreateReport(boolean btnCreateReport) {
		this.btnCreateReport = btnCreateReport;
	}
	public boolean isSlcCompany() {
		return slcCompany;
	}
	public void setSlcCompany(boolean slcCompany) {
		this.slcCompany = slcCompany;
	}
	public boolean isSlcAssignedBi() {
		return slcAssignedBi;
	}
	public void setSlcAssignedBi(boolean slcAssignedBi) {
		this.slcAssignedBi = slcAssignedBi;
	}
	public boolean isBtnReview() {
		return btnReview;
	}
	public void setBtnReview(boolean btnReview) {
		this.btnReview = btnReview;
	}
	public boolean isBtnDelete() {
		return btnDelete;
	}
	public void setBtnDelete(boolean btnDelete) {
		this.btnDelete = btnDelete;
	}
}
