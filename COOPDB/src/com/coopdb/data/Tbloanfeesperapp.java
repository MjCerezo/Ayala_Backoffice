
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbloanfeesperapp
 *  08/10/2024 21:24:57
 * 
 */
public class Tbloanfeesperapp {

    private TbloanfeesperappId id;
    private BigDecimal feeamount;
    private String collectionrule;
    private String status;
    private Date dateupdate;
    private String updatedby;
    private String acctno;
    private Integer doccount;
    private BigDecimal docrate;
    private String payableto;
    private Boolean paidflag;
    private String orno;
    private Date ordate;

    public TbloanfeesperappId getId() {
        return id;
    }

    public void setId(TbloanfeesperappId id) {
        this.id = id;
    }

    public BigDecimal getFeeamount() {
        return feeamount;
    }

    public void setFeeamount(BigDecimal feeamount) {
        this.feeamount = feeamount;
    }

    public String getCollectionrule() {
        return collectionrule;
    }

    public void setCollectionrule(String collectionrule) {
        this.collectionrule = collectionrule;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateupdate() {
        return dateupdate;
    }

    public void setDateupdate(Date dateupdate) {
        this.dateupdate = dateupdate;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public String getAcctno() {
        return acctno;
    }

    public void setAcctno(String acctno) {
        this.acctno = acctno;
    }

    public Integer getDoccount() {
        return doccount;
    }

    public void setDoccount(Integer doccount) {
        this.doccount = doccount;
    }

    public BigDecimal getDocrate() {
        return docrate;
    }

    public void setDocrate(BigDecimal docrate) {
        this.docrate = docrate;
    }

    public String getPayableto() {
        return payableto;
    }

    public void setPayableto(String payableto) {
        this.payableto = payableto;
    }

    public Boolean getPaidflag() {
        return paidflag;
    }

    public void setPaidflag(Boolean paidflag) {
        this.paidflag = paidflag;
    }

    public String getOrno() {
        return orno;
    }

    public void setOrno(String orno) {
        this.orno = orno;
    }

    public Date getOrdate() {
        return ordate;
    }

    public void setOrdate(Date ordate) {
        this.ordate = ordate;
    }

}
