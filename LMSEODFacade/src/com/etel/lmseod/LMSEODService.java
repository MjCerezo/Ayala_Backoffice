/**
 * 
 */
package com.etel.lmseod;

import java.util.List;

import com.coopdb.data.Tbprocessingdate;
import com.etel.lms.forms.LoanAccountForm;
import com.etel.lms.forms.LoanTransactionForm;

/**
 * @author ETEL-LAPTOP19
 *
 */
public interface LMSEODService {

	String loanBooking(List<LoanAccountForm> loanTx);

	String transactionPosting(List<LoanTransactionForm> txlist);

	String checkClearing();

	void runLMSEOD();

	Tbprocessingdate getProcessingDate();

	String updateProcessEndDate(Tbprocessingdate processdate);
	
	String singletransactionPosting(LoanTransactionForm tx);

	String runLMSEOD_new();
}
