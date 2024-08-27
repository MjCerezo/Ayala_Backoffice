
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbfreezeaccount
 *  08/27/2024 14:22:56
 * 
 */
public class Tbfreezeaccount {

    private Integer id;
    private String accountno;
    private Date effectivitydate;
    private Date expirydate;
    private String remarks;
    private String txstatus;
    private Date txstatusdate;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date dateupdated;
    private String liftremarks;
    private String branchcode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public Date getEffectivitydate() {
        return effectivitydate;
    }

    public void setEffectivitydate(Date effectivitydate) {
        this.effectivitydate = effectivitydate;
    }

    public Date getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(Date expirydate) {
        this.expirydate = expirydate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTxstatus() {
        return txstatus;
    }

    public void setTxstatus(String txstatus) {
        this.txstatus = txstatus;
    }

    public Date getTxstatusdate() {
        return txstatusdate;
    }

    public void setTxstatusdate(Date txstatusdate) {
        this.txstatusdate = txstatusdate;
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

    public String getLiftremarks() {
        return liftremarks;
    }

    public void setLiftremarks(String liftremarks) {
        this.liftremarks = liftremarks;
    }

    public String getBranchcode() {
        return branchcode;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode;
    }

}
