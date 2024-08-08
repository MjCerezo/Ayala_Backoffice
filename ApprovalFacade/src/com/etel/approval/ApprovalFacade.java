package com.etel.approval;

import java.math.BigDecimal;
import java.util.List;

import com.coopdb.data.Tbapprovalmatrix;
import com.coopdb.data.Tbloanapprovaldetails;
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
public class ApprovalFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
	private ApprovalService srvc = new ApprovalServiceImpl();
    
	public ApprovalFacade() {
       //super(INFO);
    }

	public String generateLoanApprovalDetails(String appno, Integer evalreportid, BigDecimal loanamount,
			String transactiontype, String loanproduct, Integer approvallevel, String approver) {
    	return srvc.generateLoanApprovalDetails(appno, evalreportid, loanamount, transactiontype, loanproduct, approvallevel, approver);
    }
	public List<Tbloanapprovaldetails> getListOfLoanApprDetails(String appno, Integer evalreportid, Boolean decisionflag){
		return srvc.getListOfLoanApprDetails(appno, evalreportid, decisionflag);
	}
	public String saveOrUpdateLoanApprDetails(Tbloanapprovaldetails loanapprddetails){
		return srvc.saveOrUpdateLoanApprDetails(loanapprddetails);
	}
	public Tbloanapprovaldetails getLoanApprovalDetails(String appno, Integer evalreportid, String username){
		return srvc.getLoanApprovalDetails(appno, evalreportid, username);
	}
	public Integer getApprovalLevel(BigDecimal loanamount, String transactiontype){
		return srvc.getApprovalLevel(loanamount,transactiontype);
	}
	public Integer getDecisionCount(String appno, Integer evalreportid, String decision, int approvallevel) {
		return srvc.getDecisionCount(appno, evalreportid, decision, approvallevel);
	}
	public Tbapprovalmatrix getApprovalMatrixByTranstype(String transactiontype, String loanproduct){
		return srvc.getApprovalMatrixByTranstype(transactiontype, loanproduct);
	}
	public BigDecimal getLAMMainCFTotalProposedAmt(String appno, Integer evalreportid){
		return srvc.getLAMMainCFTotalProposedAmt(appno, evalreportid);
	}
	public Boolean validateApproval(String appno, Integer evalreportid, String decision, String transactiontype, String product) {
		return srvc.validateApproval(appno, evalreportid, decision, transactiontype, product);
	}
	
//	public String approvedCFLineApplication(String appno, Integer evalreportid){
//		return srvc.approvedCFLineApplication(appno, evalreportid);
//	}
	public String approvalReadStatus(String appno, Integer evalreportid, String username){
		return srvc.approvalReadStatus(appno, evalreportid, username);
	}
//	public String rejectCFLineApplication(String appno, Integer evalreportid){
//		return srvc.rejectCFLineApplication(appno, evalreportid);
//	}
	
	public BigDecimal getTotalLoanAmountByApp(String appno){
		return srvc.getTotalLoanAmountByApp(appno);
	}
}
