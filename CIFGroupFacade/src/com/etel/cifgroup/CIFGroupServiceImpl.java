package com.etel.cifgroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cifsdb.data.Tbcifgroup;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.utils.HQLUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class CIFGroupServiceImpl implements CIFGroupService {

	SecurityService serviceS = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcifgroup> displayCIFGroupDetails(String groupcode) {
		// TODO Auto-generated method stub
		List<Tbcifgroup> cifgroup = new ArrayList<Tbcifgroup>();
		DBService dbService = new DBServiceImplCIF();
		@SuppressWarnings("unused")
		Map<String, Object> params = HQLUtil.getMap();
		params.put("cifgroupname", groupcode + "%");
		try {
			cifgroup = (List<Tbcifgroup>) dbService.executeListHQLQuery("FROM Tbcifgroup WHERE cifgroupname like:cifgroupname", params);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return cifgroup;

	}

	@Override
	public String addCIFGroup(Tbcifgroup group) {
		// TODO Auto-generated method stub

		String flag = "failed";
		DBService dbService = new DBServiceImplCIF();
		Tbcifgroup list = new Tbcifgroup();
		try {
			list.setCifgroupcode(group.getCifgroupcode());
			list.setCifgroupname(group.getCifgroupname());
			list.setCreatedby(serviceS.getUserName());
			list.setDatecreated(new Date());

			if (dbService.saveOrUpdate(list)) {
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String deleteCIFGroup(Tbcifgroup groupcode) {
		// TODO Auto-generated method stub
		String flag = "failed";
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("cifgroupcode", groupcode.getCifgroupcode());

		Tbcifgroup grp = new Tbcifgroup();
		try {
			grp = (Tbcifgroup) dbService.executeUniqueHQLQuery("FROM Tbcifgroup WHERE cifgroupcode=:cifgroupcode",
					params);
			if (dbService.delete(grp)) {
				flag = "successful";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;

	}

	@Override
	public String updateCIFGroup(Tbcifgroup group) {
		// TODO Auto-generated method stub

		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		String flag = "failed";

		try {
			params.put("cifgroupcode", group.getCifgroupcode());
			Tbcifgroup grouplist = (Tbcifgroup) dbService
					.executeUniqueHQLQuery("FROM Tbcifgroup WHERE cifgroupcode=:cifgroupcode", params);

			if (grouplist != null) {
				grouplist.setCifgroupname(group.getCifgroupname());
				grouplist.setUpdatedby(serviceS.getUserName());
				grouplist.setCreatedby(group.getCreatedby());
				grouplist.setDateupdated(new Date());
				grouplist.setDatecreated(group.getDatecreated());

				if (dbService.saveOrUpdate(grouplist)) {
					flag = "success";

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcifgroup> getListCIFGroup() {
		// TODO Auto-generated method stub
		DBService dbservice = new DBServiceImplCIF();
		List<Tbcifgroup> list = new ArrayList<Tbcifgroup>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			list = (List<Tbcifgroup>)dbservice.executeListHQLQuery("FROM Tbcifgroup", params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return list;
	}
}
