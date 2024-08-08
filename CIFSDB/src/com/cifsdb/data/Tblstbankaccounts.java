
package com.cifsdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  CIFSDB.Tblstbankaccounts
 *  09/26/2023 10:13:06
 * 
 */
public class Tblstbankaccounts {

    private Integer id;
    private String appno;
    private String membershipid;
    private String financialtype;
    private String bankaccttype;
    private String bank;
    private String accountstatus;
    private BigDecimal outstandingbal;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date lastupdated;
    private String accountrefno;

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

    public String getMembershipid() {
        return membershipid;
    }

    public void setMembershipid(String membershipid) {
        this.membershipid = membershipid;
    }

    public String getFinancialtype() {
        return financialtype;
    }

    public void setFinancialtype(String financialtype) {
        this.financialtype = financialtype;
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

    public String getAccountstatus() {
        return accountstatus;
    }

    public void setAccountstatus(String accountstatus) {
        this.accountstatus = accountstatus;
    }

    public BigDecimal getOutstandingbal() {
        return outstandingbal;
    }

    public void setOutstandingbal(BigDecimal outstandingbal) {
        this.outstandingbal = outstandingbal;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public Date getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(Date lastupdated) {
        this.lastupdated = lastupdated;
    }

    public String getAccountrefno() {
        return accountrefno;
    }

    public void setAccountrefno(String accountrefno) {
        this.accountrefno = accountrefno;
    }

}
