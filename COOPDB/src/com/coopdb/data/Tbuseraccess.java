
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbuseraccess
 *  08/04/2024 12:54:43
 * 
 */
public class Tbuseraccess {

    private TbuseraccessId id;
    private String modulename;
    private Date assigneddate;

    public TbuseraccessId getId() {
        return id;
    }

    public void setId(TbuseraccessId id) {
        this.id = id;
    }

    public String getModulename() {
        return modulename;
    }

    public void setModulename(String modulename) {
        this.modulename = modulename;
    }

    public Date getAssigneddate() {
        return assigneddate;
    }

    public void setAssigneddate(Date assigneddate) {
        this.assigneddate = assigneddate;
    }

}
