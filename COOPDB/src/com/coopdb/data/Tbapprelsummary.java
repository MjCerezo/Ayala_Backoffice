
package com.coopdb.data;

import java.math.BigDecimal;


/**
 *  COOPDB.Tbapprelsummary
 *  08/10/2024 21:24:57
 * 
 */
public class Tbapprelsummary {

    private Integer id;
    private String appraisalreportid;
    private String appno;
    private String tctno;
    private String lotblkno;
    private BigDecimal valuesqm;
    private BigDecimal fairmarketvalue;

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

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getTctno() {
        return tctno;
    }

    public void setTctno(String tctno) {
        this.tctno = tctno;
    }

    public String getLotblkno() {
        return lotblkno;
    }

    public void setLotblkno(String lotblkno) {
        this.lotblkno = lotblkno;
    }

    public BigDecimal getValuesqm() {
        return valuesqm;
    }

    public void setValuesqm(BigDecimal valuesqm) {
        this.valuesqm = valuesqm;
    }

    public BigDecimal getFairmarketvalue() {
        return fairmarketvalue;
    }

    public void setFairmarketvalue(BigDecimal fairmarketvalue) {
        this.fairmarketvalue = fairmarketvalue;
    }

}
