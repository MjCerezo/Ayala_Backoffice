package com.casa.acct.forms;

import java.math.BigDecimal;
import java.util.Date;

public class TimeDepositAccountDetailForm {

	private BigDecimal taxrate;
	private BigDecimal lesswtax;
	private BigDecimal intrate;
	private BigDecimal matamt;
	private BigDecimal placementamt;
	private Date opendt;
	private String status;
	private String branchname;
	
	public BigDecimal getTaxrate() {
		return taxrate;
	}
	public BigDecimal getLesswtax() {
		return lesswtax;
	}
	public BigDecimal getIntrate() {
		return intrate;
	}
	public BigDecimal getMatamt() {
		return matamt;
	}
	public BigDecimal getPlacementamt() {
		return placementamt;
	}
	public Date getOpendt() {
		return opendt;
	}
	public String getStatus() {
		return status;
	}
	public void setTaxrate(BigDecimal taxrate) {
		this.taxrate = taxrate;
	}
	public void setLesswtax(BigDecimal lesswtax) {
		this.lesswtax = lesswtax;
	}
	public void setIntrate(BigDecimal intrate) {
		this.intrate = intrate;
	}
	public void setMatamt(BigDecimal matamt) {
		this.matamt = matamt;
	}
	public void setPlacementamt(BigDecimal placementamt) {
		this.placementamt = placementamt;
	}
	public void setOpendt(Date opendt) {
		this.opendt = opendt;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBranchname() {
		return branchname;
	}
	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}
	
}
