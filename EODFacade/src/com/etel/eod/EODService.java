package com.etel.eod;

import java.util.List;

import com.etel.lms.forms.LoanAccountForm;
import com.etel.lms.forms.LoanTransactionForm;


public interface EODService {
	String loanBooking(List<LoanAccountForm> loanTx);
	String generatePaysched(List<String> pns);
	String transactionPosting(List<LoanTransactionForm> txlist);
	void applyPayment();
	void applyCredit();
	void applyDebit();
}
