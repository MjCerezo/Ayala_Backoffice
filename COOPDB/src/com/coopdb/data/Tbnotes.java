
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbnotes
 *  08/04/2024 12:54:42
 * 
 */
public class Tbnotes {

    private Integer noteid;
    private String cifno;
    private Date noteddate;
    private String notedbyuser;
    private String notedby;
    private String notetype;
    private String notes;
    private Boolean isurgent;
    private Date dateupdated;
    private String lastupdatedby;
    private String lastupdatedname;
    private Date datetimeofcall;
    private String purposeofcall;
    private String phoneno;

    public Integer getNoteid() {
        return noteid;
    }

    public void setNoteid(Integer noteid) {
        this.noteid = noteid;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public Date getNoteddate() {
        return noteddate;
    }

    public void setNoteddate(Date noteddate) {
        this.noteddate = noteddate;
    }

    public String getNotedbyuser() {
        return notedbyuser;
    }

    public void setNotedbyuser(String notedbyuser) {
        this.notedbyuser = notedbyuser;
    }

    public String getNotedby() {
        return notedby;
    }

    public void setNotedby(String notedby) {
        this.notedby = notedby;
    }

    public String getNotetype() {
        return notetype;
    }

    public void setNotetype(String notetype) {
        this.notetype = notetype;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getIsurgent() {
        return isurgent;
    }

    public void setIsurgent(Boolean isurgent) {
        this.isurgent = isurgent;
    }

    public Date getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }

    public String getLastupdatedby() {
        return lastupdatedby;
    }

    public void setLastupdatedby(String lastupdatedby) {
        this.lastupdatedby = lastupdatedby;
    }

    public String getLastupdatedname() {
        return lastupdatedname;
    }

    public void setLastupdatedname(String lastupdatedname) {
        this.lastupdatedname = lastupdatedname;
    }

    public Date getDatetimeofcall() {
        return datetimeofcall;
    }

    public void setDatetimeofcall(Date datetimeofcall) {
        this.datetimeofcall = datetimeofcall;
    }

    public String getPurposeofcall() {
        return purposeofcall;
    }

    public void setPurposeofcall(String purposeofcall) {
        this.purposeofcall = purposeofcall;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

}
