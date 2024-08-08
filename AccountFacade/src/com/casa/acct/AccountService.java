package com.casa.acct;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.casa.acct.forms.AccountClosureForm;
import com.casa.acct.forms.AccountGenericForm;
import com.casa.acct.forms.AccountMaintenanceForm;
import com.casa.acct.forms.CIFWatchlistMainForm;
import com.casa.acct.forms.CheckDeceasedForm;
import com.casa.acct.forms.FileMaintenanceHistory;
import com.casa.acct.forms.InquiryCIFNameList;
import com.casa.acct.forms.LiftGarnishFormData;
import com.casa.acct.forms.MaturedAccountActionForm;
import com.casa.acct.forms.PlaceHoldForm;
import com.casa.acct.forms.StopPaymentOrderForm;
import com.casa.acct.forms.TimeDepositAccountDetailForm;
import com.casa.acct.forms.TimeDepositCertForm;
import com.casa.acct.forms.TimeDepositListForm;
import com.casa.user.forms.UserInfoForm;
import com.cifsdb.data.Tbotheraccounts;
import com.coopdb.data.Tbchecksforclearing;
import com.coopdb.data.Tbdeposit;
import com.coopdb.data.Tbdepositcif;
import com.coopdb.data.Tbfreezeaccount;
import com.coopdb.data.Tbholdamtcheck;
import com.coopdb.data.Tblockamount;
import com.coopdb.data.Tbprodmatrix;
import com.coopdb.data.Tbtimedeposit;
import com.etel.qdeforms.QDEParameterForm;

public interface AccountService {

	AccountGenericForm createAccount(Tbdeposit dep, List<Tbdepositcif> ciflist);
	AccountGenericForm checkAccount(String accountno);
	AccountMaintenanceForm acctInfo(String accountno);
	AccountGenericForm acctSave(AccountMaintenanceForm form);
	List<Tbprodmatrix> getProdList(String prodgroup);
	AccountClosureForm getAcctClosure(String accountno);
	AccountGenericForm acctClose(String accountno);
	AccountGenericForm placeHoldAmt(Tbholdamtcheck record);
	List<PlaceHoldForm> getHoldAmtList(String accountno, String type);
	AccountGenericForm liftHoldAmt(int id, String liftreason, Date businessdt, String type);
	List<TimeDepositListForm> getTimeDepAcctList(String accountno);
	TimeDepositAccountDetailForm getTimeDepAcctDet(String accountno);
	List<Tbdeposit> getTimeDepMatAcctList(String accountno);
	AccountGenericForm submitMatAcctAction(MaturedAccountActionForm form);
	
	AccountGenericForm placeHoldCheck(Tbholdamtcheck record);
	List<StopPaymentOrderForm> spoList(String acctno);
	AccountGenericForm liftSPO(int id, String liftreason, Date businessdt);
	String createNewProduct(Tbprodmatrix input);
	Tbprodmatrix getProductDetail(String prodcode, String subprodcode);
	AccountGenericForm rolloverTimeDepositAccount(Tbdeposit dep);
	
	UserInfoForm checkMemberNo(String memberno);
	List<InquiryCIFNameList> checkMemberNoName(String name, String custtype);
	
	Integer acctAlertOff(String accountno);
	String getControlno(String accountno);
	List<TimeDepositCertForm> getTDCList(String accountno);
	
	String freezeAccount(Tbfreezeaccount data);
	Tbfreezeaccount getFreezeInfo(String accountno);
	String liftFreeze(String accountno);
	CheckDeceasedForm checkDeceased(String memberno);
	int checkMishandled(String memberno);
	List<Tbchecksforclearing> getFloatItems(String acctno);
	List<Tbdeposit> searchDeposit(String acctno, String name);
	String addPlaceHoldAmt(Tbholdamtcheck hold);
	List<Tbholdamtcheck> getListHoldAmt(String accountno);
	String updateLiftHoldAmt(Tbholdamtcheck hold);
	String saveTDDetails(Tbtimedeposit td);
	String pretermTDAccount(Tbtimedeposit td, String modeOfPayment, String creditAcctNo);
	List<Tbtimedeposit> listMaturedAccounts(String accountno);
	List<AccountMaintenanceForm> listAccountsByProduct(String acctno, String name, String prodcode);

	String saveOrupdateExternalAcct(Tbotheraccounts ext);
	String deleteExternalAcct(int id);
	List<Tbotheraccounts> externalList(String cifno, String accttype);
	Tbotheraccounts getExtAcctbyID(String cifno, Integer id);
	BigDecimal sumExtOutstandingBalance(String cifno, String accttype);
	
	//12-29-2020
	AccountGenericForm createAccountWithCIF(Tbdeposit dep, List<Tbdepositcif> ciflist, List<QDEParameterForm> list,
			String ciftype, String cifstatus, boolean isencoding);
	CIFWatchlistMainForm listCIFWatchlist(String acctno);
	String updateSigcard(String accountno, String filename);
	String lockUnlockAmount(Tblockamount lockamount);
	List<Tblockamount> listLockedAmount(String typeoflock, String accountno, BigDecimal amount, String reason,
			Date expirydate, String status);
	List<FileMaintenanceHistory> listFileMaintenanceHistory(String accountno);
	List<LiftGarnishFormData> listLiftGarnish(String accountNo, String acctsStaus);
}
