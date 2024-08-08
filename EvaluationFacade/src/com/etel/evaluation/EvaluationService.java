package com.etel.evaluation;

import java.util.List;

import com.coopdb.data.Tbcibankcheck;
import com.coopdb.data.Tbcitradecheck;
import com.coopdb.data.Tbevaldeposit;
import com.coopdb.data.Tbevalloans;
import com.coopdb.data.Tbevalmonthlyexpense;
import com.coopdb.data.Tbevalmonthlyincome;
import com.coopdb.data.Tbevalreport;
import com.etel.evaluation.forms.CoMakerForm;
import com.etel.evaluation.forms.EvalAccessRightsForm;
import com.etel.evaluation.forms.IncomeValidationForm;
import com.etel.evaluation.forms.MonthlyIncomeExpenseForm;
import com.loansdb.data.Tbincomeexpense;

public interface EvaluationService {

	String createEvalReport(String appno);
	
	List<Tbevalreport> getEvalReportList(String appno);

	Tbevalreport getEvalReportByReportId(Integer evalreportid);

	String saveOrUpdateEvalReport(Tbevalreport evalreport);

	String saveMonthlyIncome(Tbevalmonthlyincome income);

	String saveMonthlyExpense(Tbevalmonthlyexpense expense);

	String deleteMonthlyIncome(Integer id);

	String deleteMonthlyExpense(Integer id);

	MonthlyIncomeExpenseForm getMonthlyIncomeExpense(String appno, Integer evalreportid);

	EvalAccessRightsForm getEvalAccessRights(String appno, Integer evalreportid);

	String updateEvalReportStatus(String appno, Integer evalreportid, String status);

	List<CoMakerForm> getComakerList(String appno);

	List<Tbcibankcheck> getDepositAccountsList(String appno, String cifno);

	List<Tbcitradecheck> getTradeCheckList(String appno, String cifno);

	String updateAssignedEvaluator(String appno, Integer evalreportid, String assignedevaluator);

	Integer getLatestEvalReportIdByAppno(String appno);
	
	
	// Added by Wel
	String refreshFromCASA(String evalreportid);	
	
	String refreshFromLMS(String evalreportid);	
	
	String generateEvalOtherBanks(String appno, Integer evalID);

	String generateEvalCreditCheck(String appno, Integer evalID);
	
//	String generateEvalTradeCheck(String appno, Integer evalID);	

	List<Tbevaldeposit> listEvalOtherBanks(String evalreportid, String type);

	List<Tbevalloans> listEvalCreditCheck(String evalreportid, String type);

	boolean showRefreshCASAButton(String evalreportid);

//	List<Tbevaltradecheck> listEvalTradeCheck(String appno);
	//MAR 01-08-2021
	IncomeValidationForm computeIncomeForm(List<Tbincomeexpense> incomelist, String appno, List<Tbincomeexpense> expenselist, String cifno);
	boolean isAssignedBICIAPP(String username, String appno);
	String addIncomeExpenseRecord(Tbincomeexpense record);

}
