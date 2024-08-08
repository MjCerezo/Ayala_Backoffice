package com.etel.docmaintenance;

import java.util.List;

import com.coopdb.data.Tbcodetable;
import com.coopdb.data.Tbdocspercfapp;
import com.coopdb.data.Tbdocspertrans;
import com.coopdb.data.Tbgeneraldocs;
import com.coopdb.data.Tbtransaction;
import com.etel.docmaintenanceform.DocChecklistForm;
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
public class DocMaintenanceFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log
	 * level; recommend changing this to FATAL or ERROR before deploying. For
	 * info on these levels, look for tomcat/log4j documentation
	 */
	public DocMaintenanceFacade() {
		super(INFO);
	}

	private DocMaintenanceService srvc = new DocMaintenanceServiceImpl();

	public List<Tbgeneraldocs> getDocumentListPerDocCategory(String category) {
		return srvc.getDocumentListPerDocCategory(category);
	}
	
	public List<DocChecklistForm> getDocumentListPerDocCategoryNew(String category, String cifno, String custType) {
		return srvc.getDocumentListPerDocCategoryNew(category, cifno, custType);
	}

	public String saveDocType(Tbgeneraldocs doc) {
		return srvc.saveDocType(doc);
	}

	public boolean checkDocTypeAvailability(String doctype, String doccategory) {
		return srvc.checkDocTypeAvailability(doctype, doccategory);
	}

	public String deleteDocCategory(Tbcodetable doccat) {
		return srvc.deleteDocCategory(doccat);
	}

	public String deleteDocType(Tbgeneraldocs doc) {
		return srvc.deleteDocType(doc);
	}

	public List<Tbdocspercfapp> getDocsPerApplicationPerCF(String loanappno, String cfcode) {
		return srvc.getDocsPerApplicationPerCF(loanappno, cfcode);
	}

	public String getGenerateDocsPerAppPerCF(String loanappno, String cfcode, String cifno, String cfrefnoconcat) {
		return srvc.getGenerateDocsPerAppPerCF(loanappno, cfcode, cifno, cfrefnoconcat);
	}

	public String saveOrUpdateDocumentperCF(Tbdocspercfapp doc) {
		return srvc.saveOrUpdateDocumentperCF(doc);
	}
	
	public List<Tbdocspertrans> getListDocsPerTrans(String txcode) {
		return srvc.getListDocsPerTrans(txcode);
	}

	public String saveOrDeleteDocumentPerTrans(Tbdocspertrans docpertrans, String ident) {
		return srvc.saveOrDeleteDocumentPerTrans(docpertrans, ident);
	}
	
	//added by fed
	public List<Tbtransaction> getListTransaction() {
		return srvc.getListTransaction();
	}
	
	public String saveUpdateTransaction(Tbtransaction form,String beingUpdated) {
		return srvc.saveUpdateTransaction(form,beingUpdated);
		
	}
    public List<DocChecklistForm> getDocChecklist(String memberid, String appno){
    	return srvc.getDocChecklist(appno, memberid);
    }
    public String refreshDocChecklist(String appno){
    	return srvc.refreshDocChecklist(appno);
    }
}
