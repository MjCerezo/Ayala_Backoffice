package com.etel.audittrail;

import java.util.Date;
import java.util.List;

import com.cifsdb.data.AuditTrail;
import com.cifsdb.data.Tbauditevents;
import com.cifsdb.data.Tbaudittrail;
import com.coopdb.data.Tbuser;

public interface AuditTrailService {

	public Tbauditevents auditCodetable(String moduleid,int eventid);

	public List<Tbaudittrail> getListAuditTrail();
	
	public String saveAudit(AuditTrail form);
	
	public List<Tbuser> getListUsers();
	
	public List<AuditTrail> getListAuditTrailUserActivities(String createdBy, String applicationStatus, Date dateCreated);
	
	public List<AuditTrail> getListAuditTrailCompany(String createdBy, String applicationStatus, Date dateCreated);
	
	public List<AuditTrail> getListAuditTrailMember(String createdBy, String applicationStatus, Date dateCreated);

}
