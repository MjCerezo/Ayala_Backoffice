package com.etel.loancalc.forms;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentScheduleForm {

	private Date transDate;
	private String transType;
	private Double daysdiff;
	
	private BigDecimal intrate;
	private BigDecimal charges;
	private BigDecimal transAmount;
	private BigDecimal intEy;
	private BigDecimal principal;
	private BigDecimal cashflow;
	private BigDecimal prinbal;
	private BigDecimal loanbal;
	private BigDecimal uidey;
	private BigDecimal othercharge;
	
	public BigDecimal getOthercharge() {
		return othercharge;
	}
	public void setOthercharge(BigDecimal othercharge) {
		this.othercharge = othercharge;
	}
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public BigDecimal getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(BigDecimal transAmount) {
		this.transAmount = transAmount;
	}
	public BigDecimal getIntEy() {
		return intEy;
	}
	public void setIntEy(BigDecimal intEy) {
		this.intEy = intEy;
	}
	public BigDecimal getPrincipal() {
		return principal;
	}
	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}
	public BigDecimal getCashflow() {
		return cashflow;
	}
	public void setCashflow(BigDecimal cashflow) {
		this.cashflow = cashflow;
	}
	public BigDecimal getPrinbal() {
		return prinbal;
	}
	public void setPrinbal(BigDecimal prinbal) {
		this.prinbal = prinbal;
	}
	public BigDecimal getLoanbal() {
		return loanbal;
	}
	public void setLoanbal(BigDecimal loanbal) {
		this.loanbal = loanbal;
	}
	public BigDecimal getUidey() {
		return uidey;
	}
	public void setUidey(BigDecimal uidey) {
		this.uidey = uidey;
	}
	public Double getDaysdiff() {
		return daysdiff;
	}
	public void setDaysdiff(Double daysdiff) {
		this.daysdiff = daysdiff;
	}
	public BigDecimal getIntrate() {
		return intrate;
	}
	public void setIntrate(BigDecimal intrate) {
		this.intrate = intrate;
	}
	public BigDecimal getCharges() {
		return charges;
	}
	public void setCharges(BigDecimal charges) {
		this.charges = charges;
	}
}
