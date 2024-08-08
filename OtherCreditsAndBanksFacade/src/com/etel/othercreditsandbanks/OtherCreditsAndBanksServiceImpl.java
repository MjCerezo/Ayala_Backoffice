package com.etel.othercreditsandbanks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbotheraccounts;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.util.HQLUtil;

public class OtherCreditsAndBanksServiceImpl implements OtherCreditsAndBanksService {


	private DBService dbService = new DBServiceImpl();
	private Map<String, Object> params = HQLUtil.getMap();
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbotheraccounts> listLoan(String cifno) {
		List<Tbotheraccounts> list = new ArrayList<Tbotheraccounts>();
		params.put("cifno", cifno);
		try {	
			list = (List<Tbotheraccounts>) dbService.executeListHQLQuery("FROM Tbotheraccounts WHERE cifno =:cifno AND recordtype = '1'",params);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbotheraccounts> listBanks(String cifno) {
		List<Tbotheraccounts> list = new ArrayList<Tbotheraccounts>();
		params.put("cifno", cifno);
		try {	
			list = (List<Tbotheraccounts>) dbService.executeListHQLQuery("FROM Tbotheraccounts WHERE cifno =:cifno AND recordtype = '3'",params);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbotheraccounts> listCredits(String cifno) {
		List<Tbotheraccounts> list = new ArrayList<Tbotheraccounts>();
		params.put("cifno", cifno);
		try {	
			list = (List<Tbotheraccounts>) dbService.executeListHQLQuery("FROM Tbotheraccounts WHERE cifno =:cifno AND recordtype = '2'",params);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String deleteCreditBanks(Integer id) {
		String flag = "failed";
		params.put("id", id);
		try {
			if (id != null) {

				Integer res = dbService.executeUpdate("DELETE FROM TBOTHERACCOUNTS WHERE id =:id", params);
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
	public String saveOrUpdateLoan(Tbotheraccounts ref) {
		Tbotheraccounts d = new Tbotheraccounts();
		if(ref.getId() != null)
		{
			params.put("id", ref.getId());
			d = (Tbotheraccounts) dbService.executeUniqueHQLQuery("FROM Tbotheraccounts WHERE id=:id", params);
			d.setCifno(ref.getCifno());
			d.setCreditor(ref.getCreditor());
			d.setLoantype(ref.getLoantype());
			d.setCreditbalance(ref.getCreditbalance());
			d.setMonthamort(ref.getMonthamort());
			d.setRecordtype("1");
			
			if(dbService.saveOrUpdate(d)) {
				System.out.println("UPDATE");
				return "update";
			}
		}
		else {
			ref.setRecordtype("1");
			if(dbService.saveOrUpdate(ref)) {
				System.out.println("SAVE");
				return "success";
			}
		}
		return "failed";
	}

	@Override
	public String saveOrUpdateBankAccount(Tbotheraccounts ref) {
		Tbotheraccounts d = new Tbotheraccounts();
		if(ref.getId() != null)
		{
			params.put("id", ref.getId());
			d = (Tbotheraccounts) dbService.executeUniqueHQLQuery("FROM Tbotheraccounts WHERE id=:id", params);
			d.setCifno(ref.getCifno());
			d.setBankname(ref.getBankname());
			d.setAccounttype(ref.getAccounttype());
			d.setAccountno(ref.getAccountno());
			d.setCurrentbalance(ref.getCurrentbalance());
			d.setRecordtype("3");
			
			if(dbService.saveOrUpdate(d)) {
				System.out.println("UPDATE");
				return "update";
			}
		}
		else {
			ref.setRecordtype("3");
			if(dbService.saveOrUpdate(ref)) {
				System.out.println("SAVE");
				return "success";
			}
		}
		return "failed";
	}

	@Override
	public String saveOrUpdateCredit(Tbotheraccounts ref) {
		Tbotheraccounts d = new Tbotheraccounts();
		if(ref.getId() != null)
		{
			params.put("id", ref.getId());
			d = (Tbotheraccounts) dbService.executeUniqueHQLQuery("FROM Tbotheraccounts WHERE id=:id", params);
			d.setCifno(ref.getCifno());
			d.setCreditcardcompany(ref.getCreditcardcompany());
			d.setCardno(ref.getCardno());
			d.setCreditlimit(ref.getCreditlimit());
			d.setRecordtype("2");
			
			if(dbService.saveOrUpdate(d)) {
				System.out.println("UPDATE");
				return "update";
			}
		}
		else {
			ref.setRecordtype("2");
			if(dbService.saveOrUpdate(ref)) {
				System.out.println("SAVE");
				return "success";
			}
		}
		return "failed";
	}
	
}
