package com.etel.investigation;

import java.util.List;

import com.coopdb.data.Tbinvestigationresults;
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
public class InvestigationFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
	private InvestigationService srvc = new InvestigationServiceImpl();
    public InvestigationFacade() {
//       super(INFO);
    }
    
    public String saveOrUpdateInvestigation(Tbinvestigationresults inv, String invtype) {
    	return srvc.saveOrUpdateInvestigation(inv, invtype);
    }

    public List<Tbinvestigationresults> getInvestigationResList(String appno, String cifno, String invtype){
    	return srvc.getInvestigationResList(appno, cifno, invtype);
    }
    public String generateInvestigationReport(String appno) {
    	return srvc.generateInvestigationReport(appno);
    }
	public Tbinvestigationresults getInvestigationResPerType(String appno, String cifno, String invtype,
			String participationcode) {
		return srvc.getInvestigationResPerType(appno, cifno, invtype, participationcode);
	}
}
