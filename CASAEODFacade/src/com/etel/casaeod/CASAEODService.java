/**
 * 
 */
package com.etel.casaeod;

import java.util.Date;
import java.util.List;

import com.coopdb.data.Tbbranch;
import com.coopdb.data.Tbprocessingdate;
import com.etel.casaeod.form.LogsAndModulesForm;

/**
 * @author ETEL-LAPTOP19
 *
 */
public interface CASAEODService {

	Tbbranch getMainBranch();
	
	LogsAndModulesForm findAllLogsFortheDay(Date currentbusinessdate);

	int runEOD(int module, String branchcodes);
	
	Tbprocessingdate getProcDate();

	String saveProcDate(Tbprocessingdate procDate);

	String checkClearing();

	List<String> listOfOpenBranch();

	String timeDepositMaturity(String accountno);

	void runEODReports();

}
