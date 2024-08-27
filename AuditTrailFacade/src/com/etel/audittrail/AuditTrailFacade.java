package com.etel.audittrail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cifsdb.data.AuditTrail;
import com.cifsdb.data.Tbauditevents;
import com.cifsdb.data.Tbaudittrail;
import com.coopdb.data.Tbuser;
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

	AuditTrailService srvc = new AuditTrailServiceImpl();
	
	public String addAuditLog(String event, String eventdesc, String module) {
		AuditLog.addAuditLog(event, eventdesc, UserUtil.securityService.getUserName(), new Date(), module);
		return "success";
	}

	public Tbauditevents auditCodetable(String moduleid, int eventid) {
		return srvc.auditCodetable(moduleid, eventid);
	}

	public List<Tbaudittrail> getListAuditTrail() {
		List<Tbaudittrail> audit = new ArrayList<Tbaudittrail>();
		audit = srvc.getListAuditTrail();
		return audit;
	}
	
	public String saveAudit(AuditTrail form) {
		return srvc.saveAudit(form);
	}
	
	public List<Tbuser> getListUsers() {
		return srvc.getListUsers();
	}
	
	public List<AuditTrail> getListAuditTrailUserActivities(String createdBy, String applicationStatus, Date dateCreated) {
		return srvc.getListAuditTrailUserActivities(createdBy, applicationStatus, dateCreated);
	}
	
	public List<AuditTrail> getListAuditTrailCompany(String createdBy, String applicationStatus, Date dateCreated) {
		return srvc.getListAuditTrailCompany(createdBy, applicationStatus, dateCreated);
	}
	
	public List<AuditTrail> getListAuditTrailMember(String createdBy, String applicationStatus, Date dateCreated) {
		return srvc.getListAuditTrailMember(createdBy, applicationStatus, dateCreated);
	}
	
	
}
