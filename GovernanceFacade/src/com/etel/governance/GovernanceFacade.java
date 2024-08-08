package com.etel.governance;

import java.util.Date;
import java.util.List;

import com.coopdb.data.Tbeventschecklist;
import com.coopdb.data.Tbmembereventschecklist;
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
public class GovernanceFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public GovernanceFacade() {
//       super(INFO);
    }
    
    GovernanceService srvc = new GovernanceServiceImpl();
    
    public governancePojo getMember(String memberid){
    	return srvc.getMember(memberid);
    }
    
    public String updateMemberEventsChecklist(List<Tbeventschecklist> list){
    	return srvc.updateMemberEventsChecklist(list);
    }
    
    public List<Tbeventschecklist> getMemberchecklist(String memberid){
    	return srvc.getMemberchecklist(memberid);
    }
    
    public List<Tbeventschecklist> getEvents(String govertype){
    	return srvc.getEvents(govertype);
    }
    
    public String saveMemberEvents(String[] eventcode, Boolean[] hasattended, String governancetype, String memberid){
    	return srvc.saveMemberEvents(eventcode, hasattended, governancetype, memberid);
    }
    
    public String updateMemberEvents(String[] eventcode, Boolean[] hasattended, String governancetype, String memberid){
    	return srvc.updateMemberEvents(eventcode, hasattended, governancetype, memberid);
    }
    
    public List<Tbeventschecklist> getMemberEventsforUpdates(String govertype, String memberid){
    	return srvc.getMemberEventsforUpdates(govertype, memberid);
    }
    
    public String updateGovernancePerId(String membershipid, Integer id, Boolean hasattended, Date eventdate, Boolean required){
    	return srvc.updateGovernancePerId(membershipid, id, hasattended, eventdate, required);
    }
    
    public String setUpGovernance(String membershipid, String governancetype){
    	return srvc.setUpGovernance(membershipid, governancetype);
    }
    
    public List<Tbmembereventschecklist> getMemberGovernance(String membershipid){
    	return srvc.getMemberGovernance(membershipid);
    }
    
    public String updateMemberGovernanceEvents(Tbmembereventschecklist memberevent){
    	return srvc.updateMemberGovernanceEvents(memberevent);
    }
}
