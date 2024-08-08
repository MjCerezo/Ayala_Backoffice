package com.etel.lmsreport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.etel.lmsreport.form.LMSAgingReportForm;
import com.etel.lmsreport.form.LMSAnalyticalReportForm;
import com.etel.lmsreport.form.LMSBalancesReportForm;
import com.etel.lmsreport.form.LMSCollectorForm;
import com.etel.lmsreport.form.LMSComprehensiveListForm;
import com.etel.lmsreport.form.LMSDataForm;
import com.etel.lmsreport.form.LMSExceptionReportForm;
import com.etel.lmsreport.form.LMSParametersForm;
import com.etel.lmsreport.form.LMSReportForms;
import com.etel.lmsreport.form.LMSUnearnedInterestForm;
import com.etel.lmsreport.form.LMSUserForms;
import com.etel.lmsreport.form.LMSWorkflowProcessReportForm;
import com.etel.reports.ReportsFacadeImpl;
import com.etel.reports.ReportsFacadeService;
import com.etel.utils.HQLUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
import com.wavemaker.runtime.security.SecurityService;
import com.wavemaker.runtime.service.annotations.ExposeToClient;

/**
 * This is a client-facing service class. All public methods will be exposed to
 * the client. Their return values and parameters will be passed to the client
 * or taken from the client, respectively. This will be a singleton instance,
 * shared between all requests.
 * 
 * To log, call the superclass method log(LOG_LEVEL, String) or log(LOG_LEVEL,
 * String, Exception). LOG_LEVEL is one of FATAL, ERROR, WARN, INFO and DEBUG to
 * modify your log level. For info on these levels, look for tomcat/log4j
 * documentation
 */
