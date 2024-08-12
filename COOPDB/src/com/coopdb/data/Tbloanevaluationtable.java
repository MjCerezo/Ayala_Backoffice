
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbloanevaluationtable
 *  08/10/2024 21:24:57
 * 
 */
public class Tbloanevaluationtable {

    private Integer id;
    private String template;
    private Integer mintenure;
    private Integer maxtenure;
    private Integer minrankcode;
    private String minrankname;
    private Integer maxrankcode;
    private String maxrankname;
    private String minsharecap;
    private String maxsharecapmultiplier;
    private BigDecimal maxsharecapamount;
    private BigDecimal nthp;
    private String maxcounter;
    private BigDecimal maxloanable;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date dateupdated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Integer getMintenure() {
        return mintenure;
    }

    public void setMintenure(Integer mintenure) {
        this.mintenure = mintenure;
    }

    public Integer getMaxtenure() {
        return maxtenure;
    }

    public void setMaxtenure(Integer maxtenure) {
        this.maxtenure = maxtenure;
    }

    public Integer getMinrankcode() {
        return minrankcode;
    }

    public void setMinrankcode(Integer minrankcode) {
        this.minrankcode = minrankcode;
    }

    public String getMinrankname() {
        return minrankname;
    }

    public void setMinrankname(String minrankname) {
        this.minrankname = minrankname;
    }

    public Integer getMaxrankcode() {
        return maxrankcode;
    }

    public void setMaxrankcode(Integer maxrankcode) {
        this.maxrankcode = maxrankcode;
    }

    public String getMaxrankname() {
        return maxrankname;
    }

    public void setMaxrankname(String maxrankname) {
        this.maxrankname = maxrankname;
    }

    public String getMinsharecap() {
        return minsharecap;
    }

    public void setMinsharecap(String minsharecap) {
        this.minsharecap = minsharecap;
    }

    public String getMaxsharecapmultiplier() {
        return maxsharecapmultiplier;
    }

    public void setMaxsharecapmultiplier(String maxsharecapmultiplier) {
        this.maxsharecapmultiplier = maxsharecapmultiplier;
    }

    public BigDecimal getMaxsharecapamount() {
        return maxsharecapamount;
    }

    public void setMaxsharecapamount(BigDecimal maxsharecapamount) {
        this.maxsharecapamount = maxsharecapamount;
    }

    public BigDecimal getNthp() {
        return nthp;
    }

    public void setNthp(BigDecimal nthp) {
        this.nthp = nthp;
    }

    public String getMaxcounter() {
        return maxcounter;
    }

    public void setMaxcounter(String maxcounter) {
        this.maxcounter = maxcounter;
    }

    public BigDecimal getMaxloanable() {
        return maxloanable;
    }

    public void setMaxloanable(BigDecimal maxloanable) {
        this.maxloanable = maxloanable;
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
