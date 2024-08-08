
package com.loansdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  LOANSDB.Tblambalancesheet
 *  08/08/2018 15:53:06
 * 
 */
public class Tblambalancesheet {

    private Integer id;
    private String cfappno;
    private String cfrefno;
    private Date year;
    private BigDecimal cashequivalents;
    private BigDecimal tradereceivable;
    private BigDecimal inventories;
    private BigDecimal currentassets;
    private BigDecimal totalassets;
    private BigDecimal tradepayable;
    private BigDecimal currentportiondebt;
    private BigDecimal currentliabilities;
    private BigDecimal payablerelatedparty;
    private BigDecimal totalliabilities;
    private BigDecimal networth;
    private BigDecimal networkingcapital;
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

    public BigDecimal getCashequivalents() {
        return cashequivalents;
    }

    public void setCashequivalents(BigDecimal cashequivalents) {
        this.cashequivalents = cashequivalents;
    }

    public BigDecimal getTradereceivable() {
        return tradereceivable;
    }

    public void setTradereceivable(BigDecimal tradereceivable) {
        this.tradereceivable = tradereceivable;
    }

    public BigDecimal getInventories() {
        return inventories;
    }

    public void setInventories(BigDecimal inventories) {
        this.inventories = inventories;
    }

    public BigDecimal getCurrentassets() {
        return currentassets;
    }

    public void setCurrentassets(BigDecimal currentassets) {
        this.currentassets = currentassets;
    }

    public BigDecimal getTotalassets() {
        return totalassets;
    }

    public void setTotalassets(BigDecimal totalassets) {
        this.totalassets = totalassets;
    }

    public BigDecimal getTradepayable() {
        return tradepayable;
    }

    public void setTradepayable(BigDecimal tradepayable) {
        this.tradepayable = tradepayable;
    }

    public BigDecimal getCurrentportiondebt() {
        return currentportiondebt;
    }

    public void setCurrentportiondebt(BigDecimal currentportiondebt) {
        this.currentportiondebt = currentportiondebt;
    }

    public BigDecimal getCurrentliabilities() {
        return currentliabilities;
    }

    public void setCurrentliabilities(BigDecimal currentliabilities) {
        this.currentliabilities = currentliabilities;
    }

    public BigDecimal getPayablerelatedparty() {
        return payablerelatedparty;
    }

    public void setPayablerelatedparty(BigDecimal payablerelatedparty) {
        this.payablerelatedparty = payablerelatedparty;
    }

    public BigDecimal getTotalliabilities() {
        return totalliabilities;
    }

    public void setTotalliabilities(BigDecimal totalliabilities) {
        this.totalliabilities = totalliabilities;
    }

    public BigDecimal getNetworth() {
        return networth;
    }

    public void setNetworth(BigDecimal networth) {
        this.networth = networth;
    }

    public BigDecimal getNetworkingcapital() {
        return networkingcapital;
    }

    public void setNetworkingcapital(BigDecimal networkingcapital) {
        this.networkingcapital = networkingcapital;
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
