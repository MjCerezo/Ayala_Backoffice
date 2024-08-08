
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbcoa
 *  10/13/2020 10:21:35
 * 
 */
public class Tbcoa {

    private String accountno;
    private String acctdesc;
    private Boolean active;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date dateupdated;

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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    public Date getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }

}
