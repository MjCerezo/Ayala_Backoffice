
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbscoresubcriteria
 *  08/27/2024 14:22:57
 * 
 */
public class Tbscoresubcriteria {

    private String subcriteriaid;
    private String modelno;
    private String criteriaid;
    private String subcriteriaseqno;
    private String subcriterianame;
    private Integer highestscore;
    private Integer lastitemseqno;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date dateupdated;

    public String getSubcriteriaid() {
        return subcriteriaid;
    }

    public void setSubcriteriaid(String subcriteriaid) {
        this.subcriteriaid = subcriteriaid;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public String getCriteriaid() {
        return criteriaid;
    }

    public void setCriteriaid(String criteriaid) {
        this.criteriaid = criteriaid;
    }

    public String getSubcriteriaseqno() {
        return subcriteriaseqno;
    }

    public void setSubcriteriaseqno(String subcriteriaseqno) {
        this.subcriteriaseqno = subcriteriaseqno;
    }

    public String getSubcriterianame() {
        return subcriterianame;
    }

    public void setSubcriterianame(String subcriterianame) {
        this.subcriterianame = subcriterianame;
    }

    public Integer getHighestscore() {
        return highestscore;
    }

    public void setHighestscore(Integer highestscore) {
        this.highestscore = highestscore;
    }

    public Integer getLastitemseqno() {
        return lastitemseqno;
    }

    public void setLastitemseqno(Integer lastitemseqno) {
        this.lastitemseqno = lastitemseqno;
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
