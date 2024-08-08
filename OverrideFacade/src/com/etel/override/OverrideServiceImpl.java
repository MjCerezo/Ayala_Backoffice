/**
 * 
 */
package com.etel.override;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.casa.fintx.forms.OverrideResultForm;
import com.coopdb.data.Tbdeptxjrnl;
import com.coopdb.data.Tboverridematrix;
import com.coopdb.data.Tboverriderequest;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.deposittransaction.form.DepositTransactionForm;
import com.etel.facade.SecurityServiceImpl;
import com.etel.forms.UserForm;
import com.etel.override.form.OverrideRequestForm;
import com.etel.override.form.OverrideResponseForm;
import com.etel.utils.UserUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

/**
 * @author ETEL-LAPTOP19
 *
 */
@SuppressWarnings("unchecked")
public class OverrideServiceImpl implements OverrideService {

	SecurityService securityService = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");

	@Override
	public String addOverrideRule(Tboverridematrix overridematrix) {
		// TODO Auto-generated method stub
		DBService dbsrvc = new DBServiceImpl();
		try {
			if (dbsrvc.save(overridematrix))
				return "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public String deleteOverrideRule(Tboverridematrix overridematrix) {
		// TODO Auto-generated method stub
		DBService dbsrvc = new DBServiceImpl();
		try {
			if (dbsrvc.delete(overridematrix))
				return "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public List<Tboverridematrix> listOverrideRule(String txcode, String prodcode, String subprodcode) {
		// TODO Auto-generated method stub
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = new HashMap<String, Object>();
		List<Tboverridematrix> list = new ArrayList<Tboverridematrix>();
		String qry = "FROM Tboverridematrix WHERE 1=1 ";
		try {
			if (txcode != null && txcode.length() > 0) {
				param.put("txcode", txcode);
				qry += "AND txcode=:txcode ";
			}
			if (prodcode != null && prodcode.length() > 0) {
				param.put("prodcode", prodcode);
				qry += "AND prodcode=:prodcode ";
			}
			if (subprodcode != null && subprodcode.length() > 0) {
				param.put("subprodcode", subprodcode);
				qry += "AND subprodcode=:subprodcode ";
			}
			list = (List<Tboverridematrix>) dbsrvc.executeListHQLQuery(qry, param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Tboverriderequest> listOverrideRequest(String txrefno) {
		// TODO Auto-generated method stub
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = new HashMap<String, Object>();
		List<Tboverriderequest> list = new ArrayList<Tboverriderequest>();
		param.put("txrefno", txrefno);
		String qry = "FROM Tboverriderequest WHERE txrefno=:txrefno";
		try {
			list = (List<Tboverriderequest>) dbsrvc.executeListHQLQuery(qry, param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<OverrideResponseForm> checkOverride(OverrideRequestForm form) {
		// TODO Auto-generated method stub
//		DBService dbsrvc = new DBServiceImpl();
//		Map<String, Object> param = new HashMap<String, Object>();
		List<OverrideResponseForm> result = new ArrayList<OverrideResponseForm>();
		try {
			List<Tboverridematrix> m = (List<Tboverridematrix>) listOverrideRule(form.getTxcode(), form.getProdcode(),
					form.getSubprodcode());
			if (m != null && m.size() > 0) {
				for (Tboverridematrix mm : m) {
					if (mm.getOverriderule().equals("amount") && form.getAmount().compareTo(mm.getMinamount()) >= 0
							&& form.getAmount().compareTo(mm.getMaxamount()) <= 0) {
						result.add(new OverrideResponseForm(mm, "Transaction amount is subject for override"));
					}
					if (mm.getOverriderule().equals("interbranch")
							&& !form.getTransactingbranch().equals(form.getAccountbranch())) {
						result.add(new OverrideResponseForm(mm, "Interbranch transaction is subject for override"));
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	// 1 - initial
	// 2 - accepted
	// 3 - declined
	// 4 - request remote override
	// 5 - remote accepted
	// 6 - remote rejected
	@Override
	public String requestOverride(DepositTransactionForm form, List<Tboverriderequest> requests) {
		// TODO Auto-generated method stub
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = new HashMap<String, Object>();
		Tbdeptxjrnl jrnl = new Tbdeptxjrnl();
		try {
			if (form != null && form.getTxrefno() != null & !form.getTxrefno().isEmpty()) {
				param.put("txrefno", form.getTxrefno());
				jrnl = (Tbdeptxjrnl) dbsrvc.executeUniqueHQLQuery("FROM Tbdeptxjrnl WHERE txrefno =:txrefno", param);
				jrnl.setOverridestatus("1");
				jrnl.setOverridestatusdate(new Date());
				if (dbsrvc.saveOrUpdate(jrnl)) {
					if (dbsrvc.save(requests)) {
						return "success";
					}
				}
			}
			for (Tboverriderequest request : requests) {
				dbsrvc.save(request);
			}
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public String updateOverride(String txrefno, String status, String username, String password) {
		// TODO Auto-generated method stub
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("username", username);
		param.put("password", password);
		param.put("status", status);
		param.put("txrefno", txrefno);
		param.put("requestedby", UserUtil.getUserByUsername(securityService.getUserName()).getUserid());
		param.put("overrideby", null);
		com.etel.facade.SecurityService secsrvc = new SecurityServiceImpl();
		boolean isBOO = false;
		try {
//			Tbdeptxjrnl jrnl = (Tbdeptxjrnl) dbsrvc.executeUniqueHQLQuery("FROM Tbdeptxjrnl WHERE txrefno =:txrefno",
//					param);
//			jrnl.setOverridetype("2");
			if (status.equals("2")) {
//				jrnl.setOverridetype("1");
				UserForm user = secsrvc.getUserAccount(username);
				if (user == null || user.getUseraccount() == null
						|| !user.getUseraccount().getPassword().equals(UserUtil.sha1(password))) {
					return "Invalid username or password!";
				} else {
					int i = 0;
					while (i < user.getRoles().size()) {
						if (user.getRoles().get(i).getRoleid().equals("BOO")) {
							isBOO = true;
						}
						i++;
					}
				}
				if (!isBOO) {
					return "Invalid username or password!";
				}
				param.put("overrideby", UserUtil.getUserByUsername(username).getUserid());
			}
			dbsrvc.executeUpdate(
					"UPDATE Tboverriderequest SET status=:status,overrideby=:overrideby,statusdate=GETDATE() WHERE txrefno=:txrefno AND status IN ('1','4')",
					param);
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public OverrideResultForm waitRemoteOverride(String txrefno) {
		OverrideResultForm form = new OverrideResultForm();
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("txrefno", txrefno);
		try {
			form = (OverrideResultForm) dbsrvc.executeUniqueSQLQuery(
					"SELECT TOP 1 overrideby, CAST(status as int) as result FROM Tboverriderequest WHERE txrefno =:txrefno",
					param);
			if (form.getResult() == 4) {
				form.setResultstr("Waiting");
			}
			if (form.getResult() == 5) {
				form.setResultstr("Accepted");
			}
			if (form.getResult() == 6) {
				form.setResultstr("Declined");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public List<Tboverriderequest> listPendingRemoteOverride() {
		List<Tboverriderequest> list = new ArrayList<Tboverriderequest>();
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			list = (List<Tboverriderequest>) dbsrvc.executeListHQLQuery("FROM Tboverriderequest WHERE status = '4'",
					param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String updateOverrideAccountno(String txrefno, String accountno) {
		// TODO Auto-generated method stub
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("accountno", accountno);
		param.put("txrefno", txrefno);
		try {
			System.out.println("params " + param.toString());
			dbsrvc.executeUpdate("UPDATE Tboverriderequest SET accountno=:accountno WHERE txrefno=:txrefno", param);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}
}
