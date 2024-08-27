
package com.coopdb.data;

import java.math.BigDecimal;


/**
 *  COOPDB.Tbapprelbirzone
 *  08/27/2024 14:22:57
 * 
 */
public class Tbapprelbirzone {

    private Integer id;
    private String appraisalreportid;
    private String appno;
    private Integer collateralid;
    private String locationdescription;
    private String classification;
    private BigDecimal value;
    private String sourceofinfo;

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

    public String getLocationdescription() {
        return locationdescription;
    }

    public void setLocationdescription(String locationdescription) {
        this.locationdescription = locationdescription;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getSourceofinfo() {
        return sourceofinfo;
    }

    public void setSourceofinfo(String sourceofinfo) {
        this.sourceofinfo = sourceofinfo;
    }

}
