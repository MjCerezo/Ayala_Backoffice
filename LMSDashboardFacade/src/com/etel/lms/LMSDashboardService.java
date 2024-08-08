package com.etel.lms;

import java.util.List;

import com.etel.lms.forms.LMSDashboardForm;

import com.etel.lms.forms.LoanAccountForm;
import com.etel.lms.forms.LoanTransactionForm;

public interface LMSDashboardService {
	
	List<LMSDashboardForm> getDashBoard(String filter, String month, String year);
	List<LoanAccountForm> getLoanReleases(String txstat);
	List<LoanTransactionForm> getLoanTransactionbyStatus(String txstat);
	List<LoanTransactionForm> getLoanTransactionbyStatusAndTxcode(String txstat, String txcode);
}
