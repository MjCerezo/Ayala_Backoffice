package com.etel.events;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.coopdb.data.Tbevents;
import com.coopdb.data.Tbeventschecklist;
import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
import com.wavemaker.runtime.service.annotations.ExposeToClient;

/**
 * This is a client-facing service class.  All
 * public methods will be exposed to the client.  Their return
 * values and parameters will be passed to the client or taken
 * from the client, respectively.  This will be a singleton
 * instance, shared between all requests. 
 * 
 * To log, call the superclass method log(LOG_LEVEL, String) or log(LOG_LEVEL, String, Exception).
 * LOG_LEVEL is one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level.
 * For info on these levels, look for tomcat/log4j documentation
 */
@ExposeToClient
public class EventsFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public EventsFacade() {
//       super(INFO);
    }
    EventsService srvc = new EventsServiceImpl();
    
    public List<Tbeventschecklist> getGovernanceChecklist(String memappid) {
    	return srvc.getGovernanceChecklist(memappid);
    }
    
    public String addGovernance(String memappid, String governancetype) {
    	return srvc.addGovernance(memappid, governancetype);
    }
    
    public String updateGovernance(Tbeventschecklist governace) {
    	return srvc.updateGovernance(governace);
    }
    public List<Tbevents> getTbevents(){
    	List<Tbevents> events = new ArrayList<Tbevents>();
    	EventsService srvc = new EventsServiceImpl();
    	events = srvc.getTbevents();
    	return events; 
    }
    public String addEvents(String coopcode, int eventcode, String govtype, String eventtype, String eventname, Date eventdate, String eventdesc, String eventvenue, Boolean isreq, String createdby){
    	EventsService srvc = new EventsServiceImpl();
    	return srvc.addEvents(coopcode, eventcode,govtype,eventtype,eventname,eventdate,eventdesc,eventvenue,isreq,createdby);
    }
    public Tbevents getEventCode(String coopcode){
    	EventsService srvc = new EventsServiceImpl();
    	return srvc.getEventCode(coopcode);
    }
    public String updateEvents(String coopcode, int eventcode,String eventname, Date eventdate, String eventdesc, String eventvenue, Boolean isreq){
    	EventsService srvc = new EventsServiceImpl();
    	return srvc.updateEvents(coopcode, eventcode,eventname,eventdate,eventdesc,eventvenue,isreq);
    }
    
    public String checkGovernance(String memappid){
    	return srvc.checkGovernance(memappid);
    }
    
    public List<Tbevents> getEventsPerCooperative(String coopcode){
    	return srvc.getEventsPerCooperative(coopcode);
    }
    
    public String deleteEvent(String coopcode, Integer eventcode){
    	return srvc.deleteEvent(coopcode, eventcode);
    }
}
