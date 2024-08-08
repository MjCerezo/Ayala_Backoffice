package com.etel.creditfacility;

import java.util.List;

import com.etel.company.forms.CompanyForm;
import com.etel.creditfacility.forms.CorpSubsidiaryForm;
import com.etel.creditfacility.forms.ExistingFacilityForm;
import com.etel.creditfacility.forms.LineAvailmentForm;
import com.etel.creditfacility.forms.LoanProdPerCFForm;
import com.etel.forms.ReturnForm;
import com.etel.inquiry.forms.DedupeCIFForm;
import com.loansdb.data.Tbaccountinfo;
import com.loansdb.data.Tbcfcoobligor;
import com.loansdb.data.Tbcfcovenants;
import com.loansdb.data.Tbcfdetails;
import com.loansdb.data.Tbcftermconditions;
import com.loansdb.data.Tbcovenants;

public interface CreditFacilityService {

	ReturnForm saveOrUpdateCreditFacility(Tbcfdetails cfdetails, List<CompanyForm> company);

	List<Tbcfdetails> getListOfCreditFacilityByAppNo(String appno);

	String saveOrUpdateCoObligor(Tbcfcoobligor cf);

	List<Tbcfcoobligor> getCoObligorList(String appno, String cfrefno, Integer cflevel, String cfseqno, String cfsubseqno, String cfrefnoconcat);

	List<CorpSubsidiaryForm> getSubsidiaries(String maincifno, String searchcifno, String searchcorporatename);

	String validateCoObligor(String cfrefnoconcat, String cfcifno, String cfappno);

	List<Tbcfdetails> getCFByAppnoCfRefnoCfLevelCfSeqno(String appno, String cfrefno, Integer cflevel, String cfseqno, String cfsubseqno);

	ReturnForm addCreditFacility(Tbcfdetails cfdetails, String maincfrefno, Integer maincflevel, String maincfseqno, String maincfsubseqno, List<CompanyForm> company);

	Tbcfdetails getCreditFacilityDetails(String appno, String cfrefno, Integer cflevel, String cfseqno,
			String cfsubseqno, String cfrefnoconcat);

	String deleteCreditFacility(String appno, String cfrefno, Integer cflevel, String cfseqno, String cfsubseqno);

	String saveOrUpdateCovenants(Tbcfcovenants cfcovenants);

	List<Tbcfcovenants> getListOfCovenants(String appno, String cfrefno, Integer cflevel, String cfseqno,
			String cfsubseqno, String cfrefnoconcat);

	String deleteCoobligor(Integer id, String appno, String cfrefno, Integer cflevel, String cfseqno,
			String cfsubseqno, Boolean isfromfrontend);

	List<Tbcovenants> getDefaultCovenants(String str);

	String addDefaultCovenants(List<Tbcovenants> defaultcovenants, String appno, String cfrefno, Integer cflevel, String cfseqno, String cfsubseqno, String cfrefnoconcat);

	String deleteCovenants(Integer id);

	String saveOrUpdateCFTermsCondition(Tbcftermconditions termcondtition);

	List<Tbcftermconditions> getListOfCFTermsCondition(String appno, String cfrefno, Integer cflevel, String cfseqno,
			String cfsubseqno, String cfrefnoconcat);

	String deleteCFTermsCondition(Integer id);

	List<String> getDefaultTermsCondition(String c1, String c2, String c3, String facilitycode);

	List<LoanProdPerCFForm> getListOfLoanProdPerCF(String prodcode, String facilitycode);

	String addDefaultTermsCondition(List<String> termcondition, String appno, String cfrefno, Integer cflevel,
			String cfseqno, String cfsubseqno, String cfrefnoconcat);

	List<Tbcfdetails> findCFbyCIFNo(String cifno);
	
	//AVAILMENT ABBY
	
	List<Tbcfdetails> getCfDetailsByCifNoAndCfLevel(String cifno, Integer cflevel, Boolean isexpired);

	List<Tbaccountinfo> getListOfAvailmentsByStatus(String cfrefnoconcat, Integer applicationtype, String txstat, String cifno);

	String createNewLineAvailment(LineAvailmentForm form);

	List<ExistingFacilityForm> searchExistingFacilityByCifNo(String cifno);

	String replicateCreditFacility(String appno, String cfrefno, String newappno);

	String saveCFCompany(List<CompanyForm> company, String appno, String cfrefno, Integer cflevel, String cfseqno,
			String cfsubseqno, String cfrefnoconcat);

	List<CompanyForm> getCFCompanyList(String cfrefnoconcat);
	
	//MAR

}
