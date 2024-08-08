package com.etel.facade;

import java.util.Map;

import com.coopdb.data.Tbemployee;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;

public class NonMemberServiceImpl implements NonMemberService {
	
	private DBService dbService = new DBServiceImpl();
	private Map<String, Object> param = HQLUtil.getMap();

	@Override
	public Tbemployee getTbEmployeeByEmployeeID(String employeeid) {
		Tbemployee row = new Tbemployee();
		try {
			if(employeeid!=null){
				param.put("employeeid", employeeid);
				row = (Tbemployee) dbService.executeUniqueHQLQueryMaxResultOne
						("FROM Tbemployee WHERE employeeid=:employeeid", param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

}
