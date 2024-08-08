
package com.cifsdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  CIFSDB.Tbmctxjrnl
 *  09/26/2023 10:13:05
 * 
 */
public class Tbmctxjrnl {

    private Integer id;
    private String accountno;
    private BigDecimal amount;
    private String issuingbr;
    private String txbr;
    private String mccheckno;
    private String txby;
    private Date txdate;
    private String txstatus;
    private String paymode;
    private String txor;
    private String name;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getIssuingbr() {
        return issuingbr;
    }

    public void setIssuingbr(String issuingbr) {
        this.issuingbr = issuingbr;
    }

    public String getTxbr() {
        return txbr;
    }

    public void setTxbr(String txbr) {
        this.txbr = txbr;
    }

    public String getMccheckno() {
        return mccheckno;
    }

    public void setMccheckno(String mccheckno) {
        this.mccheckno = mccheckno;
    }

    public String getTxby() {
        return txby;
    }

    public void setTxby(String txby) {
        this.txby = txby;
    }

    public Date getTxdate() {
        return txdate;
    }

    public void setTxdate(Date txdate) {
        this.txdate = txdate;
    }

    public String getTxstatus() {
        return txstatus;
    }

    public void setTxstatus(String txstatus) {
        this.txstatus = txstatus;
    }

    public String getPaymode() {
        return paymode;
    }

    public void setPaymode(String paymode) {
        this.paymode = paymode;
    }

    public String getTxor() {
        return txor;
    }

    public void setTxor(String txor) {
        this.txor = txor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
