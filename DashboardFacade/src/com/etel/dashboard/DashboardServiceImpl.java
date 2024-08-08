package com.etel.dashboard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cifsdb.data.Tbcifmain;
import com.coopdb.data.Tbloanfin;
import com.coopdb.data.Tblstapp;
import com.coopdb.data.Tbworkflowprocess;
import com.etel.assignment.AssignmentService;
import com.etel.assignment.AssignmentServiceImpl;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.dashboard.forms.CIFDashboard;
import com.etel.dashboard.forms.DashBoardDocumentsForm;
import com.etel.dashboard.forms.DashBoardForm;
import com.etel.dashboard.forms.DashboardListFormCIF;
import com.etel.dashboard.forms.LoanAppBucket;
import com.etel.dashboard.forms.LoanApplicationAssignments;
import com.etel.dashboard.forms.LoanForm;
import com.etel.dashboard.forms.MembershipOtherBucket;
import com.etel.dashboard.forms.MyDashboard;
import com.etel.dashboard.forms.MyLoans;
import com.etel.dashboard.forms.MyMembership;
import com.etel.dashboard.forms.MyTransactions;
import com.etel.dashboard.forms.ResignationAssignments;
import com.etel.dashboard.forms.ResignationBucket;
import com.etel.dashboard.forms.TMPFrom;
import com.etel.dashboard.forms.TransactionAssignments;
import com.etel.dashboard.forms.UpdateProfileRequestAssignment;
import com.etel.dashboard.forms.UpdateProfileRequestOtherBucket;
import com.etel.dashboard.forms.WorkflowDashboardForm;
import com.etel.inquiry.forms.InquiryForm;
import com.etel.lmsreport.form.LMSExceptionReportForm;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

@SuppressWarnings("unchecked")
public class DashboardServiceImpl implements DashboardService {

	private DBService dbService = new DBServiceImpl();
	private Map<String, Object> params = HQLUtil.getMap();
	public static SecurityService securityService = (SecurityService) RuntimeAccess.getInstance()
			.getServiceBean("securityService");

