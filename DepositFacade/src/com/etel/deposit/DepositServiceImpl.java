/**
 * 
 */
package com.etel.deposit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbchecksforclearing;
import com.coopdb.data.Tbdepdetail;
import com.coopdb.data.Tbdepositcif;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.deposit.form.DepositAccountForm;
import com.etel.deposit.form.DepositLedgerForm;
import com.etel.utils.HQLUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

/**
 * @author ETEL-LAPTOP19
 *
 */
@SuppressWarnings("unchecked")
public class DepositServiceImpl implements DepositService {

	private DBService dbService = new DBServiceImpl();
	private Map<String, Object> params = HQLUtil.getMap();
	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");

	@Override
	public List<DepositAccountForm> listDeposits(String memberid) {
		List<DepositAccountForm> form = new ArrayList<DepositAccountForm>();
		params.put("memberid", memberid);
		try {
			form = (List<DepositAccountForm>) dbService.execSQLQueryTransformer(
					"SELECT dep.accountno as acctno, mtx.prodgroup as productcode,mtx.prodsname as productname, mtx.prodcode as subprodcode, "
							+ "mtx.prodname as subprodname, dep.AccountName accountname, dep.AccountBalance as acctbalance, "
							+ "dep.PledgeAmount as pledgeamt, dep.FloatAmount as floatamt, acct.desc1 as acctsts, dep.BookDate as dateavailed FROM "
							+ "TBDEPOSITCIF cif " + "LEFT JOIN TBDEPOSIT dep on cif.accountno = dep.AccountNo "
							+ "LEFT JOIN TBCODETABLECASA acct on dep.accountstatus = acct.codevalue "
							+ "LEFT JOIN TBPRODMATRIX mtx on dep.ProductCode = mtx.prodgroup and dep.SubProductCode = mtx.prodcode "
							+ "WHERE acct.codename= 'CASA-ACCTSTS' and cif.cifno =:memberid",
					params, DepositAccountForm.class, 1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public List<DepositLedgerForm> listLedgerPerAcctno(String acctno) {
		List<DepositLedgerForm> form = new ArrayList<DepositLedgerForm>();
		params.put("acctno", acctno);
		try {
			form = (List<DepositLedgerForm>) dbService.execSQLQueryTransformer(
					"select Accountno as acctno,TxRefNo as txrefno,Txdate as txdate, IIF(dep.credit IS NULL,1,2) as txoper, "
							+ "txname as txcode, TxAmount as txamt, outBal as outbal " + "from TBDEPTXJRNL dep "
							+ "LEFT JOIN TBTRANSACTIONCODE code on dep.Txcode = code.txcode "
							+ "where Accountno =:acctno",
					params, DepositLedgerForm.class, 1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public List<Tbdepositcif> listAccountOwners(String acctno) {
		List<Tbdepositcif> form = new ArrayList<Tbdepositcif>();
		params.put("acctno", acctno);
		try {
			form = (List<Tbdepositcif>) dbService.executeListHQLQuery("FROM Tbdepositcif WHERE accountno=:acctno",
					params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public Tbdepdetail getDepositDetails(String acctno) {
		Tbdepdetail detail = new Tbdepdetail();
		params.put("acctno", acctno);
		try {
			detail = (Tbdepdetail) dbService.executeUniqueHQLQuery("FROM Tbdepdetail WHERE accountno=:acctno", params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return detail;
	}

	@Override
	public List<Tbchecksforclearing> getClearedChecks(String acctno) {
		// TODO Auto-generated method stub
		List<Tbchecksforclearing> list = new ArrayList<Tbchecksforclearing>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			param.put("acctno", acctno);
			list = (List<Tbchecksforclearing>) dbService.execStoredProc(
					"SELECT * FROM Tbchecksforclearing WHERE accountnumber=:acctno AND status ='2'", param,
					Tbchecksforclearing.class, 1, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
}
