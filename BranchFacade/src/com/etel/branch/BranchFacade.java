package com.etel.branch;

import java.util.List;

import com.coopdb.data.Tbbranch;
import com.coopdb.data.TbbranchId;
import com.etel.branch.forms.BranchForm;
import com.etel.company.forms.CompanyForm;
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
public class BranchFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log
	 * level; recommend changing this to FATAL or ERROR before deploying. For
	 * info on these levels, look for tomcat/log4j documentation
	 */
	public BranchFacade() {
		super(INFO);
	}

	// /**Get List of Branch*/
	// public List<BranchForm> getListOfBranchByCompany(String companyCode) {
	// BranchService srvc = new BranchServiceImpl();
	// return srvc.getListOfBranchByCompany(companyCode);

	/** Get List of Branch from TBBRANCH */
	public List<BranchForm> getListOfBranchbyCompany(String companycode) {
		BranchService srvc = new BranchServiceImpl();
		return srvc.getListOfBranchByCompany(companycode);
	}

	/** Display Branch values from TBBRANCH */
	public List<BranchForm> displayBranchDetails(String branchname) {
		BranchService srvc = new BranchServiceImpl();
		return srvc.displayBranchDetails(branchname);
	}

	/** Add New Branch to TBBRANCH */
	public String addBranch(BranchForm form) {
		BranchService srvc = new BranchServiceImpl();
		return srvc.addBranch(form);
	}

	/** Update Branch to TBBRANCH */
	public String updateBranch(BranchForm form) {
		BranchService srvc = new BranchServiceImpl();
		return srvc.updateBranch(form);
	}

	/** Delete Branch to TBBRANCH */
	public String deleteBranch(BranchForm form) {
		BranchService srvc = new BranchServiceImpl();
		return srvc.deleteBranch(form);
	}

	/** Search All to TBBRANCH */
	public List<BranchForm> searchBranch(String search) {
		BranchService srvc = new BranchServiceImpl();
		return srvc.searchBranch(search);
	}

	/** COMPANY - Get List of All Companyname from TBCOMPANY */

	public List<CompanyForm> getAllCompanyList() {
		BranchService srvc = new BranchServiceImpl();
		return srvc.getAllCompanyList();
	}

	/** BRANCH - Get List of All Branchname from TBBRANCH */

	public List<BranchForm> getAllBranchList() {
		BranchService srvc = new BranchServiceImpl();
		return srvc.getAllBranchList();
	}

	/** BRANCH - Get List of Branch from TBBRANCH */

	public List<BranchForm> getListOfBranch() {
		BranchService srvc = new BranchServiceImpl();
		return srvc.getListOfBranch();
	}
	
	/** BRANCH - Get Unique Branch from TBBRANCH */
	public TbbranchId getBranch(String branchcode){
		BranchService srvc = new BranchServiceImpl();
		return srvc.getBranch(branchcode);
	}
	
	public List<BranchForm> getCoopBranches(String coopcode, String companycode){
		BranchService srvc = new BranchServiceImpl();
		return srvc.getCoopBranches(coopcode, companycode);		
	}
	
	/** BRANCH - Get all Branch codes from TBBRANCH */
	public List<String> getListofBranchCodes(){
		BranchService srvc = new BranchServiceImpl();
		return srvc.getListofBranchCodes();		
	}
	public String updateClearingCutOffTime(String hour, String minutes) {
		BranchService srvc = new BranchServiceImpl();
		return srvc.updateClearingCutOffTime(hour, minutes);		
	}
	
	public List<BranchForm> getListOfBranch(String deactivated) {
		BranchService srvc = new BranchServiceImpl();
		return srvc.getListOfBranch(deactivated);
	}
	
	public Tbbranch getBranchDetails (String branchcode) {
	BranchService srvc = new BranchServiceImpl();
	return srvc.getBranchDetails(branchcode);
	}
	
	
}