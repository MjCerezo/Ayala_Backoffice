
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbpaymentsched
 *  08/27/2024 14:22:57
 * 
 */
public class Tbpaymentsched {

    private TbpaymentschedId id;
    private String applno;
    private BigDecimal ilamt;
    private Date ilduedt;
    private BigDecimal ilint;
    private BigDecimal ilintrate;
    private Integer ilno;
    private BigDecimal ilprin;
    private BigDecimal ilrppd;
    private BigDecimal iltax;
    private BigDecimal iltaxrate;
    private Boolean isPaid;
    private BigDecimal loanbal;
    private String loanno;
    private BigDecimal others;
    private BigDecimal prinbal;
    private String txmkr;
    private String txoff;
    private BigDecimal uidbal;
    private Integer daysdiff;
    private String intperiod;
    private String intperioddesc;

    public TbpaymentschedId getId() {
        return id;
    }

    public void setId(TbpaymentschedId id) {
        this.id = id;
    }

    public String getApplno() {
        return applno;
    }

    public void setApplno(String applno) {
        this.applno = applno;
    }

    public BigDecimal getIlamt() {
        return ilamt;
    }

    public void setIlamt(BigDecimal ilamt) {
        this.ilamt = ilamt;
    }

    public Date getIlduedt() {
        return ilduedt;
    }

    public void setIlduedt(Date ilduedt) {
        this.ilduedt = ilduedt;
    }

    public BigDecimal getIlint() {
        return ilint;
    }

    public void setIlint(BigDecimal ilint) {
        this.ilint = ilint;
    }

    public BigDecimal getIlintrate() {
        return ilintrate;
    }

    public void setIlintrate(BigDecimal ilintrate) {
        this.ilintrate = ilintrate;
    }

    public Integer getIlno() {
        return ilno;
    }

    public void setIlno(Integer ilno) {
        this.ilno = ilno;
    }

    public BigDecimal getIlprin() {
        return ilprin;
    }

    public void setIlprin(BigDecimal ilprin) {
        this.ilprin = ilprin;
    }

    public BigDecimal getIlrppd() {
        return ilrppd;
    }

    public void setIlrppd(BigDecimal ilrppd) {
        this.ilrppd = ilrppd;
    }

    public BigDecimal getIltax() {
        return iltax;
    }

    public void setIltax(BigDecimal iltax) {
        this.iltax = iltax;
    }

    public BigDecimal getIltaxrate() {
        return iltaxrate;
    }

    public void setIltaxrate(BigDecimal iltaxrate) {
        this.iltaxrate = iltaxrate;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public BigDecimal getLoanbal() {
        return loanbal;
    }

    public void setLoanbal(BigDecimal loanbal) {
        this.loanbal = loanbal;
    }

    public String getLoanno() {
        return loanno;
    }

    public void setLoanno(String loanno) {
        this.loanno = loanno;
    }

    public BigDecimal getOthers() {
        return others;
    }

    public void setOthers(BigDecimal others) {
        this.others = others;
    }

    public BigDecimal getPrinbal() {
        return prinbal;
    }

    public void setPrinbal(BigDecimal prinbal) {
        this.prinbal = prinbal;
    }

    public String getTxmkr() {
        return txmkr;
    }

    public void setTxmkr(String txmkr) {
        this.txmkr = txmkr;
    }

    public String getTxoff() {
        return txoff;
    }

    public void setTxoff(String txoff) {
        this.txoff = txoff;
    }

    public BigDecimal getUidbal() {
        return uidbal;
    }

    public void setUidbal(BigDecimal uidbal) {
        this.uidbal = uidbal;
    }

    public Integer getDaysdiff() {
        return daysdiff;
    }

    public void setDaysdiff(Integer daysdiff) {
        this.daysdiff = daysdiff;
    }

    public String getIntperiod() {
        return intperiod;
    }

    public void setIntperiod(String intperiod) {
        this.intperiod = intperiod;
    }

    public String getIntperioddesc() {
        return intperioddesc;
    }

    public void setIntperioddesc(String intperioddesc) {
        this.intperioddesc = intperioddesc;
    }

}
