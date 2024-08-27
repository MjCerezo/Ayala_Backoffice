
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbgroup
 *  08/27/2024 14:22:56
 * 
 */
public class Tbgroup {

    private TbgroupId id;
    private String groupname;
    private Boolean isapprovingcommittee;
    private Date datecreated;
    private String createdby;
    private Date dateupdated;
    private String updatedby;
    private String coopcode;

    public TbgroupId getId() {
        return id;
    }

    public void setId(TbgroupId id) {
        this.id = id;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public Boolean getIsapprovingcommittee() {
        return isapprovingcommittee;
    }

    public void setIsapprovingcommittee(Boolean isapprovingcommittee) {
        this.isapprovingcommittee = isapprovingcommittee;
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

    public String getCoopcode() {
        return coopcode;
    }

    public void setCoopcode(String coopcode) {
        this.coopcode = coopcode;
    }

}
