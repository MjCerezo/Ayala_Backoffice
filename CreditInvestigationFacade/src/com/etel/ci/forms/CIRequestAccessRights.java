package com.etel.ci.forms;

import com.coopdb.data.Tbcirequest;
import com.etel.ci.CreditInvestigationServiceImpl;

public class CIRequestAccessRights extends CreditInvestigationServiceImpl {
	private String status;
	private boolean savebtn = false;
	private boolean submitbtn = false;
	private boolean cancelrequestbtn = false;
	private boolean createreportbtn = false;
	private boolean searchcifbtn = false;
	// ro : readonly
	private boolean rocifno = true;
	private boolean rocifname = true;
	private boolean rocompname = true;
	private boolean roassignedci = true;
	private boolean roremarks = true;
	private boolean formpanel = true;
	private boolean rocitype = true;
	private boolean ropdrn = true;
	private boolean roevr = true;
	private boolean robvr = true;
	private boolean robankcheck = true;
	private boolean rocreditcheck = true;
	private boolean rotradecheck = true;

	public CIRequestAccessRights() {

	}
	/**
	 * CI Request Access rights
	 * **/
	public CIRequestAccessRights(String requestID, String appno) {

		/*
		 * REQUESTSTATUS:
		 * 0 New 
		 * 1 Report Opened 
		 * 2 Report On-going 
		 * 3 Report Submitted 
		 * 4 Report For Review 
		 * 5 Completed 
		 * 6 Report Returned 
		 * 7 Cancelled 
		 * 8 On-hold
		 */

		super();
		Tbcirequest r;
		if (requestID != null) {
			r = getCIRequest(requestID);
//			if (r != null) {
//				boolean isRequestor = securityService.getUserName().equals(r.getRequestedby());
//				boolean isInsideApp = r.getAppno() != null ? true : false;
//				this.status = r.getStatus();
//
//				if (r.getAppno() == null && r.getStatus().equals("1")) {
//					// cancel request can perform by the requestor outside the
//					// application.
//					if (isRequestor) {
//						this.cancelrequestbtn = true;
//					}
//				} else {
//					// only supervisor can cancel request inside application.
//					if (isCISupervisorByCompanycode(r.getCompanycode())
//							&& (r.getStatus().equals("0") || r.getStatus().equals("3"))) {
//						this.cancelrequestbtn = true;
//					}
//				}
//				if (r.getStatus().equals("1")) {
//					this.savebtn = true;
//					this.submitbtn = true;
//					this.searchcifbtn = false;
//					if (isInsideApp) {
//						this.rocompname = true;
//						this.roremarks = false;
//						if (isRequestor) {
//							this.formpanel = false;
//							this.ropdrn = false;
//							this.roevr = false;
//							this.robvr = false;
//							this.robankcheck = false;
//							this.rocreditcheck = false;
//							this.rotradecheck = false;
//						}
//					} else {
//						this.rocifno = true;
//						this.rocompname = false;
//						this.roremarks = false;
//						if (isRequestor) {
//							this.formpanel = false;
//							this.ropdrn = false;
//							this.roevr = false;
//							this.robvr = false;
//							this.robankcheck = false;
//							this.rocreditcheck = false;
//							this.rotradecheck = false;
//						}
//					}
//				} else if (r.getStatus().equals("1")) {
//					if (isCISupervisorByCompanycode(r.getCompanycode())) {
//						this.savebtn = true;
//						this.roassignedci = false;
//					}
//					if (r.getAssignedci() != null) {
//						if (securityService.getUserName().equals(r.getAssignedci())) {
//							this.createreportbtn = true;
//						}
//					}
//				} else if (r.getStatus().equals("2")) {
//					this.rocitype = true;
//				} else if (r.getStatus().equals("3")) {
//					this.cancelrequestbtn = false;
//					this.createreportbtn = false;
//				} else if (r.getStatus().equals("4")) {
//					this.cancelrequestbtn = false;
//					this.createreportbtn = false;
//				}
//				
//			} else {
//				System.out.println("request record is null >>>>");
//				this.status = null;
//				this.savebtn = true;
//
//				if (appno == null) {
//					this.rocifno = false;
//					this.rocompname = false;
//					this.formpanel = false;
//					this.roremarks = false;
//				}
				
//			}
			// New || Report Opened || Report On-going || 
			if (r.getStatus().equals("0") || r.getStatus().equals("1") || r.getStatus().equals("2") || r.getStatus().equals("6")) {
				if (isCISupervisorByCompanycode(r.getCompanycode())) {
					this.submitbtn = true;
					this.cancelrequestbtn = true;
					this.roassignedci = false;
				}
			}
		} else {
			this.status = null;
			this.submitbtn = true;
			if (appno != null) {
				this.rocompname = true;
				this.formpanel = false;
				this.roremarks = false;
				this.rocifno = true;
				this.rocitype = false;

				this.ropdrn = false;
				this.roevr = false;
				this.robvr = false;
				this.robankcheck = false;
				this.rocreditcheck = false;
				this.rotradecheck = false;
				//String c = getAppdetailByAppno(appno).getCompanycode();
				String c = getAppdetailByAppno(appno).getCoopcode();
				if (c != null) {
					if (isCISupervisorByCompanycode(c)) {
						this.roassignedci = false;
					}
				}
			} else {
				// outside application
				this.rocifno = false;
				this.searchcifbtn = true;
				this.rocitype = false;
				this.rocompname = false;
				this.roremarks = false;

				this.ropdrn = false;
				this.roevr = false;
				this.robvr = false;
				this.robankcheck = false;
				this.rocreditcheck = false;
				this.rotradecheck = false;
			}
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isSavebtn() {
		return savebtn;
	}

	public void setSavebtn(boolean savebtn) {
		this.savebtn = savebtn;
	}

	public boolean isSubmitbtn() {
		return submitbtn;
	}

	public void setSubmitbtn(boolean submitbtn) {
		this.submitbtn = submitbtn;
	}

	public boolean isSearchcifbtn() {
		return searchcifbtn;
	}

	public void setSearchcifbtn(boolean searchcifbtn) {
		this.searchcifbtn = searchcifbtn;
	}

	public boolean isRocifno() {
		return rocifno;
	}

	public void setRocifno(boolean rocifno) {
		this.rocifno = rocifno;
	}

	public boolean isRocifname() {
		return rocifname;
	}

	public void setRocifname(boolean rocifname) {
		this.rocifname = rocifname;
	}

	public boolean isRocompname() {
		return rocompname;
	}

	public void setRocompname(boolean rocompname) {
		this.rocompname = rocompname;
	}

	public boolean isRocitype() {
		return rocitype;
	}

	public void setRocitype(boolean rocitype) {
		this.rocitype = rocitype;
	}

	public boolean isCancelrequestbtn() {
		return cancelrequestbtn;
	}

	public void setCancelrequestbtn(boolean cancelrequestbtn) {
		this.cancelrequestbtn = cancelrequestbtn;
	}

	public boolean isCreatereportbtn() {
		return createreportbtn;
	}

	public void setCreatereportbtn(boolean createreportbtn) {
		this.createreportbtn = createreportbtn;
	}

	public boolean isRoassignedci() {
		return roassignedci;
	}

	public void setRoassignedci(boolean roassignedci) {
		this.roassignedci = roassignedci;
	}

	public boolean isRoremarks() {
		return roremarks;
	}

	public void setRoremarks(boolean roremarks) {
		this.roremarks = roremarks;
	}

	public boolean isFormpanel() {
		return formpanel;
	}

	public void setFormpanel(boolean formpanel) {
		this.formpanel = formpanel;
	}

	public boolean isRopdrn() {
		return ropdrn;
	}

	public void setRopdrn(boolean ropdrn) {
		this.ropdrn = ropdrn;
	}

	public boolean isRoevr() {
		return roevr;
	}

	public void setRoevr(boolean roevr) {
		this.roevr = roevr;
	}

	public boolean isRobvr() {
		return robvr;
	}

	public void setRobvr(boolean robvr) {
		this.robvr = robvr;
	}

	public boolean isRobankcheck() {
		return robankcheck;
	}

	public void setRobankcheck(boolean robankcheck) {
		this.robankcheck = robankcheck;
	}

	public boolean isRocreditcheck() {
		return rocreditcheck;
	}

	public void setRocreditcheck(boolean rocreditcheck) {
		this.rocreditcheck = rocreditcheck;
	}

	public boolean isRotradecheck() {
		return rotradecheck;
	}

	public void setRotradecheck(boolean rotradecheck) {
		this.rotradecheck = rotradecheck;
	}

}
