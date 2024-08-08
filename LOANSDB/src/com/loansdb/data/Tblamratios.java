
package com.loansdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  LOANSDB.Tblamratios
 *  08/08/2018 15:53:04
 * 
 */
public class Tblamratios {

    private Integer id;
    private String cfappno;
    private String cfrefno;
    private Date year;
    private BigDecimal growthrate;
    private BigDecimal profgrosssmargin;
    private BigDecimal profoperatingmargin;
    private BigDecimal profnetmargin;
    private BigDecimal profreturnasset;
    private BigDecimal profreturnequity;
    private BigDecimal profcashflowmargin;
    private BigDecimal liqcurrentratio;
    private BigDecimal liqquickratio;
    private BigDecimal levdebtequityratio;
    private BigDecimal levdebtincomeratio;
    private BigDecimal liqinterestcoverage;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date lastupdated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCfappno() {
        return cfappno;
    }

    public void setCfappno(String cfappno) {
        this.cfappno = cfappno;
    }

    public String getCfrefno() {
        return cfrefno;
    }

    public void setCfrefno(String cfrefno) {
        this.cfrefno = cfrefno;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public BigDecimal getGrowthrate() {
        return growthrate;
    }

    public void setGrowthrate(BigDecimal growthrate) {
        this.growthrate = growthrate;
    }

    public BigDecimal getProfgrosssmargin() {
        return profgrosssmargin;
    }

    public void setProfgrosssmargin(BigDecimal profgrosssmargin) {
        this.profgrosssmargin = profgrosssmargin;
    }

    public BigDecimal getProfoperatingmargin() {
        return profoperatingmargin;
    }

    public void setProfoperatingmargin(BigDecimal profoperatingmargin) {
        this.profoperatingmargin = profoperatingmargin;
    }

    public BigDecimal getProfnetmargin() {
        return profnetmargin;
    }

    public void setProfnetmargin(BigDecimal profnetmargin) {
        this.profnetmargin = profnetmargin;
    }

    public BigDecimal getProfreturnasset() {
        return profreturnasset;
    }

    public void setProfreturnasset(BigDecimal profreturnasset) {
        this.profreturnasset = profreturnasset;
    }

    public BigDecimal getProfreturnequity() {
        return profreturnequity;
    }

    public void setProfreturnequity(BigDecimal profreturnequity) {
        this.profreturnequity = profreturnequity;
    }

    public BigDecimal getProfcashflowmargin() {
        return profcashflowmargin;
    }

    public void setProfcashflowmargin(BigDecimal profcashflowmargin) {
        this.profcashflowmargin = profcashflowmargin;
    }

    public BigDecimal getLiqcurrentratio() {
        return liqcurrentratio;
    }

    public void setLiqcurrentratio(BigDecimal liqcurrentratio) {
        this.liqcurrentratio = liqcurrentratio;
    }

    public BigDecimal getLiqquickratio() {
        return liqquickratio;
    }

    public void setLiqquickratio(BigDecimal liqquickratio) {
        this.liqquickratio = liqquickratio;
    }

    public BigDecimal getLevdebtequityratio() {
        return levdebtequityratio;
    }

    public void setLevdebtequityratio(BigDecimal levdebtequityratio) {
        this.levdebtequityratio = levdebtequityratio;
    }

    public BigDecimal getLevdebtincomeratio() {
        return levdebtincomeratio;
    }

    public void setLevdebtincomeratio(BigDecimal levdebtincomeratio) {
        this.levdebtincomeratio = levdebtincomeratio;
    }

    public BigDecimal getLiqinterestcoverage() {
        return liqinterestcoverage;
    }

    public void setLiqinterestcoverage(BigDecimal liqinterestcoverage) {
        this.liqinterestcoverage = liqinterestcoverage;
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

    public Date getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(Date lastupdated) {
        this.lastupdated = lastupdated;
    }

}
