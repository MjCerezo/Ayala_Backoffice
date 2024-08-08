package com.etel.multitx.form;
import java.math.BigDecimal;
import java.util.Date;
public class MultipleTransDataForm {
	String multitxrefno;
	BigDecimal amount;
	String cifno;
	String fullname;
	String fulladdress1;
	String createdby;
	Date txvaldt;
	public String getMultitxrefno() {
		return multitxrefno;
	}
	public void setMultitxrefno(String multitxrefno) {
		this.multitxrefno = multitxrefno;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getCifno() {
		return cifno;
	}
	public void setCifno(String cifno) {
		this.cifno = cifno;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getFulladdress1() {
		return fulladdress1;
	}
	public void setFulladdress1(String fulladdress1) {
		this.fulladdress1 = fulladdress1;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public Date getTxvaldt() {
		return txvaldt;
	}
	public void setTxvaldt(Date txvaldt) {
		this.txvaldt = txvaldt;
	}
	
	
}
