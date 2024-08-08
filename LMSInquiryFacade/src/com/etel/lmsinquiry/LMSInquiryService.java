package com.etel.lmsinquiry;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.coopdb.data.Tbcoa;
import com.coopdb.data.Tblntxjrnl;
import com.coopdb.data.Tbloanfin;
import com.etel.lms.forms.LoanAccountForm;
import com.etel.lmsinquiry.forms.CustomerInfoForm;
import com.etel.lmsinquiry.forms.LoanAccountInquiryForm;
import com.etel.lmsinquiry.forms.LoanTransactionHistory;
import com.etel.lmsinquiry.forms.PaymentBreakdownForm;
import com.etel.lmsinquiry.forms.PaymentScheduleForm;

public interface LMSInquiryService {

	List<LoanAccountForm> getLoanAccounts(String acctno, String clientname, String cifno);

	LoanAccountInquiryForm accountform(String accountno);

	List<LoanTransactionHistory> getTransactionHistory(String acctno);

	CustomerInfoForm getCustomerInformation(String cifno);

	PaymentScheduleForm getPaymentSchedPerAcct(String pnno);

	List<Tbloanfin> getPaymentTransactionInquiry(String clientname, String pnno, String transno, String orno,
			String status);

	PaymentBreakdownForm getPaymentBreakdown(String accountno, Date txvaldt, BigDecimal txamount, BigDecimal txinterest,
			BigDecimal txlpc, BigDecimal txar);

	List<Tbcoa> getAndListTbcoa(String acctno);

	Tbloanfin getLastTransaction(String acctno);
}
