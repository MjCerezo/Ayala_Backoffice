
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tblstcomakers
 *  10/13/2020 10:21:35
 * 
 */
public class Tblstcomakers {

    private TblstcomakersId id;
    private String customername;
    private Date dateadded;

    public TblstcomakersId getId() {
        return id;
    }

    public void setId(TblstcomakersId id) {
        this.id = id;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public Date getDateadded() {
        return dateadded;
    }

    public void setDateadded(Date dateadded) {
        this.dateadded = dateadded;
    }

}
