/**
 * 
 */
package com.etel.timedeposit;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.coopdb.data.Tbtimedeposit;

/**
 * @author ETEL-LAPTOP19
 *
 */
public interface TimeDepositService {

	Tbtimedeposit getTimeDeposit(String acctno);

	String terminateAccount(String accountno, String disposition, String intdisposition, String prindisposition);

	List<Tbtimedeposit> listMaturingAccounts(Date startdate, Date enddate, String dispo);

	String terminateTDAccount(String accountno, String credittoacctno);

	String changeDisposition(String accountno, String newdispo, String credittoacctno, String intdispo,
			BigDecimal placementamt, int term, BigDecimal intRate, BigDecimal wTaxRate, Date bookDate, Date matDate,
			String tdcno, String passbookno);

	String interestWithdrawal(String accountno, BigDecimal amount, String modeofrelease, String credtitoaccountno,
			BigDecimal amttocredit);

	Date computeMaturityDate(Date placementdate, int term, String termFreq, int skipWeekend, int skipHoliday);

}
