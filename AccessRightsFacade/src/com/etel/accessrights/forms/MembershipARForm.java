package com.etel.accessrights.forms;

import java.util.Map;

import com.coopdb.data.Tbemployee;
import com.coopdb.data.Tbmembershipapp;
import com.coopdb.data.Tbworkflowprocess;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

/**
 * Membership access rights form
 */
public class MembershipARForm {
	private boolean showoptions = false;
	private boolean showsaveasdraft = false;
	private boolean showsubmit = true;
	private boolean showsave = false;
	private boolean showheader = true;
	private boolean setReadOnly = false;
	private boolean showreturn = false;
	private boolean readonlyheader = false;
	private String status;
	private String page = "Membership_Application";

	public MembershipARForm(String membershipappid, String submitoption) {
		Map<String, Object> params = HQLUtil.getMap();
		DBService dbService = new DBServiceImpl();
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		params.put("membershipappid", membershipappid);
		if (membershipappid != null) {
			Tbmembershipapp app = (Tbmembershipapp) dbService
					.executeUniqueHQLQuery("FROM Tbmembershipapp WHERE membershipappid=:membershipappid", params);
			// System.out.println(app.getMembershipappstatus());
			if (app != null) {
				params.put("sequenceno", app.getMembershipappstatus());
//				Tbworkflowprocess process = (Tbworkflowprocess) dbService.executeUniqueHQLQuery(
//						"FROM Tbworkflowprocess WHERE workflowid='1' AND sequenceno=:sequenceno", params);
				Tbworkflowprocess process = (Tbworkflowprocess) dbService.execSQLQueryTransformer(
						"SELECT submitoption1, submitcode, processname FROM Tbworkflowprocess WHERE workflowid='1' AND sequenceno=:sequenceno",
						params, Tbworkflowprocess.class, 0);

				Integer fixedSubmitStatus = process.getSubmitoption1();
				String submitcode = process.getSubmitcode();
				@SuppressWarnings("unused")
				Integer appstatus = 1;

				if (submitcode.equals("A")) {
					if (submitoption != null && !submitoption.equals("")) {
						appstatus = Integer.valueOf(submitoption);
					} else {
						appstatus = fixedSubmitStatus;
					}

					if (app.getMembershipappstatus().equals("3")) {
						// Initial Approval
						if (submitoption.equals("7")) {
							appstatus = 4;
						}
						this.setReadOnly = true;
					}
				}
				// Fixed
				else if (submitcode.equals("B")) {
					appstatus = fixedSubmitStatus;
				}
				if (process.getSubmitoption1() != null) {
					params.put("submitoption", process.getSubmitoption1());
					String appstatusdesc = (String) dbService.executeUniqueSQLQuery(
							"SELECT processname FROM TBWORKFLOWPROCESS WHERE workflowid='1' AND sequenceno=:submitoption",
							params);
//				this.setStatus((String) dbService.execSQLQueryTransformer("SELECT processname FROM Tbworkflowprocess WHERE sequenceno=(SELECT submitoption1 FROM Tbworkflowprocess WHERE sequenceno=:sequenceno AND workflowid='1') AND workflowid='1'", params, String.class, 0));
					this.setStatus(appstatusdesc);
				}

				String mstatus = app.getMembershipappstatus();

				if (mstatus.equals("1")) {
					// Encoding
					if (secservice.getUserName().equals(app.getEncodedby())) {
						this.page = "Membership_Application";
						this.showsaveasdraft = true;
						this.showsubmit = true;
						this.readonlyheader = false;
						if (app.getEmployeeid() != null && app.getCompanycode() != null
								&& !app.getCompanycode().equals("OTHERS") && !app.getMembershipclass().equals("2")) {
							params.put("employeeid", app.getEmployeeid());
							params.put("companycode", app.getCompanycode());
							Tbemployee emp = (Tbemployee) dbService.executeUniqueHQLQuery(
									"FROM Tbemployee WHERE employeeid=:employeeid AND companycode=:companycode",
									params);
							if (emp != null) {
								this.setReadOnly = true;
							} else {
								this.setReadOnly = false;

							}
						}
					} else {
						this.page = "Membership_Application_Inquiry";
						this.setReadOnly = true;
						this.showsubmit = false;
						this.readonlyheader = true;
					}
				} else if (mstatus.equals("2")) {
					// Application Review
					this.page = "Membership_Application";
					this.showreturn = true;
					this.setReadOnly = true;
					this.readonlyheader = true;
					if (secservice.getUserName().equals(app.getAssignedto())) {
						this.showsave = true;
						this.showsaveasdraft = true;
					} else {
						this.showsubmit = false;
					}
				} else if (mstatus.equals("3")) {
					// Initial Approval
//					this.showoptions = true;
					this.page = "Membership_Application_Inquiry";
					this.setReadOnly = true;
					this.readonlyheader = true;
					this.showreturn = true;
				} else if (mstatus.equals("4")) {
					// Payment
					this.showheader = false;
				} else if (mstatus.equals("5")) {
					this.page = "Membership_Application_Inquiry";
					// Recommendation
				} else if (mstatus.equals("6")) {
					// Board Approval
					this.page = "Membership_Application_Inquiry";
					this.showoptions = true;
					this.setReadOnly = true;
					this.readonlyheader = true;
				} else if (mstatus.equals("7") || mstatus.equals("8") || mstatus.equals("9")) {
					// Board Approval
					this.page = "Membership_Application_Inquiry";
					this.setReadOnly = true;
					this.readonlyheader = true;
					this.showsubmit = false;
					this.showoptions = false;
				}

			}
		}
	}

	public boolean isShowoptions() {
		return showoptions;
	}

	public boolean isShowsaveasdraft() {
		return showsaveasdraft;
	}

	public boolean isShowsubmit() {
		return showsubmit;
	}

	public boolean isShowsave() {
		return showsave;
	}

	public boolean isShowheader() {
		return showheader;
	}

	public void setShowoptions(boolean showoptions) {
		this.showoptions = showoptions;
	}

	public void setShowsaveasdraft(boolean showsaveasdraft) {
		this.showsaveasdraft = showsaveasdraft;
	}

	public void setShowsubmit(boolean showsubmit) {
		this.showsubmit = showsubmit;
	}

	public void setShowsave(boolean showsave) {
		this.showsave = showsave;
	}

	public void setShowheader(boolean showheader) {
		this.showheader = showheader;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isSetReadOnly() {
		return setReadOnly;
	}

	public void setSetReadOnly(boolean setReadOnly) {
		this.setReadOnly = setReadOnly;
	}

	public boolean isShowreturn() {
		return showreturn;
	}

	public void setShowreturn(boolean showreturn) {
		this.showreturn = showreturn;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public boolean isReadonlyheader() {
		return readonlyheader;
	}

	public void setReadonlyheader(boolean readonlyheader) {
		this.readonlyheader = readonlyheader;
	}

}
