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
public class CreditFacilityFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public CreditFacilityFacade() {
       //super(INFO);
    }
    CreditFacilityService srvc = new CreditFacilityServiceImpl();
    
    
    public ReturnForm saveOrUpdateCreditFacility(Tbcfdetails cfdetails, List<CompanyForm> company){
    	return srvc.saveOrUpdateCreditFacility(cfdetails, company);
    }
    public ReturnForm addCreditFacility(Tbcfdetails cfdetails, String maincfrefno, Integer maincflevel, String maincfseqno, String maincfsubseqno, List<CompanyForm> company){
    	return srvc.addCreditFacility(cfdetails, maincfrefno, maincflevel, maincfseqno, maincfsubseqno, company);
    }
    public List<Tbcfdetails> getListOfCreditFacilityByAppNo(String appno){
        return srvc.getListOfCreditFacilityByAppNo(appno);
    }
    
    public String saveOrUpdateCoObligor(Tbcfcoobligor cf){
    	return srvc.saveOrUpdateCoObligor(cf);
    }
    
    public List<Tbcfcoobligor> getCoObligorList(String appno, String cfrefno, Integer cflevel, String cfseqno, String cfsubseqno, String cfrefnoconcat){
        return srvc.getCoObligorList(appno, cfrefno, cflevel, cfseqno, cfsubseqno, cfrefnoconcat);
    }
    
    public List<CorpSubsidiaryForm> getSubsidiaries(String maincifno, String searchcifno, String searchcorporatename){
    	return srvc.getSubsidiaries(maincifno, searchcifno, searchcorporatename);
    }
    public String validateCoObligor(String cfrefnoconcat, String cfcifno, String cfappno){
    	return srvc.validateCoObligor(cfrefnoconcat, cfcifno, cfappno);
    }
    public List<Tbcfdetails> getCFByAppnoCfRefnoCfLevelCfSeqno(String appno, String cfrefno, Integer cflevel, String cfseqno, String cfsubseqno){
        return srvc.getCFByAppnoCfRefnoCfLevelCfSeqno(appno, cfrefno, cflevel, cfseqno, cfsubseqno);
    }
    
    public Tbcfdetails getCreditFacilityDetails(String appno, String cfrefno, Integer cflevel, String cfseqno, String cfsubseqno, String cfrefnoconcat){
        return srvc.getCreditFacilityDetails(appno, cfrefno, cflevel, cfseqno, cfsubseqno, cfrefnoconcat);
    }
    public String deleteCreditFacility(String appno, String cfrefno, Integer cflevel, String cfseqno, String cfsubseqno){
        return srvc.deleteCreditFacility(appno, cfrefno, cflevel, cfseqno, cfsubseqno);
    }
    
    public String saveOrUpdateCovenants(Tbcfcovenants cfcovenants){
        return srvc.saveOrUpdateCovenants(cfcovenants);
    }
    public List<Tbcfcovenants> getListOfCovenants(String appno, String cfrefno, Integer cflevel, String cfseqno, String cfsubseqno, String cfrefnoconcat){
        return srvc.getListOfCovenants(appno, cfrefno, cflevel, cfseqno, cfsubseqno, cfrefnoconcat);
    }
    public String deleteCoobligor(Integer id, String appno, String cfrefno, Integer cflevel, String cfseqno, String cfsubseqno){
        return srvc.deleteCoobligor(id, appno, cfrefno, cflevel, cfseqno, cfsubseqno, true);
    }
    public List<Tbcovenants> getDefaultCovenants(String str){
        return srvc.getDefaultCovenants(str);
    }

	public String addDefaultCovenants(List<Tbcovenants> defaultcovenants, String appno, String cfrefno, Integer cflevel,
			String cfseqno, String cfsubseqno, String cfrefnoconcat) {
		return srvc.addDefaultCovenants(defaultcovenants, appno, cfrefno, cflevel, cfseqno, cfsubseqno, cfrefnoconcat);
	}
    public String deleteCovenants(Integer id){
        return srvc.deleteCovenants(id);
    }
    public String saveOrUpdateCFTermsCondition(Tbcftermconditions termcondtition){
        return srvc.saveOrUpdateCFTermsCondition(termcondtition);
    }
    public List<Tbcftermconditions> getListOfCFTermsCondition(String appno, String cfrefno, Integer cflevel, String cfseqno, String cfsubseqno, String cfrefnoconcat){
        return srvc.getListOfCFTermsCondition(appno, cfrefno, cflevel, cfseqno, cfsubseqno, cfrefnoconcat);
    }
    public String deleteCFTermsCondition(Integer id){
        return srvc.deleteCFTermsCondition(id);
    }
    public List<String> getDefaultTermsCondition(String c1, String c2, String c3, String facilitycode){
        return srvc.getDefaultTermsCondition(c1, c2, c3, facilitycode);
    }
    public List<LoanProdPerCFForm> getListOfLoanProdPerCF(String prodcode, String facilitycode){
        return srvc.getListOfLoanProdPerCF(prodcode, facilitycode);
    }
    public String addDefaultTermsCondition(List<String> termcondition, String appno, String cfrefno, Integer cflevel, String cfseqno, String cfsubseqno, String cfrefnoconcat){
        return srvc.addDefaultTermsCondition(termcondition, appno, cfrefno, cflevel, cfseqno, cfsubseqno, cfrefnoconcat);
    }
    public List<Tbcfdetails> findCFbyCIFNo(String cifno){
    	return srvc.findCFbyCIFNo(cifno);
    }
    
    
 //LINE AVAILMENT -ABBY
    
	public List<Tbcfdetails> getCfDetailsByCifNoAndCfLevel(String cifno, Integer cflevel, Boolean isexpired) {
		return srvc.getCfDetailsByCifNoAndCfLevel(cifno, cflevel, isexpired);
	}

	public List<Tbaccountinfo> getListOfAvailmentsByStatus(String cfrefnoconcat, Integer applicationtype,
			String txstat, String cifno) {
		return srvc.getListOfAvailmentsByStatus(cfrefnoconcat, applicationtype, txstat, cifno);
	}

	public String createNewLineAvailment(LineAvailmentForm form) {
		return srvc.createNewLineAvailment(form);
	}

	public List<ExistingFacilityForm> listExistingForm(List<ExistingFacilityForm> form) {
		return form;
	}

	// Kevin 09072018 Search Existing CF
	public List<ExistingFacilityForm> searchExistingFacilityByCifNo(String cifno) {
		return srvc.searchExistingFacilityByCifNo(cifno);
	}
	
	public String replicateCreditFacility(String appno, String cfrefno, String newappno) {
		return srvc.replicateCreditFacility(appno, cfrefno, newappno);
	}
	
	public String saveCFCompany(List<CompanyForm> company, String appno, String cfrefno, Integer cflevel,
			String cfseqno, String cfsubseqno, String cfrefnoconcat) {
		return srvc.saveCFCompany(company, appno, cfrefno, cflevel, cfseqno, cfsubseqno, cfrefnoconcat);
	}
	public List<CompanyForm> getCFCompanyList(String cfrefnoconcat) {
		return srvc.getCFCompanyList(cfrefnoconcat);
	}
	
	//MAR 10-27-2020
	 //MAR

}
