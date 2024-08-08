
package com.cifsdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  CIFSDB.Tbappreltechidentification
 *  09/26/2023 10:13:05
 * 
 */
public class Tbappreltechidentification {

    private Integer id;
    private String appraisalreportid;
    private String appno;
    private Integer collateralid;
    private String title;
    private String lotno;
    private String blkno;
    private String regowner;
    private String address;
    private BigDecimal area;
    private Date verificationdate;
    private String boundaries;

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

    public String getBlkno() {
        return blkno;
    }

    public void setBlkno(String blkno) {
        this.blkno = blkno;
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

    public Date getVerificationdate() {
        return verificationdate;
    }

    public void setVerificationdate(Date verificationdate) {
        this.verificationdate = verificationdate;
    }

    public String getBoundaries() {
        return boundaries;
    }

    public void setBoundaries(String boundaries) {
        this.boundaries = boundaries;
    }

}
