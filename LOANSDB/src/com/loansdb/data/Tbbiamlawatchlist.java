
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbbiamlawatchlist
 *  10/13/2020 10:21:35
 * 
 */
public class Tbbiamlawatchlist {

    private TbbiamlawatchlistId id;
    private String cifno;
    private String fullname;
    private String description;
    private String amlastatus;
    private String amlasource;
    private Date startdate;
    private Date enddate;
    private Date datecreated;
    private String remarks;
    private String biusername;

    public TbbiamlawatchlistId getId() {
        return id;
    }

    public void setId(TbbiamlawatchlistId id) {
        this.id = id;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmlastatus() {
        return amlastatus;
    }

    public void setAmlastatus(String amlastatus) {
        this.amlastatus = amlastatus;
    }

    public String getAmlasource() {
        return amlasource;
    }

    public void setAmlasource(String amlasource) {
        this.amlasource = amlasource;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getBiusername() {
        return biusername;
    }

    public void setBiusername(String biusername) {
        this.biusername = biusername;
    }

}
