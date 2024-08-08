package com.etel.dataentry;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cifsdb.data.Tbcifbusiness;
import com.cifsdb.data.Tbcifcorporate;
import com.cifsdb.data.Tbcifdependents;
import com.cifsdb.data.Tbcifemployment;
import com.cifsdb.data.Tbcifindividual;
import com.cifsdb.data.Tbcifmain;
import com.cifsdb.data.Tbothercontacts;
import com.cifsdb.data.Tbtradereference;
import com.coopdb.data.Tbaccountofficer;
import com.coopdb.data.Tbamortizedattachment;
import com.coopdb.data.Tbappbeneficiary;
import com.coopdb.data.Tbappbusiness;
import com.coopdb.data.Tbappcreditcardinfo;
import com.coopdb.data.Tbappdependents;
import com.coopdb.data.Tbappdosri;
import com.coopdb.data.Tbappemployment;
import com.coopdb.data.Tbappfinancialinfo;
import com.coopdb.data.Tbapppersonalreference;
import com.coopdb.data.Tbbanks;
import com.coopdb.data.Tbdeposit;
import com.coopdb.data.Tbloanproduct;
import com.coopdb.data.Tbloanreleaseinst;
import com.coopdb.data.Tblosdependents;
import com.coopdb.data.Tblosindividual;
import com.coopdb.data.Tblosmain;
import com.coopdb.data.Tblstapp;
import com.coopdb.data.Tblstappbusiness;
import com.coopdb.data.Tblstappcorporate;
import com.coopdb.data.Tblstappdependents;
import com.coopdb.data.Tblstappindividual;
import com.coopdb.data.Tblstbankaccounts;
import com.coopdb.data.Tblstcreditcardinfo;
import com.coopdb.data.Tblstexistingloansother;
import com.coopdb.data.Tblstsourceincome;
import com.coopdb.data.Tbmemberemployment;
import com.coopdb.data.Tbmembernetcapping;
import com.coopdb.data.Tbmemberrelationship;
import com.coopdb.data.Tbmembershipapp;
import com.coopdb.data.Tbothercontactslos;
import com.coopdb.data.Tbpledge;
import com.coopdb.data.Tbreferror;
import com.etel.company.forms.CompanyForm;
import com.etel.dataentryforms.AccountInfoForm;
import com.etel.dataentryforms.CollectionInstructionsForm;
import com.etel.dataentryforms.LoanFeesForm;
import com.etel.dataentryforms.LoanPayoutForm;
import com.etel.dataentryforms.LoanReleaseInstForm;
import com.etel.dataentryforms.MembershipHeaderForm;
import com.etel.dataentryforms.MembershipListPerStagesForm;
import com.etel.dataentryforms.PersonalDetails;
import com.etel.dataentryforms.RemarksForm;
import com.etel.dataentryutil.MembershipAppHeader;
import com.etel.forms.FormValidation;
import com.etel.utils.DateTimeUtil;
import com.etel.utils.UserUtil;
import com.etel.workflow.forms.WorkflowProcessForm;
import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
import com.wavemaker.runtime.server.FileUploadResponse;
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
public class FullDataEntryFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level;
	 * recommend changing this to FATAL or ERROR before deploying. For info on these
	 * levels, look for tomcat/log4j documentation
	 */
	private FullDataEntryService service = new FullDataEntryServiceImpl();

	public int age(Date dob) {

		int age = DateTimeUtil.getAge(dob);

		return age;
	}

	public int yrDiff(Date datefrom, Date dateto) {

		int yrdiff = DateTimeUtil.yearsDiff(datefrom, dateto);

		return yrdiff;
	}

	public Integer monthDiff(Date datefrom, Date dateto) {
		if (dateto == null) {
			dateto = new Date();
		}
		return DateTimeUtil.monthsDiff(datefrom, dateto);
	}

	public MembershipAppHeader getMemberAppHeader(String appid) {
		return MembershipAppHeader.getAppHeader(appid);
	}

	public MembershipAppHeader getAppHeaderTbmember(String memid) {
		return MembershipAppHeader.getAppHeaderTbmember(memid);
	}

	public String saveOrUpdateMemberApp(Tbmembershipapp app) {
		return service.saveOrUpdateMemberApp(app);
	}

	public Tbmembershipapp getMembershipapp(String memappid, Boolean audit) {
		return service.getMembershipapp(memappid, audit);
	}

