package com.etel.relationship;

import java.util.List;

import com.cifsdb.data.Tbcustomerrelationship;
import com.etel.codetable.forms.CodetableForm;
import com.etel.forms.FormValidation;
import com.etel.relationship.forms.Relationshipform;
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
public class RelationshipFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
	RelationshipService service = new RelationshipServiceImpl();
    public RelationshipFacade() {
       //super(INFO);
    }

//    public String saveRelationship(String cifno) {
//		return service.saveRelationship(cifno);
//	}
    
    public List<Relationshipform> getListOfCustRelationShip(String cifno, Boolean isconcatenated) {
		return service.getListOfCustRelationShip(cifno, isconcatenated);
	}
    
    public FormValidation addRelationship(Tbcustomerrelationship rel, String addOrUpdateFlag) {
		return service.addRelationship(rel, addOrUpdateFlag);
	}
    public FormValidation deleteRelationship(Integer id) {
		return service.deleteRelationship(id);
	}
    public List<CodetableForm> getRelationshipCode() {
    	return service.getRelationshipCode();
    }
//    public String AddCustTraderef(String cifno,String tradecifno,String tradetype){
//    	return service.AddCustTraderef(cifno,tradecifno,tradetype);
//    }
    
    //MAR 10-23-2020
    public String AddCustTraderefRB(String cifno,String tradecifno,String tradetype){
    	return service.AddCustTraderefRB(cifno,tradecifno,tradetype);
    }
    public List<Relationshipform> getListOfCustRelationShipRB(String cifno, Boolean isconcatenated) {
		return service.getListOfCustRelationShipRB(cifno, isconcatenated);
	}
}
