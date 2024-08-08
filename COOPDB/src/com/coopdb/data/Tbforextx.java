
package com.coopdb.data;

import java.math.BigDecimal;


/**
 *  COOPDB.Tbforextx
 *  08/04/2024 12:54:43
 * 
 */
public class Tbforextx {

    private Integer id;
    private String currency;
    private Integer txtype;
    private String rate;
    private BigDecimal amount;
    private BigDecimal rateamount;
    private BigDecimal amounttopay;
    private Integer custtype;
    private String accountno;
    private String custname;
    private String custaddress;
    private String custcontact;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getTxtype() {
        return txtype;
    }

    public void setTxtype(Integer txtype) {
        this.txtype = txtype;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getRateamount() {
        return rateamount;
    }

    public void setRateamount(BigDecimal rateamount) {
        this.rateamount = rateamount;
    }

    public BigDecimal getAmounttopay() {
        return amounttopay;
    }

    public void setAmounttopay(BigDecimal amounttopay) {
        this.amounttopay = amounttopay;
    }

    public Integer getCusttype() {
        return custtype;
    }

    public void setCusttype(Integer custtype) {
        this.custtype = custtype;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getCustaddress() {
        return custaddress;
    }

    public void setCustaddress(String custaddress) {
        this.custaddress = custaddress;
    }

    public String getCustcontact() {
        return custcontact;
    }

    public void setCustcontact(String custcontact) {
        this.custcontact = custcontact;
    }

}
