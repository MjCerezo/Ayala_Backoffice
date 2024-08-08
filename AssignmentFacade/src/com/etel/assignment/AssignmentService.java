package com.etel.assignment;

import java.util.List;

import com.coopdb.data.Tbappraisalreportmain;
import com.coopdb.data.Tbmember;
import com.coopdb.data.Tbuser;
import com.etel.assignment.forms.BIReportAssignmentForm;
import com.etel.assignment.forms.CIFAssignmentForm;
import com.etel.assignment.forms.CIReportAssignmentForm;
import com.etel.assignment.forms.UnassignedAppReview;
import com.etel.forms.TblstappForm;
import com.isls.document.forms.DocFields;

public interface AssignmentService {

	List<UnassignedAppReview> listUnassignedAppReview(Integer page, Integer maxresult);

	Integer listUnassignedAppReviewTotal();

	String grabUnassignedApp(String memappid);
	
	String batchApproval(String[] forapproval, String approvalstage, String approvalstatus, String remarks, String boardbatchremarks, String boardbatchresno);

	List<TblstappForm> getMyAssignmentAppList(String search, Integer page, Integer maxResult);

	Integer getMyAssignmentAppTotal(String search);

	List<BIReportAssignmentForm> getMyAssignmentBiReportList(String search, Integer page, Integer maxResult);

	Integer getMyAssignmentBiReportTotal(String search);

	List<CIReportAssignmentForm> getMyAssignmentCiReportList(String search, Integer page, Integer maxResult);

	Integer getMyAssignmentCiReportTotal(String search);

	List<Tbappraisalreportmain> getMyAssignmentCaReportList(String search, Integer page, Integer maxResult);

	Integer getMyAssignmentCaReportTotal(String search);

	List<Tbmember> searchMember(String search);

	List<Tbmember> getAllRecords();

	String updateAO(String membershipid,String aocode);

	CIFAssignmentForm listCIFAssignmentPerBranch(String search, Integer page, Integer maxResult);

	String assignCIFEncoder(String cifno, String cifencoder);

	List<Tbuser> getCIFEncoder();
}
