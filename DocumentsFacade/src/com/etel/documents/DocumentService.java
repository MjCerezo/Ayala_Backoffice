package com.etel.documents;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cifsdb.data.Tbdocdetails;
import com.coopdb.data.Tbdocchecklist;
import com.coopdb.data.Tbdocpertransactiontype;
import com.coopdb.data.Tbdocsperapplication;
import com.coopdb.data.Tbdocuments;
import com.coopdb.data.Tbtransaction;
import com.coopdb.data.Tbdocsperproduct;
import com.wavemaker.runtime.server.FileUploadResponse;

public interface DocumentService {

	List<Tbdocchecklist> getNewMemberAppDocChecklist(String membershipappid, String membershipid, String status);

	String verifyNewMemberAppDocumentChecklist(String membershipappid);

	String updateDocument(String field, Tbdocchecklist doc);

	/****** FROM UNI ******/
	List<Tbdocuments> getDocuments();

	String addDocumentLOS(String category, String doccode, String docname, String remarks);

	String updateDocumentLOS(String category, String doccode, String docname, String remarks, boolean iseditable);

	List<Tbdocuments> byCat(String category);

	String checkDocCode(String code);

	String deleteDoc(String code);

	/***** DOCUMENTS PER TRANSACTION *****/
	List<Tbtransaction> documentPerTrans(String txname);

	List<Tbdocpertransactiontype> reqDocuments(String txcode);

	List<Tbdocuments> tbDocuments();

	String addRequiredDocuments(Tbdocpertransactiontype req);

	String deleteRequiredDocuments(String doccode, String txcode, Integer txid);

	List<Tbdocsperapplication> getDocumentsPerLoanApplication(String appno,String doccategory,String doccode);

	String refreshLoanApplicationDocumentChecklist(String appno);

	String batchUpdateDocsSubmit(List<Tbdocsperapplication> docs);

	String updateDocperApp(Tbdocsperapplication doc,String filepath);

	String previewMembershipDocument(int docid);

	List<Tbdocuments> getDocumentsByDocCat(String doccat);

	String previewLoanApplicationDocument(int docid);

	String refreshMembershipDocumentsChecklist(String membershipid, String membershipappid);

	String getDocIDbyMemberIDandDocID(String memid, String docid);
	
	
	List<Tbdocdetails> displayDocsDetails(String doccategory, String doctype, String cifno);
	String addDocument(Tbdocdetails doc);
	String updateDocument(Tbdocdetails doc);
	String deleteDocument(int docid);
	
	
	String saveOrUpdateDocs(Tbdocuments ref,String meth);
	String deleteDocs(String documentcode);
	List<Tbdocdetails> getDocDetails(String cifno, String doccat, String doccode);
	String saveOrUpdateDocDetails(Tbdocdetails details, String filepath);
	String checkPicOrPDF(Integer docid);
	
	FileUploadResponse uploadFile(MultipartFile file);
	String viewDocument(Integer docid);

	List<Tbdocsperproduct> getDocumentPerProduct(String loanproduct, String doccategory);
	List<Tbdocsperproduct> getDocumentPerProductNoDocCat(String appno);

	String viewDocumentLAS(Integer docid);

	List<Tbdocsperapplication> getDocumentsPerLoanApplicationNoDocCat(String appno, String doccode);

}
