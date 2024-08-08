/**
 * 
 */
package com.etel.casaeod;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.casa.FinTxService;
import com.casa.FinTxServiceImpl;
import com.casa.acct.AccountService;
import com.casa.acct.AccountServiceImpl;
import com.casa.util.UtilService;
import com.casa.util.UtilServiceImpl;
import com.casa.util.forms.BranchInfoForm;
import com.coopdb.data.Tbbranch;
import com.coopdb.data.Tbdeposit;
import com.coopdb.data.Tbdepositcif;
import com.coopdb.data.Tbfintxjrnl;
import com.coopdb.data.Tbholiday;
import com.coopdb.data.Tblogs;
import com.coopdb.data.Tbprocessingdate;
import com.coopdb.data.Tbtimedeposit;
import com.etel.casaeod.form.EODModulesForm;
import com.etel.casaeod.form.LogsAndModulesForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.lmseod.LMSEODService;
import com.etel.lmseod.LMSEODServiceImpl;
import com.etel.reports.ReportsFacadeImpl;
import com.etel.reports.ReportsFacadeService;
import com.etel.util.ConfigPropertyUtil;
import com.etel.util.HQLUtil;
import com.etel.utils.DateTimeUtil;
import com.etel.utils.UserUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

/**
 * @author ETEL-LAPTOP19
 *
 */
public class CASAEODServiceImpl implements CASAEODService {

	private static DBService dbService = new DBServiceImpl();
	Map<String, Object> param = HQLUtil.getMap();
	SecurityService service = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private String wsurl = ConfigPropertyUtil.getPropertyValue("ws_url");
//	private static Tblogs log = new Tblogs();
//	private static Tbprocessingdate prcdate = (Tbprocessingdate) dbService
//			.executeUniqueHQLQuery("FROM Tbprocessingdate WHERE id = 1 ", null);
	private static FinTxService fintxSrvc = new FinTxServiceImpl();

