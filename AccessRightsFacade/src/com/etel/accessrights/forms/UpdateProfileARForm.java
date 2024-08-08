package com.etel.accessrights.forms;

import java.util.Map;

import com.coopdb.data.Tbupdateprofilerequest;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;

public class UpdateProfileARForm {
	private boolean saveAsDraft = false;
	private boolean submitRequest = false;
	private boolean cancelRequest = false;
	private boolean approveRequest = false;
	private boolean declineRequest = false;
	private boolean readOnly = false;
	private boolean showCivilStatus = false;
	private boolean showDateOfBirth = false;
	private boolean showName = false;
	private boolean showMembershipStatus = false;
	private boolean showMembershipClass = false;
	private boolean showBranch = false;
	private boolean showCompany = false;

	public boolean isShowMembershipStatus() {
		return showMembershipStatus;
	}

	public void setShowMembershipStatus(boolean showMembershipStatus) {
		this.showMembershipStatus = showMembershipStatus;
	}

	public boolean isShowMembershipClass() {
		return showMembershipClass;
	}

	public void setShowMembershipClass(boolean showMembershipClass) {
		this.showMembershipClass = showMembershipClass;
	}

	public boolean isShowBranch() {
		return showBranch;
	}

	public void setShowBranch(boolean showBranch) {
		this.showBranch = showBranch;
	}

	public boolean isShowCompany() {
		return showCompany;
	}

	public void setShowCompany(boolean showCompany) {
		this.showCompany = showCompany;
	}

	public UpdateProfileARForm (Integer txrefno) {
		Map<String, Object> params = HQLUtil.getMap();
		DBService dbService = new DBServiceImpl();
//		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		try {
			params.put("txrefno", txrefno);
			Tbupdateprofilerequest o = (Tbupdateprofilerequest) dbService
					.executeUniqueHQLQuery("FROM Tbupdateprofilerequest WHERE txrefno=:txrefno", params);
			if (o != null) {
				if (o.getTxstatus().equals("1")) {
					// docsubmission
					if (UserUtil.hasRole("COMM_MEMBER")) {
						this.saveAsDraft = true;
						this.submitRequest = true;
						this.cancelRequest = true;
					}
				}
				if (o.getTxstatus().equals("2")) {
					// approval
					this.readOnly = true;
					if (UserUtil.hasRole("HEAD_EDUC_COMM")) {
						this.approveRequest = true;
						this.declineRequest = true;
					}
				}
				if (o.getTxstatus().equals("3")) {
					// approved
					this.readOnly = true;
				}
				if (o.getTxstatus().equals("4")) {
					// declined
					this.readOnly = true;
				}
				if (o.getTxstatus().equals("5")) {
					// cancelled
					this.readOnly = true;
				}
				if(o.getChangecategorytype().equals("2")){
					//name
					this.showName = true;
				}
				if(o.getChangecategorytype().equals("3")){
					//dateofbirth
					this.showDateOfBirth = true;
				}
				if(o.getChangecategorytype().equals("4")){
					//civilstatus
					this.showCivilStatus = true;
				}
				if(o.getChangecategorytype().equals("5")){
					this.showMembershipStatus = true;
				}
				if(o.getChangecategorytype().equals("6")){
					this.showMembershipClass = true;
				}
				if(o.getChangecategorytype().equals("7")){
					this.showBranch = true;
				}
				if(o.getChangecategorytype().equals("8")){
					this.showCompany = true;
				}				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public boolean isSaveAsDraft() {
		return saveAsDraft;
	}

	public void setSaveAsDraft(boolean saveAsDraft) {
		this.saveAsDraft = saveAsDraft;
	}

	public boolean isSubmitRequest() {
		return submitRequest;
	}

	public void setSubmitRequest(boolean submitRequest) {
		this.submitRequest = submitRequest;
	}

	public boolean isCancelRequest() {
		return cancelRequest;
	}

	public void setCancelRequest(boolean cancelRequest) {
		this.cancelRequest = cancelRequest;
	}

	public boolean isApproveRequest() {
		return approveRequest;
	}

	public void setApproveRequest(boolean approveRequest) {
		this.approveRequest = approveRequest;
	}

	public boolean isDeclineRequest() {
		return declineRequest;
	}

	public void setDeclineRequest(boolean declineRequest) {
		this.declineRequest = declineRequest;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public boolean isShowDateOfBirth() {
		return showDateOfBirth;
	}

	public void setShowDateOfBirth(boolean showDateOfBirth) {
		this.showDateOfBirth = showDateOfBirth;
	}

	public boolean isShowCivilStatus() {
		return showCivilStatus;
	}

	public void setShowCivilStatus(boolean showCivilStatus) {
		this.showCivilStatus = showCivilStatus;
	}

	public boolean isShowName() {
		return showName;
	}

	public void setShowName(boolean showName) {
		this.showName = showName;
	}
}
