package com.etel.loanproduct;

import java.util.List;

import com.coopdb.data.Tbbillingcutoffperprod;
import com.coopdb.data.Tbcollateralperprod;
import com.coopdb.data.Tbdeductions;
import com.coopdb.data.Tbdocsperproduct;
import com.coopdb.data.Tbfeesandcharges;
import com.coopdb.data.Tbintratebyterm;
import com.coopdb.data.Tbloanfeesperapp;
import com.coopdb.data.Tbloanprodmembertype;
import com.coopdb.data.Tbloanproduct;
import com.coopdb.data.Tbloanproductfees;
import com.coopdb.data.Tbloanrepaymentscheme;
import com.coopdb.data.Tbloanschemeperprod;
import com.coopdb.data.Tblstapp;
import com.coopdb.data.Tbmembershiptypeperbos;
import com.coopdb.data.TbmembershiptypeperbosId;
import com.coopdb.data.Tbworkflowprocess;
import com.etel.codetable.forms.CodetableForm;
import com.etel.loanproduct.forms.BOSForm;
import com.etel.loanproduct.forms.CyclePerLoanSchemeForm;
import com.etel.loanproduct.forms.LoanFeeForm;
import com.etel.loanproduct.forms.LoanFeeInputForm;
import com.etel.loanproduct.forms.LoanIntRateTable;
import com.etel.loanproduct.forms.LoanProductForm;
import com.etel.loanproduct.forms.LoanSchemePerProdForm;
import com.etel.loanproduct.forms.RepaymentSchemeDisplayForm;
import com.etel.loanproduct.forms.Tbproductpercompanyform;

public interface LoanProductService {

	List<Tbloanproduct> getListOfLoanProduct(String product, String prodtype);

	Tbloanproduct getLoanProductByProductcode(String productcode);

	String checkProductCode(String productcode);
	
	String saveLoanProduct(Tbloanproduct product);
	
	List<Tbintratebyterm> getintratebyproduct(String productcode);
	
	Tbfeesandcharges getFeesandChargesPerProduct(String productcode);
	
	List<Tbdocsperproduct> getDocumentsPerProduct(String productcode);
	
	String saveLoanProductFull(Tbloanproduct product, Tbfeesandcharges fees);
	
	/**
	 * --Save Or Update Required Document Per Product--
	 * @author Wel (Sept.06.2018)
	 * @return String = success, otherwise failed
	 * */		
	String saveDocumentPerProduct(Tbdocsperproduct doc);
	
	Tblstapp getApplicationDetails(String appno);
	
	String deleteDocumentPerProduct(Tbdocsperproduct doc);
	
	String saveIntRatePerProduct(Tbintratebyterm intrecord);
	
//	List<CodetableForm> getListofCreditFacilities();
	
	/**
	 * --Save Or Update Credit Facility--
	 * @author Wel (Sept.06.2018)
	 * @return String = success, otherwise failed
	 * */		
//	String saveCreditFacility(String facilitycode, String facilityname, String prodcode, String productname, String repaymenttype,
//			String repaymentcode, String selectedfacilitycode, String selectedrepaymentcode);

	/**
	 * --Delete Credit Facility--
	 * @author Wel (Sept.06.2018)
	 * @return String = success, otherwise failed
	 * */	
//	String deleteCreditFacility(String prodcode, String facilitycode, String repaymentcode);
	
//	List<Tbloanprodpercf> getListofCFPerProduct(String prodcode);
	
	List<Tbloanproductfees> getLoanFees(String prodcode);
	
	/**
	 * --Save Or Update Loan Fee--
	 * @author Wel (Sept.05.2018)
	 * @return String = success, otherwise failed
	 * */		
	String saveLoanFee(LoanFeeForm feeform, String selectedloanfeecode);
	
	/**
	 * --Delete Loan Fee--
	 * @author Wel (Sept.05.2018)
	 * @return String = success, otherwise failed
	 * */	
	String deleteLoanFee(String prodcode, String loanfeecode);
	
	/**
	 * --Save Or Update Repayment--
	 * @author Wel (Sept.05.2018)
	 * @return String = success, otherwise failed
	 * */		
	String saveRepayment(String prodcode, String schemecode, String repayment, String selectedschemecode);
	
	/**
	 * --Delete Repayment--
	 * @author Wel (Sept.05.2018)
	 * @return String = success, otherwise failed
	 * */	
	String deleteRepayment(String prodcode, String schemecode);	
	
	List<Tbloanschemeperprod> getRepayments(String prodcode);
	
	List<Tbloanrepaymentscheme> getListRepayments();

