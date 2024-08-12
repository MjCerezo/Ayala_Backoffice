
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbuserroles
 *  08/10/2024 21:24:57
 * 
 */
public class Tbuserroles {

    private TbuserrolesId id;
    private String rolename;
    private Date assigneddate;
    private String assignedby;

    public TbuserrolesId getId() {
        return id;
    }

    public void setId(TbuserrolesId id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
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
