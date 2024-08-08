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
public class MLACForm {
	
	String product;
	String particular;
	BigDecimal salary;
	BigDecimal gaa;
	BigDecimal nthp;
	String servicestatus;
	BigDecimal totaldeposit;
	BigDecimal totalloan;
	BigDecimal totalcollateral;
	String memberid;
	List<DepositAccountForm> deposits;
	List<CollateralLoanableForm> collaterals;
	List<Tbloans> loans;
	BigDecimal term;
	BigDecimal interestrate;
	BigDecimal eir;
	String appno;
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getParticular() {
		return particular;
	}
	public void setParticular(String particular) {
		this.particular = particular;
	}
	public BigDecimal getSalary() {
		return salary;
	}
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}
	public BigDecimal getGaa() {
		return gaa;
	}
	public void setGaa(BigDecimal gaa) {
		this.gaa = gaa;
	}
	public BigDecimal getNthp() {
		return nthp;
	}
	public void setNthp(BigDecimal nthp) {
		this.nthp = nthp;
	}
	public String getServicestatus() {
		return servicestatus;
	}
	public void setServicestatus(String servicestatus) {
		this.servicestatus = servicestatus;
	}
	public BigDecimal getTotaldeposit() {
		return totaldeposit;
	}
	public void setTotaldeposit(BigDecimal totaldeposit) {
		this.totaldeposit = totaldeposit;
	}
	public BigDecimal getTotalloan() {
		return totalloan;
	}
	public void setTotalloan(BigDecimal totalloan) {
		this.totalloan = totalloan;
	}
	public BigDecimal getTotalcollateral() {
		return totalcollateral;
	}
	public void setTotalcollateral(BigDecimal totalcollateral) {
		this.totalcollateral = totalcollateral;
	}
	public String getMemberid() {
		return memberid;
	}
	public void setMemberid(String memberid) {
		this.memberid = memberid;
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
	public BigDecimal getTerm() {
		return term;
	}
	public void setTerm(BigDecimal term) {
		this.term = term;
	}
	public BigDecimal getInterestrate() {
		return interestrate;
	}
	public void setInterestrate(BigDecimal interestrate) {
		this.interestrate = interestrate;
	}
	public BigDecimal getEir() {
		return eir;
	}
	public void setEir(BigDecimal eir) {
		this.eir = eir;
	}
	public String getAppno() {
		return appno;
	}
	public void setAppno(String appno) {
		this.appno = appno;
	}
	@Override
	public String toString() {
		return "MLACForm [collaterals=" + collaterals + ", deposits=" + deposits + ", eir=" + eir + ", gaa=" + gaa
				+ ", interestrate=" + interestrate + ", loans=" + loans + ", memberid=" + memberid + ", nthp=" + nthp
				+ ", particular=" + particular + ", product=" + product + ", salary=" + salary + ", servicestatus="
				+ servicestatus + ", term=" + term + ", totalcollateral=" + totalcollateral + ", totaldeposit="
				+ totaldeposit + ", totalloan=" + totalloan + ", getCollaterals()=" + getCollaterals()
				+ ", getDeposits()=" + getDeposits() + ", getEir()=" + getEir() + ", getGaa()=" + getGaa()
				+ ", getInterestrate()=" + getInterestrate() + ", getLoans()=" + getLoans() + ", getMemberid()="
				+ getMemberid() + ", getNthp()=" + getNthp() + ", getParticular()=" + getParticular()
				+ ", getProduct()=" + getProduct() + ", getSalary()=" + getSalary() + ", getServicestatus()="
				+ getServicestatus() + ", getTerm()=" + getTerm() + ", getTotalcollateral()=" + getTotalcollateral()
				+ ", getTotaldeposit()=" + getTotaldeposit() + ", getTotalloan()=" + getTotalloan() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
}
