
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbchangeprofilehistory
 *  08/27/2024 14:22:56
 * 
 */
public class Tbchangeprofilehistory {

    private Integer id;
    private String memberid;
    private String changecategorytype;
    private String changefieldtype;
    private Date dateupdated;
    private String updatedby;
    private String source;
    private Integer updatecount;
    private String updateremarks;
    private Integer updaterefno;
    private String oldvalue;
    private String newvalue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getChangecategorytype() {
        return changecategorytype;
    }

    public void setChangecategorytype(String changecategorytype) {
        this.changecategorytype = changecategorytype;
    }

    public String getChangefieldtype() {
        return changefieldtype;
    }

    public void setChangefieldtype(String changefieldtype) {
        this.changefieldtype = changefieldtype;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getUpdatecount() {
        return updatecount;
    }

    public void setUpdatecount(Integer updatecount) {
        this.updatecount = updatecount;
    }

    public String getUpdateremarks() {
        return updateremarks;
    }

    public void setUpdateremarks(String updateremarks) {
        this.updateremarks = updateremarks;
    }

    public Integer getUpdaterefno() {
        return updaterefno;
    }

    public void setUpdaterefno(Integer updaterefno) {
        this.updaterefno = updaterefno;
    }

    public String getOldvalue() {
        return oldvalue;
    }

    public void setOldvalue(String oldvalue) {
        this.oldvalue = oldvalue;
    }

    public String getNewvalue() {
        return newvalue;
    }

    public void setNewvalue(String newvalue) {
        this.newvalue = newvalue;
    }

}
