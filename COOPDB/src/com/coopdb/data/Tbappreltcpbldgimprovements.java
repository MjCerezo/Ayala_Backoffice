
package com.coopdb.data;

import java.math.BigDecimal;


/**
 *  COOPDB.Tbappreltcpbldgimprovements
 *  08/27/2024 14:22:57
 * 
 */
public class Tbappreltcpbldgimprovements {

    private Integer id;
    private String appraisalreportid;
    private String appno;
    private Integer collateralid;
    private String bldg;
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

    public String getBldg() {
        return bldg;
    }

    public void setBldg(String bldg) {
        this.bldg = bldg;
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
