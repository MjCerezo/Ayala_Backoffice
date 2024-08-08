package com.etel.collateralforms;

public class CAAccessRightsForm {

	private boolean btnSave = false;
	private boolean btnCancel = false;
	private boolean readOnly = true;
	private boolean btnSubmit = false;
	
	//CARequest
	private boolean btnCreateReport = false;
	private boolean slcCompany = true;
	private boolean slcAssignedCA = true;
	
	//CAReport
	private boolean btnReturn = false;
	private boolean btnReview = false;
	private boolean btnDelete = false;
	public boolean isBtnSave() {
		return btnSave;
	}
	public boolean isBtnCancel() {
		return btnCancel;
	}
	public boolean isReadOnly() {
		return readOnly;
	}
	public boolean isBtnSubmit() {
		return btnSubmit;
	}
	public boolean isBtnCreateReport() {
		return btnCreateReport;
	}
	public boolean isSlcCompany() {
		return slcCompany;
	}
	public boolean isSlcAssignedCA() {
		return slcAssignedCA;
	}
	public boolean isBtnReturn() {
		return btnReturn;
	}
	public boolean isBtnReview() {
		return btnReview;
	}
	public boolean isBtnDelete() {
		return btnDelete;
	}
	public void setBtnSave(boolean btnSave) {
		this.btnSave = btnSave;
	}
	public void setBtnCancel(boolean btnCancel) {
		this.btnCancel = btnCancel;
	}
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	public void setBtnSubmit(boolean btnSubmit) {
		this.btnSubmit = btnSubmit;
	}
	public void setBtnCreateReport(boolean btnCreateReport) {
		this.btnCreateReport = btnCreateReport;
	}
	public void setSlcCompany(boolean slcCompany) {
		this.slcCompany = slcCompany;
	}
	public void setSlcAssignedCA(boolean slcAssignedCA) {
		this.slcAssignedCA = slcAssignedCA;
	}
	public void setBtnReturn(boolean btnReturn) {
		this.btnReturn = btnReturn;
	}
	public void setBtnReview(boolean btnReview) {
		this.btnReview = btnReview;
	}
	public void setBtnDelete(boolean btnDelete) {
		this.btnDelete = btnDelete;
	}
	
	
}
