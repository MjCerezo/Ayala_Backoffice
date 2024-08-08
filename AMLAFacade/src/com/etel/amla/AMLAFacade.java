package com.etel.amla;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.coopdb.data.Tbamlaindividual;
import com.coopdb.data.Tbamlalistmain;
import com.coopdb.data.Tbamlanoncountries;
import com.coopdb.data.Tbamlarequest;
import com.coopdb.data.Tbcountry;
import com.etel.amla.forms.AMLANonCountriesForm;
import com.etel.amla.forms.AMLApprovalForm;
import com.etel.amla.forms.AmlaForm;
import com.etel.amla.forms.AmlaInquiryForm;
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
public class AMLAFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public AMLAFacade() {
       super(INFO);
    }
    // Search existing AMLA record
    public List <Tbamlalistmain> searchAmla(AmlaInquiryForm form){
    	List<Tbamlalistmain> list = new ArrayList<Tbamlalistmain>();
		AmlaInquiryService service = new AmlaInquiryServiceImpl();
		list = service.searchAmla(form);
		return list;
	}
    
    // Add new AMLA REQUEST - Save to Tbamlarequest table
    public String addAmlaRequest(AmlaForm form){
    	AmlaInquiryService service = new AmlaInquiryServiceImpl();
		String flag = service.addAmlaRequest(form);
		return flag;
    }
    
    //Save approved amla record to TBamlaindividual
    public String addAmlaIndiv(AmlaForm form){
    	AmlaInquiryService service = new AmlaInquiryServiceImpl();
		String flag = service.addAmlaIndiv(form);
		return flag;

    }
    
  //Save approved amla record to TBamlacorporate
    public String addAmlaCorp(AmlaForm form){
    	AmlaInquiryService service = new AmlaInquiryServiceImpl();
		String flag = service.addAmlaCorp(form);
		return flag;

    }
    
    public String updateNewAmlaMain(AmlaForm form){
    	AmlaInquiryService service = new AmlaInquiryServiceImpl();
    	String flag = service.updateNewAmlaMain(form);
    	return flag;
    	
    }
    
    //Update approved amla record to TBamlaindividual
    public String updateAmlaIndiv(AmlaForm form){
    	AmlaInquiryService service = new AmlaInquiryServiceImpl();
		String flag = service.updateAmlaIndiv(form);
		return flag;

    }
    
  //update approved amla record to TBamlacorporate
    public String updateAmlaCorp(AmlaForm form){
    	AmlaInquiryService service = new AmlaInquiryServiceImpl();
		String flag = service.updateAmlaCorp(form);
		return flag;

    }
    
    
    public AmlaForm searchCIF(String cifno){
    	AmlaInquiryService service = new AmlaInquiryServiceImpl();
    	AmlaForm flag = service.searchCIF(cifno);
		return flag;
    }
    
    public List<Tbcountry> getAllCountry(){
    	List<Tbcountry> list = new ArrayList<Tbcountry>();
    	AmlaInquiryService service = new AmlaInquiryServiceImpl();
    	list = service.getAllCountry();
		return list;
    }
    
    public List<Tbamlarequest> searchRequestByStatus(AMLApprovalForm form, String requestid){
    	List<Tbamlarequest> list = new ArrayList<Tbamlarequest>();
    	AmlaInquiryService service = new AmlaInquiryServiceImpl();
		list = service.searchRequestByStatus(form, requestid);
		return list;
    	
    }
    
    public String saveNewAmlaMain(AmlaForm form){
    	AmlaInquiryService service = new AmlaInquiryServiceImpl();
    	String flag = service.saveNewAmlaMain(form);
    	return flag;
    	
    }
    
    //GET AMLA REQUEST RECORD
    public Tbamlarequest getRequestRecord(Integer requestid){
    	Tbamlarequest list = new Tbamlarequest() ;
    	AmlaInquiryService service = new AmlaInquiryServiceImpl();
		list = service.getRequestRecord(requestid);
		return list;
    }
    
    public String updateAmlaRequestStatus(Integer requestid, String requeststatus){
    	AmlaInquiryService service = new AmlaInquiryServiceImpl();
		String flag = service.updateAmlaRequestStatus(requestid, requeststatus);
		return flag;
    }
    
    
    public Tbamlaindividual getIndividualRecord(String amlalistid){
    	Tbamlaindividual list = new Tbamlaindividual() ;
    	AmlaInquiryService service = new AmlaInquiryServiceImpl();
		list = service.getIndividualRecord(amlalistid);
		return list;
    }
//    public Tbamlacorporate getCorporateRecord(String amlalistid){
//    	Tbamlacorporate list = new Tbamlacorporate() ;
//    	AmlaInquiryService service = new AmlaInquiryServiceImpl();
//		list = service.getCorporateRecord(amlalistid);
//		return list;
//    }
    //AMLA NON COOPERATIVE COUNTRIES
    
    public String addAmlaNonCoopCountries(AMLANonCountriesForm form){
    	AmlaInquiryService service = new AmlaInquiryServiceImpl();
		String flag = service.addAmlaNonCoopCountries(form);
		return flag;
    }
    
    public List<Tbamlanoncountries> getAllNonCoopCountries(){
    	List<Tbamlanoncountries> list = new ArrayList<Tbamlanoncountries>();
    	AmlaInquiryService service = new AmlaInquiryServiceImpl();
		list = service.getAllNonCoopCountries();
		return list;
    }
    
    public String updateAmalNonCoopCountries(AMLANonCountriesForm form){
    	AmlaInquiryService service = new AmlaInquiryServiceImpl();
		String flag = service.updateAmalNonCoopCountries(form);
		return flag;
    }
    
    public Tbamlanoncountries getAmlaNonCoopRecord(AMLANonCountriesForm form){
    	Tbamlanoncountries amla = new Tbamlanoncountries() ;
    	AmlaInquiryService service = new AmlaInquiryServiceImpl();
		amla = service.getAmlaNonCoopRecord(form);
		return amla;
    }
    
    public String deleteAmlaNonCoop(AMLANonCountriesForm form){
    AmlaInquiryService service = new AmlaInquiryServiceImpl();
	String flag = service.deleteAmlaNonCoop(form);
	return flag;
	}
    
    public String updateDraftRequest(String middlename, String suffix, String remarks, Boolean openended, Date enddate, Date startdate, String country, String reference, String source, Integer requestid, String requeststatus){
        AmlaInquiryService service = new AmlaInquiryServiceImpl();
    	String flag = service.updateDraftRequest(middlename, suffix, remarks, openended, enddate, startdate, country, reference, source, requestid, requeststatus);
    	return flag;
    }
}
