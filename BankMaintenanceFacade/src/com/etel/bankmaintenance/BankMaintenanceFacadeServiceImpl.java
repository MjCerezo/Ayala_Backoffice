package com.etel.bankmaintenance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.coopdb.data.Tbbanks;
import com.coopdb.data.Tbhousebank;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class BankMaintenanceFacadeServiceImpl implements BankMaintenanceFacadeService {
	SecurityService serviceS = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private Map<String, Object> param = HQLUtil.getMap();
	DBService dbService = new DBServiceImpl();
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbbanks> getBanks() {
		List<Tbbanks> list = new ArrayList <Tbbanks>();
		try {
			String myQuery = "SELECT * FROM TBBANKS";
			list = (List<Tbbanks>) dbService.execSQLQueryTransformer(myQuery, null,
					Tbbanks.class, 1);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbbanks> getBanksListPerBranch(String branchcode) {
		List<Tbbanks> list = new ArrayList <Tbbanks>();
		param.put("branchcode", branchcode);
		try {
			String myQuery = "SELECT * FROM TBBANKS WHERE branchcode=:branchcode";
			list = (List<Tbbanks>) dbService.execSQLQueryTransformer(myQuery, param,
					Tbbanks.class, 1);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}

	@Override
	public String saveBank(Tbbanks banks) {
		if (banks.getId() != null) {
			param.put("id", banks.getId());
			Tbbanks b = (Tbbanks) dbService.executeUniqueHQLQuery("FROM Tbbanks WHERE id=:id", param);
			b.setBankcode(banks.getBankcode());
			b.setBankname(banks.getBankname());
			//b.setBranchcode(banks.getBranchcode());
			b.setCreatedby(banks.getCreatedby());
			b.setDatecreated(new Date());
			//b.setGlcode(banks.getGlcode());

			if (dbService.saveOrUpdate(b)) {
				System.out.println("UPDATE");
				return "update";
			}
		} else {
			banks.setDatecreated(new Date());
			if (dbService.saveOrUpdate(banks)) {
				System.out.println("SAVE");
				return "success";
			}
		}
		return "failed";
	}

	@Override
	public String deleteBank(Integer id) {
		String flag = "failed";
		param.put("id", id);
		try {
			if (id != null) {

				Integer res = dbService.executeUpdate("DELETE FROM TBBANKS WHERE id=:id", param);
				if (res != null && res == 1) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String deleteHouseBank(Integer id) {
		String flag = "failed";
		param.put("id", id);
		try {
			if (id != null) {

				Integer res = dbService.executeUpdate("DELETE FROM TBHOUSEBANK WHERE id=:id", param);
				if (res != null && res == 1) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String saveHouseBank(Tbhousebank houseBank) {
		if (houseBank.getId() != null) {
			param.put("id", houseBank.getId());
			Tbhousebank b = (Tbhousebank) dbService.executeUniqueHQLQuery("FROM Tbhousebank WHERE id=:id", param);
			b.setBranchcode(houseBank.getBranchcode());
			b.setBankcode(houseBank.getBankcode());
			b.setBankname(houseBank.getBankname());
			b.setBankbranch(houseBank.getBankbranch());
			b.setBankaccountnumber(houseBank.getBankaccountnumber());
			b.setGlcode(houseBank.getGlcode());
			b.setStatus(houseBank.getStatus());
			b.setCreatedby(houseBank.getCreatedby());
			b.setDatecreated(new Date());

			if (dbService.saveOrUpdate(b)) {
				System.out.println("UPDATE");
				return "update";
			}
		} else {
			houseBank.setDatecreated(new Date());
			if (dbService.saveOrUpdate(houseBank)) {
				System.out.println("SAVE");
				return "success";
			}
		}
		return "failed";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbhousebank> getHouseBankListPerParams(String branchcode, String bankcode, String bankbranch, String status) {
		List<Tbhousebank> list = new ArrayList <Tbhousebank>();
		param.put("branchcode", branchcode);
		param.put("bankcode", bankcode);
		param.put("bankbranch", bankbranch);
		param.put("status", status);
		try {
			String myQuery = "SELECT * FROM Tbhousebank WHERE branchcode=:branchcode AND bankcode=:bankcode AND bankbranch=:bankbranch AND status=:status";
			list = (List<Tbhousebank>) dbService.execSQLQueryTransformer(myQuery, param,
					Tbhousebank.class, 1);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbhousebank> getHouseBank(Tbhousebank houseBank) {
		List<Tbhousebank> list = new ArrayList <Tbhousebank>();
		param.put("branchcode", houseBank.getBranchcode());
		try {
			String myQuery = "SELECT * FROM Tbhousebank WHERE branchcode=:branchcode";
			list = (List<Tbhousebank>) dbService.execSQLQueryTransformer(myQuery, param,
					Tbhousebank.class, 1);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}

	@Override
	public String setHouseBankStatus(Tbhousebank houseBank, String changeType) {
		if (houseBank.getId() != null) {
			param.put("id", houseBank.getId());
			Tbhousebank b = (Tbhousebank) dbService.executeUniqueHQLQuery("FROM Tbhousebank WHERE id=:id", param);
			if(changeType.equals("Active")) {
				b.setStatus("Inactive");
			}else {
				b.setStatus("Active");
			}
			
			if (dbService.saveOrUpdate(b)) {
				System.out.println("UPDATE");
				return "update";
			}
		}
		return "failed";
	}


	@SuppressWarnings("unchecked")
	@Override
	public Tbhousebank getBanksDetails(String bankCode) {
		Tbhousebank b = new Tbhousebank();
		param.put("bankcode", bankCode);
		try {
			if(bankCode!=null) {
				b = (Tbhousebank) dbService.executeUniqueHQLQuery("FROM Tbhousebank WHERE bankcode=:bankcode", param);
			}

		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return b;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Tbhousebank> getBanksListPerBranchAndStatus(String branchcode, String status) {
		List<Tbhousebank> list = new ArrayList <Tbhousebank>();
	
		try {
			if(branchcode!=null && status!=null) {
				param.put("branchcode", branchcode);
				param.put("status", status);
				
				String myQuery = "SELECT * FROM Tbhousebank WHERE branchcode=:branchcode and status=:status";
				list = (List<Tbhousebank>) dbService.execSQLQueryTransformer(myQuery, param,
						Tbhousebank.class, 1);
			}

		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbhousebank> getBankDetailsPerBranchAndStatus(String branchcode, String bankcode, String status) {
		List<Tbhousebank> list = new ArrayList <Tbhousebank>();

		try {
			if(branchcode!=null && bankcode!=null && status!=null) {
				param.put("branchcode", branchcode);
				param.put("bankcode", bankcode);
				param.put("status", status);
				
				String myQuery = "SELECT * FROM Tbhousebank WHERE branchcode=:branchcode and bankcode =:bankcode and status=:status ";
				list = (List<Tbhousebank>) dbService.execSQLQueryTransformer(myQuery, param,
						Tbhousebank.class, 1);
			}

		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}

}
