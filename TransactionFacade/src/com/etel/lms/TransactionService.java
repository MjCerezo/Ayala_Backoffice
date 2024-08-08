/**
 * 
 */
package com.etel.lms;

import java.math.BigDecimal;
import java.util.List;

import com.cifsdb.data.Tbcifmain;
import com.coopdb.data.Tbchecksforclearing;
import com.coopdb.data.Tbgrouppayment;
import com.coopdb.data.Tbloanfin;
import com.coopdb.data.Tbloans;
import com.etel.glentries.forms.GLEntriesForm;
import com.etel.lms.forms.GroupPaymentAccountForm;
import com.etel.lms.forms.PaymentTransactionForm;


/**
 * @author ETEL-COMP05
 *
 */
public interface TransactionService {

	String addEntry(Tbloanfin fin, Tbchecksforclearing check);
	
	List<Tbloanfin> findTXByAccountnoAndTXCode(String accountno, String txcode);

	List<Tbloanfin> findTXByAccountnoANDTXCodeANDPaymodeANDTxstatus(String accountno, String txcode, String paymode,
			String txstatus);

	String errorCorrect(String txrefno);
	
	List<Tbcifmain> getListofCIFs(String name);

	PaymentTransactionForm getTransaction(String txrefno);
	
	List<Tbcifmain> searchClient(String cifname);
	
	String addGroupPayment(Tbgrouppayment payment);
	
	List<Tbloans> getAccountListforGroupPayment(String cifno, String prodcode, String companycode);
	
	String addGroupAccountPayment(GroupPaymentAccountForm acct, String companycode, String prodcode, String txstat, String txmode);
	
	BigDecimal getTotalGroupPayments(String grouptxrefno);
	
	List<Tbloanfin> getAccountListPerGroup(String grouptxrefno);
	
	String checkOR(String orno);
	
	String cancelTransaction(String txrefno, String txcode, String reason);
	
	Tbgrouppayment getGroupPayment(String txrefno);
	
	List<GLEntriesForm> getGLEntries(String txrefno);
	
	String postSinglePayment(String txrefno);

	String returnTransaction(String txrefno, String txcode, String reason);
	
	String postGroupPayment(String txrefno);

	String reclass(String txrefno, String acctno);
}
