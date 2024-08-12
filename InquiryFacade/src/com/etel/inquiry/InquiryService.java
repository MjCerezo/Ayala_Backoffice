package com.etel.inquiry;


import java.util.Date;
import java.util.List;

import com.coopdb.data.Tblntxjrnl;
import com.coopdb.data.Tbloans;
import com.coopdb.data.Tblstapp;
import com.coopdb.data.Tbmember;
import com.coopdb.data.Tbmemberbusiness;
import com.coopdb.data.Tbmemberemployment;
import com.coopdb.data.Tbmembershipapp;
import com.coopdb.data.Tbpaysched;
import com.etel.inquiry.forms.CIFInquiryForm;
import com.etel.inquiry.forms.CompanyListPerStagesForm;
import com.etel.inquiry.forms.DedupeCIFForm;
import com.etel.inquiry.forms.InquiryForm;
import com.etel.inquiry.forms.MembershipApplicationForm;
import com.etel.inquiry.forms.MembershipListPerStagesForm;
import com.etel.inquiry.forms.NonMemberInquiryForm;

public interface InquiryService {

	List<Tbmember> searchMember(String memberid, String membername, Integer page,
			Integer maxresult);

	Tbmember getMember(String memberid);

	Integer countSearchedMember(String membername, String memberid);

	List<InquiryForm> applicationInquiry(String appno, String memberid, String fname, String lname,
			String applicationstatus, String loanproduct, String corporatename, Integer page, Integer maxresult,
			String customertype, Integer applicationtype);

	int applicationInquiryCount(String appno, String memberid, String fname, String lname, String applicationstatus,
			String loanproduct, String corporatename, Integer page, Integer maxresult, String customertype,
			Integer applicationtype);

	List<Tbmemberemployment> listEmployment(String membershipid);

	List<Tbmemberbusiness> listBusiness(String membershipid);

	List<Tblstapp> searchLoanApplications(String appno, String memberid, String appstatus, String firstname,
			String lastname, Integer page, Integer maxresult, String loanproduct);

	List<Tbpaysched> findPayschedByAccountno(String accountno);

	List<Tblntxjrnl> findLedgerByAccountno(String accountno);

	List<Tbloans> getListofLoanAccounts(String name, String accountno, String refno);

	List<Tbmember> searchMemberBills(String memberid, String membername, Integer page, Integer maxresult);

	Integer countSearchedMemberBills(String membername, String memberid);

	List<NonMemberInquiryForm> listNonMember(String name);

	List<Tbmembershipapp> searchMembershipApplication(MembershipApplicationForm srch, Integer page, Integer maxresult);

	Integer searchMembershipApplicationCount(MembershipApplicationForm srch, Integer page, Integer maxresult);
	
	//renz
	List<CIFInquiryForm> searchCIF(String customertype, String cifnumber, String cifstatus, String fullname, String ciftype,
			Date birthdate , Date encodeddate, Date dateofincorporation, Integer page, Integer maxResult, String lname, String fname, String mname, String fulladdress);

	Integer getCifTotalResult(String customertype, String cifnumber, String cifstatus, String fullname, String ciftype,
			Date birthdate, Date encodeddate, Date dateofincorporation, String lname, String fname, String mname, String fulladdress);

	//MAR

	List<DedupeCIFForm> dedupeCIF(String cifno, String lname, String fname, String mname, String nameCorpSole, Integer page,
			Integer maxresult, String customertype);

	int dedupeCIFCount(String cifno, String lname, String fname, String mname, String nameCorpSole, String customertype);

	List<CIFInquiryForm> searchCIFRB(String customertype, String cifnumber, String cifstatus, String fullname, String ciftype,
			Date birthdate , Date encodeddate, Date dateofincorporation, Integer page, Integer maxResult, String lname, String fname, String mname, String fulladdress);

	//MAR 11-09-2020
	List<InquiryForm> applicationInquiry2(String appno, String cifno, String loanproduct, String fname, String lname,
			String corporatename, Integer page, Integer maxresult,
			String customertype,String isExisting);
	int applicationInquiry2Count (String appno, String cifno, String loanproduct, String fname, String lname, 
			String corporatename, String customertype, String isExisting);

	List<CompanyListPerStagesForm> companyInquiry(String branch, String search, String applicationStatus, String dateEncode);
	
	List<CompanyListPerStagesForm> companyListPerStages(String branch, String search, String applicationStatus, String daysCount);

	List<MembershipListPerStagesForm> membershipInquiry(String branch, String search, String applicationStatus, String dateEncode);
	
	List<MembershipListPerStagesForm> membershipListPerStages(String branch, String search, String applicationStatus, String daysCount);

}
