package com.etel.memberappinquiry;

import java.util.List;

import com.coopdb.data.Tbmembershipapp;
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
public class MembershipAppInquiryFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public MembershipAppInquiryFacade() {
       //super(INFO);
    }
    private MembershipAppInquiryService srvc = new MembershipAppInquiryServiceImpl();
    
    public List<Tbmembershipapp> searchMembershipApp(String membershipappid, String membershipappstatus,String lname, String fname, String mname,String encodedby, String membershipclass , String companycode , Integer page, Integer maxResult) {
    	return srvc.searchMembershipApp(membershipappid, membershipappstatus, lname, fname, mname, encodedby, membershipclass, companycode, page, maxResult);
    }
    
    public Integer getMembershippAppTotalResult(String membershipappid, String membershipappstatus,String lname, String fname, String mname,String encodedby, String membershipclass , String companycode) {
    	return srvc.getMembershippAppTotalResult(membershipappid, membershipappstatus, lname, fname, mname, encodedby, membershipclass, companycode);
    }
}
