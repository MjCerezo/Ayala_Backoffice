
package com.cifsdb.data;

import java.util.Date;


/**
 *  CIFSDB.Tbhistory
 *  08/27/2024 14:22:04
 * 
 */
public class Tbhistory {

    private Integer id;
    private String cifno;
    private String username;
    private Date historydatetime;
    private String eventdescription;
    private String eventname;
    private String remarks;
    private byte[] appno;
    private Integer eventid;
    private byte[] ipaddress;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public byte[] getAppno() {
        return appno;
    }

    public void setAppno(byte[] appno) {
        this.appno = appno;
    }

    public Integer getEventid() {
        return eventid;
    }

    public void setEventid(Integer eventid) {
        this.eventid = eventid;
    }

    public byte[] getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(byte[] ipaddress) {
        this.ipaddress = ipaddress;
    }

}
