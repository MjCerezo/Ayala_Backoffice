package com.etel.instruction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.etel.accessrights.AccessRightsService;
import com.etel.accessrights.AccessRightsServiceImpl;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.defaultusers.forms.DefaultUsers;
import com.etel.forms.ReturnForm;
import com.etel.instruction.forms.InstructionAccessRightsForm;
import com.etel.instruction.forms.InvestigationForm;
import com.etel.instruction.forms.InvestigationFormList;
import com.etel.instruction.forms.RequestValidationForm;
import com.etel.utils.AuditLog;
import com.etel.utils.AuditLogEvents;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;
import com.coopdb.data.Tbappraisalreportmain;
import com.coopdb.data.Tbbireportmain;
import com.coopdb.data.Tbcireportmain;
import com.coopdb.data.Tbcolinvestigationinst;
import com.coopdb.data.Tbinvestigationinst;
import com.coopdb.data.Tbloancollateral;
import com.coopdb.data.Tbloancollateralgroup;
import com.coopdb.data.Tblstapp;
import com.ete.collateral.CollateralServiceImpl;

public class InstructionFormServiceImpl implements InstructionFormService {

	private DBService dbService = new DBServiceImpl();
	public static SecurityService securityService = (SecurityService) RuntimeAccess.getInstance()
			.getServiceBean("securityService");
	private String username = securityService.getUserName();

