package com.etel.evaluation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.coopdb.data.Tbaccountinfo;
import com.coopdb.data.Tbapprovalmatrix;
import com.coopdb.data.Tbcibankcheck;
import com.coopdb.data.Tbcicreditcheck;
import com.coopdb.data.Tbcitradecheck;
import com.coopdb.data.Tbevaldeposit;
import com.coopdb.data.TbevaldepositId;
import com.coopdb.data.Tbevalloans;
import com.coopdb.data.TbevalloansId;
import com.coopdb.data.Tbevalmonthlyexpense;
import com.coopdb.data.Tbevalmonthlyincome;
import com.coopdb.data.Tbevalreport;
import com.coopdb.data.Tbloanapprovaldetails;
import com.coopdb.data.Tblstapp;
import com.coopdb.data.Tbmemberdosri;
import com.coopdb.data.Tbworkflowprocess;
import com.etel.approval.ApprovalService;
import com.etel.approval.ApprovalServiceImpl;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.dataentry.FullDataEntryService;
import com.etel.dataentry.FullDataEntryServiceImpl;
import com.etel.defaultusers.forms.DefaultUsers;
import com.etel.evaluation.forms.CoMakerForm;
import com.etel.evaluation.forms.EvalAccessRightsForm;
import com.etel.evaluation.forms.IncomeValidationForm;
import com.etel.evaluation.forms.MonthlyIncomeExpenseForm;
import com.etel.history.HistoryService;
import com.etel.history.HistoryServiceImpl;
import com.etel.qde.QDEService;
import com.etel.qde.QDEServiceImpl;
import com.etel.utils.AuditLog;
import com.etel.utils.AuditLogEvents;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;
import com.etel.workflow.WorkflowService;
import com.etel.workflow.WorkflowServiceImpl;
import com.loansdb.data.Tbbirequest;
import com.loansdb.data.Tbcirequest;
import com.loansdb.data.Tbcolappraisalrequest;
import com.loansdb.data.Tbincomeexpense;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class EvaluationServiceImpl implements EvaluationService {

	private DBService dbService = new DBServiceImpl();
	private Map<String, Object> params = HQLUtil.getMap();
	public static SecurityService securityService = (SecurityService) RuntimeAccess.getInstance()
			.getServiceBean("securityService");
	private String username = securityService.getUserName();

	/**
	 * --Create Evaluation Report--
	 * 
	 * @author Kevin (01.24.2018)
	 * @return String = success, otherwise failed
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String createEvalReport(String appno) {
		// System.out.println("---------- running createEvalReport");
		String flag = "failed";
		Tbevalreport evalrpt = new Tbevalreport();
		params.put("appno", appno);
		String spouseName = "";
		String address = "";
		try {
			Tblstapp lstapp = (Tblstapp) dbService.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", params);
			if (lstapp.getCifno() != null) {
				params.put("cifno", lstapp.getCifno());
				if (lstapp.getCustomertype() != null && lstapp.getCustomertype().equals("1")) {
					spouseName = (String) dbService.executeUniqueSQLQuery(
							"SELECT (ISNULL(NULLIF(spouselastname, '') + ', ','')+"
									+ "ISNULL(spousefirstname +' ','')+ISNULL(spousemiddlename,'')) as spousename FROM Tbmember WHERE membershipid=:cifno",
							params);
				}
				// address = (String) dbService.executeUniqueSQLQuery("SELECT
				// ISNULL(fulladdress1, '') FROM Tbmember WHERE
				// membershipid=:cifno", params);
			}
			evalrpt = (Tbevalreport) dbService.executeUniqueHQLQueryMaxResultOne("FROM Tbevalreport WHERE appno=:appno",
					params);
			if (evalrpt == null) {
				evalrpt = new Tbevalreport();
				evalrpt.setAppno(appno);
				evalrpt.setCifno(lstapp.getCifno());
				evalrpt.setCustomername(lstapp.getCifname());
				evalrpt.setSpousename(spouseName);
				evalrpt.setAddress(address);
				// Set status to New
				evalrpt.setStatus("0");

				// Set assigned evaluation head
				if (lstapp.getCompanycode() != null) {
					DefaultUsers assignedEvalHead = new DefaultUsers(lstapp.getCoopcode());
					if (assignedEvalHead != null) {
						if (lstapp.getCustomertype().equals("1")) {
							// Retail
							evalrpt.setAssignedevaluationhead(assignedEvalHead.getEvaluatorheadr());
						} else if (lstapp.getCustomertype().equals("2") || lstapp.getCustomertype().equals("3")) {
							// Corporate || Sole Prop
							evalrpt.setAssignedevaluationhead(assignedEvalHead.getEvaluatorheadc());
						}
					}
				}
				// Eval Report type = ORIGINAL
				evalrpt.setEvalreporttype("0");

				evalrpt.setCustomertype(lstapp.getCustomertype());
				evalrpt.setLoanproduct(lstapp.getLoanproduct());
				evalrpt.setCompanycode(lstapp.getCompanycode());
				evalrpt.setTotalapplicantincome(BigDecimal.ZERO);
				evalrpt.setTotalspouseincome(BigDecimal.ZERO);
				evalrpt.setTotalcomakerincome(BigDecimal.ZERO);
				evalrpt.setTotalcombinedgrossincome(BigDecimal.ZERO);
				evalrpt.setTotalapplicantexpense(BigDecimal.ZERO);
				evalrpt.setTotalspouseexpense(BigDecimal.ZERO);
				evalrpt.setTotalcomakerexpense(BigDecimal.ZERO);
				evalrpt.setTotalcombinedgrossexpense(BigDecimal.ZERO);
				if (dbService.save(evalrpt)) {
					flag = "success";
					lstapp.setCreateevalreportflag(0);
					dbService.saveOrUpdate(lstapp);

					// System.out.println("------------- EVAL ID : " +
					// evalrpt.getEvalreportid());
					// Replicate CI Reports - Wel 09.25.18
					generateEvalOtherBanks(appno, evalrpt.getEvalreportid());
					generateEvalCreditCheck(appno, evalrpt.getEvalreportid());
					// generateEvalTradeCheck(appno, evalrpt.getEvalreportid());

					// Replicate CF Details to LAM - Kevin 08-30-2018
					// LAMService lamsrvc = new LAMServiceImpl();
					// lamsrvc.saveCFDetailsToLAM(evalrpt.getAppno(),
					// evalrpt.getEvalreportid()); Ced removed not needed in
					// coop 12172018

				}
			} else {
				Integer createflag = lstapp.getCreateevalreportflag();
				if (createflag != null && createflag != 0) {
					Integer maxId = (Integer) dbService.executeUniqueSQLQuery(
							"SELECT MAX(evalreportid) FROM Tbevalreport WHERE appno=:appno", params);
					if (maxId != null) {
						params.put("maxId", maxId);
						evalrpt = (Tbevalreport) dbService
								.executeUniqueHQLQuery("FROM Tbevalreport WHERE evalreportid=:maxId", params);
						if (evalrpt != null) {
							Tbevalreport newEvalRpt = evalrpt;
							newEvalRpt.setEvalreportid(null);
							newEvalRpt.setAppno(appno);
							// Set status to New
							newEvalRpt.setStatus("0");

							// Set assigned evaluation head
							newEvalRpt.setAssignedevaluator(null);

							newEvalRpt.setAcceptanceclientdecision(null);
							newEvalRpt.setAcceptanceremarks(null);
							if (lstapp.getCompanycode() != null) {
								DefaultUsers assignedEvalHead = new DefaultUsers(lstapp.getCompanycode());
								if (assignedEvalHead != null) {
									if (lstapp.getCustomertype().equals("1")) {
										// Retail
										evalrpt.setAssignedevaluationhead(assignedEvalHead.getEvaluatorheadr());
									} else if (lstapp.getCustomertype().equals("2")
											|| lstapp.getCustomertype().equals("3")) {
										// Corporate || Sole Prop
										evalrpt.setAssignedevaluationhead(assignedEvalHead.getEvaluatorheadc());
									}
								}
							}
							newEvalRpt.setLoanproduct(lstapp.getLoanproduct());
							newEvalRpt.setCompanycode(lstapp.getCompanycode());
							newEvalRpt.setEvalreporttype(String.valueOf(createflag));
							if (dbService.save(newEvalRpt)) {
								flag = "success";
								lstapp.setCreateevalreportflag(0);
								dbService.saveOrUpdate(lstapp);

								// Replicate previous Tbevalmonthlyincome data
								List<Tbevalmonthlyincome> monthlyIncome = (List<Tbevalmonthlyincome>) dbService
										.executeListHQLQuery(
												"FROM Tbevalmonthlyincome WHERE appno=:appno AND evalreportid=:maxId",
												params);
								if (monthlyIncome != null && !monthlyIncome.isEmpty()) {
									for (Tbevalmonthlyincome mIncome : monthlyIncome) {
										Tbevalmonthlyincome m = mIncome;
										m.setId(null);
										m.setEvalreportid(newEvalRpt.getEvalreportid());
										dbService.saveOrUpdate(m);
									}
								}

								// Replicate previous Tbevalmonthlyexpense data
								List<Tbevalmonthlyexpense> monthlyExpense = (List<Tbevalmonthlyexpense>) dbService
										.executeListHQLQuery(
												"FROM Tbevalmonthlyexepense WHERE appno=:appno AND evalreportid=:maxId",
												params);
								if (monthlyExpense != null && !monthlyExpense.isEmpty()) {
									for (Tbevalmonthlyexpense mExpense : monthlyExpense) {
										Tbevalmonthlyexpense m = mExpense;
										m.setId(null);
										m.setEvalreportid(newEvalRpt.getEvalreportid());
										dbService.saveOrUpdate(m);
									}
								}

								/*
								 * 
								 * Added LAM Tables 08-29-2018 (Kevin)
								 */

								// Replicate previous Tbfinancialstatements data
								// List<Tbfinancialstatements> fs =
								// (List<Tbfinancialstatements>)
								// dbService.executeListHQLQuery("FROM
								// Tbfinancialstatements WHERE cfappno=:appno
								// AND evalreportid=:maxId", params);
								// if(fs != null && !fs.isEmpty()){
								// for(Tbfinancialstatements f : fs){
								// Tbfinancialstatements m = f;
								// m.setFsid(null);
								// m.setEvalreportid(newEvalRpt.getEvalreportid());
								// dbService.saveOrUpdate(m);
								// }
								// }
								//
								// //Replicate previous Tbfsmain data
								// List<Tbfsmain> fsmain = (List<Tbfsmain>)
								// dbService.executeListHQLQuery("FROM Tbfsmain
								// WHERE appno=:appno AND evalreportid=:maxId",
								// params);
								// if(fsmain != null && !fsmain.isEmpty()){
								// for(Tbfsmain fsm : fsmain){
								// Tbfsmain m = fsm;
								// m.setId(null);
								// //m.setEvalreportid(newEvalRpt.getEvalreportid());
								// dbService.saveOrUpdate(m);
								// }
								// }
								// //Replicate previous Tblamborrowerprofile
								// data
								// List<Tblamborrowerprofile> lamBorProf =
								// (List<Tblamborrowerprofile>)
								// dbService.executeListHQLQuery("FROM
								// Tblamborrowerprofile WHERE appno=:appno AND
								// evalreportid=:maxId", params);
								// if(lamBorProf != null &&
								// !lamBorProf.isEmpty()){
								// for(Tblamborrowerprofile bf : lamBorProf){
								// Tblamborrowerprofile m = bf;
								// m.setId(null);
								// m.setEvalreportid(newEvalRpt.getEvalreportid());
								// dbService.saveOrUpdate(m);
								// }
								// }
								//
								// //Replicate previous Tblamcorporateprofile
								// data
								// List<Tblamcorporateprofile> lamCorpProf =
								// (List<Tblamcorporateprofile>)
								// dbService.executeListHQLQuery("FROM
								// Tblamcorporateprofile WHERE appno=:appno AND
								// evalreportid=:maxId", params);
								// if(lamCorpProf != null &&
								// !lamCorpProf.isEmpty()){
								// for(Tblamcorporateprofile cf : lamCorpProf){
								// Tblamcorporateprofile m = cf;
								// m.setId(null);
								// m.setEvalreportid(newEvalRpt.getEvalreportid());
								// dbService.saveOrUpdate(m);
								// }
								// }
								//
								// //Replicate previous Tblamcovenants data
								// List<Tblamcovenants> lamCovenants =
								// (List<Tblamcovenants>)
								// dbService.executeListHQLQuery("FROM
								// Tblamcovenants WHERE appno=:appno AND
								// evalreportid=:maxId", params);
								// if(lamCovenants != null &&
								// !lamCovenants.isEmpty()){
								// for(Tblamcovenants lc : lamCovenants){
								// Tblamcovenants m = lc;
								// m.setId(null);
								// m.setEvalreportid(newEvalRpt.getEvalreportid());
								// dbService.saveOrUpdate(m);
								// }
								// }
								// //Replicate previous Tblamdocumentation data
								// List<Tblamdocumentation> lamDocs =
								// (List<Tblamdocumentation>)
								// dbService.executeListHQLQuery("FROM
								// Tblamdocumentation WHERE appno=:appno AND
								// evalreportid=:maxId", params);
								// if(lamDocs != null && !lamDocs.isEmpty()){
								// for(Tblamdocumentation ld : lamDocs){
								// Tblamdocumentation m = ld;
								// m.setId(null);
								// m.setEvalreportid(newEvalRpt.getEvalreportid());
								// dbService.saveOrUpdate(m);
								// }
								// }
								//
								// //Replicate previous Tblamloandetails data
								// List<Tblamloandetails> lamLoanDetails =
								// (List<Tblamloandetails>)
								// dbService.executeListHQLQuery("FROM
								// Tblamloandetails WHERE appno=:appno AND
								// evalreportid=:maxId", params);
								// if(lamLoanDetails != null &&
								// !lamLoanDetails.isEmpty()){
								// for(Tblamloandetails ld : lamLoanDetails){
								// Tblamloandetails m = ld;
								// m.setId(null);
								// m.setEvalreportid(newEvalRpt.getEvalreportid());
								// dbService.saveOrUpdate(m);
								// }
								// }
								//
								// //Replicate previous Tblamothertermconditions
								// data
								// List<Tblamothertermconditions>
								// lamOtherTermCond =
								// (List<Tblamothertermconditions>)
								// dbService.executeListHQLQuery("FROM
								// Tblamothertermconditions WHERE appno=:appno
								// AND evalreportid=:maxId", params);
								// if(lamOtherTermCond != null &&
								// !lamOtherTermCond.isEmpty()){
								// for(Tblamothertermconditions tc :
								// lamOtherTermCond){
								// Tblamothertermconditions m = tc;
								// m.setId(null);
								// m.setEvalreportid(newEvalRpt.getEvalreportid());
								// dbService.saveOrUpdate(m);
								// }
								// }
								//
								// //Replicate previous Tblamrationalerecomm
								// data
								// List<Tblamrationalerecomm> lamRatioRecom =
								// (List<Tblamrationalerecomm>)
								// dbService.executeListHQLQuery("FROM
								// Tblamrationalerecomm WHERE appno=:appno AND
								// evalreportid=:maxId", params);
								// if(lamRatioRecom != null &&
								// !lamRatioRecom.isEmpty()){
								// for(Tblamrationalerecomm rr : lamRatioRecom){
								// Tblamrationalerecomm m = rr;
								// m.setId(null);
								// m.setEvalreportid(newEvalRpt.getEvalreportid());
								// dbService.saveOrUpdate(m);
								// }
								// }
								//
								// //Replicate previous Tblamriskprofile data
								// List<Tblamriskprofile> lamRiskProfile =
								// (List<Tblamriskprofile>)
								// dbService.executeListHQLQuery("FROM
								// Tblamriskprofile WHERE appno=:appno AND
								// evalreportid=:maxId", params);
								// if(lamRiskProfile != null &&
								// !lamRiskProfile.isEmpty()){
								// for(Tblamriskprofile rp : lamRiskProfile){
								// Tblamriskprofile m = rp;
								// m.setId(null);
								// m.setEvalreportid(newEvalRpt.getEvalreportid());
								// dbService.saveOrUpdate(m);
								// }
								// }
								//
								// //Replicate previous Tblamriskprofile data
								// List<Tblamsublimits> lamSubLimits =
								// (List<Tblamsublimits>)
								// dbService.executeListHQLQuery("FROM
								// Tblamsublimits WHERE appno=:appno AND
								// evalreportid=:maxId", params);
								// if(lamSubLimits != null &&
								// !lamSubLimits.isEmpty()){
								// for(Tblamsublimits sl : lamSubLimits){
								// Tblamsublimits m = sl;
								// m.setId(null);
								// m.setEvalreportid(newEvalRpt.getEvalreportid());
								// dbService.saveOrUpdate(m);
								// }
								// }
								//
								// //Replicate previous Tblamapa data
								// List<Tblamapa> lamAPA = (List<Tblamapa>)
								// dbService.executeListHQLQuery("FROM Tblamapa
								// WHERE appno=:appno AND id=:maxId", params);
								// if(lamAPA != null && !lamAPA.isEmpty()){
								// for(Tblamapa apa : lamAPA){
								// Tblamapa m = apa;
								// m.setId(newEvalRpt.getEvalreportid());
								// dbService.saveOrUpdate(m);
								// }
								// }
								//
								// //Replicate previous Tbapaotherincomeexpense
								// data
								// List<Tbapaotherincomeexpense> lamAPAIncome =
								// (List<Tbapaotherincomeexpense>)
								// dbService.executeListHQLQuery("FROM
								// Tbapaotherincomeexpense WHERE appno=:appno
								// AND evalreportid=:maxId", params);
								// if(lamAPAIncome != null &&
								// !lamAPAIncome.isEmpty()){
								// for(Tbapaotherincomeexpense apa :
								// lamAPAIncome){
								// Tbapaotherincomeexpense m = apa;
								// m.setId(null);
								// m.setEvalreportid(newEvalRpt.getEvalreportid());
								// dbService.saveOrUpdate(m);
								// }
								// }

								// Replicate CI Reports - Wel 09.25.18
								generateEvalOtherBanks(appno, newEvalRpt.getEvalreportid());
								generateEvalCreditCheck(appno, newEvalRpt.getEvalreportid());
								// generateEvalTradeCheck(appno,
								// newEvalRpt.getEvalreportid());
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * --Get Evaluation Report List by Appno--
	 * 
	 * @author Kevin (01.24.2018)
	 * @return List <{@link Tbevalreport}>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbevalreport> getEvalReportList(String appno) {
		List<Tbevalreport> evalreport = new ArrayList<Tbevalreport>();
		params.put("appno", appno);
		try {
			evalreport = (List<Tbevalreport>) dbService.execSQLQueryTransformer(
					"SELECT (SELECT fullname FROM TBUSER WHERE username=a.assignedevaluator) as assignedevaluator, "
							+ "(SELECT fullname FROM TBUSER WHERE username=a.assignedevaluationhead) as assignedevaluationhead, "
							+ "(SELECT desc1 FROM TBCODETABLE WHERE CODENAME='REPORTSTATUS' AND codevalue=a.status) as status, "
							+ "(SELECT desc1 FROM TBCODETABLE WHERE CODENAME='EVALREPORTTYPE' AND codevalue=a.evalreporttype) as evalreporttype, * "
							+ "FROM Tbevalreport a WHERE a.appno=:appno",
					params, Tbevalreport.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return evalreport;
	}

	/**
	 * --Get Evaluation Report by evalreportid--
	 * 
	 * @author Kevin (01.24.2018)
	 * @return List <Tbevalreport>
	 */
	@Override
	public Tbevalreport getEvalReportByReportId(Integer evalreportid) {
		Tbevalreport evalreport = new Tbevalreport();
		try {
			if (evalreportid != null) {
				params.put("evalreportid", evalreportid);
				evalreport = (Tbevalreport) dbService
						.executeUniqueHQLQuery("FROM Tbevalreport WHERE evalreportid=:evalreportid", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return evalreport;
	}

	/**
	 * --SaveOrUpdate Eval Report--
	 * 
	 * @author Kevin (01.24.2018)
	 * @return String = success, otherwise failed
	 */
	@Override
	public String saveOrUpdateEvalReport(Tbevalreport evalreport) {
		String flag = "failed";
		try {
			if (evalreport.getAppno() != null && evalreport.getEvalreportid() != null) {
				params.put("appno", evalreport.getAppno());
				params.put("evalreportid", evalreport.getEvalreportid());
				Tbevalreport rpt = (Tbevalreport) dbService.executeUniqueHQLQuery(
						"FROM Tbevalreport WHERE appno=:appno AND evalreportid=:evalreportid", params);
				if (rpt != null) {
					// System.out.println("REPORT STATUS>>>>>>>>>>>> " +
					// rpt.getStatus());
					if (evalreport.getAssignedevaluator() != null) {
						if (rpt.getAssignedevaluator() == null) {
							rpt.setAssignedevaluator(evalreport.getAssignedevaluator());
							rpt.setDateassigned(new Date());
						} else {
							// if re-assigned
							if (!evalreport.getAssignedevaluator().equalsIgnoreCase(rpt.getAssignedevaluator())) {
								rpt.setAssignedevaluator(evalreport.getAssignedevaluator());
								rpt.setDateassigned(new Date());
							}
						}
					}
					rpt.setEvaluatorremarks(evalreport.getEvaluatorremarks());
					rpt.setEvaluationheadremarks(evalreport.getEvaluationheadremarks());
					rpt.setMigsrating(evalreport.getMigsrating());
					if (dbService.saveOrUpdate(rpt)) {
						AuditLog.addAuditLog(
								AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("DEDUPE",
										AuditLogEvents.LOAN_APPLICATION_EVALUATION)),
								"User " + username + " Saved " + rpt.getAppno() + "'s Evaluation Report.", username,
								new Date(), AuditLogEvents.LOAN_APPLICATION_EVALUATION);
						flag = "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * --Save Monthly Income Computation--
	 * 
	 * @author Kevin (01.24.2018)
	 * @return String = success, otherwise failed
	 */
	@Override
	public String saveMonthlyIncome(Tbevalmonthlyincome income) {
		String flag = "failed";
		BigDecimal totalapplicantincome = BigDecimal.ZERO;
		BigDecimal totalspouseincome = BigDecimal.ZERO;
		BigDecimal totalcomakerincome = BigDecimal.ZERO;
		try {
			if (income.getEvalreportid() != null) {
				params.put("evalid", income.getEvalreportid());

				if (dbService.saveOrUpdate(income)) {
					AuditLog.addAuditLog(
							AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("ADD MONTHLY INCOME DETAILS",
									AuditLogEvents.LOAN_APPLICATION_EVALUATION)),
							"User " + username + " Added " + income.getAppno() + "'s Monthly Income Details.", username,
							new Date(), AuditLogEvents.LOAN_APPLICATION_EVALUATION);
					flag = "success";
					Tbevalreport report = (Tbevalreport) dbService
							.executeUniqueHQLQuery("FROM Tbevalreport WHERE evalreportid=:evalreportid", params);
					if (report != null) {
						BigDecimal applicantincome = (BigDecimal) dbService.executeUniqueSQLQuery(
								"SELECT SUM(grossincome) FROM Tbevalmonthlyincome WHERE persontype='0' AND appno=:appno AND evalreportid=:evalid",
								params);
						if (applicantincome != null) {
							totalapplicantincome = applicantincome;
						}
						BigDecimal spouseincome = (BigDecimal) dbService.executeUniqueSQLQuery(
								"SELECT SUM(grossincome) FROM Tbevalmonthlyincome WHERE persontype='2' AND appno=:appno AND evalreportid=:evalid",
								params);
						if (spouseincome != null) {
							totalspouseincome = spouseincome;
						}
						BigDecimal comakerincome = (BigDecimal) dbService.executeUniqueSQLQuery(
								"SELECT SUM(grossincome) FROM Tbevalmonthlyincome WHERE persontype='1' AND appno=:appno AND evalreportid=:evalid",
								params);
						if (comakerincome != null) {
							totalcomakerincome = comakerincome;
						}
						report.setTotalapplicantincome(totalapplicantincome);
						report.setTotalspouseincome(totalspouseincome);
						report.setTotalcomakerincome(totalcomakerincome);
						report.setTotalcombinedgrossincome(
								totalapplicantincome.add(totalspouseincome).add(totalcomakerincome));
						if (dbService.saveOrUpdate(report)) {
							flag = "success";
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * --Save Monthly Expense Computation--
	 * 
	 * @author Kevin (01.24.2018)
	 * @return String = success, otherwise failed
	 */
	@Override
	public String saveMonthlyExpense(Tbevalmonthlyexpense expense) {
		String flag = "failed";
		BigDecimal totalapplicantexpense = BigDecimal.ZERO;
		BigDecimal totalspouseexpense = BigDecimal.ZERO;
		BigDecimal totalcomakerexpense = BigDecimal.ZERO;
		try {
			if (expense.getEvalreportid() != null) {
				params.put("evalid", expense.getEvalreportid());
				if (dbService.saveOrUpdate(expense)) {
					flag = "success";
					Tbevalreport report = (Tbevalreport) dbService
							.executeUniqueHQLQuery("FROM Tbevalreport WHERE evalreportid=:evalreportid", params);
					if (report != null) {
						BigDecimal applicantexpense = (BigDecimal) dbService.executeUniqueSQLQuery(
								"SELECT SUM(expenseamount) FROM Tbevalmonthlyexpense WHERE persontype='0' AND appno=:appno AND evalreportid=:evalid",
								params);
						if (applicantexpense != null) {
							totalapplicantexpense = applicantexpense;
						}
						BigDecimal spouseexpense = (BigDecimal) dbService.executeUniqueSQLQuery(
								"SELECT SUM(expenseamount) FROM Tbevalmonthlyexpense WHERE persontype='2' AND appno=:appno AND evalreportid=:evalid",
								params);
						if (spouseexpense != null) {
							totalspouseexpense = spouseexpense;
						}
						BigDecimal comakerexpense = (BigDecimal) dbService.executeUniqueSQLQuery(
								"SELECT SUM(expenseamount) FROM Tbevalmonthlyexpense WHERE persontype='1' AND appno=:appno AND evalreportid=:evalid",
								params);
						if (comakerexpense != null) {
							totalcomakerexpense = comakerexpense;
						}
						report.setTotalapplicantexpense(totalapplicantexpense);
						report.setTotalspouseexpense(totalspouseexpense);
						report.setTotalcomakerexpense(totalcomakerexpense);
						report.setTotalcombinedgrossexpense(
								totalapplicantexpense.add(totalspouseexpense).add(totalcomakerexpense));
						if (dbService.saveOrUpdate(report)) {
							AuditLog.addAuditLog(
									AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID(
											"ADD MONTHLY EXPENSE DETAILS", AuditLogEvents.LOAN_APPLICATION_EVALUATION)),
									"User " + username + " Added " + report.getAppno() + "'s Monthly Expense Details.",
									username, new Date(), AuditLogEvents.LOAN_APPLICATION_EVALUATION);
							flag = "success";
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * --Delete Monthly Income by id--
	 * 
	 * @author Kevin (01.24.2018)
	 * @return String = success, otherwise failed
	 */
	@Override
	public String deleteMonthlyIncome(Integer id) {
		String flag = "failed";
		try {
			if (id != null) {
				params.put("id", id);
				int res = dbService.executeUpdate("DELETE FROM Tbevalmonthlyincome WHERE id=:id", params);
				if (res > 0) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * --Delete Monthly Expense by id--
	 * 
	 * @author Kevin (01.24.2018)
	 * @return String = success, otherwise failed
	 */
	@Override
	public String deleteMonthlyExpense(Integer id) {
		String flag = "failed";
		try {
			if (id != null) {
				params.put("id", id);
				int res = dbService.executeUpdate("DELETE FROM Tbevalmonthlyexpense WHERE id=:id", params);
				if (res > 0) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * --Get Monthly Income & Expense--
	 * 
	 * @author Kevin (01.24.2018)
	 * @return form = {@link MonthlyIncomeExpenseForm}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public MonthlyIncomeExpenseForm getMonthlyIncomeExpense(String appno, Integer evalreportid) {
		MonthlyIncomeExpenseForm form = new MonthlyIncomeExpenseForm();
		BigDecimal totalapplicantincome = BigDecimal.ZERO;
		BigDecimal totalspouseincome = BigDecimal.ZERO;
		BigDecimal totalcomakerincome = BigDecimal.ZERO;
		BigDecimal totalapplicantexpense = BigDecimal.ZERO;
		BigDecimal totalspouseexpense = BigDecimal.ZERO;
		BigDecimal totalcomakerexpense = BigDecimal.ZERO;
		try {
			if (appno != null && evalreportid != null) {
				params.put("appno", appno);
				params.put("evalid", evalreportid);

				// Monthly Income
				List<Tbevalmonthlyincome> monthlyIncomeList = (List<Tbevalmonthlyincome>) dbService.executeListHQLQuery(
						"FROM Tbevalmonthlyincome WHERE appno=:appno AND evalreportid=:evalid", params);

				BigDecimal applicantincome = (BigDecimal) dbService.executeUniqueSQLQuery(
						"SELECT SUM(grossincome) FROM Tbevalmonthlyincome WHERE persontype='0' AND appno=:appno AND evalreportid=:evalid",
						params);
				if (applicantincome != null) {
					totalapplicantincome = applicantincome;
				}
				BigDecimal spouseincome = (BigDecimal) dbService.executeUniqueSQLQuery(
						"SELECT SUM(grossincome) FROM Tbevalmonthlyincome WHERE persontype='2' AND appno=:appno AND evalreportid=:evalid",
						params);
				if (spouseincome != null) {
					totalspouseincome = spouseincome;
				}
				BigDecimal comakerincome = (BigDecimal) dbService.executeUniqueSQLQuery(
						"SELECT SUM(grossincome) FROM Tbevalmonthlyincome WHERE persontype='1' AND appno=:appno AND evalreportid=:evalid",
						params);
				if (comakerincome != null) {
					totalcomakerincome = comakerincome;
				}
				form.setMonthlyIncomeList(monthlyIncomeList);
				form.setTotalapplicantincome(totalapplicantincome);
				form.setTotalspouseincome(totalspouseincome);
				form.setTotalcomakerincome(totalcomakerincome);
				form.setTotalcombinedgrossincome(totalapplicantincome.add(totalspouseincome).add(totalcomakerincome));

				// Monthly Expense
				List<Tbevalmonthlyexpense> monthlyExpenseList = (List<Tbevalmonthlyexpense>) dbService
						.executeListHQLQuery("FROM Tbevalmonthlyexpense WHERE appno=:appno AND evalreportid=:evalid",
								params);

				BigDecimal applicantexpense = (BigDecimal) dbService.executeUniqueSQLQuery(
						"SELECT SUM(expenseamount) FROM Tbevalmonthlyexpense WHERE persontype='0' AND appno=:appno AND evalreportid=:evalid",
						params);
				if (applicantexpense != null) {
					totalapplicantexpense = applicantexpense;
				}
				BigDecimal spouseexpense = (BigDecimal) dbService.executeUniqueSQLQuery(
						"SELECT SUM(expenseamount) FROM Tbevalmonthlyexpense WHERE persontype='2' AND appno=:appno AND evalreportid=:evalid",
						params);
				if (spouseexpense != null) {
					totalspouseexpense = spouseexpense;
				}
				BigDecimal comakerexpense = (BigDecimal) dbService.executeUniqueSQLQuery(
						"SELECT SUM(expenseamount) FROM Tbevalmonthlyexpense WHERE persontype='1' AND appno=:appno AND evalreportid=:evalid",
						params);
				if (comakerexpense != null) {
					totalcomakerexpense = comakerexpense;
				}
				form.setMonthlyExpenseList(monthlyExpenseList);
				form.setTotalapplicantexpense(totalapplicantexpense);
				form.setTotalspouseexpense(totalspouseexpense);
				form.setTotalcomakerexpense(totalcomakerexpense);
				form.setTotalcombinedgrossexpense(
						totalapplicantexpense.add(totalspouseexpense).add(totalcomakerexpense));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	/**
	 * --Get Eval Access Rights--
	 * 
	 * @author Kevin (01.24.2018)
	 * @return form = {@link EvalAccessRightsForm}
	 */
	@Override
	public EvalAccessRightsForm getEvalAccessRights(String appno, Integer evalreportid) {
		EvalAccessRightsForm form = new EvalAccessRightsForm();
		String username = UserUtil.securityService.getUserName();
		params.put("appno", appno);
		boolean isEvalHead = false;
		try {
			if (evalreportid != null) {
				Integer maxEvalId = (Integer) dbService
						.executeUniqueSQLQuery("SELECT MAX(evalreportid) FROM Tbevalreport WHERE appno=:appno", params);
				// If Latest Eval Report
				if (maxEvalId.equals(evalreportid)) {
					params.put("evalreportid", evalreportid);
					Tbevalreport report = (Tbevalreport) dbService.executeUniqueHQLQuery(
							"FROM Tbevalreport WHERE appno=:appno AND evalreportid=:evalreportid", params);
					if (report != null) {
						// Evaluation Head Default User
						if (report.getAssignedevaluationhead() != null
								&& report.getAssignedevaluationhead().equalsIgnoreCase(username)) {
							isEvalHead = true;
						}

						// New or On Going or Returned
						if (report.getStatus().equals("0") || report.getStatus().equals("4")
								|| report.getStatus().equals("1")) {
							// Evaluator
							if (report.getAssignedevaluator() != null
									&& report.getAssignedevaluator().equalsIgnoreCase(username)
									&& !report.getStatus().equals("0")) {
								form.setBtnReturn(true);
								form.setBtnSaveOrDelete(true);
								form.setBtnSubmitToEvalHead(true);
								form.setReadOnly(false);
								form.setTxtEvaluatorRemarks(false);
							}
							// Eval Head
							if (isEvalHead && report.getAssignedevaluator() == null) {
								form.setTxtAssignedEvaluator(false);
								form.setBtnSubmitToEvaluator(true);
								form.setTxtEvalHeadRemarks(false);
							}
						}

						// For Review
						if (report.getStatus().equals("2")) {
							// Eval Head
							if (isEvalHead) {
								form.setTxtEvalHeadRemarks(false);
								form.setBtnReturn(true);
								form.setBtnReturnToEvaluator(true);
								form.setBtnSubmitApplication(true);
							}
						}
						// Daniel Fesalbon 02.12.2019
						QDEService qde = new QDEServiceImpl();
						Tblstapp loanapp = (Tblstapp) dbService
								.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", params);
						if (loanapp != null) {
							Tbmemberdosri dosri = qde.getDosriDetails(loanapp.getCifno());
							ApprovalService a = new ApprovalServiceImpl();
							Tbloanapprovaldetails d = a.getLoanApprovalDetails(appno, evalreportid, username);
							Tbapprovalmatrix matrix = a.getApprovalMatrixByTranstype(appno.substring(0, 2),
									loanapp.getLoanproduct());
							List<Tbloanapprovaldetails> list = a.getListOfLoanApprDetails(appno, evalreportid, true);
							if (loanapp.getApplicationstatus().equals(4) && matrix != null) {
								if (d != null) {
									System.out.println(d.getId().getUsername() + d.getDecisiondate() + " ez");
									if (d.getId().getUsername() != null && d.getDecisiondate() == null) {
										if (list.size() < 1) {
											if (dosri.getDosristatus().equals("DOSRI")) {
												form.setBtnApproversDecision(
														UserUtil.hasRole(matrix.getLevel2approver()));
											} else {
												form.setBtnApproversDecision(
														UserUtil.hasRole(matrix.getLevel1approver()));
											}
										} else {
											int rejectParam = 0;
											int approveParam = 0;
											for (Tbloanapprovaldetails e : list) {
												if (e.getId().getApprovallevel().equals(1)) {
													if (e.getDecision().equals("1")) {
														approveParam++;
													}
													if (e.getDecision().equals("2")) {
														approveParam++;
													}
													if (e.getDecision().equals("3")) {
														rejectParam++;
													}
												}
											}
											if (dosri.getDosristatus().equals("DOSRI")) {
												if (approveParam < matrix.getLevel2requiredapproval()
														&& rejectParam < matrix.getLevel2requiredrejected()) {
													form.setBtnApproversDecision(
															UserUtil.hasRole(matrix.getLevel2approver()));
												}
											} else {
												if (approveParam < matrix.getLevel1requiredapproval()
														&& rejectParam < matrix.getLevel1requiredrejected()) {
													form.setBtnApproversDecision(
															UserUtil.hasRole(matrix.getLevel1approver()));
												} else {
													Tbaccountinfo acc = (Tbaccountinfo) dbService.executeUniqueHQLQuery(
															"FROM Tbaccountinfo WHERE applno=:appno", params);
													if (acc != null) {
														int exceeding = acc.getFaceamt()
																.compareTo(matrix.getLevel1limit());
														// System.out.println(exceeding);
														if (exceeding > 0) {
															form.setBtnApproversDecision(
																	UserUtil.hasRole(matrix.getLevel2approver()));
														}
													}
												}
											}
										}
									}
								}
							}
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
	 * --Update Eval Report Status--
	 * 
	 * @author Kevin (01.25.2018)
	 * @return String = success, otherwise failed
	 */
	@Override
	public String updateEvalReportStatus(String appno, Integer evalreportid, String status) {
		String flag = "failed";
		try {
			if (appno != null && evalreportid != null) {
				params.put("appno", appno);
				params.put("evalreportid", evalreportid);
				Tbevalreport rpt = (Tbevalreport) dbService.executeUniqueHQLQuery(
						"FROM Tbevalreport WHERE appno=:appno AND evalreportid=:evalreportid", params);
				if (rpt != null) {
					if (status == null) {
						// System.out.println(">>>>>>>>Eval Report Status is
						// null !");
						return flag;
					} else {
						rpt.setStatus(status);

						// On-going
						if (status.equals("1")) {
							rpt.setEvaluationdate(new Date());
						}
						// For Review
						if (status.equals("2")) {
							// rpt.setDatereviewed(new Date());
						}
						// Reviewed
						if (status.equals("3")) {
							rpt.setDatereviewed(new Date());
						}
						if (dbService.saveOrUpdate(rpt)) {
							if (status.equals("1")) {
								AuditLog.addAuditLog(
										AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID(
												"START EVALUATION REPORT", AuditLogEvents.LOAN_APPLICATION_EVALUATION)),
										"User " + username + " Started " + rpt.getAppno() + "Evaluation Report.",
										username, new Date(), AuditLogEvents.LOAN_APPLICATION_EVALUATION);
								flag = "success";
							}
							if (status.equals("2") || status.equals("4")) {
								if (status.equals("4")) {
									AuditLog.addAuditLog(
											AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID(
													"RETURN TO EVALUATOR", AuditLogEvents.LOAN_APPLICATION_EVALUATION)),
											"User " + username + " Returned " + rpt.getAppno() + " to Evaluator.",
											username, new Date(), AuditLogEvents.LOAN_APPLICATION_EVALUATION);
								}
								if (status.equals("2")) {
									AuditLog.addAuditLog(
											AuditLogEvents.getAuditLogEvents(
													AuditLogEvents.getEventID("SUBMIT EVAL REPORT FOR REVIEW",
															AuditLogEvents.LOAN_APPLICATION_EVALUATION)),
											"User " + username + " Submitted " + rpt.getAppno()
													+ "'s Evaluation Report.",
											username, new Date(), AuditLogEvents.LOAN_APPLICATION_EVALUATION);
								}
								flag = "dashboard";
							}
							if (status.equals("3")) {
								AuditLog.addAuditLog(
										AuditLogEvents
												.getAuditLogEvents(AuditLogEvents.getEventID("REVIEW EVALUATION REPORT",
														AuditLogEvents.LOAN_APPLICATION_EVALUATION)),
										"User " + username + " Reviewed " + rpt.getAppno() + "'s Evaluation Report.",
										username, new Date(), AuditLogEvents.LOAN_APPLICATION_EVALUATION);
								WorkflowService wfservice = new WorkflowServiceImpl();
								wfservice.submitApplication("LOANS", appno, null, null, null, null);
								// CED REMOVED 5-24-2018
//								FullDataEntryService fde = new FullDataEntryServiceImpl();
//								Tblstapp app = fde.getLstapp(appno);
//								if (app != null) {
//									params.put("seq", app.getApplicationstatus());
//									Tbworkflowprocess prcss = (Tbworkflowprocess) dbService.executeUniqueHQLQuery(
//											"FROM Tbworkflowprocess WHERE workflowid='3' AND sequenceno=:seq", params);
//									if (prcss != null) {
//										params.put("appno", appno);
//										Integer i = (Integer) dbService
//												.executeUpdate(
//														"UPDATE TBLSTAPP SET applicationstatus='"
//																+ prcss.getSubmitoption1() + "' WHERE appno=:appno",
//														params);
//										if (i > 0) {
								flag = "dashboard";
//										}
//									}
//								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * --Get Comaker List (Cross DB)--
	 * 
	 * @author Kevin (01.26.2018)
	 * @return List<{@link CoMakerForm}>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CoMakerForm> getComakerList(String appno) {
		List<CoMakerForm> form = new ArrayList<CoMakerForm>();
		params.put("appno", appno);
		@SuppressWarnings("unused")
		String cifsdb = "CIFSDB";
		try {
			String cifdbname = (String) dbService.executeUniqueSQLQuery("SELECT dbname FROM TBDATABASEPARAMS", null);
			if (cifdbname != null) {
				cifsdb = cifdbname;
			}
			form = (List<CoMakerForm>) dbService.execSQLQueryTransformer(
					"SELECT a.appno, a.cifno, a.customername, a.participationcode, b.fulladdress1 "
							+ "FROM TBLSTCOMAKERS a" + ", Tbmember b WHERE a.cifno=b.membershipid AND a.appno=:appno",
					params, CoMakerForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	/**
	 * --Get Deposit Accounts List (CI)--
	 * 
	 * @author Kevin (01.26.2018)
	 * @return List<{@link Tbcibankcheck}>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcibankcheck> getDepositAccountsList(String appno, String cifno) {
		List<Tbcibankcheck> bankcheck = new ArrayList<Tbcibankcheck>();
		params.put("appno", appno);
		params.put("cifno", cifno);
		try {
			bankcheck = (List<Tbcibankcheck>) dbService
					.executeListHQLQuery("FROM Tbcibankcheck WHERE appno=:appno AND cifno=:cifno", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bankcheck;
	}

	/**
	 * --Get Trade Check List (CI)--
	 * 
	 * @author Kevin (01.26.2018)
	 * @return List<{@link Tbcibankcheck}>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcitradecheck> getTradeCheckList(String appno, String cifno) {
		List<Tbcitradecheck> tradecheck = new ArrayList<Tbcitradecheck>();
		params.put("appno", appno);
		params.put("cifno", cifno);
		try {
			tradecheck = (List<Tbcitradecheck>) dbService
					.executeListHQLQuery("FROM Tbcitradecheck WHERE appno=:appno AND cifno=:cifno", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tradecheck;
	}

	/**
	 * --Update Assigned Evaluator--
	 * 
	 * @author Kevin (01.30.2018)
	 * @return String = success, otherwise failed
	 */
	@Override
	public String updateAssignedEvaluator(String appno, Integer evalreportid, String assignedevaluator) {
		String flag = "failed";
		try {
			if (appno != null && evalreportid != null) {
				params.put("appno", appno);
				params.put("evalreportid", evalreportid);
				int res = dbService.executeUpdate(
						"UPDATE Tbevalreport SET assignedevaluator='" + assignedevaluator
								+ "', dateassigned=GETDATE() WHERE appno=:appno AND evalreportid=:evalreportid",
						params);
				if (res > 0) {
					AuditLog.addAuditLog(
							AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("ASSIGN EVALUATOR",
									AuditLogEvents.LOAN_APPLICATION_EVALUATION)),
							"User " + username + " Assigned " + appno + "'s Evaluator.", username, new Date(),
							AuditLogEvents.LOAN_APPLICATION_EVALUATION);
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * --Get Latest Evaluation Report Id by Appno--
	 * 
	 * @author Kevin (09.01.2018)
	 * @return Integer = evalreportid
	 */
	@Override
	public Integer getLatestEvalReportIdByAppno(String appno) {
		Integer evalreportid = null;
		try {
			if (appno != null) {
				params.put("appno", appno);
				evalreportid = (Integer) dbService.execSQLQueryTransformer(
						"SELECT MAX(evalreportid) FROM Tbevalreport WHERE appno=:appno", params, null, 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return evalreportid;
	}

	// Added by Wel
	/**
	 * --Refresh From Acacia 360 CASA and save to Tbevaldeposit (per CIF Number)--
	 * 
	 * @author Wel (09.24.2018)
	 * @return String = success otherwise failed
	 */
	@Override
	public String refreshFromCASA(String evalreportid) {
		String flag = "failed";
		try {
			if (evalreportid != null) {
				// Integer evalID = getLatestEvalReportIdByAppno(appno);
				params.put("evalreportid", evalreportid);
				// String cifno = (String)
				// dbService.execSQLQueryTransformer("SELECT cifno FROM Tblstapp
				// WHERE appno=:appno", params, null, 0);
				// if (cifno!=null && !cifno.equals("")) {
				// // Query at CASA DB
				// params.put("cifno", cifno);
				// List<Tbdeposit> list = (List<Tbdeposit>)
				// dbService.execSQLQueryTransformer
				// ("SELECT * FROM TBDEPOSIT WHERE AccountNo IN (SELECT
				// AccountNo FROM TBDEPOSITCIF WHERE cifno=:cifno)", params,
				// Tbdeposit.class, 1);
				// if (list!=null && !list.isEmpty()) {
				// for (Tbdeposit d : list) {
				// Tbevaldeposit e = new Tbevaldeposit();
				// e.getId().setEvalreportid(evalID);
				// e.getId().setRecordid(d.getId());
				// e.setCireportid(""); // if empty string from CASA
				// e.setCifno(cifno);
				// e.setAppno(appno);
				// e.setBankaccttype(d.getProductCode());
				// e.setBank(null); // Default
				// e.setBranch(null);
				// e.setAccountname(d.getAccountName());
				// e.setAccountnumber(d.getAccountNo());
				// e.setDateopened(d.getBookDate());
				// e.setAdb(d.getAverageDailyBalance());
				// e.setAmc(d.getMtdcredits());
				// e.setOutstandingbal(d.getAccountBalance());
				// e.setAccountstatus(String.valueOf(d.getAccountStatus()));
				// dbService.save(e);
				// }
				// }
				// }
				System.out.println(dbService.execStoredProc(
						"UPDATE eval SET eval.bankaccttype = dep.ProductCode, branch = unit, accountname = mtx.prodsname,"
								+ "accountnumber = AccountNo,adb = AverageDailyBalance,amc = eomadbbookbal,outstandingbal = AccountBalance,"
								+ "accountstatus = dep.AccountStatus " + "FROM TBEVALDEPOSIT eval  "
								+ "LEFT JOIN TBDEPOSIT dep on eval.recordid = dep.Id "
								+ "LEFT JOIN TBPRODMATRIX mtx on mtx.prodcode = dep.SubProductCode "
								+ "WHERE eval.evalreportid =:evalreportid AND cireportid = '' ",
						params, null, 2, null) + " accounts updated from LMS");
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String refreshFromLMS(String evalreportid) {
		String flag = "failed";
		try {
			if (evalreportid != null) {
				// Integer evalID = getLatestEvalReportIdByAppno(appno);
				params.put("evalreportid", evalreportid);
				// String cifno = (String)
				// dbService.execSQLQueryTransformer("SELECT cifno FROM Tblstapp
				// WHERE appno=:appno", params, null, 0);
				// if (cifno!=null && !cifno.equals("")) {
				// // Query at LMS DB
				// params.put("cifno", cifno);
				// List<Tbloans> list = (List<Tbloans>)
				// dbService.executeListHQLQuery("FROM Tbloans WHERE
				// principalNo=:cifno", params);
				// if (list!=null && !list.isEmpty()) {
				// for (Tbloans l : list) {
				// Tbevalloans e = new Tbevalloans();
				// e.getId().setEvalreportid(evalID);
				// e.getId().setRecordid(l.getId());
				// e.setCireportid(""); // if empty string from LMS
				// e.setCifno(cifno);
				// e.setAppno(appno);
				// e.setLoantype(l.getLoantype());
				// e.setBank(null); // Default
				// e.setBranch(l.getLegbranch());
				// e.setPncnno(l.getPnno());
				// e.setAccountname(l.getFullname());
				// e.setValuedate(l.getDtbook());
				// e.setMaturitydate(l.getMatdt());
				// e.setOutstandingbal(l.getLoanbal());
				// e.setCurrency("PHP");
				// e.setPnloanamount(l.getPnamt());
				// e.setExperiencehandling(null);
				// dbService.save(e);
				// }
				// }
				// }
				System.out.println(dbService.execStoredProc(
						"UPDATE eval SET eval.outstandingbal = loan.loanbal, eval.loantype = loan.prodcode "
								+ "FROM TBEVALLOANS eval " + "LEFT JOIN TBLOANS loan on eval.recordid = loan.id "
								+ "WHERE eval.evalreportid  =:evalreportid",
						params, null, 2, null) + " accounts updated from LMS");
				AuditLog.addAuditLog(
						AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("REFRESH FROM ACACIA 360 LMS",
								AuditLogEvents.LOAN_APPLICATION_EVALUATION)),
						"User " + username + " Refreshed From Acacia 360 LMS.", username, new Date(),
						AuditLogEvents.LOAN_APPLICATION_EVALUATION);
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * --Generate/Replicate Evaluation Deposit Account from Other Banks (From CI
	 * Report)--
	 * 
	 * @author Wel (09.24.2018)
	 * @return String = success otherwise failed
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String generateEvalOtherBanks(String appno, Integer evalID) {
		// System.out.println("---------- running generateEvalOtherBanks");
		// System.out.println("---------- evalID : " + evalID);
		String flag = "failed";
		try {
			if (appno != null && evalID != null) {
				params.put("appno", appno);
				List<Tbcibankcheck> cibankcheck = (List<Tbcibankcheck>) dbService
						.executeListHQLQuery("FROM Tbcibankcheck WHERE appno=:appno", params);
				if (cibankcheck != null && !cibankcheck.isEmpty()) {
					for (Tbcibankcheck c : cibankcheck) {
						Tbevaldeposit e = new Tbevaldeposit();
						TbevaldepositId id = new TbevaldepositId();
						id.setEvalreportid(evalID);
						id.setRecordid(c.getId());
						e.setId(id);
						e.setCireportid(c.getCireportid());
						e.setCifno(c.getCifno());
						e.setAppno(c.getAppno());
						e.setBankaccttype(c.getBankaccttype());
						e.setBank(c.getBank());
						e.setBranch(c.getBranch());
						e.setAccountname(c.getAccountname());
						e.setAccountnumber(c.getAccountnumber());
						e.setDateopened(c.getDateopened());
						e.setAdb(c.getAdb());
						e.setAmc(c.getAmc());
						e.setOutstandingbal(c.getOutstandingbal());
						e.setAccountstatus(c.getAccountstatus());
						dbService.save(e);
					}
				}
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String generateEvalCreditCheck(String appno, Integer evalID) {
		// System.out.println("---------- running generateEvalCreditCheck");
		String flag = "failed";
		try {
			if (appno != null && evalID != null) {
				params.put("appno", appno);
				List<Tbcicreditcheck> cicreditcheck = (List<Tbcicreditcheck>) dbService
						.executeListHQLQuery("FROM Tbcicreditcheck WHERE appno=:appno", params);
				if (cicreditcheck != null && !cicreditcheck.isEmpty()) {
					for (Tbcicreditcheck c : cicreditcheck) {
						Tbevalloans e = new Tbevalloans();
						TbevalloansId id = new TbevalloansId();
						id.setEvalreportid(evalID);
						id.setRecordid(c.getId());
						e.setId(id);
						e.setCireportid(c.getCireportid());
						e.setCifno(c.getCifno());
						e.setAppno(c.getAppno());
						e.setLoantype(c.getLoantype());
						e.setBank(c.getBank());
						e.setBranch(c.getBranch());
						e.setPncnno(c.getPncnno());
						e.setAccountname(c.getAccountname());
						e.setValuedate(c.getValuedate());
						e.setMaturitydate(c.getMaturitydate());
						e.setOutstandingbal(c.getOutstandingbal());
						e.setCurrency(c.getCurrency());
						e.setPnloanamount(c.getPnloanamount());
						e.setExperiencehandling(c.getExperiencehandling());
						dbService.save(e);
					}
				}
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	// @SuppressWarnings("unchecked")
	// @Override
	// public String generateEvalTradeCheck(String appno, Integer evalID) {
	// System.out.println("---------- running generateEvalTradeCheck");
	// String flag = "failed";
	// try {
	// if (appno!=null && evalID!=null) {
	// params.put("appno", appno);
	// List<Tbcitradecheck> cictradecheck = (List<Tbcitradecheck>)
	// dbService.executeListHQLQuery
	// ("FROM Tbcitradecheck WHERE appno=:appno", params);
	// if (cictradecheck!=null && !cictradecheck.isEmpty()) {
	// for (Tbcitradecheck c : cictradecheck) {
	// Tbevaltradecheck e = new Tbevaltradecheck();
	// TbevaltradecheckId id = new TbevaltradecheckId();
	// id.setEvalreportid(evalID);
	// id.setRecordid(c.getId());
	// e.setId(id);
	// e.setCireportid(c.getCireportid());
	// e.setCifno(c.getCifno());
	// e.setAppno(c.getAppno());
	// e.setTradetype(c.getTradetype());
	// e.setCompanyname(c.getCompanyname());
	// e.setCompanyaddress(c.getCompanyaddress());
	// e.setAreacodephone(c.getAreacodephone());
	// e.setCountrycodephone(c.getCountrycodephone());
	// e.setCompanyphoneno(c.getCompanyphoneno());
	// e.setNaturebusiness(c.getNaturebusiness());
	// e.setAveragetransaction(c.getAveragetransaction());
	// e.setCreditlimit(c.getCreditlimit());
	// e.setLengthofdealing(c.getLengthofdealing());
	// e.setProductservicesoffered(c.getProductservicesoffered());
	// e.setTradecifno(c.getTradecifno());
	// dbService.save(e);
	// }
	// }
	// flag = "success";
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return flag;
	// }

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbevaldeposit> listEvalOtherBanks(String evalreportid, String type) {
		List<Tbevaldeposit> list = new ArrayList<Tbevaldeposit>();
		try {
			if (evalreportid != null) {
				params.put("evalreportid", evalreportid);
				if (type.equalsIgnoreCase("CASA")) {
					System.out.println(dbService.execStoredProc("BEGIN TRY " + "SET IDENTITY_INSERT TBEVALDEPOSIT ON "
							+ "INSERT INTO TBEVALDEPOSIT (evalreportid,recordid,cireportid,cifno,appno,bankaccttype,bank,branch,accountname,accountnumber,"
							+ "dateopened,adb,amc,outstandingbal,accountstatus) "
							+ "SELECT rep.evalreportid,dep.id,'',cifno,appno,prodgroup,'',unit,mtx.prodsname "
							+ ",dep.AccountNo,dep.BookDate,dep.AverageDailyBalance,dep.eomadbbookbal,AccountBalance,AccountStatus "
							+ "FROM TBEVALREPORT rep  " + "LEFT JOIN TBDEPOSIT dep on rep.cifno = dep.EmployeeNo  "
							+ "LEFT JOIN TBPRODMATRIX mtx on mtx.prodcode = dep.SubProductCode "
							+ "WHERE rep.evalreportid =:evalreportid AND dep.Id NOT IN (SELECT recordid FROM TBEVALDEPOSIT WHERE evalreportid =:evalreportid) "
							+ "END TRY " + "BEGIN CATCH " + "	SELECT 0 " + "END CATCH "
							+ "SET IDENTITY_INSERT TBEVALDEPOSIT OFF ", params, null, 2, null)
							+ " deposit accounts from CASA .");
					list = (List<Tbevaldeposit>) dbService.executeListHQLQuery(
							"FROM Tbevaldeposit WHERE evalreportid =:evalreportid AND cireportid=''", params);
				} else if (type.equalsIgnoreCase("APP")) {
					System.out.println(dbService.execStoredProc("BEGIN TRY " + "SET IDENTITY_INSERT TBEVALDEPOSIT ON "
							+ "INSERT INTO TBEVALDEPOSIT (evalreportid,recordid,cireportid,cifno,appno,bankaccttype,bank,branch,accountname,accountnumber,"
							+ "dateopened,adb,amc,outstandingbal,accountstatus) "
							+ "SELECT rep.evalreportid,bnk.id,'APP',rep.cifno,rep.appno,bankaccttype,bank,branch,accountname,accountnumber,bnk.dateopened,adb,amc,outstandingbal,accountstatus "
							+ "FROM TBEVALREPORT rep " + "LEFT JOIN TBCIBANKCHECK bnk on rep.appno = bnk.appno  "
							+ "WHERE rep.evalreportid =:evalreportid AND bnk.id NOT IN (SELECT recordid FROM TBEVALDEPOSIT WHERE evalreportid =:evalreportid AND cireportid <> '') "
							+ "END TRY " + "BEGIN CATCH " + "	SELECT 0 " + "END CATCH "
							+ "SET IDENTITY_INSERT TBEVALDEPOSIT OFF ", params, null, 2, null)
							+ " deposit accounts from application.");
					list = (List<Tbevaldeposit>) dbService.executeListHQLQuery(
							"FROM Tbevaldeposit WHERE evalreportid=:evalreportid AND cireportid<>''", params);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbevalloans> listEvalCreditCheck(String evalreportid, String type) {
		List<Tbevalloans> list = new ArrayList<Tbevalloans>();
		try {
			if (evalreportid != null) {
				params.put("evalreportid", evalreportid);
				if (type.equalsIgnoreCase("LMS")) {
					System.out.println(dbService.execStoredProc("BEGIN TRY " + "SET IDENTITY_INSERT TBEVALLOANS ON "
							+ "INSERT INTO TBEVALLOANS "
							+ "(evalreportid,recordid,cireportid,cifno,appno,loantype,bank,branch,pncnno,accountname, "
							+ "valuedate,maturitydate,outstandingbal,currency,pnloanamount,experiencehandling) "
							+ "SELECT evalreportid,loan.id,'',cifno,appno,prodcode,'',legbranch,accountno,'',dtbook, "
							+ "matdt,loanbal,'',pnamt,''"
							+ "FROM TBEVALREPORT rep " + "LEFT JOIN TBLOANS loan on rep.cifno = loan.PrincipalNo "
							+ "WHERE rep.evalreportid =:evalreportid " + "END TRY " + "BEGIN CATCH " + "	SELECT 0 "
							+ "END CATCH " + "SET IDENTITY_INSERT TBEVALLOANS OFF ", params, null, 2, null)
							+ " loan accounts from LMS.");
					list = (List<Tbevalloans>) dbService.executeListHQLQuery(
							"FROM Tbevalloans WHERE evalreportid=:evalreportid AND cireportid=''", params);
				} else if (type.equalsIgnoreCase("APP")) {
					System.out.println(dbService.execStoredProc("BEGIN TRY " + "SET IDENTITY_INSERT TBEVALLOANS ON "
							+ "INSERT INTO TBEVALLOANS "
							+ "(evalreportid,recordid,cireportid,cifno,appno,loantype,bank,branch,pncnno,accountname, "
							+ "valuedate,maturitydate,outstandingbal,currency,pnloanamount,experiencehandling) "
							+ "SELECT evalreportid,loan.id,'',rep.cifno,rep.appno,loantype,'',branch,pncnno,accountname,"
							+ "valuedate,loan.maturitydate,outstandingbal,currency,pnloanamount,experiencehandling "
							+ "FROM TBEVALREPORT rep " + "LEFT JOIN TBCICREDITCHECK loan on rep.cifno = loan.cifno "
							+ "WHERE rep.evalreportid =:evalreportid " + "END TRY " + "BEGIN CATCH " + "	SELECT 0 "
							+ "END CATCH " + "SET IDENTITY_INSERT TBEVALLOANS OFF ", params, null, 2, null)
							+ " loan accounts from application.");

					list = (List<Tbevalloans>) dbService.executeListHQLQuery(
							"FROM Tbevalloans WHERE evalreportid=:evalreportid AND cireportid<>''", params);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean showRefreshCASAButton(String evalreportid) {
		// TODO Auto-generated method stub
		try {
			if (evalreportid != null) {
				params.put("id", Integer.valueOf(evalreportid));
				Tbevalreport r = (Tbevalreport) dbService
						.executeUniqueHQLQuery("FROM Tbevalreport WHERE evalreportid=:id", params);
				if (r != null) {
					// System.out.println("REPORT FOUND");
					// System.out.println(r.getStatus());
					if (r.getStatus().equals("1") || r.getStatus().equals("4")) {
						return r.getAssignedevaluator().equals(securityService.getUserName());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
			// TODO: handle exception
		}
		return false;
	}

	// @SuppressWarnings("unchecked")
	// @Override
	// public List<Tbevaltradecheck> listEvalTradeCheck(String appno) {
	// List<Tbevaltradecheck> list = new ArrayList<Tbevaltradecheck>();
	// try {
	// if (appno!=null) {
	// params.put("appno", appno);
	// list = (List<Tbevaltradecheck>) dbService.executeListHQLQuery
	// ("FROM Tbevaltradecheck WHERE appno=:appno", params);
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return list;
	// }
	
	//MAR 01-08-2021
	@Override
	public String addIncomeExpenseRecord(Tbincomeexpense record) {
		String flag = "failed";
		try {
			boolean isNew = false;
			String type = "";
			if(record != null && record.getType().equals("I")){
				type = "Income";
			}else{
				type = "Expense";
			}
			if(record.getId()==null) {
				isNew = true;
				record.setDatecreated(new Date());
				record.setCreatedby(UserUtil.securityService.getUserName());
			} else {
				record.setDateupdated(new Date());
				record.setUpdatedby(UserUtil.securityService.getUserName());
			}
			//System.out.println(record.getId());
			if(dbService.saveOrUpdate(record)) {
				flag="success";
				
				if(isNew){
					// HISTORY (Kevin 10.22.2019)
					HistoryService h = new HistoryServiceImpl();
					h.saveHistory(record.getAppno(), AuditLogEvents.ADD_INCOME_EXPENSE, "Added "+type+" - Particulars : "+record.getParticulars()+".");
				
				}else{
					// HISTORY (Kevin 10.22.2019)
					HistoryService h = new HistoryServiceImpl();
					h.saveHistory(record.getAppno(), AuditLogEvents.EDIT_INCOME_EXPENSE, "Updated "+type+" Details - Particulars : "+record.getParticulars()+".");
				
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	public boolean isAssignedBICIAPP(String username, String appno) {
		boolean result = false;
		List<Tbbirequest> reclist;
		List<Tbcirequest> cilist;
		List<Tbcolappraisalrequest> applist;
		try {
			params.put("appno", appno);
			params.put("username", UserUtil.securityService.getUserName());
			reclist = (List<Tbbirequest>) dbService
					.executeListHQLQuery("FROM Tbbirequest where appno=:appno AND assignedbi=:username", params);
			cilist = (List<Tbcirequest>) dbService
					.executeListHQLQuery("FROM Tbcirequest where appno=:appno AND assignedci=:username", params);
			applist = (List<Tbcolappraisalrequest>) dbService.executeListHQLQuery(
					"FROM Tbcolappraisalrequest where appno=:appno AND assignedappraiser=:username", params);
			if (reclist.size() != 0 || cilist.size() != 0 || applist.size() != 0) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public IncomeValidationForm computeIncomeForm(List<Tbincomeexpense> incomelist, String appno, List<Tbincomeexpense> expenselist, String cifno) {
		// TODO Auto-generated method stub
		IncomeValidationForm form = new IncomeValidationForm();
		try {
			if(incomelist != null) {
				for (Tbincomeexpense income : incomelist) {
					form.setTotalgrossincome(form.getTotalgrossincome().add(income.getAmount()));
				}
				
			}
			if(expenselist != null) {
				for (Tbincomeexpense expense : expenselist) {
					form.setTotaldeductions(form.getTotaldeductions().add(expense.getAmount()));
				}
			}
			//monthly amort
			params.put("appno", appno);
			Tbaccountinfo acctinfo = (Tbaccountinfo) dbService.executeUniqueHQLQuery("FROM Tbaccountinfo WHERE applno=:appno", params);
			if(acctinfo != null){
				form.setMonthlyamort(acctinfo.getAmortfee());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return form;
		
	}


}
