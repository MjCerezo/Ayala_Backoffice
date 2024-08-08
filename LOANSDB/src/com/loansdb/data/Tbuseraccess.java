
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbuseraccess
 *  10/13/2020 10:21:35
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
