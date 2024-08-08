package com.user.maintenance;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.security.forms.TBRoleForm;
//import com.etel.forms.GenericForm;
import com.etel.util.HQLUtil;
import com.etel.util.SequenceGenerator;
import com.etel.utils.LoggerUtil;
import com.etel.utils.UserUtil;
import com.coopdb.data.Tbcodetablecasa;
import com.coopdb.data.Tbnetamt;
import com.coopdb.data.Tbroleaccess;
import com.coopdb.data.Tbterminal;
import com.coopdb.data.Tbuser;
import com.user.maintenance.form.AccessRights;
import com.user.maintenance.form.MainSubSelectModuleForm;
import com.user.maintenance.form.UserEditForm;
import com.user.maintenance.form.UserListForm;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class UserAccountServiceImpl implements UserAccountService {

	DBService dbService = new DBServiceImpl();
	Map<String, Object> param = HQLUtil.getMap();
	SecurityService service = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private String result = "0";

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcodetablecasa> roleList() {
		// TODO Auto-generated method stub
		List<Tbcodetablecasa> list = new ArrayList<Tbcodetablecasa>();
		try {
			list = (List<Tbcodetablecasa>) dbService.execStoredProc(
					"SELECT * FROM TBCODETABLECASA WHERE codename='ROLE'", null, Tbcodetablecasa.class, 1, null);
		} catch (Exception e) {

		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccessRights> accessRightsMain() {
		// TODO Auto-generated method stub
		List<AccessRights> list = new ArrayList<AccessRights>();
		List<String> pageList = new ArrayList<String>();
		List<AccessRights> tmplist = new ArrayList<AccessRights>();
		try {
			pageList = pageList();
			param.put("userid", UserUtil.getUserByUsername(service.getUserName()).getUserid());
			System.out.println("USER ID : " + UserUtil.getUserByUsername(service.getUserName()).getUserid());
			String role = (String) dbService.execStoredProc("SELECT role FROM TBUSER WHERE userid=:userid", param, null,
					0, null);

			param.put("role", role);
			System.out.println("ROLE : " + role);
			list = (List<AccessRights>) dbService.execStoredProc(
					"SELECT tb.module AS module, tb.modulename AS modulename,  "
							+ "tb.modulelevel AS level, tb.page AS page, (SELECT COUNT(*) FROM TBROLEACCESS tbl WHERE "
							+ "tbl.module=tb.module AND tbl.role=tb.role) AS subcount "
							+ "FROM TBROLEACCESS tb WHERE tb.modulelevel=1 AND tb.role=:role ORDER BY tb.module",
					param, AccessRights.class, 1, null);
			tmplist.addAll(list);
			list = new ArrayList<AccessRights>();
			for (AccessRights row : tmplist) {
				if (row.getPage() == null) {
					list.add(row);
				} else {
					for (String page : pageList) {
						if (row.getPage().equals(page)) {
							list.add(row);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("List main : " + list.size());
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccessRights> accessRightsSub() {
		// TODO Auto-generated method stub
		List<AccessRights> list = new ArrayList<AccessRights>();
		List<String> pageList = new ArrayList<String>();
		List<AccessRights> tmplist = new ArrayList<AccessRights>();
		try {
			pageList = pageList();
			param.put("userid", UserUtil.getUserByUsername(service.getUserName()).getUserid());
			String role = (String) dbService.execStoredProc("SELECT role FROM TBUSER WHERE userid=:userid", param, null,
					0, null);
			System.out.println("sub role: " + role);
			param.put("role", role);
			list = (List<AccessRights>) dbService.execStoredProc(
					"SELECT tb.module AS module, tb.modulename AS modulename,  "
							+ "tb.modulelevel AS level, tb.page AS page "
							+ "FROM TBROLEACCESS tb WHERE tb.modulelevel!=1 AND tb.role=:role",
					param, AccessRights.class, 1, null);
			// System.out.println("List sub : " +list.size());
			tmplist.addAll(list);
			list = new ArrayList<AccessRights>();
			for (AccessRights row : tmplist) {
				if (row.getPage() == null) {
					list.add(row);
				} else {
					for (String page : pageList) {
						if (row.getPage().equals(page)) {
							list.add(row);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("List sub : " + list.size());
		return list;
	}

	@Override
	public List<String> pageList() {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		String urlDirectory = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath("pages/");
		try {
			File file = new File(urlDirectory);
			list = Arrays.asList(file.list());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbroleaccess> roleAccessList(String role) {
		// TODO Auto-generated method stub
		List<Tbroleaccess> list = new ArrayList<Tbroleaccess>();
		try {
			param.put("role", role);
			list = (List<Tbroleaccess>) dbService.execStoredProc(
					"SELECT * FROM TBROLEACCESS WHERE role=:role ORDER by module, " + " modulelevel", param,
					Tbroleaccess.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String addRoleAccess(Tbroleaccess rec) {
		// TODO Auto-generated method stub
		try {
			if ((Integer) dbService.execStoredProc(null, null, null, 3, rec) > 0) {
				result = "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String deleteRoleAccess(int id) {
		// TODO Auto-generated method stub
		try {
			param.put("id", id);
			if ((Integer) dbService.execStoredProc("DELETE FROM TBROLEACCESS WHERE id=:id", param, null, 2, null) > 0) {
				result = "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String addRole(Tbcodetablecasa rec) {
		// TODO Auto-generated method stub
		try {
			if ((Integer) dbService.execStoredProc(null, null, null, 3, rec) > 0) {
				result = "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String deleteRole(int id) {
		// TODO Auto-generated method stub
		try {
			param.put("id", id);
			if ((Integer) dbService.execStoredProc("DELETE FROM TBCODETABLECASA WHERE id=:id", param, null, 2,
					null) > 0) {
				result = "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcodetablecasa> mainMod() {
		// TODO Auto-generated method stub
		List<Tbcodetablecasa> list = null;
		try {
			list = (List<Tbcodetablecasa>) dbService.execStoredProc(
					"SELECT id AS id, codevalue AS codevalue, desc1 AS desc1 "
							+ "FROM TBCODETABLECASA WHERE codename='MODULE' ORDER by codevalue ",
					null, Tbcodetablecasa.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbterminal> terminalList(String unitid) {
		// TODO Auto-generated method stub
		List<Tbterminal> list = new ArrayList<Tbterminal>();
		try {
			param.put("unitid", unitid);
			list = (List<Tbterminal>) dbService.execStoredProc("SELECT * FROM TBTERMINAL WHERE unitid=:unitid", param,
					Tbterminal.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcodetablecasa> subMod() {
		// TODO Auto-generated method stub
		List<Tbcodetablecasa> list = null;
		try {
			list = (List<Tbcodetablecasa>) dbService
					.execStoredProc(
							"SELECT id AS id, codevalue AS codevalue, desc1 AS desc1 "
									+ "FROM TBCODETABLECASA WHERE codename='SUBMODULE'",
							null, Tbcodetablecasa.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public MainSubSelectModuleForm selectMainSub() {
		// TODO Auto-generated method stub
		MainSubSelectModuleForm form = new MainSubSelectModuleForm();
		try {
//			List<GenericForm> main = (List<GenericForm>) dbService.execStoredProc("SELECT  codevalue AS id, desc1 AS value "
//					+ "FROM TBCODETABLECASA WHERE codename='MODULE'", null, GenericForm.class, 1, null);
//			List<GenericForm> sub = (List<GenericForm>) dbService.execStoredProc("SELECT  codevalue AS id, desc1 AS value "
//					+ "FROM TBCODETABLECASA WHERE codename='SUBMODULE'", null, GenericForm.class, 1, null);
//			form.setMainmod(main);
//			form.setSubmod(sub);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public String addModule(Tbcodetablecasa rec) {
		// TODO Auto-generated method stub
		try {
			param.put("mod", rec.getCodename());
			rec.setCodevalue((String) dbService.execStoredProc("SELECT CONVERT(VARCHAR,MAX(CAST(codevalue AS INT))+1) "
					+ "FROM TBCODETABLECASA WHERE codename=:mod", param, null, 0, null));
			if ((Integer) dbService.execStoredProc(null, null, null, 3, rec) > 0) {
				result = "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String deleteModule(int id) {
		// TODO Auto-generated method stub
		try {
			param.put("id", id);
			if ((Integer) dbService.execStoredProc("DELETE FROM TBCODETABLECASA WHERE id=:id", param, null, 2,
					null) > 0) {
				result = "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String createUser(Tbuser user) {
		// TODO Auto-generated method stub
		try {
			System.out.println("ACTIVE : " + user.getIsactive());
			user.setPassword(UserUtil.sha1("P@ssword01"));
			user.setUserid(SequenceGenerator.generateUserSequence(user.getUnitbrid(), user.getCoopcode()));
			if ((Integer) dbService.execStoredProc(null, null, null, 3, user) > 0) {
				// if (user.getRole().equals("teller") || user.getRole().equals("cashier")) {
				List<String> currlist = (List<String>) dbService.execStoredProc(
						"SELECT codevalue FROM TBCODETABLECASA WHERE codename='CURR' ", null, null, 1, null);
				System.out.println("CURRENCY COUNT " + currlist.size());
				for (String currency : currlist) {
					Tbnetamt record = new Tbnetamt();
					record.setUserbalance(BigDecimal.ZERO);
					record.setCurrency(currency);
					record.setUserid(user.getUserid());
					record.setBusinessdate(user.getDatecreated());
					record.setTransfertype("1");
					dbService.execStoredProc(null, null, null, 3, record);
					record = new Tbnetamt();
					record.setUserbalance(BigDecimal.ZERO);
					record.setCurrency(currency);
					record.setUserid(user.getUserid());
					record.setBusinessdate(user.getDatecreated());
					record.setTransfertype("2");
					dbService.execStoredProc(null, null, null, 3, record);
				}
				result = "1";
				// } else {
				// result = "1";
				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserListForm> userList(String username, String instcode) {
		// TODO Auto-generated method stub
		List<UserListForm> list = new ArrayList<UserListForm>();
		try {
			param.put("username", "%" + username + "%");
			param.put("instcode", instcode);
			StringBuilder strquery = new StringBuilder();
			strquery.append("SELECT id AS id, username AS username, unitbrid AS userid, "
					+ "currency AS currency, unitbalance AS unitbalance, role AS role, "
					+ "CONCAT(firstname,' ',SUBSTRING(middlename,1,1),'. ',lastname) AS name "
					+ "FROM TBUSER WHERE instcode =:instcode ");
			if (username == null) {
			} else {
				strquery.append("AND username LIKE :username");
			}
			list = (List<UserListForm>) dbService.execStoredProc(strquery.toString(), param, UserListForm.class, 1,
					null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Tbuser userInfo(int id) {
		// TODO Auto-generated method stub
		Tbuser user = null;
		try {
			param.put("id", id);
			System.out.println("ID :" + id);
			user = (Tbuser) dbService.execStoredProc("SELECT * FROM TBUSER WHERE id=:id", param, Tbuser.class, 0, null);
//			System.out.println(user.getCurrency()+" "+user.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public String editUser(UserEditForm form) {
		// TODO Auto-generated method stub
		try {
			param.put("id", form.getId());
			param.put("fname", form.getFirstname());
			param.put("mname", form.getMiddlename());
			param.put("lname", form.getLastname());
			param.put("email", form.getEmail());
			param.put("pwnvrexp", form.isPassneverexp());
			param.put("active", form.isActive());
			param.put("locked", form.isLocked());
			param.put("suspended", form.isSuspended());
			param.put("limit", form.getTellerslimit());
			param.put("updtby", form.getUpdatedby());
			param.put("dtupdt", new Date());
			if ((Integer) dbService.execStoredProc(
					"UPDATE TBUSER SET firstname=:fname, middlename=:mname, lastname=:lname, "
							+ "emailadd=:email, ispwneverexpire=:pwnvrexp, isactive=:active, islocked=:locked, issuspended=:suspended, "
							+ "limitamount=:limit, updatedby=:updtby, dateupdated=:dtupdt WHERE id=:id",
					param, null, 2, null) > 0) {
				result = "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<TBRoleForm> getUserRolesByUsername(String username) {
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		List<TBRoleForm> roles = new ArrayList<TBRoleForm>();
		try {
			if (username != null) {
				params.put("username", username);
				roles = (List<TBRoleForm>) dbService.execSQLQueryTransformer(
						"SELECT roleid, rolename FROM Tbuserroles WHERE username=:username", params, TBRoleForm.class,
						1);
			}
		} catch (Exception e) {
			LoggerUtil.exceptionError(e, UserUtil.class);
			e.printStackTrace();
		}
		return roles;
	}

	@Override
	public Tbuser getUserByUsername(String username) {
		try {
			Tbuser user = UserUtil.getUserByUsername(username);
			if (user != null) {
				return user;
			}
		} catch (Exception e) {
			LoggerUtil.exceptionError(e, UserUtil.class);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String saveUserGLAccount(String username, String glcode) {
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("username", username);
		param.put("glcode", glcode);
		try {
			if (dbsrvc.executeUpdate("UPDATE Tbuser SET glcode=:glcode WHERE username=:username", param) > 0) {
				return "success";
			}
		} catch (Exception e) {
			LoggerUtil.exceptionError(e, UserUtil.class);
			e.printStackTrace();
		}
		return "failed";
	}

}
