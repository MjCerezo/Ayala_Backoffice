package com.etel.lam;

import java.util.ArrayList;
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
public class LAMFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
	private LAMService service = new LAMServiceImpl();
	
    public LAMFacade() {
       //super(INFO);
    }
    
    // KEV

    public String saveOrUpdateFS(Tbfinancialstatements fs){
    	return service.saveOrUpdateFS(fs);
    }
    public Tbfinancialstatements getFSDetailsByAppNoAndID(String appno, Integer fsid){
    	return service.getFSDetailsByAppNoAndID(appno, fsid);
    }
    
    public FinancialStatementListForm getListOfFSDetailsByAppNo(String appno, Integer evalreportid){
    	return service.getListOfFSDetailsByAppNo(appno, evalreportid);
    }
    
    public String deleteFSByAppnoAndID(String appno, Integer fsid){
    	return service.deleteFSByAppnoAndID(appno, fsid);
    }

    public String saveOrUpdateLAMLoanDetails(List<Tbcfdetails> cfdetails, Integer evalreportid, String appno, String cifname) {
    	return service.saveOrUpdateLAMLoanDetails(cfdetails, evalreportid, appno, cifname);
    }
	public String saveOrUpdateRiskProfile(Tblamriskprofile riskprofile) {
		return service.saveOrUpdateRiskProfile(riskprofile);
	}
	public 	String saveOrUpdateDocumentation(Tblamdocumentation allfac, Tblamdocumentation finfac, Tblamdocumentation mobfac,
			Tblamdocumentation stockfac) {
		return service.saveOrUpdateDocumentation(allfac, finfac, mobfac, stockfac);
	}

	public String saveOrUpdateOtherTerms(Tblamothertermconditions otherTerm) {
		return service.saveOrUpdateOtherTerms(otherTerm);
	}

	public String saveOrUpdateRationaleRecomm(Tblamrationalerecomm recomm) {
		return service.saveOrUpdateRationaleRecomm(recomm);
	}
	public String saveOrUpdateBackground(Tblamcorporateprofile cprofile) {
		return service.saveOrUpdateBackground(cprofile);
	}

	//Dan 8-21-18
	
	public List<Tbmanagement> getShareholdersInfo(String cifno, String relationcode){
    	return service.getShareholdersInfo(cifno, relationcode);
    }
    
    public List<Tbmanagement> getManagementTeam(String cifno, String notshareholder){
    	return service.getManagementTeam(cifno, notshareholder);
    }
    
    public List<Tbcicreditcheck> getCreditDealings(String cifno){
    	return service.getCreditDealings(cifno);
    }
    
  //Kevin 08-29-2018
    public Tblamriskprofile getLAMRiskProfile(String appno, Integer evalreportid){
    	return service.getLAMRiskProfile(appno, evalreportid);
    }
    public Tblamcorporateprofile getLAMCorporateProfile(String appno, Integer evalreportid){
    	return service.getLAMCorporateProfile(appno, evalreportid);
    }
    public Tblamcovenants getLAMCovenants(String appno, Integer evalreportid){
    	return service.getLAMCovenants(appno, evalreportid);
    }
    public List<Tblamloandetails> getLAMLoanDetails(Integer evalreportid, String appno, String cfrefno, Integer cflevel, String cfseqno, String cfsubseqno, String cfrefnoconcat){
    	return service.getLAMLoanDetails(evalreportid, appno, cfrefno, cflevel, cfseqno, cfsubseqno, cfrefnoconcat);
    }
    public Tblamothertermconditions getLAMOtherTermsCondition(String appno, Integer evalreportid){
    	return service.getLAMOtherTermsCondition(appno, evalreportid);
    }
    public String saveOrUpdateCovenants(Tblamcovenants lamcovenants){
        return service.saveOrUpdateCovenants(lamcovenants);
    }
    public String saveCFDetailsToLAM(String appno, Integer evalreportid){
    	return service.saveCFDetailsToLAM(appno, evalreportid);
    }
    public Tblamrationalerecomm getLAMRationaleRecomm(String appno, Integer evalreportid){
    	return service.getLAMRationaleRecomm(appno, evalreportid);
    }
    public String saveOrUpdateLAMBorProfile(Tblamborrowerprofile borprofile){
    	return service.saveOrUpdateLAMBorProfile(borprofile);
    }
    public Tblamborrowerprofile getLAMBorrowerProfile(String appno, Integer evalreportid){
    	return service.getLAMBorrowerProfile(appno, evalreportid);
    }
    
    public List<Tbdocspercf> getListOfDocsPerCF(String facilitycode, String documentcode){
    	return service.getListOfDocsPerCF(facilitycode, documentcode);
    }
    
	public String addLAMDocuments(List<Tbdocspercf> docspercf, Integer evalreportid, String appno, String cfrefno,
			Integer cflevel, String cfseqno, String cfsubseqno, String cfrefnoconcat) {
		return service.addLAMDocuments(docspercf, evalreportid, appno, cfrefno, cflevel, cfseqno, cfsubseqno,
				cfrefnoconcat);
	}
	public String saveOrUpdateLAMDocumentation(Tblamdocumentation lamdocs){
    	return service.saveOrUpdateLAMDocumentation(lamdocs);
    }
	public String deleteLAMDocuments(Integer id){
    	return service.deleteLAMDocuments(id);
    }
	
	public Tblamdocumentation getLAMDocuments(String appno, Integer evalreportid){
    	return service.getLAMDocuments(appno, evalreportid);
    }
	
	public String saveOrUpdateFSMain(Tbfsmain fsmain){
    	return service.saveOrUpdateFSMain(fsmain);
    }
	public Tbfsmain getFSMain(String appno, Integer evalreportid){
    	return service.getFSMain(appno, evalreportid);
    }
	public String addDefaultCovenants(List<Tbcovenants> defaultcovenants, Integer evalreportid, String appno, String cfrefno, Integer cflevel,
			String cfseqno, String cfsubseqno, String cfrefnoconcat) {
		return service.addDefaultCovenants(defaultcovenants, evalreportid, appno, cfrefno, cflevel, cfseqno, cfsubseqno, cfrefnoconcat);
	}
	public String deleteLAMCovenants(Integer id){
        return service.deleteLAMCovenants(id);
    }
	
	//ABBY 08/28 - FOR APA

	public String saveOrUpdateOtherFunds (OtherIncomeExpenseForm form){
		return service.saveOrUpdateOtherFunds(form);
	}
	public List<Tbapaotherincomeexpense> getListOfIncomeExpense(Integer evalreportid, String fundtype)  {
		return service.getListOfIncomeExpense(evalreportid, fundtype);
	}
	
	public String saveOrUpdateAPA(LAMAPAForm form){
		return service.saveOrUpdateAPA(form);
	}
	
	public Tblamapa getAPADetails (Integer id){
		return service.getAPADetails(id);
	}
	public String deleteIncomeExpense(Integer id){
		return service.deleteIncomeExpense(id);
	}
	public List<CFGroupExposureForm> getCFGroupExposureList(String appno)  {
		List<CFGroupExposureForm> list = new ArrayList<CFGroupExposureForm>();
		list =  service.getCFGroupExposureList(appno);
		return list;
	}
	public String saveLAMHistory(String appno){
		return service.saveLAMHistory(appno);
	}
	public List<LAMDMSHistoryForm> getLAMHistoryDMS(String appno){
		List<LAMDMSHistoryForm> list = new ArrayList<LAMDMSHistoryForm>();
		list =  service.getLAMHistoryDMS(appno);
		return list;
	}
}
