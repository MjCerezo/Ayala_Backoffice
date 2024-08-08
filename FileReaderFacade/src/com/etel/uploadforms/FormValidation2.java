package com.etel.uploadforms;

import java.util.List;

public class FormValidation2 {
	private String flag;
	private String errorMessage;
	private List<EmployeeForm> empForm;
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
	public List<EmployeeForm> getEmpForm() {
		return empForm;
	}
	public void setEmpForm(List<EmployeeForm> empForm) {
		this.empForm = empForm;
	}
	
	

}
