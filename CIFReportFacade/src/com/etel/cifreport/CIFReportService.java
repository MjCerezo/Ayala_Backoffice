package com.etel.cifreport;

import java.util.List;

import com.etel.cifreport.form.AllUserForm;
import com.etel.cifreport.form.CIFComprehensiveForm;
import com.etel.cifreport.form.CIFExceptionalReportForm;
import com.etel.cifreport.form.CIFParameterForm;
import com.etel.cifreport.form.CIFSchedulesReportForm;
import com.etel.cifreport.form.CIFTransactionalReportForm;
import com.etel.cifreport.form.CIFWorkFlowForm;

public interface CIFReportService {

	List<CIFTransactionalReportForm> getListofCIFEnrollmentTransactionsIndividual(CIFParameterForm forms);

	List<CIFTransactionalReportForm> getListofCIFEnrollmentTransactionsBusiness(CIFParameterForm forms);

	List<CIFSchedulesReportForm> getListofCustomersIndividual(CIFParameterForm forms);

	List<CIFSchedulesReportForm> getListofCustomersBusiness(CIFParameterForm forms);
	List<CIFTransactionalReportForm> getLOSLink();
	List<CIFTransactionalReportForm> getCIFLink();

	List<CIFWorkFlowForm> getCIFWorkflow(String workflowid);

	List<CIFExceptionalReportForm> getListofCustomersFATCAIndividual(CIFParameterForm forms);

	List<CIFExceptionalReportForm> getListofCustomersFATCABusiness(CIFParameterForm forms);

	List<CIFExceptionalReportForm> getListofCustomersPEPIndividual(CIFParameterForm forms);

	List<CIFExceptionalReportForm> getListofCustomersPEPBusiness(CIFParameterForm forms);

	List<CIFExceptionalReportForm> getListofCustomersDOSRIIndividual(CIFParameterForm forms);

	List<CIFExceptionalReportForm> getListofCustomersDOSRIBusiness(CIFParameterForm forms);

	List<CIFExceptionalReportForm> getListofExceptionalAccountsFATCAIndividual(CIFParameterForm forms);

	List<CIFExceptionalReportForm> getListofExceptionalAccountsFATCABusiness(CIFParameterForm forms);

	List<CIFExceptionalReportForm> getListofExceptionalAccountsPEPIndividual(CIFParameterForm forms);

	List<CIFExceptionalReportForm> getListofExceptionalAccountsPEPBusiness(CIFParameterForm forms);

	List<CIFExceptionalReportForm> getListofExceptionalAccountsDOSRIIndividual(CIFParameterForm forms);

	List<CIFExceptionalReportForm> getListofExceptionalAccountsDOSRIBusiness(CIFParameterForm forms);

	List<CIFTransactionalReportForm> getListofClientProfileUpdate(CIFParameterForm forms);

	List<AllUserForm> getListofUser(String branchcode, String companycode);

	List<CIFComprehensiveForm> getCIFComprehensive(CIFParameterForm forms);


}
