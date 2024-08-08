
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbfieldparams
 *  10/13/2020 10:21:35
 * 
 */
public class Tbfieldparams {

    private String fieldcode;
    private String dbcode;
    private String tbcode;
    private String fieldname;
    private String fieldnametodisplay;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date dateupdated;

    public String getFieldcode() {
        return fieldcode;
    }

    public void setFieldcode(String fieldcode) {
        this.fieldcode = fieldcode;
    }

    public String getDbcode() {
        return dbcode;
    }

    public void setDbcode(String dbcode) {
        this.dbcode = dbcode;
    }

    public String getTbcode() {
        return tbcode;
    }

    public void setTbcode(String tbcode) {
        this.tbcode = tbcode;
    }

    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    public String getFieldnametodisplay() {
        return fieldnametodisplay;
    }

    public void setFieldnametodisplay(String fieldnametodisplay) {
        this.fieldnametodisplay = fieldnametodisplay;
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

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public Date getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }

}
