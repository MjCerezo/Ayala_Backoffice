
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbcollateralmachineries
 *  08/27/2024 14:22:56
 * 
 */
public class Tbcollateralmachineries {

    private Integer collateralid;
    private String referenceno;
    private String collateralstatus;
    private String appraisalstatus;
    private Date dateencoded;
    private String encodedby;
    private String machinerydetails;
    private String manufacturer;
    private Integer manufacturedyear;
    private String machinelocation;
    private String machinecondition;
    private String intendeduse;
    private BigDecimal machinevalue;
    private String remarks;
    private String serialnumber;

    public Integer getCollateralid() {
        return collateralid;
    }

    public void setCollateralid(Integer collateralid) {
        this.collateralid = collateralid;
    }

    public String getReferenceno() {
        return referenceno;
    }

    public void setReferenceno(String referenceno) {
        this.referenceno = referenceno;
    }

    public String getCollateralstatus() {
        return collateralstatus;
    }

    public void setCollateralstatus(String collateralstatus) {
        this.collateralstatus = collateralstatus;
    }

    public String getAppraisalstatus() {
        return appraisalstatus;
    }

    public void setAppraisalstatus(String appraisalstatus) {
        this.appraisalstatus = appraisalstatus;
    }

    public Date getDateencoded() {
        return dateencoded;
    }

    public void setDateencoded(Date dateencoded) {
        this.dateencoded = dateencoded;
    }

    public String getEncodedby() {
        return encodedby;
    }

    public void setEncodedby(String encodedby) {
        this.encodedby = encodedby;
    }

    public String getMachinerydetails() {
        return machinerydetails;
    }

    public void setMachinerydetails(String machinerydetails) {
        this.machinerydetails = machinerydetails;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getManufacturedyear() {
        return manufacturedyear;
    }

    public void setManufacturedyear(Integer manufacturedyear) {
        this.manufacturedyear = manufacturedyear;
    }

    public String getMachinelocation() {
        return machinelocation;
    }

    public void setMachinelocation(String machinelocation) {
        this.machinelocation = machinelocation;
    }

    public String getMachinecondition() {
        return machinecondition;
    }

    public void setMachinecondition(String machinecondition) {
        this.machinecondition = machinecondition;
    }

    public String getIntendeduse() {
        return intendeduse;
    }

    public void setIntendeduse(String intendeduse) {
        this.intendeduse = intendeduse;
    }

    public BigDecimal getMachinevalue() {
        return machinevalue;
    }

    public void setMachinevalue(BigDecimal machinevalue) {
        this.machinevalue = machinevalue;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

}
