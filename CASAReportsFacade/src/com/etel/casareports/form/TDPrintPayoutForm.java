package com.etel.casareports.form;

import java.math.BigDecimal;
import java.util.Date;

public class TDPrintPayoutForm {
	
	String acctno;
	String acctname;
	
	// Original Details
	Date bookingdate;
	String term;
	Date maturitydate;
	String placementamount; 
	String accountbalance;
	String intrate;
	String certno;
	String totalintmaturity;
	String wtax;
	String netinterest;
	String matvalue;
	String docstamps;
	String disposition;
	String dispositionint;
	String dispositionintacctno;
	
	// Payout Details
	String totalintcredited; // amount
	String totalwithholdingtaxdebited; // 
	String modeofrelease;
	
	String transactiontype;
	Date terminateon;
	String totalnoofdays;
	
	String actualinterestearned; // 
	String ontermearned; // 
	String addnlinterestearned; // 
	
	String actualwithholdingtax; // 
	String ontermtax; // 
	String addnlinteresttax; // 
	
	String intcreditrebate; // 
	String reversal; //
	 
	String totalintposting;
	String totalwithholdingtaxposting;
	String docstamptax;
	String netproceeds;
	String lesswidrawninterest;
	String actualbalanceperaccount;
	public String getAcctno() {
		return acctno;
	}
	public void setAcctno(String acctno) {
		this.acctno = acctno;
	}
	public String getAcctname() {
		return acctname;
	}
	public void setAcctname(String acctname) {
		this.acctname = acctname;
	}
	public Date getBookingdate() {
		return bookingdate;
	}
	public void setBookingdate(Date bookingdate) {
		this.bookingdate = bookingdate;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public Date getMaturitydate() {
		return maturitydate;
	}
	public void setMaturitydate(Date maturitydate) {
		this.maturitydate = maturitydate;
	}
	public String getPlacementamount() {
		return placementamount;
	}
	public void setPlacementamount(String placementamount) {
		this.placementamount = placementamount;
	}
	public String getAccountbalance() {
		return accountbalance;
	}
	public void setAccountbalance(String accountbalance) {
		this.accountbalance = accountbalance;
	}
	public String getIntrate() {
		return intrate;
	}
	public void setIntrate(String intrate) {
		this.intrate = intrate;
	}
	public String getCertno() {
		return certno;
	}
	public void setCertno(String certno) {
		this.certno = certno;
	}
	public String getTotalintmaturity() {
		return totalintmaturity;
	}
	public void setTotalintmaturity(String totalintmaturity) {
		this.totalintmaturity = totalintmaturity;
	}
	public String getWtax() {
		return wtax;
	}
	public void setWtax(String wtax) {
		this.wtax = wtax;
	}
	public String getNetinterest() {
		return netinterest;
	}
	public void setNetinterest(String netinterest) {
		this.netinterest = netinterest;
	}
	public String getMatvalue() {
		return matvalue;
	}
	public void setMatvalue(String matvalue) {
		this.matvalue = matvalue;
	}
	public String getDocstamps() {
		return docstamps;
	}
	public void setDocstamps(String docstamps) {
		this.docstamps = docstamps;
	}
	public String getDisposition() {
		return disposition;
	}
	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}
	public String getDispositionint() {
		return dispositionint;
	}
	public void setDispositionint(String dispositionint) {
		this.dispositionint = dispositionint;
	}
	public String getDispositionintacctno() {
		return dispositionintacctno;
	}
	public void setDispositionintacctno(String dispositionintacctno) {
		this.dispositionintacctno = dispositionintacctno;
	}
	public String getTotalintcredited() {
		return totalintcredited;
	}
	public void setTotalintcredited(String totalintcredited) {
		this.totalintcredited = totalintcredited;
	}
	public String getTotalwithholdingtaxdebited() {
		return totalwithholdingtaxdebited;
	}
	public void setTotalwithholdingtaxdebited(String totalwithholdingtaxdebited) {
		this.totalwithholdingtaxdebited = totalwithholdingtaxdebited;
	}
	public String getModeofrelease() {
		return modeofrelease;
	}
	public void setModeofrelease(String modeofrelease) {
		this.modeofrelease = modeofrelease;
	}
	public String getTransactiontype() {
		return transactiontype;
	}
	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}
	public Date getTerminateon() {
		return terminateon;
	}
	public void setTerminateon(Date terminateon) {
		this.terminateon = terminateon;
	}
	public String getTotalnoofdays() {
		return totalnoofdays;
	}
	public void setTotalnoofdays(String totalnoofdays) {
		this.totalnoofdays = totalnoofdays;
	}
	public String getActualinterestearned() {
		return actualinterestearned;
	}
	public void setActualinterestearned(String actualinterestearned) {
		this.actualinterestearned = actualinterestearned;
	}
	public String getOntermearned() {
		return ontermearned;
	}
	public void setOntermearned(String ontermearned) {
		this.ontermearned = ontermearned;
	}
	public String getAddnlinterestearned() {
		return addnlinterestearned;
	}
	public void setAddnlinterestearned(String addnlinterestearned) {
		this.addnlinterestearned = addnlinterestearned;
	}
	public String getActualwithholdingtax() {
		return actualwithholdingtax;
	}
	public void setActualwithholdingtax(String actualwithholdingtax) {
		this.actualwithholdingtax = actualwithholdingtax;
	}
	public String getOntermtax() {
		return ontermtax;
	}
	public void setOntermtax(String ontermtax) {
		this.ontermtax = ontermtax;
	}
	public String getAddnlinteresttax() {
		return addnlinteresttax;
	}
	public void setAddnlinteresttax(String addnlinteresttax) {
		this.addnlinteresttax = addnlinteresttax;
	}
	public String getIntcreditrebate() {
		return intcreditrebate;
	}
	public void setIntcreditrebate(String intcreditrebate) {
		this.intcreditrebate = intcreditrebate;
	}
	public String getReversal() {
		return reversal;
	}
	public void setReversal(String reversal) {
		this.reversal = reversal;
	}
	public String getTotalintposting() {
		return totalintposting;
	}
	public void setTotalintposting(String totalintposting) {
		this.totalintposting = totalintposting;
	}
	public String getTotalwithholdingtaxposting() {
		return totalwithholdingtaxposting;
	}
	public void setTotalwithholdingtaxposting(String totalwithholdingtaxposting) {
		this.totalwithholdingtaxposting = totalwithholdingtaxposting;
	}
	public String getDocstamptax() {
		return docstamptax;
	}
	public void setDocstamptax(String docstamptax) {
		this.docstamptax = docstamptax;
	}
	public String getNetproceeds() {
		return netproceeds;
	}
	public void setNetproceeds(String netproceeds) {
		this.netproceeds = netproceeds;
	}
	public String getLesswidrawninterest() {
		return lesswidrawninterest;
	}
	public void setLesswidrawninterest(String lesswidrawninterest) {
		this.lesswidrawninterest = lesswidrawninterest;
	}
	public String getActualbalanceperaccount() {
		return actualbalanceperaccount;
	}
	public void setActualbalanceperaccount(String actualbalanceperaccount) {
		this.actualbalanceperaccount = actualbalanceperaccount;
	}

	
}
