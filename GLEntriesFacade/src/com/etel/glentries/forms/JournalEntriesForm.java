/**
 * 
 */
package com.etel.glentries.forms;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ETEL-LAPTOP07
 *
 */
public class JournalEntriesForm {

	private String branch;
	private Date businessdate;
	private String txrefno;
	private String mediano;
	private String txtype;
	private String accountno;
	private String cifno;
	private String accountname;
	private String remarks;
	private BigDecimal debit;
	private BigDecimal credit;
	private BigDecimal balance;
	private String encodedby;

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public Date getBusinessdate() {
		return businessdate;
	}

	public void setBusinessdate(Date businessdate) {
		this.businessdate = businessdate;
	}

	public String getTxrefno() {
		return txrefno;
	}

	public void setTxrefno(String txrefno) {
		this.txrefno = txrefno;
	}

	public String getMediano() {
		return mediano;
	}

	public void setMediano(String mediano) {
		this.mediano = mediano;
	}

	public String getTxtype() {
		return txtype;
	}

	public void setTxtype(String txtype) {
		this.txtype = txtype;
	}

	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public String getCifno() {
		return cifno;
	}

	public void setCifno(String cifno) {
		this.cifno = cifno;
	}

	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getEncodedby() {
		return encodedby;
	}

	public void setEncodedby(String encodedby) {
		this.encodedby = encodedby;
	}

}
