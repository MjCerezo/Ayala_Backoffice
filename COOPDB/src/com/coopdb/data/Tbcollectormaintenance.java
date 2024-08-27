
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbcollectormaintenance
 *  08/27/2024 14:22:57
 * 
 */
public class Tbcollectormaintenance {

    private Integer id;
    private String collectorid;
    private String collectouserrname;
    private String collectorname;
    private Date datecreated;
    private String createdby;
    private String branchcode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCollectorid() {
        return collectorid;
    }

    public void setCollectorid(String collectorid) {
        this.collectorid = collectorid;
    }

    public String getCollectouserrname() {
        return collectouserrname;
    }

    public void setCollectouserrname(String collectouserrname) {
        this.collectouserrname = collectouserrname;
    }

    public String getCollectorname() {
        return collectorname;
    }

    public void setCollectorname(String collectorname) {
        this.collectorname = collectorname;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getBranchcode() {
        return branchcode;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode;
    }

}
