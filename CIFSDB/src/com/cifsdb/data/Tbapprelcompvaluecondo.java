
package com.cifsdb.data;

import java.math.BigDecimal;


/**
 *  CIFSDB.Tbapprelcompvaluecondo
 *  09/26/2023 10:13:05
 * 
 */
public class Tbapprelcompvaluecondo {

    private Integer id;
    private String appraisalreportid;
    private String appno;
    private Integer collateralid;
    private BigDecimal floorarea;
    private BigDecimal valuepersqm;
    private BigDecimal appraisedvalue;
    private BigDecimal tav;

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

    public BigDecimal getFloorarea() {
        return floorarea;
    }

    public void setFloorarea(BigDecimal floorarea) {
        this.floorarea = floorarea;
    }

    public BigDecimal getValuepersqm() {
        return valuepersqm;
    }

    public void setValuepersqm(BigDecimal valuepersqm) {
        this.valuepersqm = valuepersqm;
    }

    public BigDecimal getAppraisedvalue() {
        return appraisedvalue;
    }

    public void setAppraisedvalue(BigDecimal appraisedvalue) {
        this.appraisedvalue = appraisedvalue;
    }

    public BigDecimal getTav() {
        return tav;
    }

    public void setTav(BigDecimal tav) {
        this.tav = tav;
    }

}
