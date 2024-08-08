/**
 * 
 */
package com.etel.financial.form;

import java.math.BigDecimal;
import java.util.List;

import com.coopdb.data.Tbapd;
import com.coopdb.data.Tbloans;
import com.etel.deposit.form.DepositAccountForm;

/**
 * @author ETEL-LAPTOP19
 *
 */
public class SBLForm {
	
	Tbapd apd;
	List<DepositAccountForm> deposits;
	List<CollateralLoanableForm> collaterals;
	List<Tbloans> loans;
	BigDecimal totaldeposit;
	BigDecimal totalcollateral;
	BigDecimal totalloan;
	BigDecimal loanableamount;
	public Tbapd getApd() {
		return apd;
	}
	public void setApd(Tbapd apd) {
		this.apd = apd;
	}
	public List<DepositAccountForm> getDeposits() {
		return deposits;
	}
	public void setDeposits(List<DepositAccountForm> deposits) {
		this.deposits = deposits;
	}
	public List<CollateralLoanableForm> getCollaterals() {
		return collaterals;
	}
	public void setCollaterals(List<CollateralLoanableForm> collaterals) {
		this.collaterals = collaterals;
	}
	public List<Tbloans> getLoans() {
		return loans;
	}
	public void setLoans(List<Tbloans> loans) {
		this.loans = loans;
	}
	public BigDecimal getTotaldeposit() {
		return totaldeposit;
	}
	public void setTotaldeposit(BigDecimal totaldeposit) {
		this.totaldeposit = totaldeposit;
	}
	public BigDecimal getTotalcollateral() {
		return totalcollateral;
	}
	public void setTotalcollateral(BigDecimal totalcollateral) {
		this.totalcollateral = totalcollateral;
	}
	public BigDecimal getTotalloan() {
		return totalloan;
	}
	public void setTotalloan(BigDecimal totalloan) {
		this.totalloan = totalloan;
	}
	public BigDecimal getLoanableamount() {
		return loanableamount;
	}
	public void setLoanableamount(BigDecimal loanableamount) {
		this.loanableamount = loanableamount;
	}
}
