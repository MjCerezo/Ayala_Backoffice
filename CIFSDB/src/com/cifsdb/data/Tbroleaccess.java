
package com.cifsdb.data;

import java.util.Date;


/**
 *  CIFSDB.Tbroleaccess
 *  08/06/2024 19:26:36
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
