
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbhistory
 *  10/13/2020 10:21:35
 * 
 */
public class Tbhistory {

    private Integer id;
    private String appno;
    private String username;
    private Date historydatetime;
    private String eventdescription;
    private String eventname;
    private Integer eventid;
    private String remarks;
    private String ipaddress;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getHistorydatetime() {
        return historydatetime;
    }

    public void setHistorydatetime(Date historydatetime) {
        this.historydatetime = historydatetime;
    }

    public String getEventdescription() {
        return eventdescription;
    }

    public void setEventdescription(String eventdescription) {
        this.eventdescription = eventdescription;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public Integer getEventid() {
        return eventid;
    }

    public void setEventid(Integer eventid) {
        this.eventid = eventid;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

}
