
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbcicreditcheck
 *  08/04/2024 12:54:43
 * 
 */
public class Tbcicreditcheck {

    private Integer id;
    private String cireportid;
    private String cifno;
    private String appno;
    private String loantype;
    private String bank;
    private String branch;
    private String pncnno;
    private String accountname;
    private Date valuedate;
    private Date maturitydate;
    private BigDecimal outstandingbal;
    private String currency;
    private BigDecimal pnloanamount;
    private String experiencehandling;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCireportid() {
        return cireportid;
    }

    public void setCireportid(String cireportid) {
        this.cireportid = cireportid;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getLoantype() {
        return loantype;
    }

    public void setLoantype(String loantype) {
        this.loantype = loantype;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getPncnno() {
        return pncnno;
    }

    public void setPncnno(String pncnno) {
        this.pncnno = pncnno;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public Date getValuedate() {
        return valuedate;
    }

    public void setValuedate(Date valuedate) {
        this.valuedate = valuedate;
    }

    public Date getMaturitydate() {
        return maturitydate;
    }

    public void setMaturitydate(Date maturitydate) {
        this.maturitydate = maturitydate;
    }

    public BigDecimal getOutstandingbal() {
        return outstandingbal;
    }

    public void setOutstandingbal(BigDecimal outstandingbal) {
        this.outstandingbal = outstandingbal;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getPnloanamount() {
        return pnloanamount;
    }

    public void setPnloanamount(BigDecimal pnloanamount) {
        this.pnloanamount = pnloanamount;
    }

    public String getExperiencehandling() {
        return experiencehandling;
    }

    public void setExperiencehandling(String experiencehandling) {
        this.experiencehandling = experiencehandling;
    }

}
