/**
 * 
 */
package com.casa.eod;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.cloudfoundry.org.codehaus.jackson.map.ObjectMapper;

import com.casa.FinTxService;
import com.casa.FinTxServiceImpl;
import com.casa.acct.AccountService;
import com.casa.acct.AccountServiceImpl;
import com.casa.forms.EODModulesForm;
import com.casa.forms.LogsAndModulesForm;
import com.coopdb.data.Tbaccountinfo;
import com.coopdb.data.Tbbranch;
import com.coopdb.data.Tbdeposit;
import com.coopdb.data.Tbdepositcif;
import com.coopdb.data.Tbfintxjrnl;
import com.coopdb.data.Tbholiday;
import com.coopdb.data.Tbloanfin;
import com.coopdb.data.Tblogs;
import com.coopdb.data.Tbprocessingdate;
import com.coopdb.data.Tbtimedeposit;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.util.ConfigPropertyUtil;
import com.etel.util.Connection;
import com.etel.utils.DateTimeUtil;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

/**
 * @author ETEL-COMP05
 *
 */
@SuppressWarnings("unchecked")
public class EODServiceImpl implements EODService {

	private static DBService dbService = new DBServiceImpl();
	Map<String, Object> param = HQLUtil.getMap();
	SecurityService service = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private String wsurl = ConfigPropertyUtil.getPropertyValue("ws_url");
	private static Tblogs log = new Tblogs();
//	private static Tbprocessingdate prcdate = (Tbprocessingdate) dbService.executeUniqueHQLQuery("FROM Tbprocessingdate WHERE id = 1 ", null);

