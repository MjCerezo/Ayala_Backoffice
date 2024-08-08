
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbpolicymodel
 *  08/04/2024 12:54:42
 * 
 */
public class Tbpolicymodel {

    private String modelno;
    private String modelname;
    private String description;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date dateupdated;
    private Integer lastitemseqno;

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

    public Integer getLastitemseqno() {
        return lastitemseqno;
    }

    public void setLastitemseqno(Integer lastitemseqno) {
        this.lastitemseqno = lastitemseqno;
    }

}
