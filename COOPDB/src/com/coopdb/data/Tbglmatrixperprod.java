
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbglmatrixperprod
 *  08/04/2024 12:54:43
 * 
 */
public class Tbglmatrixperprod {

    private TbglmatrixperprodId id;
    private String glline1;
    private String gllinedesc1;
    private String glline2;
    private String gllinedesc2;
    private Date dateupdated;
    private String updatedby;
    private Date datecreated;
    private String createdby;
    private String glacctcode;
    private String glacctdesc;

    public TbglmatrixperprodId getId() {
        return id;
    }

    public void setId(TbglmatrixperprodId id) {
        this.id = id;
    }

    public String getGlline1() {
        return glline1;
    }

    public void setGlline1(String glline1) {
        this.glline1 = glline1;
    }

    public String getGllinedesc1() {
        return gllinedesc1;
    }

    public void setGllinedesc1(String gllinedesc1) {
        this.gllinedesc1 = gllinedesc1;
    }

    public String getGlline2() {
        return glline2;
    }

    public void setGlline2(String glline2) {
        this.glline2 = glline2;
    }

    public String getGllinedesc2() {
        return gllinedesc2;
    }

    public void setGllinedesc2(String gllinedesc2) {
        this.gllinedesc2 = gllinedesc2;
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

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getGlacctcode() {
        return glacctcode;
    }

    public void setGlacctcode(String glacctcode) {
        this.glacctcode = glacctcode;
    }

    public String getGlacctdesc() {
        return glacctdesc;
    }

    public void setGlacctdesc(String glacctdesc) {
        this.glacctdesc = glacctdesc;
    }

}
