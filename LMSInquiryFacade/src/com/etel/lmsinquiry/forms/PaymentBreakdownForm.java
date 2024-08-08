/**
 * 
 */
package com.etel.lmsinquiry.forms;

import java.math.BigDecimal;

/**
 * @author ETEL-LAPTOP07
 *
 */
public class PaymentBreakdownForm {

	String accountno;
	BigDecimal penalty;
	BigDecimal ar;
	BigDecimal interest;
	BigDecimal principal;
	BigDecimal excess;

	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public BigDecimal getPenalty() {
		return penalty;
	}

	public void setPenalty(BigDecimal penalty) {
		this.penalty = penalty;
	}

	public BigDecimal getAr() {
		return ar;
	}

	public void setAr(BigDecimal ar) {
		this.ar = ar;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public BigDecimal getPrincipal() {
		return principal;
	}

	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}

	public BigDecimal getExcess() {
		return excess;
	}

	public void setExcess(BigDecimal excess) {
		this.excess = excess;
	}

}
