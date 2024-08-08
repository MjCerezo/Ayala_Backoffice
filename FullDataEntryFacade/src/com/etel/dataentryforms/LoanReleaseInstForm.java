package com.etel.dataentryforms;

import java.math.BigDecimal;
import java.util.Date;

public class LoanReleaseInstForm {
	
	private int id;
	private String appno;
	private String pnno;
	private String checkno;
	private String checkacctno;
	private String checkbank;
	private String checkbrstn;
	private String checkbranch;
	private Date checkdate;
	private String creditbank;
	private String creditaccttype;
	private String creditacctno;
	private String creditbrstn;
	private String creditbranch;
	private String payeename;
	private BigDecimal amount;
	private String disposition;
	private String remarks;	
	private String glcode;
	
	public String getGlcode() {
		return glcode;
	}
	public void setGlcode(String glcode) {
		this.glcode = glcode;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAppno() {
		return appno;
	}
	public String getPnno() {
		return pnno;
	}
	public String getCheckno() {
		return checkno;
	}
	public String getCheckacctno() {
		return checkacctno;
	}
	public String getCheckbank() {
		return checkbank;
	}
	public String getCheckbrstn() {
		return checkbrstn;
	}
	public String getCheckbranch() {
		return checkbranch;
	}
	public Date getCheckdate() {
		return checkdate;
	}
	public String getCreditbank() {
		return creditbank;
	}
	public String getCreditaccttype() {
		return creditaccttype;
	}
	public String getCreditacctno() {
		return creditacctno;
	}
	public String getCreditbrstn() {
		return creditbrstn;
	}
	public String getCreditbranch() {
		return creditbranch;
	}
	public String getPayeename() {
		return payeename;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public String getDisposition() {
		return disposition;
	}
	public void setAppno(String appno) {
		this.appno = appno;
	}
	public void setPnno(String pnno) {
		this.pnno = pnno;
	}
	public void setCheckno(String checkno) {
		this.checkno = checkno;
	}
	public void setCheckacctno(String checkacctno) {
		this.checkacctno = checkacctno;
	}
	public void setCheckbank(String checkbank) {
		this.checkbank = checkbank;
	}
	public void setCheckbrstn(String checkbrstn) {
		this.checkbrstn = checkbrstn;
	}
	public void setCheckbranch(String checkbranch) {
		this.checkbranch = checkbranch;
	}
	public void setCheckdate(Date checkdate) {
		this.checkdate = checkdate;
	}
	public void setCreditbank(String creditbank) {
		this.creditbank = creditbank;
	}
	public void setCreditaccttype(String creditaccttype) {
		this.creditaccttype = creditaccttype;
	}
	public void setCreditacctno(String creditacctno) {
		this.creditacctno = creditacctno;
	}
	public void setCreditbrstn(String creditbrstn) {
		this.creditbrstn = creditbrstn;
	}
	public void setCreditbranch(String creditbranch) {
		this.creditbranch = creditbranch;
	}
	public void setPayeename(String payeename) {
		this.payeename = payeename;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
