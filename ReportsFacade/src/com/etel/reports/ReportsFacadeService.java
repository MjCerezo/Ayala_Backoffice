package com.etel.reports;

import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbaccountofficer;
import com.etel.codetable.forms.CodetableForm;
import com.etel.reports.forms.AccountOfficersForm;
import com.etel.reports.forms.ReportTellerName;

public interface ReportsFacadeService {
	
	//PDF
	String executeJasperPDF(String fileName, Map<String, Object> parameters);
	//EXCEL
	String executeJasperXLSX(String fileName, Map<String, Object> parameters);
	//CSV
	String executeJasperCSV(String fileName, Map<String, Object> parameters);
	//DOCX
	String executeJasperDOCX(String fileName, Map<String, Object> parameters);
	List<AccountOfficersForm> getListofOfficerPerAocode();
	String executeJasperXLSXwithOutputFileName(String fileName, Map<String, Object> parameters, String outputfilePath,
			String outputfileName);

	//MAR
	String ordinal(int i);
	String capitalize(String text);
	String intToRoman(int num);
	
}
