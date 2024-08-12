
package com.coopdb.data;

import java.math.BigDecimal;


/**
 *  COOPDB.Tbappautomarketsurvey
 *  08/10/2024 21:24:57
 * 
 */
public class Tbappautomarketsurvey {

    private Integer id;
    private String appraisalreportid;
    private String appno;
    private String year;
    private String make;
    private String modelseries;
    private String modeldetails;
    private String fuel;
    private String mileage;
    private BigDecimal marketvalue;
    private String contactperson;
    private String contactno;
    private String vehicletype;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppraisalreportid() {
        return appraisalreportid;
    }

    public void setAppraisalreportid(String appraisalreportid) {
        this.appraisalreportid = appraisalreportid;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModelseries() {
        return modelseries;
    }

    public void setModelseries(String modelseries) {
        this.modelseries = modelseries;
    }

    public String getModeldetails() {
        return modeldetails;
    }

    public void setModeldetails(String modeldetails) {
        this.modeldetails = modeldetails;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public BigDecimal getMarketvalue() {
        return marketvalue;
    }

    public void setMarketvalue(BigDecimal marketvalue) {
        this.marketvalue = marketvalue;
    }

    public String getContactperson() {
        return contactperson;
    }

    public void setContactperson(String contactperson) {
        this.contactperson = contactperson;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getVehicletype() {
        return vehicletype;
    }

    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype;
    }

}
