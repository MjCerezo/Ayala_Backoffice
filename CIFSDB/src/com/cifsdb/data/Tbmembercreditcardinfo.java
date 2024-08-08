
package com.cifsdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  CIFSDB.Tbmembercreditcardinfo
 *  09/26/2023 10:13:06
 * 
 */
public class Tbmembercreditcardinfo {

    private Integer id;
    private String membershipappid;
    private String memberid;
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

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
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
