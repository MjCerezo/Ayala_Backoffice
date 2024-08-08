package com.etel.loancalc.forms;

import java.math.BigDecimal;
import java.util.Date;

public class PDCForm {

	private String loanappno;
	private String cifno;
	private String targetpn;
	
	private String purpose;
	private String brstn;
	private String checkno;
	private String checkaccountno;
	private Date checkdate;
	private String bank;
	private BigDecimal checkamt;
	private String checkaccountname;
	
	public String getLoanappno() {
		return loanappno;
	}
	public void setLoanappno(String loanappno) {
		this.loanappno = loanappno;
	}
	public String getCifno() {
		return cifno;
	}
	public void setCifno(String cifno) {
		this.cifno = cifno;
	}
	public String getTargetpn() {
		return targetpn;
	}
	public void setTargetpn(String targetpn) {
		this.targetpn = targetpn;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getBrstn() {
		return brstn;
	}
	public void setBrstn(String brstn) {
		this.brstn = brstn;
	}
	public String getCheckno() {
		return checkno;
	}
	public void setCheckno(String checkno) {
		this.checkno = checkno;
	}
	public String getCheckaccountno() {
		return checkaccountno;
	}
	public void setCheckaccountno(String checkaccountno) {
		this.checkaccountno = checkaccountno;
	}
	public Date getCheckdate() {
		return checkdate;
	}
	public void setCheckdate(Date checkdate) {
		this.checkdate = checkdate;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public BigDecimal getCheckamt() {
		return checkamt;
	}
	public void setCheckamt(BigDecimal checkamt) {
		this.checkamt = checkamt;
	}
	public String getCheckaccountname() {
		return checkaccountname;
	}
	public void setCheckaccountname(String checkaccountname) {
		this.checkaccountname = checkaccountname;
	}
	
}
