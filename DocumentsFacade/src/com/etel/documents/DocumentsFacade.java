package com.etel.documents;

import java.util.List;

import javax.print.Doc;

import org.springframework.web.multipart.MultipartFile;

import com.cifsdb.data.Tbdocdetails;
import com.coopdb.data.Tbdocchecklist;
import com.coopdb.data.Tbdocpertransactiontype;
import com.coopdb.data.Tbdocsperapplication;
import com.coopdb.data.Tbdocuments;
import com.coopdb.data.Tbtransaction;
import com.coopdb.data.Tbdocsperproduct;
import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
import com.wavemaker.runtime.server.FileUploadResponse;
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
public class DocumentsFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log
	 * level; recommend changing this to FATAL or ERROR before deploying. For
	 * info on these levels, look for tomcat/log4j documentation
	 */
	DocumentService service = new DocumentServiceImpl();

	public List<Tbdocchecklist> getNewMemberAppDocChecklist(String membershipappid, String membershipid, String status) {
		return service.getNewMemberAppDocChecklist(membershipappid, membershipid, status);
	}

	public String updateDocument(String field, Tbdocchecklist doc) {
		return service.updateDocument(field, doc);
	}

	public String verifyNewMemberAppDocumentChecklist(String membershipappid) {
		return service.verifyNewMemberAppDocumentChecklist(membershipappid);
	}

	/********** FROM UNI *********/
	public List<Tbdocuments> getDocuments() {
		return service.getDocuments();
	}

	public String addDocumentLOS(String category, String doccode, String docname, String remarks) {
		return service.addDocumentLOS(category, doccode, docname, remarks);
	}

	public String updateDocumentLOS(String category, String doccode, String docname, String remarks,
			boolean iseditable) {
		return service.updateDocumentLOS(category, doccode, docname, remarks, iseditable);
	}

	public List<Tbdocuments> byCat(String category) {
		return service.byCat(category);
	}

	public String checkDocCode(String code) {
		return service.checkDocCode(code);
	}

	public String deleteDoc(String code) {
		return service.deleteDoc(code);
	}

	/********** Documents Per Transaction **********/
	public List<Tbtransaction> documentPerTrans(String txname) {
		return service.documentPerTrans(txname);
	}

	public List<Tbdocpertransactiontype> reqDocuments(String txcode) {
		return service.reqDocuments(txcode);
	}

	public List<Tbdocuments> tbDocuments() {
		return service.tbDocuments();
	}
	//edited added isExpiring
	public String addRequiredDocuments(Tbdocpertransactiontype req) {
		return service.addRequiredDocuments(req);
	}

	public String deleteRequiredDocuments(String doccode, String txcode, Integer txid) {
		return service.deleteRequiredDocuments(doccode, txcode, txid);
	}

	/* December 3, LOS Integration, Daniel , Documents Per Application */
	public List<Tbdocsperapplication> getDocumentsPerLoanApplication(String appno,String doccategory,String doccode) {
		return service.getDocumentsPerLoanApplication(appno, doccategory, doccode);
	}
	
	public String refreshLoanApplicationDocumentChecklist(String appno){
		return service.refreshLoanApplicationDocumentChecklist(appno);
	}
	public String batchUpdateDocsSubmit(List<Tbdocsperapplication> docs){
		return service.batchUpdateDocsSubmit(docs);
	}
	public String updateDocperApp(Tbdocsperapplication doc,String filepath){
		return service.updateDocperApp(doc,filepath);
	}
	
	public String previewMembershipDocument(int docid) {
		return service.previewMembershipDocument(docid);
	}
	
	//added by fed
	public List<Tbdocuments> getDocumentsByDocCat(String doccat) {
		return service.getDocumentsByDocCat(doccat);
	}
	
	public String previewLoanApplicationDocument(int docid) {
		return service.previewLoanApplicationDocument(docid);
	}
	
	public String refreshMembershipDocumentsChecklist(String membershipid, String membershipappid) {
		return service.refreshMembershipDocumentsChecklist(membershipid, membershipappid);
	}

	public String getDocIDbyMemberIDandDocID(String memid, String docid) {
		return service.getDocIDbyMemberIDandDocID(memid, docid);
	}
	
	public String addDocument(Tbdocdetails doc) {
		DocumentService srvc = new DocumentServiceImpl();
		return srvc.addDocument(doc);
	}
	
	public String deleteDocument(int docid) {
		String flag = "";
		DocumentService srvc = new DocumentServiceImpl();
		flag = srvc.deleteDocument(docid);
		return flag;
	}
	
	public List<Tbdocdetails> displayDocsDetails(String doccategory, String doctype, String cifno) {
		DocumentService srvc = new DocumentServiceImpl();
		return srvc.displayDocsDetails(doccategory, doctype, cifno);
	}
	
	public String updateDocument(Tbdocdetails doc) {
		DocumentService srvc = new DocumentServiceImpl();
		return srvc.updateDocument(doc);
	}
	public String saveOrUpdateDocs(Tbdocuments ref,String meth) {
		DocumentService srvc = new DocumentServiceImpl();
  		return srvc.saveOrUpdateDocs(ref,meth);
  	}
	
	public String deleteDocs(String documentcode) {
		DocumentService srvc = new DocumentServiceImpl();
		return srvc.deleteDocs(documentcode);
	}
	
	public List<Tbdocdetails> getDocDetails(String cifno,String doccat,String doccode){
		DocumentService srvc = new DocumentServiceImpl();
		return srvc.getDocDetails(cifno,doccat,doccode);
	}
	
	public String saveOrUpdateDocDetails(Tbdocdetails details,String filepath) {
		DocumentService srvc = new DocumentServiceImpl();
		return srvc.saveOrUpdateDocDetails(details,filepath);
	}
	
	public String checkPicOrPDF(Integer docid) {
		DocumentService srvc = new DocumentServiceImpl();
		return srvc.checkPicOrPDF(docid);
	}
	//upload document file
	public FileUploadResponse uploadFile(MultipartFile file) {
		DocumentService srvc = new DocumentServiceImpl();	
		return srvc.uploadFile(file);
	} 
	//view document
	public String viewDocument(Integer docid)
	{
		DocumentService srvc = new DocumentServiceImpl();	
		return srvc.viewDocument(docid);
	}
	
	//LAS SIDE
	public List<Tbdocsperproduct> getDocumentPerProduct(String loanproduct,String doccategory){
		DocumentService srvc = new DocumentServiceImpl();	
		return srvc.getDocumentPerProduct(loanproduct,doccategory);
	}
	
	//LAS No doccat
	public List<Tbdocsperproduct> getDocumentPerProductNoDocCat(String appno){
		DocumentService srvc = new DocumentServiceImpl();	
		return srvc.getDocumentPerProductNoDocCat(appno);
	}
	public List<Tbdocsperapplication> getDocumentsPerLoanApplicationNoDocCat(String appno, String doccode) {
		return service.getDocumentsPerLoanApplicationNoDocCat(appno, doccode);
	}
	
	public String viewDocumentLAS(Integer docid)
	{
		DocumentService srvc = new DocumentServiceImpl();	
		return srvc.viewDocumentLAS(docid);
	}
	
}
