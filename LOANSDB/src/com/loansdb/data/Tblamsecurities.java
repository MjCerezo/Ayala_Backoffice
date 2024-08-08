
package com.loansdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  LOANSDB.Tblamsecurities
 *  02/13/2019 19:49:04
 * 
 */
public class Tblamsecurities {

    private TblamsecuritiesId id;
    private String collateraltype;
    private String collateralrefno;
    private BigDecimal unitvalue;
    private BigDecimal avperunit;
    private BigDecimal totalav;
    private BigDecimal lvpercent1;
    private BigDecimal lvamt1;
    private BigDecimal lvpercent2;
    private BigDecimal lvamt2;
    private String registeredowner;
    private String securitydescription;
    private Date lastappraiseddate;
    private String lastappraisedby;

    public TblamsecuritiesId getId() {
        return id;
    }

    public void setId(TblamsecuritiesId id) {
        this.id = id;
    }

    public String getCollateraltype() {
        return collateraltype;
    }

    public void setCollateraltype(String collateraltype) {
        this.collateraltype = collateraltype;
    }

    public String getCollateralrefno() {
        return collateralrefno;
    }

    public void setCollateralrefno(String collateralrefno) {
        this.collateralrefno = collateralrefno;
    }

    public BigDecimal getUnitvalue() {
        return unitvalue;
    }

    public void setUnitvalue(BigDecimal unitvalue) {
        this.unitvalue = unitvalue;
    }

    public BigDecimal getAvperunit() {
        return avperunit;
    }

    public void setAvperunit(BigDecimal avperunit) {
        this.avperunit = avperunit;
    }

    public BigDecimal getTotalav() {
        return totalav;
    }

    public void setTotalav(BigDecimal totalav) {
        this.totalav = totalav;
    }

    public BigDecimal getLvpercent1() {
        return lvpercent1;
    }

    public void setLvpercent1(BigDecimal lvpercent1) {
        this.lvpercent1 = lvpercent1;
    }

    public BigDecimal getLvamt1() {
        return lvamt1;
    }

    public void setLvamt1(BigDecimal lvamt1) {
        this.lvamt1 = lvamt1;
    }

    public BigDecimal getLvpercent2() {
        return lvpercent2;
    }

    public void setLvpercent2(BigDecimal lvpercent2) {
        this.lvpercent2 = lvpercent2;
    }

    public BigDecimal getLvamt2() {
        return lvamt2;
    }

    public void setLvamt2(BigDecimal lvamt2) {
        this.lvamt2 = lvamt2;
    }

    public String getRegisteredowner() {
        return registeredowner;
    }

    public void setRegisteredowner(String registeredowner) {
        this.registeredowner = registeredowner;
    }

    public String getSecuritydescription() {
        return securitydescription;
    }

    public void setSecuritydescription(String securitydescription) {
        this.securitydescription = securitydescription;
    }

    public Date getLastappraiseddate() {
        return lastappraiseddate;
    }

    public void setLastappraiseddate(Date lastappraiseddate) {
        this.lastappraiseddate = lastappraiseddate;
    }

    public String getLastappraisedby() {
        return lastappraisedby;
    }

    public void setLastappraisedby(String lastappraisedby) {
        this.lastappraisedby = lastappraisedby;
    }

}
