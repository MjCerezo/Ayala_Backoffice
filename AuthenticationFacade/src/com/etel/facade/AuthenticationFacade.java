package com.etel.facade;

import com.etel.forms.FormValidation;
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
public class AuthenticationFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public AuthenticationFacade() {
       super(FATAL);
    }
    public String autheticateUser(String username, String password){
    	
    	AuthenticationService auth = new AuthenticationServiceImpl();
    	return auth.autheticateUser(username, password);
    }
    
    public String removeADLoginToken(String username, String password, String tag){
    	AuthenticationService auth = new AuthenticationServiceImpl();
    	return auth.removeADLoginToken(username, password, tag);
    }
    
    public String computePasswordAge(String username){
    	AuthenticationService auth = new AuthenticationServiceImpl();
    	return auth.computePasswordAge(username);
    }
    
    public Integer failedLoginCount(String username) {
    	AuthenticationService auth = new AuthenticationServiceImpl();
    	return auth.failedLoginCount(username);
    }
    
    public void success(String username){
    	AuthenticationService auth = new AuthenticationServiceImpl();
    	auth.success(username);
    }
    
    public String status(String username, String password) {
    	AuthenticationService auth = new AuthenticationServiceImpl();
    	return auth.status(username, password);
    }
    public FormValidation requestPassReset(String username, String emailAdd) {
    	AuthenticationService auth = new AuthenticationServiceImpl();
    	return auth.requestPassReset(username, emailAdd);   	
    }
    public FormValidation checkUserStatus(String username, String password) {
    	AuthenticationService auth = new AuthenticationServiceImpl();
    	return auth.checkUserStatus(username, password);
    }
    /**
	 * Terminate Existing Session - Send Email if lastIP is not equal to currentIP.
	 * @author Kevin
	 * @return String = success / failed
	 * */
    public String existingSession(String username) {
    	AuthenticationService auth = new AuthenticationServiceImpl();
    	return auth.existingSession(username);
    }
}
