
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbapprellienencumbrance
 *  08/27/2024 14:22:57
 * 
 */
public class Tbapprellienencumbrance {

    private Integer id;
    private String appraisalreportid;
    private String appno;
    private Integer collateralid;
    private String entryno;
    private String particulars;
    private Date dateinscribed;
    private String tracebacktitleno;
    private String registeredowner;
    private Date dateofregistration;
    private String remarks;

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

    public Integer getCollateralid() {
        return collateralid;
    }

    public void setCollateralid(Integer collateralid) {
        this.collateralid = collateralid;
    }

    public String getEntryno() {
        return entryno;
    }

    public void setEntryno(String entryno) {
        this.entryno = entryno;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public Date getDateinscribed() {
        return dateinscribed;
    }

    public void setDateinscribed(Date dateinscribed) {
        this.dateinscribed = dateinscribed;
    }

    public String getTracebacktitleno() {
        return tracebacktitleno;
    }

    public void setTracebacktitleno(String tracebacktitleno) {
        this.tracebacktitleno = tracebacktitleno;
    }

    public String getRegisteredowner() {
        return registeredowner;
    }

    public void setRegisteredowner(String registeredowner) {
        this.registeredowner = registeredowner;
    }

    public Date getDateofregistration() {
        return dateofregistration;
    }

    public void setDateofregistration(Date dateofregistration) {
        this.dateofregistration = dateofregistration;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
