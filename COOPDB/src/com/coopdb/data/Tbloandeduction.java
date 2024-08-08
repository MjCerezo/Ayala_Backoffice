
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbloandeduction
 *  08/04/2024 12:54:42
 * 
 */
public class Tbloandeduction {

    private Integer id;
    private String appno;
    private String deductiontype;
    private String credittoaccountnumber;
    private BigDecimal deductionamount;
    private String createdby;
    private Date datecreated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getDeductiontype() {
        return deductiontype;
    }

    public void setDeductiontype(String deductiontype) {
        this.deductiontype = deductiontype;
    }

    public String getCredittoaccountnumber() {
        return credittoaccountnumber;
    }

    public void setCredittoaccountnumber(String credittoaccountnumber) {
        this.credittoaccountnumber = credittoaccountnumber;
    }

    public BigDecimal getDeductionamount() {
        return deductionamount;
    }

    public void setDeductionamount(BigDecimal deductionamount) {
        this.deductionamount = deductionamount;
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
