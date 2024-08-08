package com.etel.accessrights.forms;

public class ReadOnlyOrDisable {
	private boolean readOnly = true;
	private boolean disable = true;
	private boolean isDisableMainbtns = true; 
	private boolean showing = false;
	private boolean isShowingMainbtns = false;
	private boolean editMainButton = false;
	
	public boolean isEditMainButton() {
		return editMainButton;
	}
	public void setEditMainButton(boolean editMainButton) {
		this.editMainButton = editMainButton;
	}
	public boolean isShowingMainbtns() {
		return isShowingMainbtns;
	}
	public void setShowingMainbtns(boolean isShowingMainbtns) {
		this.isShowingMainbtns = isShowingMainbtns;
	}
	public boolean isShowing() {
		return showing;
	}
	public void setShowing(boolean showing) {
		this.showing = showing;
	}
	public boolean isDisableMainbtns() {
		return isDisableMainbtns;
	}
	public void setDisableMainbtns(boolean isDisableMainbtns) {
		this.isDisableMainbtns = isDisableMainbtns;
	}
	public boolean isReadOnly() {
		return readOnly;
	}
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	public boolean isDisable() {
		return disable;
	}
	public void setDisable(boolean disable) {
		this.disable = disable;
	}
	
}
