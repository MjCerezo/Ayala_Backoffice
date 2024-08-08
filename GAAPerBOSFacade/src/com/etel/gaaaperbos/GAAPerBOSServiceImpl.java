/**
 * 
 */
package com.etel.gaaaperbos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbgaaperbos;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

/**
 * @author ETEL-LAPTOP19
 *
 */
@SuppressWarnings("unchecked")
public class GAAPerBOSServiceImpl implements GAAPerBOSService {
	
	DBService dbsrvc = new DBServiceImpl();
	SecurityService serviceS = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private Map<String, Object> params = HQLUtil.getMap();
	
	@Override
    public String saveGAA(Tbgaaperbos gaaperbos) {
		String flag = "failed";
    	try {
    		if(gaaperbos.getId() != null) {
        		gaaperbos.setDatecreated(new Date());
        		gaaperbos.setCreatedby(serviceS.getUserName());
    		} else {
    			gaaperbos.setDateupdated(new Date());
    			gaaperbos.setUpdatedby(serviceS.getUserName());
    		}
			if(dbsrvc.saveOrUpdate(gaaperbos))
					flag = "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
    }

	@Override
	public List<Tbgaaperbos> listGAAPerBOS(String boscode){
		List<Tbgaaperbos> list = new ArrayList<Tbgaaperbos>();
		params.put("boscode", boscode);
		System.out.println(boscode);
		try {
			list = (List<Tbgaaperbos>)dbsrvc.executeListHQLQuery("FROM Tbgaaperbos WHERE boscode =:boscode", params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public String deleteGAA(int id){
		String flag = "failed";
		params.put("id", id);
		try {
			if(dbsrvc.executeUpdate("DELETE FROM Tbgaaperbos WHERE id =:id", params)>0)
					flag = "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}
}
