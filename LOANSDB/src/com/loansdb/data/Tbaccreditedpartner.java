
package com.loansdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  LOANSDB.Tbaccreditedpartner
 *  10/13/2020 10:21:35
 * 
 */
public class Tbaccreditedpartner {

    private String cifno;
    private String customertradename;
    private BigDecimal exposurelimit;
    private BigDecimal currentexposure;
    private BigDecimal remainingunused;
    private BigDecimal overthelimit;
    private String accreditationstatus;
    private Date dateofaccreditation;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date dateupdated;

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getCustomertradename() {
        return customertradename;
    }

    public void setCustomertradename(String customertradename) {
        this.customertradename = customertradename;
    }

    public BigDecimal getExposurelimit() {
        return exposurelimit;
    }

    public void setExposurelimit(BigDecimal exposurelimit) {
        this.exposurelimit = exposurelimit;
    }

    public BigDecimal getCurrentexposure() {
        return currentexposure;
    }

    public void setCurrentexposure(BigDecimal currentexposure) {
        this.currentexposure = currentexposure;
    }

    public BigDecimal getRemainingunused() {
        return remainingunused;
    }

    public void setRemainingunused(BigDecimal remainingunused) {
        this.remainingunused = remainingunused;
    }

    public BigDecimal getOverthelimit() {
        return overthelimit;
    }

    public void setOverthelimit(BigDecimal overthelimit) {
        this.overthelimit = overthelimit;
    }

    public String getAccreditationstatus() {
        return accreditationstatus;
    }

    public void setAccreditationstatus(String accreditationstatus) {
        this.accreditationstatus = accreditationstatus;
    }

    public Date getDateofaccreditation() {
        return dateofaccreditation;
    }

    public void setDateofaccreditation(Date dateofaccreditation) {
        this.dateofaccreditation = dateofaccreditation;
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
