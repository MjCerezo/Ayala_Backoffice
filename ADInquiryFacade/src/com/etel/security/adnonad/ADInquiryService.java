package com.etel.security.adnonad;

import javax.naming.NamingException;

import com.etel.forms.ADUser;
import com.etel.forms.UserForm;
import com.etel.security.adnonad.forms.CreateUserForm;
import com.coopdb.data.Tbuser;

public interface ADInquiryService {

	public Tbuser searchADUser(String username) throws NamingException;

	public boolean addToLosUser(ADUser adUser);

	public UserForm searchDBUser(String username);

	public boolean saveEditUserAndRoles(UserForm userForm, String tag);

	public boolean deactivateUser(String username);

	public CreateUserForm saveDbUser(UserForm userForm);
	
	public CreateUserForm saveMemberCredentials(String cifNo, String role);
	
}
