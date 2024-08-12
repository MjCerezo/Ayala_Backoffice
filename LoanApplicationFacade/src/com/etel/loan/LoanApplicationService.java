package com.etel.loan;

import java.util.List;

import com.coopdb.data.Tbloanevaluationtable;
import com.etel.loanform.LoanAppInquiryForApprovalForm;
import com.etel.loanform.LoanAppInquiryForReleaseForm;
import com.etel.loanform.LoanEvaluationResultForm;
import com.etel.loanform.LoanRuleForm;
import com.etel.loanform.MemberLoanEvaluationForm;

public interface LoanApplicationService {

	List<LoanAppInquiryForApprovalForm> searchLoanApplicationForApproval(String tat, String emp);

	List<LoanAppInquiryForReleaseForm> searchLoanApplicationForRelease(String tat, String emp);

	List<LoanAppInquiryForReleaseForm> searchLoanApplicationForBooking(String tat, String emp);

	List<LoanAppInquiryForApprovalForm> searchLoanApplication(String tat, String emp, Integer status);

	List<Tbloanevaluationtable> listEvaluationTable(String template);

	LoanRuleForm getLoanRule(String appno);

	MemberLoanEvaluationForm getMemberEvalForm(String appno, String cifno);

	LoanEvaluationResultForm getLoanEvaluationResult(String appno, String cifno, LoanRuleForm ruleForm,
			MemberLoanEvaluationForm memberForm);

	String saveOrUpdateEvaluationTable(Tbloanevaluationtable d);

	String saveOrUpdateLoanEvaluationResult(String appno, String cifno);

}
