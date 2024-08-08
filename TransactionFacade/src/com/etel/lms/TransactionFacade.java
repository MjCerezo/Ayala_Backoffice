package com.etel.lms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.cifsdb.data.Tbcifmain;
import com.coopdb.data.Tbchecksforclearing;
import com.coopdb.data.Tbgrouppayment;
import com.coopdb.data.Tbloanfin;
import com.coopdb.data.Tbloans;
import com.etel.glentries.forms.GLEntriesForm;
import com.etel.lms.forms.GroupPaymentAccountForm;
import com.etel.lms.forms.PaymentTransactionForm;
import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
import com.wavemaker.runtime.service.annotations.ExposeToClient;

/**
 * This is a client-facing service class. All public methods will be exposed to
 * the client. Their return values and parameters will be passed to the client
 * or taken from the client, respectively. This will be a singleton instance,
 * shared between all requests.
 * 
 * To log, call the superclass method log(LOG_LEVEL, String) or log(LOG_LEVEL,
 * String, Exception). LOG_LEVEL is one of FATAL, ERROR, WARN, INFO and DEBUG to
 * modify your log level. For info on these levels, look for tomcat/log4j
 * documentation
 */
@ExposeToClient
public class TransactionFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level;
	 * recommend changing this to FATAL or ERROR before deploying. For info on these
	 * levels, look for tomcat/log4j documentation
	 */
	public TransactionFacade() {
		super(INFO);
	}

	public String addEntry(Tbloanfin fin, Tbchecksforclearing check) {
		TransactionService txSrvc = new TransactionServiceImpl();
		String result = new String();
		try {
			log(INFO, "Starting addEntry");
			result = txSrvc.addEntry(fin, check);
			log(INFO, "Finished addEntry");
		} catch (Exception e) {
			log(ERROR, "The addEntry service operation has failed", e);
		}
		return result;
	}

	public List<Tbloanfin> findTXByAccountnoAndTXCode(String accountno, String txcode) {
		TransactionService txSrvc = new TransactionServiceImpl();
		List<Tbloanfin> result = new ArrayList<Tbloanfin>();
		try {
			log(INFO, "Starting findTXByAccountnoAndTXCode");
			result = txSrvc.findTXByAccountnoAndTXCode(accountno, txcode);
			log(INFO, "Finished findTXByAccountnoAndPTXCode");
		} catch (Exception e) {
			log(ERROR, "The findTXByAccountnoAndPTXCode service operation has failed", e);
		}
		return result;
	}

	public List<Tbloanfin> findTXByAccountnoANDTXCodeANDPaymode(String accountno, String txcode, String paymode,
			String txstatus) {
		TransactionService txSrvc = new TransactionServiceImpl();
		List<Tbloanfin> result = new ArrayList<Tbloanfin>();
		try {
			log(INFO, "Starting findTXByAccountnoANDTXCodeANDPaymode");
			result = txSrvc.findTXByAccountnoANDTXCodeANDPaymodeANDTxstatus(accountno, txcode, paymode, txstatus);
			log(INFO, "Finished findTXByAccountnoANDTXCodeANDPaymode");
		} catch (Exception e) {
			log(ERROR, "The findTXByAccountnoANDTXCodeANDPaymode service operation has failed", e);
		}
		return result;
	}

	public String errorCorrect(String txrefno) {
		TransactionService txSrvc = new TransactionServiceImpl();
		return txSrvc.errorCorrect(txrefno);
	}

	public PaymentTransactionForm getTransaction(String txrefno) {
		TransactionService txSrvc = new TransactionServiceImpl();
		return txSrvc.getTransaction(txrefno);
	}

	public List<Tbcifmain> searchClient(String cifname) {

		TransactionService txSrvc = new TransactionServiceImpl();
		List<Tbcifmain> ciflist = txSrvc.searchClient(cifname);
		return ciflist;

	}

	public String addGroupPayment(Tbgrouppayment payment) {
		TransactionService txSrvc = new TransactionServiceImpl();
		String result = txSrvc.addGroupPayment(payment);
		return result;
	}

	public List<Tbloans> getAccountListforGroupPayment(String cifno, String prodcode, String companycode) {
		TransactionService txSrvc = new TransactionServiceImpl();
		List<Tbloans> acctlist = txSrvc.getAccountListforGroupPayment(cifno, prodcode, companycode);
		return acctlist;

	}

	public String addGroupAccountPayment(GroupPaymentAccountForm acct, String companycode, String prodcode,
			String txstat, String txmode) {
		TransactionService txSrvc = new TransactionServiceImpl();
		String result = txSrvc.addGroupAccountPayment(acct, companycode, prodcode, txstat, txmode);

		return result;

	}

	public BigDecimal getTotalGroupPayments(String grouptxrefno) {
		TransactionService txSrvc = new TransactionServiceImpl();
		BigDecimal totalamt = txSrvc.getTotalGroupPayments(grouptxrefno);
		return totalamt;
	}

	public List<Tbloanfin> getAccountListPerGroup(String grouptxrefno) {
		TransactionService txSrvc = new TransactionServiceImpl();
		List<Tbloanfin> translist = txSrvc.getAccountListPerGroup(grouptxrefno);
		return translist;
	}

	public String checkOR(String orno) {
		TransactionService txSrvc = new TransactionServiceImpl();
		String returnval = txSrvc.checkOR(orno);
		return returnval;

	}

	public String cancelTransaction(String txrefno, String txcode, String reason) {
		TransactionService txSrvc = new TransactionServiceImpl();
		String result = txSrvc.cancelTransaction(txrefno, txcode, reason);
		return result;

	}

	public Tbgrouppayment getGroupPayment(String txrefno) {
		TransactionService txSrvc = new TransactionServiceImpl();
		Tbgrouppayment payment = txSrvc.getGroupPayment(txrefno);
		return payment;

	}

	public List<GLEntriesForm> getGLEntries(String txrefno) {

		TransactionService txSrvc = new TransactionServiceImpl();
		List<GLEntriesForm> gl = txSrvc.getGLEntries(txrefno);

		return gl;

	}

	public String postSinglePayment(String txrefno) {
		TransactionService txSrvc = new TransactionServiceImpl();
		String results = txSrvc.postSinglePayment(txrefno);

		return results;
	}

	public String returnTransaction(String txrefno, String txcode, String reason) {
		TransactionService txSrvc = new TransactionServiceImpl();
		String results = txSrvc.returnTransaction(txrefno, txcode, reason);

		return results;
	}

	public String postGroupPayment(String txrefno) {
		TransactionService txSrvc = new TransactionServiceImpl();
		String results = txSrvc.postGroupPayment(txrefno);

		return results;
	}

	public String reclass(String txrefno, String acctno) {
		TransactionService txSrvc = new TransactionServiceImpl();
		return txSrvc.reclass(txrefno, acctno);
	}
}
