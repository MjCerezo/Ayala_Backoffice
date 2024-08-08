package com.etel.ci;

import java.util.List;

import com.coopdb.data.Tbcirequest;
import com.etel.ci.forms.CIAppDetails;
import com.etel.ci.forms.CIEvalForm;
import com.etel.ci.forms.CIFRecord;
import com.etel.ci.forms.CreditInvestigator;

public interface CreditInvestigationService {
	String saveUpdateCIRequest(Tbcirequest request);

	Tbcirequest getCIRequest(String requestID);

	CIFRecord getCIFRecord(String cifno);

	String submitCIRequest(String requestID, String status);

	boolean isCISupervisorByCompanycode(String companycode);

	CIAppDetails getAppdetailByAppno(String appno);

	List<CreditInvestigator> listCI();

	String getCIFNoByReportID(String rptID);
	
	List<Tbcirequest> getListofCiRequest(String appno, String lastname, String firstname, String middlename,
			String corporatename, String customertype, String cirequestid, String cifno, String requeststatus,
			Integer page, Integer maxResult, String assigneduser, Boolean viewflag);

	int getCiRequestTotal(String appno, String lastname, String firstname, String middlename, String corporatename,
			String customertype, String cirequestid, String cifno, String requeststatus, String assigneduser, Boolean viewflag);

	List<CIEvalForm> getAllCIReportperAppNo(String appno, Integer evalreportid);
}
