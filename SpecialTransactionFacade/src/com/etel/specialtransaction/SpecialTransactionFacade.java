package com.etel.specialtransaction;

import java.util.List;

import com.cifsdb.data.ChangeMemberStatus;
import com.cifsdb.data.ChangeMemberType;
import com.etel.specialtransaction.form.ChangeMemberStatusForm;
import com.etel.specialtransaction.form.TMPFrom;
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
public class SpecialTransactionFacade extends JavaServiceSuperClass {
	SpecialTransactionService service = new SpecialTransactionServiceImpl();
	
	//MemberStatus
	public List<TMPFrom> getChangeMemberStatusDashboard() {
		return service.getChangeMemberStatusDashboard();
	}
	
	public List<ChangeMemberStatusForm> changeMemberStatusListPerStages(String branch, String search, String applicationStatus, String daysCount) {
		return service.changeMemberStatusListPerStages(branch, search, applicationStatus, daysCount);
	}
	
	public ChangeMemberStatus viewChangeMemberStatus(String trn) {
  		return service.viewChangeMemberStatus(trn);
  	}
	
	public String saveChangeMemberStatus(ChangeMemberStatus form) {
  		return service.saveChangeMemberStatus(form);
  	}
	
	public String approvedDeclinedChangeMemberStatus(String cifNo, String trn, String status) {
  		return service.approvedDeclinedChangeMemberStatus(cifNo, trn, status);
  	}
	
	//MemberType
	public List<TMPFrom> getChangeMemberTypeDashboard() {
		return service.getChangeMemberTypeDashboard();
	}
	
	public List<ChangeMemberStatusForm> changeMemberTypeListPerStages(String branch, String search, String applicationStatus, String daysCount) {
		return service.changeMemberTypeListPerStages(branch, search, applicationStatus, daysCount);
	}
	
	public ChangeMemberType viewChangeMemberType(String trn) {
  		return service.viewChangeMemberType(trn);
  	}
	
	public String saveChangeMemberType(ChangeMemberType form) {
  		return service.saveChangeMemberType(form);
  	}
	
	public String approvedDeclinedChangeMemberType(String cifNo, String trn, String status) {
  		return service.approvedDeclinedChangeMemberType(cifNo, trn, status);
  	}
}
