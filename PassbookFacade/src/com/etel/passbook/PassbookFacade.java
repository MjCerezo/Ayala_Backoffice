
package com.etel.passbook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbpassbookinventory;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.passbook.form.PassbookDataForm;
import com.etel.reports.ReportsFacadeImpl;
import com.etel.reports.ReportsFacadeService;
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
public class PassbookFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public PassbookFacade() {
       super(INFO);
    }
    PassBookService service = new PassbookServiceImpl();
	ReportsFacadeService serviceReport = new ReportsFacadeImpl();
	DBService dbServiceCOOP = new DBServiceImpl();

	public List<Tbpassbookinventory> listPassbookPerCompanyPerBranch(String company, String branch){
       return service.listPassbookPerCompanyPerBranch(company, branch);
    }
	public String addPBSeries(Tbpassbookinventory pb) {
		return service.addPBSeries(pb);
	}
	public String deletePB(Tbpassbookinventory pb) {
		return service.deletePB(pb);
	}
	
	public List<PassbookDataForm> getPassbook(String passbookType, String accountNo, String totalLineNumber, String lineno, String startLineNumber, String endLineNumber) {
		return service.getPassbook(passbookType,accountNo,totalLineNumber,lineno,startLineNumber,endLineNumber);
	}

	public String updatePassbook(String passbookType, String accountNo, String totalLineNumber, String lineno, String startLineNumber, String endLineNumber) {
		String filepath = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("passbookType",passbookType);
		params.put("accountNo", accountNo);
		params.put("totalLineNumber", totalLineNumber);
		params.put("lineno", lineno);
		params.put("startLineNumber", startLineNumber);
		params.put("endLineNumber", endLineNumber);
		try {
			filepath = serviceReport.executeJasperPDF("passbook", params);
			dbServiceCOOP.executeUpdate("update TBDEPTXJRNL set txjrnlno = 1 where Accountno=:accountNo", params);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
}
