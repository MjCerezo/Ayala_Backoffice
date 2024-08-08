package com.etel.company;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.coopdb.data.AyalaCompany;
import com.coopdb.data.Tbcollateralperprod;
import com.coopdb.data.Tbcomaker;
import com.coopdb.data.Tbmembercompany;
import com.coopdb.data.Tbmembershiptypeperbos;
import com.coopdb.data.Tbproductpercoop;
import com.etel.codetable.forms.CodetableForm;
import com.etel.company.forms.CompanyBranchForm;
import com.etel.company.forms.CompanyForm;
import com.etel.company.forms.CooperativeForm;
import com.etel.documents.DocumentService;
import com.etel.documents.DocumentServiceImpl;
import com.etel.personalreference.PersonalReferenceService;
import com.etel.personalreference.PersonalReferenceServiceImpl;
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
public class CompanyFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public CompanyFacade() {
       super(INFO);
    }
    CompanyService srvc = new CompanyServiceImpl();
    /**Get List of Company from TBCOMPANY*/
    public List<CompanyForm> getListOfCompany(String tableparameter) {
    	return srvc.getListOfCompany(tableparameter);
    }

    public String saveOrUpdateCompany(CompanyForm form, String tableparameter, String flag, String filepath) {
    	return srvc.saveOrUpdateCompany(form, tableparameter, flag, filepath);
    }
    /** Delete Company to TBCOMPANY*/
    public String deleteCompany (CompanyForm form, String tableparameter){
		return srvc.deleteCompany(form, tableparameter);
    }
    
    public CompanyForm getCompany(String companycode, String companyname, String tableparameter) {
    	return srvc.getCompany(companycode, companyname, tableparameter);
    }
    
    public List<CooperativeForm> getAllCooperativeCompanies(){
    	return srvc.getAllCooperativeCompanies();
    }
    
    public List<CompanyForm> getMemberCompanyPerCooperative(String coopcode){
    	return srvc.getMemberCompanyPerCooperative(coopcode);
    }
    public String saveOrUpdateMemberCompanyPerCooperative(Tbmembercompany company){
    	return srvc.saveOrUpdateMemberCompanyPerCooperative(company);
    }
    public String deleteMemberCompany(String coopcode, String companycode){
    	return srvc.deleteMemberCompany(coopcode, companycode);
    }
    public List<Tbproductpercoop> getListProdperCoop(String prodcode, String coopcode){
    	return srvc.getListProdperCoop(prodcode,coopcode);
    }
    public String updateProdlist(List<Tbproductpercoop> prod){
    	return srvc.updateProdlist(prod);
    }
	public List<CompanyForm> getMemberCompanyPerCooperativePerMemberType(String coopcode, String membertype) {
		return srvc.getMemberCompanyPerCooperativePerMemberType(coopcode, membertype);
	}
	public String saveMemberType(String boscode,List<CodetableForm> membertype)
	{
		return srvc.saveMemberType(boscode,membertype);
	}
	public List<CodetableForm> getSavedMemberType(String boscode) {
		return srvc.getSavedMemberType(boscode);
	}
	
	//Renz
	public FileUploadResponse uploadFile(MultipartFile file) {
		 return srvc.uploadFile(file);
	} 
	public FileUploadResponse uploadFile2(MultipartFile file) {
		 return srvc.uploadFile2(file);
	} 
	 public String viewImage(String coopcode){
		 return srvc.viewImage(coopcode);
	 }
	public String checkPicOrPDF(String coopcode) {
		return srvc.checkPicOrPDF(coopcode);
	}
	
	public List<CompanyBranchForm> getBranchDropdown() {
		return srvc.getBranchDropdown();
	}
	
}