
package com.cifsdb.data;

import java.util.Date;


/**
 *  CIFSDB.Tbteams
 *  08/27/2024 14:22:04
 * 
 */
public class Tbteams {

    private String teamcode;
    private String companycode;
    private String branchcode;
    private String groupcode;
    private String teamname;
    private String backupofficer;
    private String officer;
    private Boolean isofficeravailable;
    private Date datecreated;
    private String createdby;
    private Date dateupdated;
    private String updatedby;

    public String getTeamcode() {
        return teamcode;
    }

    public void setTeamcode(String teamcode) {
        this.teamcode = teamcode;
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

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
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
