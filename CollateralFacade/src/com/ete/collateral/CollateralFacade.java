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
import com.etel.utils.UserUtil;
import com.loansdb.data.Tbcollateralgroupmain;
import com.loansdb.data.Tbcollateralpergroup;
import com.loansdb.data.Tbloancollateralgroup;
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
public class CollateralFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public CollateralFacade() {
        //super(INFO);
     }
     
     CollateralService srvc = new CollateralServiceImpl();
     
     public String saveOrupdateCollateral(String appno, String saveOrupdate, String collateraltype, String referenceno, String oldreferenceno, CollateralTables tb){
     	return srvc.saveOrupdateCollateral(appno, saveOrupdate, collateraltype, referenceno, oldreferenceno, tb);
     }
 	public String checkCollateralMain(String referenceno){
 		return srvc.checkCollateralMain(referenceno);
 	}
 	public Tbcollateralmain geTbcollateralmain (String referenceno){
 		return srvc.geTbcollateralmain(referenceno);
 	}
 	public String saveAndUseCollateral (String appno, String collateralrefno, String collateraltype){
 		return srvc.saveAndUseCollateral(appno, collateralrefno, collateraltype);
 	}
 	public List<TbCollateralMainForm> getCollateralMainList(String appno, String collateraltype, Boolean audit){
 		return srvc.getCollateralMainList(appno, collateraltype, audit);
 	}
 	public DedupeCollateralForm dedupeTbcollateralmain (String referenceno){
 		return srvc.dedupeTbcollateralmain(referenceno);
 	}
 	public List<LoanApplicationUsageForm> listLoanApplicationUsage(String referenceno, String loanno){
 		return srvc.listLoanApplicationUsage(referenceno, loanno);
 	}
 	public CollateralTables getCollateralbyCollateralTypeAndRefNo(String collateraltype, String referenceno){
 		return srvc.getCollateralbyCollateralTypeAndRefNo(collateraltype, referenceno);
 	}
 	public String removeCollateral (String appno, String referenceno){
 		return srvc.removeCollateral(appno, referenceno);
 	}
// 	public String generateCollateralGrpID(){
// 		return srvc.generateCollateralGrpID();
// 	}
// 	public String saveOrupdateCollateralGrpMain(String saveOrupdate, Tbcollateralgroupmain grpmain){
// 		return srvc.saveOrupdateCollateralGrpMain(saveOrupdate, grpmain);
// 	}
// 	public List<Tbcollateralpergroup> listCollateralperGrp (String grpID){
// 		return srvc.listCollateralperGrp(grpID);
// 	}
// 	public List<Tbloancollateralgroup> listGrpLoanApplicationUsage(String grpID, String loanno){
// 		return srvc.listGrpLoanApplicationUsage(grpID, loanno);
// 	}
// 	public SearchCollateralGroupForm searchCollateralGrp(String grpID, String grpName){
// 		return srvc.searchCollateralGrp(grpID, grpName);
// 	}
// 	public List<TbCollateralMainFormGroup> getCollateralGrpMainList(String appno){
// 		return srvc.getCollateralGrpMainList(appno);
// 	}
// 	public DedupeCollateralFormGroup dedupeTbcollateralgroupmain (String grpID, String grpName){
// 		return srvc.dedupeTbcollateralgroupmain(grpID, grpName);
// 	}	
// 	public String useCollateralGroup(String grpID, String appno, String grpType, String grpName){
// 		return srvc.useCollateralGroup(grpID, appno, grpType, grpName);
// 	}
// 	public String addSingleCollateralToGroup(String grpID, String singleReferenceNo, String grpType){
// 		return srvc.addSingleCollateralToGroup(grpID, singleReferenceNo, grpType);
// 	}
// 	public String removeGroupCollateralToLoan (String appno, String grpID){
// 		return srvc.removeGroupCollateralToLoan(appno, grpID);
// 	}
// 	public String removeSingleCollateralToGroup (String singleReferenceNo, String grpID){
// 		return srvc.removeSingleCollateralToGroup(singleReferenceNo, grpID);
// 	}
 	public List<Tbcolappraisalrequest> getListCArequest(String appNo, String reqId, String refNo, String reqStat, String colType, String appType, Integer page,
 			Integer maxResult, Boolean viewflag,String colCat, String assgndUser, Boolean isViewRequest){
 		return srvc.getListCArequest(appNo,reqId,refNo,reqStat,colType,appType,page,maxResult,viewflag,colCat,assgndUser, isViewRequest);
 	}
 	public int getCArequestTotal(String appNo, String reqId, String refNo, String reqStat, String colType, String appType,
 			String colCat, String assgndUser,Boolean viewflag, Boolean isViewRequest){
 		return srvc.getCArequestTotal(appNo,reqId,refNo,reqStat,colType,appType,colCat,assgndUser,viewflag,isViewRequest);
 	}
 	public String addorUpdateCARequest(Tbcolappraisalrequest request, String status){
 		return srvc.getaddorUpdateCARequest(request,status);
 	}
 	public Tbcolappraisalrequest getDataByRequestId(String colappid){
 		return srvc.getDataByRequestId(colappid);
 	}
