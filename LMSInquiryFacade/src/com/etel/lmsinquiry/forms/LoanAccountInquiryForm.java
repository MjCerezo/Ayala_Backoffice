package com.etel.lmsinquiry.forms;

import java.util.List;

import com.cifsdb.data.Tbcifmain;
import com.coopdb.data.Tbaccountinfo;
import com.coopdb.data.Tbapprovedcf;
import com.coopdb.data.Tblntxjrnl;
import com.coopdb.data.Tbloancollateral;
import com.coopdb.data.Tbloanproduct;
import com.coopdb.data.Tbloans;
import com.coopdb.data.Tblstcomakers;
import com.coopdb.data.Tbpaymentsched;
import com.coopdb.data.Tbpaysched;



public class LoanAccountInquiryForm {

	//CIF
	
	Tbcifmain cifdetails;
	
	//Application Details of Loan Account
	Tbaccountinfo application;
	List<Tbpaysched> apppaysched;
	
	String accountstatus;
	
	//Account Details
	Tbloans account;
	List<Tbpaymentsched> paysched;
		
	//loan product information
	Tbloanproduct product;
	
	//Transaction History
	List<Tblntxjrnl> journal;
	
	//Credit Facility Main and Sub Details
	Tbapprovedcf maincfdetails;
	Tbapprovedcf subcfdetails;
	
	//Comakers
	List<Tblstcomakers> comakers;
	
	// COllaterals
	List<Tbloancollateral> collaterals;
	
	public Tbaccountinfo getApplication() {
		return application;
	}
	public void setApplication(Tbaccountinfo application) {
		this.application = application;
	}
	public List<Tbpaysched> getApppaysched() {
		return apppaysched;
	}
	public void setApppaysched(List<Tbpaysched> apppaysched) {
		this.apppaysched = apppaysched;
	}
	public Tbloans getAccount() {
		return account;
	}
	public void setAccount(Tbloans account) {
		this.account = account;
	}
	public List<Tbpaymentsched> getPaysched() {
		return paysched;
	}
	public void setPaysched(List<Tbpaymentsched> paysched) {
		this.paysched = paysched;
	}
	public Tbloanproduct getProduct() {
		return product;
	}
	public void setProduct(Tbloanproduct product) {
		this.product = product;
	}
	public List<Tblntxjrnl> getJournal() {
		return journal;
	}
	public void setJournal(List<Tblntxjrnl> journal) {
		this.journal = journal;
	}
//	public Tbapprovedcf getMaincfdetails() {
//		return maincfdetails;
//	}
//	public void setMaincfdetails(Tbapprovedcf maincfdetails) {
//		this.maincfdetails = maincfdetails;
//	}
//	public Tbapprovedcf getSubcfdetails() {
//		return subcfdetails;
//	}
//	public void setSubcfdetails(Tbapprovedcf subcfdetails) {
//		this.subcfdetails = subcfdetails;
//	}
	public List<Tblstcomakers> getComakers() {
		return comakers;
	}
	public void setComakers(List<Tblstcomakers> comakers) {
		this.comakers = comakers;
	}
	public Tbcifmain getCifdetails() {
		return cifdetails;
	}
	public void setCifdetails(Tbcifmain cifdetails) {
		this.cifdetails = cifdetails;
	}
	public String getAccountstatus() {
		return accountstatus;
	}
	public void setAccountstatus(String accountstatus) {
		this.accountstatus = accountstatus;
	}
	public List<Tbloancollateral> getCollaterals() {
		return collaterals;
	}
	public void setCollaterals(List<Tbloancollateral> collaterals) {
		this.collaterals = collaterals;
	}
	
	
}
