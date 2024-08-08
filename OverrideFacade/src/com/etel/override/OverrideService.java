/**
 * 
 */
package com.etel.override;

import java.util.List;

import com.casa.fintx.forms.OverrideResultForm;
import com.coopdb.data.Tboverridematrix;
import com.coopdb.data.Tboverriderequest;
import com.etel.deposittransaction.form.DepositTransactionForm;
import com.etel.override.form.OverrideRequestForm;
import com.etel.override.form.OverrideResponseForm;

/**
 * @author ETEL-LAPTOP19w
 *
 */
public interface OverrideService {

	String addOverrideRule(Tboverridematrix overridematrix);

	String deleteOverrideRule(Tboverridematrix overridematrix);

	List<Tboverridematrix> listOverrideRule(String txcode, String prodcode, String subprodcode);

	List<Tboverriderequest> listOverrideRequest(String txrefno);

	List<OverrideResponseForm> checkOverride(OverrideRequestForm form);

	String requestOverride(DepositTransactionForm form, List<Tboverriderequest> requests);

	String updateOverride(String txrefno, String status, String username, String password);

	OverrideResultForm waitRemoteOverride(String txrefno);

	List<Tboverriderequest> listPendingRemoteOverride();

	String updateOverrideAccountno(String txrefno, String accountno);
}
