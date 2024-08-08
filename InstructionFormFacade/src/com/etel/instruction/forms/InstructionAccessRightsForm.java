package com.etel.instruction.forms;

public class InstructionAccessRightsForm {
	private boolean btnCreateRequest = false;
	private boolean btnSave = false;
	private boolean slcInstruction = true;
	private boolean txtAORemarks = true;
	private boolean txtSupervisorRemarks = true;
	public boolean isBtnCreateRequest() {
		return btnCreateRequest;
	}
	public void setBtnCreateRequest(boolean btnCreateRequest) {
		this.btnCreateRequest = btnCreateRequest;
	}
	public boolean isBtnSave() {
		return btnSave;
	}
	public void setBtnSave(boolean btnSave) {
		this.btnSave = btnSave;
	}
	public boolean isSlcInstruction() {
		return slcInstruction;
	}
	public void setSlcInstruction(boolean slcInstruction) {
		this.slcInstruction = slcInstruction;
	}
	public boolean isTxtAORemarks() {
		return txtAORemarks;
	}
	public void setTxtAORemarks(boolean txtAORemarks) {
		this.txtAORemarks = txtAORemarks;
	}
	public boolean isTxtSupervisorRemarks() {
		return txtSupervisorRemarks;
	}
	public void setTxtSupervisorRemarks(boolean txtSupervisorRemarks) {
		this.txtSupervisorRemarks = txtSupervisorRemarks;
	}
	
}
