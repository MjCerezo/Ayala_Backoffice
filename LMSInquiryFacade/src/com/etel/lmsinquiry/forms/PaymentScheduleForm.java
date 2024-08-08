/**
 * 
 */
package com.etel.lmsinquiry.forms;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Mheanne
 *
 */
public class PaymentScheduleForm {

	int ilno;
	Date ilduedt;
	int daysdiff;
	BigDecimal ilamt;
	BigDecimal ilprin;
	BigDecimal ilint;
	BigDecimal prinbal;
	BigDecimal loanbal;
	BigDecimal uidbal;
	
	public int getIlno() {
		return ilno;
	}
	public void setIlno(int ilno) {
		this.ilno = ilno;
	}
	public Date getIlduedt() {
		return ilduedt;
	}
	public void setIlduedt(Date ilduedt) {
		this.ilduedt = ilduedt;
	}
	public int getDaysdiff() {
		return daysdiff;
	}
	public void setDaysdiff(int daysdiff) {
		this.daysdiff = daysdiff;
	}
	public BigDecimal getIlamt() {
		return ilamt;
	}
	public void setIlamt(BigDecimal ilamt) {
		this.ilamt = ilamt;
	}
	public BigDecimal getIlprin() {
		return ilprin;
	}
	public void setIlprin(BigDecimal ilprin) {
		this.ilprin = ilprin;
	}
	public BigDecimal getIlint() {
		return ilint;
	}
	public void setIlint(BigDecimal ilint) {
		this.ilint = ilint;
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
	
	
	
	

	
	
	
	
	
	
}
