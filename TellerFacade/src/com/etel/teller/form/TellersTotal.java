/**
 * 
 */
package com.etel.teller.form;

import java.math.BigDecimal;

/**
 * @author ETEL-LAPTOP19
 *
 */
public class TellersTotal {
	String txcode;
	String txname;
	int totalCount;
	BigDecimal totalAmt;
	int listorder;
	int txoper;

	public String getTxcode() {
		return txcode;
	}

	public void setTxcode(String txcode) {
		this.txcode = txcode;
	}

	public String getTxname() {
		return txname;
	}

	public void setTxname(String txname) {
		this.txname = txname;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public BigDecimal getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}

	public int getListorder() {
		return listorder;
	}

	public void setListorder(int listorder) {
		this.listorder = listorder;
	}

	public int getTxoper() {
		return txoper;
	}

	public void setTxoper(int txoper) {
		this.txoper = txoper;
	}

}
