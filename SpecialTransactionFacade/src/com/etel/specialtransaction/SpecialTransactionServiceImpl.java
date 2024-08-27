package com.etel.specialtransaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import com.cifsdb.data.ChangeMemberStatus;
import com.cifsdb.data.ChangeMemberType;
import com.cifsdb.data.Tbcifmain;
import com.coopdb.data.AyalaCompany;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.generator.NoGenerator;
import com.etel.specialtransaction.form.ChangeMemberStatusForm;
import com.etel.specialtransaction.form.TMPFrom;
import com.etel.utils.HQLUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class SpecialTransactionServiceImpl implements SpecialTransactionService {
	private DBService dbService = new DBServiceImpl();
	private DBService dbServiceCIF = new DBServiceImplCIF();
	HashMap<String, Object> params = new HashMap<String, Object>();
	public static SecurityService securityService = (SecurityService) RuntimeAccess.getInstance()
			.getServiceBean("securityService");
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TMPFrom> getChangeMemberStatusDashboard() {
		List<TMPFrom> list = new ArrayList<TMPFrom>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("username", securityService.getUserName());
			list = (List<TMPFrom>) dbService.execStoredProc(
					"EXEC sp_Dashboard_Change_Member_Status :username", params,
					TMPFrom.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ChangeMemberStatusForm> changeMemberStatusListPerStages(String branch, String search,
			String applicationStatus, String daysCount) {
		List<ChangeMemberStatusForm> list = new ArrayList<ChangeMemberStatusForm>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("branch", branch);
			params.put("search", search);
			params.put("applicationStatus", applicationStatus);
			params.put("daysCount", daysCount);
			list = (List<ChangeMemberStatusForm>) dbServiceCIF.execStoredProc(
					"EXEC sp_Dashboard_Change_Member_Status_Stages :branch, :search, :applicationStatus, :daysCount", params,
					ChangeMemberStatusForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public ChangeMemberStatus viewChangeMemberStatus(String trn) {
		ChangeMemberStatus changeMemberStatus = new ChangeMemberStatus();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("trn", trn);
		try {
			changeMemberStatus = (ChangeMemberStatus) dbServiceCIF.executeUniqueHQLQuery("FROM ChangeMemberStatus WHERE TransactionNumber=:trn", params);
			if(changeMemberStatus != null) {
				return changeMemberStatus;
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		return null;
	}

	@Override
	public String saveChangeMemberStatus(ChangeMemberStatus form) {
		ChangeMemberStatus changeMemberStatus = new ChangeMemberStatus();
		NoGenerator noGenerator = new NoGenerator();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("CIFNo", form.getCifno());
	
		try {
			changeMemberStatus = (ChangeMemberStatus) dbServiceCIF.executeUniqueHQLQuery("FROM ChangeMemberStatus WHERE CIFNo=:CIFNo", params);
			
			if(changeMemberStatus != null) {
				if(changeMemberStatus.getApplicationStatus().equals("2")) {
					return "There are existing requests to change member status.";
				}
					
			}else {
				String transactionNumber = noGenerator.generateTRN();
				form.setTransactionNumber(transactionNumber);
				form.setApplicationStatus("2");
				form.setDateCreated(new Date());
				form.setCreatedBy(securityService.getUserName());	
				
				if (dbServiceCIF.save(form)) {
					return "Success";
				} else {
					return "There was a problem saving your request";
				}
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		return "There was a problem saving your request";
	}

	@Override
	public String approvedDeclinedChangeMemberStatus(String cifNo, String trn, String status) {
		Tbcifmain cifMain = new Tbcifmain();
		ChangeMemberStatus changeMemberStatus = new ChangeMemberStatus();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("CIFNo", cifNo);
		params.put("trn", trn);
		
		changeMemberStatus = (ChangeMemberStatus) dbServiceCIF.executeUniqueHQLQuery("FROM ChangeMemberStatus WHERE TransactionNumber=:trn", params);
		if(changeMemberStatus != null) {
			
			changeMemberStatus.setApplicationStatus(status);
			changeMemberStatus.setDateApproved(new Date());
			changeMemberStatus.setApprovedBy(securityService.getUserName());	
			
			if (dbServiceCIF.saveOrUpdate(changeMemberStatus)) {
				System.out.println("Save ChangeMemberStatus");
				cifMain = (Tbcifmain) dbServiceCIF.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno=:CIFNo", params);
				
				if(cifMain != null) {
					
					cifMain.setMemberStatus(changeMemberStatus.getNewStatus());
					cifMain.setMemberStatusSub(changeMemberStatus.getSubNewStatus());				
					if (dbServiceCIF.saveOrUpdate(cifMain)) {
						System.out.println("Save CIFMAIN");
						
						return "success";
					}
				}
			}
		}
		return "failed";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TMPFrom> getChangeMemberTypeDashboard() {
		List<TMPFrom> list = new ArrayList<TMPFrom>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("username", securityService.getUserName());
			list = (List<TMPFrom>) dbService.execStoredProc(
					"EXEC sp_Dashboard_Change_Member_Type :username", params,
					TMPFrom.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChangeMemberStatusForm> changeMemberTypeListPerStages(String branch, String search,
			String applicationStatus, String daysCount) {
		List<ChangeMemberStatusForm> list = new ArrayList<ChangeMemberStatusForm>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("branch", branch);
			params.put("search", search);
			params.put("applicationStatus", applicationStatus);
			params.put("daysCount", daysCount);
			list = (List<ChangeMemberStatusForm>) dbServiceCIF.execStoredProc(
					"EXEC sp_Dashboard_Change_Member_Type_Stages :branch, :search, :applicationStatus, :daysCount", params,
					ChangeMemberStatusForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public ChangeMemberType viewChangeMemberType(String trn) {
		ChangeMemberType changeMemberType = new ChangeMemberType();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("trn", trn);
		try {
			changeMemberType = (ChangeMemberType) dbServiceCIF.executeUniqueHQLQuery("FROM ChangeMemberType WHERE TransactionNumber=:trn", params);
			if(changeMemberType != null) {
				return changeMemberType;
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		return null;
	}

	@Override
	public String saveChangeMemberType(ChangeMemberType form) {
		ChangeMemberType changeMemberType = new ChangeMemberType();
		NoGenerator noGenerator = new NoGenerator();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("CIFNo", form.getCifno());
	
		try {
			changeMemberType = (ChangeMemberType) dbServiceCIF.executeUniqueHQLQuery("FROM ChangeMemberType WHERE CIFNo=:CIFNo", params);
			
			if(changeMemberType != null) {
				if(changeMemberType.getApplicationStatus().equals("2")) {
					return "There are existing requests to change member status.";
				}
					
			}else {
				String transactionNumber = noGenerator.generateTRN();
				form.setTransactionNumber(transactionNumber);
				form.setApplicationStatus("2");
				form.setDateCreated(new Date());
				form.setCreatedBy(securityService.getUserName());	
				
				if (dbServiceCIF.save(form)) {
					return "Success";
				} else {
					return "There was a problem saving your request";
				}
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		return "There was a problem saving your request";
	}

	@Override
	public String approvedDeclinedChangeMemberType(String cifNo, String trn, String status) {
		Tbcifmain cifMain = new Tbcifmain();
		ChangeMemberType changeMemberType = new ChangeMemberType();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("CIFNo", cifNo);
		params.put("trn", trn);
		
		changeMemberType = (ChangeMemberType) dbServiceCIF.executeUniqueHQLQuery("FROM ChangeMemberType WHERE TransactionNumber=:trn", params);
		if(changeMemberType != null) {
			
			changeMemberType.setApplicationStatus(status);
			changeMemberType.setDateApproved(new Date());
			changeMemberType.setApprovedBy(securityService.getUserName());	
			
			if (dbServiceCIF.saveOrUpdate(changeMemberType)) {
				System.out.println("Save ChangeMemberStatus");
				cifMain = (Tbcifmain) dbServiceCIF.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno=:CIFNo", params);
				
				if(cifMain != null) {
					
					cifMain.setMemberStatus(changeMemberType.getNewMemberType());
					cifMain.setMemberStatusSub(changeMemberType.getNewSubMemberType());				
					if (dbServiceCIF.saveOrUpdate(cifMain)) {
						System.out.println("Save CIFMAIN");
						
						return "success";
					}
				}
			}
		}
		return "failed";
	}
	
}
