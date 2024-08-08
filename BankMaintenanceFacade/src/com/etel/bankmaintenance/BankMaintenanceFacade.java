package com.etel.bankmaintenance;

import java.util.List;

import com.coopdb.data.Tbbanks;
import com.coopdb.data.Tbhousebank;
import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
import com.wavemaker.runtime.service.annotations.ExposeToClient;

/**
 * This is a client-facing service class.  All
 * public methods will be exposed to the client.  Their return
 * values and parameters will be passed to the client or taken
 * from the client, respectively.  This will be a singleton
 * instance, shared between all requests. 
 * 
 * To log, call the superclass method log(LOG_LEVEL, String) or log(LOG_LEVEL, String, Exception).
 * LOG_LEVEL is one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level.
 * For info on these levels, look for tomcat/log4j documentation
 */
@ExposeToClient
public class BankMaintenanceFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */

	BankMaintenanceFacadeService service = new BankMaintenanceFacadeServiceImpl();
	
	public List<Tbbanks> getBanks (){
		return service.getBanks();
	}
	
	public List<Tbbanks> getBanksListPerBranch (String branchcode){
		return service.getBanksListPerBranch(branchcode);
	}
	public String saveBank(Tbbanks banks){
		return service.saveBank(banks);
	}
	
	public String deleteBank(Integer id) {
		return service.deleteBank(id);
	}
	
	public List<Tbhousebank> getHouseBank (Tbhousebank houseBank){
		return service.getHouseBank(houseBank);
	}
	
	public List<Tbhousebank> getHouseBankListPerParams(String branchcode, String bankcode, String bankbranch, String status){
		return service.getHouseBankListPerParams(branchcode,bankcode,bankbranch,status);
	}
	public String saveHouseBank (Tbhousebank houseBank){
		return service.saveHouseBank(houseBank);
	}
	
	public String deleteHouseBank(Integer id) {
		return service.deleteHouseBank(id);
	}
	
	public String setHouseBankStatus (Tbhousebank houseBank, String changeType) {
		return service.setHouseBankStatus(houseBank, changeType);
	}
	
	public Tbhousebank getBanksDetails (String bankCode){
		return service.getBanksDetails(bankCode);
	}
	
	public List<Tbhousebank> getBanksListPerBranchAndStatus (String branchcode,String status){
		return service.getBanksListPerBranchAndStatus(branchcode,status);
	}
	public List<Tbhousebank> getBankDetailsPerBranchAndStatus (String branchcode,String bankcode,String status){
		return service.getBankDetailsPerBranchAndStatus(branchcode,bankcode,status);
	}
}
