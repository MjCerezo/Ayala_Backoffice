
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbcollateralmain
 *  08/04/2024 12:54:40
 * 
 */
public class Tbcollateralmain {

    private Integer collateralid;
    private String colgroupid;
    private String collateraltype;
    private String appraisalreportid;
    private String referenceno;
    private String collateralstatus;
    private String appraisalstatus;
    private Date dateencoded;
    private String encodedby;
    private String registeredowner;
    private Date dateoflastappraisal;
    private BigDecimal lastappraisedvalue;
    private BigDecimal lastfairmarketvalue;
    private Date dateupdated;
    private String updatedby;

    public Integer getCollateralid() {
        return collateralid;
    }

    public void setCollateralid(Integer collateralid) {
        this.collateralid = collateralid;
    }

    public String getColgroupid() {
        return colgroupid;
    }

    public void setColgroupid(String colgroupid) {
        this.colgroupid = colgroupid;
    }

    public String getCollateraltype() {
        return collateraltype;
    }

    public void setCollateraltype(String collateraltype) {
        this.collateraltype = collateraltype;
    }

    public String getAppraisalreportid() {
        return appraisalreportid;
    }

    public void setAppraisalreportid(String appraisalreportid) {
        this.appraisalreportid = appraisalreportid;
    }

    public String getReferenceno() {
        return referenceno;
    }

    public void setReferenceno(String referenceno) {
        this.referenceno = referenceno;
    }

    public String getCollateralstatus() {
        return collateralstatus;
    }

    public void setCollateralstatus(String collateralstatus) {
        this.collateralstatus = collateralstatus;
    }

    public String getAppraisalstatus() {
        return appraisalstatus;
    }

    public void setAppraisalstatus(String appraisalstatus) {
        this.appraisalstatus = appraisalstatus;
    }

    public Date getDateencoded() {
        return dateencoded;
    }

    public void setDateencoded(Date dateencoded) {
        this.dateencoded = dateencoded;
    }

    public String getEncodedby() {
        return encodedby;
    }

    public void setEncodedby(String encodedby) {
        this.encodedby = encodedby;
    }

    public String getRegisteredowner() {
        return registeredowner;
    }

    public void setRegisteredowner(String registeredowner) {
        this.registeredowner = registeredowner;
    }

    public Date getDateoflastappraisal() {
        return dateoflastappraisal;
    }

    public void setDateoflastappraisal(Date dateoflastappraisal) {
        this.dateoflastappraisal = dateoflastappraisal;
    }

    public BigDecimal getLastappraisedvalue() {
        return lastappraisedvalue;
    }

    public void setLastappraisedvalue(BigDecimal lastappraisedvalue) {
        this.lastappraisedvalue = lastappraisedvalue;
    }

    public BigDecimal getLastfairmarketvalue() {
        return lastfairmarketvalue;
    }

    public void setLastfairmarketvalue(BigDecimal lastfairmarketvalue) {
        this.lastfairmarketvalue = lastfairmarketvalue;
    }

    public Date getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

}