	@Override
	public Tbbranch getMainBranch() {
		Tbbranch unit = new Tbbranch();
		try {
			param.put("coopcode", UserUtil.getUserByUsername(service.getUserName()).getCoopcode());
			unit = (Tbbranch) dbService.execStoredProc(
					"SELECT * FROM Tbbranch WHERE branchname ='HEAD OFFICE' and coopcode =:coopcode", param,
					Tbbranch.class, 0, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return unit;
	}

	@Override
	public LogsAndModulesForm findAllLogsFortheDay(Date currentbusinessdate) {
		LogsAndModulesForm logsAndModuleForm = new LogsAndModulesForm();
		List<Tblogs> logList = new ArrayList<Tblogs>();
		EODModulesForm eodForm = new EODModulesForm();
		param.put("currentbusinessdate",
				DateTimeUtil.convertDateToString(currentbusinessdate, DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
		boolean check = Connection.connectionCheck();
		try {
			if (check) {
				String strUrl = wsurl + "/util/eodlogs/" + param.get("currentbusinessdate");
				System.out.println("ez " + strUrl);
				URL url = new URL(strUrl);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setDoOutput(true);
				con.setRequestMethod("GET");
				System.out.println("Response Code : " + con.getResponseCode());
				if (con.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED || con.getResponseCode() == 200) {
					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					ObjectMapper mapper = new ObjectMapper();
					logList = Arrays.asList(mapper.readValue(in.readLine(), Tblogs[].class));
					System.out.println(logList.size() + " logs size");
				}
				con.disconnect();
				eodForm = (EODModulesForm) dbService.execStoredProc("select "
						+ "(select count(*) from TBLOGS where currentdate =:currentbusinessdate and modulename = 'PCHC CLEARING' "
						+ "and eventname = 'END') as pchcClearing , "
						+ "(select count(*) from TBLOGS where currentdate =:currentbusinessdate and modulename = 'ROLL DOWN' "
						+ "and eventname = 'END') as rolldown , "
						+ "(select count(*) from TBLOGS where currentdate =:currentbusinessdate and modulename = 'BULK TRANSACTION' "
						+ "and eventname = 'END') as batchProcessing , "
						+ "(select count(*)  from TBLOGS where currentdate =:currentbusinessdate and modulename = 'EOD' "
						+ "and eventname = 'END') as eod", param, EODModulesForm.class, 0, null);
				logsAndModuleForm.setLogList(logList);
				logsAndModuleForm.setEodForm(eodForm);
			} else {
				System.out.println("Cannot connect to host.");
			}
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
			timeDepositMaturity();
			if (module == 0) {
				param.put("branchcodes", branchcodes);
				System.out.println(param);
				result = (Integer) dbService.execStoredProc(
						"declare @res int exec rolldown :branchcodes,@res output " + "select @res", param, null, 0,
						null);
			} else if (module == 1) {
				result = (Integer) dbService.execStoredProc(
						"declare @res int exec pchc_clearing @res output " + "select @res", param, null, 0, null);
			} else if (module == 2) {
				result = (Integer) dbService.execStoredProc(
						"declare @res int exec batch_posting @res output " + "select @res", param, null, 0, null);
			} else if (module == 3) {
				param.put("coopcode", UserUtil.getUserByUsername(service.getUserName()).getCoopcode());
				result = (Integer) dbService.execStoredProc(
						"declare @res int exec casa_eod :coopcode,@res output " + "select @res", param, null, 0, null);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String loanBooking(List<Tbaccountinfo> newloans) {
		// TODO Auto-generated method stub
		return LoanBooking.loanBooking(newloans);
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
		EODService eodSrvc = new EODServiceImpl();
		Tbbranch prcdate = eodSrvc.getMainBranch();
		param.put("dt", DateTimeUtil.convertDateToString(prcdate.getCurrentbusinessdate(),
				DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
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

	@Override
	public void runLMSEOD() {
		// TODO Auto-generated method stub
		EODService eodSrvc = new EODServiceImpl();
		Tbbranch prcdate = eodSrvc.getMainBranch();
		try {
			System.out.println(">> EOD PROCESS : START CHECK CLEARING " + new Date() + " >>");
			log.setCurrentdate(prcdate.getCurrentbusinessdate());
			log.setNextdate(prcdate.getNextbusinessdate());
			log.setModulename("LOAN EOD");
			log.setEventdate(new Date());
			log.setEventname("START");
			log.setDescription("Loan End Of Day");
			log.setUniquekey("");
			dbService.save(log);
//			log.setCurrentdate(prcdate.getCurrentbusinessdate());
//			log.setNextdate(prcdate.getNextbusinessdate());
//			log.setModulename("CHECK CLEARING");
//			log.setEventdate(new Date());
//			log.setEventname("START");
//			log.setUniquekey("");
//			log.setDescription("Start of Check Clearing Routine");
//			dbService.save(log);
//			log.setCurrentdate(prcdate.getCurrentbusinessdate());
//			log.setNextdate(prcdate.getNextbusinessdate());
//			log.setModulename("CHECK CLEARING");
//			log.setEventdate(new Date());
//			log.setEventname("END");
//			log.setDescription(eodSrvc.checkClearing());
//			log.setUniquekey("");
//			dbService.save(log);
//			System.out.println(">> EOD PROCESS : END CHECK CLEARING " + new Date() + " >>");
//			List<Tbaccountinfo> newloans = (List<Tbaccountinfo>) dbService
//					.executeListHQLQuery("FROM Tbaccountinfo WHERE txstatus = '9'", param);
//			if (newloans != null) {
//				eodSrvc.loanBooking(newloans);
//			}
//			List<Tbloanfin> finTx = (List<Tbloanfin>) dbService
//					.executeListHQLQuery("FROM Tbloanfin WHERE txstatus = 'F'", param);
//			if (finTx != null) {
//				eodSrvc.loanPostingTest(finTx);
//			}
//			PaymentAnniversary.main();
//			log.setModulename("LOAN EOD");
//			log.setEventdate(new Date());
//			log.setEventname("END");
//			log.setDescription("Loan End Of Day");
//			log.setUniquekey("");
//			dbService.save(log);
//			prcdate.setStartdate(prcdate.getNextbusinessdate());
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(prcdate.getNextbusinessdate());
//			cal.add(Calendar.DAY_OF_MONTH, 1);
//			prcdate.setEnddate(cal.getTime());
//			dbService.saveOrUpdate(prcdate);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public String loanPostingTest(List<Tbloanfin> finList) {
		return LoanPosting.main(finList);
	}

	public String timeDepositMaturity() {
		String flag = "failed";
		List<Tbtimedeposit> timeDeposit = new ArrayList<Tbtimedeposit>();
		FinTxService fintxSrvc = new FinTxServiceImpl();
		AccountService acctSrvc = new AccountServiceImpl();
		param.put("coopcode", UserUtil.getUserByUsername(service.getUserName()).getCoopcode());
		try {
			timeDeposit = (List<Tbtimedeposit>) dbService.execStoredProc(
					"SELECT b.* " + "FROM TBBRANCH a left join TBTIMEDEPOSIT b on a.currentbusinessdate = b.dtmat "
							+ "where a.coopcode =:coopcode and branchname= 'HEAD OFFICE'",
					param, Tbtimedeposit.class, 1, null);
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
							form.setRemarks("Time Deposit Interest Crediting");
							form.setTxby("SYS");
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
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<String> listOfOpenBranch() {
		List<String> openBranches = new ArrayList<String>();
		param.put("coopcode", UserUtil.getUserByUsername(service.getUserName()).getCoopcode());
		try {
			openBranches = (List<String>) dbService
					.executeListSQLQuery("select branchname from TBBRANCH where branchstatus=1 and coopcode =:coopcode"
							+ " and branchname != 'HEAD OFFICE'", param);
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
			dbService.save(timedep);
			dbService.executeUpdate("UPDATE TBDEPOSIT SET AccountStatus ='5',AccountBalance=0 WHERE accountno =:acctno",
					param);
			result = 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
}
