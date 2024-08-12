
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbloanoffset
 *  08/10/2024 21:24:56
 * 
 */
public class Tbloanoffset {

    private TbloanoffsetId id;
    private String pnno;
    private String applno;
    private String cifno;
    private String prodcode;
    private String productgroup;
    private BigDecimal prinbal;
    private BigDecimal loanbal;
    private BigDecimal uidbal;
    private BigDecimal lpc;
    private BigDecimal othercharges;
    private BigDecimal rebate;
    private Integer acctsts;
    private BigDecimal outstandingbal;
    private String appstatus;
    private Date txdate;

    public TbloanoffsetId getId() {
        return id;
    }

    public void setId(TbloanoffsetId id) {
        this.id = id;
    }

    public String getPnno() {
        return pnno;
    }

    public void setPnno(String pnno) {
        this.pnno = pnno;
    }

    public String getApplno() {
        return applno;
    }

    public void setApplno(String applno) {
        this.applno = applno;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getProdcode() {
        return prodcode;
    }

    public void setProdcode(String prodcode) {
        this.prodcode = prodcode;
    }

    public String getProductgroup() {
        return productgroup;
    }

    public void setProductgroup(String productgroup) {
        this.productgroup = productgroup;
    }

    public BigDecimal getPrinbal() {
        return prinbal;
    }

    public void setPrinbal(BigDecimal prinbal) {
        this.prinbal = prinbal;
    }

    public BigDecimal getLoanbal() {
        return loanbal;
    }

    public void setLoanbal(BigDecimal loanbal) {
        this.loanbal = loanbal;
    }

    public BigDecimal getUidbal() {
        return uidbal;
    }

    public void setUidbal(BigDecimal uidbal) {
        this.uidbal = uidbal;
    }

    public BigDecimal getLpc() {
        return lpc;
    }

    public void setLpc(BigDecimal lpc) {
        this.lpc = lpc;
    }

    public BigDecimal getOthercharges() {
        return othercharges;
    }

    public void setOthercharges(BigDecimal othercharges) {
        this.othercharges = othercharges;
    }

    public BigDecimal getRebate() {
        return rebate;
    }

    public void setRebate(BigDecimal rebate) {
        this.rebate = rebate;
    }

    public Integer getAcctsts() {
        return acctsts;
    }

    public void setAcctsts(Integer acctsts) {
        this.acctsts = acctsts;
    }

    public BigDecimal getOutstandingbal() {
        return outstandingbal;
    }

    public void setOutstandingbal(BigDecimal outstandingbal) {
        this.outstandingbal = outstandingbal;
    }

    public String getAppstatus() {
        return appstatus;
    }

    public void setAppstatus(String appstatus) {
        this.appstatus = appstatus;
    }

    public Date getTxdate() {
        return txdate;
    }

    public void setTxdate(Date txdate) {
        this.txdate = txdate;
    }

}
