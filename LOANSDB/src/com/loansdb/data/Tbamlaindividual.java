
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbamlaindividual
 *  06/21/2019 09:57:53
 * 
 */
public class Tbamlaindividual {

    private String amlalistid;
    private String cifno;
    private String country;
    private String description;
    private String lastname;
    private String firstname;
    private String middlename;
    private String suffix;
    private Date dateofbirth;
    private Date startdate;
    private Date enddate;
    private String remarks;
    private Date datecreated;
    private String createdby;
    private String amlalistsource;
    private String amlaliststatus;
    private Boolean isopenended;

    public String getAmlalistid() {
        return amlalistid;
    }

    public void setAmlalistid(String amlalistid) {
        this.amlalistid = amlalistid;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getAmlalistsource() {
        return amlalistsource;
    }

    public void setAmlalistsource(String amlalistsource) {
        this.amlalistsource = amlalistsource;
    }

    public String getAmlaliststatus() {
        return amlaliststatus;
    }

    public void setAmlaliststatus(String amlaliststatus) {
        this.amlaliststatus = amlaliststatus;
    }

    public Boolean getIsopenended() {
        return isopenended;
    }

    public void setIsopenended(Boolean isopenended) {
        this.isopenended = isopenended;
    }

}
