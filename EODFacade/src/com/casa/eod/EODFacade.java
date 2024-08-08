package com.casa.eod;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.casa.forms.LogsAndModulesForm;
import com.coopdb.data.Tbaccountinfo;
import com.coopdb.data.Tbbranch;
import com.coopdb.data.Tbloanfin;
import com.coopdb.data.Tbprocessingdate;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.dashboard.forms.LoanForm;
import com.etel.utils.HQLUtil;
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
@SuppressWarnings("unchecked")
public class EODFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public EODFacade() {
       super(INFO);
    }
    
    EODService eodSrvc = new EODServiceImpl();

    public Tbbranch getMainBranch() {
    	return eodSrvc.getMainBranch();
    }
    
    public LogsAndModulesForm findAllLogsFortheDay(Date currentbusinessdate) {
    	return eodSrvc.findAllLogsFortheDay(currentbusinessdate);
    }

    public int runEOD(int module, String branchcodes) {
    	return eodSrvc.runEOD(module, branchcodes);
    }
	public void runEOD() {
		String result  = null;
		try {
			log(INFO, "Starting EOD");
			eodSrvc.runLMSEOD();
			log(INFO, "Finished EOD" + result);
		} catch(Exception e) {
			log(ERROR, "The EOD service operation has failed", e);
		}
	}

	public String LMSBooking(List<LoanForm> loanTx) {
		String result  = null;
		List<String> txrefnos = new ArrayList<String>();
		List<Tbaccountinfo> acctinfo = new ArrayList<Tbaccountinfo>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			log(INFO, "Starting loanBooking");
			for(LoanForm row: loanTx) {
				txrefnos.add(row.getTxrefno());
			}
			param.put("txrefnos", txrefnos);
			acctinfo = (List<Tbaccountinfo>) dbService.executeListHQLQuery("FROM Tbaccountinfo WHERE applno IN (:txrefnos)", param);
			result = LoanBooking.loanBooking(acctinfo);
			log(INFO, "Finished loanBooking" + result);
		} catch(Exception e) {
			log(ERROR, "The loanBooking service operation has failed", e);
		}
		return result;
	}

//	public String paymentAnniversary() {
//		String result  = null;
//		try {
//			log(INFO, "Starting paymentAnniversary");
//			result = eodSrvc.paymentAnniversary();
//			log(INFO, "Finished paymentAnniversary" + result);
//		} catch(Exception e) {
//			log(ERROR, "The paymentAnniversary service operation has failed", e);
//		}
//		return result;
//	}

	public String checkClearing() {
		String result  = null;
		try {
			System.out.println( "Starting checkClearing");
			result = eodSrvc.checkClearing();
			System.out.println("Finished checkClearing" + result);
		} catch(Exception e) {
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

	public String loanPostingTest(List<Tbloanfin> finTx) {
		String result  = null;
		try {
			System.out.println("Starting loanPosting");
			result = eodSrvc.loanPostingTest(finTx);
			System.out.println("Finished loanPosting " + result);
		} catch(Exception e) {
			System.out.println("The loanPosting service operation has failed \n" +  e);
		}
		return result;
	}

	public void sessionTest() {

//		DBService dbsrvc = new DBServiceImpl();

//		List<Tbloanfin> finList = new ArrayList<Tbloanfin>();
//		finList = (List<Tbloanfin>) dbsrvc.executeListHQLQuery("FROM Tbloans", null);
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
	public List<String> listOfOpenBranch(){
		return eodSrvc.listOfOpenBranch();
	}
}
