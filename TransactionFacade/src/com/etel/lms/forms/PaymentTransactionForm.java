/**
 * 
 */
package com.etel.lms.forms;

import java.util.List;

import com.coopdb.data.Tbloanfin;
import com.etel.glentries.forms.GLEntriesForm;

/**
 * @author Mheanne Munoz
 *
 */
public class PaymentTransactionForm {

	
	Tbloanfin transaction;
	List<GLEntriesForm> glentries;
	
	public Tbloanfin getTransaction() {
		return transaction;
	}
	public void setTransaction(Tbloanfin transaction) {
		this.transaction = transaction;
	}
	public List<GLEntriesForm> getGlentries() {
		return glentries;
	}
	public void setGlentries(List<GLEntriesForm> glentries) {
		this.glentries = glentries;
	}
	
	
}
