
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbcfmaintenance
 *  08/27/2024 14:22:56
 * 
 */
public class Tbcfmaintenance {

    private String facilitycode;
    private String facilityname;
    private String type;
    private Boolean enablesubfacility;
    private Boolean enablecoobligor;
    private Date datecreated;
    private String createdby;
    private Date dateupdated;
    private String updatedby;

    public String getFacilitycode() {
        return facilitycode;
    }

    public void setFacilitycode(String facilitycode) {
        this.facilitycode = facilitycode;
    }

    public String getFacilityname() {
        return facilityname;
    }

    public void setFacilityname(String facilityname) {
        this.facilityname = facilityname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getEnablesubfacility() {
        return enablesubfacility;
    }

    public void setEnablesubfacility(Boolean enablesubfacility) {
        this.enablesubfacility = enablesubfacility;
    }

    public Boolean getEnablecoobligor() {
        return enablecoobligor;
    }

    public void setEnablecoobligor(Boolean enablecoobligor) {
        this.enablecoobligor = enablecoobligor;
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

    public Date getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

}
