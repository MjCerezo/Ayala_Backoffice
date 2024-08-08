package com.etel.instruction;

import java.util.List;

import com.etel.forms.ReturnForm;
import com.etel.instruction.forms.InstructionAccessRightsForm;
import com.etel.instruction.forms.InvestigationForm;
import com.etel.instruction.forms.InvestigationFormList;
import com.etel.instruction.forms.RequestValidationForm;
import com.coopdb.data.Tbappraisalreportmain;
import com.coopdb.data.Tbbireportmain;
import com.coopdb.data.Tbcireportmain;
import com.coopdb.data.Tblstapp;

public interface InstructionFormService {

	String generateInvestigation(String appno, String investntype);

	InvestigationFormList getInvestigationList(String appno,String investigationtype);

	List<Tbbireportmain> getBIHistory(String cifno);

	List<Tbcireportmain> getCIHistory(String cifno);

	Tblstapp getLstapp(String appno);

	String saveInvestigation(String appno, String cifno, String investigationtype, String instruction, String aoremarks,
			String assignedsupervisor, String supervisorremarks);

	InstructionAccessRightsForm getInstructionAccessRights(String appno, String type);

	InvestigationFormList getInvestigationListCA(String appno, String investigationtype, String collateralcategory);

	String generateInvestigationCA(String appno);

	List<Tbappraisalreportmain> getCAHistory(String cifno, String collateraltype, String referenceno);

	String saveInvestigationCA(String appno, String cifno, String investigationtype, String instruction,
			String aoremarks, String assignedsupervisor, String supervisorremarks, String colid);

	String checkInstructionSheet(String appno);

	Boolean isOpenedBySupervisor(String appno, String cifno, String investigationtype, String collateralid);

	Boolean checkIfReportIsOpened(String reportid, String status, String type);

	String startReport(String reportid, String status, String type);

	String submitReport(String reportid, String status, String type);

	String doneReviewReport(String reportid, String status, String type);

	ReturnForm validateRequest(RequestValidationForm requestform);

	List<InvestigationForm> getInvestigationListCASingle(String appno, String investigationtype,
			String collateralcategory);

	List<InvestigationForm> getInvestigationListCAGroup(String appno, String investigationtype,
			String collateralcategory);


}
