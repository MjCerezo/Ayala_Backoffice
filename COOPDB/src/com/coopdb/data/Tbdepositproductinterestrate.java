
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbdepositproductinterestrate
 *  08/04/2024 12:54:40
 * 
 */
public class Tbdepositproductinterestrate {

    private Integer id;
    private String productcode;
    private String subprodcode;
    private BigDecimal interest;
    private BigDecimal minamount;
    private BigDecimal maxamount;
    private Integer mintermindays;
    private Integer maxtermindays;
    private String role;
    private String status;
    private String username;
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

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public String getSubprodcode() {
        return subprodcode;
    }

    public void setSubprodcode(String subprodcode) {
        this.subprodcode = subprodcode;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getMinamount() {
        return minamount;
    }

    public void setMinamount(BigDecimal minamount) {
        this.minamount = minamount;
    }

    public BigDecimal getMaxamount() {
        return maxamount;
    }

    public void setMaxamount(BigDecimal maxamount) {
        this.maxamount = maxamount;
    }

    public Integer getMintermindays() {
        return mintermindays;
    }

    public void setMintermindays(Integer mintermindays) {
        this.mintermindays = mintermindays;
    }

    public Integer getMaxtermindays() {
        return maxtermindays;
    }

    public void setMaxtermindays(Integer maxtermindays) {
        this.maxtermindays = maxtermindays;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
