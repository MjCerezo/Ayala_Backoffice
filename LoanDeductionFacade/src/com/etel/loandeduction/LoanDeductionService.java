/**
 * 
 */
package com.etel.loandeduction;

import java.util.List;

import com.coopdb.data.Tbloandeduction;

/**
 * @author ETEL-LAPTOP07
 *
 */
public interface LoanDeductionService {

	String saveDeduction(Tbloandeduction loandeduction);

	String deleteDeduction(Tbloandeduction loandeduction);

	List<Tbloandeduction> listDeductions(String appno);

}
