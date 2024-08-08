package com.etel.documentation;

import java.util.List;

import com.etel.documentation.forms.DocAccessRightsForm;
//import com.coopdb.data.Tbcfinsurancedetails;
import com.coopdb.data.Tbdocsperapplication;
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
public class DocumentationFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
	private DocumentationService srvc = new DocumentationServiceImpl();
    public DocumentationFacade() {
       //super(INFO);
    }
//    public String saveOrUpdateCFInsurance(Tbcfinsurancedetails insurance){
//    	return srvc.saveOrUpdateCFInsurance(insurance);
//    }
//    public String deleteCFInsurance(String appno, Integer id){
//    	return srvc.deleteCFInsurance(appno, id);
//    }
//    public List<Tbcfinsurancedetails> getListOfCFInsurance(String appno){
//    	return srvc.getListOfCFInsurance(appno);
//    }
//    public String refreshDocChecklistPerApp(String appno, String cifno){
//    	return srvc.refreshDocChecklistPerApp(appno, cifno);
//    }
    public List<Tbdocsperapplication> getListOfDocsPerApp(String appno){
    	return srvc.getListOfDocsPerApp(appno);
    }
    public String saveOrUpdateDocsPerApp(Tbdocsperapplication docsperapp){
    	return srvc.saveOrUpdateDocsPerApp(docsperapp);
    }
    
    public String getExistingDocumentsDetailsfromCIF(String cifno, String appno){
    	return srvc.getExistingDocumentsDetailsfromCIF(cifno, appno);
    }
    public DocAccessRightsForm getDocAccessRights(String appno){
		return srvc.getDocAccessRights(appno);
	}
    
    public String updateAssignedDocAnalyst(String appno, String assigneddocanalyst){
		return srvc.updateAssignedDocAnalyst(appno, assigneddocanalyst);
	}
    
    public String updateDocumentationStatus(String appno, Integer status){
		return srvc.updateDocumentationStatus(appno, status);
	}
    public List<Tbdocsperapplication> getDocsperApp(String appno){
    	return srvc.getDocsperApp(appno);
    }
    
    public String generateAndSavePNNo(String appno){
    	return srvc.generateAndSavePNNo(appno);
    }
    public String bookToLMS(String appno, String status){
    	return srvc.bookToLMS(appno, status);
    }
    public String updateAccountInfoStatus(String appno, String status){
    	return srvc.updateAccountInfoStatus(appno, status);
    }
    public String checkLoanRelease (String appno){
    	return srvc.checkLoanRelease(appno);
    }
}
