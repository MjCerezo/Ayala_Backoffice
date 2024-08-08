package com.etel.collateralforms;

public class AppraisalAccessRights {
	private boolean showSubmitForReview = false;
	private boolean showReturn = false;
	private boolean showSaveReport = false;
	private boolean showStartReport = false;
	private boolean showDoneReview = false;
	
	private boolean disableSubmitForReview = false;
	private boolean disableReturn = false;
	private boolean disableSaveReport = false;
	private boolean disableStartReport = false;
	private boolean disableDoneReview = false;
	
	private boolean readOnly = true;
	
	public boolean isShowSubmitForReview() {
		return showSubmitForReview;
	}

	public void setShowSubmitForReview(boolean showSubmitForReview) {
		this.showSubmitForReview = showSubmitForReview;
	}

	public boolean isShowReturn() {
		return showReturn;
	}

	public void setShowReturn(boolean showReturn) {
		this.showReturn = showReturn;
	}

	public boolean isShowSaveReport() {
		return showSaveReport;
	}

	public void setShowSaveReport(boolean showSaveReport) {
		this.showSaveReport = showSaveReport;
	}

	public boolean isShowStartReport() {
		return showStartReport;
	}

	public void setShowStartReport(boolean showStartReport) {
		this.showStartReport = showStartReport;
	}

	public boolean isShowDoneReview() {
		return showDoneReview;
	}

	public void setShowDoneReview(boolean showDoneReview) {
		this.showDoneReview = showDoneReview;
	}

	public boolean isDisableSubmitForReview() {
		return disableSubmitForReview;
	}

	public void setDisableSubmitForReview(boolean disableSubmitForReview) {
		this.disableSubmitForReview = disableSubmitForReview;
	}

	public boolean isDisableReturn() {
		return disableReturn;
	}

	public void setDisableReturn(boolean disableReturn) {
		this.disableReturn = disableReturn;
	}

	public boolean isDisableSaveReport() {
		return disableSaveReport;
	}

	public void setDisableSaveReport(boolean disableSaveReport) {
		this.disableSaveReport = disableSaveReport;
	}

	public boolean isDisableStartReport() {
		return disableStartReport;
	}

	public void setDisableStartReport(boolean disableStartReport) {
		this.disableStartReport = disableStartReport;
	}

	public boolean isDisableDoneReview() {
		return disableDoneReview;
	}

	public void setDisableDoneReview(boolean disableDoneReview) {
		this.disableDoneReview = disableDoneReview;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
}
