package com.etel.defaultusers;

import java.util.List;

import com.etel.defaultusers.forms.ApprovalMatrixForm;
import com.etel.defaultusers.forms.CompanyName;
import com.coopdb.data.Tbapprovalmatrix;
import com.coopdb.data.Tbdefaultusers;
import com.coopdb.data.Tbuser;

public interface DefaultUsersService {
	List<Tbdefaultusers> displayDefaultUsers();
	List<Tbuser> listUsernamePerRole(String role, String companycode);
	String saveUpdateDefaultUsers(Tbdefaultusers defaultusers);
	List<CompanyName> listCompany();
	String deleteDefaultUsers(String companycode);
	String saveOrUpdateApprovalMatrix(ApprovalMatrixForm form);
	Tbapprovalmatrix getApprovalMatrixRecord(Integer id);
	List<Tbapprovalmatrix> getListOfApprovalMatrix();
	String deleteApprovalMatrix(Integer id);
	
}
