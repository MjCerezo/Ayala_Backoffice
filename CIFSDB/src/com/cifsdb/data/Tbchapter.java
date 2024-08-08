
package com.cifsdb.data;

import java.util.Date;


/**
 *  CIFSDB.Tbchapter
 *  09/26/2023 10:13:05
 * 
 */
public class Tbchapter {

    private TbchapterId id;
    private String chaptername;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date dateupdated;
    private String coopcode;

    public TbchapterId getId() {
        return id;
    }

    public void setId(TbchapterId id) {
        this.id = id;
    }

    public String getChaptername() {
        return chaptername;
    }

    public void setChaptername(String chaptername) {
        this.chaptername = chaptername;
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

    public String getCoopcode() {
        return coopcode;
    }

    public void setCoopcode(String coopcode) {
        this.coopcode = coopcode;
    }

}
