/**
 * 
 */
package com.etel.depositproductinterest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbdepositproductinterestrate;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

/**
 * @author ETEL-LAPTOP19
 *
 */
@SuppressWarnings("unchecked")
public class DepositProductInterestServiceImpl implements DepositProductInterestService {

	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");

	@Override
	public String saveOrUpdateProductInterest(Tbdepositproductinterestrate depositproductinterestrate) {
		DBService dbsrvc = new DBServiceImpl();
		try {
			if (depositproductinterestrate.getId() == null) {
				depositproductinterestrate.setCreatedby(secservice.getUserName());
				depositproductinterestrate.setDatecreated(new Date());
			} else {
				depositproductinterestrate.setUpdatedby(secservice.getUserName());
				depositproductinterestrate.setDateupdated(new Date());
			}
			if (dbsrvc.saveOrUpdate(depositproductinterestrate))
				return "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "failed";
		}
		return "failed";
	}

	@Override
	public List<Tbdepositproductinterestrate> listDepositProductInterestRates(
			Tbdepositproductinterestrate depositproductinterestrate) {
		List<Tbdepositproductinterestrate> list = new ArrayList<Tbdepositproductinterestrate>();
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("productcode", depositproductinterestrate.getProductcode());
		param.put("subprodcode", depositproductinterestrate.getSubprodcode());
		param.put("interest", depositproductinterestrate.getInterest());
		param.put("role", depositproductinterestrate.getRole());
		param.put("username", depositproductinterestrate.getUsername());
		param.put("termindays", depositproductinterestrate.getMintermindays());
		param.put("amount", depositproductinterestrate.getMinamount());
		try {
			String qry = "FROM Tbdepositproductinterestrate WHERE status <> 'D' ";
			if (!depositproductinterestrate.getProductcode().isEmpty()) {
				qry += "AND  productcode=:productcode ";
			}
			if (!depositproductinterestrate.getSubprodcode().isEmpty()) {
				qry += "AND  subprodcode=:subprodcode ";
			}
			if (!depositproductinterestrate.getRole().isEmpty()) {
				qry += "AND  role=:role ";
			}
			if (!depositproductinterestrate.getUsername().isEmpty()) {
				qry += "AND  username=:username ";
			}
			if (depositproductinterestrate.getMintermindays() != null
					&& depositproductinterestrate.getMintermindays() > 0) {
				qry += "AND  mintermindays <=:termindays AND maxtermindays>=:termindays ";
			}
			if (depositproductinterestrate.getMinamount() != null
					&& depositproductinterestrate.getMinamount().compareTo(BigDecimal.ZERO) == 1) {
				qry += "AND  minamount <=:amount AND maxamount>=:amount ";
			}
			qry += "ORDER BY id";
			System.out.println(qry);
			list = (List<Tbdepositproductinterestrate>) dbsrvc.executeListHQLQuery(qry, param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

}
