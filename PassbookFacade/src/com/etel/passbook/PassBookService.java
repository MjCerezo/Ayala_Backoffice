/**
 * 
 */
package com.etel.passbook;

import java.util.List;

import com.coopdb.data.Tbpassbookinventory;
import com.etel.passbook.form.PassbookDataForm;
import com.etel.passbook.form.PassbookParams;

/**
 * @author ETEL-LAPTOP19
 *
 */
public interface PassBookService {

	List<Tbpassbookinventory> listPassbookPerCompanyPerBranch(String company, String branch);

	String addPBSeries(Tbpassbookinventory pb);

	String deletePB(Tbpassbookinventory pb);

	List<PassbookDataForm> getPassbook(String passbookType, String accountNo, String totalLineNumber, String lineno, String startLineNumber, String endLineNumber);

}
