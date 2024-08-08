package com.etel.override;

import java.util.List;

import com.casa.fintx.forms.OverrideResultForm;
import com.coopdb.data.Tboverridematrix;
import com.coopdb.data.Tboverriderequest;
import com.etel.deposittransaction.form.DepositTransactionForm;
import com.etel.override.form.OverrideRequestForm;
import com.etel.override.form.OverrideResponseForm;
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
public class OverrideFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level;
	 * recommend changing this to FATAL or ERROR before deploying. For info on these
	 * levels, look for tomcat/log4j documentation
	 */
	public OverrideFacade() {
		super(INFO);
	}

	OverrideService srvc = new OverrideServiceImpl();

	public String addOverrideRule(Tboverridematrix overridematrix) {
		return srvc.addOverrideRule(overridematrix);
	}

	public String deleteOverrideRule(Tboverridematrix overridematrix) {
		return srvc.deleteOverrideRule(overridematrix);
	}

	public List<Tboverridematrix> listOverrideRules(String txcode, String prodcode, String subprodcode) {
		return srvc.listOverrideRule(txcode, prodcode, subprodcode);
	}

	public List<Tboverriderequest> listOverrideRequest(String txrefno) {
		return srvc.listOverrideRequest(txrefno);
	}

	public List<OverrideResponseForm> checkOverride(OverrideRequestForm form) {
		return srvc.checkOverride(form);
	}

	public String requestOverride(DepositTransactionForm form, List<Tboverriderequest> requests) {
		return srvc.requestOverride(form, requests);
	}

	public String updateOverride(String txrefno, String status, String username, String password) {
		return srvc.updateOverride(txrefno, status, username, password);
	}

	public OverrideResultForm waitRemoteOverride(String txrefno) {
		return srvc.waitRemoteOverride(txrefno);
	}

	public List<Tboverriderequest> listPendingRemoteOverride() {
		return srvc.listPendingRemoteOverride();
	}

	public String updateOverrideAccountno(String txrefno, String accountno) {
		return srvc.updateOverrideAccountno(txrefno, accountno);
	}

}