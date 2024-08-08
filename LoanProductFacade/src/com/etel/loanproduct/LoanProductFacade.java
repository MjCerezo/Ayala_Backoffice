package com.etel.loanproduct;

import java.util.ArrayList;
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
import com.etel.utils.UserUtil;
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
public class LoanProductFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log
	 * level; recommend changing this to FATAL or ERROR before deploying. For
	 * info on these levels, look for tomcat/log4j documentation
	 */
	LoanProductService srvc = new LoanProductServiceImpl();

	public LoanProductFacade() {
		// super(INFO);
	}

	/** Get List of LoanProduct */
	public List<Tbloanproduct> getListOfLoanProduct(String product, String prodtype) {
		return srvc.getListOfLoanProduct(product, prodtype);
	}

	/** Get Loan Product by productcode */
	public Tbloanproduct getLoanProductByProductcode(String productcode) {
		return srvc.getLoanProductByProductcode(productcode);
	}

	public String checkProductCode(String productcode) {

		return srvc.checkProductCode(productcode);

	}

	public String saveLoanProduct(Tbloanproduct product) {
		return srvc.saveLoanProduct(product);
	}

	public List<Tbintratebyterm> getintratebyproduct(String productcode) {
		return srvc.getintratebyproduct(productcode);
	}

	public String saveIntRatePerProduct(Tbintratebyterm intrecord) {
		return srvc.saveIntRatePerProduct(intrecord);
	}

	public String saveIntRatePerProduct1(Tbintratebyterm intrecord) {
		return srvc.saveIntRatePerProduct(intrecord);
	}

	public Tbfeesandcharges getFeesandChargesPerProduct(String productcode) {
		return srvc.getFeesandChargesPerProduct(productcode);
	}

	public List<Tbdocsperproduct> getDocumentsPerProduct(String productcode) {
		return srvc.getDocumentsPerProduct(productcode);
	}

	public String saveLoanProductFull(Tbloanproduct product, Tbfeesandcharges fees) {
		return srvc.saveLoanProductFull(product, fees);

	}

	public String saveDocumentPerProduct(Tbdocsperproduct doc) {
		return srvc.saveDocumentPerProduct(doc);
	}

	public Tblstapp getApplicationDetails(String appno) {
		return srvc.getApplicationDetails(appno);
	}

	public String deleteDocumentPerProduct(Tbdocsperproduct doc) {
		return srvc.deleteDocumentPerProduct(doc);
	}

