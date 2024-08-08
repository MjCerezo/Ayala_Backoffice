
package com.cifsdb.data;

import java.util.Date;


/**
 *  CIFSDB.Tbbranch
 *  08/06/2024 19:26:36
 * 
 */
public class Tbbranch {

    private TbbranchId id;
    private String companycode;
    private String branchaddress;
    private String phoneno;
    private String faxno;
    private Boolean branchstatus;
    private Boolean isopen;
    private String branchclassification;
    private Date currentbusinessdate;
    private Date nextbusinessdate;
    private Date datecreated;
    private String createdby;
    private Date dateupdated;
    private String updatedby;

    public TbbranchId getId() {
        return id;
    }

    public void setId(TbbranchId id) {
        this.id = id;
    }

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode;
    }

    public String getBranchaddress() {
        return branchaddress;
    }

    public void setBranchaddress(String branchaddress) {
        this.branchaddress = branchaddress;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getFaxno() {
        return faxno;
    }

    public void setFaxno(String faxno) {
        this.faxno = faxno;
    }

    public Boolean getBranchstatus() {
        return branchstatus;
    }

    public void setBranchstatus(Boolean branchstatus) {
        this.branchstatus = branchstatus;
    }

    public Boolean getIsopen() {
        return isopen;
    }

    public void setIsopen(Boolean isopen) {
        this.isopen = isopen;
    }

    public String getBranchclassification() {
        return branchclassification;
    }

    public void setBranchclassification(String branchclassification) {
        this.branchclassification = branchclassification;
    }

    public Date getCurrentbusinessdate() {
        return currentbusinessdate;
    }

    public void setCurrentbusinessdate(Date currentbusinessdate) {
        this.currentbusinessdate = currentbusinessdate;
    }

    public Date getNextbusinessdate() {
        return nextbusinessdate;
    }

    public void setNextbusinessdate(Date nextbusinessdate) {
        this.nextbusinessdate = nextbusinessdate;
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

}