//	Commented by Renz because of duplicate method name
//	public String saveOrUpdateEmployment(Tbappemployment employment) {
//		return service.saveOrUpdateEmployment(employment);
//	}
//
//	public String saveOrUpdateBusiness(Tbappbusiness business) {
//		return service.saveOrUpdateBusiness(business);
//	}
//
//	public String deleteItem(Integer employment, Integer business, Integer dependents, Integer financial, Integer beneficiary, Integer personalreference, Integer creditcard, Integer lstsourceincome, Integer existingloans) {
//		return service.deleteItem(employment, business, dependents, financial, beneficiary, personalreference, creditcard, lstsourceincome, existingloans);
//	}

	public String saveOrUpdateDependents(Tbappdependents dependents) {
		return service.saveOrUpdateDependents(dependents);
	}

	public List<Tbappemployment> listEmployment(String memappid) {
		return service.listEmployment(memappid);
	}

	public List<Tbappbusiness> listBusiness(String memappid) {
		return service.listBusiness(memappid);
	}

	public List<Tbappdependents> listDependents(String memappid) {
		return service.listDependents(memappid);
	}

	public Tbappemployment getEmployment(Integer id) {
		return service.getEmployment(id);
	}

	public Tbappbusiness getBusiness(Integer id) {
		return service.getBusiness(id);
	}

	public Tbappdependents getDependents(Integer id) {
		return service.getDependents(id);
	}

	public Tbappbeneficiary getBeneficiary(Integer id) {
		return service.getBeneficiary(id);
	}

	public Tbapppersonalreference getPersonalreference(Integer id) {
		return service.getPersonalreference(id);
	}

	public String saveOrUpdateFinancial(Tbappfinancialinfo fin) {
		return service.saveOrUpdateFinancial(fin);
	}

	public List<Tbappfinancialinfo> listFinancialInfo(String memappid, String fintype) {
		return service.listFinancialInfo(memappid, fintype);
	}

	public String saveOrUpdateBeneficiary(Tbappbeneficiary beneficiary) {
		return service.saveOrUpdateBeneficiary(beneficiary);
	}

	public List<Tbappbeneficiary> listBeneficiary(String memappid) {
		return service.listBeneficiary(memappid);
	}

	public List<Tbapppersonalreference> listPersonalReference(String memappid) {
		return service.listPersonalReference(memappid);
	}

	public String saveOrUpdateReference(Tbapppersonalreference personalreference) {
		return service.saveOrUpdateReference(personalreference);
	}

	public String saveOrUpdateDOSRIStatus(Tbappdosri dosrichecking) {
		return service.saveOrUpdateDOSRIStatus(dosrichecking);
	}

	public String saveMembership(Tbmembershipapp appid) {
		return service.saveMembership(appid);
	}

	public Tbappdosri getMemberDosri(String appid) {
		return service.getMemberDosri(appid);
	}

	public String saveApplicationHeaderdetails(String appid, String branch, String source, String company, String group,
			String memberclass, String ao, String agent) {
		return service.saveApplicationHeaderdetails(appid, branch, source, company, group, memberclass, ao, agent);
	}

	public Tblstapp getLstapp(String appno) {
		return service.getLstapp(appno);
	}

	public String getFullName(String username) {
		return UserUtil.getUserFullname(username);
	}

	public Tbmemberemployment getComakerInfo(String memid) {
		return service.getComakerInfo(memid);
	}

	public String addComaker(String appno, String memid) {
		return service.addComaker(appno, memid);
	}

	public List<Tblstexistingloansother> getExistingLoansOthers(String memid) {
		return service.getExistingLoansOthers(memid);
	}

	public String addCreditCardInfo(Tblstcreditcardinfo credcard) {
		return service.addCreditCardInfo(credcard);
	}

	public List<Tbappcreditcardinfo> getCreditCardInfo(String membershipappid) {
		return service.getCreditCardInfo(membershipappid);
	}

	public List<Tblstbankaccounts> getDepositsWithOtherBanks(String membershipID) {
		return service.getDepositWithOtherBanks(membershipID);
	}

	public String addDepositInfo(Tblstbankaccounts accounts) {
		return service.addDepositInfo(accounts);
	}

	public PersonalDetails getAndAddMemberAs(String relationship, String membershipid, String applicantid) {
		return service.getAndAddMemberAs(relationship, membershipid, applicantid);
	}

	public String dedupeComaker(String memid) {
		return service.dedupeComaker(memid);
	}

	public String addLoanInfoExistingLoans(Tblstexistingloansother exist) {
		return service.addLoanInfoExistingLoans(exist);
	}

	public String doneEncoding(Tblstapp app) {
		return service.doneEncoding(app);
	}

	public String saveOrUpdateCreditCardApp(Tbappcreditcardinfo creditcard) {
		return service.saveOrUpdateCreditCardApp(creditcard);
	}

	public List<Tbappcreditcardinfo> getApplicantsCreditCard(String membershipappid) {
		return service.getApplicantsCreditCard(membershipappid);
	}

	public Tbappcreditcardinfo getAppCreditCard(Integer id) {
		return service.getAppCreditCard(id);
	}

	public List<Tbloanproduct> getListLoanProduct() {
		return service.getListLoanProduct();
	}

	public String saveAsDraft(String appno, String loanpurpose, Date appdate) {
		return service.saveAsDraft(appno, loanpurpose, appdate);
	}

	public String saveOfficer(String appno) {
		return service.saveOfficer(appno);
	}

	public String saveSourceIncome(Tblstsourceincome source) {
		return service.saveSourceIncome(source);
	}

	public Tblstsourceincome getLstSourceIncome(String appno, String participation) {
		return service.getLstSourceIncome(appno, participation);
	}

	public List<Tbdeposit> getDeposits(String appno) {
		return service.getDeposits(appno);
	}

	public String returnApplication(String membershipappid, String returnremarks) {
		return service.returnApplication(membershipappid, returnremarks);
	}

	public String addLSTCreditCard(Tblstcreditcardinfo card) {
		return service.addLSTCreditCard(card);
	}

	public List<Tblstcreditcardinfo> getLSTCredCardInfo(String appno) {
		return service.getLSTCredCardInfo(appno);
	}

	public Tbpledge getCapconSavings(String memid) {
		return service.getCapconSavings(memid);
	}

	public String updateDepositInfo(Tblstbankaccounts acct) {
		return service.updateDepositInfo(acct);
	}

	public String deleteDepInfo(Integer id) {
		return service.deleteDepInfo(id);
	}

	public String updateExistingLoanOth(Tblstexistingloansother other) {
		return service.updateExistingLoanOth(other);
	}

	public Tblstexistingloansother getOtherExistingLoan(Integer id) {
		return service.getOtherExistingLoan(id);
	}

	public AccountInfoForm getAccountInfo(String appno) {
		return service.getAccountInfo(appno);
	}

	public List<Tbbanks> listBanks() {
		return service.listBanks();
	}

	public String updateAccountInfo(CollectionInstructionsForm collect, String type) {
		return service.updateAccountInfo(collect, type);

	}

	public List<LoanFeesForm> getListLoanFeesperapp(String appno) {
		return service.getListLoanFeesperapp(appno);
	}

	public List<LoanPayoutForm> getLoanPayout(String appno) {
		return service.getLoanPayout(appno);
	}

	public List<LoanReleaseInstForm> getLoanReleaseInst(String appno) {
		return service.getLoanReleaseInst(appno);
	}

	public CompanyForm getCoopNameByCoopcode(String coopcode) {
		return service.getCoopNameByCoopcode(coopcode);
	}

	public String saveLoanRelInstruction(Tbloanreleaseinst relInst) {
		return service.saveLoanRelInstruction(relInst);
	}

	public String updateRelInst(LoanReleaseInstForm form) {
		return service.updateRelInst(form);
	}

	public Tbloanreleaseinst getLoanRelease(int id) {
		return service.getLoanRelease(id);
	}

	public String deletePDC(Integer id) {
		return service.deletePDC(id);
	}

	public RemarksForm getRemarksDetails(String appid) {
		return service.getRemarksDetails(appid);
	}

	public String saveAccountSecurity(String appno, String seccode) {
		return service.saveAccountSecurity(appno, seccode);
	}

	public String saveOrUpdateAO(Tbaccountofficer d, String saveOrUpdate) {
		return service.saveOrUpdateAO(d, saveOrUpdate);
	}

	public List<Tbaccountofficer> listAO() {
		return service.listAO();
	}

	public List<Tbmemberrelationship> listMemberRelationshipPerAppID(String appid) {
		return service.listMemberRelationshipPerAppID(appid);
	}

	public String saveOrUpdateMemberRelationship(Tbmemberrelationship relative) {
		return service.saveOrUpdateMemberRelationship(relative);
	}

	public List<Tbmemberrelationship> listMemberRelationshipPerMemberID(String memberid) {
		return service.listMemberRelationshipPerMemberID(memberid);
	}

	public String deleteAO(String aocode, String companycode) {
		return service.deleteAO(aocode, companycode);
	}

	public Tbreferror getReferrorPerParam(String appid, String memberid) {
		return service.getReferrorPerParam(appid, memberid);
	}

	public BigDecimal getCapconAccount(String appno, String type) {
		return service.getCapconAccount(appno, type);
	}

	public Tbcifindividual getIndividual(String cifno) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.getIndividual(cifno);
	}

	public Tbcifmain getDetails(String cifno) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.getDetails(cifno);
	}

	public String updateCIFMain(Tbcifmain main, String filepath) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.updateCIFMain(main, filepath);
	}

	public String deleteItem(Integer dependentsID, Integer employmentID, Integer businessID, Integer otherid,
			Integer bankid) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.deleteItem(dependentsID, employmentID, businessID, otherid, bankid);
	}

	public List<Tbcifdependents> getListDependents(String cifno) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.getListDependents(cifno);
	}

	public String saveDependents(Tbcifdependents dependents) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.saveDependents(dependents);
	}

	public String updateIndividualCIF(Tbcifindividual indiv) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.updateIndividualCIF(indiv);
	}

	public FormValidation validateDataEntry(String cifno) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.validateDataEntry(cifno);
	}

	public String saveOrUpdateBusiness(Tbcifbusiness bus) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.saveOrUpdateBusiness(bus);
	}

	public String saveOrUpdateEmployment(Tbcifemployment emp) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.saveOrUpdateEmployment(emp);
	}

	public Tbcifcorporate getCorporate(String cifno) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.getCorporate(cifno);
	}

	public List<Tbcifbusiness> getListBusiness(String cifno) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.getListBusiness(cifno);
	}

	public List<Tbcifemployment> getListEmployment(String cifno) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.getListEmployment(cifno);
	}

	public List<Tbtradereference> getTradeRefList(String cifno, String tradetype) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.getTradeRefList(cifno, tradetype);
	}

	public String changeAccreditationStat(String cifno, String tradecifno, String tradetype) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.changeAccreditationStat(cifno, tradecifno, tradetype);
	}

