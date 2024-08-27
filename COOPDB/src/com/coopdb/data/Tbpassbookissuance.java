
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbpassbookissuance
 *  08/27/2024 14:22:56
 * 
 */
public class Tbpassbookissuance {

    private Integer id;
    private String accountno;
    private String oldpassbksn;
    private String newpssbksn;
    private String remarks;
    private String issuancetype;
    private Date txdate;
    private String brid;
    private String txby;

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

    public String getOldpassbksn() {
        return oldpassbksn;
    }

    public void setOldpassbksn(String oldpassbksn) {
        this.oldpassbksn = oldpassbksn;
    }

    public String getNewpssbksn() {
        return newpssbksn;
    }

    public void setNewpssbksn(String newpssbksn) {
        this.newpssbksn = newpssbksn;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getIssuancetype() {
        return issuancetype;
    }

    public void setIssuancetype(String issuancetype) {
        this.issuancetype = issuancetype;
    }

    public Date getTxdate() {
        return txdate;
    }

    public void setTxdate(Date txdate) {
        this.txdate = txdate;
    }

    public String getBrid() {
        return brid;
    }

    public void setBrid(String brid) {
        this.brid = brid;
    }

    public String getTxby() {
        return txby;
    }

    public void setTxby(String txby) {
        this.txby = txby;
    }

}
