/**
 * 
 */
package com.etel.financial.form;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ETEL-LAPTOP19
 *
 */
public class CollateralLoanableForm {
	String collateralid;
	String collateralreferenceno;
	String collateraltype;
	BigDecimal appraisedvalue;
	BigDecimal loanablepercentage;
	BigDecimal loanablevalue;
	Date lastappraisaldate;
	public String getCollateralid() {
		return collateralid;
	}
	public void setCollateralid(String collateralid) {
		this.collateralid = collateralid;
	}
	public String getCollateralreferenceno() {
		return collateralreferenceno;
	}
	public void setCollateralreferenceno(String collateralreferenceno) {
		this.collateralreferenceno = collateralreferenceno;
	}
	public String getCollateraltype() {
		return collateraltype;
	}
	public void setCollateraltype(String collateraltype) {
		this.collateraltype = collateraltype;
	}
	public BigDecimal getAppraisedvalue() {
		return appraisedvalue;
	}
	public void setAppraisedvalue(BigDecimal appraisedvalue) {
		this.appraisedvalue = appraisedvalue;
	}
	public BigDecimal getLoanablepercentage() {
		return loanablepercentage;
	}
	public void setLoanablepercentage(BigDecimal loanablepercentage) {
		this.loanablepercentage = loanablepercentage;
	}
	public BigDecimal getLoanablevalue() {
		return loanablevalue;
	}
	public void setLoanablevalue(BigDecimal loanablevalue) {
		this.loanablevalue = loanablevalue;
	}
	public Date getLastappraisaldate() {
		return lastappraisaldate;
	}
	public void setLastappraisaldate(Date lastappraisaldate) {
		this.lastappraisaldate = lastappraisaldate;
	}
}
