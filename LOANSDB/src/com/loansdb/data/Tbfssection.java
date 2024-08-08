
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbfssection
 *  10/13/2020 10:21:35
 * 
 */
public class Tbfssection {

    private TbfssectionId id;
    private Boolean applytocorp;
    private Boolean applytosole;
    private Boolean applytopart;
    private Boolean ratioflag;
    private Integer sequence;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date lastupdated;

    public TbfssectionId getId() {
        return id;
    }

    public void setId(TbfssectionId id) {
        this.id = id;
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

    public Boolean getRatioflag() {
        return ratioflag;
    }

    public void setRatioflag(Boolean ratioflag) {
        this.ratioflag = ratioflag;
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

}
