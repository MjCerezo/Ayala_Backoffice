
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbcoamaintenance
 *  08/10/2024 21:24:57
 * 
 */
public class Tbcoamaintenance {

    private String accountno;
    private String acctdesc;
    private String createdby;
    private Date datecreated;

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getAcctdesc() {
        return acctdesc;
    }

    public void setAcctdesc(String acctdesc) {
        this.acctdesc = acctdesc;
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
