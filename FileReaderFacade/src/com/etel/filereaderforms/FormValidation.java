package com.etel.filereaderforms;

import java.util.List;

public class FormValidation {
	private String flag;
	private String errorMessage;
	private List<CollateralVehicleForm> autoForm;
	private List<CollateralRealEstateForm> relForm;
	private List<ReceivableForm> receivableForm;
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public List<CollateralVehicleForm> getAutoForm() {
		return autoForm;
	}
	public void setAutoForm(List<CollateralVehicleForm> autoForm) {
		this.autoForm = autoForm;
	}
	public List<CollateralRealEstateForm> getRelForm() {
		return relForm;
	}
	public void setRelForm(List<CollateralRealEstateForm> relForm) {
		this.relForm = relForm;
	}
	public List<ReceivableForm> getReceivableForm() {
		return receivableForm;
	}
	public void setReceivableForm(List<ReceivableForm> receivableForm) {
		this.receivableForm = receivableForm;
	}
	
	
}