// 	public Tbcollateralgroupmain getGroupId(String groupid){
// 		return srvc.getGroupId(groupid);
// 	}
 	public CAAccessRightsForm getCAAccessRights(String requestid, String dlgType, Boolean viewflag){
 		return srvc.getCAAccessRights(requestid,dlgType,viewflag);
 	}
 	public List<Tbappraisalreportmain> getCAReportByRequestId(String requestid){
 		return srvc.getCAReportByRequestId(requestid);
 	}
 	public ReturnForm createCAReport(String carequestid, String careportid){
 		return srvc.createCAReport(carequestid,careportid);
 	}
 	public Tbappraisalreportmain getCaReportMain(String appreportid){
 		return srvc.getCaReportMain(appreportid);
 	}
 	public Tbappraisalrel getRealEstatebyReportId(String appreportid){
 		return srvc.getRealEstatebyReportId(appreportid);
 	}
 	public Tbappraisalauto getAppAutoByRepId(String appreportid){
 		return srvc.getAppAutoByRepId(appreportid);
 	}
 	public String addOrupdateAutoAppraisal(Tbappraisalauto auto){
 		return srvc.addOrupdateAutoAppraisal(auto);
 	}
 	public String addOrupdateRealEstate(Tbappraisalrel real){
 		return srvc.addOrupdateRealEstate(real);
 	}
 	public String addOrupdateMachineries(Tbappraisalmachine machine, String type){
 		return srvc.addOrupdateMachineries(machine, type);
 	}
// 	public String addOrupdateAppDeposit(Tbappraisaldeposits dep){
// 		return srvc.addOrupdateAppDeposit(dep);
// 	}
// 	public String addOrupdateAppSecurities(Tbappraisalsecurities sec){
// 		return srvc.addOrupdateAppSecurities(sec);
// 	}
 	public CollateralTables getCollateralDetails(String colrefno,String coltype){
 		return srvc.getCollateralDetails(colrefno,coltype);
 	}
 	public String appraisalChangeStatus (String status, String requestid, String reasonforreturn){
 		return srvc.appraisalChangeStatus(status,requestid,reasonforreturn);
 	}
 	public String saveOrUpdateAppraisalreportmain (String appraisalreportid, Date appraisalDate, Boolean startReportFlag){
 		return srvc.saveOrUpdateAppraisalreportmain(appraisalreportid,appraisalDate, startReportFlag);
 	}
