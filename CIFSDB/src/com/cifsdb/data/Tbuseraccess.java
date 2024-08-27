
package com.cifsdb.data;

import java.util.Date;


/**
 *  CIFSDB.Tbuseraccess
 *  08/27/2024 14:22:04
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
