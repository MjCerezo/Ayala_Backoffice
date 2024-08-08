package com.etel.cifgroup;

import java.util.List;

import com.cifsdb.data.Tbcifgroup;
import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
import com.wavemaker.runtime.service.annotations.ExposeToClient;

/**
 * This is a client-facing service class. All public methods will be exposed to
 * the client. Their return values and parameters will be passed to the client
 * or taken from the client, respectively. This will be a singleton instance,
 * shared between all requests.
 * 
 * To log, call the superclass method log(LOG_LEVEL, String) or log(LOG_LEVEL,
 * String, Exception). LOG_LEVEL is one of FATAL, ERROR, WARN, INFO and DEBUG to
 * modify your log level. For info on these levels, look for tomcat/log4j
 * documentation
 */
@ExposeToClient
public class CIFGroupFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log
	 * level; recommend changing this to FATAL or ERROR before deploying. For
	 * info on these levels, look for tomcat/log4j documentation
	 */
	public CIFGroupFacade() {
		super(INFO);
	}

	/** Display Group values from TBCIFGROUP */
	public List<Tbcifgroup> displayCIFGroupDetails(String groupcode) {
		CIFGroupService srvc = new CIFGroupServiceImpl();
		return srvc.displayCIFGroupDetails(groupcode);
	}

	/** Add CIF Group values from TBCIFGROUP */
	public String addCIFGroup(Tbcifgroup group) {
		CIFGroupService srvc = new CIFGroupServiceImpl();
		return srvc.addCIFGroup(group);
	}

	/** Delete CIF GROUP FROM TBCIFGROUP */
	public String deleteCIFGroup(Tbcifgroup groupcode) {
		CIFGroupService srvc = new CIFGroupServiceImpl();
		return srvc.deleteCIFGroup(groupcode);
	}

	/** Update value to TBCIFGROUP */
	public String updateCIFGroup(Tbcifgroup group) {
		CIFGroupService srvc = new CIFGroupServiceImpl();
		return srvc.updateCIFGroup(group);
	}
	public List<Tbcifgroup> getListCIFGroup(){
		CIFGroupService srvc = new CIFGroupServiceImpl();
		return srvc.getListCIFGroup();
	}

}