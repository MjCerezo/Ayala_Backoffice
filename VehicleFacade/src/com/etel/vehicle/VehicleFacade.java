package com.etel.vehicle;

import java.util.List;

import com.coopdb.data.Tbcollateraldetailsauto;
import com.coopdb.data.Tbvehicle;
import com.etel.vehicleforms.VehicleForm;
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
public class VehicleFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public VehicleFacade() {
       super(INFO);
    }

    VehicleService srvc = new VehicleServiceImpl();
    
    /** List All VEHICLE CATEGORY,TYPE */
    public List<VehicleForm> getVehicleCategoryList(){
    	return srvc.getVehicleCategoryList();
    }
    
    /** List All VEHICLE MAKE by VEHICLE CATEGORY,TYPE */
    public List<VehicleForm> getMakeListByVehicleType(String vehicletype){
    	return srvc.getMakeListByVehicleType(vehicletype);
    }
    
    /** List All VEHICLE MODEL by VEHICLE CATEGORY and MAKE */
    public List<VehicleForm> getModelListByVehicleTypeAndMake(String vehicletype, String make){
    	return srvc.getModelListByVehicleTypeAndMake(vehicletype, make);
    }    

    /** Get VEHICLE MODEL DETAILS */
    public List<VehicleForm> getModelDetails(String vehicletype, String make, String model){
    	return srvc.getModelDetails(vehicletype, make, model);
    } 
    
 // Add New Vehicle
 	public String saveOrUpdateVehicle(Tbvehicle car) {
 		return srvc.saveOrUpdateVehicle(car);
 	}
 	public List<Tbvehicle> searchByFieldVehicle(String search) {
		return srvc.searchByFieldVehicle(search);
	}
 	public List<Tbvehicle> searchWithMake(String make, String search) {
		return srvc.searchWithMake(make, search);
	}
 	public List<Tbvehicle> listVehicle() {
		return srvc.listVehicle();
	}

	public List<String> vehiclemakeList() {
		return srvc.vehiclemakeList();
	}
	
	public String saveCollateralDetailsAuto(Tbcollateraldetailsauto autodetails) {
 		return srvc.saveCollateralDetailsAuto(autodetails);
 	}
	public Tbcollateraldetailsauto getAutoCollateralDetails(String appno) {
		return srvc.getAutoCollateralDetails(appno);
	}

}
