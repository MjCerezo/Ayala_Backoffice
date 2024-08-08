package com.isls.document;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.coopdb.data.Tbdocchecklist;
import com.coopdb.data.Tbgeneraldocs;
import com.coopdb.data.Tbmembershipapp;
import com.etel.docmaintenance.DocMaintenanceService;
import com.etel.docmaintenance.DocMaintenanceServiceImpl;
import com.isls.document.forms.DocFields;
import com.isls.document.forms.DocumentForm;
import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
import com.wavemaker.runtime.server.FileUploadResponse;
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
public class DocumentManagementFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public DocumentManagementFacade() {
       super(INFO);
    }
    private DocumentManagementService service = new DocumentManagementServiceImpl();
    public FileUploadResponse uploadFile(MultipartFile file) {
    	return service.uploadFile(file);
    } 
    
    public String exportFiles(int docid,String fullname)
    {
    	return service.exportFiles(docid,fullname);
    }
    public String addDocuments(String filepath,Tbdocchecklist docchecklist) {
    	return service.addDocuments(filepath,docchecklist);
    }

    public String checkDocStatus(String docid)
    {
    	return service.checkDocStatus(docid);
    }
    public String updateDocs(Date dateSubmitted,int docid)
    {
    	return service.updateDocs(dateSubmitted,docid);
    }
    public List<DocFields> searchMember(String membershipid, String name,String status,String doccat)
    {
    	return service.searchMember(membershipid,name,status,doccat);
    } 
    public List<DocFields> getAllRecords()
    {
    	return service.getAllRecords();
    } 
    public String previewPDF(int docid)
    {
    	return service.previewPDF(docid);
    }
    public String updateExpiration(Date dateExpiration,int docid)
    {
    	return service.updateExpiration(dateExpiration,docid);
    }
    public String checkIsExpiring(int docid,int txcode)
    {
    	return service.checkIsExpiring(docid,txcode);
    }
    public List<Tbgeneraldocs> ifFieldisActivated(String doctype, String doccategory){
    	return service.ifFieldisActivated(doctype, doccategory);
    }
    
    public List<DocumentForm> listAllMemberDocuments(DocumentForm docparameters){
    	return service.listAllMemberDocuments(docparameters);
    }

	public String saveOrUpdateMemberDoucment(DocumentForm docparameters, Tbdocchecklist doc) {
    	return service.saveOrUpdateMemberDoucment(docparameters, doc);
    }
    
    public String viewMemberDocument(DocumentForm docparameters) {
    	return service.viewMemberDocument(docparameters);
    }
    public String deleteUploadedFile(int docid) {
    	return service.deleteUploadedFile(docid);
    }

}
