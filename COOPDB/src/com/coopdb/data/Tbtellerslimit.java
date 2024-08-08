
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbtellerslimit
 *  08/04/2024 12:54:42
 * 
 */
public class Tbtellerslimit {

    private Integer id;
    private String coopcode;
    private String branchcode;
    private String roleid;
    private BigDecimal limitamount;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date lastupdated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCoopcode() {
        return coopcode;
    }

    public void setCoopcode(String coopcode) {
        this.coopcode = coopcode;
    }

    public String getBranchcode() {
        return branchcode;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public BigDecimal getLimitamount() {
        return limitamount;
    }

    public void setLimitamount(BigDecimal limitamount) {
        this.limitamount = limitamount;
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

    public Date getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(Date lastupdated) {
        this.lastupdated = lastupdated;
    }

}
