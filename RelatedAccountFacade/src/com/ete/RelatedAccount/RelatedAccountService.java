package com.ete.RelatedAccount;

import java.util.List;

import com.etel.relatedaccount.form.AccountProfitabilityForm;
import com.etel.relatedaccount.form.DepositAccountForm;
import com.etel.relatedaccount.form.LoanAccountForm;
import com.etel.relatedaccount.form.RelatedAccountParameterForm;

public interface RelatedAccountService {

	List<DepositAccountForm> getDepositAccount(RelatedAccountParameterForm forms);

	List<LoanAccountForm> getLoanAccount(RelatedAccountParameterForm forms);

	List<AccountProfitabilityForm> getAccountProfitability(RelatedAccountParameterForm forms);

	List<LoanAccountForm> getLoanPayment(RelatedAccountParameterForm forms);

}
