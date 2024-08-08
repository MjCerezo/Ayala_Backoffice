package com.etel.CollectionManagement;

import java.util.Map;
import java.util.Date;
import java.util.HashMap;
import com.coopdb.data.Tbcollectionmanagement;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class CollectionManagementFacadeServiceImpl implements CollectionManagementFacadeService {
	
	private Map<String, Object> params = new HashMap<String, Object>();
	SecurityService serviceS = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private Map<String, Object> param = HQLUtil.getMap();
	DBService dbService = new DBServiceImpl();

	@Override
	public Tbcollectionmanagement getCollectionDetails(String accountno) {
		Tbcollectionmanagement list = new Tbcollectionmanagement();
		param.put("accountno", accountno);
		try {
			String myQuery = "FROM Tbcollectionmanagement Where accountno=:accountno ";
			list = (Tbcollectionmanagement) dbService.executeUniqueHQLQuery(myQuery, param);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}

	@Override
	public String saveCollectionDetails(Tbcollectionmanagement collection) {
		Tbcollectionmanagement collec = new Tbcollectionmanagement();
		try {
			if (collec.getAccountno() != null) {
				param.put("appno", collec.getAccountno());
				Tbcollectionmanagement row = (Tbcollectionmanagement) dbService
						.executeUniqueHQLQuery("FROM Tbcollectionmanagement where Tbcollectionmanagement=:appno", param);
				if (row != null) {
					row.setLoanpaymode(collection.getLoanpaymode());
					row.setAutodebitaccountno(collection.getAutodebitaccountno());
					row.setAccountname(collection.getAccountname());
					row.setStatus(collection.getStatus());
					
					row.setCollectionnoti(collection.getCollectionnoti());
					row.setEmail1(collection.getEmail1());
					row.setEmail2(collection.getEmail2());
					
					row.setAddress1(collection.getAddress1());
					row.setAddress2(collection.getAddress2());
					
					row.setCreatedby(collection.getCreatedby());
					row.setDatecreated(new Date());

					if (dbService.saveOrUpdate(row)) {
						return "success";
					}
				}
			}else {
				
				collection.setDatecreated((new Date()));
				if (dbService.saveOrUpdate(collection)) {
					System.out.println("SAVE");
					return "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

}
