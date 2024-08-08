package com.etel.ayalacompany;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.coopdb.data.AyalaCompany;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;

public class AyalaCompanyServiceImpl implements AyalaCompanyService {
	DBService dbService = new DBServiceImpl();
	Map<String, Object> params = HQLUtil.getMap();

	@SuppressWarnings("unchecked")
	@Override
	public List<AyalaCompany> listCompany() {
		List<AyalaCompany> list = new ArrayList<AyalaCompany>();
		//params.put("cifno", cifno);
		try {
			//params.put("appno", appno);
			list = (List<AyalaCompany>) dbService
					.executeListHQLQuery("FROM AyalaCompany", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String deleteCompany(Integer id) {
		String flag = "failed";
		params.put("id", id);
		try {
			if (id != null) {

				Integer res = dbService.executeUpdate("DELETE FROM AyalaCompany WHERE id =:id", params);
				if (res != null && res == 1) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String saveOrUpdateCompany(AyalaCompany com) {
		AyalaCompany d = new AyalaCompany();
		if (com.getId() != null) {
			params.put("id", com.getId());
			d = (AyalaCompany) dbService.executeUniqueHQLQuery("FROM AyalaCompany WHERE id=:id", params);
			d.setId(com.getId());
			d.setBranch(com.getBranch());
			d.setCompanyCode(com.getCompanyCode());
			d.setCompanyName(com.getCompanyName());
			d.setClusterHead(com.getClusterHead());
			d.setClusterHeadCode(com.getClusterHeadCode());
			//d.setDateCreated(new Date());
			if (dbService.saveOrUpdate(d)) {
				System.out.println("UPDATE");
				return "update";
			}
		} else {
			if (dbService.saveOrUpdate(com)) {
				System.out.println("SAVE");
				return "success";
			}
		}
		return "failed";
	}

}
