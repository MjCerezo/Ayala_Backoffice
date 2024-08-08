package com.casa.user;

import java.math.BigDecimal;

import com.casa.user.forms.UserDetailForm;
import com.coopdb.data.Tbbranch;
import com.coopdb.data.Tbunit;
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
public class UserInfoFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public UserInfoFacade() {
       super(INFO);
    }

    UserInfoService service = new UserInfoServiceImpl();
	
	public UserDetailForm getUserinfo(String userid) {
		UserDetailForm user = service.getUserinfo(userid);
		return user;
	}

	public BigDecimal getUnitBalance(String userid, String currency, String transfertype) {
		BigDecimal amount = service.getUnitBalance(userid, currency, transfertype);
		return amount;
	}
	
	public Tbbranch getUnitinfo() {
		Tbbranch unit = service.getUnitinfo();
		return unit;
	}
	
}
