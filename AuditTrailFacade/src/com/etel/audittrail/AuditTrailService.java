package com.etel.audittrail;

import java.util.List;

import com.cifsdb.data.Tbauditevents;
import com.cifsdb.data.Tbaudittrail;

public interface AuditTrailService {

	public Tbauditevents auditCodetable(String moduleid,int eventid);

	public List<Tbaudittrail> getListAuditTrail();

}
