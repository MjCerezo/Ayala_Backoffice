/**
 * 
 */
package com.etel.loancalc;

import com.etel.loancalc.forms.LoanCalculatorForm;

/**
 * @author ETEL-LAPTOP07
 *
 */
public interface LoanCalculatorServiceNew {

	LoanCalculatorForm computeLoan(LoanCalculatorForm loancalc);

	String saveLoanDetails(LoanCalculatorForm loancalc);

}
