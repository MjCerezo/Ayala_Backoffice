package com.etel.loan;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.cifsdb.data.Tbcifemployment;
import com.cifsdb.data.Tbcifindividual;
import com.cifsdb.data.Tbcodetable;
import com.coopdb.data.Tbaccountinfo;
import com.coopdb.data.Tbloanevaluationtable;
import com.coopdb.data.Tblstapp;
import com.coopdb.data.Tbmembernetcapping;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.loanform.LoanAppInquiryForApprovalForm;
import com.etel.loanform.LoanAppInquiryForReleaseForm;
import com.etel.loanform.LoanEvaluationResultForm;
import com.etel.loanform.LoanRuleForm;
import com.etel.loanform.MemberLoanEvaluationForm;
import com.etel.util.DateTimeUtil;
import com.etel.utils.UserUtil;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbloanevaluationtable> listEvaluationTable(String template) {
		List<Tbloanevaluationtable> list = new ArrayList<Tbloanevaluationtable>();
		DBService dbServiceCore = new DBServiceImpl();
		//HashMap<String, Object> params = new HashMap<String, Object>();
		try {
			list = (List<Tbloanevaluationtable>) dbServiceCore.executeListHQLQuery
					("FROM Tbloanevaluationtable", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public LoanRuleForm getLoanRule(String appno) {
		LoanRuleForm form = new LoanRuleForm();
		DBService dbServiceCIF = new DBServiceImplCIF();
		DBService dbServiceCore = new DBServiceImpl();
		HashMap<String, Object> params = new HashMap<String, Object>();
		try {
			if(appno!=null) {
				params.put("appno", appno);
				Tblstapp app = (Tblstapp) dbServiceCore.executeUniqueHQLQuery
						("FROM Tblstapp WHERE appno=:appno", params);
				
				/**Set values**/
				// Qualified Member
				if(app.getLoanproduct()!=null) {
					params.put("loanProduct", app.getLoanproduct());
					String qualifiedmember = (String) dbServiceCore.executeUniqueSQLQuery
							("SELECT qualifiedmember FROM Tbloanproduct WHERE productcode=:loanProduct", params);
					if(qualifiedmember!=null) {
						if(qualifiedmember.equals("0")){
							form.setRank("Regular");
						}else if(qualifiedmember.equals("1")){
							form.setRank("Associate");
						}else if(qualifiedmember.equals("2")){
							form.setRank("Both");
						}
					}
				}
				
				params.put("cifno", app.getCifno());
				Tbcifemployment emp = (Tbcifemployment) dbServiceCIF.executeUniqueHQLQuery
						("FROM Tbcifemployment WHERE cifno=:cifno", params);
				if(emp.getDatehiredfrom()!=null && emp.getDatehiredto()!=null) {
					params.put("positionCode", emp.getPosition());
					params.put("tenureMember", DateTimeUtil.yearsDiff(emp.getDatehiredfrom(), emp.getDatehiredto()));
					
					Integer evalTableId = (Integer) dbServiceCore.executeUniqueSQLQuery
							("SELECT id FROM TBLOANEVALUATIONTABLE "
									+ "WHERE template = '102' "
									+ "AND :positionCode BETWEEN minrankcode AND maxrankcode "
									+ "AND :tenureMember BETWEEN mintenure AND maxtenure", params);
					params.put("id", evalTableId);
					Tbloanevaluationtable loanEval = (Tbloanevaluationtable) dbServiceCore.executeUniqueHQLQuery
							("FROM Tbloanevaluationtable WHERE id=:id", params);
					
					// Tenure
					form.setTenure(loanEval.getMintenure().toString() + " - " + loanEval.getMaxtenure().toString() + " years");
					
					// Position
					form.setPosition(loanEval.getMinrankname() + " - " + loanEval.getMaxrankname());
					
					// Loan Amount
					form.setMaxLoanAmount(loanEval.getMaxloanable());
					
					// Net Take Home Pay
					//
			
					// Max No. Loan
					form.setMaxNoLoan(Integer.valueOf(loanEval.getMaxcounter()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public MemberLoanEvaluationForm getMemberEvalForm(String appno, String cifno) {
		MemberLoanEvaluationForm form = new MemberLoanEvaluationForm();
		DBService dbServiceCIF = new DBServiceImplCIF();
		DBService dbServiceCore = new DBServiceImpl();
		HashMap<String, Object> params = new HashMap<String, Object>();
		try {
			if(appno!=null && cifno!=null) {
				params.put("appno", appno);
				params.put("cifno", cifno);
				
				Tbcifindividual indiv = (Tbcifindividual) dbServiceCIF.executeUniqueHQLQuery
						("FROM Tbcifindividual WHERE cifno=:cifno", params);
				
				Tbcifemployment emp = (Tbcifemployment) dbServiceCIF.executeUniqueHQLQuery
						("FROM Tbcifemployment WHERE cifno=:cifno", params);

				Tbaccountinfo info = (Tbaccountinfo) dbServiceCore.executeUniqueHQLQuery
						("FROM Tbaccountinfo WHERE applno=:appno", params);
				
				Tbmembernetcapping net = (Tbmembernetcapping) dbServiceCore.executeUniqueHQLQuery
						("FROM Tbmembernetcapping WHERE appno=:appno", params);
				
				/**Set values**/
				// Qualified Member
				if(indiv.getMembershiptype()!=null) {
					params.put("memberType", indiv.getMembershiptype());
					Tbcodetable code = (Tbcodetable) dbServiceCIF.executeUniqueHQLQuery
							("FROM Tbcodetable WHERE codevalue=:memberType AND codename='MEMBERSHIPTYPE'", params);
					form.setRank(code.getDesc1());
				}
				// Tenure
				if(emp.getDatehiredfrom()!=null && emp.getDatehiredto()!=null) {
					form.setTenure(DateTimeUtil.yearsDiff(emp.getDatehiredfrom(), emp.getDatehiredto()));
				}
				// Position
				if(emp.getPosition()!=null) {
					params.put("position", emp.getPosition());
					String positionDesc1 = (String) dbServiceCore.executeUniqueSQLQuery
							("SELECT desc1 FROM Tbcodetable WHERE codevalue=:position AND codename='POSITION'", params);
					form.setPosition(positionDesc1);
				}
				// Loan Amount
				form.setMaxLoanAmount(info.getFaceamt());
				// Net Take Home Pay
				// 
				
				// Total No. of Loans
				Integer totalLoan = (Integer) dbServiceCore.executeUniqueSQLQuery(
						"SELECT COUNT(*) FROM Tblstapp WHERE cifno=:cifno", params);
				form.setMaxNoLoan(totalLoan);
				
				// Total No. of Loans - Availed
				Integer totalLoanAvailed = (Integer) dbServiceCore.executeUniqueSQLQuery(
						"SELECT COUNT(*) FROM Tblstapp a"
						+ " LEFT JOIN TBLOANPRODUCT b ON a.loanproduct=b.productcode"
						+ " WHERE cifno=:cifno AND b.isexempted = '1'", params);
				form.setTotalLoanAvailed(totalLoanAvailed);
				
				// Total No. of Loans - Exempted
				Integer totalLoanExempted = (Integer) dbServiceCore.executeUniqueSQLQuery(
						"SELECT COUNT(*) FROM Tblstapp a"
						+ " LEFT JOIN TBLOANPRODUCT b ON a.loanproduct=b.productcode"
						+ " WHERE cifno=:cifno AND b.isexempted = '0'", params);
				form.setTotalLoanExempted(totalLoanExempted);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@SuppressWarnings("unused")
	@Override
	public LoanEvaluationResultForm getLoanEvaluationResult(String appno, String cifno,
			LoanRuleForm ruleForm, MemberLoanEvaluationForm memberForm) {
		LoanEvaluationResultForm form = new LoanEvaluationResultForm();
		DBService dbServiceCIF = new DBServiceImplCIF();
		DBService dbServiceCore = new DBServiceImpl();
		HashMap<String, Object> params = new HashMap<String, Object>();
		try {
			params.put("appno", appno);
			params.put("cifno", cifno);
			
			Tblstapp app = (Tblstapp) dbServiceCore.executeUniqueHQLQuery
					("FROM Tblstapp WHERE appno=:appno", params);
			
			Tbcifindividual indiv = (Tbcifindividual) dbServiceCIF.executeUniqueHQLQuery
					("FROM Tbcifindividual WHERE cifno=:cifno", params);
			
			// Qualified Member
			if(ruleForm.getRank()!=null && memberForm.getRank()!=null) {
				if(ruleForm.getRank().equals("Both")) {
					form.setRank("Passed");
					//n.setRank(true);
				}else {
					if(ruleForm.getRank().equals("Regular") && memberForm.getRank().equals("Regular")) {
						form.setRank("Passed");
						//n.setRank(true);
					}else if(ruleForm.getRank().equals("Regular") && !memberForm.getRank().equals("Regular")) {
						form.setRank("Failed");
						//n.setRank(false);
					}else if(ruleForm.getRank().equals("Associate") && memberForm.getRank().equals("Associate")) {
						form.setRank("Passed");
						//n.setRank(true);
					}else if(ruleForm.getRank().equals("Associate") && !memberForm.getRank().equals("Associate")) {
						form.setRank("Failed");
						//n.setRank(false);
					}
				}
			}
			
			Tbcifemployment emp = (Tbcifemployment) dbServiceCIF.executeUniqueHQLQuery
					("FROM Tbcifemployment WHERE cifno=:cifno", params);
			if(emp.getDatehiredfrom()!=null && emp.getDatehiredto()!=null) {
				params.put("positionCode", emp.getPosition());
				params.put("tenureMember", DateTimeUtil.yearsDiff(emp.getDatehiredfrom(), emp.getDatehiredto()));
				
				Integer evalTableId = (Integer) dbServiceCore.executeUniqueSQLQuery
						("SELECT id FROM TBLOANEVALUATIONTABLE "
								+ "WHERE template = '102' "
								+ "AND :positionCode BETWEEN minrankcode AND maxrankcode "
								+ "AND :tenureMember BETWEEN mintenure AND maxtenure", params);
				params.put("id", evalTableId);
				Tbloanevaluationtable loanEval = (Tbloanevaluationtable) dbServiceCore.executeUniqueHQLQuery
						("FROM Tbloanevaluationtable WHERE id=:id", params);	
				
					// Tenure
					Integer empTenure =  DateTimeUtil.yearsDiff(emp.getDatehiredfrom(), emp.getDatehiredto());
					if(empTenure >= loanEval.getMintenure() && empTenure <= loanEval.getMaxtenure()) {
						form.setTenure("Passed");
						//n.setTenure(true);
					}else {
						form.setTenure("Failed");
						//n.setTenure(false);
					}
					// Position
					if(Integer.valueOf(emp.getPosition()) >= loanEval.getMinrankcode()  && Integer.valueOf(emp.getPosition()) <= loanEval.getMaxrankcode()) {
						form.setPosition("Passed");
						//n.setPosition(true);
					}else {
						form.setPosition("Failed");
						//n.setPosition(false);
					}
					
					// Max Loan Amount
					Tbaccountinfo info = (Tbaccountinfo) dbServiceCore.executeUniqueHQLQuery
							("FROM Tbaccountinfo WHERE applno=:appno", params);
					
					if(info.getFaceamt().compareTo(loanEval.getMaxloanable()) >0) { // greater than
						form.setMaxLoanAmount("Failed");
						//n.setMaxloanamount(false);
					}else {
						form.setMaxLoanAmount("Passed");
						//n.setMaxloanamount(true);
					}
					
					// NTHP
					form.setNthp("Passed");
					//n.setNthp(false);
					
					// Total Loan
					form.setMaxNoLoan("Passed");
					//n.setMaxnoloan(true);

				}	
				// Save Loan Eval Result
			 	/*Tbloanevaluationresult n2 = (Tbloanevaluationresult) dbServiceCore.executeUniqueHQLQuery
						("FROM Tbloanevaluationresult WHERE appno=:appno", params);
				if(n2!=null) {
				 	n2.setRank(n.getRank());
				 	n2.setTenure(n.getTenure());
				 	n2.setPosition(n.getPosition());
				 	n2.setMaxloanamount(n.getMaxloanamount());
				 	n2.setNthp(n.getNthp());
				 	n2.setMaxnoloan(n.getMaxnoloan());
				 	n2.setUpdatedby(UserUtil.securityService.getUserName());
				 	n2.setDateupdated(new Date());
					dbServiceCore.saveOrUpdate(n2);
				}else {
					n.setAppno(appno);
					n.setCreatedby(UserUtil.securityService.getUserName());
					n.setDatecreated(new Date());
					dbServiceCore.save(n);
				}*/
				// Save Deviation Flag
				if(form.getRank().equals("Failed") 
						|| form.getTenure().equals("Failed")
						|| form.getPosition().equals("Failed")
						|| form.getMaxLoanAmount().equals("Failed")
						|| form.getNthp().equals("Failed")
						|| form.getMaxNoLoan().equals("Failed")) {
					app.setDeviationflag(false);
				}else {
					app.setDeviationflag(true);
				}
				dbServiceCore.saveOrUpdate(app);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public String saveOrUpdateEvaluationTable(Tbloanevaluationtable d) {
		String flag = "failed";
		DBService dbServiceCore = new DBServiceImpl();
		HashMap<String, Object> params = new HashMap<String, Object>();
		try {
			if(d.getId()!=null) {
				params.put("id", d.getId());
				Tbloanevaluationtable row = (Tbloanevaluationtable) dbServiceCore.executeUniqueHQLQuery
						("FROM Tbloanevaluationtable WHERE id=:id", params);
				if(row!=null) {
					d.setUpdatedby(UserUtil.securityService.getUserName());
					d.setDateupdated(new Date());
					if(dbServiceCore.saveOrUpdate(d)) {
						flag = "success";
					}
				}
			}else {
				d.setCreatedby(UserUtil.securityService.getUserName());
				d.setDatecreated(new Date());
				if(dbServiceCore.save(d)) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String saveOrUpdateLoanEvaluationResult(String appno, String cifno) {
		String flag = "failed";
		LoanEvaluationResultForm form = new LoanEvaluationResultForm();
		DBService dbServiceCIF = new DBServiceImplCIF();
		DBService dbServiceCore = new DBServiceImpl();
		HashMap<String, Object> params = new HashMap<String, Object>();
		try {
			params.put("appno", appno);
			params.put("cifno", cifno);
			
			Tblstapp app = (Tblstapp) dbServiceCore.executeUniqueHQLQuery
					("FROM Tblstapp WHERE appno=:appno", params);
			
			Tbcifindividual indiv = (Tbcifindividual) dbServiceCIF.executeUniqueHQLQuery
					("FROM Tbcifindividual WHERE cifno=:cifno", params);
			
			/**Set values**/
			// Qualified Member
			String ruleRank = "";
			String memberRank = "";
			
			if(app.getLoanproduct()!=null) {
				params.put("loanProduct", app.getLoanproduct());
				String qualifiedmember = (String) dbServiceCore.executeUniqueSQLQuery
						("SELECT qualifiedmember FROM Tbloanproduct WHERE productcode=:loanProduct", params);
				if(qualifiedmember!=null) {
					if(qualifiedmember.equals("0")){
						ruleRank.equals("Regular");
					}else if(qualifiedmember.equals("1")){
						ruleRank.equals("Associate");
					}else if(qualifiedmember.equals("2")){
						ruleRank.equals("Both");
					}
				}
			}
			if(indiv.getMembershiptype()!=null) {
				params.put("memberType", indiv.getMembershiptype());
				Tbcodetable code = (Tbcodetable) dbServiceCIF.executeUniqueHQLQuery
						("FROM Tbcodetable WHERE codevalue=:memberType AND codename='MEMBERSHIPTYPE'", params);
				memberRank = (code.getDesc1());
			}

			if(!ruleRank.equals("") && !memberRank.equals("")) {
				if(ruleRank.equals("Both")) {
				}else {
					if(ruleRank.equals("Regular") && memberRank.equals("Regular")) {
						form.setRank("Passed");
					}else if(ruleRank.equals("Regular") && !memberRank.equals("Regular")) {
						form.setRank("Failed");
					}else if(ruleRank.equals("Associate") && memberRank.equals("Associate")) {
						form.setRank("Passed");
					}else if(ruleRank.equals("Associate") && !memberRank.equals("Associate")) {
						form.setRank("Failed");
					}
				}
			}
			
			Tbcifemployment emp = (Tbcifemployment) dbServiceCIF.executeUniqueHQLQuery
					("FROM Tbcifemployment WHERE cifno=:cifno", params);
			if(emp.getDatehiredfrom()!=null && emp.getDatehiredto()!=null) {
				params.put("positionCode", emp.getPosition());
				params.put("tenureMember", DateTimeUtil.yearsDiff(emp.getDatehiredfrom(), emp.getDatehiredto()));
				
				Integer evalTableId = (Integer) dbServiceCore.executeUniqueSQLQuery
						("SELECT id FROM TBLOANEVALUATIONTABLE "
								+ "WHERE template = '102' "
								+ "AND :positionCode BETWEEN minrankcode AND maxrankcode "
								+ "AND :tenureMember BETWEEN mintenure AND maxtenure", params);
				params.put("id", evalTableId);
				Tbloanevaluationtable loanEval = (Tbloanevaluationtable) dbServiceCore.executeUniqueHQLQuery
						("FROM Tbloanevaluationtable WHERE id=:id", params);	
				
					// Tenure
					Integer empTenure =  DateTimeUtil.yearsDiff(emp.getDatehiredfrom(), emp.getDatehiredto());
					if(empTenure >= loanEval.getMintenure() && empTenure <= loanEval.getMaxtenure()) {
						form.setTenure("Passed");
					}else {
						form.setTenure("Failed");
					}
					// Position
					if(Integer.valueOf(emp.getPosition()) >= loanEval.getMinrankcode()  && Integer.valueOf(emp.getPosition()) <= loanEval.getMaxrankcode()) {
						form.setPosition("Passed");
					}else {
						form.setPosition("Failed");
					}
					
					// Max Loan Amount
					Tbaccountinfo info = (Tbaccountinfo) dbServiceCore.executeUniqueHQLQuery
							("FROM Tbaccountinfo WHERE applno=:appno", params);
					
					if(info.getFaceamt().compareTo(loanEval.getMaxloanable()) >0) { // greater than
						form.setMaxLoanAmount("Failed");
					}else {
						form.setMaxLoanAmount("Passed");
					}
					
					// NTHP
					form.setNthp("Passed");
					// Total Loan
					form.setMaxNoLoan("Passed");
				}	
			
				// Save Deviation Flag
				if(form.getRank().equals("Failed") 
						&& form.getTenure().equals("Failed")
						&& form.getPosition().equals("Failed")
						&& form.getMaxLoanAmount().equals("Failed")
						&& form.getNthp().equals("Failed")
						&& form.getMaxNoLoan().equals("Failed")) {
					app.setDeviationflag(false);
				}else {
					app.setDeviationflag(true);
				}
				if(dbServiceCore.saveOrUpdate(app)) {
					flag = "success";
				}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}
