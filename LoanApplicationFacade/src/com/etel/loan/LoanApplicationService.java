package com.etel.loan;

import java.util.List;

import com.etel.loanform.LoanAppInquiryForApprovalForm;
import com.etel.loanform.LoanAppInquiryForReleaseForm;

public interface LoanApplicationService {

	List<LoanAppInquiryForApprovalForm> searchLoanApplicationForApproval(String tat, String emp);

	List<LoanAppInquiryForReleaseForm> searchLoanApplicationForRelease(String tat, String emp);

	List<LoanAppInquiryForReleaseForm> searchLoanApplicationForBooking(String tat, String emp);

	List<LoanAppInquiryForApprovalForm> searchLoanApplication(String tat, String emp, Integer status);

}
