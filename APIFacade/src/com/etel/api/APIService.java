package com.etel.api;

import com.etel.api.forms.AddJournalForm;
import com.etel.api.forms.AddReceiptForm;
import com.etel.forms.ReturnForm;

public interface APIService {
	
	/**
	 * --Add Loan Payment to GL (API)--
	 * @author Kevin 03.26.2019
	 * @return List<{@link ReturnForm}>
	 * */
	ReturnForm addLoanDisbursement(String appno);
	
	/**
	 * --Add Receipt to GL(API)--
	 * @author Kevin 07.17.2019
	 * @return List<{@link ReturnForm}>
	 * */
	ReturnForm addReceipt(String appno, AddReceiptForm receipt);
	
	/**
	 * --Add Journal Entry to GL(API)--
	 * @author Kevin 07.17.2019
	 * @return List<{@link ReturnForm}>
	 * */
	ReturnForm addJournal(String appno, AddJournalForm journal);

}