// 	public Tbappraisalsecurities getAppSecuritiesByRepId(String appreportid){
// 		return srvc.getAppSecuritiesByRepId(appreportid);
// 	}	
// 	public Tbappraisaldeposits getAppDepositsByRepId(String appreportid){
// 		return srvc.getAppDepositsByRepId(appreportid);
// 	}	
 	public Tbappraisalmachine getAppMachineriesByRepId(String appreportid){
 		return srvc.getAppMachineriesByRepId(appreportid);
 	}		
 	public String updateColInstStatus (Integer id){
 		return srvc.updateColInstStatus(id);
 	}
 	public String generateAppraisalTableAuto (String refno, String apprisaltype, String appraisalreportid, String appno){
 		return srvc.generateAppraisalTableAuto(refno, apprisaltype, appraisalreportid, appno);
 	}
 	public String generateAppraisalTableRel(String refno, String apprisaltype, String appraisalreportid, String appno){
 		return srvc.generateAppraisalTableRel(refno, apprisaltype, appraisalreportid, appno);
 	}
     /*public String generateCollateralNoPerType(){
    	   String no = ApplicationNoGenerator.generateCollateralNoPerType("COLLATERALPERTYPE");
    	   return no;
      }*/	
     public String saveAndSubmitRequest (Boolean flag, String colappraisalrequestid){
     	return srvc.saveAndSubmitRequest(flag, colappraisalrequestid);
     }
     public String checkIfExisting(String refno){
     	return srvc.checkIfExisting(refno);
     }
//     public String addSingleCollateralToGrp(Tbcollateralpergroup data){
//     	return srvc.addSingleCollateralToGrp(data);
//     }
     
     public Tbcollateralauto getAutoDetailsByReference(String reference){
    	 return srvc.getAutoDetailsByReference(reference);
     }
     
     public ReturnForm updateReportStatus(String reportid, String status){
    	 return srvc.updateReportStatus(reportid, status);
     }
     
     public ReturnForm saveOrUpdateAppraisal(AppraisalForm appraisals, String collateraltype){
    	 return srvc.saveOrUpdateAppraisal(appraisals, collateraltype);
     }
     
     public AppraisalForm getAppraisalDetailsViaID(String reportid, String requestid){
    	 return srvc.getAppraisalDetailsViaID(reportid, requestid);
     }
     
     public AppraisalAccessRights AppraisalButtonAccess(String reportid){
    	 return srvc.AppraisalButtonAccess(reportid);
     }
     public Tbappraisaldeposits getAppDepositsByRepId(String appreportid) {
    	 return srvc.getAppDepositsByRepId(appreportid);
     }
     public String addOrupdateAppDeposit(Tbappraisaldeposits dep) {
    	 return srvc.addOrupdateAppDeposit(dep);
     }
     
     public String saveOrUpdateApprMainMarketValueDetails(Tbappraisalreportmain values){
    	 return srvc.saveOrUpdateApprMainMarketValueDetails(values);
     }
     
     public String getUserFullName(){
    	 return UserUtil.getUserFullname(UserUtil.securityService.getUserName());
     }
 	public List<CollateralLoanableForm> listLoanCollateral(String appno, String acctno){
 		return srvc.listLoanCollateral(appno, acctno);
 	}
 	
 	//MAR 11-03-2020
 	
 	public String addSingleCollateralToGrp(Tbcollateralpergroup data){
    	return srvc.addSingleCollateralToGrp(data);
    }
    public String useCollateralGroup(String grpID, String appno, String grpType, String grpName){
		return srvc.useCollateralGroup(grpID, appno, grpType, grpName);
	}
    public String removeGroupCollateralToLoan (String appno, String grpID){
		return srvc.removeGroupCollateralToLoan(appno, grpID);
	}
    public String saveOrupdateCollateralGrpMain(String saveOrupdate, Tbcollateralgroupmain grpmain){
		return srvc.saveOrupdateCollateralGrpMain(saveOrupdate, grpmain);
	}
    public List<Tbloancollateralgroup> listGrpLoanApplicationUsage(String grpID, String loanno){
		return srvc.listGrpLoanApplicationUsage(grpID, loanno);
	}

	public List<Tbcollateralpergroup> listCollateralperGrp (String grpID){
		return srvc.listCollateralperGrp(grpID);
	}
	public List<TbCollateralMainFormGroup> getCollateralGrpMainList(String appno){
		return srvc.getCollateralGrpMainList(appno);
	}
	public DedupeCollateralFormGroup dedupeTbcollateralgroupmain (String grpID, String grpName){
		return srvc.dedupeTbcollateralgroupmain(grpID, grpName);
	}
	public String generateCollateralGrpID(){
		return srvc.generateCollateralGrpID();
	}
}
