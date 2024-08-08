/**
 * 
 */
package com.lms.loans;

import com.coopdb.data.Tbloans;

/**
 * @author ETEL-COMP05
 *
 */
public interface LoansService {

	Tbloans findLoanByAccountno(String accountno);

}
