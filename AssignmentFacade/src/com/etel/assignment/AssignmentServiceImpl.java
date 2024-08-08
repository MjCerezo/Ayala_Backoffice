package com.etel.assignment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cifsdb.data.Tbcifmain;
import com.coopdb.data.Tbappraisalreportmain;
import com.coopdb.data.Tbbranch;
import com.coopdb.data.Tbcoa;
import com.coopdb.data.Tbcodetable;
import com.coopdb.data.Tbmember;
import com.coopdb.data.Tbmembershipapp;
import com.coopdb.data.Tbuser;
import com.etel.assignment.forms.BIReportAssignmentForm;
import com.etel.assignment.forms.CIFAssignmentForm;
import com.etel.assignment.forms.CIReportAssignmentForm;
import com.etel.assignment.forms.UnassignedAppReview;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.dataentry.FullDataEntryServiceImpl;
import com.etel.defaultusers.forms.DefaultUsers;
import com.etel.forms.TblstappForm;
import com.etel.utils.AuditLog;
import com.etel.utils.AuditLogEvents;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;
import com.etel.workflow.WorkflowServiceImpl;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class AssignmentServiceImpl implements AssignmentService {

	private DBService dbService = new DBServiceImpl();
	private Map<String, Object> param = HQLUtil.getMap();
	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private String username = secservice.getUserName();

	@SuppressWarnings("unchecked")
	@Override
	public List<UnassignedAppReview> listUnassignedAppReview(Integer page, Integer maxresult) {
		List<UnassignedAppReview> list = new ArrayList<UnassignedAppReview>();
		// String usr = UserUtil.securityService.getUserName();
		try {
			list = (List<UnassignedAppReview>) dbService
					.execSQLQueryTransformer(
							"SELECT " + "CONCAT(lastname,', ',firstname,' ',middlename)as name, " + "membershipappid, "
									+ "applicationdate, " + "encodedby, " + "encodeddate "
									+ "FROM Tbmembershipapp WHERE membershipappstatus = '2' "
									+ "AND assignedto IS NULL " + "ORDER BY encodeddate OFFSET (" + page + " - 1)*("
									+ maxresult + ") ROWS FETCH NEXT " + maxresult + " ROWS ONLY",
							null, UnassignedAppReview.class, 1);
			if (list != null) {
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer listUnassignedAppReviewTotal() {
		try {
			// param.put("encoder", secservice.getUserName());
			// Integer count = (Integer) dbService.executeUniqueSQLQuery(
			// "SELECT COUNT(membershipappid) FROM TBMEMBERSHIPAPP m WHERE
			// membershipappstatus = '2' AND assignedto IS NULL", null);
			// if (count != null)
			// return count;
			Integer count = (Integer) dbService.executeUniqueSQLQuery(
					"SELECT COUNT(membershipappid) FROM TBMEMBERSHIPAPP m WHERE membershipappstatus = '2' AND assignedto IS NULL",
					null);
			if (count != null)
				return count;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public String grabUnassignedApp(String memappid) {
		try {
			param.put("memappid", memappid);
			Tbmembershipapp app = (Tbmembershipapp) dbService
					.executeUniqueHQLQuery("FROM Tbmembershipapp WHERE membershipappid=:memappid", param);
			if (app != null) {
				app.setAssignedto(UserUtil.securityService.getUserName());
				app.setAssigneddate(new Date());
				if (dbService.saveOrUpdate(app)) {
					AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_ASSIGN_APPLICATION),
							"User " + username + " Grab " + app.getMembershipappid() + "'s Membership Application.",
							username, new Date(), AuditLogEvents.getEventModule(AuditLogEvents.M_ASSIGN_APPLICATION));
					return "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public String batchApproval(String[] forapproval, String approvalstage, String approvalstatus, String remarks,
			String boardbatchremarks, String boardbatchresno) {
		ArrayList<String> unproccess = new ArrayList<String>();
		try {
			boolean approved = false;
			boolean declined = false;
			boolean deferred = false;
			if (forapproval == null) {
				return "Please select a record to proccess.";
			}
			if (approvalstage.equals("INITIAL APPROVAL")) {
				// INITIAL APPROVAL
				for (String s : forapproval) {
					param.put("memappid", s);
					Tbmembershipapp a = (Tbmembershipapp) dbService
							.executeUniqueHQLQuery("FROM Tbmembershipapp WHERE membershipappid=:memappid", param);
					if (a != null) {
						// saving membership TBMEMBER..
						FullDataEntryServiceImpl savingmember = new FullDataEntryServiceImpl();
						savingmember.saveMembership(a);
						a.setEdcomapprover(UserUtil.securityService.getUserName());
						a.setEdcomapproverremarks(remarks);
						a.setEdcomappstatusdate(new Date());
						if (!approvalstatus.equals("8") || !approvalstatus.equals("9")) {
							// since it's default for board approval stage = 7 in client side
							// set status to "payment" = 4
							approvalstatus = "4";
							approved = true;
						} else {
							if (approvalstatus.equals("8")) {
								declined = true;
							}
							if (approvalstatus.equals("9")) {
								deferred = true;
							}
						}
						a.setMembershipappstatus(approvalstatus);
						if (dbService.saveOrUpdate(a)) {
							if (approved) {
								AuditLog.addAuditLog(
										AuditLogEvents
												.getAuditLogEvents(AuditLogEvents.M_APPROVED_APPLICATION_APPROVAL),
										"User " + UserUtil.securityService.getUserName() + " Approved "
												+ a.getMembershipappid() + "'s Membership Application.",
										UserUtil.securityService.getUserName(), new Date(),
										AuditLogEvents.getEventModule(AuditLogEvents.M_APPROVED_APPLICATION_APPROVAL));
							}
							if (deferred) {
								AuditLog.addAuditLog(
										AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_DEFER_APPLICATION_APPROVAL),
										"User " + UserUtil.securityService.getUserName() + " Deferred "
												+ a.getMembershipappid() + "'s Membership Application.",
										UserUtil.securityService.getUserName(), new Date(),
										AuditLogEvents.getEventModule(AuditLogEvents.M_DEFER_APPLICATION_APPROVAL));
							}
							if (declined) {
								AuditLog.addAuditLog(
										AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_DECLINE_APPLICATION_APPROVAL),
										"User " + UserUtil.securityService.getUserName() + " Declined "
												+ a.getMembershipappid() + "'s Membership Application.",
										UserUtil.securityService.getUserName(), new Date(),
										AuditLogEvents.getEventModule(AuditLogEvents.M_DECLINE_APPLICATION_APPROVAL));
							}
							WorkflowServiceImpl.UpdateStatus(a.getMembershipappstatus(), a.getMembershipappid());
						} else {
							unproccess.add(s);
						}
					}
				}
			} else if (approvalstage.equals("BOARD APPROVAL")) {
				// BOARD APPROVAL
				for (String s : forapproval) {
					param.put("memappid", s);
					Tbmembershipapp a = (Tbmembershipapp) dbService
							.executeUniqueHQLQuery("FROM Tbmembershipapp WHERE membershipappid=:memappid", param);
					if (a != null) {
						a.setBoardapprover(UserUtil.securityService.getUserName());
						a.setBoardresno(boardbatchresno);
						a.setBoardapproverremarks(boardbatchremarks);
						a.setBoardappstatusdate(new Date());
						a.setBoardapprovalstatus("1");
						a.setMembershipappstatus(approvalstatus);
//						dbService.saveOrUpdate(a);
//						System.out.println("Application for " + a.getMembershipappid() + " approved..");
						System.out.println("board: " + approvalstatus);
						if (approvalstatus.equals("7")) {
							approved = true;
						}
						if (approvalstatus.equals("8")) {
							declined = true;
						}
						if (approvalstatus.equals("9")) {
							deferred = true;
						}
						if (dbService.saveOrUpdate(a)) {
							if (approved) {
								AuditLog.addAuditLog(
										AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_APPROVED_MEMBER_BOARD),
										"User " + UserUtil.securityService.getUserName() + " Approved "
												+ a.getMembershipappid() + "'s Membership Application.",
										UserUtil.securityService.getUserName(), new Date(),
										AuditLogEvents.getEventModule(AuditLogEvents.M_APPROVED_MEMBER_BOARD));
							}
//							if (deferred) {
//								AuditLog.addAuditLog(
//										AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_DEFER_APPLICATION_APPROVAL),
//										"User " + UserUtil.securityService.getUserName() + " Deferred "
//												+ a.getMembershipappid() + "'s Membership Application.",
//										UserUtil.securityService.getUserName(), new Date(),
//										AuditLogEvents.getEventModule(AuditLogEvents.M_DEFER_APPLICATION_APPROVAL));
//							}
//							if (declined) {
//								AuditLog.addAuditLog(
//										AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_DECLINE_APPLICATION_APPROVAL),
//										"User " + UserUtil.securityService.getUserName() + " Declined "
//												+ a.getMembershipappid() + "'s Membership Application.",
//										UserUtil.securityService.getUserName(), new Date(),
//										AuditLogEvents.getEventModule(AuditLogEvents.M_DECLINE_APPLICATION_APPROVAL));
//							}
							WorkflowServiceImpl.UpdateStatus(a.getMembershipappstatus(), a.getMembershipappid());
						} else {
							unproccess.add(s);
						}
					}
				}
			} else if (approvalstage.equals("PAYMENT")) {
				// PAYMENT
				for (String s : forapproval) {
					param.put("memappid", s);
					Tbmembershipapp a = (Tbmembershipapp) dbService
							.executeUniqueHQLQuery("FROM Tbmembershipapp WHERE membershipappid=:memappid", param);
					if (a != null) {
						a.setCashier(UserUtil.securityService.getUserName());
						a.setPaymentapprovaldate(new Date());
						// for recommendation = '4'
						a.setMembershipappstatus("5");
						if (!dbService.saveOrUpdate(a)) {
							unproccess.add(s);
						} else if (dbService.saveOrUpdate(a)) {
							WorkflowServiceImpl.UpdateStatus(a.getMembershipappstatus(), a.getMembershipappid());
						}
					}
				}
			} else if (approvalstage.equals("RECOMMENDATION")) {
				// RECOMMENDATION
				for (String s : forapproval) {
					param.put("memappid", s);
					Tbmembershipapp a = (Tbmembershipapp) dbService
							.executeUniqueHQLQuery("FROM Tbmembershipapp WHERE membershipappid=:memappid", param);
					if (a != null) {
						a.setRecommendedby(UserUtil.securityService.getUserName());
						a.setRecommendationdate(new Date());
						a.setMembershipappstatus("6");
						a.setRemarks(remarks);
						if (!dbService.saveOrUpdate(a)) {
							unproccess.add(s);
						} else if (dbService.saveOrUpdate(a)) {
							AuditLog.addAuditLog(
									AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_SUBMIT_RECOMMENDATION),
									"User " + UserUtil.securityService.getUserName() + " Submitted "
											+ a.getMembershipappid() + "'s Membership Application for Board Approval.",
									UserUtil.securityService.getUserName(), new Date(),
									AuditLogEvents.getEventModule(AuditLogEvents.M_SUBMIT_RECOMMENDATION));
							WorkflowServiceImpl.UpdateStatus(a.getMembershipappstatus(), a.getMembershipappid());
						}
					}
				}
			}
			if (unproccess.isEmpty()) {
				return "success";
			} else {
				return "Problem updating: <b>" + unproccess.toArray().toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	/**
	 * Get getMyAssignment (Application) (Stored Procedure)
	 * 
	 * @return List <{@link TblstappForm}>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TblstappForm> getMyAssignmentAppList(String search, Integer page, Integer maxResult) {
		List<TblstappForm> lstapp = new ArrayList<TblstappForm>();
		Map<String, Object> params = HQLUtil.getMap();
		String username = UserUtil.securityService.getUserName();
		try {
			params.put("username", username);
			params.put("search", search);
			lstapp = (List<TblstappForm>) dbService.execSQLQueryTransformer(
					"EXEC sp_GetMyAssignmentAppList @username=:username, @cifname=:search, @page =" + page
							+ ", @maxresult =" + maxResult + ", @gettotalcount = 'false'",
					params, TblstappForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstapp;
	}

	/**
	 * Get getMyAssignment total for pagination (Stored Procedure)
	 * 
	 * @return Integer = totalcount
	 */
	@Override
	public Integer getMyAssignmentAppTotal(String search) {
		Integer total = 0;
		Map<String, Object> params = HQLUtil.getMap();
		String username = UserUtil.securityService.getUserName();
		try {
			params.put("username", username);
			params.put("search", search);
			total = (Integer) dbService.executeUniqueSQLQuery(
					"EXEC sp_GetMyAssignmentAppList @username=:username, @cifname=:search, @page = 0, @maxresult = 0, @gettotalcount = 'true'",
					params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BIReportAssignmentForm> getMyAssignmentBiReportList(String search, Integer page, Integer maxResult) {
		List<BIReportAssignmentForm> bireport = new ArrayList<BIReportAssignmentForm>();
		Map<String, Object> params = HQLUtil.getMap();
		String username = UserUtil.securityService.getUserName();
		StringBuilder hql = new StringBuilder();
		DefaultUsers def = new DefaultUsers();
		def = new DefaultUsers("SLA");
		try {
			params.put("username", username);
			params.put("search", search == null ? "%" : "%" + search + "%");

			hql.append(
					"SELECT a.bireportid, a.birequestid, a.appno, a.cifno, a.customername, a.customertype, (SELECT fullname FROM TBUSER WHERE username=a.reportedby) as reportedby, ");
			hql.append(
					"(SELECT fullname FROM TBUSER WHERE username=a.requestedby) as requestedby, (SELECT fullname FROM TBUSER WHERE username=a.reviewedby) as reviewedby, ");
			hql.append(
					"(SELECT desc1 FROM TBCODETABLE WHERE codename='REPORTSTATUS' AND codevalue=a.status) as status, ");
			hql.append(
					"a.companycode, isbaprequired, iscmaprequired, iscicrequired, isblacklistrequired, isamlawatchlistrequired ");
			hql.append("FROM Tbbireportmain a INNER JOIN TBBIREQUEST b ON a.birequestid=b.birequestid ");
			hql.append(
					"WHERE (a.bireportid like :search OR a.appno like :search OR a.customername like :search) AND ((a.reportedby=:username AND a.status IN ('0','1','4')) OR (((SELECT assignedbisupervisor FROM TBBIREQUEST WHERE birequestid=a.birequestid)='"+def.getBisupervisor()+"') AND (a.status = '2') AND ((SELECT assignedbisupervisor FROM TBBIREQUEST WHERE birequestid=a.birequestid)=:username)))");

			bireport = (List<BIReportAssignmentForm>) dbService.execSQLQueryTransformerListPagination(hql.toString(),
					params, BIReportAssignmentForm.class, page, maxResult);
			
			System.out.println(">>>>>>>>>>>>> hql.toString() : " + hql.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bireport;
	}

	@Override
	public Integer getMyAssignmentBiReportTotal(String search) {
		Integer total = 0;
		Map<String, Object> params = HQLUtil.getMap();
		String username = UserUtil.securityService.getUserName();
		StringBuilder hql = new StringBuilder();
		try {
			params.put("username", username);
			params.put("search", search == null ? "%" : "%" + search + "%");
			hql.append(
					"SELECT COUNT(*) FROM Tbbireportmain a WHERE (a.bireportid like :search OR a.appno like :search OR a.customername like :search) AND ((a.reportedby=:username AND a.status IN ('0','1','4')) OR (a.reviewedby=:username AND a.status = '2'))");

			total = (Integer) dbService.executeUniqueSQLQuery(hql.toString(), params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CIReportAssignmentForm> getMyAssignmentCiReportList(String search, Integer page, Integer maxResult) {
		List<CIReportAssignmentForm> cireport = new ArrayList<CIReportAssignmentForm>();
		Map<String, Object> params = HQLUtil.getMap();
		String username = UserUtil.securityService.getUserName();
		StringBuilder hql = new StringBuilder();
		try {
			params.put("username", username);
			params.put("search", search == null ? "%" : "%" + search + "%");

			hql.append(
					"SELECT a.cireportid, a.cirequestid, a.appno, a.cifno, a.customername, a.customertype, (SELECT fullname FROM TBUSER WHERE username=a.reportedby) as reportedby, ");
			hql.append(
					"(SELECT fullname FROM TBUSER WHERE username=a.requestedby) as requestedby, (SELECT fullname FROM TBUSER WHERE username=a.reviewedby) as reviewedby, ");
			hql.append(
					"(SELECT desc1 FROM TBCODETABLE WHERE codename='REPORTSTATUS' AND codevalue=a.status) as status,  a.companycode, ");
			hql.append(
					"(SELECT desc1 FROM TBCODETABLE WHERE codename='CITYPE' AND codevalue=b.citype) as citype, ispdrn, isevr, isbvr, istradecheck, isbankcheck, iscreditcheck ");
			hql.append("FROM Tbcireportmain a INNER JOIN TBCIREQUEST b ON a.cirequestid=b.cirequestid ");
			hql.append(
					"WHERE (a.cireportid like :search OR a.appno like :search OR a.customername like :search) AND ((a.reportedby=:username AND a.status IN ('0','1','4')) OR (a.reviewedby=:username AND a.status = '2'))");

			cireport = (List<CIReportAssignmentForm>) dbService.execSQLQueryTransformerListPagination(hql.toString(),
					params, CIReportAssignmentForm.class, page, maxResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cireport;
	}

	@Override
	public Integer getMyAssignmentCiReportTotal(String search) {
		Integer total = 0;
		Map<String, Object> params = HQLUtil.getMap();
		String username = UserUtil.securityService.getUserName();
		StringBuilder hql = new StringBuilder();
		try {
			params.put("username", username);
			params.put("search", search == null ? "%" : "%" + search + "%");
			hql.append(
					"SELECT COUNT(*) FROM Tbcireportmain a WHERE (a.cireportid like :search OR a.appno like :search OR a.customername like :search) AND ((a.reportedby=:username AND a.status IN ('0','1','4')) OR (a.reviewedby=:username AND a.status = '2'))");

			total = (Integer) dbService.executeUniqueSQLQuery(hql.toString(), params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	// JAY 06-06-18
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbappraisalreportmain> getMyAssignmentCaReportList(String search, Integer page, Integer maxResult) {
		// TODO Auto-generated method stub
		List<Tbappraisalreportmain> careport = new ArrayList<Tbappraisalreportmain>();
		Map<String, Object> params = HQLUtil.getMap();
		String username = UserUtil.securityService.getUserName();
		StringBuilder hql = new StringBuilder();
		try {
			params.put("username", username);
			params.put("search", search == null ? "%" : "%" + search + "%");

			hql.append(
					"SELECT a.appraisalreportid, a.colappraisalrequestid, a.appno, a.referenceno,(SELECT desc1 from Tbcodetable WHERE codename='TYPEAPPRAISAL' and codevalue=a.typeappraisal) as typeappraisal, ");
			hql.append(
					"(SELECT desc1 from Tbcodetable WHERE codename='REPORTSTATUS' AND codevalue=a.status) as status,(SELECT desc1 from Tbcodetable WHERE codename='COLLATERALTYPE' AND codevalue=a.collateraltype) as collateraltype,  ");
			hql.append(
					"(SELECT fullname FROM Tbuser WHERE username=a.reportedby) as reportedby, a.statusdatetime, a.reportdate, a.reasonforappraisal, a.companycode, "
							+ "(SELECT desc1 FROM Tbcodetable WHERE codename='NEWUSED' AND codevalue=(SELECT neworused FROM TBCOLLATERALAUTO WHERE referenceno=a.referenceno)) as newused, ");
			hql.append(
					"(SELECT desc1 from Tbcodetable WHERE codename='PROPERTYTYPE' AND codevalue=a.propertytype)as propertytype, ");
			hql.append(
					"(SELECT desc1 from Tbcodetable WHERE codename='COLLATERALTYPE' AND codevalue=a.collateraltype)as collateraltype, ");
			hql.append(
					"(SELECT desc1 from Tbcodetable WHERE codename='PROPERTYTYPE' AND codevalue=(SELECT propertytype FROM TBCOLLATERALREL WHERE referenceno =a.referenceno))as propertytype, ");
			hql.append(
					"(SELECT desc1 from Tbcodetable WHERE codename='TYPEAPPRAISAL' AND codevalue=a.typeappraisal)as typeappraisal ");
			hql.append(
					"FROM Tbappraisalreportmain a WHERE (a.appraisalreportid like :search OR a.appno like :search OR a.contactperson like :search) AND ");
			hql.append(
					"((a.reportedby=:username AND a.status IN ('0','1','4')) OR (a.reviewedby=:username AND a.status = '2'))");

			careport = (List<Tbappraisalreportmain>) dbService.execSQLQueryTransformerListPagination(hql.toString(),
					params, Tbappraisalreportmain.class, page, maxResult);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return careport;
	}

	@Override
	public Integer getMyAssignmentCaReportTotal(String search) {
		// TODO Auto-generated method stub
		Integer total = 0;
		Map<String, Object> params = HQLUtil.getMap();
		String username = UserUtil.securityService.getUserName();
		StringBuilder hql = new StringBuilder();

		try {
			params.put("username", username);
			params.put("search", search == null ? "%" : "%" + search + "%");

			hql.append(
					"SELECT COUNT(*) FROM Tbappraisalreportmain a WHERE (a.appraisalreportid like :search OR a.appno like :search OR a.contactperson like :search) AND ");
			// hql.append("((a.reportedby=:username AND a.status IN('0','3')) OR
			// (a.reviewedby=:username AND a.status= '1'))");
			hql.append(
					"((a.reportedby=:username AND a.status IN ('0','1','4')) OR (a.reviewedby=:username AND a.status = '2'))");
			total = (Integer) dbService.executeUniqueSQLQuery(hql.toString(), params);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbmember> searchMember(String search) {
		Map<String, Object> params = HQLUtil.getMap();
		List<Tbmember> list = new ArrayList<Tbmember>();
		params.put("search",search + "%%");
		list = (List<Tbmember>) dbService.executeListHQLQuery("FROM Tbmember where membershipid like:search or membername like:search", params);

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbmember> getAllRecords() {
		List<Tbmember> list = new ArrayList<Tbmember>();
		list = (List<Tbmember>) dbService.executeListHQLQuery("FROM Tbmember", null);

		return list;
	}

	@Override
	public String updateAO(String membershipid,String aocode) {
		String flag = "";
		Map<String, Object> params = HQLUtil.getMap();
		params.put("id", membershipid);
		Tbmember mem = (Tbmember) dbService
				.executeUniqueHQLQuery("FROM Tbmember WHERE membershipid=:id", params);
		if(mem != null)
		{
			if(mem.getNewao() == null)
			{
				mem.setNewao(aocode);
			
			}
			else {
				mem.setAccountofficer(mem.getNewao());
				mem.setNewao(aocode);
			}
			dbService.saveOrUpdate(mem);
			flag = "success";
			return flag;
		}
		
		flag = "failed";
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CIFAssignmentForm listCIFAssignmentPerBranch(String search, Integer page, Integer maxResult) {
		CIFAssignmentForm form = new CIFAssignmentForm();
		DBService dbServiceCoop = new DBServiceImpl();
		DBService dbServiceCIF = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("search", search==null? "%" : "%"+search+"%");
			List<Tbcifmain> list = new ArrayList<Tbcifmain>();
			
			params.put("username", secservice.getUserName());
			Tbuser user =(Tbuser) dbServiceCoop.executeUniqueHQLQuery("FROM Tbuser WHERE username=:username", params); // get users branch code
			
			if(user!=null && user.getBranchcode()!=null) {
				params.put("branch",user.getBranchcode());
				String hqlQuery = "FROM Tbcifmain WHERE originatingbranch=:branch and cifstatus IN ('1','2','4') AND (fullname like :search OR cifno like :search)";
				
				list = (List<Tbcifmain>) dbServiceCIF.executeListHQLQueryWithFirstAndMaxResults(hqlQuery, params, page, maxResult);
				if(list != null){ 
					for(Tbcifmain c : list){
						if(c.getCifstatus() != null){	
							params.put("status", c.getCifstatus());
							Tbcodetable cifStatus = (Tbcodetable) dbServiceCoop.executeUniqueHQLQuery("FROM Tbcodetable a WHERE a.id.codename ='CIFSTATUS' AND a.id.codevalue =:status", params);
							if(cifStatus!=null){
								c.setCifstatus(cifStatus.getDesc1());
							}
						}
						if(c.getOriginatingbranch() !=null) {
							params.put("branch", c.getOriginatingbranch());
							Tbbranch branch = (Tbbranch) dbServiceCoop.executeUniqueHQLQuery("FROM Tbbranch a WHERE a.branchcode =:branch", params);
							if(branch!=null){
								c.setOriginatingbranch(branch.getBranchname());
							}
						}
					}
					form.setMain(list);
					List<Tbcifmain> count = (List<Tbcifmain>) dbServiceCIF.executeListHQLQuery(hqlQuery, params);
					if (count != null) {
						form.setResult(count.size());
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public String assignCIFEncoder(String cifno, String cifencoder) {
		String flag = "failed";
		//DBService dbServiceCoop = new DBServiceImpl();
		DBService dbServiceCIF = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(cifno!=null && cifencoder!=null) {
				params.put("cifno", cifno);
				
				Tbcifmain row = (Tbcifmain) dbServiceCIF.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno=:cifno", params);
				if (row!=null) {
					row.setAssignedto(cifencoder);
					
					row.setAssignedby(secservice.getUserName());
					row.setDateassigned(new Date());
					
					if(dbServiceCIF.saveOrUpdate(row)) {
						flag = "success";
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
	public List<Tbuser> getCIFEncoder() {
		List<Tbuser> list = new ArrayList<Tbuser>();
		DBService dbServiceCoop = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("username", secservice.getUserName());
			Tbuser user =(Tbuser) dbServiceCoop.executeUniqueHQLQuery("FROM Tbuser WHERE username=:username", params); // get users branch code
			
			if(user.getBranchcode()!=null) {
				params.put("branchcode", user.getBranchcode());
				
				list = (List<Tbuser>) dbServiceCoop.execSQLQueryTransformer
						("SELECT a.username, a.lastname, a.firstname, a.middlename FROM TBUSER a LEFT JOIN TBUSERROLES b ON a.username=b.username WHERE a.branchcode=:branchcode AND b.roleid = 'CIF_ENCODER'", 
								params, Tbuser.class, 1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
