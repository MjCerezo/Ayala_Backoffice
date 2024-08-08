
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbcifmergerequest
 *  06/21/2019 09:57:53
 * 
 */
public class Tbcifmergerequest {

    private String requestid;
    private String maincifno;
    private String fullname;
    private String customertype;
    private String mergedcifno;
    private String status;
    private Date requestdate;
    private String requeststatus;
    private String approvedby;
    private Date dateapproved;
    private String remarks;
    private String approverremarks;
    private String teamcode;
    private String requestedby;

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public String getMaincifno() {
        return maincifno;
    }

    public void setMaincifno(String maincifno) {
        this.maincifno = maincifno;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCustomertype() {
        return customertype;
    }

    public void setCustomertype(String customertype) {
        this.customertype = customertype;
    }

    public String getMergedcifno() {
        return mergedcifno;
    }

    public void setMergedcifno(String mergedcifno) {
        this.mergedcifno = mergedcifno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getRequestdate() {
        return requestdate;
    }

    public void setRequestdate(Date requestdate) {
        this.requestdate = requestdate;
    }

    public String getRequeststatus() {
        return requeststatus;
    }

    public void setRequeststatus(String requeststatus) {
        this.requeststatus = requeststatus;
    }

    public String getApprovedby() {
        return approvedby;
    }

    public void setApprovedby(String approvedby) {
        this.approvedby = approvedby;
    }

    public Date getDateapproved() {
        return dateapproved;
    }

    public void setDateapproved(Date dateapproved) {
        this.dateapproved = dateapproved;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getApproverremarks() {
        return approverremarks;
    }

    public void setApproverremarks(String approverremarks) {
        this.approverremarks = approverremarks;
    }

    public String getTeamcode() {
        return teamcode;
    }

    public void setTeamcode(String teamcode) {
        this.teamcode = teamcode;
    }

    public String getRequestedby() {
        return requestedby;
    }

    public void setRequestedby(String requestedby) {
        this.requestedby = requestedby;
    }

}
