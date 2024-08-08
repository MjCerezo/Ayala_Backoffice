package com.etel.dedupe;
import java.util.Date;
import java.util.List;

import com.etel.dedupeforms.InquiryCount;
import com.etel.dedupeforms.MembershipDedupeForm;
import com.etel.dedupeforms.amladedupeform;
import com.etel.dedupeforms.blacklistdedupeform;
import com.etel.dedupeforms.cifdedupeform;
import com.etel.dedupeforms.dedupeform;
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
public class CIFDedupe extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public CIFDedupe() {
       super(INFO);
    }
    CIFDedupeService srvc = new CIFDedupeServiceImpl();
    
    public dedupeform dedupeIndiv(String lname, String fname, String mname, String suf, Date dob) {
    	return srvc.dedupeIndiv(lname, fname, mname, suf, dob);
    }
    public dedupeform dedupeCorp(String businessname, Date incorporationdate, String soleProp) {
    	return srvc.dedupeCorp(businessname, incorporationdate, soleProp);
    }
    public dedupeform dedupeIndividual(String lname, String fname, Date dob, String tin, String sss, 
    		String streetno, String subdivision, String country, String province, String city, String barangay, String postalCode, String losLink) {
    	return srvc.dedupeIndividual(lname, fname, dob, tin, sss, 
        		streetno, subdivision, country, province, city, barangay, postalCode, losLink);
    }    
    public dedupeform dedupeCorporate(String businessname, Date incorporationdate, String tin, String sss, 
    		String streetno, String subdivision, String country, String province, String city, String barangay, String postalCode, String corpOrSoleProp, String losLink) {
    	return srvc.dedupeCorporate(businessname, incorporationdate, tin, sss, 
        		streetno, subdivision, country, province, city, barangay, postalCode, corpOrSoleProp, losLink);
    }      
    public String checkIfExactMatch(String cifno, String lname, String fname, Date dob, String businessname, Date incorporationdate,  String tin, String sss, 
    		String streetno, String subdivision, String country, String province, String city, String barangay, String postalCode) {
    	return srvc.checkIfExactMatch(cifno, lname, fname, dob, businessname, incorporationdate,  tin, sss, 
        		streetno, subdivision, country, province, city, barangay, postalCode);
    }     
    public dedupeform dedupeIndivFinal(String lname, String fname, String mname, String suf, Date dob, Integer page,
			Integer maxresult, String customertype, String inquirytype, String businessname, Date incorporationdate) {
    	return srvc.dedupeIndivFinal(lname, fname, mname, suf, dob, page, maxresult, customertype, inquirytype, businessname, incorporationdate);
    }    
    public InquiryCount dedupeIndivFinalCount(String lname, String fname, String mname, String suf, Date dob, Integer page,
			Integer maxresult, String customertype, String inquirytype) {
    	return srvc.dedupeIndivFinalCount(lname, fname, mname, suf, dob, page, maxresult, customertype, inquirytype);
    } 
    public List<cifdedupeform> listCifdedupeform (List<cifdedupeform> form){
    	return form;
    }
    public List<amladedupeform> listAmladedupeform (List<amladedupeform> form){
    	return form;
    }    
    public List<blacklistdedupeform> listBlkdedupeform (List<blacklistdedupeform> form){
    	return form;
    }    
    public List<MembershipDedupeForm> membershipDedupe (List<MembershipDedupeForm> form){
    	return form;
    }    
    

}
