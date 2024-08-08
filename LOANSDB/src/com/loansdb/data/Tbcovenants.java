
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbcovenants
 *  10/13/2020 10:21:35
 * 
 */
public class Tbcovenants {

    private TbcovenantsId id;
    private String covenants;
    private String remarks;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date lastupdated;

    public TbcovenantsId getId() {
        return id;
    }

    public void setId(TbcovenantsId id) {
        this.id = id;
    }

    public String getCovenants() {
        return covenants;
    }

    public void setCovenants(String covenants) {
        this.covenants = covenants;
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
