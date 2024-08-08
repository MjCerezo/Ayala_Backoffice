package com.etel.glentries.forms;

import java.math.BigDecimal;

public class GLEntriesForm {

	private String glaccountno;
	private String gldesc;
	private BigDecimal debit;
	private BigDecimal credit;
	
	public String getGlaccountno() {
		return glaccountno;
	}
	public void setGlaccountno(String glaccountno) {
		this.glaccountno = glaccountno;
	}
	public String getGldesc() {
		return gldesc;
	}
	public void setGldesc(String gldesc) {
		this.gldesc = gldesc;
	}
	public BigDecimal getDebit() {
		return debit;
	}
	public void setDebit(BigDecimal debit) {
		this.debit = debit;
	}
	public BigDecimal getCredit() {
		return credit;
	}
	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}
	
}
