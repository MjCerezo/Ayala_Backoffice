
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbfsoperand
 *  10/13/2020 10:21:35
 * 
 */
public class Tbfsoperand {

    private Integer id;
    private String fsoprgroupcode;
    private String fsopttype;
    private String fsoptname;
    private String fsident;
    private String fsoptapplyto;
    private String fssection;
    private String fssubsection;
    private String fsitemtype;
    private String fsitemname;
    private Boolean applytocorp;
    private Boolean applytosole;
    private Boolean applytopart;
    private Integer sequence;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date lastupdated;
    private String fssectionmain;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFsoprgroupcode() {
        return fsoprgroupcode;
    }

    public void setFsoprgroupcode(String fsoprgroupcode) {
        this.fsoprgroupcode = fsoprgroupcode;
    }

    public String getFsopttype() {
        return fsopttype;
    }

    public void setFsopttype(String fsopttype) {
        this.fsopttype = fsopttype;
    }

    public String getFsoptname() {
        return fsoptname;
    }

    public void setFsoptname(String fsoptname) {
        this.fsoptname = fsoptname;
    }

    public String getFsident() {
        return fsident;
    }

    public void setFsident(String fsident) {
        this.fsident = fsident;
    }

    public String getFsoptapplyto() {
        return fsoptapplyto;
    }

    public void setFsoptapplyto(String fsoptapplyto) {
        this.fsoptapplyto = fsoptapplyto;
    }

    public String getFssection() {
        return fssection;
    }

    public void setFssection(String fssection) {
        this.fssection = fssection;
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

    public String getFsitemname() {
        return fsitemname;
    }

    public void setFsitemname(String fsitemname) {
        this.fsitemname = fsitemname;
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

    public String getFssectionmain() {
        return fssectionmain;
    }

    public void setFssectionmain(String fssectionmain) {
        this.fssectionmain = fssectionmain;
    }

}
