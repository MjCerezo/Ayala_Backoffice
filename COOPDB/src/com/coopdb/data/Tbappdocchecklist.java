
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbappdocchecklist
 *  07/23/2018 11:52:21
 * 
 */
public class Tbappdocchecklist {

    private Integer id;
    private String memberid;
    private String documentcode;
    private String documentname;
    private Boolean isrequired;
    private Boolean issubmitted;
    private Boolean datesubmitted;
    private Boolean isrequestwaiver;
    private Date dateapproved;
    private Boolean ispoa;
    private String reqtype;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getDocumentcode() {
        return documentcode;
    }

    public void setDocumentcode(String documentcode) {
        this.documentcode = documentcode;
    }

    public String getDocumentname() {
        return documentname;
    }

    public void setDocumentname(String documentname) {
        this.documentname = documentname;
    }

    public Boolean getIsrequired() {
        return isrequired;
    }

    public void setIsrequired(Boolean isrequired) {
        this.isrequired = isrequired;
    }

    public Boolean getIssubmitted() {
        return issubmitted;
    }

    public void setIssubmitted(Boolean issubmitted) {
        this.issubmitted = issubmitted;
    }

    public Boolean getDatesubmitted() {
        return datesubmitted;
    }

    public void setDatesubmitted(Boolean datesubmitted) {
        this.datesubmitted = datesubmitted;
    }

    public Boolean getIsrequestwaiver() {
        return isrequestwaiver;
    }

    public void setIsrequestwaiver(Boolean isrequestwaiver) {
        this.isrequestwaiver = isrequestwaiver;
    }

    public Date getDateapproved() {
        return dateapproved;
    }

    public void setDateapproved(Date dateapproved) {
        this.dateapproved = dateapproved;
    }

    public Boolean getIspoa() {
        return ispoa;
    }

    public void setIspoa(Boolean ispoa) {
        this.ispoa = ispoa;
    }

    public String getReqtype() {
        return reqtype;
    }

    public void setReqtype(String reqtype) {
        this.reqtype = reqtype;
    }

}
