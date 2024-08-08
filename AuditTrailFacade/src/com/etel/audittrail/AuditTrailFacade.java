package com.etel.audittrail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cifsdb.data.Tbauditevents;
import com.cifsdb.data.Tbaudittrail;
import com.etel.utils.AuditLog;
import com.etel.utils.UserUtil;
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
public class AuditTrailFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level;
	 * recommend changing this to FATAL or ERROR before deploying. For info on these
	 * levels, look for tomcat/log4j documentation
	 */
	public AuditTrailFacade() {
		// super(INFO);
	}

	/**
	 * --Add Audit Log--
	 * 
	 * @param event     = e.g. "USER LOGIN"
	 * @param eventdesc = e.g. "User CIF login."
	 * @param module    = e.g. "SECURITY"
	 * @return String = "success" otherwise "failed"
	 */
	public String addAuditLog(String event, String eventdesc, String module) {
		AuditLog.addAuditLog(event, eventdesc, UserUtil.securityService.getUserName(), new Date(), module);
		return "success";
	}

	public Tbauditevents auditCodetable(String moduleid, int eventid) {
		AuditTrailService srvc = new AuditTrailServiceImpl();
		return srvc.auditCodetable(moduleid, eventid);
	}

	public List<Tbaudittrail> getListAuditTrail() {
		List<Tbaudittrail> audit = new ArrayList<Tbaudittrail>();
		AuditTrailService srvc = new AuditTrailServiceImpl();
		audit = srvc.getListAuditTrail();
		return audit;
	}
}
