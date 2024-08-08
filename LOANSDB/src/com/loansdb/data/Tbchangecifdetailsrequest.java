
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbchangecifdetailsrequest
 *  06/21/2019 09:57:53
 * 
 */
public class Tbchangecifdetailsrequest {

    private String requestid;
    private String cifno;
    private String fullname;
    private String changetype;
    private String requestedby;
    private Date daterequested;
    private String requeststatus;
    private String requesttype;
    private String customertype;
    private String remarks;
    private String approvedby;
    private Date dateapprove;
    private String approverremarks;
    private String teamcode;

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
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

    public String getChangetype() {
        return changetype;
    }

    public void setChangetype(String changetype) {
        this.changetype = changetype;
    }

    public String getRequestedby() {
        return requestedby;
    }

    public void setRequestedby(String requestedby) {
        this.requestedby = requestedby;
    }

    public Date getDaterequested() {
        return daterequested;
    }

    public void setDaterequested(Date daterequested) {
        this.daterequested = daterequested;
    }

    public String getRequeststatus() {
        return requeststatus;
    }

    public void setRequeststatus(String requeststatus) {
        this.requeststatus = requeststatus;
    }

    public String getRequesttype() {
        return requesttype;
    }

    public void setRequesttype(String requesttype) {
        this.requesttype = requesttype;
    }

    public String getCustomertype() {
        return customertype;
    }

    public void setCustomertype(String customertype) {
        this.customertype = customertype;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getApprovedby() {
        return approvedby;
    }

    public void setApprovedby(String approvedby) {
        this.approvedby = approvedby;
    }

    public Date getDateapprove() {
        return dateapprove;
    }

    public void setDateapprove(Date dateapprove) {
        this.dateapprove = dateapprove;
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

}
