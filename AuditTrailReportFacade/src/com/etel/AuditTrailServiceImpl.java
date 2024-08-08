package com.etel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.etel.audittrailreport.form.AuditTrailReportForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.reports.ReportsFacadeImpl;
import com.etel.reports.ReportsFacadeService;
import com.etel.utils.HQLUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class AuditTrailServiceImpl implements AuditTrailService {
	
	private ReportsFacadeService rptservice = new ReportsFacadeImpl();
	private Map<String, Object> params = new HashMap<String, Object>();
	SecurityService serviceS = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private Map<String, Object> param = HQLUtil.getMap();
	DBService dbService = new DBServiceImpl();

	@SuppressWarnings("unchecked")
	@Override
	public List<AuditTrailReportForm> getAuditTrail(String dateFilter, Date businessDate, Date startDate, Date endDate,
			String moduleType, String branch, String username) {
		List<AuditTrailReportForm> list = new ArrayList<AuditTrailReportForm>();
		try {
			param.put("dateFilter", dateFilter);
			param.put("businessDate", businessDate);
			param.put("startDate", startDate);
			param.put("endDate", endDate);
			param.put("moduleType", moduleType);
			param.put("branch", branch);
			param.put("username", username);

			list = (List<AuditTrailReportForm>) dbService.execStoredProc(
					"EXEC sp_AuditTrail_Report :dateFilter, :businessDate, :startDate, :endDate, :moduleType, :branch, :username",
					param, AuditTrailReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

}
