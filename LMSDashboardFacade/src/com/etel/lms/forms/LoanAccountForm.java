package com.etel.lms.forms;

import java.math.BigDecimal;
import java.util.Date;

public class LoanAccountForm {

	String applno;						// application number from LOS
	String pnno;
	Date txdate;						// Booking / Release Date
	String clientid;
	String fullname;
	String product;
	BigDecimal loanamount;
	Date fduedate;
	Date matdate;
	BigDecimal interestrate;
	String intperiod;
	int ppynum;
	int term;
	String termperiod;
	BigDecimal amortization;
	String accountno;
	String acctsts;
	
	
	public String getAcctsts() {
		return acctsts;
	}
	public void setAcctsts(String acctsts) {
		this.acctsts = acctsts;
	}
	public String getApplno() {
		return applno;
	}
	public void setApplno(String applno) {
		this.applno = applno;
	}
	public String getPnno() {
		return pnno;
	}
	public void setPnno(String pnno) {
		this.pnno = pnno;
	}
	public Date getTxdate() {
		return txdate;
	}
	public void setTxdate(Date txdate) {
		this.txdate = txdate;
	}
	public String getClientid() {
		return clientid;
	}
	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public BigDecimal getLoanamount() {
		return loanamount;
	}
	public void setLoanamount(BigDecimal loanamount) {
		this.loanamount = loanamount;
	}
	public Date getFduedate() {
		return fduedate;
	}
	public void setFduedate(Date fduedate) {
		this.fduedate = fduedate;
	}
	public Date getMatdate() {
		return matdate;
	}
	public void setMatdate(Date matdate) {
		this.matdate = matdate;
	}
	public BigDecimal getInterestrate() {
		return interestrate;
	}
	public void setInterestrate(BigDecimal interestrate) {
		this.interestrate = interestrate;
	}
	public String getIntperiod() {
		return intperiod;
	}
	public void setIntperiod(String intperiod) {
		this.intperiod = intperiod;
	}
	public int getPpynum() {
		return ppynum;
	}
	public void setPpynum(int ppynum) {
		this.ppynum = ppynum;
	}
	public int getTerm() {
		return term;
	}
	public void setTerm(int term) {
		this.term = term;
	}
	public String getTermperiod() {
		return termperiod;
	}
	public void setTermperiod(String termperiod) {
		this.termperiod = termperiod;
	}
	public BigDecimal getAmortization() {
		return amortization;
	}
	public void setAmortization(BigDecimal amortization) {
		this.amortization = amortization;
	}
	public String getAccountno() {
		return accountno;
	}
	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}
	
}
