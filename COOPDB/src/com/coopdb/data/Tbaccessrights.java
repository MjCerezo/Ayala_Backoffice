
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbaccessrights
 *  08/27/2024 14:22:57
 * 
 */
public class Tbaccessrights {

    private TbaccessrightsId id;
    private String accesstype;
    private String submodulename;
    private String createdby;
    private Date createddate;
    private String updatedby;
    private Date dateupdated;
    private String description;
    private String navorder;
    private String navparent;
    private String parentsubmodulename;
    private String parentnavorder;

    public TbaccessrightsId getId() {
        return id;
    }

    public void setId(TbaccessrightsId id) {
        this.id = id;
    }

    public String getAccesstype() {
        return accesstype;
    }

    public void setAccesstype(String accesstype) {
        this.accesstype = accesstype;
    }

    public String getSubmodulename() {
        return submodulename;
    }

    public void setSubmodulename(String submodulename) {
        this.submodulename = submodulename;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNavorder() {
        return navorder;
    }

    public void setNavorder(String navorder) {
        this.navorder = navorder;
    }

    public String getNavparent() {
        return navparent;
    }

    public void setNavparent(String navparent) {
        this.navparent = navparent;
    }

    public String getParentsubmodulename() {
        return parentsubmodulename;
    }

    public void setParentsubmodulename(String parentsubmodulename) {
        this.parentsubmodulename = parentsubmodulename;
    }

    public String getParentnavorder() {
        return parentnavorder;
    }

    public void setParentnavorder(String parentnavorder) {
        this.parentnavorder = parentnavorder;
    }

}
