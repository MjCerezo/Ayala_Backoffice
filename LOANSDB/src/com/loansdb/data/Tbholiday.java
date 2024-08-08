
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbholiday
 *  10/13/2020 10:21:35
 * 
 */
public class Tbholiday {

    private Integer id;
    private Date holdate;
    private String holname;
    private String holtype;
    private String reference;
    private String remarks;
    private Boolean isactive;
    private Date dateadded;
    private String addedby;
    private Date dateupdated;
    private String updatedby;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getHoldate() {
        return holdate;
    }

    public void setHoldate(Date holdate) {
        this.holdate = holdate;
    }

    public String getHolname() {
        return holname;
    }

    public void setHolname(String holname) {
        this.holname = holname;
    }

    public String getHoltype() {
        return holtype;
    }

    public void setHoltype(String holtype) {
        this.holtype = holtype;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public Date getDateadded() {
        return dateadded;
    }

    public void setDateadded(Date dateadded) {
        this.dateadded = dateadded;
    }

    public String getAddedby() {
        return addedby;
    }

    public void setAddedby(String addedby) {
        this.addedby = addedby;
    }

    public Date getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

}
