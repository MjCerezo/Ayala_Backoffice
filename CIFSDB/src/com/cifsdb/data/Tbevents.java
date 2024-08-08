
package com.cifsdb.data;

import java.util.Date;


/**
 *  CIFSDB.Tbevents
 *  09/26/2023 10:13:06
 * 
 */
public class Tbevents {

    private TbeventsId id;
    private String eventtype;
    private String eventname;
    private Date eventdate;
    private String eventdesc;
    private String eventvenue;
    private String govtypeclassification;
    private Boolean isrequired;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date dateupdated;

    public TbeventsId getId() {
        return id;
    }

    public void setId(TbeventsId id) {
        this.id = id;
    }

    public String getEventtype() {
        return eventtype;
    }

    public void setEventtype(String eventtype) {
        this.eventtype = eventtype;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public Date getEventdate() {
        return eventdate;
    }

    public void setEventdate(Date eventdate) {
        this.eventdate = eventdate;
    }

    public String getEventdesc() {
        return eventdesc;
    }

    public void setEventdesc(String eventdesc) {
        this.eventdesc = eventdesc;
    }

    public String getEventvenue() {
        return eventvenue;
    }

    public void setEventvenue(String eventvenue) {
        this.eventvenue = eventvenue;
    }

    public String getGovtypeclassification() {
        return govtypeclassification;
    }

    public void setGovtypeclassification(String govtypeclassification) {
        this.govtypeclassification = govtypeclassification;
    }

    public Boolean getIsrequired() {
        return isrequired;
    }

    public void setIsrequired(Boolean isrequired) {
        this.isrequired = isrequired;
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
