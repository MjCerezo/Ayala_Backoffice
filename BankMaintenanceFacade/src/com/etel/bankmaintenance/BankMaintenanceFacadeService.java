package com.etel.bankmaintenance;

import java.util.List;

import com.coopdb.data.Tbbanks;
import com.coopdb.data.Tbhousebank;

public interface BankMaintenanceFacadeService {

	List<Tbbanks> getBanksListPerBranch(String branchcode);
	String saveBank(Tbbanks banks);
	String deleteBank(Integer id);
	String deleteHouseBank(Integer id);
	String saveHouseBank(Tbhousebank houseBank);
	List<Tbhousebank> getHouseBankListPerParams(String branchcode, String bankcode, String bankbranch, String status);
	List<Tbhousebank> getHouseBank(Tbhousebank houseBank);
	List<Tbbanks> getBanks();
	String setHouseBankStatus(Tbhousebank houseBank, String changeType);
	Tbhousebank getBanksDetails(String bankCode);
	List<Tbhousebank> getBanksListPerBranchAndStatus(String branchcode,String status);
	List<Tbhousebank> getBankDetailsPerBranchAndStatus(String branchcode, String bankcode, String status);
}