//	public List<CodetableForm> getListofCreditFacilities() {
//		return srvc.getListofCreditFacilities();
//	}
//
//	public String saveCreditFacility(String facilitycode, String facilityname, String prodcode, String productname,
//			String repaymenttype, String repaymentcode, String selectedfacilitycode, String selectedrepaymentcode) {
//		return srvc.saveCreditFacility(facilitycode, facilityname, prodcode, productname, repaymenttype, repaymentcode,
//				selectedfacilitycode, selectedrepaymentcode);
//	}
//
//	public String deleteCreditFacility(String prodcode, String facilitycode, String repaymentcode) {
//		return srvc.deleteCreditFacility(prodcode, facilitycode, repaymentcode);
//	}
//
//	public List<Tbloanprodpercf> getListofCFPerProduct(String prodcode) {
//		return srvc.getListofCFPerProduct(prodcode);
//
//	}

	public List<Tbloanproductfees> getLoanFees(String prodcode) {
		return srvc.getLoanFees(prodcode);

	}

	public String saveLoanFee(LoanFeeForm feeform, String selectedloanfeecode) {
		return srvc.saveLoanFee(feeform, selectedloanfeecode);

	}

	public String deleteLoanFee(String prodcode, String loanfeecode) {
		return srvc.deleteLoanFee(prodcode, loanfeecode);
	}

	public String saveRepayment(String prodcode, String schemecode, String repayment, String selectedschemecode) {
		return srvc.saveRepayment(prodcode, schemecode, repayment, selectedschemecode);
	}

	public String deleteRepayment(String prodcode, String schemecode) {
		return srvc.deleteRepayment(prodcode, schemecode);
	}

	public List<Tbloanschemeperprod> getRepayments(String prodcode) {
		return srvc.getRepayments(prodcode);

	}

	public List<Tbloanrepaymentscheme> getListRepayments() {
		return srvc.getListRepayments();
	}

	public Tbworkflowprocess getAppStatusProcessName(Integer workflowid, Integer sequenceno) {
		return srvc.getAppStatusProcessName(workflowid, sequenceno);
	}

	public List<CyclePerLoanSchemeForm> listCycleForm(List<CyclePerLoanSchemeForm> form) {
		return form;
	}

	public List<LoanSchemePerProdForm> listLoanSchemePerProdForm(List<LoanSchemePerProdForm> form) {
		return form;
	}

	public List<CyclePerLoanSchemeForm> getPayCyclePerLoanScheme(Integer schemecode, String pi) {
		return srvc.getPayCyclePerLoanScheme(schemecode, pi);
	}

	public List<LoanSchemePerProdForm> getLoanSchemePerProd(String prodcode) {
		return srvc.getLoanSchemePerProd(prodcode);
	}

	public List<Tbloanfeesperapp> getLoanFeesPerApp(String appno) {
		return srvc.getLoanFeesPerApp(appno);
	}

	public String saveOrUpdateLoanFeesPerApp(Tbloanfeesperapp loanfees, String prevloanfeecode) {
		return srvc.saveOrUpdateLoanFeesPerApp(loanfees, prevloanfeecode);
	}

	public String deleteLoanFeesPerApp(Tbloanfeesperapp loanfees) {
		return srvc.deleteLoanFeesPerApp(loanfees);
	}

	public String generateLoanFeesPerApp(LoanFeeInputForm fee) {
		return srvc.generateLoanFeesPerApp(fee);
	}

	public List<Tbloanrepaymentscheme> getLoanRepayScheme(Integer schemecode) {
		return srvc.getLoanRepayScheme(schemecode);
	}

	public Boolean isDefaultLoanFee(String prodcode, String loanfeecode) {
		return srvc.isDefaultLoanFee(prodcode, loanfeecode);
	}

	public String submitLoanProduct(String productcode, String status) {
		return srvc.submitLoanProduct(productcode, status);
	}

	public boolean isUserHasARoleId(String roleid) {
		return UserUtil.hasRole(roleid);
	}

	public List<Tbcollateralperprod> getListOfCollateralPerProd(String prodcode) {
		return srvc.getListOfCollateralPerProd(prodcode);
	}

	public String saveCollateralPerProd(String prodcode, Boolean iscollateralrequired, List<CodetableForm> collateral) {
		return srvc.saveCollateralPerProd(prodcode, iscollateralrequired, collateral);
	}

	public List<CodetableForm> getSavedCollateralPerProd(String prodcode) {
		return srvc.getSavedCollateralPerProd(prodcode);
	}

	public List<RepaymentSchemeDisplayForm> getRepaymentScheme(String prodcode) {
		List<RepaymentSchemeDisplayForm> list = new ArrayList<RepaymentSchemeDisplayForm>();
		list = srvc.getRepaymentScheme(prodcode);
		return list;
	}

	/** Get List of LoanProduct Per Company */
	public List<Tbproductpercompanyform> getListOfLoanProductPerCompany(String company) {
		return srvc.getListOfLoanProductPerCompany(company);
	}

	public String checkProductNo(String productno) {
		return srvc.checkProductNo(productno);
	}
	
	/** Get List of LoanProduct Per Company */
	public List<Tbproductpercompanyform> getListOfLoanProductPerCompanyPerGroup(String company, String prodgrp) {
		return srvc.getListOfLoanProductPerCompanyPerGroup(company, prodgrp);
	}
	public List<Tbdeductions> getListOfDeductionsPerProductCode(String productcode)
	{
		return srvc.getListOfDeductionsPerProductCode(productcode);
	}
//Added FED
	
	public String saveorUpdateDeduction(Tbdeductions deduction,String beingUpdated)
	{
		return srvc.saveorUpdateDeduction(deduction,beingUpdated);
	}
	public List<Tbbillingcutoffperprod> getListOfBillingCutOff(String productcode)
	{
		return srvc.getListOfBillingCutOff(productcode);
	}
	
	public String saveOrUpdateBillingCutOff(Tbbillingcutoffperprod billingCutOff,String beingUpdated)
	{
		return srvc.saveOrUpdateBillingCutOff(billingCutOff,beingUpdated);
	}
	public String deleteBillingCutOFf(Tbbillingcutoffperprod billingCutOff) {
		return srvc.deleteBillingCutOFf(billingCutOff);
		
	}
	public String deleteDeductions(Tbdeductions deductions) {
		return srvc.deleteDeductions(deductions);
		
	}
	
	public List<Tbmembershiptypeperbos> getMembershipTypePerBos(String boscode)
	{
		return srvc.getMembershipTypePerBos(boscode);
	}
	
	public List<BOSForm> getBOSPerProd(String productcode)
	{
		return srvc.getBOSPerProd(productcode);
	}
	
	public String saveBOS(Tbloanprodmembertype loanprod,List<CodetableForm> servicestatus)
	{
		return srvc.saveBOS(loanprod,servicestatus);
	}
	
	public List<CodetableForm> getSavedServiceStatus(String productcode,String membertype) {
		return srvc.getSavedServiceStatus(productcode,membertype);
	}
	public String saveMLAC(List<CodetableForm> mlaparticulars,String lowest,String highest,String prodcode) {
		return srvc.saveMLAC(mlaparticulars,lowest,highest,prodcode);
	}
	public List<CodetableForm> getSavedParticulars (String productcode,String lowest,String highest) {
		return srvc.getSavedParticulars(productcode,lowest,highest);
	}
	public List<LoanProductForm> listLoanProduct (){
		return srvc.listLoanProduct();
	}
	//Mar
	public List<Tbdocsperproduct> getDocumentsPerProductDocCat(String productcode, String docCat) {
		return srvc.getDocumentsPerProductDocCat(productcode, docCat);
	}
	
	public List<LoanIntRateTable> getLoanIntRateTable (String productcode){
		return srvc.getLoanIntRateTable(productcode);
	}

}
