package com.etel.audittrail;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cifsdb.data.AuditTrail;
import com.cifsdb.data.Tbauditevents;
import com.cifsdb.data.Tbaudittrail;
import com.coopdb.data.Tbuser;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.common.service.DBServiceImplCIF;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;
import com.etel.utils.HQLUtil;

public class AuditTrailServiceImpl implements AuditTrailService {
	
	private DBService dbService = new DBServiceImpl();
	private DBService dbServiceCIF = new DBServiceImplCIF();
	Map<String, Object> params = HQLUtil.getMap();
	public static SecurityService securityService = (SecurityService) RuntimeAccess.getInstance()
			.getServiceBean("securityService");

	@Override
	public Tbauditevents auditCodetable(String moduleid,int eventid) {
		Tbauditevents form = new Tbauditevents();
		DBService dbsrvc = new DBServiceImplCIF();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("moduleid", moduleid);
		params.put("eventid",eventid);
		try {
			form = (Tbauditevents)dbsrvc.executeUniqueHQLQuery("From Tbauditevents where eventid=:eventid and moduleid=:moduleid", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbaudittrail> getListAuditTrail() {
		List<Tbaudittrail> audit = new ArrayList<Tbaudittrail>();
		DBService dbsrvc = new DBServiceImplCIF();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			audit = (List<Tbaudittrail>)dbsrvc.executeListHQLQuery("From Tbaudittrail", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return audit;
	}

	@Override
	public String saveAudit(AuditTrail form) {
		try {
			
			form.setDateCreated(new Date());
			if(securityService.getUserName() != null) {
				form.setCreatedBy(securityService.getUserName());
			}
			
			if (dbServiceCIF.save(form)) {
				return "Success";
			} else {
				return "There was a problem saving your request";
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		return "There was a problem saving your request";
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbuser> getListUsers() {
		List<Tbuser> list = new ArrayList<Tbuser>();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			params.put("isactive", "1");
			params.put("isdisabled", "0");
			list = (List<Tbuser>)dbService.executeListSQLQuery("SELECT username FROM TBUSER WHERE isactive = 1 AND isdisabled = 0 ", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AuditTrail> getListAuditTrailUserActivities(String createdBy, String applicationStatus,
			Date dateCreated) {
		List<AuditTrail> list = new ArrayList<AuditTrail>();
		Map<String, Object> params = HQLUtil.getMap();		
		try {
			params.put("createdBy", createdBy);
			params.put("applicationStatus", applicationStatus);
			params.put("dateCreated", dateCreated);
			list = (List<AuditTrail>) dbServiceCIF.execStoredProc(
					"EXEC sp_Audit_Trail_User_Activities :createdBy, :applicationStatus, :dateCreated ", params,
					AuditTrail.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<AuditTrail> getListAuditTrailCompany(String createdBy, String applicationStatus, Date dateCreated) {
		List<AuditTrail> list = new ArrayList<AuditTrail>();
		Map<String, Object> params = HQLUtil.getMap();		
		try {
			params.put("createdBy", createdBy);
			params.put("applicationStatus", applicationStatus);
			params.put("dateCreated", dateCreated);
			list = (List<AuditTrail>) dbServiceCIF.execStoredProc(
					"EXEC sp_Audit_Trail_Company :createdBy, :applicationStatus, :dateCreated ", params,
					AuditTrail.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuditTrail> getListAuditTrailMember(String createdBy, String applicationStatus, Date dateCreated) {
		List<AuditTrail> list = new ArrayList<AuditTrail>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("createdBy", createdBy);
			params.put("applicationStatus", applicationStatus);
			params.put("dateCreated", dateCreated);
			list = (List<AuditTrail>) dbServiceCIF.execStoredProc(
					"EXEC sp_Audit_Trail_Member :createdBy, :applicationStatus, :dateCreated ", params,
					AuditTrail.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
