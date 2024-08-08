package com.etel.branch;

import java.util.List;

import com.coopdb.data.Tbbranch;
import com.coopdb.data.TbbranchId;
import com.etel.branch.forms.BranchForm;
import com.etel.company.forms.CompanyForm;

public interface BranchService {

//	List<BranchForm> getListOfBranchByCompany(String companyCode);

	List<BranchForm>  getListOfBranchbyCompany(String companycode);
	List<BranchForm> displayBranchDetails(String branchname);
	String addBranch(BranchForm form);
	String updateBranch(BranchForm form);
	String deleteBranch(BranchForm form);
	List<BranchForm> searchBranch(String search);
	List<BranchForm> getListOfBranchByCompany(String companyCode);
	List<CompanyForm> getAllCompanyList();
	List<BranchForm> getAllBranchList();
	List<BranchForm> getListOfBranch();
	TbbranchId getBranch(String branchcode);
	List<BranchForm> getCoopBranches(String coopcode, String companycode);
	List<String> getListofBranchCodes();
	String updateClearingCutOffTime(String hour, String minutes);
	
	List<BranchForm> getListOfBranch(String deactivated);
	Tbbranch getBranchDetails(String branchcode);

}
