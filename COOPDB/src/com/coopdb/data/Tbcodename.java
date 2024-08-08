
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbcodename
 *  08/04/2024 12:54:40
 * 
 */
public class Tbcodename {

    private String codename;
    private Boolean iseditable;
    private String remarks;
    private String createdby;
    private Date createddate;

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

}
