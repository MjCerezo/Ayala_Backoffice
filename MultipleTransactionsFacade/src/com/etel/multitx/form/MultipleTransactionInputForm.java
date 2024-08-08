/**
 * 
 */
package com.etel.multitx.form;

import com.coopdb.data.Tbmultipletransaction;
import com.etel.lmsinquiry.forms.PaymentBreakdownForm;

/**
 * @author ETEL-LAPTOP07
 *
 */
public class MultipleTransactionInputForm {
	Tbmultipletransaction transaction;
	PaymentBreakdownForm breakdown;

	public Tbmultipletransaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Tbmultipletransaction transaction) {
		this.transaction = transaction;
	}

	public PaymentBreakdownForm getBreakdown() {
		return breakdown;
	}

	public void setBreakdown(PaymentBreakdownForm breakdown) {
		this.breakdown = breakdown;
	}

}
