package com.casa.misc;

import java.util.List;

import com.casa.misc.forms.MerchantForm;
import com.coopdb.data.Tbbillspayment;
import com.coopdb.data.Tbcheckbook;
import com.coopdb.data.Tbchecksforclearing;
import com.coopdb.data.Tbcooperative;
import com.coopdb.data.Tbdeposit;
import com.coopdb.data.Tbmerchant;
import com.coopdb.data.Tbmisctx;
import com.coopdb.data.Tbpassbookissuance;
import com.etel.lmsinquiry.forms.PaymentBreakdownForm;

public interface MiscTxService {

	List<MerchantForm> getMerchantList();

	String createPayment(Tbbillspayment payment, List<Tbchecksforclearing> checks,
			PaymentBreakdownForm paymentbreakdown);

	String createMiscTx(Tbmisctx misc, List<Tbchecksforclearing> checks);

	String addMerchant(Tbmerchant merch);

	String checkbookIssuance(Tbcheckbook data);

	String passbookIssuance(Tbpassbookissuance pbissuance);

	Tbdeposit getAcctDetails(String acctno);

	int checkFreeze(String acctno);

	Tbcooperative getMemfeeAmount(String coopcode);

	List<Tbpassbookissuance> getPassBook(String accountno, String issuancetype);
	
	List<Tbdeposit> getAcctDetailsList(String acctno, String clientname);	

	Tbmisctx getMiscTxDetails(String txrefno, String mediaNo);

	String errorCorrectMiscTx(Tbmisctx misc, List<Tbchecksforclearing> checks, String txrefno);	
}
