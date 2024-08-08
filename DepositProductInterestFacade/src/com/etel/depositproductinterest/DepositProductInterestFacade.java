package com.etel.depositproductinterest;

import java.util.List;

import com.coopdb.data.Tbdepositproductinterestrate;
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
public class DepositProductInterestFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level;
	 * recommend changing this to FATAL or ERROR before deploying. For info on these
	 * levels, look for tomcat/log4j documentation
	 */
	public DepositProductInterestFacade() {
		super(INFO);
	}

	DepositProductInterestService srvc = new DepositProductInterestServiceImpl();

	public String saveOrUpdateProductInterest(Tbdepositproductinterestrate depositproductinterestrate) {
		return srvc.saveOrUpdateProductInterest(depositproductinterestrate);
	}

	public List<Tbdepositproductinterestrate> listDepositProductInterestRates(
			Tbdepositproductinterestrate depositproductinterestrate) {
		return srvc.listDepositProductInterestRates(depositproductinterestrate);
	}

}