	@Override
	public Tbbranch getMainBranch() {
		Tbbranch unit = new Tbbranch();
		try {
			param.put("coopcode", UserUtil.getUserByUsername(service.getUserName()).getCoopcode());
			unit = (Tbbranch) dbService.execStoredProc(
					"SELECT * FROM Tbbranch WHERE branchclassification ='0' and coopcode =:coopcode", param,
					Tbbranch.class, 0, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return unit;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LogsAndModulesForm findAllLogsFortheDay(Date currentbusinessdate) {
		LogsAndModulesForm logsAndModuleForm = new LogsAndModulesForm();
		List<Tblogs> logList = new ArrayList<Tblogs>();
		EODModulesForm eodForm = new EODModulesForm();
		param.put("currentbusinessdate",
				DateTimeUtil.convertDateToString(currentbusinessdate, DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
//		boolean check = Connection.connectionCheck();
		try {
//			if (check) {
//				String strUrl = wsurl + "/util/eodlogs/" + param.get("currentbusinessdate");
//				System.out.println("ez " + strUrl);
//				URL url = new URL(strUrl);
//				HttpURLConnection con = (HttpURLConnection) url.openConnection();
//				con.setDoOutput(true);
//				con.setRequestMethod("GET");
//				System.out.println("Response Code : " + con.getResponseCode());
//				if (con.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED || con.getResponseCode() == 200) {
//					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//					ObjectMapper mapper = new ObjectMapper();
//					logList = Arrays.asList(mapper.readValue(in.readLine(), Tblogs[].class));
//					System.out.println(logList.size() + " logs size");
//				}
//				con.disconnect();
			eodForm = (EODModulesForm) dbService.execStoredProc("select "
					+ "(select count(*) from TBLOGS where currentdate =:currentbusinessdate and modulename = 'PCHC CLEARING' "
					+ "and eventname = 'END') as pchcClearing , "
					+ "(select count(*) from TBLOGS where currentdate =:currentbusinessdate and modulename = 'ROLL DOWN' "
					+ "and eventname = 'END') as rolldown , "
					+ "(select count(*) from TBLOGS where currentdate =:currentbusinessdate and modulename = 'BULK TRANSACTION' "
					+ "and eventname = 'END') as batchProcessing , "
					+ "(select count(*)  from TBLOGS where currentdate =:currentbusinessdate and modulename = 'CASAEOD' "
					+ "and eventname = 'END') as casaeod , "
					+ "(select count(*)  from TBLOGS where currentdate =:currentbusinessdate and modulename = 'LOAN EOD' "
					+ "and eventname = 'END') as loaneod , "
					+ "(select count(*)  from TBLOGS where currentdate =:currentbusinessdate and modulename IN ('CASAEOD','LOAN EOD') "
					+ "and eventname = 'END') as eod", param, EODModulesForm.class, 0, null);
			logList = (List<Tblogs>) dbService.execStoredProc(
					"select * from TBLOGS where currentdate =:currentbusinessdate ", param, Tblogs.class, 1, null);
			logsAndModuleForm.setLogList(logList);
			logsAndModuleForm.setEodForm(eodForm);
//			} else {
//				System.out.println("Cannot connect to host.");
//			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return logsAndModuleForm;
	}

	@Override
	public int runEOD(int module, String branchcodes) {
		int result = 0;
		try {
//			timeDepositMaturity("");
			if (module == 0) {
				param.put("branchcodes", branchcodes);
//				System.out.println(param);
				result = (Integer) dbService.execStoredProc(
						"declare @res int exec rolldown :branchcodes,@res output select @res", param, null, 0, null);
			} else if (module == 1) {
				result = (Integer) dbService.execStoredProc(
						"declare @res int exec pchc_clearing @res output select @res", param, null, 0, null);
			} else if (module == 2) {
				result = (Integer) dbService.execStoredProc(
						"declare @res int exec batch_posting @res output select @res", param, null, 0, null);
			} else if (module == 3) {
				param.put("coopcode", UserUtil.getUserByUsername(service.getUserName()).getCoopcode());
				checkClearing();
				result = (Integer) dbService.execStoredProc(
						"declare @res int exec casa_eod :coopcode,@res output select @res", param, null, 0, null);
			} else if (module == 4) {
				LMSEODService eodSrvc = new LMSEODServiceImpl();
				eodSrvc.runLMSEOD_new();
			} else if (module == 5) {
				param.put("coopcode", UserUtil.getUserByUsername(service.getUserName()).getCoopcode());
				checkClearing();
				result = (Integer) dbService.execStoredProc(
						"declare @res int exec casa_eod :coopcode,@res output select @res", param, null, 0, null);
				LMSEODService eodSrvc = new LMSEODServiceImpl();
				eodSrvc.runLMSEOD_new();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Tbprocessingdate getProcDate() {
		Tbprocessingdate procdate = new Tbprocessingdate();
		try {
			procdate = (Tbprocessingdate) dbService.executeUniqueHQLQuery("FROM Tbprocessingdate WHERE id = 1", param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return procdate;
	}

	@Override
	public String saveProcDate(Tbprocessingdate procDate) {
		param.put("dt", DateTimeUtil.convertDateToString(procDate.getEnddate(), DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
		try {
			if (dbService.executeUpdate("UPDATE Tbprocessingdate SET startdate = enddate,enddate =:dt WHERE id = 1",
					param) > 0)
				return "Success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "Failed";
		}
		return "Failed";
	}

	@Override
	public String checkClearing() {
		// TODO Auto-generated method stub
		String flag = "Failed";
		try {
//			flag = (String) 
			Clob clob = (Clob) dbService.execStoredProc("DECLARE @result varchar(MAX) EXEC sp_checkClearing "
					+ "@result OUTPUT SELECT CAST(@result AS VARCHAR(MAX))", param, null, 0, null);
			flag = clob.getSubString(1, (int) clob.length());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return flag;
	}

	// DISPO - TD01 = Rollover, TD02 = Renew, TD03 = None
	// INTPRINDISPO - 1 = Cash, 2 = Check, 3 = GL Suspended Account, 4 = Credit to
	// Account, 5 = None
	@Override
	public String timeDepositMaturity(String accountno) {
		String flag = "failed";
		List<Tbtimedeposit> timeDeposit = new ArrayList<Tbtimedeposit>();
		AccountService acctSrvc = new AccountServiceImpl();
		param.put("coopcode", UserUtil.getUserByUsername(service.getUserName()).getCoopcode());
		String q = "SELECT b.* " + "FROM TBTIMEDEPOSIT b left join TBDEPOSIT c on b.accountno = c.AccountNo "
				+ "left join TBBRANCH a on CAST(a.currentbusinessdate as date)= CAST(b.dtmat AS DATE) "
				+ "where a.coopcode =:coopcode and branchname= 'HEAD OFFICE' and c.AccountStatus <> 5";
		try {
			if (accountno != null && accountno.trim() != "") {
				param.put("acctno", accountno);
				q = "SELECT b.* FROM TBTIMEDEPOSIT b " + "left join TBDEPOSIT c on b.accountno = c.AccountNo "
						+ "where c.AccountStatus = 4 and b.accountno =:acctno";
			} else {
//				System.out.println(dbService.execStoredProc("UPDATE b SET b.accountstatus = '4' "
//						+ "FROM TBDEPOSIT b left join TBBRANCH a on CAST(b.MaturityDate as date) > CAST(a.currentbusinessdate as date) "
//						+ "and CAST(b.MaturityDate AS DATE) <= CAST(a.nextbusinessdate as date) "
//						+ "where a.coopcode =:coopcode and branchname= 'HEAD OFFICE' and productcode ='40'", param,
//						null, 2, null) + " Matured Accounts");
			}
			timeDeposit = (List<Tbtimedeposit>) dbService.execStoredProc(q, param, Tbtimedeposit.class, 1, null);
			if (timeDeposit != null && !timeDeposit.isEmpty()) {
				System.out.println(timeDeposit.size() + " : SIZE ");
				for (Tbtimedeposit row : timeDeposit) {
					param.put("acctno", row.getAccountno());
					if (row.getDispositiontype().equals("TD02") || row.getDispositiontype().equals("TD03")) {
						if (row.getDispositiontype().equals("TD02")) {
							System.out.println(renewTimeDeposit(row.getAccountno()) + " renew");
						}
						if (row.getIntdispo().equals("4")) {
							Tbfintxjrnl form = new Tbfintxjrnl();
							form.setTxcode("112013");
							form.setTxvaldt(row.getDtmat());
							form.setTxdate(new Date());
							form.setAccountno(row.getIntdispoacttno());
							form.setTxamount(row.getGrossint().subtract(row.getLesswtaxamt()));
							form.setReasoncode("101");
							form.setCurrency("PHP");
							form.setRemarks("Time Deposit Interest Crediting");
							form.setTxby("SYS");
							form.setAcctname(row.getAccountname());
							form.setTxbatch("CASA");
							form.setTxoper(2);
							form.setTxmode("9");
							if (!fintxSrvc.cashDepWithDrCrMemo(form, null).getResult().equals("1")) {
								System.out.println(form.getAccountno() + " Crediting unsuccessfull.");
							}
						}
						if (row.getDispositiontype().equals("TD03")) {
							if (row.getPrindispo().equals("4")) {
								Tbfintxjrnl form = new Tbfintxjrnl();
								form.setTxcode("112013");
								form.setTxvaldt(row.getDtmat());
								form.setTxdate(new Date());
								form.setAccountno(row.getPrindispoacctno());
								form.setTxamount(row.getPlaceamt());
								form.setReasoncode("101");
								form.setCurrency("PHP");
								form.setRemarks("Time Deposit Principal Crediting");
								form.setTxby("SYS");
								form.setAcctname(row.getAccountname());
								form.setTxbatch("CASA");
								form.setTxoper(2);
								form.setTxmode("9");
								if (fintxSrvc.cashDepWithDrCrMemo(form, null).getResult().equals("1")) {
									dbService.executeUpdate(
											"UPDATE TBDEPOSIT SET AccountStatus ='4' WHERE accountno =:acctno", param);
								}
							} else if (row.getPrindispo().equals("5")) {
								Tbdeposit dep = (Tbdeposit) dbService
										.executeUniqueHQLQuery("FROM Tbdeposit WHERE accountno=:acctno", param);
								dep.setAccountBalance(row.getPlaceamt());
								if (row.getIntdispo().equals("5")) {
									dep.setAccountBalance(row.getMatvalue());
								}
								dep.setAccountStatus(4);
								dep.setIntRate(acctSrvc.getProductDetail("20", "11").getIntrate());
								dbService.saveOrUpdate(dep);
							}
						}
					} else { // Auto Roll
						renewTimeDeposit(row.getAccountno());

					}
				}
				flag = "success";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> listOfOpenBranch() {
		List<String> openBranches = new ArrayList<String>();
		param.put("coopcode", UserUtil.getUserByUsername(service.getUserName()).getCoopcode());
		try {
			openBranches = (List<String>) dbService
					.executeListSQLQuery("select branchname from TBBRANCH where branchstatus=1 and coopcode =:coopcode"
							+ " and branchclassification != '0'", param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return openBranches;
	}

	public int renewTimeDeposit(String accountno) {
		AccountService acctSrvc = new AccountServiceImpl();
		Calendar cal = Calendar.getInstance();
		Tbdeposit dep = new Tbdeposit();
		Tbtimedeposit timedep = new Tbtimedeposit();
		List<Tbdepositcif> cifList = new ArrayList<Tbdepositcif>();
		List<Tbholiday> holidays = new ArrayList<Tbholiday>();
		int result = 0;
		try {
			holidays = (List<Tbholiday>) dbService.executeListHQLQuery("FROM Tbholiday", null);
			dep = (Tbdeposit) dbService.executeUniqueHQLQuery("FROM Tbdeposit WHERE accountno=:acctno", param);
			timedep = (Tbtimedeposit) dbService.execStoredProc("SELECT * FROM Tbtimedeposit WHERE accountno=:acctno",
					param, Tbtimedeposit.class, 0, null);
			cifList = (List<Tbdepositcif>) dbService.executeListHQLQuery("FROM Tbdepositcif WHERE accountno =:acctno",
					param);
			dep.setId(null);
			timedep.setId(null);
			dep.setPlacementAmt(timedep.getIntdispo() == null || !timedep.getIntdispo().equals("4") ? dep.getMatAmt()
					: dep.getPlacementAmt());
			dep.setTdcreleaseind(0);
			dep.setAccountStatus(1);
			dep.setBookDate(dep.getMaturityDate());
			dep.setLasttxdate(null);
//			dep.setStatusDate(dep.getBookDate());
			cal.setTime(DateTimeUtil.dtRunDateEndDateD(dep.getMaturityDate()));
			cal.add(Calendar.DAY_OF_MONTH, dep.getTerm());
			dep.setMaturityDate(cal.getTime());
			boolean isTermValid = false;
			while (!isTermValid) {
				boolean isHoliday = false;
				for (Tbholiday hol : holidays) {
					if (cal.getTime().compareTo(DateTimeUtil.dtRunDateEndDateD(hol.getHolDate())) == 0) {
						isHoliday = true;
					}
				}
				if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
						|| cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || isHoliday) {
					cal.add(Calendar.DAY_OF_MONTH, 1);
					dep.setTerm(dep.getTerm() + 1);
					dep.setMaturityDate(cal.getTime());
				} else {
					isTermValid = true;
				}
			}
			timedep.setGrossint(BigDecimal
					.valueOf(dep.getPlacementAmt().doubleValue() * dep.getIntRate().doubleValue() * dep.getTerm() / 360)
					.setScale(2, RoundingMode.HALF_EVEN));
			dep.setLessWtaxAmt(BigDecimal.valueOf(timedep.getGrossint().doubleValue() * dep.getWtaxrate().doubleValue())
					.setScale(2, RoundingMode.HALF_EVEN));
			timedep.setLessdocstamp(
					BigDecimal.valueOf(((dep.getPlacementAmt().doubleValue() / 200) * 1.50) * dep.getTerm() / 360)
							.setScale(2, RoundingMode.HALF_EVEN));
			dep.setMatAmt(BigDecimal.valueOf((dep.getPlacementAmt().doubleValue() + timedep.getGrossint().doubleValue())
					- dep.getLessWtaxAmt().doubleValue()).setScale(2, RoundingMode.HALF_EVEN));
			timedep.setPlaceamt(dep.getPlacementAmt());
			timedep.setTerm(dep.getTerm());
			timedep.setNetint(timedep.getGrossint().subtract(timedep.getLesswtaxamt()));
			timedep.setDtbook(dep.getBookDate());
			timedep.setDtmat(dep.getMaturityDate());
			timedep.setLesswtaxamt(dep.getLessWtaxAmt());
			timedep.setMatvalue(dep.getMatAmt());
			dep.setAccountBalance(timedep.getPlaceamt());
			timedep.setAccountno(acctSrvc.createAccount(dep, cifList).getValue());
			timedep.setOldacctno(accountno);
			dbService.save(timedep);
			dbService.executeUpdate("UPDATE TBDEPOSIT SET AccountStatus ='5',AccountBalance=0 WHERE accountno =:acctno",
					param);
			if (timedep.getDispositiontype().equals("TD01")) {
				Tbfintxjrnl form = new Tbfintxjrnl();
				form.setTxcode("112013");
				form.setTxvaldt(timedep.getDtmat());
				form.setTxdate(new Date());
				form.setAccountno(timedep.getAccountno());
				form.setTxamount(timedep.getPlaceamt());
				form.setReasoncode("101");
				form.setCurrency("PHP");
				form.setRemarks("Time Deposit Roll Over Crediting");
				form.setTxby("SYS");
				form.setAcctname(dep.getAccountName());
				form.setTxbatch("CASA");
				form.setTxoper(2);
				form.setTxmode("9");
				if (!fintxSrvc.cashDepWithDrCrMemo(form, null).getResult().equals("1")) {
					System.out.println(form.getAccountno() + " Crediting unsuccessfull.");
				}
			}
			result = 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void runEODReports() {
		ReportsFacadeService rptSrvc = new ReportsFacadeImpl();
		String reportDate = new SimpleDateFormat("MMddyyyy").format(new Date());
		param.put("curdate", new Date());
		String outputFilePath = RuntimeAccess.getInstance().getSession().getServletContext()
				.getRealPath("/resources/docdir/" + reportDate + "/");
		File path = new File(outputFilePath);
		DBService dbsrvc = new DBServiceImpl();
		try {
			if (!path.exists()) {
				path.mkdir();
			}
			UtilService utilsrvc = new UtilServiceImpl();
			BranchInfoForm b = utilsrvc.getBrInfo();
			Tblogs log = new Tblogs();
			log.setCurrentdate(b.getBusinessdt());
			log.setNextdate(b.getNxtbusinessdt());
			log.setDescription("Start generating EOD Reports");
			log.setEventdate(new Date());
			log.setEventname("START");
			log.setModulename("EOD REPORTS");
			dbsrvc.save(log);

			// SAVINGS REPORTS
			rptSrvc.executeJasperXLSXwithOutputFileName("DailyListofAccountsBelowMinimumBalanceXLS", param,
					outputFilePath, "DailyListofAccountsBelowMinimumBalance" + reportDate);
			rptSrvc.executeJasperXLSXwithOutputFileName("DailyListofActivatedDormantAccountsXLS", param, outputFilePath,
					"DailyListofActivatedDormantAccounts" + reportDate);
			rptSrvc.executeJasperXLSXwithOutputFileName("DailyListofClosedAccountsXLS", param, outputFilePath,
					"DailyListofClosedAccounts" + reportDate);
			rptSrvc.executeJasperXLSXwithOutputFileName("DailyListofDormantAccounts5YearsXLS", param, outputFilePath,
					"DailyListofDormantAccounts5Years" + reportDate);
			rptSrvc.executeJasperXLSXwithOutputFileName("DailyListofDormantAccounts10YearsXLS", param, outputFilePath,
					"DailyListofDormantAccounts10Years" + reportDate);
			rptSrvc.executeJasperXLSXwithOutputFileName(
					"DailyListofDormantAccountsfallingBelowMinimumBalance2MonthsXLS", param, outputFilePath,
					"DailyListofDormantAccountsfallingBelowMinimumBalance2Months" + reportDate);
			rptSrvc.executeJasperXLSXwithOutputFileName("DailyListofDormantAccountsXLS", param, outputFilePath,
					"DailyListofDormantAccounts" + reportDate);
			rptSrvc.executeJasperXLSXwithOutputFileName("DailyListofNewAccountsXLS", param, outputFilePath,
					"DailyListofNewAccounts" + reportDate);
			rptSrvc.executeJasperXLSXwithOutputFileName("SACASAFullBalanceListing", param, outputFilePath,
					"MasterListSA" + reportDate);
			rptSrvc.executeJasperXLSXwithOutputFileName("Savings and Premium Savings Deposit", param, outputFilePath,
					"SavingsandPremiumSavingsDeposit" + reportDate);
			rptSrvc.executeJasperXLSXwithOutputFileName("SASummaryofDailyTransactionReport", param, outputFilePath,
					"SASummaryofDailyTransactionReport" + reportDate);

			// TIME DEPOSIT REPORTS
			rptSrvc.executeJasperXLSXwithOutputFileName("DailyListofAccuredInterestPayableXLS", param, outputFilePath,
					"DailyListofAccuredInterestPayable" + reportDate);
			rptSrvc.executeJasperXLSXwithOutputFileName("DailyListofMaturedbutUnwithdrawnAccountsXLS", param,
					outputFilePath, "DailyListofMaturedbutUnwithdrawnAccounts" + reportDate);
			rptSrvc.executeJasperXLSXwithOutputFileName("DailyListofNewPlacements", param, outputFilePath,
					"DailyListofNewPlacements" + reportDate);
			rptSrvc.executeJasperXLSXwithOutputFileName("TDDailyListPretermTimeAcctsReport", param, outputFilePath,
					"TDDailyListPretermTimeAcctsReport" + reportDate);
			rptSrvc.executeJasperXLSXwithOutputFileName("Daily List of Roll-Overs", param, outputFilePath,
					"DailyListofRoll-Overs" + reportDate);
			rptSrvc.executeJasperXLSXwithOutputFileName("TDDailyListPretermTimeAcctsReport", param, outputFilePath,
					"TDDailyListPretermTimeAcctsReport" + reportDate);
			rptSrvc.executeJasperXLSXwithOutputFileName("GrossDepositsandWithdrawalfortheQuarterXLS", param,
					outputFilePath, "GrossDepositsandWithdrawalfortheQuarter" + reportDate);
			rptSrvc.executeJasperXLSXwithOutputFileName("Maturity Matching (PSD and CTD Accruals)", param,
					outputFilePath, "MaturityMatching(PSDandCTDAccruals)" + reportDate);

			// Mar 06-25-2021 START
			rptSrvc.executeJasperXLSXwithOutputFileName("DailyListofNewPlacements", param, outputFilePath,
					"DailyListofNewPlacements" + reportDate);
			rptSrvc.executeJasperXLSXwithOutputFileName("DailyListofRollOvers", param, outputFilePath,
					"DailyListofRollOvers" + reportDate);
			rptSrvc.executeJasperXLSXwithOutputFileName("masterListTD", param, outputFilePath,
					"MasterListTD" + reportDate);
			// Mar 06-25-2021 END

			log.setDescription("Done generating EOD Reports");
			log.setEventdate(new Date());
			log.setEventname("END");
			log.setModulename("EOD REPORTS");
			dbsrvc.save(log);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
