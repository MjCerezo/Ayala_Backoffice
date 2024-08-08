
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbhistory
 *  08/04/2024 12:54:42
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

}
