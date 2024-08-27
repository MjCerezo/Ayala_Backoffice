
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbproductpercompany
 *  08/27/2024 14:22:58
 * 
 */
public class Tbproductpercompany {

    private TbproductpercompanyId id;
    private String productname;
    private String assignedby;
    private Date dateassigned;
    private Boolean isactive;
    private String updatedby;
    private Date dateupdated;
    private String producttype;

    public TbproductpercompanyId getId() {
        return id;
    }

    public void setId(TbproductpercompanyId id) {
        this.id = id;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getAssignedby() {
        return assignedby;
    }

    public void setAssignedby(String assignedby) {
        this.assignedby = assignedby;
    }

    public Date getDateassigned() {
        return dateassigned;
    }

    public void setDateassigned(Date dateassigned) {
        this.dateassigned = dateassigned;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
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

    public String getProducttype() {
        return producttype;
    }

    public void setProducttype(String producttype) {
        this.producttype = producttype;
    }

}
