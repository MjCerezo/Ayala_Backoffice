package com.etel.approval;

import java.math.BigDecimal;
import java.util.List;

import com.coopdb.data.Tbapprovalmatrix;
import com.coopdb.data.Tbloanapprovaldetails;

public interface ApprovalService {

	/**
	 * -- Generate Loan Approval Details--
	 * @author Kevin (08.25.2018)
	 * @return String = success otherwise failed
	 * */
	String generateLoanApprovalDetails(String appno, Integer evalreportid, BigDecimal loanamount,
			String transactiontype, String loanproduct, Integer approvallevel, String approver);
	/**
	 * --Get List of Loan Approval Details--
	 * @author Kevin (08.25.2018)
	 * @return form  = {@link Tbloanapprovaldetails}
	 * */
	List<Tbloanapprovaldetails> getListOfLoanApprDetails(String appno, Integer evalreportid, Boolean decisionflag);

	/**
	 * -- Save or Update Loan Approval Details--
	 * @author Kevin (08.25.2018)
	 * @return String = success otherwise failed
	 * */
	String saveOrUpdateLoanApprDetails(Tbloanapprovaldetails loanapprddetails);
	
	/**
	 * --Get Loan Approval Details by appno, evalreportid and username--
	 * @author Kevin (08.25.2018)
	 * @return form = {@link Tbloanapprovaldetails}
	 * */
	Tbloanapprovaldetails getLoanApprovalDetails(String appno, Integer evalreportid, String username);

	/**
	 * --Get Approval Level (Approval Matrix)--
	 * @author Kevin (08.25.2018)
	 * @return Integer = approval level
	 * */
	Integer getApprovalLevel(BigDecimal loanamount, String transactiontype);

	/**
	 * --Get Approval Decision Count--
	 * @author Kevin (08.25.2018)
	 * @return Integer = count per decision ('Approved','Approved with condition',Rejected)
	 * */
	Integer getDecisionCount(String appno, Integer evalreportid, String decision, int approvallevel);
	/**
	 * --Get Loan Approval Matrix (Parameter)--
	 * @author Kevin (08.25.2018)
	 * @return form = {@link Tbapprovalmatrix}
	 * */
	Tbapprovalmatrix getApprovalMatrixByTranstype(String transactiontype, String loanproduct);
	/**
	 * --Get Main CF Total Proposed Amount--
	 * @author Kevin (08.25.2018)
	 * @return BigDecimal = Sum of Proposed Credit Limit
	 * */
	BigDecimal getLAMMainCFTotalProposedAmt(String appno, Integer evalreportid);
	/**
	 * --Validate Line Approval--
	 * @author Kevin (08.25.2018)
	 * @return true otherwise false
	 * */
//	String rejectCFLineApplication(String appno, Integer evalreportid);
	Boolean validateApproval(String appno, Integer evalreportid, String decision, String transactiontype,
			String product);
	/**
	 * -- Approved Credit Facility--
	 * @author Kevin (08.25.2018)
	 * @return String = success otherwise failed
	 * */
//	String approvedCFLineApplication(String appno, Integer evalreportid);
	/**
	 * --Approval Read Status flag--
	 * @author Kevin (08.25.2018)
	 * @return String = success otherwise failed
	 * */
	String approvalReadStatus(String appno, Integer evalreportid, String username);

	
	BigDecimal getTotalLoanAmountByApp(String appno);

}
