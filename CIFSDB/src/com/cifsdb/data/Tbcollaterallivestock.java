
package com.cifsdb.data;

import java.util.Date;


/**
 *  CIFSDB.Tbcollaterallivestock
 *  09/26/2023 10:13:05
 * 
 */
public class Tbcollaterallivestock {

    private Integer collateralid;
    private String referenceno;
    private String collateralstatus;
    private String appraisalstatus;
    private Date dateencoded;
    private String encodedby;
    private String description;
    private String typelivestock;

    public Integer getCollateralid() {
        return collateralid;
    }

    public void setCollateralid(Integer collateralid) {
        this.collateralid = collateralid;
    }

    public String getReferenceno() {
        return referenceno;
    }

    public void setReferenceno(String referenceno) {
        this.referenceno = referenceno;
    }

    public String getCollateralstatus() {
        return collateralstatus;
    }

    public void setCollateralstatus(String collateralstatus) {
        this.collateralstatus = collateralstatus;
    }

    public String getAppraisalstatus() {
        return appraisalstatus;
    }

    public void setAppraisalstatus(String appraisalstatus) {
        this.appraisalstatus = appraisalstatus;
    }

    public Date getDateencoded() {
        return dateencoded;
    }

    public void setDateencoded(Date dateencoded) {
        this.dateencoded = dateencoded;
    }

    public String getEncodedby() {
        return encodedby;
    }

    public void setEncodedby(String encodedby) {
        this.encodedby = encodedby;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypelivestock() {
        return typelivestock;
    }

    public void setTypelivestock(String typelivestock) {
        this.typelivestock = typelivestock;
    }

}
