package com.etel.lmsinquiry.forms;

import java.math.BigDecimal;
import java.util.Date;

public class LoanTransactionHistory {

	String txrefno;
	String accountno;
	String pnno;
	String txcode;
	Date txdate;
	Date txvldt;
	String txmode;
	String ilnopd;
	Date duedtpd;
	BigDecimal txamt;
	BigDecimal txint;
	BigDecimal txprin;
	BigDecimal txlpc;
	BigDecimal txar;
	BigDecimal txexcessbal;
	BigDecimal prinbal;
	BigDecimal loanbal;
	BigDecimal uidbal;
	String acctstatus;
	BigDecimal txexcess;
	
	public String getAccountno() {
		return accountno;
	}
	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}
	public String getPnno() {
		return pnno;
	}
	public void setPnno(String pnno) {
		this.pnno = pnno;
	}
	public String getTxcode() {
		return txcode;
	}
	public void setTxcode(String txcode) {
		this.txcode = txcode;
	}
	public Date getTxdate() {
		return txdate;
	}
	public void setTxdate(Date txdate) {
		this.txdate = txdate;
	}
	public Date getTxvldt() {
		return txvldt;
	}
	public void setTxvldt(Date txvldt) {
		this.txvldt = txvldt;
	}
	public String getTxmode() {
		return txmode;
	}
	public void setTxmode(String txmode) {
		this.txmode = txmode;
	}
	public String getIlnopd() {
		return ilnopd;
	}
	public void setIlnopd(String ilnopd) {
		this.ilnopd = ilnopd;
	}
	public Date getDuedtpd() {
		return duedtpd;
	}
	public void setDuedtpd(Date duedtpd) {
		this.duedtpd = duedtpd;
	}
	public BigDecimal getTxamt() {
		return txamt;
	}
	public void setTxamt(BigDecimal txamt) {
		this.txamt = txamt;
	}
	public BigDecimal getTxint() {
		return txint;
	}
	public void setTxint(BigDecimal txint) {
		this.txint = txint;
	}
	public BigDecimal getTxprin() {
		return txprin;
	}
	public void setTxprin(BigDecimal txprin) {
		this.txprin = txprin;
	}
	public BigDecimal getTxlpc() {
		return txlpc;
	}
	public void setTxlpc(BigDecimal txlpc) {
		this.txlpc = txlpc;
	}
	public BigDecimal getTxar() {
		return txar;
	}
	public void setTxar(BigDecimal txar) {
		this.txar = txar;
	}
	public BigDecimal getTxexcessbal() {
		return txexcessbal;
	}
	public void setTxexcessbal(BigDecimal txexcessbal) {
		this.txexcessbal = txexcessbal;
	}
	public BigDecimal getPrinbal() {
		return prinbal;
	}
	public void setPrinbal(BigDecimal prinbal) {
		this.prinbal = prinbal;
	}
	public BigDecimal getLoanbal() {
		return loanbal;
	}
	public void setLoanbal(BigDecimal loanbal) {
		this.loanbal = loanbal;
	}
	public BigDecimal getUidbal() {
		return uidbal;
	}
	public void setUidbal(BigDecimal uidbal) {
		this.uidbal = uidbal;
	}
	public String getAcctstatus() {
		return acctstatus;
	}
	public void setAcctstatus(String acctstatus) {
		this.acctstatus = acctstatus;
	}
	public String getTxrefno() {
		return txrefno;
	}
	public void setTxrefno(String txrefno) {
		this.txrefno = txrefno;
	}
	public BigDecimal getTxexcess() {
		return txexcess;
	}
	public void setTxexcess(BigDecimal txexcess) {
		this.txexcess = txexcess;
	}
	
}