@ExposeToClient
public class LMSReportFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level;
	 * recommend changing this to FATAL or ERROR before deploying. For info on these
	 * levels, look for tomcat/log4j documentation
	 */
	LMSReportService service = new LMSReportServiceImpl();
	private ReportsFacadeService rptSrvc = new ReportsFacadeImpl();
	Map<String, Object> params = new HQLUtil().getMap();

	public List<LMSReportForms> getLMSReportTransType(String codename) {
		return service.getLMSReportTransType(codename);
	}

	public List<LMSReportForms> getLMSReportTransStatus(String codename) {
		return service.getLMSReportTransType(codename);
	}

	public List<LMSReportForms> getLMSBranch() {
		return service.getLMSBranch();
	}

	public List<Integer> getYears(int startYear) {
		List<Integer> years = new ArrayList<Integer>();
		int endYear = Calendar.getInstance().get(Calendar.YEAR);
		for (int year = startYear; year <= endYear; year++) {
			years.add(year);
		}
		return years;
	}

	public Date convertDateFromMonthAndYear(int month, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);
		Date date = (Date) calendar.getTime();
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date1 = simpleDateFormat.format(date);
		return date;
	}

	public String listOfLoanAccts(String pnno, String fullname, String product, String companycode, String companyname,
			String rpttype) {
		return service.listOfLoanAccts(pnno, fullname, product, companycode, companyname, rpttype);
	}

	// MAr 08-10-2021 TRANSACTIONAL
	public List<LMSDataForm> getListofLoanApplications(LMSParametersForm forms) {
		return service.getListofLoanApplications(forms);
	}

	public List<LMSDataForm> getListofLMSTransactions(LMSParametersForm forms) {
		return service.getListofLMSTransactions(forms);
	}

	public List<LMSDataForm> getListofDecidedLoanApplications(LMSParametersForm forms) {
		return service.getListofDecidedLoanApplications(forms);
	}

	public List<LMSDataForm> getListofLoanReleases(LMSParametersForm forms) {
		return service.getListofLoanReleases(forms);
	}

	public List<LMSDataForm> getInterestAccrualSetupforthePeriod(LMSParametersForm forms) {
		return service.getInterestAccrualSetupforthePeriod(forms);
	}

	public List<LMSDataForm> getJournalEntriesfortheDay(LMSParametersForm forms) {
		return service.getJournalEntriesfortheDay(forms);
	}

	public List<LMSDataForm> getScheduleofReceivedDocumentsSecurities(LMSParametersForm forms) {
		return service.getScheduleofReceivedDocumentsSecurities(forms);
	}

	public List<LMSDataForm> getLoanReleasesPerRange(LMSParametersForm forms) {
		return service.getLoanReleasesPerRange(forms);
	}

	public List<LMSDataForm> getListOfLoanReleasesPerFirmSize(LMSParametersForm forms) {
		return service.getListOfLoanReleasesPerFirmSize(forms);
	}
	
	public List<LMSDataForm> getLoanInterestAndPenaltyComputationWorksheet(LMSParametersForm forms) {
		return service.getLoanInterestAndPenaltyComputationWorksheet(forms);
	}


	// -------------------------------------------------------------AnalyticalReport-----------------------------------------------------
	public List<LMSUserForms> getLoansUsers(String branch) {
		return service.getLoansUsers(branch);
	}

	public List<LMSAnalyticalReportForm> getCollectionReport(LMSParametersForm forms) {
		return service.getCollectionReport(forms);
	}

	public List<LMSAnalyticalReportForm> getScheduleofAccountswithArrearages(LMSParametersForm forms) {
		return service.getScheduleofAccountswithArrearages(forms);
	}

	public List<LMSAnalyticalReportForm> getLoansTargetvsPerformance(LMSParametersForm forms) {
		return service.getLoansTargetvsPerformance(forms);
	}

	public List<LMSAnalyticalReportForm> getLoanGrantPerformanceReviewPerBranchSolicitingOfficerApprovingOfficer(
			LMSParametersForm forms) {
		return service.getLoanGrantPerformanceReviewPerBranchSolicitingOfficerApprovingOfficer(forms);
	}

	public List<LMSAnalyticalReportForm> getLoanGrantPerformanceReviewPerBranch(LMSParametersForm forms) {
		return service.getLoanGrantPerformanceReviewPerBranch(forms);
	}

	public List<LMSAnalyticalReportForm> getLoanCollectionsPerBranchSolicitingOfficerApprovingOfficer(
			LMSParametersForm forms) {
		return service.getLoanCollectionsPerBranchSolicitingOfficerApprovingOfficer(forms);
	}

	public List<LMSAnalyticalReportForm> getListOfLoanAccountsFullyPaidBeforeMaturityDate(LMSParametersForm forms) {
		return service.getListOfLoanAccountsFullyPaidBeforeMaturityDate(forms);
	}

	public List<LMSAnalyticalReportForm> getRecordedLoanIncomeperBranch(LMSParametersForm forms) {
		return service.getRecordedLoanIncomeperBranch(forms);
	}

	// -------------------------------------------------------------ExceptionReport-----------------------------------------------------
	public List<LMSExceptionReportForm> getListofDisapprovedTransactions(LMSParametersForm forms) {
		return service.getListofDisapprovedTransactions(forms);
	}

	public List<LMSExceptionReportForm> getListofUnpostedFinancialTransactions(LMSParametersForm forms) {
		return service.getListofUnpostedFinancialTransactions(forms);
	}

	public List<LMSBalancesReportForm> getListofLoanAccounts(LMSParametersForm forms) {
		return service.getListofLoanAccounts(forms);
	}

	public List<LMSBalancesReportForm> getScheduleofDueAmortizations(LMSParametersForm forms) {
		return service.getScheduleofDueAmortizations(forms);
	}

	public List<LMSBalancesReportForm> getScheduleofOutstandingAccruedInterestReceivables(LMSParametersForm forms) {
		return service.getScheduleofOutstandingAccruedInterestReceivables(forms);
	}

	public List<LMSBalancesReportForm> getScheduleofOutstandingUnearnedInterestDiscounts(LMSParametersForm forms) {
		return service.getScheduleofOutstandingUnearnedInterestDiscounts(forms);
	}

	public List<LMSBalancesReportForm> getBorrowerListofLoanAccounts(LMSParametersForm forms) {
		return service.getBorrowerListofLoanAccounts(forms);
	}

	public List<LMSAnalyticalReportForm> getDelinquencyBucketListDueAmount(LMSParametersForm forms) {
		return service.getDelinquencyBucketListDueAmount(forms);
	}

	public List<LMSAnalyticalReportForm> getDelinquencyBucketListLoanAccount(LMSParametersForm forms) {
		return service.getDelinquencyBucketListLoanAccount(forms);
	}

	// 09-22-2021
	public List<LMSExceptionReportForm> getListofWaivedInterestsPenaltiesOtherCharges(LMSParametersForm forms) {
		return service.getListofWaivedInterestsPenaltiesOtherCharges(forms);
	}

	public List<LMSExceptionReportForm> getListofLoanReleasesFATCARelated(LMSParametersForm forms) {
		return service.getListofLoanReleasesFATCARelated(forms);
	}

	public List<LMSExceptionReportForm> getListofLoanReleasestoPEP(LMSParametersForm forms) {
		return service.getListofLoanReleasestoPEP(forms);
	}

	public List<LMSExceptionReportForm> getListofLoanReleasestoDOSRI(LMSParametersForm forms) {
		return service.getListofLoanReleasestoDOSRI(forms);
	}

	public List<LMSBalancesReportForm> getScheduleofLoanAccountsfromOldStatustoCurrentStatus(LMSParametersForm forms) {
		return service.getScheduleofLoanAccountsfromOldStatustoCurrentStatus(forms);
	}

	public List<LMSBalancesReportForm> getListofLoanAccountswithExcessPaymentBalance(LMSParametersForm forms) {
		return service.getListofLoanAccountswithExcessPaymentBalance(forms);
	}

	public List<LMSBalancesReportForm> getScheduleofLoanAccountswithAccountsReceivableBalance(LMSParametersForm forms) {
		return service.getScheduleofLoanAccountswithAccountsReceivableBalance(forms);
	}

	public List<LMSBalancesReportForm> getScheduleofLoanAccountswithOutstandingLPC(LMSParametersForm forms) {
		return service.getScheduleofLoanAccountswithOutstandingLPC(forms);
	}

	// 10-01-2021
	public List<LMSBalancesReportForm> getScheduleofLoanReleasesperBranch(LMSParametersForm forms) {
		return service.getScheduleofLoanReleasesperBranch(forms);
	}

	public List<LMSBalancesReportForm> getScheduleofLoanReleasesperSolicitingOfficer(LMSParametersForm forms) {
		return service.getScheduleofLoanReleasesperSolicitingOfficer(forms);
	}

	public List<LMSDataForm> getScheduleofHeldSecurities(LMSParametersForm forms) {
		return service.getScheduleofHeldSecurities(forms);
	}

	public List<LMSWorkflowProcessReportForm> getLoanApplicationStatus(int workflowid) {
		return service.getLoanApplicationStatus(workflowid);
	}

	public List<LMSComprehensiveListForm> getLMSComprehensive(LMSParametersForm forms) {
		return service.getLMSComprehensive(forms);
	}

	public List<LMSCollectorForm> getCollectionDueAndOverdue(String branchcode, String areacode, String subareacode,
			String collector, Date asof, Integer page, Integer maxresult) {
		return service.getCollectionDueAndOverdue(branchcode, areacode, subareacode, collector, asof, page, maxresult);
	}

	// unused
	public int getCollectionDueAndOverdueCount(String branchcode, String areacode, String subareacode, String collector,
			Date asof) {
		return service.getCollectionDueAndOverdueCount(branchcode, areacode, subareacode, collector, asof);
	}

	// -------------------------------------------------------------AGING-----------------------------------------------------
	public List<LMSAgingReportForm> getAgingByAmort_Detailed(LMSParametersForm forms) {
		return service.getAgingByAmort_Detailed(forms);
	}

	public List<LMSAgingReportForm> getAgingByAmort_Summary(LMSParametersForm forms) {
		return service.getAgingByAmort_Summary(forms);
	}

	public List<LMSAgingReportForm> getAgingByAmort_Summary_Product(LMSParametersForm forms) {
		return service.getAgingByAmort_Summary_Product(forms);
	}

	public List<LMSAgingReportForm> getAgingByMat_Detailed(LMSParametersForm forms) {
		return service.getAgingByMat_Detailed(forms);
	}

	public List<LMSAgingReportForm> getAgingByMat_Summary(LMSParametersForm forms) {
		return service.getAgingByMat_Summary(forms);
	}

	public List<LMSAgingReportForm> getAgingByMat_Summary_Product(LMSParametersForm forms) {
		return service.getAgingByMat_Summary_Product(forms);
	}

	public List<LMSAgingReportForm> getAgingByPAR_Detailed(LMSParametersForm forms) {
		return service.getAgingByPAR_Detailed(forms);
	}

	public List<LMSAgingReportForm> getAgingByPAR_Summary(LMSParametersForm forms) {
		return service.getAgingByPAR_Summary(forms);
	}

	public List<LMSAgingReportForm> getAgingByPAR_Summary_Product(LMSParametersForm forms) {
		return service.getAgingByPAR_Summary_Product(forms);
	}

	// ------------------------------------------------PDF-------------------------------------------------------
	public String generateCASA_CollectionDueAndOverdue(String filetype, String companyname, Date asof,
			String branchcode, String areacode, String subareacode, String collector, String imgsrc,
			String generatedby) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("asof", asof);
		/*
		 * params.put("branchcode", branchcode); params.put("areacode", areacode);
		 * params.put("subareacode", subareacode); params.put("collector", collector);
		 */
		params.put("branchcode", branchcode == null ? "%" : "%" + branchcode + "%");
		params.put("areacode", areacode == null ? "%" : "%" + areacode + "%");
		params.put("subareacode", subareacode == null ? "%" : "%" + subareacode + "%");
		params.put("collector", collector == null ? "%" : "%" + collector + "%");

		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);

		try {
			if (filetype.equalsIgnoreCase("PDF")) {
				filepath = rptSrvc.executeJasperPDF("LMS_CollectionDueAndOverdue", params);
			} else if (filetype.equalsIgnoreCase("EXCEL")) {
				filepath = rptSrvc.executeJasperXLSX("LMS_CollectionDueAndOverdue", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String generateLMS_AgingbyAmortDetailed(String filetype, String companyname, String agingfilter, Date asof,
			String branch, String imgsrc, String generatedby) {

		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("agingfilter", agingfilter);
		params.put("asof", asof);
		params.put("branch", branch);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);

		try {
			if (filetype.equalsIgnoreCase("PDF")) {
				filepath = rptSrvc.executeJasperPDF("LMS_AgingByAmort_Detailed", params);
			} else {
				filepath = rptSrvc.executeJasperXLSX("LMS_AgingByAmort_Detailed", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String generateLMS_AgingbyAmortSummary(String filetype, String companyname, String agingfilter, Date asof,
			String branch, String imgsrc, String generatedby) {

		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("agingfilter", agingfilter);
		params.put("asof", asof);
		params.put("branch", branch);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);

		try {
			if (filetype.equalsIgnoreCase("PDF")) {
				filepath = rptSrvc.executeJasperPDF("LMS_AgingByAmort_Summary", params);
			} else {
				filepath = rptSrvc.executeJasperXLSX("LMS_AgingByAmort_Summary", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String generateLMS_AgingbyAmortSummaryProd(String filetype, String companyname, String agingfilter,
			Date asof, String branch, String imgsrc, String generatedby) {

		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("agingfilter", agingfilter);
		params.put("asof", asof);
		params.put("branch", branch);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);

		try {
			if (filetype.equalsIgnoreCase("PDF")) {
				filepath = rptSrvc.executeJasperPDF("LMS_AgingByAmort_SummaryProd", params);
			} else {
				filepath = rptSrvc.executeJasperXLSX("LMS_AgingByAmort_SummaryProd", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String generateLMS_AgingbyMatDetailed(String filetype, String companyname, String agingfilter, Date asof,
			String branch, String imgsrc, String generatedby) {

		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("agingfilter", agingfilter);
		params.put("asof", asof);
		params.put("branch", branch);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);

		try {
			if (filetype.equalsIgnoreCase("PDF")) {
				filepath = rptSrvc.executeJasperPDF("LMS_AgingByMat_Detailed", params);
			} else {
				filepath = rptSrvc.executeJasperXLSX("LMS_AgingByMat_Detailed", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String generateLMS_AgingbyMatSummary(String filetype, String companyname, String agingfilter, Date asof,
			String branch, String imgsrc, String generatedby) {

		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("agingfilter", agingfilter);
		params.put("asof", asof);
		params.put("branch", branch);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);

		try {
			if (filetype.equalsIgnoreCase("PDF")) {
				filepath = rptSrvc.executeJasperPDF("LMS_AgingByMat_Summary", params);
			} else {
				filepath = rptSrvc.executeJasperXLSX("LMS_AgingByMat_Summary", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String generateLMS_AgingbyMatSummaryProd(String filetype, String companyname, String agingfilter, Date asof,
			String branch, String imgsrc, String generatedby) {

		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("agingfilter", agingfilter);
		params.put("asof", asof);
		params.put("branch", branch);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);

		try {
			if (filetype.equalsIgnoreCase("PDF")) {
				filepath = rptSrvc.executeJasperPDF("LMS_AgingByMat_SummaryProd", params);
			} else {
				filepath = rptSrvc.executeJasperXLSX("LMS_AgingByMat_SummaryProd", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String generateLMS_AgingbyPARDetailed(String filetype, String companyname, String agingfilter, Date asof,
			String branch, String imgsrc, String generatedby) {

		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("agingfilter", agingfilter);
		params.put("asof", asof);
		params.put("branch", branch);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);

		try {
			if (filetype.equalsIgnoreCase("PDF")) {
				filepath = rptSrvc.executeJasperPDF("LMS_AgingByPAR_Detailed", params);
			} else {
				filepath = rptSrvc.executeJasperXLSX("LMS_AgingByPAR_Detailed", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String generateLMS_AgingbyPARSummary(String filetype, String companyname, String agingfilter, Date asof,
			String branch, String imgsrc, String generatedby) {

		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("agingfilter", agingfilter);
		params.put("asof", asof);
		params.put("branch", branch);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);

		try {
			if (filetype.equalsIgnoreCase("PDF")) {
				filepath = rptSrvc.executeJasperPDF("LMS_AgingByPAR_Summary", params);
			} else {
				filepath = rptSrvc.executeJasperXLSX("LMS_AgingByPAR_Summary", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String generateLMS_AgingbyPARSummaryProd(String filetype, String companyname, String agingfilter, Date asof,
			String branch, String imgsrc, String generatedby) {

		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("agingfilter", agingfilter);
		params.put("asof", asof);
		params.put("branch", branch);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);

		try {
			if (filetype.equalsIgnoreCase("PDF")) {
				filepath = rptSrvc.executeJasperPDF("LMS_AgingByPAR_SummaryProd", params);
			} else {
				filepath = rptSrvc.executeJasperXLSX("LMS_AgingByPAR_SummaryProd", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	// Added 12.19.22 Wel
	public String generateLMS_BatchTransactionLoans(String filetype, String companyname, String branchcode,
			String batchtxrefno, String imgsrc, String generatedby) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		Map<String, Object> params = new HQLUtil().getMap();
		ReportsFacadeService service = new ReportsFacadeImpl();

		params.put("companyname", companyname);
		params.put("branchcode", branchcode);
		params.put("batchtxrefno", batchtxrefno);
		params.put("imgsrc", imgsrc);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);

		try {
			if (filetype.equalsIgnoreCase("PDF")) {
				filepath = service.executeJasperPDF("LMS_BatchTransactionLoans", params);
			} else if (filetype.equalsIgnoreCase("EXCEL")) {
				filepath = service.executeJasperXLSX("LMS_BatchTransactionLoans", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public List<LMSUnearnedInterestForm> listLMSUnearnedInterest(String branch, String groupby, Date from, Date to) {
		LMSReportService srvc = new LMSReportServiceImpl();
		return srvc.listLMSUnearnedInterest(branch, groupby, from, to);
	}

	public String generateCASA_InterestAccrualReport(String filetype, String branch, String groupby, Date from, Date to,
			String company, String imgsrc) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("branch", branch);
		params.put("groupby", groupby);
		params.put("from", from);
		params.put("to", to);
		params.put("companyname", company);
		params.put("imgsrc", imgsrc);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		ReportsFacadeService service = new ReportsFacadeImpl();
		try {
			if (filetype.equalsIgnoreCase("PDF")) {
				filepath = service.executeJasperPDF("LMS_UnearnedInterestDiscounts", params);
			} else if (filetype.equalsIgnoreCase("EXCEL")) {
				filepath = service.executeJasperXLSX("LMS_UnearnedInterestDiscounts", params);
			}
		} catch (Exception e)

		{
			e.printStackTrace();
		}
		return filepath;
	}
	
	public String generateCASA_LoanInterestAndPenaltyComputationWorksheet(String filetype, String imgsrc, Date asOf, String accountno) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("asOf", asOf == null ? "" : asOf);
		params.put("accountno", accountno == null ? "" : accountno);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		ReportsFacadeService service = new ReportsFacadeImpl();
		try {
			if (filetype.equalsIgnoreCase("PDF")) {
				filepath = service.executeJasperPDF("LMS_LoanPaymentWithPenalty", params);
			} else {
				filepath = service.executeJasperXLSX("LMS_LoanPaymentWithPenalty", params);
			}
		} catch (Exception e)

		{
			e.printStackTrace();
		}
		return filepath;
	}
}
