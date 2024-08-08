/**
 * 
 */
package com.etel.docmaintenance;

import java.util.List;

import com.coopdb.data.Tbcodetable;
import com.coopdb.data.Tbdocspercfapp;
import com.coopdb.data.Tbdocspertrans;
import com.coopdb.data.Tbgeneraldocs;
import com.coopdb.data.Tbtransaction;
import com.etel.docmaintenanceform.DocChecklistForm;

/**
 * @author MMM
 *
 */
public interface DocMaintenanceService {

	List<Tbgeneraldocs> getDocumentListPerDocCategory(String category);
	String saveDocType(Tbgeneraldocs doc);
	boolean checkDocTypeAvailability(String doctype, String doccategory);
	String deleteDocCategory(Tbcodetable doccat);
	String deleteDocType(Tbgeneraldocs doc);
	List<Tbdocspercfapp> getDocsPerApplicationPerCF(String loanappno, String cfcode);
	String getGenerateDocsPerAppPerCF(String loanappno, String cfcode, String cifno, String cfrefnoconcat);
	String saveOrUpdateDocumentperCF(Tbdocspercfapp doc);
	List<Tbdocspertrans> getListDocsPerTrans(String txcode);
	String saveOrDeleteDocumentPerTrans(Tbdocspertrans docpertrans, String ident);
	List<Tbtransaction> getListTransaction();
	String saveUpdateTransaction(Tbtransaction form,String beingUpdated);
	List<DocChecklistForm> getDocChecklist(String appno, String memberid);
	String refreshDocChecklist(String appno);
	List<DocChecklistForm> getDocumentListPerDocCategoryNew(String category, String cifno, String custType);
}
