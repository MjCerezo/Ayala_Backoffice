
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbcollateralgroupmain
 *  08/10/2024 21:24:56
 * 
 */
public class Tbcollateralgroupmain {

    private String groupid;
    private String groupname;
    private String grouptype;
    private String groupstatus;
    private Date datecreated;
    private String createdby;
    private String groupappraisalstatus;
    private Date dateoflastappraisal;
    private BigDecimal totalappraisedvalue;
    private BigDecimal totalfairmarket;
    private Date dateupdated;
    private String updatedby;

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getGrouptype() {
        return grouptype;
    }

    public void setGrouptype(String grouptype) {
        this.grouptype = grouptype;
    }

    public String getGroupstatus() {
        return groupstatus;
    }

    public void setGroupstatus(String groupstatus) {
        this.groupstatus = groupstatus;
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

    public String getGroupappraisalstatus() {
        return groupappraisalstatus;
    }

    public void setGroupappraisalstatus(String groupappraisalstatus) {
        this.groupappraisalstatus = groupappraisalstatus;
    }

    public Date getDateoflastappraisal() {
        return dateoflastappraisal;
    }

    public void setDateoflastappraisal(Date dateoflastappraisal) {
        this.dateoflastappraisal = dateoflastappraisal;
    }

    public BigDecimal getTotalappraisedvalue() {
        return totalappraisedvalue;
    }

    public void setTotalappraisedvalue(BigDecimal totalappraisedvalue) {
        this.totalappraisedvalue = totalappraisedvalue;
    }

    public BigDecimal getTotalfairmarket() {
        return totalfairmarket;
    }

    public void setTotalfairmarket(BigDecimal totalfairmarket) {
        this.totalfairmarket = totalfairmarket;
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

}
