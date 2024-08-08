package com.etel.blacklistinquiry;

import java.util.ArrayList;
import java.util.List;

import com.coopdb.data.Tbblacklistcorporate;
import com.coopdb.data.Tbblacklistindividual;
import com.coopdb.data.Tbblacklistmain;
import com.coopdb.data.Tbblacklistrequest;
import com.coopdb.data.Tbcountry;
import com.coopdb.data.Tbmember;
import com.etel.blacklist.forms.BlacklistApprovalForm;
import com.etel.blacklist.forms.BlacklistForm;
import com.etel.blacklist.forms.BlacklistInquiryForm;
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
public class BlacklistInquiryFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public BlacklistInquiryFacade() {
       super(INFO);
    }

    public String sampleJavaOperation() {
       String result  = null;
       try {
          log(INFO, "Starting sample operation");
          result = "Hello World";
          log(INFO, "Returning " + result);
       } catch(Exception e) {
          log(ERROR, "The sample java service operation has failed", e);
       }
       return result;
    }
    
    public List <Tbblacklistmain> searchBlacklist(BlacklistInquiryForm form){
    	List<Tbblacklistmain> list = new ArrayList<Tbblacklistmain>();
		BlacklistInquiryService service = new BlacklistInquiryServiceImpl();
		list = service.searchBlacklist(form);
		return list;
	}
    
    
    public String addBlacklistRequest(BlacklistForm form){
    	BlacklistInquiryService service = new BlacklistInquiryServiceImpl();
		String flag = service.addBlacklistRequest(form);
		return flag;
    }
    
    public String addBlacklistIndiv(BlacklistForm form){
    	BlacklistInquiryService service = new BlacklistInquiryServiceImpl();
		String flag = service.addBlacklistIndiv(form);
		return flag;

    }
    
    public String addBlacklistCorp(BlacklistForm form){
    	BlacklistInquiryService service = new BlacklistInquiryServiceImpl();
		String flag = service.addBlacklistCorp(form);
		return flag;

    }
    
    public String updateBlacklistIndiv(BlacklistForm form){
    	BlacklistInquiryService service = new BlacklistInquiryServiceImpl();
		String flag = service.updateBlacklistIndiv(form);
		return flag;

    }
    
    public String updateBlacklistCorp(BlacklistForm form){
    	BlacklistInquiryService service = new BlacklistInquiryServiceImpl();
		String flag = service.updateBlacklistCorp(form);
		return flag;
    }

 public String updateNewBlacklistMain(BlacklistForm form){
    	BlacklistInquiryService service = new BlacklistInquiryServiceImpl();
		String flag = service.updateNewBlacklistMain(form);
		return flag;
    	
    }
    
    public String searchCIF(String cifno){
    	BlacklistInquiryService service = new BlacklistInquiryServiceImpl();
		String flag = service.searchCIF(cifno);
		return flag;
    }
    
   public String checkCIF(String cifno, String customertype){
	   BlacklistInquiryService service = new BlacklistInquiryServiceImpl();
		String flag = service.checkCIF(cifno, customertype);
		return flag;
   }
   
   public String seachIndivCIF(String cifno){
	   BlacklistInquiryService service = new BlacklistInquiryServiceImpl();
		String flag = service.seachIndivCIF(cifno);
		return flag;
   }
  
   public String seachCorpCIF(String cifno){
	   BlacklistInquiryService service = new BlacklistInquiryServiceImpl();
		String flag = service.seachCorpCIF(cifno);
		return flag;
   }
    
    public List<Tbcountry> getAllCountry(){
    	List<Tbcountry> list = new ArrayList<Tbcountry>();
    	BlacklistInquiryService service = new BlacklistInquiryServiceImpl();
    	list = service.getAllCountry();
		return list;
    }
    
    public List<Tbblacklistrequest> searchRequestByStatus(BlacklistApprovalForm form, String requestid){
    	List<Tbblacklistrequest> list = new ArrayList<Tbblacklistrequest>();
		BlacklistInquiryService service = new BlacklistInquiryServiceImpl();
		list = service.searchRequestByStatus(form, requestid);
		return list;
    	
    }
    
    public Tbblacklistrequest getRequestRecord(Integer requestid){
    	Tbblacklistrequest list = new Tbblacklistrequest() ;
		BlacklistInquiryService service = new BlacklistInquiryServiceImpl();
		list = service.getRequestRecord(requestid);
		return list;
    }
    
    public String updateBlacklistRequestStatus(Integer requestid, String requeststatus){
    	BlacklistInquiryService service = new BlacklistInquiryServiceImpl();
		String flag = service.updateBlacklistRequestStatus(requestid, requeststatus);
		return flag;
    }
    
    public String saveNewBlacklistMain(BlacklistForm form){
    	BlacklistInquiryService service = new BlacklistInquiryServiceImpl();
		String flag = service.saveNewBlacklistMain(form);
		return flag;
    	
    }
    
    public Tbblacklistindividual getIndividualRecord(String blacklistid){
    	Tbblacklistindividual list = new Tbblacklistindividual() ;
		BlacklistInquiryService service = new BlacklistInquiryServiceImpl();
		list = service.getIndividualRecord(blacklistid);
		return list;
    }
//    public Tbblacklistcorporate getCorporateRecord(String blacklistid){
//    	Tbblacklistcorporate list = new Tbblacklistcorporate() ;
//		BlacklistInquiryService service = new BlacklistInquiryServiceImpl();
//		list = service.getCorporateRecord(blacklistid);
//		return list;
//    }
    
    public Tbmember getCIFIndivRecord(String cifno){
    	Tbmember list = new Tbmember() ;
		BlacklistInquiryService service = new BlacklistInquiryServiceImpl();
		list = service.getCIFIndivRecord(cifno);
		return list;
	}
    
    
//    public Tbcifcorporate getCIFCorpRecord(String cifno){
//    	Tbcifcorporate list = new Tbcifcorporate() ;
//		BlacklistInquiryService service = new BlacklistInquiryServiceImpl();
//		list = service.getCIFCorpRecord(cifno);
//		return list;
//	}
     
    public String getUser(){
    String user = null;
	BlacklistInquiryService service = new BlacklistInquiryServiceImpl();
	user = service.getUser();
	return user;
    }
    
    public String updateBlacklistRequestDetails(BlacklistForm form, String requestid){
    	BlacklistInquiryService service = new BlacklistInquiryServiceImpl();
		String flag = service.updateBlacklistRequestDetails(form, requestid);
		return flag;
    }
   }
