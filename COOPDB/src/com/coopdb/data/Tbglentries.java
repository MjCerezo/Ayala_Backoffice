
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbglentries
 *  08/27/2024 14:22:56
 * 
 */
public class Tbglentries {

    private Integer id;
    private Date txdate;
    private String accountno;
    private String txcode;
    private Integer txctr;
    private String txseqno;
    private String txcurr;
    private BigDecimal txamt;
    private String glline;
    private String gllegveh;
    private String glbranch;
    private String glsl;
    private BigDecimal debit;
    private BigDecimal credit;
    private String txprod;
    private String txsubprod;
    private Date txvaldt;
    private String txlegveh;
    private String txbr;
    private String txglbr;
    private String costc;

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

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getTxcode() {
        return txcode;
    }

    public void setTxcode(String txcode) {
        this.txcode = txcode;
    }

    public Integer getTxctr() {
        return txctr;
    }

    public void setTxctr(Integer txctr) {
        this.txctr = txctr;
    }

    public String getTxseqno() {
        return txseqno;
    }

    public void setTxseqno(String txseqno) {
        this.txseqno = txseqno;
    }

    public String getTxcurr() {
        return txcurr;
    }

    public void setTxcurr(String txcurr) {
        this.txcurr = txcurr;
    }

    public BigDecimal getTxamt() {
        return txamt;
    }

    public void setTxamt(BigDecimal txamt) {
        this.txamt = txamt;
    }

    public String getGlline() {
        return glline;
    }

    public void setGlline(String glline) {
        this.glline = glline;
    }

    public String getGllegveh() {
        return gllegveh;
    }

    public void setGllegveh(String gllegveh) {
        this.gllegveh = gllegveh;
    }

    public String getGlbranch() {
        return glbranch;
    }

    public void setGlbranch(String glbranch) {
        this.glbranch = glbranch;
    }

    public String getGlsl() {
        return glsl;
    }

    public void setGlsl(String glsl) {
        this.glsl = glsl;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public String getTxprod() {
        return txprod;
    }

    public void setTxprod(String txprod) {
        this.txprod = txprod;
    }

    public String getTxsubprod() {
        return txsubprod;
    }

    public void setTxsubprod(String txsubprod) {
        this.txsubprod = txsubprod;
    }

    public Date getTxvaldt() {
        return txvaldt;
    }

    public void setTxvaldt(Date txvaldt) {
        this.txvaldt = txvaldt;
    }

    public String getTxlegveh() {
        return txlegveh;
    }

    public void setTxlegveh(String txlegveh) {
        this.txlegveh = txlegveh;
    }

    public String getTxbr() {
        return txbr;
    }

    public void setTxbr(String txbr) {
        this.txbr = txbr;
    }

    public String getTxglbr() {
        return txglbr;
    }

    public void setTxglbr(String txglbr) {
        this.txglbr = txglbr;
    }

    public String getCostc() {
        return costc;
    }

    public void setCostc(String costc) {
        this.costc = costc;
    }

}
