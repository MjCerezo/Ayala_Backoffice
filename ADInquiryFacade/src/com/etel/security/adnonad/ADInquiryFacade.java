package com.etel.security.adnonad;

import javax.naming.NamingException;

import com.etel.forms.ADUser;
import com.etel.forms.UserForm;
import com.etel.security.adnonad.forms.CreateUserForm;
import com.coopdb.data.Tbuser;
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
public class ADInquiryFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log
	 * level; recommend changing this to FATAL or ERROR before deploying. For
	 * info on these levels, look for tomcat/log4j documentation
	 */
	public ADInquiryFacade() {
		super(FATAL);
	}

	public Tbuser searchADUser(String username) throws NamingException {

		ADInquiryService adInq = new ADInquiryServiceImpl();
		return adInq.searchADUser(username);
	}

	public boolean addToLosUser(ADUser adUser) {
		ADInquiryService adInq = new ADInquiryServiceImpl();
		if (adInq.addToLosUser(adUser)) {
			return true;
		} else {
			return false;
		}
	}

	public UserForm searchDBUser(String username) {
		ADInquiryService adInq = new ADInquiryServiceImpl();

		return adInq.searchDBUser(username);
	}

	public boolean saveEditUserAndRoles(UserForm userForm, String tag) {
		ADInquiryService adInq = new ADInquiryServiceImpl();
		return adInq.saveEditUserAndRoles(userForm,tag);
	}

	public boolean deactivateUser(String username) {
		ADInquiryService adInq = new ADInquiryServiceImpl();
		return adInq.deactivateUser(username);
	}

	public CreateUserForm saveDbUser(UserForm userForm) {
		ADInquiryService adInq = new ADInquiryServiceImpl();
		return adInq.saveDbUser(userForm);
	}
	
	public CreateUserForm saveMemberCredentials(String cifNo, String role) {
		ADInquiryService adInq = new ADInquiryServiceImpl();
		return adInq.saveMemberCredentials(cifNo, role);
	}
	
	public CreateUserForm createCompanyCredentials(String cifNo, String role) {
		ADInquiryService adInq = new ADInquiryServiceImpl();
		return adInq.createCompanyCredentials(cifNo, role);
	}

}
