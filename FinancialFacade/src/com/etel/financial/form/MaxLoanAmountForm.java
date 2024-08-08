/**
 * 
 */
package com.etel.financial.form;

import java.math.BigDecimal;

/**
 * @author ETEL-LAPTOP19
 *
 */
public class MaxLoanAmountForm {
	
	String mlatype;
	BigDecimal loanableamount;
	String condition;
	String result;
	public String getMlatype() {
		return mlatype;
	}
	public void setMlatype(String mlatype) {
		this.mlatype = mlatype;
	}
	public BigDecimal getLoanableamount() {
		return loanableamount;
	}
	public void setLoanableamount(BigDecimal loanableamount) {
		this.loanableamount = loanableamount;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
