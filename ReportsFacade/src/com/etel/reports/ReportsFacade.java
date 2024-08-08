package com.etel.reports;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.cifsdb.data.Tbcifindividual;
import com.cifsdb.data.Tbcifmain;
import com.cifsdb.data.Tbreferrors;
import com.coopdb.data.Tblstapp;
import com.coopdb.data.Tbmember;
import com.coopdb.data.Tbtdc;
import com.coopdb.data.Tbtimedeposit;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.common.service.DBServiceImplLOS;
import com.etel.dataentry.FullDataEntryService;
import com.etel.dataentry.FullDataEntryServiceImpl;
import com.etel.reports.forms.AccountOfficersForm;
import com.etel.reports.forms.CurrentAcctReportParam;
import com.etel.reports.forms.ReportApptype;
import com.etel.reports.forms.ReportTellerName;
import com.etel.reports.forms.SavingsReportParam;
import com.etel.reports.forms.SearchApprovedCFForm;
import com.etel.reports.forms.TimeDepParam;
import com.etel.utils.AuditLog;
import com.etel.utils.AuditLogEvents;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;
import com.loansdb.data.Tbcodetable;
import com.loansdb.data.Tbdefaultsignatories;
import com.loansdb.data.Tbteams;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
import com.wavemaker.runtime.security.SecurityService;
import com.wavemaker.runtime.service.annotations.ExposeToClient;

import member.MemberService;
import member.MemberServiceImpl;

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

public class ReportsFacade extends JavaServiceSuperClass {
	private static final String directory = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("/WEB-INF/jasper/");

	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private String username = secservice.getUserName();
	DBService dbServiceCOOP = new DBServiceImpl();

	public ReportsFacade() {
		// super(FATAL);
		String dir = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath("/resources/images");
		File file = new File(dir);
		if (!file.exists()) {
			file.mkdirs();
		}

		File logo = new File(file, "AcaciaBanc.png");
		if (logo.exists()) {
			// System.out.println(logo.getAbsolutePath());
			String aa = "\\\\";
			String a = "\\";
			this.logo = logo.getAbsolutePath().replace(a, aa);
		}
	}

	// Acknowledgement-Sub
	final String AcknowledgementCHDISC = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("WEB-INF/jasper/Acknowledgement-CHDISC.jasper");
	// Client Credit Line Summary

	final String creditLineSummary = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("WEB-INF/jasper/ClientCreditLineSummary_subreport1.jasper");
	// SOA

	final String soa_transactiondetails = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("WEB-INF/jasper/rptSOAMain_transactionDetails.jasper");

	final String soa_acctsummary = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("WEB-INF/jasper/rptSOAMain_acctSummary.jasper");

	// Subreport
	final String fileUrlAfterClearing = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("/WEB-INF/jasper/rptTellerTransactionTotals_AfterClearing.jasper");
	final String fileUrlCash = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("/WEB-INF/jasper/rptTellerTransactionTotals_CashTransactions.jasper");
	final String fileUrlNonCash = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("/WEB-INF/jasper/rptTellerTransactionTotals_NonCashTrans.jasper");

	// Sample Subreport
	final String forPie = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("/WEB-INF/jasper/sampleReportsPie.jasper");
	final String forGraph = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("/WEB-INF/jasper/sampleReportsBar.jasper");

	// MAR 06-14-2021
	// final String cocologo =
	// RuntimeAccess.getInstance().getSession().getServletContext()
	// .getRealPath("WEB-INF/jasper/CocoLife.jpg");
	final String listofloansCOCOLIFE = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("WEB-INF/jasper/Cocolife_ListOfLoans_Sub.jasper");
	final String LAMlogo = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("WEB-INF/jasper/unicapitallogo.png");
	final String LAMamendSUBREPORTS = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("WEB-INF/jasper/");
	final String report1 = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("WEB-INF/jasper/report1_subreport1.jasper");
	final String subloanoffset = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("WEB-INF/jasper/rptSubLoanOffset.jasper");
	final String subfeesandcharges = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("WEB-INF/jasper/rptSubFeesAndCharges.jasper");
	final String subPN = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("WEB-INF/jasper/rptPnRetail_PNSub.jasper");
	final String subLWS = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("WEB-INF/jasper/rptLoanWorksheet_Comakers.jasper");
	final String subPay = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("WEB-INF/jasper/rptLoanWorkSheet_SubPaySched.jasper");
	final String otherDeduction = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("WEB-INF/jasper/rptLoanWorksheet_OtherDeduction.jasper");
	final String picss = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("WEB-INF/jasper/new-page0002.jpg");

	// Subreport PDIC AnnexF
	final String page2 = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("/WEB-INF/jasper/PDIC_ANNEXF_PAGE2.jasper");
	final String page3 = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("/WEB-INF/jasper/PDIC_ANNEXF_PAGE3.jasper");
	final String page4 = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("/WEB-INF/jasper/PDIC_ANNEXF_PAGE4.jasper");
	final String page5 = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("/WEB-INF/jasper/PDIC_ANNEXF_PAGE5.jasper");

	// Disclosure Sub Report
	final String disclosuresubfeesandcharges = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("WEB-INF/jasper/LMS_DisclosureStatementFeesCharge.jasper");
	final String disclosureotherdeduction = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("WEB-INF/jasper/LMS_DisclosureOtherDeduction.jasper");
	final String loanoffset = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("WEB-INF/jasper/LMS_DisclosureLoanOffset.jasper");
	
	
	
	final String SUBREPORT_DIR_COL = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("WEB-INF/jasper/rptLoanVoucerSubReportCollateral.jasper");
	final String SUBREPORT_DIR_COMAKER = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("WEB-INF/jasper/rptLoanVoucerSubReportCoMaker.jasper");
	final String SUBREPORT_DIR_GL = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("WEB-INF/jasper/rptLoanVoucerSubReportGL.jasper");
	//COA SUBREPORT
	final String SUBREPORT_COA_DEPOSIT = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("WEB-INF/jasper/CIF_COA _DEPOSIT.jasper");
	final String SUBREPORT_COA_LOANS = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("WEB-INF/jasper/CIF_COA _LOANS.jasper");
	
	// CASA_DailySummaryofTransactionPerProduct_ActiveDormant
	final String SUBREPORT_ACTIVEDORMANT = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("/WEB-INF/jasper/CASA_DailySummaryofTransactionPerProduct_ActiveDormant.jasper");

	// Loan Disclosure Statement
	final String SUBREPORT_AMORTSCHED = RuntimeAccess.getInstance().getSession().getServletContext()
			.getRealPath("/WEB-INF/jasper/rptLoanDisclosureStatement_AmortSched.jasper");
	
	private String logo;

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	private ReportsFacadeService service = new ReportsFacadeImpl();

	Map<String, Object> params = new HQLUtil().getMap();

	public String authorizationLetter(String id, String name, String filetype) {
		String filepath = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appid", id);
		params.put("membername", name);
		params.put("logo", this.logo);
		try {
			filepath = service.executeJasperPDF("AuthorizationAgreement", params);
			if (filepath != null) {
				AuditLog.addAuditLog(
						AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_GENERATE_AUTHORIZATION_AGREEMENT),
						"User " + username + " Generated " + id + "'s Authorization Agreement .", username, new Date(),
						AuditLogEvents.getEventModule(AuditLogEvents.M_GENERATE_AUTHORIZATION_AGREEMENT));
			}
			return filepath;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String EJournalPDF(String username, Date date) {
		String filepath = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		params.put("date", date);
		filepath = service.executeJasperPDF("ElectronicJournal", params);
		return filepath;
	}

	public String AllAcceptedTrans(String username, Date date) {
		String filepath = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		params.put("date", date);
		filepath = service.executeJasperPDF("AllAcceptedTrans", params);
		return filepath;
	}

	public String ErrorCorrectionTrans() {
		String filepath = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", UserUtil.getUserByUsername(secservice.getUserName()).getUserid());
		filepath = service.executeJasperPDF("ErrorCorrectionTrans", params);
		return filepath;
	}

	public String CashInCashOut(String username, Date date) {
		String filepath = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		params.put("date", date);
		if (username.equals("0001011")) {
			filepath = service.executeJasperPDF("CashInCashOutBoo", params);
		} else {

			filepath = service.executeJasperPDF("CashInCashOut", params);

		}
		return filepath;
	}

	public String TellersTotal(String username, Date date) {
		String filepath = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		params.put("date", date);
		filepath = service.executeJasperPDF("TellersTotal", params);
		return filepath;

	}

	public String AllRejectedTrans(String username, Date date) {
		String filepath = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		params.put("date", date);
		filepath = service.executeJasperPDF("AllRejectedTrans", params);
		return filepath;
	}

	// CED 12-16-2020
	// CED 04-30-2021 added issuedby
	// CED 05-03-2021 added Tbtdc
	@SuppressWarnings("unchecked")
	public String CertTimeDep(String acctno, String address, Date issuedt, Date matdt, String name, Integer term,
			BigDecimal interest, BigDecimal sum, String brname, String tdcno) {
		List<Tbtdc> tdcList = new ArrayList<Tbtdc>();
		String filepath = null;
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("AcctNo", acctno);
		params.put("address", address);
		params.put("issuedt", new Date());
		params.put("matdt", matdt);
		params.put("name", name);
		params.put("interest", interest);
		params.put("sum", sum);
		params.put("brname", brname);
		params.put("tdcno", tdcno);
		tdcList = (List<Tbtdc>) dbsrvc.executeListHQLQuery("FROM Tbtdc WHERE accountno<>:AcctNo and tdcno=:tdcno",
				params);
		if (tdcList != null && tdcList.size() > 0) {
			return "failed";
		}
		Tbtimedeposit td = (Tbtimedeposit) dbsrvc.executeUniqueHQLQuery("FROM Tbtimedeposit WHERE accountno=:AcctNo",
				params);
		params.put("term", td.getTerm());
		filepath = service.executeJasperPDF("CertificateOfTimeDeposit", params);
		dbsrvc.executeUpdate("UPDATE Tbdeposit SET tdcno =:tdcno,tdcreleaseind=1 WHERE accountno=:AcctNo", params);
		Tbtdc tdc = new Tbtdc();
		tdc.setAccountno(acctno);
		tdc.setBookingdate(td.getDtbook());
		tdc.setMaturitydate(matdt);
		tdc.setDocstamps(td.getLessdocstamp());
		tdc.setInterestamt(td.getGrossint());
		tdc.setIssuedate(new Date());
		tdc.setIssuedby(username);
		tdc.setMaturityvalue(td.getMatvalue());
		tdc.setPlacementamt(td.getPlaceamt());
		tdc.setStatus("1");
		tdc.setTdcno(tdcno);
		tdc.setTermindays(td.getTerm());
		tdc.setWtaxamt(td.getLesswtaxamt());
		dbsrvc.saveOrUpdate(tdc);
		return filepath;
	}

	public String AllSupOverridetx(String username, Date date) {
		String filepath = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		params.put("date", date);
		filepath = service.executeJasperPDF("AllSupervisorOverrideTx", params);
		return filepath;
	}

	public String SupOverridetx(String username, Date date) {
		String filepath = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		params.put("date", date);
		filepath = service.executeJasperPDF("AllSupervisorOverrideTx", params);
		return filepath;
	}

	public String AllTimeOutAcceptedtx(String username, Date date) {
		String filepath = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		params.put("date", date);
		filepath = service.executeJasperPDF("AllTimeOutAndAcceptedTrans", params);
		return filepath;
	}

	public String MembershipReport(String reporttype, String branch, String coop, String apptype, Date appdate,
			Date appdatefrom, Date appdateto, Date memdate) {
		String filepath = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("branch", branch);
		params.put("coopcode", coop);
		params.put("applicationdate", appdate);
		params.put("applicanttype", apptype);
		params.put("applicationDateFrom", appdatefrom);
		params.put("applicationDateTo", appdateto);
		params.put("approvaldate", memdate);
		params.put("branchcode", branch);
		params.put("approvalDateFrom", appdatefrom);
		params.put("approvalDateTo", appdateto);
		if (reporttype.equals("AppBranch")) {
			filepath = service.executeJasperPDF("rptCoopListofMembersAppByAppBranch", params);
		}
		if (reporttype.equals("AppDate")) {
			filepath = service.executeJasperPDF("rptCoopListofMembersAppByAppDateDaily", params);
		}
		if (reporttype.equals("AppType")) {
			filepath = service.executeJasperPDF("rptCoopListofMembersAppByAppType", params);
		}
		if (reporttype.equals("AppPeriodic")) {
			filepath = service.executeJasperPDF("rptCoopListofMembersAppByAppDatePeriodic", params);
		}
		if (reporttype.equals("MemDate")) {
			filepath = service.executeJasperPDF("rptMembersPerBranchByDate(Daily)", params);
		}
		if (reporttype.equals("MemPeriodic")) {
			filepath = service.executeJasperPDF("rptMembersPerBranchByiDate(Periodic)", params);
		}
		return filepath;
	}

	public String LoanApplicationReport(String coop, String reporttype, String status, String branch, Date appdate,
			Date appdatefrom, Date appdateto) {
		String filepath = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("branchcode", branch);
		params.put("coopcode", coop);
		params.put("applicationdatefrom", appdatefrom);
		params.put("applicationdateto", appdateto);
		params.put("applicationstatus", status);
		if (reporttype.equals("Branch")) {
			filepath = service.executeJasperPDF("rptListOfApplicationsPerBranch", params);
		}
		if (reporttype.equals("Status")) {
			filepath = service.executeJasperPDF("rptListOfApplicationsByApplicationStatus", params);
		}
		if (reporttype.equals("Branch-Date")) {
			filepath = service.executeJasperPDF("rptListOfApplicationsPerBranchByApplicationDate", params);
		}
		return filepath;
	}

	public String CASASavingsReport(SavingsReportParam parameters) {
		String filepath = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("txdate", parameters.getTxdate());
		params.put("branchcode", parameters.getBranchcode());
		params.put("logo", this.logo);
//		try {
//			ObjectMapper mapper = new ObjectMapper();
//			System.out.println(mapper.writeValueAsString(parameters));
//		} catch (Exception e) {
//			e.printStackTrace();
//			// TODO: handle exception
//		}
		if (parameters.getReporttype() != null && parameters.getFiletype() != null) {
			if (parameters.getReporttype().equals("DTR")) {
				if (parameters.getFiletype().equals("PDF")) {
					filepath = service.executeJasperPDF("SADailyTransactionReport", params);
				}
				if (parameters.getFiletype().equals("Excel")) {
					filepath = service.executeJasperXLSX("SADailyTransactionReport", params);
				}
			}
			if (parameters.getReporttype().equals("DLPT")) {
				if (parameters.getFiletype().equals("PDF")) {
					filepath = service.executeJasperPDF("SADailyListofPostedTransactions", params);
				}
				if (parameters.getFiletype().equals("Excel")) {
					filepath = service.executeJasperXLSX("SADailyListofPostedTransactions", params);
				}
			}
			if (parameters.getReporttype().equals("CFBL")) {
				if (parameters.getFiletype().equals("PDF")) {
					filepath = service.executeJasperPDF("SACASAFullBalanceListing", params);
				}
				if (parameters.getFiletype().equals("Excel")) {
					filepath = service.executeJasperXLSX("SACASAFullBalanceListing", params);
				}
			}
			if (parameters.getReporttype().equals("SDTR")) {
				if (parameters.getFiletype().equals("PDF")) {
					filepath = service.executeJasperPDF("SASummaryofDailyTransactionReport", params);
				}
				if (parameters.getFiletype().equals("Excel")) {
					filepath = service.executeJasperXLSX("SASummaryofDailyTransactionReport", params);
				}
			}
			if (parameters.getReporttype().equals("DTB")) {
				if (parameters.getFiletype().equals("PDF")) {
					filepath = service.executeJasperPDF("SADailyTrialBalance", params);
				}
				if (parameters.getFiletype().equals("Excel")) {
					filepath = service.executeJasperXLSX("SADailyTrialBalance", params);
				}
			}
		}
		return filepath;
	}

	public String TimeDepositReport(TimeDepParam params) {
		String filepath = null;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("txdate", params.getTxdate());
		param.put("branchcode", params.getBranchcode());
		param.put("logo", this.logo);
//		try {
//		ObjectMapper mapper = new ObjectMapper();
//		System.out.println(mapper.writeValueAsString(params));
//	} catch (Exception e) {
//		e.printStackTrace();
//		// TODO: handle exception
//	}		
		if (params.getReporttype() != null && params.getFiletype() != null) {
			if (params.getReporttype().equals("DLMA")) {
				if (params.getFiletype().equals("PDF")) {
					filepath = service.executeJasperPDF("TDDailyListMaturedAcctsReport", param);
				}
				if (params.getFiletype().equals("Excel")) {
					filepath = service.executeJasperXLSX("TDDailyListMaturedAcctsReport", param);
				}
			}
			if (params.getReporttype().equals("DLWTD")) {
				if (params.getFiletype().equals("PDF")) {
					filepath = service.executeJasperPDF("TDDailyListWdrawnTimeAcctsReport", param);
				}
				if (params.getFiletype().equals("Excel")) {
					filepath = service.executeJasperXLSX("TDDailyListWdrawnTimeAcctsReport", param);
				}
			}
			if (params.getReporttype().equals("DLPTTD")) {
				if (params.getFiletype().equals("PDF")) {
					filepath = service.executeJasperPDF("TDDailyListPretermTimeAcctsReport", param);
				}
				if (params.getFiletype().equals("Excel")) {
					filepath = service.executeJasperXLSX("TDDailyListPretermTimeAcctsReport", param);
				}
			}
			if (params.getReporttype().equals("DLUMTD")) {
				if (params.getFiletype().equals("PDF")) {
					filepath = service.executeJasperPDF("TDDailyListUnclaimedMaturedAcctsReport", param);
				}
				if (params.getFiletype().equals("Excel")) {
					filepath = service.executeJasperXLSX("TDDailyListUnclaimedMaturedAcctsReport", param);
				}
			}
			if (params.getReporttype().equals("DLNTD")) {
				if (params.getFiletype().equals("PDF")) {
					filepath = service.executeJasperPDF("TDDailyListNewTimeDepositAcctsReport", param);
				}
				if (params.getFiletype().equals("Excel")) {
					filepath = service.executeJasperXLSX("TDDailyListNewTimeDepositAcctsReport", param);
				}
			}
		}
		return filepath;
	}

	public String getPN(String coopcode, String appno) {
		Map<String, Object> param = new HashMap<String, Object>();
		FullDataEntryService fde = new FullDataEntryServiceImpl();
		MemberService mem = new MemberServiceImpl();
		param.put("appno", appno);
		// param.put("coopcode", coopcode);
		Tblstapp a = fde.getLstapp(appno);
		if (a != null) {
			Tbmember m = mem.getMember(a.getCifno()).getMemberpersonalinformation();
			if (m != null) {
				param.put("coopcode", m.getCoopcode());
				return service.executeJasperPDF("rptPNCoop", param);
			}
		}
		return null;
	}

	public String disclosureStatement(String appno) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("appno", appno);
//		System.out.println(directory + "report1_subreport1.jasper");
		param.put("SUBREPORT_DIR", directory + "/report1_subreport1.jasper");
		return service.executeJasperPDF("rptDisclosureStatement", param);
	}

