package com.etel.personalreference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.cifsdb.data.Tbpersonalreference;
import com.coopdb.data.Tbcomaker;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.utils.HQLUtil;
public class PersonalReferenceServiceImpl implements PersonalReferenceService {
	private DBService dbService = new DBServiceImplCIF();
	private Map<String, Object> params = HQLUtil.getMap();
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbpersonalreference> listPersonalreference(String cifno) {
		List<Tbpersonalreference> list = new ArrayList<Tbpersonalreference>();
		params.put("cifno", cifno);
		try {
			list = (List<Tbpersonalreference>) dbService
					.executeListHQLQuery("FROM Tbpersonalreference WHERE cifno =:cifno", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public String deletePref(Integer id) {
		String flag = "failed";
		params.put("id", id);
		try {
			if (id != null) {
				Integer res = dbService.executeUpdate("DELETE FROM TBPERSONALREFERENCE WHERE id =:id", params);
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
	public String saveOrUpdatePref(Tbpersonalreference ref) {
		Tbpersonalreference d = new Tbpersonalreference();
		if (ref.getId() != null) {
			params.put("id", ref.getId());
			d = (Tbpersonalreference) dbService.executeUniqueHQLQuery("FROM Tbpersonalreference WHERE id=:id", params);
			d.setCifno(ref.getCifno());
			d.setPersonalrefname(ref.getPersonalrefname());
			d.setEmployername(ref.getEmployername());
			d.setEmployeraddress(ref.getEmployeraddress());
			d.setContactno(ref.getContactno());
			d.setRelationship(ref.getRelationship());

			if (dbService.saveOrUpdate(d)) {
				System.out.println("UPDATE");
				return "update";
			}
		} else {
			if (dbService.saveOrUpdate(ref)) {
				System.out.println("SAVE");
				return "success";
			}
		}
		return "failed";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcomaker> listComaker(String cifno, String appno) {
		DBService dbServiceCOOP = new DBServiceImpl();
		List<Tbcomaker> list = new ArrayList<Tbcomaker>();
		params.put("cifno", cifno);
		try {
			if (appno != null && !appno.equals("")) {
				params.put("appno", appno);
				list = (List<Tbcomaker>) dbServiceCOOP
						.executeListHQLQuery("FROM Tbcomaker WHERE appno=:appno", params);// Ced 6-21-2021 Removed cifno in WHERE clause
			} else {
				list = (List<Tbcomaker>) dbServiceCOOP.executeListHQLQuery("FROM Tbcomaker WHERE cifno =:cifno",
						params);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String deleteComaker(Integer id) {
		DBService dbServiceCOOP = new DBServiceImpl();
		String flag = "failed";
		params.put("id", id);
		try {
			if (id != null) {

				Integer res = dbServiceCOOP.executeUpdate("DELETE FROM Tbcomaker WHERE id =:id", params);
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
	public String saveOrUpdateComaker(Tbcomaker ref) {
		DBService dbServiceCOOP = new DBServiceImpl();
		Tbcomaker d = new Tbcomaker();
		if (ref.getId() != null) {
			params.put("id", ref.getId());
			d = (Tbcomaker) dbServiceCOOP.executeUniqueHQLQuery("FROM Tbcomaker WHERE id=:id", params);
			d.setCifno(ref.getCifno());
			d.setPersonalrefname(ref.getPersonalrefname());
			d.setEmployername(ref.getEmployername());
			d.setEmployeraddress(ref.getEmployeraddress());
			d.setContactno(ref.getContactno());
			// d.setRelationship(ref.getRelationship());
			if (dbServiceCOOP.saveOrUpdate(d)) {
				System.out.println("UPDATE");
				return "update";
			}
		} else {
			if (dbServiceCOOP.saveOrUpdate(ref)) {
				System.out.println("SAVE");
				return "success";
			}
		}
		return "failed";
	}
}
