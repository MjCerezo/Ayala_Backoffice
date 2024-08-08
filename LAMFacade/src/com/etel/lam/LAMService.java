package com.etel.lam;

import java.util.List;

import com.cifsdb.data.Tbmanagement;
import com.etel.lam.forms.CFGroupExposureForm;
import com.etel.lam.forms.FinancialStatementListForm;
import com.etel.lam.forms.LAMAPAForm;
import com.etel.lam.forms.LAMDMSHistoryForm;
import com.etel.lam.forms.OtherIncomeExpenseForm;
import com.loansdb.data.Tbapaotherincomeexpense;
import com.loansdb.data.Tbcfdetails;
import com.loansdb.data.Tbcicreditcheck;
import com.loansdb.data.Tbcovenants;
import com.loansdb.data.Tbdocspercf;
import com.loansdb.data.Tbfinancialstatements;
import com.loansdb.data.Tbfsmain;
import com.loansdb.data.Tblamapa;
import com.loansdb.data.Tblamborrowerprofile;
import com.loansdb.data.Tblamcorporateprofile;
import com.loansdb.data.Tblamcovenants;
import com.loansdb.data.Tblamdocumentation;
import com.loansdb.data.Tblamloandetails;
import com.loansdb.data.Tblamothertermconditions;
import com.loansdb.data.Tblamrationalerecomm;
import com.loansdb.data.Tblamriskprofile;

public interface LAMService {
	
	// KEV

	Tbfinancialstatements getFSDetailsByAppNoAndID(String appno, Integer fsid);

	String saveOrUpdateFS(Tbfinancialstatements fs);

	FinancialStatementListForm getListOfFSDetailsByAppNo(String appno, Integer evalreportid);

	String deleteFSByAppnoAndID(String appno, Integer fsid);
	
	// Ced 8-21-18

	String saveOrUpdateLAMLoanDetails(List<Tbcfdetails> cfdetails, Integer evalreportid, String appno, String cifname);

	String saveOrUpdateRiskProfile(Tblamriskprofile riskprofile);

	String saveOrUpdateOtherTerms(Tblamothertermconditions otherTerm);

	String saveOrUpdateRationaleRecomm(Tblamrationalerecomm recomm);

	String saveOrUpdateBackground(Tblamcorporateprofile cprofile);

	String saveOrUpdateDocumentation(Tblamdocumentation allfac, Tblamdocumentation finfac, Tblamdocumentation mobfac,
			Tblamdocumentation stockfac);
	
	//Dan 8-21-18
	
	List<Tbmanagement> getShareholdersInfo(String cifno, String relationcode);

	List<Tbmanagement> getManagementTeam(String cifno, String notshareholder);

	List<Tbcicreditcheck> getCreditDealings(String cifno);

	//Kevin 08-29-2018
	Tblamriskprofile getLAMRiskProfile(String appno, Integer evalreportid);

	Tblamcorporateprofile getLAMCorporateProfile(String appno, Integer evalreportid);

	Tblamcovenants getLAMCovenants(String appno, Integer evalreportid);

	List<Tblamloandetails> getLAMLoanDetails(Integer evalreportid, String appno, String cfrefno, Integer cflevel,
			String cfseqno, String cfsubseqno, String cfrefnoconcat);

	Tblamothertermconditions getLAMOtherTermsCondition(String appno, Integer evalreportid);

	String saveOrUpdateCovenants(Tblamcovenants lamcovenants);

	String saveCFDetailsToLAM(String appno, Integer evalreportid);

	Tblamrationalerecomm getLAMRationaleRecomm(String appno, Integer evalreportid);

	String saveOrUpdateLAMBorProfile(Tblamborrowerprofile borprofile);

	Tblamborrowerprofile getLAMBorrowerProfile(String appno, Integer evalreportid);

	List<Tbdocspercf> getListOfDocsPerCF(String facilitycode, String documentcode);

	String addLAMDocuments(List<Tbdocspercf> docspercf, Integer evalreportid, String appno, String cfrefno,
			Integer cflevel, String cfseqno, String cfsubseqno, String cfrefnoconcat);

	String saveOrUpdateLAMDocumentation(Tblamdocumentation lamdocs);

	String deleteLAMDocuments(Integer id);

	Tblamdocumentation getLAMDocuments(String appno, Integer evalreportid);

	String saveOrUpdateFSMain(Tbfsmain fsmain);

	Tbfsmain getFSMain(String appno, Integer evalreportid);

	String addDefaultCovenants(List<Tbcovenants> defaultcovenants, Integer evalreportid, String appno, String cfrefno,
			Integer cflevel, String cfseqno, String cfsubseqno, String cfrefnoconcat);

	String deleteLAMCovenants(Integer id);
	
	//ABBY
	
	String saveOrUpdateOtherFunds(OtherIncomeExpenseForm form);

	List<Tbapaotherincomeexpense> getListOfIncomeExpense(Integer evalreportid, String fundtype);

	String saveOrUpdateAPA(LAMAPAForm form);

	Tblamapa getAPADetails(Integer id);

	String deleteIncomeExpense(Integer id);

	List<CFGroupExposureForm> getCFGroupExposureList(String appno);

	String saveLAMHistory(String appno);

	List<LAMDMSHistoryForm> getLAMHistoryDMS(String appno);
	

}
