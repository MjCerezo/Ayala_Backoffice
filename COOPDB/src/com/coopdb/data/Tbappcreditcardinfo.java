
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbappcreditcardinfo
 *  08/10/2024 21:24:56
 * 
 */
public class Tbappcreditcardinfo {

    private Integer id;
    private String membershipappid;
    private String bank;
    private String cardtype;
    private Date dateexpiry;
    private BigDecimal creditlimit;
    private BigDecimal limitbalance;
    private BigDecimal outstandingbalance;
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMembershipappid() {
        return membershipappid;
    }

    public void setMembershipappid(String membershipappid) {
        this.membershipappid = membershipappid;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public Date getDateexpiry() {
        return dateexpiry;
    }

    public void setDateexpiry(Date dateexpiry) {
        this.dateexpiry = dateexpiry;
    }

    public BigDecimal getCreditlimit() {
        return creditlimit;
    }

    public void setCreditlimit(BigDecimal creditlimit) {
        this.creditlimit = creditlimit;
    }

    public BigDecimal getLimitbalance() {
        return limitbalance;
    }

    public void setLimitbalance(BigDecimal limitbalance) {
        this.limitbalance = limitbalance;
    }

    public BigDecimal getOutstandingbalance() {
        return outstandingbalance;
    }

    public void setOutstandingbalance(BigDecimal outstandingbalance) {
        this.outstandingbalance = outstandingbalance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
