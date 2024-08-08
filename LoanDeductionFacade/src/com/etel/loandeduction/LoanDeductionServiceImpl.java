/**
 * 
 */
package com.etel.loandeduction;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbloandeduction;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.UserUtil;

/**
 * @author ETEL-LAPTOP07
 *
 */
public class LoanDeductionServiceImpl implements LoanDeductionService {

	@Override
	public String saveDeduction(Tbloandeduction loandeduction) {
		DBService dbsrvc = new DBServiceImpl();
		try {
			loandeduction.setCreatedby(UserUtil.securityService.getUserName());
			loandeduction.setDatecreated(new Date());
			dbsrvc.save(loandeduction);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String deleteDeduction(Tbloandeduction loandeduction) {
		DBService dbsrvc = new DBServiceImpl();
		try {
			dbsrvc.delete(loandeduction);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbloandeduction> listDeductions(String appno) {
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("appno", appno);
		try {
			return (List<Tbloandeduction>) dbsrvc.executeListHQLQuery("FROM Tbloandeduction WHERE appno=:appno", param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
