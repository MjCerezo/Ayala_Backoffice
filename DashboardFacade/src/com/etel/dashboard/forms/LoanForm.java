/**
 * 
 */
package com.etel.dashboard.forms;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ETEL-LAPTOP19
 *
 */
public class LoanForm {
	
	String txrefno;
	Date txdate;
	Date txstatusdate;
	String clientid;
	String fullname;
	String product;
	BigDecimal loanamount;
	String paycycle;
	BigDecimal amortization;
	public String getTxrefno() {
		return txrefno;
	}
	public void setTxrefno(String txrefno) {
		this.txrefno = txrefno;
	}
	public Date getTxdate() {
		return txdate;
	}
	public void setTxdate(Date txdate) {
		this.txdate = txdate;
	}
	public Date getTxstatusdate() {
		return txstatusdate;
	}
	public void setTxstatusdate(Date txstatusdate) {
		this.txstatusdate = txstatusdate;
	}
	public String getClientid() {
		return clientid;
	}
	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public BigDecimal getLoanamount() {
		return loanamount;
	}
	public void setLoanamount(BigDecimal loanamount) {
		this.loanamount = loanamount;
	}
	public String getPaycycle() {
		return paycycle;
	}
	public void setPaycycle(String paycycle) {
		this.paycycle = paycycle;
	}
	public BigDecimal getAmortization() {
		return amortization;
	}
	public void setAmortization(BigDecimal amortization) {
		this.amortization = amortization;
	}
	
	
}
