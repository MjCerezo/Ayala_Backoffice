package com.casa.acct;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cloudfoundry.org.codehaus.jackson.map.ObjectMapper;

import com.casa.FinTxService;
import com.casa.FinTxServiceImpl;
import com.casa.acct.forms.AccountClosureForm;
import com.casa.acct.forms.AccountGenericForm;
import com.casa.acct.forms.AccountMaintenanceForm;
import com.casa.acct.forms.CIFWatchlistForm;
import com.casa.acct.forms.CIFWatchlistMainForm;
import com.casa.acct.forms.CheckDeceasedForm;
import com.casa.acct.forms.FileMaintenanceHistory;
import com.casa.acct.forms.InquiryCIFNameList;
import com.casa.acct.forms.LiftGarnishFormData;
import com.casa.acct.forms.MaturedAccountActionForm;
import com.casa.acct.forms.MaturedAccountHandler;
import com.casa.acct.forms.PlaceHoldForm;
import com.casa.acct.forms.PlaceHoldFormHandler;
import com.casa.acct.forms.StopPaymentOrderForm;
import com.casa.acct.forms.StopPaymentOrderHandler;
import com.casa.acct.forms.TimeDepositAccountDetailForm;
import com.casa.acct.forms.TimeDepositCertForm;
import com.casa.acct.forms.TimeDepositListForm;
import com.casa.acct.util.AccountNumberGenerator;
import com.casa.user.forms.UserInfoForm;
import com.cifsdb.data.Tbamlalistmain;
import com.cifsdb.data.Tbblacklistmain;
import com.cifsdb.data.Tbcodetable;
import com.cifsdb.data.Tbdosri;
import com.cifsdb.data.Tbfatca;
import com.cifsdb.data.Tbotheraccounts;
import com.cifsdb.data.Tbpepinfo;
import com.coopdb.data.Tbbranch;
import com.coopdb.data.Tbchecksforclearing;
import com.coopdb.data.Tbcodetablecasa;
import com.coopdb.data.Tbdeposit;
import com.coopdb.data.Tbdepositcif;
import com.coopdb.data.Tbfilehistory;
import com.coopdb.data.Tbfintxjrnl;
import com.coopdb.data.Tbfreezeaccount;
//import com.coopdb.data.Tbfreezeaccount;
import com.coopdb.data.Tbholdamtcheck;
import com.coopdb.data.Tblockamount;
import com.coopdb.data.Tbprodmatrix;
import com.coopdb.data.Tbsigcard;
import com.coopdb.data.Tbtimedeposit;
import com.etel.branch.BranchService;
import com.etel.branch.BranchServiceImpl;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.qde.QDEService;
import com.etel.qde.QDEServiceImpl;
import com.etel.qdeforms.QDEParameterForm;
import com.etel.util.ConfigPropertyUtil;
import com.etel.util.Connection;
import com.etel.util.HQLUtil;
import com.etel.utils.CIFNoGenerator;
import com.etel.utils.DateTimeUtil;
import com.etel.utils.ImageUtils;
import com.etel.utils.UserUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

@SuppressWarnings("unchecked")
public class AccountServiceImpl implements AccountService {

	DBService dbService = new DBServiceImpl();
	Map<String, Object> param = HQLUtil.getMap();
	SecurityService service = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private static boolean check = Connection.connectionCheck();
	private String wsurl = ConfigPropertyUtil.getPropertyValue("ws_url");

