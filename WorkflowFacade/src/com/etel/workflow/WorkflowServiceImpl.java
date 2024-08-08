package com.etel.workflow;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.casa.FinTxService;
import com.casa.FinTxServiceImpl;
import com.coopdb.data.Tbaccountinfo;
import com.coopdb.data.Tbapprovalmatrix;
import com.coopdb.data.Tbfintxjrnl;
import com.coopdb.data.Tbloanreleaseinst;
import com.coopdb.data.Tblstapp;
import com.coopdb.data.Tbmember;
import com.coopdb.data.Tbmembershipapp;
import com.coopdb.data.Tbresign;
import com.coopdb.data.Tbworkflowprocess;
import com.etel.approval.ApprovalService;
import com.etel.approval.ApprovalServiceImpl;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.dataentry.FullDataEntryService;
import com.etel.dataentry.FullDataEntryServiceImpl;
import com.etel.dataentryforms.CollectionInstructionsForm;
import com.etel.dataentryforms.LoanFeesForm;
import com.etel.evaluation.EvaluationService;
import com.etel.evaluation.EvaluationServiceImpl;
import com.etel.forms.ReturnForm;
//import com.etel.loancalc.LoanCalculatorService;
//import com.etel.loancalc.LoanCalculatorServiceImpl;
//import com.etel.loanproduct.forms.LoanFeeForm;
import com.etel.utils.ApplicationNoGenerator;
import com.etel.utils.AuditLog;
import com.etel.utils.AuditLogEvents;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;
import com.etel.workflow.forms.SubmitOptionForm;
import com.etel.workflow.forms.WorkflowDashboardForm;
import com.etel.workflow.forms.WorkflowProcessForm;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class WorkflowServiceImpl implements WorkflowService {
	public static SecurityService securityService = (SecurityService) RuntimeAccess.getInstance()
			.getServiceBean("securityService");
	private String username = securityService.getUserName();

	public static boolean isValidEnum(String str) {
		if (str == null)
			return false;
		try {
			ArrayList<String> flow = new ArrayList<String>();
			for (WorkFlow f : WorkFlow.values()) {
				flow.add(f.toString());
			}
			if (flow.contains(str))
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	private DBService dbService = new DBServiceImpl();

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public ReturnForm submitApplication(String flow, String generatedno, String submitoption, String remarks,
			String boardresno, String boardremarks) {
		ReturnForm form = new ReturnForm();
		List<String> flowcheck = new ArrayList<String>();
		String stageparam = "";
		String auditSubmit = "";
		int auditID = 0;
		form.setFlag("failed");
		form.setMessage("Submit Failed!");
		Map<String, Object> params = HQLUtil.getMap();
		params.put("generatedno", generatedno);
		try {
			if (isValidEnum(flow)) {
				switch (WorkFlow.valueOf(flow.toUpperCase())) {
				case MEMBERSHIP:
					Tbmembershipapp app = (Tbmembershipapp) dbService
							.executeUniqueHQLQuery("FROM Tbmembershipapp WHERE membershipappid=:generatedno", params);

					if (app != null) {
						params.put("coopcode", app.getCoopcode());
						params.put("sequenceno", app.getMembershipappstatus());
						Tbworkflowprocess process = (Tbworkflowprocess) dbService.executeUniqueHQLQuery(
								"FROM Tbworkflowprocess WHERE workflowid='1' AND sequenceno=:sequenceno", params);
						if (process != null) {
//							System.out.println("option " + submitoption);
							Integer fixedSubmitStatus = process.getSubmitoption1();
							String submitcode = process.getSubmitcode();
							Integer appstatus = 1;

							Boolean approved = false;
							Boolean deferred = false;
							Boolean declined = false;

							if (submitcode == null) {
								return form;
							} else {
//								System.out.println("submit " + submitcode);
								// Flexible
								if (submitcode.equals("A")) {

									appstatus = Integer.valueOf(submitoption);
									// Initial Approval Stage..
									if (app.getMembershipappstatus().equals("3")) {
										// Initial Approval
										app.setEdcomapprover(UserUtil.securityService.getUserName());
										app.setEdcomapproverremarks(remarks);
										app.setEdcomappstatusdate(new Date());
										if (submitoption.equals("8")) {
											// Declined
											app.setEdcomapprovalstatus("2");
											declined = true;
										} else if (submitoption.equals("9")) {
											// Deferred
											app.setEdcomapprovalstatus("3");
											deferred = true;
										} else {
											// Approved
											params.put("coopcode", app.getCoopcode());
											app.setEdcomapprovalstatus("1");
											app.setMembershipstatus("0");
//											app.setMembershipstatus("0");
//											FullDataEntryService createMember = new FullDataEntryServiceImpl();
//											createMember.saveMembership(app);
//											appstatus = 4;
											appstatus = process.getSubmitoption1();
											approved = true;
											auditID = AuditLogEvents.M_SUBMIT_APPROVAL;
											auditSubmit = " Submitted " + app.getMembershipappid()
													+ "'s Membership Application for Payment.";
										}
									}
									// Board Approval Stage..
									if (app.getMembershipappstatus().equals("6")) {
										if (submitoption.equals("8")) {
											declined = true;
											// Declined
										} else if (submitoption.equals("9")) {
											deferred = true;
											// Deferred
										} else {
											// Approved
											app.setServicestatus("1");
											app.setMembershipstatus("0");
											app.setBoardapprovalstatus("1");
											FullDataEntryService createMember = new FullDataEntryServiceImpl();
											createMember.saveMembership(app);
											params.put("id", app.getMembershipappid());
											app.setBoardapprover(UserUtil.securityService.getUserName());
											app.setBoardappstatusdate(new Date());
											app.setBoardapproverremarks(boardremarks);
											app.setBoardresno(boardresno);
											appstatus = 7;
											auditSubmit = " Board Approver Approved " + app.getMembershipappid()
													+ "'s Membership Application.";
											auditID = AuditLogEvents.M_APPROVED_MEMBER_BOARD;
										}
									}
								}
								// Fixed
								else if (submitcode.equals("B")) {
									appstatus = fixedSubmitStatus;

									if (appstatus.equals(2)) {
										/*
										 * Encoding (1) to Application Review (2)
										 */
										auditSubmit = " Submitted " + app.getMembershipappid()
												+ "'s Membership Application for Application Review.";
										auditID = AuditLogEvents.M_SUBMIT_ENCODING;
									} else if (appstatus.equals(3)) {
										/*
										 * Application Review (2) to Initial approval (3)
										 */
										app.setMembershipstatus("0");
										auditSubmit = " Submitted " + app.getMembershipappid()
												+ " Membership Application for Initial Approval.";
										auditID = AuditLogEvents.M_SUBMIT_REVIEW;
									} else if (appstatus.equals(4)) {
										// Initial approval (3) to Payment (4)
										app.setEdcomapprover(UserUtil.securityService.getUserName());
										app.setEdcomappstatusdate(new Date());
										app.setEdcomapproverremarks(remarks);
										auditSubmit = " Submitted " + app.getMembershipappid()
												+ "'s Membership Application for Payment.";
										auditID = AuditLogEvents.M_SUBMIT_APPROVAL;

									} else if (appstatus.equals(5)) {
										// Payment (4) to Recommendation (5)
										app.setCashier(UserUtil.securityService.getUserName());
										app.setPaymentapprovaldate(new Date());
										stageparam = "payment";
										auditSubmit = " Tagged " + app.getMembershipappid()
												+ "'s Membership Fee payment as \"Paid\".";
										auditID = AuditLogEvents.M_SUBMIT_BILLLOAN_PAYMENT_TRANSACTION_PAYMENT;
									} else if (appstatus.equals(6)) {
										params.put("coopcode", app.getCoopcode());
										app.setEdcomapprovalstatus("1");
										app.setMembershipstatus("0");
//										FullDataEntryService createMember = new FullDataEntryServiceImpl();
//										createMember.saveMembership(app);
//										appstatus = 4;
										appstatus = process.getSubmitoption1();
										// Recommendation (5) to board
										// approval(6)
										app.setRecommendedby(UserUtil.securityService.getUserName());
										app.setRecommendationdate(new Date());
										stageparam = "recommendation";
										auditSubmit = " Submitted " + app.getMembershipappid()
												+ "'s Membership Application for Board Approval.";
										auditID = AuditLogEvents.M_SUBMIT_RECOMMENDATION;
									} else if (appstatus.equals(7) || appstatus.equals(8) || appstatus.equals(9)) {
										// Board
										app.setBoardapprover(UserUtil.securityService.getUserName());
										app.setBoardappstatusdate(new Date());
										app.setBoardapproverremarks(boardremarks);
										app.setBoardresno(boardresno);
										stageparam = "board approval";
										if (appstatus.equals(7)) {
											FullDataEntryService createMember = new FullDataEntryServiceImpl();
											createMember.saveMembership(app);
											auditSubmit = " Board Approver Approved " + app.getMembershipappid()
													+ "'s Membership Application.";
											auditID = AuditLogEvents.M_APPROVED_MEMBER_BOARD;
										}
//										auditSubmit = " Submitted the Membership Application for Initial Approval.";
									}
								}

								String appstatusdesc = (String) dbService.executeUniqueSQLQuery(
										"SELECT processname FROM TBWORKFLOWPROCESS WHERE workflowid='1' AND sequenceno="
												+ appstatus,
										params);
								app.setMembershipappstatus(appstatus.toString());
								app.setStatusdate(new Date());
								if (dbService.saveOrUpdate(app)) {
									form.setFlag("success");
									form.setMessage("Application submitted to: <b><br>" + "\"" + appstatusdesc + "\"");
									if (!deferred && !declined) {
										AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(auditID),
												"User " + UserUtil.securityService.getUserName() + auditSubmit,
												UserUtil.securityService.getUserName(), new Date(),
												AuditLogEvents.getEventModule(auditID));
									}
									if (deferred) {
										AuditLog.addAuditLog(
												AuditLogEvents
														.getAuditLogEvents(AuditLogEvents.M_DEFER_APPLICATION_APPROVAL),
												"User " + UserUtil.securityService.getUserName() + " Deferred "
														+ app.getMembershipappid() + "'s Membership Application.",
												UserUtil.securityService.getUserName(), new Date(), AuditLogEvents
														.getEventModule(AuditLogEvents.M_DEFER_APPLICATION_APPROVAL));
									}
									if (declined) {
										AuditLog.addAuditLog(
												AuditLogEvents.getAuditLogEvents(
														AuditLogEvents.M_DECLINE_APPLICATION_APPROVAL),
												"User " + UserUtil.securityService.getUserName() + " Declined "
														+ app.getMembershipappid() + "'s Membership Application.",
												UserUtil.securityService.getUserName(), new Date(), AuditLogEvents
														.getEventModule(AuditLogEvents.M_DECLINE_APPLICATION_APPROVAL));
									}
									if (app.getMembershipappstatus().equals("3")) {
										if (remarks != null || remarks != "") {
											AuditLog.addAuditLog(
													AuditLogEvents
															.getAuditLogEvents(AuditLogEvents.M_INPUT_REMARKS_REVIEW),
													"User " + UserUtil.securityService.getUserName()
															+ " Inputted Remarks during Application Review for Applicant "
															+ app.getMembershipappid() + ".",
													UserUtil.securityService.getUserName(), new Date(),
													AuditLogEvents.getEventModule(auditID));
										}
									}
									if (approved) {
										AuditLog.addAuditLog(
												AuditLogEvents.getAuditLogEvents(
														AuditLogEvents.M_APPROVED_APPLICATION_APPROVAL),
												"User " + UserUtil.securityService.getUserName() + " Approved "
														+ app.getMembershipappid() + "'s Membership Application.",
												UserUtil.securityService.getUserName(), new Date(),
												AuditLogEvents.getEventModule(
														AuditLogEvents.M_APPROVED_APPLICATION_APPROVAL));
									}
									UpdateStatus(app.getMembershipappstatus(), app.getMembershipappid());
								}
							}
						}
					}
					return form;
				case LOANS:
					Tblstapp lstapp = (Tblstapp) dbService
							.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:generatedno", params);
					if (lstapp != null) {
						params.put("workflowid", lstapp.getApplicationtype());
						params.put("sequenceno", lstapp.getApplicationstatus());
						Tbworkflowprocess process = (Tbworkflowprocess) dbService.executeUniqueHQLQuery(
								"FROM Tbworkflowprocess WHERE id.workflowid = 3 AND id.sequenceno=:sequenceno",
								params);
						if (process != null) {
							Integer fixedSubmitStatus = process.getSubmitoption1();
							String submitcode = process.getSubmitcode();
							Integer appstatus = 0;
							if (submitcode == null) {
								return form;
							} else {
								// Flexible
								if (submitcode.equals("A")) {
									appstatus = Integer.valueOf(submitoption);
								}
								// Fixed
								if (submitcode.equals("B")) {
									appstatus = fixedSubmitStatus;
								}
								String appstatusdesc = (String) dbService.executeUniqueSQLQuery(
										"SELECT processname FROM TBWORKFLOWPROCESS WHERE workflowid= 3 AND sequenceno="
												+ appstatus,
										params);

								// Previous App Status
								lstapp.setLastapplicationstatus(lstapp.getApplicationstatus());

								lstapp.setApplicationstatus(appstatus);
								lstapp.setStatusdatetime(new Date());
								lstapp.setDatelastupdated(new Date());
								lstapp.setLastupdatedby(securityService.getUserName());

								if (appstatus == 6) {// Daniel(02.13.2019)
									lstapp.setPnno(ApplicationNoGenerator.generatePNNo(lstapp.getBranchcode(),
											lstapp.getLoanproduct()));
								}

								// lstapp.setReasonreturn(null);
								// >>CED 3-08-2019
								if (appstatus == 8) {
									params.put("appno", lstapp.getAppno());
									FullDataEntryService fdeSrvc = new FullDataEntryServiceImpl();
									BigDecimal proceeds = dbService.getSQLAmount(
											"SELECT ISNULL((SELECT SUM(amount) FROM Tbloanreleaseinst WHERE applno =:appno AND status = '0'),0)",
											params);
									BigDecimal offset = dbService.getSQLAmount(
											"SELECT ISNULL((SELECT SUM(outstandingbal) FROM Tbloanoffset WHERE applno =:appno),0)",
											params);
//									LoanCalculatorService lc = new LoanCalculatorServiceImpl();
									Tbaccountinfo acctinfo = (Tbaccountinfo) dbService
											.executeUniqueHQLQuery("FROM Tbaccountinfo WHERE applno =:appno", params);
									List<LoanFeesForm> fees = fdeSrvc.getListLoanFeesperapp(lstapp.getAppno());
									BigDecimal onUS = BigDecimal.ZERO;
									BigDecimal proceed = BigDecimal.ZERO;
									BigDecimal paid = BigDecimal.ZERO;
									BigDecimal unpaid = BigDecimal.ZERO;
									BigDecimal total = BigDecimal.ZERO;
									CollectionInstructionsForm collect = new CollectionInstructionsForm();
									if (fees != null && !fees.isEmpty()) {
										for (LoanFeesForm fee : fees) {
											onUS = onUS.add(fee.getPayableto() != null && fee.getPayableto()
													.equals(UserUtil.getUserByUsername(securityService.getUserName())
															.getCoopcode()) ? fee.getAmount() : BigDecimal.ZERO);
											proceed = proceed.add(fee.getDisposition() != null
													&& fee.getDisposition().equals("Deducted from Loan Proceeds")
															? fee.getAmount()
															: BigDecimal.ZERO);
											paid = paid.add(fee.getPaid() != null && fee.getPaid().equals("Yes")
													? fee.getAmount()
													: BigDecimal.ZERO);
											unpaid = unpaid.add(fee.getPaid() != null && fee.getPaid().equals("No")
													? fee.getAmount()
													: BigDecimal.ZERO);
											total = total.add(fee.getAmount());
										}
										collect.setFeesandcharges(total);
										collect.setPayabletobank(onUS);
										collect.setPaidfees(paid);
										collect.setUnpaidfees(unpaid);
										collect.setFeesdeduct(proceed);
										collect.setLoanoffset(offset);
										collect.setAppno(lstapp.getAppno());
										fdeSrvc.updateAccountInfo(collect, "payout");
										System.out.print("MAR total " + total);
										System.out.print("MAR onUS " + onUS);
										System.out.print("MAR paid " + paid);
										System.out.print("MAR unpaid " + unpaid);
										System.out.print("MAR proceed " + proceed);
										System.out.print("MAR offset " + offset);
										
									}
									if (acctinfo != null && proceeds.compareTo(acctinfo.getNetprcds()) == 0) {
//										acctinfo = (Tbaccountinfo) dbService.executeUniqueHQLQuery("FROM Tbaccountinfo WHERE applno =:appno", params);
										acctinfo.setTxstat("9");
										List<Tbloanreleaseinst> inst = (List<Tbloanreleaseinst>) dbService
												.executeListHQLQuery(
														"FROM Tbloanreleaseinst WHERE applno =:appno and checkbank = '000000000'",
														params);
										if (!inst.isEmpty()) {
											for (Tbloanreleaseinst row : inst) {
												FinTxService finservice = new FinTxServiceImpl();
												Tbfintxjrnl jrnl = new Tbfintxjrnl();
												jrnl.setTxcode("112013");
												jrnl.setTxvaldt(new Date());
												jrnl.setTxdate(new Date());
												jrnl.setAccountno(row.getCreditacctno());
												jrnl.setTxamount(row.getAmount());
												jrnl.setReasoncode("101");
												jrnl.setCurrency("PHP");
												jrnl.setRemarks("Loan Release Instruction");
												jrnl.setTxby(securityService.getUserName());
												finservice.cashDepWithDrCrMemo(jrnl, null);
											}
										}
										if (dbService.saveOrUpdate(acctinfo)) {
											if (dbService.saveOrUpdate(lstapp)) {
												AuditLog.addAuditLog(
														AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID(
																"BOOK LOAN APPLICATION",
																AuditLogEvents.LOAN_APPLICATION_RELEASING_BOOKING)),
														"User " + username + " Booked " + acctinfo.getApplno()
																+ "'s Loan Application.",
														username, new Date(),
														AuditLogEvents.LOAN_APPLICATION_RELEASING_BOOKING);
												form.setFlag("success");
												form.setMessage("Application submitted to: <b><br>" + "\""
														+ appstatusdesc + "\"");
											}
										} else {
											form.setMessage("Error.");
											form.setFlag("failed");
										}
									} else {
										form.setMessage("Loan release amount is not equal to net proceeds!");
										form.setFlag("failed");
									}
								} else if (dbService.saveOrUpdate(lstapp)) {// <<CED 3-08-2019
									form.setFlag("success");
									form.setMessage("Application submitted to: <b><br>" + "\"" + appstatusdesc + "\"");

									// Create Eval Report Routine (Kevin
									// 01-30-2018)
//									if (appstatus == 3) {
//										AuditLog.addAuditLog(
//												AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID(
//														"SUBMIT TO EVALUATION",
//														AuditLogEvents.LOAN_APPLICATION_GENERATE_INSTRUCTION_SHEET)),
//												"User " + username + " Submitted " + lstapp.getAppno()
//														+ "'s Application to Evaluation.",
//												username, new Date(),
//												AuditLogEvents.LOAN_APPLICATION_GENERATE_INSTRUCTION_SHEET);
//										EvaluationService evalsrvc = new EvaluationServiceImpl();
//										evalsrvc.createEvalReport(lstapp.getAppno());
//									}
//									if (appstatus == 4) {
//										params.put("txtype", "LO");
//										params.put("product", lstapp.getLoanproduct());
//										params.put("memberid", lstapp.getCifno());
//										params.put("appno", lstapp.getAppno());
//										ApprovalService approvalSrvc = new ApprovalServiceImpl();
//										EvaluationService evalsrvc = new EvaluationServiceImpl();
//										Tbapprovalmatrix mtx = (Tbapprovalmatrix) dbService.executeUniqueHQLQuery(
//												"FROM Tbapprovalmatrix WHERE transactiontype=:txtype AND loanproduct=:product",
//												params);
//
//										boolean isDOSRI = dbService.executeUniqueSQLQuery(
//												"SELECT dosristatus FROM Tbmemberdosri WHERE membershipid=:memberid",
//												params).equals("DOSRI");
//										int evalreportid = evalsrvc.getLatestEvalReportIdByAppno(lstapp.getAppno());
//										Tbaccountinfo acctInfo = (Tbaccountinfo) dbService.executeUniqueHQLQuery(
//												"FROM Tbaccountinfo WHERE applno=:appno", params);
//										BigDecimal loanamount = acctInfo != null
//												? acctInfo.getFaceamt() != null ? acctInfo.getFaceamt()
//														: BigDecimal.ZERO
//												: BigDecimal.ZERO;
//										approvalSrvc.generateLoanApprovalDetails(lstapp.getAppno(), evalreportid,
//												loanamount, "LO", lstapp.getLoanproduct(), isDOSRI ? 2 : 1,
//												isDOSRI ? mtx.getLevel2approver() : mtx.getLevel1approver());
//									}
									// if(appstatus == 6){
									// System.out.println("");
									// System.out.println(lstapp.getAppno()+" is
									// on documentation now..");
									// }
									// if(appstatus == 7) {
									//
									// }
//									if (appstatus == 6) {
//										AuditLog.addAuditLog(
//												AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID(
//														"SUBMIT APPLICATION FOR DOCUMENTATION",
//														AuditLogEvents.LOAN_APPLICATION_CLIENTACCEPTANCE)),
//												"User " + username + " Submitted " + lstapp.getAppno()
//														+ "'s Application for Documentation.",
//												username, new Date(), AuditLogEvents.LOAN_APPLICATION_CLIENTACCEPTANCE);
//										params.put("appno", lstapp.getAppno());
//										params.put("pnno", lstapp.getPnno());
//										if (dbService.executeUpdate(
//												"UPDATE Tbaccountinfo SET pnno=:pnno WHERE applno =:appno",
//												params) == 0) {
//											form.setMessage("<b>PN</b> number not updated.");
//											form.setFlag("failed");
//										}
//									}
									// >>CED 3-08-2019 REMOVED
//									if (appstatus == 8) {
//										params.put("appno", lstapp.getAppno());
//										List<Tbloanreleaseinst> inst = (List<Tbloanreleaseinst>) dbService
//												.executeListHQLQuery(
//														"FROM Tbloanreleaseinst WHERE applno =:appno and checkbank = '000000000'",
//														params);
//										if (!inst.isEmpty()) {
//											for (Tbloanreleaseinst row : inst) {
//												FinTxService finservice = new FinTxServiceImpl();
//												Tbfintxjrnl jrnl = new Tbfintxjrnl();
//												jrnl.setTxcode("112013");
//												jrnl.setTxvaldt(new Date());
//												jrnl.setTxdate(new Date());
//												jrnl.setAccountno(row.getCreditacctno());
//												jrnl.setTxamount(row.getAmount());
//												jrnl.setReasoncode("101");
//												jrnl.setCurrency("PHP");
//												jrnl.setRemarks("Loan Release Instruction");
//												jrnl.setTxby(securityService.getUserName());
//												finservice.cashDepWithDrCrMemo(jrnl, null);
//											}
//										}
//									} // << CED 3-08-2019
									//
									// Email Submit Application (Kevin
									// 10.12.2018)
									// params.put("username",
									// UserUtil.securityService.getUserName());
									// dbService.execSQLQueryTransformer("EXEC
									// sp_InsertEmailSMTP @appno=:appno,
									// @username=:username, @emailcode = 'EM6',
									// @body =NULL", params, null, 0);
								}
							}
						}
					}
					return form;
				case GENERALLEDGER:
					return form;
				case DEPOSITS:
					return form;
				default:
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	/**
	 * @author Daniel
	 */
	public ReturnForm submitResignation(String flow, String memberid, String submitoption, String boardremarks,
			String boardno) {
		ReturnForm flags = new ReturnForm();
		flags.setFlag("failed");
		flags.setMessage("Filing for resignation failed");
		try {
			Map<String, Object> params = HQLUtil.getMap();
			params.put("memberid", memberid);
			if (flow.equals("RESIGNATION")) {
				Tbresign filing = (Tbresign) dbService
						.executeUniqueHQLQuery("FROM Tbresign WHERE membershipId=:memberid", params);
				if (filing != null) {
					params.put("sequenceno", filing.getResignstatus());
					Tbworkflowprocess process = (Tbworkflowprocess) dbService.executeUniqueHQLQuery(
							"FROM Tbworkflowprocess WHERE workflowid='2' AND sequenceno=:sequenceno", params);
					if (process != null) {
						String status = filing.getResignstatus();
						if (process.getSubmitcode().equals("A")) { // Flexible
							if (filing.getResignstatus().equals("3")) { // Initial
																		// Approval
																		// to
																		// Board
																		// Approval
								if (submitoption.equals("7")) { // Approved
									filing.setApprovedby(UserUtil.securityService.getUserName());
									filing.setApprovaldate(new Date());
									status = process.getSubmitoption1().toString();
									// filing.setResignstatus(status);
								}
								if (submitoption.equals("8")) { // Declined

								}
								if (submitoption.equals("9")) { // Deferred

								}
							}
							if (filing.getResignstatus().equals("4")) { // Board
																		// Approval
																		// to
																		// Approve
																		// for
																		// Release
								if (submitoption.equals("7")) { // Approved
									filing.setBoardapprovedby(UserUtil.securityService.getUserName());
									filing.setBoardapprovaldate(new Date());
									filing.setBoardremarks(boardremarks);
									filing.setBoardresno(boardno);
									status = process.getSubmitoption1().toString();
									params.put("member", filing.getMembershipid());
									Tbmember mem = (Tbmember) dbService
											.executeUniqueHQLQuery("FROM Tbmember WHERE membershipid=:member", params);
									if (mem != null) {
										mem.setMembershipstatus("3");// RESIGNED
										dbService.saveOrUpdate(mem);
									}
									// filing.setResignstatus(status);
								}
								if (submitoption.equals("8")) { // Declined

								}
								if (submitoption.equals("9")) { // Deferred

								}

							}
							if (filing.getResignstatus().equals("5")) {
								if (submitoption.equals("7")) { // Approved
									// filing.setResignstatus(status);
									filing.setApprovedby(UserUtil.securityService.getUserName());
									filing.setApprovaldate(new Date());
									status = process.getSubmitoption1().toString();
								}
								if (submitoption.equals("8")) { // Declined

								}
								if (submitoption.equals("9")) { // Deferred

								}

							}
							filing.setResignstatus(status);
							String appstatusdesc = (String) dbService.executeUniqueSQLQuery(
									"SELECT processname FROM TBWORKFLOWPROCESS WHERE workflowid='2' AND sequenceno="
											+ status,
									params);
							if (dbService.saveOrUpdate(filing)) {
								flags.setFlag("success");
								flags.setMessage("Resignation submitted to: <b><br>" + "\"" + appstatusdesc + "\"");
							}
						} else if (process.getSubmitcode().equals("B")) { // Fixed
							status = process.getSubmitoption1().toString();
							filing.setResignstatus(status); // current status to
															// next status..
							String appstatusdesc = (String) dbService.executeUniqueSQLQuery(
									"SELECT processname FROM TBWORKFLOWPROCESS WHERE workflowid='2' AND sequenceno="
											+ status,
									params);
							if (dbService.saveOrUpdate(filing)) {
								flags.setFlag("success");
								flags.setMessage("Resignation submitted to: <b><br>" + "\"" + appstatusdesc + "\"");
							}
						} else {
							flags.setMessage("Problem with Submitcode!");
							return flags;
						}
					} else {
						flags.setMessage("Problem with Workflow Process!");
						return flags;
					}
				} else {
					flags.setMessage("No record found!");
					return flags;
				}
				return flags;
			} else {
				flags.setMessage("Problem with workflow. Not on resignation process!");
				return flags;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flags;
	}

	/**
	 * Validate InstructionForm (Stored Procedure)
	 * 
	 * @author Kevin (10.11.2018)
	 * @return true otherwise false
	 */
	@Override
	public Boolean validateInstructionForm(String appno) {
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (appno != null) {
				params.put("appno", appno);
				String returnflag = (String) dbService
						.execSQLQueryTransformer("EXEC sp_ValidateInstructionForm @appno=:appno", params, null, 0);
				if (returnflag != null && returnflag.equals("success")) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SubmitOptionForm getSubmitOption(String appno) {
		SubmitOptionForm form = new SubmitOptionForm();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("appno", appno);
		try {
			/*
			 * SUBMIT/RETURN CODE - (A = Flexible, B = Fixed, N = Not Applicable)
			 */
			Tblstapp lstapp = (Tblstapp) dbService.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", params);
			if (lstapp != null) {
				params.put("workflowid", lstapp.getApplicationtype());
				params.put("sequenceno", lstapp.getApplicationstatus());
				Tbworkflowprocess process = (Tbworkflowprocess) dbService.executeUniqueHQLQuery(
						"FROM Tbworkflowprocess WHERE id.workflowid=:workflowid AND id.sequenceno=:sequenceno", params);
				if (process != null) {
					Integer workflowid = lstapp.getApplicationtype();
					 Integer appstatus = lstapp.getApplicationstatus();

					/***************** Submit *****************/
					String sQuery = "SELECT a.processname, a.sequenceno FROM TBWORKFLOWPROCESS a WHERE a.workflowid="
							+ workflowid + " AND a.sequenceno IN (" + process.getSubmitoption1() + ","
							+ process.getSubmitoption2() + "," + process.getSubmitoption3() + ") AND isvisibleindb='1'";

					String submitcode = process.getSubmitcode();
					if (submitcode != null && submitcode.equals("A")) {
						List<WorkflowProcessForm> submitOptionDataSet = (List<WorkflowProcessForm>) dbService
								.execSQLQueryTransformer(sQuery, null, WorkflowProcessForm.class, 1);
						form.setSubmitOptionDataSet(submitOptionDataSet);
						form.setSlcSubmitOption(true);
					}
					if (submitcode != null && !submitcode.equals("N")) {
						form.setBtnSubmit(true);
						// Application Status Name
						String appstatusdesc = (String) dbService
								.executeUniqueSQLQuery("SELECT processname FROM TBWORKFLOWPROCESS WHERE workflowid="
										+ workflowid + " AND sequenceno=" + process.getSubmitoption1(), null);
						form.setSubmitAppStatusDesc(appstatusdesc);
					}

					/***************** Return *****************/
					String rQuery = "SELECT a.processname, a.sequenceno FROM TBWORKFLOWPROCESS a WHERE a.workflowid="
							+ workflowid + " AND a.sequenceno < "+appstatus+ " AND isvisibleindb='1'";

					String returncode = (String) dbService
							.executeUniqueSQLQuery("SELECT returncode FROM TBWORKFLOWPROCESS WHERE workflowid="
									+ workflowid + " AND sequenceno=" + appstatus, params);
					if (returncode != null && returncode.equals("A")) {
						List<WorkflowProcessForm> returnOptionDataSet = (List<WorkflowProcessForm>) dbService
								.execSQLQueryTransformer(rQuery, null, WorkflowProcessForm.class, 1);
						form.setReturnOptionDataSet(returnOptionDataSet);
						form.setSlcReturnOption(true);
					}
					if (returncode != null && !returncode.equals("N")) {
						form.setBtnReturn(true);
						// Application Status Name
						String appstatusdesc = (String) dbService
								.executeUniqueSQLQuery("SELECT processname FROM TBWORKFLOWPROCESS WHERE workflowid="
										+ workflowid + " AND sequenceno=" + process.getReturnoption(), null);
						form.setReturnAppStatusDesc(appstatusdesc);
					}
					/***************** Other flag *****************/
					form.setIsbookprocess(process.getIsbookprocess());
					form.setIscancelprocess(process.getIscancelprocess());
					form.setIsrejectprocess(process.getIsrejectprocess());

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public ReturnForm returnApplication(String appno, String returnoption) {
		ReturnForm form = new ReturnForm();
		form.setFlag("failed");
		form.setMessage("Submit Failed !");
		Map<String, Object> params = HQLUtil.getMap();
		params.put("appno", appno);
		try {
			Tblstapp lstapp = (Tblstapp) dbService.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", params);
			if (lstapp != null) {
				params.put("workflowid", lstapp.getApplicationtype());
				params.put("sequenceno", lstapp.getApplicationstatus());
				Tbworkflowprocess process = (Tbworkflowprocess) dbService.executeUniqueHQLQuery(
						"FROM Tbworkflowprocess WHERE id.workflowid=:workflowid AND id.sequenceno=:sequenceno", params);
				if (process != null) {
					Integer fixedReturnStatus = process.getReturnoption();
					String returncode = process.getReturncode();
					Integer appstatus = 0;
					if (returncode == null) {
						return form;
					} else {
						// Flexible
						if (returncode.equals("A")) {
							appstatus = Integer.valueOf(returnoption);
						}
						// Fixed
						if (returncode.equals("B")) {
							appstatus = fixedReturnStatus;
						}
						String appstatusdesc = (String) dbService.executeUniqueSQLQuery(
								"SELECT processname FROM TBWORKFLOWPROCESS WHERE workflowid=:workflowid AND sequenceno="
										+ appstatus,
								params);

//						// Investigation & Eval Return flag
//						if (appstatus <= 2) {
//							lstapp.setIsappraisalcompleted(false);
//							lstapp.setIsbicompleted(false);
//							lstapp.setIscicompleted(false);
//							lstapp.setCreateevalreportflag(1);
//						}
						//For Eval & Approval
						if(appstatus < 4) {
							lstapp.setIsrejected(null);
						}
						
						// Previous App Status
						lstapp.setLastapplicationstatus(lstapp.getApplicationstatus());

						lstapp.setApplicationstatus(appstatus);
						lstapp.setStatusdatetime(new Date());
						lstapp.setDatelastupdated(new Date());
						lstapp.setLastupdatedby(UserUtil.securityService.getUserName());
						if (dbService.saveOrUpdate(lstapp)) {
							form.setFlag("success");
							form.setMessage("Application returned to: <b><br>" + "\"" + appstatusdesc + "\"");

//							// Email Submit Application (Kevin 10.12.2018)
//							params.put("username", UserUtil.securityService.getUserName());
//							dbService.execSQLQueryTransformer(
//									"EXEC sp_InsertEmailSMTP @appno=:appno, @username=:username, @emailcode = 'EM6', @body=NULL",
//									params, null, 0);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	/**
	 * Update Application Status
	 * 
	 * @author Kevin (09.01.2018)
	 * @return String = "success" otherwise "failed"
	 */
	@Override
	public String updateAppStatus(String appno, Integer applicationstatus) {
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (appno != null && applicationstatus != null) {
				params.put("appno", appno);
				Tblstapp lstapp = (Tblstapp) dbService.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno",
						params);
				if (lstapp != null) {
					// Previous App Status
					lstapp.setLastapplicationstatus(lstapp.getApplicationstatus());

					lstapp.setApplicationstatus(applicationstatus);
					lstapp.setStatusdatetime(new Date());
					lstapp.setDatelastupdated(new Date());
					lstapp.setLastupdatedby(UserUtil.securityService.getUserName());
					if (dbService.saveOrUpdate(lstapp)) {
						flag = "success";

//						// Email Submit Application (Kevin 10.12.2018)
//						params.put("username", UserUtil.securityService.getUserName());
//						dbService.execSQLQueryTransformer(
//								"EXEC sp_InsertEmailSMTP @appno=:appno, @username=:username, @emailcode = 'EM6', @body =NULL",
//								params, null, 0);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static void UpdateStatus(String appstatus, String membershipappid) {
		DBService dbService = new DBServiceImpl();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> params = HQLUtil.getMap();
		params.put("membershipappid", membershipappid);
		if (appstatus.equals("4")) {
			/* Payment */
			Integer payment = (Integer) dbService.executeUpdate(
					"UPDATE Tbmember SET edcomapprovalstatus=(SELECT edcomapprovalstatus FROM Tbmembershipapp WHERE membershipappid=:membershipappid), companycode=(SELECT companycode FROM Tbmembershipapp WHERE membershipappid=:membershipappid), membershipstatusdate='"
							+ formatter.format(new Date()) + "' WHERE membershipappid=:membershipappid",
					params);
			System.out.println("Payment update : " + payment);
		}
		if (appstatus.equals("5")) {
			/* Recommendation */
			Integer rec = (Integer) dbService.executeUpdate(
					"UPDATE Tbmember SET paymentapprovaldate=(SELECT paymentapprovaldate FROM Tbmembershipapp WHERE membershipappid=:membershipappid), companycode=(SELECT companycode FROM Tbmembershipapp WHERE membershipappid=:membershipappid), cashier=(SELECT cashier FROM Tbmembershipapp WHERE membershipappid=:membershipappid), membershipstatusdate='"
							+ formatter.format(new Date()) + "' WHERE membershipappid=:membershipappid",
					params);
			System.out.println("Recommendation update : " + rec);
		}
		if (appstatus.equals("6")) {
			/* Board Approval */
			Integer board = (Integer) dbService.executeUpdate(
					"UPDATE Tbmember SET recommendedby=(SELECT recommendedby FROM Tbmembershipapp WHERE membershipappid=:membershipappid), companycode=(SELECT companycode FROM Tbmembershipapp WHERE membershipappid=:membershipappid), remarks=(SELECT remarks FROM Tbmembershipapp WHERE membershipappid=:membershipappid), recommendationdate=(SELECT recommendationdate FROM Tbmembershipapp WHERE membershipappid=:membershipappid), membershipstatusdate='"
							+ formatter.format(new Date()) + "' WHERE membershipappid=:membershipappid",
					params);
			System.out.println("Board Approval update : " + board);
		}
		if (appstatus.equals("7")) {
			/* Approved */
			Integer approved = (Integer) dbService.executeUpdate(
					"UPDATE Tbmember SET boardapprover=(SELECT boardapprover FROM Tbmembershipapp WHERE membershipappid=:membershipappid), boardappstatusdate=(SELECT boardappstatusdate FROM Tbmembershipapp WHERE membershipappid=:membershipappid), boardresno=(SELECT boardresno FROM Tbmembershipapp WHERE membershipappid=:membershipappid), companycode=(SELECT companycode FROM Tbmembershipapp WHERE membershipappid=:membershipappid), boardapproverremarks=(SELECT boardapproverremarks FROM Tbmembershipapp WHERE membershipappid=:membershipappid), membershipstatusdate='"
							+ formatter.format(new Date())
							+ "', boardapprovalstatus=(SELECT boardapprovalstatus FROM Tbmembershipapp WHERE membershipappid=:membershipappid),servicestatus=(SELECT servicestatus FROM Tbmembershipapp WHERE membershipappid=:membershipappid), membershipstatus=(SELECT membershipstatus FROM Tbmembershipapp WHERE membershipappid=:membershipappid) WHERE membershipappid=:membershipappid",
					params);
			System.out.println("Approved update : " + approved);
		}
	}

	@Override
	public ReturnForm submitInvestigation(String appno, String investigationtype) {
		ReturnForm form = new ReturnForm();
		form.setFlag("failed");
		Map<String, Object> params = HQLUtil.getMap();
		int res = 0;
		try {
			if (appno != null) {
				params.put("appno", appno);
				Tblstapp app = (Tblstapp) dbService.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", params);
				// BI
				if (investigationtype != null && investigationtype.equals("BI")) {
					Integer bicount = (Integer) dbService.executeUniqueSQLQuery(
							"SELECT COUNT(*) FROM Tbinvestigationinst WHERE appno=:appno AND investigationtype='BI' AND status IN ('0','1','2')",
							params);
					if (bicount == 0) {
						res = dbService.executeUpdate("UPDATE Tblstapp SET isbicompleted='true' WHERE appno=:appno",
								params);
						if (res > 0) {
							// Done BI

							if (app.getIscicompleted() != null && app.getIscicompleted()) {

								// Check if with Collateral Appraisal
								Integer cacount = (Integer) dbService.executeUniqueSQLQuery(
										"SELECT COUNT(*) FROM Tbcolinvestigationinst WHERE appno=:appno ", params);
								if (cacount > 0) {
									if (app.getIsappraisalcompleted() != null && app.getIsappraisalcompleted()) {
										form.setFlag("completed");
										form.setMessage(
												submitApplication("LOANS", appno, null, null, null, null).getMessage());
									} else {
										form.setFlag("success");
										form.setMessage("Bureau Investigation Completed !");
									}
								} else {
									// If None and
									// Done CI - To Evaluation
									form.setFlag("completed");
									form.setMessage(
											submitApplication("LOANS", appno, null, null, null, null).getMessage());
								}
							} else {
								form.setFlag("success");
								form.setMessage("Bureau Investigation Completed !");
							}
							AuditLog.addAuditLog(
									AuditLogEvents.getAuditLogEvents(
											AuditLogEvents.getEventID("COMPLETE BUREAU INVESTIGATION",
													AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL)),
									"User " + username + " Completed Bureau Investigation Report.", username,
									new Date(), AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL);
						}
					} else {
						res = dbService.executeUpdate("UPDATE Tblstapp SET isbicompleted='false' WHERE appno=:appno",
								params);
						if (res > 0) {
							form.setFlag("incomplete");
							form.setMessage("Please Complete Bureau Investigation Stage !");
						}
					}
				}

				// CI
				if (investigationtype != null && investigationtype.equals("CI")) {
					Integer cicount = (Integer) dbService.executeUniqueSQLQuery(
							"SELECT COUNT(*) FROM Tbinvestigationinst WHERE appno=:appno AND investigationtype='CI' AND status IN ('0','1','2')",
							params);
					if (cicount == 0) {
						res = dbService.executeUpdate("UPDATE Tblstapp SET iscicompleted='true' WHERE appno=:appno",
								params);
						if (res > 0) {
							if (app.getIsbicompleted() != null && app.getIsbicompleted()) {
								Integer cacount = (Integer) dbService.executeUniqueSQLQuery(
										"SELECT COUNT(*) FROM Tbcolinvestigationinst WHERE appno=:appno ", params);
								if (cacount > 0) {
									if (app.getIsappraisalcompleted() != null && app.getIsappraisalcompleted()) {
										form.setFlag("completed");
										form.setMessage(
												submitApplication("LOANS", appno, null, null, null, null).getMessage());
									} else {
										form.setFlag("success");
										form.setMessage("Credit Investigation Completed !");
									}
								} else {
									form.setFlag("completed");
									form.setMessage(
											submitApplication("LOANS", appno, null, null, null, null).getMessage());
								}
							} else {
								form.setFlag("success");
								form.setMessage("Credit Investigation Completed !");
							}
							AuditLog.addAuditLog(
									AuditLogEvents.getAuditLogEvents(
											AuditLogEvents.getEventID("COMPLETE CREDIT INVESTIGATION",
													AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL)),
									"User " + username + " Completed Credit Investigation.", username, new Date(),
									AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL);
						}
					} else {
						res = dbService.executeUpdate("UPDATE Tblstapp SET iscicompleted='false' WHERE appno=:appno",
								params);
						if (res > 0) {
							form.setFlag("incomplete");
							form.setMessage("Please Complete Credit Investigation Stage !");
						}
					}
				}
				// CA 8.27.18 JAY
				if (investigationtype != null && investigationtype.equals("CA")) {
					// System.out.println("investigationtype: "
					// +investigationtype);
					Integer cacount = (Integer) dbService.executeUniqueSQLQuery(
							"SELECT COUNT(*) FROM Tbcolinvestigationinst WHERE appno=:appno AND investigationtype='CA' AND status IN ('0','1','2')",
							params);
					// System.out.println("cacount: " +cacount);
					if (cacount == 0) {
						res = dbService.executeUpdate(
								"UPDATE Tblstapp SET isappraisalcompleted='true' WHERE appno=:appno", params);
						if (res > 0) {
							// Done Appraisal
							if ((app.getIsbicompleted() != null && app.getIsbicompleted())
									&& (app.getIscicompleted() != null && app.getIscicompleted())) {
								form.setFlag("completed");
								form.setMessage(submitApplication("LOANS", appno, null, null, null, null).getMessage());
							} else if ((app.getIsbicompleted() == null || app.getIsbicompleted() == false)
									|| (app.getIscicompleted() == null || app.getIscicompleted() == false)) {
								form.setFlag("success");
								form.setMessage("Collateral Appraisal Completed !");
							} else {
								form.setFlag("success");
								form.setMessage("Collateral Appraisal Completed !");
							}
						}
					} else {
						res = dbService.executeUpdate(
								"UPDATE Tblstapp SET isappraisalcompleted='false' WHERE appno=:appno", params);
						if (res > 0) {
							form.setFlag("incomplete");
							form.setMessage("Please Complete Collateral Appraisal Stage !");
						}
					}
				}

				// 12.19.18 wel
				String a = appno.substring(0, 2);
				if (a.equals("LI")) {
					app = (Tblstapp) dbService.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", params);
					Integer collateral = (Integer) dbService.executeUniqueSQLQuery(
							"SELECT COUNT(*) FROM Tbcolinvestigationinst WHERE appno=:appno", params);
					if (collateral != null && collateral > 0) {
						if ((app.getIsbicompleted() != null && app.getIsbicompleted())
								&& (app.getIscicompleted() != null && app.getIscicompleted())
								&& (app.getIsappraisalcompleted() != null && app.getIsappraisalcompleted())) {
							form.setMessage("Submitted to Evaluation Stage!");
						}
					} else {
						// no collateral
						if ((app.getIsbicompleted() != null && app.getIsbicompleted())
								&& (app.getIscicompleted() != null && app.getIscicompleted())) {
							form.setMessage("Submitted to Evaluation Stage!");
						}
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		form.setFlag("success");
		form.setMessage("Submitted to Evaluation Stage!");
		return form;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkflowDashboardForm> getActiveWorkflowList(String viewby, String company) {
		List<WorkflowDashboardForm> workflowlist = new ArrayList<WorkflowDashboardForm>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("username", UserUtil.securityService.getUserName());
			params.put("viewby", viewby);
			params.put("company", company);
			workflowlist = (List<WorkflowDashboardForm>) dbService.execSQLQueryTransformer(
					"EXEC sp_GetActiveWorkflow @username=:username, @viewby=:viewby, @company=:company", params,
					WorkflowDashboardForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workflowlist;
	}

	@SuppressWarnings("unchecked")
	public List<WorkflowProcessForm> getStatusByApplicationType(Integer workflowid) {
		List<WorkflowProcessForm> form = new ArrayList<WorkflowProcessForm>();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("id", workflowid);
		try {
			form = (List<WorkflowProcessForm>) dbService.execSQLQueryTransformer(
					"SELECT processname, sequenceno FROM Tbworkflowprocess WHERE workflowid=:id AND (isvisibleindb='1' OR sequenceno >=10) ORDER BY sequenceno ASC",
					params, WorkflowProcessForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	/**
	 * Cancel Application
	 * @author Kevin (06.24.2019)
	 * @return String = "success" otherwise "failed"
	 */
	@Override
	public String cancelApplication(String appno, Integer appstatus, Boolean iscancelled,
			String reasonforcancellation) {
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(appno != null && appstatus != null){
				params.put("appno", appno);
				Tblstapp lstapp = (Tblstapp) dbService.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", params);
				if(lstapp != null){
					//Previous App Status
					lstapp.setLastapplicationstatus(lstapp.getApplicationstatus());
					
					lstapp.setIscancelled(iscancelled == null ? false : iscancelled);
					lstapp.setCancelledby(UserUtil.securityService.getUserName());
					lstapp.setReasonforcancellation(reasonforcancellation);
					lstapp.setApplicationstatus(appstatus);
					lstapp.setStatusdatetime(new Date());
					lstapp.setDatelastupdated(new Date());
					lstapp.setLastupdatedby(UserUtil.securityService.getUserName());
					
					lstapp.setIsrejected(null);
					if(dbService.saveOrUpdate(lstapp)){
						flag = "success";
						
//						if(lstapp.getIscancelled()){
//							//Email Submit Application (Kevin 10.12.2018)
//							params.put("username", UserUtil.securityService.getUserName());
//							dbService.execSQLQueryTransformer("EXEC sp_InsertEmailSMTP @appno=:appno, @username=:username, @emailcode = 'EM6', @body =NULL", params, null, 0);
//						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkflowProcessForm> getWorkFlow(Integer workflowid) {
		List<WorkflowProcessForm> form = new ArrayList<WorkflowProcessForm>();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("id", workflowid);
		try {
			form = (List<WorkflowProcessForm>) dbService.execSQLQueryTransformer(
					"SELECT processname, sequenceno FROM Tbworkflowprocess WHERE workflowid=:id ",
					params, WorkflowProcessForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}


}
