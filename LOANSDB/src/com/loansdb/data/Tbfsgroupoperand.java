
package com.loansdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  LOANSDB.Tbfsgroupoperand
 *  10/13/2020 10:21:35
 * 
 */
public class Tbfsgroupoperand {

    private TbfsgroupoperandId id;
    private String fsoprgroupname;
    private String fsopttype;
    private String fssubsection;
    private String fsitemtype;
    private Boolean applytocorp;
    private Boolean applytosole;
    private Boolean applytopart;
    private Integer sequence;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date lastupdated;
    private String startbracket;
    private String endbracket;
    private Boolean ismanualcomp;
    private BigDecimal manualcompvalue;

    public TbfsgroupoperandId getId() {
        return id;
    }

    public void setId(TbfsgroupoperandId id) {
        this.id = id;
    }

    public String getFsoprgroupname() {
        return fsoprgroupname;
    }

    public void setFsoprgroupname(String fsoprgroupname) {
        this.fsoprgroupname = fsoprgroupname;
    }

    public String getFsopttype() {
        return fsopttype;
    }

    public void setFsopttype(String fsopttype) {
        this.fsopttype = fsopttype;
    }

    public String getFssubsection() {
        return fssubsection;
    }

    public void setFssubsection(String fssubsection) {
        this.fssubsection = fssubsection;
    }

    public String getFsitemtype() {
        return fsitemtype;
    }

    public void setFsitemtype(String fsitemtype) {
        this.fsitemtype = fsitemtype;
    }

    public Boolean getApplytocorp() {
        return applytocorp;
    }

    public void setApplytocorp(Boolean applytocorp) {
        this.applytocorp = applytocorp;
    }

    public Boolean getApplytosole() {
        return applytosole;
    }

    public void setApplytosole(Boolean applytosole) {
        this.applytosole = applytosole;
    }

    public Boolean getApplytopart() {
        return applytopart;
    }

    public void setApplytopart(Boolean applytopart) {
        this.applytopart = applytopart;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
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

    public String getStartbracket() {
        return startbracket;
    }

    public void setStartbracket(String startbracket) {
        this.startbracket = startbracket;
    }

    public String getEndbracket() {
        return endbracket;
    }

    public void setEndbracket(String endbracket) {
        this.endbracket = endbracket;
    }

    public Boolean getIsmanualcomp() {
        return ismanualcomp;
    }

    public void setIsmanualcomp(Boolean ismanualcomp) {
        this.ismanualcomp = ismanualcomp;
    }

    public BigDecimal getManualcompvalue() {
        return manualcompvalue;
    }

    public void setManualcompvalue(BigDecimal manualcompvalue) {
        this.manualcompvalue = manualcompvalue;
    }

}
