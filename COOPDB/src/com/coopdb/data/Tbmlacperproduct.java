
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbmlacperproduct
 *  08/27/2024 14:22:58
 * 
 */
public class Tbmlacperproduct {

    private TbmlacperproductId id;
    private Date dateadded;
    private String addedby;

    public TbmlacperproductId getId() {
        return id;
    }

    public void setId(TbmlacperproductId id) {
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
