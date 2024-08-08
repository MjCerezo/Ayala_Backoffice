
package com.loansdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  LOANSDB.Tblamincomestatement
 *  08/08/2018 15:53:06
 * 
 */
public class Tblamincomestatement {

    private Integer id;
    private String cfappno;
    private String cfrefno;
    private Date year;
    private BigDecimal costgoodssold;
    private BigDecimal operatingexpenses;
    private BigDecimal earningsbefore;
    private BigDecimal financecost;
    private BigDecimal netincome;
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

    public String getCfappno() {
        return cfappno;
    }

    public void setCfappno(String cfappno) {
        this.cfappno = cfappno;
    }

    public String getCfrefno() {
        return cfrefno;
    }

    public void setCfrefno(String cfrefno) {
        this.cfrefno = cfrefno;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public BigDecimal getCostgoodssold() {
        return costgoodssold;
    }

    public void setCostgoodssold(BigDecimal costgoodssold) {
        this.costgoodssold = costgoodssold;
    }

    public BigDecimal getOperatingexpenses() {
        return operatingexpenses;
    }

    public void setOperatingexpenses(BigDecimal operatingexpenses) {
        this.operatingexpenses = operatingexpenses;
    }

    public BigDecimal getEarningsbefore() {
        return earningsbefore;
    }

    public void setEarningsbefore(BigDecimal earningsbefore) {
        this.earningsbefore = earningsbefore;
    }

    public BigDecimal getFinancecost() {
        return financecost;
    }

    public void setFinancecost(BigDecimal financecost) {
        this.financecost = financecost;
    }

    public BigDecimal getNetincome() {
        return netincome;
    }

    public void setNetincome(BigDecimal netincome) {
        this.netincome = netincome;
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
