package com.etel.casareports;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbglentries;
import com.etel.casareports.form.CASAAllProductReportForm;
import com.etel.casareports.form.CASAAnalyticalReportForm;
import com.etel.casareports.form.CASAComprehensiveListForm;
import com.etel.casareports.form.CASADataForm;
import com.etel.casareports.form.CASAExceptionalReportForm;
import com.etel.casareports.form.CASAGetMasterListAll;
import com.etel.casareports.form.CASAInterestForm;
import com.etel.casareports.form.CASAParametersForm;
import com.etel.casareports.form.CASASavingReportForm;
import com.etel.casareports.form.CASATimeDepositReportForm;
import com.etel.casareports.form.CASATransactionalReportForm;
import com.etel.casareports.form.CashTransferSlip;
import com.etel.casareports.form.DormatData;
import com.etel.casareports.form.DormatForm;
import com.etel.casareports.form.ElectronicJournalData;
import com.etel.casareports.form.ElectronicJournalForm;
import com.etel.casareports.form.ElectronicJournalResponse;
import com.etel.casareports.form.TDPrintPayoutForm;
import com.etel.casareports.form.TellersBlotterForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.company.CompanyService;
import com.etel.company.CompanyServiceImpl;
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
public class CASAReportsFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level;
	 * recommend changing this to FATAL or ERROR before deploying. For info on these
	 * levels, look for tomcat/log4j documentation
	 */
	public CASAReportsFacade() {
		super(INFO);
	}

	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private Map<String, Object> param = HQLUtil.getMap();
	private DBService dbsrvc = new DBServiceImpl();

	private ReportsFacadeService service = new ReportsFacadeImpl();
	@SuppressWarnings("static-access")
	Map<String, Object> params = new HQLUtil().getMap();
	CASAReportsService srvc = new CASAReportsServiceImpl();

	public ElectronicJournalResponse getEJ(ElectronicJournalForm form) {
		return srvc.getEJ(form);
	}

	public List<ElectronicJournalData> getEJData(ElectronicJournalForm form) {
		return srvc.getEJData(form);
	}

	public List<DormatData> getDormat(DormatForm form) {
		return srvc.getDormat(form);
	}

	public List<CASADataForm> getDepositProductBySub(String prodgroup) {
		return srvc.getDepositProductBySub(prodgroup);
	}
	
	public List<CASADataForm> getDepositProductAll() {
		return srvc.getDepositProductAll();
	}

	// MAR 10-18-2021

	// TRANSACTIONAL REPORT
	public List<CASATransactionalReportForm> getTellerListofTransactionsfortheDay(CASAParametersForm forms) {
		return srvc.getTellerListofTransactionsfortheDay(forms);
	}

	public List<CASATransactionalReportForm> getListofNewlyOpenedAccountsfortheDay(CASAParametersForm forms) {
		return srvc.getListofNewlyOpenedAccountsfortheDay(forms);
	}

	public List<CASATransactionalReportForm> getListofClosedTerminatedPreterminatedAccountsfortheDay(
			CASAParametersForm forms) {
		return srvc.getListofClosedTerminatedPreterminatedAccountsfortheDay(forms);
	}

	public List<CASATransactionalReportForm> getBranchTransactionListfortheDayFinancialSavingChecking(
			CASAParametersForm forms) {
		return srvc.getBranchTransactionListfortheDayFinancialSavingChecking(forms);
	}

	public List<CASATransactionalReportForm> getBranchTransactionListfortheDayFinancialTermProducts(
			CASAParametersForm forms) {
		return srvc.getBranchTransactionListfortheDayFinancialTermProducts(forms);
	}

	public List<CASATransactionalReportForm> getFileMaintenanceUpdateTransactionsfortheDay(CASAParametersForm forms) {
		return srvc.getFileMaintenanceUpdateTransactionsfortheDay(forms);
	}

	public List<CASATransactionalReportForm> getCashTransferMovementsfortheDay(CASAParametersForm forms) {
		return srvc.getCashTransferMovementsfortheDay(forms);
	}

	public List<CASATransactionalReportForm> getListofIssuedCTDPASSBOOKCKBOOKfortheDay(CASAParametersForm forms) {
		return srvc.getListofIssuedCTDPASSBOOKCKBOOKfortheDay(forms);
	}

	public List<CASATransactionalReportForm> getListofLateCheckDepositsfortheDay(CASAParametersForm forms) {
		return srvc.getListofLateCheckDepositsfortheDay(forms);
	}

	public List<CASATransactionalReportForm> getListofClosedAccountsfortheDayPeriod(CASAParametersForm forms) {
		return srvc.getListofClosedAccountsfortheDayPeriod(forms);
	}

	public List<CASATransactionalReportForm> getListofEscheatedAccountsfortheDayPeriod(CASAParametersForm forms) {
		return srvc.getListofEscheatedAccountsfortheDayPeriod(forms);
	}

	public List<CASATransactionalReportForm> getListofAutoReneworAutoRollTermDepositPlacementsFortheDay(
			CASAParametersForm forms) {
		return srvc.getListofAutoReneworAutoRollTermDepositPlacementsFortheDay(forms);
	}

	// BALANCES & SCHEDULES
	// Savings
	public List<CASASavingReportForm> getSAMasterList(CASAParametersForm forms) {
		return srvc.getSAMasterList(forms);
	}

	public List<CASASavingReportForm> getSADailyListofAccountsBelowMinimumBalance(CASAParametersForm forms) {
		return srvc.getSADailyListofAccountsBelowMinimumBalance(forms);
	}

	public List<CASASavingReportForm> getSADailyListofActivatedDormantAccounts(CASAParametersForm forms) {
		return srvc.getSADailyListofActivatedDormantAccounts(forms);
	}

	public List<CASASavingReportForm> getSADailyListofClosedAccounts(CASAParametersForm forms) {
		return srvc.getSADailyListofClosedAccounts(forms);
	}

	public List<CASASavingReportForm> getSADailyListofDormantAccounts5Years(CASAParametersForm forms) {
		return srvc.getSADailyListofDormantAccounts5Years(forms);
	}

	public List<CASASavingReportForm> getSADailyListofDormantAccounts10Years(CASAParametersForm forms) {
		return srvc.getSADailyListofDormantAccounts10Years(forms);
	}

	public List<CASASavingReportForm> getSADailyListofDormantAccountsfallingBelowMinimumBalance2Months(
			CASAParametersForm forms) {
		return srvc.getSADailyListofDormantAccountsfallingBelowMinimumBalance2Months(forms);
	}

	public List<CASASavingReportForm> getSADailyListofDormantAccounts(CASAParametersForm forms) {
		return srvc.getSADailyListofDormantAccounts(forms);
	}

	public List<CASASavingReportForm> getSADailyListofNewAccounts(CASAParametersForm forms) {
		return srvc.getSADailyListofNewAccounts(forms);
	}

	public List<CASASavingReportForm> getSASavingsandPremiumSavingsDeposit(CASAParametersForm forms) {
		return srvc.getSASavingsandPremiumSavingsDeposit(forms);
	}

	public List<CASASavingReportForm> getSASummaryofDailyTransactionReport(CASAParametersForm forms) {
		return srvc.getSASummaryofDailyTransactionReport(forms);
	}

	// Time
	public List<CASATimeDepositReportForm> getTDMasterList(CASAParametersForm forms) {
		return srvc.getTDMasterList(forms);
	}

	public List<CASATimeDepositReportForm> getTDDailyListofAccuredInterestPayable(CASAParametersForm forms) {
		return srvc.getTDDailyListofAccuredInterestPayable(forms);
	}

	public List<CASATimeDepositReportForm> getTDDailyListofMaturedbutUnwithdrawnAccounts(CASAParametersForm forms) {
		return srvc.getTDDailyListofMaturedbutUnwithdrawnAccounts(forms);
	}

	public List<CASATimeDepositReportForm> getTDDailyListofNewPlacements(CASAParametersForm forms) {
		return srvc.getTDDailyListofNewPlacements(forms);
	}

	public List<CASATimeDepositReportForm> getTDDailyListPretermTimeAcctsReport(CASAParametersForm forms) {
		return srvc.getTDDailyListPretermTimeAcctsReport(forms);
	}

	public List<CASATimeDepositReportForm> getTDDailyListofRollOvers(CASAParametersForm forms) {
		return srvc.getTDDailyListofRollOvers(forms);
	}

	// All Product
	public List<CASAAllProductReportForm> getMasterlistofAccountsActive(CASAParametersForm forms) {
		return srvc.getMasterlistofAccountsActive(forms);
	}

	public List<CASAAllProductReportForm> getMasterlistofAccountsBelowMinimumBalance(CASAParametersForm forms) {
		return srvc.getMasterlistofAccountsBelowMinimumBalance(forms);
	}

	public List<CASAAllProductReportForm> getMasterListofAccountsDormant5And10Years(CASAParametersForm forms) {
		return srvc.getMasterListofAccountsDormant5And10Years(forms);
	}

	public List<CASAAllProductReportForm> getScheduleofAccruedInterestPayable(CASAParametersForm forms) {
		return srvc.getScheduleofAccruedInterestPayable(forms);
	}

	public List<CASAAllProductReportForm> getCustomerListofDepositAccounts(CASAParametersForm forms) {
		return srvc.getCustomerListofDepositAccounts(forms);
	}

	public List<CASAAllProductReportForm> getScheduleofAccountswithNegativeBalanceTemporaryOverdraft(
			CASAParametersForm forms) {
		return srvc.getScheduleofAccountswithNegativeBalanceTemporaryOverdraft(forms);
	}

	public List<CASAGetMasterListAll> getMasterListAll(CASAParametersForm forms) {
		return srvc.getMasterListAll(forms);
	}

	// ANALYTICAL REPORTS
	public List<CASAAnalyticalReportForm> getGrossDepositsandWithdrawalfortheQuarter(CASAParametersForm forms) {
		return srvc.getGrossDepositsandWithdrawalfortheQuarter(forms);
	}

	// EXCEPTIONAL REPORTS
	public List<CASAExceptionalReportForm> getListofActivatedDormantAccounts(CASAParametersForm forms) {
		return srvc.getListofActivatedDormantAccounts(forms);
	}

	public List<CASAExceptionalReportForm> getListofAccountswith500tTransactions(CASAParametersForm forms) {
		return srvc.getListofAccountswith500tTransactions(forms);
	}

	public List<CASAExceptionalReportForm> getListofErrorCorrectedTransactionsforthePeriod(CASAParametersForm forms) {
		return srvc.getListofErrorCorrectedTransactionsforthePeriod(forms);
	}

	public List<CASAExceptionalReportForm> getListofRejectedTransactionsforthePeriod(CASAParametersForm forms) {
		return srvc.getListofRejectedTransactionsforthePeriod(forms);
	}

	public List<CASAExceptionalReportForm> getListofTimeoutTransactions(CASAParametersForm forms) {
		return srvc.getListofTimeoutTransactions(forms);
	}

	public List<CASAExceptionalReportForm> getListofOverrideTransactionsforthePeriod(CASAParametersForm forms) {
		return srvc.getListofOverrideTransactionsforthePeriod(forms);
	}

	public List<CASAExceptionalReportForm> getListofAccountsClassifiedtoDormantfortheDay(CASAParametersForm forms) {
		return srvc.getListofAccountsClassifiedtoDormantfortheDay(forms);
	}

	public List<CASAExceptionalReportForm> getOtherBankReturnCheck(CASAParametersForm forms) {
		return srvc.getOtherBankReturnCheck(forms);
	}

	public List<CASAExceptionalReportForm> getListofForceClearTransactions(CASAParametersForm forms) {
		return srvc.getListofForceClearTransactions(forms);
	}

	@SuppressWarnings("unchecked")
	public List<CASADataForm> getTranType() {
		List<CASADataForm> list = new ArrayList<CASADataForm>();
		try {
			String myQuery = "SELECT txcode,txname FROM TBTRANSACTIONCODE";
			System.out.print("MAR " + myQuery);
			list = (List<CASADataForm>) dbsrvc.execSQLQueryTransformer(myQuery, param, CASADataForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Tbglentries> getGLEntriesForTheDay(CASAParametersForm forms) {
		return srvc.getGLEntriesForTheDay(forms);
	}

	public List<CASAComprehensiveListForm> getCASAComprehensive(CASAParametersForm forms) {
		return srvc.getCASAComprehensive(forms);
	}

	@SuppressWarnings("unchecked")
	public List<CASADataForm> getAllDepProdType(String prodcode) {
		List<CASADataForm> list = new ArrayList<CASADataForm>();
		try {
			param.put("prodType", prodcode);
			String myQuery = "SELECT prodgroup, prodcode, prodname, prodsname, currency FROM TBPRODMATRIX";
			System.out.print("MAR " + myQuery);
			list = (List<CASADataForm>) dbsrvc.execSQLQueryTransformer(myQuery, param, CASADataForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// END

	// ------------------------------------PDF/EXCEL------------------------------------
	//updated added tname bname renz 51123 612pm
	//no branch and tellername params so nagadd ako kasi ginagamit sa report- renz 
	public String generateCASA_ListofForceClearTransactions(String imgsrc, String filetype, String companyname,
			Date startdate, Date enddate,String bname,String tname, String branch,String tellername) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		params.put("bname", bname);
		params.put("tname", tname);
		params.put("branch", branch);
		params.put("tellername", tellername);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_ListOfForceClearTransaction", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_ListOfForceClearTransaction", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateCASA_OtherBankReturnCheck(String imgsrc, String filetype, String companyname, Date startdate,
			Date enddate) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_OtherBankReturnCheck", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_OtherBankReturnCheck", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	//updated added tname bname renz 5923 605pm
	public String generateCASA_getGLEntriesForTheDay(String imgsrc, String filetype, String companyname,
			String datefilter, Date businessdate, Date from, Date to, String branch, String tellername,String bname,String tname) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("datefilter", datefilter);
		params.put("businessdate", businessdate);
		params.put("from", from);
		params.put("to", to);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellername", tellername == null ? "" : tellername);
		params.put("bname", bname == null ? "" : bname);
		params.put("tname", tname == null ? "" : tname);
		try {

			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_JournalEntriesfortheDay", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_JournalEntriesfortheDay", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return filepath;
	}

	public String generateCASA_PassbookCover(String accountNo) {
		String filepath = null;
		params.put("accountno", accountNo);
		try {
			filepath = service.executeJasperPDF("CASA_PassbookCover", params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return filepath;
	}

	// Added 12.19.22 Wel
	public String generateCASA_BatchTransactionDeposit(String filetype, String companyname, String branchcode,
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
				filepath = service.executeJasperPDF("CASA_BatchTransactionDeposit", params);
			} else if (filetype.equalsIgnoreCase("EXCEL")) {
				filepath = service.executeJasperXLSX("CASA_BatchTransactionDeposit", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	// Added 12.20.22 Wel
	public String generateCASA_TDPrintPayout(String filetype, String branch, String companyname, String imgsrc,
			String generatedby, TDPrintPayoutForm d) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		Map<String, Object> params = new HQLUtil().getMap();
		ReportsFacadeService service = new ReportsFacadeImpl();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

		// System.out.println(">>> running generateCASA_TDPrintPayout<<<");

		params.put("branch", branch);
		params.put("companyname", companyname);
		params.put("imgsrc", imgsrc);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);

		params.put("acctno", d.getAcctno()); // 1
		params.put("acctname", d.getAcctname()); // 2

		// Original Details
		params.put("bookingdate", formatter.format(d.getBookingdate()));
		params.put("term", d.getTerm());
		params.put("maturitydate", formatter.format(d.getMaturitydate()));
		params.put("placementamount", d.getPlacementamount());
		params.put("accountbalance", d.getAccountbalance());

		params.put("intrate", d.getIntrate());
		params.put("certno", d.getCertno());
		params.put("totalintmaturity", d.getTotalintmaturity());
		params.put("wtax", d.getWtax());
		params.put("netinterest", d.getNetinterest());

		params.put("matvalue", d.getMatvalue());
		params.put("docstamps", d.getDocstamps());
		params.put("disposition", d.getDisposition());
		params.put("dispositionint", d.getDispositionint());
		params.put("dispositionintacctno", d.getDispositionintacctno());

		// Payout Details
		params.put("totalintcredited", d.getTotalintcredited());
		params.put("totalwithholdingtaxdebited", d.getTotalwithholdingtaxdebited());
		params.put("modeofrelease", d.getModeofrelease());

		params.put("transactiontype", d.getTransactiontype());
		params.put("terminateon", formatter.format(d.getTerminateon()));
		params.put("totalnoofdays", d.getTotalnoofdays());

		params.put("actualinterestearned", d.getActualinterestearned());
		params.put("ontermearned", d.getOntermearned());
		params.put("addnlinterestearned", d.getAddnlinterestearned());

		params.put("actualwithholdingtax", d.getActualwithholdingtax());
		params.put("ontermtax", d.getOntermtax());
		params.put("addnlinteresttax", d.getAddnlinteresttax());

		params.put("intcreditrebate", d.getIntcreditrebate());
		params.put("reversal", d.getReversal());
		params.put("totalintposting", d.getTotalintposting());
		params.put("totalwithholdingtaxposting", d.getTotalwithholdingtaxposting());

		params.put("docstamptax", d.getDocstamptax());
		params.put("netproceeds", d.getNetproceeds());
		params.put("lesswidrawninterest", d.getLesswidrawninterest());
		params.put("actualbalanceperaccount", d.getActualbalanceperaccount()); // 37

		System.out.println(params);

		try {
			if (filetype.equalsIgnoreCase("PDF")) {
				filepath = service.executeJasperPDF("CASA_TDPrintPayout2", params);
			} else if (filetype.equalsIgnoreCase("EXCEL")) {
				filepath = service.executeJasperXLSX("CASA_TDPrintPayout2", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	// Added 01.07.23 Wel
	public List<TellersBlotterForm> listTellersBlotter(String branch, String userid, Date from, Date to) {
		return srvc.listTellersBlotter(branch, userid, from, to);
	}

	// Added 01.04.23 Wel
	public String generateCASA_TellersBlotter(String imgsrc, String generatedby, String filetype, String companyname,
			String branch, String userid, Date from, Date to) {
		System.out.println(">>> running generateCASA_TellersBlotter <<<");
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		Map<String, Object> params = new HQLUtil().getMap();
		ReportsFacadeService service = new ReportsFacadeImpl();

		params.put("imgsrc", imgsrc);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);

		params.put("companyname", companyname);
		params.put("branch", branch == null ? "" : branch);
		params.put("userid", userid == null ? "" : userid);
		params.put("from", from);
		params.put("to", to);

		System.out.println(">>> imgsrc - " + imgsrc);
		System.out.println(">>> username - " + username);
		System.out.println(">>> companyname - " + companyname);
		System.out.println(">>> userid - " + userid);
		System.out.println(">>> from - " + from);
		System.out.println(">>> to - " + to);

		try {
			if (filetype.equalsIgnoreCase("PDF")) {
				filepath = service.executeJasperPDF("CASA_TellersBlotter", params);
			} else if (filetype.equalsIgnoreCase("EXCEL")) {
				filepath = service.executeJasperXLSX("CASA_TellersBlotter", params);
			}
			System.out.println(">>> end of generateCASA_TellersBlotter <<<");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	// Added 01.04.23 Wel
	public String generateCASA_CashTransferSlip(String imgsrc, String filetype, String companyname, String branchcode,
			String address, CashTransferSlip d, Date businessDate) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		Map<String, Object> params = new HQLUtil().getMap();
		ReportsFacadeService service = new ReportsFacadeImpl();

		params.put("businessDate", businessDate);
		
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("branchcode", branchcode);
		params.put("address", address);

		// Bills
		params.put("qOneThousand", d.getqOneThousand());
		params.put("qFiveHundred", d.getqFiveHundred());
		params.put("qTwoHundred", d.getqTwoHundred());
		params.put("qOneHundred", d.getqOneHundred());
		params.put("qFifty", d.getqFifty());
		params.put("qTwenty", d.getqTwenty());

		params.put("qTen", d.getqTen());
		params.put("qFive", d.getqFive());
		params.put("qOne", d.getqOne());
		params.put("qTwentyFiveCents", d.getqTwentyFiveCents());
		params.put("qTenCents", d.getqTenCents());
		params.put("qFiveCents", d.getqFiveCents());
		params.put("qOneCent", d.getqOneCent());

		// Coins
		params.put("aOneThousand", d.getaOneThousand());
		params.put("aFiveHundred", d.getaFiveHundred());
		params.put("aTwoHundred", d.getaTwoHundred());
		params.put("aOneHundred", d.getaOneHundred());
		params.put("aFifty", d.getaFifty());
		params.put("aTwenty", d.getaTwenty());

		params.put("aTen", d.getaTen());
		params.put("aFive", d.getaFive());
		params.put("aOne", d.getaOne());
		params.put("aTwentyFiveCents", d.getaTwentyFiveCents());
		params.put("aTenCents", d.getaTenCents());
		params.put("aFiveCents", d.getaFiveCents());
		params.put("aOneCent", d.getaOneCent());

		params.put("totalamount", d.getTotalamount());

		try {
			if (filetype.equalsIgnoreCase("PDF")) {
				filepath = service.executeJasperPDF("CASA_CashTransferSlip", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public List<CASAInterestForm> listCASAInterestAccrual(String branch, String groupby, Date from, Date to) {
		CASAReportsService srvc = new CASAReportsServiceImpl();
		return srvc.listCASAInterestAccrual(branch, groupby, from, to);
	}

	public List<CASAInterestForm> listCASAInterestCredit(String branch, String groupby, Date from, Date to) {
		CASAReportsService srvc = new CASAReportsServiceImpl();
		return srvc.listCASAInterestCredit(branch, groupby, from, to);
	}
	//updated added tname bname renz 51123 612pm
	public String generateCASA_InterestAccrualReport(String filetype, String branch, String groupby, Date from, Date to,
			String company, String imgsrc,String bname) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("branch", branch);
		params.put("groupby", groupby);
		params.put("from", from);
		params.put("to", to);
		params.put("companyname", company);
		params.put("bname", bname);
		params.put("imgsrc", imgsrc);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equalsIgnoreCase("PDF")) {
				filepath = service.executeJasperPDF("CASA_InterestAccrual", params);
			} else if (filetype.equalsIgnoreCase("EXCEL")) {
				filepath = service.executeJasperXLSX("CASA_InterestAccrual", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}
	//updated added tname bname renz 51123 612pm
	public String generateCASA_InterestCreditReport(String filetype, String branch, String groupby, Date from, Date to,
			String company, String imgsrc,String bname) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("branch", branch);
		params.put("groupby", groupby);
		params.put("from", from);
		params.put("to", to);
		params.put("companyname", company);
		params.put("bname", bname);
		params.put("imgsrc", imgsrc);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equalsIgnoreCase("PDF")) {
				filepath = service.executeJasperPDF("CASA_InterestCredit", params);
			} else if (filetype.equalsIgnoreCase("EXCEL")) {
				filepath = service.executeJasperXLSX("CASA_InterestCredit", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}
}
