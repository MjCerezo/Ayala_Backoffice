package com.etel.loancalc.forms;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class LoanCalculatorForm {

	private String loanappno;
	private BigDecimal loanamount;
	private BigDecimal term;
	private String termcycle;

	private String repaytype;
	private String cycle;

	private BigDecimal rate;
	private String intcycle;

	private String inttype;

	private Date bookingdate;
	private Date origbookingdate;
	private Date firstbookingdate;

	private int ppynum;

	private BigDecimal advanceinterest;

	private BigDecimal docstamp;
	private BigDecimal taInsurance;
	private BigDecimal clInsurance;
	private BigDecimal loanappfee;
	private int noofdocs;
	private BigDecimal notarialfee;
	private BigDecimal offsetamt;
	private BigDecimal othercharges;
	private BigDecimal servicefee;

	private String strothercharges;
	private BigDecimal processingFee;

	private BigDecimal totalcharges;
	private BigDecimal netproceeds;
	private String loanpurpose;

	private String producttype;
	private String offsetpn;

	private BigDecimal amountfinanced;
	private BigDecimal eyrate;
	private BigDecimal mir;
	private BigDecimal eir;

	private List<PaymentScheduleForm> paymentsched;
	private List<PDCForm> pdclist;

	private BigDecimal bkintey;
	private BigDecimal prinbal;
	private BigDecimal loanbal;
	private BigDecimal interestdue;
	private BigDecimal amortfee;
	private BigDecimal lastamort;

	private Date maturitydate;

	private int dsttype;
	// private boolean isNFWaived;

	private int intchargeday;
	private BigDecimal intchargeamt;

	private String intpaytype;
	private String prinpaycycle;
	private String intpaycycle;
	private BigDecimal maturityvalue;
	private BigDecimal totalfeescharges;
	private BigDecimal totalloanoffsetamt;
	private BigDecimal downpayment;
	private BigDecimal downpaymentpcnt;
	private BigDecimal retentionamt;
	private BigDecimal retentionpcnt;
	private String productgroup;
	private BigDecimal graceperiod;
	private BigDecimal lpcrate;
	private String cfrefno;

	// Code Desc
	private String repaytypedesc;
	private String inttypedesc;
	private String intpaytypedesc;
	private String termcycdesc;
	private String intcycdesc;
	private String prinpaycycdesc;
	private String intpaycycdesc;

	private BigDecimal totalfeeschargesbilled;

	// Added 7.15.2019
	private Integer noofintpay;
	private Integer noofprinpay;
	private Integer noofadvint;
	private Integer noofadvprin;
	private String duedaterule;
	private BigDecimal advintamt;
	private BigDecimal advprinamt;
	private String prodcode;
	
	private BigDecimal totalattachment;
	
	@Override
	public String toString() {
		return "LoanCalculatorForm [loanappno=" + loanappno + ", loanamount=" + loanamount + ", term=" + term
				+ ", termcycle=" + termcycle + ", repaytype=" + repaytype + ", cycle=" + cycle + ", rate=" + rate
				+ ", intcycle=" + intcycle + ", inttype=" + inttype + ", bookingdate=" + bookingdate
				+ ", origbookingdate=" + origbookingdate + ", firstbookingdate=" + firstbookingdate + ", ppynum="
				+ ppynum + ", advanceinterest=" + advanceinterest + ", docstamp=" + docstamp + ", taInsurance="
				+ taInsurance + ", clInsurance=" + clInsurance + ", loanappfee=" + loanappfee + ", noofdocs=" + noofdocs
				+ ", notarialfee=" + notarialfee + ", offsetamt=" + offsetamt + ", othercharges=" + othercharges
				+ ", servicefee=" + servicefee + ", strothercharges=" + strothercharges + ", processingFee="
				+ processingFee + ", totalcharges=" + totalcharges + ", netproceeds=" + netproceeds + ", loanpurpose="
				+ loanpurpose + ", producttype=" + producttype + ", offsetpn=" + offsetpn + ", amountfinanced="
				+ amountfinanced + ", eyrate=" + eyrate + ", mir=" + mir + ", eir=" + eir + ", paymentsched="
				+ paymentsched + ", pdclist=" + pdclist + ", bkintey=" + bkintey + ", prinbal=" + prinbal + ", loanbal="
				+ loanbal + ", interestdue=" + interestdue + ", amortfee=" + amortfee + ", lastamort=" + lastamort
				+ ", maturitydate=" + maturitydate + ", dsttype=" + dsttype + ", intchargeday=" + intchargeday
				+ ", intchargeamt=" + intchargeamt + ", intpaytype=" + intpaytype + ", prinpaycycle=" + prinpaycycle
				+ ", intpaycycle=" + intpaycycle + ", maturityvalue=" + maturityvalue + ", totalfeescharges="
				+ totalfeescharges + ", totalloanoffsetamt=" + totalloanoffsetamt + ", downpayment=" + downpayment
				+ ", downpaymentpcnt=" + downpaymentpcnt + ", retentionamt=" + retentionamt + ", retentionpcnt="
				+ retentionpcnt + ", productgroup=" + productgroup + ", graceperiod=" + graceperiod + ", lpcrate="
				+ lpcrate + ", cfrefno=" + cfrefno + ", repaytypedesc=" + repaytypedesc + ", inttypedesc=" + inttypedesc
				+ ", intpaytypedesc=" + intpaytypedesc + ", termcycdesc=" + termcycdesc + ", intcycdesc=" + intcycdesc
				+ ", prinpaycycdesc=" + prinpaycycdesc + ", intpaycycdesc=" + intpaycycdesc
				+ ", totalfeeschargesbilled=" + totalfeeschargesbilled + ", noofintpay=" + noofintpay + ", noofprinpay="
				+ noofprinpay + ", noofadvint=" + noofadvint + ", noofadvprin=" + noofadvprin + ", duedaterule="
				+ duedaterule + ", advintamt=" + advintamt + ", advprinamt=" + advprinamt + ", totalamortization=\" + totalattachment + \" prodcode=" + prodcode
				+ "]";
	}


	public BigDecimal getTotalattachment() {
		return totalattachment;
	}


	public void setTotalattachment(BigDecimal totalattachment) {
		this.totalattachment = totalattachment;
	}


	public Date getOrigbookingdate() {
		return origbookingdate;
	}

	public void setOrigbookingdate(Date origbookingdate) {
		this.origbookingdate = origbookingdate;
	}

	public String getProdcode() {
		return prodcode;
	}

	public void setProdcode(String prodcode) {
		this.prodcode = prodcode;
	}

	public Integer getNoofintpay() {
		return noofintpay;
	}

	public void setNoofintpay(Integer noofintpay) {
		this.noofintpay = noofintpay;
	}

	public Integer getNoofprinpay() {
		return noofprinpay;
	}

	public void setNoofprinpay(Integer noofprinpay) {
		this.noofprinpay = noofprinpay;
	}

	public Integer getNoofadvint() {
		return noofadvint;
	}

	public void setNoofadvint(Integer noofadvint) {
		this.noofadvint = noofadvint;
	}

	public Integer getNoofadvprin() {
		return noofadvprin;
	}

	public void setNoofadvprin(Integer noofadvprin) {
		this.noofadvprin = noofadvprin;
	}

	public String getDuedaterule() {
		return duedaterule;
	}

	public void setDuedaterule(String duedaterule) {
		this.duedaterule = duedaterule;
	}

	public BigDecimal getAdvintamt() {
		return advintamt;
	}

	public void setAdvintamt(BigDecimal advintamt) {
		this.advintamt = advintamt;
	}

	public BigDecimal getAdvprinamt() {
		return advprinamt;
	}

	public void setAdvprinamt(BigDecimal advprinamt) {
		this.advprinamt = advprinamt;
	}

	public BigDecimal getTotalfeeschargesbilled() {
		return totalfeeschargesbilled;
	}

	public void setTotalfeeschargesbilled(BigDecimal totalfeeschargesbilled) {
		this.totalfeeschargesbilled = totalfeeschargesbilled;
	}

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

	public BigDecimal getGraceperiod() {
		return graceperiod;
	}

	public void setGraceperiod(BigDecimal graceperiod) {
		this.graceperiod = graceperiod;
	}

	public BigDecimal getLpcrate() {
		return lpcrate;
	}

	public void setLpcrate(BigDecimal lpcrate) {
		this.lpcrate = lpcrate;
	}

	public String getProductgroup() {
		return productgroup;
	}

	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
	}

	public BigDecimal getDownpaymentpcnt() {
		return downpaymentpcnt;
	}

	public void setDownpaymentpcnt(BigDecimal downpaymentpcnt) {
		this.downpaymentpcnt = downpaymentpcnt;
	}

	public BigDecimal getRetentionamt() {
		return retentionamt;
	}

	public void setRetentionamt(BigDecimal retentionamt) {
		this.retentionamt = retentionamt;
	}

	public BigDecimal getRetentionpcnt() {
		return retentionpcnt;
	}

	public void setRetentionpcnt(BigDecimal retentionpcnt) {
		this.retentionpcnt = retentionpcnt;
	}

	public BigDecimal getTotalfeescharges() {
		return totalfeescharges;
	}

	public void setTotalfeescharges(BigDecimal totalfeescharges) {
		this.totalfeescharges = totalfeescharges;
	}

	public BigDecimal getTotalloanoffsetamt() {
		return totalloanoffsetamt;
	}

	public void setTotalloanoffsetamt(BigDecimal totalloanoffsetamt) {
		this.totalloanoffsetamt = totalloanoffsetamt;
	}

	public BigDecimal getMaturityvalue() {
		return maturityvalue;
	}

	public void setMaturityvalue(BigDecimal maturityvalue) {
		this.maturityvalue = maturityvalue;
	}

	public String getPrinpaycycle() {
		return prinpaycycle;
	}

	public void setPrinpaycycle(String prinpaycycle) {
		this.prinpaycycle = prinpaycycle;
	}

	public String getIntpaycycle() {
		return intpaycycle;
	}

	public void setIntpaycycle(String intpaycycle) {
		this.intpaycycle = intpaycycle;
	}

	public String getIntpaytype() {
		return intpaytype;
	}

	public void setIntpaytype(String intpaytype) {
		this.intpaytype = intpaytype;
	}

	public int getIntchargeday() {
		return intchargeday;
	}

	public void setIntchargeday(int intchargeday) {
		this.intchargeday = intchargeday;
	}

	public BigDecimal getIntchargeamt() {
		return intchargeamt;
	}

	public void setIntchargeamt(BigDecimal intchargeamt) {
		this.intchargeamt = intchargeamt;
	}

	public Date getMaturitydate() {
		return maturitydate;
	}

	public void setMaturitydate(Date maturitydate) {
		this.maturitydate = maturitydate;
	}

	public BigDecimal getLoanamount() {
		return loanamount;
	}

	public void setLoanamount(BigDecimal loanamount) {
		this.loanamount = loanamount;
	}

	public BigDecimal getDownpayment() {
		return downpayment;
	}

	public void setDownpayment(BigDecimal downpayment) {
		this.downpayment = downpayment;
	}

	public BigDecimal getTerm() {
		return term;
	}

	public void setTerm(BigDecimal term) {
		this.term = term;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public Date getBookingdate() {
		return bookingdate;
	}

	public void setBookingdate(Date bookingdate) {
		this.bookingdate = bookingdate;
	}

	public Date getFirstbookingdate() {
		return firstbookingdate;
	}

	public void setFirstbookingdate(Date firstbookingdate) {
		this.firstbookingdate = firstbookingdate;
	}

	public BigDecimal getAdvanceinterest() {
		return advanceinterest;
	}

	public void setAdvanceinterest(BigDecimal advanceinterest) {
		this.advanceinterest = advanceinterest;
	}

	public BigDecimal getDocstamp() {
		return docstamp;
	}

	public void setDocstamp(BigDecimal docstamp) {
		this.docstamp = docstamp;
	}

	public BigDecimal getOthercharges() {
		return othercharges;
	}

	public void setOthercharges(BigDecimal othercharges) {
		this.othercharges = othercharges;
	}

	public BigDecimal getAmountfinanced() {
		return amountfinanced;
	}

	public void setAmountfinanced(BigDecimal amountfinanced) {
		this.amountfinanced = amountfinanced;
	}

	public BigDecimal getEyrate() {
		return eyrate;
	}

	public BigDecimal getEir() {
		return eir;
	}

	public void setEir(BigDecimal eir) {
		this.eir = eir;
	}

	public void setEyrate(BigDecimal eyrate) {
		this.eyrate = eyrate;
	}

	public List<PaymentScheduleForm> getPaymentsched() {
		return paymentsched;
	}

	public void setPaymentsched(List<PaymentScheduleForm> paymentsched) {
		this.paymentsched = paymentsched;
	}

	public BigDecimal getBkintey() {
		return bkintey;
	}

	public void setBkintey(BigDecimal bkintey) {
		this.bkintey = bkintey;
	}

	public int getPpynum() {
		return ppynum;
	}

	public void setPpynum(int ppynum) {
		this.ppynum = ppynum;
	}

	public BigDecimal getPrinbal() {
		return prinbal;
	}

	public void setPrinbal(BigDecimal prinbal) {
		this.prinbal = prinbal;
	}

	public BigDecimal getInterestdue() {
		return interestdue;
	}

	public void setInterestdue(BigDecimal interestdue) {
		this.interestdue = interestdue;
	}

	public BigDecimal getAmortfee() {
		return amortfee;
	}

	public void setAmortfee(BigDecimal amortfee) {
		this.amortfee = amortfee;
	}

	public String getProducttype() {
		return producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}

	public BigDecimal getTaInsurance() {
		return taInsurance;
	}

	public void setTaInsurance(BigDecimal taInsurance) {
		this.taInsurance = taInsurance;
	}

	public BigDecimal getClInsurance() {
		return clInsurance;
	}

	public void setClInsurance(BigDecimal clInsurance) {
		this.clInsurance = clInsurance;
	}

	public BigDecimal getLoanappfee() {
		return loanappfee;
	}

	public void setLoanappfee(BigDecimal loanappfee) {
		this.loanappfee = loanappfee;
	}

	public BigDecimal getNotarialfee() {
		return notarialfee;
	}

	public void setNotarialfee(BigDecimal notarialfee) {
		this.notarialfee = notarialfee;
	}

	public BigDecimal getOffsetamt() {
		return offsetamt;
	}

	public void setOffsetamt(BigDecimal offsetamt) {
		this.offsetamt = offsetamt;
	}

	public BigDecimal getTotalcharges() {
		return totalcharges;
	}

	public void setTotalcharges(BigDecimal totalcharges) {
		this.totalcharges = totalcharges;
	}

	public BigDecimal getNetproceeds() {
		return netproceeds;
	}

	public void setNetproceeds(BigDecimal netproceeds) {
		this.netproceeds = netproceeds;
	}

	public BigDecimal getProcessingFee() {
		return processingFee;
	}

	public void setProcessingFee(BigDecimal processingFee) {
		this.processingFee = processingFee;
	}

	public String getOffsetpn() {
		return offsetpn;
	}

	public void setOffsetpn(String offsetpn) {
		this.offsetpn = offsetpn;
	}

	public String getStrothercharges() {
		return strothercharges;
	}

	public void setStrothercharges(String strothercharges) {
		this.strothercharges = strothercharges;
	}

	public String getLoanpurpose() {
		return loanpurpose;
	}

	public void setLoanpurpose(String loanpurpose) {
		this.loanpurpose = loanpurpose;
	}

	public String getTermcycle() {
		return termcycle;
	}

	public void setTermcycle(String termcycle) {
		this.termcycle = termcycle;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getRepaytype() {
		return repaytype;
	}

	public void setRepaytype(String repaytype) {
		this.repaytype = repaytype;
	}

	public BigDecimal getLastamort() {
		return lastamort;
	}

	public void setLastamort(BigDecimal lastamort) {
		this.lastamort = lastamort;
	}

	public BigDecimal getMir() {
		return mir;
	}

	public void setMir(BigDecimal mir) {
		this.mir = mir;
	}

	public String getIntcycle() {
		return intcycle;
	}

	public void setIntcycle(String intcycle) {
		this.intcycle = intcycle;
	}

	public String getInttype() {
		return inttype;
	}

	public void setInttype(String inttype) {
		this.inttype = inttype;
	}

	public int getDsttype() {
		return dsttype;
	}

	public void setDsttype(int dsttype) {
		this.dsttype = dsttype;
	}

	public List<PDCForm> getPdclist() {
		return pdclist;
	}

	public void setPdclist(List<PDCForm> pdclist) {
		this.pdclist = pdclist;
	}

	public String getLoanappno() {
		return loanappno;
	}

	public void setLoanappno(String loanappno) {
		this.loanappno = loanappno;
	}

	public BigDecimal getLoanbal() {
		return loanbal;
	}

	public void setLoanbal(BigDecimal loanbal) {
		this.loanbal = loanbal;
	}

	public int getNoofdocs() {
		return noofdocs;
	}

	public void setNoofdocs(int noofdocs) {
		this.noofdocs = noofdocs;
	}

	public BigDecimal getServicefee() {
		return servicefee;
	}

	public void setServicefee(BigDecimal servicefee) {
		this.servicefee = servicefee;
	}

	public String getCfrefno() {
		return cfrefno;
	}

	public void setCfrefno(String cfrefno) {
		this.cfrefno = cfrefno;
	}

}
