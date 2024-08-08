
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbroleaccess
 *  08/04/2024 12:54:43
 * 
 */
public class Tbroleaccess {

    private TbroleaccessId id;
    private String modulename;
    private Date assigneddate;
    private String assignedby;

    public TbroleaccessId getId() {
        return id;
    }

    public void setId(TbroleaccessId id) {
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

    public String getAssignedby() {
        return assignedby;
    }

    public void setAssignedby(String assignedby) {
        this.assignedby = assignedby;
    }

}
