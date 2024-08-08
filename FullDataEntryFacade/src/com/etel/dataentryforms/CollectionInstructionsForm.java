package com.etel.dataentryforms;

import java.math.BigDecimal;

public class CollectionInstructionsForm {
	
	private boolean pdcflag;
	private boolean cashflag;
	private boolean checkflag;
	private boolean debitacctflag;
	private Integer pdcctr;
	private String bankbrstn;
	private String bankname;
	private String bankbr;
	private String accttype;
	private String acctno;
	private String appno;
	private BigDecimal payabletobank;
	private BigDecimal feesandcharges;
	private BigDecimal feesdeduct;
	private BigDecimal unpaidfees;
	private BigDecimal paidfees;
	private BigDecimal loanoffset;
	
	private String repaytypedesc;
	private String inttypedesc;
	private String intpaytypedesc;
	private String termcycdesc;
	private String intcycdesc;
	private String prinpaycycdesc;
	private String intpaycycdesc;
	
	private String seccode;
	
	public String getRepaytypedesc() {
		return repaytypedesc;
	}
	public void setRepaytypedesc(String repaytypedesc) {
		this.repaytypedesc = repaytypedesc;
	}
	public String getInttypedesc() {
		return inttypedesc;
	}
	public void setInttypedesc(String inttypedesc) {
		this.inttypedesc = inttypedesc;
	}
	public String getIntpaytypedesc() {
		return intpaytypedesc;
	}
	public void setIntpaytypedesc(String intpaytypedesc) {
		this.intpaytypedesc = intpaytypedesc;
	}
	public String getTermcycdesc() {
		return termcycdesc;
	}
	public void setTermcycdesc(String termcycdesc) {
		this.termcycdesc = termcycdesc;
	}
	public String getIntcycdesc() {
		return intcycdesc;
	}
	public void setIntcycdesc(String intcycdesc) {
		this.intcycdesc = intcycdesc;
	}
	public String getPrinpaycycdesc() {
		return prinpaycycdesc;
	}
	public void setPrinpaycycdesc(String prinpaycycdesc) {
		this.prinpaycycdesc = prinpaycycdesc;
	}
	public String getIntpaycycdesc() {
		return intpaycycdesc;
	}
	public void setIntpaycycdesc(String intpaycycdesc) {
		this.intpaycycdesc = intpaycycdesc;
	}
	public boolean isPdcflag() {
		return pdcflag;
	}
	public boolean isCashflag() {
		return cashflag;
	}
	public boolean isCheckflag() {
		return checkflag;
	}
	public boolean isDebitacctflag() {
		return debitacctflag;
	}
	public Integer getPdcctr() {
		return pdcctr;
	}
	public String getBankbrstn() {
		return bankbrstn;
	}
	public String getBankname() {
		return bankname;
	}
	public String getBankbr() {
		return bankbr;
	}
	public String getAccttype() {
		return accttype;
	}
	public String getAcctno() {
		return acctno;
	}
	public void setPdcflag(boolean pdcflag) {
		this.pdcflag = pdcflag;
	}
	public void setCashflag(boolean cashflag) {
		this.cashflag = cashflag;
	}
	public void setCheckflag(boolean checkflag) {
		this.checkflag = checkflag;
	}
	public void setDebitacctflag(boolean debitacctflag) {
		this.debitacctflag = debitacctflag;
	}
	public void setPdcctr(Integer pdcctr) {
		this.pdcctr = pdcctr;
	}
	public void setBankbrstn(String bankbrstn) {
		this.bankbrstn = bankbrstn;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public void setBankbr(String bankbr) {
		this.bankbr = bankbr;
	}
	public void setAccttype(String accttype) {
		this.accttype = accttype;
	}
	public void setAcctno(String acctno) {
		this.acctno = acctno;
	}
	public String getAppno() {
		return appno;
	}
	public void setAppno(String appno) {
		this.appno = appno;
	}
	public BigDecimal getPayabletobank() {
		return payabletobank;
	}
	public BigDecimal getFeesandcharges() {
		return feesandcharges;
	}
	public BigDecimal getFeesdeduct() {
		return feesdeduct;
	}
	public BigDecimal getUnpaidfees() {
		return unpaidfees;
	}
	public BigDecimal getPaidfees() {
		return paidfees;
	}
	public BigDecimal getLoanoffset() {
		return loanoffset;
	}
	public void setPayabletobank(BigDecimal payabletobank) {
		this.payabletobank = payabletobank;
	}
	public void setFeesandcharges(BigDecimal feesandcharges) {
		this.feesandcharges = feesandcharges;
	}
	public void setFeesdeduct(BigDecimal feesdeduct) {
		this.feesdeduct = feesdeduct;
	}
	public void setUnpaidfees(BigDecimal unpaidfees) {
		this.unpaidfees = unpaidfees;
	}
	public void setPaidfees(BigDecimal paidfees) {
		this.paidfees = paidfees;
	}
	public void setLoanoffset(BigDecimal loanoffset) {
		this.loanoffset = loanoffset;
	}
	@Override
	public String toString() {
		return "CollectionInstructionsForm [pdcflag=" + pdcflag + ", cashflag=" + cashflag + ", checkflag=" + checkflag
				+ ", debitacctflag=" + debitacctflag + ", pdcctr=" + pdcctr + ", bankbrstn=" + bankbrstn + ", bankname="
				+ bankname + ", bankbr=" + bankbr + ", accttype=" + accttype + ", acctno=" + acctno + ", appno=" + appno
				+ ", payabletobank=" + payabletobank + ", feesandcharges=" + feesandcharges + ", feesdeduct="
				+ feesdeduct + ", unpaidfees=" + unpaidfees + ", paidfees=" + paidfees + ", loanoffset=" + loanoffset
				+ "]";
	}
	public String getSeccode() {
		return seccode;
	}
	public void setSeccode(String seccode) {
		this.seccode = seccode;
	}
}
