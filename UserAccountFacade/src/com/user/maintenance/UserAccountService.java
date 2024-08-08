package com.user.maintenance;

import java.util.List;

import com.coopdb.data.Tbcodetablecasa;
import com.coopdb.data.Tbroleaccess;
import com.coopdb.data.Tbterminal;
import com.coopdb.data.Tbuser;
import com.etel.security.forms.TBRoleForm;
import com.user.maintenance.form.AccessRights;
import com.user.maintenance.form.MainSubSelectModuleForm;
import com.user.maintenance.form.UserEditForm;
import com.user.maintenance.form.UserListForm;

public interface UserAccountService {

	List<Tbcodetablecasa> roleList();

	List<AccessRights> accessRightsMain();

	List<AccessRights> accessRightsSub();

	List<String> pageList();

	List<Tbroleaccess> roleAccessList(String role);

	String addRoleAccess(Tbroleaccess rec);

	String deleteRoleAccess(int id);

	String addRole(Tbcodetablecasa rec);

	String deleteRole(int id);

	List<Tbcodetablecasa> mainMod();

	List<Tbcodetablecasa> subMod();

	MainSubSelectModuleForm selectMainSub();

	String addModule(Tbcodetablecasa rec);

	String deleteModule(int id);

	String createUser(Tbuser user);

	List<UserListForm> userList(String username, String instcode);

	Tbuser userInfo(int id);

	String editUser(UserEditForm form);

	List<Tbterminal> terminalList(String unitid);

	List<TBRoleForm> getUserRolesByUsername(String username);

	Tbuser getUserByUsername(String username);

	String saveUserGLAccount(String username, String glcode);

}
