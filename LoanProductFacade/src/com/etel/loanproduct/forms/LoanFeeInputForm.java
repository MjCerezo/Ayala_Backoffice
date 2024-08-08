package com.etel.loanproduct.forms;

import java.math.BigDecimal;
import java.util.Date;

public class LoanFeeInputForm {

	private String appno;
	private BigDecimal loanamount;
	private BigDecimal principalvalue;
	private BigDecimal interestvalue;
	private BigDecimal pastdueamount;
	private BigDecimal maturityval;
	private BigDecimal termval;
	private String termperiod;
	private Date bookingdate;
	private Date matdate;
	private String refferaltype;
	private String ciftype;
	
	private String businesstype;
	private String prodcode;
	
	
	public String getProdcode() {
		return prodcode;
	}
	public void setProdcode(String prodcode) {
		this.prodcode = prodcode;
	}
	public String getAppno() {
		return appno;
	}
	public void setAppno(String appno) {
		this.appno = appno;
	}
	public BigDecimal getLoanamount() {
		return loanamount;
	}
	public void setLoanamount(BigDecimal loanamount) {
		this.loanamount = loanamount;
	}
	public BigDecimal getPrincipalvalue() {
		return principalvalue;
	}
	public void setPrincipalvalue(BigDecimal principalvalue) {
		this.principalvalue = principalvalue;
	}
	public BigDecimal getInterestvalue() {
		return interestvalue;
	}
	public void setInterestvalue(BigDecimal interestvalue) {
		this.interestvalue = interestvalue;
	}
	public BigDecimal getPastdueamount() {
		return pastdueamount;
	}
	public void setPastdueamount(BigDecimal pastdueamount) {
		this.pastdueamount = pastdueamount;
	}
	public BigDecimal getMaturityval() {
		return maturityval;
	}
	public void setMaturityval(BigDecimal maturityval) {
		this.maturityval = maturityval;
	}
	public BigDecimal getTermval() {
		return termval;
	}
	public void setTermval(BigDecimal termval) {
		this.termval = termval;
	}
	public String getTermperiod() {
		return termperiod;
	}
	public void setTermperiod(String termperiod) {
		this.termperiod = termperiod;
	}
	public Date getBookingdate() {
		return bookingdate;
	}
	public void setBookingdate(Date bookingdate) {
		this.bookingdate = bookingdate;
	}
	public Date getMatdate() {
		return matdate;
	}
	public void setMatdate(Date matdate) {
		this.matdate = matdate;
	}
	public String getRefferaltype() {
		return refferaltype;
	}
	public void setRefferaltype(String refferaltype) {
		this.refferaltype = refferaltype;
	}
	public String getCiftype() {
		return ciftype;
	}
	public void setCiftype(String ciftype) {
		this.ciftype = ciftype;
	}
	public String getBusinesstype() {
		return businesstype;
	}
	public void setBusinesstype(String businesstype) {
		this.businesstype = businesstype;
	}
	
}
