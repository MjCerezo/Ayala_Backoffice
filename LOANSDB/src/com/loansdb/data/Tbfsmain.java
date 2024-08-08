
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbfsmain
 *  10/13/2020 10:21:35
 * 
 */
public class Tbfsmain {

    private TbfsmainId id;
    private String fsdesc;
    private String fsauditorsopinion;
    private String status;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date lastupdated;
    private Date auditdate;
    private String auditor;

    public TbfsmainId getId() {
        return id;
    }

    public void setId(TbfsmainId id) {
        this.id = id;
    }

    public String getFsdesc() {
        return fsdesc;
    }

    public void setFsdesc(String fsdesc) {
        this.fsdesc = fsdesc;
    }

    public String getFsauditorsopinion() {
        return fsauditorsopinion;
    }

    public void setFsauditorsopinion(String fsauditorsopinion) {
        this.fsauditorsopinion = fsauditorsopinion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Date getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(Date lastupdated) {
        this.lastupdated = lastupdated;
    }

    public Date getAuditdate() {
        return auditdate;
    }

    public void setAuditdate(Date auditdate) {
        this.auditdate = auditdate;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

}
