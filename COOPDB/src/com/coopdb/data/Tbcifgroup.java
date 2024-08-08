
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbcifgroup
 *  02/23/2023 13:04:33
 * 
 */
public class Tbcifgroup {

    private String cifgroupcode;
    private String cifgroupname;
    private Date datecreated;
    private String createdby;
    private Date dateupdated;
    private String updatedby;

    public String getCifgroupcode() {
        return cifgroupcode;
    }

    public void setCifgroupcode(String cifgroupcode) {
        this.cifgroupcode = cifgroupcode;
    }

    public String getCifgroupname() {
        return cifgroupname;
    }

    public void setCifgroupname(String cifgroupname) {
        this.cifgroupname = cifgroupname;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
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
