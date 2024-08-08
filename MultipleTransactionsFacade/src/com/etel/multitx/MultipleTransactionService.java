/**
 * 
 */
package com.etel.multitx;

import java.util.Date;
import java.util.List;

import com.coopdb.data.Tbmultipletransaction;
import com.etel.inquiry.forms.CIFInquiryForm;
import com.etel.lmsinquiry.forms.PaymentBreakdownForm;
import com.etel.multitx.form.MultipleTransDataForm;
import com.etel.multitx.form.MultipleTransactionAccountForm;

/**
 * @author ETEL-LAPTOP07
 *
 */
public interface MultipleTransactionService {

	List<Tbmultipletransaction> listTransactions(String multitxrefno);

	String addTransaction(Tbmultipletransaction transaction);

	String editTransaction(Tbmultipletransaction transaction);

	String postTransaction(List<Tbmultipletransaction> multipletransactions,
			List<PaymentBreakdownForm> paymentbreakdown);

	List<MultipleTransactionAccountForm> listAccounts(String cifno);

	List<CIFInquiryForm> listCIF(String cifno, String cifname);

	List<MultipleTransDataForm> getMultipleTransaction(Date startDate, Date endDate, String branch, String teller);


}
