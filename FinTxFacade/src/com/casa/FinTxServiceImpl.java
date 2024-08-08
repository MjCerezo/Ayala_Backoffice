package com.casa;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.casa.acct.AccountService;
import com.casa.acct.AccountServiceImpl;
import com.casa.fintx.forms.AccountCheckForm;
import com.casa.fintx.forms.AccountInquiryForm;
import com.casa.fintx.forms.AccountInquiryJournalForm;
import com.casa.fintx.forms.AccountInquiryMainForm;
import com.casa.fintx.forms.CashInCashOutForm;
import com.casa.fintx.forms.CashPositionConfirmForm;
import com.casa.fintx.forms.CashPositionForm;
import com.casa.fintx.forms.FinGenericForm;
import com.casa.fintx.forms.InquiryNameList;
import com.casa.fintx.forms.OverrideActionForm;
import com.casa.fintx.forms.OverrideResultForm;
import com.casa.fintx.forms.TransactForm;
import com.casa.util.UtilService;
import com.casa.util.UtilServiceImpl;
import com.coopdb.data.Tbbranch;
import com.coopdb.data.Tbbrfintxjrnl;
import com.coopdb.data.Tbcashposition;
import com.coopdb.data.Tbcashpositiondenom;
import com.coopdb.data.Tbchecksforclearing;
import com.coopdb.data.Tbdeposit;
import com.coopdb.data.Tbdeptxjrnl;
import com.coopdb.data.Tbfintxchecklist;
import com.coopdb.data.Tbfintxjrnl;
import com.coopdb.data.Tbholdamtcheck;
import com.coopdb.data.Tbmanagerscheck;
import com.coopdb.data.Tbmctxjrnl;
import com.coopdb.data.Tbnetamt;
import com.coopdb.data.Tboverride;
import com.coopdb.data.Tbtransactioncode;
import com.coopdb.data.Tbuser;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.util.ConfigPropertyUtil;
import com.etel.util.Connection;
import com.etel.util.HQLUtil;
import com.etel.util.SequenceGenerator;
import com.etel.utils.DateTimeUtil;
import com.etel.utils.UserUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class FinTxServiceImpl implements FinTxService {

	DBService dbService = new DBServiceImpl();
	Map<String, Object> param = HQLUtil.getMap();
	SecurityService service = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	UtilService utlSrvc = new UtilServiceImpl();
	private static boolean check = Connection.connectionCheck();
	private String wsurl = ConfigPropertyUtil.getPropertyValue("ws_url");

	@Override
	public String cashPos(CashPositionForm form) {
		// TODO Auto-generated method stub
		String flag = null;
		/*
		 * flag return values 1 = success null = error in routine
		 */
		try {
			String seq = SequenceGenerator
					.generateSequence(UserUtil.getUserByUsername(service.getUserName()).getUserid());
			Tbcashposition pos = form.getCashpos();
			Tbcashpositiondenom dnm = form.getCashdnm();
			pos.setTxref(seq);
			dnm.setTxrefno(seq);
			pos.setCreatedby(UserUtil.getUserByUsername(service.getUserName()).getUserid());
			pos.setTxdate(new Date());
			dbService.save(pos);
			dbService.save(dnm);
			param.put("txref", pos.getTxref());
			param.put("userid", pos.getCreatedby());
			System.out.println(param);
			flag = String.valueOf(dbService.execStoredProc(
					"DECLARE @retrn varchar(1) EXEC CASHPOSITION "
							+ "@retrn = @retrn OUTPUT, @txref=:txref,@userid=:userid SELECT @retrn as N'@result'", // CED
																													// 922019
																													// userid
					param, null, 0, null));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public CashPositionConfirmForm cashPosDenom(String txref) {
		// TODO Auto-generated method stub
		CashPositionConfirmForm form = new CashPositionConfirmForm();
		try {
			param.put("txref", txref);
			String status = String.valueOf(dbService.execStoredProc(
					"SELECT cd.desc1 FROM Tbcashposition pos JOIN Tbcodetablecasa cd "
							+ "ON pos.txstatus=cd.codevalue WHERE pos.txref=:txref AND cd.codename='CSPD'",
					param, null, 0, null));
			Tbcashpositiondenom denom = (Tbcashpositiondenom) dbService
					.executeUniqueHQLQuery("FROM Tbcashpositiondenom " + "WHERE txrefno=:txref", param);
			form.setDenom(denom);
			form.setStatus(status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public String confirmCashPos(String txref, String act, String remarks) {
		// TODO Auto-generated method stub
		String flag = null;
		/*
		 * flag return values 1 = success updating null = error in routine
		 */
		try {
			param.put("txref", txref);
			param.put("act", act);
			param.put("remarks", remarks);
			param.put("userid", UserUtil.getUserByUsername(service.getUserName()).getUserid());
			flag = String.valueOf(dbService.execStoredProc("DECLARE @result VARCHAR(3) EXEC CASHPOSITIONACTION "
					+ "@txref=:txref, @action=:act, @result = @result OUTPUT,@remarks =:remarks,@userid =:userid  "
					+ "SELECT @result as N'result'", param, null, 0, null));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public FinGenericForm cashDepWithDrCrMemo(Tbfintxjrnl jrnl, List<Tbfintxchecklist> checklist) {
		// TODO Auto-generated method stub
		/*
		 * flag return values result 999 = error in routine result 0 = account not found
		 * result 1 = success result 2 = insufficient balance result 3 = posting
		 * restriction result 4 = close account result 5 = tellers limit result 6 =
		 * posttx and tellers limit result 7 = hold spo result 8 = (On Us Chech Deposit)
		 * checkno not yet issued result 9 = (On Us Chech Deposit) checkno already used
		 * result 10 = Rejected (For ATA only)/ATA posttx
		 */
		/*
		 * txcode 111212 = Other Bank Check Deposit txcode 110111 = Cash Deposit txcode
		 * 120121 = Cash Withdrawal txcode 112013 = Credit Memo txcode 122023 = Debit
		 * Memo txcode 111322 = Fund transfer txcode 111211 = On Us Check Deposit
		 */
		FinGenericForm form = new FinGenericForm();
		try {
			Tbuser user = UserUtil.getUserByUsername(service.getUserName());
			param.put("userid", user.getUserid());
			param.put("branchcode", user.getBranchcode());
			param.put("coopcode", user.getCoopcode());
			jrnl.setTxdate(SequenceGenerator.fixDateTime(jrnl.getTxdate()));
			jrnl.setTxvaldt(jrnl.getTxdate());
			jrnl.setTxref(
					SequenceGenerator.generateSequence(UserUtil.getUserByUsername(service.getUserName()).getUserid()));
			ObjectMapper mapper = new ObjectMapper();
			String strData = mapper.writeValueAsString(jrnl);
			Tbbrfintxjrnl tosave = mapper.readValue(strData, Tbbrfintxjrnl.class);
			if ((Integer) dbService.execStoredProc(null, null, null, 4, tosave) > 0) {
				System.out.println("SAVED");
			}
			if (check) {
				URL url = new URL(wsurl + "/fin/transact");
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setDoOutput(true);
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Type", "application/json");
				TransactForm txform = new TransactForm();
				txform.setJrnl(mapper.readValue(strData, Tbfintxjrnl.class));
				txform.setChecklist(checklist);
				System.out.println(
						param.get("coopcode") + " \n ez " + param.get("branchcode") + " \n pz " + param.get("userid"));
				txform.setTellerslimit(new BigDecimal(dbService.execStoredProc(
						"SELECT ISNULL((SELECT TOP 1 ISNULL(limit.limitamount,999999999) from TBUSER usr "
								+ "left join TBUSERROLES roles " + "on roles.username = usr.username "
								+ "left join TBTELLERSLIMIT limit " + "on limit.roleid = roles.roleid "
								+ "where usr.userid =:userid "
								+ "and limit.coopcode =:coopcode and limit.branchcode =:branchcode "
								+ "and roles.roleid like '%TELLER%' order by limit.limitamount desc),999999999)",
						param, null, 0, null).toString()));
				String jsonData = mapper.writeValueAsString(txform);
				OutputStream os = con.getOutputStream();
				os.write(jsonData.getBytes());
				os.flush();
				if (con.getResponseCode() != HttpURLConnection.HTTP_ACCEPTED) {
					form.setResult("999");
				} else {
					BufferedReader in = new BufferedReader(new InputStreamReader((con.getInputStream())));
					form = mapper.readValue(in.readLine(), FinGenericForm.class);
				}
				con.disconnect();
				param.put("txref", form.getTxrefbr());
				param.put("txrefmain", form.getTxrefno());
				param.put("txstat", form.getTxstatus());
				param.put("override", form.getOverride());
				dbService.execStoredProc(
						"UPDATE TBBRFINTXJRNL SET hostacceptind='1', txrefmain=:txrefmain, txstatus=:txstat, "
								+ "override=:override WHERE txref=:txref",
						param, null, 2, null);
				System.out.println("0: " + form.getTxrefbr());
				System.out.println("1: " + form.getTxrefno());
				System.out.println("2: " + form.getResult());
				if (jrnl.getTxcode().equals("111212") && form.getResult().equals("1")) {
					// Other Bank Checks
					for (Tbfintxchecklist ck : checklist) {
						System.out.println("check date > " + jrnl.getCheckdate() + " checkno > " + jrnl.getCheckno()
								+ " brstn > " + jrnl.getBrstnno());
						Calendar clearingdate = Calendar.getInstance();
						clearingdate.setTime(new Date());
						clearingdate.add(Calendar.DAY_OF_MONTH,
								Integer.valueOf(jrnl.getCleartype().replaceAll("[^\\d.]", "")));
						Tbchecksforclearing check = new Tbchecksforclearing();
						check.setAccountnumber(jrnl.getAccountno());
						check.setBrstn(ck.getBrstnno());
						check.setCheckamount(ck.getCheckamt());
						check.setChecknumber(ck.getCheckno());
						check.setClearingdate(clearingdate.getTime());
						check.setClearingdays(Integer.valueOf(jrnl.getCleartype().replaceAll("[^\\d.]", "")));
						check.setIslateclearing(jrnl.getChecklateind());
						check.setStatus("1");
						check.setCheckdate(jrnl.getCheckdate());
						dbService.save(check);
					}
				}
			} else {
				form.setResult("503");
			}
		} catch (Exception e) {
			e.printStackTrace();
			form.setResult("999");
		}
		return form;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AccountInquiryMainForm accountInquiry(String acctno) {
		// TODO Auto-generated method stub
		AccountInquiryMainForm form = new AccountInquiryMainForm();
		/*
		 * Result 0 = Account not found/does not exist Result 1 = Account found Result
		 * 999 = Error in webservice check for error
		 */
		try {
//			if (check) {
//				String url = wsurl + "/util/acctinq/" + acctno;
//				URL obj = new URL(url);
//				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//				con.setRequestMethod("GET");
//				System.out.println("Response Code : " + con.getResponseCode());
//				if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
//					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//					ObjectMapper mapper = new ObjectMapper();
//					form = mapper.readValue(in.readLine(), AccountInquiryMainForm.class);
//					param.put("acctno", form.getInquiry().getAccountno());
//					if (!dbService
//							.execStoredProc("SELECT instcode FROM TBDEPOSIT WHERE accountno=:acctno", param, null, 0,
//									null)
//							.toString().equals(UserUtil.getUserByUsername(service.getUserName()).getCoopcode()))
//						form.setResult("0");
//				} else {
//					form.setResult("999");
//				}
//				con.disconnect();
//			} else {
//				form.setResult("503");
//			}
			param.put("acctno", acctno);
			form.setInquiry((AccountInquiryForm) dbService.execStoredProc("EXEC ACCTINQ :acctno", param,
					AccountInquiryForm.class, 0, null));
			if (form.getInquiry().getAccountnoata() != null) {
				param.put("ataacctno", form.getInquiry().getAccountnoata());
				form.setAtainq((AccountInquiryForm) dbService.execStoredProc("EXEC ACCTINQ :ataacctno", param,
						AccountInquiryForm.class, 0, null));
			}
			form.setHistlist((List<AccountInquiryJournalForm>) dbService.execStoredProc(
					"SELECT ROW_NUMBER() OVER(ORDER BY txrefno) AS sequenceNo, txdate,txrefno,txvaldt,debit,credit,txamount as txamt,outbal,IIF(islatecheck=1,txname + ' (LC)',txname) as txcode,"
							+ "IIF(errorcorrectind = 1,'EC','') as errorcorrect,txbranch as unit, (u.username) as createdby,tx.txcode + ',' + ISNULL(jrnl.remarks,'') as accountname "
							+ "FROM Tbdeptxjrnl jrnl left join Tbtransactioncode tx on jrnl.txcode = tx.txcode left join TBUSER u on u.userid = jrnl.CreatedBy WHERE accountno=:acctno and txstatus = '2' "
							+ "AND tx.txcode != '112243' AND jrnl.txcode !='911400' order by txrefno",
					param, AccountInquiryJournalForm.class, 1, null));
			form.setResult("1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InquiryNameList> accountInquiryName(String name) {
		// TODO Auto-generated method stub
		List<InquiryNameList> list = null;
		param.put("coopcode", UserUtil.getUserByUsername(service.getUserName()).getCoopcode());
		try {
			param.put("name", "%" + name + "%");
			list = (List<InquiryNameList>) dbService.execStoredProc("SELECT TD.AccountName AS name, "
					+ "CASE WHEN TD.freezeind = 1 AND "
					+ "(SELECT effectivitydate FROM Tbfreezeaccount WHERE accountno = TD.accountno AND txstatus = '1') <= "
					+ "(SELECT currentbusinessdate FROM Tbbranch WHERE branchcode = TD.unit) THEN 'Freeze Account' ELSE "
					+ "(SELECT desc1 FROM TBCODETABLECASA WHERE codename = 'CASA-ACCTSTS' AND codevalue = TD.AccountStatus) END AS acctsts, "
					+ "TD.AccountNo AS acctno, PROD.prodname AS productCode FROM TBDEPOSIT TD LEFT JOIN TBPRODMATRIX PROD "
					+ "on PROD.prodcode=TD.SubProductCode WHERE "
					+ "TD.AccountName LIKE :name AND TD.instcode =:coopcode", param, InquiryNameList.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CashInCashOutForm getCashInCashOut(String userid, String currency) {
		// System.out.println(">>> running getCashInCashOut <<<");
		CashInCashOutForm form = new CashInCashOutForm();
		try {
			param.put("userid", userid);
			param.put("currency", currency);
			param.put("branchcode", UserUtil.getUserByUsername(service.getUserName()).getBranchcode());
			Tbbranch branch = (Tbbranch) dbService.executeUniqueHQLQuery("FROM Tbbranch WHERE branchcode=:branchcode",
					param);
			param.put("busdate", branch.getCurrentbusinessdate());
			List<Tbcashposition> cashout = (List<Tbcashposition>) dbService
					.execStoredProc("SELECT pos.id, pos.txdate, pos.txvaldt, tu.branchname AS unit, "
							+ "orgn.username AS origin, dest.username AS destination, pos.currency, pos.txamount, pos.remarks, pos.txref, "
							+ "cd.desc1 AS txstatus, pos.instcode, pos.transfertype as transfertype, pos.createdby as createdby, picos FROM TBCASHPOSITION pos , TBUSER orgn, TBUSER dest, TBBRANCH tu, "
							+ "TBCODETABLECASA cd WHERE orgn.userid = pos.origin AND dest.userid= pos.destination AND tu.branchcode=pos.unit "
							+ "AND pos.origin=:userid AND pos.origin!=pos.destination AND pos.txstatus = cd.codevalue AND cd.codename='CSPD' "
							+ "AND CAST(pos.txvaldt AS DATE) =:busdate AND pos.currency=:currency ",
//							+ "AND pos.txstatus <> 'R'",
							param, Tbcashposition.class, 1, null);
			List<Tbcashposition> cashin = (List<Tbcashposition>) dbService
					.execStoredProc("SELECT pos.id, pos.txdate, pos.txvaldt, tu.branchname AS unit, "
							+ "orgn.username AS origin, dest.username AS destination, pos.currency, pos.txamount, pos.remarks, pos.txref, "
							+ "cd.desc1 AS txstatus, pos.instcode, pos.transfertype as transfertype, pos.createdby as createdby, picos FROM TBCASHPOSITION pos , TBUSER orgn, TBUSER dest, TBBRANCH tu, "
							+ "TBCODETABLECASA cd WHERE orgn.userid = pos.origin AND dest.userid= pos.destination AND tu.branchcode=pos.unit "
							+ "AND pos.destination=:userid AND pos.txstatus = cd.codevalue AND cd.codename='CSPD' "
							+ "AND CAST(pos.txvaldt AS DATE) =:busdate AND pos.currency=:currency ",
//							+ "AND pos.txstatus <> 'R'",
							param, Tbcashposition.class, 1, null);

			form.setCashin(cashin);
			form.setCashout(cashout);
			// System.out.println(">>> inside getCashInCashOut <<<");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public String overrideTransaction(String txrefno, String username) {
		// TODO Auto-generated method stub
		String result = "0";
		try {
			if (check) {
				param.put("txrefno", txrefno);
				param.put("userid", UserUtil.getUserByUsername(service.getUserName()).getUserid());
				System.out.println(" overid " + txrefno + username);
				ObjectMapper mapper = new ObjectMapper();
				String jsonData = mapper.writeValueAsString(param);
				String url = wsurl + "/fin/override";
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("PUT");
				con.setDoOutput(true);
				con.setRequestProperty("Content-Type", "application/json");
				con.setRequestProperty("Accept", "application/json");
				OutputStream os = con.getOutputStream();
				os.write(jsonData.getBytes());
				os.flush();
				System.out.println("Response Code : " + con.getResponseCode());
				if (con.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED) {
					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					result = in.readLine().equalsIgnoreCase("success") ? "1" : "0";
				}
				con.disconnect();
			} else {
				result = "503";
			}
			System.out.println("###>>>>>> OVERRIDE " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String cancelOverrideTX(String txrefno) {
		// TODO Auto-generated method stub
		String result = "0";
		try {
			if (check) {
				param.put("txref", txrefno);
				ObjectMapper mapper = new ObjectMapper();
				String jsonData = mapper.writeValueAsString(param);
				String url = wsurl + "/fin/override-cancel";
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("PUT");
				con.setDoOutput(true);
				con.setRequestProperty("Content-Type", "application/json");
				con.setRequestProperty("Accept", "application/json");
				OutputStream os = con.getOutputStream();
				os.write(jsonData.getBytes());
				os.flush();
				System.out.println("Response Code : " + con.getResponseCode());
				if (con.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED) {
					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					result = in.readLine().equalsIgnoreCase("success") ? "1" : "0";
				}
				con.disconnect();
			} else {
				result = "503";
			}
			System.out.println("###>>>>>> CANCEL OVERRIDE " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public AccountCheckForm checkAcct(String acctno) {
		// TODO Auto-generated method stub
		/**
		 * result 0 = not found result 1 = success, result 999 = error in routine
		 **/
		AccountCheckForm form = new AccountCheckForm();
		form.setResult("0");
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("accountno", acctno);
		try {
//			if (check) {
//				String url = wsurl + "/util/checkacct/" + acctno;
//				URL obj = new URL(url);
//				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//				con.setRequestMethod("GET");
//				System.out.println("Response Code : " + con.getResponseCode());
//				if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
//					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//					ObjectMapper mapper = new ObjectMapper();
//					form = mapper.readValue(in.readLine(), AccountCheckForm.class);
//					param.put("acctno", form.getAccountno());
//					if (form.getAccountno() != null && !dbService
//							.execStoredProc("SELECT instcode FROM TBDEPOSIT WHERE accountno=:acctno", param, null, 0,
//									null)
//							.toString().equals(UserUtil.getUserByUsername(service.getUserName()).getCoopcode()))
//						form.setResult("0");
//				} else {
//					form.setResult("999");
//				}
//				con.disconnect();
//			} else {
//				form.setResult("503");
//			}
			form = (AccountCheckForm) dbService.execStoredProc(
					"SELECT dep.accountno, ISNULL(prod.currency,'PHP') AS currency, dep.AccountName AS name, dep.ProductCode AS prodtype,'1' AS result,dep.unit,ISNULL(prod.passbookind,0) as passbookind, "
							+ " ISNULL(prod.checkbookind,0) as checkbookind, ISNULL(prod.soaind,0) as soaind,prod.rbmminmainbal AS mainbal, ISNULL(prod.certtimedepind,0) as certtimedepind, cd.desc1 AS accountstatus, prod.posttx, "
							+ " prod.prodname AS subprod, (isnull(SUM(isnull(dep.accountBalance,0)),0) - (isnull(SUM(isnull(dep.floatAmount,0)),0) + "
							+ " isnull(SUM(isnull(dep.placeholdAmt,0)),0) +  isnull(SUM(isnull(dep.floatAmount,0)),0) + isnull(SUM(isnull(dep.earmarkbal,0)),0) + isnull(SUM(isnull(dep.garnishedbal,0)),0) )) AS availbal "
							+ " FROM TBDEPOSIT dep LEFT JOIN TBCODETABLECASA cd on dep.accountStatus=cd.codevalue AND cd.codename ='CASA-ACCTSTS' LEFT JOIN "
							+ " TBPRODMATRIX prod ON dep.ProductCode=prod.prodgroup AND dep.SubProductCode = prod.prodcode WHERE  dep.AccountNo=:accountno GROUP BY dep.accountno, "
							+ " dep.unit, prod.passbookind,prod.checkbookind, prod.soaind, prod.certtimedepind, prod.posttx,prod.currency, dep.AccountName, dep.ProductCode, "
							+ " prod.rbmminmainbal,cd.desc1, prod.prodname",
					param, AccountCheckForm.class, 0, null);
			if (form == null) {
				form = new AccountCheckForm();
				form.setResult("0");
				return form;
			}
			form.setResult("1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public AccountCheckForm checkAcctFundTrans(String acctnoto, String acctnofrom) {
		// TODO Auto-generated method stub
		/**
		 * result 0 = both acct not found result 0.1 = source acct not found result 0.2
		 * = destination acct not found result 0.3 = not same currency result 1 =
		 * success
		 **/
		AccountCheckForm form = new AccountCheckForm();
		try {
			if (check) {
				String url = wsurl + "/util/checkacct-fundtrans/" + acctnoto + "/" + acctnofrom;
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");
				System.out.println("Response Code : " + con.getResponseCode());
				if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					ObjectMapper mapper = new ObjectMapper();
					form = mapper.readValue(in.readLine(), AccountCheckForm.class);
				} else {
					form.setResult("999");
				}
				con.disconnect();
			} else {
				form.setResult("503");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public String requestRemoteOverride(String txrefno) {
		// TODO Auto-generated method stub
		String result = "0";
		try {
			param.put("txrefno", txrefno);
			if ((Integer) dbService.execStoredProc("UPDATE TBBRFINTXJRNL SET override=3 WHERE txrefmain=:txrefno",
					param, null, 2, null) > 0) {
				result = "1";
			}
			if (check) {
				String url = wsurl + "/fin/remote-override/" + txrefno;
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("PUT");
				System.out.println("Response Code : " + con.getResponseCode());
				if (con.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED) {
					result = "1";
				} else {
					result = "0";
				}
				con.disconnect();
			} else {
				result = "503";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Integer requestRemoteOverrideCount() {
		// TODO Auto-generated method stub
		Integer count = 0;
		try {
			count = (Integer) dbService.execStoredProc(
					"SELECT COUNT(*) FROM TBBRFINTXJRNL WHERE override=3 AND txstatus='5' ", null, null, 0, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OverrideActionForm> requestRemoteOverrideList() {
		// TODO Auto-generated method stub
		List<OverrideActionForm> list = new ArrayList<OverrideActionForm>();
		try {
			list = (List<OverrideActionForm>) dbService.execStoredProc(
					"SELECT jrnl.accountno AS acctno, jrnl.txamount AS amount, jrnl.txby, "
							+ "txc.txname AS txcode, jrnl.txref AS txrefno, 'jrnl' AS source FROM TBBRFINTXJRNL jrnl JOIN TBTRANSACTIONCODE txc "
							+ "ON jrnl.txcode=txc.txcode WHERE override=3 AND txstatus='5' ",
					null, OverrideActionForm.class, 1, null);
			list.addAll((List<OverrideActionForm>) dbService.execStoredProc(
					"SELECT accountno AS acctno, chargeamount AS amount, txby, txcode, CONVERT(VARCHAR(20), sequence) AS txrefno, 'override' AS source FROM TBOVERRIDE WHERE status=0",
					null, OverrideActionForm.class, 1, null));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Integer requestRemoteOverrideWait(String txrefno) {
		// TODO Auto-generated method stub
		Integer count = 0;
		try {
			param.put("txrefno", txrefno);
			count = (Integer) dbService.execStoredProc("SELECT COUNT(*) FROM TBBRFINTXJRNL WHERE override IN (4,5) "
					+ "AND txstatus='5' AND txrefmain=:txrefno", param, null, 0, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public OverrideResultForm requestRemoteOverrideResult(String txrefno) {
		// TODO Auto-generated method stub
		OverrideResultForm form = new OverrideResultForm();
		try {
			param.put("txrefno", txrefno);
			form = (OverrideResultForm) dbService.execStoredProc(
					"SELECT usr.username AS overrideby, CASE WHEN(jrnl.override=4) THEN 'Accepted' WHEN(jrnl.override=5) THEN 'Rejected' END AS resultstr, "
							+ "jrnl.override AS result FROM TBBRFINTXJRNL jrnl JOIN TBUSER usr ON jrnl.overrideby=usr.userid WHERE txrefmain=:txrefno",
					param, OverrideResultForm.class, 0, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public String requestRemoteOverrideAction(String txrefno, Integer val) {
		// TODO Auto-generated method stub
		String result = "0";
		try {
			param.put("override", val);
			param.put("txrefno", txrefno);
			param.put("overrideby", UserUtil.getUserByUsername(service.getUserName()).getUserid());
			if ((Integer) dbService.execStoredProc(
					"UPDATE TBBRFINTXJRNL SET override=:override, overrideby=:overrideby WHERE txref=:txrefno", param,
					null, 2, null) > 0) {
				System.out.println();
				result = "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbnetamt> userCashPos(String userid) {
		// TODO Auto-generated method stub
		List<Tbnetamt> list = new ArrayList<Tbnetamt>();
		try {
			param.put("userid", userid);
			list = (List<Tbnetamt>) dbService.execStoredProc("SELECT * FROM TBNETAMT WHERE userid=:userid", param,
					Tbnetamt.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String errorCorrect(String txrefno) {
		// TODO Auto-generated method stub
		String result = "";
		try {
			if (check) {
				String url = wsurl + "/fin/error-correct/" + txrefno;
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("PUT");
				System.out.println("Response Code : " + con.getResponseCode());
				if (con.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED) {
					result = "1";
				} else {
					result = "0";
				}
				con.disconnect();
			} else {
				result = "503";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Tbtransactioncode getTxinfo(String txname) {
		// TODO Auto-generated method stub
		try {
			param.put("txname", txname);
			System.out.println("service charge of: " + txname);
			return (Tbtransactioncode) dbService.execStoredProc("SELECT * FROM TBTRANSACTIONCODE WHERE txname=:txname",
					param, Tbtransactioncode.class, 0, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String initOverride(Tboverride data) {
		try {
			dbService.execStoredProc(null, null, null, 4, data);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "0";
	}

	@Override
	public String requestMc(Tbmanagerscheck data) {
		// TODO Auto-generated method stub
		String result = "";
		try {
			if (check) {
				data.setStatus("1");
				data.setDaterequest(new Date());
				ObjectMapper mapper = new ObjectMapper();
				URL url = new URL(wsurl + "/fin/request-mc");
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setDoOutput(true);
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Type", "application/json");
				String jsonData = mapper.writeValueAsString(data);
				OutputStream os = con.getOutputStream();
				os.write(jsonData.getBytes());
				os.flush();
				if (con.getResponseCode() != HttpURLConnection.HTTP_ACCEPTED) {
					result = "0";
				} else {
					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					result = mapper.readValue(in.readLine(), String.class);
				}
				con.disconnect();
			} else {
				result = "503";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String depositMc(Tbmctxjrnl data) {
		// TODO Auto-generated method stub
		// mc status
		// 1 = pending
		// 2 = issued
		// 3 = negotiated
		String result = "";
		Tbmanagerscheck mCheck = new Tbmanagerscheck();
		try {
			data.setTxdate(new Date());
			data.setTxstatus("2");
			param.put("mccheckno", data.getMccheckno());
			mCheck = (Tbmanagerscheck) dbService.execStoredProc(
					"SELECT * FROM Tbmanagerscheck WHERE mccheckno =:mccheckno", param, Tbmanagerscheck.class, 0, null);
			if (mCheck == null) {
				// check not found
				return "1";
			} else {
				if (data.getAmount().compareTo(mCheck.getAmount()) != 0) {
					// Invalid amount
					return "2";
				} else if (!DateTimeUtil.convertDateToString(data.getTxdate(), DateTimeUtil.DATE_FORMAT_MM_DD_YYYY)
						.equals(DateTimeUtil.convertDateToString(mCheck.getDaterequest(),
								DateTimeUtil.DATE_FORMAT_MM_DD_YYYY))) {
					// Invalid Date
					return "3";
				} else {
					// Stale
					Calendar mDate = Calendar.getInstance();
					mDate.setTime(mCheck.getDaterequest());
					Calendar sixMonths = Calendar.getInstance();
					sixMonths.set(Calendar.MONTH, -6);
					if (mDate.before(sixMonths)) {
						return "4";
					}
				}
				if (mCheck.getStatus().equals("3")) {
					// Negotiated
					return "5";
				}
			}
			dbService.save(data);
			mCheck.setStatus("2");
			dbService.saveOrUpdate(mCheck);
			return "0";
		} catch (Exception e) {
			result = "5";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String saveBuySellFx(Tbbrfintxjrnl brjrnl) {
		// TODO Auto-generated method stub
		String result = "failed";
//		Tbfintxjrnl fin = new Tbfintxjrnl();
		try {
			if (check) {
				String url = wsurl + "/util/sequence";
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");
				System.out.println("Response Code : " + con.getResponseCode());
				if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					ObjectMapper mapper = new ObjectMapper();
					String txrefmain = mapper.readValue(in.readLine(), String.class);

					if (txrefmain == null) {
						return result;
					}
					brjrnl.setTxref(SequenceGenerator
							.generateSequence(UserUtil.getUserByUsername(service.getUserName()).getUserid()));
					brjrnl.setTxrefmain(txrefmain);
					if (brjrnl.getRemarks().equals("Buy")) {
						brjrnl.setTxcode("111123");
					} else {
						brjrnl.setTxcode("111133");
					}
					if (brjrnl.getAccountno() == null) {
						brjrnl.setAccountno("000000000000");
					} else {
						brjrnl.setAccountno(brjrnl.getAccountno());
					}
					if (dbService.save(brjrnl)) {
						result = "saved";

					}

				} else {
					return result;
				}
				con.disconnect();

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String mcgcDeposit(Tbfintxjrnl fin) {
		String res = "8";
		Tbmanagerscheck check = new Tbmanagerscheck();
		try {
			param.put("checkacctno", fin.getCheckacctno());
			check = (Tbmanagerscheck) dbService.execStoredProc(
					"SELECT * FROM Tbmanagerscheck WHERE mccheckno =:checkacctno", param, Tbmanagerscheck.class, 0,
					null);
			if (check == null) {
				// check not found
				return "1";
			} else {
				if (fin.getTxamount().compareTo(check.getAmount()) != 0) {
					// Invalid amount
					return "2";
				} else if (!DateTimeUtil.convertDateToString(fin.getCheckdate(), DateTimeUtil.DATE_FORMAT_MM_DD_YYYY)
						.equals(DateTimeUtil.convertDateToString(check.getDaterequest(),
								DateTimeUtil.DATE_FORMAT_MM_DD_YYYY))) {
					// Invalid Date
					return "3";
				} else {
					// Stale
					Calendar mDate = Calendar.getInstance();
					mDate.setTime(check.getDaterequest());
					Calendar sixMonths = Calendar.getInstance();
					sixMonths.set(Calendar.MONTH, -6);
					if (mDate.before(sixMonths)) {
						return "4";
					}
				}
				if (check.getStatus().equals("3")) {
					// Negotiated
					return "5";
				}
			}
			param.put("acctno", fin.getAccountno());
			param.put("userid", UserUtil.getUserByUsername(service.getUserName()).getUserid());
			Tbdeposit dep = (Tbdeposit) dbService.executeUniqueHQLQuery("FROM Tbdeposit WHERE accountno=:acctno",
					param);
			fin.setTxstatus("2");
			fin.setTxref(
					(String) dbService
							.executeUniqueSQLQuery(
									"DECLARE @txrefno VARCHAR(20) " + "DECLARE @txrefno1 VARCHAR(20) "
											+ "EXEC SEQGEN_REFNO :userid, @txrefno1 OUTPUT " + "SELECT @txrefno1",
									param));
			fin.setTxrefmain((String) dbService.executeUniqueSQLQuery(
					"DECLARE @txrefno VARCHAR(20) " + "EXEC SEQGENERATE @txrefno OUTPUT " + "SELECT @txrefno", param));
			fin.setTxdate(new Date());
			if (dbService.save(fin)) {
				dep.setAccountBalance(dep.getAccountBalance().add(fin.getTxamount()));
				dep.setMtdcredits(dep.getMtdcredits().add(fin.getTxamount()));
				dep.setBtdcredits(dep.getBtdcredits().add(fin.getTxamount()));
				dep.setTotalNoCredits(dep.getTotalNoCredits() + 1);
				dbService.saveOrUpdate(dep);
				Tbdeptxjrnl jrnl = new Tbdeptxjrnl();
				jrnl.setTxdate(fin.getTxdate());
				jrnl.setAccountno(fin.getAccountno());
				jrnl.setApprovalDate(new Date());
				jrnl.setApprovedBy(UserUtil.getUserByUsername(service.getUserName()).getUserid());
				jrnl.setCheckamount(fin.getTxamount());
				jrnl.setCheckdate(
						DateTimeUtil.convertDateToString(fin.getCheckdate(), DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
				jrnl.setChecknumber(fin.getCheckacctno());
				jrnl.setCreatedBy(UserUtil.getUserByUsername(service.getUserName()).getUserid());
				jrnl.setCreationDate(new Date());
				jrnl.setCredit(jrnl.getCheckamount());
				jrnl.setDebit(BigDecimal.ZERO);
				jrnl.setOutBal(dep.getAccountBalance());
				jrnl.setTxcode(fin.getTxcode());
				jrnl.setTxStatus("2");
				jrnl.setTxRefNo(fin.getTxrefmain());
				jrnl.setTxvaldt(fin.getTxvaldt());
				dbService.save(jrnl);
				check.setStatus("3");
				dbService.saveOrUpdate(check);
				return "0";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "8";
		}
		return res;
	}

	@Override
	public String encashMc(Tbmctxjrnl data) {
		// TODO Auto-generated method stub
		// mc status
		// 1 = pending
		// 2 = issued
		// 3 = negotiated
		String result = "";
		Tbmanagerscheck mCheck = new Tbmanagerscheck();
		try {
			data.setTxdate(new Date());
			data.setTxstatus("2");
			param.put("mccheckno", data.getMccheckno());
			mCheck = (Tbmanagerscheck) dbService.execStoredProc(
					"SELECT * FROM Tbmanagerscheck WHERE mccheckno =:mccheckno", param, Tbmanagerscheck.class, 0, null);
			if (mCheck == null) {
				// check not found
				return "1";
			} else {
				if (data.getAmount().compareTo(mCheck.getAmount()) != 0) {
					// Invalid amount
					return "2";
				} else if (!DateTimeUtil.convertDateToString(data.getTxdate(), DateTimeUtil.DATE_FORMAT_MM_DD_YYYY)
						.equals(DateTimeUtil.convertDateToString(mCheck.getDaterequest(),
								DateTimeUtil.DATE_FORMAT_MM_DD_YYYY))) {
					// Invalid Date
					return "3";
				} else {
					// Stale
					Calendar mDate = Calendar.getInstance();
					mDate.setTime(mCheck.getDaterequest());
					Calendar sixMonths = Calendar.getInstance();
					sixMonths.set(Calendar.MONTH, -6);
					if (mDate.before(sixMonths)) {
						return "4";
					}
				}
				if (mCheck.getStatus().equals("3")) {
					// Negotiated
					return "5";
				}
			}
			dbService.save(data);
			mCheck.setStatus("3");
			dbService.saveOrUpdate(mCheck);
			return "0";
		} catch (Exception e) {
			result = "5";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Integer checkBranch(String brcode, String acctno) {
		// TODO Auto-generated method stub
		int check = 0;
		try {
			param.put("acctno", acctno);
			param.put("brcode", brcode);
			System.out.println("acctno: " + acctno + " brcode: " + brcode);
			if (acctno != null && brcode != null) {
				check = (Integer) dbService.executeUniqueSQLQuery(
						"SELECT COUNT(*) FROM Tbdeposit WHERE accountNo=:acctno AND unit=:brcode", param);

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return check;
	}

	@Override
	public String lockAmount(String memberid, BigDecimal amount, String reason) {
		String flag = "failed";
		AccountService acctservice = new AccountServiceImpl();
		param.put("memberid", memberid);
		param.put("amount", amount);
		param.put("reason", reason);
		try {
			String accountno = (String) dbService.executeUniqueHQLQuery(
					"select accountno from Tbdepositcif where cifno=:memberid and SUBSTRING(accountno,4,2)='51'",
					param);
			param.put("accountno", accountno);
			Tbdeposit dep = (Tbdeposit) dbService.executeUniqueHQLQuery("FROM Tbdeposit WHERE accountno=:accountno",
					param);
			Tbfintxjrnl jrnl = new Tbfintxjrnl();
			jrnl.setTxby(service.getUserId());
			jrnl.setTxdate(new Date());
			jrnl.setAcctname(dep.getAccountName());
			jrnl.setTxamount(amount);
			jrnl.setCurrency("PHP");
			jrnl.setInstcode(dep.getInstcode());
			jrnl.setTxcode("113383");
			jrnl.setUnit(UserUtil.getUserByUsername(service.getUserName()).getBranchcode());
			jrnl.setAccountno(accountno);
			if (cashDepWithDrCrMemo(jrnl, null).getResult().equals("1")) {
				Tbholdamtcheck form = new Tbholdamtcheck();
				form.setAccountname(dep.getAccountName());
				form.setStatus(1);
				form.setRemarks("MDR");
				form.setExpirydate(null);
				form.setTxcode("113321");
				form.setHoldreason(reason);
				form.setCurrency("PHP");
				form.setAmount(amount);
				form.setUnit(jrnl.getUnit());
				form.setAccountno(jrnl.getAccountno());
				form.setDatehold(jrnl.getTxdate());
				form.setHoldby(service.getUserId());
				form.setInstcode("SLA");
				if (acctservice.placeHoldAmt(form).getResult() == 1)
					flag = "success";
				else
					System.out.println("Error creating hold out transaction");
			} else
				System.out.println("Error locking amount");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String unlockAmount(String memberid, BigDecimal amount) {
		String flag = "failed";
		param.put("memberid", memberid);
		param.put("amount", amount);
		try {
			String accountno = (String) dbService.executeUniqueHQLQuery(
					"select accountno from TBDEPOSITCIF where cifno=:memberid and SUBSTRING(accountno,4,2)='51'",
					param);
			param.put("accountno", accountno);
			Tbdeposit dep = (Tbdeposit) dbService.execStoredProc("SELECT * FROM Tbdeposit WHERE accountno=:accountno",
					param, Tbdeposit.class, 0, null);
			Tbfintxjrnl jrnl = new Tbfintxjrnl();
			jrnl.setTxby(service.getUserId());
			jrnl.setTxdate(new Date());
			jrnl.setAcctname(dep.getAccountName());
			jrnl.setTxamount(amount);
			jrnl.setCurrency("PHP");
			jrnl.setInstcode(dep.getInstcode());
			jrnl.setTxcode("113393");
			jrnl.setUnit(UserUtil.getUserByUsername(service.getUserName()).getBranchcode());
			jrnl.setAccountno(accountno);
			if (cashDepWithDrCrMemo(jrnl, null).getResult().equals("1")) {
				flag = "success";

			} else
				System.out.println("Error unlocking amount");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}
}
