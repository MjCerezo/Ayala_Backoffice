
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbaudittrail
 *  08/27/2024 14:22:57
 * 
 */
public class Tbaudittrail {

    private Integer audittrailid;
    private String eventname;
    private String eventdescription;
    private String username;
    private Date eventdatetime;
    private String module;
    private String ipaddress;

    public Integer getAudittrailid() {
        return audittrailid;
    }

    public void setAudittrailid(Integer audittrailid) {
        this.audittrailid = audittrailid;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getEventdescription() {
        return eventdescription;
    }

    public void setEventdescription(String eventdescription) {
        this.eventdescription = eventdescription;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getEventdatetime() {
        return eventdatetime;
    }

    public void setEventdatetime(Date eventdatetime) {
        this.eventdatetime = eventdatetime;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

}
