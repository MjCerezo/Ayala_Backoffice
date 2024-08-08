package com.etel.accessrights;

import java.util.List;

import com.etel.accessrights.forms.LAAccessRightsForm;
import com.etel.accessrights.forms.MembershipARForm;
import com.etel.accessrights.forms.ReadOnlyOrDisable;
import com.etel.accessrights.forms.RequestForm;
import com.etel.accessrights.forms.ResignationARForm;
import com.etel.accessrights.forms.StatusAndRoles;
import com.etel.accessrights.forms.UpdateProfileARForm;
import com.etel.role.forms.AccessRightsForm;
import com.etel.utils.MenuUtil;
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
public class AccessRightsFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log
	 * level; recommend changing this to FATAL or ERROR before deploying. For
	 * info on these levels, look for tomcat/log4j documentation
	 */
	private AccessRightsService service = new AccessRightsServiceImpl();

	public AccessRightsFacade() {
		// super(INFO);
	}

	public String addAccessRights(AccessRightsForm form) {
		return service.addAccessRights(form);
	}

	public String deleteAccessRights(String accessname) {
		return service.deleteAccessRights(accessname);
	}

	public List<AccessRightsForm> searchAccess(String parameter) {
		return service.searchAccess(parameter);
	}

	public List<AccessRightsForm> searchAccessWithModulename(String parameter, String modulename) {
		return service.searchAccessWithModulename(parameter, modulename);
	}

	public String updateAccessRights(AccessRightsForm form) {
		return service.updateAccessRights(form);
	}

	public MembershipARForm membershipAppButtonsAccess(String membershipappid, String submitoption) {
		return new MembershipARForm(membershipappid, submitoption);
	}

	public ResignationARForm resignationAppButtonAccess(String memberid, String submitoption) {
		return new ResignationARForm(memberid, submitoption);
	}

	public String getOfficer() {
		return service.getOfficer();
	}

	public LAAccessRightsForm getLoanApplicationAccess(String appno) {
		return service.getLoanApplicationAccess(appno);
	}

	public String completeInvestigationAccess(String appno) {
		return service.completeInvestigationAccess(appno);
	}

	public UpdateProfileARForm getUpdateProfileAccessRights(Integer txrefno) {
		return new UpdateProfileARForm(txrefno);
	}

	public String getMenuAccessRights(String paraccesstype, String modulecode, String chaccesstype) {
		return MenuUtil.MenuTreeBuilder(paraccesstype, modulecode, chaccesstype);
	}
	
	public StatusAndRoles getStatusAndRoles(String cifno) {
		return service.getStatusAndRoles(cifno);
	}
	
	public ReadOnlyOrDisable disabledOrReadOnly(String cifno) {
		AccessRightsService service = new AccessRightsServiceImpl();
		return service.disabledOrReadOnly(cifno);
	}
	
	//Renz
	public RequestForm getRequestAccessRight(String cifno, String requestid, String changetype) {
		AccessRightsService service = new AccessRightsServiceImpl();
		return service.getRequestAccessRight(cifno, requestid, changetype);
	}
	public LAAccessRightsForm getLASAccess(String appno, Integer applicationtype) {
		return service.getLASAccess(appno, applicationtype);
	}
	//12-11-2020
	public List<AccessRightsForm> listAccessRightsByAccessType(String module, String accesstype){
		AccessRightsService service = new AccessRightsServiceImpl();
		return service.listAccessRightsByAccessType(module, accesstype);
	}
	 public List<AccessRightsForm> listAccessRights(String module){
		 AccessRightsService service = new AccessRightsServiceImpl();
			return service.listAccessRights(module);
	 }
}
