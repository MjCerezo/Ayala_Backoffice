package com.etel.teller;

import java.util.Date;
import java.util.List;

import com.casa.fintx.forms.AccountInquiryJournalForm;
import com.coopdb.data.Tboverageshortage;
import com.coopdb.data.Tbtellerslimit;
import com.etel.security.forms.TBRoleForm;
import com.etel.teller.form.TellerForm;
import com.etel.teller.form.TellersTotal;
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
public class TellerFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level;
	 * recommend changing this to FATAL or ERROR before deploying. For info on these
	 * levels, look for tomcat/log4j documentation
	 */
	public TellerFacade() {
		super(INFO);
	}

	TellerService tellerService = new TellerServiceImpl();

	public List<Tbtellerslimit> findAllTellersLimitbyCoopCodeAndBranchCode() {
		return tellerService.findAllTellersLimitbyCoopCodeAndBranchCode();
	}

	public String createTellersLimit(Tbtellerslimit tellerslimit) {
		return tellerService.createTellersLimit(tellerslimit);
	}

	public String updateTellersLimit(Tbtellerslimit tellerslimit) {
		return tellerService.updateTellersLimit(tellerslimit);
	}

	public String deleteTellersLimit(int id) {
		return tellerService.deleteTellersLimit(id);
	}

	public Tbtellerslimit findTellersLimitbyId(int id) {
		return tellerService.findTellersLimitbyId(id);
	}

	public List<TBRoleForm> findAllTellerRoles() {
		return tellerService.findAllTellerRoles();
	}

	public List<TellerForm> findAllTellers(String branchcode, String currency) {
		return tellerService.findAllTellers(branchcode, currency);
	}

	public String updateTellerStatus(String userid) {
		return tellerService.updateTellerStatus(userid);
	}

	public TellerForm findTeller(String userid, String currency) {
		return tellerService.findTeller(userid, currency);
	}

	public Tbtellerslimit findTellersLimit(String username) {
		return tellerService.findTellersLimit(username);
	}

	public List<AccountInquiryJournalForm> listTellerTxPerPeriod(Date from, Date to, String userid) {
		return tellerService.listTellerTxPerPeriod(from, to, userid);
	}

	public List<TellersTotal> listTellerTotalCashTx(Date from, Date to, String userid, String txcode) {
		return tellerService.listTellerTotalCashTx(from, to, userid, txcode);
	}

	public List<TellersTotal> listTellerTotalNonCashTx(Date from, Date to, String userid, String txcode) {
		return tellerService.listTellerTotalNonCashTx(from, to, userid, txcode);
	}

	public List<TellersTotal> listTellerTotalChecksForClearing(String userid, int clearingdays) {
		return tellerService.listTellerTotalChecksForClearing(userid, clearingdays);
	}

	public String declareOverageShortage(Tboverageshortage data) {
		return tellerService.declareOverageShortage(data);
	}
}
