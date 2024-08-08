
package com.loansdb.data;

import java.math.BigDecimal;


/**
 *  LOANSDB.Tbapprellanddata
 *  10/13/2020 10:21:35
 * 
 */
public class Tbapprellanddata {

    private Integer id;
    private String appraisalreportid;
    private String appno;
    private Integer collateralid;
    private String title;
    private String lotno;
    private String regowner;
    private String address;
    private BigDecimal area;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLotno() {
        return lotno;
    }

    public void setLotno(String lotno) {
        this.lotno = lotno;
    }

    public String getRegowner() {
        return regowner;
    }

    public void setRegowner(String regowner) {
        this.regowner = regowner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
