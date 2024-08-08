
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbcodename
 *  10/13/2020 10:21:35
 * 
 */
public class Tbcodename {

    private String codename;
    private Boolean iseditable;
    private String remarks;
    private String createdby;
    private Date createddate;
    private String updatedby;
    private Date lastupdated;

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public Boolean getIseditable() {
        return iseditable;
    }

    public void setIseditable(Boolean iseditable) {
        this.iseditable = iseditable;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
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
