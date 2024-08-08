
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbcollectionarea
 *  08/04/2024 12:54:43
 * 
 */
public class Tbcollectionarea {

    private Integer id;
    private String areacode;
    private String areaname;
    private String subareacode;
    private String subareaname;
    private String status;
    private String createdby;
    private Date datecreated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
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
