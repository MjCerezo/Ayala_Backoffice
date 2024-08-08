/**
 * 
 */
package com.etel.lmsreport.form;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ETEL-LAPTOP07
 *
 */
public class LMSUnearnedInterestForm {
	private String branch;
	private String cifno;
	private String product;
	private String accountno;
	private String accountname;
	private Date bookdate;
	private Date maturitydate;
	private BigDecimal intamount;
	private BigDecimal uidbal;
	private BigDecimal intearned;

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getCifno() {
		return cifno;
	}

	public void setCifno(String cifno) {
		this.cifno = cifno;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public Date getBookdate() {
		return bookdate;
	}

	public void setBookdate(Date bookdate) {
		this.bookdate = bookdate;
	}

	public Date getMaturitydate() {
		return maturitydate;
	}

	public void setMaturitydate(Date maturitydate) {
		this.maturitydate = maturitydate;
	}

	public BigDecimal getIntamount() {
		return intamount;
	}

	public void setIntamount(BigDecimal intamount) {
		this.intamount = intamount;
	}

	public BigDecimal getUidbal() {
		return uidbal;
	}

	public void setUidbal(BigDecimal uidbal) {
		this.uidbal = uidbal;
	}

	public BigDecimal getIntearned() {
		return intearned;
	}

	public void setIntearned(BigDecimal intearned) {
		this.intearned = intearned;
	}

}
