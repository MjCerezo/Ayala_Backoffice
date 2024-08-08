package com.etel.loanproduct.forms;

import java.math.BigDecimal;

public class BOSForm {
	
	private String boscode;
	private String companyname;
	private BigDecimal maxloanableamt;
	public String getBoscode() {
		return boscode;
	}
	public void setBoscode(String boscode) {
		this.boscode = boscode;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public BigDecimal getMaxloanableamt() {
		return maxloanableamt;
	}
	public void setMaxloanableamt(BigDecimal maxloanableamt) {
		this.maxloanableamt = maxloanableamt;
	}
	
	
}
