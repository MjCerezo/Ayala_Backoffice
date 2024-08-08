
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbholdamtcheck
 *  08/04/2024 12:54:43
 * 
 */
public class Tbholdamtcheck {

    private Integer id;
    private String accountno;
    private String accountname;
    private String checkno;
    private BigDecimal amount;
    private String currency;
    private Date datehold;
    private Date validitydate;
    private Date expirydate;
    private Date datereleased;
    private Date dateupdated;
    private String holdby;
    private String holdreason;
    private String releaseby;
    private String releasereason;
    private String remarks;
    private String txcode;
    private Integer status;
    private String unit;
    private String instcode;

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

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getCheckno() {
        return checkno;
    }

    public void setCheckno(String checkno) {
        this.checkno = checkno;
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

    public Date getDatehold() {
        return datehold;
    }

    public void setDatehold(Date datehold) {
        this.datehold = datehold;
    }

    public Date getValiditydate() {
        return validitydate;
    }

    public void setValiditydate(Date validitydate) {
        this.validitydate = validitydate;
    }

    public Date getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(Date expirydate) {
        this.expirydate = expirydate;
    }

    public Date getDatereleased() {
        return datereleased;
    }

    public void setDatereleased(Date datereleased) {
        this.datereleased = datereleased;
    }

    public Date getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }

    public String getHoldby() {
        return holdby;
    }

    public void setHoldby(String holdby) {
        this.holdby = holdby;
    }

    public String getHoldreason() {
        return holdreason;
    }

    public void setHoldreason(String holdreason) {
        this.holdreason = holdreason;
    }

    public String getReleaseby() {
        return releaseby;
    }

    public void setReleaseby(String releaseby) {
        this.releaseby = releaseby;
    }

    public String getReleasereason() {
        return releasereason;
    }

    public void setReleasereason(String releasereason) {
        this.releasereason = releasereason;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTxcode() {
        return txcode;
    }

    public void setTxcode(String txcode) {
        this.txcode = txcode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getInstcode() {
        return instcode;
    }

    public void setInstcode(String instcode) {
        this.instcode = instcode;
    }

}
