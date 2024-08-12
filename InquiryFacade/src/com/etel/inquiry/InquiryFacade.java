package com.etel.inquiry;


import java.util.ArrayList;
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
public class InquiryFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
	private InquiryService service = new InquiryServiceImpl();
	
	public List<Tbmember> searchMember(String memberid, String membername, Integer page, Integer maxresult){
		return service.searchMember(memberid, membername, page, maxresult);
	}
	
	public Tbmember getMember(String memberid){
		return service.getMember(memberid);
	}
	
	public Integer countSearchedMember(String membername, String memberid){
		return service.countSearchedMember(membername, memberid);
	}
	public List<InquiryForm> applicationInquiry (String appno,String memberid, String fname, String lname, 
    		String applicationstatus, String loanproduct, String corporatename, Integer page, Integer maxresult,String customertype,Integer applicationtype){
    	return service.applicationInquiry(appno, memberid, fname, lname, applicationstatus,loanproduct ,corporatename, page, maxresult,customertype,applicationtype);
    }
    public int applicationInquiryCount (String appno,String memberid, String fname, String lname, 
    		String applicationstatus, String loanproduct, String corporatename, Integer page, Integer maxresult,String customertype,Integer applicationtype){
    	return service.applicationInquiryCount(appno, memberid, fname, lname, applicationstatus,loanproduct ,corporatename, page, maxresult,customertype,applicationtype);
    }
    public List<Tbmemberemployment> listEmployment(String membershipid){
    	return service.listEmployment(membershipid);
    }
    public List<Tbmemberbusiness> listBusiness(String membershipid){
    	return service.listBusiness(membershipid);
    }
    
    public List<Tblstapp> searchLoanApplications(String appno, String memberid, String appstatus, String firstname, String lastname, Integer page, Integer maxresult, String loanproduct){
    	return service.searchLoanApplications(appno, memberid, appstatus, firstname, lastname, page, maxresult, loanproduct);
    }
    public List<Tbloans> getListofLoanAccounts(String name, String accountno, String refno) {
        InquiryService inqSrvc = new InquiryServiceImpl();
        List<Tbloans> listOfLoanAccounts = new ArrayList<Tbloans>();
        try {
           log(INFO, "Starting getListofLoanAccounts");
           listOfLoanAccounts = inqSrvc.getListofLoanAccounts(name,accountno,refno);
        } catch(Exception e) {
           log(ERROR, "The getListofLoanAccounts service operation has failed", e);
        }
        return listOfLoanAccounts;
     }
    
    public List<Tblntxjrnl> findLedgerByAccountno(String accountno) {
        InquiryService inqSrvc = new InquiryServiceImpl();
        List<Tblntxjrnl> listOfTx = new ArrayList<Tblntxjrnl>();
        try {
           log(INFO, "Starting findLedgerByAccountno");
           listOfTx = inqSrvc.findLedgerByAccountno(accountno);
        } catch(Exception e) {
           log(ERROR, "The findLedgerByAccountno service operation has failed", e);
        }
        return listOfTx ;
     }

    public List<Tbpaysched> findPayschedByAccountno(String accountno) {
        InquiryService inqSrvc = new InquiryServiceImpl();
        List<Tbpaysched> listOfPsched = new ArrayList<Tbpaysched>();
        try {
           log(INFO, "Starting findPayschedByAccountno");
           listOfPsched = inqSrvc.findPayschedByAccountno(accountno);
        } catch(Exception e) {
           log(ERROR, "The findPayschedByAccountno service operation has failed", e);
        }
        return listOfPsched;
     }
    public List<Tbmember> searchMemberBills(String memberid, String membername, Integer page, Integer maxresult){
		return service.searchMemberBills(memberid, membername, page, maxresult);
	}
    public Integer countSearchedMemberBills(String membername, String memberid){
		return service.countSearchedMemberBills(membername, memberid);
	}
	public List<NonMemberInquiryForm> ReceivableForm (List<NonMemberInquiryForm> r){
		return r;
	}
    public List<NonMemberInquiryForm> listNonMember (String name){
    	return service.listNonMember(name);
    }
    
    public List<Tbmembershipapp> searchMembershipApplication(MembershipApplicationForm srch, Integer page, Integer maxresult){
    	return service.searchMembershipApplication(srch, page, maxresult);
    }
    
    public Integer searchMembershipApplicationCount(MembershipApplicationForm srch, Integer page, Integer maxresult) {
    	return service.searchMembershipApplicationCount(srch, page, maxresult);
    }
    
    //Renz
    public List<CIFInquiryForm> searchCIF(String customertype, String cifnumber, String cifstatus, String fullname, String ciftype,
			Date birthdate , Date encodeddate, Date dateofincorporation, Integer page, Integer maxResult,String lname, String fname, String mname, String fulladdress ) {
    	return service.searchCIF(customertype, cifnumber, cifstatus, fullname, ciftype, birthdate, encodeddate, dateofincorporation, page, maxResult,lname,fname,mname, fulladdress);
    }
    
    public Integer getCifTotalResult(String customertype, String cifnumber, String cifstatus, String fullname, String ciftype,
			Date birthdate , Date encodeddate, Date dateofincorporation, String lname, String fname, String mname, String fulladdress) {
    	return service.getCifTotalResult(customertype, cifnumber, cifstatus, fullname, ciftype, birthdate, encodeddate, dateofincorporation, lname,fname, mname, fulladdress);
    }
    
    //MAR
    public List<DedupeCIFForm> dedupeCIF (String cifno, String lname, String fname, String mname,
    		String nameCorpSole, Integer page, Integer maxresult, String customertype){
    	return service.dedupeCIF(cifno, lname, fname, mname, nameCorpSole, page, maxresult, customertype);
    }
    public int dedupeCIFCount (String cifno, String lname, String fname, String mname,
    		String nameCorpSole, String customertype){
    	return service.dedupeCIFCount(cifno, lname, fname,mname, nameCorpSole, customertype);
    }
    
    //Renz
    public List<CIFInquiryForm> searchCIFRB(String customertype, String cifnumber, String cifstatus, String fullname, String ciftype,
			Date birthdate , Date encodeddate, Date dateofincorporation, Integer page, Integer maxResult,String lname, String fname, String mname, String fulladdress ) {
    	return service.searchCIFRB(customertype, cifnumber, cifstatus, fullname, ciftype, birthdate, encodeddate, dateofincorporation, page, maxResult,lname,fname,mname, fulladdress);
    }
    
    //MAR 11-09-2020
    public List<InquiryForm> applicationInquiry2 (String appno,String cifno,String loanproduct, String fname, String lname, 
    		String corporatename, Integer page, Integer maxresult,String customertype,String isExisting){
    	return service.applicationInquiry2(appno, cifno, loanproduct, fname, lname ,corporatename, page, maxresult, customertype, isExisting);
    }
    public int applicationInquiry2Count (String appno, String cifno, String loanproduct, String fname, String lname, 
	String corporatename, Integer page, Integer maxresult,String customertype,String isExisting){
    	return service.applicationInquiry2Count(appno, cifno, loanproduct, fname, lname, corporatename, customertype,isExisting);
    }
    
    //
	public List<CompanyListPerStagesForm> companyInquiry(String branch, String search, String applicationStatus, String dateEncode) {
		return service.companyInquiry(branch, search, applicationStatus, dateEncode);
	}
	
	public List<CompanyListPerStagesForm> companyListPerStages(String branch, String search, String applicationStatus, String daysCount) {
		return service.companyListPerStages(branch, search, applicationStatus, daysCount);
	}
	
	public List<MembershipListPerStagesForm> membershipInquiry(String branch, String search, String applicationStatus, String dateEncode) {
		return service.membershipInquiry(branch, search, applicationStatus, dateEncode);
	}
	
	public List<MembershipListPerStagesForm> membershipListPerStages(String branch, String search, String applicationStatus, String daysCount) {
		return service.membershipListPerStages(branch, search, applicationStatus, daysCount);
	}
}