	public String Loanworksheetamortization(String appno) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("appno", appno);
		String comaker = RuntimeAccess.getInstance().getSession().getServletContext()
				.getRealPath("WEB-INF/jasper/rptLoanWorksheet_Comakers.jasper");
		String feescharges = RuntimeAccess.getInstance().getSession().getServletContext()
				.getRealPath("WEB-INF/jasper/rptSubFeesAndCharges.jasper");
		String loanoffset = RuntimeAccess.getInstance().getSession().getServletContext()
				.getRealPath("WEB-INF/jasper/rptSubLoanOffset.jasper");
		String paysched = RuntimeAccess.getInstance().getSession().getServletContext()
				.getRealPath("WEB-INF/jasper/rptLoanWorkSheet_SubPaySched.jasper");
		param.put("comakerSub", comaker);
		param.put("subfeesandcharges", feescharges);
		param.put("subloanoffset", loanoffset);
		param.put("subPaySched", paysched);
		System.out.println(param);
		return service.executeJasperPDF("rptLoanWorksheet", param);
	}

	public String auditTrail(String type, String module, String user, Date eventdate) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("username", user);
		param.put("module", module);
		if (type.equals("PDF")) {
			return service.executeJasperPDF("rptAuditTrail", param);
		}
		if (type.equals("Excel")) {
			return service.executeJasperXLSX("rptAuditTrail", param);
		}
		return null;
	}

	public String generateLIRF(String appno) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("appno", appno);
		String comaker = RuntimeAccess.getInstance().getSession().getServletContext()
				.getRealPath("WEB-INF/jasper/rptLIRFMain_Comaker.jasper");
		String collateral = RuntimeAccess.getInstance().getSession().getServletContext()
				.getRealPath("WEB-INF/jasper/rptLIRFMain_Collateral.jasper");
		String feesandcharges = RuntimeAccess.getInstance().getSession().getServletContext()
				.getRealPath("WEB-INF/jasper/rptLIRFMain_Fees_and_Charges.jasper");
		String releaseints = RuntimeAccess.getInstance().getSession().getServletContext()
				.getRealPath("WEB-INF/jasper/rptLIRFMain_Release_Inst.jasper");
		param.put("SUBREPORT_DIR", comaker);
		param.put("SUBREPORT_DIR_1", collateral);
		param.put("SUBREPORT_DIR_2", feesandcharges);
		param.put("SUBREPORT_DIR_3", releaseints);
		System.out.println(param);
		return service.executeJasperPDF("rptLIRFMain", param);
	}

	public String generateOR(String accountno) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("accountno", accountno);
		return service.executeJasperPDF("rptOR", param);
	}

	public String MembershipListbyBranch(Date applicationDateFrom, Date applicationDateTo, String br) {
		String filepath = null;
		Map<String, Object> param = new HashMap<String, Object>();
		ReportsFacadeService srvc = new ReportsFacadeImpl();
		param.put("BoS", br);
		param.put("applicationDateFrom", applicationDateFrom);
		param.put("applicationDateTo", applicationDateTo);
		try {
			filepath = srvc.executeJasperPDF("rptListMembershipTypePerBranchService", param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String ListMemberPerMembershipType(Date applicationDateFrom, Date applicationDateTo, String MemType) {
		String filepath = null;
		Map<String, Object> param = new HashMap<String, Object>();
		ReportsFacadeService srvc = new ReportsFacadeImpl();
		param.put("MemType", MemType);
		param.put("applicationDateFrom", applicationDateFrom);
		param.put("applicationDateTo", applicationDateTo);
		try {
			filepath = srvc.executeJasperPDF("rptListMemberPerMembershipType", param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String ListMemberAllMembershipType() {
		String filepath = null;
		Map<String, Object> param = new HashMap<String, Object>();
		ReportsFacadeService srvc = new ReportsFacadeImpl();
		try {
			filepath = srvc.executeJasperPDF("rptListMemberAllMembershipType", param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String ListMemberPerMembershipStatus(Date applicationDateFrom, Date applicationDateTo, String MemStat) {
		String filepath = null;
		Map<String, Object> param = new HashMap<String, Object>();
		ReportsFacadeService srvc = new ReportsFacadeImpl();
		param.put("MemStat", MemStat);
		param.put("applicationDateFrom", applicationDateFrom);
		param.put("applicationDateTo", applicationDateTo);
		try {
			filepath = srvc.executeJasperPDF("	", param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String ListMemberAllMembershipStatus() {
		String filepath = null;
		Map<String, Object> param = new HashMap<String, Object>();
		ReportsFacadeService srvc = new ReportsFacadeImpl();
		try {
			filepath = srvc.executeJasperPDF("rptListMemberAllMembershipSatus", param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String ListMemberPerServiceStatus(Date applicationDateFrom, Date applicationDateTo, String ServStat) {
		String filepath = null;
		Map<String, Object> param = new HashMap<String, Object>();
		ReportsFacadeService srvc = new ReportsFacadeImpl();
		param.put("ServStat", ServStat);
		param.put("applicationDateFrom", applicationDateFrom);
		param.put("applicationDateTo", applicationDateTo);
		try {
			filepath = srvc.executeJasperPDF("rptListMemberPerServiceStatus", param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String ListMemberAllServiceStatus() {
		String filepath = null;
		Map<String, Object> param = new HashMap<String, Object>();
		ReportsFacadeService srvc = new ReportsFacadeImpl();
		try {
			filepath = srvc.executeJasperPDF("rptListMemberAllServiceStatus", param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String ListMemberperAccountOfficer(Date applicationDateFrom, Date applicationDateTo, String AccOfficer) {
		String filepath = null;
		Map<String, Object> param = new HashMap<String, Object>();
		ReportsFacadeService srvc = new ReportsFacadeImpl();
		param.put("AccOfficer", AccOfficer);
		param.put("applicationDateFrom", applicationDateFrom);
		param.put("applicationDateTo", applicationDateTo);
		try {
			filepath = srvc.executeJasperPDF("rptListMemberPerAO", param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	/** Get List of Account Officer per aocode */
	public List<AccountOfficersForm> getListofOfficerPerAocode() {
		ReportsFacadeService codesrvc = new ReportsFacadeImpl();
		return codesrvc.getListofOfficerPerAocode();
	}

	public String DailyTrialBalance(Date txdate, String branchcode) {
		String filepath = null;
		Map<String, Object> param = new HashMap<String, Object>();
		ReportsFacadeService srvc = new ReportsFacadeImpl();
		param.put("txdate", txdate);
		param.put("branchcode", branchcode);
		param.put("logos", this.logo);
		try {
			filepath = srvc.executeJasperPDF("CADailyTrialBalance", param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			filepath = srvc.executeJasperXLSX("CADailyTrialBalance", param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String CurrentAccountReport(CurrentAcctReportParam params) {
		String filepath = null;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("txdate", params.getTxdate());
		param.put("branchcode", params.getBranchcode());
		param.put("logo", this.logo);

		if (params.getReporttype() != null && params.getFiletype() != null) {
			if (params.getReporttype().equals("CADTBR")) {
				if (params.getFiletype().equals("PDF")) {
					filepath = service.executeJasperPDF("CADailyTrialBalance", param);
				}
				if (params.getFiletype().equals("Excel")) {
					filepath = service.executeJasperXLSX("CADailyTrialBalance", param);
				}
			}
			if (params.getReporttype().equals("CADLNA")) {
				if (params.getFiletype().equals("PDF")) {
					filepath = service.executeJasperPDF("CADailyListofNewAccounts", param);
				}
				if (params.getFiletype().equals("Excel")) {
					filepath = service.executeJasperXLSX("CADailyListofNewAccounts", param);
				}
			}
			if (params.getReporttype().equals("CADTR")) {
				if (params.getFiletype().equals("PDF")) {
					filepath = service.executeJasperPDF("CADailyTransactionReport", param);
				}
				if (params.getFiletype().equals("Excel")) {
					filepath = service.executeJasperXLSX("CADailyTransactionReport", param);
				}
			}
			if (params.getReporttype().equals("CASDTR")) {
				if (params.getFiletype().equals("PDF")) {
					filepath = service.executeJasperPDF("CASummaryofDailyTransactionReport", param);
				}
				if (params.getFiletype().equals("Excel")) {
					filepath = service.executeJasperCSV("CASummaryofDailyTransactionReport", param);
				}
			}
			if (params.getReporttype().equals("CAADBR")) {
				if (params.getFiletype().equals("PDF")) {
					filepath = service.executeJasperPDF("CAAverageDailyBalanceReport", param);
				}
				if (params.getFiletype().equals("Excel")) {
					filepath = service.executeJasperXLSX("CAAverageDailyBalanceReport", param);
				}
			}
			if (params.getReporttype().equals("CAFBL")) {
				if (params.getFiletype().equals("PDF")) {
					filepath = service.executeJasperPDF("CASAFullBalanceListing", param);
				}
				if (params.getFiletype().equals("Excel")) {
					filepath = service.executeJasperXLSX("CASAFullBalanceListing", param);
				}
			}
		}
		return filepath;
	}

	// Renz Report Integration 12162020
	public String generateAllErrorCorrected(String filetype, String companyname, Date curdate) {

		String filepath = null;
		params.put("companyname", companyname);
		params.put("curdate", curdate);

		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("rptAllErrorCorrectedTransactions", params);

			} else {

				filepath = service.executeJasperXLSX("rptAllErrorCorrectedTransactionsXLS", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateDailyTransactionReport(String filetype, String companyname, Date curdate) {

		String filepath = null;
		params.put("companyname", companyname);
		params.put("curdate", curdate);

		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("DailyTransactionReport", params);

			} else {

				filepath = service.executeJasperXLSX("DailyTransactionReportXLS", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateDailyListofAccountsBelowMinimumBalance(String filetype, String companyname, Date curdate) {
		String filepath = null;
		params.put("curdate", curdate);
		params.put("companyname", companyname);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("DailyListofAccountsBelowMinimumBalance", params);
			} else {
				filepath = service.executeJasperXLSX("DailyListofAccountsBelowMinimumBalanceXLS", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateDailyListofAccuredInterestPayable(String filetype, String companyname, Date curdate) {

		String filepath = null;

		params.put("curdate", curdate);
		params.put("companyname", companyname);
		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("DailyListofAccuredInterestPayable", params);

			} else {

				filepath = service.executeJasperXLSX("DailyListofAccuredInterestPayableXLS", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateDailyListofActivatedDormantAccounts(String filetype, String companyname, Date curdate) {

		String filepath = null;

		params.put("curdate", curdate);
		params.put("companyname", companyname);
		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("DailyListofActivatedDormantAccounts", params);

			} else {

				filepath = service.executeJasperXLSX("DailyListofActivatedDormantAccountsXLS", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateDailyListofNewAccounts(String filetype, String companyname, Date curdate) {

		String filepath = null;
		params.put("companyname", companyname);
		params.put("curdate", curdate);

		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("DailyListofNewAccounts", params);

			} else {

				filepath = service.executeJasperXLSX("DailyListofNewAccountsAccountsXLS", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateDailyListofNewPlacements(String filetype, Date curdate) {

		String filepath = null;

		params.put("curdate", curdate);

		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("DailyListofNewPlacements", params);

			} else {

				filepath = service.executeJasperXLSX("DailyListofNewPlacementsXLS", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	// Renz Report Integration 01052020

	public String generateDailyListofClosedAccounts(String filetype, String companyname, Date curdate) {

		String filepath = null;

		params.put("curdate", curdate);
		params.put("companyname", companyname);
		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("DailyListofClosedAccounts", params);

			} else {

				filepath = service.executeJasperXLSX("DailyListofClosedAccountsXLS", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateDailyListofDormantAccounts5Years(String filetype, String companyname, Date curdate) {

		String filepath = null;

		params.put("curdate", curdate);
		params.put("companyname", companyname);
		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("DailyListofDormantAccounts5Years", params);

			} else {

				filepath = service.executeJasperXLSX("DailyListofDormantAccounts5YearsXLS", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateDailyListofDormantAccounts10Years(String filetype, String companyname, Date curdate) {

		String filepath = null;
		params.put("companyname", companyname);
		params.put("curdate", curdate);

		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("DailyListofDormantAccounts10Years", params);

			} else {

				filepath = service.executeJasperXLSX("DailyListofDormantAccounts10YearsXLS", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateDailyListofDormantAccountsfallingBelowMinimumBalance2Months(String filetype,
			String companyname, Date curdate) {

		String filepath = null;

		params.put("curdate", curdate);
		params.put("companyname", companyname);
		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("DailyListofDormantAccountsfallingBelowMinimumBalance2Months",
						params);

			} else {

				filepath = service.executeJasperXLSX("DailyListofDormantAccountsfallingBelowMinimumBalance2MonthsXLS",
						params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateDailyListofDormantAccounts(String filetype, String companyname, Date curdate) {

		String filepath = null;

		params.put("curdate", curdate);
		params.put("companyname", companyname);
		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("DailyListofDormantAccounts", params);

			} else {

				filepath = service.executeJasperXLSX("DailyListofDormantAccountsXLS", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generaterptAllRejectedTransactions(String filetype, Date curdate, Boolean errorcorrect) {

		String filepath = null;

		params.put("curdate", curdate);

		params.put("errorcorrect", errorcorrect);

		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("rptAllRejectedTransactions", params);

			} else {

				filepath = service.executeJasperXLSX("rptAllRejectedTransactionsXLS", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generaterptAllSuccessfulTransactions(String filetype, Date curdate, Boolean errorcorrect) {

		String filepath = null;

		params.put("curdate", curdate);

		params.put("errorcorrect", errorcorrect);

		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("rptAllSuccessfulTransactions", params);

			} else {

				filepath = service.executeJasperXLSX("rptAllSuccessfulTransactionsXLS", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generaterptAllTimeOutTransactions(String filetype, Date curdate) {

		String filepath = null;

		params.put("curdate", curdate);

		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("rptAllTimeOutTransactions", params);

			} else {

				filepath = service.executeJasperXLSX("rptAllTimeOutTransactionsXLS", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generaterptAllTransactionReversals(String filetype, Date curdate) {

		String filepath = null;

		params.put("curdate", curdate);

		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("rptAllTransactionReversals", params);

			} else {

				filepath = service.executeJasperXLSX("rptAllTransactionReversalsXLS", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateASOFDATEBALANCES(String filetype, String companyname, Date curdate) {

		String filepath = null;

		params.put("curdate", curdate);
		params.put("companyname", companyname);
		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("ASOFDATEBALANCES", params);

			} else {

				filepath = service.executeJasperXLSX("ASOFDATEBALANCESXLS", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generaterptSummaryofDailyCashInCashOut(String filetype, Date date, String companyname,
			String userid) {

		String filepath = null;

		params.put("date", date);
		params.put("userid", userid);
		params.put("companyname", companyname);
		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("CashIn", params);

			} else {

				filepath = service.executeJasperXLSX("CashInXLS", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generaterptDailyListofMaturedbutUnwithdrawnAccounts(String filetype, String companyname,
			Date curdate) {

		String filepath = null;

		params.put("curdate", curdate);
		params.put("companyname", companyname);
		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("DailyListofMaturedbutUnwithdrawnAccounts", params);

			} else {

				filepath = service.executeJasperXLSX("DailyListofMaturedbutUnwithdrawnAccountsXLS", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateGrossDepositsandWithdrawalfortheQuarter(String filetype, String companyname, Date curdate) {

		String filepath = null;

		params.put("curdate", curdate);
		params.put("companyname", companyname);
		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("GrossDepositsandWithdrawalfortheQuarter", params);

			} else {

				filepath = service.executeJasperXLSX("GrossDepositsandWithdrawalfortheQuarterXLS", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateElectronicJournal(String filetype, String companyname, Date curdate) {

		String filepath = null;

		params.put("curdate", curdate);
		params.put("companyname", companyname);
		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("rptElectronicJournal", params);

			} else {

				filepath = service.executeJasperXLSX("rptElectronicJournalXLS", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateTellersTotal(String filetype, Date businessdate, String companyname, String userid) {

		String filepath = null;

		params.put("businessdate", businessdate);
		params.put("userid", userid);
		params.put("companyname", companyname);
		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("rptTellersTotal", params);

			} else {

				filepath = service.executeJasperXLSX("rptTellersTotalXLS", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	// Dropdown Tellername
	@SuppressWarnings("unchecked")
	public List<ReportTellerName> getListofUsers() {
		List<ReportTellerName> codelist = new ArrayList<ReportTellerName>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			codelist = (List<ReportTellerName>) dbService.execSQLQueryTransformer(
					"SELECT CONCAT(lastname,' ',firstname,' ',middlename) AS tellername,userid from TBUSER", null,
					ReportTellerName.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codelist;
	}

	// 02-01-2021

	public String AllTellerReport(String filetype, Date curdate, String rpttitle, String reporttype, String userid,
			String branchcode, String companyname, String tellername) {

		String filepath = null;

		if (userid == null) {
			userid = "";
		}
		params.put("curdate", curdate);
		params.put("rpttitle", rpttitle);
		params.put("reporttype", reporttype);
		params.put("userid", userid);
		params.put("branchcode", branchcode);
		params.put("tellername", tellername);
		params.put("companyname", companyname);
		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("rptAllTellerReports", params);

			} else {

				filepath = service.executeJasperXLSX("rptAllTellerReportsXLS", params);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	// 02-07-2021

	public String AllDormatReport(String filetype, Date curdate, String rpttitle, String reporttype, String userid,
			String branchcode, String companyname, String tellername) {
		String filepath = null;
		params.put("curdate", curdate);
		params.put("rpttitle", rpttitle);
		params.put("reporttype", reporttype);
		params.put("userid", userid);
		params.put("branchcode", branchcode);
		params.put("tellername", tellername);
		params.put("companyname", companyname);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("AllDormatReport", params);
			} else {
				filepath = service.executeJasperXLSX("AllDormatReportXLS", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String DormatAccountBelowMinimum(String filetype, Date curdate, String rpttitle, String reporttype,
			String userid, String branchcode, String companyname, String tellername) {
		String filepath = null;
		params.put("curdate", curdate);
		params.put("rpttitle", rpttitle);
		params.put("reporttype", reporttype);
		params.put("userid", userid);
		params.put("branchcode", branchcode);
		params.put("tellername", tellername);
		params.put("companyname", companyname);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("DormatAccountBelowMinimum", params);
			} else {
				filepath = service.executeJasperXLSX("DormatAccountBelowMinimumXLS", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String ActiveDormatAccount(String filetype, Date curdate, String rpttitle, String reporttype, String userid,
			String branchcode, String companyname, String tellername) {
		String filepath = null;
		params.put("curdate", curdate);
		params.put("rpttitle", rpttitle);
		params.put("reporttype", reporttype);
		params.put("userid", userid);
		params.put("branchcode", branchcode);
		params.put("tellername", tellername);
		params.put("companyname", companyname);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("ActiveDormatAccount", params);
			} else {
				filepath = service.executeJasperXLSX("ActiveDormatAccountXLS", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	// 02-09-2021
	public String customerListAllCustomerType(String filetype, String companyname) {
		String filepath = null;
		params.put("companyname", companyname);
		params.put("preparedby", username);
		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("rptCustomerListAllCustomerType", params);

			} else {

				filepath = service.executeJasperXLSX("rptCustomerListAllCustomerTypeXLS", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String customerListPerStatus(String filetype, String companyname, String customerType) {
		String filepath = null;
		params.put("customertype", customerType);
		params.put("companyname", companyname);
		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("rptCustomerListPerStatus", params);

			} else {

				filepath = service.executeJasperXLSX("rptCustomerListPerStatusXLS", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String customerListFATCA(String filetype, String companyname, String customerType) {
		String filepath = null;
		params.put("customertype", customerType);
		params.put("companyname", companyname);
		params.put("preparedby", username);
		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("rpt_CustomerListFATCA", params);

			} else {

				filepath = service.executeJasperXLSX("rpt_CustomerListFATCAXLS", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String customerListPEP(String filetype, String companyname, String customerType) {
		String filepath = null;
		params.put("customertype", customerType);
		params.put("companyname", companyname);
		params.put("preparedby", username);
		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("rpt_CustomerListPEP", params);

			} else {

				filepath = service.executeJasperXLSX("rpt_CustomerListPEPXLS", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	// Mar 03-24-2021
	public String inputtedFinancial(String filetype, String frequency, Date date, Date datemonthyear, Date datefrom,
			Date dateto, String transtype, String transstatus, String companyname, String printedby) {
		String filepath = null;
		params.put("frequency", frequency);
		params.put("date", date);
		params.put("datemonthyear", datemonthyear);
		params.put("datefrom", datefrom);
		params.put("dateto", dateto);
		params.put("transtype", transtype);
		params.put("transstatus", transstatus);
		params.put("printedby", printedby);
		params.put("companyname", companyname);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("rptInputtedFinancial", params);
			} else {
				filepath = service.executeJasperXLSX("rptInputtedFinancialXLS", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String loanReleases(String filetype, String frequency, Date date, Date datemonthyear, Date datefrom,
			Date dateto, String companyname, String printedby) {
		String filepath = null;
		params.put("frequency", frequency);
		params.put("date", date);
		params.put("datemonthyear", datemonthyear);
		params.put("datefrom", datefrom);
		params.put("dateto", dateto);
		params.put("printedby", printedby);
		params.put("companyname", companyname);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("rptLoanRelease", params);
			} else {
				filepath = service.executeJasperXLSX("rptLoanReleaseXLS", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String postedFinancial(String filetype, String frequency, Date date, Date datemonthyear, Date datefrom,
			Date dateto, String transtype, String companyname, String printedby) {
		String filepath = null;
		params.put("frequency", frequency);
		params.put("date", date);
		params.put("datemonthyear", datemonthyear);
		params.put("datefrom", datefrom);
		params.put("dateto", dateto);
		params.put("transtype", transtype);
		params.put("printedby", printedby);
		params.put("companyname", companyname);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("rptPostedFinancial", params);
			} else {
				filepath = service.executeJasperXLSX("rptPostedFinancialXLS", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String getReport(String reportName) {
		String reportDate = new SimpleDateFormat("MMddyyyy").format(new Date());
		String rpt = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath("/resources/docdir/")
				+ "\\" + reportDate + "\\" + reportName + reportDate + ".xlsx";
		File r = new File(rpt);
		try {
			if (!r.exists()) {
				return "not found";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "resources/docdir/" + r.getName();
	}

	// renz
	public String generaterptLOSListOfApprovedCF(String filetype, String companyname, Date curdate) {
		String filepath = null;
		params.put("curdate", curdate);
		params.put("companyname", companyname);
		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("rptLOSListOfApprovedCF", params);

			} else {

				filepath = service.executeJasperXLSX("rptLOSListOfApprovedCF", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generaterptLOSListOfLineAvailmentApplication(String filetype, String companyname,
			Date applicationDateFrom, Date applicationDateTo, String applicationtype) {
		String filepath = null;
		params.put("applicationDateFrom", applicationDateFrom);
		params.put("applicationDateTo", applicationDateTo);
		params.put("applicationtype", applicationtype);
		params.put("companyname", companyname);
		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("rptLOSListOfLineAvailmentApplication", params);

			} else {

				filepath = service.executeJasperXLSX("rptLOSListOfLineAvailmentApplication", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generaterptLOSListOfRetailLoanApplication(String filetype, String companyname,
			Date applicationDateFrom, Date applicationDateTo) {
		String filepath = null;
		params.put("applicationDateFrom", applicationDateFrom);
		params.put("applicationDateTo", applicationDateTo);
		params.put("companyname", companyname);
		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("rptLOSListOfRetailLoanApplication", params);

			} else {

				filepath = service.executeJasperXLSX("rptLOSListOfRetailLoanApplication", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generaterptLOSListOfRetailLoanApplicationPerAppStatus(String filetype, String companyname,
			Date applicationDateFrom, Date applicationDateTo, String applicationstatus) {
		String filepath = null;
		params.put("applicationDateFrom", applicationDateFrom);
		params.put("applicationDateTo", applicationDateTo);
		params.put("applicationstatus", applicationstatus);
		params.put("companyname", companyname);
		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("rptLOSListOfRetailLoanApplicationPerAppStatus", params);

			} else {

				filepath = service.executeJasperXLSX("rptLOSListOfRetailLoanApplicationPerAppStatus", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	// DropdownApptype
	@SuppressWarnings("unchecked")
	public List<ReportApptype> getListApptype() {
		List<ReportApptype> codelist = new ArrayList<ReportApptype>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			codelist = (List<ReportApptype>) dbService.execSQLQueryTransformer(
					"SELECT processid,processname,sequenceno from TBWORKFLOWPROCESS where workflowid = '3'", null,
					ReportApptype.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codelist;
	}

	// 04-19-2021
	public String AccrualSched(String filetype, String companyname) {
		String filepath = null;
		Map<String, Object> params = new HashMap<String, Object>();
		ReportsFacadeService service = new ReportsFacadeImpl();
		params.put("companyname", companyname);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("rptLMS_InterestAccuralSchedule", params);
			} else {
				filepath = service.executeJasperXLSX("rptLMS_InterestAccuralSchedule", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String AnnexCheckDiscounting(String appno, String sigName1, String sigName2) {

		String filepath = null;

		Map<String, Object> params = new HashMap<String, Object>();

		ReportsFacadeService srvc = new ReportsFacadeImpl();

		try {

			params.put("appno", appno);
			params.put("sigName1", sigName1 == null ? "" : sigName1);
			params.put("sigName2", sigName2 == null ? "" : sigName2);
			params.put("Acknowledgement", AcknowledgementCHDISC);
			filepath = srvc.executeJasperPDF("Annex-Receivables", params);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return filepath;
	}

	public String creditLineSummary(String cifname, String type, String filetype) {
		String filepath = null;
		Map<String, Object> params = new HashMap<String, Object>();
		ReportsFacadeService service = new ReportsFacadeImpl();
		params.put("cifname", cifname);
		params.put("SUBREPORT_DIR", creditLineSummary);
		try {

			if (type.equals("Consolidated")) {

				if (filetype.equals("PDF")) {
					filepath = service.executeJasperPDF("ClientCreditLineSummary", params);
				} else {
					filepath = service.executeJasperXLSX("ClientCreditLineSummary_XLS", params);
				}

			} else if (type.equals("Per Company")) {

				if (filetype.equals("PDF")) {
					filepath = service.executeJasperPDF("ClientCreditLineSummaryPerCompany", params);
				} else {
					filepath = service.executeJasperXLSX("ClientCreditLineSummaryPerCompany_XLS", params);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;

	}

	public String generateAccountLoanLedger(String pnno, String filetype, String companyname) {

		String filepath = null;

		Map<String, Object> params = new HashMap<String, Object>();

		ReportsFacadeService service = new ReportsFacadeImpl();

		params.put("Pnno", pnno);
		params.put("companyname", companyname);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("InstallmentLedger_PDF", params);
			} else {
				filepath = service.executeJasperXLSX("InstallmentLedger_XLS", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return filepath;
	}

	public String loanReleaseDaily(Date first, String type, String filetype) {

		String filepath = null;

		Map<String, Object> params = new HashMap<String, Object>();

		ReportsFacadeService service = new ReportsFacadeImpl();

		params.put("givenDate", first);

		try {

			if (type.equals("Daily")) {
				if (filetype.equals("PDF")) {

					filepath = service.executeJasperPDF("DailyReleases_A", params);

				} else if (filetype.equals("Excel")) {

					filepath = service.executeJasperXLSX("DailyReleases_A_XLS", params);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String listOfInputtedA(Date givenDate, String filetype) {

		String filepath = null;

		Map<String, Object> params = new HashMap<String, Object>();

		ReportsFacadeService service = new ReportsFacadeImpl();

		params.put("givenDate", givenDate);

		try {

			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("InputtedTransactions_A", params);
			} else {
				filepath = service.executeJasperXLSX("InputtedTransactions_A_XLS", params);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String listOfInputtedB(Date fromDate, Date toDate, String filetype) {

		String filepath = null;

		Map<String, Object> params = new HashMap<String, Object>();

		ReportsFacadeService service = new ReportsFacadeImpl();

		params.put("fromDate", fromDate);
		params.put("toDate", toDate);

		try {
			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("InputtedTransactions_B", params);

			} else {

				filepath = service.executeJasperXLSX("InputtedTransactions_B_XLS", params);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String inventOfLendingsPerCutoffPDF(String filetype, String month, String monthName, String company,
			String year, String companyname) {
		String filepath = null;
		Map<String, Object> params = new HashMap<String, Object>();
		ReportsFacadeService service = new ReportsFacadeImpl();
		params.put("company", company);
		params.put("companyname", companyname);
		params.put("month", month);
		params.put("monthName", monthName);
		params.put("year", year);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("rptLMS_InventoryofLendingsPerCutoff", params);
			} else if (filetype.equals("Excel")) {
				filepath = service.executeJasperXLSX("rptLMS_InventoryofLendingsPerCutoff", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String inventOfLendings(String filetype, String company, String companyname) {
		String filepath = null;
		Map<String, Object> params = new HashMap<String, Object>();
		ReportsFacadeService service = new ReportsFacadeImpl();
		params.put("company", company);
		params.put("companyname", companyname);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("rptLMS_InventoryofLendings", params);
			} else if (filetype.equals("Excel")) {
				filepath = service.executeJasperXLSX("rptLMS_InventoryofLendings", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return filepath;
	}

	public String JournalRegister(Date first, Date second, String type, String filetype, String legveh,
			String companyname) {
		String filepath = null;
		// Map<String, Object>params = new HashMap<String, Object>();
		// ReportsFacadeService service = new ReportsFacadeImpl();
		params.put("datefrom", first);
		params.put("dateto", second);
		params.put("companyid", legveh);
		params.put("companyname", companyname);
		try {
			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("rptLMS_JournalIntegration", params);

			} else if (filetype.equals("Excel")) {

				filepath = service.executeJasperXLSX("rptLMS_JournalIntegration", params);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String loanReleaseOnDemand(Date first, Date second, String type, String filetype) {

		String filepath = null;

		Map<String, Object> params = new HashMap<String, Object>();

		ReportsFacadeService service = new ReportsFacadeImpl();

		params.put("fromDate", first);
		params.put("toDate", second);

		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("OnDemandReleases_B", params);

			} else if (filetype.equals("Excel")) {

				filepath = service.executeJasperXLSX("OnDemandReleases_B_XLS", params);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String postedTransactionB(Date fromDate, Date toDate, String filetype) {

		String filepath = null;

		Map<String, Object> params = new HashMap<String, Object>();

		ReportsFacadeService service = new ReportsFacadeImpl();

		params.put("fromDate", fromDate);
		params.put("toDate", toDate);

		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("PostedTransactions_B", params);

			} else {

				filepath = service.executeJasperXLSX("PostedTransactions_B_XLS", params);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return filepath;
	}

	public String postedTransationA(Date givenDate, String filetype) {

		String filepath = null;

		Map<String, Object> params = new HashMap<String, Object>();

		ReportsFacadeService service = new ReportsFacadeImpl();

		params.put("givenDate", givenDate);

		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("PostedTransactions_A", params);

			} else {

				filepath = service.executeJasperXLSX("PostedTransactions_A_XLS", params);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return filepath;
	}

	@SuppressWarnings("unchecked")
	public List<SearchApprovedCFForm> searchApprovedCf(String cifno, String cifname) {
		List<SearchApprovedCFForm> list = new ArrayList<SearchApprovedCFForm>();
		DBService dbServiceLOS = new DBServiceImplLOS();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			String strQuery = "SELECT DISTINCT cifno, cifname FROM TBAPPROVEDCF";
			if (cifno != null || cifname != null) {
				strQuery += " WHERE cifno IS NOT NULL";
				if (cifno != null) {
					params.put("cifno", "%" + cifno + "%");
					strQuery += " AND cifno LIKE :cifno";
				}
				if (cifname != null) {
					params.put("cifname", "%" + cifname + "%");
					strQuery += " AND cifname LIKE :cifname";
				}
			}
			list = (List<SearchApprovedCFForm>) dbServiceLOS.execSQLQueryTransformer(strQuery, params,
					SearchApprovedCFForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public String generateSOA(String pnno, String filetype, String imgsrc, String companyname, Date startdate,
			Date enddate) {

		String filepath = null;

		Map<String, Object> params = new HashMap<String, Object>();

		ReportsFacadeService service = new ReportsFacadeImpl();

		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("accountno", pnno);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		params.put("companyname", companyname);
		params.put("SUBREPORT_DIR", soa_transactiondetails);
		params.put("SUBREPORT_DIR_ACCOUNTSUMMARRY", soa_acctsummary);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("rptSOAMain", params);
			} else {
				filepath = service.executeJasperXLSX("rptSOAMain", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return filepath;
	}

	public String generaterptAgrarianReform(String filetype, String companyname, Date curdate, String status,
			String preparedby) {
		String filepath = null;
		params.put("curdate", curdate);
		params.put("status", status);
		params.put("preparedby", preparedby);
		params.put("companyname", companyname);
		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("rptAgrarianReform", params);

			} else {

				filepath = service.executeJasperXLSX("rptAgrarianReform", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generaterptCommercialLoans(String filetype, String companyname, Date curdate, String status,
			String preparedby) {
		String filepath = null;
		params.put("curdate", curdate);
		params.put("status", status);
		params.put("preparedby", preparedby);
		params.put("companyname", companyname);
		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("rptCommercialLoans", params);

			} else {

				filepath = service.executeJasperXLSX("rptCommercialLoans", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String getReport(String reportName, Date rptDate) {
		String reportDate = new SimpleDateFormat("MMddyyyy").format(rptDate);
		String rpt = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath("/resources/docdir/")
				+ "\\" + reportDate + "\\" + reportName + reportDate + ".xlsx";
		System.out.println(rpt);
		File r = new File(rpt);
		try {
			if (!r.exists()) {
				return "not found";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "resources/docdir/" + reportDate + "/" + r.getName();
	}

	// 4-28-2021
	public String generateDETAILSOFLATESTPAYMENTLOANS(String filetype, Date curdate) {
		String filepath = null;
		params.put("curdate", curdate);

		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("DETAILSOFLATESTPAYMENTLOANS", params);

			} else {

				filepath = service.executeJasperXLSX("DETAILSOFLATESTPAYMENTLOANS", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generaterptPromissoryNote(String filetype, String imgsrc, String pnno, String applno,
			String companyname) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("pnno", pnno);
		params.put("companyname", companyname);
		params.put("applno", applno);

		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("rptPromissoryNote", params);

			} else {

				filepath = service.executeJasperXLSX("rptPromissoryNote", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generaterptLoansLitigation(String filetype, String companyname, Date curdate) {
		String filepath = null;
		params.put("curdate", curdate);
		params.put("companyname", companyname);
		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("rptLoansLitigation", params);

			} else {

				filepath = service.executeJasperXLSX("rptLoansLitigation", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generaterptDisclosure(String filetype, String imgsrc, String applno, String companyname) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("applno", applno);
		params.put("companyname", companyname);
		params.put("subfeesandcharges", disclosuresubfeesandcharges);
		params.put("subdisclosureotherdeduction", disclosureotherdeduction);
		params.put("loanoffset", loanoffset);

		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("rptDisclosure", params);

			} else {

				filepath = service.executeJasperXLSX("rptDisclosure", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateDOSRIHOLDOUTLOANS(String filetype, String companyname) {
		String filepath = null;
		params.put("companyname", companyname);
		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("DOSRIHOLDOUTLOANS", params);

			} else {

				filepath = service.executeJasperXLSX("DOSRIHOLDOUTLOANS", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generaterptMLClientsLoans(String filetype) {
		String filepath = null;

		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("rptMLClientsLoans", null);

			} else {

				filepath = service.executeJasperXLSX("rptMLClientsLoans", null);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generaterptOtherAgriculturalCreditLoans(String filetype, String companyname, Date curdate,
			String status, String preparedby) {
		String filepath = null;
		params.put("curdate", curdate);
		params.put("status", status);
		params.put("preparedby", preparedby);
		params.put("companyname", companyname);
		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("rptOtherAgriculturalCreditLoans", params);

			} else {

				filepath = service.executeJasperXLSX("rptOtherAgriculturalCreditLoans", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generaterptRealEstateLoan(String filetype, String companyname, Date curdate, String status,
			String preparedby) {
		String filepath = null;
		params.put("curdate", curdate);
		params.put("status", status);
		params.put("preparedby", preparedby);
		params.put("companyname", companyname);
		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("rptRealEstateLoan", params);

			} else {

				filepath = service.executeJasperXLSX("rptRealEstateLoan", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generaterptTellerTransactionTotals(String filetype, Date curdate, String userid,
			String clearingdays) {

		String filepath = null;

		params.put("SUBREPORT_AFTERCLEARING", fileUrlAfterClearing);
		params.put("SUBREPORT_CASHTRANSACTION", fileUrlCash);
		params.put("SUBREPORT_NONCASHTRANSACTIONS", fileUrlNonCash);

		params.put("datefrom", curdate);
		params.put("dateto", curdate);
		params.put("userid", userid);
		params.put("clearingdays", clearingdays);

		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("rptTellerTransactionTotals", params);

			} else {

				filepath = service.executeJasperXLSX("rptTellerTransactionTotals", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateSummaryOfCreditReview(String filetype, String companyname, String appno, String BAPNFIS,
			String CMAPResult, String CICResult, String BlacklistInternalResult, String BlacklistExternalResult,
			String AMLAWatchlistResult, String PDRNResult, String EVRResult, String BankCheckResult,
			String CreditCheckResult, String TradeCheckResult) {
		String filepath = null;
		params.put("appno", appno);
		params.put("companyname", companyname);
		params.put("BAPNFIS", BAPNFIS);
		params.put("CMAPResult", CMAPResult);
		params.put("CICResult", CICResult);
		params.put("BlacklistInternalResult", BlacklistInternalResult);
		params.put("BlacklistExternalResult", BlacklistExternalResult);
		params.put("AMLAWatchlistResult", AMLAWatchlistResult);
		params.put("PDRNResult", PDRNResult);
		params.put("EVRResult", EVRResult);
		params.put("BankCheckResult", BankCheckResult);
		params.put("CreditCheckResult", CreditCheckResult);
		params.put("TradeCheckResult", TradeCheckResult);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("LAS_SummaryOfCreditReview", params);
			} else {
				filepath = service.executeJasperXLSX("LAS_SummaryOfCreditReview", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	// LMS REPORTS
	public String generaterptLMS_ageingRreport(String filetype) {
		String filepath = null;
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("rptLMS_ageingRreport", null);
			} else {
				filepath = service.executeJasperXLSX("rptLMS_ageingRreport", null);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generaterptLMS_DelinquencyBucketList(String filetype, String productcode, String companyname) {
		String filepath = null;
		params.put("productcode", productcode);
		params.put("companyname", companyname);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("rptLMS_DelinquencyBucketList", params);
			} else {
				filepath = service.executeJasperXLSX("rptLMS_DelinquencyBucketList", params);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generaterptLMS_CollectionReport(String filetype, String company, String companyname, Date START_DATE,
			Date END_DATE) {
		String filepath = null;
		params.put("company", company);
		params.put("companyname", companyname);
		params.put("START_DATE", START_DATE);
		params.put("END_DATE", END_DATE);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("rptLMS_CollectionReport", params);
			} else {
				filepath = service.executeJasperXLSX("rptLMS_CollectionReport", params);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generaterptLMS_LoanTrialBalance(String filetype, String company, String companyname) {
		String filepath = null;
		params.put("company", company);
		params.put("companyname", companyname);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("rptLMS_LoanTrialBalance", params);
			} else {
				filepath = service.executeJasperXLSX("rptLMS_LoanTrialBalance", params);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generaterptLMS_DetailedAccountingEntriesForTheDay_product(String filetype, String company,
			String companyname, String product, Date txdate) {
		String filepath = null;
		params.put("company", company);
		params.put("companyname", companyname);
		params.put("product", product);
		params.put("txdate", txdate);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("rptLMS_DetailedAccountingEntriesForTheDay_product", params);
			} else {
				filepath = service.executeJasperXLSX("rptLMS_DetailedAccountingEntriesForTheDay_product", params);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	// MAR 06-15-2021
	public String cocoLife(String applno, String companyname) {
		String filepath = null;

		ReportsFacadeService srvc = new ReportsFacadeImpl();

		Map<String, Object> params = new HashMap<String, Object>();
		try {
			params.put("applno", applno);
			params.put("companyname", companyname);
			params.put("SUBREPORT_DIR_LISTOFLOANS", listofloansCOCOLIFE);
			filepath = srvc.executeJasperPDF("Cocolife_Form", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String billing(String appno, String companyname) {

		String filepath = null;

		ReportsFacadeService srvc = new ReportsFacadeImpl();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appno", appno);
		params.put("companyname", companyname);
		try {
			params.put("appno", appno);
			params.put("companyname", companyname);
			filepath = srvc.executeJasperPDF("rptOfficialBillingUI", params);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String loanWorksheet(String appno) {
		String filepath = null;
		ReportsFacadeService service = new ReportsFacadeImpl();
		Map<String, Object> params = new HashMap<String, Object>();
		DBService dbServiceCIF = new DBServiceImplCIF();
		DBService dbServiceLOS = new DBServiceImplLOS();
		Tbcodetable codecif = new Tbcodetable();
		Tbteams teams = new Tbteams();
		Tbreferrors ref = new Tbreferrors();
		Tbcifmain cifmain = new Tbcifmain();
		try {
			params.put("appno", appno);
			Tblstapp lst = (Tblstapp) dbServiceLOS.executeUniqueHQLQuery("FROM Tblstapp Where appno=:appno", params);
			if (lst != null) {
				if (lst.getReferraltype() != null) {
					params.put("reftype", lst.getReferraltype());
					codecif = (Tbcodetable) dbServiceLOS.executeUniqueHQLQuery(
							"FROM Tbcodetable WHERE codename='REFERRALTYPE' AND codevalue=:reftype", params);
					params.put("team", lst.getLosoriginatingteam());
					teams = (Tbteams) dbServiceLOS.executeUniqueHQLQuery("FROM Tbteams WHERE teamcode=:team", params);
					params.put("cifno", lst.getCifno());
					cifmain = (Tbcifmain) dbServiceCIF.executeUniqueHQLQuery("FROM Tbcifmain Where cifno=:cifno",
							params);
					/*
					 * if (lst.getReferrorname()==null || lst.getReferrorname().equals("") ||
					 * lst.getReferrorname().equalsIgnoreCase("null")) { params.put("referralType",
					 * ""); }else {
					 * 
					 * }
					 */
					String referrorname = "";
					String referraltype = "";
					if (codecif != null) {
						referraltype = codecif.getDesc1();
					}
					params.put("id", lst.getReferrorname() == null ? "" : lst.getReferrorname());
					ref = (Tbreferrors) dbServiceCIF.executeUniqueHQLQuery("FROM Tbreferrors WHERE referrorname=:id",
							params);
					if (ref != null) {
						referrorname = " | " + (ref.getReferrorname());
					}
					params.put("referralType",
							lst.getReferraltype().equals("3") ? "In-House" : referraltype + referrorname);
				}
				System.out.println(cifmain.getAssignedto() + " zdddd");
				String originatedby = cifmain.getAssignedto() + (teams == null ? "" : " | " + teams.getTeamcode());
				params.put("cifno", lst.getCifno());
				Tbcifindividual indiv = (Tbcifindividual) dbServiceCIF
						.executeUniqueHQLQuery("FROM Tbcifindividual WHERE cifno=:cifno", params);
				if (indiv != null) {
					String firstname = indiv.getFirstname();
					String middlename = "";
					String lastname = indiv.getLastname();
					;
					String suffix = "";
					if (!indiv.getMiddlename().equals("") || indiv.getMiddlename() != null) {
						middlename = indiv.getMiddlename();
					} else {
						middlename = "";
					}
					if (!indiv.getSuffix().equals("") || indiv.getSuffix() != null) {
						suffix = indiv.getSuffix();
					} else {
						suffix = "";
					}
					params.put("paramNameProper", firstname + " " + middlename + " " + lastname + " " + suffix);
				}
				params.put("originatedBy", originatedby);
				// params.put("subloanoffset", subloanoffset);
				// params.put("subfeesandcharges", subfeesandcharges);
				// params.put("comakerSub", subLWS);
				// params.put("subPaySched", subPay);
				filepath = service.executeJasperPDF("rptLoanWorksheet", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public Tbdefaultsignatories getSignatories(String appno) {
		DBService srvc = new DBServiceImplLOS();
		Map<String, Object> params = new HashMap<String, Object>();
		Tbdefaultsignatories def = new Tbdefaultsignatories();
		Tblstapp app = new Tblstapp();
		params.put("appno", appno);
		params.put("date", new SimpleDateFormat("EEEE").format(new Date()));
		try {
			app = (Tblstapp) srvc.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", params);
			params.put("company", app.getCompanycode());
			def = (Tbdefaultsignatories) srvc.executeUniqueHQLQuery(
					"FROM Tbdefaultsignatories WHERE companycode=:company AND day=:date", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return def;
	}

	// Reports on DocumentChecklist?
	public String generaterptOfficialBillingUI(String companyname, String imgsrc, String appno) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("appno", appno);
		try {
			filepath = service.executeJasperPDF("rptOfficialBillingUI", params);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateCocolife_Form(String companyname, String applno) {
		String filepath = null;
		params.put("companyname", companyname);
		params.put("applno", applno);
		params.put("SUBREPORT_DIR_LISTOFLOANS", listofloansCOCOLIFE);
		try {
			filepath = service.executeJasperPDF("Cocolife_Form", params);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generaterptLoanWorksheet(String companyname, String imgsrc, String appno, String createdby,
			String generatedby) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("appno", appno);
		params.put("createdby", createdby);
		params.put("subloanoffset", subloanoffset);
		params.put("subfeesandcharges", subfeesandcharges);
		params.put("comakerSub", subLWS);
		params.put("subPaySched", subPay);
		params.put("otherDeduction", otherDeduction);

		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			filepath = service.executeJasperPDF("rptLoanWorksheet", params);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	// MAR 07-01-2021
	public String generaterptListOfOutstandingLoans(String filetype, String companyname, Date curdate,
			String reportname, String loantype, String preparedby) {
		String filepath = null;
		params.put("curdate", curdate);
		params.put("reportname", reportname);
		params.put("loantype", loantype);
		params.put("preparedby", preparedby);
		params.put("companyname", companyname);
		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("rptListOfOutstandingLoans", params);

			} else {

				filepath = service.executeJasperXLSX("rptListOfOutstandingLoans", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generaterptClientListByRisk(String filetype, String companyname) {
		String filepath = null;
		params.put("companyname", companyname);
		params.put("preparedby", username);
		try {

			if (filetype.equals("PDF")) {

				filepath = service.executeJasperPDF("rptClientListByRisk", params);

			} else {

				filepath = service.executeJasperXLSX("rptClientListByRisk", params);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generaterptLMS_ScheduleOfAccountWithArrearage(String filetype, String companyname, Date curdate) {
		String filepath = null;
		params.put("companyname", companyname);
		params.put("curdate", curdate);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("rptLMS_ScheduleOfAccountWithArrearage", params);
			} else {
				filepath = service.executeJasperXLSX("rptLMS_ScheduleOfAccountWithArrearage", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generaterptLMS_DelinquencyAgeingByProduct(String filetype, String companyname, String loantype) {
		String filepath = null;
		params.put("companyname", companyname);
		params.put("loantype", loantype);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("rptLMS_DelinquencyAgeingByProduct", params);
			} else {
				filepath = service.executeJasperXLSX("rptLMS_DelinquencyAgeingByProduct", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	// Renz

	// LMS Exceptions Report
	public String generateLMS_ListofDisapprovedTransactions(String filetype, String imgsrc, String companyname,
			Date startdate, Date enddate, String branch, String officer) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		params.put("branch", branch);
		params.put("officer", officer);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("LMS_ListofDisapprovedTransactions", params);
			} else {
				filepath = service.executeJasperXLSX("LMS_ListofDisapprovedTransactions", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateLMS_ListofUnpostedFinancialTransactions(String filetype, String imgsrc, String companyname,
			Date startdate, Date enddate, String datefilter, Date asof, String branch, String officer) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("datefilter", datefilter);
		params.put("businessDate", asof);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		params.put("branch", branch == null ? "" : branch);
		params.put("officer", officer == null ? "" : officer);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("LMS_ListofUnpostedFinancialTransactions", params);
			} else {
				filepath = service.executeJasperXLSX("LMS_ListofUnpostedFinancialTransactions", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateMIS_ListofLoanReleasestoPEP(String filetype, String imgsrc, String companyname,
			String datefilter, Date asof, Date from, Date to, String branch, String officer) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("datefilter", datefilter);
		params.put("asof", asof);
		params.put("from", from);
		params.put("to", to);
		params.put("branch", branch == null ? "" : branch);
		params.put("officer", officer == null ? "" : officer);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("MIS_ListofLoanReleasestoPEP", params);
			} else {
				filepath = service.executeJasperXLSX("MIS_ListofLoanReleasestoPEP", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateMIS_ListofLoanReleasestoFATCA(String filetype, String imgsrc, String companyname,
			String datefilter, Date asof, Date from, Date to, String branch, String officer) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("datefilter", datefilter);
		params.put("asof", asof);
		params.put("from", from);
		params.put("to", to);
		params.put("branch", branch == null ? "" : branch);
		params.put("officer", officer == null ? "" : officer);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("MIS_ListofLoanReleasestoFATCA", params);
			} else {
				filepath = service.executeJasperXLSX("MIS_ListofLoanReleasestoFATCA", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateMIS_ListofLoanReleasestoDOSRI(String filetype, String imgsrc, String datefilter, Date asof,
			Date from, Date to, String companyname, String branch, String officer) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("datefilter", datefilter);
		params.put("asof", asof);
		params.put("from", from);
		params.put("to", to);
		params.put("branch", branch == null ? "" : branch);
		params.put("officer", officer == null ? "" : officer);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("MIS_ListofLoanReleasestoDOSRI", params);
			} else {
				filepath = service.executeJasperXLSX("MIS_ListofLoanReleasestoDOSRI", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	// Exceptions End

	// LMS Analytical Report
	public String generateLMS_CollectionReport(String filetype, String imgsrc, String companyname, Date startdate,
			Date enddate, String branch, String loanproduct, String accountstat, String sourcepayment, String officer,
			String generatedby) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("startdate", startdate == null ? "" : startdate);
		params.put("enddate", enddate == null ? "" : enddate);
		params.put("branch", branch == null ? "" : branch);
		params.put("officer", officer == null ? "" : officer);
		params.put("loanproduct", loanproduct == null ? "" : loanproduct);
		params.put("accountstat", accountstat == null ? "" : accountstat);
		params.put("sourcepayment", sourcepayment == null ? "" : sourcepayment);

		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("LMS_CollectionReport", params);
			} else {
				filepath = service.executeJasperXLSX("LMS_CollectionReport", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateLMS_ScheduleofAccountswithArrearages(String filetype, String imgsrc, String companyname,
			Date asof, String branch, String officer) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("asof", asof);
		params.put("branch", branch);
		params.put("officer", officer);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("LMS_ScheduleofAccountswithArrearages", params);
			} else {
				filepath = service.executeJasperXLSX("LMS_ScheduleofAccountswithArrearages", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateLMS_DelinquencyBucketListDueAmount(String filetype, String imgsrc, String companyname,
			Date asof, String loanproduct, String branch, String officer, String generatedby) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("asOf", asof == null ? "" : asof);
		params.put("branch", branch == null ? "" : branch);
		params.put("officer", loanproduct == null ? "" : loanproduct);

		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("LMS_DelinquencyBucketListDueAmount", params);
			} else {
				filepath = service.executeJasperXLSX("LMS_DelinquencyBucketListDueAmount", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateLMS_DelinquencyBucketListLoanAmount(String filetype, String imgsrc, String companyname,
			Date asof, String loanproduct, String branch, String officer) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("asof", asof);
		params.put("branch", branch);
		params.put("officer", officer);
		params.put("loanproduct", loanproduct);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("LMS_DelinquencyBucketListLoanAmount", params);
			} else {
				filepath = service.executeJasperXLSX("LMS_DelinquencyBucketListLoanAmount", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	// Analytical End

	// LMS Balance and Sched Report

	public String generateLMS_ScheduleofOutstandingUnearnedInterestDiscounts(String filetype, String imgsrc,
			String companyname, String datefilter, Date asof, Date from, Date to, String loanproduct, String branch,
			String officer) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("datefilter", datefilter);
		params.put("asof", asof);
		params.put("from", from);
		params.put("to", to);
		params.put("branch", branch == null ? "" : branch);
		params.put("officer", officer == null ? "" : officer);
		params.put("loanproduct", loanproduct == null ? "" : loanproduct);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("LMS_ScheduleofOutstandingUnearnedInterestDiscounts", params);
			} else {
				filepath = service.executeJasperXLSX("LMS_ScheduleofOutstandingUnearnedInterestDiscounts", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateLMS_ListofLoanAccounts(String filetype, String imgsrc, String companyname, Date startdate,
			Date enddate, Date transdate, String accountstatus, String borrowertype, String loanproduct, String officer,
			String branch, String generatedby) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("startdate", startdate == null ? "" : startdate);
		params.put("enddate", enddate == null ? "" : enddate);
		params.put("branch", branch == null ? "" : branch);
		params.put("officer", officer == null ? "" : officer);
		params.put("borrowerType", borrowertype == null ? "" : borrowertype);
		params.put("loanProduct", loanproduct == null ? "" : loanproduct);
		params.put("accountStatus", accountstatus == null ? "" : accountstatus);

		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("LMS_ListofLoanAccounts", params);
			} else {
				filepath = service.executeJasperXLSX("LMS_ListofLoanAccounts", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateLMS_ScheduleofDueAmortizations(String filetype, String imgsrc, String companyname,
			Date startdate, Date enddate, String loanproduct, String branch, String officer) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		params.put("branch", branch);
		params.put("officer", officer);
		params.put("loanproduct", loanproduct);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("LMS_ScheduleofDueAmortizations", params);
			} else {
				filepath = service.executeJasperXLSX("LMS_ScheduleofDueAmortizations", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateLMS_BorrowerListofLoanAccounts(String filetype, String imgsrc, String companyname, Date asof,
			String borrowertype, String businessname, String firstname, String lastname, String loanproduct,
			String branch, String officer) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("asof", asof);
		params.put("borrowertype", borrowertype);
		params.put("businessname", businessname);
		params.put("firstname", firstname);
		params.put("lastname", lastname);
		params.put("loanproduct", loanproduct);
		params.put("branch", branch);
		params.put("officer", officer);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("LMS_BorrowerListofLoanAccounts", params);
			} else {
				filepath = service.executeJasperXLSX("LMS_BorrowerListofLoanAccounts", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateLMS_ScheduleofOutstandingAccruedInterestReceivables(String filetype, String imgsrc,
			String companyname, String datefilter, Date asof, Date from, Date to, String loanproduct, String branch,
			String officer) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("datefilter", datefilter);
		params.put("asof", asof);
		params.put("from", from);
		params.put("to", to);
		params.put("branch", branch == null ? "" : branch);
		params.put("officer", officer == null ? "" : officer);
		params.put("loanproduct", loanproduct == null ? "" : loanproduct);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("LMS_ScheduleofOutstandingAccruedInterestReceivables", params);
			} else {
				filepath = service.executeJasperXLSX("LMS_ScheduleofOutstandingAccruedInterestReceivables", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateMIS_ListofLoanAccountswithExcessPaymentBalance(String filetype, String imgsrc,
			String companyname, String datefilter, Date asof, Date from, Date to, String branch, String officer) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("datefilter", datefilter);
		params.put("asof", asof);
		params.put("from", from);
		params.put("to", to);
		params.put("branch", branch == null ? "" : branch);
		params.put("officer", officer == null ? "" : officer);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("MIS_ListofLoanAccountswithExcessPaymentBalance", params);
			} else {
				filepath = service.executeJasperXLSX("MIS_ListofLoanAccountswithExcessPaymentBalance", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateMIS_ScheduleofLoanAccountsfromCurrenttoDelinquency(String filetype, String imgsrc,
			String companyname, Date transdate, String fromaccountstat, String toaccountstat) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("transdate", transdate);
		params.put("fromaccountstat", fromaccountstat);
		params.put("toaccountstat", toaccountstat);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("MIS_ScheduleofLoanAccountsfromCurrenttoDelinquency", params);
			} else {
				filepath = service.executeJasperXLSX("MIS_ScheduleofLoanAccountsfromCurrenttoDelinquency", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateMIS_ScheduleofLoanAccountswithAccountsReceivableBalance(String filetype, String imgsrc,
			String companyname, String datefilter, Date asof, Date from, Date to, String branch, String officer) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("datefilter", datefilter);
		params.put("asof", asof);
		params.put("from", from);
		params.put("to", to);
		params.put("branch", branch == null ? "" : branch);
		params.put("officer", officer == null ? "" : officer);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("MIS_ScheduleofLoanAccountswithAccountsReceivableBalance", params);
			} else {
				filepath = service.executeJasperXLSX("MIS_ScheduleofLoanAccountswithAccountsReceivableBalance", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateMIS_ScheduleofLoanAccountswithOutstandingLPC(String filetype, String imgsrc,
			String companyname, String datefilter, Date asof, Date from, Date to, String branch, String officer) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("datefilter", datefilter);
		params.put("asof", asof);
		params.put("from", from);
		params.put("to", to);
		params.put("branch", branch == null ? "" : branch);
		params.put("officer", officer == null ? "" : officer);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("MIS_ScheduleofLoanAccountswithOutstandingLPC", params);
			} else {
				filepath = service.executeJasperXLSX("MIS_ScheduleofLoanAccountswithOutstandingLPC", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateMIS_ScheduleofLoanReleasesperBranch(String filetype, String imgsrc, String companyname,
			Date startdate, Date enddate, String branch, String officer) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		params.put("branch", branch);
		params.put("officer", officer);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("MIS_ScheduleofLoanReleasesperBranch", params);
			} else {
				filepath = service.executeJasperXLSX("MIS_ScheduleofLoanReleasesperBranch", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateMIS_ScheduleofLoanReleasesperSolicitingOfficer(String filetype, String imgsrc,
			String companyname, Date startdate, Date enddate, String branch, String officer) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		params.put("branch", branch);
		params.put("officer", officer);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("MIS_ScheduleofLoanReleasesperSolicitingOfficer", params);
			} else {
				filepath = service.executeJasperXLSX("MIS_ScheduleofLoanReleasesperSolicitingOfficer", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	// Balance and Sched End

	// LMS Transactional Report
	public String generateLMS_InterestAccrualSetup(String filetype, String imgsrc, String companyname, Date startdate,
			Date enddate, String loanproduct, String branch, String officer) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		params.put("branch", branch == null ? "" : branch);
		params.put("officer", officer == null ? "" : officer);
		params.put("loanproduct", loanproduct == null ? "" : loanproduct);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("LMS_InterestAccrualSetup", params);
			} else {
				filepath = service.executeJasperXLSX("LMS_InterestAccrualSetup", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateLMS_ListofLoanReleases(String filetype, String imgsrc, String companyname, Date startdate,
			Date enddate, String borrowertype, String loanproduct, String officer, String branch) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		params.put("branch", branch == null ? "" : branch);
		params.put("officer", officer == null ? "" : officer);
		params.put("borrowertype", borrowertype == null ? "" : borrowertype);
		params.put("loanproduct", loanproduct == null ? "" : loanproduct);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("LMS_ListofLoanReleases", params);
			} else {
				filepath = service.executeJasperXLSX("LMS_ListofLoanReleases", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateLMS_JournalEntriesfortheDay(String filetype, String imgsrc, String companyname,
			Date transdate, String datefilter, Date from, Date to, String branch, String officer, String generatedby) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("datefilter", datefilter);
		params.put("transdate", transdate == null ? "" : transdate);
		params.put("from", from);
		params.put("to", to);
		params.put("branch", branch == null ? "" : branch);
		params.put("officer", officer == null ? "" : officer);

		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("LMS_JournalEntriesfortheDay", params);
			} else {
				filepath = service.executeJasperXLSX("LMS_JournalEntriesfortheDay", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateLMS_ScheduleofHeldSecurities(String filetype, String imgsrc, String companyname,
			Date transdate, String branch, String datefilter, Date asof, Date from, Date to, String officer,
			String loanproduct) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("datefilter", datefilter);
		params.put("transdate", transdate);
		params.put("from", from);
		params.put("to", to);
		params.put("branch", branch == null ? "" : branch);
		params.put("officer", officer == null ? "" : officer);
		params.put("loanproduct", loanproduct == null ? "" : loanproduct);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("LMS_ScheduleofHeldSecurities", params);
			} else {
				filepath = service.executeJasperXLSX("LMS_ScheduleofHeldSecurities", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateLMS_ListofDecidedLoanApplications(String filetype, String imgsrc, String companyname,
			Date startdate, Date enddate, String accountstatus, String borrowertype, String loanproduct, String officer,
			String decision, String branch) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		params.put("accountstatus", accountstatus == null ? "" : accountstatus);
		params.put("borrowertype", borrowertype == null ? "" : borrowertype);
		params.put("loanproduct", loanproduct == null ? "" : loanproduct);
		params.put("officer", officer == null ? "" : officer);
		params.put("branch", branch == null ? "" : branch);
		params.put("decision", decision == null ? "" : decision);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("LMS_ListofDecidedLoanApplications", params);
			} else {
				filepath = service.executeJasperXLSX("LMS_ListofDecidedLoanApplications", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateLMS_ListofLoanApplications(String filetype, String imgsrc, String companyname, Date startdate,
			Date enddate, String accountstatus, String borrowertype, String loanproduct, String officer, String branch,
			String generatedby) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("startdate", startdate == null ? "" : startdate);
		params.put("enddate", enddate == null ? "" : enddate);
		params.put("branch", branch == null ? "" : branch);
		params.put("officer", officer == null ? "" : officer);
		params.put("loanproduct", loanproduct == null ? "" : loanproduct);
		params.put("accountstatus", accountstatus == null ? "" : accountstatus);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("LMS_ListofLoanApplications", params);
			} else {
				filepath = service.executeJasperXLSX("LMS_ListofLoanApplications", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateLMS_ScheduleofReceivedDocumentsSecurities(String filetype, String imgsrc, String companyname,
			Date transdate, String loanproduct, String branch, String officer) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("transdate", transdate);
		params.put("branch", branch);
		params.put("officer", officer);
		params.put("loanproduct", loanproduct);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("LMS_ScheduleofReceivedDocuments&Securities", params);
			} else {
				filepath = service.executeJasperXLSX("LMS_ScheduleofReceivedDocuments&Securities", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateLMS_LMS_ScheduleOfLoanAccountsFromOldToCurrent(String filetype, String imgsrc,
			String companyname, String datefilter, Date from, Date to, Date transdate, String accountStatFrom,
			String accountStatTo, String branch, String officer) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("datefilter", datefilter);
		params.put("transdate", transdate);
		params.put("from", from);
		params.put("to", to);
		params.put("branch", branch == null ? "" : branch);
		params.put("officer", officer == null ? "" : officer);
		params.put("accountStatFrom", accountStatFrom == null ? "" : accountStatFrom);
		params.put("accountStatTo", accountStatTo == null ? "" : accountStatTo);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("LMS_ScheduleOfLoanAccountsFromOldToCurrent", params);
			} else {
				filepath = service.executeJasperXLSX("LMS_ScheduleOfLoanAccountsFromOldToCurrent", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateLMS_LMS_LoanReleasePerRange(String filetype, String imgsrc, String companyname,
			Date startdate, Date enddate, String branch, String officer) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		params.put("branch", branch);
		params.put("officer", officer);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("LMS_LoanReleasePerRange", params);
			} else {
				filepath = service.executeJasperXLSX("LMS_LoanReleasePerRange", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateLMS_ListOfLoanReleasesPerFirmSize(String filetype, String imgsrc, String companyname,
			Date startdate, Date enddate, String branch, String officer) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		params.put("branch", branch);
		params.put("officer", officer);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("MIS_ListofLoanReleasesperFirmSize", params);
			} else {
				filepath = service.executeJasperXLSX("MIS_ListofLoanReleasesperFirmSize", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateLMS_ListofLMSTransactions(String filetype, String imgsrc, String companyname, Date startdate,
			Date enddate, String transstat, String transtype, String borrowertype, String loanproduct, String officer,
			String branch) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("startdate", startdate == null ? "" : startdate);
		params.put("enddate", enddate == null ? "" : enddate);
		params.put("transstat", transstat == null ? "" : transstat);
		params.put("borrowertype", borrowertype == null ? "" : borrowertype);
		params.put("loanproduct", loanproduct == null ? "" : loanproduct);
		params.put("officer", officer == null ? "" : officer);
		params.put("branch", branch == null ? "" : branch);
		params.put("transtype", transtype == null ? "" : transtype);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("LMS_ListofLMSTransactions", params);
			} else {
				filepath = service.executeJasperXLSX("LMS_ListofLMSTransactions", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateMIS_ListofWaviedInterestsPenaltiesandOtherCharges(String filetype, String imgsrc,
			String companyname, Date startdate, Date enddate, String branch, String officer) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		params.put("branch", branch);
		params.put("officer", officer);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("MIS_ListofWaviedInterestsPenaltiesandOtherCharges", params);
			} else {
				filepath = service.executeJasperXLSX("MIS_ListofWaviedInterestsPenaltiesandOtherCharges", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateMIS_LoansTargetvsPerformance(String filetype, String imgsrc, String companyname,
			Date startdate, Date enddate, Integer enterTargetCount, Integer enterTargetAmount, String branch,
			String officer) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		params.put("branch", branch);
		params.put("officer", officer);
		params.put("enterTargetCount", enterTargetCount);
		params.put("enterTargetAmount", enterTargetAmount);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("MIS_LoansTargetvsPerformance", params);
			} else {
				filepath = service.executeJasperXLSX("MIS_LoansTargetvsPerformance", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateMIS_LoanGrantPerformanceReview(String filetype, String imgsrc, String companyname,
			String datefilter, Date asof, Date from, Date to, String branch, String officer) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("datefilter", datefilter);
		params.put("asof", asof);
		params.put("from", from);
		params.put("to", to);
		params.put("branch", branch == null ? "" : branch);
		params.put("officer", officer == null ? "" : officer);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("MIS_LoanGrantPerformanceReview", params);
			} else {
				filepath = service.executeJasperXLSX("MIS_LoanGrantPerformanceReview", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateMIS_LoanGrantPerformanceReviewperBranch(String filetype, String imgsrc, String companyname,
			String datefilter, Date asof, Date from, Date to, String branch, String officer) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("datefilter", datefilter);
		params.put("asof", asof);
		params.put("from", from);
		params.put("to", to);
		params.put("branch", branch == null ? "" : branch);
		params.put("officer", officer == null ? "" : officer);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("MIS_LoanGrantPerformanceReviewperBranch", params);
			} else {
				filepath = service.executeJasperXLSX("MIS_LoanGrantPerformanceReviewperBranch", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateMIS_LoanCollections(String filetype, String imgsrc, String companyname, Date startdate,
			Date enddate, String branch, String officer) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		params.put("branch", branch);
		params.put("officer", officer);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("MIS_LoanCollections", params);
			} else {
				filepath = service.executeJasperXLSX("MIS_LoanCollections", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateMIS_ListofLoanAccountsFullyPaidBeforeMaturityDate(String filetype, String imgsrc,
			String companyname, Date startdate, Date enddate, String branch, String officer) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		params.put("branch", branch);
		params.put("officer", officer);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("MIS_ListofLoanAccountsFullyPaidBeforeMaturityDate", params);
			} else {
				filepath = service.executeJasperXLSX("MIS_ListofLoanAccountsFullyPaidBeforeMaturityDate", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateMIS_RecordedLoanIncomeperBranch(String filetype, String imgsrc, String companyname,
			String datefilter, Date asof, Date from, Date to, String branch, String officer) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("datefilter", datefilter);
		params.put("asof", asof);
		params.put("from", from);
		params.put("to", to);
		params.put("branch", branch == null ? "" : branch);
		params.put("officer", officer == null ? "" : officer);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("MIS_RecordedLoanIncomeperBranch", params);
			} else {
				filepath = service.executeJasperXLSX("MIS_RecordedLoanIncomeperBranch", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	// Transactional End

	// Sample Report
	public String generaterptsampleGraphPick(String filetype, String changetype) {
		String filepath = null;
		if (changetype.equals("0")) {
			params.put("chooseGraph", forPie);
		} else if (changetype.equals("1")) {
			params.put("chooseGraph", forGraph);
		}
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("sampleGraphPick", params);
			} else {
				filepath = service.executeJasperXLSX("sampleGraphPick", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	// CIF Transactional
	public String generateCIF_ListofCIFEnrollmentTransactionsIndividual(String filetype, String imgsrc,
			String companyname, Date startdate, Date enddate, String gender, String civilstat, String nationality,
			String cifstatus, String riskrating, String branch, String tellername, String generatedby) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("startdate", startdate == null ? "" : startdate);
		params.put("enddate", enddate == null ? "" : enddate);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellername", tellername == null ? "" : tellername);
		params.put("gender", gender == null ? "" : gender);
		params.put("civilstat", civilstat == null ? "" : civilstat);
		params.put("nationality", nationality == null ? "" : nationality);
		params.put("cifstat", cifstatus == null ? "" : cifstatus);
		params.put("riskrating", riskrating == null ? "" : riskrating);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CIF_ListofCIFEnrollmentTransactionsIndividual", params);
			} else {
				filepath = service.executeJasperXLSX("CIF_ListofCIFEnrollmentTransactionsIndividual", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateCIF_ListofCIFEnrollmentTransactionsBusiness(String filetype, String imgsrc,
			String companyname, Date startdate, Date enddate, String registrationtype, String businesstype,
			String paidupcapital, String firmsize, String nationality, String cifstatus, String riskrating,
			String branch, String tellername, String generatedby) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);

		params.put("startDate", startdate == null ? "" : startdate);
		params.put("endDate", enddate == null ? "" : enddate);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellerName", tellername == null ? "" : tellername);
		params.put("registrationtype", registrationtype == null ? "" : registrationtype);
		params.put("businesstype", businesstype == null ? "" : businesstype);
		params.put("paidupcapital", paidupcapital == null ? "" : paidupcapital);
		params.put("firmsize", firmsize == null ? "" : firmsize);
		params.put("nationality", nationality == null ? "" : nationality);
		params.put("cifstatus", cifstatus == null ? "" : cifstatus);
		params.put("riskrating", riskrating == null ? "" : riskrating);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CIF_ListofCIFEnrollmentTransactionsBusiness", params);
			} else {
				filepath = service.executeJasperXLSX("CIF_ListofCIFEnrollmentTransactionsBusiness", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateCIF_ListofClientProfileUpdateTransactions(String filetype, String imgsrc, String companyname,
			Date startdate, Date enddate, String branch, String tellername, String generatedby) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("startdate", startdate == null ? "" : startdate);
		params.put("enddate", enddate == null ? "" : enddate);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellername", tellername == null ? "" : tellername);

		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CIF_ListofClientProfileUpdateTransaction", params);
			} else {
				filepath = service.executeJasperXLSX("CIF_ListofClientProfileUpdateTransaction", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	// Transaction End

	// CIF Schedule
	public String generateCIF_ListofCustomersIndividual(String filetype, String imgsrc, String companyname, Date asof,
			String branch, String tellername, String generatedby) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("asof", asof == null ? "" : asof);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellerName", tellername == null ? "" : tellername);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CIF_ListofCustomersIndividual", params);
			} else {
				filepath = service.executeJasperXLSX("CIF_ListofCustomersIndividual", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateCIF_ListofCustomersBusiness(String filetype, String imgsrc, String companyname, Date asof,
			String branch, String tellername, String generatedby) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("asof", asof == null ? "" : asof);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellerName", tellername == null ? "" : tellername);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CIF_ListofCustomersBusiness", params);
			} else {
				filepath = service.executeJasperXLSX("CIF_ListofCustomersBusiness", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	// Schedule End

	// CIF Exeptional
	// Individual
	public String generateCIF_CustomerListDOSRIIndividual(String filetype, String imgsrc, String companyname, Date asof,
			String branch, String tellername, String riskrating, String generatedby) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("branch", branch);
		params.put("tellername", tellername);
		params.put("companyname", companyname);
		params.put("asof", asof);
		params.put("riskrating", riskrating);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CIF_CustomerListDOSRIIndividual", params);
			} else {
				filepath = service.executeJasperXLSX("CIF_CustomerListDOSRIIndividual", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateCIF_CustomerListFATCAIndividual(String filetype, String imgsrc, String companyname, Date asof,
			String branch, String tellername, String riskrating, String generatedby) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("branch", branch);
		params.put("tellername", tellername);
		params.put("companyname", companyname);
		params.put("asof", asof);
		params.put("riskrating", riskrating);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CIF_CustomerListFATCAIndividual", params);
			} else {
				filepath = service.executeJasperXLSX("CIF_CustomerListFATCAIndividual", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateCIF_CustomerListPEPIndividual(String filetype, String imgsrc, String companyname, Date asof,
			String branch, String tellername, String riskrating, String generatedby) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("branch", branch);
		params.put("tellername", tellername);
		params.put("companyname", companyname);
		params.put("asof", asof);
		params.put("riskrating", riskrating);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CIF_CustomerListPEPIndividual", params);
			} else {
				filepath = service.executeJasperXLSX("CIF_CustomerListPEPIndividual", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateCIF_CustomerListDOSRIBusiness(String filetype, String imgsrc, String companyname, Date asof,
			String branch, String tellername, String riskrating, String generatedby) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("branch", branch);
		params.put("tellername", tellername);
		params.put("companyname", companyname);
		params.put("asof", asof);
		params.put("riskrating", riskrating);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CIF_CustomerListDOSRIBusiness", params);
			} else {
				filepath = service.executeJasperXLSX("CIF_CustomerListDOSRIBusiness", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateCIF_CustomerListFATCABusiness(String filetype, String imgsrc, String companyname, Date asof,
			String branch, String tellername, String riskrating, String generatedby) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("branch", branch);
		params.put("tellername", tellername);
		params.put("companyname", companyname);
		params.put("asof", asof);
		params.put("riskrating", riskrating);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CIF_CustomerListFATCABusiness", params);
			} else {
				filepath = service.executeJasperXLSX("CIF_CustomerListFATCABusiness", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateCIF_CustomerListPEPBusiness(String filetype, String imgsrc, String companyname, Date asof,
			String branch, String tellername, String riskrating, String generatedby) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("branch", branch);
		params.put("tellername", tellername);
		params.put("companyname", companyname);
		params.put("asof", asof);
		params.put("riskrating", riskrating);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CIF_CustomerListPEPBusiness", params);
			} else {
				filepath = service.executeJasperXLSX("CIF_CustomerListPEPBusiness", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateCIF_ExceptionalAccountsFATCAIndividual(String filetype, String imgsrc, String companyname,
			Date startdate, Date enddate, String branch, String tellername, String generatedby) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("branch", branch);
		params.put("tellername", tellername);
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CIF_ExceptionalAccountsFATCAIndividual", params);
			} else {
				filepath = service.executeJasperXLSX("CIF_ExceptionalAccountsFATCAIndividual", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateCIF_ExceptionalAccountsDOSRIIndividual(String filetype, String imgsrc, String companyname,
			Date startdate, Date enddate, String branch, String tellername, String generatedby) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("branch", branch);
		params.put("tellername", tellername);
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CIF_ExceptionalAccountsDOSRIIndividual", params);
			} else {
				filepath = service.executeJasperXLSX("CIF_ExceptionalAccountsDOSRIIndividual", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateCIF_ExceptionalAccountsPEPIndividual(String filetype, String imgsrc, String companyname,
			Date startdate, Date enddate, String branch, String tellername, String generatedby) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("branch", branch);
		params.put("tellername", tellername);
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CIF_ExceptionalAccountsPEPIndividual", params);
			} else {
				filepath = service.executeJasperXLSX("CIF_ExceptionalAccountsPEPIndividual", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	// Individual End

	// Business
	public String generateCIF_ExceptionalAccountsDOSRIBusiness(String filetype, String imgsrc, String companyname,
			Date startdate, Date enddate, String branch, String tellername, String generatedby) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("branch", branch);
		params.put("tellername", tellername);
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CIF_ExceptionalAccountsDOSRIBusiness", params);
			} else {
				filepath = service.executeJasperXLSX("CIF_ExceptionalAccountsDOSRIBusiness", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateCIF_ExceptionalAccountsFATCABusiness(String filetype, String imgsrc, String companyname,
			Date startdate, Date enddate, String branch, String tellername, String generatedby) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("branch", branch);
		params.put("tellername", tellername);
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CIF_ExceptionalAccountsFATCABusiness", params);
			} else {
				filepath = service.executeJasperXLSX("CIF_ExceptionalAccountsFATCABusiness", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateCIF_ExceptionalAccountsPEPBusiness(String filetype, String imgsrc, String companyname,
			Date startdate, Date enddate, String branch, String tellername, String generatedby) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("branch", branch);
		params.put("tellername", tellername);
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CIF_ExceptionalAccountsPEPBusiness", params);
			} else {
				filepath = service.executeJasperXLSX("CIF_ExceptionalAccountsPEPBusiness", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	// Business End
	// Exeptional End

	// Renz
	// FRP
	// part1
	public String generateFRP_Sch11a1(String filetype) {
		String filepath = null;
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("FRP_Sch11a1", params);
			} else {
				filepath = service.executeJasperXLSX("FRP_Sch11a1", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateFRP_Sch11a2(String filetype) {
		String filepath = null;
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("FRP_Sch11a2", params);
			} else {
				filepath = service.executeJasperXLSX("FRP_Sch11a2", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateFRP_Sch11a3(String filetype) {
		String filepath = null;
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("FRP_Sch11a3", params);
			} else {
				filepath = service.executeJasperXLSX("FRP_Sch11a3", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateFRP_Sch11a4(String filetype) {
		String filepath = null;
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("FRP_Sch11a4", params);
			} else {
				filepath = service.executeJasperXLSX("FRP_Sch11a4", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateFRP_Sch11aByStatus(String filetype) {
		String filepath = null;
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("FRP_Sch11aByStatus", params);
			} else {
				filepath = service.executeJasperXLSX("FRP_Sch11aByStatus", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateFRP_Sch11b(String filetype) {
		String filepath = null;
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("FRP_Sch11b", params);
			} else {
				filepath = service.executeJasperXLSX("FRP_Sch11b", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateFRP_Sch11b1(String filetype) {
		String filepath = null;
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("FRP_Sch11b1", params);
			} else {
				filepath = service.executeJasperXLSX("FRP_Sch11b1", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateFRP_Sch11b2(String filetype) {
		String filepath = null;
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("FRP_Sch11b2", params);
			} else {
				filepath = service.executeJasperXLSX("FRP_Sch11b2", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateFRP_Sch11b3(String filetype) {
		String filepath = null;
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("FRP_Sch11b3", params);
			} else {
				filepath = service.executeJasperXLSX("FRP_Sch11b3", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateFRP_Sch11b4(String filetype) {
		String filepath = null;
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("FRP_Sch11b4", params);
			} else {
				filepath = service.executeJasperXLSX("FRP_Sch11b4", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateFRP_Sch11c(String filetype) {
		String filepath = null;
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("FRP_Sch11c", params);
			} else {
				filepath = service.executeJasperXLSX("FRP_Sch11c", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateFRP_Sch11c1(String filetype) {
		String filepath = null;
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("FRP_Sch11c1", params);
			} else {
				filepath = service.executeJasperXLSX("FRP_Sch11c1", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateFRP_Sch11c2(String filetype) {
		String filepath = null;
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("FRP_Sch11c2", params);
			} else {
				filepath = service.executeJasperXLSX("FRP_Sch11c2", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateFRP_Sch11c3(String filetype) {
		String filepath = null;
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("FRP_Sch11c3", params);
			} else {
				filepath = service.executeJasperXLSX("FRP_Sch11c3", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateFRP_Sch11c4(String filetype) {
		String filepath = null;
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("FRP_Sch11c4", params);
			} else {
				filepath = service.executeJasperXLSX("FRP_Sch11c4", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateFRP_Sch11d(String filetype) {
		String filepath = null;
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("FRP_Sch11d", params);
			} else {
				filepath = service.executeJasperXLSX("FRP_Sch11d", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateFRP_Sch11d1(String filetype) {
		String filepath = null;
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("FRP_Sch11d1", params);
			} else {
				filepath = service.executeJasperXLSX("FRP_Sch11d1", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateFRP_Sch11d2(String filetype) {
		String filepath = null;
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("FRP_Sch11d2", params);
			} else {
				filepath = service.executeJasperXLSX("FRP_Sch11d2", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateFRP_Sch11d3(String filetype) {
		String filepath = null;
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("FRP_Sch11d3", params);
			} else {
				filepath = service.executeJasperXLSX("FRP_Sch11d3", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateFRP_Sch11d4(String filetype) {
		String filepath = null;
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("FRP_Sch11d4", params);
			} else {
				filepath = service.executeJasperXLSX("FRP_Sch11d4", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateFRP_Sch11e(String filetype) {
		String filepath = null;
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("FRP_Sch11e", params);
			} else {
				filepath = service.executeJasperXLSX("FRP_Sch11e", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateFRP_Sch11e1(String filetype) {
		String filepath = null;
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("FRP_Sch11e1", params);
			} else {
				filepath = service.executeJasperXLSX("FRP_Sch11e1", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateFRP_Sch11e2(String filetype) {
		String filepath = null;
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("FRP_Sch11e2", params);
			} else {
				filepath = service.executeJasperXLSX("FRP_Sch11e2", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateFRP_Sch11e3(String filetype) {
		String filepath = null;
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("FRP_Sch11e3", params);
			} else {
				filepath = service.executeJasperXLSX("FRP_Sch11e3", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateFRP_Sch11e4(String filetype) {
		String filepath = null;
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("FRP_Sch11e4", params);
			} else {
				filepath = service.executeJasperXLSX("FRP_Sch11e4", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	// part1 end

	// part2
	public String generateFRP_Sch11f(String filetype) {
		String filepath = null;
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("FRP_Sch11f", params);
			} else {
				filepath = service.executeJasperXLSX("FRP_Sch11f", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	// part2 end

	// FRP end

	// MAR 11-12-2021
	public String generaterptCIFAccountProfitability(String filetype, String cifno, String companyname,
			BigDecimal otherIncome, BigDecimal otherExpense) {
		String filepath = null;
		params.put("cifno", cifno);
		params.put("companyname", companyname);
		params.put("otherIncome", otherIncome);
		params.put("otherExpense", otherExpense);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CIF_AccountProfitability", params);
			} else {
				filepath = service.executeJasperXLSX("CIF_AccountProfitability", params);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	// Renz
	// CASA
	// Balances and Schedules
	public String generateCASA_CustomerListofDepositAccounts(String filetype, String imgsrc, String cifno,
			String companyname, String accountname, String tellername, Date asof, String branch, String generatedby,
			String tname, String bname) {
		// Changed param
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("asof", asof);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellername", tellername == null ? "" : tellername);
		params.put("tname", tname);
		params.put("bname", bname);
		params.put("cifno", cifno == null ? "" : cifno);
		params.put("accountname", accountname == null ? "" : accountname);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_CustomerListofDepositAccounts", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_CustomerListofDepositAccountsExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	//updated added tname bname renz 51123 612pm
	public String generateCASA_MasterListofAccounts(String filetype, String imgsrc, String companyname, String prodtype,
			Date asof, String branch, String tellername, String status, String generatedby,String tname,String bname) {
		// Changed param
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("asof", asof);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellername", tellername == null ? "" : tellername);
		params.put("tname", tname);
		params.put("bname", bname);
		params.put("prodtype", prodtype == null ? "" : prodtype);
		params.put("status", status == null ? "" : status);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_MasterListofAccounts", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_MasterListofAccountsExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	//updated added tname bname renz 51123 612pm
	public String generateCASA_MasterListofAccountsBelowMinimumBalance(String filetype, String imgsrc,
			String companyname, String prodtype, Date asof, String branch, String tellername, String status,
			String generatedby,String tname,String bname) {
		// Changed param
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("asof", asof);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellername", tellername == null ? "" : tellername);
		params.put("tname", tname);
		params.put("bname", bname);
		params.put("prodtype", prodtype == null ? "" : prodtype);
		params.put("status", status == null ? "" : status);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_MasterListofAccountsBelowMinimumBalance", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_MasterListofAccountsBelowMinimumBalanceExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	//updated added tname bname renz 51123 612pm
	public String generateCASA_MasterListofAccountsDormant5years10Years(String filetype, String imgsrc,
			String companyname, Date asof, String branch, String tellername, String generatedby, String tname,String bname) {
		// Changed param
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("asof", asof);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellername", tellername == null ? "" : tellername);
		params.put("tname", tname);
		params.put("bname", bname);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_MasterListofAccountsDormant5years10Years", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_MasterListofAccountsDormant5years10YearsExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	//updated added tname bname renz 51123 612pm
	public String generateCASA_ScheduleofAccountswithNegativeBalancesTemporaryOverdraft(String filetype, String imgsrc,
			String companyname, String tellername, Date asof, String branch, String generatedby, String tname,String bname) {
		// Changed param
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("asof", asof);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellername", tellername == null ? "" : tellername);
		params.put("tname", tname);
		params.put("bname", bname);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_ScheduleofAccountswithNegativeBalancesTemporaryOverdraft",
						params);
			} else {
				filepath = service.executeJasperXLSX("CASA_ScheduleofAccountswithNegativeBalancesTemporaryOverdraftExcel",
						params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	//updated added tname bname renz 51123 612pm
	public String generateCASA_ScheduleofAccruedInterestPayable(String filetype, String imgsrc, String companyname,
			String tellername, String branch, Date asof, String generatedby,String tname,String bname) {
		// Changed param
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("asof", asof);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellername", tellername == null ? "" : tellername);
		params.put("tname", tname);
		params.put("bname", bname);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_ScheduleofAccruedInterestPayable", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_ScheduleofAccruedInterestPayableExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	// Balances and Schedules End

	// Exceptional
	// SPNA
	//updated added tname bname renz 51123 612pm
	public String generateCASA_ListofActivatedDormantAccounts(String filetype, String imgsrc, String companyname,
			Date startdate, Date enddate, String tellername, String branch, String generatedby,String tname,String bname) {
		// Changed param
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellername", tellername == null ? "" : tellername);
		params.put("tname", tname);
		params.put("bname", bname);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_ListofActivatedDormantAccounts", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_ListofActivatedDormantAccountsExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	//Added tname bname 6123 renz
	public String generateCASA_ListofAccountswithP500Transactions(String filetype, String imgsrc, String companyname,
			Date startdate, Date enddate, String tellername, String branch, String generatedby,String bname,String tname) {
		// New
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		params.put("branch", branch == null ? "" : branch);
		params.put("bname", bname);
		params.put("tname", tname);
		params.put("tellername", tellername == null ? "" : tellername);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_ListofAccountswithP500Transactions", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_ListofAccountswithP500TransactionsExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	//updated added tname bname renz 51123 612pm
	public String generateCASA_ListofErrorCorrectedTransactions(String filetype, String imgsrc, String companyname,
			Date startdate, Date enddate, String tellername, String branch, String generatedby, String tname,String bname) {
		// New
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellername", tellername == null ? "" : tellername);
		params.put("tname", tname);
		params.put("bname", bname);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_ListofErrorCorrectedTransactions", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_ListofErrorCorrectedTransactionsExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	//updated added tname bname renz 51123 612pm
	public String generateCASA_ListofRejectedTransactions(String filetype, String imgsrc, String companyname,
			Date startdate, Date enddate, String tellername, String branch, String generatedby,String tname,String bname) {
		// New
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellername", tellername == null ? "" : tellername);
		params.put("tname", tname);
		params.put("bname", bname);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_ListofRejectedTransactions", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_ListofRejectedTransactionsExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	//updated added tname bname renz 51123 612pm
	public String generateCASA_ListofOverrideTransactions(String filetype, String imgsrc, String companyname,
			Date startdate, Date enddate, String tellername, String branch, String generatedby,String tname,String bname) {
		// New
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellername", tellername == null ? "" : tellername);
		params.put("tname", tname);
		params.put("bname", bname);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_ListofOverrideTransactions", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_ListofOverrideTransactionsExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateCASA_ListofAccountsClassifiedtoDormant(String filetype, String imgsrc, String companyname,
			Date startdate, Date enddate, String tellername, String branch, String generatedby) {
		// New
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellername", tellername == null ? "" : tellername);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_ListofAccountsClassifiedtoDormant", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_ListofAccountsClassifiedtoDormant", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateCASA_OtherBankReturnCheck(String filetype, String imgsrc, String companyname, Date startdate,
			Date enddate, String tellername, String branch, String generatedby) {
		// New
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		params.put("tellername", tellername);
		params.put("branch", branch);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
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
	//updated added tname bname renz 51123 612pm
	public String generateCASA_ListOfForceClearTransaction(String filetype, String imgsrc, String companyname,
			Date startdate, Date enddate, String tellername, String branch, String generatedby,String tname,String bname) {
		// New
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		params.put("tellername", tellername);
		params.put("branch", branch);
		params.put("tname", tname);
		params.put("bname", bname);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_ListOfForceClearTransaction", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_ListOfForceClearTransactionExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	// Exceptional End

	// Transactional
	//updated added tname bname renz 51123 105pm
	public String generateCASA_BranchTransactionListfortheDayFinancialTermProducts(String filetype, String imgsrc,
			String companyname, String branch, String prodtype, String datefilter, Date asof, Date from, Date to,
			String tellername, String generatedby,String tname, String bname) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("datefilter", datefilter);
		params.put("asof", asof);
		params.put("from", from);
		params.put("to", to);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellername", tellername == null ? "" : tellername);
		params.put("bname", bname == null ? "" : bname);
		params.put("tname", tname == null ? "" : tname);
		params.put("prodtype", prodtype == null ? "" : prodtype);

		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_BranchTransactionListfortheDayFinancialTermProducts", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_BranchTransactionListfortheDayFinancialTermProductsExcel",
						params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	//updated added tname bname renz 51123 105pm
	public String generateCASA_BranchTransactionListfortheDayFinancial(String filetype, String imgsrc,
			String companyname, String datefilter, Date asof, Date from, Date to, String prodtype, String tellername,
			String branch, String generatedby,String tname, String bname) {
		// Changed param
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("datefilter", datefilter);
		params.put("asof", asof);
		params.put("from", from);
		params.put("to", to);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellername", tellername == null ? "" : tellername);
		params.put("bname", bname == null ? "" : bname);
		params.put("tname", tname == null ? "" : tname);
		params.put("prodtype", prodtype == null ? "" : prodtype);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_BranchTransactionListfortheDayFinancial", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_BranchTransactionListfortheDayFinancialExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateCASA_CashTransferMovements(String filetype, String imgsrc, String companyname,
			String tellername, String datefilter, Date asof, Date from, Date to, String branch, String generatedby) {
		// Changed param
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("datefilter", datefilter);
		params.put("asof", asof);
		params.put("from", from);
		params.put("to", to);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellername", tellername == null ? "" : tellername);
		params.put("companyname", companyname);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_CashTransferMovements", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_CashTransferMovements", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	//updated added tname bname renz 51123 105pm
	public String generateCASA_ListofClosedAccounts(String filetype, String imgsrc, String companyname, String branch,
			String datefilter, Date asof, Date startdate, Date enddate, String tellername, String accountstatus,
			String generatedby,String bname,String tname) {
		// Changed param
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("datefilter", datefilter);
		params.put("businessDate", asof);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellername", tellername == null ? "" : tellername);
		params.put("tname", tname == null ? "" : tname);
		params.put("bname", bname == null ? "" : bname);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_ListofClosedAccounts", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_ListofClosedAccountsExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	//updated added tname bname renz 51123 105pm
	public String generateCASA_ListofClosedTerminatedPreterminatedAccountsfortheDay(String filetype, String imgsrc,
			String datefilter, Date from, Date to, String companyname, Date asof, String prodtype, String branch,
			String tellername, String generatedby,String tname, String bname) {
		// Changed param
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("datefilter", datefilter);
		params.put("asof", asof);
		params.put("from", from);
		params.put("to", to);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellername", tellername == null ? "" : tellername);
		params.put("tname", tname == null ? "" : tname);
		params.put("bname", bname == null ? "" : bname);
		params.put("prodtype", prodtype == null ? "" : prodtype);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_ListofClosedTerminatedPreterminatedAccountsfortheDay",
						params);
			} else {
				filepath = service.executeJasperXLSX("CASA_ListofClosedTerminatedPreterminatedAccountsfortheDayExcel",
						params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	//updated added tname bname renz 51223 445pm
	public String generateCASA_ListofEscheatedAccounts(String filetype, String imgsrc, String companyname,
			String datefilter, Date asof, Date startdate, Date enddate, String tellername, String prodtype,
			String branch, String generatedby,String tname,String bname) {
		// Changed param
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("datefilter", datefilter);
		params.put("businessDate", asof);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellername", tellername == null ? "" : tellername);
		params.put("tname", tname);
		params.put("bname", bname);

		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_ListofEscheatedAccounts", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_ListofEscheatedAccountsExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	//updated added tname bname renz 51123 105pm
	public String generateCASA_ListofIssuedCTDPassbookCKBook(String filetype, String imgsrc, String companyname,
			String datefilter, Date asof, Date from, Date to, String tellername, String branch, String generatedby,
			String bname,String tname) {
		// Changed param
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("datefilter", datefilter);
		params.put("asof", asof);
		params.put("from", from);
		params.put("to", to);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellername", tellername == null ? "" : tellername);
		params.put("bname", bname == null ? "" : bname);
		params.put("tname", tname == null ? "" : tname);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_ListofIssuedCTDPassbookCKBook", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_ListofIssuedCTDPassbookCKBookExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateCASA_ListofLateCheckDeposits(String filetype, String imgsrc, String companyname,
			String datefilter, Date asof, Date from, Date to, String tellername, String branch, String generatedby) {
		// Changed param
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("datefilter", datefilter);
		params.put("asof", asof);
		params.put("from", from);
		params.put("to", to);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellername", tellername == null ? "" : tellername);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_ListofLateCheckDeposits", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_ListofLateCheckDepositsExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	
	//updated added tname bname renz 51123 105pm
	public String generateCASA_ListofNewlyOpenedAccountsfortheDay(String filetype, String imgsrc, String companyname,
			String datefilter, Date asof, Date from, Date to, String prodtype, String tellername, String branch,
			String generatedby,String bname,String tname) {
		// Changed param
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("datefilter", datefilter);
		params.put("asof", asof);
		params.put("from", from);
		params.put("to", to);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellername", tellername == null ? "" : tellername);
		params.put("bname", bname == null ? "" : bname);
		params.put("tname", tname == null ? "" : tname);
		params.put("prodtype", prodtype == null ? "" : prodtype);

		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_ListofNewlyOpenedAccountsfortheDay", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_ListofNewlyOpenedAccountsfortheDayExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	//updated added tname bname renz 51123 105pm
	public String generateCASA_TellerListofTransactionsfortheDay(String filetype, String imgsrc, String companyname,
			Date asof, String tellername, Date from, Date to, String datefilter, String branch, String generatedby,
			String transtype, String tname, String bname) {
		// Changed param
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("datefilter", datefilter);
		params.put("businessDate", asof);
		params.put("from", from);
		params.put("to", to);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellername", tellername == null ? "" : tellername);
		params.put("tname", tname == null ? "" : tname);
		params.put("bname", bname == null ? "" : bname);
		params.put("transtype", transtype == null ? "" : transtype);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_TellerListofTransactionsfortheDay", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_TellerListofTransactionsfortheDayExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	//updated added tname bname renz 51123 105pm
	public String generateCASA_ListofAutoRenewAutoRollTermDepositPlacements(String filetype, String imgsrc,
			String companyname, String datefilter, Date asof, Date from, Date to, String dispositiontype, String branch,
			String tellername, String generatedby,String bname,String tname) {
		// Changed param
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("datefilter", datefilter);
		params.put("asof", asof);
		params.put("from", from);
		params.put("to", to);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellername", tellername == null ? "" : tellername);
		params.put("bname", bname == null ? "" : bname);
		params.put("tname", tname == null ? "" : tname);
		params.put("dispositiontype", dispositiontype == null ? "" : dispositiontype);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_ListofAutoRenewAutoRollTermDepositPlacements", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_ListofAutoRenewAutoRollTermDepositPlacementsExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	//updated added tname bname renz 51123 105pm
	public String generateCASA_FileMaintenanceUpdateTransactions(String filetype, String imgsrc, String companyname,
			String datefilter, Date asof, Date from, Date to, String branch, String tellername, String generatedby, String bname,
			String tname) {
		// New
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("datefilter", datefilter);
		params.put("asof", asof);
		params.put("from", from);
		params.put("to", to);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellername", tellername == null ? "" : tellername);
		params.put("tname", tname == null ? "" : tname);
		params.put("bname", bname == null ? "" : bname);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_FileMaintenanceUpdateTransactionsfortheDay", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_FileMaintenanceUpdateTransactionsfortheDayExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	// Transactional End

	// CASA End

	// Renz
	public String generaterptDepositsSOA(String filetype, String imgsrc, String companyname, String accountno,
			String accountname, Date startdate, Date enddate) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		params.put("accountno", accountno);
		params.put("accountname", accountname);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("rptDepositsSOA", params);
			} else {
				filepath = service.executeJasperXLSX("rptDepositsSOA", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	// PDIC
		// Renz
		public String generatePDIC_ANNEXG(String filetype, String companyname, Date asof) {
			String filepath = null;
			params.put("companyname", companyname);
			params.put("asof", asof);

			try {
				if (filetype.equals("PDF")) {
					filepath = service.executeJasperPDF("PDIC_ANNEXG", params);
				} else {
					filepath = service.executeJasperXLSX("PDIC_ANNEXGExcel", params);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			return filepath;
		}

		public String generatePDIC_ANNEXF(String filetype) {
			String filepath = null;
			params.put("page2", page2);
			params.put("page3", page3);
			params.put("page4", page4);
			params.put("page5", page5);

			try {
				if (filetype.equals("PDF")) {
					filepath = service.executeJasperPDF("PDIC_ANNEXF", params);
				} else {
					filepath = service.executeJasperXLSX("PDIC_ANNEXF", params);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			return filepath;
		}

		public String generatePDIC_AssessmentTableBaseDay1(String filetype, String companyname, Date curdate) {
			String filepath = null;
			params.put("companyname", companyname);
			params.put("curdate", curdate);

			try {
				if (filetype.equals("PDF")) {
					filepath = service.executeJasperPDF("PDIC_AssessmentTableBaseDay1", params);
				} else {
					filepath = service.executeJasperXLSX("PDIC_AssessmentTableBaseDay1", params);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			return filepath;
		}

		public String generatePDIC_AssessmentTableBaseDay2(String filetype, String companyname, Date curdate) {
			String filepath = null;
			params.put("companyname", companyname);
			params.put("curdate", curdate);

			try {
				if (filetype.equals("PDF")) {
					filepath = service.executeJasperPDF("PDIC_AssessmentTableBaseDay2", params);
				} else {
					filepath = service.executeJasperXLSX("PDIC_AssessmentTableBaseDay2", params);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			return filepath;
		}

		public String generatePDIC_BASEDONDEPOSITBALANCES(String filetype) {
			String filepath = null;

			try {
				if (filetype.equals("PDF")) {
					filepath = service.executeJasperPDF("PDIC_BASEDONDEPOSITBALANCES", params);
				} else {
					filepath = service.executeJasperXLSX("PDIC_BASEDONDEPOSITBALANCES", params);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			return filepath;
		}

		//UPDATED 5223
		public String generatePDIC_BREAKDOWNOFDEPOSITLIABILITIES(String filetype, String companyname, Date asof) {
			String filepath = null;
			params.put("companyname", companyname);
			params.put("asof", asof);
			try {
				if (filetype.equals("PDF")) {
					filepath = service.executeJasperPDF("PDIC_BREAKDOWNOFDEPOSITLIABILITIES", params);
				} else {
					filepath = service.executeJasperXLSX("PDIC_BREAKDOWNOFDEPOSITLIABILITIESExcel", params);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			return filepath;
		}
		//UPDATED 5323
		public String generatePDIC_CONSOLIDATEDSCHEDULEOFOTHERLIABILITYACCOUNTS(String filetype, String companyname, Date asof) {
			String filepath = null;
			params.put("companyname", companyname);
			params.put("asof", asof);
			try {
				if (filetype.equals("PDF")) {
					filepath = service.executeJasperPDF("PDIC_CONSOLIDATEDSCHEDULEOFOTHERLIABILITYACCOUNTS", params);
				} else {
					filepath = service.executeJasperXLSX("PDIC_CONSOLIDATEDSCHEDULEOFOTHERLIABILITYACCOUNTSExcel", params);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			return filepath;
		}

		public String generatePDIC_DailyListofDormantAccounts(String filetype, String companyname, Date curdate) {
			String filepath = null;
			params.put("companyname", companyname);
			params.put("curdate", curdate);

			try {
				if (filetype.equals("PDF")) {
					filepath = service.executeJasperPDF("PDIC_DailyListofDormantAccounts", params);
				} else {
					filepath = service.executeJasperXLSX("PDIC_DailyListofDormantAccounts", params);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			return filepath;
		}

		public String generatePDIC_DailyListofDormantAccounts5Years(String filetype, String companyname, Date curdate) {
			String filepath = null;
			params.put("companyname", companyname);
			params.put("curdate", curdate);

			try {
				if (filetype.equals("PDF")) {
					filepath = service.executeJasperPDF("PDIC_DailyListofDormantAccounts5Years", params);
				} else {
					filepath = service.executeJasperXLSX("PDIC_DailyListofDormantAccounts5Years", params);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			return filepath;
		}

		public String generatePDIC_DailyListofDormantAccounts10Years(String filetype, String companyname, Date curdate) {
			String filepath = null;
			params.put("companyname", companyname);
			params.put("curdate", curdate);

			try {
				if (filetype.equals("PDF")) {
					filepath = service.executeJasperPDF("PDIC_DailyListofDormantAccounts10Years", params);
				} else {
					filepath = service.executeJasperXLSX("PDIC_DailyListofDormantAccounts10Years", params);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			return filepath;
		}

		public String generatePDIC_DailyListofDormantAccountsfallingBelowMinimumBalance2Months(String filetype,
				String companyname, Date curdate) {
			String filepath = null;
			params.put("companyname", companyname);
			params.put("curdate", curdate);

			try {
				if (filetype.equals("PDF")) {
					filepath = service.executeJasperPDF("PDIC_DailyListofDormantAccounts2Years",
							params);
				} else {
					filepath = service.executeJasperXLSX("PDIC_DailyListofDormantAccounts2Years",
							params);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			return filepath;
		}
		
		//new pdic 5223
		public String generatePDIC_RI_C1(String filetype, String companyname, Date asof) {
			String filepath = null;
			params.put("companyname", companyname);
			params.put("asof", asof);

			try {
				if (filetype.equals("PDF")) {
					filepath = service.executeJasperPDF("PDIC_RI_C1", params);
				} else {
					filepath = service.executeJasperXLSX("PDIC_RI_C1Excel", params);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			return filepath;
		}
		//5423
		public String generatePDIC_AMLA(String filetype, String companyname, String branch, Date asof, String bname) {
			String filepath = null;
			params.put("companyname", companyname);
			params.put("branch", branch);
			params.put("asof", asof);
			params.put("bname", bname);
			try {
				if (filetype.equals("PDF")) {
					filepath = service.executeJasperPDF("PDIC_AMLA", params);
				} else {
					filepath = service.executeJasperXLSX("PDIC_AMLAExcel", params);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			return filepath;
		}
		// PDIC END

	// CIC
	public String generateCIC_Companies(String filetype, String companyname) {
		String filepath = null;
		params.put("companyname", companyname);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CIC_Companies", params);
			} else {
				filepath = service.executeJasperXLSX("CIC_Companies", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateCIC_CreditCardContracts(String filetype, String companyname) {
		String filepath = null;
		params.put("companyname", companyname);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CIC_CreditCardContracts", params);
			} else {
				filepath = service.executeJasperXLSX("CIC_CreditCardContracts", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateCIC_Individuals(String filetype, String companyname) {
		String filepath = null;
		params.put("companyname", companyname);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CIC_Individuals", params);
			} else {
				filepath = service.executeJasperXLSX("CIC_Individuals", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateCIC_InstallmentContracts(String filetype, String companyname) {
		String filepath = null;
		params.put("companyname", companyname);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CIC_InstallmentContracts", params);
			} else {
				filepath = service.executeJasperXLSX("CIC_InstallmentContracts", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateCIC_LinksBetweenSubjects(String filetype, String companyname) {
		String filepath = null;
		params.put("companyname", companyname);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CIC_LinksBetweenSubjects", params);
			} else {
				filepath = service.executeJasperXLSX("CIC_LinksBetweenSubjects", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateCIC_NonInstallmentContracts(String filetype, String companyname) {
		String filepath = null;
		params.put("companyname", companyname);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CIC_NonInstallmentContracts", params);
			} else {
				filepath = service.executeJasperXLSX("CIC_NonInstallmentContracts", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateCIC_SubjectNegativeEvents(String filetype, String companyname) {
		String filepath = null;
		params.put("companyname", companyname);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CIC_SubjectNegativeEvents", params);
			} else {
				filepath = service.executeJasperXLSX("CIC_SubjectNegativeEvents", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateCIC_Utilities(String filetype, String companyname) {
		String filepath = null;
		params.put("companyname", companyname);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CIC_Utilities", params);
			} else {
				filepath = service.executeJasperXLSX("CIC_Utilities", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	// CIC end

	public String printValidationSliWithdrawal(String txrefno) {
		String filepath = null;
		params.put("txrefno", txrefno);
		try {
			filepath = service.executeJasperPDF("ValidationSlipWithdrawal", params);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String printValidationSliDeposit(String txrefno) {
		String filepath = null;
		params.put("txrefno", txrefno);
		try {
			filepath = service.executeJasperPDF("ValidationSlipDeposit", params);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String printValidationSlipMulti(String multitxrefno) {
		String filepath = null;
		params.put("multitxrefno", multitxrefno);
		try {
			filepath = service.executeJasperPDF("ValidationSlipMulti", params);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String updatePassbook(String txrefno, String acctno, String lineno) {
		String filepath = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("txrefno", txrefno);
		params.put("acctno", acctno);
		params.put("lineno", lineno);
		try {
			filepath = service.executeJasperPDF("postToPassbook", params);
			dbServiceCOOP.executeUpdate("update TBDEPTXJRNL set txjrnlno = 1 where TxRefNo=:txrefno", params);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String validateMisc(String txrefno) {
		String filepath = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("txrefno", txrefno);
		try {
			filepath = service.executeJasperPDF("validateMisc", params);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String validateBillsLoansPayment(String txrefno) {
		String filepath = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("txrefno", txrefno);
		try {
			filepath = service.executeJasperPDF("validateBillsLoansPayment", params);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String loanDisbursementVoucher(String filetype, String applno, String companyname, String imgsrc) {
		String filepath = null;
		Map<String, Object> params = new HashMap<String, Object>();
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("applno", applno);
		params.put("companyname", companyname);

		params.put("SUBREPORT_DIR_COL", SUBREPORT_DIR_COL);
		params.put("SUBREPORT_DIR_COMAKER", SUBREPORT_DIR_COMAKER);
		params.put("SUBREPORT_DIR_GL", SUBREPORT_DIR_GL);

		try {
			filepath = service.executeJasperPDF("rptLoanVoucher", params);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	// -----------------------------------------------SA-----------------------------------------------
	//updated added tname bname renz 51123 105pm
	public String generateCASA_MasterListSavings(String filetype, String imgsrc, String companyname, String tellername,
			String branch, String subprod, Date asof, String generatedby, String tname,String bname) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("asof", asof);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellername", tellername == null ? "" : tellername);
		params.put("tname", tname == null ? "" : tname);
		params.put("bname", bname == null ? "" : bname);
		params.put("subprod", subprod == null ? "" : subprod);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_MasterListSavings", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_MasterListSavingsExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	//updated added tname bname renz 51923 812pm
	public String generateCASA_SavingsandPremiumSavings(String filetype, String imgsrc, String companyname,
			String tellername, String branch, String subprod, Date asof, String generatedby, String tname, String bname) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("tellername", tellername);
		params.put("tname", tname);
		params.put("bname", bname);
		params.put("asof", asof);
		params.put("branch", branch);
		params.put("subprod", subprod);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_SavingsandPremiumSavings", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_SavingsandPremiumSavingsExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	//updated added tname bname renz 51123 612pm
	public String generateCASA_SummaryofDailyTransaction(String filetype, String imgsrc, String companyname,
			String tellername, String branch, String subprod, Date asof, String generatedby,String tname, String bname) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("tellername", tellername);
		params.put("tname", tname);
		params.put("bname", bname);
		params.put("branch", branch);
		params.put("subprod", subprod);
		params.put("asof", asof);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_SummaryofDailyTransaction", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_SummaryofDailyTransactionExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	// -----------------------------------------------TD-----------------------------------------------
	//updated added tname bname renz 51123 105pm
	public String generateCASA_DailyListofAccuredInterestPayable(String filetype, String imgsrc, String companyname,
			String tellername, String branch, String subprod, Date asof, String generatedby,String tname,String bname) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("tellername", tellername);
		params.put("tname", tname);
		params.put("bname", bname);
		params.put("asof", asof);
		params.put("branch", branch);
		params.put("subprod", subprod);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_DailyListofAccuredInterestPayable", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_DailyListofAccuredInterestPayableExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	//updated added tname bname renz 51123 612pm
	public String generateCASA_DailyListofMaturedUnwithdrawnAccounts(String filetype, String imgsrc, String companyname,
			String tellername, String branch, String subprod, Date asof, String generatedby,String tname,String bname) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("tellername", tellername);
		params.put("tname", tname);
		params.put("bname", bname);
		params.put("branch", branch);
		params.put("asof", asof);
		params.put("subprod", subprod);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_DailyListofMaturedUnwithdrawnAccounts", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_DailyListofMaturedUnwithdrawnAccountsExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	//updated added tname bname renz 51123 612pm
	public String generateCASA_DailyListofNewPlacement(String filetype, String imgsrc, String companyname,
			String tellername, String branch, String subprod, Date asof, String generatedby,String tname,String bname) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("tellername", tellername);
		params.put("tname", tname);
		params.put("bname", bname);
		params.put("asof", asof);
		params.put("branch", branch);
		params.put("subprod", subprod);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_DailyListofNewPlacement", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_DailyListofNewPlacementExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	//updated added tname bname renz 51123 612pm
	public String generateCASA_DailyListofPreTerminatedAccounts(String filetype, String imgsrc, String companyname,
			String tellername, String branch, String subprod, Date asof, String generatedby,String tname,String bname) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("tellername", tellername);
		params.put("tname", tname);
		params.put("bname", bname);
		params.put("asof", asof);
		params.put("branch", branch);
		params.put("subprod", subprod);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_DailyListofPreTerminatedAccounts", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_DailyListofPreTerminatedAccountsExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	//updated added tname bname renz 51123 612pm
	public String generateCASA_DailyListofRollOvers(String filetype, String imgsrc, String companyname,
			String tellername, String branch, String subprod, Date asof, String generatedby,String tname, String bname) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("tellername", tellername);
		params.put("tname", tname);
		params.put("bname", bname);
		params.put("asof", asof);
		params.put("branch", branch);
		params.put("subprod", subprod);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_DailyListofRollOvers", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_DailyListofRollOversExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	//updated added tname bname renz 51123 612pm
	public String generateCASA_MasterListTimeDeposit(String filetype, String imgsrc, String companyname,
			String tellername, String branch, String subprod, Date asof, String generatedby,String tname,String bname) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("asof", asof);
		params.put("branch", branch == null ? "" : branch);
		params.put("tellername", tellername == null ? "" : tellername);
		params.put("tname", tname);
		params.put("bname", bname);
		params.put("subprod", subprod == null ? "" : subprod);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);

		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_MasterListTimeDeposit", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_MasterListTimeDepositExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generate_COA() {
		String filepath = null;
		try {
			filepath = service.executeJasperXLSX("SystemAdmin_GetCOA", params);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateLMS_ComprehensiveList(String filetype, String imgsrc, String companyname, Date asof,
			String branch, String prodtype, String generatedby) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("asof", asof == null ? "" : asof);
		params.put("branch", branch == null ? "" : branch);
		params.put("prodtype", prodtype == null ? "" : prodtype);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("LMS_Comprehensive", params);
			} else {
				filepath = service.executeJasperXLSX("LMS_Comprehensive", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	//updated added tname bname renz 51123 612pm
	public String generateCASA_ComprehensiveList(String filetype, String imgsrc, String companyname, Date asof,
			String branch, String prodtype, String generatedby,String bname) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("asof", asof == null ? "" : asof);
		params.put("branch", branch == null ? "" : branch);
		params.put("bname", bname);
		params.put("prodtype", prodtype == null ? "" : prodtype);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_Comprehensive", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_ComprehensiveExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

	public String generateCIF_ComprehensiveList(String filetype, String imgsrc, String companyname, Date asof,
			String branch, String generatedby) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("asof", asof == null ? "" : asof);
		params.put("branch", branch == null ? "" : branch);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CIF_Comprehensive", params);
			} else {
				filepath = service.executeJasperXLSX("CIF_Comprehensive", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}

//	Ced New 10-25-2022
	public String generateBatchTransactionDeposit(String filetype, String batchtxrefno) {
		Map<String, Object> param = new HashMap<String, Object>();
		String filepath = null;
		param.put("batchtxrefno", batchtxrefno);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("Batch_Transaction_Deposit", params);
			} else {
				filepath = service.executeJasperXLSX("Batch_Transaction_Deposit", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

//	Ced New 10-25-2022
	public String generateBatchTransactionLoan(String filetype, String batchtxrefno) {
		Map<String, Object> param = new HashMap<String, Object>();
		String filepath = null;
		param.put("batchtxrefno", batchtxrefno);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("Batch_Transaction_Loan", params);
			} else {
				filepath = service.executeJasperXLSX("Batch_Transaction_Loan", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String generateLMS_CollectionDueAndOverdue(String filetype, String imgsrc, String companyname, String branch,
			String collector, Date startdate, Date enddate) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("branch", branch == null ? "" : branch);
		params.put("collector", collector == null ? "" : collector);
		params.put("startdate", startdate == null ? "" : startdate);
		params.put("enddate", enddate == null ? "" : enddate);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("LMS_ListofCollectionDueAndOverdue", params);
			} else {
				filepath = service.executeJasperXLSX("LMS_ListofDecidedLoanApplications", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

//	Ced New 11-28-2022
	public String generateCollectionTransactionLoan(String filetype, String batchtxrefno) {
		Map<String, Object> param = new HashMap<String, Object>();
		String filepath = null;
		param.put("batchtxrefno", batchtxrefno);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("Collection_Transaction_Loan", params);
			} else {
				filepath = service.executeJasperXLSX("Collection_Transaction_Loan", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	public String generateMultiTransactionOR(String multitxrefno) {
		String filepath = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("txrefno", multitxrefno);
		try {
			filepath = service.executeJasperPDF("multiTransactionOR", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}
	
	public String generateBillsLoanOR(String txrefno) {
		String filepath = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("txrefno", txrefno);
		try {
			filepath = service.executeJasperPDF("LMS_BillsAndLoans", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}
	
	public String reprintValidation(String txrefno, String validationType) {
		String filepath = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("txrefno", txrefno);
		try {
			if(validationType.equals("Cash Deposit") || validationType.equals("Credit Memo") || 
			validationType.equals("On Us Check Deposit") || validationType.equals("Other Bank Check Deposit")){
				// Cash Deposit, Credit Memo, On Us Check Deposit, Other Bank Check Deposit
				filepath = service.executeJasperPDF("ValidationSlipDeposit", params);
			}else if(validationType.equals("Cash Withdrawal") || validationType.equals("Debit Memo") || 
					validationType.equals("Fund Transfer") || validationType.equals("Other Bank Return Check")){
				// Cash Withdrawal, Debit Memo, Fund Transfer, Other Bank Return Check
				filepath = service.executeJasperPDF("ValidationSlipWithdrawal", params);
			}else if(validationType.equals("Miscellaneous Receipts - Cash") || validationType.equals("Miscellaneous Disbursement - Check") ||
					validationType.equals("Miscellaneous Receipts - Error Correct") || validationType.equals("Miscellaneous Disbursement - Error Correct")){
				// Cash Withdrawal, Debit Memo, Fund Transfer, Other Bank Return Check
				filepath = service.executeJasperPDF("validateMisc", params);
			}else {
				//Loans Payment and bills
				filepath = service.executeJasperPDF("validateBillsLoansPayment", params);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	
	public String reprintMultiTransactionOR(String txrefno) {
		String filepath = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("txrefno", txrefno);
		try {
			filepath = service.executeJasperPDF("reprintMultiTransactionOR", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}
	public String printCOA(String imgsrc,String companyname,String branch,String cifno,Date asof) {
		String filepath = null;
		Map<String, Object> params = new HashMap<String, Object>();
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("cifno", cifno);
		params.put("asof", asof);
		params.put("SUBREPORT_DEPOSIT", SUBREPORT_COA_DEPOSIT);
		params.put("SUBREPORT_LOANS", SUBREPORT_COA_LOANS);
		params.put("branch", branch == null ? "" : branch);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		params.put("generatedby", username);
		try {
			filepath = service.executeJasperPDF("CIF_COA", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}
	
	//Renz new 
	public String generateDEP_DepositAccountSizing(String filetype, String companyname, Date asof,
			String branch, String generatedby) {
		String filepath = null;
		
		params.put("companyname", companyname);
		params.put("asof", asof);
		params.put("branch", branch);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("DEP_DepositAccountSizing", params);
			} else {
				filepath = service.executeJasperXLSX("DEP_DepositAccountSizingExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	
	//ms joji new reports put in Reports GL
	public String generateCASA_DailyTransactionIntegration(String filetype, String companyname, Date asof,
			String branch, String bname,String imgsrc) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("asof", asof);
		params.put("branch", branch);
		params.put("bname", bname);
		
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_DailyTransactionIntegration", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_DailyTransactionIntegrationExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	
	public String generateCASA_DailySummaryTransaction(String filetype, String companyname, Date asof,
			String branch, String bname,String imgsrc) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("asof", asof);
		params.put("branch", branch);
		params.put("bname", bname);
		
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_DailySummaryTransaction", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_DailySummaryTransactionExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	
	public String generateCASA_MonthEndSummaryTransaction(String filetype, String companyname,
			String branch, String bname,String imgsrc) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("branch", branch);
		params.put("bname", bname);
		
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_MonthEndSummaryTransaction", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_MonthEndSummaryTransactionExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	
	public String generateCASA_MonthEndTransactionIntegration(String filetype, String companyname,
			String branch, String bname,String imgsrc) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("branch", branch);
		params.put("bname", bname);
		
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_MonthEndTransactionIntegration", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_MonthEndTransactionIntegrationExcel", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	
	public String generateCASA_DailySummaryofTransactionPerProduct(String filetype, String companyname,
			String branch, String bname, String prodcode, String prodname, Date asof) {
		String filepath = null;
		params.put("companyname", companyname);
		params.put("branch", branch);
		params.put("bname", bname);
		params.put("prodcode", prodcode);
		params.put("prodname", prodname);
		params.put("asof", asof);
		params.put("SUBREPORT_ACTIVEDORMANT", SUBREPORT_ACTIVEDORMANT);
		
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_DailySummaryofTransactionPerProduct", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_DailySummaryofTransactionPerProduct", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	
	public String generateCASA_SAMasterlistSummary(String filetype, String companyname,
			String branch, String bname,String imgsrc) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("branch", branch);
		params.put("bname", bname);
		
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_SAMasterlistSummary", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_SAMasterlistSummary", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	
	public String generateCASA_TDMasterlistSummary(String filetype, String companyname,
			String branch, String bname,String imgsrc) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		params.put("imgsrc", imgsrc);
		params.put("companyname", companyname);
		params.put("branch", branch);
		params.put("bname", bname);
		
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CASA_TDMasterlistSummary", params);
			} else {
				filepath = service.executeJasperXLSX("CASA_TDMasterlistSummary", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
	
	public String generateLoanDisclosureStatement(String appno, String imgsrc) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		
		ReportsFacadeService service = new ReportsFacadeImpl();
		Map<String, Object> params = new HQLUtil().getMap();
		params.put("appno", appno);
		params.put("SUBREPORT_AMORTSCHED", SUBREPORT_AMORTSCHED);
		params.put("imgsrc", imgsrc);
		
		try {
				filepath = service.executeJasperPDF("rptLoanDisclosureStatement", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}
		
	public String generateLoanPromissoryNote(String appno, String imgsrc) {
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		
		ReportsFacadeService service = new ReportsFacadeImpl();
		Map<String, Object> params = new HQLUtil().getMap();
		params.put("appno", appno);
		params.put("imgsrc", imgsrc);
		
		try {
				filepath = service.executeJasperPDF("rptLoanPromissoryNote", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}
			
	
}
