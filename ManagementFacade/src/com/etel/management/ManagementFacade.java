package com.etel.management;

import java.util.List;

import com.cifsdb.data.Tbmanagement;
import com.coopdb.data.Tbmanagementlos;
import com.etel.managementforms.ManagementForm;
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
public class ManagementFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public ManagementFacade() {
       //super(INFO);
    }

    ManagementService srvc = new ManagementServiceImpl();
    
	public String setupManagement(ManagementForm form) {
		return srvc.setupManagement(form);
	}
	public List<Tbmanagement> mngmtList(String cifno, String pcode, String shaCustType, Integer id) {
		return srvc.mngmtList(cifno, pcode, shaCustType, id); 									
	}	
	public List<Tbmanagement> mngmtList2(String cifno, String oldBtype) {
		return srvc.mngmtList2(cifno, oldBtype); 									
	}	
	public String updateManagement(Tbmanagement mg) {
		return srvc.updateManagement(mg); 							
	}	
	public String deleteManagement(int id){
		return srvc.deleteManagement(id);
	}		
	public Tbmanagement getManagementRecord(String cifno, int id){
		return srvc.getManagementRecord(cifno, id);
	}
	public String deleteUnusedBusinessType(String cifno, String oldBtype){
		return srvc.deleteUnusedBusinessType(cifno, oldBtype);
	}
	public String updateBusinessType(String cifno, String btype) {
		return srvc.updateBusinessType(cifno, btype); 							
	}	
	public String checkIfExisting(String cifno, String relatedcifno, String relcode, String ofcrposition,String poscategory) {
		return srvc.checkIfExisting(cifno, relatedcifno, relcode, ofcrposition,poscategory); 							
	}	
	public Tbmanagement getGenManager(String cifno){
		return srvc.getGenManager(cifno);
	}	
	//MAR 10-23-2020
	public List<Tbmanagementlos> mngmtList2RB(String cifno, String oldBtype) {
		return srvc.mngmtList2RB(cifno, oldBtype); 									
	}	
	public String deleteUnusedBusinessTypeRB(String cifno, String oldBtype){
		return srvc.deleteUnusedBusinessTypeRB(cifno, oldBtype);
	}
	
	// no dedupe Renz02152020
	public List<Tbmanagement> listRegOwner(String cifno) {
		return srvc.listRegOwner(cifno);
	}
	
	public List<Tbmanagement> listPartners(String cifno) {
		return srvc.listPartners(cifno);
	}
	
	public List<Tbmanagement> listDirectorsTrustees(String cifno) {
		return srvc.listDirectorsTrustees(cifno);
	}
	
	public List<Tbmanagement> listOfficers(String cifno) {
		return srvc.listOfficers(cifno);
	}
	
	public List<Tbmanagement> listSignatories(String cifno) {
		return srvc.listSignatories(cifno);
	}
	
	public List<Tbmanagement> listShareholdersIndiv(String cifno) {
		return srvc.listShareholdersIndiv(cifno);
	}
	
	public List<Tbmanagement> listShareholdersCorp(String cifno) {
		return srvc.listShareholdersCorp(cifno);
	}
	
	public List<Tbmanagement> listGenMngr(String cifno) {
		return srvc.listGenMngr(cifno);
	}
	
	public String saveOrUpdateRegOwner(Tbmanagement ref) {	
  		return srvc.saveOrUpdateRegOwner(ref);
  	}
	
	public String deleteRegOwner(Integer id) {
		return srvc.deleteRegOwner(id);
	}
	
	public String saveOrUpdateDir(Tbmanagement ref) {	
  		return srvc.saveOrUpdateDir(ref);
  	}
	
	public String deleteDir(Integer id) {
		return srvc.deleteDir(id);
	}
	
	public String saveOrUpdateOfficer(Tbmanagement ref) {	
  		return srvc.saveOrUpdateOfficer(ref);
  	}
	
	public String deleteNew(Integer id) {
		return srvc.deleteNew(id);
	}
	
	public String saveOrUpdateSignatory(Tbmanagement ref) {	
  		return srvc.saveOrUpdateSignatory(ref);
  	}
	
	public String saveOrUpdateShareIndiv(Tbmanagement ref) {	
  		return srvc.saveOrUpdateShareIndiv(ref);
  	}
	
	public String saveOrUpdateShareCorp(Tbmanagement ref) {	
  		return srvc.saveOrUpdateShareCorp(ref);
  	}
}
