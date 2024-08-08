
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbgrouppayment
 *  08/04/2024 12:54:44
 * 
 */
public class Tbgrouppayment {

    private String txrefno;
    private Date txdate;
    private Date txvaldt;
    private String cifno;
    private String cifname;
    private BigDecimal txamt;
    private String txchkno;
    private Date txchkdt;
    private String txbrstn;
    private String txchkbankname;
    private String txchkbankbr;
    private String encodedby;
    private String updatedby;
    private Date dateupdated;
    private String approvedby;
    private Date approveddate;
    private Integer noofacctapplied;
    private String txstat;
    private String txor;
    private String legveh;
    private String prodcode;
    private String txmode;
    private BigDecimal commrate;
    private BigDecimal commamt;
    private BigDecimal wtaxrate;
    private BigDecimal wtaxamt;
    private BigDecimal addtnlint;
    private String txdepositorybank;

    public String getTxrefno() {
        return txrefno;
    }

    public void setTxrefno(String txrefno) {
        this.txrefno = txrefno;
    }

    public Date getTxdate() {
        return txdate;
    }

    public void setTxdate(Date txdate) {
        this.txdate = txdate;
    }

    public Date getTxvaldt() {
        return txvaldt;
    }

    public void setTxvaldt(Date txvaldt) {
        this.txvaldt = txvaldt;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getCifname() {
        return cifname;
    }

    public void setCifname(String cifname) {
        this.cifname = cifname;
    }

    public BigDecimal getTxamt() {
        return txamt;
    }

    public void setTxamt(BigDecimal txamt) {
        this.txamt = txamt;
    }

    public String getTxchkno() {
        return txchkno;
    }

    public void setTxchkno(String txchkno) {
        this.txchkno = txchkno;
    }

    public Date getTxchkdt() {
        return txchkdt;
    }

    public void setTxchkdt(Date txchkdt) {
        this.txchkdt = txchkdt;
    }

    public String getTxbrstn() {
        return txbrstn;
    }

    public void setTxbrstn(String txbrstn) {
        this.txbrstn = txbrstn;
    }

    public String getTxchkbankname() {
        return txchkbankname;
    }

    public void setTxchkbankname(String txchkbankname) {
        this.txchkbankname = txchkbankname;
    }

    public String getTxchkbankbr() {
        return txchkbankbr;
    }

    public void setTxchkbankbr(String txchkbankbr) {
        this.txchkbankbr = txchkbankbr;
    }

    public String getEncodedby() {
        return encodedby;
    }

    public void setEncodedby(String encodedby) {
        this.encodedby = encodedby;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public Date getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }

    public String getApprovedby() {
        return approvedby;
    }

    public void setApprovedby(String approvedby) {
        this.approvedby = approvedby;
    }

    public Date getApproveddate() {
        return approveddate;
    }

    public void setApproveddate(Date approveddate) {
        this.approveddate = approveddate;
    }

    public Integer getNoofacctapplied() {
        return noofacctapplied;
    }

    public void setNoofacctapplied(Integer noofacctapplied) {
        this.noofacctapplied = noofacctapplied;
    }

    public String getTxstat() {
        return txstat;
    }

    public void setTxstat(String txstat) {
        this.txstat = txstat;
    }

    public String getTxor() {
        return txor;
    }

    public void setTxor(String txor) {
        this.txor = txor;
    }

    public String getLegveh() {
        return legveh;
    }

    public void setLegveh(String legveh) {
        this.legveh = legveh;
    }

    public String getProdcode() {
        return prodcode;
    }

    public void setProdcode(String prodcode) {
        this.prodcode = prodcode;
    }

    public String getTxmode() {
        return txmode;
    }

    public void setTxmode(String txmode) {
        this.txmode = txmode;
    }

    public BigDecimal getCommrate() {
        return commrate;
    }

    public void setCommrate(BigDecimal commrate) {
        this.commrate = commrate;
    }

    public BigDecimal getCommamt() {
        return commamt;
    }

    public void setCommamt(BigDecimal commamt) {
        this.commamt = commamt;
    }

    public BigDecimal getWtaxrate() {
        return wtaxrate;
    }

    public void setWtaxrate(BigDecimal wtaxrate) {
        this.wtaxrate = wtaxrate;
    }

    public BigDecimal getWtaxamt() {
        return wtaxamt;
    }

    public void setWtaxamt(BigDecimal wtaxamt) {
        this.wtaxamt = wtaxamt;
    }

    public BigDecimal getAddtnlint() {
        return addtnlint;
    }

    public void setAddtnlint(BigDecimal addtnlint) {
        this.addtnlint = addtnlint;
    }

    public String getTxdepositorybank() {
        return txdepositorybank;
    }

    public void setTxdepositorybank(String txdepositorybank) {
        this.txdepositorybank = txdepositorybank;
    }

}
