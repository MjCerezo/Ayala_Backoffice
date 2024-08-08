
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbcifcancellationrequest
 *  06/21/2019 09:57:53
 * 
 */
public class Tbcifcancellationrequest {

    private String requestid;
    private String maincifno;
    private String fullname;
    private String customertype;
    private String status;
    private Date requesteddate;
    private String requestedby;
    private String remarks;
    private String cancellationcode;
    private String teamcode;
    private String makerequestid;
    private String reasonforcancellation;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getRequesteddate() {
        return requesteddate;
    }

    public void setRequesteddate(Date requesteddate) {
        this.requesteddate = requesteddate;
    }

    public String getRequestedby() {
        return requestedby;
    }

    public void setRequestedby(String requestedby) {
        this.requestedby = requestedby;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCancellationcode() {
        return cancellationcode;
    }

    public void setCancellationcode(String cancellationcode) {
        this.cancellationcode = cancellationcode;
    }

    public String getTeamcode() {
        return teamcode;
    }

    public void setTeamcode(String teamcode) {
        this.teamcode = teamcode;
    }

    public String getMakerequestid() {
        return makerequestid;
    }

    public void setMakerequestid(String makerequestid) {
        this.makerequestid = makerequestid;
    }

    public String getReasonforcancellation() {
        return reasonforcancellation;
    }

    public void setReasonforcancellation(String reasonforcancellation) {
        this.reasonforcancellation = reasonforcancellation;
    }

}
