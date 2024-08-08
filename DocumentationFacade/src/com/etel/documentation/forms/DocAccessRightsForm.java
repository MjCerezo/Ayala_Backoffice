package com.etel.documentation.forms;

public class DocAccessRightsForm {

	private boolean readOnly = true;
	private boolean btnSaveOrDelete = false;
	private boolean btnSubmitToDocHead = false;
	private boolean btnSubmitToDocAnalyst = false;
	private boolean btnReturnToDocAnalyst = false;
	private boolean btnReturn = false;
	private boolean btnSubmitApplication = false;

	public boolean isReadOnly() {
		return readOnly;
	}
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	public boolean isBtnSaveOrDelete() {
		return btnSaveOrDelete;
	}
	public void setBtnSaveOrDelete(boolean btnSaveOrDelete) {
		this.btnSaveOrDelete = btnSaveOrDelete;
	}
	public boolean isBtnSubmitToDocHead() {
		return btnSubmitToDocHead;
	}
	public void setBtnSubmitToDocHead(boolean btnSubmitToDocHead) {
		this.btnSubmitToDocHead = btnSubmitToDocHead;
	}
	public boolean isBtnSubmitToDocAnalyst() {
		return btnSubmitToDocAnalyst;
	}
	public void setBtnSubmitToDocAnalyst(boolean btnSubmitToDocAnalyst) {
		this.btnSubmitToDocAnalyst = btnSubmitToDocAnalyst;
	}
	public boolean isBtnReturnToDocAnalyst() {
		return btnReturnToDocAnalyst;
	}
	public void setBtnReturnToDocAnalyst(boolean btnReturnToDocAnalyst) {
		this.btnReturnToDocAnalyst = btnReturnToDocAnalyst;
	}
	public boolean isBtnReturn() {
		return btnReturn;
	}
	public void setBtnReturn(boolean btnReturn) {
		this.btnReturn = btnReturn;
	}
	public boolean isBtnSubmitApplication() {
		return btnSubmitApplication;
	}
	public void setBtnSubmitApplication(boolean btnSubmitApplication) {
		this.btnSubmitApplication = btnSubmitApplication;
	}

	
}
