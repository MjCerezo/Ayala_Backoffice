/**
 * 
 */
package com.etel.casareports.form;

import java.math.BigDecimal;

/**
 * @author ETEL-LAPTOP07
 *
 */
public class CASAInterestForm {
	private String branch;
	private String cifno;
	private String product;
	private String accountno;
	private String accountname;
	private BigDecimal intrate;
	private BigDecimal adb;
	private BigDecimal intamount;
	private BigDecimal wtaxamount;
	private BigDecimal netamount;

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getCifno() {
		return cifno;
	}

	public void setCifno(String cifno) {
		this.cifno = cifno;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public BigDecimal getIntrate() {
		return intrate;
	}

	public void setIntrate(BigDecimal intrate) {
		this.intrate = intrate;
	}

	public BigDecimal getAdb() {
		return adb;
	}

	public void setAdb(BigDecimal adb) {
		this.adb = adb;
	}

	public BigDecimal getIntamount() {
		return intamount;
	}

	public void setIntamount(BigDecimal intamount) {
		this.intamount = intamount;
	}

	public BigDecimal getWtaxmount() {
		return wtaxamount;
	}

	public void setWtaxmount(BigDecimal wtaxmount) {
		this.wtaxamount = wtaxmount;
	}

	public BigDecimal getNetamount() {
		return netamount;
	}

	public void setNetamount(BigDecimal netamount) {
		this.netamount = netamount;
	}

}
