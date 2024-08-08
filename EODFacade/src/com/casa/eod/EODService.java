/**

 * 
 */
package com.casa.eod;

import java.util.Date;
import java.util.List;

import com.casa.forms.LogsAndModulesForm;
import com.coopdb.data.Tbaccountinfo;
import com.coopdb.data.Tbbranch;
import com.coopdb.data.Tbloanfin;
import com.coopdb.data.Tbprocessingdate;

/**
 * @author ETEL-COMP05
 *
 */
public interface EODService {

	Tbbranch getMainBranch();
	
	LogsAndModulesForm findAllLogsFortheDay(Date currentbusinessdate);

	int runEOD(int module, String branchcodes);
	
	String loanBooking(List<Tbaccountinfo> loanTx);

	Tbprocessingdate getProcDate();

	String saveProcDate(Tbprocessingdate procDate);

	String checkClearing();

	void runLMSEOD();
	
	String loanPostingTest(List<Tbloanfin> finLsit);

	List<String> listOfOpenBranch();

}
