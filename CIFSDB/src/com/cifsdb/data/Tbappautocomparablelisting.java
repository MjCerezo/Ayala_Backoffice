
package com.cifsdb.data;

import java.math.BigDecimal;


/**
 *  CIFSDB.Tbappautocomparablelisting
 *  09/26/2023 10:13:04
 * 
 */
public class Tbappautocomparablelisting {

    private Integer id;
    private String appraisalreportid;
    private Integer collateralid;
    private String source;
    private BigDecimal price;
    private Integer lotarea;
    private String description;

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

    public Integer getCollateralid() {
        return collateralid;
    }

    public void setCollateralid(Integer collateralid) {
        this.collateralid = collateralid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getLotarea() {
        return lotarea;
    }

    public void setLotarea(Integer lotarea) {
        this.lotarea = lotarea;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
