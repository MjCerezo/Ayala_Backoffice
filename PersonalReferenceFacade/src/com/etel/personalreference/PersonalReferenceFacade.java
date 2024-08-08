package com.etel.personalreference;

import java.util.List;

import com.cifsdb.data.Tbpersonalreference;
import com.coopdb.data.Tbcomaker;
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
public class PersonalReferenceFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
	public List<Tbpersonalreference> listPersonalreference(String cifno) {
		PersonalReferenceService srvc = new PersonalReferenceServiceImpl();
		return srvc.listPersonalreference(cifno);
	}
	
	public String deletePref(Integer id) {
		PersonalReferenceService srvc = new PersonalReferenceServiceImpl();
		return srvc.deletePref(id);
	}
	
	public String saveOrUpdatePref(Tbpersonalreference ref) {
		PersonalReferenceService srvc = new PersonalReferenceServiceImpl();
  		return srvc.saveOrUpdatePref(ref);
  	}
	
	

	public List<Tbcomaker> listComaker(String cifno, String appno) {
		PersonalReferenceService srvc = new PersonalReferenceServiceImpl();
		return srvc.listComaker(cifno, appno);
	}
	
	public String deleteComaker(Integer id) {
		PersonalReferenceService srvc = new PersonalReferenceServiceImpl();
		return srvc.deleteComaker(id);
	}
	
	public String saveOrUpdateComaker(Tbcomaker ref) {
		PersonalReferenceService srvc = new PersonalReferenceServiceImpl();
  		return srvc.saveOrUpdateComaker(ref);
  	}
	
	
}
