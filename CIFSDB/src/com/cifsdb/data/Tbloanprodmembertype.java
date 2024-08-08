
package com.cifsdb.data;

import java.util.Date;


/**
 *  CIFSDB.Tbloanprodmembertype
 *  09/26/2023 10:13:05
 * 
 */
public class Tbloanprodmembertype {

    private TbloanprodmembertypeId id;
    private Boolean isactive;
    private Boolean isretired;
    private Date dateadded;
    private String addedby;

    public TbloanprodmembertypeId getId() {
        return id;
    }

    public void setId(TbloanprodmembertypeId id) {
        this.id = id;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public Boolean getIsretired() {
        return isretired;
    }

    public void setIsretired(Boolean isretired) {
        this.isretired = isretired;
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

}
