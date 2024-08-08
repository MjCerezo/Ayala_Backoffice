package com.isls.document;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.coopdb.data.Tbdocchecklist;
import com.coopdb.data.Tbgeneraldocs;
import com.isls.document.forms.DocFields;
import com.isls.document.forms.DocumentForm;
import com.wavemaker.runtime.server.FileUploadResponse;

public interface DocumentManagementService {

	FileUploadResponse uploadFile(MultipartFile file);

	String exportFiles(int docid, String fullname);

	String addDocuments(String filepath,Tbdocchecklist docchecklist);

	String checkDocStatus(String docid);

	String updateDocs(Date dateSubmitted, int docid);

	List<DocFields> searchMember(String membershipid, String name,String status,String doccat);

	List<DocFields> getAllRecords();

	String previewPDF(int docid);

	String updateExpiration(Date dateExpiration, int docid);

	String checkIsExpiring(int docid,int txcode);

	List<Tbgeneraldocs> ifFieldisActivated(String doctype, String doccategory);

	List<DocumentForm> listAllMemberDocuments(DocumentForm docparameters);

	String saveOrUpdateMemberDoucment(DocumentForm docparameters, Tbdocchecklist doc); 

	String viewMemberDocument(DocumentForm docparameters);

	String deleteUploadedFile(int docid);

	

}
