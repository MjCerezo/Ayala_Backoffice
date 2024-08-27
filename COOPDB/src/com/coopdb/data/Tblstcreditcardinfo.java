
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tblstcreditcardinfo
 *  08/27/2024 14:22:57
 * 
 */
public class Tblstcreditcardinfo {

    private Integer id;
    private String appno;
    private String bank;
    private String cardtype;
    private Date dateexpiry;
    private BigDecimal creditlimit;
    private BigDecimal limitbalance;
    private String cardno;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
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

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

}
