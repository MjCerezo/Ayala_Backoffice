package com.etel.loan;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.loanform.LoanAppInquiryForApprovalForm;
import com.etel.loanform.LoanAppInquiryForReleaseForm;
import com.etel.util.DateTimeUtil;

public class LoanApplicationServiceImpl implements LoanApplicationService {

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanAppInquiryForApprovalForm> searchLoanApplicationForApproval(String tat, String emp) {
		List<LoanAppInquiryForApprovalForm> formList = new ArrayList<LoanAppInquiryForApprovalForm>();
		DBService dbServiceCore = new DBServiceImpl();
		HashMap<String, Object> params = new HashMap<String, Object>();
		try {
			Date origDt = new Date();
			params.put("origDate", origDt);
			Date dt1 = DateTimeUtil.getTat(-24, origDt);
			params.put("dt1", dt1);
			Date dt2 = DateTimeUtil.getTat(-48, origDt);
			params.put("dt2", dt2);
			Date dt3 = DateTimeUtil.getTat(-72, origDt);
			params.put("dt3", dt3);
			Date dt4 = DateTimeUtil.getTat(-96, origDt);
			params.put("dt4", dt4);

			params.put("tat", tat);

			if (emp == null || emp.isEmpty())
				params.put("emp", "%");
			else if (emp != null || !emp.isEmpty())
				params.put("emp", emp);
			
			formList = (List<LoanAppInquiryForApprovalForm>) dbServiceCore.executeListSQLQueryWithClass(
					"EXEC sp_Dashboard_Loan_Application_ForApproval @enddate = NULL, @startdate = NULL, @param =:emp",
					params, LoanAppInquiryForApprovalForm.class);
			
			/*if (tat.equals("1")) {
				formList = (List<LoanApplicationInquiryForm>) dbService.executeListSQLQueryWithClass(
						"EXEC sla_loan @txstat =:stat,@enddate =:dt1,@startdate =:dt1,@param =:emp,@all=:tat,@username=:username",
						params, LoanApplicationInquiryForm.class);
			}
			if (tat.equals("2")) {
				formList = (List<LoanApplicationInquiryForm>) dbService.executeListSQLQueryWithClass(
						"EXEC sla_loan @txstat =:stat,@enddate =:dt1,@startdate =:dt2,@param =:emp,@all=:tat,@username=:username",
						params, LoanApplicationInquiryForm.class);
			}
			if (tat.equals("3")) {
				formList = (List<LoanApplicationInquiryForm>) dbService.executeListSQLQueryWithClass(
						"EXEC sla_loan @txstat =:stat,@enddate =:dt2,@startdate =:dt3,@param =:emp,@all=:tat,@username=:username",
						params, LoanApplicationInquiryForm.class);
			}
			if (tat.equals("4")) {
				formList = (List<LoanApplicationInquiryForm>) dbService.executeListSQLQueryWithClass(
						"EXEC sla_loan @txstat =:stat,@enddate =:dt3,@startdate =:dt4,@param =:emp,@all=:tat,@username=:username",
						params, LoanApplicationInquiryForm.class);
			}
			if (tat.equals("5")) {
				formList = (List<LoanApplicationInquiryForm>) dbService.executeListSQLQueryWithClass(
						"EXEC sla_loan @txstat =:stat,@enddate =:dt4,@startdate =:dt1,@param =:emp,@all=:tat,@username=:username",
						params, LoanApplicationInquiryForm.class);
			}
			if (tat.equals("0")) {
				formList = (List<LoanApplicationInquiryForm>) dbService.executeListSQLQueryWithClass(
						"EXEC sla_loan @txstat =:stat ,@enddate =:dt4,@startdate =:dt1,@param =:emp,@all=:tat,@username=:username",
						params, LoanApplicationInquiryForm.class);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return formList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanAppInquiryForReleaseForm> searchLoanApplicationForRelease(String tat, String emp) {
		List<LoanAppInquiryForReleaseForm> formList = new ArrayList<LoanAppInquiryForReleaseForm>();
		DBService dbServiceCore = new DBServiceImpl();
		HashMap<String, Object> params = new HashMap<String, Object>();
		try {
			Date origDt = new Date();
			params.put("origDate", origDt);
			Date dt1 = DateTimeUtil.getTat(-24, origDt);
			params.put("dt1", dt1);
			Date dt2 = DateTimeUtil.getTat(-48, origDt);
			params.put("dt2", dt2);
			Date dt3 = DateTimeUtil.getTat(-72, origDt);
			params.put("dt3", dt3);
			Date dt4 = DateTimeUtil.getTat(-96, origDt);
			params.put("dt4", dt4);

			params.put("tat", tat);

			if (emp == null || emp.isEmpty())
				params.put("emp", "%");
			else if (emp != null || !emp.isEmpty())
				params.put("emp", emp);
			
			formList = (List<LoanAppInquiryForReleaseForm>) dbServiceCore.executeListSQLQueryWithClass(
					"EXEC sp_Dashboard_Loan_Application_ForRelease @enddate = NULL, @startdate = NULL, @param =:emp",
					params, LoanAppInquiryForReleaseForm.class);
			
			/*if (tat.equals("1")) {
				formList = (List<LoanApplicationInquiryForm>) dbService.executeListSQLQueryWithClass(
						"EXEC sla_loan @txstat =:stat,@enddate =:dt1,@startdate =:dt1,@param =:emp,@all=:tat,@username=:username",
						params, LoanApplicationInquiryForm.class);
			}
			if (tat.equals("2")) {
				formList = (List<LoanApplicationInquiryForm>) dbService.executeListSQLQueryWithClass(
						"EXEC sla_loan @txstat =:stat,@enddate =:dt1,@startdate =:dt2,@param =:emp,@all=:tat,@username=:username",
						params, LoanApplicationInquiryForm.class);
			}
			if (tat.equals("3")) {
				formList = (List<LoanApplicationInquiryForm>) dbService.executeListSQLQueryWithClass(
						"EXEC sla_loan @txstat =:stat,@enddate =:dt2,@startdate =:dt3,@param =:emp,@all=:tat,@username=:username",
						params, LoanApplicationInquiryForm.class);
			}
			if (tat.equals("4")) {
				formList = (List<LoanApplicationInquiryForm>) dbService.executeListSQLQueryWithClass(
						"EXEC sla_loan @txstat =:stat,@enddate =:dt3,@startdate =:dt4,@param =:emp,@all=:tat,@username=:username",
						params, LoanApplicationInquiryForm.class);
			}
			if (tat.equals("5")) {
				formList = (List<LoanApplicationInquiryForm>) dbService.executeListSQLQueryWithClass(
						"EXEC sla_loan @txstat =:stat,@enddate =:dt4,@startdate =:dt1,@param =:emp,@all=:tat,@username=:username",
						params, LoanApplicationInquiryForm.class);
			}
			if (tat.equals("0")) {
				formList = (List<LoanApplicationInquiryForm>) dbService.executeListSQLQueryWithClass(
						"EXEC sla_loan @txstat =:stat ,@enddate =:dt4,@startdate =:dt1,@param =:emp,@all=:tat,@username=:username",
						params, LoanApplicationInquiryForm.class);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return formList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanAppInquiryForReleaseForm> searchLoanApplicationForBooking(String tat, String emp) {
		List<LoanAppInquiryForReleaseForm> formList = new ArrayList<LoanAppInquiryForReleaseForm>();
		DBService dbServiceCore = new DBServiceImpl();
		HashMap<String, Object> params = new HashMap<String, Object>();
		try {
			Date origDt = new Date();
			params.put("origDate", origDt);
			Date dt1 = DateTimeUtil.getTat(-24, origDt);
			params.put("dt1", dt1);
			Date dt2 = DateTimeUtil.getTat(-48, origDt);
			params.put("dt2", dt2);
			Date dt3 = DateTimeUtil.getTat(-72, origDt);
			params.put("dt3", dt3);
			Date dt4 = DateTimeUtil.getTat(-96, origDt);
			params.put("dt4", dt4);

			params.put("tat", tat);

			if (emp == null || emp.isEmpty())
				params.put("emp", "%");
			else if (emp != null || !emp.isEmpty())
				params.put("emp", emp);
			
			formList = (List<LoanAppInquiryForReleaseForm>) dbServiceCore.executeListSQLQueryWithClass(
					"EXEC sp_Dashboard_Loan_Application_ForBooking @enddate = NULL, @startdate = NULL, @param =:emp",
					params, LoanAppInquiryForReleaseForm.class);
			
			/*if (tat.equals("1")) {
				formList = (List<LoanApplicationInquiryForm>) dbService.executeListSQLQueryWithClass(
						"EXEC sla_loan @txstat =:stat,@enddate =:dt1,@startdate =:dt1,@param =:emp,@all=:tat,@username=:username",
						params, LoanApplicationInquiryForm.class);
			}
			if (tat.equals("2")) {
				formList = (List<LoanApplicationInquiryForm>) dbService.executeListSQLQueryWithClass(
						"EXEC sla_loan @txstat =:stat,@enddate =:dt1,@startdate =:dt2,@param =:emp,@all=:tat,@username=:username",
						params, LoanApplicationInquiryForm.class);
			}
			if (tat.equals("3")) {
				formList = (List<LoanApplicationInquiryForm>) dbService.executeListSQLQueryWithClass(
						"EXEC sla_loan @txstat =:stat,@enddate =:dt2,@startdate =:dt3,@param =:emp,@all=:tat,@username=:username",
						params, LoanApplicationInquiryForm.class);
			}
			if (tat.equals("4")) {
				formList = (List<LoanApplicationInquiryForm>) dbService.executeListSQLQueryWithClass(
						"EXEC sla_loan @txstat =:stat,@enddate =:dt3,@startdate =:dt4,@param =:emp,@all=:tat,@username=:username",
						params, LoanApplicationInquiryForm.class);
			}
			if (tat.equals("5")) {
				formList = (List<LoanApplicationInquiryForm>) dbService.executeListSQLQueryWithClass(
						"EXEC sla_loan @txstat =:stat,@enddate =:dt4,@startdate =:dt1,@param =:emp,@all=:tat,@username=:username",
						params, LoanApplicationInquiryForm.class);
			}
			if (tat.equals("0")) {
				formList = (List<LoanApplicationInquiryForm>) dbService.executeListSQLQueryWithClass(
						"EXEC sla_loan @txstat =:stat ,@enddate =:dt4,@startdate =:dt1,@param =:emp,@all=:tat,@username=:username",
						params, LoanApplicationInquiryForm.class);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return formList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanAppInquiryForApprovalForm> searchLoanApplication(String tat, String emp, Integer status) {
		List<LoanAppInquiryForApprovalForm> formList = new ArrayList<LoanAppInquiryForApprovalForm>();
		DBService dbServiceCore = new DBServiceImpl();
		HashMap<String, Object> params = new HashMap<String, Object>();
		try {
			Date origDt = new Date();
			params.put("origDate", origDt);
			Date dt1 = DateTimeUtil.getTat(-24, origDt);
			params.put("dt1", dt1);
			Date dt2 = DateTimeUtil.getTat(-48, origDt);
			params.put("dt2", dt2);
			Date dt3 = DateTimeUtil.getTat(-72, origDt);
			params.put("dt3", dt3);
			Date dt4 = DateTimeUtil.getTat(-96, origDt);
			params.put("dt4", dt4);

			params.put("tat", tat);

			if (emp == null || emp.isEmpty())
				params.put("emp", "%");
			else if (emp != null || !emp.isEmpty())
				params.put("emp", emp);
			params.put("status", status);
			
			formList = (List<LoanAppInquiryForApprovalForm>) dbServiceCore.executeListSQLQueryWithClass(
					"EXEC sp_Dashboard_Loan_Application_List @enddate = NULL, @startdate = NULL, @param =:emp, @status =:status",
					params, LoanAppInquiryForApprovalForm.class);
			
			/*if (tat.equals("1")) {
				formList = (List<LoanApplicationInquiryForm>) dbService.executeListSQLQueryWithClass(
						"EXEC sla_loan @txstat =:stat,@enddate =:dt1,@startdate =:dt1,@param =:emp,@all=:tat,@username=:username",
						params, LoanApplicationInquiryForm.class);
			}
			if (tat.equals("2")) {
				formList = (List<LoanApplicationInquiryForm>) dbService.executeListSQLQueryWithClass(
						"EXEC sla_loan @txstat =:stat,@enddate =:dt1,@startdate =:dt2,@param =:emp,@all=:tat,@username=:username",
						params, LoanApplicationInquiryForm.class);
			}
			if (tat.equals("3")) {
				formList = (List<LoanApplicationInquiryForm>) dbService.executeListSQLQueryWithClass(
						"EXEC sla_loan @txstat =:stat,@enddate =:dt2,@startdate =:dt3,@param =:emp,@all=:tat,@username=:username",
						params, LoanApplicationInquiryForm.class);
			}
			if (tat.equals("4")) {
				formList = (List<LoanApplicationInquiryForm>) dbService.executeListSQLQueryWithClass(
						"EXEC sla_loan @txstat =:stat,@enddate =:dt3,@startdate =:dt4,@param =:emp,@all=:tat,@username=:username",
						params, LoanApplicationInquiryForm.class);
			}
			if (tat.equals("5")) {
				formList = (List<LoanApplicationInquiryForm>) dbService.executeListSQLQueryWithClass(
						"EXEC sla_loan @txstat =:stat,@enddate =:dt4,@startdate =:dt1,@param =:emp,@all=:tat,@username=:username",
						params, LoanApplicationInquiryForm.class);
			}
			if (tat.equals("0")) {
				formList = (List<LoanApplicationInquiryForm>) dbService.executeListSQLQueryWithClass(
						"EXEC sla_loan @txstat =:stat ,@enddate =:dt4,@startdate =:dt1,@param =:emp,@all=:tat,@username=:username",
						params, LoanApplicationInquiryForm.class);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return formList;
	}

}