	@Override
	public List<MyTransactions> listMyTransactions() {

		try {
			params.put("username", UserUtil.securityService.getUserName());
			List<MyTransactions> myDashboardList = (List<MyTransactions>) dbService.execSQLQueryTransformer(
					"EXEC sp_GetTransaction @username=:username", params, MyTransactions.class, 1);
			return myDashboardList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<MyTransactions>();
	}

	@Override
	public List<TransactionAssignments> myTransactionAssignments(String stage, String searchstr, Integer page,
			Integer maxresult) {
		List<TransactionAssignments> list = new ArrayList<TransactionAssignments>();
		List<TransactionAssignments> paid = new ArrayList<TransactionAssignments>();
		try {
			System.out.println(stage);
			String status = "1";
			StringBuilder query = new StringBuilder();
			params.put("stage", stage);
			params.put("username", UserUtil.securityService.getUserName());
			params.put("str", searchstr);

			query.append("SELECT " + "membershipappid, "
					+ "CONCAT (lastname, ', ', firstname, ' ', middlename) as name, " + "applicationdate, "
					+ "(SELECT desc1 FROM TBCODETABLE WHERE codename='MEMBERSHIPSTATUS' and codevalue=membershipstatus) as membershipstatus, "
					+ "(SELECT desc1 FROM TBCODETABLE WHERE codename='SERVICESTATUS' and codevalue=servicestatus) as servicestatus, "
					+ "(SELECT desc1 FROM TBCODETABLE WHERE codename='MEMBERSHIPCLASS' and codevalue=membershipclass) as membershiptype, "
					+ "(SELECT CONCAT (lastname, ', ', firstname, ' ', middlename) FROM TBUSER where username = encodedby) as encodedby, "
					+ "(SELECT CONCAT (lastname, ', ', firstname, ' ', middlename) FROM TBACCOUNTOFFICER where aocode = accountofficer) as accountofficer "
					+ "FROM Tbmembershipapp " + "WHERE ");
			if (stage.equals("ENCODING")) {
//				query.append("encodedby=:username AND ");
			} else if (stage.equals("APPLICATION REVIEW")) {
				query.append("assignedto=:username AND ");
				status = "2";
			} else if (stage.equals("INITIAL APPROVAL") || stage.equals("FOR REVIEW")) {
				status = "3";
			} else if (stage.equals("PAYMENT")) {
				paid = (List<TransactionAssignments>) dbService.execSQLQueryTransformer(
						"SELECT app.membershipappstatus, bill.amount, bill.orno, app.membershipappid, "
								+ "CONCAT (app.lastname, ', ', app.firstname, ' ', app.middlename) as name, "
								+ "app.applicationdate FROM TBMEMBERSHIPAPP app "
								+ "INNER JOIN TBMEMBER mem ON mem.membershipappid=app.membershipappid "
								+ "INNER JOIN TBBILLSPAYMENT bill ON mem.membershipid=bill.membershipid "
								+ "WHERE bill.typepayment='M' AND app.membershipappstatus='5' OR app.membershipappstatus='6' "
								+ "OR app.membershipappstatus='7'" + "ORDER BY app.encodeddate OFFSET (" + page
								+ " - 1)*(" + maxresult + ") ROWS FETCH NEXT " + maxresult + " ROWS ONLY",
						params, TransactionAssignments.class, 1);
				status = "4";
			} else if (stage.equals("RECOMMENDATION")) {
				status = "5";
			} else if (stage.equals("BOARD APPROVAL") || stage.equals("FOR APPROVAL")) {
				status = "6";
			} else if (stage.equals("APPROVED")) {
				status = "7";
			} else if (stage.equals("DECLINED")) {
				status = "8";
			} else if (stage.equals("DEFERRED")) {
				status = "9";
			} else {
				throw new IllegalArgumentException("No such: " + stage + " stage.");
			}
			query.append("membershipappstatus=:status ");
			if (searchstr != null) {
				query.append("AND (lastname like '%" + searchstr + "%' " + "OR firstname like '%" + searchstr + "%' "
						+ "OR middlename like '%" + searchstr + "%' " + "OR membershipappid like '%" + searchstr
						+ "%') ");
			}

			query.append("ORDER BY encodeddate OFFSET (" + page + " - 1)*(" + maxresult + ") ROWS FETCH NEXT "
					+ maxresult + " ROWS ONLY");
			params.put("status", status);
			System.out.println(query.toString());
			list = (List<TransactionAssignments>) dbService.execSQLQueryTransformer(query.toString(), params,
					TransactionAssignments.class, 1);
			if (paid.size() > 0) {
				list.addAll(paid);
				for (TransactionAssignments l : list) {
					l.setAmount(l.getAmount() == null ? BigDecimal.ZERO : l.getAmount());
					l.setOrno(l.getOrno() == null ? "" : l.getOrno());
					l.setMembershipappstatus(l.getMembershipappstatus() != null ? "Paid" : "Unpaid");
//					list.remove(l);
//					list.add(l);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public MyDashboard getMyDashboard() {
		try {
			params.put("username", UserUtil.securityService.getUserName());
			MyDashboard m = new MyDashboard();
			// Membership
			List<MyTransactions> lm = listMyTransactions();
			List<MyTransactions> rs = listResignTransactions();
			List<MyTransactions> lo = listLoanTransactions(6, 0);
			List<MyTransactions> up = listUpdateProfileRequests();

			Integer membershiptotal = 0;
			Integer resigningtotal = 0;
			Integer loanapptotal = 0;
			Integer profileupdate = 0;

			for (MyTransactions d : lm) {
				membershiptotal += d.getTotal();
			}
			for (MyTransactions e : rs) {
				resigningtotal += e.getTotal();
			}
			for (MyTransactions l : lo) {
				loanapptotal += l.getTotal();
			}
			for (MyTransactions p : up) {
				profileupdate += p.getTotal();
			}
			MyMembership mymem = new MyMembership();
			AssignmentService assign = new AssignmentServiceImpl();
			if (UserUtil.hasRole("COMM_MEMBER")) {
				/* Default Role ID . Daniel Fesalbon */
				membershiptotal += assign.listUnassignedAppReviewTotal();
			}
			mymem.setNewmemberapp(membershiptotal);
			mymem.setResignation(resigningtotal); /* Daniel Fesalbon */
			mymem.setUpdateprofile(profileupdate);/* Daniel Fesalbon */
			m.setMembership(mymem);

			// Loans
			MyLoans myloans = new MyLoans();

			// Daniel 05.09.2019
			List<WorkflowDashboardForm> totalloanapp = getWorkflowProcessList(3, "company", "ETL");
			myloans.setNewloanapp(totalloanapp.get(totalloanapp.size() - 1).getBuckettotal());

			myloans.setLoanpayments(getFinancialTransactions("9").size() + getFinancialTransactions("4").size());
			myloans.setAdjustments(getFinancialTransactions("F").size());
			m.setLoans(myloans);
			// Deposits

			// General Ledger
			return m;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new MyDashboard();
	}

	@Override
	public Integer countMyTransactionAssignments(String str, String stage) {
		try {
			String status = "1";
			StringBuilder query = new StringBuilder();
			params.put("stage", stage);
			params.put("username", UserUtil.securityService.getUserName());
			params.put("str", str);

			query.append("SELECT COUNT (membershipappid) " + "FROM Tbmembershipapp " + "WHERE ");
			if (stage.equals("ENCODING")) {
				query.append("encodedby=:username AND ");
			} else if (stage.equals("APPLICATION REVIEW")) {
				query.append("assignedto=:username AND ");
				status = "2";
			} else if (stage.equals("INITIAL APPROVAL") || stage.equals("FOR REVIEW")) {
				status = "3";
			} else if (stage.equals("PAYMENT")) {
				status = "4";
			} else if (stage.equals("RECOMMENDATION")) {
				status = "5";
			} else if (stage.equals("BOARD APPROVAL") || stage.equals("FOR APPROVAL")) {
				status = "6";
			} else if (stage.equals("APPROVED")) {
				status = "7";
			} else if (stage.equals("DECLINED")) {
				status = "8";
			} else if (stage.equals("DEFERRED")) {
				status = "9";
			} else {
				throw new IllegalArgumentException("No such: " + stage + " stage.");
			}
			query.append("membershipappstatus=:status ");
			if (str != null) {
				query.append("AND (lastname like '%" + str + "%' " + "OR firstname like '%" + str + "%' "
						+ "OR middlename like '%" + str + "%' " + "OR membershipappid like '%" + str + "%') ");
			}
			params.put("status", status);
			Integer total = (Integer) dbService.executeUniqueSQLQuery(query.toString(), params);
			return total;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public MembershipOtherBucket listNewMembershipOtheBucket(Date dtFrom, Date dtTo) {
		MembershipOtherBucket m = new MembershipOtherBucket();
		try {
			Integer approved = (Integer) dbService.executeUniqueSQLQuery(
					"SELECT COUNT(membershipappid) FROM Tbmembershipapp WHERE membershipappstatus='7'", null);

			Integer declined = (Integer) dbService.executeUniqueSQLQuery(
					"SELECT COUNT(membershipappid) FROM Tbmembershipapp WHERE membershipappstatus='8'", null);

			Integer deferred = (Integer) dbService.executeUniqueSQLQuery(
					"SELECT COUNT(membershipappid) FROM Tbmembershipapp WHERE membershipappstatus='9'", null);

			m.setApproved(approved);
			m.setDeclined(declined);
			m.setDeferred(deferred);

			return m;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	@Override
	public List<MyTransactions> listResignTransactions() {
		try {
			params.put("username", UserUtil.securityService.getUserName());
			List<MyTransactions> myDashboardList = (List<MyTransactions>) dbService.execSQLQueryTransformer(
					"EXEC sp_GetResignationTX @username=:username", params, MyTransactions.class, 1);
			return myDashboardList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<MyTransactions>();
	}

	public List<ResignationAssignments> ResignationAssignments(String stage, String search) {
		List<ResignationAssignments> list = new ArrayList<ResignationAssignments>();
		try {
			String status = "1";
			StringBuilder query = new StringBuilder();
			params.put("stage", stage);
			params.put("username", UserUtil.securityService.getUserName());
			params.put("str", search);

			query.append(
					"SELECT " + "membershipid, " + "CONCAT (lastname, ', ', firstname, ' ', middlename) as firstname, "
							+ "creationdate, txrefno FROM Tbresign WHERE ");
			if (stage.equals("ENCODING")) {
				query.append("createdby=:username AND ");
			} else if (stage.equals("RESIGNATION REVIEW")) {
				status = "2";
			} else if (stage.equals("INITIAL APPROVAL")) {
				status = "3";
			} else if (stage.equals("BOARD APPROVAL")) {
				status = "4";
			} else if (stage.equals("APPROVED FOR RELEASE")) {
				status = "5";
			} else if (stage.equals("RELEASE")) {
				status = "6";
			} else if (stage.equals("APPROVED")) {
				status = "7";
			} else if (stage.equals("DECLINED")) {
				status = "8";
			} else if (stage.equals("DEFERRED")) {
				status = "9";
			} else {
				throw new IllegalArgumentException("No such: " + stage + " stage.");
			}
			query.append("resignstatus=:status ");
			if (search != null) {
				query.append("AND (lastname like '%" + search + "%' " + "OR firstname like '%" + search + "%' "
						+ "OR middlename like '%" + search + "%' " + "OR membershipid like '%" + search + "%') ");
			}

			// query.append("ORDER BY creationDate OFFSET ("+page+" -
			// 1)*("+maxresult+") ROWS FETCH NEXT "+maxresult+" ROWS ONLY");
			params.put("status", status);
			list = (List<ResignationAssignments>) dbService.execSQLQueryTransformer(query.toString(), params,
					ResignationAssignments.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public ResignationBucket countResignations() {
		ResignationBucket r = new ResignationBucket();
		try {
			Integer ar = (Integer) dbService
					.executeUniqueSQLQuery("SELECT COUNT(membershipid) FROM Tbmember WHERE membershipstatus='3'", null);
			Integer rm = (Integer) dbService
					.executeUniqueSQLQuery("SELECT COUNT(membershipid) FROM Tbresign WHERE resignstatus='6'", null);
			Integer dc = (Integer) dbService
					.executeUniqueSQLQuery("SELECT COUNT(membershipid) FROM Tbresign WHERE resignstatus='8'", null);
			Integer df = (Integer) dbService
					.executeUniqueSQLQuery("SELECT COUNT(membershipid) FROM Tbresign WHERE resignstatus='9'", null);

			r.setApprovedresignation(ar);
			r.setReleasedmembership(rm);
			r.setDeclinedresignation(dc);
			r.setDeferredresignation(df);

			return r;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public List<MyTransactions> listLoanTransactions(int bucket, int stage) {
		try {
			params.put("username", UserUtil.securityService.getUserName());
			params.put("bucket", bucket);
			params.put("stage", stage);
			System.out.println(params + " ez");
			List<MyTransactions> loanDashboard = (List<MyTransactions>) dbService.execSQLQueryTransformer(
					"EXEC sp_GetLoanTransactions :username,:bucket,:stage", params, MyTransactions.class, 1);
			return loanDashboard;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<MyTransactions>();
	}

	@Override
	public List<LoanApplicationAssignments> getLoanApplicationAssignments(String stage, String search) {
		List<LoanApplicationAssignments> lo = new ArrayList<LoanApplicationAssignments>();
		try {
			StringBuilder query = new StringBuilder();
			params.put("stage", Integer.valueOf(stage));
			params.put("username", UserUtil.securityService.getUserName());
			params.put("dayMax", search);
			params.put("branchcode", UserUtil.getUserByUsername(securityService.getUserName()).getBranchcode());
			if (search != null) {
//				params.put("dayMin", Integer.valueOf(search) - 1);
				params.put("dayMin", search);
			}
			query.append("SELECT cifno, pnno, appno, applicationdate, createdby, cifname, "
					+ "(SELECT productname from TBLOANPRODUCT where productcode = loanproduct) as loanproduct,"
					+ "ISNULL(isdoneencoding,0) as isdoneencoding FROM Tblstapp ");
			/* modified daniel 12122018 */
//			if (stage.contains("ENCODING")) {
//				query.append("WHERE applicationstatus='1'");
////				if (UserUtil.hasRole("ACCT_OFFICER") && !UserUtil.hasRole("LOAN_ENCODER"))
////					query.append("WHERE isdoneencoding ='1' AND applicationstatus = '1' ");
////				/* modified ced 12042018 */
////				else if (UserUtil.hasRole("LOAN_ENCODER") && !UserUtil.hasRole("ACCT_OFFICER"))
////					query.append(
////							"WHERE  applicationstatus = '1'");
//////							"WHERE (isdoneencoding ='0' OR isdoneencoding is null) AND applicationstatus = '1'AND createdby=:username ");
////				else if (UserUtil.hasRole("ACCT_OFFICER") && UserUtil.hasRole("LOAN_ENCODER"))
////					query.append(
////							"WHERE isdoneencoding ='1' AND applicationstatus = '1' OR (isdoneencoding ='0' OR isdoneencoding is null) AND applicationstatus = '1'");
//////							"WHERE isdoneencoding ='1' AND applicationstatus = '1' OR (isdoneencoding ='0' OR isdoneencoding is null) AND applicationstatus = '1'AND createdby=:username ");
//			} else if (stage.contains("INVESTIGATION")) {
//				query.append("WHERE applicationstatus='2'");
//
//			} else if (stage.contains("EVALUATION")) {
//				query.append("WHERE applicationstatus='3'");
//
//			} else if (stage.contains("APPROVAL")) {
//				query.append("WHERE applicationstatus='4'");
//
//			} else if (stage.contains("FOR DOCUMENTATION")) {
//				query.append("WHERE applicationstatus='6'");
//
//			} else if (stage.contains("FOR ACCEPTANCE")) {
//				query.append("WHERE applicationstatus='5'");
//
//			} else if (stage.contains("BOOKING")) {
//				query.append("WHERE applicationstatus='7'");
//
//			} else if (stage.equalsIgnoreCase("BOOKED")) {
//				query.append("WHERE applicationstatus='8'");
//
//			} else if (stage.equalsIgnoreCase("REJECTED")) {
//				query.append("WHERE applicationstatus='11'");
//
//			} else if (stage.equalsIgnoreCase("AMENDMENT")) {
//				query.append("WHERE applicationstatus='10'");
//
//			} else {
//				throw new IllegalArgumentException("No such: " + stage + " stage.");
//			}
			query.append("WHERE applicationstatus=:stage ");
//			if (!search.equals("6")) {
//				query.append(" AND ");
//				if (search.equals("5"))
//					query.append("DATEDIFF(DAY, statusdatetime, GETDATE()) > 4 ");
//				else if (search.equals("1"))
//					query.append("DATEDIFF(DAY, statusdatetime, GETDATE()) <= 1 ");
//				else
//					query.append(
//							"DATEDIFF(DAY, statusdatetime, GETDATE()) <=:dayMax AND DATEDIFF(DAY, statusdatetime, GETDATE()) >:dayMin ");
//			}
			if (search.equals("1")) {
				query.append(
						" AND applicationstatus NOT IN ('8','9','11')  AND applicationtype='3' AND applicationstatus=:stage AND DATEDIFF(DAY, statusdatetime ,GETDATE()) <= 1");
			}
			if (search.equals("2")) {
				query.append(
						" AND applicationstatus NOT IN ('8','9','11')  AND applicationtype='3' AND applicationstatus=:stage AND DATEDIFF(DAY, statusdatetime ,GETDATE()) <= 2 AND DATEDIFF(DAY, statusdatetime ,GETDATE()) > 1");
			}
			if (search.equals("3")) {
				query.append(
						" AND applicationstatus NOT IN ('8','9','11')  AND applicationtype='3' AND applicationstatus=:stage AND DATEDIFF(DAY, statusdatetime ,GETDATE()) <= 3 AND DATEDIFF(DAY, statusdatetime ,GETDATE()) > 2");
			}
			if (search.equals("4")) {
				query.append(
						" AND applicationstatus NOT IN ('8','9','11')  AND applicationtype='3' AND applicationstatus=:stage AND DATEDIFF(DAY, statusdatetime ,GETDATE()) <= 4 AND DATEDIFF(DAY, statusdatetime ,GETDATE()) > 3");
			}
			if (search.equals("5")) {
				query.append(
						" AND applicationstatus NOT IN ('8','9','11')  AND applicationtype='3' AND applicationstatus=:stage AND DATEDIFF(DAY, statusdatetime ,GETDATE()) > 4");
			}
			if (search.equals("6")) {
				query.append(" AND applicationstatus=:stage AND applicationtype='3'");
			}
			query.append(" AND branchcode=:branchcode");

			lo = (List<LoanApplicationAssignments>) dbService.execSQLQueryTransformer(query.toString(), params,
					LoanApplicationAssignments.class, 1);
			if (lo != null) {
				return lo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lo;
	}

	/**
	 * Get Workflow Process (Stored Procedure)
	 * 
	 * @author Kevin (11.07.2017)
	 * @param workflowid - Application Type
	 * @param viewby     - e.g. team,company,viewall
	 * @return List <{@link WorkflowDashboardForm}>
	 */
	@Override
	public List<WorkflowDashboardForm> getWorkflowProcessList(Integer workflowid, String viewby, String company) {
		List<WorkflowDashboardForm> form = new ArrayList<WorkflowDashboardForm>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("workflowid", workflowid);
			params.put("username", securityService.getUserName());
			params.put("viewby", viewby);
			params.put("company", UserUtil.getUserByUsername(securityService.getUserName()).getCoopcode());
			form = (List<WorkflowDashboardForm>) dbService.execSQLQueryTransformer(
					"EXEC sp_GetWorkflowProcess @wflowid =:workflowid, @username=:username, @viewby=:viewby, @companycode =:company",
					params, WorkflowDashboardForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public LoanAppBucket summaryLoanApplications() {// CED
		LoanAppBucket loanAppBucket = new LoanAppBucket();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("branchcode", UserUtil.getUserByUsername(securityService.getUserName()).getBranchcode());
		try {
			loanAppBucket = (LoanAppBucket) dbService.execStoredProc("select "
					+ "(select COUNT(*) from TBLSTAPP where applicationstatus = 8 and branchcode=:branchcode) as booked, "
					+ "(select COUNT(*) from TBLSTAPP where applicationstatus = 11 and branchcode=:branchcode) as declined, "
					+ "(select COUNT(*) from TBLSTAPP where applicationstatus = 9 and branchcode=:branchcode) as rejected, "
					+ "(select COUNT(*) from TBLSTAPP where applicationstatus = 10 and branchcode=:branchcode) as foramendment",
					params, LoanAppBucket.class, 0, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return loanAppBucket;
	}

	@Override
	public List<MyTransactions> listUpdateProfileRequests() {
		try {
			params.put("username", UserUtil.securityService.getUserName());
			List<MyTransactions> myDashboardList = (List<MyTransactions>) dbService.execSQLQueryTransformer(
					"EXEC sp_GetProfileUpdateRequest @username=:username", params, MyTransactions.class, 1);
			return myDashboardList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<MyTransactions>();
	}

	@Override
	public UpdateProfileRequestOtherBucket listUpdateProfileOtherBucket() {
		// TODO Auto-generated method stub
		UpdateProfileRequestOtherBucket bucket = new UpdateProfileRequestOtherBucket();
		try {
			Integer approved = (Integer) dbService.executeUniqueSQLQuery(
					"SELECT COUNT(txrefno) FROM Tbupdateprofilerequest WHERE txstatus='3'", null);
			Integer cancelled = (Integer) dbService.executeUniqueSQLQuery(
					"SELECT COUNT(txrefno) FROM Tbupdateprofilerequest WHERE txstatus='5'", null);
			Integer declined = (Integer) dbService.executeUniqueSQLQuery(
					"SELECT COUNT(txrefno) FROM Tbupdateprofilerequest WHERE txstatus='4'", null);
			bucket.setApproved(approved);
			bucket.setCancelled(cancelled);
			bucket.setDeclined(declined);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return bucket;
	}

	@Override
	public List<UpdateProfileRequestAssignment> listUpdateProfileRequestAssignments(String stage, String search) {
		// TODO Auto-generated method stub
		List<UpdateProfileRequestAssignment> list = new ArrayList<UpdateProfileRequestAssignment>();
		String status = "1";
		StringBuilder query = new StringBuilder(
				"SELECT memberid, txrefno, changecategorytype, daterequested, requestedby FROM Tbupdateprofilerequest WHERE ");
		params.put("stage", stage);
		params.put("username", UserUtil.securityService.getUserName());
		params.put("str", search);
		try {
			if (stage.equals("FOR APPROVAL")) {
				status = "2";
			}
			if (stage.equals("CANCELLED")) {
				status = "5";
			}
			if (stage.equals("DECLINED")) {
				status = "4";
			}
			if (stage.equals("APPROVED")) {
				status = "3";
			}
			params.put("status", status);
			query.append("txstatus=:status");
			list = (List<UpdateProfileRequestAssignment>) dbService.execSQLQueryTransformer(query.toString(), params,
					UpdateProfileRequestAssignment.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public MyTransactions listLoanOtherTrans(int seqno, int total) {
		// TODO Auto-generated method stub
		MyTransactions form = new MyTransactions();
		try {
			params.put("seqno", seqno);
			Tbworkflowprocess process = (Tbworkflowprocess) dbService
					.executeListHQLQuery("FROM Tbworkflowprocess WHERE workflowid =3 AND sequenceno=:seqno", params);
			form.setStage(process.getProcessname());
			form.setTotal(total);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return form;
	}

	@Override
	public List<Tblstapp> listOtherTxLoans(int appstatus) {
		// TODO Auto-generated method stub
		List<Tblstapp> list = new ArrayList<Tblstapp>();
		// StringBuilder query = new StringBuilder();
		try {
			params.put("status", appstatus);
			list = (List<Tblstapp>) dbService.executeListHQLQuery("FROM Tblstapp WHERE applicationstatus=:status",
					params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<DashBoardForm> getDashBoard() {
		// TODO Auto-generated method stub
		List<DashBoardForm> form = new ArrayList<DashBoardForm>();
		try {
			form = (List<DashBoardForm>) dbService.execStoredProc(
					"SELECT 'New Bookings' as txType,(SELECT COUNT(*) FROM Tbaccountinfo WHERE txstat ='9') as pending"
							+ " ,(SELECT COUNT(*) FROM Tbaccountinfo WHERE txstat ='10') as posted " + "UNION ALL "
							+ "SELECT 'Financial' as txType,(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='F') as pending,"
							+ " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='P') as posted",
					null, DashBoardForm.class, 1, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public List<LoanForm> getLoanTransactions(String status) {
		// TODO Auto-generated method stub
		List<LoanForm> listOfLoans = new ArrayList<LoanForm>();
		params.put("stat", status);
		try {
			listOfLoans = (List<LoanForm>) dbService.execStoredProc(
					"SELECT applno as txrefno, txdate, stsdate as txstatusdate, clientid, name as fullname"
							+ ", (SELECT productname FROM TBLOANPRODUCT WHERE productcode = product) as product, amtfinance as loanamount"
							+ ", (SELECT desc1 FROM TBCODETABLE WHERE codename = 'PAYMENTCYCLE' AND codevalue = ppycyc) as paycycle, amortfee as amortization"
							+ "  FROM Tbaccountinfo WHERE txstat =:stat",
					params, LoanForm.class, 1, null);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listOfLoans;
	}

	@Override
	public List<Tbloanfin> getFinancialTransactions(String status) {
		// TODO Auto-generated method stub
		List<Tbloanfin> listOffin = new ArrayList<Tbloanfin>();
		params.put("stat", status);
		params.put("branchcode", UserUtil.getUserByUsername(securityService.getUserName()).getBranchcode());
		try {
			listOffin = (List<Tbloanfin>) dbService
					.executeListHQLQuery("FROM Tbloanfin WHERE txstatus =:stat and branchcode=:branchcode", params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listOffin;
	}

	@Override
	public String updateLoanStatus(List<LoanForm> listOfLoans, String status) {
		String result = "Failed";
		List<String> ids = new ArrayList<String>();
		try {
			params.put("stat", status);
			for (LoanForm row : listOfLoans) {
				ids.add(row.getTxrefno());
			}
			params.put("ids", ids);
			dbService.execStoredProc(
					"UPDATE Tbaccountinfo SET txstat =:status, stsdate = GETDATE() WHERE applno IN (:ids)", params,
					null, 0, null);
			result = "Success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String updateFinStatus(List<Tbloanfin> listOfFin, String status) {
		String result = "Failed";
		try {
			for (Tbloanfin row : listOfFin) {
				row.setTxstatus(status);
				row.setTxstatusdate(new Date());
				dbService.saveOrUpdate(row);
			}
			result = "Success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	// Renz CIF DASHBOARD
	@Override
	public CIFDashboard getCIFDashboard() {
		CIFDashboard dashform = new CIFDashboard();
		DBService dbService = new DBServiceImplCIF();
		params.put("branchcode", UserUtil.getUserByUsername(securityService.getUserName()).getBranchcode());
		try {
			//MEMBER
			// QDE ONGOING
			Integer qdeongoingMember = (Integer) dbService.executeUniqueSQLQuery(
					"Select Count(*) FROM Tbcifmain WHERE isencoding = '0' AND customertype = 1 AND cifstatus='1' and originatingbranch=:branchcode",
					params);
			dashform.setQdeongoingMember(qdeongoingMember);
			// QDE FORREVIEW
			Integer qdeforreviewMember = (Integer) dbService.executeUniqueSQLQuery(
					"Select Count(*) FROM Tbcifmain WHERE isencoding = '1' AND customertype = 1 AND cifstatus='1' and originatingbranch=:branchcode",
					params);
			dashform.setQdeforreviewMember(qdeforreviewMember);
			// FDE ONGOING
			Integer fdeongoingMember = (Integer) dbService.executeUniqueSQLQuery(
					"Select Count(*) FROM Tbcifmain WHERE isencoding = '0' AND customertype = 1 AND cifstatus='2' and originatingbranch=:branchcode",
					params);
			dashform.setFdeongoingMember(fdeongoingMember);
			// FDE FORREVIEW
			Integer fdeforreviewMember = (Integer) dbService.executeUniqueSQLQuery(
					"Select Count(*) FROM Tbcifmain WHERE isencoding = '1' AND customertype = 1 AND cifstatus='2' and originatingbranch=:branchcode",
					params);
			dashform.setFdeforreviewMember(fdeforreviewMember);
			
			//COMPANY
			// QDE ONGOING
			Integer qdeongoingCompany = (Integer) dbService.executeUniqueSQLQuery(
					"Select Count(*) FROM Tbcifmain WHERE isencoding = '0' AND customertype = 2 AND cifstatus='1' and originatingbranch=:branchcode",
					params);
			dashform.setQdeongoingCompany(qdeongoingCompany);
			// QDE FORREVIEW
			Integer qdeforreviewCompany = (Integer) dbService.executeUniqueSQLQuery(
					"Select Count(*) FROM Tbcifmain WHERE isencoding = '1' AND customertype = 2 AND cifstatus='1' and originatingbranch=:branchcode",
					params);
			dashform.setQdeforreviewCompany(qdeforreviewCompany);
			// FDE ONGOING
			Integer fdeongoingCompany = (Integer) dbService.executeUniqueSQLQuery(
					"Select Count(*) FROM Tbcifmain WHERE isencoding = '0' AND customertype = 2 AND cifstatus='2' and originatingbranch=:branchcode",
					params);
			dashform.setFdeongoingCompany(fdeongoingCompany);
			// FDE FORREVIEW
			Integer fdeforreviewCompany = (Integer) dbService.executeUniqueSQLQuery(
					"Select Count(*) FROM Tbcifmain WHERE isencoding = '1' AND customertype = 2 AND cifstatus='2' and originatingbranch=:branchcode",
					params);
			dashform.setFdeforreviewCompany(fdeforreviewCompany);
			
			
			//MEMBER
			// APPROVED
			Integer approvedMember = (Integer) dbService.executeUniqueSQLQuery(
					"Select Count(*) FROM Tbcifmain WHERE customertype = 1 AND cifstatus='3' and originatingbranch=:branchcode", params);
			dashform.setApprovedMember(approvedMember);
			
			//COMPANY
			// APPROVED
			Integer approvedCompany = (Integer) dbService.executeUniqueSQLQuery(
					"Select Count(*) FROM Tbcifmain WHERE customertype = 2 AND cifstatus='3' and originatingbranch=:branchcode", params);
			dashform.setApprovedCompany(approvedCompany);
			
			
			// CANCELLED
			Integer cancelled = (Integer) dbService.executeUniqueSQLQuery(
					"Select Count(*) FROM Tbcifmain WHERE cifstatus='4' and originatingbranch=:branchcode", params);
			dashform.setCancelled(cancelled);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dashform;
	}

	@Override
	public List<Tbcifmain> getCIFDashboardList(String query) {
		// TODO Auto-generated method stub
		DBService service = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();

		List<Tbcifmain> ciflist = new ArrayList<Tbcifmain>();
		params.put("query", query);
		try {
			ciflist = (List<Tbcifmain>) service.executeListHQLQuery(query, params);
			for (int x = 0; x < ciflist.size(); x++) {
				// ONGOING
				if (ciflist.get(x).getIsencoding() != null && ciflist.get(x).getCifstatus() != null) {
					if (ciflist.get(x).getIsencoding() == false && ciflist.get(x).getCifstatus().equals("1")) {
						ciflist.get(x).setCifstatus("QDE - Ongoing");
					}
					// FORREVIEW
					else if (ciflist.get(x).getIsencoding() == true && ciflist.get(x).getCifstatus().equals("1")) {
						ciflist.get(x).setCifstatus("QDE - For Review");
					}
					// ONGOING
					else if (ciflist.get(x).getIsencoding() == false && ciflist.get(x).getCifstatus().equals("2")) {
						ciflist.get(x).setCifstatus("FDE - Ongoing");
					}
					// FORREVIEW
					else if (ciflist.get(x).getIsencoding() == true && ciflist.get(x).getCifstatus().equals("2")) {
						ciflist.get(x).setCifstatus("FDE - For Review");
					}
					// APPROVED
					else if (ciflist.get(x).getCifstatus().equals("3")) {
						ciflist.get(x).setCifstatus("Approved");
					}
					// CANCELLED
					else if (ciflist.get(x).getCifstatus().equals("4")) {
						ciflist.get(x).setCifstatus("Cancelled");
					}
					// MERGED
					else {
						ciflist.get(x).setCifstatus("Merged");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ciflist;
	}

	@Override
	public List<DashboardListFormCIF> dashboardListCIF(Integer page, Integer maxresult, String fullname,
			String cifstatus, Boolean isEncoding, String branchcode, String customertype) {
		
		List<DashboardListFormCIF> list = new ArrayList<DashboardListFormCIF>();
		DBService dbServiceCIF = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(cifstatus!=null && branchcode!=null) {
				params.put("page", page);
				params.put("maxresult", maxresult);
				
				params.put("fullname", fullname == null ? "%" : "%" + fullname + "%");
				params.put("cifstatus", cifstatus);
				params.put("isEncoding", isEncoding);
				params.put("branchcode", branchcode);
				params.put("customertype", customertype);

				String q = "EXEC sp_DashboardCIF @fullname=:fullname, @cifstatus=:cifstatus, @isEncoding=:isEncoding, @branchcode=:branchcode, @customertype=:customertype, @ispagingon='true', @page=:page, @maxresult=:maxresult";
				list = (List<DashboardListFormCIF>) dbServiceCIF.execSQLQueryTransformer(q, params, DashboardListFormCIF.class, 1);
			}else {
				System.out.println(">> CIF STATUS OR BRANCH CODE IS NULL <<<");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int dashboardListCIFCount(String fullname, String cifstatus,
			Boolean isEncoding, String branchcode, String customertype) {
		
		Integer count = 0;
		DBService dbServiceCIF = new DBServiceImplCIF();
		try {
			if(cifstatus!=null && branchcode!=null) {
				params.put("fullname", fullname == null ? "%" : "%" + fullname + "%");
				params.put("cifstatus", cifstatus);
				params.put("isEncoding", isEncoding);
				params.put("branchcode", branchcode);
				params.put("customertype", customertype);

				String q = "EXEC sp_DashboardCIF @fullname=:fullname, @cifstatus=:cifstatus, @isEncoding=:isEncoding, @branchcode=:branchcode, @customertype=:customertype, @ispagingon='false', @page=NULL, @maxresult=NULL";
				count = (Integer) dbServiceCIF.execSQLQueryTransformer(q, params, null, 0);
			}else {
				System.out.println(">> CIF STATUS OR BRANCH CODE IS NULL <<<");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<DashBoardDocumentsForm> getListofToFollowDocumentsCIF(String getDocType, String cifname) {
		List<DashBoardDocumentsForm> list = new ArrayList<DashBoardDocumentsForm>();
		try {
			params.put("getDocType", getDocType);
			params.put("cifname", cifname);
			list = (List<DashBoardDocumentsForm>) dbService.execStoredProc(
					"EXEC sp_DASHBOARD_GetDocuments :getDocType, :cifname",
					params, DashBoardDocumentsForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@Override
	public int getListofToFollowDocumentsCIFCount(String getDocType, String cifname) {
		Integer count = 0;
		try {
			params.put("getDocType", getDocType);
			params.put("cifname", cifname);
			String q = "EXEC sp_DASHBOARD_GetDocuments :getDocType, :cifname";
			count = (Integer) dbService.execSQLQueryTransformer(q, params, null, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<DashBoardDocumentsForm> getListofToFollowDocumentsLoans(String getDocType, String cifname) {
		List<DashBoardDocumentsForm> list = new ArrayList<DashBoardDocumentsForm>();
		try {
			params.put("getDocType", getDocType);
			params.put("cifname", cifname);
			list = (List<DashBoardDocumentsForm>) dbService.execStoredProc(
					"EXEC sp_DASHBOARD_GetDocuments :getDocType, :cifname",
					params, DashBoardDocumentsForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@Override
	public int getListofToFollowDocumentsLoansCount(String getDocType, String cifname) {
		Integer count = 0;
		try {
			params.put("getDocType", getDocType);
			params.put("cifname", cifname);
			String q = "EXEC sp_DASHBOARD_GetDocuments :getDocType, :cifname";
			count = (Integer) dbService.execSQLQueryTransformer(q, params, null, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public List<TMPFrom> getTMP() {
		List<TMPFrom> list = new ArrayList<TMPFrom>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			System.out.print("Username : " + securityService.getUserName());
			params.put("username", securityService.getUserName());
			//params.put("company", UserUtil.getUserByUsername(securityService.getUserName()).getCoopcode());
			list = (List<TMPFrom>) dbService.execStoredProc(
					"EXEC sp_Dashboard_TMP :username", params,
					TMPFrom.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<TMPFrom> getMembershipDashboard() {
		List<TMPFrom> list = new ArrayList<TMPFrom>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("username", securityService.getUserName());
			list = (List<TMPFrom>) dbService.execStoredProc(
					"EXEC sp_Dashboard_Membership_Application :username", params,
					TMPFrom.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<TMPFrom> getCompanyDashboard() {
		List<TMPFrom> list = new ArrayList<TMPFrom>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("username", securityService.getUserName());
			list = (List<TMPFrom>) dbService.execStoredProc(
					"EXEC sp_Dashboard_Company_Application :username", params,
					TMPFrom.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<TMPFrom> getLoanDashboard() {
		List<TMPFrom> list = new ArrayList<TMPFrom>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("username", securityService.getUserName());
			list = (List<TMPFrom>) dbService.execStoredProc(
					"EXEC sp_Dashboard_Loan_Application :username", params,
					TMPFrom.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	
	}

}
