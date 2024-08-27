
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbcollector
 *  08/27/2024 14:22:57
 * 
 */
public class Tbcollector {

    private Integer id;
    private String collectorid;
    private String collectorname;
    private String areacode;
    private String subareacode;
    private String status;
    private String createdby;
    private Date datecreated;

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

    public String getCollectorname() {
        return collectorname;
    }

    public void setCollectorname(String collectorname) {
        this.collectorname = collectorname;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public String getSubareacode() {
        return subareacode;
    }

    public void setSubareacode(String subareacode) {
        this.subareacode = subareacode;
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

}
