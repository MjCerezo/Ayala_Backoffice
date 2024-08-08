package com.etel.evaluation;

import java.util.ArrayList;
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
public class EvaluationFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
	private EvaluationService srvc = new EvaluationServiceImpl();
   
	public EvaluationFacade() {
       //super(INFO);
    }
	public String createEvalReport(String appno){
		return srvc.createEvalReport(appno);
	}
	
	public List<Tbevalreport> getEvalReportList(String appno){
		return srvc.getEvalReportList(appno);
	}
	
	public Tbevalreport getEvalReportByReportId(Integer evalreportid){
		return srvc.getEvalReportByReportId(evalreportid);
	}
	
	public String saveOrUpdateEvalReport(Tbevalreport evalreport){
		return srvc.saveOrUpdateEvalReport(evalreport);
	}
	
	public String saveMonthlyIncome(Tbevalmonthlyincome income){
		return srvc.saveMonthlyIncome(income);
	}
	
	public String saveMonthlyExpense(Tbevalmonthlyexpense expense){
		return srvc.saveMonthlyExpense(expense);
	}
	
	public String deleteMonthlyIncome(Integer id){
		return srvc.deleteMonthlyIncome(id);
	}
	
	public String deleteMonthlyExpense(Integer id){
		return srvc.deleteMonthlyExpense(id);
	}
	public MonthlyIncomeExpenseForm getMonthlyIncomeExpense(String appno, Integer evalreportid){
		return srvc.getMonthlyIncomeExpense(appno, evalreportid);
	}
	public EvalAccessRightsForm getEvalAccessRights(String appno, Integer evalreportid){
		return srvc.getEvalAccessRights(appno, evalreportid);
	}
	public String updateEvalReportStatus(String appno, Integer evalreportid, String status){
		return srvc.updateEvalReportStatus(appno, evalreportid, status);
	}
	
	public List<CoMakerForm> getComakerList(String appno){
		List<CoMakerForm> form = new ArrayList<CoMakerForm>();
		form = srvc.getComakerList(appno);
		return form;
	}
	public List<Tbcibankcheck> getDepositAccountsList(String appno, String cifno) {
		return srvc.getDepositAccountsList(appno, cifno);
	}
	public List<Tbcitradecheck> getTradeCheckList(String appno, String cifno) {
		return srvc.getTradeCheckList(appno, cifno);
	}
	public String updateAssignedEvaluator(String appno, Integer evalreportid, String assignedevaluator){
		return srvc.updateAssignedEvaluator(appno, evalreportid, assignedevaluator);
	}
	public Integer getLatestEvalReportIdByAppno(String appno){
		return srvc.getLatestEvalReportIdByAppno(appno);
	}
	
	public String generateEvalOtherBanks(String appno, Integer evalID){
		return srvc.generateEvalOtherBanks(appno, evalID);
	}
	public List<Tbevaldeposit> listEvalOtherBanks(String evalreportid, String type){
		return srvc.listEvalOtherBanks(evalreportid, type);
	}	
	public String generateEvalCreditCheck(String evalreportid, Integer evalID){
		return srvc.generateEvalCreditCheck(evalreportid, evalID);
	}
	public List<Tbevalloans> listEvalCreditCheck(String evalreportid, String type){
		return srvc.listEvalCreditCheck(evalreportid, type);
	}		
//	public String generateEvalTradeCheck(String appno, Integer evalID){
//		return srvc.generateEvalTradeCheck(appno, evalID);
//	}	
//	public List<Tbevaltradecheck> listEvalTradeCheck(String appno){
//		return srvc.listEvalTradeCheck(appno);
//	}			
	public String refreshFromCASA(String evalreportid){
		return srvc.refreshFromCASA(evalreportid);
	}
	public String refreshFromLMS(String evalreportid){
		return srvc.refreshFromLMS(evalreportid);
	}	
	
	public boolean showRefreshCASAButton(String evalreportid){
		return srvc.showRefreshCASAButton(evalreportid);
	}
	//mar 01-08-2021
	public String addIncomeExpenseRecord(Tbincomeexpense record) {
		return srvc.addIncomeExpenseRecord(record);
	}
	public boolean isAssignedBICIAPP(String username, String appno) {
		return srvc.isAssignedBICIAPP(username, appno);
	}
	public IncomeValidationForm computeIncomeForm(List<Tbincomeexpense> incomelist, String appno, List<Tbincomeexpense> expenselist, String cifno) {
		return srvc.computeIncomeForm(incomelist, appno, expenselist, cifno);
	}
}
