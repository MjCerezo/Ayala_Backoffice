package com.etel.acceptance;

import java.util.Map;

import com.coopdb.data.Tblstapp;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;

public class AcceptanceServiceImpl implements AcceptanceService {
	private Map<String, Object> params = HQLUtil.getMap();
	private DBService dbService = new DBServiceImpl();
	
	@Override
	public String updateLstApp(Tblstapp app) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			params.put("appno", app.getAppno());
			if(app.getAppno() != null){
				Tblstapp check = (Tblstapp)dbService.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", params);
				if(check != null){
						check.setDisbursementmethod(app.getDisbursementmethod());
						check.setClientdecision(app.getClientdecision());
						check.setAcceptanceremarks(app.getAcceptanceremarks());
						check.setDisbursementacctno(app.getDisbursementacctno());
						check.setAccttype(app.getAccttype());
						check.setAcceptanceremarks(app.getAcceptanceremarks());
						if(dbService.saveOrUpdate(check)){
							flag = "success";
						}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

}
