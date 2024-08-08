package com.etel.audittrail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cifsdb.data.Tbauditevents;
import com.cifsdb.data.Tbaudittrail;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImplCIF;

public class AuditTrailServiceImpl implements AuditTrailService {

	@Override
	public Tbauditevents auditCodetable(String moduleid,int eventid) {
		Tbauditevents form = new Tbauditevents();
		DBService dbsrvc = new DBServiceImplCIF();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("moduleid", moduleid);
		params.put("eventid",eventid);
		try {
			form = (Tbauditevents)dbsrvc.executeUniqueHQLQuery("From Tbauditevents where eventid=:eventid and moduleid=:moduleid", params);
//			if(form!=null){
//				System.out.println("NOT NULL!");
//			} else {
//			}
//			System.out.println(form.getModuleid()+"<<<<<<<< Module ID");
//			System.out.println(moduleid+"<<<<< PARAMS MOD ID");
//			System.out.println(eventid+"<<<<<< PARAMS ID");
//			System.out.println(form.getEventname()+"<< EVENTNAME");
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbaudittrail> getListAuditTrail() {
		List<Tbaudittrail> audit = new ArrayList<Tbaudittrail>();
		DBService dbsrvc = new DBServiceImplCIF();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			audit = (List<Tbaudittrail>)dbsrvc.executeListHQLQuery("From Tbaudittrail", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return audit;
	}
}
