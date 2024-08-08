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
import com.etel.lasform.SearchCIFForm;
import com.etel.lasform.SearchLOSForm;

public interface LASService {

	String saveOrUpdateLstappIndiv(Tblstappindividual d);

	Tblstappindividual getTblstappindividual(String appno);

	String saveOrUpdateDependents(Tblstappdependents d);

	List<Tblstappdependents> listTblstappdependents(String appno);

	String deleteDependent(Integer id);

	String saveOrUpdateEmployment(Tblstappemployment d);

	List<Tblstappemployment> listTblstappemployment(String appno);

	String deleteEmployment(Integer id);

	String saveOrUpdateReference(Tblstapppersonalreference d);

	List<Tblstapppersonalreference> listTblstappsonalreference(String appno);

	String deleteReference(Integer id);

	String saveOrUpdateExistingLoan(Tblstexistingloansother d);

	List<Tblstexistingloansother> listTblstexistingloansother(String appno);

	String deleteExistingLoan(Integer id);

	String saveOrUpdateBankAccount(Tblstbankaccounts d);

	List<Tblstbankaccounts> listTblstbankaccounts(String appno);

	String deleteBankAccount(Integer id);

	String saveOrUpdateCreditCard(Tblstcreditcardinfo d);

	List<Tblstcreditcardinfo> listTblstcreditcardinfo(String appno);

	String deleteCreditCard(Integer id);

	String saveOrUpdateBusiness(Tblstappbusiness d);

	List<Tblstappbusiness> listTblstappbusiness(String appno);

	String deleteBusiness(Integer id);

	String saveOrUpdateHeader(Tblstapp d);

	String createCIFRecordForNonExistingClients(String appno);

	List<SearchCIFForm> searchCIF(String branch,String lname, String fname, String businessname, Integer page, Integer maxresult,
			String customertype);

	int searchCIFCount(String branch,String lname, String fname, String businessname, String customertype);

	List<SearchLOSForm> searchLOS(String branch,String lname, String fname, String businessname, Integer page, Integer maxresult,
			String customertype);

	int searchLOSCount(String branch,String lname, String fname, String businessname, String customertype);

	String saveOrUpdateApprovalDetails(Tbevaldetails d);

	Tbevaldetails getTbevaldetails(String appno);

	FormValidation submitLoanApp(String appno);

	FormValidation waiveBICI(String appno);

}
