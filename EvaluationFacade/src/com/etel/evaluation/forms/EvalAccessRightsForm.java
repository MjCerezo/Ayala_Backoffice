package com.etel.evaluation.forms;

public class EvalAccessRightsForm {
	
	private boolean readOnly = true;
	private boolean btnSaveOrDelete = false;
	private boolean btnSubmitToEvalHead = false;
	private boolean btnSubmitToEvaluator = false;
	private boolean btnReturnToEvaluator = false;
	private boolean btnReturn = false;
	private boolean btnSubmitApplication = false;
	private boolean txtAssignedEvaluator = true;
	private boolean txtEvaluatorRemarks = true;
	private boolean txtEvalHeadRemarks = true;
	private boolean btnStartReport = true;
	private boolean btnApproversDecision = false;
	
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
	public boolean isBtnSubmitToEvalHead() {
		return btnSubmitToEvalHead;
	}
	public void setBtnSubmitToEvalHead(boolean btnSubmitToEvalHead) {
		this.btnSubmitToEvalHead = btnSubmitToEvalHead;
	}
	public boolean isBtnSubmitToEvaluator() {
		return btnSubmitToEvaluator;
	}
	public void setBtnSubmitToEvaluator(boolean btnSubmitToEvaluator) {
		this.btnSubmitToEvaluator = btnSubmitToEvaluator;
	}
	public boolean isBtnReturnToEvaluator() {
		return btnReturnToEvaluator;
	}
	public void setBtnReturnToEvaluator(boolean btnReturnToEvaluator) {
		this.btnReturnToEvaluator = btnReturnToEvaluator;
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
	public boolean isTxtAssignedEvaluator() {
		return txtAssignedEvaluator;
	}
	public void setTxtAssignedEvaluator(boolean txtAssignedEvaluator) {
		this.txtAssignedEvaluator = txtAssignedEvaluator;
	}
	public boolean isTxtEvaluatorRemarks() {
		return txtEvaluatorRemarks;
	}
	public void setTxtEvaluatorRemarks(boolean txtEvaluatorRemarks) {
		this.txtEvaluatorRemarks = txtEvaluatorRemarks;
	}
	public boolean isTxtEvalHeadRemarks() {
		return txtEvalHeadRemarks;
	}
	public void setTxtEvalHeadRemarks(boolean txtEvalHeadRemarks) {
		this.txtEvalHeadRemarks = txtEvalHeadRemarks;
	}
	public boolean isBtnStartReport() {
		return btnStartReport;
	}
	public void setBtnStartReport(boolean btnStartReport) {
		this.btnStartReport = btnStartReport;
	}
	public boolean isBtnApproversDecision() {
		return btnApproversDecision;
	}
	public void setBtnApproversDecision(boolean btnApproversDecision) {
		this.btnApproversDecision = btnApproversDecision;
	}
	
}
