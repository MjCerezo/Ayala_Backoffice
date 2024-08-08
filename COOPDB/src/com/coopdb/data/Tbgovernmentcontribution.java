
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbgovernmentcontribution
 *  08/04/2024 12:54:43
 * 
 */
public class Tbgovernmentcontribution {

    private Integer id;
    private String contributiontype;
    private BigDecimal salaryrangefrom;
    private BigDecimal salaryrangeto;
    private String sharetype;
    private BigDecimal sharepercentage;
    private BigDecimal amountcontribution;
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

    public String getContributiontype() {
        return contributiontype;
    }

    public void setContributiontype(String contributiontype) {
        this.contributiontype = contributiontype;
    }

    public BigDecimal getSalaryrangefrom() {
        return salaryrangefrom;
    }

    public void setSalaryrangefrom(BigDecimal salaryrangefrom) {
        this.salaryrangefrom = salaryrangefrom;
    }

    public BigDecimal getSalaryrangeto() {
        return salaryrangeto;
    }

    public void setSalaryrangeto(BigDecimal salaryrangeto) {
        this.salaryrangeto = salaryrangeto;
    }

    public String getSharetype() {
        return sharetype;
    }

    public void setSharetype(String sharetype) {
        this.sharetype = sharetype;
    }

    public BigDecimal getSharepercentage() {
        return sharepercentage;
    }

    public void setSharepercentage(BigDecimal sharepercentage) {
        this.sharepercentage = sharepercentage;
    }

    public BigDecimal getAmountcontribution() {
        return amountcontribution;
    }

    public void setAmountcontribution(BigDecimal amountcontribution) {
        this.amountcontribution = amountcontribution;
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
