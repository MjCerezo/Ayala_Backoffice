package com.etel.specialtransaction;

import java.util.List;

import com.cifsdb.data.ChangeMemberStatus;
import com.cifsdb.data.ChangeMemberType;
import com.etel.specialtransaction.form.ChangeMemberStatusForm;
import com.etel.specialtransaction.form.TMPFrom;

public interface SpecialTransactionService {
	
	//MemberStatus
	public List<TMPFrom> getChangeMemberStatusDashboard();
	
	List<ChangeMemberStatusForm> changeMemberStatusListPerStages(String branch, String search, String applicationStatus, String daysCount);
	
	ChangeMemberStatus viewChangeMemberStatus(String trn);
	
	String saveChangeMemberStatus(ChangeMemberStatus form);
	
	String approvedDeclinedChangeMemberStatus(String cifNo,String trn, String status);
	
	
	//MemberType
	public List<TMPFrom> getChangeMemberTypeDashboard();
	
	List<ChangeMemberStatusForm> changeMemberTypeListPerStages(String branch, String search, String applicationStatus, String daysCount);
	
	ChangeMemberType viewChangeMemberType(String trn);
	
	String saveChangeMemberType(ChangeMemberType form);
	
	String approvedDeclinedChangeMemberType(String cifNo,String trn, String status);
}
