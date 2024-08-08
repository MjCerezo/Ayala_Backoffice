package com.etel.riskprofile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbriskprofile;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;

public class RiskProfileServiceImpl implements RiskProfileService {

	@Override
	public String addRiskProfile1(Tbriskprofile risk) {
		// TODO Auto-generated method stub
		String result = "failed";
		DBService dbService = new DBServiceImpl();
		Tbriskprofile riskval = new Tbriskprofile();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("id", risk.getId());
//		params.put("id", id);
		try {
			riskval = (Tbriskprofile) dbService.executeUniqueHQLQuery("FROM Tbriskprofile WHERE id=:id", params);
			if (riskval != null) {
				System.out.println(">>>>>>>>");
				riskval.setRiskprofiletype(risk.getRiskprofiletype());
				riskval.setDescription(risk.getDescription());
				riskval.setQ1(risk.getQ1());
				riskval.setQ2(risk.getQ2());
				riskval.setQ3(risk.getQ3());
				riskval.setResult(risk.getResult());
				riskval.setRemarks(risk.getRemarks());
				if (dbService.saveOrUpdate(riskval)) {
					result = "success";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		}	
		return result;
		}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbriskprofile> getRiskProfile(Integer id) {
		// TODO Auto-generated method stub
		DBService dbService = new DBServiceImpl();
		List<Tbriskprofile> val = new ArrayList<Tbriskprofile>();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("id",id);
		
		try {
			val = (List<Tbriskprofile>) dbService.executeListHQLQuery("FROM Tbriskprofile where id=:id ", params);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return val;
	}

	}
