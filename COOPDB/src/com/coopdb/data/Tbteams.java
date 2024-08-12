
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbteams
 *  08/10/2024 21:24:57
 * 
 */
public class Tbteams {

    private TbteamsId id;
    private String companycode;
    private String branchcode;
    private String groupcode;
    private String backupofficer;
    private String officer;
    private Boolean isofficeravailable;
    private Date datecreated;
    private String createdby;
    private Date dateupdated;
    private String updatedby;

    public TbteamsId getId() {
        return id;
    }

    public void setId(TbteamsId id) {
        this.id = id;
    }

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode;
    }

    public String getBranchcode() {
        return branchcode;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode;
    }

    public String getGroupcode() {
        return groupcode;
    }

    public void setGroupcode(String groupcode) {
        this.groupcode = groupcode;
    }

    public String getBackupofficer() {
        return backupofficer;
    }

    public void setBackupofficer(String backupofficer) {
        this.backupofficer = backupofficer;
    }

    public String getOfficer() {
        return officer;
    }

    public void setOfficer(String officer) {
        this.officer = officer;
    }

    public Boolean getIsofficeravailable() {
        return isofficeravailable;
    }

    public void setIsofficeravailable(Boolean isofficeravailable) {
        this.isofficeravailable = isofficeravailable;
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
