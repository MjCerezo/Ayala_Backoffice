
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbrole
 *  08/27/2024 14:22:56
 * 
 */
public class Tbrole {

    private TbroleId id;
    private String createdby;
    private Date createddate;
    private String updatedby;
    private Date dateupdated;

    public TbroleId getId() {
        return id;
    }

    public void setId(TbroleId id) {
        this.id = id;
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

    public Date getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }

}
