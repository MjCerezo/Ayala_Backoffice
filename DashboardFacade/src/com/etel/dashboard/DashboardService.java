package com.etel.dashboard;

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
import com.etel.dashboard.forms.MyTransactions;
import com.etel.dashboard.forms.ResignationAssignments;
import com.etel.dashboard.forms.ResignationBucket;
import com.etel.dashboard.forms.TMPFrom;
import com.etel.dashboard.forms.TransactionAssignments;
import com.etel.dashboard.forms.UpdateProfileRequestAssignment;
import com.etel.dashboard.forms.UpdateProfileRequestOtherBucket;
import com.etel.dashboard.forms.WorkflowDashboardForm;

public interface DashboardService {
	public List<MyTransactions> listMyTransactions();

	public List<TransactionAssignments> myTransactionAssignments(String stage, String searchstr, Integer page,
			Integer maxresult);

	public MyDashboard getMyDashboard();

	public Integer countMyTransactionAssignments(String str, String stage);

	public MembershipOtherBucket listNewMembershipOtheBucket(Date dtFrom, Date dtTo);

	public List<MyTransactions> listResignTransactions();

	public List<ResignationAssignments> ResignationAssignments(String stage, String search);

	public ResignationBucket countResignations();

	// Newly Added
	public List<MyTransactions> listLoanTransactions(int bucket, int stage);

	public List<LoanApplicationAssignments> getLoanApplicationAssignments(String stage, String search);

	List<WorkflowDashboardForm> getWorkflowProcessList(Integer workflowid, String viewby, String company);

	LoanAppBucket summaryLoanApplications();

	/* Daniel Fesalbon , Jan 18, 2019 */
	public List<MyTransactions> listUpdateProfileRequests();
	
	/* Daniel Fesalbon , Jan 18, 2019 */
	public UpdateProfileRequestOtherBucket listUpdateProfileOtherBucket();
	
	/* Daniel Fesalbon , Jan 18, 2019 */
	public List<UpdateProfileRequestAssignment> listUpdateProfileRequestAssignments(String stage, String search);

	public MyTransactions listLoanOtherTrans(int seqno,int total);

	public List<Tblstapp> listOtherTxLoans(int appstatus);

	List<DashBoardForm> getDashBoard();

	List<LoanForm> getLoanTransactions(String status);

	List<Tbloanfin> getFinancialTransactions(String status);

	String updateLoanStatus(List<LoanForm> listOfLoans, String status);

	String updateFinStatus(List<Tbloanfin> listOfFin, String status);
	
	//Renz
	public CIFDashboard getCIFDashboard();

	public List<Tbcifmain> getCIFDashboardList(String query);

	public List<DashboardListFormCIF> dashboardListCIF(Integer page, Integer maxresult, String fullname,
			String cifstatus, Boolean isEncoding, String branchcode, String customertype);

	public int dashboardListCIFCount(String fullname, String cifstatus,
			Boolean isEncoding, String branchcode, String customertype);

	public List<DashBoardDocumentsForm> getListofToFollowDocumentsCIF(String getDocType, String cifname);

	public int getListofToFollowDocumentsCIFCount(String getDocType, String cifname);

	public List<DashBoardDocumentsForm> getListofToFollowDocumentsLoans(String getDocType, String cifname);

	public int getListofToFollowDocumentsLoansCount(String getDocType, String cifname);
	
	public List<TMPFrom> getTMP();
	
	public List<TMPFrom> getMembershipDashboard();
	
	public List<TMPFrom> getCompanyDashboard();
	
	public List<TMPFrom> getLoanDashboard();

}