	/**
	 * --Get App Status/Process Name by workflowid, sequenceno--
	 * @author Wel (Sept.07.2018)
	 * @return form = {@link Tbworkflowprocess}
	 * */
	Tbworkflowprocess getAppStatusProcessName(Integer workflowid, Integer sequenceno);
	/**
	 * --Get Cycle per Loan Scheme(Repaymenttype)--
	 * @author Kevin (09.13.2018)
	 * @return List<{@link CyclePerLoanSchemeForm}>
	 * */
	List<CyclePerLoanSchemeForm> getPayCyclePerLoanScheme(Integer schemecode, String pi);
	/**
	 * --Get Loan Scheme per Product--
	 * @author Kevin (09.13.2018)
	 * @return List<{@link LoanSchemePerProdForm}>
	 * */
	List<LoanSchemePerProdForm> getLoanSchemePerProd(String prodcode);
	/**
	 * --Get Loan Fess per appno--
	 * @author Kevin (09.13.2018)
	 * @return List<{@link Tbloanfeesperapp}>
	 * */
	List<Tbloanfeesperapp> getLoanFeesPerApp(String appno);
	/**
	 * --Save Or Update Loan Scheme per appno--
	 * @author Kevin (09.13.2018)
	 * @return String = success, otherwise failed
	 * */	
	String saveOrUpdateLoanFeesPerApp(Tbloanfeesperapp loanfees, String prevloanfeecode);
	/**
	 * --Delete Loan Scheme per appno--
	 * @author Kevin (09.13.2018)
	 * @return String = success, otherwise failed
	 * */
	String deleteLoanFeesPerApp(Tbloanfeesperapp loanfees);
	/**
	 * --Generate Loan Fees per appno--
	 * @author Kevin (09.14.2018)
	 * @return String = success, otherwise failed
	 * */
	String generateLoanFeesPerApp(LoanFeeInputForm fee);
	/**
	 * --Get List of Repayment scheme--
	 * @author Kevin (09.14.2018)
	 * @return List<{@link Tbloanrepaymentscheme}>
	 * */
	List<Tbloanrepaymentscheme> getLoanRepayScheme(Integer schemecode);
	/**
	 * --Check default loan fee per prod--
	 * @author Kevin (09.15.2018)
	 * @return true otherwise false
	 * */
	Boolean isDefaultLoanFee(String prodcode, String loanfeecode);

	/**
	 * --Submit Loan Product (Change Status)--
	 * @author Kevin (10.17.2018)
	 * @return String "success" otherwise "failed"
	 * */
	String submitLoanProduct(String productcode, String status);
	/**
	 * --Get List of Collateral per Prod--
	 * @author Kevin (10.18.2018)
	 * @return List<{@link Tbcollateralperprod}>
	 * */
	List<Tbcollateralperprod> getListOfCollateralPerProd(String prodcode);
	/**
	 * --Save Collateral Per Prod--
	 * @author Kevin (10.18.2018)
	 * @return String "success" otherwise "failed"
	 * */
	String saveCollateralPerProd(String prodcode, Boolean iscollateralrequired, List<CodetableForm> collateral);
	/**
	 * --Get Saved Collateral per Prod--
	 * @author Kevin (10.18.2018)
	 * @return List<{@link CodetableForm}>
	 * */
	List<CodetableForm> getSavedCollateralPerProd(String prodcode);
	/**
	 * --Get Repayment Scheme per Prod w/ Payment and Interest Cycle--
	 * @author Kevin (10.18.2018)
	 * @return List<{@link RepaymentSchemeDisplayForm}>
	 * */
	List<RepaymentSchemeDisplayForm> getRepaymentScheme(String prodcode);

	List<Tbproductpercompanyform> getListOfLoanProductPerCompany(String company);

	String checkProductNo(String productno);
	
	List<Tbproductpercompanyform> getListOfLoanProductPerCompanyPerGroup(String company, String prodgrp);

	List<Tbdeductions> getListOfDeductionsPerProductCode(String productcode);

	String saveorUpdateDeduction(Tbdeductions deduction,String beingUpdated);

	List<Tbbillingcutoffperprod> getListOfBillingCutOff(String productcode);

	String saveOrUpdateBillingCutOff(Tbbillingcutoffperprod billingCutOff, String beingUpdated);

	String deleteBillingCutOFf(Tbbillingcutoffperprod billingCutOff);

	String deleteDeductions(Tbdeductions deductions);

	List<Tbmembershiptypeperbos> getMembershipTypePerBos(String boscode);

	List<BOSForm> getBOSPerProd(String productcode);

	String saveBOS(Tbloanprodmembertype loanprod,List<CodetableForm> servicestatus);

	List<CodetableForm> getSavedServiceStatus(String productcode, String membertype);

	String saveMLAC(List<CodetableForm> mlaparticulars, String lowest, String highest, String prodcode);

	List<CodetableForm> getSavedParticulars(String productcode, String lowest, String highest);

	List<LoanProductForm> listLoanProduct();

	List<Tbdocsperproduct> getDocumentsPerProductDocCat(String productcode, String docCat);

	List<LoanIntRateTable> getLoanIntRateTable(String productcode);

	

	
}
