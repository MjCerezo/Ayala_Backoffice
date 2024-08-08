
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbscoremodel
 *  08/04/2024 12:54:42
 * 
 */
public class Tbscoremodel {

    private String modelno;
    private String modelname;
    private String description;
    private BigDecimal totalpercentage;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date dateupdated;
    private BigDecimal passingscore;
    private BigDecimal highestscore;
    private Integer lastcriteriaseqno;

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public String getModelname() {
        return modelname;
    }

    public void setModelname(String modelname) {
        this.modelname = modelname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTotalpercentage() {
        return totalpercentage;
    }

    public void setTotalpercentage(BigDecimal totalpercentage) {
        this.totalpercentage = totalpercentage;
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

    public BigDecimal getPassingscore() {
        return passingscore;
    }

    public void setPassingscore(BigDecimal passingscore) {
        this.passingscore = passingscore;
    }

    public BigDecimal getHighestscore() {
        return highestscore;
    }

    public void setHighestscore(BigDecimal highestscore) {
        this.highestscore = highestscore;
    }

    public Integer getLastcriteriaseqno() {
        return lastcriteriaseqno;
    }

    public void setLastcriteriaseqno(Integer lastcriteriaseqno) {
        this.lastcriteriaseqno = lastcriteriaseqno;
    }

}
