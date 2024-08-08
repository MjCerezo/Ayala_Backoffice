
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbrates
 *  08/04/2024 12:54:43
 * 
 */
public class Tbrates {

    private Integer id;
    private String currency;
    private BigDecimal boardrate;
    private BigDecimal irr;
    private BigDecimal pds;
    private String createdby;
    private Date createddate;
    private String updatedby;
    private Date updateddate;
    private String buySell;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getBoardrate() {
        return boardrate;
    }

    public void setBoardrate(BigDecimal boardrate) {
        this.boardrate = boardrate;
    }

    public BigDecimal getIrr() {
        return irr;
    }

    public void setIrr(BigDecimal irr) {
        this.irr = irr;
    }

    public BigDecimal getPds() {
        return pds;
    }

    public void setPds(BigDecimal pds) {
        this.pds = pds;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public Date getUpdateddate() {
        return updateddate;
    }

    public void setUpdateddate(Date updateddate) {
        this.updateddate = updateddate;
    }

    public String getBuySell() {
        return buySell;
    }

    public void setBuySell(String buySell) {
        this.buySell = buySell;
    }

}
