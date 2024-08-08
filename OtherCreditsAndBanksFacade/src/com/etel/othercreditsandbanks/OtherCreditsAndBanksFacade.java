package com.etel.othercreditsandbanks;

import java.util.List;


import com.coopdb.data.Tbotheraccounts;
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
public class OtherCreditsAndBanksFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
	public List<Tbotheraccounts> listLoan(String cifno) {
		OtherCreditsAndBanksService srvc = new OtherCreditsAndBanksServiceImpl();
		return srvc.listLoan(cifno);
	}
	
	public List<Tbotheraccounts> listBanks(String cifno) {
		OtherCreditsAndBanksService srvc = new OtherCreditsAndBanksServiceImpl();
		return srvc.listBanks(cifno);
	}
	
	public List<Tbotheraccounts> listCredits(String cifno) {
		OtherCreditsAndBanksService srvc = new OtherCreditsAndBanksServiceImpl();
		return srvc.listCredits(cifno);
	}
	
	public String deleteCreditBanks(Integer id) {
		OtherCreditsAndBanksService srvc = new OtherCreditsAndBanksServiceImpl();
		return srvc.deleteCreditBanks(id);
	}
	
	public String saveOrUpdateLoan(Tbotheraccounts ref) {
		OtherCreditsAndBanksService srvc = new OtherCreditsAndBanksServiceImpl();
  		return srvc.saveOrUpdateLoan(ref);
  	}
	
	public String saveOrUpdateBankAccount(Tbotheraccounts ref) {
		OtherCreditsAndBanksService srvc = new OtherCreditsAndBanksServiceImpl();
  		return srvc.saveOrUpdateBankAccount(ref);
  	}
	
	public String saveOrUpdateCredit(Tbotheraccounts ref) {
		OtherCreditsAndBanksService srvc = new OtherCreditsAndBanksServiceImpl();
  		return srvc.saveOrUpdateCredit(ref);
  	}

}
