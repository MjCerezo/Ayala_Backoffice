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

public interface QDEService {
	String createApplication(QDEParameters params);

	DedupeResult dedupeResults(QDEParameters params);

	QDEParameterForm searchMember(String membershipid, String firstname, String lastname, String empid, String memberOrEmployee);

	Tbmember getMember(String membershipid);

	List<Tblstapp> getLoanApplications(String empID);

	String setupNewApplication(QDEParameterForm form, List<Tbloans> loans);

	 public List<Tbmemberdependents> getMemberDependents(String memid);

	List<Tbmemberemployment> getMemberEmployment(String memid);

	List<Tbmemberbusiness> getMemberBusiness(String memid);

	String getMemStat(String codevalue);

	String getChapter(String chaptercode);

	List<Tblstcomakers> getMemberComakers(String appno);

	String deleteComaker(String appno, String memberid);

	Tbmemberdosri getDosriDetails(String memappid);

	String returnLoanApplication(String appno);

	LoansForm getLoansbyMemberID(String memid);

	String getBranchName(String branchcode);

	List<Tbmemberfinancialinfo> getMemberFinancialInfo(String membershipid, String financialtype);

	List<Tbmembercreditcardinfo> getMemberCreditCardInfo(String membershipid);

	String setupNewCIF(QDEParameterForm form, String ciftype);
	
	//MAR 10-13-2020
	List<TblstappForm> listLstapp(String cifno);
	
	List<TblstappForm> listSpsLstapp(String cifno);
	Tbcifcorporate getCIFCorp(String cifno);
	Tbcifindividual getCIFIndiv(String cifno);
	Tbcifmain getCIFMain(String cifno);
	String getSpsNameforDedupe(String cifno);
	QDEParameterForm searchCIF(String cifno, String fname, String lname, String corporatename, String custType);
	FormValidation setupNewApplicationLOS(QDEParameterForm form);
	//MAR 10-23-2020
	String setupNewCIFRB(QDEParameterForm form, String ciftype);
	//MAR 10-25-2020
	FormValidation setupNewApplicationLOSNotExisting(QDEParameterForm form);
	
	//MAr 10-28-2020
	FormValidation generateCreditLine(QDEParameterForm form);

	//Ced 11-16-2020
	String setupApprovedCIF(QDEParameterForm form, String ciftype, String cifstatus, Boolean isencoding);

}
