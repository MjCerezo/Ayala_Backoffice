
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tblstsourceincome
 *  08/04/2024 12:54:41
 * 
 */
public class Tblstsourceincome {

    private Integer id;
    private String appno;
    private String participationcode;
    private BigDecimal monthlyincome;
    private BigDecimal otherincome;
    private BigDecimal totalincome;
    private BigDecimal lessrental;
    private BigDecimal lessutilities;
    private BigDecimal lessfood;
    private BigDecimal lessmedical;
    private BigDecimal lessotherexpense;
    private BigDecimal netincome;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date lastupdated;
    private BigDecimal businessincome;

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

    public String getParticipationcode() {
        return participationcode;
    }

    public void setParticipationcode(String participationcode) {
        this.participationcode = participationcode;
    }

    public BigDecimal getMonthlyincome() {
        return monthlyincome;
    }

    public void setMonthlyincome(BigDecimal monthlyincome) {
        this.monthlyincome = monthlyincome;
    }

    public BigDecimal getOtherincome() {
        return otherincome;
    }

    public void setOtherincome(BigDecimal otherincome) {
        this.otherincome = otherincome;
    }

    public BigDecimal getTotalincome() {
        return totalincome;
    }

    public void setTotalincome(BigDecimal totalincome) {
        this.totalincome = totalincome;
    }

    public BigDecimal getLessrental() {
        return lessrental;
    }

    public void setLessrental(BigDecimal lessrental) {
        this.lessrental = lessrental;
    }

    public BigDecimal getLessutilities() {
        return lessutilities;
    }

    public void setLessutilities(BigDecimal lessutilities) {
        this.lessutilities = lessutilities;
    }

    public BigDecimal getLessfood() {
        return lessfood;
    }

    public void setLessfood(BigDecimal lessfood) {
        this.lessfood = lessfood;
    }

    public BigDecimal getLessmedical() {
        return lessmedical;
    }

    public void setLessmedical(BigDecimal lessmedical) {
        this.lessmedical = lessmedical;
    }

    public BigDecimal getLessotherexpense() {
        return lessotherexpense;
    }

    public void setLessotherexpense(BigDecimal lessotherexpense) {
        this.lessotherexpense = lessotherexpense;
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

    public BigDecimal getBusinessincome() {
        return businessincome;
    }

    public void setBusinessincome(BigDecimal businessincome) {
        this.businessincome = businessincome;
    }

}
