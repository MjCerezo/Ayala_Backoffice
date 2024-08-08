package com.etel.dataentryforms;

import java.math.BigDecimal;
import java.util.Date;

public class LoanFeesForm {
	
	private String loanfeecode;
	private String particular;
	private String payableto;
	private BigDecimal amount;
	private String paid;
	private String disposition;
	private String orno;
	private Date ordate;
	
	public String getLoanfeecode() {
		return loanfeecode;
	}
	public String getParticular() {
		return particular;
	}
	public String getPayableto() {
		return payableto;
	}
	public BigDecimal getAmount() {
		return amount;
	}

	public String getDisposition() {
		return disposition;
	}
	public void setLoanfeecode(String loanfeecode) {
		this.loanfeecode = loanfeecode;
	}
	public void setParticular(String particular) {
		this.particular = particular;
	}
	public void setPayableto(String payableto) {
		this.payableto = payableto;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}
	public String getPaid() {
		return paid;
	}
	public void setPaid(String paid) {
		this.paid = paid;
	}
	public String getOrno() {
		return orno;
	}
	public void setOrno(String orno) {
		this.orno = orno;
	}
	public Date getOrdate() {
		return ordate;
	}
	public void setOrdate(Date ordate) {
		this.ordate = ordate;
	}
}
