
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbgovernance
 *  12/05/2018 13:56:22
 * 
 */
public class Tbgovernance {

    private Integer id;
    private String membershipid;
    private String governancetype;
    private String eventtype;
    private String eventname;
    private Date eventdate;
    private String eventdescription;
    private String remarks;
    private Boolean isrequired;
    private Boolean hasattended;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMembershipid() {
        return membershipid;
    }

    public void setMembershipid(String membershipid) {
        this.membershipid = membershipid;
    }

    public String getGovernancetype() {
        return governancetype;
    }

    public void setGovernancetype(String governancetype) {
        this.governancetype = governancetype;
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

    public String getEventdescription() {
        return eventdescription;
    }

    public void setEventdescription(String eventdescription) {
        this.eventdescription = eventdescription;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getIsrequired() {
        return isrequired;
    }

    public void setIsrequired(Boolean isrequired) {
        this.isrequired = isrequired;
    }

    public Boolean getHasattended() {
        return hasattended;
    }

    public void setHasattended(Boolean hasattended) {
        this.hasattended = hasattended;
    }

}
