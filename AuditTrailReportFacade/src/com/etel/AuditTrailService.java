package com.etel;

import java.util.Date;

import java.util.List;

import com.etel.audittrailreport.form.AuditTrailReportForm;

public interface AuditTrailService {

	List<AuditTrailReportForm> getAuditTrail(String dateFilter, Date businessDate, Date startDate, Date endDate,
			String moduleType, String branch, String username);

}
