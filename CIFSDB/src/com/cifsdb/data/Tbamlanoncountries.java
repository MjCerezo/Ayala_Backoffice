
package com.cifsdb.data;

import java.util.Date;


/**
 *  CIFSDB.Tbamlanoncountries
 *  08/06/2024 19:26:36
 * 
 */
public class Tbamlanoncountries {

    private String countrycode;
    private String countryname;
    private String reference;
    private Date startdate;
    private Date enddate;
    private String remarks;
    private Date datecreated;
    private String createdby;
    private String uploadrefid;
    private Date uploadeddate;
    private String uploadbatch;

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
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

    public String getUploadrefid() {
        return uploadrefid;
    }

    public void setUploadrefid(String uploadrefid) {
        this.uploadrefid = uploadrefid;
    }

    public Date getUploadeddate() {
        return uploadeddate;
    }

    public void setUploadeddate(Date uploadeddate) {
        this.uploadeddate = uploadeddate;
    }

    public String getUploadbatch() {
        return uploadbatch;
    }

    public void setUploadbatch(String uploadbatch) {
        this.uploadbatch = uploadbatch;
    }

}
