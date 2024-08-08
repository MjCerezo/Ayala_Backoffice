package com.etel.dashboard;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cifsdb.data.Tbcifmain;
import com.coopdb.data.Tbloanfin;
import com.coopdb.data.Tblstapp;
import com.etel.dashboard.forms.CIFDashboard;
import com.etel.dashboard.forms.DashBoardDocumentsForm;
import com.etel.dashboard.forms.DashBoardForm;
import com.etel.dashboard.forms.DashboardListFormCIF;
import com.etel.dashboard.forms.LoanAppBucket;
import com.etel.dashboard.forms.LoanApplicationAssignments;
import com.etel.dashboard.forms.LoanForm;
import com.etel.dashboard.forms.MembershipOtherBucket;
import com.etel.dashboard.forms.MyDashboard;
import com.etel.dashboard.forms.MyDeposits;
import com.etel.dashboard.forms.MyGeneralLedger;
import com.etel.dashboard.forms.MyLoans;
import com.etel.dashboard.forms.MyMembership;
import com.etel.dashboard.forms.MyTransactions;
import com.etel.dashboard.forms.ResignationAssignments;
import com.etel.dashboard.forms.ResignationBucket;
import com.etel.dashboard.forms.TMPFrom;
import com.etel.dashboard.forms.TransactionAssignments;
import com.etel.dashboard.forms.UpdateProfileRequestAssignment;
import com.etel.dashboard.forms.UpdateProfileRequestOtherBucket;
import com.etel.dashboard.forms.WorkflowDashboardForm;
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
public class DashboardFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
	public MyLoans lo;
	public MyDeposits d;
	public MyGeneralLedger gl;
	public MyMembership mem;
	
	
	DashboardService service = new DashboardServiceImpl();
	public DashboardFacade() {
//       super(INFO);
    }
	public List<MyTransactions> listMyTransactions(){
		return service.listMyTransactions();
	}
	public List<TransactionAssignments> myTransactionAssignments(String stage, String searchstr, Integer page, Integer maxresult) {
		return service.myTransactionAssignments(stage, searchstr, page, maxresult);
	}
	
	public MyDashboard getmyDashboard() {
		return service.getMyDashboard();
	}
	
	public Integer countMyTransactionAssignments(String str, String stage) {
		return service.countMyTransactionAssignments(str, stage);
	}
	
	public MyMembership testmyMembership(){
		return new MyMembership();
	}
	
	public MyLoans testLoan() {
		return new MyLoans();
	}

	public MembershipOtherBucket listNewMembershipOtheBucket(Date dtFrom, Date dtTo) {
		return service.listNewMembershipOtheBucket(dtFrom, dtTo);
	}
	
	//DANIEL FESALBON
	public ResignationBucket countResignations(){
		return service.countResignations();
	}
	//DANIEL FESALBON
	public List<MyTransactions> listResignTransactions(){
		return service.listResignTransactions();
	}
	//DANIEL FESALBON
	public List<ResignationAssignments> ResignationAssignments(String stage, String search){
		return service.ResignationAssignments(stage, search);
	}
	//Newly Added
	public List<MyTransactions> listLoanTransactions(int bucket, int stage){
		return service.listLoanTransactions(bucket, stage);
	}
	public List<LoanApplicationAssignments> getLoanApplicationAssignments(String stage, String search){
		return service.getLoanApplicationAssignments(stage,search);
	}
	public List<WorkflowDashboardForm> getWorkflowProcessList(Integer workflowid, String viewby, String company) {
		return service.getWorkflowProcessList(workflowid, viewby, company);
	}
	public LoanAppBucket summaryLoanApplications() {// CED
		return service.summaryLoanApplications();
	}
	/*Daniel Fesalbon, Jan 18, 2019*/
	public List<MyTransactions> listUpdateProfileRequests(){
		return service.listUpdateProfileRequests();
	}
	/*Daniel Fesalbon, Jan 18, 2019*/
	public UpdateProfileRequestOtherBucket listUpdateProfileOtherBucket(){
		return service.listUpdateProfileOtherBucket();
	}
	
	public List<UpdateProfileRequestAssignment> listUpdateProfileRequestAssignments(String stage, String search){
		return service.listUpdateProfileRequestAssignments(stage, search);
	}
	public MyTransactions listLoanOtherTrans(int seqno,int total){
		return service.listLoanOtherTrans(seqno,total);
	}
	public List<Tblstapp> listOtherTxLoans(int appstatus){
		return service.listOtherTxLoans(appstatus);
	}
	
    public List<DashBoardForm> getDashBoard() {
        List<DashBoardForm> form = new ArrayList<DashBoardForm>();
        DashboardService dashService = new DashboardServiceImpl();
        try {
           log(INFO, "Starting getDashBoard()");
           form = dashService.getDashBoard();
        } catch(Exception e) {
           log(ERROR, "The getDashBoard service operation has failed", e);
        }
        return form;
     }
     
     public List<LoanForm> getLoanTransactions(String status) {
         List<LoanForm> listOfLoans = new ArrayList<LoanForm>();
         DashboardService dashService = new DashboardServiceImpl();
         try {
            log(INFO, "Starting getLoanTransactions");
            listOfLoans = dashService.getLoanTransactions(status);
         } catch(Exception e) {
            log(ERROR, "The getLoanTransactions service operation has failed", e);
         }
         return listOfLoans;
      }    

     public List<Tbloanfin> getFinancialTransactions(String status) {
         List<Tbloanfin> listOffin = new ArrayList<Tbloanfin>();
         DashboardService dashService = new DashboardServiceImpl();
         try {
            log(INFO, "Starting getFinancialTransactions");
            listOffin = dashService.getFinancialTransactions(status);
         } catch(Exception e) {
            log(ERROR, "The getFinancialTransactions service operation has failed", e);
         }
         return listOffin;
      }
     
     public String updateLoanStatus(List<LoanForm> listOfLoans, String status) {
         DashboardService dashService = new DashboardServiceImpl();
         String result = "";
         try {
            log(INFO, "Starting updateLoansStatus");
            result = dashService.updateLoanStatus(listOfLoans, status);
         } catch(Exception e) {
            log(ERROR, "The updateLoansStatus service operation has failed", e);
         }
         return result ;
      }  
     
     public String updateFinStatus(List<Tbloanfin> listOfFin, String status) {
         DashboardService dashService = new DashboardServiceImpl();
         String result = "";
         try {
            log(INFO, "Starting updateFinStatus");
            result = dashService.updateFinStatus(listOfFin, status);
         } catch(Exception e) {
            log(ERROR, "The updateFinStatus service operation has failed", e);
         }
         return result ;
      }  
     
     //Renz CIF DASHBOARD
     public CIFDashboard getCIFDashboard() {
    	return service.getCIFDashboard();
     }
     
 	public List<Tbcifmain> getCIFDashboardList(String query){
		return service.getCIFDashboardList(query);
	}
 	
 	public List<DashboardListFormCIF> dashboardListCIF
 	(Integer page, Integer maxresult, String fullname, String cifstatus, Boolean isEncoding, String branchcode, String customertype){
 		return service.dashboardListCIF(page, maxresult, fullname, cifstatus, isEncoding, branchcode, customertype);
 	}
 	public int dashboardListCIFCount
 	(String fullname, String cifstatus, Boolean isEncoding, String branchcode, String customertype){
 		return service.dashboardListCIFCount(fullname, cifstatus, isEncoding, branchcode, customertype);
 	}
 	
 	//Mar 02-08-2023
 	public List<DashBoardDocumentsForm> getListofToFollowDocumentsCIF(String getDocType, String cifname) {
		return service.getListofToFollowDocumentsCIF(getDocType, cifname);
	}
 	public int getListofToFollowDocumentsCIFCount(String getDocType, String cifname) {
		return service.getListofToFollowDocumentsCIFCount(getDocType, cifname);
	}
 	public List<DashBoardDocumentsForm> getListofToFollowDocumentsLoans(String getDocType, String cifname) {
		return service.getListofToFollowDocumentsLoans(getDocType, cifname);
	}
 	public int getListofToFollowDocumentsLoansCount(String getDocType, String cifname) {
		return service.getListofToFollowDocumentsLoansCount(getDocType, cifname);
	}
 	
	public List<TMPFrom> getTMP() {
		return service.getTMP();
	}
	
	public List<TMPFrom> getMembershipDashboard() {
		return service.getMembershipDashboard();
	}
	
	public List<TMPFrom> getCompanyDashboard() {
		return service.getCompanyDashboard();
	}
	
	public List<TMPFrom> getLoanDashboard() {
		return service.getLoanDashboard();
	}
 	
}
