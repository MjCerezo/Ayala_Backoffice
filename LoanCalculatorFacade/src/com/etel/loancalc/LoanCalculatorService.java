/**
 * 
 */
package com.etel.loancalc;

import java.math.BigDecimal;
import java.util.List;

import com.coopdb.data.Tbaccountinfo;
import com.coopdb.data.Tbirregsched;
import com.coopdb.data.Tbloanoffset;
import com.coopdb.data.Tbloans;
import com.coopdb.data.Tbpaysched;
import com.coopdb.data.Tbpdc;
import com.etel.loancalc.forms.LoanCalculatorForm;

/**
 * @author MMM
 * 
 *
 */
public interface LoanCalculatorService {

	Tbaccountinfo getAccountInfoByAppno(String appno);
	LoanCalculatorForm computeLoan(LoanCalculatorForm loancalc);
	String saveLoanDetails(LoanCalculatorForm loancalc);
	
	String addCheckRecord(Tbpdc pdc);
	
	List<Tbpdc> getCheckList(String loanappno);
	List<Tbirregsched> getIrregSchedList(String loanappno);
	String addIrregSched(Tbirregsched sched);
	BigDecimal getTotalCheckAmount(String loanappno, List<Tbpdc> pdcs);
	List<Tbpaysched> getPayschedList(String loanappno);
	/**
	 * --Get Existing Loans by CIFNo--
	 * @author Kevin (09.14.2018)
	 * @return List<{@link Tbloans}>
	 * */
	List<Tbloans> getExistingLoansByCIFNo(String cifno);
	/**
	 * --Add Loan Account for Offset--
	 * @author Kevin (09.15.2018)
	 * @return String = success, otherwise failed
	 * */
	String addLoanAccountForOffset(List<Tbloans> tbloans, String appno);
	/**
	 * --Delete Loan Account for Offset--
	 * @author Kevin (09.15.2018)
	 * @return String = success, otherwise failed
	 * */
	String deleteLoanAccountForOffset(Tbloanoffset loanoffset);
	/**
	 * --Get List of Loan Accounts for Offset--
	 * @author Kevin (09.15.2018)
	 * @return List<{@link Tbloanoffset}>
	 * */
	List<Tbloanoffset> getListLoanAcctForOffset(String cifno);
	String deleteCheckRecord(Tbpdc pdc);
	String deleteIrregSched(Tbirregsched sched);
	BigDecimal getTotalReceivablesByAppNo(String appno);
	Tbloans getLoanAccount(String pnno);
	
}
