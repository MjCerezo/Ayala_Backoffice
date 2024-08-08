/**
 * 
 */
package com.etel.teller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.casa.FinTxService;
import com.casa.FinTxServiceImpl;
import com.casa.fintx.forms.AccountInquiryJournalForm;
import com.casa.fintx.forms.CashInCashOutForm;
import com.coopdb.data.Tbcashposition;
import com.coopdb.data.Tbnetamt;
import com.coopdb.data.Tboverageshortage;
import com.coopdb.data.Tbtellerslimit;
import com.coopdb.data.Tbuser;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.security.forms.TBRoleForm;
import com.etel.teller.form.TellerForm;
import com.etel.teller.form.TellersTotal;
import com.etel.util.HQLUtil;
import com.etel.utils.UserUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

/**
 * @author ETEL-LAPTOP19
 *
 */
@SuppressWarnings("unchecked")
public class TellerServiceImpl implements TellerService {

	private final DBService dbSrvc = new DBServiceImpl();
	Map<String, Object> param = HQLUtil.getMap();
	SecurityService serviceS = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");

	@Override
	public List<Tbtellerslimit> findAllTellersLimitbyCoopCodeAndBranchCode() {
		// TODO find ALl Tellers Liimit bh CoopCode and Branch Code
		List<Tbtellerslimit> tellersLimitList = new ArrayList<Tbtellerslimit>();
		try {
			Tbuser user = UserUtil.getUserByUsername(serviceS.getUserName());
			param.put("coopcode", user.getCoopcode());
			param.put("branchcode", user.getBranchcode());
			tellersLimitList = (List<Tbtellerslimit>) dbSrvc.execStoredProc(
					"SELECT id,coop.coopname as coopcode, branch.branchname as branchcode, l.roleid, l.limitamount, "
							+ "l.createdby, l.datecreated, l.updatedby, l.lastupdated FROM Tbtellerslimit l "
							+ "LEFT JOIN Tbcooperative coop on l.coopcode = coop.coopcode "
							+ "LEFT JOIN Tbbranch branch on l.branchcode = branch.branchcode WHERE l.coopcode =:coopcode AND l.branchcode =:branchcode",
					param, Tbtellerslimit.class, 1, null);
			if (tellersLimitList == null || tellersLimitList.isEmpty()) {
				System.out.println("No teller's limit found.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tellersLimitList;
	}

	@Override
	public String createTellersLimit(Tbtellerslimit tellerslimit) {
		// TODO Create Tellers Limit
		try {

			Tbuser user = UserUtil.getUserByUsername(serviceS.getUserName());
			tellerslimit.setCoopcode(user.getCoopcode());
			tellerslimit.setBranchcode(user.getBranchcode());
			tellerslimit.setCreatedby(user.getUsername());
			tellerslimit.setDatecreated(new Date());
			if (dbSrvc.save(tellerslimit)) {
				return "Teller's limit created successfully!";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "Error occured during process.";
	}

	@Override
	public String updateTellersLimit(Tbtellerslimit tellerslimit) {
		// TODO Update Tellers Limit
		param.put("amount", tellerslimit.getLimitamount());
		param.put("id", tellerslimit.getId());
		param.put("roleid", tellerslimit.getRoleid());
		param.put("username", serviceS.getUserName());
		try {
			if ((Integer) dbSrvc.execStoredProc(
					"UPDATE TBTELLERSLIMIT SET limitamount =:amount, updatedby =:username, lastupdated = GETDATE()"
							+ ", roleid =:roleid WHERE id =:id",
					param, null, 2, null) > 0) {
				return "Teller's limit updated successfully!";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "Error occured during process.";
	}

	@Override
	public String deleteTellersLimit(int id) {
		// TODO Delete Tellers Limit
		param.put("id", id);
		try {
			if ((Integer) dbSrvc.execStoredProc("DELETE FROM TBTELLERSLIMIT WHERE id=:id", param, null, 2, null) > 0) {
				return "Teller's limit deleted!";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "Error occured during process.";
	}

	@Override
	public Tbtellerslimit findTellersLimitbyId(int id) {
		// TODO Find teller's limit by id
		Tbtellerslimit tellersLimit = new Tbtellerslimit();
		try {
			param.put("id", id);
			tellersLimit = (Tbtellerslimit) dbSrvc.executeUniqueHQLQuery("FROM Tbtellerslimit WHERE id=:id", param);
			if (tellersLimit == null) {
				System.out.println("No teller's limit found.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tellersLimit;
	}

	@Override
	public List<TBRoleForm> findAllTellerRoles() {
		// TODO Find all teller roles
		List<TBRoleForm> roles = new ArrayList<TBRoleForm>();
		try {
			Tbuser user = UserUtil.getUserByUsername(serviceS.getUserName());
			param.put("coopcode", user.getCoopcode());
			param.put("branchcode", user.getBranchcode());
			roles = (List<TBRoleForm>) dbSrvc
					.execSQLQueryTransformer(
							"SELECT roleid, rolename FROM TBROLE WHERE roleid like '%teller%' "
									+ "AND roleid not IN (SELECT roleid FROM TBTELLERSLIMIT "
									+ "WHERE coopcode =:coopcode and branchcode =:branchcode)",
							param, TBRoleForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roles;
	}

	@Override
	public List<TellerForm> findAllTellers(String branchcode, String currency) {
		List<TellerForm> tellers = new ArrayList<TellerForm>();
		param.put("currency", currency);
		param.put("branchcode", UserUtil.getUserByUsername(serviceS.getUserName()).getBranchcode());
		try {
			tellers = (List<TellerForm>) dbSrvc.execSQLQueryTransformer(
					"select username,a.userid,ISNULL(isopen,0) as isopen,branchcode as branchid,userbalance as runningbalance "
							+ "from tbuser a left join TBNETAMT b on a.userid = b.userid "
							+ "where username in (select username from TBUSERROLES where rolename like '%teller%') "
							+ "and branchcode =:branchcode and b.currency =:currency and b.transfertype='1' and a.isdisabled <> 1",
					param, TellerForm.class, 1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return tellers;
	}

	@Override
	public String updateTellerStatus(String userid) {
		param.put("userid", userid);
		try {
			Tbuser user = (Tbuser) dbSrvc.executeUniqueHQLQuery("FROM Tbuser where userid=:userid", param);
			FinTxService finSrvc = new FinTxServiceImpl();
			CashInCashOutForm transfers = finSrvc.getCashInCashOut(userid, "PHP");
			if (user.getIsopen()) {
				if (transfers.getCashin() != null && !transfers.getCashin().isEmpty()) {
					boolean withPending = false;
					for (Tbcashposition transfer : transfers.getCashin()) {
						if (transfer.getTxstatus().equals("P") || transfer.getTxstatus().equals("Pending")) {
							withPending = true;
						}
					}
					if (withPending) {
						return "You still have pending cash in transaction/s.";
					}
				}
				if (transfers.getCashout() != null && !transfers.getCashout().isEmpty()) {
					boolean withPending = false;
					for (Tbcashposition transfer : transfers.getCashout()) {
						if (transfer.getTxstatus().equals("P") || transfer.getTxstatus().equals("Pending")) {
							withPending = true;
						}
					}
					if (withPending) {
						return "You still have pending cash out transaction/s.";
					}
				}
			}
			if (dbSrvc.executeUpdate(
					"UPDATE Tbuser Set isopen = IIF(isopen is null OR isopen = 1,0,1) where userid =:userid",
					param) == 1)
				return "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public TellerForm findTeller(String userid, String currency) {
		TellerForm teller = new TellerForm();
		param.put("currency", currency);
		param.put("userid", userid);
		try {
			teller = (TellerForm) dbSrvc.execSQLQueryTransformer(
					"select username,a.userid,ISNULL(isopen,0) as isopen,branchcode as branchid,userbalance as runningbalance "
							+ "from tbuser a left join TBNETAMT b on a.userid = b.userid "
							+ "where a.userid =:userid and b.currency =:currency and b.transfertype ='1'",
					param, TellerForm.class, 0);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return teller;
	}

	@Override
	public Tbtellerslimit findTellersLimit(String username) {
		Tbtellerslimit tellerslimit = new Tbtellerslimit();
		param.put("username", username);
		param.put("branchcode", UserUtil.getUserByUsername(serviceS.getUserName()).getBranchcode());
		param.put("coopcode", UserUtil.getUserByUsername(serviceS.getUserName()).getCoopcode());
		try {
			tellerslimit = (Tbtellerslimit) dbSrvc.execStoredProc(
					"SELECT TOP 1 t.* FROM Tbtellerslimit t LEFT JOIN Tbuserroles u on t.roleid = u.roleid "
							+ "WHERE u.username =:username AND u.roleid LIKE '%TELLER%' AND t.branchcode=:branchcode AND t.coopcode=:coopcode order by t.limitamount",
					param, Tbtellerslimit.class, 0, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return tellerslimit;
	}

	@Override
	public List<AccountInquiryJournalForm> listTellerTxPerPeriod(Date from, Date to, String userid) {
		List<AccountInquiryJournalForm> list = new ArrayList<AccountInquiryJournalForm>();
		param.put("from", from);
		param.put("to", to);
		param.put("userid", userid);
		try {
			list = (List<AccountInquiryJournalForm>) dbSrvc.execSQLQueryTransformer(
					"exec sp_tellerstransaction :from,:to,:userid", param, AccountInquiryJournalForm.class, 1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<TellersTotal> listTellerTotalCashTx(Date from, Date to, String userid, String txcode) {
		List<TellersTotal> list = new ArrayList<TellersTotal>();
		param.put("from", from);
		param.put("to", to);
		param.put("userid", userid);
		try {
			list = (List<TellersTotal>) dbSrvc.execSQLQueryTransformer("exec sp_tellerstotalcash :from,:to,:userid",
					param, TellersTotal.class, 1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<TellersTotal> listTellerTotalNonCashTx(Date from, Date to, String userid, String txcode) {
		List<TellersTotal> list = new ArrayList<TellersTotal>();
		param.put("from", from);
		param.put("to", to);
		param.put("userid", userid);
		try {
			list = (List<TellersTotal>) dbSrvc.execSQLQueryTransformer("exec sp_tellerstotalnoncash :from,:to,:userid",
					param, TellersTotal.class, 1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<TellersTotal> listTellerTotalChecksForClearing(String userid, int clearingdays) {
		List<TellersTotal> list = new ArrayList<TellersTotal>();
		param.put("clearingdays", clearingdays);
		param.put("userid", userid);
		try {
			list = (List<TellersTotal>) dbSrvc.execSQLQueryTransformer(
					"exec sp_tellerstotalcheckafterclearing :userid,:clearingdays", param, TellersTotal.class, 1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String declareOverageShortage(Tboverageshortage data) {
		String result = "failed";
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			data.setDatecreated(new Date());
			data.setTxrefno((String) dbsrvc.executeUniqueSQLQuery(
					"DECLARE @txrefno VARCHAR(20) EXEC SEQGENERATE @txrefno OUTPUT SELECT @txrefno", param));
			data.setBranchcode(UserUtil.getUserByUsername(serviceS.getUserName()).getBranchcode());
			if (dbSrvc.saveOrUpdate(data)) {
				param.put("txamt", data.getAmount());
				param.put("currency", "PHP");
				param.put("userid", data.getCreatedby());
				Tbnetamt netamt = (Tbnetamt) dbsrvc.executeUniqueHQLQuery(
						"FROM Tbnetamt WHERE userid =:userid and currency =:currency and transfertype=1", param);
				if (data.getReason().equals("1")) {
					netamt.setUserbalance(netamt.getUserbalance().subtract(data.getAmount()));
//					dbSrvc.executeUpdate("UPDATE TBNETAMT SET userbalance = userbalance -:txamt "
//							+ "WHERE userid =:userid and currency =:currency and transfertype=1", param);
				} else {
					netamt.setUserbalance(netamt.getUserbalance().add(data.getAmount()));
//					dbSrvc.executeUpdate("UPDATE TBNETAMT SET userbalance = userbalance +:txamt "
//							+ "WHERE userid =:userid and currency =:currency and transfertype=1", param);
				}
				dbsrvc.saveOrUpdate(netamt);
				result = "success";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
}
