
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbpolicygroup
 *  08/10/2024 21:24:57
 * 
 */
public class Tbpolicygroup {

    private Integer id;
    private String modelno;
    private String groupname;
    private String groupkey;
    private String policydesc;
    private String approvallevel;
    private String keydesc;
    private String keyids;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date dateupdated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getGroupkey() {
        return groupkey;
    }

    public void setGroupkey(String groupkey) {
        this.groupkey = groupkey;
    }

    public String getPolicydesc() {
        return policydesc;
    }

    public void setPolicydesc(String policydesc) {
        this.policydesc = policydesc;
    }

    public String getApprovallevel() {
        return approvallevel;
    }

    public void setApprovallevel(String approvallevel) {
        this.approvallevel = approvallevel;
    }

    public String getKeydesc() {
        return keydesc;
    }

    public void setKeydesc(String keydesc) {
        this.keydesc = keydesc;
    }

    public String getKeyids() {
        return keyids;
    }

    public void setKeyids(String keyids) {
        this.keyids = keyids;
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

}
