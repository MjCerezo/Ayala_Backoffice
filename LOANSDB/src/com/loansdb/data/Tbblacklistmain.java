
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbblacklistmain
 *  06/21/2019 09:57:53
 * 
 */
public class Tbblacklistmain {

    private String blacklistid;
    private String cifno;
    private String fullname;
    private Date dateofbirth;
    private Date dateofincorporation;
    private Date startdate;
    private Date enddate;
    private String status;
    private String blacklistsource;
    private String customertype;
    private Date createddate;
    private String uploadrefid;
    private Date uploadeddate;
    private String uploadbatch;
    private Boolean isopenended;

    public String getBlacklistid() {
        return blacklistid;
    }

    public void setBlacklistid(String blacklistid) {
        this.blacklistid = blacklistid;
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

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public Date getDateofincorporation() {
        return dateofincorporation;
    }

    public void setDateofincorporation(Date dateofincorporation) {
        this.dateofincorporation = dateofincorporation;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBlacklistsource() {
        return blacklistsource;
    }

    public void setBlacklistsource(String blacklistsource) {
        this.blacklistsource = blacklistsource;
    }

    public String getCustomertype() {
        return customertype;
    }

    public void setCustomertype(String customertype) {
        this.customertype = customertype;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
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

    public Boolean getIsopenended() {
        return isopenended;
    }

    public void setIsopenended(Boolean isopenended) {
        this.isopenended = isopenended;
    }

}
