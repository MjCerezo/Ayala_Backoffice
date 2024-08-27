
package com.cifsdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  CIFSDB.Tbotheraccounts
 *  08/27/2024 14:22:04
 * 
 */
public class Tbotheraccounts {

    private Integer id;
    private String cifno;
    private String bankaccttype;
    private String bank;
    private String branch;
    private String accountname;
    private String accountnumber;
    private Date dateopened;
    private BigDecimal adb;
    private BigDecimal outstandingbal;
    private String accounttype;
    private String loantype;
    private String pncnno;
    private Date valuedate;
    private Date maturitydate;
    private String investmenttype;
    private String investmentrefno;
    private Date dateupdated;
    private String updatedby;
    private String currency;
    private String atmcardno;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
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

    public BigDecimal getOutstandingbal() {
        return outstandingbal;
    }

    public void setOutstandingbal(BigDecimal outstandingbal) {
        this.outstandingbal = outstandingbal;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public String getLoantype() {
        return loantype;
    }

    public void setLoantype(String loantype) {
        this.loantype = loantype;
    }

    public String getPncnno() {
        return pncnno;
    }

    public void setPncnno(String pncnno) {
        this.pncnno = pncnno;
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

    public String getInvestmentrefno() {
        return investmentrefno;
    }

    public void setInvestmentrefno(String investmentrefno) {
        this.investmentrefno = investmentrefno;
    }

    public Date getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAtmcardno() {
        return atmcardno;
    }

    public void setAtmcardno(String atmcardno) {
        this.atmcardno = atmcardno;
    }

}
