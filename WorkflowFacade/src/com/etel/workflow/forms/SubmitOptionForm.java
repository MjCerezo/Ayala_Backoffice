package com.etel.workflow.forms;

import java.util.List;

public class SubmitOptionForm {
	
	private boolean btnSubmit = false;
	private boolean btnReturn = false;
	private String submitAppStatusDesc;
	private String returnAppStatusDesc;
	private boolean isrejectprocess = false;
	private boolean iscancelprocess = false;
	private boolean isbookprocess = false;
	private boolean slcSubmitOption = false;
	private boolean slcReturnOption = false;
	private List<WorkflowProcessForm> submitOptionDataSet;
	private List<WorkflowProcessForm> returnOptionDataSet;
	
	public boolean isBtnSubmit() {
		return btnSubmit;
	}
	public void setBtnSubmit(boolean btnSubmit) {
		this.btnSubmit = btnSubmit;
	}
	public boolean isBtnReturn() {
		return btnReturn;
	}
	public void setBtnReturn(boolean btnReturn) {
		this.btnReturn = btnReturn;
	}
	public String getSubmitAppStatusDesc() {
		return submitAppStatusDesc;
	}
	public void setSubmitAppStatusDesc(String submitAppStatusDesc) {
		this.submitAppStatusDesc = submitAppStatusDesc;
	}
	public String getReturnAppStatusDesc() {
		return returnAppStatusDesc;
	}
	public void setReturnAppStatusDesc(String returnAppStatusDesc) {
		this.returnAppStatusDesc = returnAppStatusDesc;
	}
	public boolean isIsrejectprocess() {
		return isrejectprocess;
	}
	public void setIsrejectprocess(boolean isrejectprocess) {
		this.isrejectprocess = isrejectprocess;
	}
	public boolean isIscancelprocess() {
		return iscancelprocess;
	}
	public void setIscancelprocess(boolean iscancelprocess) {
		this.iscancelprocess = iscancelprocess;
	}
	public boolean isIsbookprocess() {
		return isbookprocess;
	}
	public void setIsbookprocess(boolean isbookprocess) {
		this.isbookprocess = isbookprocess;
	}
	public boolean isSlcSubmitOption() {
		return slcSubmitOption;
	}
	public void setSlcSubmitOption(boolean slcSubmitOption) {
		this.slcSubmitOption = slcSubmitOption;
	}
	public boolean isSlcReturnOption() {
		return slcReturnOption;
	}
	public void setSlcReturnOption(boolean slcReturnOption) {
		this.slcReturnOption = slcReturnOption;
	}
	public List<WorkflowProcessForm> getSubmitOptionDataSet() {
		return submitOptionDataSet;
	}
	public void setSubmitOptionDataSet(List<WorkflowProcessForm> submitOptionDataSet) {
		this.submitOptionDataSet = submitOptionDataSet;
	}
	public List<WorkflowProcessForm> getReturnOptionDataSet() {
		return returnOptionDataSet;
	}
	public void setReturnOptionDataSet(List<WorkflowProcessForm> returnOptionDataSet) {
		this.returnOptionDataSet = returnOptionDataSet;
	}
	
	
	
}
