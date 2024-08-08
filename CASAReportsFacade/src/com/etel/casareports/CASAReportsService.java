/**
 * 
 */
package com.etel.casareports;

import java.util.Date;
import java.util.List;

import com.etel.casareports.form.CASAAnalyticalReportForm;
import com.etel.casareports.form.CASAComprehensiveListForm;
import com.etel.casareports.form.CASADataForm;
import com.coopdb.data.Tbglentries;
import com.etel.casareports.form.CASAAllProductReportForm;
import com.etel.casareports.form.CASAExceptionalReportForm;
import com.etel.casareports.form.CASAGetMasterListAll;
import com.etel.casareports.form.CASAInterestForm;
import com.etel.casareports.form.CASAParametersForm;
import com.etel.casareports.form.CASASavingReportForm;
import com.etel.casareports.form.CASATimeDepositReportForm;
import com.etel.casareports.form.CASATransactionalReportForm;
import com.etel.casareports.form.DormatData;
import com.etel.casareports.form.DormatForm;
import com.etel.casareports.form.ElectronicJournalData;
import com.etel.casareports.form.ElectronicJournalForm;
import com.etel.casareports.form.ElectronicJournalResponse;
import com.etel.casareports.form.TellersBlotterForm;

/**
 * @author ETEL-LAPTOP19
 *
 */
public interface CASAReportsService {

	ElectronicJournalResponse getEJ(ElectronicJournalForm form);

	List<ElectronicJournalData> getEJData(ElectronicJournalForm form);

	List<DormatData> getDormat(DormatForm form);

	List<CASATransactionalReportForm> getTellerListofTransactionsfortheDay(CASAParametersForm forms);

	List<CASATransactionalReportForm> getListofNewlyOpenedAccountsfortheDay(CASAParametersForm forms);

	List<CASATransactionalReportForm> getListofClosedTerminatedPreterminatedAccountsfortheDay(CASAParametersForm forms);

	List<CASATransactionalReportForm> getBranchTransactionListfortheDayFinancialSavingChecking(
			CASAParametersForm forms);

	List<CASATransactionalReportForm> getBranchTransactionListfortheDayFinancialTermProducts(CASAParametersForm forms);

	List<CASAAllProductReportForm> getMasterlistofAccountsActive(CASAParametersForm forms);

	List<CASAAllProductReportForm> getMasterlistofAccountsBelowMinimumBalance(CASAParametersForm forms);

	List<CASAAllProductReportForm> getMasterListofAccountsDormant5And10Years(CASAParametersForm forms);

	List<CASAAnalyticalReportForm> getGrossDepositsandWithdrawalfortheQuarter(CASAParametersForm forms);

	List<CASAExceptionalReportForm> getListofActivatedDormantAccounts(CASAParametersForm forms);

	List<CASASavingReportForm> getSAMasterList(CASAParametersForm forms);

	List<CASASavingReportForm> getSADailyListofAccountsBelowMinimumBalance(CASAParametersForm forms);

	List<CASASavingReportForm> getSADailyListofActivatedDormantAccounts(CASAParametersForm forms);

	List<CASASavingReportForm> getSADailyListofClosedAccounts(CASAParametersForm forms);

	List<CASASavingReportForm> getSADailyListofDormantAccounts5Years(CASAParametersForm forms);

	List<CASASavingReportForm> getSADailyListofDormantAccounts10Years(CASAParametersForm forms);

	List<CASASavingReportForm> getSADailyListofDormantAccountsfallingBelowMinimumBalance2Months(
			CASAParametersForm forms);

	List<CASASavingReportForm> getSADailyListofDormantAccounts(CASAParametersForm forms);

	List<CASASavingReportForm> getSASummaryofDailyTransactionReport(CASAParametersForm forms);

	List<CASASavingReportForm> getSASavingsandPremiumSavingsDeposit(CASAParametersForm forms);

	List<CASASavingReportForm> getSADailyListofNewAccounts(CASAParametersForm forms);

	List<CASATimeDepositReportForm> getTDMasterList(CASAParametersForm forms);

	List<CASATimeDepositReportForm> getTDDailyListofAccuredInterestPayable(CASAParametersForm forms);

	List<CASATimeDepositReportForm> getTDDailyListofMaturedbutUnwithdrawnAccounts(CASAParametersForm forms);

	List<CASATimeDepositReportForm> getTDDailyListofNewPlacements(CASAParametersForm forms);

	List<CASATimeDepositReportForm> getTDDailyListPretermTimeAcctsReport(CASAParametersForm forms);

	List<CASATimeDepositReportForm> getTDDailyListofRollOvers(CASAParametersForm forms);

	List<CASATransactionalReportForm> getFileMaintenanceUpdateTransactionsfortheDay(CASAParametersForm forms);

	List<CASATransactionalReportForm> getCashTransferMovementsfortheDay(CASAParametersForm forms);

	List<CASATransactionalReportForm> getListofIssuedCTDPASSBOOKCKBOOKfortheDay(CASAParametersForm forms);

	List<CASATransactionalReportForm> getListofLateCheckDepositsfortheDay(CASAParametersForm forms);

	List<CASATransactionalReportForm> getListofClosedAccountsfortheDayPeriod(CASAParametersForm forms);

	List<CASATransactionalReportForm> getListofEscheatedAccountsfortheDayPeriod(CASAParametersForm forms);

	List<CASAAllProductReportForm> getScheduleofAccruedInterestPayable(CASAParametersForm forms);

	List<CASAAllProductReportForm> getCustomerListofDepositAccounts(CASAParametersForm forms);

	List<CASAAllProductReportForm> getScheduleofAccountswithNegativeBalanceTemporaryOverdraft(CASAParametersForm forms);

	List<CASAExceptionalReportForm> getListofAccountswith500tTransactions(CASAParametersForm forms);

	List<CASAExceptionalReportForm> getListofErrorCorrectedTransactionsforthePeriod(CASAParametersForm forms);

	List<CASAExceptionalReportForm> getListofRejectedTransactionsforthePeriod(CASAParametersForm forms);

	List<CASAExceptionalReportForm> getListofTimeoutTransactions(CASAParametersForm forms);

	List<CASAExceptionalReportForm> getListofOverrideTransactionsforthePeriod(CASAParametersForm forms);

	List<CASAExceptionalReportForm> getOtherBankReturnCheck(CASAParametersForm forms);

	List<CASAExceptionalReportForm> getListofAccountsClassifiedtoDormantfortheDay(CASAParametersForm forms);

	List<CASADataForm> getDepositProductBySub(String prodgroup);
	
	List<CASADataForm> getDepositProductAll();

	List<CASATransactionalReportForm> getListofAutoReneworAutoRollTermDepositPlacementsFortheDay(
			CASAParametersForm forms);

	List<CASAExceptionalReportForm> getListofForceClearTransactions(CASAParametersForm forms);

	List<Tbglentries> getGLEntriesForTheDay(CASAParametersForm forms);

	List<CASAGetMasterListAll> getMasterListAll(CASAParametersForm forms);

	List<CASAComprehensiveListForm> getCASAComprehensive(CASAParametersForm forms);

	List<TellersBlotterForm> listTellersBlotter(String branch, String userid, Date from, Date to);

	List<CASAInterestForm> listCASAInterestAccrual(String branch, String groupby, Date from, Date to);

	List<CASAInterestForm> listCASAInterestCredit(String branch, String groupby, Date from, Date to);

}
