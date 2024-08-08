package com.etel.ci.forms;

import com.coopdb.data.Tbcireportmain;
import com.coopdb.data.Tbcirequest;
import com.etel.ci.report.CIReportServiceImpl;
import com.etel.defaultusers.forms.DefaultUsers;
import com.etel.utils.UserUtil;

public class CIReportAccessRights extends CIReportServiceImpl {
	private boolean btnforreview = false;
	private boolean btndonereview = false;
	private boolean btnreturnci = false;
	private boolean isreadonly = true;
	private boolean isdisabled = true;
	private boolean btnStartRpt = false;

	public CIReportAccessRights(String rptid) {
		try {
			Tbcireportmain m = getCIReport(rptid);
			boolean supervisor = new DefaultUsers(m.getCompanycode()).getCisupervisor()
					.equals(securityService.getUserName());
			Tbcirequest r = getCIRequest(m.getCirequestid());
			boolean assignedci = r.getAssignedci()
					.equals(securityService.getUserName());
			
			// New or Returned
			if (m.getStatus().equals("0")) {
				if (r.getAssignedci() != null
						&& r.getAssignedci().equals(UserUtil.securityService.getUserName())) {
					this.btnStartRpt = true;
				}
			}
			if ((m.getStatus().equals("1") || m.getStatus().equals("4")) && assignedci) {
				//OnGoing
				this.btnforreview = true;
			}
			if (m.getStatus().equals("2") && supervisor) {
				//For Review
				this.btndonereview = true;
				this.btnreturnci = true;
			}
			if (r.getAssignedci() != null && r.getAssignedci().equals(securityService.getUserName())
					&& ( m.getStatus().equals("1")) || m.getStatus().equals("4") ){
				//if assigned CI and status is ongoing=(0) or returned=(3)
				this.isreadonly  = false;
				this.isdisabled = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isBtnStartRpt() {
		return btnStartRpt;
	}

	public void setBtnStartRpt(boolean btnStartRpt) {
		this.btnStartRpt = btnStartRpt;
	}

	public boolean isBtnforreview() {
		return btnforreview;
	}

	public void setBtnforreview(boolean btnforreview) {
		this.btnforreview = btnforreview;
	}

	public boolean isBtndonereview() {
		return btndonereview;
	}

	public void setBtndonereview(boolean btndonereview) {
		this.btndonereview = btndonereview;
	}

	public boolean isBtnreturnci() {
		return btnreturnci;
	}

	public void setBtnreturnci(boolean btnreturnci) {
		this.btnreturnci = btnreturnci;
	}

	public boolean isIsreadonly() {
		return isreadonly;
	}

	public void setIsreadonly(boolean isreadonly) {
		this.isreadonly = isreadonly;
	}

	public boolean isIsdisabled() {
		return isdisabled;
	}

	public void setIsdisabled(boolean isdisabled) {
		this.isdisabled = isdisabled;
	}
	
	
}
