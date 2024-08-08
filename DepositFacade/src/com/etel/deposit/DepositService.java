/**
 * 
 */
package com.etel.deposit;

import java.util.List;

import com.coopdb.data.Tbchecksforclearing;
import com.coopdb.data.Tbdepdetail;
import com.coopdb.data.Tbdepositcif;
import com.etel.deposit.form.DepositAccountForm;
import com.etel.deposit.form.DepositLedgerForm;

/**
 * @author ETEL-LAPTOP19
 *
 */
public interface DepositService {

	List<DepositAccountForm> listDeposits(String memberid);

	List<DepositLedgerForm> listLedgerPerAcctno(String acctno);

	List<Tbdepositcif> listAccountOwners(String acctno);

	Tbdepdetail getDepositDetails(String acctno);

	List<Tbchecksforclearing> getClearedChecks(String acctno);

}
