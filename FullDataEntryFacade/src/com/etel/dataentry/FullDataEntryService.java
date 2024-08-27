package com.etel.dataentry;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cifsdb.data.CapitalPledge;
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
import com.etel.forms.FormValidation;
import com.etel.workflow.forms.WorkflowProcessForm;
import com.wavemaker.runtime.server.FileUploadResponse;

public interface FullDataEntryService {

	String saveOrUpdateMemberApp(Tbmembershipapp memberapp);

	Tbmembershipapp getMembershipapp(String memappid, Boolean audit);

//	Commented by Renz because of duplicate method name	
//	String saveOrUpdateEmployment(Tbappemployment employment);
//	
//	String saveOrUpdateBusiness(Tbappbusiness business);
//	
//	String deleteItem(Integer employment, Integer business, Integer dependents, Integer financial, Integer beneficiary, Integer personalreference, Integer creditcard, Integer lstsourceincome, Integer existingloans);

	String saveOrUpdateDependents(Tbappdependents dependents);

	List<Tbappemployment> listEmployment(String memappid);

	List<Tbappbusiness> listBusiness(String memappid);

	List<Tbappdependents> listDependents(String memappid);

	Tbappemployment getEmployment(Integer id);

	Tbappbusiness getBusiness(Integer id);

	Tbappdependents getDependents(Integer id);

	String saveOrUpdateFinancial(Tbappfinancialinfo fin);

	List<Tbappfinancialinfo> listFinancialInfo(String memappid, String fintype);

	Tbappfinancialinfo getFinancialInfo(Integer id);

	String saveOrUpdateBeneficiary(Tbappbeneficiary beneficiary);

	List<Tbappbeneficiary> listBeneficiary(String memappid);

	Tbappbeneficiary getBeneficiary(Integer id);

	List<Tbapppersonalreference> listPersonalReference(String memappid);

	String saveOrUpdateReference(Tbapppersonalreference personalreference);

	Tbapppersonalreference getPersonalreference(Integer id);

	String saveOrUpdateDOSRIStatus(Tbappdosri dosrichecking);

	String saveMembership(Tbmembershipapp appid);

	Tbappdosri getMemberDosri(String appid);

	String saveApplicationHeaderdetails(String appid, String branch, String source, String company, String group,
			String memberclass, String ao, String agent);

	// Recently Added

	Tblstapp getLstapp(String appno);

	Tbmemberemployment getComakerInfo(String memid);

	String addComaker(String appno, String memid);

	List<Tblstexistingloansother> getExistingLoansOthers(String memid);

	String addCreditCardInfo(Tblstcreditcardinfo credcard);

	List<Tbappcreditcardinfo> getCreditCardInfo(String membershipappid);

	List<Tblstbankaccounts> getDepositWithOtherBanks(String membershipID);

	String addDepositInfo(Tblstbankaccounts accounts);

	PersonalDetails getAndAddMemberAs(String relationship, String membershipid, String applicantid);

	String dedupeComaker(String memid);

	String addLoanInfoExistingLoans(Tblstexistingloansother exist);

	String doneEncoding(Tblstapp app);

	// New (DAN)
	String saveOrUpdateCreditCardApp(Tbappcreditcardinfo creditcard);

	List<Tbappcreditcardinfo> getApplicantsCreditCard(String membershipappid);

	Tbappcreditcardinfo getAppCreditCard(Integer id);

	List<Tbloanproduct> getListLoanProduct();

	String saveAsDraft(String appno, String loanpurpose, Date appdate);

	String saveOfficer(String appno);

	String saveSourceIncome(Tblstsourceincome source);

	Tblstsourceincome getSourceIncome(Integer id);

	Tblstsourceincome getLstSourceIncome(String appno, String participation);

	List<Tbdeposit> getDeposits(String appno);

	String returnApplication(String membershipappid, String returnremarks);

	String addLSTCreditCard(Tblstcreditcardinfo card);

	List<Tblstcreditcardinfo> getLSTCredCardInfo(String appno);

	Tbpledge getCapconSavings(String memid);

	String updateDepositInfo(Tblstbankaccounts acct);

	String deleteDepInfo(Integer id);

	String updateExistingLoanOth(Tblstexistingloansother other);

	Tblstexistingloansother getOtherExistingLoan(Integer id);

	AccountInfoForm getAccountInfo(String appno);

	List<Tbbanks> listBanks();

	String updateAccountInfo(CollectionInstructionsForm collect, String type);

	List<LoanFeesForm> getListLoanFeesperapp(String appno);

	List<LoanPayoutForm> getLoanPayout(String appno);

	List<LoanReleaseInstForm> getLoanReleaseInst(String appno);

	CompanyForm getCoopNameByCoopcode(String coopcode);

	String saveLoanRelInstruction(Tbloanreleaseinst relInst);

	String updateRelInst(LoanReleaseInstForm form);

