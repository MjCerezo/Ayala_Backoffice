
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbcollectorpersubarea
 *  08/04/2024 12:54:42
 * 
 */
public class Tbcollectorpersubarea {

    private Integer id;
    private String branchcode;
    private String areacode;
    private String subareacode;
    private String subareaname;
    private String collectorid;
    private Date datecreated;
    private String createdby;
    private String username;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBranchcode() {
        return branchcode;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode;
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

    public String getSubareaname() {
        return subareaname;
    }

    public void setSubareaname(String subareaname) {
        this.subareaname = subareaname;
    }

    public String getCollectorid() {
        return collectorid;
    }

    public void setCollectorid(String collectorid) {
        this.collectorid = collectorid;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
