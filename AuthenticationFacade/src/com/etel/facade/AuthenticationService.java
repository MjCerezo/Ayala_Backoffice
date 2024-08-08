package com.etel.facade;

import com.etel.forms.FormValidation;

public interface AuthenticationService {

	public String autheticateUser(String username, String password);
	public String removeADLoginToken(String username, String password, String tag);
	public String computePasswordAge(String username);
	public Integer failedLoginCount(String username);
	public void success(String username);
	public String status(String username, String password);
	public FormValidation requestPassReset(String username, String emailAdd);
	public FormValidation checkUserStatus(String username, String password);
	public String existingSession(String username);

}
