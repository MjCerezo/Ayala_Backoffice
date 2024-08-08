
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbmembereventschecklist
 *  08/04/2024 12:54:41
 * 
 */
public class Tbmembereventschecklist {

    private Integer id;
    private String membershipappid;
    private String membershipid;
    private String governancetype;
    private Integer eventcode;
    private String eventtype;
    private String eventname;
    private Date eventdate;
    private Boolean isrequired;
    private Boolean hasattended;
    private Boolean hasvoted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMembershipappid() {
        return membershipappid;
    }

    public void setMembershipappid(String membershipappid) {
        this.membershipappid = membershipappid;
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

    public Integer getEventcode() {
        return eventcode;
    }

    public void setEventcode(Integer eventcode) {
        this.eventcode = eventcode;
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

    public Boolean getHasvoted() {
        return hasvoted;
    }

    public void setHasvoted(Boolean hasvoted) {
        this.hasvoted = hasvoted;
    }

}
