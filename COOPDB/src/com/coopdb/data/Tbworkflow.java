
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbworkflow
 *  08/04/2024 12:54:43
 * 
 */
public class Tbworkflow {

    private Integer workflowid;
    private String workflowname;
    private Integer sequenceno;
    private Boolean isactive;
    private String remarks;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date dateupdated;

    public Integer getWorkflowid() {
        return workflowid;
    }

    public void setWorkflowid(Integer workflowid) {
        this.workflowid = workflowid;
    }

    public String getWorkflowname() {
        return workflowname;
    }

    public void setWorkflowname(String workflowname) {
        this.workflowname = workflowname;
    }

    public Integer getSequenceno() {
        return sequenceno;
    }

    public void setSequenceno(Integer sequenceno) {
        this.sequenceno = sequenceno;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
