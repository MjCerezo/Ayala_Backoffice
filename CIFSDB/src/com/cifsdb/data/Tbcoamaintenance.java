
package com.cifsdb.data;

import java.util.Date;


/**
 *  CIFSDB.Tbcoamaintenance
 *  09/26/2023 10:13:06
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
