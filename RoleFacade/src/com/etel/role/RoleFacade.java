package com.etel.role;

import java.util.List;

import com.etel.forms.FormValidation;
import com.etel.role.forms.AccessRightsForm;
import com.etel.role.forms.MenuForm;
import com.etel.role.forms.RoleAccessList;
import com.etel.role.forms.RoleForm;
import com.coopdb.data.Tbrole;
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
public class RoleFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public RoleFacade() {
       super(INFO);
    }
    
	public FormValidation addRole(RoleForm rolesForm) {
		RoleService service = new RoleServiceImpl();
		return service.addRole(rolesForm);
	}

	public FormValidation deleteRole(String roleid, String rolename) {
		RoleService service = new RoleServiceImpl();
		return service.deleteRole(roleid, rolename);		
	}

	public FormValidation saveRolesAccess(RoleAccessList roleAccess) {
		RoleService service = new RoleServiceImpl();
		return service.saveRolesAccess(roleAccess);
	}
	
	public List<Tbrole> listRoles() {
		RoleService service = new RoleServiceImpl();
		return service.listRoles();
	}
	
	public List<AccessRightsForm> listAccessRightsSubModule(String modulename) {
		RoleService service = new RoleServiceImpl();
		return service.listAccessRightsSubModule(modulename);	
	}
	
	public List<AccessRightsForm> getRoleAccess(String roleid, String modulename) {
		RoleService service = new RoleServiceImpl();
		return service.getRoleAccess(roleid, modulename);
	}
	public MenuForm getMenuAccessRights() {
		RoleService service = new RoleServiceImpl();
		return service.getMenuAccessRights();
	}
	public List<RoleForm> getListRoleId(){
		RoleService service = new RoleServiceImpl();
		return service.getListRoleId();
	}
}
