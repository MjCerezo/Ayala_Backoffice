/**
 * 
 */
package com.lms.loans;

import java.util.Map;

import com.coopdb.data.Tbloans;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

/**
 * @author ETEL-COMP05
 *
 */
public class LoansServiceImpl implements LoansService {
	
	DBService dbSrvc = new DBServiceImpl();
	Map<String, Object> params = HQLUtil.getMap();
	SecurityService secsrvc = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	@Override
	public Tbloans findLoanByAccountno(String accountno) {
		// TODO Auto-generated method stub
		Tbloans loan = new Tbloans();
		params.put("acctno", accountno);
		try {
			loan = (Tbloans) dbSrvc.executeUniqueHQLQuery("FROM Tbloans WHERE accountno =:acctno", params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return loan;
	}
	
}
