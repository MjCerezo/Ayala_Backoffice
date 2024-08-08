package com.etel.facade;

import java.io.IOException;
import java.util.List;

import com.coopdb.data.Tbcoa;
import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
import com.wavemaker.runtime.service.annotations.ExposeToClient;

/**
 * This is a client-facing service class. All public methods will be exposed to
 * the client. Their return values and parameters will be passed to the client
 * or taken from the client, respectively. This will be a singleton instance,
 * shared between all requests.
 * 
 * To log, call the superclass method log(LOG_LEVEL, String) or log(LOG_LEVEL,
 * String, Exception). LOG_LEVEL is one of FATAL, ERROR, WARN, INFO and DEBUG to
 * modify your log level. For info on these levels, look for tomcat/log4j
 * documentation
 */
@ExposeToClient
public class AccountChartFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level;
	 * recommend changing this to FATAL or ERROR before deploying. For info on these
	 * levels, look for tomcat/log4j documentation
	 */
	private AccountChartService service = new AccountChartImpl();

	public String saveOrUpdateCoa(Tbcoa d, String oldaccountno) {
		return service.saveOrUpdateCoa(d, oldaccountno);
	}

	public List<Tbcoa> listCoa() {
		return service.listCoa();
	}

	public String deleteItem(String accountno) {
		return service.deleteItem(accountno);
	}

	public List<Tbcoa> displayAccountDescription(String accountDescript) {
		AccountChartService srvc = new AccountChartImpl();
		return srvc.displayAccountDescription(accountDescript);
	}

	public String uploadCOA(String filename) throws IOException {
		AccountChartService srvc = new AccountChartImpl();
		return srvc.uploadCOA(filename);
	}
}
