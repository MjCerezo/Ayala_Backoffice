/**
 * 
 */
package com.etel.passbook;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbpassbookinventory;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.passbook.form.PassbookDataForm;
import com.etel.passbook.form.PassbookParams;
import com.etel.utils.HQLUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

/**
 * @author ETEL-LAPTOP19
 *
 */
@SuppressWarnings("unchecked")
public class PassbookServiceImpl implements PassBookService {
	private DBService dbService = new DBServiceImpl();
	private Map<String, Object> param = HQLUtil.getMap();
	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	
	@Override
	public List<Tbpassbookinventory> listPassbookPerCompanyPerBranch(String company, String branch){
		List<Tbpassbookinventory> pbList = new ArrayList<Tbpassbookinventory>();
		param.put("company", company);
		param.put("branch", branch);
		String qry = "FROM Tbpassbookinventory WHERE id IS NOT NULL";
		try {
			if(company != null && !company.equals("")) {
				qry = qry  + "AND company =:company ";
			} 
			if(branch != null && !branch.equals("")) {
				qry = qry  + "AND branch =:branch ";
			} 
			pbList = (List<Tbpassbookinventory>) dbService.executeListHQLQuery(qry, param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return pbList;
	}
	@Override
	public String addPBSeries(Tbpassbookinventory pb) {
		param.put("company", pb.getCompany());
		param.put("branch", pb.getBranch());
		param.put("seriesfrom", pb.getSeriesfrom());
		param.put("seriesto", pb.getSeriesto());
		try {
			int count = dbService.getSQLMaxId("SELECT COUNT(*) FROM Tbpassbookinventory WHERE company =:company and branch =:branch and "
					+ "seriesfrom <=:seriesto AND seriesto >=:seriesfrom", param);
			if(count>0) {
				return "Passbook series already enrolled.";
			}
			pb.setCreatedby(secservice.getUserName());
			pb.setDatecreated(new Date());
			pb.setCurrentseriesno("0");
			dbService.saveOrUpdate(pb);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "failed";
		}
		return "success";
	}
	@Override
	public String deletePB(Tbpassbookinventory pb) {
		try {
			dbService.delete(pb);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "failed";
		}
		return "success";
	}

	@Override
	public List<PassbookDataForm> getPassbook(String passbookType, String accountNo, String totalLineNumber, String lineno, String startLineNumber, String endLineNumber) {
		List<PassbookDataForm> list = new ArrayList<PassbookDataForm>();
		try {
			param.put("passbookType",passbookType);
			param.put("accountNo",accountNo);
			param.put("totalLineNumber", totalLineNumber);
			param.put("lineno", lineno);
			param.put("startLineNumber", startLineNumber);
			param.put("endLineNumber", endLineNumber);

			list = (List<PassbookDataForm>) dbService.execStoredProc(
					"EXEC sp_Passbook :passbookType, :accountNo, :totalLineNumber, :lineno, :startLineNumber, :endLineNumber", param,
					PassbookDataForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