	/**
	 * Generate Investigation (Stored Procedure)
	 * 
	 * @author Kevin (11.19.2017)
	 * @param appno
	 * @return String = success, otherwise failed
	 */
	@Override
	public String generateInvestigation(String appno, String investntype) {
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (appno != null && investntype != null) {
				params.put("appno", appno);
				params.put("investntype", investntype);
				params.put("username", securityService.getUserName());
				String res = (String) dbService.execSQLQueryTransformer(
						"EXEC sp_GenerateInstructionForm @appno=:appno, @investntype=:investntype, @username=:username",
						params, null, 0);
				if (res.equals("success")) {
					if (investntype.equals("BI")) {
						AuditLog.addAuditLog(
								AuditLogEvents
										.getAuditLogEvents(AuditLogEvents.getEventID("GENERATE BI INSTRUCTION SHEET",
												AuditLogEvents.LOAN_APPLICATION_GENERATE_INSTRUCTION_SHEET)),
								"User " + username + " Generated " + appno + "'s BI Instruction Sheet.", username,
								new Date(), AuditLogEvents.LOAN_APPLICATION_GENERATE_INSTRUCTION_SHEET);
					}
					if (investntype.equals("CI")) {
						AuditLog.addAuditLog(
								AuditLogEvents
										.getAuditLogEvents(AuditLogEvents.getEventID("GENERATE CI INSTRUCTION SHEET",
												AuditLogEvents.LOAN_APPLICATION_GENERATE_INSTRUCTION_SHEET)),
								"User " + username + " Generated " + appno + "'s CI Instruction Sheet.", username,
								new Date(), AuditLogEvents.LOAN_APPLICATION_GENERATE_INSTRUCTION_SHEET);
					}
//					if(investntype.equals("BI")) {}
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * Get List of Investigation
	 * 
	 * @author Kevin (11.19.2017)
	 * @param appno
	 * @param investigationtype - e.g BI, CI
	 * @return form = {@link InvestigationFormList}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public InvestigationFormList getInvestigationList(String appno, String investigationtype) {
		InvestigationFormList form = new InvestigationFormList();
		List<InvestigationForm> indiv = new ArrayList<InvestigationForm>();
		List<InvestigationForm> corp = new ArrayList<InvestigationForm>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("appno", appno);
			params.put("investntype", investigationtype);
			String query = "SELECT appno, cifno, customername, investigationtype, instruction,"
					+ "(SELECT fullname FROM Tbuser WHERE username=initiatedby) AS initiatedby, aoremarks, supervisorremarks,"
					+ "datecreated, updatedby, dateupdated, supervisor," + "customertype, status, lastrecorddate, "
					+ "(SELECT desc1 FROM Tbcodetable WHERE codevalue = participationcode AND codename = 'PARTICIPATIONCODE') as participationcode "
					+ "FROM Tbinvestigationinst WHERE appno=:appno AND investigationtype=:investntype AND (participationcode = 'PRI' OR participationcode = 'CMK')";
			indiv = (List<InvestigationForm>) dbService.execSQLQueryTransformer(query, params, InvestigationForm.class,
					1);

			query = "SELECT appno, cifno, customername, investigationtype, instruction,"
					+ "(SELECT fullname FROM Tbuser WHERE username=initiatedby) AS initiatedby, aoremarks, supervisorremarks,"
					+ "datecreated, updatedby, dateupdated, supervisor," + "customertype, status, lastrecorddate, "
					+ "(SELECT desc1 FROM Tbcodetable WHERE codevalue = participationcode AND codename = 'PARTICIPATIONCODE') as participationcode "
					+ "FROM Tbinvestigationinst WHERE appno=:appno AND investigationtype=:investntype AND participationcode = 'BUS'";
			corp = (List<InvestigationForm>) dbService.execSQLQueryTransformer(query, params, InvestigationForm.class,
					1);

//			String query = "select  cast(case when b.participationcode = '3' then 'Co-Borrower' else 'Primary Borrower' end as varchar) as participationcode,"
//					+ "(select processname from TBWORKFLOWPROCESS where sequenceno = a.applicationstatus and workflowid = '3') as status,"
//					+ "a.appno as appno,"
//					+ "a.cifno as cifno,"
//					+ "a.datecreated as datecreated, "
//					+ "a.cifname as customername "
//					+ "from TBLSTAPP a left join TBLSTCOMAKERS b on a.appno = b.appno";
//			String cust = "";

			// Indiv
			// cust = " AND customertype IN ('1')";

//			cust = "";

			// Corp || Sole Prop
			/*
			 * cust = " AND customertype IN ('2','3')"; corp = (List<InvestigationForm>)
			 * dbService.execSQLQueryTransformer(query + cust, params,
			 * InvestigationForm.class, 1);
			 */

			form.setIndividual(indiv);
			form.setCorporate(corp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	/**
	 * Get Top 10 Latest Bureau Investigation History (Reviewed)
	 * 
	 * @author Kevin (11.19.2017)
	 * @param cifno
	 * @return List<{@link Tbbireportmain}>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbbireportmain> getBIHistory(String cifno) {
		List<Tbbireportmain> list = new ArrayList<Tbbireportmain>();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("cifno", cifno);
		try {
			list = (List<Tbbireportmain>) dbService.execSQLQueryTransformer(
					"SELECT TOP 10 * FROM Tbbireportmain WHERE cifno=:cifno AND status='2' ORDER BY datecreated DESC ",
					params, Tbbireportmain.class, 1);
			for (Tbbireportmain r : list) {
				r.setReportedby(UserUtil.getUserFullname(r.getReportedby()));
				r.setReviewedby(UserUtil.getUserFullname(r.getReviewedby()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Get Top 10 Latest Credit Investigation History (Reviewed)
	 * 
	 * @author Kevin (11.19.2017)
	 * @param cifno
	 * @return List<{@link Tbcireportmain}>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcireportmain> getCIHistory(String cifno) {
		List<Tbcireportmain> list = new ArrayList<Tbcireportmain>();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("cifno", cifno);
		try {
			list = (List<Tbcireportmain>) dbService.execSQLQueryTransformer(
					"SELECT TOP 10 * FROM Tbcireportmain WHERE cifno=:cifno AND status='2' ORDER BY datecreated DESC ",
					params, Tbcireportmain.class, 1);
			for (Tbcireportmain r : list) {
				r.setReportedby(UserUtil.getUserFullname(r.getReportedby()));
				r.setReviewedby(UserUtil.getUserFullname(r.getReviewedby()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Get Tblstapp data
	 * 
	 * @author Kevin (12.18.2017)
	 * @param appno
	 * @return {@link Tblstapp}
	 */
	@Override
	public Tblstapp getLstapp(String appno) {
		Tblstapp lstapp = new Tblstapp();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (appno != null) {
				params.put("appno", appno);
				lstapp = (Tblstapp) dbService.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstapp;
	}

	/**
	 * Update Investigation
	 * 
	 * @author Kevin (12.18.2017)
	 * @return success otherwise failed
	 */
	@Override
	public String saveInvestigation(String appno, String cifno, String investigationtype, String instruction,
			String aoremarks, String assignedsupervisor, String supervisorremarks) {
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (appno != null && cifno != null && investigationtype != null) {
				params.put("appno", appno);
				params.put("cifno", cifno);
				params.put("invsttype", investigationtype);
				Tbinvestigationinst inv = (Tbinvestigationinst) dbService.executeUniqueHQLQuery(
						"FROM Tbinvestigationinst WHERE id.appno=:appno AND id.cifno=:cifno AND id.investigationtype=:invsttype",
						params);
				if (inv != null) {
					inv.setInstruction(instruction);
					inv.setSupervisor(assignedsupervisor);
					inv.setAoremarks(aoremarks);
					inv.setSupervisorremarks(supervisorremarks);
					if (instruction != null) {
						/*
						 * INVESTIGATIONSTAT: 0 New 1 Instruction Opened 2 On-Process 3 Completed 4
						 * On-Hold 5 Skipped 6 Waived
						 */

						if (inv.getStatus().equals("0") || inv.getStatus().equals("2") || inv.getStatus().equals("4")
								|| inv.getStatus().equals("5") || inv.getStatus().equals("6")) {
							if (instruction.equals("R")) {
								if (inv.getStatus().equals("2")) {
								} else {
									inv.setStatus("0");
								}
							} else if (instruction.equals("S")) {
								inv.setStatus("5");
							} else if (instruction.equals("W")) {
								inv.setStatus("6");
							} else if (instruction.equals("OH")) {
								inv.setStatus("4");
							}

						}
					}
					if (dbService.saveOrUpdate(inv)) {
						if (investigationtype.equals("BI")) {
							AuditLog.addAuditLog(
									AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("SAVE BI INSTRUCTION",
											AuditLogEvents.LOAN_APPLICATION_GENERATE_INSTRUCTION_SHEET)),
									"User " + username + " Saved " + inv.getId().getAppno() + "'s BI Instruction.",
									username, new Date(), AuditLogEvents.LOAN_APPLICATION_GENERATE_INSTRUCTION_SHEET);
						}
						if (investigationtype.equals("CI")) {
							AuditLog.addAuditLog(
									AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("SAVE CI INSTRUCTION",
											AuditLogEvents.LOAN_APPLICATION_GENERATE_INSTRUCTION_SHEET)),
									"User " + username + " Saved " + inv.getId().getAppno() + "'s CI Instruction.",
									username, new Date(), AuditLogEvents.LOAN_APPLICATION_GENERATE_INSTRUCTION_SHEET);
						}
						flag = "success";

						/*
						 * REQUESTSTATUS: 0 New 1 Report Opened 2 Report On-going 3 Report Submitted 4
						 * Report For Review 5 Completed 6 Report Returned 7 Cancelled 8 On-hold
						 */

						/*
						 * REPORTSTATUS: 0 New 1 On-going 2 For Review 3 Reviewed 4 Returned 5 Cancelled
						 */

						// Set status to cancelled if skipped or waived ; On-hold = On-hold
						if (instruction.equals("S") || instruction.equals("W") || instruction.equals("OH")) {
							if (investigationtype != null && investigationtype.equals("BI")) {
								if (instruction.equals("OH")) {
									dbService.executeUpdate(
											"UPDATE Tbbirequest SET status='8', statusdate=GETDATE() WHERE appno=:appno AND cifno=:cifno AND status IN('0','1','2','6')",
											params);
								} else {
									dbService.executeUpdate(
											"UPDATE Tbbirequest SET status='4', statusdate=GETDATE() WHERE appno=:appno AND cifno=:cifno AND status IN('0','1','2','6')",
											params);
									dbService.executeUpdate(
											"UPDATE Tbbireportmain SET status='4', statusdatetime=GETDATE() WHERE appno=:appno AND cifno=:cifno AND status IN('0','1','4')",
											params);
								}

							}
							if (investigationtype != null && investigationtype.equals("CI")) {
								if (instruction.equals("OH")) {
									dbService.executeUpdate(
											"UPDATE Tbcirequest SET status='8', statusdate=GETDATE() WHERE appno=:appno AND cifno=:cifno AND status IN('0','1','2','6')",
											params);
								} else {
									dbService.executeUpdate(
											"UPDATE Tbcirequest SET status='4', statusdate=GETDATE() WHERE appno=:appno AND cifno=:cifno AND status IN('0','1','2','6')",
											params);
									dbService.executeUpdate(
											"UPDATE Tbcireportmain SET status='4', statusdatetime=GETDATE() WHERE appno=:appno AND cifno=:cifno AND status IN('0','1','4')",
											params);
								}
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
	 * Get Instruction Access Right
	 * 
	 * @author Kevin (12.20.2017)
	 * @param appno
	 * @param type  = (BI / CI)
	 * @return {@link InstructionAccessRightsForm}
	 */
	@Override
	public InstructionAccessRightsForm getInstructionAccessRights(String appno, String type) {
		InstructionAccessRightsForm form = new InstructionAccessRightsForm();
		Map<String, Object> params = HQLUtil.getMap();
		DefaultUsers def = new DefaultUsers();
		String username = securityService.getUserName();
		AccessRightsService arService = new AccessRightsServiceImpl();
		try {
			if (appno != null) {
				params.put("appno", appno);
				Tblstapp lstapp = (Tblstapp) dbService.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno",
						params);
				if (lstapp != null) {
					Integer apptype = lstapp.getApplicationtype();
					Integer appstatus = lstapp.getApplicationstatus();
					String status = (String) dbService
							.executeUniqueSQLQuery("SELECT processname FROM Tbworkflowprocess WHERE workflowid='"
									+ apptype + "' AND sequenceno='" + appstatus + "'", null);
					if (lstapp.getCompanycode() != null) {
						def = new DefaultUsers(lstapp.getCoopcode());
					}
//					Removed not needed (Ced 01-09-2019)
					if (status != null && status.equals("FOR ENCODING")) {
//						String flag = arService.getOfficer();
						if ((lstapp.getAccountofficer() != null && lstapp.getAccountofficer().equals(username))
//								|| (flag != null && (flag.equals("OFFICER AVAILABLE") || flag.equals("BACKUP OFFICER AVAILABLE")))
						) {
//							Removed showing of Create Request button- ABBY
							form.setBtnSave(true);
							form.setSlcInstruction(false);
							form.setTxtAORemarks(false);
						}
					}
					if (status != null && status.equals("FOR INVESTIGATION AND APPRAISAL")) {
						if (type != null && type.equals("BI")) {
							
							if (username.equals(def.getBisupervisor())) {
								// added showing of Create Request button -ABBY
								form.setBtnCreateRequest(true);
								form.setBtnSave(true);
								form.setTxtSupervisorRemarks(false);
							}
						}
						if (type != null && type.equals("CI")) {
							if (username.equals(def.getCisupervisor())) {
								// added showing of Create Request button -ABBY
								form.setBtnCreateRequest(true);
								form.setBtnSave(true);
								form.setTxtSupervisorRemarks(false);
							}
						}
						if (type != null && type.equals("CA")) {
							if (username.equals(def.getAppraisalsupervisor())) {
								form.setBtnCreateRequest(true);
								form.setBtnSave(true);
								form.setTxtSupervisorRemarks(false);
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

	@SuppressWarnings("unchecked")
	@Override
	public InvestigationFormList getInvestigationListCA(String appno, String investigationtype,
			String collateralcategory) {
		InvestigationFormList form = new InvestigationFormList();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("appno", appno);
			params.put("investntype", investigationtype);
			if (collateralcategory != null) {
				if (collateralcategory.equalsIgnoreCase("S")) {
					params.put("collateralcategory", "S");
				} else if (collateralcategory.equalsIgnoreCase("G")) {
					params.put("collateralcategory", "G");
				}
			}
			String query = "SELECT a.id, a.appno, a.cifno,a.customername,a.investigationtype,instruction,"
					+ "(SELECT fullname FROM Tbuser WHERE username=a.initiatedby) AS initiatedby, a.aoremarks,a.supervisorremarks,"
					+ "a.datecreated,a.updatedby,a.dateupdated,a.supervisor, (SELECT desc1 FROM Tbcodetable WHERE codename='COLLATERALTYPE' AND codevalue=a.grouptype)AS grouptype,"
					+ "(SELECT desc1 FROM Tbcodetable WHERE codename='COLLATERALTYPE' AND codevalue=a.collateraltype) AS collateraltype,a.colid,"
					+ "a.customertype, a.status, a.lastrecorddate,a.participationcode, a.referenceno"
					+ ",(SELECT desc1 FROM Tbcodetable WHERE codename='PROPERTYTYPE' AND codevalue= b.propertytype) as propertytype "
					+ ",(SELECT desc1 FROM Tbcodetable WHERE codename='NEWUSED' AND codevalue=(SELECT neworused FROM TBCOLLATERALAUTO WHERE referenceno=a.referenceno)) as neworused "
					+ "FROM Tbcolinvestigationinst a LEFT JOIN TBCOLLATERALREL b ON b.referenceno = a.referenceno WHERE a.appno=:appno AND a.investigationtype=:investntype and a.collateralcategory=:collateralcategory";

			// Single
			// params.put("collateralcategory", "S");
			List<InvestigationForm> single = (List<InvestigationForm>) dbService.execSQLQueryTransformer(query, params,
					InvestigationForm.class, 1);
			// Group
			// params.put("collateralcategory", "G");
			List<InvestigationForm> group = (List<InvestigationForm>) dbService.execSQLQueryTransformer(query, params,
					InvestigationForm.class, 1);

			form.setSingle(single);
			form.setGroup(group);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return form;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String generateInvestigationCA(String appno) {
		// TODO Auto-generated method stub
		// System.out.println("------------- running generateInvestigationCA");
		Map<String, Object> params = HQLUtil.getMap();
		String flag = "failed";
		List<Tbloancollateral> single = new ArrayList<Tbloancollateral>();
		List<Tbloancollateralgroup> group = new ArrayList<Tbloancollateralgroup>();
		try {
			params.put("appno", appno);
			Tblstapp app = (Tblstapp) dbService.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", params);

			single = (List<Tbloancollateral>) dbService.executeListHQLQuery("FROM Tbloancollateral WHERE appno=:appno",
					params);

			group = (List<Tbloancollateralgroup>) dbService
					.executeListHQLQuery("FROM Tbloancollateralgroup WHERE appno=:appno", params);

			if (single != null) {
				for (Tbloancollateral sn : single) {
					Tbcolinvestigationinst invest = new Tbcolinvestigationinst();
					params.put("colid", sn.getColid());
					Tbcolinvestigationinst colinvest = (Tbcolinvestigationinst) dbService.executeUniqueHQLQuery(
							"FROM Tbcolinvestigationinst WHERE appno=:appno AND colid=:colid", params);
					if (colinvest == null) {
						invest.setAppno(sn.getAppno());
						invest.setColid(sn.getColid());
						invest.setCifno(app.getCifno());
						invest.setCustomername(app.getCifname());
						invest.setInvestigationtype("CA");
						invest.setInstruction("R");
						invest.setInitiatedby(UserUtil.securityService.getUserName());
						invest.setDatecreated(new Date());
						invest.setCustomertype(app.getCustomertype());
						invest.setCollateraltype(sn.getCollateraltype());
						invest.setStatus("0");// New
						invest.setParticipationcode("Primary Borrower");
						invest.setCollateralcategory("S");
						invest.setCompanycode(app.getCompanycode());
						invest.setReferenceno(sn.getCollateralreferenceno());
						if (invest.getCompanycode() != null) {
							DefaultUsers d = new DefaultUsers(app.getCompanycode());
							invest.setSupervisor(d.getAppraisalsupervisor());
						}
						if (dbService.save(invest)) {
							flag = "instruction sheet created - single";
						}

					}
				}
			}
			if (group != null) {
				for (Tbloancollateralgroup gr : group) {
					Tbcolinvestigationinst invest = new Tbcolinvestigationinst();
					params.put("colid", gr.getColid());
					Tbcolinvestigationinst col = (Tbcolinvestigationinst) dbService.executeUniqueHQLQuery(
							"FROM Tbcolinvestigationinst WHERE appno=:appno AND colid=:colid", params);
					if (col == null) {
						invest.setAppno(app.getAppno());
						invest.setGroupid(gr.getGroupid());
						invest.setColid(gr.getColid());
						invest.setGroupname(gr.getGroupname());
						invest.setGrouptype(gr.getGrouptype());
						invest.setCifno(app.getCifno());
						invest.setCustomername(app.getCifname());
						invest.setInvestigationtype("CA");
						invest.setInstruction("R");
						invest.setInitiatedby(UserUtil.securityService.getUserName());
						invest.setDatecreated(new Date());
						invest.setCustomertype(app.getCustomertype());
						invest.setCollateraltype(gr.getCollateraltype());
						invest.setStatus("0");// New
						invest.setParticipationcode("Primary Borrower");
						invest.setCollateralcategory("G");
						invest.setCompanycode(app.getCompanycode());
						if (invest.getCompanycode() != null) {
							DefaultUsers d = new DefaultUsers(app.getCompanycode());
							invest.setSupervisor(d.getAppraisalsupervisor());
						}
						if (dbService.save(invest)) {
							flag = "instruction sheet created - group";
						}
					}
				}
			}
			AuditLog.addAuditLog(
					AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("GENERATE APPRAISAL INSTRUCTION SHEET",
							AuditLogEvents.LOAN_APPLICATION_GENERATE_INSTRUCTION_SHEET)),
					"User " + username + " Generated " + app.getAppno() + "'s Appraisal Instruction Sheet.", username,
					new Date(), AuditLogEvents.LOAN_APPLICATION_GENERATE_INSTRUCTION_SHEET);
			flag = "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbappraisalreportmain> getCAHistory(String cifno, String collateraltype, String referenceno) {
		// TODO Auto-generated method stub
		List<Tbappraisalreportmain> list = new ArrayList<Tbappraisalreportmain>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("cifno", cifno);
			params.put("collateraltype", collateraltype);
			params.put("refno", referenceno);
			System.out.println("collateral type : " + collateraltype);
			list = (List<Tbappraisalreportmain>) dbService.execSQLQueryTransformer(
					"SELECT TOP 10 :collateraltype as collateraltype,"
							+ "(SELECT desc1 FROM TBCODETABLE WHERE codename = 'TYPEAPPRAISAL' AND codevalue = typeappraisal ) as typeappraisal,"
							+ "* FROM Tbappraisalreportmain WHERE cifno=:cifno AND status='2' "
							+ "AND collateraltype =(SELECT codevalue FROM TBCODETABLE WHERE codename = 'COLLATERALTYPE' AND desc1 =:collateraltype ) "
							+ "AND referenceno =:refno " + "ORDER BY datecreated DESC",
					params, Tbappraisalreportmain.class, 1);
			for (Tbappraisalreportmain r : list) {
				r.setReportedby(UserUtil.getUserFullname(r.getReportedby()));
				r.setReviewedby(UserUtil.getUserFullname(r.getReviewedby()));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String saveInvestigationCA(String appno, String cifno, String investigationtype, String instruction,
			String aoremarks, String assignedsupervisor, String supervisorremarks, String colid) {
		// TODO Auto-generated method stub
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (appno != null && cifno != null && investigationtype != null) {
				params.put("appno", appno);
				params.put("cifno", cifno);
				params.put("invsttype", investigationtype);
				params.put("colid", colid);
				Tbcolinvestigationinst inv = (Tbcolinvestigationinst) dbService.executeUniqueHQLQuery(
						"FROM Tbcolinvestigationinst WHERE appno=:appno AND cifno=:cifno AND investigationtype=:invsttype AND colid=:colid",
						params);
				if (inv != null) {
					inv.setInstruction(instruction);
					inv.setSupervisor(assignedsupervisor);
					inv.setAoremarks(aoremarks);
					inv.setSupervisorremarks(supervisorremarks);
//					if(instruction != null){
//						if(inv.getStatus().equals("New") || inv.getStatus().equals("Skipped") || inv.getStatus().equals("Waived") || inv.getStatus().equals("On-Process")){
//							if(instruction.equals("R")){
//								if(inv.getStatus().equals("On-Process")){
//								}else{
//									inv.setStatus("New");
//								}
//							}else if(instruction.equals("S")){
//								inv.setStatus("Skipped");
//							}else if(instruction.equals("W")){
//								inv.setStatus("Waived");
//							}
//							
//						}
//					}
					if (instruction != null) {
						/*
						 * INVESTIGATIONSTAT: 0 New 1 Instruction Opened 2 On-Process 3 Completed 4
						 * On-Hold 5 Skipped 6 Waived
						 */

						if (inv.getStatus().equals("0") || inv.getStatus().equals("2") || inv.getStatus().equals("4")
								|| inv.getStatus().equals("5") || inv.getStatus().equals("6")) {
							if (instruction.equals("R")) {
								if (inv.getStatus().equals("2")) {
								} else {
									inv.setStatus("0");
								}
							} else if (instruction.equals("S")) {
								inv.setStatus("5");
							} else if (instruction.equals("W")) {
								inv.setStatus("6");
							} else if (instruction.equals("OH")) {
								inv.setStatus("4");
							}

						}
					}
					if (dbService.saveOrUpdate(inv)) {
						AuditLog.addAuditLog(
								AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("SAVE APPRAISAL INSTRUCTION",
										AuditLogEvents.LOAN_APPLICATION_GENERATE_INSTRUCTION_SHEET)),
								"User " + username + " Saved " + inv.getAppno() + "'s Appraisal Instruction.", username,
								new Date(), AuditLogEvents.LOAN_APPLICATION_GENERATE_INSTRUCTION_SHEET);
						flag = "success";

//						//Set status to cancelled if skipped or waived
//						if(instruction.equals("S") || instruction.equals("W")){
//							dbService.executeUpdate("UPDATE Tbcolappraisalrequest SET status='4', statusdate=GETDATE() WHERE appno=:appno AND cifno=:cifno AND status IN('0','1','2')", params);
//							dbService.executeUpdate("UPDATE Tbappraisalreportmain SET status='4', statusdatetime=GETDATE() WHERE appno=:appno AND cifno=:cifno AND status IN('0','1')", params);
//						}

						/*
						 * REQUESTSTATUS: 0 New 1 Report Opened 2 Report On-going 3 Report Submitted 4
						 * Report For Review 5 Completed 6 Report Returned 7 Cancelled 8 On-hold
						 */

						/*
						 * REPORTSTATUS: 0 New 1 On-going 2 For Review 3 Reviewed 4 Returned 5 Cancelled
						 */

						// Set status to cancelled if skipped or waived ; On-hold = On-hold
						if (instruction.equals("S") || instruction.equals("W") || instruction.equals("OH")) {
							if (instruction.equals("OH")) {
								dbService.executeUpdate(
										"UPDATE Tbcolappraisalrequest SET status='8', statusdate=GETDATE() WHERE appno=:appno AND cifno=:cifno AND status IN('0','1','2','6')",
										params);
							} else {
								dbService.executeUpdate(
										"UPDATE Tbcolappraisalrequest SET status='4', statusdate=GETDATE() WHERE appno=:appno AND cifno=:cifno AND status IN('0','1','2','6')",
										params);
								dbService.executeUpdate(
										"UPDATE Tbappraisalreportmain SET status='4', statusdatetime=GETDATE() WHERE appno=:appno AND cifno=:cifno AND status IN('0','1','4')",
										params);
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

	@SuppressWarnings("unchecked")
	@Override
	public String checkInstructionSheet(String appno) {
		// TODO Auto-generated method stub
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		params.put("appno", appno);
		try {

			// BI & CI
			List<Tbinvestigationinst> inslist = (List<Tbinvestigationinst>) dbService
					.executeListHQLQuery("FROM Tbinvestigationinst WHERE appno=:appno AND aoremarks is NULL", params);
			if (inslist.size() == 0 || inslist == null) {
				flag = "success";

				// Collateral Appraisal
				List<Tbloancollateral> collaterals = (List<Tbloancollateral>) dbService
						.executeListHQLQuery("FROM Tbloancollateral WHERE appno=:appno", params);

				if (collaterals.size() == 0 || collaterals == null) {
					flag = "success";
				} else {

					List<Tbcolinvestigationinst> colinstlist = (List<Tbcolinvestigationinst>) dbService
							.executeListHQLQuery("FROM Tbcolinvestigationinst WHERE appno=:appno AND aoremarks is NULL",
									params);
					if (colinstlist.size() == 0 || colinstlist == null) {
						flag = "success";
					} else {
						flag = "failed";
					}
				}
			} else {
				flag = "failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Boolean isOpenedBySupervisor(String appno, String cifno, String investigationtype, String collateralid) {
		boolean flag = false;
		Map<String, Object> params = HQLUtil.getMap();
		String username = UserUtil.securityService.getUserName();
		try {
			if (appno != null && cifno != null && investigationtype != null) {
				params.put("appno", appno);
				params.put("cifno", cifno);
				params.put("invsttype", investigationtype);

				Tblstapp lstapp = (Tblstapp) dbService.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno",
						params);
				DefaultUsers def = new DefaultUsers(
						UserUtil.getUserByUsername(securityService.getUserName()).getCoopcode());

				if (investigationtype.equals("BI") || investigationtype.equals("CI")) {
					Tbinvestigationinst inv = (Tbinvestigationinst) dbService.executeUniqueHQLQuery(
							"FROM Tbinvestigationinst WHERE id.appno=:appno AND id.cifno=:cifno AND id.investigationtype=:invsttype",
							params);
					if (inv != null) {
						// New
						if (inv.getStatus().equals("0")) {
							// Bureau Investigation Supervisor
							if (investigationtype.equals("BI")) {
								if (username.equals(def.getBisupervisor())) {
									// Instruction Opened
									inv.setStatus("1");
									if (dbService.saveOrUpdate(inv)) {
										flag = true;
									}
								}
							}
							// Credit Investigation Supervisor
							if (investigationtype.equals("CI")) {
								if (username.equals(def.getCisupervisor())) {
									// Instruction Opened
									inv.setStatus("1");
									if (dbService.saveOrUpdate(inv)) {
										flag = true;
									}
								}
							}
						}

					}
				} else if (investigationtype.equals("CA") && collateralid != null) {
					params.put("colid", collateralid);
					Tbcolinvestigationinst colinv = (Tbcolinvestigationinst) dbService.executeUniqueHQLQuery(
							"FROM Tbcolinvestigationinst WHERE appno=:appno AND cifno=:cifno AND investigationtype=:invsttype AND colid=:colid",
							params);
					if (colinv != null) {
						// New
						if (colinv.getStatus().equals("0")) {

							// Appraisal Investigation Supervisor
							if (username.equals(def.getAppraisalsupervisor())) {
								// Instruction Opened
								colinv.setStatus("1");
								if (dbService.saveOrUpdate(colinv)) {
									flag = true;
								}
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

	@Override
	public Boolean checkIfReportIsOpened(String reportid, String status, String type) {
		boolean flag = false;
		Map<String, Object> params = HQLUtil.getMap();
		String username = UserUtil.securityService.getUserName();
		try {
			if (reportid != null && status != null && type != null) {
				params.put("reportid", reportid);
				params.put("invsttype", type);

				// Bureau Investigation User
				if (type.equals("BI")) {
					Tbbireportmain biRpt = (Tbbireportmain) dbService
							.executeUniqueHQLQuery("FROM Tbbireportmain WHERE bireportid=:reportid", params);
					if (biRpt != null) {
						// New
						if (status.equals("0")) {
							if (username.equals(biRpt.getReportedby())) {
								System.out.println(">>>>>>>>>>>>>>NEW BI REQUEST: username is equal: " + username
										+ " - " + biRpt.getReportedby());
								// Report Opened - 1
								flag = true;
								params.put("birequestid", biRpt.getBirequestid());
								dbService.executeUpdate(
										"UPDATE Tbbirequest SET status='1', statusdate=GETDATE() WHERE birequestid=:birequestid",
										params);

							}
						}

						// For Review
						if (status.equals("2")) {
							if (biRpt.getCompanycode() != null) {
								DefaultUsers def = new DefaultUsers(biRpt.getCompanycode());
								if (def != null && def.getBisupervisor() != null
										&& def.getBisupervisor().equals(username)) {
									// Report For Review - 4
									flag = true;
									params.put("birequestid", biRpt.getBirequestid());
									dbService.executeUpdate(
											"UPDATE Tbbirequest SET status='4', statusdate=GETDATE() WHERE birequestid=:birequestid",
											params);

								}
							}
						}

					}
				}

				// Credit Investigation User
				if (type.equals("CI")) {
					Tbcireportmain ciRpt = (Tbcireportmain) dbService
							.executeUniqueHQLQuery("FROM Tbcireportmain WHERE cireportid=:reportid", params);
					if (ciRpt != null) {
						// New
						if (status.equals("0")) {
							if (username.equals(ciRpt.getReportedby())) {
								System.out.println(">>>>>>>>>>>>>>NEW CI REQUEST: username is equal: " + username
										+ " - " + ciRpt.getReportedby());
								// Report Opened - 1
								flag = true;
								params.put("cirequestid", ciRpt.getCirequestid());
								dbService.executeUpdate(
										"UPDATE Tbcirequest SET status='1', statusdate=GETDATE() WHERE cirequestid=:cirequestid",
										params);

							}
						}

						// For Review
						if (status.equals("2")) {
							if (ciRpt.getCompanycode() != null) {
								DefaultUsers def = new DefaultUsers(ciRpt.getCompanycode());
								if (def != null && def.getCisupervisor() != null
										&& def.getCisupervisor().equals(username)) {
									// Report For Review - 4
									flag = true;
									params.put("cirequestid", ciRpt.getCirequestid());
									dbService.executeUpdate(
											"UPDATE Tbcirequest SET status='4', statusdate=GETDATE() WHERE cirequestid=:cirequestid",
											params);

								}
							}
						}

					}
				}

				// Appraisal Investigation User
				if (type.equals("CA")) {
					Tbappraisalreportmain apprRpt = (Tbappraisalreportmain) dbService.executeUniqueHQLQuery(
							"FROM Tbappraisalreportmain WHERE appraisalreportid=:reportid", params);
					if (apprRpt != null) {
						// New
						if (status.equals("0")) {
							if (username.equals(apprRpt.getReportedby())) {
								// Report Opened - 1
								flag = true;
								params.put("colappraisalrequestid", apprRpt.getColappraisalrequestid());
								dbService.executeUpdate(
										"UPDATE Tbcolappraisalrequest SET status='1', statusdate=GETDATE() WHERE colappraisalrequestid=:colappraisalrequestid",
										params);

							}
						}

						// For Review
						if (status.equals("2")) {
							if (apprRpt.getCompanycode() != null) {
								DefaultUsers def = new DefaultUsers(apprRpt.getCompanycode());
								if (def != null && def.getAppraisalsupervisor() != null
										&& def.getAppraisalsupervisor().equals(username)) {
									// Report For Review - 4
									flag = true;
									params.put("colappraisalrequestid", apprRpt.getColappraisalrequestid());
									dbService.executeUpdate(
											"UPDATE Tbcolappraisalrequest SET status='4', statusdate=GETDATE() WHERE colappraisalrequestid=:colappraisalrequestid",
											params);

								}
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

	@Override
	public String startReport(String reportid, String status, String type) {
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		String username = UserUtil.securityService.getUserName();
		try {
			if (reportid != null && status != null && type != null) {
				params.put("reportid", reportid);

				// New
				if (status.equals("0")) {
					// Bureau Investigation
					if (type.equals("BI")) {
						Tbbireportmain biRpt = (Tbbireportmain) dbService
								.executeUniqueHQLQuery("FROM Tbbireportmain WHERE bireportid=:reportid", params);
						if (biRpt != null && biRpt.getReportedby() != null) {
							if (username.equals(biRpt.getReportedby())) {
								// On-going - 1
								biRpt.setStatus("1");
								biRpt.setStatusdatetime(new Date());
								biRpt.setReportdate(new Date());
								biRpt.setReportedby(username);
								if (dbService.saveOrUpdate(biRpt)) {
									AuditLog.addAuditLog(
											AuditLogEvents
													.getAuditLogEvents(AuditLogEvents.getEventID("START BI REPORT",
															AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL)),
											"User " + username + " Started BI Report.", username, new Date(),
											AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL);
									flag = "success";

									// Report On-going - 2
									params.put("birequestid", biRpt.getBirequestid());
									dbService.executeUpdate(
											"UPDATE Tbbirequest SET status='2', statusdate=GETDATE() WHERE birequestid=:birequestid",
											params);
								}
							}
						}
					}
					// Credit Investigation
					if (type.equals("CI")) {
						Tbcireportmain ciRpt = (Tbcireportmain) dbService
								.executeUniqueHQLQuery("FROM Tbcireportmain WHERE cireportid=:reportid", params);
						if (ciRpt != null) {
							if (username.equals(ciRpt.getReportedby())) {
								// On-going - 1
								ciRpt.setStatus("1");
								ciRpt.setStatusdatetime(new Date());
								ciRpt.setReportdate(new Date());
								ciRpt.setReportedby(username);
								if (dbService.saveOrUpdate(ciRpt)) {
									AuditLog.addAuditLog(
											AuditLogEvents
													.getAuditLogEvents(AuditLogEvents.getEventID("START CI REPORT",
															AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL)),
											"User " + username + " Started CI Report.", username, new Date(),
											AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL);
									flag = "success";

									// Report On-going - 2
									params.put("cirequestid", ciRpt.getCirequestid());
									dbService.executeUpdate(
											"UPDATE Tbcirequest SET status='2', statusdate=GETDATE() WHERE cirequestid=:cirequestid",
											params);

								}
							}
						}
					}

					// Appraisal
					if (type.equals("CA")) {
						Tbappraisalreportmain apprRpt = (Tbappraisalreportmain) dbService.executeUniqueHQLQuery(
								"FROM Tbappraisalreportmain WHERE appraisalreportid=:reportid", params);
						if (apprRpt != null) {
							if (username.equals(apprRpt.getReportedby())) {
								// On-going - 1
								apprRpt.setStatus("1");
								apprRpt.setStatusdatetime(new Date());
								apprRpt.setReportdate(new Date());
								apprRpt.setReportedby(username);
								if (dbService.saveOrUpdate(apprRpt)) {
									AuditLog.addAuditLog(
											AuditLogEvents
													.getAuditLogEvents(AuditLogEvents.getEventID("START CA REPORT",
															AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL)),
											"User " + username + " Started CA Report.", username, new Date(),
											AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL);
									flag = "success";

									// Report On-going - 2
									params.put("colappraisalrequestid", apprRpt.getColappraisalrequestid());
									dbService.executeUpdate(
											"UPDATE Tbcolappraisalrequest SET status='2', statusdate=GETDATE() WHERE colappraisalrequestid=:colappraisalrequestid",
											params);

								}
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

	@Override
	public String submitReport(String reportid, String status, String type) {
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		String username = UserUtil.securityService.getUserName();
		try {
			if (reportid != null && status != null && type != null) {
				params.put("reportid", reportid);

				// Bureau Investigation
				if (type.equals("BI")) {
					Tbbireportmain biRpt = (Tbbireportmain) dbService
							.executeUniqueHQLQuery("FROM Tbbireportmain WHERE bireportid=:reportid", params);
					if (biRpt != null) {
						if (username.equals(biRpt.getReportedby())) {
							// Report For Review - 2
							biRpt.setStatus("2");
							biRpt.setStatusdatetime(new Date());

							// Assign Default Supervisor
							if (biRpt.getCompanycode() != null) {
								DefaultUsers def = new DefaultUsers(biRpt.getCompanycode());
								if (def != null && def.getBisupervisor() != null) {
									biRpt.setReviewedby(def.getBisupervisor());
								}
							}

							if (dbService.saveOrUpdate(biRpt)) {
								AuditLog.addAuditLog(
										AuditLogEvents.getAuditLogEvents(
												AuditLogEvents.getEventID("SUBMIT BI REPORT FOR REVIEW",
														AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL)),
										"User " + username + " Submitted BI Report for Review.", username, new Date(),
										AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL);
								flag = "success";

								// Report Submitted - 3
								params.put("birequestid", biRpt.getBirequestid());
								dbService.executeUpdate(
										"UPDATE Tbbirequest SET status='3', statusdate=GETDATE() WHERE birequestid=:birequestid",
										params);
							}
						}
					}
				}

				// Credit Investigation
				if (type.equals("CI")) {
					Tbcireportmain ciRpt = (Tbcireportmain) dbService
							.executeUniqueHQLQuery("FROM Tbcireportmain WHERE cireportid=:reportid", params);
					if (ciRpt != null) {
						// Report For Review - 2
						ciRpt.setStatus("2");
						ciRpt.setStatusdatetime(new Date());

						// Assign Default Supervisor
						if (ciRpt.getCompanycode() != null) {
							DefaultUsers def = new DefaultUsers(ciRpt.getCompanycode());
							if (def != null && def.getCisupervisor() != null) {
								ciRpt.setReviewedby(def.getCisupervisor());
							}
						}

						if (dbService.saveOrUpdate(ciRpt)) {
							AuditLog.addAuditLog(
									AuditLogEvents
											.getAuditLogEvents(AuditLogEvents.getEventID("SUBMIT CI REPORT FOR REVIEW",
													AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL)),
									"User " + username + " Submitted CI Report for Review.", username, new Date(),
									AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL);
							flag = "success";

							// Report Submitted - 3
							params.put("cirequestid", ciRpt.getCirequestid());
							dbService.executeUpdate(
									"UPDATE Tbcirequest SET status='3', statusdate=GETDATE() WHERE cirequestid=:cirequestid",
									params);

						}
					}
				}

				// Appraisal
				if (type.equals("CA")) {
					Tbappraisalreportmain apprRpt = (Tbappraisalreportmain) dbService.executeUniqueHQLQuery(
							"FROM Tbappraisalreportmain WHERE appraisalreportid=:reportid", params);
					if (apprRpt != null) {
						// Report For Review - 2
						apprRpt.setStatus("2");
						;
						apprRpt.setStatusdatetime(new Date());

						// Assign Default Supervisor
						if (apprRpt.getCompanycode() != null) {
							DefaultUsers def = new DefaultUsers(apprRpt.getCompanycode());
							if (def != null && def.getCisupervisor() != null) {
								apprRpt.setReviewedby(def.getAppraisalsupervisor());
							}
						}

						if (dbService.saveOrUpdate(apprRpt)) {
							AuditLog.addAuditLog(
									AuditLogEvents
											.getAuditLogEvents(AuditLogEvents.getEventID("SUBMIT CA REPORT FOR REVIEW",
													AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL)),
									"User " + username + " Submitted CA Report for Review.", username, new Date(),
									AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL);
							flag = "success";

							// Report Submitted - 3
							params.put("colappraisalrequestid", apprRpt.getColappraisalrequestid());
							dbService.executeUpdate(
									"UPDATE Tbcolappraisalrequest SET status='3', statusdate=GETDATE() WHERE colappraisalrequestid=:colappraisalrequestid",
									params);

						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String doneReviewReport(String reportid, String status, String type) {
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		String username = UserUtil.securityService.getUserName();
		try {
			if (reportid != null && status != null && type != null) {
				params.put("reportid", reportid);

				// Bureau Investigation
				if (type.equals("BI")) {
					Tbbireportmain biRpt = (Tbbireportmain) dbService
							.executeUniqueHQLQuery("FROM Tbbireportmain WHERE bireportid=:reportid", params);
					if (biRpt != null) {
						// Reviewed - 3
						biRpt.setStatus("3");
						biRpt.setStatusdatetime(new Date());
						biRpt.setReviewedby(username);
						biRpt.setRevieweddatetime(new Date());
						if (dbService.saveOrUpdate(biRpt)) {
							AuditLog.addAuditLog(
									AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("DONE BI REPORT REVIEW",
											AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL)),
									"User " + username + " Reviewed BI Report.", username, new Date(),
									AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL);
							flag = "success";

							// Completed - 5
							params.put("birequestid", biRpt.getBirequestid());
							int res = dbService.executeUpdate(
									"UPDATE Tbbirequest SET status='5', statusdate=GETDATE() WHERE birequestid=:birequestid",
									params);
							if (res > 0) {
								// Update status (Tbinvestigationinst) Inside Application
								if (biRpt.getAppno() != null) {
									params.put("appno", biRpt.getAppno());
									params.put("cifno", biRpt.getCifno());
									params.put("invsttype", "BI");
									Integer a = (Integer) dbService.executeUniqueSQLQuery(
											"SELECT COUNT(*) FROM Tbbirequest WHERE appno=:appno AND cifno=:cifno AND status IN ('0','1','2','6')",
											params);
									if (a == null || (a != null && a == 0)) {
										// Completed
										dbService.executeUpdate(
												"UPDATE Tbinvestigationinst SET status='3' WHERE appno=:appno AND cifno=:cifno AND investigationtype=:invsttype",
												params);
									} else {
										// On-Process
										dbService.executeUpdate(
												"UPDATE Tbinvestigationinst SET status='2' WHERE appno=:appno AND cifno=:cifno AND investigationtype=:invsttype",
												params);
									}
								}
							}

						}
					}
				}

				// Credit Investigation
				if (type.equals("CI")) {
					Tbcireportmain ciRpt = (Tbcireportmain) dbService
							.executeUniqueHQLQuery("FROM Tbcireportmain WHERE cireportid=:reportid", params);
					if (ciRpt != null) {
						// Reviewed - 3
						ciRpt.setStatus("3");
						ciRpt.setStatusdatetime(new Date());
						ciRpt.setReviewedby(username);
						ciRpt.setDatereviewed(new Date());
						if (dbService.saveOrUpdate(ciRpt)) {
							AuditLog.addAuditLog(
									AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("DONE CI REPORT REVIEW",
											AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL)),
									"User " + username + " Reviewed CI Report.", username, new Date(),
									AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL);
							flag = "success";

							// Completed - 5
							params.put("cirequestid", ciRpt.getCirequestid());
							int res = dbService.executeUpdate(
									"UPDATE Tbcirequest SET status='5', statusdate=GETDATE() WHERE cirequestid=:cirequestid",
									params);
							if (res > 0) {
								// Update status (Tbinvestigationinst) Inside Application
								if (ciRpt.getAppno() != null) {
									params.put("appno", ciRpt.getAppno());
									params.put("cifno", ciRpt.getCifno());
									params.put("invsttype", "CI");
									Integer a = (Integer) dbService.executeUniqueSQLQuery(
											"SELECT COUNT(*) FROM Tbcirequest WHERE appno=:appno AND cifno=:cifno AND status IN ('0','1','2','6')",
											params);
									if (a == null || (a != null && a == 0)) {
										// Completed
										dbService.executeUpdate(
												"UPDATE Tbinvestigationinst SET status='3' WHERE appno=:appno AND cifno=:cifno AND investigationtype=:invsttype",
												params);
									} else {
										// On-Process
										dbService.executeUpdate(
												"UPDATE Tbinvestigationinst SET status='2' WHERE appno=:appno AND cifno=:cifno AND investigationtype=:invsttype",
												params);
									}
								}
							}

						}
					}
				}

				// Appraisal
				if (type.equals("CA")) {
					Tbappraisalreportmain apprRpt = (Tbappraisalreportmain) dbService.executeUniqueHQLQuery(
							"FROM Tbappraisalreportmain WHERE appraisalreportid=:reportid", params);
					if (apprRpt != null) {
						// Reviewed - 3
						apprRpt.setStatus("3");
						apprRpt.setStatusdatetime(new Date());
						apprRpt.setReviewedby(username);
						apprRpt.setDatereviewed(new Date());
						CollateralServiceImpl c = new CollateralServiceImpl();
						c.saveAppraisalValues(apprRpt);
						if (dbService.saveOrUpdate(apprRpt)) {
							AuditLog.addAuditLog(
									AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("DONE CA REPORT REVIEW",
											AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL)),
									"User " + username + " Reviewed CA Report.", username, new Date(),
									AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL);
							flag = "success";

							// Completed - 5
							params.put("colappraisalrequestid", apprRpt.getColappraisalrequestid());
							int res = dbService.executeUpdate(
									"UPDATE Tbcolappraisalrequest SET status='5', statusdate=GETDATE() WHERE colappraisalrequestid=:colappraisalrequestid",
									params);
							if (res > 0) {
								// Update status (Tbinvestigationinst) Inside Application
								if (apprRpt.getAppno() != null) {
									params.put("appno", apprRpt.getAppno());
									params.put("cifno", apprRpt.getCifno());
									params.put("invsttype", "CA");
									params.put("refno", apprRpt.getReferenceno());
									Integer a = (Integer) dbService.executeUniqueSQLQuery(
											"SELECT COUNT(*) FROM Tbcolappraisalrequest WHERE appno=:appno AND status IN ('0','1','2','6')",
//											"SELECT COUNT(*) FROM Tbcolappraisalrequest WHERE appno=:appno AND cifno=:cifno AND status IN ('0','1','2','6')",
											params);
									if (a == null || (a != null && a == 0)) {
										// Completed
										dbService.executeUpdate(
												"UPDATE Tbcolinvestigationinst SET status='3' WHERE appno=:appno AND cifno=:cifno AND investigationtype=:invsttype AND referenceno=:refno",
												params);
									} else {
										// On-Process
										dbService.executeUpdate(
												"UPDATE Tbcolinvestigationinst SET status='2' WHERE appno=:appno AND cifno=:cifno AND investigationtype=:invsttype AND referenceno=:refno",
												params);
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
		return flag;
	}

	@Override
	public ReturnForm validateRequest(RequestValidationForm requestform) {
		ReturnForm form = new ReturnForm();
		form.setFlag("success");
		Map<String, Object> params = HQLUtil.getMap();
		try {
			StringBuilder str = new StringBuilder();
			Integer res = 0;
			String queryExt = "";
			if (requestform.getCifno() != null && requestform.getInvestigationtype() != null) {

				// If Inside App
				if (requestform.getAppno() != null && !requestform.equals("---")) {
					params.put("appno", requestform.getAppno());
					queryExt = " AND req.appno=:appno";
				}

				params.put("cifno", requestform.getCifno());

				/*
				 * REQUESTSTATUS: 0 New 1 Instruction Opened 2 On-Process 3 Completed 4 On-Hold
				 * 5 Skipped 6 Waived 7 Cancelled
				 */

				// >>>>>>>>>>>>>>>>>>> BI <<<<<<<<<<<<<<<<<<<
				if (requestform.getInvestigationtype().equals("BI")) {
					// BAP
					if (requestform.getBap() != null && requestform.getBap()) {
						res = (Integer) dbService.executeUniqueSQLQuery("SELECT COUNT(*) "
								+ "FROM Tbbirequest req LEFT JOIN TBBIREPORTMAIN rep on req.birequestid = rep.birequestid WHERE req.status NOT IN('5') "
								+ "AND req.cifno=:cifno AND isbaprequired='1' and rep.status NOT IN ('3')" + queryExt,
								params);
						if (res > 0) {
							form.setFlag("failed");
							str.append("<li>Pending <b>BAP</b> request detected.</li>");
						}
					}
					// CMAP
					if (requestform.getCmap() != null && requestform.getCmap()) {
						res = (Integer) dbService.executeUniqueSQLQuery("SELECT COUNT(*) "
								+ "FROM Tbbirequest req LEFT JOIN TBBIREPORTMAIN rep on req.birequestid = rep.birequestid WHERE req.status NOT IN('5') "
								+ "AND req.cifno=:cifno AND iscmaprequired='1' and rep.status NOT IN ('3')" + queryExt,
								params);
						if (res > 0) {
							form.setFlag("failed");
							str.append("<li>Pending <b>CMAP</b> request detected.</li>");
						}
					}
					// CIC
					if (requestform.getCic() != null && requestform.getCic()) {
						res = (Integer) dbService.executeUniqueSQLQuery("SELECT COUNT(*) "
								+ "FROM Tbbirequest req LEFT JOIN TBBIREPORTMAIN rep on req.birequestid = rep.birequestid WHERE req.status NOT IN('5') "
								+ "AND req.cifno=:cifno AND iscicrequired='1' and rep.status NOT IN ('3')" + queryExt,
								params);
						if (res > 0) {
							form.setFlag("failed");
							str.append("<li>Pending <b>CIC</b> request detected.</li>");
						}
					}
					// Removed for COOP (Ced 1082019
					// BLACKLIST
//					if(requestform.getBlacklist() != null && requestform.getBlacklist()){
//						res = (Integer) dbService.executeUniqueSQLQuery("SELECT COUNT(*) FROM Tbbirequest WHERE status NOT IN(" + status + ") AND cifno=:cifno AND isblacklistrequired='1'" + queryExt, params);
//						if(res > 0){
//							form.setFlag("failed");
//							str.append("<li>Pending <b>BLACKLIST</b> request detected.</li>");
//						}
//					}
					// AMLA
//					if(requestform.getAmla() != null && requestform.getAmla()){
//						res = (Integer) dbService.executeUniqueSQLQuery("SELECT COUNT(*) FROM Tbbirequest WHERE status NOT IN(" + status + ") AND cifno=:cifno AND isamlawatchlistrequired='1'" + queryExt, params);
//						if(res > 0){
//							form.setFlag("failed");
//							str.append("<li>Pending <b>AMLA</b> request detected.</li>");
//						}
//					}
				}

				// >>>>>>>>>>>>>>>>>>> CI <<<<<<<<<<<<<<<<<<<
				if (requestform.getInvestigationtype().equals("CI")) {
					// PDRN
					if (requestform.getPdrn() != null && requestform.getPdrn()) {
						res = (Integer) dbService.executeUniqueSQLQuery("SELECT COUNT(*) "
								+ "FROM Tbcirequest req LEFT JOIN TBCIREPORTMAIN rep on req.cirequestid = rep.cirequestid WHERE req.status NOT IN('5') "
								+ "AND req.cifno=:cifno AND ispdrn='1' and rep.status NOT IN ('3')" + queryExt, params);
						if (res > 0) {
							form.setFlag("failed");
							str.append("<li>Pending <b>PDRN</b> request detected.</li>");
						}
					}
					// EVR
					if (requestform.getEvr() != null && requestform.getEvr()) {
						res = (Integer) dbService.executeUniqueSQLQuery("SELECT COUNT(*) "
								+ "FROM Tbcirequest req LEFT JOIN TBCIREPORTMAIN rep on req.cirequestid = rep.cirequestid WHERE req.status NOT IN('5') "
								+ "AND req.cifno=:cifno AND isevr='1' and rep.status NOT IN ('3')" + queryExt, params);
						if (res > 0) {
							form.setFlag("failed");
							str.append("<li>Pending <b>EVR</b> request detected.</li>");
						}
					}
					// BVR
					if (requestform.getBvr() != null && requestform.getBvr()) {
						res = (Integer) dbService.executeUniqueSQLQuery("SELECT COUNT(*) "
								+ "FROM Tbcirequest req LEFT JOIN TBCIREPORTMAIN rep on req.cirequestid = rep.cirequestid WHERE req.status NOT IN('5') "
								+ "AND req.cifno=:cifno AND isbvr='1' and rep.status NOT IN ('3')" + queryExt, params);
						if (res > 0) {
							form.setFlag("failed");
							str.append("<li>Pending <b>BVR</b> request detected.</li>");
						}
					}
					// BANK CHECK
					if (requestform.getBankcheck() != null && requestform.getBankcheck()) {
						res = (Integer) dbService.executeUniqueSQLQuery("SELECT COUNT(*) "
								+ "FROM Tbcirequest req LEFT JOIN TBCIREPORTMAIN rep on req.cirequestid = rep.cirequestid WHERE req.status NOT IN('5') "
								+ "AND req.cifno=:cifno AND isbankcheck='1' and rep.status NOT IN ('3')" + queryExt,
								params);
						if (res > 0) {
							form.setFlag("failed");
							str.append("<li>Pending <b>BANK CHECK</b> request detected.</li>");
						}
					}
					// CREDIT CHECK
					if (requestform.getCreditcheck() != null && requestform.getCreditcheck()) {
						res = (Integer) dbService.executeUniqueSQLQuery("SELECT COUNT(*) "
								+ "FROM Tbcirequest req LEFT JOIN TBCIREPORTMAIN rep on req.cirequestid = rep.cirequestid WHERE req.status NOT IN('5') "
								+ "AND req.cifno=:cifno AND iscreditcheck='1' and rep.status NOT IN ('3')" + queryExt,
								params);
						if (res > 0) {
							form.setFlag("failed");
							str.append("<li>Pending <b>CREDIT CHECK</b> request detected.</li>");
						}
					}
					// Removed for COOP (Ced 1082019
					// TRADE CHECK
//					if(requestform.getTradecheck() != null && requestform.getTradecheck()){
//						res = (Integer) dbService.executeUniqueSQLQuery("SELECT COUNT(*) FROM Tbcirequest WHERE status NOT IN(" + status + ") AND cifno=:cifno AND istradecheck='1'" + queryExt, params);
//						if(res > 0){
//							form.setFlag("failed");
//							str.append("<li>Pending <b>TRADE CHECK</b> request detected.</li>");
//						}
//					}
				} else {
					// CA VALIDATION
					params.put("refno", requestform.getInvestigationtype());
					res = (Integer) dbService.executeUniqueSQLQuery("SELECT COUNT(*) "
							+ "FROM TBCOLAPPRAISALREQUEST req LEFT JOIN TBAPPRAISALREPORTMAIN rep on req.referenceno = rep.referenceno "
							+ "WHERE req.status NOT IN('5') and rep.status NOT IN ('3') and req.referenceno =:refno"
							+ queryExt, params);
					if (res > 0) {
						form.setFlag("failed");
						str.append("<li>Pending request detected.</li>");
					}
				}

				form.setMessage(str.toString());
			}
		} catch (Exception e) {
			form.setFlag("failed");
			e.printStackTrace();
		}
		return form;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InvestigationForm> getInvestigationListCASingle(String appno, String investigationtype,
			String collateralcategory) {
		List<InvestigationForm> form = new ArrayList<InvestigationForm>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("appno", appno);
//			params.put("investntype", investigationtype);
//			params.put("collateralcategory", "S");

//			String query = "SELECT a.id, a.appno, a.cifno,a.customername,a.investigationtype,instruction,"
//					+ "(SELECT fullname FROM Tbuser WHERE username=a.initiatedby) AS initiatedby, a.aoremarks,a.supervisorremarks,"
//					+ "a.datecreated,a.updatedby,a.dateupdated,a.supervisor, (SELECT desc1 FROM Tbcodetable WHERE codename='GROUPTYPE' AND codevalue=a.grouptype)AS grouptype,"
//					+ "(SELECT desc1 FROM Tbcodetable WHERE codename='COLLATERALTYPE' AND codevalue=a.collateraltype) AS collateraltype,a.colid,"
//					+ "a.customertype, a.status, a.lastrecorddate,a.participationcode, a.referenceno"
//					+ ",(SELECT desc1 FROM Tbcodetable WHERE codename='PROPERTYTYPE' AND codevalue= b.propertytype) as propertytype "
//					+ ",(SELECT desc1 FROM Tbcodetable WHERE codename='NEWUSED' AND codevalue=(SELECT neworused FROM TBCOLLATERALAUTO WHERE referenceno=a.referenceno)) as neworused "
//					+ "FROM Tbcolinvestigationinst a LEFT JOIN TBCOLLATERALREL b ON b.referenceno = a.referenceno WHERE a.appno=:appno AND a.investigationtype=:investntype and a.collateralcategory=:collateralcategory";

			String query = "SELECT a.id, a.appno, a.cifno,a.customername,a.investigationtype,instruction,"
					+ " e.fullname AS initiatedby,"
					+ " a.aoremarks,a.supervisorremarks, a.datecreated, a.updatedby, a.dateupdated, a.supervisor,"
					+ " c.desc1 as grouptype," + " d.desc1 as collateraltype,"
					+ " a.colid, a.customertype, a.status, a.lastrecorddate, a.participationcode, a.referenceno,"
					+ " (SELECT desc1 FROM Tbcodetable WHERE codename='PROPERTYTYPE' AND codevalue= b.propertytype) as propertytype,"
					+ " f.desc1 as neworused" + " FROM Tbcolinvestigationinst a"
					+ " LEFT JOIN TBCOLLATERALREL b ON b.referenceno = a.referenceno"
					+ " LEFT JOIN Tbcodetable c ON c.codevalue = a.grouptype  AND c.codename='GROUPTYPE'"
					+ " LEFT JOIN Tbcodetable d ON d.codevalue = a.collateraltype  AND d.codename='COLLATERALTYPE'"
					+ " LEFT JOIN Tbuser e ON e.username = a.initiatedby"
					+ " LEFT JOIN Tbcodetable f ON f.codevalue = (SELECT neworused FROM TBCOLLATERALAUTO WHERE referenceno=a.referenceno) AND f.codename='NEWUSED'"
					+ " WHERE a.appno=:appno AND a.investigationtype='CA' and a.collateralcategory='S'";

			form = (List<InvestigationForm>) dbService.execSQLQueryTransformer(query, params, InvestigationForm.class,
					1);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return form;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InvestigationForm> getInvestigationListCAGroup(String appno, String investigationtype,
			String collateralcategory) {
		List<InvestigationForm> form = new ArrayList<InvestigationForm>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("appno", appno);
			String query = "SELECT a.id, a.appno, a.cifno,a.customername,a.investigationtype,instruction,"
					+ " e.fullname AS initiatedby,"
					+ " a.aoremarks,a.supervisorremarks, a.datecreated, a.updatedby, a.dateupdated, a.supervisor,"
					+ " c.desc1 as grouptype," + " d.desc1 as collateraltype,"
					+ " a.colid, a.customertype, a.status, a.lastrecorddate, a.participationcode, a.referenceno,"
					+ " (SELECT desc1 FROM Tbcodetable WHERE codename='PROPERTYTYPE' AND codevalue= b.propertytype) as propertytype,"
					+ " f.desc1 as neworused" + " FROM Tbcolinvestigationinst a"
					+ " LEFT JOIN TBCOLLATERALREL b ON b.referenceno = a.referenceno"
					+ " LEFT JOIN Tbcodetable c ON c.codevalue = a.grouptype  AND c.codename='GROUPTYPE'"
					+ " LEFT JOIN Tbcodetable d ON d.codevalue = a.collateraltype  AND d.codename='COLLATERALTYPE'"
					+ " LEFT JOIN Tbuser e ON e.username = a.initiatedby"
					+ " LEFT JOIN Tbcodetable f ON f.codevalue = (SELECT neworused FROM TBCOLLATERALAUTO WHERE referenceno=a.referenceno) AND f.codename='NEWUSED'"
					+ " WHERE a.appno=:appno AND a.investigationtype='CA' and a.collateralcategory='G'";
			form = (List<InvestigationForm>) dbService.execSQLQueryTransformer(query, params, InvestigationForm.class,
					1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return form;
	}

}
