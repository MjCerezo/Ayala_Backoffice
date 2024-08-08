package com.etel.accessrights.forms;

public class RequestForm {
	private boolean btnPanel = false;
	private boolean saveAsDraftBtn = false;
	private boolean submitBtn = false;
	private boolean editBtn = false;
	private boolean cancelBtn = false;
	private boolean approveBtn = false;
	private boolean returnBtn = false;
	private boolean rejectBtn = false;
	private boolean requestNewBtn = false;
	
	
	public boolean isRequestNewBtn() {
		return requestNewBtn;
	}
	public void setRequestNewBtn(boolean requestNewBtn) {
		this.requestNewBtn = requestNewBtn;
	}
	public boolean isRejectBtn() {
		return rejectBtn;
	}
	public void setRejectBtn(boolean rejectBtn) {
		this.rejectBtn = rejectBtn;
	}
	public boolean isBtnPanel() {
		return btnPanel;
	}
	public void setBtnPanel(boolean btnPanel) {
		this.btnPanel = btnPanel;
	}
	public boolean isSaveAsDraftBtn() {
		return saveAsDraftBtn;
	}
	public void setSaveAsDraftBtn(boolean saveAsDraftBtn) {
		this.saveAsDraftBtn = saveAsDraftBtn;
	}
	public boolean isSubmitBtn() {
		return submitBtn;
	}
	public void setSubmitBtn(boolean submitBtn) {
		this.submitBtn = submitBtn;
	}
	public boolean isEditBtn() {
		return editBtn;
	}
	public void setEditBtn(boolean editBtn) {
		this.editBtn = editBtn;
	}
	public boolean isCancelBtn() {
		return cancelBtn;
	}
	public void setCancelBtn(boolean cancelBtn) {
		this.cancelBtn = cancelBtn;
	}
	public boolean isApproveBtn() {
		return approveBtn;
	}
	public void setApproveBtn(boolean approveBtn) {
		this.approveBtn = approveBtn;
	}
	public boolean isReturnBtn() {
		return returnBtn;
	}
	public void setReturnBtn(boolean returnBtn) {
		this.returnBtn = returnBtn;
	}
	
	
}
