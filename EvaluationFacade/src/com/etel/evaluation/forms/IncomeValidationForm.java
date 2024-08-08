/**
 * 
 */
package com.etel.evaluation.forms;

import java.math.BigDecimal;

/**
 * @author Mheanne
 *
 */
public class IncomeValidationForm {
	private BigDecimal totalgrossincome = BigDecimal.ZERO;
	
	private BigDecimal totaldeductions = BigDecimal.ZERO;
	private BigDecimal netpay = BigDecimal.ZERO;
	private BigDecimal monthlyamort = BigDecimal.ZERO;
	private BigDecimal takehomepay = BigDecimal.ZERO;
	
	public BigDecimal getTotalgrossincome() {
		return totalgrossincome;
	}
	public void setTotalgrossincome(BigDecimal totalgrossincome) {
		this.totalgrossincome = totalgrossincome;
	}
	public BigDecimal getTotaldeductions() {
		return totaldeductions;
	}
	public void setTotaldeductions(BigDecimal totaldeductions) {
		this.totaldeductions = totaldeductions;
	}
	public BigDecimal getNetpay() {
		return netpay;
	}
	public void setNetpay(BigDecimal netpay) {
		this.netpay = netpay;
	}
	public BigDecimal getMonthlyamort() {
		return monthlyamort;
	}
	public void setMonthlyamort(BigDecimal monthlyamort) {
		this.monthlyamort = monthlyamort;
	}
	public BigDecimal getTakehomepay() {
		return takehomepay;
	}
	public void setTakehomepay(BigDecimal takehomepay) {
		this.takehomepay = takehomepay;
	}
}
