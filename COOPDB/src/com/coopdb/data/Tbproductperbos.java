
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbproductperbos
 *  08/10/2024 21:24:57
 * 
 */
public class Tbproductperbos {

    private TbproductperbosId id;
    private String productname;
    private String membershiptype;
    private String servicestatus;
    private String assignedby;
    private Date dateassigned;
    private Boolean isactive;
    private String updatedby;
    private Date dateupdated;
    private String producttype;
    private String membershiptypedesc;
    private String servicestatusdesc;
    private BigDecimal maxloanableamt;

    public TbproductperbosId getId() {
        return id;
    }

    public void setId(TbproductperbosId id) {
        this.id = id;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getMembershiptype() {
        return membershiptype;
    }

    public void setMembershiptype(String membershiptype) {
        this.membershiptype = membershiptype;
    }

    public String getServicestatus() {
        return servicestatus;
    }

    public void setServicestatus(String servicestatus) {
        this.servicestatus = servicestatus;
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

    public String getMembershiptypedesc() {
        return membershiptypedesc;
    }

    public void setMembershiptypedesc(String membershiptypedesc) {
        this.membershiptypedesc = membershiptypedesc;
    }

    public String getServicestatusdesc() {
        return servicestatusdesc;
    }

    public void setServicestatusdesc(String servicestatusdesc) {
        this.servicestatusdesc = servicestatusdesc;
    }

    public BigDecimal getMaxloanableamt() {
        return maxloanableamt;
    }

    public void setMaxloanableamt(BigDecimal maxloanableamt) {
        this.maxloanableamt = maxloanableamt;
    }

}
