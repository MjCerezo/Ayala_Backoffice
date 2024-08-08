package com.etel.CICReport;

import java.util.List;

import com.etel.cicreports.form.CICDataForm;
import com.etel.cicreports.form.CICParametersForm;

public interface CICReportService {

	List<CICDataForm> getCoveredTransaction(CICParametersForm forms);

	
}
