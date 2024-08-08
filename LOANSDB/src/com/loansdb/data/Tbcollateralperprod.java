
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbcollateralperprod
 *  10/13/2020 10:21:35
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
