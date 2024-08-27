
package com.cifsdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  CIFSDB.Tbbankaccounts
 *  08/27/2024 14:22:04
 * 
 */
public class Tbbankaccounts {

    private TbbankaccountsId id;
    private String transtype;
    private String accounttype;
    private String bankname;
    private String branch;
    private String accountname;
    private Date dateopened;
    private BigDecimal adb;
    private BigDecimal outstandingbal;
    private String loantype;
    private Date valuedate;
    private Date maturitydate;
    private String investmenttype;
    private String createdby;
    private Date createddate;
    private String updatedby;
    private Date dateupdated;

    public TbbankaccountsId getId() {
        return id;
    }

    public void setId(TbbankaccountsId id) {
        this.id = id;
    }

    public String getTranstype() {
        return transtype;
    }

    public void setTranstype(String transtype) {
        this.transtype = transtype;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
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

    public BigDecimal getOutstandingbal() {
        return outstandingbal;
    }

    public void setOutstandingbal(BigDecimal outstandingbal) {
        this.outstandingbal = outstandingbal;
    }

    public String getLoantype() {
        return loantype;
    }

    public void setLoantype(String loantype) {
        this.loantype = loantype;
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

    public String getInvestmenttype() {
        return investmenttype;
    }

    public void setInvestmenttype(String investmenttype) {
        this.investmenttype = investmenttype;
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

    public Date getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }

}
