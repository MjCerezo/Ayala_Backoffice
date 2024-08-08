
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbfsanalysis
 *  08/31/2018 10:16:39
 * 
 */
public class Tbfsanalysis {

    private Integer fsid;
    private String cfappno;
    private String analysis;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date lastupdated;

    public Integer getFsid() {
        return fsid;
    }

    public void setFsid(Integer fsid) {
        this.fsid = fsid;
    }

    public String getCfappno() {
        return cfappno;
    }

    public void setCfappno(String cfappno) {
        this.cfappno = cfappno;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
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

}
