package com.etel.casaeod;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.coopdb.data.Tbbranch;
import com.coopdb.data.Tbloanfin;
import com.coopdb.data.Tbprocessingdate;
import com.etel.casaeod.form.LogsAndModulesForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
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
public class CASAEODFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level;
	 * recommend changing this to FATAL or ERROR before deploying. For info on these
	 * levels, look for tomcat/log4j documentation
	 */
	public CASAEODFacade() {
		super(INFO);
	}

	CASAEODService eodSrvc = new CASAEODServiceImpl();

	public Tbbranch getMainBranch() {
		return eodSrvc.getMainBranch();
	}

	public LogsAndModulesForm findAllLogsFortheDay(Date currentbusinessdate) {
		return eodSrvc.findAllLogsFortheDay(currentbusinessdate);
	}

	public int runEOD(int module, String branchcodes) {
		return eodSrvc.runEOD(module, branchcodes);
	}

	public String checkClearing() {
		String result = null;
		try {
			System.out.println("Starting checkClearing");
			result = eodSrvc.checkClearing();
			System.out.println("Finished checkClearing" + result);
		} catch (Exception e) {
			log(ERROR, "The checkClearing service operation has failed", e);
		}
		return result;
	}

	public Tbprocessingdate getProcDate() {
		return eodSrvc.getProcDate();
	}

	public String saveProcDate(Tbprocessingdate procDate) {
		return eodSrvc.saveProcDate(procDate);
	}

	public void sessionTest() {

		DBService dbsrvc = new DBServiceImpl();

		List<Tbloanfin> finList = new ArrayList<Tbloanfin>();
		finList = (List<Tbloanfin>) dbsrvc.executeListHQLQuery("FROM Tbloans", null);
//		for(Tbloanfin row: finList) {
//			LOANSDB service = (LOANSDB) RuntimeAccess.getInstance().getServiceBean(
//					"LOANSDB");
//			try {
//				service.begin();
//				row.setAccountno("");
//				dbsrvc.batchSaveOrUpdate(service, session, row);
//			} catch (Exception ex) {
//				if(service!=null) service.rollback();
//				service.rollback();
//			} finally {
//				service.commit();
//				session.close();
//			}
//		}
	}

	public List<String> listOfOpenBranch() {
		return eodSrvc.listOfOpenBranch();
	}

	public String timeDepositMaturity(String accountno) {
		return eodSrvc.timeDepositMaturity(accountno);
	}

	public void runEODReports() {
		eodSrvc.runEODReports();
	}
}
