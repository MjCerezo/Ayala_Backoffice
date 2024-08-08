/**
 * 
 */
package com.etel.deposittransaction;

import java.util.List;

import com.coopdb.data.Tbchecksforclearing;
import com.coopdb.data.Tbdeptxjrnl;
import com.coopdb.data.Tboverriderequest;
import com.coopdb.data.Tbtransactioncode;
import com.etel.deposittransaction.form.DepositTransactionForm;
import com.etel.deposittransaction.form.DepositTransactionResultForm;

/**
 * @author ETEL-LAPTOP19
 *
 */
public interface DepositTransactionService {

	Tbdeptxjrnl createDepJrnl(DepositTransactionForm form);

	DepositTransactionResultForm creditTransaction(DepositTransactionForm form, Tbtransactioncode tx);

	DepositTransactionResultForm debitTransaction(DepositTransactionForm form, Tbtransactioncode tx);

	DepositTransactionResultForm casaTransaction(DepositTransactionForm form, Tbtransactioncode tx,
			List<Tboverriderequest> requests);

	DepositTransactionResultForm errorCorrect(String acctno, String txrefno, String overridetxrefno,
			String overridestatus, String username, String password);

	DepositTransactionResultForm clearChecks(List<Tbchecksforclearing> checks, String accountno);

	List<Tbtransactioncode> getTransactionCodes(String txcode);
}
