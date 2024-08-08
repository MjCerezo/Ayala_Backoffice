
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbteammembers
 *  02/23/2023 13:04:33
 * 
 */
public class Tbteammembers {

    private String teamcode;
    private String username;
    private String teamrole;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTeamrole() {
        return teamrole;
    }

    public void setTeamrole(String teamrole) {
        this.teamrole = teamrole;
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
