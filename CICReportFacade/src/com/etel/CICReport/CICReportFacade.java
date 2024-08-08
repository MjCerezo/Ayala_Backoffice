package com.etel.CICReport;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.etel.cicreports.form.CICDataForm;
import com.etel.cicreports.form.CICParametersForm;
import com.etel.reports.ReportsFacadeImpl;
import com.etel.reports.ReportsFacadeService;
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
public class CICReportFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
	CICReportService srvc = new CICReportServiceImpl();
	private ReportsFacadeService service = new ReportsFacadeImpl();
	@SuppressWarnings("static-access")
	Map<String, Object> params = new HQLUtil().getMap();
	
	public List<CICDataForm> getCoveredTransaction(CICParametersForm forms){
		return srvc.getCoveredTransaction(forms);
	}
	
	//------------------------------------PDF/EXCEL------------------------------------
	public String generateCIC_getCoveredTransaction(String filetype, String companyname, Date startdate, Date enddate) {
		String filepath = null;
		params.put("companyname", companyname);
		params.put("startdate", startdate);
		params.put("enddate", enddate);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("CIC_CoveredTransactionsReport(AMLA)", params);
			} else {
				filepath = service.executeJasperXLSX("CIC_CoveredTransactionsReport(AMLA)", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return filepath;
	}
}
