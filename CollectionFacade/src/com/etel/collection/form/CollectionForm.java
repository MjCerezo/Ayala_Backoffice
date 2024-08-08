/**
 * 
 */
package com.etel.collection.form;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ETEL-LAPTOP07
 *
 */
public class CollectionForm {

	private String accountNumber;
	private String accountName;
	private String productName;
	private Date valuedate;
	private BigDecimal amount;
	private String remarks;
	private String collector;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getValuedate() {
		return valuedate;
	}

	public void setValuedate(Date valuedate) {
		this.valuedate = valuedate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCollector() {
		return collector;
	}

	public void setCollector(String collector) {
		this.collector = collector;
	}

}
