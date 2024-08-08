package com.etel.accessrights.forms;

import java.util.Map;

import com.coopdb.data.Tbresign;
import com.coopdb.data.Tbworkflowprocess;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;

public class ResignationARForm {
	private boolean showoptions = false;
	private boolean showsaveasdraft = false;
	private boolean showsubmit = true;
	private boolean showsave = false;
	private boolean showheader = true;
	private String status;

	private static Map<String, Object> params = HQLUtil.getMap();
	private static DBService dbService = new DBServiceImpl();

	public ResignationARForm(String memberid, String submitoption) {
		try {
			params.put("id", memberid);
			params.put("option", submitoption);
			Tbresign r = (Tbresign) dbService.executeUniqueHQLQuery("FROM Tbresign WHERE membershipid=:id", params);
			params.put("sequenceno", r.getResignstatus());
			Tbworkflowprocess process = (Tbworkflowprocess) dbService.executeUniqueHQLQuery(
					"FROM Tbworkflowprocess WHERE workflowid='2' AND sequenceno=:sequenceno", params);
			Integer fixedSubmitStatus = process.getSubmitoption1();
			String submitcode = process.getSubmitcode();
			Integer appstatus = 1;
			if (submitcode.equals("A")) {
				if (r.getResignstatus().equals("3")) {
					// Initial Approval
					this.showoptions = true;
					appstatus = fixedSubmitStatus;
//					if (submitoption.equals("7")) {
//					}
				}
				if (r.getResignstatus().equals("4")) {
					this.showoptions = true;
					appstatus = fixedSubmitStatus;
//					if (submitoption.equals("7")) {
//					}
				}
				if (r.getResignstatus().equals("5")) {
					this.showoptions = true;
					appstatus = fixedSubmitStatus;
//					if (submitoption.equals("7")) {
//					}
				}
			}
			// Fixed
			else if (submitcode.equals("B")) {
				appstatus = fixedSubmitStatus;
			}

			String appstatusdesc = (String) dbService.executeUniqueSQLQuery(
					"SELECT processname FROM TBWORKFLOWPROCESS WHERE workflowid='2' AND sequenceno=" + appstatus,
					params);

			this.setStatus(appstatusdesc);

			String mstatus = r.getResignstatus();

			if (mstatus.equals("1")) {
				// Encoding
				this.showsaveasdraft = true;
			} else if (mstatus.equals("2")) {
				// Resignation Review
				this.showsave = true;
			} else if (mstatus.equals("3")) {
				// Initial Approval
				this.showoptions = true;
			} else if (mstatus.equals("4")) {
				// Board Approval
				this.showoptions = true;
				this.showheader = false;
			} else if (mstatus.equals("5")) {
				// Approved for Release
			} else if (mstatus.equals("6")) {
				// Release
				this.showoptions = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isShowoptions() {
		return showoptions;
	}

	public void setShowoptions(boolean showoptions) {
		this.showoptions = showoptions;
	}

	public boolean isShowsaveasdraft() {
		return showsaveasdraft;
	}

	public void setShowsaveasdraft(boolean showsaveasdraft) {
		this.showsaveasdraft = showsaveasdraft;
	}

	public boolean isShowsubmit() {
		return showsubmit;
	}

	public void setShowsubmit(boolean showsubmit) {
		this.showsubmit = showsubmit;
	}

	public boolean isShowsave() {
		return showsave;
	}

	public void setShowsave(boolean showsave) {
		this.showsave = showsave;
	}

	public boolean isShowheader() {
		return showheader;
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

}
