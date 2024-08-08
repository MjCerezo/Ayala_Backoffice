package com.etel.cifreport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.etel.casareports.form.CASASavingReportForm;
import com.etel.cifreport.form.AllUserForm;
import com.etel.cifreport.form.CIFComprehensiveForm;
import com.etel.cifreport.form.CIFExceptionalReportForm;
import com.etel.cifreport.form.CIFParameterForm;
import com.etel.cifreport.form.CIFSchedulesReportForm;
import com.etel.cifreport.form.CIFTransactionalReportForm;
import com.etel.cifreport.form.CIFWorkFlowForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.reports.ReportsFacadeImpl;
import com.etel.reports.ReportsFacadeService;
import com.etel.utils.HQLUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class CIFReportServiceImpl implements CIFReportService {

	private ReportsFacadeService rptservice = new ReportsFacadeImpl();
	private Map<String, Object> params = new HashMap<String, Object>();
	SecurityService serviceS = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private Map<String, Object> param = HQLUtil.getMap();
	DBService dbService = new DBServiceImpl();
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CIFTransactionalReportForm> getLOSLink() {
		List<CIFTransactionalReportForm> list = new ArrayList<CIFTransactionalReportForm>();
		try {
			
			String myQuery = "SELECT losLink AS losDbLink FROM TBPROPERTIES";
			System.out.print("MAR " + myQuery);
			list = (List<CIFTransactionalReportForm>) dbService.execSQLQueryTransformer(myQuery, param,CIFTransactionalReportForm.class, 1);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CIFTransactionalReportForm> getCIFLink() {
		List<CIFTransactionalReportForm> list = new ArrayList<CIFTransactionalReportForm>();
		try {
			
			String myQuery = "SELECT cifLink AS cifDbLink FROM TBPROPERTIES";
			System.out.print("MAR " + myQuery);
			list = (List<CIFTransactionalReportForm>) dbService.execSQLQueryTransformer(myQuery, param,CIFTransactionalReportForm.class, 1);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CIFWorkFlowForm> getCIFWorkflow(String workflowid) {
		List<CIFWorkFlowForm> list = new ArrayList<CIFWorkFlowForm>();
		try {
			param.put("workflowid", workflowid);
			
			String myQuery = "SELECT  processid, workflowid, processname FROM TBWORKFLOWPROCESS WHERE workflowid = :workflowid";
			System.out.print("MAR " + myQuery);
			list = (List<CIFWorkFlowForm>) dbService.execSQLQueryTransformer(myQuery, param,CIFWorkFlowForm.class, 1);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}
	//Transactional Reports
	@SuppressWarnings("unchecked")
	@Override
	public List<CIFTransactionalReportForm> getListofCIFEnrollmentTransactionsIndividual(CIFParameterForm forms) {
		List<CIFTransactionalReportForm> list = new ArrayList<CIFTransactionalReportForm>();
		param.put("startDate", forms.getStartDate() == null ? "" : forms.getStartDate());
		param.put("endDate", forms.getEndDate() == null ? "" : forms.getEndDate());
		param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
		param.put("tellerName", forms.getTellerName() == null ? "" : forms.getTellerName());
		param.put("gender", forms.getGender() == null ? "" : forms.getGender());
		param.put("civilStat", forms.getCivilStat() == null ? "" : forms.getCivilStat());
		param.put("nationality", forms.getNationality() == null ? "" : forms.getNationality());
		param.put("cifStat", forms.getCifStat() == null ? "" : forms.getCifStat());
		param.put("riskRating", forms.getRiskRating() == null ? "" : forms.getRiskRating());
		try {
			list = (List<CIFTransactionalReportForm>) dbService.execStoredProc(
					"EXEC sp_CIF_ListofCIFEnrollmentTransactionsIndividual :startDate, :endDate, :branch, :tellerName, :gender, :civilStat, :nationality, :cifStat, :riskRating", param,
					CIFTransactionalReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CIFTransactionalReportForm> getListofCIFEnrollmentTransactionsBusiness(CIFParameterForm forms) {
		List<CIFTransactionalReportForm> list = new ArrayList<CIFTransactionalReportForm>();
		try {
			param.put("startDate", forms.getStartDate() == null ? "" : forms.getStartDate());
			param.put("endDate", forms.getEndDate() == null ? "" : forms.getEndDate());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("tellerName", forms.getTellerName() == null ? "" : forms.getTellerName());
			param.put("registrationNo", forms.getRegisterTin() == null ? "" : forms.getRegisterTin());
			param.put("businessType", forms.getBusinessType() == null ? "" : forms.getBusinessType());
			param.put("paidUpCapital", forms.getPaidUpCapital() == null ? "" : forms.getPaidUpCapital());
			param.put("firmSize", forms.getFirmSize() == null ? "" : forms.getFirmSize());
			param.put("nationality", forms.getNationality() == null ? "" : forms.getNationality());
			param.put("cifStat", forms.getCifStat() == null ? "" : forms.getCifStat());
			param.put("riskRating", forms.getRiskRating() == null ? "" : forms.getRiskRating());
			
			list = (List<CIFTransactionalReportForm>) dbService.execStoredProc(
					"EXEC sp_CIF_ListofCIFEnrollmentTransactionsBusiness :startDate, :endDate, :branch, :tellerName, :registrationNo, "
					+ ":businessType, :paidUpCapital, :firmSize, :nationality, :cifStat, :riskRating", param,
					CIFTransactionalReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return list;
	}

	//Schedules
	@SuppressWarnings("unchecked")
	@Override
	public List<CIFSchedulesReportForm> getListofCustomersIndividual(CIFParameterForm forms) {
		List<CIFSchedulesReportForm> list = new ArrayList<CIFSchedulesReportForm>();
		try {
			param.put("asOf", forms.getAsOf() == null ? "" : forms.getAsOf());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("tellerName", forms.getTellerName() == null ? "" : forms.getTellerName());
			list = (List<CIFSchedulesReportForm>) dbService.execStoredProc(
					"EXEC sp_CIF_ListOfCustomerIndividual :asOf, :branch, :tellerName", param,
					CIFSchedulesReportForm.class, 1, null);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CIFSchedulesReportForm> getListofCustomersBusiness(CIFParameterForm forms) {
		List<CIFSchedulesReportForm> list = new ArrayList<CIFSchedulesReportForm>();
		try {
			param.put("asOf", forms.getAsOf() == null ? "" : forms.getAsOf());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("tellerName", forms.getTellerName() == null ? "" : forms.getTellerName());
			
			list = (List<CIFSchedulesReportForm>) dbService.execStoredProc(
					"EXEC sp_CIF_ListOfCustomerBusiness :asOf, :branch, :tellerName", param,
					CIFSchedulesReportForm.class, 1, null);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}

	//Exceptional Reports

	
	@SuppressWarnings("unchecked")
	@Override
	public List<CIFExceptionalReportForm> getListofExceptionalAccountsFATCAIndividual(CIFParameterForm forms) {
		List<CIFExceptionalReportForm> list = new ArrayList<CIFExceptionalReportForm>();
		try {
			param.put("branch", forms.getBranch());
			param.put("tellerName", forms.getTellerName());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			param.put("startDate", forms.getStartDate());
			param.put("endDate", forms.getEndDate());
			
			String myQuery = "SELECT c.encodeddate AS dateEncoded, c.fullname AS custName," + 
					" (SELECT branchname FROM "+ forms.getLosDbLink() + ".dbo.TBBRANCH WHERE branchcode =c.originatingbranch) AS branch," + 
					" a.cifno AS cifNo, c.dateofbirth AS dateOfBirth, b.placeofbirth AS cityOfBirth," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'GENDER' AND codevalue = b.gender) AS gender," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'CIVILSTATUS' AND codevalue = b.civilstatus) AS civilStat," + 
					" (SELECT DISTINCT country FROM "+ forms.getLosDbLink() + ".dbo.TBCOUNTRY WHERE code = b.nationality) AS nationality," + 
					" c.fulladdress1, b.tin," + 
					" (SELECT processname FROM "+ forms.getLosDbLink() + ".dbo.TBWORKFLOWPROCESS WHERE processid = c.cifstatus) AS cifStatus," + 
					" a.dateupdated AS statDate," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'RISKAPPETITE' AND codevalue =c.riskrating) AS riskRating," + 
					" CASE" + 
					"	WHEN a.q1 = 'true'" + 
					"	THEN 'US Citizen is Marked'" + 
					"	ELSE ''" + 
					" END AS usCitizenOrGreenCardHolder," + 
					" CASE" + 
					"	WHEN a.q2 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS usResidentResidesDaysMore183," + 
					" CASE" + 
					"	WHEN a.q3 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS payTaxesInTheUS," + 
					" CASE" + 
					"	WHEN a.q5 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS grantPowerOfAttority," + 
					" a.ustin AS usTIN," + 
					" c.encodedby" + 
					" FROM "+ forms.getCifDbLink() + ".dbo.TBFATCA a" + 
					" LEFT JOIN "+ forms.getCifDbLink() + ".dbo.TBCIFINDIVIDUAL b ON a.cifno = b.cifno" + 
					" LEFT JOIN "+ forms.getCifDbLink() + ".dbo.TBCIFMAIN c ON a.cifno = c.cifno" + 
					" WHERE c.customertype = '1' AND c.cifstatus = '3'";

			if(forms.getStartDate() != null || forms.getEndDate() != null) {
				myQuery += " AND CAST(c.encodeddate AS DATE) BETWEEN CAST(:startDate AS DATE) AND CAST(:endDate AS DATE) ";
			}
			if(forms.getBranch() != null) {
				myQuery += " AND c.originatingbranch = :branch";
			}
			if(forms.getTellerName() != null) {
				myQuery += " AND c.encodedby = :tellerName";
			}
			System.out.print("MAR " + myQuery);
			list = (List<CIFExceptionalReportForm>) dbService.execSQLQueryTransformer(myQuery, param,CIFExceptionalReportForm.class, 1);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CIFExceptionalReportForm> getListofExceptionalAccountsPEPIndividual(CIFParameterForm forms) {
		List<CIFExceptionalReportForm> list = new ArrayList<CIFExceptionalReportForm>();
		try {
			param.put("branch", forms.getBranch());
			param.put("tellerName", forms.getTellerName());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			param.put("startDate", forms.getStartDate());
			param.put("endDate", forms.getEndDate());
			
			String myQuery = "SELECT c.encodeddate AS dateEncoded, c.fullname AS custName," + 
					" (SELECT branchname FROM "+ forms.getLosDbLink() + ".dbo.TBBRANCH WHERE branchcode = c.originatingbranch) AS branch," + 
					" a.cifno AS cifNo, c.dateofbirth AS dateOfBirth, b.placeofbirth AS cityOfBirth," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'GENDER' AND codevalue = b.gender) AS gender," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'CIVILSTATUS' AND codevalue = b.civilstatus) AS civilStat," + 
					" (SELECT DISTINCT country FROM "+ forms.getLosDbLink() + ".dbo.TBCOUNTRY WHERE code = b.nationality) AS nationality," + 
					" c.fulladdress1, b.tin," + 
					" (SELECT processname FROM "+ forms.getLosDbLink() + ".dbo.TBWORKFLOWPROCESS WHERE processid = c.cifstatus) AS cifStatus," + 
					" a.dateupdated AS statDate," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'RISKAPPETITE' AND codevalue = c.riskrating) AS riskRating," + 
					" CASE" + 
					"	WHEN a.q1 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS govOffName," + 
					" CASE" + 
					"	WHEN a.govposition1 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS positionFromQ1," + 
					" CASE" + 
					"	WHEN a.q2 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS govOffName2," + 
					" CASE" + 
					"	WHEN a.govposition2 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS positionFromQ2," + 
					" CASE" + 
					"	WHEN a.q3 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS govOffName3," + 
					" CASE" + 
					"	WHEN d.govposition = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS positionFromQ3," + 
					" CASE" + 
					"	WHEN d.relationship = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS relationshipFromQ3," + 
					" c.encodedby" + 
					" FROM"+ forms.getCifDbLink() + ".dbo.TBPEPINFO a" + 
					" LEFT JOIN"+ forms.getCifDbLink() + ".dbo.TBCIFINDIVIDUAL b ON a.cifno = b.cifno" + 
					" LEFT JOIN"+ forms.getCifDbLink() + ".dbo.TBCIFMAIN c ON a.cifno = c.cifno" + 
					" LEFT JOIN"+ forms.getCifDbLink() + ".dbo.TBPEPQ3 d ON a.cifno = d.cifno" + 
					" WHERE c.customertype = '1' AND c.cifstatus = '3'";

			if(forms.getStartDate() != null || forms.getEndDate() != null) {
				myQuery += " AND CAST(c.encodeddate AS DATE) BETWEEN CAST(:startDate AS DATE) AND CAST(:endDate AS DATE) ";
			}
			if(forms.getBranch() != null) {
				myQuery += " AND c.originatingbranch = :branch";
			}
			if(forms.getTellerName() != null) {
				myQuery += " AND c.encodedby = :tellerName";
			}
			System.out.print("MAR " + myQuery);
			list = (List<CIFExceptionalReportForm>) dbService.execSQLQueryTransformer(myQuery, param,CIFExceptionalReportForm.class, 1);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CIFExceptionalReportForm> getListofExceptionalAccountsDOSRIIndividual(CIFParameterForm forms) {
		List<CIFExceptionalReportForm> list = new ArrayList<CIFExceptionalReportForm>();
		try {
			param.put("branch", forms.getBranch());
			param.put("tellerName", forms.getTellerName());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			param.put("startDate", forms.getStartDate());
			param.put("endDate", forms.getEndDate());
			
			String myQuery = "SELECT c.encodeddate AS dateEncoded, c.fullname AS custName," + 
					" (SELECT branchname FROM "+ forms.getLosDbLink() + ".dbo.TBBRANCH WHERE branchcode = c.originatingbranch) AS branch," + 
					" a.cifno AS cifNo, c.dateofbirth AS dateOfBirth, b.placeofbirth AS cityOfBirth," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'GENDER' AND codevalue = b.gender) AS gender," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'CIVILSTATUS' AND codevalue = b.civilstatus) AS civilStat," + 
					" (SELECT DISTINCT country FROM "+ forms.getLosDbLink() + ".dbo.TBCOUNTRY WHERE code = b.nationality) AS nationality," + 
					" c.fulladdress1, b.tin," + 
					" (SELECT processname FROM "+ forms.getLosDbLink() + ".dbo.TBWORKFLOWPROCESS WHERE processid = c.cifstatus) AS cifStatus," + 
					" a.dateupdated AS statDate," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'RISKAPPETITE' AND codevalue = c.riskrating) AS riskRating," + 
					" CASE" + 
					"	WHEN a.q1 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS stockholderOfCom," + 
					" CASE" + 
					"	WHEN a.q2 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS companyDirector," + 
					" CASE" + 
					"	WHEN a.q3 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS dosri," + 
					" c.encodedby" + 
					" FROM"+ forms.getCifDbLink() + ".dbo.TBDOSRI a" + 
					" LEFT JOIN"+ forms.getCifDbLink() + ".dbo.TBCIFINDIVIDUAL b ON a.cifno = b.cifno" + 
					" LEFT JOIN"+ forms.getCifDbLink() + ".dbo.TBCIFMAIN c ON a.cifno = c.cifno" + 
					" WHERE c.customertype = '1' AND c.cifstatus = '3'";

			if(forms.getStartDate() != null || forms.getEndDate() != null) {
				myQuery += " AND CAST(c.encodeddate AS DATE) BETWEEN CAST(:startDate AS DATE) AND CAST(:endDate AS DATE) ";
			}
			if(forms.getBranch() != null) {
				myQuery += " AND c.originatingbranch = :branch";
			}
			if(forms.getTellerName() != null) {
				myQuery += " AND c.encodedby = :tellerName";
			}
			System.out.print("MAR " + myQuery);
			list = (List<CIFExceptionalReportForm>) dbService.execSQLQueryTransformer(myQuery, param,CIFExceptionalReportForm.class, 1);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CIFExceptionalReportForm> getListofExceptionalAccountsFATCABusiness(CIFParameterForm forms) {
		List<CIFExceptionalReportForm> list = new ArrayList<CIFExceptionalReportForm>();
		try {
			param.put("branch", forms.getBranch());
			param.put("tellerName", forms.getTellerName());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			param.put("startDate", forms.getStartDate());
			param.put("endDate", forms.getEndDate());
			
			String myQuery = "SELECT c.encodeddate AS dateEncoded, b.registrationno AS registrationNo," + 
					" (SELECT branchname FROM "+ forms.getLosDbLink() + ".dbo.TBBRANCH WHERE branchcode = c.originatingbranch) AS branch," + 
					" (SELECT processname FROM "+ forms.getLosDbLink() + ".dbo.TBWORKFLOWPROCESS WHERE processid = c.cifstatus) AS cifStatus," + 
					" c.fullname AS companyName," + 
					" a.cifno AS cifNo, b.dateofincorporation AS dateOfCorporation," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'FIRMSIZE' AND codevalue = b.firmsize) AS firmSize," + 
					" (SELECT DISTINCT country FROM "+ forms.getLosDbLink() + ".dbo.TBCOUNTRY WHERE code = b.nationality) AS nationality," + 
					" b.tin, b.sss," + 
					" (SELECT psicdesc FROM "+ forms.getCifDbLink() + ".dbo.TBPSICCODES WHERE psiccode = b.psiccode) AS industryCode," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'BUSINESSTYPE' AND codevalue = b.businesstype) AS businessType," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'PAIDUPCAPITAL' AND codevalue = b.paidupcapital) AS paidUpCapital," + 
					" c.fulladdress1, b.country1, b.city1," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'RISKAPPETITE' AND codevalue = c.riskrating) AS riskRating," + 
					" CASE" + 
					"	WHEN a.q1 = 'true'" + 
					"	THEN 'US Citizen is Marked'" + 
					"	ELSE ''" + 
					" END AS usCitizenOrGreenCardHolder," + 
					" CASE" + 
					"	WHEN a.q2 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS usResidentResidesDaysMore183," + 
					" CASE" + 
					"	WHEN a.q3 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS payTaxesInTheUS," + 
					" CASE" + 
					"	WHEN a.q5 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS grantPowerOfAttority," + 
					" a.ustin AS usTIN," + 
					" a.dateupdated AS statDate, c.encodedby" + 
					" FROM "+ forms.getCifDbLink() + ".dbo.TBFATCA a" + 
					" LEFT JOIN "+ forms.getCifDbLink() + ".dbo.TBCIFMAIN c ON a.cifno = c.cifno" + 
					" LEFT JOIN "+ forms.getCifDbLink() + ".dbo.TBCIFCORPORATE b ON a.cifno = b.cifno" + 
					" WHERE c.customertype = '2' AND c.cifstatus = '3'";

			if(forms.getStartDate() != null || forms.getEndDate() != null) {
				myQuery += " AND CAST(c.encodeddate AS DATE) BETWEEN CAST(:startDate AS DATE) AND CAST(:endDate AS DATE) ";
			}
			if(forms.getBranch() != null) {
				myQuery += " AND c.originatingbranch = :branch";
			}
			if(forms.getTellerName() != null) {
				myQuery += " AND c.encodedby = :tellerName";
			}
			System.out.print("MAR " + myQuery);
			list = (List<CIFExceptionalReportForm>) dbService.execSQLQueryTransformer(myQuery, param,CIFExceptionalReportForm.class, 1);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}	

	@SuppressWarnings("unchecked")
	@Override
	public List<CIFExceptionalReportForm> getListofExceptionalAccountsPEPBusiness(CIFParameterForm forms) {
		List<CIFExceptionalReportForm> list = new ArrayList<CIFExceptionalReportForm>();
		try {
			param.put("branch", forms.getBranch());
			param.put("tellerName", forms.getTellerName());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			param.put("startDate", forms.getStartDate());
			param.put("endDate", forms.getEndDate());
			
			String myQuery = "SELECT c.encodeddate AS dateEncoded, b.registrationno AS registrationNo," + 
					" (SELECT branchname FROM "+ forms.getLosDbLink() + ".dbo.TBBRANCH WHERE branchcode = c.originatingbranch) AS branch," + 
					" (SELECT processname FROM "+ forms.getLosDbLink() + ".dbo.TBWORKFLOWPROCESS WHERE processid = c.cifstatus) AS cifStatus," + 
					" c.fullname AS companyName," + 
					" a.cifno AS cifNo, b.dateofincorporation AS dateOfCorporation," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'FIRMSIZE' AND codevalue = b.firmsize) AS firmSize," + 
					" (SELECT DISTINCT country FROM "+ forms.getLosDbLink() + ".dbo.TBCOUNTRY WHERE code = b.nationality) AS nationality," + 
					" b.tin, b.sss," + 
					" (SELECT psicdesc FROM "+ forms.getCifDbLink() + ".dbo.TBPSICCODES WHERE psiccode = b.psiccode) AS industryCode," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'BUSINESSTYPE' AND codevalue = b.businesstype) AS businessType," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'PAIDUPCAPITAL' AND codevalue = b.paidupcapital) AS paidUpCapital," + 
					" c.fulladdress1, b.country1, b.city1," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'RISKAPPETITE' AND codevalue = c.riskrating) AS riskRating," + 
					" CASE" + 
					"	WHEN a.q1 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS govOffName," + 
					" CASE" + 
					"	WHEN a.govposition1 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS positionFromQ1," + 
					" CASE" + 
					"	WHEN a.q2 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS govOffName2," + 
					" CASE" + 
					"	WHEN a.govposition2 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS positionFromQ2," + 
					" CASE" + 
					"	WHEN a.q3 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS govOffName3," + 
					" CASE" + 
					"	WHEN d.govposition = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS positionFromQ3," + 
					" CASE" + 
					"	WHEN d.relationship = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS relationshipFromQ3," + 
					" a.dateupdated AS statDate, c.encodedby" + 
					" FROM "+ forms.getCifDbLink() + ".dbo.TBPEPINFO a" + 
					" LEFT JOIN "+ forms.getCifDbLink() + ".dbo.TBCIFCORPORATE b ON a.cifno = b.cifno" + 
					" LEFT JOIN "+ forms.getCifDbLink() + ".dbo.TBCIFMAIN c ON a.cifno = c.cifno" + 
					" LEFT JOIN "+ forms.getCifDbLink() + ".dbo.TBPEPQ3 d ON a.cifno = d.cifno" + 
					" WHERE c.customertype = '2' AND c.cifstatus = '3'";

			if(forms.getStartDate() != null || forms.getEndDate() != null) {
				myQuery += " AND CAST(c.encodeddate AS DATE) BETWEEN CAST(:startDate AS DATE) AND CAST(:endDate AS DATE) ";
			}
			if(forms.getBranch() != null) {
				myQuery += " AND c.originatingbranch = :branch";
			}
			if(forms.getTellerName() != null) {
				myQuery += " AND c.encodedby = :tellerName";
			}
			System.out.print("MAR " + myQuery);
			list = (List<CIFExceptionalReportForm>) dbService.execSQLQueryTransformer(myQuery, param,CIFExceptionalReportForm.class, 1);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CIFExceptionalReportForm> getListofExceptionalAccountsDOSRIBusiness(CIFParameterForm forms) {
		List<CIFExceptionalReportForm> list = new ArrayList<CIFExceptionalReportForm>();
		try {
			param.put("branch", forms.getBranch());
			param.put("tellerName", forms.getTellerName());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			param.put("startDate", forms.getStartDate());
			param.put("endDate", forms.getEndDate());
			
			String myQuery = "SELECT c.encodeddate AS dateEncoded, b.registrationno AS registrationNo," + 
					" (SELECT branchname FROM "+ forms.getLosDbLink() + ".dbo.TBBRANCH WHERE branchcode = c.originatingbranch) AS branch," + 
					" (SELECT processname FROM "+ forms.getLosDbLink() + ".dbo.TBWORKFLOWPROCESS WHERE processid = c.cifstatus) AS cifStatus," + 
					" c.fullname AS companyName," + 
					" a.cifno AS cifNo, b.dateofincorporation AS dateOfCorporation," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'FIRMSIZE' AND codevalue = b.firmsize) AS firmSize," + 
					" (SELECT DISTINCT country FROM "+ forms.getLosDbLink() + ".dbo.TBCOUNTRY WHERE code = b.nationality) AS nationality," + 
					" b.tin, b.sss," + 
					" (SELECT psicdesc FROM "+ forms.getCifDbLink() + ".dbo.TBPSICCODES WHERE psiccode = b.psiccode) AS industryCode," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'BUSINESSTYPE' AND codevalue = b.businesstype) AS businessType," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'PAIDUPCAPITAL' AND codevalue = b.paidupcapital) AS paidUpCapital," + 
					" c.fulladdress1, b.country1, b.city1," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'RISKAPPETITE' AND codevalue = c.riskrating) AS riskRating," + 
					" CASE" + 
					"	WHEN a.q1 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS stockholderOfCom," + 
					" CASE" + 
					"	WHEN a.q2 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS companyDirector," + 
					" CASE" + 
					"	WHEN a.q3 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS dosri," + 
					" a.dateupdated AS statDate, c.encodedby" + 
					" FROM "+ forms.getCifDbLink() + ".dbo.TBDOSRI a" + 
					" LEFT JOIN "+ forms.getCifDbLink() + ".dbo.TBCIFCORPORATE b ON a.cifno = b.cifno" + 
					" LEFT JOIN "+ forms.getCifDbLink() + ".dbo.TBCIFMAIN c ON a.cifno = c.cifno" + 
					" WHERE c.customertype = '2' AND c.cifstatus = '3'";

			if(forms.getStartDate() != null || forms.getEndDate() != null) {
				myQuery += " AND CAST(c.encodeddate AS DATE) BETWEEN CAST(:startDate AS DATE) AND CAST(:endDate AS DATE) ";
			}
			if(forms.getBranch() != null) {
				myQuery += " AND c.originatingbranch = :branch";
			}
			if(forms.getTellerName() != null) {
				myQuery += " AND c.encodedby = :tellerName";
			}
			System.out.print("MAR " + myQuery);
			list = (List<CIFExceptionalReportForm>) dbService.execSQLQueryTransformer(myQuery, param,CIFExceptionalReportForm.class, 1);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}

	//08-02-2022
	//Customer List
	@SuppressWarnings("unchecked")
	@Override
	public List<CIFExceptionalReportForm> getListofCustomersFATCAIndividual(CIFParameterForm forms) {
		List<CIFExceptionalReportForm> list = new ArrayList<CIFExceptionalReportForm>();
		try {
			param.put("branch", forms.getBranch());
			param.put("tellerName", forms.getTellerName());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			param.put("asOf", forms.getAsOf());
			param.put("riskRating", forms.getRiskRating());
			
			String myQuery = "SELECT c.encodeddate AS dateEncoded, c.fullname AS custName, " + 
					" (SELECT branchname FROM "+ forms.getLosDbLink() + ".dbo.TBBRANCH WHERE branchcode = c.originatingbranch) AS branch,  " + 
					" a.cifno AS cifNo, c.dateofbirth AS dateOfBirth, b.placeofbirth AS cityOfBirth,  " + 
					" (SELECT desc1 FROM "+ forms.getCifDbLink() + ".dbo.TBCODETABLE WHERE codename = 'GENDER' AND codevalue = b.gender) AS gender,  " + 
					" (SELECT desc1 FROM "+ forms.getCifDbLink() + ".dbo.TBCODETABLE WHERE codename = 'CIVILSTATUS' AND codevalue = b.civilstatus) AS civilStat,  " + 
					" (SELECT DISTINCT country FROM "+ forms.getCifDbLink() + ".dbo.TBCOUNTRY WHERE code = b.nationality) AS nationality, c.fulladdress1, b.tin,  " + 
					" (SELECT processname FROM "+ forms.getLosDbLink() + ".dbo.TBWORKFLOWPROCESS WHERE processid = c.cifstatus) AS cifStatus,  " + 
					" a.dateupdated AS statDate, " + 
					" (SELECT desc1 FROM "+ forms.getCifDbLink() + ".dbo.TBCODETABLE WHERE codename = 'RISKAPPETITE' AND codevalue = c.riskrating) AS riskRating,  " + 
					" CASE  WHEN a.q1 = 'true'  THEN 'US Citizen is Marked'  ELSE ''  END AS usCitizenOrGreenCardHolder,  " + 
					" CASE  WHEN a.q2 = 'true'  THEN 'As Marked'  ELSE ''  END AS usResidentResidesDaysMore183,  " + 
					" CASE  WHEN a.q3 = 'true'  THEN 'As Marked'  ELSE ''  END AS payTaxesInTheUS,  " + 
					" CASE  WHEN a.q5 = 'true'  THEN 'As Marked'  ELSE ''  END AS grantPowerOfAttority,  a.ustin AS usTIN,  c.encodedby " + 
					" FROM "+ forms.getCifDbLink() + ".dbo.TBFATCA a " + 
					" LEFT JOIN "+ forms.getCifDbLink() + ".dbo.TBCIFINDIVIDUAL b ON a.cifno = b.cifno " + 
					" LEFT JOIN "+ forms.getCifDbLink() + ".dbo.TBCIFMAIN c ON a.cifno = c.cifno  " + 
					" WHERE c.customertype = '1' AND c.cifstatus = '3'";

			if(forms.getAsOf() != null) {
				myQuery += " AND CAST(a.dateupdated AS DATE) <= CAST(:asOf AS DATE)";
			}
			if(forms.getBranch() != null) {
				myQuery += " AND c.originatingbranch = :branch";
			}
			if(forms.getTellerName() != null) {
				myQuery += " AND c.encodedby = :tellerName";
			}
			if(forms.getRiskRating() != null) {
				myQuery += " AND c.riskrating = :riskRating ";
			}
			System.out.print("MAR " + myQuery);
			list = (List<CIFExceptionalReportForm>) dbService.execSQLQueryTransformer(myQuery, param,CIFExceptionalReportForm.class, 1);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CIFExceptionalReportForm> getListofCustomersPEPIndividual(CIFParameterForm forms) {
		List<CIFExceptionalReportForm> list = new ArrayList<CIFExceptionalReportForm>();
		try {
			param.put("branch", forms.getBranch());
			param.put("tellerName", forms.getTellerName());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			param.put("asOf", forms.getAsOf());
			param.put("riskRating", forms.getRiskRating());
			
			String myQuery = "SELECT c.encodeddate AS dateEncoded, c.fullname AS custName," + 
					" (SELECT branchname FROM "+ forms.getLosDbLink() + ".dbo.TBBRANCH WHERE branchcode = c.originatingbranch) AS branch," + 
					" a.cifno AS cifNo, c.dateofbirth AS dateOfBirth, b.placeofbirth AS cityOfBirth," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'GENDER' AND codevalue = b.gender) AS gender," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'CIVILSTATUS' AND codevalue = b.civilstatus) AS civilStat," + 
					" (SELECT DISTINCT country FROM "+ forms.getLosDbLink() + ".dbo.TBCOUNTRY WHERE code = b.nationality) AS nationality," + 
					" c.fulladdress1, b.tin," + 
					" (SELECT processname FROM "+ forms.getLosDbLink() + ".dbo.TBWORKFLOWPROCESS WHERE processid = c.cifstatus) AS cifStatus," + 
					" a.dateupdated AS statDate," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'RISKAPPETITE' AND codevalue = c.riskrating) AS riskRating," + 
					" CASE" + 
					"	WHEN a.q1 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS govOffName," + 
					" CASE" + 
					"	WHEN a.govposition1 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS positionFromQ1," + 
					" CASE" + 
					"	WHEN a.q2 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS govOffName2," + 
					" CASE" + 
					"	WHEN a.govposition2 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS positionFromQ2," + 
					" CASE" + 
					"	WHEN a.q3 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS govOffName3," + 
					" CASE" + 
					"	WHEN d.govposition = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS positionFromQ3," + 
					" CASE" + 
					"	WHEN d.relationship = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS relationshipFromQ3," + 
					" c.encodedby" + 
					" FROM "+ forms.getCifDbLink() + ".dbo.TBPEPINFO a" + 
					" LEFT JOIN "+ forms.getCifDbLink() + ".dbo.TBCIFINDIVIDUAL b ON a.cifno = b.cifno" + 
					" LEFT JOIN "+ forms.getCifDbLink() + ".dbo.TBCIFMAIN c ON a.cifno = c.cifno" + 
					" LEFT JOIN "+ forms.getCifDbLink() + ".dbo.TBPEPQ3 d ON a.cifno = d.cifno" + 
					" WHERE c.customertype = '1' AND c.cifstatus = '3'";

			if(forms.getAsOf() != null) {
				myQuery += " AND CAST(c.encodeddate AS DATE) <= CAST(:asOf AS DATE) ";
			}
			if(forms.getBranch() != null) {
				myQuery += " AND c.originatingbranch = :branch";
			}
			if(forms.getTellerName() != null) {
				myQuery += " AND c.encodedby = :tellerName";
			}
			if(forms.getRiskRating() != null) {
				myQuery += " AND c.riskrating = :riskRating ";
			}
			System.out.print("MAR " + myQuery);
			list = (List<CIFExceptionalReportForm>) dbService.execSQLQueryTransformer(myQuery, param,CIFExceptionalReportForm.class, 1);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CIFExceptionalReportForm> getListofCustomersDOSRIIndividual(CIFParameterForm forms) {
		List<CIFExceptionalReportForm> list = new ArrayList<CIFExceptionalReportForm>();
		try {
			param.put("branch", forms.getBranch());
			param.put("tellerName", forms.getTellerName());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			param.put("asOf", forms.getAsOf());
			param.put("noFilter", forms.getNoFilter());
			param.put("riskRating", forms.getRiskRating());
			
			String myQuery = "SELECT c.encodeddate AS dateEncoded, c.fullname AS custName," + 
					" (SELECT branchname FROM "+ forms.getLosDbLink() + ".dbo.TBBRANCH WHERE branchcode = c.originatingbranch) AS branch," + 
					" a.cifno AS cifNo, c.dateofbirth AS dateOfBirth, b.placeofbirth AS cityOfBirth," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'GENDER' AND codevalue = b.gender) AS gender," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'CIVILSTATUS' AND codevalue = b.civilstatus) AS civilStat," + 
					" (SELECT DISTINCT country FROM "+ forms.getLosDbLink() + ".dbo.TBCOUNTRY WHERE code = b.nationality) AS nationality," + 
					" c.fulladdress1, b.tin," + 
					" (SELECT processname FROM "+ forms.getLosDbLink() + ".dbo.TBWORKFLOWPROCESS WHERE processid = c.cifstatus) AS cifStatus," + 
					" a.dateupdated AS statDate," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'RISKAPPETITE' AND codevalue = c.riskrating) AS riskRating," + 
					" CASE" + 
					"	WHEN a.q1 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS stockholderOfCom," + 
					" CASE" + 
					"	WHEN a.q2 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS companyDirector," + 
					" CASE" + 
					"	WHEN a.q3 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS dosri," + 
					" c.encodedby" + 
					" FROM "+ forms.getCifDbLink() + ".dbo.TBDOSRI a" + 
					" LEFT JOIN "+ forms.getCifDbLink() + ".dbo.TBCIFINDIVIDUAL b ON a.cifno = b.cifno" + 
					" LEFT JOIN "+ forms.getCifDbLink() + ".dbo.TBCIFMAIN c ON a.cifno = c.cifno" + 
					" WHERE c.customertype = '1' AND c.cifstatus = '3'";

			if(forms.getAsOf() != null) {
				myQuery += " AND CAST(c.encodeddate AS DATE) <= CAST(:asOf AS DATE)";
			}
			if(forms.getBranch() != null) {
				myQuery += " AND c.originatingbranch = :branch";
			}
			if(forms.getTellerName() != null) {
				myQuery += " AND c.encodedby = :tellerName";
			}
			if(forms.getRiskRating() != null) {
				myQuery += " AND c.riskrating = :riskRating ";
			}
			System.out.print("MAR " + myQuery);
			list = (List<CIFExceptionalReportForm>) dbService.execSQLQueryTransformer(myQuery, param,CIFExceptionalReportForm.class, 1);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CIFExceptionalReportForm> getListofCustomersFATCABusiness(CIFParameterForm forms) {
		List<CIFExceptionalReportForm> list = new ArrayList<CIFExceptionalReportForm>();
		try {
			param.put("branch", forms.getBranch());
			param.put("tellerName", forms.getTellerName());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			param.put("asof", forms.getAsOf());
			param.put("riskrating", forms.getRiskRating());
			
			String myQuery = "SELECT c.encodeddate AS dateEncoded, b.registrationno AS registrationNo," + 
					" (SELECT branchname FROM "+ forms.getLosDbLink() + ".dbo.TBBRANCH WHERE branchcode = c.originatingbranch) AS branch," + 
					" (SELECT processname FROM "+ forms.getLosDbLink() + ".dbo.TBWORKFLOWPROCESS WHERE processid = c.cifstatus) AS cifStatus," + 
					" c.fullname AS companyName," + 
					" a.cifno AS cifNo, b.dateofincorporation AS dateOfCorporation," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'FIRMSIZE' AND codevalue = b.firmsize) AS firmSize," + 
					" (SELECT DISTINCT country FROM "+ forms.getLosDbLink() + ".dbo.TBCOUNTRY WHERE code = b.nationality) AS nationality," + 
					" b.tin, b.sss," + 
					" (SELECT psicdesc FROM "+ forms.getCifDbLink() + ".dbo.TBPSICCODES WHERE psiccode = b.psiccode) AS industryCode," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'BUSINESSTYPE' AND codevalue = b.businesstype) AS businessType," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'PAIDUPCAPITAL' AND codevalue = b.paidupcapital) AS paidUpCapital," + 
					" c.fulladdress1, b.country1, b.city1," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'RISKAPPETITE' AND codevalue = c.riskrating) AS riskRating," + 
					" CASE" + 
					"	WHEN a.q1 = 'true'" + 
					"	THEN 'US Citizen is Marked'" + 
					"	ELSE ''" + 
					" END AS usCitizenOrGreenCardHolder," + 
					" CASE" + 
					"	WHEN a.q2 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS usResidentResidesDaysMore183," + 
					" CASE" + 
					"	WHEN a.q3 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS payTaxesInTheUS," + 
					" CASE" + 
					"	WHEN a.q5 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS grantPowerOfAttority," + 
					" a.ustin AS usTIN," + 
					" a.dateupdated AS statDate, c.encodedby" + 
					" FROM "+ forms.getCifDbLink() + ".dbo.TBFATCA a" + 
					" LEFT JOIN "+ forms.getCifDbLink() + ".dbo.TBCIFMAIN c ON a.cifno = c.cifno" + 
					" LEFT JOIN "+ forms.getCifDbLink() + ".dbo.TBCIFCORPORATE b ON a.cifno = b.cifno" + 
					" WHERE c.customertype = '2' AND c.cifstatus = '3'";

			if(forms.getStartDate() != null || forms.getEndDate() != null) {
				myQuery += "  AND CAST(c.encodeddate AS DATE)  <= CAST(:asof AS DATE)";
			}
			if(forms.getBranch() != null) {
				myQuery += " AND c.originatingbranch = :branch";
			}
			if(forms.getTellerName() != null) {
				myQuery += " AND c.encodedby = :tellerName";
			}
			if(forms.getRiskRating() != null) {
				myQuery += " AND c.riskrating = :riskrating";
			}
			System.out.print("MAR " + myQuery);
			list = (List<CIFExceptionalReportForm>) dbService.execSQLQueryTransformer(myQuery, param,CIFExceptionalReportForm.class, 1);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CIFExceptionalReportForm> getListofCustomersPEPBusiness(CIFParameterForm forms) {
		List<CIFExceptionalReportForm> list = new ArrayList<CIFExceptionalReportForm>();
		try {
			param.put("branch", forms.getBranch());
			param.put("tellerName", forms.getTellerName());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			param.put("asof", forms.getAsOf());
			param.put("riskrating", forms.getRiskRating());
			
			String myQuery = "SELECT c.encodeddate AS dateEncoded, b.registrationno AS registrationNo," + 
					" (SELECT branchname FROM "+ forms.getLosDbLink() + ".dbo.TBBRANCH WHERE branchcode = c.originatingbranch) AS branch," + 
					" (SELECT processname FROM "+ forms.getLosDbLink() + ".dbo.TBWORKFLOWPROCESS WHERE processid = c.cifstatus) AS cifStatus," + 
					" c.fullname AS companyName," + 
					" a.cifno AS cifNo, b.dateofincorporation AS dateOfCorporation," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'FIRMSIZE' AND codevalue = b.firmsize) AS firmSize," + 
					" (SELECT DISTINCT country FROM "+ forms.getLosDbLink() + ".dbo.TBCOUNTRY WHERE code = b.nationality) AS nationality," + 
					" b.tin, b.sss," + 
					" (SELECT psicdesc FROM "+ forms.getCifDbLink() + ".dbo.TBPSICCODES WHERE psiccode = b.psiccode) AS industryCode," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'BUSINESSTYPE' AND codevalue = b.businesstype) AS businessType," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'PAIDUPCAPITAL' AND codevalue = b.paidupcapital) AS paidUpCapital," + 
					" c.fulladdress1, b.country1, b.city1," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'RISKAPPETITE' AND codevalue = c.riskrating) AS riskRating," + 
					" CASE" + 
					"	WHEN a.q1 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS govOffName," + 
					" CASE" + 
					"	WHEN a.govposition1 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS positionFromQ1," + 
					" CASE" + 
					"	WHEN a.q2 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS govOffName2," + 
					" CASE" + 
					"	WHEN a.govposition2 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS positionFromQ2," + 
					" CASE" + 
					"	WHEN a.q3 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS govOffName3," + 
					" CASE" + 
					"	WHEN d.govposition = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS positionFromQ3," + 
					" CASE" + 
					"	WHEN d.relationship = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS relationshipFromQ3," + 
					" a.dateupdated AS statDate, c.encodedby" + 
					" FROM "+ forms.getCifDbLink() + ".dbo.TBPEPINFO a" + 
					" LEFT JOIN "+ forms.getCifDbLink() + ".dbo.TBCIFCORPORATE b ON a.cifno = b.cifno" + 
					" LEFT JOIN "+ forms.getCifDbLink() + ".dbo.TBCIFMAIN c ON a.cifno = c.cifno" + 
					" LEFT JOIN "+ forms.getCifDbLink() + ".dbo.TBPEPQ3 d ON a.cifno = d.cifno" + 
					" WHERE c.customertype = '2' AND c.cifstatus = '3'";

			if(forms.getStartDate() != null || forms.getEndDate() != null) {
				myQuery += "  AND CAST(c.encodeddate AS DATE)  <= CAST(:asof AS DATE)";
			}
			if(forms.getBranch() != null) {
				myQuery += " AND c.originatingbranch = :branch";
			}
			if(forms.getTellerName() != null) {
				myQuery += " AND c.encodedby = :tellerName";
			}
			if(forms.getRiskRating() != null) {
				myQuery += " AND c.riskrating = :riskRating ";
			}
			System.out.print("MAR " + myQuery);
			list = (List<CIFExceptionalReportForm>) dbService.execSQLQueryTransformer(myQuery, param,CIFExceptionalReportForm.class, 1);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CIFExceptionalReportForm> getListofCustomersDOSRIBusiness(CIFParameterForm forms) {
		List<CIFExceptionalReportForm> list = new ArrayList<CIFExceptionalReportForm>();
		try {
			param.put("branch", forms.getBranch());
			param.put("tellerName", forms.getTellerName());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			param.put("asof", forms.getAsOf());
			param.put("riskrating", forms.getRiskRating());
			
			String myQuery = "SELECT c.encodeddate AS dateEncoded, b.registrationno AS registrationNo," + 
					" (SELECT branchname FROM "+ forms.getLosDbLink() + ".dbo.TBBRANCH WHERE branchcode = c.originatingbranch) AS branch," + 
					" (SELECT processname FROM "+ forms.getLosDbLink() + ".dbo.TBWORKFLOWPROCESS WHERE processid = c.cifstatus) AS cifStatus," + 
					" c.fullname AS companyName," + 
					" a.cifno AS cifNo, b.dateofincorporation AS dateOfCorporation," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'FIRMSIZE' AND codevalue = b.firmsize) AS firmSize," + 
					" (SELECT DISTINCT country FROM "+ forms.getLosDbLink() + ".dbo.TBCOUNTRY WHERE code = b.nationality) AS nationality," + 
					" b.tin, b.sss," + 
					" (SELECT psicdesc FROM "+ forms.getCifDbLink() + ".dbo.TBPSICCODES WHERE psiccode = b.psiccode) AS industryCode," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'BUSINESSTYPE' AND codevalue = b.businesstype) AS businessType," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'PAIDUPCAPITAL' AND codevalue = b.paidupcapital) AS paidUpCapital," + 
					" c.fulladdress1, b.country1, b.city1," + 
					" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'RISKAPPETITE' AND codevalue = c.riskrating) AS riskRating," + 
					" CASE" + 
					"	WHEN a.q1 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS stockholderOfCom," + 
					" CASE" + 
					"	WHEN a.q2 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS companyDirector," + 
					" CASE" + 
					"	WHEN a.q3 = 'true'" + 
					"	THEN 'As Marked'" + 
					"	ELSE ''" + 
					" END AS dosri," + 
					" a.dateupdated AS statDate, c.encodedby" + 
					" FROM "+ forms.getCifDbLink() + ".dbo.TBDOSRI a" + 
					" LEFT JOIN "+ forms.getCifDbLink() + ".dbo.TBCIFCORPORATE b ON a.cifno = b.cifno" + 
					" LEFT JOIN "+ forms.getCifDbLink() + ".dbo.TBCIFMAIN c ON a.cifno = c.cifno" + 
					" WHERE c.customertype = '2' AND c.cifstatus = '3'";

			if(forms.getStartDate() != null || forms.getEndDate() != null) {
				myQuery += "  AND CAST(c.encodeddate AS DATE)  <= CAST(:asof AS DATE)";
			}
			if(forms.getBranch() != null) {
				myQuery += " AND c.originatingbranch = :branch";
			}
			if(forms.getTellerName() != null) {
				myQuery += " AND c.encodedby = :tellerName";
			}
			if(forms.getRiskRating() != null) {
				myQuery += " AND c.riskrating = :riskRating ";
			}
			System.out.print("MAR " + myQuery);
			list = (List<CIFExceptionalReportForm>) dbService.execSQLQueryTransformer(myQuery, param,CIFExceptionalReportForm.class, 1);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CIFTransactionalReportForm> getListofClientProfileUpdate(CIFParameterForm forms) {
		List<CIFTransactionalReportForm> list = new ArrayList<CIFTransactionalReportForm>();
		try {
			param.put("startDate", forms.getStartDate() == null ? "" : forms.getStartDate());
			param.put("endDate", forms.getEndDate() == null ? "" : forms.getEndDate());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("tellerName", forms.getTellerName() == null ? "" : forms.getTellerName());
			list = (List<CIFTransactionalReportForm>) dbService.execStoredProc(
					"EXEC sp_CIF_ListofClientProfileUpdate :startDate, :endDate, :branch, :tellerName", param,
					CIFTransactionalReportForm.class, 1, null);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AllUserForm> getListofUser(String branchcode, String companycode) {
		List<AllUserForm> tellers = new ArrayList<AllUserForm>();
		param.put("branchcode", branchcode);
		param.put("companycode", companycode);
		try {
			tellers = (List<AllUserForm>) dbService.execSQLQueryTransformer(
					" select username, userid, branchcode, companycode" + 
					" from tbuser WHERE branchcode = :branchcode",
					param, AllUserForm.class, 1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return tellers;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CIFComprehensiveForm> getCIFComprehensive(CIFParameterForm forms) {
		List<CIFComprehensiveForm> list = new ArrayList<CIFComprehensiveForm>();
		try {
			param.put("asOf", forms.getAsOf() == null ? "" : forms.getAsOf());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			list = (List<CIFComprehensiveForm>) dbService.execStoredProc(
					"EXEC sp_CIF_ComprehensiveList :asOf, :branch", param,
					CIFComprehensiveForm.class, 1, null);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}

}
