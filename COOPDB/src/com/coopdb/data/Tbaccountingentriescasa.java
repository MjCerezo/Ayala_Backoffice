
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbaccountingentriescasa
 *  08/27/2024 14:22:56
 * 
 */
public class Tbaccountingentriescasa {

    private Integer id;
    private String txcode;
    private String gllineno;
    private String txtype;
    private String gllinedesc;
    private String txoper;
    private String remarks;
    private Date dateupdated;
    private String updatedby;
    private Date datecreated;
    private String createdby;
    private String glcode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTxcode() {
        return txcode;
    }

    public void setTxcode(String txcode) {
        this.txcode = txcode;
    }

    public String getGllineno() {
        return gllineno;
    }

    public void setGllineno(String gllineno) {
        this.gllineno = gllineno;
    }

    public String getTxtype() {
        return txtype;
    }

    public void setTxtype(String txtype) {
        this.txtype = txtype;
    }

    public String getGllinedesc() {
        return gllinedesc;
    }

    public void setGllinedesc(String gllinedesc) {
        this.gllinedesc = gllinedesc;
    }

    public String getTxoper() {
        return txoper;
    }

    public void setTxoper(String txoper) {
        this.txoper = txoper;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getGlcode() {
        return glcode;
    }

    public void setGlcode(String glcode) {
        this.glcode = glcode;
    }

}
