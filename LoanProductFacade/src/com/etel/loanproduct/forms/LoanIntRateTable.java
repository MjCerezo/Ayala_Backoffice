package com.etel.loanproduct.forms;

import java.math.BigDecimal;

public class LoanIntRateTable {
	
	 private String productcode;
	 private Integer loanterm;
	 private BigDecimal rate;
	 
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public Integer getLoanterm() {
		return loanterm;
	}
	public void setLoanterm(Integer loanterm) {
		this.loanterm = loanterm;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	} 
   
	 
}