	@Override
	public AccountGenericForm createAccount(Tbdeposit dep, List<Tbdepositcif> ciflist) {
		/***
		 * RESULT 0/999 = Error 1 = Success
		 ***/
		AccountGenericForm form = new AccountGenericForm();
		form.setResult(0);
		try {
//			if (check) {
//				dep.setCreatedBy(UserUtil.getUserByUsername(service.getUserName()).getUserid());
//				URL url = new URL(wsurl + "/csr/create-account");
//				HttpURLConnection con = (HttpURLConnection) url.openConnection();
//				con.setDoOutput(true);
//				con.setRequestMethod("POST");
//				con.setRequestProperty("Content-Type", "application/json");
//				ObjectMapper mapper = new ObjectMapper();
//				AccountCreationForm txform = new AccountCreationForm();
//				txform.setTbdeposit(dep);
//				txform.setCiflist(ciflist);
//				System.out.println(txform.getTbdeposit().getAccountName());
//				String jsonData = mapper.writeValueAsString(txform);
//				System.out.println(jsonData);
//				OutputStream os = con.getOutputStream();
//				os.write(jsonData.getBytes());
//				os.flush();
//				if (con.getResponseCode() != HttpURLConnection.HTTP_ACCEPTED) {
//					form.setResult(0);
//				} else {
//					BufferedReader in = new BufferedReader(new InputStreamReader((con.getInputStream())));
//					form = mapper.readValue(in.readLine(), AccountGenericForm.class);
//				}
//				con.disconnect();
//			} else {
//				System.out.println("Cannot connect to server.");
//				form.setResult(503);
//			}

			dep.setAccountNo(AccountNumberGenerator.genereteAccountNumber(dep.getUnit(), dep.getSubProductCode(),
					dep.getProductCode()));
			if (dbService.save(dep)) {
				if (dep.getSigfilename() != null && dep.getSigfilename() != null) {
					String path = RuntimeAccess.getInstance().getSession().getServletContext()
							.getRealPath("resources/tempdir/" + dep.getSigfilename());
					Tbsigcard sigcard = new Tbsigcard();
					sigcard.setAccountno(dep.getAccountNo());
					sigcard.setSigbasecode(ImageUtils.imageToBase64(path));
					String filename = dep.getSigfilename() != null ? dep.getSigfilename().replace(" ", "") : "";
					sigcard.setSigfilename(filename.substring(0, filename.lastIndexOf(".")));
					dbService.save(sigcard);
//					dep.setSigbasecode(ImageUtils.imageToBase64(path));// CED SIGCARD
//					dep.setSigfilename(filename.substring(0, filename.lastIndexOf(".")));
				}
				for (Tbdepositcif cif : ciflist) {
					cif.setAccountno(dep.getAccountNo());
					dbService.save(cif);
				}
			}
			param.put("acctno", dep.getAccountNo());
			form.setResult((Integer) dbService.executeUniqueSQLQuery(
					"declare @result int exec ACCOUNTINIT :acctno,@result output select @result", param));
			form.setValue(dep.getAccountNo());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public AccountGenericForm rolloverTimeDepositAccount(Tbdeposit dep) {
		/***
		 * RESULT 0/999 = Error 1 = Success
		 ***/
		AccountGenericForm form = new AccountGenericForm();
		form.setResult(0);
		try {
			if (check) {
				dep.setCreatedBy(UserUtil.getUserByUsername(service.getUserName()).getUserid());
				URL url = new URL(wsurl + "/csr/rollover-account");
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setDoOutput(true);
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Type", "application/json");
				ObjectMapper mapper = new ObjectMapper();
				String jsonData = mapper.writeValueAsString(dep);
				OutputStream os = con.getOutputStream();
				os.write(jsonData.getBytes());
				os.flush();
				if (con.getResponseCode() != HttpURLConnection.HTTP_ACCEPTED) {
					form.setResult(0);
				} else {
					BufferedReader in = new BufferedReader(new InputStreamReader((con.getInputStream())));
					form = mapper.readValue(in.readLine(), AccountGenericForm.class);
				}
				con.disconnect();
			} else {
				form.setResult(503);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public AccountGenericForm checkAccount(String accountno) {
		/***
		 * RESULT 0 = Error 1 = Success
		 ***/
		AccountGenericForm form = new AccountGenericForm();
		try {
			form.setResult(0);
			param.put("accountno", accountno);
			Integer chk = (Integer) dbService
					.execStoredProc("SELECT COUNT(*) FROM TBDEPOSIT WHERE AccountNo=:accountno", param, null, 0, null);
			if (chk != null && chk > 0) {
				form.setResult(1);
				form.setValue(accountno);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public AccountMaintenanceForm acctInfo(String accountno) {
		AccountMaintenanceForm form = new AccountMaintenanceForm();
		try {
//			if (check) {
//				String url = wsurl + "/csr/account-maintenance/detail/" + accountno;
//				URL obj = new URL(url);
//				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//				con.setRequestMethod("GET");
//				System.out.println("Response Code : " + con.getResponseCode());
//				if (con.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED) {
//					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//					ObjectMapper mapper = new ObjectMapper();
//					form = mapper.readValue(in.readLine(), AccountMaintenanceForm.class);
//				}
//				con.disconnect();
//			} else {
//				form.setAcctsts(503);
//			}
			param.put("accountno", accountno);
			form = (AccountMaintenanceForm) dbService.execStoredProc(
					"SELECT tb.AccountNo as accountno, tb.AccountName AS name, tb.AccountStatus as acctsts, tb.BookDate as dtopened, cd.desc1 as jointaccttype, "
							+ "SUBSTRING(AccountName,1,10) AS shortname, tb.Posttx as posttx, tb.alertflag AS alertflag, tb.alertlevel AS alertlevel, "
							+ "tb.alertmessage AS alertmessage, watchlistcode as watchlist ,tb.solicitingofficer AS solofficer, tb.referralofficer AS refofficer, "
							+ "tb.campaign AS channel,  prod.prodname AS prodtype, prod.ataind AS ataind FROM TBDEPOSIT tb  JOIN TBPRODMATRIX prod ON tb.productCode = prod.prodgroup AND "
							+ "tb.subProductCode = prod.prodcode JOIN TBCODETABLECASA cd ON tb.OwnershipType=cd.codevalue WHERE "
							+ "AccountNo=:accountno AND cd.codename='OWNERSHIPTYPE'",
					param, AccountMaintenanceForm.class, 0, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public AccountGenericForm acctSave(AccountMaintenanceForm form) {
		/***
		 * RESULT 0 = Error 1 = Success
		 ***/
		AccountGenericForm rtrnform = new AccountGenericForm();
		rtrnform.setResult(0);
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("branchcode", UserUtil.getUserByUsername(UserUtil.securityService.getUserName()).getBranchcode());
		Tbbranch branch = (Tbbranch) dbsrvc.executeUniqueHQLQuery("FROM Tbbranch WHERE branchcode =:branchcode", param);
		try {
			param.put("status", form.getAcctsts());
			param.put("posttx", form.getPosttx());
			param.put("acctno", form.getAccountno());
			param.put("statusdate", form.getDtopened());
			param.put("alertflag", form.getAlertflag());
			param.put("alertlevel", form.getAlertflag() == null || form.getAlertflag() == 0 ? 0 : form.getAlertlevel());
			param.put("alertmessage", form.getAlertflag() == null || form.getAlertflag() == 0 ? "" : form.getAlertmessage());
			param.put("watchlist", form.getWatchlist());
			Tbdeposit dep = (Tbdeposit) dbsrvc.executeUniqueHQLQuery("FROM Tbdeposit WHERE accountno =:acctno", param);
			if (dep != null) {
				dep.setPosttx(form.getPosttx());
				dep.setAccountStatus(form.getAcctsts());
				dep.setAlertflag(form.getAlertflag());
				dep.setAlertlevel(form.getAlertlevel());
				dep.setAlertmessage(form.getAlertmessage());
				form.setWatchlist(form.getWatchlist());
				if (branch != null && form.getAcctsts() != dep.getAccountStatus()) {
					dep.setStatusDate(branch.getCurrentbusinessdate());
				}
				if (dbsrvc.saveOrUpdate(dep)) {
					rtrnform.setResult(1);
				}
			}
//			if (check) {
//				ObjectMapper mapper = new ObjectMapper();
//				String jsonData = mapper.writeValueAsString(param);
//				System.out.println(jsonData);
//				String url = wsurl + "/csr/account-maintenance/update";
//				URL obj = new URL(url);
//				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//				con.setRequestMethod("PUT");
//				con.setDoOutput(true);
//				con.setRequestProperty("Content-Type", "application/json");
//				con.setRequestProperty("Accept", "application/json");
//				OutputStream os = con.getOutputStream();
//				os.write(jsonData.getBytes());
//				os.flush();
//				System.out.println("Response Code : " + con.getResponseCode());
//				if (con.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED) {
//					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//					rtrnform = mapper.readValue(in.readLine(), AccountGenericForm.class);
//				}
//				con.disconnect();
//			} else {
//				rtrnform.setResult(503);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtrnform;
	}

	@Override
	public List<Tbprodmatrix> getProdList(String prodgroup) {
		List<Tbprodmatrix> list = new ArrayList<Tbprodmatrix>();
		try {
			param.put("prodgroup", prodgroup);
			list = (List<Tbprodmatrix>) dbService.execStoredProc(
					"SELECT * FROM TBPRODMATRIX WHERE isenable = 1"
							+ (prodgroup != null && !prodgroup.isEmpty() ? " AND prodgroup=:prodgroup" : ""),
					param, Tbprodmatrix.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public AccountClosureForm getAcctClosure(String accountno) {
		AccountClosureForm form = new AccountClosureForm();
		try {
			if (check) {
				String strUrl = wsurl + "/csr/account-close/view/" + accountno;
				URL url = new URL(strUrl);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setDoOutput(true);
				con.setRequestMethod("GET");
				System.out.println("Response Code : " + con.getResponseCode());
				if (con.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED) {
					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					ObjectMapper mapper = new ObjectMapper();
					form = mapper.readValue(in.readLine(), AccountClosureForm.class);
				}
				con.disconnect();
			} else {
				form.setAccountstatus("503");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public AccountGenericForm acctClose(String accountno) {
		/***
		 * RESULT 0 = Error 1 = Success
		 ***/
		AccountGenericForm form = new AccountGenericForm();
		form.setResult(0);
		try {
//			if (check) {
//				param.put("accountno", accountno);
//				ObjectMapper mapper = new ObjectMapper();
//				String jsonData = mapper.writeValueAsString(param);
//				System.out.println(jsonData);
//				String url = wsurl + "/csr/account-close/close";
//				URL obj = new URL(url);
//				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//				con.setRequestMethod("PUT");
//				con.setDoOutput(true);
//				con.setRequestProperty("Content-Type", "application/json");
//				con.setRequestProperty("Accept", "application/json");
//				OutputStream os = con.getOutputStream();
//				os.write(jsonData.getBytes());
//				os.flush();
//				System.out.println("Response Code : " + con.getResponseCode());
//				if (con.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED) {
//					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//					form = mapper.readValue(in.readLine(), AccountGenericForm.class);
//				}
//				con.disconnect();
//			} else {
//				form.setResult(503);
//			}

			DBService dbsrvc = new DBServiceImpl();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("branchcode", UserUtil.getUserByUsername(UserUtil.securityService.getUserName()));
			Tbbranch branch = (Tbbranch) dbsrvc.executeUniqueHQLQuery("FROM Tbbranch WHERE branchcode =:branchcode",
					param);
			param.put("accountno", accountno);
			Tbdeposit dep = (Tbdeposit) dbsrvc.executeUniqueHQLQuery("FROM Tbdeposit WHERE accountno=:accountno",
					param);
			if (dep == null) {
				form.setValue("Account number not found.");
				form.setResult(0);
				return form;
			}
			dep.setAccountStatus(5);
			if (branch != null) {
				dep.setStatusDate(branch.getCurrentbusinessdate());
			}
			dbsrvc.saveOrUpdate(dep);
			form.setValue("Account closed.");
			form.setResult(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public AccountGenericForm placeHoldAmt(Tbholdamtcheck record) {
		/*
		 * flag return values 0 = account not found/exist 1 = success 2 = insufficient
		 * balance 3 = error in routine
		 */
		AccountGenericForm form = new AccountGenericForm();
		try {
			if (check) {
				URL url = new URL(wsurl + "/csr/placehold-amount");
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setDoOutput(true);
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Type", "application/json");
				ObjectMapper mapper = new ObjectMapper();
				String jsonData = mapper.writeValueAsString(record);
				OutputStream os = con.getOutputStream();
				os.write(jsonData.getBytes());
				os.flush();
				if (con.getResponseCode() != HttpURLConnection.HTTP_ACCEPTED) {
					form.setResult(3);
				} else {
					BufferedReader in = new BufferedReader(new InputStreamReader((con.getInputStream())));
					form = mapper.readValue(in.readLine(), AccountGenericForm.class);
				}
				con.disconnect();
			} else {
				form.setResult(503);
			}
		} catch (Exception e) {
			e.printStackTrace();
			form.setResult(3);
		}
		return form;
	}

	@Override
	public List<PlaceHoldForm> getHoldAmtList(String accountno, String type) {
		List<PlaceHoldForm> list = new ArrayList<PlaceHoldForm>();
		try {
			if (check) {
				String strUrl = wsurl + "/csr/holdamount/";
				strUrl = accountno == null ? strUrl + "all" : strUrl + accountno;
				URL url = new URL(strUrl + "/" + type);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setDoOutput(true);
				con.setRequestMethod("GET");
				System.out.println(strUrl);
				System.out.println("Response Code : " + con.getResponseCode());
				if (con.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED) {
					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					ObjectMapper mapper = new ObjectMapper();
					PlaceHoldFormHandler form = new PlaceHoldFormHandler();
					form = mapper.readValue(in.readLine(), PlaceHoldFormHandler.class);
					list = form.getList();
				}
				con.disconnect();
			} else {
				System.out.println("getHoldAmyList : 503");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public AccountGenericForm liftHoldAmt(int id, String liftreason, Date businessdt, String type) {
		AccountGenericForm form = new AccountGenericForm();
		form.setResult(0);
		try {
			if (check) {
				param.put("id", id);
				param.put("liftreason", liftreason);
				param.put("userid", UserUtil.getUserByUsername(service.getUserName()).getUserid());
				param.put("busidt", businessdt);
				param.put("type", type);
				ObjectMapper mapper = new ObjectMapper();
				String jsonData = mapper.writeValueAsString(param);
				System.out.println(jsonData);
				String url = wsurl + "/csr/lift-holdamount";
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
					form = mapper.readValue(in.readLine(), AccountGenericForm.class);
				}
				con.disconnect();
			} else {
				form.setResult(503);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public List<TimeDepositListForm> getTimeDepAcctList(String accountno) {
		List<TimeDepositListForm> list = new ArrayList<TimeDepositListForm>();
		try {
			if (accountno == null) {
				list = (List<TimeDepositListForm>) dbService.execStoredProc(
						"SELECT AccountBalance AS accountbal, AccountName as accountname, AccountNo as accountno, "
								+ "Maturitydate AS matdt, Term AS term FROM TBDEPOSIT "
								+ "WHERE ProductCode='30' AND (PlacementAmt > 0 OR PlacementAmt > AccountBalance)",
						null, TimeDepositListForm.class, 1, null);
			} else {
				param.put("accountno", accountno);
				list = (List<TimeDepositListForm>) dbService.execStoredProc(
						"SELECT AccountBalance AS accountbal, AccountName as accountname, AccountNo as accountno, "
								+ "Maturitydate AS matdt, Term AS term FROM TBDEPOSIT "
								+ "WHERE ProductCode='30' AND AccountNo=:accountno",
						param, TimeDepositListForm.class, 1, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public TimeDepositAccountDetailForm getTimeDepAcctDet(String accountno) {
		TimeDepositAccountDetailForm form = new TimeDepositAccountDetailForm();
		try {
			param.put("accountno", accountno);
			form = (TimeDepositAccountDetailForm) dbService.execStoredProc(""
					+ "SELECT tb.IntRate AS intrate, tb.LessWTaxAmt AS lesswtax, tb.MatAmt AS matamt, tb.BookDate AS opendt, "
					+ "tb.PlacementAmt AS placementamt, cd.desc1 AS status, tb.WTAXRate AS taxrate, br.branchname as branchname "
					+ "FROM TBDEPOSIT tb JOIN TBCODETABLECASA cd ON cd.codevalue=CONVERT(VARCHAR(1), tb.AccountStatus) "
					+ "JOIN TBBRANCH br ON br.branchcode = tb.unit AND br.coopcode= tb.instcode "
					+ "WHERE AccountNo=:accountno AND cd.codename='CASA-ACCTSTS'  ", param,
					TimeDepositAccountDetailForm.class, 0, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public List<Tbdeposit> getTimeDepMatAcctList(String accountno) {
		List<Tbdeposit> list = new ArrayList<Tbdeposit>();
		try {
			if (check) {
				String strUrl = wsurl + "/csr/matured-account/";
				strUrl = accountno == null ? strUrl + "all" : strUrl + accountno;
				URL url = new URL(strUrl);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setDoOutput(true);
				con.setRequestMethod("GET");
				System.out.println("Response Code : " + con.getResponseCode());
				if (con.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED) {
					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					ObjectMapper mapper = new ObjectMapper();
					MaturedAccountHandler form = new MaturedAccountHandler();
					form = mapper.readValue(in.readLine(), MaturedAccountHandler.class);
					list = form.getMaturedlist();
				}
				con.disconnect();
			} else {
				System.out.println("getTimeDepMatAcctList : 503");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public AccountGenericForm submitMatAcctAction(MaturedAccountActionForm form) {
		return null;
	}

	@Override
	public AccountGenericForm placeHoldCheck(Tbholdamtcheck record) {
		/*
		 * flag return values 0 = account not found/exist 1 = success 2 = error in
		 * routine
		 */
		AccountGenericForm form = new AccountGenericForm();
		try {
			if (check) {
				URL url = new URL(wsurl + "/csr/place-spo");
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setDoOutput(true);
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Type", "application/json");
				ObjectMapper mapper = new ObjectMapper();
				String jsonData = mapper.writeValueAsString(record);
				OutputStream os = con.getOutputStream();
				os.write(jsonData.getBytes());
				os.flush();
				if (con.getResponseCode() != HttpURLConnection.HTTP_ACCEPTED) {
					form.setResult(3);
				} else {
					BufferedReader in = new BufferedReader(new InputStreamReader((con.getInputStream())));
					form = mapper.readValue(in.readLine(), AccountGenericForm.class);
				}
				con.disconnect();
			} else {
				form.setResult(503);
			}
		} catch (Exception e) {
			e.printStackTrace();
			form.setResult(3);
		}
		return form;
	}

	@Override
	public List<StopPaymentOrderForm> spoList(String accountno) {
		List<StopPaymentOrderForm> list = null;
		try {
			if (check) {
				String strUrl = wsurl + "/csr/spo-list/";
				strUrl = accountno == null ? strUrl + "all" : strUrl + accountno;
				URL url = new URL(strUrl);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setDoOutput(true);
				con.setRequestMethod("GET");
				System.out.println("Response Code : " + con.getResponseCode());
				if (con.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED) {
					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					ObjectMapper mapper = new ObjectMapper();
					StopPaymentOrderHandler form = new StopPaymentOrderHandler();
					form = mapper.readValue(in.readLine(), StopPaymentOrderHandler.class);
					list = form.getList();
				}
				con.disconnect();
			} else {
				System.out.println("spoList : 503");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public AccountGenericForm liftSPO(int id, String liftreason, Date businessdt) {
		AccountGenericForm form = new AccountGenericForm();
		form.setResult(0);
		try {
			if (check) {
				param.put("id", id);
				param.put("liftreason", liftreason);
				param.put("userid", UserUtil.getUserByUsername(service.getUserName()).getUserid());
				param.put("busidt", businessdt);
				ObjectMapper mapper = new ObjectMapper();
				String jsonData = mapper.writeValueAsString(param);
				String url = wsurl + "/csr/lift-spo";
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
					form = mapper.readValue(in.readLine(), AccountGenericForm.class);
					// result = in.readLine().equalsIgnoreCase("success") ? "1" : "0";
				}
				con.disconnect();
			} else {
				form.setResult(503);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public String createNewProduct(Tbprodmatrix input) {
		/***
		 * RESULT 0 = Error 1 = Already Existing 2 = Success
		 ***/
		String result = "0";
		try {
			param.put("prodgroup", input.getProdgroup());
			param.put("prodcode", input.getProdcode());
			param.put("prodname", input.getProdname());
			param.put("prodsname", input.getProdsname());
			if ((Integer) dbService.execStoredProc("SELECT COUNT(*) FROM TBPRODMATRIX WHERE "
					+ "prodgroup=:prodgroup AND prodcode=:prodcode AND prodname=:prodname AND "
					+ "prodsname=:prodsname ", param, null, 0, null) > 0) {
				result = "1";
			} else {
				input.setCreatedby(UserUtil.getUserByUsername(service.getUserName()).getUserid());
				input.setCreateddate(new Date());
				if ((Integer) dbService.execStoredProc(null, param, null, 3, input) > 0) {
					result = "2";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public UserInfoForm checkMemberNo(String memberno) {
		UserInfoForm form = new UserInfoForm();
		DBService dbsrvcCIF = new DBServiceImplCIF();
		try {
			param.put("memberno", memberno);
			param.put("coopcode", UserUtil.getUserByUsername(service.getUserName()).getCoopcode());
			form = (UserInfoForm) dbsrvcCIF.execStoredProc(
					// "SELECT main.membername AS name FROM TBMEMBER main WHERE
					// membershipid=:memberno AND coopcode=:coopcode", param,
					"SELECT 1 as result, main.cifno, main.fullname AS name, indiv.dateofbirth as dateofbirth FROM TBCIFMAIN main "
							+ "LEFT JOIN TBCIFINDIVIDUAL indiv on main.cifno = indiv.cifno WHERE main.cifno=:memberno",
					param, UserInfoForm.class, 0, null);
			if (form == null) {
				form = new UserInfoForm();
				form.setName("");
				form.setResult(0);
				form.setCifno(null);
			}
//			if (name == null || name.equals(null)) {
//				form.setName("");
//				form.setResult(0);
//				form.setCifno(null);
//			} else {
//				form.setName(name);
//				form.setCifno(memberno);
//				form.setResult(1);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public List<InquiryCIFNameList> checkMemberNoName(String name, String custtype) {
		List<InquiryCIFNameList> list = null;
		DBService dbsrvcCIF = new DBServiceImplCIF();
		try {
			param.put("name", "%" + name + "%");
			param.put("custtype", custtype);
			param.put("coopcode", UserUtil.getUserByUsername(service.getUserName()).getCoopcode());
			list = (List<InquiryCIFNameList>) dbsrvcCIF.execStoredProc(
					/*
					 * "SELECT main.membername AS name, membershipid as cifno, '1' AS custtype FROM TBMEMBER main WHERE main.membername LIKE :name AND main.membershipstatus='1' and main.coopcode=:coopcode"
					 * , param, InquiryCIFNameList.class, 1, null);
					 */
					"SELECT main.fullname AS name, main.cifno,customertype AS custtype, "
							+ "indiv.dateofbirth as dob, motherlastname + ', ' + motherfirstname + ' ' + mothermiddlename as maidenname, stat.desc1 as cifstatus "
							+ "FROM TBCIFMAIN main left join Tbcifindividual indiv on main.cifno = indiv.cifno "
							+ "left join Tbcodetable stat on main.cifstatus = stat.codevalue "
							+ "WHERE stat.codename='cifstatus' and main.fullname LIKE :name AND main.cifstatus IN ('1','2','3')",
					param, InquiryCIFNameList.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Tbprodmatrix getProductDetail(String prodcode, String subprodcode) {
		try {
			param.put("prodcode", prodcode);
			param.put("subprodcode", subprodcode);
			return (Tbprodmatrix) dbService.execStoredProc(
					"SELECT * FROM TBPRODMATRIX WHERE prodgroup=:prodcode AND prodcode=:subprodcode", param,
					Tbprodmatrix.class, 0, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer acctAlertOff(String accountno) {
		try {
			if (check) {
				ObjectMapper mapper = new ObjectMapper();
				String jsonData = mapper.writeValueAsString(accountno);
				String url = wsurl + "/util/acctalert/off";
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
					return mapper.readValue(in.readLine(), Integer.class);
					// result = in.readLine().equalsIgnoreCase("success") ? "1" : "0";
				}
				con.disconnect();
			} else {
				return 503;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getControlno(String accountno) {
		try {
			if (check) {
				String url = wsurl + "/csr/tdc_print_detail/" + accountno;
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");
				System.out.println("Response Code : " + con.getResponseCode());
				if (con.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED) {
					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					ObjectMapper mapper = new ObjectMapper();
					String result = mapper.readValue(in.readLine(), String.class);
					return result;
				}
				con.disconnect();
			} else {
				return "503";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<TimeDepositCertForm> getTDCList(String accountno) {
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("branchcode", UserUtil.getUserByUsername(UserUtil.securityService.getUserName()).getBranchcode());
		try {
//			if (check) {
//				String strUrl = wsurl + "/csr/tdc_list/";
//				strUrl = accountno == null ? strUrl + "all" : strUrl + accountno;
//				URL url = new URL(strUrl);
//				HttpURLConnection con = (HttpURLConnection) url.openConnection();
//				con.setRequestMethod("GET");
//				System.out.println("Response Code : " + con.getResponseCode());
//				if (con.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED) {
//					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//					ObjectMapper mapper = new ObjectMapper();
//					TimeDepositCertFormHandler result = mapper.readValue(in.readLine(),
//							TimeDepositCertFormHandler.class);
//					System.out.println("TDC List count : " + result.getList().size());
//					return result.getList();
//				}
//				con.disconnect();
//			}
			String qry = "SELECT dep.accountno, dep.AccountName AS name, acct.desc1 as accountstatus, dep.BookDate AS datebook, "
					+ "dep.MaturityDate AS matdt,unt.branchname AS branch, dep.AccountBalance AS accountbal, dep.term, "
					+ "dep.intrate, dep.WTAXRate AS taxrate, dep.LessWTaxAmt AS lesswtax, dep.matamt, dep.placementamt "
					+ "FROM TBDEPOSIT dep LEFT JOIN TBBRANCH unt ON dep.unit=unt.branchcode "
					+ "LEFT JOIN TBCODETABLE cd ON dep.AccountStatus=cd.codevalue "
					+ "LEFT JOIN TBCODETABLE acct on dep.AccountStatus=acct.codevalue "
					+ "LEFT JOIN TBDEPDETAIL detail on dep.AccountNo = detail.accountno "
					+ "WHERE cd.codename='CASA-ACCTSTS' AND dep.AccountStatus=1 AND dep.tdcreleaseind=0 AND dep.ProductCode='40' "
					+ "AND acct.codename='CASA-ACCTSTS' AND dep.unit =:branchcode and detail.indctd = 1 ";
			if (accountno != null && accountno.trim().length() > 0) {
				param.put("acctno", accountno);
				qry += "AND dep.AccountNo=:acctno ";
			}
			return dbsrvc.executeListSQLQueryWithClass(qry, param, TimeDepositCertForm.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String freezeAccount(Tbfreezeaccount data) {
		String result = "failed";
		try {
			if (data.getId() == null) {
				data.setCreatedby(UserUtil.getUserByUsername(service.getUserName()).getUserid());
				data.setDatecreated(new Date());
			} else {
				data.setTxstatus("2");
				data.setUpdatedby(UserUtil.getUserByUsername(service.getUserName()).getUserid());
				data.setDateupdated(new Date());
			}
			data.setTxstatusdate(new Date());
			data.setBranchcode(UserUtil.getUserByUsername(service.getUserName()).getBranchcode());
			if (dbService.saveOrUpdate(data)) {
				param.put("acctno", data.getAccountno());
				Tbdeposit dep = (Tbdeposit) dbService.executeUniqueHQLQuery("FROM Tbdeposit WHERE accountno =:acctno",
						param);
				if (data.getTxstatus().equals("1")) {
					// FREEZE ACCOUNT
					Tbfreezeaccount acct = getFreezeInfo(data.getAccountno());
					dep.setFreezeexpdt(data.getExpirydate());
					dep.setFreezeid(acct.getId());
					dep.setFreezeind(1);
				} else {
					// UNFREEZE ACCOUNT
					dep.setFreezeexpdt(null);
					dep.setFreezeid(null);
					dep.setFreezeind(0);
				}
				// ADD DLT CED 04252023
				BranchService branchService = new BranchServiceImpl();
				Tbbranch branch = branchService.getBranchDetails(
						UserUtil.getUserByUsername(UserUtil.securityService.getUserName()).getBranchcode());
				dep.setLasttxdate(branch.getCurrentbusinessdate());
				dbService.saveOrUpdate(dep);
			}
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Tbfreezeaccount getFreezeInfo(String accountno) {
		Tbfreezeaccount result = null;
		DBService dbservice = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("accountno", accountno);
		try {
			result = (Tbfreezeaccount) dbservice.executeUniqueHQLQuery(
					"FROM Tbfreezeaccount WHERE accountno =:accountno AND txstatus = '1'", param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String liftFreeze(String accountno) {
		String result = "";
		try {
			if (check) {
				String url = wsurl + "/csr/freeze-lift/" + accountno;
				param.put("now", DateTimeUtil.convertDateToString(new Date(), DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
				param.put("userid", UserUtil.getUserByUsername(service.getUserName()).getUserid());
				param.put("acctno", accountno);
				param.put("id",
						(Integer) dbService.execStoredProc(
								"SELECT id FROM TBFREEZEACCOUNT "
										+ "WHERE id = (SELECT freezeid FROM TBDEPOSIT WHERE accountno=:acctno)",
								param, null, 0, null));
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("PUT");
				System.out.println("Response Code : " + con.getResponseCode());
				if (con.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED) {
					// BufferedReader in = new BufferedReader(new
					// InputStreamReader(con.getInputStream()));
					// ObjectMapper mapper = new ObjectMapper();
					result = "1";
//					System.out.println("EZ " + param);
					dbService.execStoredProc("UPDATE TBFREEZEACCOUNT SET liftdate=:now, liftby=:userid WHERE id=:id",
							param, null, 2, null);
				} else {
					result = "0";
				}
				con.disconnect();
			} else {
				result = "503";
				System.out.println("Freeze Info : 503 >> Host unavailable! ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public CheckDeceasedForm checkDeceased(String memberno) {
		CheckDeceasedForm form = new CheckDeceasedForm();
//		List<UserInfoForm> form1 = new ArrayList<UserInfoForm>();
		DBService dbsrvcCIF = new DBServiceImplCIF();

		try {
			param.put("memberno", memberno);
			System.out.println("memberno: " + memberno);
			form = (CheckDeceasedForm) dbsrvcCIF.execStoredProc(
					// "SELECT CAST(0 as bit) as deceasedflag FROM TBMEMBER main WHERE
					// membershipid=:memberno",
					"SELECT ISNULL(deceasedflag,0) as deceasedflag FROM TBCIFMAIN main WHERE cifno=:memberno", param,
					CheckDeceasedForm.class, 0, null);
			// System.out.println("a:" + form.isDeceasedflag());
//			form.setDeceasedflag(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public int checkMishandled(String memberno) {
//		CheckDeceasedForm form = new CheckDeceasedForm();
//		List<UserInfoForm> form1 = new ArrayList<UserInfoForm>();
		int res = 0;
		try {
			param.put("memberno", memberno);
			System.out.println("memberno: " + memberno);
			res = (Integer) dbService.executeUniqueSQLQuery(
					"select count(*) from TBDEPOSITCIF a left join TBDEPOSIT b on a.accountno = b.accountno where cifno =:memberno "
							+ "and b.WatchlistCode ='03'",
					param);
			System.out.println(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public List<Tbchecksforclearing> getFloatItems(String acctno) {
		List<Tbchecksforclearing> list = new ArrayList<Tbchecksforclearing>();
		try {
			param.put("acctno", acctno);
			list = (List<Tbchecksforclearing>) dbService.execStoredProc(
					"SELECT * FROM Tbchecksforclearing WHERE accountnumber=:acctno AND status ='1'", param,
					Tbchecksforclearing.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Tbdeposit> searchDeposit(String acctno, String name) {
		List<Tbdeposit> list = new ArrayList<Tbdeposit>();
		try {
			if (acctno != null) {
				param.put("acctno", acctno);
				list = (List<Tbdeposit>) dbService.executeListHQLQuery("FROM Tbdeposit WHERE accountno=:acctno", param);

			} else {
				param.put("name", "%" + name + "%");
				list = (List<Tbdeposit>) dbService.executeListHQLQuery("FROM Tbdeposit WHERE accountname like :name",
						param);
			}
			for (Tbdeposit dep : list) {
				param.put("prodgroup", dep.getProductCode());
				param.put("prodcode", dep.getSubProductCode());
				Tbprodmatrix prod = (Tbprodmatrix) dbService.executeUniqueHQLQuery(
						"FROM Tbprodmatrix WHERE prodgroup=:prodgroup AND prodcode=:prodcode", param);
				dep.setProductCode(prod.getProdname());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String addPlaceHoldAmt(Tbholdamtcheck hold) {
		String flag = "failed";
		try {
			param.put("acctno", hold.getAccountno());
			if (dbService.save(hold)) {
				flag = "success";
				Tbdeposit dep = (Tbdeposit) dbService.executeUniqueHQLQuery("FROM Tbdeposit WHERE accountno=:acctno",
						param);
				if (dep != null) {
					dep.setPlaceholdAmt(hold.getAmount());
					dbService.saveOrUpdate(dep);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<Tbholdamtcheck> getListHoldAmt(String accountno) {
		List<Tbholdamtcheck> list = new ArrayList<Tbholdamtcheck>();
		try {
			param.put("acctno", accountno == null ? "%%" : accountno);
			list = (List<Tbholdamtcheck>) dbService
					.executeListHQLQuery("FROM Tbholdamtcheck WHERE status ='1' AND accountno LIKE :acctno", param);

			for (Tbholdamtcheck chck : list) {
				param.put("holdreason", chck.getHoldreason());
				Tbcodetablecasa code = (Tbcodetablecasa) dbService.executeUniqueHQLQuery(
						"FROM Tbcodetablecasa WHERE codename='HOLDREASON' AND codevalue=:holdreason", param);
				chck.setHoldreason(code.getDesc1());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String updateLiftHoldAmt(Tbholdamtcheck hold) {
		String flag = "failed";
		try {
			param.put("id", hold.getId());
			System.out.println("id: " + hold.getId());
			Tbholdamtcheck chck = (Tbholdamtcheck) dbService.executeUniqueHQLQuery("FROM Tbholdamtcheck WHERE id=:id",
					param);

			if (chck != null) {
				chck.setStatus(2);
				chck.setReleaseby(service.getUserName());
				chck.setReleasereason(hold.getReleasereason());
				chck.setTxcode("113322");
				chck.setDateupdated(new Date());
				chck.setDatereleased(new Date());
				if (dbService.saveOrUpdate(chck)) {
					param.put("acctno", chck.getAccountno());
					Tbdeposit dep = (Tbdeposit) dbService
							.executeUniqueHQLQuery("FROM Tbdeposit WHERE accountno=:acctno", param);
					dep.setPlaceholdAmt(BigDecimal.ZERO);
					dbService.saveOrUpdate(dep);
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String saveTDDetails(Tbtimedeposit td) {
		String flag = "failed";
		try {
			if (dbService.saveOrUpdate(td)) {
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String pretermTDAccount(Tbtimedeposit td, String modeOfPayment, String creditAcctNo) {
		String result = "failed";
		Tbdeposit acct = new Tbdeposit();
		DBService dbService = new DBServiceImpl();
		FinTxService finTxSrvc = new FinTxServiceImpl();
		param.put("acctno", td.getAccountno());
		param.put("branchcode", UserUtil.getUserByUsername(UserUtil.securityService.getUserName()));
		Tbbranch branch = (Tbbranch) dbService.executeUniqueHQLQuery("FROM Tbbranch WHERE branchcode =:branchcode",
				param);
		try {
			acct = (Tbdeposit) dbService.executeUniqueHQLQuery("FROM Tbdeposit WHERE accountno =:acctno", param);
			if (acct == null) {
				result = "Account not found!";
			} else {
				if (modeOfPayment.equals("3")) {
					Tbfintxjrnl form = new Tbfintxjrnl();
					form.setTxcode("112013");
					form.setTxvaldt(td.getDtmat());
					form.setTxdate(new Date());
					form.setAccountno(creditAcctNo);
					form.setTxamount(td.getMatvalue());
					form.setReasoncode("101");
					form.setCurrency("PHP");
					form.setRemarks("Time Deposit Pre-termination.");
					form.setTxby(service.getUserName());
					if (!finTxSrvc.cashDepWithDrCrMemo(form, null).getResult().equals("1")) {
						System.out.println(form.getAccountno() + " Crediting unsuccessfull.");
						result = "Unable to credit to account";
					} else {
						acct.setAccountBalance(BigDecimal.ZERO);
						acct.setAccountStatus(5);
						if (branch != null) {
							acct.setStatusDate(branch.getCurrentbusinessdate());
						}
						dbService.saveOrUpdate(acct);
						result = "success";
					}
				} else {
					acct.setAccountBalance(BigDecimal.ZERO);
					acct.setAccountStatus(5);
					if (branch != null) {
						acct.setStatusDate(branch.getCurrentbusinessdate());
					}
					dbService.saveOrUpdate(acct);
					result = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Tbtimedeposit> listMaturedAccounts(String accountno) {
		List<Tbtimedeposit> tdList = new ArrayList<Tbtimedeposit>();
		param.put("acctno", accountno);
		String q = "select a.* from TBTIMEDEPOSIT a left join TBDEPOSIT b on a.accountno = b.AccountNo "
				+ "where b.AccountStatus=4 and b.ProductCode ='40' "
						.concat(accountno != null && !accountno.equals("") ? "and a.accountno =:acctno" : "");
		try {
			tdList = (List<Tbtimedeposit>) dbService.executeListSQLQueryWithClass(q, param, Tbtimedeposit.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tdList;
	}

	@Override
	public List<AccountMaintenanceForm> listAccountsByProduct(String acctno, String name, String prodcode) {
		List<AccountMaintenanceForm> form = new ArrayList<AccountMaintenanceForm>();
		String q = "";
		if (acctno != null && acctno.trim() != "") {
			param.put("acctno", acctno);
			param.put("coopcode", name);
			q = "SELECT accountno,accountname as name FROM Tbdeposit WHERE accountno =:acctno and instcode=:coopcode";
		} else {
			param.put("name", "%" + cleanTextContent(name) + "%");
			param.put("prodcode", prodcode);
			q = "SELECT accountno,accountname as name FROM Tbdeposit WHERE accountname like :name and productcode =:prodcode";
		}
		try {
			form = (List<AccountMaintenanceForm>) dbService.execStoredProc(q, param, AccountMaintenanceForm.class, 1,
					null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	private static String cleanTextContent(String text) {
		// strips off all non-ASCII characters
		text = text.replaceAll("[^\\x00-\\x7F]", "");

		// erases all the ASCII control characters
		text = text.replaceAll("[\\p{Cntrl}&&[^\t]]", "");

		// removes non-printable characters from Unicode
		text = text.replaceAll("\\p{C}", "");

		return text.trim();
	}

	@Override
	public String saveOrupdateExternalAcct(Tbotheraccounts ext) {
		DBService dbService = new DBServiceImplCIF();
		String flag = "failed";
		Map<String, Object> param = HQLUtil.getMap();

		try {

			if (ext.getId() == null) {
				ext.setDateupdated(new Date());
				ext.setUpdatedby(UserUtil.securityService.getUserName());
				if (dbService.save(ext)) {
					flag = "success";
				}
			} else {
				param.put("id", ext.getId());
				Tbotheraccounts acct = (Tbotheraccounts) dbService
						.executeUniqueHQLQuery("FROM Tbotheraccounts WHERE id=:id", param);

				if (acct != null) {

					/** Deposits Info **/
					acct.setCifno(ext.getCifno());
					acct.setBankaccttype(ext.getBankaccttype());
					acct.setBank(ext.getBank());
					acct.setBranch(ext.getBranch());
					acct.setAccountname(ext.getAccountname());
					acct.setAccountnumber(ext.getAccountnumber());
					acct.setDateopened(ext.getDateopened());
					acct.setAdb(ext.getAdb());
					acct.setOutstandingbal(ext.getOutstandingbal());
					acct.setDateupdated(new Date());
					acct.setUpdatedby(UserUtil.securityService.getUserName());
					acct.setAccounttype(ext.getAccounttype()); /** Deposit or Loan or Investment **/

					/** Loan Account Info **/
					acct.setLoantype(ext.getLoantype());
					// setBank
					// setBranch
					acct.setPncnno(ext.getPncnno());
					acct.setValuedate(ext.getValuedate());
					acct.setMaturitydate(ext.getMaturitydate());
					// setOutstandingbal
					// setAdb
					// setDateupdated
					// setUpdatedby
					// setAccounttype

					// 10-16-17 pongyu
					acct.setCurrency(ext.getCurrency());

					/** Investment Account Info **/
					acct.setInvestmenttype(ext.getInvestmenttype());
					// setBank
					// setBranch
					acct.setInvestmentrefno(ext.getInvestmentrefno());
					// setValuedate
					// setMaturitydate
					// setOutstandingbal
					// setDateupdated
					// setUpdatedby
					// setAccounttype

					if (dbService.saveOrUpdate(acct)) {
						flag = "success";
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String deleteExternalAcct(int id) {
		String flag = "failed";
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("id", id);

		try {

			Tbotheraccounts row = (Tbotheraccounts) dbService.executeUniqueHQLQuery("FROM Tbotheraccounts WHERE id=:id",
					param);
			if (dbService.delete(row)) {
				flag = "success";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<Tbotheraccounts> externalList(String cifno,
			String accttype) { /** accttype = Deposit or Loan or Investment **/
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();
		List<Tbotheraccounts> list = new ArrayList<Tbotheraccounts>();

		try {

			param.put("cifno", cifno);
			param.put("type", accttype);

			list = (List<Tbotheraccounts>) dbService
					.executeListHQLQuery("FROM Tbotheraccounts WHERE cifno=:cifno AND accounttype=:type", param);

			// Formatter's
			if (list != null) {
				for (Tbotheraccounts other : list) {
					if (other.getBankaccttype() != null) {
						param.put("BANKACCTTYPE", other.getBankaccttype());
						Tbcodetable codeBANKACCTTYPE = (Tbcodetable) dbService.executeUniqueHQLQuery(
								"FROM Tbcodetable a WHERE a.id.codename ='BANKACCTTYPE' AND a.id.codevalue =:BANKACCTTYPE",
								param);
						if (codeBANKACCTTYPE != null) {
							other.setBankaccttype(codeBANKACCTTYPE.getDesc1());
						}
					}
					if (other.getBank() != null) {
						param.put("BANKFI", other.getBank());
						Tbcodetable codeBANKFI = (Tbcodetable) dbService.executeUniqueHQLQuery(
								"FROM Tbcodetable a WHERE a.id.codename ='BANKFI' AND a.id.codevalue =:BANKFI", param);
						if (codeBANKFI != null) {
							other.setBank(codeBANKFI.getDesc1());
						}
					}
					if (other.getLoantype() != null) {
						param.put("LOANTYPE", other.getLoantype());
						Tbcodetable codeLOANTYPE = (Tbcodetable) dbService.executeUniqueHQLQuery(
								"FROM Tbcodetable a WHERE a.id.codename ='LOANTYPE' AND a.id.codevalue =:LOANTYPE",
								param);
						if (codeLOANTYPE != null) {
							other.setLoantype(codeLOANTYPE.getDesc1());
						}
					}
					if (other.getInvestmenttype() != null) {
						param.put("INVESTMENTTYPE", other.getInvestmenttype());
						Tbcodetable codeINVESTMENTTYPE = (Tbcodetable) dbService.executeUniqueHQLQuery(
								"FROM Tbcodetable a WHERE a.id.codename ='INVESTMENTTYPE' AND a.id.codevalue =:INVESTMENTTYPE",
								param);
						if (codeINVESTMENTTYPE != null) {
							other.setInvestmenttype(codeINVESTMENTTYPE.getDesc1());
						}
					}
					other.setUpdatedby(UserUtil.getUserFullname(other.getUpdatedby()));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Tbotheraccounts getExtAcctbyID(String cifno, Integer id) {
		DBService dbservice = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();
		Tbotheraccounts acct = new Tbotheraccounts();
		try {
			if (cifno != null && id != null) {
				// System.out.println(">>>>>>>>>>>>>>>>> CIFNO" + cifno);
				// System.out.println(">>>>>>>>>>>>>>>>> ID" + id);
				param.put("cifno", cifno);
				param.put("id", id);
				acct = (Tbotheraccounts) dbservice
						.executeUniqueHQLQuery("FROM Tbotheraccounts WHERE cifno=:cifno AND id=:id", param);
				if (acct != null) {
					return acct;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return acct;
	}

	@Override
	public BigDecimal sumExtOutstandingBalance(String cifno, String accttype) {
		BigDecimal total = BigDecimal.ZERO;
		DBService dbService = new DBServiceImplCIF();
		HashMap<String, Object> param = new HashMap<String, Object>();

		try {
			param.put("cifno", cifno);
			param.put("accttype", accttype);

			Object ob = (Object) dbService.executeUniqueSQLQuery(
					"SELECT SUM(IsNull(outstandingbal,0)) as value FROM Tbotheraccounts WHERE cifno=:cifno AND accounttype=:accttype",
					param);
			if (ob != null) {
				BigDecimal value = (BigDecimal) ob;
				total = value;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	/**
	 * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> END OF EXTERNAL ACCOUNT
	 * <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	 **/

	// 12-29-2020
	@Override
	public CIFWatchlistMainForm listCIFWatchlist(String acctno) {
		List<CIFWatchlistForm> list = new ArrayList<CIFWatchlistForm>();
		CIFWatchlistMainForm form = new CIFWatchlistMainForm();
		DBService dbServiceCIF = new DBServiceImplCIF();
		DBService dbServiceCASA = new DBServiceImpl();
		HashMap<String, Object> param = new HashMap<String, Object>();

		Boolean amla = false;
		Boolean blacklist = false;
		Boolean dosri = false;
		Boolean fatca = false;
		Boolean pep = false;
		try {
			if (acctno != null) {
				param.put("accountno", acctno);
				List<Tbdepositcif> dep = (List<Tbdepositcif>) dbServiceCASA
						.executeListHQLQuery("FROM Tbdepositcif WHERE accountno=:accountno", param);
				CIFWatchlistForm n = new CIFWatchlistForm();
				if (dep != null) {
					for (Tbdepositcif d : dep) {
						n.setCifno(d.getCifno());
						n.setCifname(d.getCifname());

						param.put("cifno", d.getCifno());
						Tbamlalistmain amlaL = (Tbamlalistmain) dbServiceCIF
								.executeUniqueHQLQuery("FROM Tbamlalistmain WHERE cifno=:cifno", param);
						if (amlaL != null) {
							n.setAmla(true);
							amla = true;
						} else {
							n.setAmla(false);
						}
						Tbblacklistmain blkL = (Tbblacklistmain) dbServiceCIF
								.executeUniqueHQLQuery("FROM Tbblacklistmain WHERE cifno=:cifno", param);
						if (blkL != null) {
							n.setBlacklist(true);
							blacklist = true;
						} else {
							n.setBlacklist(false);
						}
						Tbdosri dosriL = (Tbdosri) dbServiceCIF.executeUniqueHQLQuery("FROM Tbdosri WHERE cifno=:cifno",
								param);
						if (dosriL != null) {
							n.setDosri(true);
							dosri = true;
						} else {
							n.setDosri(false);
						}
						Tbfatca fatcaL = (Tbfatca) dbServiceCIF.executeUniqueHQLQuery("FROM Tbfatca WHERE cifno=:cifno",
								param);
						if (fatcaL != null) {
							n.setFatca(true);
							fatca = true;
						} else {
							n.setFatca(false);
						}
						Tbpepinfo pepL = (Tbpepinfo) dbServiceCIF
								.executeUniqueHQLQuery("FROM Tbpepinfo WHERE cifno=:cifno", param);
						if (pepL != null) {
							n.setPep(true);
							pep = true;
						} else {
							n.setPep(false);
						}
					}
				}
				String a = "";
				if (amla == true) {
					a = "AMLA";
				}
				if (blacklist == true) {
					if (amla == true) {
						a = a + ",";
					}
					a = a + "BLACKLIST";
				}
				if (dosri == true) {
					if (amla == true || blacklist == true) {
						a = a + ",";
					}
					a = a + "DOSRI";
				}
				if (fatca == true) {
					if (amla == true || blacklist == true || dosri == true) {
						a = a + ",";
					}
					a = a + "FATCA";
				}
				if (pep == true) {
					if (amla == true || blacklist == true || dosri == true || fatca == true) {
						a = a + ",";
					}
					a = a + "PEP";
				}
				list.add(n);
				form.setList(list);
				form.setView(a);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public AccountGenericForm createAccountWithCIF(Tbdeposit dep, List<Tbdepositcif> ciflist,
			List<QDEParameterForm> list, String ciftype, String cifstatus, boolean isencoding) {
		AccountGenericForm result = new AccountGenericForm();
		QDEService qdeSrvc = new QDEServiceImpl();
		DBService dbservice = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (list != null && list.size() > 0) {
				for (QDEParameterForm form : list) {
					form.setCifno(CIFNoGenerator
							.generateCIFNo(form.getCustomertype().equals("1") ? "INDIVIDUAL" : "CORPORATE"));
					if (qdeSrvc.setupApprovedCIF(form, ciftype, cifstatus, isencoding).equals("success")) {
						param.put("cifno", form.getCifno());
						Tbdepositcif cif = new Tbdepositcif();
						cif.setCifname((String) dbservice
								.executeUniqueSQLQuery("SELECT fullname FROM Tbcifmain WHERE cifno=:cifno", param));
						cif.setCifno(form.getCifno());
						ciflist.add(cif);
					}
				}
			}
			result = createAccount(dep, ciflist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String updateSigcard(String accountno, String filename) {
		String result = "failed";
		DBService dbservice = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("accountno", accountno);
		Tbfilehistory hist = new Tbfilehistory();
		try {
			Tbdeposit dep = (Tbdeposit) dbservice.executeUniqueHQLQuery("FROM Tbdeposit WHERE accountNo =:accountno",
					param);
			hist.setFilecode(dep.getSigbasecode());
			hist.setFilename(dep.getSigfilename());
			String path = RuntimeAccess.getInstance().getSession().getServletContext()
					.getRealPath("resources/tempdir/" + filename);
			dep.setSigbasecode(ImageUtils.imageToBase64(path));// CED SIGCARD
			filename = filename != null ? filename.replace(" ", "") : "";
			dep.setSigfilename(filename.substring(0, filename.lastIndexOf(".")));
			if (dbservice.saveOrUpdate(dep)) {
				hist.setAccountnumber(dep.getAccountNo());
				hist.setCreatedby(UserUtil.getUserByUsername(service.getUserName()).getUserid());
				hist.setDatecreated(new Date());
				hist.setBranchcode(UserUtil.getUserByUsername(service.getUserName()).getBranchcode());
				dbservice.save(hist);
				result = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String lockUnlockAmount(Tblockamount lockamount) {
		String result = "failed";
		DBService dbservice = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("accountno", lockamount.getAccountno());
		param.put("txamt", lockamount.getTxamount());
		param.put("branchcode", UserUtil.getUserByUsername(service.getUserName()).getBranchcode());
		try {
			Date businessdate = (Date) dbservice.executeUniqueSQLQuery(
					"SELECT currentbusinessdate FROM Tbbranch where branchcode =:branchcode", param);
			param.put("businessdate", businessdate);
			// VALIDATE BALANCE
			if (lockamount.getId() == null) {
				String qry = lockamount.getTypeoflock().equals("3")
						? "select accountbalance - (earmarkbal + garnishedbal) from Tbdeposit where accountno =:accountno"
						: "select accountbalance - (floatAmount + placeholdAmt + earmarkbal + garnishedbal) from Tbdeposit where accountno =:accountno";
				if (lockamount.getTxamount().compareTo((BigDecimal) dbservice.getSQLAmount(qry, param)) == 1) {
					return "Insufficient available balance!";
				}
				lockamount.setTxrefno((String) dbService.executeUniqueSQLQuery(
						"DECLARE @txrefno VARCHAR(20) EXEC SEQGENERATE @txrefno OUTPUT SELECT @txrefno", param));
				lockamount.setCreatedby(UserUtil.getUserByUsername(service.getUserName()).getUserid());
				lockamount.setDatecreated(new Date());
			} else {
				lockamount.setUpdatedby(UserUtil.getUserByUsername(service.getUserName()).getUserid());
				lockamount.setDateupdated(new Date());
			}
			// LOCK AMOUNT
			if (lockamount.getTxstatus().equals("1")) {
				if (lockamount.getEffectivitydate().compareTo(businessdate) <= 0) {
					if (lockamount.getTypeoflock().equals("1")) {
						dbservice.executeUpdate(
								"UPDATE Tbdeposit SET placeholdAmt = placeholdAmt + :txamt,lasttxdate=:businessdate WHERE accountno =:accountno",
								param);
					} else if (lockamount.getTypeoflock().equals("2")) {
						dbservice.executeUpdate(
								"UPDATE Tbdeposit SET earmarkbal = earmarkbal + :txamt,lasttxdate=:businessdate WHERE accountno =:accountno",
								param);
					} else if (lockamount.getTypeoflock().equals("3")) {
						dbservice.executeUpdate(
								"UPDATE Tbdeposit SET garnishedbal = garnishedbal + :txamt, accountstatus='2', statusdate=getdate(),lasttxdate=:businessdate WHERE accountno =:accountno",
								param);
					} else {
//						dbservice.executeUpdate(
//								"UPDATE Tbdeposit SET garnishedbal = garnishedbal + :txamt,lasttxdate=:businessdate WHERE accountno =:accountno",
//								param);
					}
				} else {
					// NOT WITHIN EFFECTIVITY DATE
					lockamount.setTxstatus("0");
				}
				// UNLOCK AMOUNT
			} else {
				if (lockamount.getTypeoflock().equals("1")) {
					dbservice.executeUpdate(
							"UPDATE Tbdeposit SET placeholdAmt = placeholdAmt - :txamt,lasttxdate=:businessdate WHERE accountno =:accountno",
							param);
				} else if (lockamount.getTypeoflock().equals("2")) {
					dbservice.executeUpdate(
							"UPDATE Tbdeposit SET earmarkbal = earmarkbal - :txamt,lasttxdate=:businessdate WHERE accountno =:accountno",
							param);
				} else if (lockamount.getTypeoflock().equals("3")) {
					dbservice.executeUpdate(
							"UPDATE Tbdeposit SET garnishedbal = garnishedbal - :txamt, accountstatus ='1', statusdate=getdate(),lasttxdate=:businessdate WHERE accountno =:accountno",
							param);
				}
				lockamount.setTxstatus("2");
			}
			lockamount.setTxstatusdate(new Date());
			lockamount.setBranchcode(param.get("branchcode").toString());
			dbservice.saveOrUpdate(lockamount);
			result = "success";
		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Tblockamount> listLockedAmount(String typeoflock, String accountno, BigDecimal amount, String reason,
			Date expirydate, String status) {
		List<Tblockamount> result = new ArrayList<Tblockamount>();
		DBService dbservice = new DBServiceImpl();
		String qry = "FROM Tblockamount WHERE 1=1 ";
		try {
			if (typeoflock != null && !typeoflock.equals("")) {
				param.put("typeoflock", typeoflock);
				qry += "AND typeoflock =:typeoflock ";
			}
			if (accountno != null && !accountno.equals("")) {
				param.put("accountno", accountno);
				qry += "AND accountno=:accountno ";
			}
			if (amount != null) {
				param.put("amount", amount);
				qry += "AND amount =:amount ";
			}
			if (reason != null && !reason.equals("")) {
				param.put("reason", reason);
				qry += "AND reason=:reason ";
			}
			if (expirydate != null && !expirydate.equals("")) {
				param.put("expirydate", expirydate);
				qry += "AND expirydate =:expirydate ";
			}
			if (status != null && !status.equals("")) {
				param.put("status", status);
				qry += "AND txstatus =:status ";
			}
			qry += "order by datecreated";
			System.out.println(qry);
			result = (List<Tblockamount>) dbservice.executeListHQLQuery(qry, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<FileMaintenanceHistory> listFileMaintenanceHistory(String accountno) {
		List<FileMaintenanceHistory> list = new ArrayList<FileMaintenanceHistory>();
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("acctno", accountno);
		String qry = "select accountno, b.desc1 as txname,a.datecreated,effectivitydate,expirydate, "
				+ "c.username as createdby, a.remarks, a.txamount FROM Tblockamount a "
				+ "left join Tbcodetablecasa b on a.typeoflock = b.codevalue "
				+ "left join Tbuser c on a.createdby = c.userid WHERE b.codename ='TYPEOFLOCK' "
				+ "and a.accountno=:acctno union all "
				+ "select accountno, 'Lift ' + b.desc1 as txname,a.dateupdated,effectivitydate,expirydate, "
				+ "c.username as createdby, a.liftremarks, a.txamount FROM Tblockamount a "
				+ "left join Tbcodetablecasa b on a.typeoflock = b.codevalue "
				+ "left join Tbuser c on a.updatedby = c.userid WHERE b.codename ='TYPEOFLOCK' and txstatus='2' "
				+ "and a.accountno=:acctno union all "
				+ "select accountno,'Freeze Account',a.datecreated,effectivitydate,expirydate, "
				+ "c.username,remarks,0 from Tbfreezeaccount a left join Tbuser c on a.createdby = c.userid "
				+ "where accountno =:acctno union all "
				+ "select accountno,'Lift Freeze Account',a.dateupdated,effectivitydate,expirydate, "
				+ "c.username,remarks,0 from Tbfreezeaccount a left join Tbuser c on a.updatedby = c.userid "
				+ "where txstatus='2' and accountno =:acctno "
				+ "union all SELECT accountnumber,'Update Sigcard',a.datecreated,null,null, c.username,'',0 "
				+ "FROM Tbfilehistory a left join Tbuser c on a.createdby = c.userid WHERE accountnumber =:acctno "
				+ "union all SELECT accountno,'Account Profile Maintenance',a.statusdate,null,null, c.username,overridemessage,0 "
				+ "FROM Tboverriderequest a left join Tbuser c on a.requestedby = c.userid WHERE a.accountno =:acctno and a.status='2' and "
				+ "a.overriderule ='ACCOUNT PROFILE MAINTENANCE' "
				+ "union all SELECT accountno, tx.txname, jrnl.txdate, jrnl.txvaldt, null, jrnl.createdby, jrnl.remarks, jrnl.txamount "
				+ "FROM Tbdeptxjrnl jrnl LEFT JOIN Tbtransactioncode tx on jrnl.txcode = tx.txcode "
				+ "WHERE jrnl.txcode IN ('402101','402102','402103') and jrnl.accountno =:acctno order by datecreated";
		try {
			list = (List<FileMaintenanceHistory>) dbsrvc.execStoredProc(qry, param, FileMaintenanceHistory.class, 1,
					null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<LiftGarnishFormData> listLiftGarnish(String accountNo, String acctsStaus) {
		List<LiftGarnishFormData> list = new ArrayList<LiftGarnishFormData>();
		try {
			param.put("accountNo", accountNo);
			param.put("acctsStaus", acctsStaus);

			String myQuery = " SELECT a.accountno, b.AccountName AS accountName, a.txamount, a.effectivitydate, a.expirydate, a.lockreason "
					+ " FROM TBLOCKAMOUNT a " + " LEFT JOIN TBDEPOSIT b on b.AccountNo = a.accountno"
					+ " WHERE a.txstatus = :acctsStaus";
			if (accountNo != null) {
				myQuery += " AND a.accountno = :accountNo";
			}
			list = (List<LiftGarnishFormData>) dbService.execSQLQueryTransformer(myQuery, param,
					LiftGarnishFormData.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
