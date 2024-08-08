
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbdefaultsignatories
 *  10/13/2020 10:21:35
 * 
 */
public class Tbdefaultsignatories {

    private Integer id;
    private String companycode;
    private String day;
    private String signatory1;
    private String signatory1position;
    private String signatory2;
    private String signatory2position;
    private Date datecreated;
    private String createdby;
    private Date datelastupdated;
    private String updatedby;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getSignatory1() {
        return signatory1;
    }

    public void setSignatory1(String signatory1) {
        this.signatory1 = signatory1;
    }

    public String getSignatory1position() {
        return signatory1position;
    }

    public void setSignatory1position(String signatory1position) {
        this.signatory1position = signatory1position;
    }

    public String getSignatory2() {
        return signatory2;
    }

    public void setSignatory2(String signatory2) {
        this.signatory2 = signatory2;
    }

    public String getSignatory2position() {
        return signatory2position;
    }

    public void setSignatory2position(String signatory2position) {
        this.signatory2position = signatory2position;
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

    public Date getDatelastupdated() {
        return datelastupdated;
    }

    public void setDatelastupdated(Date datelastupdated) {
        this.datelastupdated = datelastupdated;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

}
