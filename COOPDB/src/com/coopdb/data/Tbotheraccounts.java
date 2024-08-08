
package com.coopdb.data;

import java.math.BigDecimal;


/**
 *  COOPDB.Tbotheraccounts
 *  08/04/2024 12:54:42
 * 
 */
public class Tbotheraccounts {

    private Integer id;
    private String cifno;
    private String recordtype;
    private String creditor;
    private String loantype;
    private BigDecimal creditbalance;
    private BigDecimal monthamort;
    private String creditcardcompany;
    private String cardno;
    private BigDecimal creditlimit;
    private String bankname;
    private String accounttype;
    private String accountno;
    private BigDecimal currentbalance;

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

    public String getRecordtype() {
        return recordtype;
    }

    public void setRecordtype(String recordtype) {
        this.recordtype = recordtype;
    }

    public String getCreditor() {
        return creditor;
    }

    public void setCreditor(String creditor) {
        this.creditor = creditor;
    }

    public String getLoantype() {
        return loantype;
    }

    public void setLoantype(String loantype) {
        this.loantype = loantype;
    }

    public BigDecimal getCreditbalance() {
        return creditbalance;
    }

    public void setCreditbalance(BigDecimal creditbalance) {
        this.creditbalance = creditbalance;
    }

    public BigDecimal getMonthamort() {
        return monthamort;
    }

    public void setMonthamort(BigDecimal monthamort) {
        this.monthamort = monthamort;
    }

    public String getCreditcardcompany() {
        return creditcardcompany;
    }

    public void setCreditcardcompany(String creditcardcompany) {
        this.creditcardcompany = creditcardcompany;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public BigDecimal getCreditlimit() {
        return creditlimit;
    }

    public void setCreditlimit(BigDecimal creditlimit) {
        this.creditlimit = creditlimit;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public BigDecimal getCurrentbalance() {
        return currentbalance;
    }

    public void setCurrentbalance(BigDecimal currentbalance) {
        this.currentbalance = currentbalance;
    }

}
