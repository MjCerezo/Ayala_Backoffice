package com.etel.deposittransaction;

import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbchecksforclearing;
import com.coopdb.data.Tboverriderequest;
import com.coopdb.data.Tbtransactioncode;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.deposittransaction.form.DepositTransactionForm;
import com.etel.deposittransaction.form.DepositTransactionResultForm;
import com.etel.utils.HQLUtil;
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
public class DepositTransactionFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level;
	 * recommend changing this to FATAL or ERROR before deploying. For info on these
	 * levels, look for tomcat/log4j documentation
	 */
	public DepositTransactionFacade() {
		super(INFO);
	}

	DepositTransactionService srvc = new DepositTransactionServiceImpl();

	public DepositTransactionResultForm debitTransaction(DepositTransactionForm form, Tbtransactioncode tx) {
		return srvc.debitTransaction(form, tx);
	}

	public DepositTransactionResultForm creditTransaction(DepositTransactionForm form, Tbtransactioncode tx) {
		return srvc.creditTransaction(form, tx);
	}

	public DepositTransactionResultForm casaTransaction(DepositTransactionForm form, Tbtransactioncode tx,
			List<Tboverriderequest> requests) {
		return srvc.casaTransaction(form, tx, requests);
	}

	public DepositTransactionResultForm errorCorrect(String acctno, String txrefno, String overridetxrefno,
			String overridestatus, String username, String password) {
		return srvc.errorCorrect(acctno, txrefno, overridetxrefno, overridestatus, username, password);
	}

	public DepositTransactionResultForm clearChecks(List<Tbchecksforclearing> checks, String accountno) {
		return srvc.clearChecks(checks, accountno);
	}

	public String generateTxrefno() {
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		return (String) dbsrvc.executeUniqueSQLQuery(
				"DECLARE @txrefno VARCHAR(20) EXEC SEQGENERATE @txrefno OUTPUT SELECT @txrefno", param);
	}

	public List<Tbtransactioncode> getTransactionCodes(String txcode) {
		return srvc.getTransactionCodes(txcode);
	}
}
