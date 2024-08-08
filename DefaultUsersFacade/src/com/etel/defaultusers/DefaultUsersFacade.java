package com.etel.defaultusers;

import java.util.List;

import com.etel.defaultusers.forms.ApprovalMatrixForm;
import com.etel.defaultusers.forms.CompanyName;
import com.etel.defaultusers.forms.DefaultUsers;
import com.etel.utils.UserUtil;
import com.coopdb.data.Tbapprovalmatrix;
import com.coopdb.data.Tbdefaultusers;
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
public class DefaultUsersFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log
	 * level; recommend changing this to FATAL or ERROR before deploying. For
	 * info on these levels, look for tomcat/log4j documentation
	 */
	
	private DefaultUsersService srvc = new DefaultUsersServiceImpl();
	
	public DefaultUsersFacade() {
		//super(INFO);
	}

	/***** 12-04-17 - Noreen Default Users Maintenance ***/

	public List<Tbdefaultusers> displayDefaultUsers() {
		DefaultUsersService srvc = new DefaultUsersServiceImpl();
		return srvc.displayDefaultUsers();
	}

	public List<Tbuser> listUsernamePerRole(String roleid,String companycode) {
		DefaultUsersService srvc = new DefaultUsersServiceImpl();
		return srvc.listUsernamePerRole(roleid, companycode);
	}

	public String saveUpdateDefaultUsers(Tbdefaultusers defaultusers) {
		DefaultUsersService srvc = new DefaultUsersServiceImpl();
		return srvc.saveUpdateDefaultUsers(defaultusers);
	}

	public List<CompanyName> listCompany() {
		DefaultUsersService srvc = new DefaultUsersServiceImpl();
		return srvc.listCompany();
	}

	public String deleteDefaultUsers(String companycode) {
		DefaultUsersService srvc = new DefaultUsersServiceImpl();
		return srvc.deleteDefaultUsers(companycode);
	}
	
	/**
	 * ---Get List of Users---<br>
	 * Added Dec.12,2017
	 * @author Kevin
	 * @return List<{@link Tbuser}>
	 * */
	public List<Tbuser> getListofUser(){
		return UserUtil.getListofUser();
	}
	/**
	 * ---Get List of Users by Company---<br>
	 * Added Dec.12,2017
	 * @author Kevin
	 * @return List<{@link Tbuser}>
	 * */
	public List<Tbuser> getListofUserByCompany(String companycode){
		return UserUtil.getListofUserByCompany(companycode);
	}
	/**
	 * ---Get Default Users by Company---<br>
	 * Added Dec.20,2017
	 * @author Kevin
	 * @return @link DefaultUsers
	 * */
	public DefaultUsers getDefaultUserByCompany(String companycode){
		DefaultUsers def = new DefaultUsers();
		if(companycode != null){
			def = new DefaultUsers(companycode);
		}
		return def;
	}
	
	// ABBY -08/29/2018
	//FOR APPROVAL MATRIX MAINTENANCE
	
	public String saveOrUpdateApprovalMatrix(ApprovalMatrixForm form){
		return srvc.saveOrUpdateApprovalMatrix(form);
	}
	
	public Tbapprovalmatrix getApprovalMatrixRecord(Integer id){
		return srvc.getApprovalMatrixRecord(id);
	}
	public List<Tbapprovalmatrix> getListOfApprovalMatrix(){
		return srvc.getListOfApprovalMatrix();
	}
	
	public String deleteApprovalMatrix(Integer id){
		return srvc.deleteApprovalMatrix(id);
	}

}
