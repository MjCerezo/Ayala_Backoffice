
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbdepedcodes
 *  06/21/2019 09:57:53
 * 
 */
public class Tbdepedcodes {

    private TbdepedcodesId id;
    private String regionname;
    private String divisionname;
    private String stationname;
    private String updatedby;
    private Date dateupdated;
    private String createdby;
    private Date datecreated;

    public TbdepedcodesId getId() {
        return id;
    }

    public void setId(TbdepedcodesId id) {
        this.id = id;
    }

    public String getRegionname() {
        return regionname;
    }

    public void setRegionname(String regionname) {
        this.regionname = regionname;
    }

    public String getDivisionname() {
        return divisionname;
    }

    public void setDivisionname(String divisionname) {
        this.divisionname = divisionname;
    }

    public String getStationname() {
        return stationname;
    }

    public void setStationname(String stationname) {
        this.stationname = stationname;
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
