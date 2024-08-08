package com.etel.lmsreport;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
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
import com.etel.utils.UserUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class LMSReportServiceImpl implements LMSReportService {

	private ReportsFacadeService rptservice = new ReportsFacadeImpl();
	private Map<String, Object> params = new HashMap<String, Object>();
	SecurityService serviceS = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private Map<String, Object> param = HQLUtil.getMap();
	DBService dbService = new DBServiceImpl();

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSReportForms> getLMSReportTransType(String codename) {
		List<LMSReportForms> form = new ArrayList<LMSReportForms>();
		DBService dbService = new DBServiceImpl();

		params.put("codename", codename);
		try {
			form = (List<LMSReportForms>) dbService.execSQLQueryTransformer(
					"select codename,codevalue,desc1 from TBCODETABLE where codename =:codename", params,
					LMSReportForms.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSReportForms> getLMSBranch() {
		List<LMSReportForms> form = new ArrayList<LMSReportForms>();
		DBService dbService = new DBServiceImpl();
		try {
			form = (List<LMSReportForms>) dbService.execSQLQueryTransformer(
					"SELECT branchcode, branchname, companycode, coopcode FROM TBBRANCH", params, LMSReportForms.class,
					1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSReportForms> getLMSReportTransStatus(String codename) {
		List<LMSReportForms> form = new ArrayList<LMSReportForms>();
		DBService dbService = new DBServiceImpl();

		params.put("codename", codename);
		try {
			form = (List<LMSReportForms>) dbService.execSQLQueryTransformer(
					"select codename,codevalue,desc1 from TBCODETABLE where codename =:codename", params,
					LMSReportForms.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public String listOfLoanAccts(String pnno, String fullname, String product, String companycode, String companyname,
			String rpttype) {
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			param.put("pnno", pnno);
			param.put("fullname", fullname);
			param.put("product", product);
			param.put("company", companycode);
			param.put("companyname", companyname);
			if (rpttype != null) {
				if (rpttype.equalsIgnoreCase("Excel")) {
					return rptservice.executeJasperXLSX("ListofLoanAccounts", param);
				} else {
					return rptservice.executeJasperPDF("ListofLoanAccounts", param);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// ------------------------------------------------------------TRANSACTIONAL----------------------------------------------------------

	// MAR

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSAnalyticalReportForm> getCollectionReport(LMSParametersForm forms) {
		List<LMSAnalyticalReportForm> list = new ArrayList<LMSAnalyticalReportForm>();
		try {
			param.put("startDate", forms.getStartDate() == null ? "" : forms.getStartDate());
			param.put("endDate", forms.getEndDate() == null ? "" : forms.getEndDate());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("officer", forms.getOfficer() == null ? "" : forms.getOfficer());
			param.put("loanProduct", forms.getLoanProduct() == null ? "" : forms.getLoanProduct());
			param.put("accountStatus", forms.getAccountStatus() == null ? "" : forms.getAccountStatus());
			param.put("sourceOfPayment", forms.getSourceOfPayment() == null ? "" : forms.getSourceOfPayment());

			list = (List<LMSAnalyticalReportForm>) dbService.execStoredProc(
					"EXEC sp_LMS_GetCollection :startDate, :endDate, :branch, :officer, :loanProduct, :accountStatus, :sourceOfPayment",
					param, LMSAnalyticalReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSDataForm> getInterestAccrualSetupforthePeriod(LMSParametersForm forms) {
		List<LMSDataForm> list = new ArrayList<LMSDataForm>();
		try {
			param.put("startDate", forms.getStartDate() == null ? "" : forms.getStartDate());
			param.put("endDate", forms.getEndDate() == null ? "" : forms.getEndDate());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("officer", forms.getOfficer() == null ? "" : forms.getOfficer());
			param.put("loanProduct", forms.getLoanProduct() == null ? "" : forms.getLoanProduct());

			list = (List<LMSDataForm>) dbService.execStoredProc(
					"EXEC sp_LMS_InterestAccrualSetupforthePeriod :startDate, :endDate, :branch, :officer, :loanProduct",
					param, LMSDataForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSDataForm> getListofLoanApplications(LMSParametersForm forms) {
		List<LMSDataForm> list = new ArrayList<LMSDataForm>();
		try {
			param.put("startDate", forms.getStartDate() == null ? "" : forms.getStartDate());
			param.put("endDate", forms.getEndDate() == null ? "" : forms.getEndDate());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("officer", forms.getOfficer() == null ? "" : forms.getOfficer());
			param.put("borrowerType", forms.getBorrowerType() == null ? "" : forms.getBorrowerType());
			param.put("loanProduct", forms.getLoanProduct() == null ? "" : forms.getLoanProduct());
			param.put("accountStatus", forms.getAccountStatus() == null ? "" : forms.getAccountStatus());

			list = (List<LMSDataForm>) dbService.execStoredProc(
					"EXEC sp_LOAN_LisfOfApplication :startDate, :endDate, :branch, :officer, :borrowerType, :loanProduct, :accountStatus ",
					param, LMSDataForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSDataForm> getListofLMSTransactions(LMSParametersForm forms) {
		List<LMSDataForm> list = new ArrayList<LMSDataForm>();
		try {
			param.put("startDate", forms.getStartDate() == null ? "" : forms.getStartDate());
			param.put("endDate", forms.getEndDate() == null ? "" : forms.getEndDate());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("officer", forms.getOfficer() == null ? "" : forms.getOfficer());
			param.put("borrowerType", forms.getBorrowerType() == null ? "" : forms.getBorrowerType());
			param.put("loanProduct", forms.getLoanProduct() == null ? "" : forms.getLoanProduct());
			param.put("transStat", forms.getTransStat() == null ? "" : forms.getTransStat());
			param.put("transType", forms.getTransType() == null ? "" : forms.getTransType());

			list = (List<LMSDataForm>) dbService.execStoredProc(
					"EXEC sp_LMS_ListofLMSTransactions :startDate, :endDate, :branch, :officer, :borrowerType, :loanProduct, :transStat, :transType",
					param, LMSDataForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSDataForm> getListofDecidedLoanApplications(LMSParametersForm forms) {
		List<LMSDataForm> list = new ArrayList<LMSDataForm>();
		try {
			param.put("startDate", forms.getStartDate() == null ? "" : forms.getStartDate());
			param.put("endDate", forms.getEndDate() == null ? "" : forms.getEndDate());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("officer", forms.getOfficer() == null ? "" : forms.getOfficer());
			param.put("borrowerType", forms.getBorrowerType() == null ? "" : forms.getBorrowerType());
			param.put("loanProduct", forms.getLoanProduct() == null ? "" : forms.getLoanProduct());
			param.put("accountStatus", forms.getAccountStatus() == null ? "" : forms.getAccountStatus());
			param.put("decision", forms.getDecision() == null ? "" : forms.getDecision());

			list = (List<LMSDataForm>) dbService.execStoredProc(
					"EXEC sp_LMS_ListofDecidedLoanApplications :startDate, :endDate, :branch, :officer, :borrowerType, :accountStatus, :loanProduct, :decision",
					param, LMSDataForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSDataForm> getListofLoanReleases(LMSParametersForm forms) {
		List<LMSDataForm> list = new ArrayList<LMSDataForm>();
		try {
			param.put("startDate", forms.getStartDate() == null ? "" : forms.getStartDate());
			param.put("endDate", forms.getEndDate() == null ? "" : forms.getEndDate());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("officer", forms.getOfficer() == null ? "" : forms.getOfficer());
			param.put("borrowerType", forms.getBorrowerType() == null ? "" : forms.getBorrowerType());
			param.put("loanProduct", forms.getLoanProduct() == null ? "" : forms.getLoanProduct());
			param.put("accountStatus", forms.getAccountStatus() == null ? "" : forms.getAccountStatus());
			list = (List<LMSDataForm>) dbService.execStoredProc(
					"EXEC sp_LMS_ListofLoanReleases :startDate, :endDate, :branch, :officer, :borrowerType, :loanProduct",
					param, LMSDataForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSDataForm> getJournalEntriesfortheDay(LMSParametersForm forms) {
		List<LMSDataForm> list = new ArrayList<LMSDataForm>();
		try {
			param.put("dateFilter", forms.getDateFilter());
			param.put("transDate", forms.getTransDate() == null ? "" : forms.getTransDate());
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("officer", forms.getOfficer() == null ? "" : forms.getOfficer());

			list = (List<LMSDataForm>) dbService.execStoredProc(
					"EXEC sp_LMS_JournalEntriesfortheDay :dateFilter, :transDate, :from, :to, :branch, :officer", param,
					LMSDataForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSDataForm> getScheduleofReceivedDocumentsSecurities(LMSParametersForm forms) {
		List<LMSDataForm> list = new ArrayList<LMSDataForm>();
		try {
			param.put("branch", forms.getBranch());
			param.put("officer", forms.getOfficer());
			param.put("endDate", forms.getEndDate());
			param.put("startDate", forms.getStartDate());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());

			String myQuery = "SELECT a.datesubmitted, a.documentname,"
					+ " a.appno AS loanNo, b.pnno, b.cifname, (SELECT branchname FROM " + forms.getLosDbLink()
					+ ".dbo.TBBRANCH WHERE branchcode = b.branchcode) AS branch," + " (SELECT productname FROM "
					+ forms.getLosDbLink() + ".dbo.TBLOANPRODUCT WHERE productcode = b.loanproduct) AS loanProduct,"
					+ " uploadedby" + " FROM " + forms.getLosDbLink() + ".dbo.TBDOCSPERAPPLICATION a" + " LEFT JOIN "
					+ forms.getLosDbLink() + ".dbo.TBLSTAPP b ON a.appno = b.appno ";

			if (forms.getStartDate() != null && forms.getEndDate() != null) {
				myQuery += " WHERE CAST(a.datesubmitted AS DATE) BETWEEN CAST(:startDate AS DATE) AND CAST(:endDate AS DATE)";
			}
			if (forms.getBranch() != null) {
				myQuery += " AND b.branchcode = :branch ";
			}
			if (forms.getOfficer() != null) {
				myQuery += " AND b.createdby = :officer ";
			}
			list = (List<LMSDataForm>) dbService.execSQLQueryTransformer(myQuery, param, LMSDataForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSDataForm> getLoanReleasesPerRange(LMSParametersForm forms) {
		List<LMSDataForm> list = new ArrayList<LMSDataForm>();
		try {
			param.put("branch", forms.getBranch());
			param.put("officer", forms.getOfficer());
			param.put("startDate", forms.getStartDate());
			param.put("endDate", forms.getEndDate());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			String myQuery = " SELECT a.productname AS loanProduct," + " (SELECT branchname FROM "
					+ forms.getLosDbLink() + ".dbo.TBBRANCH where branchcode = b.legbranch) AS branch,"
					+ " SUM(CASE WHEN b.pnamt BETWEEN 0 AND 100000 THEN 1 ELSE 0 END) AS upTo100kCount,"
					+ " SUM(CASE WHEN b.pnamt BETWEEN 0 AND 100000 THEN pnamt ELSE 0 END) AS upTo100kTotal,"
					+ " SUM(CASE WHEN b.pnamt BETWEEN 100001 AND 500000 THEN 1 ELSE 0 END) AS upTo500kCount,"
					+ " SUM(CASE WHEN b.pnamt BETWEEN 100001 AND 500000 THEN pnamt ELSE 0 END) AS upTo500kTotal,"
					+ " SUM(CASE WHEN b.pnamt BETWEEN 500001 AND 1000000 THEN 1 ELSE 0 END) AS upTo1mCount,"
					+ " SUM(CASE WHEN b.pnamt BETWEEN 500001 AND 1000000 THEN pnamt ELSE 0 END) AS upTo1mTotal,"
					+ " SUM(CASE WHEN b.pnamt BETWEEN 1000001 AND 2000000 THEN 1 ELSE 0 END) AS upTo2mCount,"
					+ " SUM(CASE WHEN b.pnamt BETWEEN 1000001 AND 2000000 THEN pnamt ELSE 0 END) AS upTo2mTotal,"
					+ " SUM(CASE WHEN b.pnamt BETWEEN 2000001 AND 5000000 THEN 1 ELSE 0 END) AS upTo5mCount,"
					+ " SUM(CASE WHEN b.pnamt BETWEEN 2000001 AND 5000000 THEN pnamt ELSE 0 END) AS upTo5mTotal,"
					+ " SUM(CASE WHEN b.pnamt BETWEEN 5000001 AND 10000000 THEN 1 ELSE 0 END) AS upTo10mCount,"
					+ " SUM(CASE WHEN b.pnamt BETWEEN 5000001 AND 10000000 THEN pnamt ELSE 0 END) AS upTo10mTotal,"
					+ " SUM(CASE WHEN b.pnamt > 10000001 THEN 1 ELSE 0 END) AS upTo10mOverCount,"
					+ " SUM(CASE WHEN b.pnamt > 10000001 THEN pnamt ELSE 0 END) AS upTo10mOverTotal,"
					+ " SUM(CASE WHEN b.pnamt > 0 THEN 1 ELSE 0 END) AS totalCount,"
					+ " SUM(CASE WHEN b.pnamt > 0 THEN pnamt ELSE 0 END) AS totalAmount" + " FROM "
					+ forms.getLosDbLink() + ".dbo.TBLOANPRODUCT a " + " RIGHT JOIN " + forms.getLosDbLink()
					+ ".dbo.TBLOANS b ON b.prodcode = a.productcode";

			if (forms.getStartDate() != null && forms.getEndDate() != null) {
				myQuery += " WHERE CAST(b.dtbook AS DATE) BETWEEN CAST(:startDate AS DATE) AND CAST(:endDate AS DATE)";
			}
			if (forms.getBranch() != null) {
				myQuery += " AND b.legbranch = :branch ";
			}
			if (forms.getOfficer() != null) {
				myQuery += " AND b.loanoff = :officer ";
			}

			myQuery += " GROUP BY a.productname, b.legbranch ORDER BY a.productname ASC";
			list = (List<LMSDataForm>) dbService.execSQLQueryTransformer(myQuery, param, LMSDataForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSDataForm> getListOfLoanReleasesPerFirmSize(LMSParametersForm forms) {
		List<LMSDataForm> list = new ArrayList<LMSDataForm>();
		try {
			param.put("branch", forms.getBranch());
			param.put("officer", forms.getOfficer());
			param.put("startDate", forms.getStartDate());
			param.put("endDate", forms.getEndDate());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			String myQuery = " SELECT a.productname AS loanProduct," + " (SELECT branchname FROM "
					+ forms.getLosDbLink() + ".dbo.TBBRANCH where branchcode = b.legbranch) AS branch,"
					+ " SUM(CASE WHEN b.pnamt BETWEEN 0 AND 3000000 THEN 1 ELSE 0 END) AS microCount,"
					+ " SUM(CASE WHEN b.pnamt BETWEEN 0 AND 3000000 THEN pnamt ELSE 0 END) AS microTotal,"
					+ " SUM(CASE WHEN b.pnamt BETWEEN 3000001 AND 15000000 THEN 1 ELSE 0 END) AS smallCount,"
					+ " SUM(CASE WHEN b.pnamt BETWEEN 3000001 AND 15000000 THEN pnamt ELSE 0 END) AS smallTotal,"
					+ " SUM(CASE WHEN b.pnamt BETWEEN 1500001 AND 100000000 THEN 1 ELSE 0 END) AS mediumCount,"
					+ " SUM(CASE WHEN b.pnamt BETWEEN 1500001 AND 100000000 THEN pnamt ELSE 0 END) AS mediumTotal,"
					+ " SUM(CASE WHEN b.pnamt > 100000001 THEN 1 ELSE 0 END) AS largeCount,"
					+ " SUM(CASE WHEN b.pnamt > 100000001 THEN pnamt ELSE 0 END) AS largeTotal,"
					+ " SUM(CASE WHEN b.pnamt > 0 THEN 1 ELSE 0 END) AS totalCount,"
					+ " SUM(CASE WHEN b.pnamt > 0 THEN pnamt ELSE 0 END) AS totalAmount" + " FROM "
					+ forms.getLosDbLink() + ".dbo.TBLOANPRODUCT a" + " RIGHT JOIN " + forms.getLosDbLink()
					+ ".dbo.TBLOANS b ON b.prodcode = a.productcode" + " LEFT JOIN " + forms.getLosDbLink()
					+ ".dbo.TBLSTAPP c ON c.appno = b.applno" + " WHERE c.customertype = 2";

			if (forms.getStartDate() != null && forms.getEndDate() != null) {
				myQuery += " AND CAST(b.dtbook AS DATE) BETWEEN CAST(:startDate AS DATE) AND CAST(:endDate AS DATE)";
			}
			if (forms.getBranch() != null) {
				myQuery += " AND b.legbranch = :branch ";
			}
			if (forms.getOfficer() != null) {
				myQuery += " AND b.loanoff = :officer ";
			}
			myQuery += " GROUP BY a.productname, b.legbranch ORDER BY a.productname ASC";
			list = (List<LMSDataForm>) dbService.execSQLQueryTransformer(myQuery, param, LMSDataForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	// ------------------------------------------------------------AnalyticalReport----------------------------------------------------------

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSUserForms> getLoansUsers(String branch) {
		List<LMSUserForms> listLoanUser = new ArrayList<LMSUserForms>();
		try {
			param.put("branchcode", UserUtil.getUserByUsername(serviceS.getUserName()).getBranchcode());
			listLoanUser = (List<LMSUserForms>) dbService
					.execSQLQueryTransformer("select username, a.userid, branchcode as branchid" + " from tbuser a"
							+ " WHERE username IN (select username from TBUSERROLES where rolename like '%lms%' OR rolename like '%loan%')"
							+ " and branchcode = :branchcode ", param, LMSUserForms.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print("MAR " + listLoanUser);
		return listLoanUser;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSAnalyticalReportForm> getScheduleofAccountswithArrearages(LMSParametersForm forms) {
		List<LMSAnalyticalReportForm> list = new ArrayList<LMSAnalyticalReportForm>();
		try {
			param.put("branch", forms.getBranch());
			param.put("officer", forms.getOfficer());
			param.put("transDate", forms.getTransDate());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());

			String myQuery = "SELECT a.nxtdueilduedt as nextDueDate,a.pnno, b.loanno,"
					+ " a.fullname, (SELECT branchname FROM " + forms.getLosDbLink()
					+ ".dbo.TBBRANCH WHERE branchcode = a.legbranch) AS branch," + " (SELECT productname FROM "
					+ forms.getLosDbLink() + ".dbo.TBLOANPRODUCT WHERE productcode = a.prodcode) AS loanProduct,"
					+ " a.pnamt AS originalLoanAmount," + " a.pnterm AS term, a.faceamt AS monthlyAmozt,"
					+ " a.loanbal AS outsBalance, a.iltogo AS instToGo," + " (SELECT desc1 FROM " + forms.getLosDbLink()
					+ ".dbo.TBCODETABLE WHERE codename='ACCTSTS' AND codevalue = a.acctsts) AS acctsts,"
					+ " a.tpdilno AS lastInstPaid," + " a.ar1 AS accountReceivable, a.lpcbal AS latePaymentCharge, "
					+ " case when a.tdueilno >= a.tpdilno" + " then a.nxtdueilint" + " else a.nxtintamt - a.partialint"
					+ " end AS interest," + " case when a.tdueilno >= a.tpdilno" + " then a.nxtdueilprin"
					+ " else a.nxtprinamt - a.partialprin" + " end principal,"
					+ " (SELECT (a.ar1 + a.lpcbal + a.unpaidint + a.unpaidprin + nxtintamt +  nxtprinamt))AS amountToUpdate"
					+ " FROM " + forms.getLosDbLink() + ".dbo.TBLOANS a" + " LEFT JOIN " + forms.getLosDbLink()
					+ ".dbo.TBACCOUNTINFO b ON a.accountno = b.loanno "
					+ " WHERE a.acctsts < '5' AND tpdilno <= tdueilno";

			if (forms.getTransDate() != null) {
				myQuery += " AND CAST(a.nxtdueilduedt AS DATE) BETWEEN CAST(:transDate AS DATE) AND CAST(:transDate AS DATE)";
			}
			if (forms.getBranch() != null) {
				myQuery += " AND a.legbranch = :branch ";
			}
			if (forms.getOfficer() != null) {
				myQuery += " AND a.loanoff = :officer ";
			}
			list = (List<LMSAnalyticalReportForm>) dbService.execSQLQueryTransformer(myQuery, param,
					LMSAnalyticalReportForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSAnalyticalReportForm> getLoansTargetvsPerformance(LMSParametersForm forms) {
		List<LMSAnalyticalReportForm> list = new ArrayList<LMSAnalyticalReportForm>();
		try {
			param.put("branch", forms.getBranch());
			param.put("officer", forms.getOfficer());
			param.put("startDate", forms.getStartDate());
			param.put("endDate", forms.getEndDate());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			param.put("enterTargetCount", forms.getEnterTargetCount());
			param.put("enterTargetAmount", forms.getEnterTargetAmount());

			String myQuery = " SELECT a.productname AS loanProduct," + " (SELECT branchname FROM "
					+ forms.getLosDbLink() + ".dbo.TBBRANCH where branchcode = b.legbranch) AS branch,"
					+ " SUM(CASE WHEN b.pnamt > 0 THEN 1 ELSE 0 END) AS totalActualPerformanceCount,"
					+ " SUM(CASE WHEN b.pnamt > 0 THEN pnamt ELSE 0 END) AS totalActualPerformanceAmount,"
					+ " (CASE WHEN :enterTargetCount > 0 THEN :enterTargetCount ELSE 0 END) AS totalTargetCount,"
					+ " (CASE WHEN :enterTargetAmount > 0 THEN :enterTargetAmount ELSE 0 END) AS totalTargetAmount,"
					+ " (SUM(CASE WHEN b.pnamt > 0 THEN 1 ELSE 0 END) - :enterTargetCount) AS totalDifferenceCount,"
					+ " (SUM(CASE WHEN b.pnamt > 0 THEN pnamt ELSE 0 END) - :enterTargetAmount) AS totalDifferenceTargetAmount"
					+ " FROM " + forms.getLosDbLink() + ".dbo.TBLOANPRODUCT a " + " RIGHT JOIN " + forms.getLosDbLink()
					+ ".dbo.TBLOANS b ON b.prodcode = a.productcode";

			if (forms.getStartDate() != null && forms.getEndDate() != null) {
				myQuery += " WHERE CAST(b.dtbook AS DATE) BETWEEN CAST (:startDate AS DATE) AND CAST (:endDate AS DATE)";
			}
			if (forms.getBranch() != null) {
				myQuery += " AND b.legbranch = :branch ";
			}
			if (forms.getOfficer() != null) {
				myQuery += " AND b.loanoff = :officer ";
			}
			myQuery += " GROUP BY a.productname, b.legbranch ORDER BY a.productname ASC";
			System.out.print("MAR " + myQuery);
			list = (List<LMSAnalyticalReportForm>) dbService.execSQLQueryTransformer(myQuery, param,
					LMSAnalyticalReportForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSAnalyticalReportForm> getLoanGrantPerformanceReviewPerBranchSolicitingOfficerApprovingOfficer(
			LMSParametersForm forms) {
		List<LMSAnalyticalReportForm> list = new ArrayList<LMSAnalyticalReportForm>();
		try {
			param.put("dateFilter", forms.getDateFilter());
			param.put("asOf", forms.getAsOf() == null ? "" : forms.getAsOf());
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("officer", forms.getOfficer() == null ? "" : forms.getOfficer());
			list = (List<LMSAnalyticalReportForm>) dbService.execStoredProc(
					"EXEC sp_LMS_LoanGrantPerformanceReviewPerBranchSolicitingOfficerApprovingOfficer :dateFilter, :asOf, :from, :to, :branch, :officer",
					param, LMSAnalyticalReportForm.class, 1, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSAnalyticalReportForm> getLoanGrantPerformanceReviewPerBranch(LMSParametersForm forms) {
		List<LMSAnalyticalReportForm> list = new ArrayList<LMSAnalyticalReportForm>();
		try {
			param.put("dateFilter", forms.getDateFilter());
			param.put("asOf", forms.getAsOf() == null ? "" : forms.getAsOf());
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("officer", forms.getOfficer() == null ? "" : forms.getOfficer());
			list = (List<LMSAnalyticalReportForm>) dbService.execStoredProc(
					"EXEC sp_LMS_LoanGrantPerformanceReviewPerBranch :dateFilter, :asOf, :from, :to, :branch, :officer",
					param, LMSAnalyticalReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSAnalyticalReportForm> getLoanCollectionsPerBranchSolicitingOfficerApprovingOfficer(
			LMSParametersForm forms) {
		List<LMSAnalyticalReportForm> list = new ArrayList<LMSAnalyticalReportForm>();
		try {
			param.put("startDate", forms.getStartDate());
			param.put("endDate", forms.getEndDate());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			param.put("branch", forms.getBranch());
			param.put("officer", forms.getOfficer());

			String myQuery = " SELECT a.productname AS loanProduct," + " (SELECT branchname FROM "
					+ forms.getLosDbLink() + ".dbo.TBBRANCH where branchcode = c.legbranch) AS branch,"
					+ " SUM(CASE WHEN b.txamt > 0 AND txcode = 40 THEN 1 ELSE 0 END) AS totalCollectionCount,"
					+ " SUM(CASE WHEN b.txamt > 0 AND txcode = 40 THEN txamt ELSE 0 END) AS totalCollectionAmount,"
					+ " SUM(CASE WHEN b.txacctsts = 1 AND txcode = 40 THEN 1 ELSE 0 END) AS totalCurrentCollectionCount,"
					+ " SUM(CASE WHEN b.txacctsts = 1 AND txcode = 40 THEN txamt ELSE 0 END) AS totalCurrentCollection,"
					+ " SUM(CASE WHEN b.txacctsts = 3 AND txcode = 40 THEN 1 ELSE 0 END) AS totalPastDueCollectionCount,"
					+ " SUM(CASE WHEN b.txacctsts = 3 AND txcode = 40 THEN txamt ELSE 0 END) AS totalPastDueCollection,"
					+ " SUM(CASE WHEN b.txacctsts = 5 AND txcode = 40 THEN 1 ELSE 0 END) AS totalPaidOffCollectionCount,"
					+ " SUM(CASE WHEN b.txacctsts = 5 AND txcode = 40 THEN txamt ELSE 0 END) AS totalPaidOffCollection,"
					+ " SUM(CASE WHEN b.txacctsts = 4 AND txcode = 40 THEN 1 ELSE 0 END) AS totalLitigationCollectionCount,"
					+ " SUM(CASE WHEN b.txacctsts = 4 AND txcode = 40 THEN txamt ELSE 0 END) AS totalLitigationCollection"
					+ " FROM " + forms.getLosDbLink() + ".dbo.TBLOANPRODUCT a " + " RIGHT JOIN " + forms.getLosDbLink()
					+ ".dbo.TBLNTXJRNL b ON b.txprod = a.productcode" + " RIGHT JOIN " + forms.getLosDbLink()
					+ ".dbo.TBLOANS c ON c.prodcode = a.productcode";

			if (forms.getStartDate() != null && forms.getEndDate() != null) {
				myQuery += " WHERE CAST(b.txdate AS DATE) BETWEEN CAST (:startDate AS DATE) AND CAST (:endDate AS DATE)";
			}

			if (forms.getBranch() != null) {
				myQuery += " AND c.legbranch = :branch ";
			}

			if (forms.getLoanOfficer() != null) {
				myQuery += " AND c.loanoff = :officer";
			}
			myQuery += " GROUP BY a.productname, c.legbranch ORDER BY a.productname ASC";
			System.out.print("MAR " + myQuery);
			list = (List<LMSAnalyticalReportForm>) dbService.execSQLQueryTransformer(myQuery, param,
					LMSAnalyticalReportForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSAnalyticalReportForm> getListOfLoanAccountsFullyPaidBeforeMaturityDate(LMSParametersForm forms) {
		List<LMSAnalyticalReportForm> list = new ArrayList<LMSAnalyticalReportForm>();
		try {
			param.put("startDate", forms.getStartDate());
			param.put("endDate", forms.getEndDate());
			param.put("branch", forms.getBranch());
			param.put("officer", forms.getOfficer());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			String myQuery = " SELECT a.pnno, a.pnno AS loanno," + " (SELECT branchname FROM " + forms.getLosDbLink()
					+ ".dbo.TBBRANCH WHERE branchcode = a.legbranch) AS branch," + " (SELECT productname FROM "
					+ forms.getLosDbLink() + ".dbo.TBLOANPRODUCT WHERE productcode = a.prodcode) AS loanProduct,"
					+ " a.fullname AS accountName, a.matdt AS matDate," + " (SELECT TOP 1 txloanbal from "
					+ forms.getLosDbLink()
					+ ".dbo.TBLNTXJRNL where CAST (txdate AS DATE) <= CAST (:endDate AS DATE) AND pnno = a.pnno order by txdate desc) AS outsBalance,"
					+ " (SELECT TOP 1 txdate from " + forms.getLosDbLink()
					+ ".dbo.TBLNTXJRNL where CAST (txdate AS DATE) <= CAST (:endDate AS DATE) AND pnno = a.pnno order by txdate desc) AS dateOfPayment,"
					+ " (SELECT TOP 1 txamt from " + forms.getLosDbLink()
					+ ".dbo.TBLNTXJRNL where CAST (txdate AS DATE) <= CAST (:endDate AS DATE) AND pnno = a.pnno order by txdate desc) AS amountOfPayment,"
					+ " a.loanoff AS loanOfficer" + " FROM " + forms.getLosDbLink() + ".dbo.TBLOANS a ";

			if (forms.getStartDate() != null && forms.getEndDate() != null) {
				myQuery += " WHERE CAST(a.matdt AS DATE) BETWEEN CAST (:startDate AS DATE) AND CAST (:endDate AS DATE)";
			}
			if (forms.getBranch() != null) {
				myQuery += " AND a.legbranch = :branch ";
			}
			if (forms.getOfficer() != null) {
				myQuery += " AND a.loanoff = :officer ";
			}
			System.out.print("MAR " + myQuery);
			list = (List<LMSAnalyticalReportForm>) dbService.execSQLQueryTransformer(myQuery, param,
					LMSAnalyticalReportForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSAnalyticalReportForm> getRecordedLoanIncomeperBranch(LMSParametersForm forms) {
		List<LMSAnalyticalReportForm> list = new ArrayList<LMSAnalyticalReportForm>();
		try {
			param.put("dateFilter", forms.getDateFilter());
			param.put("asOf", forms.getAsOf() == null ? "" : forms.getAsOf());
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("officer", forms.getOfficer() == null ? "" : forms.getOfficer());
			list = (List<LMSAnalyticalReportForm>) dbService.execStoredProc(
					"EXEC sp_LMS_RecordedLoanIncomeperBranch :dateFilter, :asOf, :from, :to, :branch, :officer", param,
					LMSAnalyticalReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	// ----------------------------------------------------------ExceptionReportForm-------------------------------------------------------------

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSExceptionReportForm> getListofDisapprovedTransactions(LMSParametersForm forms) {
		List<LMSExceptionReportForm> list = new ArrayList<LMSExceptionReportForm>();
		try {
			param.put("branch", forms.getBranch());
			param.put("officer", forms.getOfficer());
			param.put("endDate", forms.getEndDate());
			param.put("startDate", forms.getStartDate());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());

			String myQuery = "SELECT a.txvaldt AS transactionValueDate,"
					+ " a.txrefno AS transactionRefNo, (SELECT branchname FROM " + forms.getLosDbLink()
					+ ".dbo.TBBRANCH WHERE branchcode = c.legbranch) AS branch," + " (Select desc1 FROM "
					+ forms.getLosDbLink()
					+ ".dbo.TBCODETABLE  WHERE codename = 'TXCODE' and codevalue = a.txcode) AS transactioncode,"
					+ " a.pnno AS pnNo,a.accountno AS loanAcctNo," + " a.clientname AS acctName,"
					+ " a.txamount AS transactionAmount," + " (Select desc1 FROM " + forms.getLosDbLink()
					+ ".dbo.TBCODETABLE  WHERE codename = 'TXSTAT' and codevalue = a.txstatus) AS transtat,"
					+ " a.approvedby AS solicitingOfficer," + " a.particulars" + " FROM " + forms.getLosDbLink()
					+ ".dbo.TBLOANFIN a " + " LEFT JOIN " + forms.getLosDbLink() + ".dbo.TBLOANS c ON a.pnno = c.pnno "
					+ " WHERE a.txstatus = 'C'";

			if (forms.getStartDate() != null && forms.getEndDate() != null) {
				myQuery += " AND CAST(a.txvaldt AS DATE) BETWEEN CAST(:startDate AS DATE) AND CAST(:endDate AS DATE) ";
			}
			if (forms.getBranch() != null) {
				myQuery += " AND c.legbranch = :branch ";
			}
			if (forms.getOfficer() != null) {
				myQuery += " AND c.loanoff = :officer ";
			}
			list = (List<LMSExceptionReportForm>) dbService.execSQLQueryTransformer(myQuery, param,
					LMSExceptionReportForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSExceptionReportForm> getListofUnpostedFinancialTransactions(LMSParametersForm forms) {
		List<LMSExceptionReportForm> list = new ArrayList<LMSExceptionReportForm>();
		try {
			param.put("dateFilter", forms.getDateFilter());
			param.put("asOf", forms.getAsOf() == null ? "" : forms.getAsOf());
			param.put("startDate", forms.getStartDate());
			param.put("endDate", forms.getEndDate());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("officer", forms.getOfficer() == null ? "" : forms.getOfficer());
			list = (List<LMSExceptionReportForm>) dbService.execStoredProc(
					"EXEC sp_LMS_ListofUnpostedFinancialTransactions :dateFilter, :asOf, :startDate, :endDate, :branch, :officer",
					param, LMSExceptionReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	// 08-27-2021 MAR
	@SuppressWarnings("unchecked")
	@Override
	public List<LMSBalancesReportForm> getListofLoanAccounts(LMSParametersForm forms) {
		List<LMSBalancesReportForm> list = new ArrayList<LMSBalancesReportForm>();
		try {
			param.put("startDate", forms.getStartDate() == null ? "" : forms.getStartDate());
			param.put("endDate", forms.getEndDate() == null ? "" : forms.getEndDate());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("officer", forms.getOfficer() == null ? "" : forms.getOfficer());
			param.put("borrowerType", forms.getBorrowerType() == null ? "" : forms.getBorrowerType());
			param.put("loanProduct", forms.getLoanProduct() == null ? "" : forms.getLoanProduct());
			param.put("accountStatus", forms.getAccountStatus() == null ? "" : forms.getAccountStatus());
			list = (List<LMSBalancesReportForm>) dbService.execStoredProc(
					"EXEC sp_LMS_ListofLoanAccounts :startDate, :endDate, :branch, :officer, :borrowerType, :loanProduct, :accountStatus",
					param, LMSBalancesReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSBalancesReportForm> getScheduleofDueAmortizations(LMSParametersForm forms) {
		List<LMSBalancesReportForm> list = new ArrayList<LMSBalancesReportForm>();
		try {
			param.put("branch", forms.getBranch());
			param.put("officer", forms.getOfficer());
			param.put("endDate", forms.getEndDate());
			param.put("startDate", forms.getStartDate());
			param.put("loanProduct", forms.getLoanProduct());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());

			String myQuery = "SELECT " + " CASE" + " WHEN a.nxtdueilno >= a.tpdilno " + " THEN nxtdueilduedt"
					+ " ELSE a.nxtduedt" + " END AS amortzDate, (SELECT branchname FROM " + forms.getLosDbLink()
					+ ".dbo.TBBRANCH WHERE branchcode = a.legbranch) AS branch,"
					+ " a.pnno AS pnNo, a.pnno AS applicationNo, a.fullname AS accountName,"
					+ " nxtintamt + nxtprinamt AS amortzAmount," + " nxtilno AS installmentNo,"
					+ " iltogo AS totalNoOfInstal," + " (SELECT productname FROM " + forms.getLosDbLink()
					+ ".dbo.TBLOANPRODUCT WHERE productcode = a.prodcode) AS loanProduct,"
					+ " (a.ar1 + a.lpcbal + a.unpaidint + a.unpaidprin)AS totalAmountToUpdate" + " FROM "
					+ forms.getLosDbLink() + ".dbo.TBLOANS a ";

			if (forms.getStartDate() != null && forms.getEndDate() != null) {
				myQuery += " WHERE CAST(a.nxtdueilduedt AS DATE) BETWEEN CAST(:startDate AS DATE) AND CAST(:endDate AS DATE)";
			}
			if (forms.getBranch() != null) {
				myQuery += " AND a.legbranch = :branch ";
			}
			if (forms.getOfficer() != null) {
				myQuery += " AND a.loanoff = :officer ";
			}
			if (forms.getLoanProduct() != null) {
				myQuery += " AND a.prodcode = :loanProduct ";
			}
			list = (List<LMSBalancesReportForm>) dbService.execSQLQueryTransformer(myQuery, param,
					LMSBalancesReportForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSBalancesReportForm> getScheduleofOutstandingAccruedInterestReceivables(LMSParametersForm forms) {
		List<LMSBalancesReportForm> list = new ArrayList<LMSBalancesReportForm>();
		try {
			param.put("dateFilter", forms.getDateFilter());
			param.put("transDate", forms.getTransDate());
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("officer", forms.getOfficer() == null ? "" : forms.getOfficer());
			param.put("loanProduct", forms.getLoanProduct() == null ? "" : forms.getLoanProduct());

			list = (List<LMSBalancesReportForm>) dbService.execStoredProc(
					"EXEC sp_LMS_ScheduleofOutstandingAccruedInterestReceivables :dateFilter, :transDate, :from, :to, :branch, :officer, :loanProduct",
					param, LMSBalancesReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSBalancesReportForm> getScheduleofOutstandingUnearnedInterestDiscounts(LMSParametersForm forms) {
		List<LMSBalancesReportForm> list = new ArrayList<LMSBalancesReportForm>();
		try {
			param.put("dateFilter", forms.getDateFilter());
			param.put("transDate", forms.getTransDate());
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("officer", forms.getOfficer() == null ? "" : forms.getOfficer());
			param.put("loanProduct", forms.getLoanProduct() == null ? "" : forms.getLoanProduct());
			list = (List<LMSBalancesReportForm>) dbService.execStoredProc(
					"EXEC sp_LMS_ScheduleofOutstandingAccruedInterestReceivables :dateFilter, :transDate, :from, :to, :branch, :officer, :loanProduct",
					param, LMSBalancesReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSBalancesReportForm> getBorrowerListofLoanAccounts(LMSParametersForm forms) {
		List<LMSBalancesReportForm> list = new ArrayList<LMSBalancesReportForm>();
		try {
			param.put("transDate", forms.getTransDate());
			param.put("branch", forms.getBranch());
			param.put("officer", forms.getOfficer());
			param.put("borrowerType", forms.getBorrowerType());
			param.put("loanProduct", forms.getLoanProduct());
			param.put("businessName", forms.getBusinessName());
			param.put("lastName", forms.getLastName());
			param.put("firstName", forms.getFirstName());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());

			String myQuery = "";
			if (forms.getTransDate() != null) {
				myQuery = "SELECT DISTINCT a.pnno AS pnNo, a.pnno AS loanNo,"
						+ " b.cifname AS accountName, (SELECT branchname FROM " + forms.getLosDbLink()
						+ ".dbo.TBBRANCH WHERE branchcode = a.legbranch) AS branch, " + " (SELECT productname FROM "
						+ forms.getLosDbLink() + ".dbo.TBLOANPRODUCT WHERE productcode = a.prodcode) AS loanProduct,"
						+ " a.dtbook AS dateAvailed," + " a.matdt AS maturityDate, a.pnintrate AS intRate,"
						+ " a.pnterm AS term, a.faceamt AS origAmount," + " (SELECT TOP 1 txprinbal from "
						+ forms.getLosDbLink()
						+ ".dbo.TBLNTXJRNL where CAST (txdate AS DATE) <= CAST (:transDate AS DATE) AND pnno = a.pnno  order by txdate desc) AS prinBal,"
						+ " (SELECT TOP 1 txloanbal from " + forms.getLosDbLink()
						+ ".dbo.TBLNTXJRNL where CAST (txdate AS DATE) <= CAST (:transDate AS DATE) AND pnno = a.pnno order by txdate desc) AS outLoanBal,"
						+ " (SELECT TOP 1 txuidb from " + forms.getLosDbLink()
						+ ".dbo.TBLNTXJRNL where CAST (txdate AS DATE) <= CAST (:transDate AS DATE) AND pnno = a.pnno order by txdate desc) AS uidBal, "
						+ " (SELECT TOP 1 txair from " + forms.getLosDbLink()
						+ ".dbo.TBLNTXJRNL where CAST (txdate AS DATE) <= CAST (:transDate AS DATE) AND pnno = a.pnno order by txdate desc) AS airBal,"
						+ " (a.ar1 + a.lpcbal + a.unpaidint + a.unpaidprin)AS totalAmountToUpdate,"
						+ " (SELECT desc1 FROM " + forms.getLosDbLink()
						+ ".dbo.TBCODETABLE WHERE codename= 'ACCTSTS' AND codevalue = a.acctsts) AS acctsts,"
						+ " a.loanoff AS solicitingOff" + " FROM " + forms.getLosDbLink() + ".dbo.TBLOANS a "
						+ " LEFT JOIN " + forms.getLosDbLink() + ".dbo.TBLSTAPP b ON a.applno = b.appno" + " LEFT JOIN "
						+ forms.getLosDbLink() + ".dbo.TBLNTXJRNL c ON a.pnno = c.pnno ";
				myQuery += " WHERE CAST(a.dtbook AS DATE) <= CAST(:transDate AS DATE)";
			}

			if (forms.getBranch() != null) {
				myQuery += " AND a.legbranch = :branch ";
			}
			if (forms.getOfficer() != null) {
				myQuery += " AND a.loanoff = :officer ";
			}

			if (forms.getBorrowerType() != null) {
				if (forms.getBorrowerType().equals("1")) {

					if (forms.getLastName() != null) {
						String lastName = forms.getLastName();
						myQuery += " AND b.lastname like '%" + lastName + "%'";
					}
					if (forms.getFirstName() != null) {
						String firstName = forms.getFirstName();
						myQuery += " AND b.firstname like '%" + firstName + "%'";
					}
					if (forms.getFirstName() != null || forms.getLastName() != null) {
						myQuery += " AND b.customertype = '1' ";
					} else {
						myQuery += " AND b.customertype = '1' ";
					}
				}
				if (forms.getBorrowerType().equals("2")) {
					if (forms.getBusinessName() != null) {
						String businessName = forms.getBusinessName();
						myQuery += " AND a.fullname like '%" + businessName + "%' AND b.customertype = '2' ";
					} else {
						myQuery += " AND b.customertype = '2' ";
					}
				}
			}
			System.out.print("MAR " + myQuery);
			list = (List<LMSBalancesReportForm>) dbService.execSQLQueryTransformer(myQuery, param,
					LMSBalancesReportForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSAnalyticalReportForm> getDelinquencyBucketListDueAmount(LMSParametersForm forms) {
		List<LMSAnalyticalReportForm> list = new ArrayList<LMSAnalyticalReportForm>();
		try {
			param.put("asOf", forms.getAsOf() == null ? "" : forms.getAsOf());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("officer", forms.getOfficer() == null ? "" : forms.getOfficer());

			list = (List<LMSAnalyticalReportForm>) dbService.execStoredProc(
					"EXEC sp_LMS_DelinquencyBucketListDueAmount :asOf, :branch, :officer", param,
					LMSAnalyticalReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSAnalyticalReportForm> getDelinquencyBucketListLoanAccount(LMSParametersForm forms) {
		List<LMSAnalyticalReportForm> list = new ArrayList<LMSAnalyticalReportForm>();
		try {
			param.put("branch", forms.getBranch());
			param.put("officer", forms.getOfficer());
			param.put("transDate", forms.getTransDate());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			String myQuery = "SELECT pnno AS pnNo, pnno AS loanNo, fullname AS accountName, (SELECT branchname FROM "
					+ forms.getLosDbLink() + ".dbo.TBBRANCH WHERE branchcode = legbranch) AS branch,"
					+ " (SELECT productname FROM " + forms.getLosDbLink()
					+ ".dbo.TBLOANPRODUCT WHERE productcode = prodcode) as loanProd," + " (SELECT desc1 FROM "
					+ forms.getLosDbLink()
					+ ".dbo.TBCODETABLE WHERE codename='ACCTSTS' AND codevalue= CAST(acctsts AS varchar(5))) as accountStat,"
					+ " SUM(CASE WHEN ddlq BETWEEN 1 AND 30 THEN 1 ELSE 0 END) AS days1To30No,"
					+ " SUM(CASE WHEN ddlq BETWEEN 1 AND 30 THEN amortizationamt - partialint - partialprin WHEN ddlq > 30 THEN amortizationamt ELSE 0 END) AS sum1To30,"
					+ " SUM(CASE WHEN ddlq BETWEEN 1 AND 30 THEN(prinbal)ELSE(0) END) AS outstandingBalance1To30,"
					+ " SUM(CASE WHEN ddlq BETWEEN 31 AND 60 THEN 1 ELSE 0 END) AS days31To60No,"
					+ " SUM(CASE WHEN ddlq BETWEEN 31 AND 60 THEN amortizationamt - partialint - partialprin WHEN ddlq > 60 THEN amortizationamt ELSE 0 END) AS sum31To60,"
					+ " SUM(CASE WHEN ddlq BETWEEN 31 AND 60 THEN(prinbal)ELSE(0) END) AS outstandingBalance31To60,"
					+ " SUM(CASE WHEN ddlq BETWEEN 61 AND 90 THEN 1 ELSE 0 END) AS days61To90No,"
					+ " SUM(CASE WHEN ddlq BETWEEN 61 AND 90 THEN amortizationamt - partialint - partialprin WHEN ddlq > 90 THEN amortizationamt ELSE 0 END) AS sum61To90,"
					+ " SUM(CASE WHEN ddlq BETWEEN 61 AND 90 THEN(prinbal)ELSE(0) END) AS outstandingBalance61To90,"
					+ " SUM(CASE WHEN ddlq BETWEEN 91 AND 120 THEN 1 ELSE 0 END) AS days91To120No,"
					+ " SUM(CASE WHEN ddlq BETWEEN 91 AND 120 THEN amortizationamt - partialint - partialprin WHEN ddlq > 120 THEN amortizationamt ELSE 0 END) AS sum91To120,"
					+ " SUM(CASE WHEN ddlq BETWEEN 91 AND 120 THEN(prinbal)ELSE(0) END) AS outstandingBalance91To120,"
					+ " SUM(CASE WHEN ddlq BETWEEN 121 AND 150 THEN 1 ELSE 0 END) AS days121To150No,"
					+ " SUM(CASE WHEN ddlq BETWEEN 121 AND 150 THEN amortizationamt - partialint - partialprin WHEN ddlq > 150 THEN amortizationamt ELSE 0 END) AS sum121To150,"
					+ " SUM(CASE WHEN ddlq BETWEEN 121 AND 150 THEN(prinbal)ELSE(0) END) AS outstandingBalance121To150,"
					+ " SUM(CASE WHEN ddlq BETWEEN 151 AND 180 THEN 1 ELSE 0 END) AS days151To180No,"
					+ " SUM(CASE WHEN ddlq BETWEEN 151 AND 180 THEN amortizationamt - partialint - partialprin WHEN ddlq > 180 THEN amortizationamt ELSE 0 END) AS sum151To180,"
					+ " SUM(CASE WHEN ddlq BETWEEN 151 AND 180 THEN(prinbal)ELSE(0) END) AS outstandingBalance151To180,"
					+ " SUM(CASE WHEN ddlq >= 181 THEN 1 ELSE 0 END) AS days180NoPlus,"
					+ " SUM(CASE WHEN ddlq >= 181 THEN amortizationamt - partialint-partialprin ELSE 0 END) AS sum180Plus,"
					+ " SUM(CASE WHEN ddlq >= 181 THEN(prinbal)ELSE(0) END) AS outstandingBalance180Plus" + " FROM "
					+ forms.getLosDbLink() + ".dbo.TBLOANS WHERE ddlq > 0";

			if (forms.getTransDate() != null) {
				myQuery += " AND CAST(nxtdueilduedt AS DATE) <= CAST(:transDate AS DATE)";
			}
			if (forms.getBranch() != null) {
				myQuery += " AND legbranch = :branch ";
			}
			if (forms.getOfficer() != null) {
				myQuery += " AND loanoff = :officer ";
			}
			myQuery += " GROUP BY fullname, pnno, prodcode, acctsts, legbranch ";
			System.out.print("MAR " + myQuery);
			list = (List<LMSAnalyticalReportForm>) dbService.execSQLQueryTransformer(myQuery, param,
					LMSAnalyticalReportForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	// 09-22-2021
	// Exception
	@SuppressWarnings("unchecked")
	@Override
	public List<LMSExceptionReportForm> getListofWaivedInterestsPenaltiesOtherCharges(LMSParametersForm forms) {
		List<LMSExceptionReportForm> list = new ArrayList<LMSExceptionReportForm>();
		try {
			param.put("branch", forms.getBranch());
			param.put("officer", forms.getOfficer());

			param.put("endDate", forms.getEndDate());
			param.put("startDate", forms.getStartDate());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());

			String myQuery = "SELECT (SELECT branchname FROM " + forms.getLosDbLink()
					+ ".dbo.TBBRANCH WHERE branchcode = c.branchcode) AS branch,"
					+ " a.txdate AS transDate,a.txvaldt AS transactionValueDate," + " a.txrefno AS transactionRefNo,"
					+ " a.accountno AS loanAcctNo, a.pnno AS pnNo," + " a.clientname AS acctName,"
					+ " (SELECT desc1 FROM " + forms.getLosDbLink()
					+ ".dbo.TBCODETABLE where codename='TXCODE' AND codevalue = a.txcode) AS transType,"
					+ " a.txamount AS amountWaived," + " (SELECT productname FROM " + forms.getLosDbLink()
					+ ".dbo.TBLOANPRODUCT WHERE productcode = b.txprod) AS loanProduct," + " a.createdby AS postedBy,"
					+ " a.approvedby AS approvedBy," + " a.particulars" + " FROM " + forms.getLosDbLink()
					+ ".dbo.TBLOANFIN a" + " LEFT JOIN " + forms.getLosDbLink()
					+ ".dbo.TBLNTXJRNL b ON a.txrefno = b.txrefno " + " LEFT JOIN " + forms.getLosDbLink()
					+ ".dbo.TBLSTAPP c ON a.pnno = c.pnno" + " WHERE a.reason = '2'";

			if (forms.getStartDate() != null && forms.getEndDate() != null) {
				myQuery += " AND CAST(a.txvaldt AS DATE) BETWEEN CAST(:startDate AS DATE) AND CAST(:endDate AS DATE)";
			}
			if (forms.getBranch() != null) {
				myQuery += " AND a.branchcode = :branch ";
			}
			if (forms.getOfficer() != null) {
				myQuery += " AND a.createdby = :officer ";
			}
			list = (List<LMSExceptionReportForm>) dbService.execSQLQueryTransformer(myQuery, param,
					LMSExceptionReportForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSExceptionReportForm> getListofLoanReleasesFATCARelated(LMSParametersForm forms) {
		List<LMSExceptionReportForm> list = new ArrayList<LMSExceptionReportForm>();
		try {
			param.put("dateFilter", forms.getDateFilter());
			param.put("transDate", forms.getTransDate() == null ? "" : forms.getTransDate());
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("officer", forms.getOfficer() == null ? "" : forms.getOfficer());
			list = (List<LMSExceptionReportForm>) dbService.execStoredProc(
					"EXEC sp_LMS_ListofLoanReleasesFATCARelated :dateFilter, :transDate, :from, :to, :branch, :officer",
					param, LMSExceptionReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSExceptionReportForm> getListofLoanReleasestoPEP(LMSParametersForm forms) {
		List<LMSExceptionReportForm> list = new ArrayList<LMSExceptionReportForm>();
		try {
			param.put("dateFilter", forms.getDateFilter());
			param.put("transDate", forms.getTransDate() == null ? "" : forms.getTransDate());
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("officer", forms.getOfficer() == null ? "" : forms.getOfficer());

			list = (List<LMSExceptionReportForm>) dbService.execStoredProc(
					"EXEC sp_LMS_ListofLoanReleasestoPEP :dateFilter, :transDate, :from, :to, :branch, :officer", param,
					LMSExceptionReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSExceptionReportForm> getListofLoanReleasestoDOSRI(LMSParametersForm forms) {
		List<LMSExceptionReportForm> list = new ArrayList<LMSExceptionReportForm>();
		try {
			param.put("dateFilter", forms.getDateFilter());
			param.put("transDate", forms.getTransDate() == null ? "" : forms.getTransDate());
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("officer", forms.getOfficer() == null ? "" : forms.getOfficer());

			list = (List<LMSExceptionReportForm>) dbService.execStoredProc(
					"EXEC sp_LMS_ListofLoanReleasestoDOSRI :dateFilter, :transDate, :from, :to, :branch, :officer",
					param, LMSExceptionReportForm.class, 1, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	// Balance Schedules
	@SuppressWarnings("unchecked")
	@Override
	public List<LMSBalancesReportForm> getScheduleofLoanAccountsfromOldStatustoCurrentStatus(LMSParametersForm forms) {
		List<LMSBalancesReportForm> list = new ArrayList<LMSBalancesReportForm>();
		try {
			param.put("dateFilter", forms.getDateFilter());
			param.put("transDate", forms.getTransDate() == null ? "" : forms.getTransDate());
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("officer", forms.getOfficer() == null ? "" : forms.getOfficer());
			param.put("fromAccountStat", forms.getFromAccountStat() == null ? "" : forms.getFromAccountStat());
			param.put("toAccountStat", forms.getToAccountStat() == null ? "" : forms.getToAccountStat());

			list = (List<LMSBalancesReportForm>) dbService.execStoredProc(
					"EXEC sp_LMS_ScheduleofLoanAccountsfromOldStatustoCurrentStatus :dateFilter, :transDate, :from, :to, :branch, :officer, :fromAccountStat, :toAccountStat",
					param, LMSBalancesReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSBalancesReportForm> getListofLoanAccountswithExcessPaymentBalance(LMSParametersForm forms) {
		List<LMSBalancesReportForm> list = new ArrayList<LMSBalancesReportForm>();
		try {
			param.put("dateFilter", forms.getDateFilter());
			param.put("asOf", forms.getTransDate());
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("officer", forms.getOfficer() == null ? "" : forms.getOfficer());

			list = (List<LMSBalancesReportForm>) dbService.execStoredProc(
					"EXEC sp_LMS_ListofLoanAccountswithExcessPaymentBalance :dateFilter, :asOf, :from, :to, :branch, :officer",
					param, LMSBalancesReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSBalancesReportForm> getScheduleofLoanAccountswithAccountsReceivableBalance(LMSParametersForm forms) {
		List<LMSBalancesReportForm> list = new ArrayList<LMSBalancesReportForm>();
		try {
			param.put("dateFilter", forms.getDateFilter());
			param.put("asOf", forms.getTransDate());
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("officer", forms.getOfficer() == null ? "" : forms.getOfficer());

			list = (List<LMSBalancesReportForm>) dbService.execStoredProc(
					"EXEC sp_LMS_ScheduleofLoanAccountswithAccountsReceivableBalance :dateFilter, :asOf, :from, :to, :branch, :officer",
					param, LMSBalancesReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSBalancesReportForm> getScheduleofLoanAccountswithOutstandingLPC(LMSParametersForm forms) {
		List<LMSBalancesReportForm> list = new ArrayList<LMSBalancesReportForm>();
		try {
			param.put("dateFilter", forms.getDateFilter());
			param.put("asOf", forms.getTransDate());
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("officer", forms.getOfficer() == null ? "" : forms.getOfficer());

			list = (List<LMSBalancesReportForm>) dbService.execStoredProc(
					"EXEC sp_LMS_ScheduleofLoanAccountswithOutstandingLPC :dateFilter, :asOf, :from, :to, :branch, :officer",
					param, LMSBalancesReportForm.class, 1, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSBalancesReportForm> getScheduleofLoanReleasesperBranch(LMSParametersForm forms) {
		List<LMSBalancesReportForm> list = new ArrayList<LMSBalancesReportForm>();
		try {
			param.put("branch", forms.getBranch());
			param.put("officer", forms.getOfficer());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			param.put("startDate", forms.getStartDate());
			param.put("endDate", forms.getEndDate());

			String myQuery = "SELECT a.productname, " + " (SELECT branchname FROM " + forms.getLosDbLink()
					+ ".dbo.TBBRANCH where branchcode = b.legbranch) AS branch,"
					+ " (SUM(CASE WHEN b.prodcode = a.productcode AND c.branchcode = b.legbranch THEN 1 ELSE 0 END)) AS totalCountPerProdHeadOffice, "
					+ " (SUM(CASE WHEN b.prodcode = a.productcode  AND c.branchcode = b.legbranch THEN b.faceamt ELSE 0 END)) AS totalAmountPerProdHeadOffice "
					+ " FROM " + forms.getLosDbLink() + ".dbo.TBLOANPRODUCT a " + " RIGHT JOIN " + forms.getLosDbLink()
					+ ".dbo.TBLOANS b ON b.prodcode = a.productcode" + " LEFT JOIN " + forms.getLosDbLink()
					+ ".dbo.TBBRANCH c ON c.branchcode = b.legbranch";

			if (forms.getStartDate() != null) {
				myQuery += "  WHERE CAST(b.dtbook AS DATE) BETWEEN CAST (:startDate AS DATE) AND CAST (:endDate AS DATE)";
			}
			if (forms.getBranch() != null) {
				myQuery += " AND b.legbranch = :branch ";
			}
			if (forms.getOfficer() != null) {
				myQuery += " AND b.loanoff = :officer ";
			}
			myQuery += " GROUP BY a.productname, b.legbranch ORDER BY a.productname ASC";
			System.out.print("MAR " + myQuery);
			list = (List<LMSBalancesReportForm>) dbService.execSQLQueryTransformer(myQuery, param,
					LMSBalancesReportForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSBalancesReportForm> getScheduleofLoanReleasesperSolicitingOfficer(LMSParametersForm forms) {
		List<LMSBalancesReportForm> list = new ArrayList<LMSBalancesReportForm>();
		try {
			param.put("branch", forms.getBranch());
			param.put("officer", forms.getOfficer());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			param.put("startDate", forms.getStartDate());
			param.put("endDate", forms.getEndDate());

			String myQuery = "SELECT a.username, " + " (SELECT branchname FROM " + forms.getLosDbLink()
					+ ".dbo.TBBRANCH where branchcode = b.legbranch) AS branch,"
					+ " (SUM(CASE WHEN b.loanoff = a.username THEN 1 ELSE 0 END)) as totalCountPerSoliciting, "
					+ " (SUM(CASE WHEN b.loanoff = a.username  THEN b.faceamt ELSE 0 END)) as totalAmountPerSoliciting "
					+ " FROM " + forms.getLosDbLink() + ".dbo.TBUSER a " + " RIGHT JOIN " + forms.getLosDbLink()
					+ ".dbo.TBLOANS b ON b.loanoff = a.username" + " WHERE a.username IN (SELECT username FROM "
					+ forms.getLosDbLink() + ".dbo.TBUSERROLES WHERE rolename LIKE '%lms%' OR rolename LIKE '%loan%') ";

			if (forms.getStartDate() != null) {
				myQuery += "  AND CAST(b.dtbook AS DATE) BETWEEN CAST (:startDate AS DATE) AND CAST (:endDate AS DATE)";
			}
			if (forms.getBranch() != null) {
				myQuery += " AND b.legbranch = :branch ";
			}
			if (forms.getOfficer() != null) {
				myQuery += " AND b.loanoff = :officer ";
			}
			myQuery += " GROUP BY a.username, b.legbranch ORDER BY a.username ASC";
			System.out.print("MAR " + myQuery);
			list = (List<LMSBalancesReportForm>) dbService.execSQLQueryTransformer(myQuery, param,
					LMSBalancesReportForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSDataForm> getScheduleofHeldSecurities(LMSParametersForm forms) {
		List<LMSDataForm> list = new ArrayList<LMSDataForm>();
		try {

			param.put("dateFilter", forms.getDateFilter());
			param.put("transDate", forms.getTransDate());
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("officer", forms.getOfficer() == null ? "" : forms.getOfficer());
			param.put("loanProduct", forms.getLoanProduct() == null ? "" : forms.getLoanProduct());

			list = (List<LMSDataForm>) dbService.execStoredProc(
					"EXEC sp_LMS_ScheduleofHeldSecurities :dateFilter, :asOf, :from, :to, :branch, :officer, :loanProduct",
					param, LMSDataForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSWorkflowProcessReportForm> getLoanApplicationStatus(int workflowid) {
		List<LMSWorkflowProcessReportForm> form = new ArrayList<LMSWorkflowProcessReportForm>();
		DBService dbService = new DBServiceImpl();
		params.put("workflowid", workflowid);
		try {
			form = (List<LMSWorkflowProcessReportForm>) dbService.execSQLQueryTransformer(
					"SELECT processid, workflowid, processname, sequenceno FROM TBWORKFLOWPROCESS WHERE workflowid =:workflowid",
					params, LMSWorkflowProcessReportForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSComprehensiveListForm> getLMSComprehensive(LMSParametersForm forms) {
		List<LMSComprehensiveListForm> list = new ArrayList<LMSComprehensiveListForm>();
		try {
			param.put("asof", forms.getAsOf() == null ? "" : forms.getAsOf());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("prodtype", forms.getLoanProduct() == null ? "" : forms.getLoanProduct());

			list = (List<LMSComprehensiveListForm>) dbService.execStoredProc(
					"EXEC sp_LMS_ComprehensiveList :asof, :branch, :prodtype", param, LMSComprehensiveListForm.class, 1,
					null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSCollectorForm> getCollectionDueAndOverdue(String branchcode, String areacode, String subareacode,
			String collector, Date asof, Integer page, Integer maxresult) {
		List<LMSCollectorForm> list = new ArrayList<LMSCollectorForm>();
		Map<String, Object> params = HQLUtil.getMap();
		try {

			if (branchcode != null && asof != null) {
				/*
				 * params.put("branchcode", branchcode); params.put("areacode", areacode);
				 * params.put("subareacode", subareacode); params.put("collector", collector);
				 */

				params.put("branchcode", branchcode == null ? "%" : "%" + branchcode + "%");
				params.put("areacode", areacode == null ? "%" : "%" + areacode + "%");
				params.put("subareacode", subareacode == null ? "%" : "%" + subareacode + "%");
				params.put("collector", collector == null ? "%" : "%" + collector + "%");

				params.put("asof", asof);
				params.put("page", page);
				params.put("maxresult", maxresult);

				/*
				 * System.out.println(">>>>>>>>>>>>>> getCollectionDueAndOverdue");
				 * System.out.println(">>>>>>>>>>>>>> branchcode - " + branchcode);
				 * System.out.println(">>>>>>>>>>>>>> areacode - " + areacode);
				 * System.out.println(">>>>>>>>>>>>>> subareacode - " + subareacode);
				 * System.out.println(">>>>>>>>>>>>>> collector - " + collector);
				 * System.out.println(">>>>>>>>>>>>>> asof - " + asof);
				 * System.out.println(">>>>>>>>>>>>>> page - " + page);
				 * System.out.println(">>>>>>>>>>>>>> maxresult - " + maxresult);
				 */

				String q = "EXEC sp_LMS_collectiondueandoverdue @branchcode=:branchcode, @areacode=:areacode, @subareacode=:subareacode, @collector=:collector, @asof=:asof, @ispagingon='true', @page=:page, @maxresult=:maxresult";
				list = (List<LMSCollectorForm>) dbService.execSQLQueryTransformer(q, params, LMSCollectorForm.class, 1);
			} else {
				System.out.println(">>> branch || asof || page || maxresult == NULL <<<");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// unused method
	@Override
	public int getCollectionDueAndOverdueCount(String branchcode, String areacode, String subareacode, String collector,
			Date asof) {
		Integer count = 0;
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("branchcode", branchcode);
			/*
			 * params.put("areacode", areacode); params.put("subareacode", subareacode);
			 * params.put("collector", collector);
			 */

			params.put("branchcode", branchcode == null ? "%" : "%" + branchcode + "%");
			params.put("areacode", areacode == null ? "%" : "%" + areacode + "%");
			params.put("subareacode", subareacode == null ? "%" : "%" + subareacode + "%");
			params.put("collector", collector == null ? "%" : "%" + collector + "%");

			params.put("asof", asof);
			String q = "EXEC sp_LMS_collectiondueandoverdue @branchcode=:branchcode, @areacode=:areacode, @subareacode=:subareacode, @collector=:collector, @asof=:asof, @ispagingon='false', @page=NULL, @maxresult=NULL";
			count = (Integer) dbService.execSQLQueryTransformer(q, params, null, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSAgingReportForm> getAgingByPAR_Detailed(LMSParametersForm forms) {
		List<LMSAgingReportForm> list = new ArrayList<LMSAgingReportForm>();
		try {
			param.put("agingType", forms.getAgingType() == null ? "" : forms.getAgingType());
			param.put("asof", forms.getAsOf() == null ? "" : forms.getAsOf());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			list = (List<LMSAgingReportForm>) dbService.execStoredProc(
					"EXEC sp_LMS_AgingByPAR :agingType, :asof, :branch", param, LMSAgingReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSAgingReportForm> getAgingByPAR_Summary(LMSParametersForm forms) {
		List<LMSAgingReportForm> list = new ArrayList<LMSAgingReportForm>();
		try {
			param.put("agingType", forms.getAgingType() == null ? "" : forms.getAgingType());
			param.put("asof", forms.getAsOf() == null ? "" : forms.getAsOf());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			list = (List<LMSAgingReportForm>) dbService.execStoredProc(
					"EXEC sp_LMS_AgingByPAR :agingType, :asof, :branch", param, LMSAgingReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSAgingReportForm> getAgingByPAR_Summary_Product(LMSParametersForm forms) {
		List<LMSAgingReportForm> list = new ArrayList<LMSAgingReportForm>();
		try {
			param.put("agingType", forms.getAgingType() == null ? "" : forms.getAgingType());
			param.put("asof", forms.getAsOf() == null ? "" : forms.getAsOf());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			list = (List<LMSAgingReportForm>) dbService.execStoredProc(
					"EXEC sp_LMS_AgingByPAR :agingType, :asof, :branch", param, LMSAgingReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSAgingReportForm> getAgingByAmort_Detailed(LMSParametersForm forms) {
		List<LMSAgingReportForm> list = new ArrayList<LMSAgingReportForm>();
		try {
			param.put("agingType", forms.getAgingType() == null ? "" : forms.getAgingType());
			param.put("asof", forms.getAsOf() == null ? "" : forms.getAsOf());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			list = (List<LMSAgingReportForm>) dbService.execStoredProc(
					"EXEC sp_LMS_AgingByAmort :agingType, :asof, :branch", param, LMSAgingReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSAgingReportForm> getAgingByAmort_Summary(LMSParametersForm forms) {
		List<LMSAgingReportForm> list = new ArrayList<LMSAgingReportForm>();
		try {
			param.put("agingType", forms.getAgingType() == null ? "" : forms.getAgingType());
			param.put("asof", forms.getAsOf() == null ? "" : forms.getAsOf());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			list = (List<LMSAgingReportForm>) dbService.execStoredProc(
					"EXEC sp_LMS_AgingByAmort :agingType, :asof, :branch", param, LMSAgingReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSAgingReportForm> getAgingByAmort_Summary_Product(LMSParametersForm forms) {
		List<LMSAgingReportForm> list = new ArrayList<LMSAgingReportForm>();
		try {
			param.put("agingType", forms.getAgingType() == null ? "" : forms.getAgingType());
			param.put("asof", forms.getAsOf() == null ? "" : forms.getAsOf());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			list = (List<LMSAgingReportForm>) dbService.execStoredProc(
					"EXEC sp_LMS_AgingByAmort :agingType, :asof, :branch", param, LMSAgingReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSAgingReportForm> getAgingByMat_Detailed(LMSParametersForm forms) {
		List<LMSAgingReportForm> list = new ArrayList<LMSAgingReportForm>();
		try {
			param.put("agingType", forms.getAgingType() == null ? "" : forms.getAgingType());
			param.put("asof", forms.getAsOf() == null ? "" : forms.getAsOf());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			list = (List<LMSAgingReportForm>) dbService.execStoredProc(
					"EXEC sp_LMS_AgingByMat :agingType, :asof, :branch", param, LMSAgingReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSAgingReportForm> getAgingByMat_Summary(LMSParametersForm forms) {
		List<LMSAgingReportForm> list = new ArrayList<LMSAgingReportForm>();
		try {
			param.put("agingType", forms.getAgingType() == null ? "" : forms.getAgingType());
			param.put("asof", forms.getAsOf() == null ? "" : forms.getAsOf());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			list = (List<LMSAgingReportForm>) dbService.execStoredProc(
					"EXEC sp_LMS_AgingByMat :agingType, :asof, :branch", param, LMSAgingReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSAgingReportForm> getAgingByMat_Summary_Product(LMSParametersForm forms) {
		List<LMSAgingReportForm> list = new ArrayList<LMSAgingReportForm>();
		try {
			param.put("agingType", forms.getAgingType() == null ? "" : forms.getAgingType());
			param.put("asof", forms.getAsOf() == null ? "" : forms.getAsOf());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			list = (List<LMSAgingReportForm>) dbService.execStoredProc(
					"EXEC sp_LMS_AgingByMat :agingType, :asof, :branch", param, LMSAgingReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSUnearnedInterestForm> listLMSUnearnedInterest(String branch, String groupby, Date from, Date to) {
		List<LMSUnearnedInterestForm> list = new ArrayList<LMSUnearnedInterestForm>();
		Map<String, Object> param = HQLUtil.getMap();
		DBService dbsrvc = new DBServiceImpl();
		param.put("branch", branch == null ? "" : branch);
		param.put("from", from);
		param.put("to", to);
		param.put("groupby", groupby);
		try {
			list = (List<LMSUnearnedInterestForm>) dbsrvc.execStoredProc(
					"EXEC sp_LMS_UnearnedInterestReport :from, :to, :branch, :groupby", param,
					LMSUnearnedInterestForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LMSDataForm> getLoanInterestAndPenaltyComputationWorksheet(LMSParametersForm forms) {
		List<LMSDataForm> list = new ArrayList<LMSDataForm>();
		try {
			param.put("startDate", forms.getStartDate() == null ? "" : forms.getStartDate());
			param.put("endDate", forms.getEndDate() == null ? "" : forms.getEndDate());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("cifno", forms.getCifno() == null ? "" : forms.getLoanProduct());
			param.put("accountno", forms.getAccountno() == null ? "" : forms.getAccountno());
			param.put("accountname", forms.getAccountname() == null ? "" : forms.getAccountname());
			param.put("loanProduct", forms.getLoanProduct() == null ? "" : forms.getLoanProduct());
			param.put("officer", forms.getOfficer() == null ? "" : forms.getOfficer());

			list = (List<LMSDataForm>) dbService.execStoredProc(
					"EXEC sp_LMS_LoanInterestAndPenaltyComputationWorksheet :startDate, :endDate, :branch, :cifno, :accountno, :accountname, :loanProduct, :officer",
					param, LMSDataForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}
}
