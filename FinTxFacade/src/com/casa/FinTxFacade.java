package com.casa;

import java.math.BigDecimal;
import java.util.List;

import com.casa.fintx.forms.AccountCheckForm;
import com.casa.fintx.forms.AccountInquiryForm;
import com.casa.fintx.forms.AccountInquiryJournalForm;
import com.casa.fintx.forms.AccountInquiryMainForm;
import com.casa.fintx.forms.CashInCashOutForm;
import com.casa.fintx.forms.CashPositionConfirmForm;
import com.casa.fintx.forms.CashPositionForm;
import com.casa.fintx.forms.FinGenericForm;
import com.casa.fintx.forms.InquiryNameList;
import com.casa.fintx.forms.OverrideActionForm;
import com.casa.fintx.forms.OverrideResultForm;
import com.casa.util.UtilService;
import com.casa.util.UtilServiceImpl;
import com.coopdb.data.Tbbrfintxjrnl;
import com.coopdb.data.Tbfintxchecklist;
import com.coopdb.data.Tbfintxjrnl;
import com.coopdb.data.Tbmanagerscheck;
import com.coopdb.data.Tbmctxjrnl;
import com.coopdb.data.Tbnetamt;
import com.coopdb.data.Tboverride;
import com.coopdb.data.Tbtransactioncode;
import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
import com.wavemaker.runtime.service.annotations.ExposeToClient;

/**
 * This is a client-facing service class.  All
 * public methods will be exposed to the client.  Their return
 * values and parameters will be passed to the client or taken
 * from the client, respectively.  This will be a singleton
 * instance, shared between all requests. 
 * 
 * To log, call the superclass method log(LOG_LEVEL, String) or log(LOG_LEVEL, String, Exception).
 * LOG_LEVEL is one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level.
 * For info on these levels, look for tomcat/log4j documentation
 */
@ExposeToClient
public class FinTxFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public FinTxFacade() {
       super(INFO);
    }
    
	public String cashPos(CashPositionForm form) {
		FinTxService service = new FinTxServiceImpl();
		String result = service.cashPos(form);
		return result;
	}

	public CashPositionConfirmForm cashPosDenom(String txref){
		FinTxService service = new FinTxServiceImpl();
		CashPositionConfirmForm form = service.cashPosDenom(txref);
		return form;
	}
	
	public String confirmCashPos(String txref, String act, String remarks) {
		FinTxService service = new FinTxServiceImpl();
		String result = service.confirmCashPos(txref, act, remarks);
		return result;
	}

	public AccountInquiryMainForm accountInquiry(String acctno){
		FinTxService service = new FinTxServiceImpl();
		AccountInquiryMainForm form = service.accountInquiry(acctno);
		return form;
	}
	
	public List<InquiryNameList> accountInquiryName(String name) {
		FinTxService service = new FinTxServiceImpl();
		return service.accountInquiryName(name);
	}
	
	public CashInCashOutForm getCashInCashOut(String userid, String currency){
		FinTxService service = new FinTxServiceImpl();
		CashInCashOutForm form = service.getCashInCashOut(userid, currency);
		return form;
	}
	
	public AccountCheckForm checkAcct(String acctno) {
		FinTxService service = new FinTxServiceImpl();
		return service.checkAcct(acctno);
	}
	
	public AccountCheckForm checkAcctFundTrans(String acctnoto, String acctnofrom) {
		FinTxService service = new FinTxServiceImpl();
		return service.checkAcctFundTrans(acctnoto, acctnofrom);
	}
	/*-----------------------TRANSACTION METHODS-----------------------*/
	public FinGenericForm cashDepWithDrCrMemo(Tbfintxjrnl jrnl, List<Tbfintxchecklist> checklist) {
		FinTxService service = new FinTxServiceImpl();
		FinGenericForm result = service.cashDepWithDrCrMemo(jrnl, checklist);
		System.out.println("#### "+result.getResult());
		return result;
	}
	
