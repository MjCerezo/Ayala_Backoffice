package com.etel.lmsreport;

import java.util.Date;
import java.util.List;

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

public interface LMSReportService {

	List<LMSReportForms> getLMSReportTransType(String codename);
	List<LMSReportForms> getLMSReportTransStatus(String codename);
	String listOfLoanAccts(String pnno, String fullname, String product, String companycode, String companyname, String rpttype);
	List<LMSDataForm> getListofLoanApplications(LMSParametersForm forms);
	List<LMSDataForm> getListofLMSTransactions(LMSParametersForm forms);
	List<LMSDataForm> getListofDecidedLoanApplications(LMSParametersForm forms);
	List<LMSDataForm> getListofLoanReleases(LMSParametersForm forms);
	List<LMSDataForm> getInterestAccrualSetupforthePeriod(LMSParametersForm forms);
	List<LMSDataForm> getJournalEntriesfortheDay(LMSParametersForm forms);
	List<LMSDataForm> getScheduleofReceivedDocumentsSecurities(LMSParametersForm forms);
	List<LMSUserForms> getLoansUsers(String branch);
	List<LMSAnalyticalReportForm> getCollectionReport(LMSParametersForm forms);
	List<LMSAnalyticalReportForm> getScheduleofAccountswithArrearages(LMSParametersForm forms);
	List<LMSExceptionReportForm> getListofDisapprovedTransactions(LMSParametersForm forms);
	List<LMSExceptionReportForm> getListofUnpostedFinancialTransactions(LMSParametersForm forms);
	List<LMSBalancesReportForm> getListofLoanAccounts(LMSParametersForm forms);
	List<LMSBalancesReportForm> getScheduleofDueAmortizations(LMSParametersForm forms);
	List<LMSBalancesReportForm> getScheduleofOutstandingAccruedInterestReceivables(LMSParametersForm forms);
	List<LMSBalancesReportForm> getScheduleofOutstandingUnearnedInterestDiscounts(LMSParametersForm forms);
	List<LMSBalancesReportForm> getBorrowerListofLoanAccounts(LMSParametersForm forms);
	List<LMSAnalyticalReportForm> getDelinquencyBucketListDueAmount(LMSParametersForm forms);
	List<LMSAnalyticalReportForm> getDelinquencyBucketListLoanAccount(LMSParametersForm forms);
	List<LMSExceptionReportForm> getListofWaivedInterestsPenaltiesOtherCharges(LMSParametersForm forms);
	List<LMSBalancesReportForm> getScheduleofLoanAccountsfromOldStatustoCurrentStatus(LMSParametersForm forms);
	List<LMSBalancesReportForm> getListofLoanAccountswithExcessPaymentBalance(LMSParametersForm forms);
	List<LMSBalancesReportForm> getScheduleofLoanAccountswithAccountsReceivableBalance(LMSParametersForm forms);
	List<LMSBalancesReportForm> getScheduleofLoanAccountswithOutstandingLPC(LMSParametersForm forms);
	List<LMSBalancesReportForm> getScheduleofLoanReleasesperBranch(LMSParametersForm forms);
	List<LMSExceptionReportForm> getListofLoanReleasesFATCARelated(LMSParametersForm forms);
	List<LMSExceptionReportForm> getListofLoanReleasestoPEP(LMSParametersForm forms);
	List<LMSExceptionReportForm> getListofLoanReleasestoDOSRI(LMSParametersForm forms);
	List<LMSBalancesReportForm> getScheduleofLoanReleasesperSolicitingOfficer(LMSParametersForm forms);
	List<LMSDataForm> getScheduleofHeldSecurities(LMSParametersForm forms);
	List<LMSWorkflowProcessReportForm> getLoanApplicationStatus(int workflowid);
	List<LMSReportForms> getLMSBranch();
	List<LMSDataForm> getLoanReleasesPerRange(LMSParametersForm forms);
	List<LMSDataForm> getListOfLoanReleasesPerFirmSize(LMSParametersForm forms);
	List<LMSAnalyticalReportForm> getLoansTargetvsPerformance(LMSParametersForm forms);
	List<LMSAnalyticalReportForm> getLoanGrantPerformanceReviewPerBranchSolicitingOfficerApprovingOfficer(
			LMSParametersForm forms);
	List<LMSAnalyticalReportForm> getLoanGrantPerformanceReviewPerBranch(LMSParametersForm forms);
	List<LMSAnalyticalReportForm> getLoanCollectionsPerBranchSolicitingOfficerApprovingOfficer(LMSParametersForm forms);
	List<LMSAnalyticalReportForm> getListOfLoanAccountsFullyPaidBeforeMaturityDate(LMSParametersForm forms);
	List<LMSAnalyticalReportForm> getRecordedLoanIncomeperBranch(LMSParametersForm forms);
	List<LMSComprehensiveListForm> getLMSComprehensive(LMSParametersForm forms);
	
	// Collection Due And Overdue
	List<LMSCollectorForm> getCollectionDueAndOverdue
		(String branchcode, String areacode, String subareacode, String collector, Date asof, Integer page, Integer maxresult);
	int getCollectionDueAndOverdueCount(String branchcode, String areacode, String subareacode, String collector, Date asof);
	List<LMSAgingReportForm> getAgingByPAR_Detailed(LMSParametersForm forms);
	List<LMSAgingReportForm> getAgingByPAR_Summary(LMSParametersForm forms);
	List<LMSAgingReportForm> getAgingByPAR_Summary_Product(LMSParametersForm forms);
	List<LMSAgingReportForm> getAgingByAmort_Detailed(LMSParametersForm forms);
	List<LMSAgingReportForm> getAgingByAmort_Summary(LMSParametersForm forms);
	List<LMSAgingReportForm> getAgingByAmort_Summary_Product(LMSParametersForm forms);
	List<LMSAgingReportForm> getAgingByMat_Detailed(LMSParametersForm forms);
	List<LMSAgingReportForm> getAgingByMat_Summary(LMSParametersForm forms);
	List<LMSAgingReportForm> getAgingByMat_Summary_Product(LMSParametersForm forms);
	List<LMSUnearnedInterestForm> listLMSUnearnedInterest(String branch, String groupby, Date from, Date to);
	List<LMSDataForm> getLoanInterestAndPenaltyComputationWorksheet(LMSParametersForm forms);
	
}