	Tbloanreleaseinst getLoanRelease(int id);

	String deletePDC(Integer id);

	RemarksForm getRemarksDetails(String appid);

	String saveAccountSecurity(String appno, String seccode);

	String saveOrUpdateAO(Tbaccountofficer d, String saveOrUpdate);

	List<Tbaccountofficer> listAO();

	List<Tbmemberrelationship> listMemberRelationshipPerAppID(String appid);

	String saveOrUpdateMemberRelationship(Tbmemberrelationship relative);

	String deleteAO(String aocode, String companycode);

	List<Tbmemberrelationship> listMemberRelationshipPerMemberID(String memberid);

	Tbreferror getReferrorPerParam(String appid, String memberid);

	String addReferrorPerParam(Tbreferror r);

	BigDecimal getCapconAccount(String appno, String type);

	Tbcifindividual getIndividual(String cifno);

	Tbcifmain getDetails(String cifno);

	String updateCIFMain(Tbcifmain main, String filepath);

	String deleteItem(Integer dependentsID, Integer employmentID, Integer businessID, Integer otherid, Integer bankid);

	List<Tbcifdependents> getListDependents(String cifno);

	String saveDependents(Tbcifdependents dependents);

	String updateIndividualCIF(Tbcifindividual indiv);

	FormValidation validateDataEntry(String cifno);

	String saveOrUpdateBusiness(Tbcifbusiness bus);

	String saveOrUpdateEmployment(Tbcifemployment emp);

	Tbcifcorporate getCorporate(String cifno);

	List<Tbcifbusiness> getListBusiness(String cifno);

	List<Tbcifemployment> getListEmployment(String cifno);

	List<Tbtradereference> getTradeRefList(String cifno, String tradetype);

	String changeAccreditationStat(String cifno, String tradecifno, String tradetype);

//	String deleteTradeRef(String cifno, String tradecifno, String tradetype, String cif);
	Tbtradereference getTRDetails(String cifno, String tradecifno, String tradetype);
//	String saveTradeRef(Tbtradereference data);
//	String updateTradeRef(Tbtradereference data);

	String updateCorporateCIF(Tbcifcorporate corp);

	List<Tbothercontacts> listOthercontacts(String cifno);

	String addOtherContact(Tbothercontacts contacts);

	WorkflowProcessForm getWorkflowDetails(String sequenceno, String cifno);

	// 11-10-2020 MAR
	String saveOrupdateHeader(String appno, String product, String company, String branch, String purpose,
			String reason, String reftype, String refname, String rollovertype);

	// Renz
	List<Tbtradereference> listNewTradeRef(String cifno);

	String saveOrUpdateNewTradeRef(Tbtradereference ref);

	String deleteNewTradeRef(Integer id);

	String saveOrUpdateNewTradeRefSupplier(Tbtradereference ref);

	List<Tbtradereference> listNewTradeRefSupplier(String cifno);

	// MAR 03-30-2021
	Tblosmain getDetailsRB(String cifno);

	Tblosindividual getIndividualRB(String cifno);

	List<Tblosdependents> getListDependentsRB(String cifno);

	String updateRBMain(Tblosmain main);

	String saveDependentsRB(Tblstappdependents dependents);

	String updateIndividualRB(Tblstappindividual indiv);

	FormValidation validateDataEntryRB(String cifno);

	List<Tblstappbusiness> getListBusinessRB(String cifno);

	Tbcifcorporate getCorporateRB(String cifno);

	List<Tbothercontactslos> listOthercontactsRB(String cifno);

	String updateCorporateCIFRB(Tblstappcorporate corp);

	BigDecimal totalDebitCredit(String pnno, String type, String txcode);

	Boolean isPNNoValid(String pnno);

	String checkNetProceeds(String appno, BigDecimal amount);

	String saveOrUpdateBus(Tbcifbusiness d);

	String deleteDependents(Tblstappdependents d);

	FileUploadResponse uploadFile(MultipartFile file);

	String updateProfilePhoto(String cifno, String filepath);

	String changeCompanyOrMemberStatus(String cifno, String status, String remarks);

	String saveOrUpdateAmortizedDetails(Tbamortizedattachment d);

	List<Tbamortizedattachment> listAmortizedDetails(String appno);

	String deleteAmortizedDetails(Integer id);

	MembershipHeaderForm getMembershipHeader(String cifno);
	
	String saveOrUpdateNetCapping(Tbmembernetcapping d);

	Tbmembernetcapping getTbmembernetcapping(String appno);
	
	Tbcifemployment getTbcifemploymentTop1(String cifno);
	
	String changeCompanyOrMemberBatchUpdateStatus(List<MembershipListPerStagesForm> memberList, String status);

	String saveUpdateCapitalPledge(CapitalPledge form);
	
	CapitalPledge getCapitalPledge(String cifno);
}
