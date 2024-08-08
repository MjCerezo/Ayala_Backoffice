package com.etel.company;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.coopdb.data.AyalaCompany;
import com.coopdb.data.Tbcomaker;
import com.coopdb.data.Tbmembercompany;
import com.coopdb.data.Tbmembershiptypeperbos;
import com.coopdb.data.Tbproductpercoop;
import com.etel.codetable.forms.CodetableForm;
import com.etel.company.forms.CompanyBranchForm;
import com.etel.company.forms.CompanyForm;
import com.etel.company.forms.CooperativeForm;
import com.wavemaker.runtime.server.FileUploadResponse;

public interface CompanyService {

	List<CompanyForm> getListOfCompany(String tableparameter);

	String saveOrUpdateCompany(CompanyForm form, String tableparameter, String flag, String filepath);

	String deleteCompany(CompanyForm form, String tableparameter);
	
	CompanyForm getCompany(String companycode, String companyname, String tableparameter);

	List<CooperativeForm> getAllCooperativeCompanies();

	List<CompanyForm> getMemberCompanyPerCooperative(String coopcode);

	String saveOrUpdateMemberCompanyPerCooperative(Tbmembercompany company);

	String deleteMemberCompany(String coopcode, String companycode);

	List<Tbproductpercoop> getListProdperCoop(String prodcode, String coopcode);

	String updateProdlist(List<Tbproductpercoop> prod);

	CompanyForm getMemberCompanyPerCode(String companycode);

	List<CompanyForm> getMemberCompanyPerCooperativePerMemberType(String coopcode, String membertype);

	String saveMemberType(String boscode, List<CodetableForm> membertype);

	List<CodetableForm> getSavedMemberType(String boscode);
	
	//Renz
	FileUploadResponse uploadFile(MultipartFile file);
	FileUploadResponse uploadFile2(MultipartFile file);
	String viewImage(String coopcode);
	String checkPicOrPDF(String coopcode);

	List<CompanyBranchForm> getBranchDropdown();
	
}
