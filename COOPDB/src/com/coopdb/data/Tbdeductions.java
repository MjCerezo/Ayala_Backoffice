
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbdeductions
 *  08/10/2024 21:24:57
 * 
 */
public class Tbdeductions {

    private Integer id;
    private String boscode;
    private String servicestatus;
    private String billmode;
    private String productcode;
    private String deductionmode;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date dateupdated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBoscode() {
        return boscode;
    }

    public void setBoscode(String boscode) {
        this.boscode = boscode;
    }

    public String getServicestatus() {
        return servicestatus;
    }

    public void setServicestatus(String servicestatus) {
        this.servicestatus = servicestatus;
    }

    public String getBillmode() {
        return billmode;
    }

    public void setBillmode(String billmode) {
        this.billmode = billmode;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public String getDeductionmode() {
        return deductionmode;
    }

    public void setDeductionmode(String deductionmode) {
        this.deductionmode = deductionmode;
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
