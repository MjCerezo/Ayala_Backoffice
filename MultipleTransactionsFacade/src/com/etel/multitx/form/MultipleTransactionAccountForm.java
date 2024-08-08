/**
 * 
 */
package com.etel.multitx.form;

import java.math.BigDecimal;

/**
 * @author ETEL-LAPTOP07
 *
 */
public class MultipleTransactionAccountForm {

	String accountno;
	String accounttype;
	String productcode;
	String productname;
	String subproductcode;
	String subproductname;
	Boolean wpassbook;
	String name;
	BigDecimal accountbalance;
	BigDecimal availablebalance;
	BigDecimal placementamount;

	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}

	public String getProductcode() {
		return productcode;
	}

	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getSubproductcode() {
		return subproductcode;
	}

	public void setSubproductcode(String subproductcode) {
		this.subproductcode = subproductcode;
	}

	public String getSubproductname() {
		return subproductname;
	}

	public void setSubproductname(String subproductname) {
		this.subproductname = subproductname;
	}

	public Boolean getWpassbook() {
		return wpassbook;
	}

	public void setWpassbook(Boolean wpassbook) {
		this.wpassbook = wpassbook;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getAccountbalance() {
		return accountbalance;
	}

	public void setAccountbalance(BigDecimal accountbalance) {
		this.accountbalance = accountbalance;
	}

	public BigDecimal getAvailablebalance() {
		return availablebalance;
	}

	public void setAvailablebalance(BigDecimal availablebalance) {
		this.availablebalance = availablebalance;
	}

	public BigDecimal getPlacementamount() {
		return placementamount;
	}

	public void setPlacementamount(BigDecimal placementamount) {
		this.placementamount = placementamount;
	}

}