//	public String deleteTradeRef(String cifno, String tradecifno, String tradetype, String cif){
//		FullDataEntryService service = new FullDataEntryServiceImpl();
//		return service.deleteTradeRef(cifno, tradecifno, tradetype, cif);
//	}

	public Tbtradereference getTRDetails(String cifno, String tradecifno, String tradetype) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.getTRDetails(cifno, tradecifno, tradetype);
	}

//	public String saveTradeRef(Tbtradereference data) {
//		FullDataEntryService service = new FullDataEntryServiceImpl();
//		return service.saveTradeRef(data);
//	}

//	public String updateTradeRef(Tbtradereference data){
//		FullDataEntryService service = new FullDataEntryServiceImpl();
//		return service.updateTradeRef(data);
//	}

	public String addOtherContact(Tbothercontacts contacts) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.addOtherContact(contacts);
	}

	public List<Tbothercontacts> listOthercontacts(String cifno) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.listOthercontacts(cifno);
	}

	public String updateCorporateCIF(Tbcifcorporate corp) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.updateCorporateCIF(corp);
	}

	public WorkflowProcessForm getWorkflowDetails(String sequenceno, String cifno) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.getWorkflowDetails(sequenceno, cifno);
	}

	// 11-10-2020 MAR
	public String saveOrupdateHeader(String appno, String product, String company, String branch, String purpose,
			String reason, String reftype, String refname, String rollovertype) {
		return service.saveOrupdateHeader(appno, product, company, branch, purpose, reason, reftype, refname,
				rollovertype);
	}

	// no dedupe Renz 02172021
	public List<Tbtradereference> listNewTradeRef(String cifno) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.listNewTradeRef(cifno);
	}

	public List<Tbtradereference> listNewTradeRefSupplier(String cifno) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.listNewTradeRefSupplier(cifno);
	}

	public String saveOrUpdateNewTradeRef(Tbtradereference ref) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.saveOrUpdateNewTradeRef(ref);
	}

	public String saveOrUpdateNewTradeRefSupplier(Tbtradereference ref) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.saveOrUpdateNewTradeRefSupplier(ref);
	}

	public String deleteNewTradeRef(Integer id) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.deleteNewTradeRef(id);
	}

	// MAR 03-30-2021
	public Tblosmain getDetailsRB(String cifno) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.getDetailsRB(cifno);
	}

	public Tblosindividual getIndividualRB(String cifno) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.getIndividualRB(cifno);
	}

	public List<Tblosdependents> getListDependentsRB(String cifno) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.getListDependentsRB(cifno);
	}

	public String updateRBMain(Tblosmain main) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.updateRBMain(main);
	}

	public String saveDependentsRB(Tblstappdependents dependents) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.saveDependentsRB(dependents);
	}

	public String updateIndividualRB(Tblstappindividual indiv) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.updateIndividualRB(indiv);
	}

	public FormValidation validateDataEntryRB(String cifno) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.validateDataEntryRB(cifno);
	}

	public List<Tblstappbusiness> getListBusinessRB(String cifno) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.getListBusinessRB(cifno);
	}

	public Tbcifcorporate getCorporateRB(String cifno) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.getCorporateRB(cifno);
	}

	public List<Tbothercontactslos> listOthercontactsRB(String cifno) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.listOthercontactsRB(cifno);
	}

	public String updateCorporateCIFRB(Tblstappcorporate corp) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.updateCorporateCIFRB(corp);
	}

	public BigDecimal totalDebitCredit(String pnno, String type, String txcode) {
		return service.totalDebitCredit(pnno, type, txcode);
	}

	public Boolean isPNNoValid(String pnno) {
		return service.isPNNoValid(pnno);
	}

	public String checkNetProceeds(String appno, BigDecimal amount) {
		return service.checkNetProceeds(appno, amount);
	}

	public String saveOrUpdateBus(Tbcifbusiness d) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.saveOrUpdateBus(d);
	}

	public String deleteDependents(Tblstappdependents d) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.deleteDependents(d);
	}

	public FileUploadResponse uploadFile(MultipartFile file) {
		return service.uploadFile(file);
	}

	public String updateProfilePhoto(String cifno, String filepath) {
		return service.updateProfilePhoto(cifno, filepath);
	}
	//MAr 
	public String changeCompanyOrMemberStatus(String cifno, String status, String remarks) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.changeCompanyOrMemberStatus(cifno, status, remarks);
	}

	// 01.31.2023 - Amortized Attachment 
	public String saveOrUpdateAmortizedDetails(Tbamortizedattachment d) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.saveOrUpdateAmortizedDetails(d);
	}
	public List<Tbamortizedattachment> listAmortizedDetails(String appno) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.listAmortizedDetails(appno);
	}
	public String deleteAmortizedDetails(Integer id) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.deleteAmortizedDetails(id);
	}
	// end of Amortized Attachment 
	
	public MembershipHeaderForm getMembershipHeader(String cifno) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.getMembershipHeader(cifno);
	}
	
	public String saveOrUpdateNetCapping(Tbmembernetcapping d) {
		return service.saveOrUpdateNetCapping(d);
	}	
	
	public Tbmembernetcapping getTbmembernetcapping(String appno) {
		return service.getTbmembernetcapping(appno);
	}	
	
	public Tbcifemployment getTbcifemploymentTop1(String cifno) {
		return service.getTbcifemploymentTop1(cifno);
	}
	
	public String changeCompanyOrMemberBatchUpdateStatus(List<MembershipListPerStagesForm> memberList,String status) {
		FullDataEntryService service = new FullDataEntryServiceImpl();
		return service.changeCompanyOrMemberBatchUpdateStatus(memberList, status);
	}
}
