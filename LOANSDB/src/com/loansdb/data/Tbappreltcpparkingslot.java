
package com.loansdb.data;

import java.math.BigDecimal;


/**
 *  LOANSDB.Tbappreltcpparkingslot
 *  10/13/2020 10:21:35
 * 
 */
public class Tbappreltcpparkingslot {

    private Integer id;
    private String appraisalreportid;
    private String appno;
    private Integer collateralid;
    private String parkingslot;
    private BigDecimal tcp;
    private BigDecimal grosstcp;

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

    public String getParkingslot() {
        return parkingslot;
    }

    public void setParkingslot(String parkingslot) {
        this.parkingslot = parkingslot;
    }

    public BigDecimal getTcp() {
        return tcp;
    }

    public void setTcp(BigDecimal tcp) {
        this.tcp = tcp;
    }

    public BigDecimal getGrosstcp() {
        return grosstcp;
    }

    public void setGrosstcp(BigDecimal grosstcp) {
        this.grosstcp = grosstcp;
    }

}
