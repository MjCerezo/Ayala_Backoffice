package com.casa.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.casa.util.forms.BranchInfoForm;
import com.casa.util.forms.DescIdForm;
import com.casa.util.forms.PassBookFrom;
import com.casa.util.forms.ProductMatrixForm;
import com.coopdb.data.Tbbanks;
import com.coopdb.data.Tbbranch;
import com.coopdb.data.Tbbrfintxjrnl;
import com.coopdb.data.Tbcodetablecasa;
import com.coopdb.data.Tbdeposit;
import com.coopdb.data.Tbdocsperproduct;
import com.coopdb.data.Tbdocuments;
import com.coopdb.data.Tbholiday;
import com.coopdb.data.Tbmerchant;
import com.coopdb.data.Tboverridematrix;
import com.coopdb.data.Tbprodmatrix;
import com.coopdb.data.Tbrates;
import com.coopdb.data.Tbterminal;
import com.coopdb.data.Tbtransactioncode;
import com.coopdb.data.Tbuser;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.printer.PrinterUtil;
import com.etel.security.forms.TBRoleForm;
import com.etel.util.HQLUtil;
import com.etel.utils.UserUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

@SuppressWarnings("unchecked")
public class UtilServiceImpl implements UtilService {

	DBService dbService = new DBServiceImpl();
	Map<String, Object> param = HQLUtil.getMap();
	SecurityService service = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");

