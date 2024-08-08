package com.etel.deposit;

import java.util.List;

import com.coopdb.data.Tbchecksforclearing;
import com.coopdb.data.Tbdepdetail;
import com.coopdb.data.Tbdepositcif;
import com.etel.deposit.form.DepositAccountForm;
import com.etel.deposit.form.DepositLedgerForm;
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
public class DepositFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level;
	 * recommend changing this to FATAL or ERROR before deploying. For info on these
	 * levels, look for tomcat/log4j documentation
	 */
	public DepositFacade() {
		super(INFO);
	}

	DepositService srvc = new DepositServiceImpl();

	public List<DepositAccountForm> listDeposits(String memberid) {
		return srvc.listDeposits(memberid);
	}

	public List<DepositLedgerForm> listLedgerPerAcctno(String acctno) {
		return srvc.listLedgerPerAcctno(acctno);
	}

	public List<Tbdepositcif> listAccountOwners(String acctno) {
		return srvc.listAccountOwners(acctno);
	}

	public Tbdepdetail getDepositDetails(String acctno) {
		return srvc.getDepositDetails(acctno);
	}

	public List<Tbchecksforclearing> getClearedChecks(String acctno) {
		return srvc.getClearedChecks(acctno);
	}
}
