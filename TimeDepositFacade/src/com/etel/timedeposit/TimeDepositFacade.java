package com.etel.timedeposit;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.coopdb.data.Tbtimedeposit;
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
public class TimeDepositFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level;
	 * recommend changing this to FATAL or ERROR before deploying. For info on these
	 * levels, look for tomcat/log4j documentation
	 */
	public TimeDepositFacade() {
		super(INFO);
	}

	TimeDepositService srvc = new TimeDepositServiceImpl();

	public Tbtimedeposit getTimeDeposit(String acctno) {
		return srvc.getTimeDeposit(acctno);
	}

	public String terminateAccount(String accountno, String disposition, String intdisposition,
			String prindisposition) {
		return srvc.terminateAccount(accountno, disposition, intdisposition, prindisposition);
	}

	public List<Tbtimedeposit> listMaturingAccounts(Date startdate, Date enddate, String dispo) {
		return srvc.listMaturingAccounts(startdate, enddate, dispo);
	}

	public String terminateTDAccount(String accountno, String credittoacctno) {
		return srvc.terminateTDAccount(accountno, credittoacctno);
	}

	public String changeDisposition(String accountno, String newdispo, String credittoacctno, String intdispo,
			BigDecimal placementamt, int term, BigDecimal intRate, BigDecimal wTaxRate, Date bookDate, Date matDate,
			String tdcno, String passbookno) {
		return srvc.changeDisposition(accountno, newdispo, credittoacctno, intdispo, placementamt, term, intRate,
				wTaxRate, bookDate, matDate, tdcno, passbookno);
	}

	public String interestWithdrawal(String accountno, BigDecimal amount, String modeofrelease,
			String credtitoaccountno, BigDecimal amttocredit) {
		return srvc.interestWithdrawal(accountno, amount, modeofrelease, credtitoaccountno, amttocredit);
	}

	public Date computeMaturityDate(Date placementdate, int term, String termFreq, int skipWeekend, int skipHoliday) {
		return srvc.computeMaturityDate(placementdate, term, termFreq, skipWeekend, skipHoliday);
	}
}
