
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbcollateralperprod
 *  08/27/2024 14:22:57
 * 
 */
public class Tbcollateralperprod {

    private TbcollateralperprodId id;
    private Date dateadded;
    private String addedby;

    public TbcollateralperprodId getId() {
        return id;
    }

    public void setId(TbcollateralperprodId id) {
        this.id = id;
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
