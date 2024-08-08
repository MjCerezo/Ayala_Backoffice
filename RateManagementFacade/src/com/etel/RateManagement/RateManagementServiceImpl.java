package com.etel.RateManagement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbratehistory;
import com.coopdb.data.Tbrates;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;

public class RateManagementServiceImpl implements RateManagementService {
	private DBService dbservice = new DBServiceImpl();
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbrates> getListRates() {
		// TODO Auto-generated method stub
		List<Tbrates> list = new ArrayList<Tbrates>();
		try {
			list =(List<Tbrates>)dbservice.executeListHQLQuery("FROM Tbrates", null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public String addOrupdateRates(Tbrates rate) {
		// TODO Auto-generated method stub
		String flag = "failed";
		Map<String,Object> params = HQLUtil.getMap();
		try {
			params.put("id", rate.getId());
			Tbrates fx = (Tbrates)dbservice.executeUniqueHQLQuery("FROM Tbrates WHERE id=:id", params);
			
			if(fx != null){
//				fx.setId(rate.getId());
				fx.setBuySell(rate.getBuySell());
				fx.setBoardrate(rate.getBoardrate());
				fx.setPds(rate.getPds());
				fx.setIrr(rate.getIrr());
				fx.setCreatedby(fx.getCreatedby());
				fx.setCreateddate(fx.getCreateddate());
				fx.setUpdatedby(UserUtil.securityService.getUserName());
				fx.setUpdateddate(new Date());
				if(dbservice.saveOrUpdate(fx)){
					Tbratehistory history = new Tbratehistory();
					history.setBuySell(fx.getBuySell());
					history.setCurrency(fx.getCurrency());
					history.setBoardrate(fx.getBoardrate());
					history.setIrr(fx.getIrr());
					history.setPds(fx.getPds());
					history.setUpdatedby(fx.getUpdatedby());
					history.setUpdateddate(fx.getUpdateddate());
					dbservice.save(history);
					flag = "updated";
					System.out.println("rate updated.");
					
				}
			}else{
				Tbrates rates = new Tbrates();
				rates.setBuySell(rate.getBuySell());
				rates.setCurrency(rate.getCurrency());
				rates.setBoardrate(rate.getBoardrate());
				rates.setIrr(rate.getIrr());
				rates.setPds(rate.getPds());
				rates.setCreatedby(UserUtil.securityService.getUserName());
				rates.setCreateddate(new Date());
				if(dbservice.save(rates)){
					flag = "created";
					System.out.println("rate created.");
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}
	
	public String deleteRates(int id) {
		// TODO Auto-generated method stub
		String flag = "failed";
		Map<String,Object> params = HQLUtil.getMap();
		try {
			params.put("id", id);
			Tbrates rate = (Tbrates)dbservice.executeUniqueHQLQuery("FROM Tbrates WHERE id=:id", params);
			if(rate != null){
				if(dbservice.delete(rate)){
					flag = "success";
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}
	

}
