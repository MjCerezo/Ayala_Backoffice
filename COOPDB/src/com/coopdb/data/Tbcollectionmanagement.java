
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbcollectionmanagement
 *  08/27/2024 14:22:57
 * 
 */
public class Tbcollectionmanagement {

    private String accountno;
    private String autodebitaccountno;
    private String accountname;
    private String status;
    private String loanpaymode;
    private String collectionnoti;
    private String email1;
    private String email2;
    private String address1;
    private String address2;
    private String createdby;
    private Date datecreated;

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getAutodebitaccountno() {
        return autodebitaccountno;
    }

    public void setAutodebitaccountno(String autodebitaccountno) {
        this.autodebitaccountno = autodebitaccountno;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoanpaymode() {
        return loanpaymode;
    }

    public void setLoanpaymode(String loanpaymode) {
        this.loanpaymode = loanpaymode;
    }

    public String getCollectionnoti() {
        return collectionnoti;
    }

    public void setCollectionnoti(String collectionnoti) {
        this.collectionnoti = collectionnoti;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
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

}
