
package com.cifsdb.data;

import java.math.BigDecimal;


/**
 *  CIFSDB.Tbapprelcomparablelisting
 *  09/26/2023 10:13:05
 * 
 */
public class Tbapprelcomparablelisting {

    private Integer id;
    private String appraisalreportid;
    private String appno;
    private Integer collateralid;
    private String locationdescription;
    private BigDecimal lotarea;
    private BigDecimal askingprice;
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

    public BigDecimal getLotarea() {
        return lotarea;
    }

    public void setLotarea(BigDecimal lotarea) {
        this.lotarea = lotarea;
    }

    public BigDecimal getAskingprice() {
        return askingprice;
    }

    public void setAskingprice(BigDecimal askingprice) {
        this.askingprice = askingprice;
    }

    public String getSourceofinfo() {
        return sourceofinfo;
    }

    public void setSourceofinfo(String sourceofinfo) {
        this.sourceofinfo = sourceofinfo;
    }

}
