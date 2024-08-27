
package com.coopdb.data;

import java.math.BigDecimal;


/**
 *  COOPDB.Tboutreturn
 *  08/27/2024 14:22:56
 * 
 */
public class Tboutreturn {

    private Integer id;
    private String txbatch;
    private String txdate;
    private String txrefno;
    private String txcode;
    private String accountno;
    private String checkbrstn;
    private BigDecimal checkamount;
    private String checkdate;
    private String checknumber;
    private Integer checktype;
    private String status;
    private String reason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTxbatch() {
        return txbatch;
    }

    public void setTxbatch(String txbatch) {
        this.txbatch = txbatch;
    }

    public String getTxdate() {
        return txdate;
    }

    public void setTxdate(String txdate) {
        this.txdate = txdate;
    }

    public String getTxrefno() {
        return txrefno;
    }

    public void setTxrefno(String txrefno) {
        this.txrefno = txrefno;
    }

    public String getTxcode() {
        return txcode;
    }

    public void setTxcode(String txcode) {
        this.txcode = txcode;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getCheckbrstn() {
        return checkbrstn;
    }

    public void setCheckbrstn(String checkbrstn) {
        this.checkbrstn = checkbrstn;
    }

    public BigDecimal getCheckamount() {
        return checkamount;
    }

    public void setCheckamount(BigDecimal checkamount) {
        this.checkamount = checkamount;
    }

    public String getCheckdate() {
        return checkdate;
    }

    public void setCheckdate(String checkdate) {
        this.checkdate = checkdate;
    }

    public String getChecknumber() {
        return checknumber;
    }

    public void setChecknumber(String checknumber) {
        this.checknumber = checknumber;
    }

    public Integer getChecktype() {
        return checktype;
    }

    public void setChecktype(Integer checktype) {
        this.checktype = checktype;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
