package com.etel.role;

import java.util.List;

import com.etel.forms.FormValidation;
import com.etel.role.forms.AccessRightsForm;
import com.etel.role.forms.MenuForm;
import com.etel.role.forms.RoleAccessList;
import com.etel.role.forms.RoleForm;
import com.coopdb.data.Tbrole;

public interface RoleService {
	FormValidation addRole(RoleForm rolesForm);
	FormValidation deleteRole(String roleid, String rolename);
	FormValidation saveRolesAccess(RoleAccessList roleAccess);
	List<AccessRightsForm> getRoleAccess(String roleid, String modulename);
	List<Tbrole> listRoles();
	List<AccessRightsForm> listAccessRightsSubModule(String modulename);
	MenuForm getMenuAccessRights();
	List<RoleForm> getListRoleId();
}
