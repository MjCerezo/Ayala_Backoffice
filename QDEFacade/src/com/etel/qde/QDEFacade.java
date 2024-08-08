package com.etel.qde;

import java.util.List;

import com.cifsdb.data.Tbcifcorporate;
import com.cifsdb.data.Tbcifindividual;
import com.cifsdb.data.Tbcifmain;
import com.coopdb.data.Tbloans;
import com.coopdb.data.Tblstapp;
import com.coopdb.data.Tblstcomakers;
import com.coopdb.data.Tbmember;
import com.coopdb.data.Tbmemberbusiness;
import com.coopdb.data.Tbmembercreditcardinfo;
import com.coopdb.data.Tbmemberdependents;
import com.coopdb.data.Tbmemberdosri;
import com.coopdb.data.Tbmemberemployment;
import com.coopdb.data.Tbmemberfinancialinfo;
import com.etel.forms.FormValidation;
import com.etel.forms.TblstappForm;
import com.etel.qdeforms.LoansForm;
import com.etel.qdeforms.QDEParameterForm;
import com.etel.utils.ApplicationNoGenerator;
//import com.sun.glass.ui.Application;
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
public class QDEFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level;
	 * recommend changing this to FATAL or ERROR before deploying. For info on these
	 * levels, look for tomcat/log4j documentation
	 */
	public QDEFacade() {
		// super(INFO);
	}

	private QDEService service = new QDEServiceImpl();

	public String generateLO() {
		String no = ApplicationNoGenerator.generateApplicationNo("LO");
		return no;
	}

	public String generateLI() {
		String no = ApplicationNoGenerator.generateApplicationNo("LI");
		return no;
	}

	public String createApplication(QDEParameters qdeparams) {
		return service.createApplication(qdeparams);
	}

	public DedupeResult dedupeResults(QDEParameters qdeparams) {
		return service.dedupeResults(qdeparams);
	}

	public QDEParameterForm searchMember(String membershipid, String firstname, String lastname, String empid,
			String memberOrEmployee) {
		return service.searchMember(membershipid, firstname, lastname, empid, memberOrEmployee);
	}

	public Tbmember getMember(String membershipid) {
		return service.getMember(membershipid);
	}

	public List<Tblstapp> getLoanApplications(String EmpID) {
		return service.getLoanApplications(EmpID);
	}

	public String setupNewApplication(QDEParameterForm form, List<Tbloans> loans) {
		return service.setupNewApplication(form, loans);
	}

	public FormValidation setupNewApplicationLOS(QDEParameterForm form) {
		return service.setupNewApplicationLOS(form);
	}

	public List<Tbmemberdependents> getMemberDependents(String memid) {
		return service.getMemberDependents(memid);
	}

	public List<Tbmemberemployment> getMemberEmployment(String memid) {
		return service.getMemberEmployment(memid);
	}

	public List<Tbmemberbusiness> getMemberBusiness(String memid) {
		return service.getMemberBusiness(memid);
	}

	public String getMemStat(String codevalue) {
		return service.getMemStat(codevalue);
	}

	public String getChapter(String chaptercode) {
		return service.getChapter(chaptercode);
	}

	public List<Tblstcomakers> getMemberComakers(String appno) {
		return service.getMemberComakers(appno);
	}

	public String deleteComaker(String appno, String memberid) {
		return service.deleteComaker(appno, memberid);
	}

	public Tbmemberdosri getDosriDetails(String memappid) {
		return service.getDosriDetails(memappid);
	}

	public String returnLoanApplication(String appno) {
		return service.returnLoanApplication(appno);
	}

	public LoansForm getLoansbyMemberID(String memid) {
		return service.getLoansbyMemberID(memid);
	}

	public String getBranchName(String branchcode) {
		return service.getBranchName(branchcode);
	}

	public List<Tbmemberfinancialinfo> getMemberFinancialInfo(String membershipid, String financialtype) {
		return service.getMemberFinancialInfo(membershipid, financialtype);
	}

	public List<Tbmembercreditcardinfo> getMemberCreditCardInfo(String membershipid) {
		return service.getMemberCreditCardInfo(membershipid);
	}

	public String setupNewCIF(QDEParameterForm form, String ciftype) {
		return service.setupNewCIF(form, ciftype);
	}

	// MAR 10-13-2020
	public List<TblstappForm> listLstapp(String cifno) {
		return service.listLstapp(cifno);
	}

	// MAR 10-13-2020
	public List<TblstappForm> listSpsLstapp(String cifno) {
		return service.listSpsLstapp(cifno);
	}

	public Tbcifcorporate getCIFCorp(String cifno) {
		return service.getCIFCorp(cifno);
	}

	public Tbcifindividual getCIFIndiv(String cifno) {
		return service.getCIFIndiv(cifno);
	}

	public Tbcifmain getCIFMain(String cifno) {
		return service.getCIFMain(cifno);
	}

	public String getSpsNameforDedupe(String cifno) {
		return service.getSpsNameforDedupe(cifno);
	}

	public QDEParameterForm searchCIF(String cifno, String fname, String lname, String corporatename, String custType) {
		return service.searchCIF(cifno, fname, lname, corporatename, custType);
	}

	// MAR 10-23-2020
	public String setupNewCIFRB(QDEParameterForm form, String ciftype) {
		return service.setupNewCIFRB(form, ciftype);
	}

	// MAR 10-25-2020
	public FormValidation setupNewApplicationLOSNotExisting(QDEParameterForm form) {
		return service.setupNewApplicationLOSNotExisting(form);
	}

	// MAR 10-29-2020
	public FormValidation generateCreditLine(QDEParameterForm form) {
		return service.generateCreditLine(form);
	}

	// CED 11-16-2020
	public String setupApprovedCIF(QDEParameterForm form, String ciftype, String cifstatus, Boolean isencoding) {
		return service.setupApprovedCIF(form, ciftype, cifstatus, isencoding);
	}
}
