
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbglentriesperproduct
 *  10/13/2020 10:21:35
 * 
 */
public class Tbglentriesperproduct {

    private TbglentriesperproductId id;
    private String acctdesc;
    private String txmode;
    private String txoper;
    private Date datecreated;
    private String createdby;
    private String glline1;
    private String glline2;

    public TbglentriesperproductId getId() {
        return id;
    }

    public void setId(TbglentriesperproductId id) {
        this.id = id;
    }

    public String getAcctdesc() {
        return acctdesc;
    }

    public void setAcctdesc(String acctdesc) {
        this.acctdesc = acctdesc;
    }

    public String getTxmode() {
        return txmode;
    }

    public void setTxmode(String txmode) {
        this.txmode = txmode;
    }

    public String getTxoper() {
        return txoper;
    }

    public void setTxoper(String txoper) {
        this.txoper = txoper;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getGlline1() {
        return glline1;
    }

    public void setGlline1(String glline1) {
        this.glline1 = glline1;
    }

    public String getGlline2() {
        return glline2;
    }

    public void setGlline2(String glline2) {
        this.glline2 = glline2;
    }

}