	@Override
	public List<DescIdForm> getCurrency() {
		// TODO Auto-generated method stub
		List<DescIdForm> list = null;
		try {
			list = (List<DescIdForm>) dbService.execStoredProc(
					"SELECT codevalue AS id, desc1 AS description FROM TBCODETABLECASA WHERE codename='CURR'", null,
					DescIdForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<DescIdForm> getUserListCico() {// Ced 1-15-2021 added group by
		List<DescIdForm> list = new ArrayList<DescIdForm>();
		try {
			String role = "";
			List<TBRoleForm> roles = UserUtil.getUserRolesByUsername(service.getUserName());
			Tbuser user = UserUtil.getUserByUsername(service.getUserName());
			param.put("userid", user.getUserid());
			param.put("branch", user.getBranchcode());
			param.put("coop", user.getCoopcode());
			for (TBRoleForm row : roles) {
				role = row.getRoleid();
				if (role.equals("VAULT_MNGR") || role.equals("BOO") || role.equals("CASHIER")
						|| role.contains("TELLER")) {
					break;
				}
			}
			if (role.contains("TELLER") || role.contains("CASHIER")) {
				list = (List<DescIdForm>) dbService
						.execStoredProc("SELECT userid AS id, usr.username AS description FROM TBUSER "
								+ "usr left join TBUSERROLES roles on usr.username = roles.username WHERE "
								+ "usr.userid <>:userid and "
								+ "(roles.roleid LIKE '%TELLER%' "
								+ "or roles.roleid IN ('BOO')) and usr.userid is not null AND branchcode =:branch "
								+ "AND usr.isactive = 1 and usr.isdisabled = 0 group by userid,usr.username", param,
								DescIdForm.class, 1, null);
			} else if (role.equals("BOO")) {
				list = (List<DescIdForm>) dbService.execStoredProc(
						"SELECT userid AS id, usr.username AS description FROM TBUSER "
								+ "usr left join TBUSERROLES roles on usr.username = roles.username WHERE "
								+ "usr.userid <>:userid "
								+ "and (roles.roleid IN ('VAULT_MNGR','BOO'"
								+ ") or roles.roleid LIKE '%TELLER%') and usr.userid is not null "
								+ "AND branchcode =:branch "
								+ "AND usr.isactive = 1 and usr.isdisabled = 0 group by userid,usr.username",
						param, DescIdForm.class, 1, null);
			} else if (role.equals("VAULT_MNGR")) {
				list = (List<DescIdForm>) dbService.execStoredProc(
						"SELECT userid AS id, usr.username AS description FROM TBUSER "
								+ "usr left join TBUSERROLES roles on usr.username = roles.username "
								+ "WHERE roles.roleid IN ('BOO') and usr.userid is not null "// changed
								+ "AND usr.isactive = 1 and usr.isdisabled = 0 group by userid,usr.username",
						param, DescIdForm.class, 1, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public List<DescIdForm> genCodetable(String codename) {
		List<DescIdForm> list = null;
		try {
			param.put("codename", codename);
			list = (List<DescIdForm>) dbService.execStoredProc(
					"SELECT codevalue AS id, desc1 AS description " + "FROM Tbcodetablecasa WHERE codename=:codename",
					param, DescIdForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<DescIdForm> getProdtype(String prodgroup) {
		// TODO Auto-generated method stub
		List<DescIdForm> list = null;
		try {
			param.put("prodgroup", prodgroup);
			list = (List<DescIdForm>) dbService.execStoredProc(
					"SELECT prodcode AS id, prodname AS description " + "FROM TBPRODMATRIX WHERE prodgroup=:prodgroup",
					param, DescIdForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Tbbranch> getBranchList() {
		// TODO Auto-generated method stub
		List<Tbbranch> list = new ArrayList<Tbbranch>();
		try {
			list = (List<Tbbranch>) dbService.execStoredProc("SELECT * FROM TBBRANCH", null, Tbbranch.class, 1, null);
			System.out.println("list size: " + list.size());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Date getBusinessdt() {
		// TODO Auto-generated method stub
		Date dt = null;
		try {
			param.put("userid", UserUtil.getUserByUsername(service.getUserName()).getUserid());
			dt = (Date) dbService.execStoredProc("SELECT unit.currentbusinessdate "
					+ "FROM TBBRANCH unit JOIN TBUSER usr ON unit.branchcode=usr.branchcode WHERE usr.userid=:userid",
					param, null, 0, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dt;
	}

	@Override
	public String validateUser(String username, String password) {
		// TODO Auto-generated method stub
		String result = "0";
		try {
			param.put("username", username);
			System.out.println("pass : " + password);
			param.put("password", UserUtil.sha1(password));
			Tbuser user = UserUtil.getUserByUsername(service.getUserName());
			param.put("branchcode", user.getBranchcode());
			param.put("coopcode", user.getCoopcode());
			Integer check = (Integer) dbService.execStoredProc("SELECT COUNT(*) FROM TBUSER WHERE username=:username "
					+ "AND password=:password " + "AND coopcode=:coopcode AND branchcode =:branchcode AND username "
					+ " IN (SELECT username from tbuserroles where username =:username and roleid = 'BOO')"
			// removed for coop
					, param, null, 0, null);
			if (check > 0) {
				System.out.println(">>>>>>>>>> Validate User : true >>>>>>>>>>>");
				result = "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public BranchInfoForm getBrInfo() {
		// TODO Auto-generated method stub
		BranchInfoForm form = null;
		try {
			param.put("userid", UserUtil.getUserByUsername(service.getUserName()).getUserid());
			form = (BranchInfoForm) dbService.execStoredProc("SELECT unit.currentbusinessdate AS businessdt, "
					+ "unit.nextbusinessdate AS nxtbusinessdt, CASE WHEN unit.branchstatus='0' THEN 'Close' ELSE 'Open' END AS brstatus "
					+ "FROM TBBRANCH unit JOIN TBUSER usr ON unit.branchcode=usr.branchcode WHERE usr.userid=:userid",
					param, BranchInfoForm.class, 0, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public String updateBr(String brstat) {
		// TODO Auto-generated method stub
		String result = "0";
		try {
			param.put("userid", UserUtil.getUserByUsername(service.getUserName()).getUserid());
			param.put("branchcode", UserUtil.getUserByUsername(service.getUserName()).getBranchcode());
			if (brstat.equalsIgnoreCase("open")) {
				if ((Integer) dbService.execStoredProc(
						"UPDATE TBBRANCH SET branchstatus=CASE WHEN branchstatus = '0' THEN '1' ELSE '0' END "
								+ "FROM TBBRANCH unit JOIN TBUSER usr ON unit.branchcode=usr.branchcode WHERE usr.userid=:userid",
						param, null, 2, null) > 0) {
					result = "1";
				}
			} else if (brstat.equalsIgnoreCase("close")) {
//				if ((Integer) dbService.execStoredProc(
//						"UPDATE TBBRANCH SET branchstatus=CASE WHEN branchstatus = '0' THEN '1' ELSE '0' END, "
//								+ "currentbusinessdate = nextbusinessdate, nextbusinessdate = DATEADD(DAY,1,nextbusinessdate) "
//								+ "FROM TBBRANCH unit JOIN TBUSER usr ON unit.branchcode=usr.branchcode WHERE usr.userid=:userid",
//						param, null, 2, null) > 0) {
//					result = "1";
//				}
				if ((Integer) dbService.execStoredProc(
						"UPDATE TBBRANCH SET branchstatus=CASE WHEN branchstatus = '0' THEN '1' ELSE '0' END "
								+ "FROM TBBRANCH unit JOIN TBUSER usr ON unit.branchcode=usr.branchcode WHERE usr.userid=:userid",
						param, null, 2, null) > 0) {
					result = "1";
				}
			}
			Tbbranch branch = (Tbbranch) dbService.executeUniqueHQLQuery("FROM Tbbranch WHERE branchcode=:branchcode",
					param);
			if (branch != null && branch.getBranchclassification().equals("0")) {
				dbService.executeUpdate("update TBPROCESSINGDATE set "
						+ "startdate = (select currentbusinessdate from TBBRANCH where branchclassification = 0), "
						+ "enddate = (select nextbusinessdate from TBBRANCH where branchclassification = 0)", param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String aeTerminal(Tbterminal data) {
		// TODO Auto-generated method stub
		// result 0 = existing, 1 = success, 999 = error
		String result = "0";
		try {
			System.out.println(data.getIpaddress() + " " + data.getTerminal());
			param.put("ipadd", data.getIpaddress());
			param.put("trmnl", data.getTerminal());
			System.out.println("DATA" + dbService.execStoredProc(
					"SELECT COUNT(*) FROM TBTERMINAL WHERE ipaddress=:ipadd OR terminal=:trmnl", param, null, 1, null));
			if ((Integer) dbService.execStoredProc(
					"SELECT COUNT(*) FROM TBTERMINAL WHERE ipaddress=:ipadd OR terminal=:trmnl", param, null, 0,
					null) == 0) {
				if ((Integer) dbService.execStoredProc(null, null, null, data.getId() == null ? 3 : 2, data) > 0) {
					result = "1";
				}
			} else {
				if ((Integer) dbService.execStoredProc(null, null, null, 3, data) > 0) {
					result = "1";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "999";
		}
		return result;
	}

	@Override
	public List<Tbterminal> terminalList(String unitid, int isUnused, String userid) {
		// TODO Auto-generated method stub
		List<Tbterminal> list = new ArrayList<Tbterminal>();
		try {
			param.put("unitid", unitid);
			System.out.println(unitid + "  <<<<<<<< unitid");
			if (isUnused == 1) {
				list = (List<Tbterminal>) dbService.execStoredProc(
						"SELECT * FROM TBTERMINAL WHERE unitid=:unitid AND userid IS NULL", param, Tbterminal.class, 1,
						null);
			} else if (isUnused == 2) {
				list = (List<Tbterminal>) dbService.execStoredProc("SELECT * FROM TBTERMINAL WHERE unitid=:unitid",
						param, Tbterminal.class, 1, null);
			} else {
				param.put("userid", userid);
				System.out.println(userid + " <<<<<< UDI");
				list = (List<Tbterminal>) dbService.execStoredProc(
						"SELECT * FROM TBTERMINAL WHERE unitid=:unitid AND (userid IS NULL OR userid =:userid)", param,
						Tbterminal.class, 1, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String deleteTerminal(int id) {
		// TODO Auto-generated method stub
		String result = "0";
		try {
			param.put("id", id);
			if ((Integer) dbService.execStoredProc("DELETE FROM TBTERMINAL WHERE id=:id", param, null, 2, null) > 0) {
				result = "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<ProductMatrixForm> productList() {
		// TODO Auto-generated method stub
		try {
			return (List<ProductMatrixForm>) dbService.execStoredProc(
					"SELECT prod.id AS id, cdtable.desc1 AS productgroup, prod.prodcode, prod.prodname, "
							+ "prod.prodsname, prod.currency, usr.username AS username FROM TBPRODMATRIX prod LEFT JOIN TBCODETABLECASA cdtable "
							+ "ON prod.prodgroup = cdtable.codevalue LEFT JOIN TBUSER usr ON "
							+ "prod.createdby=usr.userid WHERE cdtable.codename='PRODUCTGROUP'",
					param, ProductMatrixForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String aeProduct(Tbprodmatrix input) {
		// TODO Auto-generated method stub
		/***
		 * RESULT 0 = Error 1 = Already Existing 2 = Success
		 ***/
		String result = "Unable to save product";
		try {
			param.put("prodgroup", input.getProdgroup());
			param.put("prodcode", input.getProdcode());
			param.put("prodname", input.getProdname());
			param.put("prodsname", input.getProdsname());
			// New Product
			if (input.getId() == null) {
				Tbprodmatrix prod = (Tbprodmatrix) dbService.executeUniqueHQLQuery(
						"FROM Tbprodmatrix WHERE (prodcode=:prodcode or prodname=:prodname or prodsname=:prodsname)",
						param);
				if (prod != null) {
					if (prod.getProdcode().equals(input.getProdcode()))
						return "Product code alreaday exisitng! Unable to save!";
					if (prod.getProdname().equals(input.getProdname()))
						return "Product name alreaday exisitng! Unable to save!";
					if (prod.getProdsname().equals(input.getProdsname()))
						return "Product Short Name alreaday exisitng! Unable to save!";
				}
				if (dbService.save(input)) {
					return "New product created.";
				}
				// Update Product
			} else {
				param.put("id", input.getId());
				Tbprodmatrix prod = (Tbprodmatrix) dbService.executeUniqueHQLQuery(
						"FROM Tbprodmatrix WHERE id!=:id and (prodcode=:prodcode or prodname=:prodname or prodsname=:prodsname)",
						param);
				Tbprodmatrix origProd = (Tbprodmatrix) dbService.executeUniqueHQLQuery("FROM Tbprodmatrix WHERE id=:id",
						param);
				if (prod != null) {
					if (prod.getProdcode().equals(input.getProdcode()))
						return "Product code alreaday exisitng! Unable to save!";
					if (prod.getProdname().equals(input.getProdname()))
						return "Product name alreaday exisitng! Unable to save!";
					if (prod.getProdsname().equals(input.getProdsname()))
						return "Product Short Name alreaday exisitng! Unable to save!";
				}
				if (dbService.saveOrUpdate(input)) {
					param.put("origprodcode", origProd.getProdcode());
					param.put("origprodgroup", origProd.getProdgroup());
					dbService.executeUpdate(
							"UPDATE TBDEPOSITPRODUCTINTERESTRATE SET subprodcode=:prodcode WHERE subprodcode=:origprodcode AND productcode =:origprodgroup",
							param);
					return "Product updated.";
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Tbprodmatrix getProductDetail(int id) {
		// TODO Auto-generated method stub
		try {
			param.put("id", id);
			return (Tbprodmatrix) dbService.execStoredProc("SELECT * FROM TBPRODMATRIX WHERE id=:id", param,
					Tbprodmatrix.class, 0, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int terminalNo(String userid) {
		// TODO Auto-generated method stub
		int id = 0;
		try {
			param.put("userid", userid);
			System.out.println(userid + " <<<<< userid");
			if ((Integer) dbService.execStoredProc("SELECT id FROM TBTERMINAL WHERE userid=:userid", param, null, 0,
					null) != null) {
				id = (Integer) dbService.execStoredProc("SELECT id FROM TBTERMINAL WHERE userid=:userid", param, null,
						0, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public Integer printDocSlip(String txrefno) {
		// TODO Auto-generated method stub
		try {
			param.put("txrefno", txrefno);
			String query = "SELECT cd.txname AS txcode, jrnl.* FROM TBBRFINTXJRNL jrnl LEFT JOIN "
					+ "TBTRANSACTIONCODE cd ON jrnl.txcode=cd.txcode WHERE jrnl.txrefmain=:txrefno";
			if (txrefno.length() > 12) {
				query = "SELECT cd.txname AS txcode, jrnl.* FROM TBBRFINTXJRNL jrnl LEFT JOIN "
						+ "TBTRANSACTIONCODE cd ON jrnl.txcode=cd.txcode WHERE jrnl.txref=:txrefno";
			}
			Tbbrfintxjrnl jrnl = (Tbbrfintxjrnl) dbService.execStoredProc(query, param, Tbbrfintxjrnl.class, 0, null);
			List<String> datatoprint = new ArrayList<String>();
			datatoprint.add(jrnl.getTxcode().toUpperCase() + " " + jrnl.getCurrency() + " "
					+ PrinterUtil.formatData(jrnl.getTxamount().toString(), 2));
			// datatoprint.add(jrnl.getHostacceptind().equals("1") ? "HOST ACCEPTED" : "HOST
			// NOT ACCEPTED");
			datatoprint.add(jrnl.getAccountno());
			datatoprint.add(jrnl.getAcctname());
			datatoprint.add(jrnl.getTxref() + "   " + jrnl.getTerminalid() + "    " + jrnl.getUnit());
			datatoprint.add(jrnl.getTxdate().toString() + " "
					+ (jrnl.getHostacceptind().equals("1") ? "HOST ACCEPTED" : "HOST NOT ACCEPTED"));
			return PrinterUtil.printerUtil(datatoprint, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer printPassbook(String accountno) {
		// TODO Auto-generated method stub
		param.put("acctno", accountno);
		List<PassBookFrom> jrnl = new ArrayList<PassBookFrom>();
		List<String> datatoprint = new ArrayList<String>();
		int lineNo = 0;
		try {
			String query = "IF OBJECT_ID('tempdb..#tmpjrnl') IS NOT NULL DROP TABLE #tmpjrnl "
					+ "select CAST(ISNULL(jrnlno,0) AS INT) as line "
					+ ",FORMAT(CAST(Txdate as DATE),'dd-MM-yy') as txdate "
					+ ",dbo.getInitial(c.txname ) as txcode,debit,credit,outBal " + "INTO #tmpjrnl "
					+ "from tbdeptxjrnl a left join TBDEPOSIT b " + "on a.Accountno = b.AccountNo "
					+ "left join TBTRANSACTIONCODE c on a.Txcode = c.txcode " + "where a.Accountno =:acctno "
					+ "order by txdate,a.id " + "select * from #tmpjrnl where line<22 ";
			jrnl = (List<PassBookFrom>) dbService.execStoredProc(query, param, PassBookFrom.class, 1, null);
			if (jrnl != null && jrnl.size() > 0) {
				// datatoprint.add("");
				// datatoprint.add("");
				lineNo = jrnl.get(0).getLine();
				for (PassBookFrom row : jrnl) {
					System.out.println(lineNo + " ez");
					lineNo++;
					row.setLine(lineNo);
					datatoprint.add(String.valueOf(lineNo) + " 		" + row.getTxcode()
					// + " "
					// + (row.getDebit()==null?"":row.getDebit().toString()) +
					// " " + (row.getCredit()==null?"":row.getCredit().toString())
					// + " " + (row.getOutBal()==null?"":row.getOutBal().toString())
					);
					// dbService.saveOrUpdate(row);
					System.out.println(datatoprint.get(lineNo - 1));
				}
				param.put("lineNo", lineNo);
				return PrinterUtil.printerUtil(datatoprint, 3);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	public Integer passbookIssuance(String accountno, String serialno) {
		// TODO Auto-generated method stub
		param.put("acctno", accountno);
		List<PassBookFrom> jrnl = new ArrayList<PassBookFrom>();
		List<String> datatoprint = new ArrayList<String>();
		int lineNo = 0;
		try {
			String query = "IF OBJECT_ID('tempdb..#tmpjrnl') IS NOT NULL DROP TABLE #tmpjrnl "
					+ "select CAST(ISNULL(jrnlno,0) AS INT) as line " + ",Txdate,Txcode,debit,credit,outBal "
					+ "INTO #tmpjrnl " + "from tbdeptxjrnl a left join TBDEPOSIT b " + "on a.Accountno = b.AccountNo "
					+ "where a.Accountno =:acctno " + "order by txdate,a.id " + "select * from #tmpjrnl where line<22 ";
			jrnl = (List<PassBookFrom>) dbService.execStoredProc(query, param, PassBookFrom.class, 1, null);
			if (jrnl != null && jrnl.size() > 0) {
				lineNo = jrnl.get(0).getLine();
				for (PassBookFrom row : jrnl) {
					lineNo = lineNo + 1;
					row.setLine(lineNo);
					datatoprint.add(String.valueOf(lineNo) + " " + row.getTxcode() + " "
							+ (row.getDebit() == null ? "" : row.getDebit().toString()) + " "
							+ (row.getCredit() == null ? "" : row.getCredit().toString()) + " "
							+ (row.getOutBal() == null ? "" : row.getOutBal().toString()));
					// dbService.saveOrUpdate(row);
				}
				param.put("lineNo", lineNo);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return PrinterUtil.printerUtil(datatoprint, 3);
	}

	@Override
	public Tbrates getRates(String curr, String buysell) {
		// TODO Auto-generated method stub
		Tbrates rate = new Tbrates();
		try {
			param.put("curr", curr);
			param.put("buysell", buysell);
			System.out.println("currency: " + curr + " buysell: " + buysell);
			rate = (Tbrates) dbService.execStoredProc(
					"SELECT currency,buySell,boardrate,irr,pds FROM Tbrates WHERE currency=:curr AND buySell=:buysell",
					param, Tbrates.class, 0, null);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return rate;
	}

	@Override
	public List<Tbholiday> listHolidays(Date minDate) {
		List<Tbholiday> listHolidays = new ArrayList<Tbholiday>();
		minDate = Calendar.getInstance().getTime();
		param.put("minDate", minDate);
		try {
			listHolidays = (List<Tbholiday>) dbService.executeListHQLQuery("FROM Tbholiday WHERE holDate>:minDate",
					param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listHolidays;
	}

	@Override
	public Integer printCTD(String accountno) {
		// TODO Auto-generated method stub
		try {
			param.put("acctno", accountno);
			Tbdeposit dep = (Tbdeposit) dbService.executeUniqueHQLQuery("FROM Tbdeposit WHERE accountno=:acctno",
					param);
			param.put("unit", dep.getUnit());
			String unit = (String) dbService
					.executeUniqueSQLQuery("SELECT branchname FROM Tbbranch WHERE branchcode=:unit", param);
			List<String> datatoprint = new ArrayList<String>();
			datatoprint.add("");
			datatoprint.add("");
			datatoprint.add("");
			datatoprint.add("");
			datatoprint.add("");
			datatoprint.add("");
			datatoprint.add("");
			datatoprint.add("                                                            	" + unit);
			datatoprint.add("");
			datatoprint.add("");
			datatoprint.add("                  " + dep.getAccountName());
			datatoprint.add("");
			datatoprint.add("                  " + dep.getAccountNo());
			return PrinterUtil.printerUtil(datatoprint, 4);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ProductMatrixForm checkProduct(String prodcode, String prodgroup) {
		// TODO Auto-generated method stub
		ProductMatrixForm form = new ProductMatrixForm();
		try {
			param.put("prodcode", prodcode);
			param.put("prodgroup", prodgroup);
			form = (ProductMatrixForm) dbService.execStoredProc(
					"SELECT prodgroup AS productgroup,prodcode, prodname, reqinitialdepamt, rbmminmainbal FROM Tbprodmatrix WHERE prodcode=:prodcode AND prodgroup=:prodgroup",
					param, ProductMatrixForm.class, 0, null);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public List<Tbdocuments> getListDocument() {
		// TODO Auto-generated method stub
		List<Tbdocuments> list = new ArrayList<Tbdocuments>();
		try {
			list = (List<Tbdocuments>) dbService.execStoredProc("SELECT * FROM Tbdocuments", null, Tbdocuments.class, 1,
					null);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Tbdocsperproduct> getDocsperProd() {
		// TODO Auto-generated method stub
		List<Tbdocsperproduct> list = new ArrayList<Tbdocsperproduct>();
		try {
			list = (List<Tbdocsperproduct>) dbService.execStoredProc("SELECT * from Tbdocsperproduct", null,
					Tbdocsperproduct.class, 1, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String addOrupdateDoc(Tbdocuments doc) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			param.put("id", doc.getDocumentcode());
			Tbdocuments docu = (Tbdocuments) dbService.execStoredProc(
					"SELECT * FROM Tbdocuments WHERE documentcode=:id", param, Tbdocuments.class, 0, null);
			if (docu != null) {
				doc.setDocumentcode(docu.getDocumentcode());
				doc.setCreatedby(doc.getCreatedby());
				doc.setDatecreated(doc.getDatecreated());
				doc.setUpdatedby(UserUtil.securityService.getUserName());
				// doc.setLastupdated(new Date());
				if (dbService.saveOrUpdate(doc)) {
					flag = "updated";
				}
			} else {
				if (dbService.save(doc)) {
					flag = "created";
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String addOrupdateDocsperProd(Tbdocsperproduct docs) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			param.put("id", docs.getId());
			Tbdocsperproduct doc = (Tbdocsperproduct) dbService.execStoredProc(
					"SELECT * FROM Tbdocsperproduct WHERE id=:id", param, Tbdocsperproduct.class, 0, null);
			if (doc != null) {
				docs.setId(doc.getId());
				docs.setDatecreated(docs.getDatecreated());
				docs.setCreatedby(docs.getCreatedby());
				docs.setUpdatedby(UserUtil.securityService.getUserName());
				// docs.setLastupdated(new Date());
				if (dbService.saveOrUpdate(docs)) {
					flag = "updated";
				}
			} else {
				if (dbService.save(docs)) {
					flag = "created";
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<Tbtransactioncode> getListTxcode(String search) {
		// TODO Auto-generated method stub
		List<Tbtransactioncode> list = new ArrayList<Tbtransactioncode>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("search", search == null ? "%" : "%" + search + "%");
			list = (List<Tbtransactioncode>) dbService.executeListHQLQuery(
					"FROM Tbtransactioncode WHERE txcode like :search OR txname like :search", params);
			System.out.println("size: " + list.size());

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String addOrUpdateTxcode(Tbtransactioncode code) {
		// TODO Auto-generated method stub
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		DBService dbService = new DBServiceImpl();
		try {
			params.put("id", code.getId());
			Tbtransactioncode tx = (Tbtransactioncode) dbService
					.executeUniqueHQLQuery("FROM Tbtransactioncode WHERE id=:id", params);

			if (tx != null) {
				code.setId(tx.getId());
				if (dbService.saveOrUpdate(code)) {
					flag = "updated";
				}
			} else {
				if (dbService.save(code)) {
					flag = "created";
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String addEditOverride(Tboverridematrix override) {
		String result = "failed";
		try {
			System.out.println(override.getId() + " <<<<<< aydi");
			dbService.saveOrUpdate(override);
			result = "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Tboverridematrix> overrideList() {
		List<Tboverridematrix> list = new ArrayList<Tboverridematrix>();
		try {
			list = (List<Tboverridematrix>) dbService.execStoredProc(
					"select ov.id,tx.txname as txcode,amount,txrole.desc1 as transactingrole "
							+ ",ovrole.desc1 as overridebyrole,interbranch,reqtype.desc1 as requirementtype,requirementgroup "
							+ "from TBOVERRIDEMATRIX ov " + "left join tbtransactioncode tx on ov.txcode = tx.txcode "
							+ "left join TBCODETABLECASA txrole on ov.transactingrole = txrole.codevalue "
							+ "left join TBCODETABLECASA ovrole on ov.overridebyrole = ovrole.codevalue "
							+ "left join TBCODETABLECASA reqtype on ov.requirementtype = reqtype.codevalue "
							+ "where txrole.codename ='ROLE' and ovrole.codename ='ROLE' and reqtype.codename ='DOCREQUIRETYPE'",
					param, Tboverridematrix.class, 1, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String deleteOverride(Tboverridematrix override) {
		String result = "failed";
		try {
			dbService.delete(override);
			result = "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Tbtransactioncode> txList() {
		List<Tbtransactioncode> list = new ArrayList<Tbtransactioncode>();
		try {
			list = (List<Tbtransactioncode>) dbService.execStoredProc("select * from tbtransactioncode", param,
					Tbtransactioncode.class, 1, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Tbcodetablecasa> codetableList(String codename) {
		List<Tbcodetablecasa> list = new ArrayList<Tbcodetablecasa>();
		param.put("codename", codename);
		try {
			list = (List<Tbcodetablecasa>) dbService.execStoredProc(
					"SELECT * FROM Tbcodetablecasa WHERE codename =:codename", param, Tbcodetablecasa.class, 1, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String deleteCodetable(Tbcodetablecasa codetable) {
		String result = "failed";
		try {
			dbService.delete(codetable);
			result = "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String addEditCodetable(Tbcodetablecasa codetable) {
		String result = "failed";
		try {
			System.out.println(codetable.getId() + " <<<<<< aydi");
			dbService.saveOrUpdate(codetable);
			result = "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String addOrupdateMerchant(Tbmerchant merchant) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			param.put("id", merchant.getId());
			System.out.println("merchant id : " + merchant.getId());
			Tbmerchant merch = (Tbmerchant) dbService.execStoredProc("SELECT * FROM Tbmerchant WHERE id=:id", param,
					Tbmerchant.class, 0, null);

			if (merch != null) {
				merchant.setId(merch.getId());
				merchant.setUnit(merchant.getUnit());
				merchant.setStatus(merchant.getStatus());
				merchant.setInstcode(merchant.getInstcode());
				if (dbService.saveOrUpdate(merchant)) {
					flag = "updated";
				}

			} else {
				if (dbService.save(merchant)) {
					flag = "created";
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return flag;
	}

	@Override
	public List<Tbmerchant> getListMerchant() {
		// TODO Auto-generated method stub
		List<Tbmerchant> list = new ArrayList<Tbmerchant>();
		try {
			list = (List<Tbmerchant>) dbService.execStoredProc("SELECT * FROM Tbmerchant", null, Tbmerchant.class, 1,
					null);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Boolean hasRole(String roleid) {
		return UserUtil.hasRole(roleid);
	}

	@Override
	public List<Tbbanks> getBankList() {
		// TODO Auto-generated method stub
		List<Tbbanks> list = new ArrayList<Tbbanks>();
		try {
			list = (List<Tbbanks>) dbService.execStoredProc("SELECT * FROM Tbbanks", null, Tbbanks.class, 1, null);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public String addOrupdateHoliday(Tbholiday hol, String type) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			param.put("id", hol.getId());
			System.out.println("holiday id: " + hol.getId());

			Tbholiday hd = (Tbholiday) dbService.executeUniqueHQLQuery("FROM Tbholiday WHERE id=:id", param);

			if (hd == null && type.equals("1")) {
				if (dbService.save(hol)) {
					flag = "created";
				}
			}
			if (hd != null && type.equals("2")) {
				hol.setId(hd.getId());
				if (dbService.saveOrUpdate(hol)) {
					flag = "updated";
				}
			}
			if (hd != null && type.equals("3")) {
				if (dbService.delete(hd)) {
					flag = "deleted";
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<Tbholiday> getListHoliday(String search) {
		// TODO Auto-generated method stub
		List<Tbholiday> list = new ArrayList<Tbholiday>();
		try {
			param.put("search", search == null ? "%" : "%" + search + "%");
			System.out.println("search holiday: " + search);

			list = (List<Tbholiday>) dbService.executeListHQLQuery("FROM Tbholiday WHERE hol_name like :search", param);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public BranchInfoForm checkBranchStat() {
		// TODO Auto-generated method stub
		BranchInfoForm form = new BranchInfoForm();
		try {
			Tbuser user = UserUtil.getUserByUsername(service.getUserName());
			param.put("username", user.getUsername());
			param.put("brcode", user.getBranchcode());
			System.out.println("username: " + user.getUsername() + " brcode:" + user.getBranchcode());
			form = (BranchInfoForm) dbService.execStoredProc(
					"SELECT branchstatus AS branchstatus, currentbusinessdate as businessdt, branchclassification FROM Tbbranch b INNER JOIN Tbuser u on b.branchcode=:brcode AND u.username=:username",
					param, BranchInfoForm.class, 0, null);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return form;
	}

	@Override
	public Tbprodmatrix getProductDetailPerProductCodeORAccountno(String productcode, String accountno) {
		// TODO Auto-generated method stub
		try {
			if (accountno != null && !accountno.equals("")) {
				param.put("acctno", accountno);
				return (Tbprodmatrix) dbService.execStoredProc(
						"SELECT * FROM TBPRODMATRIX WHERE prodcode=(SELECT SubProductCode FROM Tbdeposit WHERE accountno=:acctno)",
						param, Tbprodmatrix.class, 0, null);
			} else {
				param.put("productcode", productcode);
				return (Tbprodmatrix) dbService.execStoredProc("SELECT * FROM TBPRODMATRIX WHERE prodcode=:productcode",
						param, Tbprodmatrix.class, 0, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String checkMaxWithdraw(String acctno) {
		String flag = "Unable to process right now.";
		param.put("acctno", acctno);
		Tbdeposit dep = new Tbdeposit();
		try {
			dep = (Tbdeposit) dbService.executeUniqueHQLQuery("FROM Tbdeposit WHERE accountno=:acctno", param);
			if ((dep != null)) {
				if (dep.getMaxwithdrawind() != null && !dep.getMaxwithdrawind()) {
					flag = "passed";
				} else {
					param.put("freq", dep.getMaxwithdrawfreq());
					String desc2 = (String) dbService.executeUniqueHQLQuery(
							"SELECT desc2 FROM Tbcodetablecasa WHERE codename ='WITHDRAWALFREQ' and codevalue =:freq",
							param);
					flag = "Account reached the maximum withdrawals for this " + desc2 + ".";
				}
			} else {
				flag = "Account not found!";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Tbprodmatrix getProductDetailByProdCode(String subProdCode, String prodCode) {
		// TODO Auto-generated method stub
		try {
			param.put("subProdCode", subProdCode);
			param.put("prodCode", prodCode);
			return (Tbprodmatrix) dbService.execStoredProc(
					"SELECT * FROM TBPRODMATRIX WHERE prodgroup=:subProdCode AND prodcode=:prodCode ", param,
					Tbprodmatrix.class, 0, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
