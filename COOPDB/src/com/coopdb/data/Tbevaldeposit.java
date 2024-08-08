
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbevaldeposit
 *  08/04/2024 12:54:41
 * 
 */
public class Tbevaldeposit {

    private TbevaldepositId id;
    private String cireportid;
    private String cifno;
    private String appno;
    private String bankaccttype;
    private String bank;
    private String branch;
    private String accountname;
    private String accountnumber;
    private Date dateopened;
    private BigDecimal adb;
    private BigDecimal amc;
    private BigDecimal outstandingbal;
    private String accountstatus;

    public TbevaldepositId getId() {
        return id;
    }

    public void setId(TbevaldepositId id) {
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

    public String getBankaccttype() {
        return bankaccttype;
    }

    public void setBankaccttype(String bankaccttype) {
        this.bankaccttype = bankaccttype;
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

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }

    public Date getDateopened() {
        return dateopened;
    }

    public void setDateopened(Date dateopened) {
        this.dateopened = dateopened;
    }

    public BigDecimal getAdb() {
        return adb;
    }

    public void setAdb(BigDecimal adb) {
        this.adb = adb;
    }

    public BigDecimal getAmc() {
        return amc;
    }

    public void setAmc(BigDecimal amc) {
        this.amc = amc;
    }

    public BigDecimal getOutstandingbal() {
        return outstandingbal;
    }

    public void setOutstandingbal(BigDecimal outstandingbal) {
        this.outstandingbal = outstandingbal;
    }

    public String getAccountstatus() {
        return accountstatus;
    }

    public void setAccountstatus(String accountstatus) {
        this.accountstatus = accountstatus;
    }

}
