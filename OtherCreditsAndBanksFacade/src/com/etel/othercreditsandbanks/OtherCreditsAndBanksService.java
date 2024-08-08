package com.etel.othercreditsandbanks;

import java.util.List;

import com.coopdb.data.Tbotheraccounts;

public interface OtherCreditsAndBanksService {

	List<Tbotheraccounts> listLoan(String cifno);

	List<Tbotheraccounts> listBanks(String cifno);
	
	List<Tbotheraccounts> listCredits(String cifno);
	
	String deleteCreditBanks(Integer id);

	String saveOrUpdateLoan(Tbotheraccounts ref);

	String saveOrUpdateBankAccount(Tbotheraccounts ref);

	String saveOrUpdateCredit(Tbotheraccounts ref);
	
}
