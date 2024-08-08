package com.etel.events;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.coopdb.data.Tbevents;
import com.coopdb.data.TbeventsId;
import com.coopdb.data.Tbeventschecklist;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;
import com.wavemaker.runtime.RuntimeAccess;

public class EventsServiceImpl implements EventsService {
	com.wavemaker.runtime.security.SecurityService serviceS = (com.wavemaker.runtime.security.SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	
	private Map<String, Object> params = HQLUtil.getMap();
	private DBService dbService = new DBServiceImpl();
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbevents> listEventsByType(String governancetype) {
		try {
			params.put("type", governancetype);
			List<Tbevents> list = (List<Tbevents>) dbService
					.executeListHQLQuery("FROM Tbevents WHERE govtypeclassification=:type", params);
			if(list != null)
				return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbeventschecklist> getGovernanceChecklist(String memappid) {
		try {
			params.put("memappid", memappid);
			List<Tbeventschecklist>  list = (List<Tbeventschecklist>) dbService
					.executeListHQLQuery("FROM Tbeventschecklist WHERE membershipappid=:memappid", params);		
			if(list != null)
				return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String addGovernance(String memappid, String governancetype) {
		try {
			List<Tbeventschecklist> list = getGovernanceChecklist(memappid);
			if (list == null || list.size() == 0) {
				List<Tbevents> events = (List<Tbevents>) listEventsByType(governancetype);
				if (events != null && !events.isEmpty())
					for (Tbevents e : events){
						Tbeventschecklist c = new Tbeventschecklist();
						c.setEventcode(e.getId().getEventcode());
						c.setEventdate(e.getEventdate());
						c.setEventname(e.getEventname());
						c.setIsrequired(e.getIsrequired());
						c.setGovernancetype(e.getGovtypeclassification());
						c.setMembershipappid(memappid);
						dbService.save(c);
					}
			} else {
				return "Governance checklist already exist.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	@Override
	public String updateGovernance(Tbeventschecklist governace) {
		try {
			if(dbService.saveOrUpdate(governace))
				return "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbevents> getTbevents() {
		List<Tbevents> events = new ArrayList<Tbevents>();
		DBService srvc = new DBServiceImpl();
		Map<String, Object>params = new HashMap<String,Object>();
		try {
			events = (List<Tbevents>)srvc.executeListHQLQuery("FROM Tbevents", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return events;
	}

	@Override
	public String addEvents(String coopcode, int eventcode, String govtype, 
			String eventtype, String eventname, Date eventdate, String eventdesc, 
			String eventvenue, Boolean isreq, String createdby) {
		
		String flag = "";
		Tbevents events = new Tbevents();
		TbeventsId id = new TbeventsId();
		DBService dbsrvc = new DBServiceImpl();
		
		try {
			id.setCoopcode(coopcode);
			id.setEventcode(eventcode);
			events.setId(id);
			events.setGovtypeclassification(govtype);
			events.setEventtype(eventtype);
			events.setEventname(eventname);
			events.setEventdate(eventdate);
			events.setEventdesc(eventdesc);
			events.setEventvenue(eventvenue);
			events.setIsrequired(isreq);
			events.setDatecreated(new Date());
			events.setCreatedby(serviceS.getUserName());
			if (dbsrvc.save(events)) {
				flag = "success";
			}else{
				flag = "failed";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}

	@Override
	public Tbevents getEventCode(String coopcode) {
		// EventsForm form = new EventsForm();
		DBService srvc = new DBServiceImpl();
		// Tbevents events = new Tbevents();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			params.put("coopcode", coopcode);
			Tbevents events = (Tbevents) srvc.executeUniqueHQLQueryMaxResultOne(
					"FROM Tbevents e WHERE e.id.eventcode=(SELECT MAX(e.id.eventcode) FROM e) AND e.id.coopcode=:coopcode", params);
			if (events != null) {
				events.getId().setEventcode(events.getId().getEventcode() + 1);
				return events;
			} else {
				Tbevents e = new Tbevents();
				TbeventsId id = new TbeventsId();
				id.setCoopcode(coopcode);
				id.setEventcode(101);
				e.setId(id);
				return e;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String updateEvents(String coopcode, int eventcode, String eventname, Date eventdate, String eventdesc, String eventvenue,
			Boolean isreq) {
		String flag = "";
		DBService srvc = new DBServiceImpl();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("eventcode", eventcode);
		params.put("coopcode", coopcode);
		try {
			Tbevents events = (Tbevents) srvc.executeUniqueHQLQuery("FROM Tbevents e WHERE e.id.eventcode=:eventcode AND e.id.coopcode=:coopcode", params);
			events.setEventname(eventname);
			events.setEventdate(eventdate);
			events.setEventdesc(eventdesc);
			events.setEventvenue(eventvenue);
			events.setIsrequired(isreq);
			events.setUpdatedby(serviceS.getUserName());
			events.setDateupdated(new Date());
			if (srvc.update(events)) {
				flag = "success";
			} else {
				flag = "failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String checkGovernance(String memappid) {
		// DANIEl
		try {
			params.put("memappid", memappid);
			@SuppressWarnings("unchecked")
			List<Tbeventschecklist> gov = (List<Tbeventschecklist>) dbService
					.executeListHQLQuery("FROM Tbeventschecklist WHERE membershipappid=:memappid", params);
			if (gov.size() == 0) {
				return "false";
			} else {
				return "true";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbevents> getEventsPerCooperative(String coopcode) {
		// TODO Auto-generated method stub
		try {
			params.put("coopcode", coopcode);
			List<Tbevents> events = (List<Tbevents>) dbService.executeListHQLQuery("FROM Tbevents e WHERE e.id.coopcode=:coopcode", params);
			if (events != null) {
				return events;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public String deleteEvent(String coopcode, Integer eventcode) {
		// TODO Auto-generated method stub
		try {
			params.put("coopcode", coopcode);
			params.put("eventcode", eventcode);
			Tbevents e = (Tbevents) dbService.executeUniqueHQLQuery(
					"FROM Tbevents e WHERE e.id.coopcode=:coopcode AND e.id.eventcode=:eventcode", params);
			if (e != null) {
				if (dbService.delete(e)) {
					return "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return "failed";
	}
}
