package com.etel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.etel.audittrailreport.form.AuditTrailReportForm;
import com.etel.reports.ReportsFacadeImpl;
import com.etel.reports.ReportsFacadeService;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
import com.wavemaker.runtime.security.SecurityService;
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
public class AuditTrailReport extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public AuditTrailReport() {
       super(INFO);
    }
    
    public AuditTrailService srvc = new AuditTrailServiceImpl();
    private ReportsFacadeService service = new ReportsFacadeImpl();

    public String sampleJavaOperation() {
       String result  = null;
       try {
          log(INFO, "Starting sample operation");
          result = "Hello World";
          log(INFO, "Returning " + result);
       } catch(Exception e) {
          log(ERROR, "The sample java service operation has failed", e);
       }
       return result;
    }
    
	public List<AuditTrailReportForm> getAuditTrail(String dateFilter, Date businessDate, Date startDate, Date endDate, String moduleType, String branch, String username) {
		return srvc.getAuditTrail(dateFilter, businessDate, startDate, endDate, moduleType, branch, username);
	}
	
	
	
	public String generateAuditTrail(String filetype, String imgsrc, String companyname, String dateFilter, 
			Date businessDate, Date startDate, Date endDate, String moduleType, String branch, String username) {
		Map<String, Object> param = new HashMap<String, Object>();
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		param.put("imgsrc", imgsrc);
		param.put("companyname", companyname);
		param.put("dateFilter", dateFilter);
		param.put("businessDate", businessDate);
		param.put("startDate", startDate);
		param.put("endDate", endDate);
		param.put("moduleType",moduleType);
		param.put("branch", branch);
		param.put("username", username);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String generateby = secservice.getUserName();
		param.put("generatedby", generateby);
		
		if (filetype.equals("PDF")) {
			return service.executeJasperPDF("rptAuditTrail", param);
		}
		if (filetype.equals("Excel")) {
			return service.executeJasperXLSX("rptAuditTrail", param);
		}
		return null;
	}

}
