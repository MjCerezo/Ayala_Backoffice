package com.etel.las;

import java.util.List;

import com.coopdb.data.Tbevaldetails;
import com.coopdb.data.Tblstapp;
import com.coopdb.data.Tblstappbusiness;
import com.coopdb.data.Tblstappdependents;
import com.coopdb.data.Tblstappemployment;
import com.coopdb.data.Tblstappindividual;
import com.coopdb.data.Tblstapppersonalreference;
import com.coopdb.data.Tblstbankaccounts;
import com.coopdb.data.Tblstcreditcardinfo;
import com.coopdb.data.Tblstexistingloansother;
import com.etel.forms.FormValidation;
import com.etel.inquiry.forms.DedupeCIFForm;
import com.etel.lasform.SearchCIFForm;
import com.etel.lasform.SearchLOSForm;
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
public class LASFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level;
	 * recommend changing this to FATAL or ERROR before deploying. For info on these
	 * levels, look for tomcat/log4j documentation
	 */
	public LASFacade() {
		// super(INFO);
	}

	private LASService srvc = new LASServiceImpl();

	/** Start of Basic Details **/
	public String saveOrUpdateLstappIndiv(Tblstappindividual d) {
		return srvc.saveOrUpdateLstappIndiv(d);
	}

	public Tblstappindividual getTblstappindividual(String appno) {
		return srvc.getTblstappindividual(appno);
	}

	/***/

	/** Start of Dependent **/
	public String saveOrUpdateDependent(Tblstappdependents d) {
		return srvc.saveOrUpdateDependents(d);
	}

	public List<Tblstappdependents> listTblstappdependents(String appno) {
		return srvc.listTblstappdependents(appno);
	}

	public String deleteDependent(Integer id) {
		return srvc.deleteDependent(id);
	}

	/***/

	/** Start of Employment **/
	public String saveOrUpdateEmployment(Tblstappemployment d) {
		return srvc.saveOrUpdateEmployment(d);
	}

	public List<Tblstappemployment> listTblstappemployment(String appno) {
		return srvc.listTblstappemployment(appno);
	}

	public String deleteEmployment(Integer id) {
		return srvc.deleteEmployment(id);
	}

	/***/

	/** Start of Personal Reference **/
	public String saveOrUpdateReference(Tblstapppersonalreference d) {
		return srvc.saveOrUpdateReference(d);
	}

	public List<Tblstapppersonalreference> listTblstappsonalreference(String appno) {
		return srvc.listTblstappsonalreference(appno);
	}

	public String deleteReference(Integer id) {
		return srvc.deleteReference(id);
	}

	/***/

	/** Start of Existing Loan Other **/
	public String saveOrUpdateExistingLoan(Tblstexistingloansother d) {
		return srvc.saveOrUpdateExistingLoan(d);
	}

	public List<Tblstexistingloansother> listTblstexistingloansother(String appno) {
		return srvc.listTblstexistingloansother(appno);
	}

	public String deleteExistingLoan(Integer id) {
		return srvc.deleteExistingLoan(id);
	}

	/***/

	/** Start of Bank Account **/
	public String saveOrUpdateBankAccount(Tblstbankaccounts d) {
		return srvc.saveOrUpdateBankAccount(d);
	}

	public List<Tblstbankaccounts> listTblstbankaccounts(String appno) {
		return srvc.listTblstbankaccounts(appno);
	}

	public String deleteBankAccount(Integer id) {
		return srvc.deleteBankAccount(id);
	}

	/***/

	/** Start of Credit Card **/
	public String saveOrUpdateCreditCard(Tblstcreditcardinfo d) {
		return srvc.saveOrUpdateCreditCard(d);
	}

	public List<Tblstcreditcardinfo> listTblstcreditcardinfo(String appno) {
		return srvc.listTblstcreditcardinfo(appno);
	}

	public String deleteCreditCard(Integer id) {
		return srvc.deleteCreditCard(id);
	}

	/***/

	/** Start of Business **/
	public String saveOrUpdateBusiness(Tblstappbusiness d) {
		return srvc.saveOrUpdateBusiness(d);
	}

	public List<Tblstappbusiness> listTblstappbusiness(String appno) {
		return srvc.listTblstappbusiness(appno);
	}

	public String deleteBusiness(Integer id) {
		return srvc.deleteBusiness(id);
	}

	/** End of Business *****/

	// Save As Draft
	public String saveOrUpdateHeader(Tblstapp d) {
		return srvc.saveOrUpdateHeader(d);
	}

	public String createCIFRecordForNonExistingClients(String appno) {
		return srvc.createCIFRecordForNonExistingClients(appno);
	}

	// Search CIF
	public List<SearchCIFForm> searchCIF(String branch,String lname, String fname, String businessname, Integer page,
			Integer maxresult, String customertype) {
		return srvc.searchCIF(branch,lname, fname, businessname, page, maxresult, customertype);
	}

	public int searchCIFCount(String branch,String lname, String fname, String businessname, String customertype) {
		return srvc.searchCIFCount(branch,lname, fname, businessname, customertype);
	}

	// Search LOS
	public List<SearchLOSForm> searchLOS(String branch,String lname, String fname, String businessname, Integer page,
			Integer maxresult, String customertype) {
		return srvc.searchLOS(branch,lname, fname, businessname, page, maxresult, customertype);
	}

	public int searchLOSCount(String branch,String lname, String fname, String businessname, String customertype) {
		return srvc.searchLOSCount(branch,lname, fname, businessname, customertype);
	}

	// Approval Stage
	public String saveOrUpdateApprovalDetails(Tbevaldetails d) {
		return srvc.saveOrUpdateApprovalDetails(d);
	}

	public Tbevaldetails getTbevaldetails(String appno) {
		return srvc.getTbevaldetails(appno);
	}

	// Ced 6-24-2021
	public FormValidation submitLoanApp(String appno) {
		return srvc.submitLoanApp(appno);
	}
	
	public FormValidation waiveBICI(String appno) {
		return srvc.waiveBICI(appno);
	}

}
