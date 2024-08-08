package com.casa.acct.forms;

import java.math.BigDecimal;
import java.util.Date;

public class TimeDepositCertForm {

	private String controlno;
	private String accountno;
	private String name;
	private String accountstatus;
	private Date datebook;
	private Date matdt;
	private String branch;
	private BigDecimal accountbal;
	private Integer term;
	private BigDecimal intrate;
	private BigDecimal taxrate;
	private BigDecimal lesswtax;
	private BigDecimal matamt;
	private BigDecimal placementamt;

	public String getControlno() {
		return controlno;
	}

	public void setControlno(String controlno) {
		this.controlno = controlno;
	}

	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccountstatus() {
		return accountstatus;
	}

	public void setAccountstatus(String accountstatus) {
		this.accountstatus = accountstatus;
	}

	public Date getDatebook() {
		return datebook;
	}

	public void setDatebook(Date datebook) {
		this.datebook = datebook;
	}

	public Date getMatdt() {
		return matdt;
	}

	public void setMatdt(Date matdt) {
		this.matdt = matdt;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public BigDecimal getAccountbal() {
		return accountbal;
	}

	public void setAccountbal(BigDecimal accountbal) {
		this.accountbal = accountbal;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public BigDecimal getIntrate() {
		return intrate;
	}

	public void setIntrate(BigDecimal intrate) {
		this.intrate = intrate;
	}

	public BigDecimal getTaxrate() {
		return taxrate;
	}

	public void setTaxrate(BigDecimal taxrate) {
		this.taxrate = taxrate;
	}

	public BigDecimal getLesswtax() {
		return lesswtax;
	}

	public void setLesswtax(BigDecimal lesswtax) {
		this.lesswtax = lesswtax;
	}

	public BigDecimal getMatamt() {
		return matamt;
	}

	public void setMatamt(BigDecimal matamt) {
		this.matamt = matamt;
	}

	public BigDecimal getPlacementamt() {
		return placementamt;
	}

	public void setPlacementamt(BigDecimal placementamt) {
		this.placementamt = placementamt;
	}
	
	
}
