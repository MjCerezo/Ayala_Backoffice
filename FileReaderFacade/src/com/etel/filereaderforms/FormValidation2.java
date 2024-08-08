package com.etel.filereaderforms;

import java.util.List;

import com.loansdb.data.Tbcireceivables;

public class FormValidation2 {

	private String flag;
	private String errorMessage;
	private List<ReceivableForm> receivableFormTemp;
	private List<ReceivableForm> receivableForm;
	private List<ReceivableForm> receivableFormInvalid;
	private List<ReceivableForm> receivableFormNotUploaded;
	private List<Tbcireceivables> receivableFormCI;
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public List<ReceivableForm> getReceivableFormTemp() {
		return receivableFormTemp;
	}
	public void setReceivableFormTemp(List<ReceivableForm> receivableFormTemp) {
		this.receivableFormTemp = receivableFormTemp;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public List<ReceivableForm> getReceivableForm() {
		return receivableForm;
	}
	public void setReceivableForm(List<ReceivableForm> receivableForm) {
		this.receivableForm = receivableForm;
	}
	public List<ReceivableForm> getReceivableFormInvalid() {
		return receivableFormInvalid;
	}
	public void setReceivableFormInvalid(List<ReceivableForm> receivableFormInvalid) {
		this.receivableFormInvalid = receivableFormInvalid;
	}
	public List<ReceivableForm> getReceivableFormNotUploaded() {
		return receivableFormNotUploaded;
	}
	public void setReceivableFormNotUploaded(List<ReceivableForm> receivableFormNotUploaded) {
		this.receivableFormNotUploaded = receivableFormNotUploaded;
	}
	public List<Tbcireceivables> getReceivableFormCI() {
		return receivableFormCI;
	}
	public void setReceivableFormCI(List<Tbcireceivables> receivableFormCI) {
		this.receivableFormCI = receivableFormCI;
	}
	
	
	
	
	
	
	
	
}
