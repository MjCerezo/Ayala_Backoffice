
package com.loansdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  LOANSDB.Tbscorecriteria
 *  10/13/2020 10:21:35
 * 
 */
public class Tbscorecriteria {

    private String criteriaid;
    private String modelno;
    private String criteriaseqno;
    private String criterianame;
    private BigDecimal totalscore;
    private Integer lastsubcriteriaseqno;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date dateupdated;

    public String getCriteriaid() {
        return criteriaid;
    }

    public void setCriteriaid(String criteriaid) {
        this.criteriaid = criteriaid;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public String getCriteriaseqno() {
        return criteriaseqno;
    }

    public void setCriteriaseqno(String criteriaseqno) {
        this.criteriaseqno = criteriaseqno;
    }

    public String getCriterianame() {
        return criterianame;
    }

    public void setCriterianame(String criterianame) {
        this.criterianame = criterianame;
    }

    public BigDecimal getTotalscore() {
        return totalscore;
    }

    public void setTotalscore(BigDecimal totalscore) {
        this.totalscore = totalscore;
    }

    public Integer getLastsubcriteriaseqno() {
        return lastsubcriteriaseqno;
    }

    public void setLastsubcriteriaseqno(Integer lastsubcriteriaseqno) {
        this.lastsubcriteriaseqno = lastsubcriteriaseqno;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public Date getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }

}
