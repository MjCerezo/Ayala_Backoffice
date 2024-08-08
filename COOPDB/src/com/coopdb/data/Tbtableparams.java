
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbtableparams
 *  08/04/2024 12:54:40
 * 
 */
public class Tbtableparams {

    private String tbcode;
    private String dbcode;
    private String tbname;
    private String tbnametodisplay;
    private Integer lastfieldseqno;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date dateupdated;

    public String getTbcode() {
        return tbcode;
    }

    public void setTbcode(String tbcode) {
        this.tbcode = tbcode;
    }

    public String getDbcode() {
        return dbcode;
    }

    public void setDbcode(String dbcode) {
        this.dbcode = dbcode;
    }

    public String getTbname() {
        return tbname;
    }

    public void setTbname(String tbname) {
        this.tbname = tbname;
    }

    public String getTbnametodisplay() {
        return tbnametodisplay;
    }

    public void setTbnametodisplay(String tbnametodisplay) {
        this.tbnametodisplay = tbnametodisplay;
    }

    public Integer getLastfieldseqno() {
        return lastfieldseqno;
    }

    public void setLastfieldseqno(Integer lastfieldseqno) {
        this.lastfieldseqno = lastfieldseqno;
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