//	public FinGenericForm finTransaction(TransactForm form) {
//		FinTxService service = new FinTxServiceImpl();
//		FinGenericForm result = service.cashDepWithDrCrMemo();
//		System.out.println("#### "+result.getResult());
//		return result;
//	}
	
	public String overrideTransaction(String txrefno, String username) {
		FinTxService service = new FinTxServiceImpl();
		String result = service.overrideTransaction(txrefno, username);
		return result;
	}
	
	public String cancelOverrideTX(String txrefno) {
		FinTxService service = new FinTxServiceImpl();
		String result = service.cancelOverrideTX(txrefno);
		return result;
	}
	
	public String requestRemoteOverride(String txrefno) {
		FinTxService service = new FinTxServiceImpl();
		return service.requestRemoteOverride(txrefno);
	}
	
	public Integer requestRemoteOverrideCount() {
		FinTxService service = new FinTxServiceImpl();
		return service.requestRemoteOverrideCount();
	}

	public List<OverrideActionForm> requestRemoteOverrideList() {
		FinTxService service = new FinTxServiceImpl();
		return service.requestRemoteOverrideList();
	}
	
	public Integer requestRemoteOverrideWait(String txrefno) {
		FinTxService service = new FinTxServiceImpl();
		return service.requestRemoteOverrideWait(txrefno);
	}

	public OverrideResultForm requestRemoteOverrideResult(String txrefno) {
		FinTxService service = new FinTxServiceImpl();
		return service.requestRemoteOverrideResult(txrefno);
	}
	
	public String requestRemoteOverrideAction(String txrefno, Integer val) {
		FinTxService service = new FinTxServiceImpl();
		return service.requestRemoteOverrideAction(txrefno, val);
	}
	
	public List<Tbnetamt> userCashPos(String userid) {
		FinTxService service = new FinTxServiceImpl();
		return service.userCashPos(userid);
	}
	
	/*-----------------------FORMS ACTIVATOR METHODS-----------------------*/
	public AccountInquiryForm actinqform(){
		AccountInquiryForm form = new AccountInquiryForm();
		return form;
	}
	
	public AccountInquiryJournalForm actinqhistform(){
		AccountInquiryJournalForm form = new AccountInquiryJournalForm();
		return form;
	}	
	
	public Integer printDocSlip(String txrefno) {
		UtilService service = new UtilServiceImpl();
		return service.printDocSlip(txrefno);
	}
	
	public String errorCorrect(String txrefno) {
		FinTxService service = new FinTxServiceImpl();
		return service.errorCorrect(txrefno);
	}
	
	public Tbtransactioncode getTxinfo(String txname) {
		FinTxService service = new FinTxServiceImpl();
		return service.getTxinfo(txname);
	}
	
	public String initOverride(Tboverride data) {
		FinTxService service = new FinTxServiceImpl();
		return service.initOverride(data);
	}
	
	public String requestMc(Tbmanagerscheck data) {
		FinTxService service = new FinTxServiceImpl();
		return service.requestMc(data);
	}
	
	public String depositMc(Tbmctxjrnl data) {
		FinTxService service = new FinTxServiceImpl();
		return service.depositMc(data);
	}
	public String saveBuySellFx(Tbbrfintxjrnl brjrnl){
		FinTxService service = new FinTxServiceImpl();
		return service.saveBuySellFx(brjrnl);
	}
	public String mcgcDeposit(Tbfintxjrnl fin) {
		FinTxService service = new FinTxServiceImpl();
		return service.mcgcDeposit(fin);
	}
	public Integer checkBranch(String brcode,String acctno){ 
		FinTxService service = new FinTxServiceImpl();
		return service.checkBranch(brcode,acctno);
	}
	public String lockAmount(String memberid, BigDecimal amount, String reason) {
		FinTxService service = new FinTxServiceImpl();
		return service.lockAmount(memberid, amount, reason);
	}
	public String unlockAmount(String memberid, BigDecimal amount) {
		FinTxService service = new FinTxServiceImpl();
		return service.unlockAmount(memberid, amount);
	}
}		
