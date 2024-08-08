
package com.cifsdb.data;

import java.util.Date;


/**
 *  CIFSDB.Tbmembershiptypeperbos
 *  09/26/2023 10:13:05
 * 
 */
public class Tbmembershiptypeperbos {

    private TbmembershiptypeperbosId id;
    private Date dateadded;
    private String addedby;

    public TbmembershiptypeperbosId getId() {
        return id;
    }

    public void setId(TbmembershiptypeperbosId id) {
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
