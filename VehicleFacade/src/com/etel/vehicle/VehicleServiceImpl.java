package com.etel.vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbcollateraldetailsauto;
import com.coopdb.data.Tbvehicle;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;
import com.etel.vehicleforms.VehicleForm;

public class VehicleServiceImpl implements VehicleService {
	
	private DBService dbServiceLOS = new DBServiceImpl();
	//private DBService dbServiceCIF = new DBServiceImplCIF();
	private Map<String, Object> params = HQLUtil.getMap();
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleForm> getVehicleCategoryList() {
		List<VehicleForm> list = new ArrayList<VehicleForm>();
		try {
			list = (List<VehicleForm>) dbServiceLOS.execSQLQueryTransformer("SELECT DISTINCT vehiclecategory FROM Tbvehicle", null, VehicleForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleForm> getMakeListByVehicleType(String vehicletype) {
		List<VehicleForm> list = new ArrayList<VehicleForm>();
		try {
			if (vehicletype != null) {
				params.put("vt", vehicletype);
				list = (List<VehicleForm>) dbServiceLOS.execSQLQueryTransformer("SELECT DISTINCT make FROM Tbvehicle WHERE vehiclecategory=:vt", params, VehicleForm.class, 1);
			} else {
				list = (List<VehicleForm>) dbServiceLOS.execSQLQueryTransformer("SELECT DISTINCT make FROM Tbvehicle", params, VehicleForm.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleForm> getModelListByVehicleTypeAndMake(String vehicletype, String make) {
		List<VehicleForm> list = new ArrayList<VehicleForm>();
		try {
			if (vehicletype != null && make != null) {
				params.put("vt", vehicletype);
				params.put("make", make);
				list = (List<VehicleForm>) dbServiceLOS.execSQLQueryTransformer
						("SELECT DISTINCT model FROM Tbvehicle WHERE vehiclecategory=:vt AND make=:make", params, VehicleForm.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleForm> getModelDetails(String vehicletype, String make, String model) {
		List<VehicleForm> list = new ArrayList<VehicleForm>();
		try {
			if (vehicletype != null && make != null && model != null) {
				params.put("vt", vehicletype);
				params.put("make", make);
				params.put("model", model);
				list = (List<VehicleForm>) dbServiceLOS.execSQLQueryTransformer
						("SELECT modeldetails FROM Tbvehicle WHERE vehiclecategory=:vt AND make=:make AND model=:model", params, VehicleForm.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * --Save Vehicle--
	 * @return String = success otherwise failed
	 * */
	@Override
	public String saveOrUpdateVehicle(Tbvehicle car) {
		String flag = "failed";
		try{
			if(dbServiceLOS.saveOrUpdate(car)){
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * --Search Vehicle by make or model or vehiclecategory or model details--
	 * @return List<{@link Tbvehicle}>
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbvehicle> searchByFieldVehicle(String search) {
		List<Tbvehicle> list = new ArrayList<Tbvehicle>();
		search = (search == null) ? "" : search;
		params.put("search", "%"+search+"%");
		try{
			list = (List<Tbvehicle>) dbServiceLOS.executeListHQLQuery("FROM Tbvehicle where make LIKE :search or model LIKE :search or vehiclecategory LIKE :search or modeldetails LIKE :search ORDER BY MAKE ASC", params);
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * --Search Vehicle by make AND (model or vehiclecategory or model details)--
	 * @return List<{@link Vehicle}>
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbvehicle> searchWithMake(String make, String search) {
		List<Tbvehicle> list = new ArrayList<Tbvehicle>();
		search = (search == null) ? "" : search;
		params.put("make", make);
		params.put("search", "%"+search+"%");
		try{
			list = (List<Tbvehicle>) dbServiceLOS.executeListHQLQuery("FROM Tbvehicle where make = :make and (model LIKE :search or vehiclecategory LIKE :search or modeldetails LIKE :search) ORDER BY MAKE ASC", params);
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}


	/**
	 * --Get List of Vehicle--
	 * @return List<{@link Tbvehicle}>
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbvehicle> listVehicle() {
		// TODO Auto-generated method stub
		List<Tbvehicle> list = new ArrayList<Tbvehicle>();
		
		try{
			list = (List<Tbvehicle>) dbServiceLOS.executeListHQLQueryWitMaxResults("FROM Tbvehicle ORDER BY MAKE ASC", 500, null);
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * --Get vehicle make list--
	 * @return List = make
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> vehiclemakeList() {
		//TODO
		List<String> list = new ArrayList<String>();
		try{
			list = (List<String>) dbServiceLOS.executeListSQLQuery("SELECT DISTINCT MAKE from Tbvehicle ORDER BY MAKE ASC", null);
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * --Save Collateral Details--
	 * @return String = success otherwise failed
	 * */
	@Override
	public String saveCollateralDetailsAuto(Tbcollateraldetailsauto autodetails) {
		String flag = "failed";
		try{
			if(dbServiceLOS.saveOrUpdate(autodetails)){
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * --Get Collateral Details Auto--
	 * @return form = {@link Tbcollateraldetailsauto}
	 * */
	@Override
	public Tbcollateraldetailsauto getAutoCollateralDetails(String appno) {
		Tbcollateraldetailsauto details = new Tbcollateraldetailsauto();
		try {
			if(appno != null){
				params.put("appno", appno);
				details = (Tbcollateraldetailsauto) dbServiceLOS.executeUniqueHQLQueryMaxResultOne("FROM Tbcollateraldetailsauto WHERE appno=:appno", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return details;
	}
	
}
