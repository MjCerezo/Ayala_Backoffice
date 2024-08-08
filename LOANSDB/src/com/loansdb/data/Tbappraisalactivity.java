
package com.loansdb.data;

import java.math.BigDecimal;


/**
 *  LOANSDB.Tbappraisalactivity
 *  10/13/2020 10:21:35
 * 
 */
public class Tbappraisalactivity {

    private Integer id;
    private String appraisalreportid;
    private String collateraltype;
    private String referenceno;
    private String collateralid;
    private String overallfindings;
    private String overallremarks;
    private BigDecimal loanablevalue;
    private BigDecimal loanablepercent;
    private BigDecimal appraisedvalue;
    private BigDecimal appraisedpercent;
    private BigDecimal marketvalue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppraisalreportid() {
        return appraisalreportid;
    }

    public void setAppraisalreportid(String appraisalreportid) {
        this.appraisalreportid = appraisalreportid;
    }

    public String getCollateraltype() {
        return collateraltype;
    }

    public void setCollateraltype(String collateraltype) {
        this.collateraltype = collateraltype;
    }

    public String getReferenceno() {
        return referenceno;
    }

    public void setReferenceno(String referenceno) {
        this.referenceno = referenceno;
    }

    public String getCollateralid() {
        return collateralid;
    }

    public void setCollateralid(String collateralid) {
        this.collateralid = collateralid;
    }

    public String getOverallfindings() {
        return overallfindings;
    }

    public void setOverallfindings(String overallfindings) {
        this.overallfindings = overallfindings;
    }

    public String getOverallremarks() {
        return overallremarks;
    }

    public void setOverallremarks(String overallremarks) {
        this.overallremarks = overallremarks;
    }

    public BigDecimal getLoanablevalue() {
        return loanablevalue;
    }

    public void setLoanablevalue(BigDecimal loanablevalue) {
        this.loanablevalue = loanablevalue;
    }

    public BigDecimal getLoanablepercent() {
        return loanablepercent;
    }

    public void setLoanablepercent(BigDecimal loanablepercent) {
        this.loanablepercent = loanablepercent;
    }

    public BigDecimal getAppraisedvalue() {
        return appraisedvalue;
    }

    public void setAppraisedvalue(BigDecimal appraisedvalue) {
        this.appraisedvalue = appraisedvalue;
    }

    public BigDecimal getAppraisedpercent() {
        return appraisedpercent;
    }

    public void setAppraisedpercent(BigDecimal appraisedpercent) {
        this.appraisedpercent = appraisedpercent;
    }

    public BigDecimal getMarketvalue() {
        return marketvalue;
    }

    public void setMarketvalue(BigDecimal marketvalue) {
        this.marketvalue = marketvalue;
    }

}
