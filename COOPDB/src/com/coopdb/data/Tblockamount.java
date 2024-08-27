
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tblockamount
 *  08/27/2024 14:22:56
 * 
 */
public class Tblockamount {

    private Integer id;
    private String typeoflock;
    private String txrefno;
    private String accountno;
    private BigDecimal txamount;
    private String txstatus;
    private Date txstatusdate;
    private Date effectivitydate;
    private Date expirydate;
    private String lockreason;
    private String unlockreason;
    private String remarks;
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

    public String getTypeoflock() {
        return typeoflock;
    }

    public void setTypeoflock(String typeoflock) {
        this.typeoflock = typeoflock;
    }

    public String getTxrefno() {
        return txrefno;
    }

    public void setTxrefno(String txrefno) {
        this.txrefno = txrefno;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public BigDecimal getTxamount() {
        return txamount;
    }

    public void setTxamount(BigDecimal txamount) {
        this.txamount = txamount;
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

    public String getLockreason() {
        return lockreason;
    }

    public void setLockreason(String lockreason) {
        this.lockreason = lockreason;
    }

    public String getUnlockreason() {
        return unlockreason;
    }

    public void setUnlockreason(String unlockreason) {
        this.unlockreason = unlockreason;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
