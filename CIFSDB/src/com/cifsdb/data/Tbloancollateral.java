
package com.cifsdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  CIFSDB.Tbloancollateral
 *  09/26/2023 10:13:06
 * 
 */
public class Tbloancollateral {

    private Integer collateralid;
    private String collateralreferenceno;
    private String appno;
    private String loanno;
    private String collateraltype;
    private String loanstatus;
    private String applicationstatus;
    private String borrowername;
    private String loanproduct;
    private String colid;
    private String cfrefno1;
    private BigDecimal appraisedvalue;
    private BigDecimal loanablevalue;
    private BigDecimal marketvalue;
    private Date lastappraisaldate;

    public Integer getCollateralid() {
        return collateralid;
    }

    public void setCollateralid(Integer collateralid) {
        this.collateralid = collateralid;
    }

    public String getCollateralreferenceno() {
        return collateralreferenceno;
    }

    public void setCollateralreferenceno(String collateralreferenceno) {
        this.collateralreferenceno = collateralreferenceno;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getLoanno() {
        return loanno;
    }

    public void setLoanno(String loanno) {
        this.loanno = loanno;
    }

    public String getCollateraltype() {
        return collateraltype;
    }

    public void setCollateraltype(String collateraltype) {
        this.collateraltype = collateraltype;
    }

    public String getLoanstatus() {
        return loanstatus;
    }

    public void setLoanstatus(String loanstatus) {
        this.loanstatus = loanstatus;
    }

    public String getApplicationstatus() {
        return applicationstatus;
    }

    public void setApplicationstatus(String applicationstatus) {
        this.applicationstatus = applicationstatus;
    }

    public String getBorrowername() {
        return borrowername;
    }

    public void setBorrowername(String borrowername) {
        this.borrowername = borrowername;
    }

    public String getLoanproduct() {
        return loanproduct;
    }

    public void setLoanproduct(String loanproduct) {
        this.loanproduct = loanproduct;
    }

    public String getColid() {
        return colid;
    }

    public void setColid(String colid) {
        this.colid = colid;
    }

    public String getCfrefno1() {
        return cfrefno1;
    }

    public void setCfrefno1(String cfrefno1) {
        this.cfrefno1 = cfrefno1;
    }

    public BigDecimal getAppraisedvalue() {
        return appraisedvalue;
    }

    public void setAppraisedvalue(BigDecimal appraisedvalue) {
        this.appraisedvalue = appraisedvalue;
    }

    public BigDecimal getLoanablevalue() {
        return loanablevalue;
    }

    public void setLoanablevalue(BigDecimal loanablevalue) {
        this.loanablevalue = loanablevalue;
    }

    public BigDecimal getMarketvalue() {
        return marketvalue;
    }

    public void setMarketvalue(BigDecimal marketvalue) {
        this.marketvalue = marketvalue;
    }

    public Date getLastappraisaldate() {
        return lastappraisaldate;
    }

    public void setLastappraisaldate(Date lastappraisaldate) {
        this.lastappraisaldate = lastappraisaldate;
    }

}
