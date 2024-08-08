
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tboverride
 *  11/06/2020 16:14:09
 * 
 */
public class Tboverride {

    private Integer id;
    private Integer sequence;
    private BigDecimal chargeamount;
    private String accountno;
    private String txcode;
    private Date txdate;
    private String txby;
    private String overrideby;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public BigDecimal getChargeamount() {
        return chargeamount;
    }

    public void setChargeamount(BigDecimal chargeamount) {
        this.chargeamount = chargeamount;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getTxcode() {
        return txcode;
    }

    public void setTxcode(String txcode) {
        this.txcode = txcode;
    }

    public Date getTxdate() {
        return txdate;
    }

    public void setTxdate(Date txdate) {
        this.txdate = txdate;
    }

    public String getTxby() {
        return txby;
    }

    public void setTxby(String txby) {
        this.txby = txby;
    }

    public String getOverrideby() {
        return overrideby;
    }

    public void setOverrideby(String overrideby) {
        this.overrideby = overrideby;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
