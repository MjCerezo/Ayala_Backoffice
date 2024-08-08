package com.etel.vehicle;

import java.util.List;

import com.coopdb.data.Tbcollateraldetailsauto;
import com.coopdb.data.Tbvehicle;
import com.etel.vehicleforms.VehicleForm;

public interface VehicleService {

	List<VehicleForm> getVehicleCategoryList();

	List<VehicleForm> getMakeListByVehicleType(String vehicletype);

	List<VehicleForm> getModelListByVehicleTypeAndMake(String vehicletype, String make);

	List<VehicleForm> getModelDetails(String vehicletype, String make, String model);

	String saveOrUpdateVehicle(Tbvehicle car);

	List<Tbvehicle> searchByFieldVehicle(String search);

	List<Tbvehicle> searchWithMake(String make, String search);

	List<Tbvehicle> listVehicle();

	List<String> vehiclemakeList();

	String saveCollateralDetailsAuto(Tbcollateraldetailsauto autodetails);

	Tbcollateraldetailsauto getAutoCollateralDetails(String appno);

}
