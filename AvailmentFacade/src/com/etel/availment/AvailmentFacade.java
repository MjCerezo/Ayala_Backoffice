package com.etel.availment;

import java.util.List;

import com.etel.availmentforms.ProductForm;
import com.etel.collateralforms.TbCollateralMainForm;
import com.etel.collateralforms.TbCollateralMainFormGroup;
//import com.coopdb.data.Tbcfcoobligor;
//import com.coopdb.data.Tbcfdetails;
import com.coopdb.data.Tbdocsperapplication;
import com.coopdb.data.Tblstcomakers;
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
public class AvailmentFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public AvailmentFacade() {
       //super(INFO);
    }
    
    AvailmentService srvc = new AvailmentServiceImpl();
    
    /***Creation And Encoding***/
//	public List<Tbcfdetails> listMainLine(String cifno) {
//		return srvc.listMainLine(cifno);
//	}
//	public List<Tbcfcoobligor> listCoobligagor(String cfrefno, String cfrefnoconcat, Boolean isChangeSetup) {
//		return srvc.listCoobligagor(cfrefno, cfrefnoconcat, isChangeSetup);
//	}
//	public List<Tbcfdetails> listSubFacility(String cfrefno, String cfrefnoconcat, Boolean isChangeSetup) {
//		return srvc.listSubFacility(cfrefno, cfrefnoconcat, isChangeSetup);
//	}	
//	public List<Tbcfdetails> getMainLine(String cifno, String cfrefno, String cfrefnoconcat) {
//		return srvc.getMainLine(cifno, cfrefno, cfrefnoconcat);
//	}
//	public String saveOrupdateAvailment(String appno, String cfrefno, String cfcode, String cfrefnoconcat){
//		return srvc.saveOrupdateAvailment(appno, cfrefno, cfcode, cfrefnoconcat);
//	}
//	public Tbcfdetails getMainLineOfAvailmentApp(String refno, String cifno, String cfrefnoconcat) {
//		return srvc.getMainLineOfAvailmentApp(refno, cifno, cfrefnoconcat);
//	}
//	public List<Tblstcomakers> getSuretyList(String liAppno, String avAppno){
//		return srvc.getSuretyList(liAppno, avAppno);
//	}
	public List<TbCollateralMainForm> listCollateralSingle(String liAppno, String avAppno, String collateraltype){
		return srvc.listCollateralSingle(liAppno, avAppno, collateraltype);
	}	
	public List<TbCollateralMainFormGroup> listCollateralGroup(String liAppno, String avAppno, String collateraltype){
		return srvc.listCollateralGroup(liAppno, avAppno, collateraltype);
	}
	public String changeAvailmentDetails(String appno, String cfrefno, String cifno, String cifname, String cfcode, String cfrefnoconcat){
		return srvc.changeAvailmentDetails(appno, cfrefno, cifno, cifname, cfcode, cfrefnoconcat);
	}
	public List<ProductForm> getListOfLoanProduct(String facilitycode) {
		return srvc.getListOfLoanProduct(facilitycode);
	}
	/***Documentation***/
    public String refreshDocChecklistPerApp(String appno){
    	return srvc.refreshDocChecklistPerApp(appno);
    }
    public String saveOrUpdateDocsPerApp(Tbdocsperapplication docsperapp){
    	return srvc.saveOrUpdateDocsPerApp(docsperapp);
    }
	
	
}
