
package com.cifsdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  CIFSDB.Tbmiscellaneous
 *  09/26/2023 10:13:06
 * 
 */
public class Tbmiscellaneous {

    private Integer id;
    private Date txdate;
    private String payeename;
    private String payeeacctno;
    private String accountno;
    private BigDecimal amount;
    private String currency;
    private String reason;
    private String remarks;
    private String relrecmode;
    private String txcode;
    private String txby;
    private String checktype;
    private String checkno;
    private Date checkdate;
    private String debitacctno;
    private String unitid;
    private String instcode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTxdate() {
        return txdate;
    }

    public void setTxdate(Date txdate) {
        this.txdate = txdate;
    }

    public String getPayeename() {
        return payeename;
    }

    public void setPayeename(String payeename) {
        this.payeename = payeename;
    }

    public String getPayeeacctno() {
        return payeeacctno;
    }

    public void setPayeeacctno(String payeeacctno) {
        this.payeeacctno = payeeacctno;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRelrecmode() {
        return relrecmode;
    }

    public void setRelrecmode(String relrecmode) {
        this.relrecmode = relrecmode;
    }

    public String getTxcode() {
        return txcode;
    }

    public void setTxcode(String txcode) {
        this.txcode = txcode;
    }

    public String getTxby() {
        return txby;
    }

    public void setTxby(String txby) {
        this.txby = txby;
    }

    public String getChecktype() {
        return checktype;
    }

    public void setChecktype(String checktype) {
        this.checktype = checktype;
    }

    public String getCheckno() {
        return checkno;
    }

    public void setCheckno(String checkno) {
        this.checkno = checkno;
    }

    public Date getCheckdate() {
        return checkdate;
    }

    public void setCheckdate(Date checkdate) {
        this.checkdate = checkdate;
    }

    public String getDebitacctno() {
        return debitacctno;
    }

    public void setDebitacctno(String debitacctno) {
        this.debitacctno = debitacctno;
    }

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public String getInstcode() {
        return instcode;
    }

    public void setInstcode(String instcode) {
        this.instcode = instcode;
    }

}
