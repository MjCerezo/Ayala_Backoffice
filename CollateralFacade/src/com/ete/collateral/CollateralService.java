package com.ete.collateral;

import java.util.Date;
import java.util.List;

import com.coopdb.data.Tbappraisalauto;
import com.coopdb.data.Tbappraisaldeposits;
import com.coopdb.data.Tbappraisalmachine;
import com.coopdb.data.Tbappraisalrel;
import com.coopdb.data.Tbappraisalreportmain;
import com.coopdb.data.Tbcolappraisalrequest;
import com.coopdb.data.Tbcollateralauto;
import com.coopdb.data.Tbcollateralmain;
import com.etel.collateralforms.AppraisalAccessRights;
import com.etel.collateralforms.AppraisalForm;
import com.etel.collateralforms.CAAccessRightsForm;
import com.etel.collateralforms.CollateralTables;
import com.etel.collateralforms.DedupeCollateralForm;
import com.etel.collateralforms.DedupeCollateralFormGroup;
import com.etel.collateralforms.LoanApplicationUsageForm;
import com.etel.collateralforms.TbCollateralMainForm;
import com.etel.collateralforms.TbCollateralMainFormGroup;
import com.etel.financial.form.CollateralLoanableForm;
import com.etel.forms.ReturnForm;
import com.loansdb.data.Tbcollateralgroupmain;
import com.loansdb.data.Tbcollateralpergroup;
import com.loansdb.data.Tbloancollateralgroup;

public interface CollateralService {

	String saveOrupdateCollateral(String appno, String saveOrupdate, String collateraltype, String referenceno, String oldreferenceno, CollateralTables tb);

	String checkCollateralMain(String referenceno);

	Tbcollateralmain geTbcollateralmain(String referenceno);

	String saveAndUseCollateral(String appno, String collateralrefno, String collateraltype);

	List<TbCollateralMainForm> getCollateralMainList(String appno, String collateraltype, Boolean audit);

	DedupeCollateralForm dedupeTbcollateralmain(String referenceno);

	List<LoanApplicationUsageForm> listLoanApplicationUsage(String referenceno, String loanno);

	CollateralTables getCollateralbyCollateralTypeAndRefNo(String collateraltype, String referenceno);

	String removeCollateral(String appno, String referenceno);

//	String generateCollateralGrpID();

//	String saveOrupdateCollateralGrpMain(String saveOrupdate, Tbcollateralgroupmain grpmain);

//	List<Tbcollateralpergroup> listCollateralperGrp(String grpID);

//	List<Tbloancollateralgroup> listGrpLoanApplicationUsage(String grpID, String loanno);

//	SearchCollateralGroupForm searchCollateralGrp(String grpID, String grpName);

//	List<TbCollateralMainFormGroup> getCollateralGrpMainList(String appno);

//	DedupeCollateralFormGroup dedupeTbcollateralgroupmain(String grpID, String grpName);

//	String useCollateralGroup(String grpID, String appno, String grpType, String grpName);

//	String addSingleCollateralToGroup(String grpID, String singleReferenceNo, String grpType);

//	String removeGroupCollateralToLoan(String appno, String grpID);

//	String removeSingleCollateralToGroup(String singleReferenceNo, String grpID);

	List<Tbcolappraisalrequest> getListCArequest(String appNo, String reqId, String refNo, String reqStat,
			String colType, String appType, Integer page, Integer maxResult, Boolean viewflag,String colCat,String assgndUser, Boolean isViewRequest);

	int getCArequestTotal(String appNo, String reqId, String refNo, String reqStat, String colType, String appType,String colCat, String assgndUser, 
			Boolean viewflag, Boolean isViewRequest);

	String getaddorUpdateCARequest(Tbcolappraisalrequest request, String status);

	Tbcolappraisalrequest getDataByRequestId(String colappid);

//	Tbcollateralgroupmain getGroupId(String groupid);

	CAAccessRightsForm getCAAccessRights(String requestid, String dlgType, Boolean viewflag);

	List<Tbappraisalreportmain> getCAReportByRequestId(String requestid);

	ReturnForm createCAReport(String carequestid, String careportid);

	Tbappraisalreportmain getCaReportMain(String appreportid);

	Tbappraisalrel getRealEstatebyReportId(String appreportid);

	Tbappraisalauto getAppAutoByRepId(String appreportid);

	String addOrupdateAutoAppraisal(Tbappraisalauto auto);

	String addOrupdateRealEstate(Tbappraisalrel real);

	String addOrupdateMachineries(Tbappraisalmachine machine, String type);

//	String addOrupdateAppDeposit(Tbappraisaldeposits dep);

//	String addOrupdateAppSecurities(Tbappraisalsecurities sec);

	CollateralTables getCollateralDetails(String colrefno,String coltype);

	String appraisalChangeStatus(String status, String requestid, String reasonforreturn);

	String saveOrUpdateAppraisalreportmain(String appraisalreportid, Date appraisalDate, Boolean startReportFlag);

//	Tbappraisalsecurities getAppSecuritiesByRepId(String appreportid);

//	Tbappraisaldeposits getAppDepositsByRepId(String appreportid);

	Tbappraisalmachine getAppMachineriesByRepId(String appreportid);

	String updateColInstStatus(Integer id);

	String generateAppraisalTableAuto(String refno, String apprisaltype, String appraisalreportid, String appno);

	String generateAppraisalTableRel(String refno, String apprisaltype, String appraisalreportid, String appno);

	String saveAndSubmitRequest(Boolean flag, String colappraisalrequestid);


	Tbcollateralauto getAutoDetailsByReference(String reference);

	ReturnForm updateReportStatus(String reportid, String status);

	ReturnForm saveOrUpdateAppraisal(AppraisalForm appraisals, String collateraltype);

	AppraisalForm getAppraisalDetailsViaID(String reportid, String requestid);

	AppraisalAccessRights AppraisalButtonAccess(String reportid);

	Tbappraisaldeposits getAppDepositsByRepId(String appreportid);

	String addOrupdateAppDeposit(Tbappraisaldeposits dep);

	String saveOrUpdateApprMainMarketValueDetails(Tbappraisalreportmain values);

	List<CollateralLoanableForm> listLoanCollateral(String appno, String acctno);
	
//	String addSingleCollateralToGrp(Tbcollateralpergroup data);
	
	//MAR 11-03-2020
	String checkIfExisting(String refno);
	DedupeCollateralFormGroup dedupeTbcollateralgroupmain(String grpID, String grpName);
	List<TbCollateralMainFormGroup> getCollateralGrpMainList(String appno);
	List<Tbcollateralpergroup> listCollateralperGrp(String grpID);
	List<Tbloancollateralgroup> listGrpLoanApplicationUsage(String grpID, String loanno);
	String saveOrupdateCollateralGrpMain(String saveOrupdate, Tbcollateralgroupmain grpmain);
	String removeGroupCollateralToLoan(String appno, String grpID);
	String useCollateralGroup(String grpID, String appno, String grpType, String grpName);
	String addSingleCollateralToGrp(Tbcollateralpergroup data);
	String generateCollateralGrpID();
}
