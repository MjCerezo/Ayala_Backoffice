package com.etel.upload;

import java.util.List;

import com.etel.filereaderforms.CollateralRealEstateForm;
import com.etel.filereaderforms.CollateralVehicleForm;
import com.etel.filereaderforms.FormValidation;
import com.etel.uploadforms.EmployeeForm;
import com.etel.uploadforms.FormValidation2;
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
public class FileReaderFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public FileReaderFacade() {
       //super(INFO);
    }
    FileReaderService srvc = new FileReaderServiceImpl();
    
	public List<EmployeeForm> ReceivableForm (List<EmployeeForm> r){
		return r;
	}
	public FormValidation2 uploadEmployee (String filename) {
		return srvc.uploadEmployee(filename);		
	}
	public String saveOrUpdateUploadedEmployee (List<EmployeeForm> form){
		return srvc.saveOrUpdateUploadedEmployee(form);	
	}
	public FormValidation uploadCollateral(String filename, String type) {
		return srvc.uploadCollateral(filename, type);
	}
	public List<CollateralVehicleForm> removeAllExistingAuto (List<CollateralVehicleForm> form){
		return srvc.removeAllExistingAuto(form);
	}
	public List<CollateralRealEstateForm> removeAllExistingRel (List<CollateralRealEstateForm> form){
		return srvc.removeAllExistingRel(form);
	}	
	public String saveAuto (List<CollateralVehicleForm> form){
		return srvc.saveAuto(form);
	}
	public String saveRel (List<CollateralRealEstateForm> form){
		return srvc.saveRel(form);
	}

}
