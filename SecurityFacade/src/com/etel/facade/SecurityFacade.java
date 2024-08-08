
package com.etel.facade;

import java.util.List;

import com.coopdb.data.Tbsecurityparams;
import com.coopdb.data.Tbuser;
import com.etel.forms.FormValidation;
import com.etel.forms.UserForm;
import com.etel.security.forms.TBRoleForm;
import com.etel.utils.UserUtil;
import com.wavemaker.runtime.RuntimeAccess;
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
public class SecurityFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level;
	 * recommend changing this to FATAL or ERROR before deploying. For info on these
	 * levels, look for tomcat/log4j documentation
	 */
	public SecurityFacade() {
		super(FATAL);
	}

	public List<Tbuser> getListofUsersPerUsernameOrRole(String username, String rolecode, String coopcode,
			String branch, String company, String groupcode, Integer page, Integer maxResult) {
		SecurityService secsrvc = new SecurityServiceImpl();
		return secsrvc.getListofUsersPerUsernameOrRole(username, rolecode, coopcode, branch, company, groupcode, page,
				maxResult);
	}

	public Integer getUsersTotal(String username, String rolecode, String groupcode, String coopcode, String branch,
			String company) {
		SecurityService secsrvc = new SecurityServiceImpl();
		return secsrvc.getUsersTotal(username, rolecode, groupcode, coopcode, branch, company);
	}

	// get Roles
	public List<TBRoleForm> getListofRoles() {
		SecurityService secsrvc = new SecurityServiceImpl();
		return secsrvc.getListofRoles();
	}

	public Boolean checkUsername(String username) {
		SecurityService secsrvc = new SecurityServiceImpl();
		return secsrvc.checkUsername(username);
	}

	public UserForm getUserAccount(String username) {
		UserForm useraccount = new UserForm();
		SecurityService secsrvc = new SecurityServiceImpl();
		useraccount = secsrvc.getUserAccount(username);
		return useraccount;
	}

	public String saveUserAccount(Tbuser useracct, List<TBRoleForm> roles) {
		String result = "Failed";
		SecurityService secsrvc = new SecurityServiceImpl();
		result = secsrvc.saveUserAccount(useracct, roles);
		return result;
	}

	public FormValidation validate(Tbuser useracct, TBRoleForm role, String newOrEdited) {
		SecurityService secsrvc = new SecurityServiceImpl();
		return secsrvc.validate(useracct, role, newOrEdited);
	}

	public FormValidation validatePassword(String password, String username) {
		SecurityService secsrvc = new SecurityServiceImpl();
		return secsrvc.validatePassword(password, username);
	}

	public String validateOldPassword(String password, String username) {
		SecurityService secsrvc = new SecurityServiceImpl();
		return secsrvc.validateOldPassword(password, username);
	}

	public String changePassword(String username, String password) {
		SecurityService secsrvc = new SecurityServiceImpl();
		return secsrvc.changePassword(username, password);
	}

	public String datetimerecord() {
		SecurityService secsrvc = new SecurityServiceImpl();
		return secsrvc.datetimerecord();
	}

	public String saveSecurityParams(Tbsecurityparams params) {
		SecurityService secsrvc = new SecurityServiceImpl();
		return secsrvc.saveSecurityParams(params);
	}

	public Tbsecurityparams getSecurityParams() {
		SecurityService secsrvc = new SecurityServiceImpl();
		return secsrvc.getSecurityParams();
	}

	public String resetUserSession() {
		SecurityService secsrvc = new SecurityServiceImpl();
		return secsrvc.resetUserSession();
	}

	public List<Tbuser> filterDeletedAcct(String search) {
		SecurityService secsrvc = new SecurityServiceImpl();
		return secsrvc.filterDeletedAcct(search);
	}

	public String enableUserAcct(String username) {
		SecurityService secsrvc = new SecurityServiceImpl();
		return secsrvc.enableUserAcct(username);
	}

	public String getUserFirstname() throws Exception {
		String name = null;
		name = UserUtil.getUserByUsername(RuntimeAccess.getInstance().getRequest().getUserPrincipal().getName())
				.getFirstname();
		if (name == null) {
			name = "";
		}
		return name;
	}

	public String getUserAgreementMessage() {
		SecurityService secsrvc = new SecurityServiceImpl();
		return secsrvc.getUserAgreementMessage();
	}

	public FormValidation resetPassword(String username) {
		SecurityService secsrvc = new SecurityServiceImpl();
		return secsrvc.resetPassword(username);
	}

	public boolean userSessionCheck() {
		return UserUtil.userSessionCheck();
	}

	public boolean validateMaxNoOfUser() {
		return UserUtil.isMaxNumberOfUserReached();
	}

	public FormValidation resendEmail(String username, String changepass, String type) {
		SecurityService secsrvc = new SecurityServiceImpl();
		return secsrvc.resendEmail(username, changepass, type);
	}

	public List<Tbuser> getListOfUserPerParam(String roleid, String company, String branch, String username) {
		SecurityService secsrvc = new SecurityServiceImpl();
		return secsrvc.getListOfUserPerParam(roleid, company, branch, username);
	}
}
