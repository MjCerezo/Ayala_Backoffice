
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbevalappraisal
 *  10/13/2020 10:21:35
 * 
 */
public class Tbevalappraisal {

    private TbevalappraisalId id;
    private String appno;
    private String collateraltype;
    private String typeappraisal;
    private String createdby;
    private Date datecreated;

    public TbevalappraisalId getId() {
        return id;
    }

    public void setId(TbevalappraisalId id) {
        this.id = id;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getCollateraltype() {
        return collateraltype;
    }

    public void setCollateraltype(String collateraltype) {
        this.collateraltype = collateraltype;
    }

    public String getTypeappraisal() {
        return typeappraisal;
    }

    public void setTypeappraisal(String typeappraisal) {
        this.typeappraisal = typeappraisal;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

}
