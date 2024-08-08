/**
 * 
 */
package com.etel.teller;

import java.util.Date;
import java.util.List;

import com.casa.fintx.forms.AccountInquiryJournalForm;
import com.coopdb.data.Tboverageshortage;
import com.coopdb.data.Tbtellerslimit;
import com.etel.security.forms.TBRoleForm;
import com.etel.teller.form.TellerForm;
import com.etel.teller.form.TellersTotal;

/**
 * @author ETEL-LAPTOP19
 *
 */
public interface TellerService {

	List<Tbtellerslimit> findAllTellersLimitbyCoopCodeAndBranchCode();

	String createTellersLimit(Tbtellerslimit tellerslimit);

	String updateTellersLimit(Tbtellerslimit tellerslimit);

	String deleteTellersLimit(int id);

	Tbtellerslimit findTellersLimitbyId(int id);

	List<TBRoleForm> findAllTellerRoles();

	List<TellerForm> findAllTellers(String branchcode, String currency);

	String updateTellerStatus(String userid);

	TellerForm findTeller(String userid, String currency);

	Tbtellerslimit findTellersLimit(String username);
	
	List<AccountInquiryJournalForm> listTellerTxPerPeriod(Date from, Date to, String userid);

	List<TellersTotal> listTellerTotalCashTx(Date from, Date to, String userid, String txcode);

	List<TellersTotal> listTellerTotalNonCashTx(Date from, Date to, String userid, String txcode);

	List<TellersTotal> listTellerTotalChecksForClearing(String userid, int clearingdays);

	String declareOverageShortage(Tboverageshortage data);


}
