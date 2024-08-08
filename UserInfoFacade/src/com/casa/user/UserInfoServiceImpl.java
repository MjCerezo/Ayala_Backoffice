package com.casa.user;

import java.math.BigDecimal;
import java.util.Map;

import com.casa.user.forms.UserDetailForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;
import com.coopdb.data.Tbbranch;
import com.coopdb.data.Tbunit;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class UserInfoServiceImpl implements UserInfoService {

	DBService dbService = new DBServiceImpl();
	Map<String, Object> param = HQLUtil.getMap();
	SecurityService service = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");

	@Override
	public UserDetailForm getUserinfo(String userid) {
		// TODO Auto-generated method stub
		UserDetailForm user = null;
		try {
			param.put("userid", userid);
			user = (UserDetailForm) dbService.execStoredProc(
					"SELECT TOP 1 usr.username AS username, role.roleid AS role, usr.firstname AS firstname, "
							+ "usr.middlename AS middlename, usr.lastname AS lastname, ntamt.userbalance AS unitbalance, usr.branchcode AS brid, "
							+ "usr.userid AS userid, ntamt.currency AS currency, unit.branchname AS brname, usr.coopcode AS instcode, coop.coopname as instname "
							+ "FROM TBUSER usr JOIN TBBRANCH unit ON unit.branchcode=usr.branchcode "
							+ "JOIN TBCOOPERATIVE coop on usr.coopcode = coop.coopcode "
							+ "JOIN TBUSERROLES role on role.username = usr.username "
							+ "JOIN TBNETAMT ntamt ON ntamt.userid=usr.userid WHERE usr.userid=:userid AND ntamt.currency='PHP' and ntamt.transfertype='1'",
					param, UserDetailForm.class, 0, null);
//			System.out.println(user.getInstname() + " INST NAME");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public BigDecimal getUnitBalance(String userid, String currency, String transfertype) {
		// TODO Auto-generated method stub
		BigDecimal amount = null;
		try {
			param.put("userid", userid);
			param.put("currency", currency);
			param.put("transfertype", transfertype == null?"1":transfertype);
			// amount = dbService.getSQLAmount("SELECT unitbalance FROM Tbuser WHERE
			// userid=:userid", param);
			if(transfertype == null) {
				amount = (BigDecimal) dbService.execStoredProc(
						"SELECT SUM(userbalance) FROM TBNETAMT WHERE userid=:userid AND currency=:currency", param, null, 0,
						null);
			}else {
				amount = (BigDecimal) dbService.execStoredProc(
						"SELECT userbalance FROM TBNETAMT WHERE userid=:userid AND currency=:currency and transfertype=:transfertype", param, null, 0,
						null);
			}//			System.out.println("Amount : " + amount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return amount;
	}

	@Override
	public Tbbranch getUnitinfo() {
		// TODO Auto-generated method stub
		Tbbranch unit = null;
		try {
			param.put("userid", UserUtil.getUserByUsername(service.getUserName()).getUserid());
			System.out.println(">?>>>>" + UserUtil.getUserByUsername(service.getUserName()).getUserid());
			unit = (Tbbranch) dbService.execStoredProc(
					"SELECT unit.* FROM TBBRANCH unit LEFT JOIN TBUSER usr ON "
							+ "unit.branchcode=usr.branchcode WHERE unit.coopcode=usr.coopcode AND usr.userid=:userid",
					param, Tbbranch.class, 0, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return unit;
	}

}
