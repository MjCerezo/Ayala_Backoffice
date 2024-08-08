package com.etel.events;

import java.util.Date;
import java.util.List;

import com.coopdb.data.Tbevents;
import com.coopdb.data.Tbeventschecklist;

public interface EventsService {
	
	List<Tbevents> listEventsByType(String governancetype);
	
	List<Tbeventschecklist> getGovernanceChecklist(String memappid);
	
	String addGovernance(String memappid, String governancetype);
	
	String updateGovernance(Tbeventschecklist governace);

	List<Tbevents> getTbevents();

	String addEvents(String coopcode, int eventcode, String govtype, String eventtype, String eventname, Date eventdate, String eventdesc, String eventvenue, Boolean isreq, String createdby);

	Tbevents getEventCode(String coopcode);

	String updateEvents(String coopcode, int eventcode,String eventname, Date eventdate, String eventdesc, String eventvenue, Boolean isreq);

	String checkGovernance(String memappid);
	
	List<Tbevents> getEventsPerCooperative(String coopcode);

	String deleteEvent(String coopcode, Integer eventcode);
	
}
