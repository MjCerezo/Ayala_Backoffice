
package com.cifsdb.data;

import java.util.Date;


/**
 *  CIFSDB.Tbcollateralpergroup
 *  09/26/2023 10:13:05
 * 
 */
public class Tbcollateralpergroup {

    private Integer id;
    private String groupid;
    private String groupname;
    private String collateralreferenceno;
    private String collateraltype;
    private Date groupdateadded;
    private String groupaddedby;
    private String grouptype;
    private String groupappraisalstatus;
    private Date groupdateupdated;
    private String groupupdatedby;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getCollateralreferenceno() {
        return collateralreferenceno;
    }

    public void setCollateralreferenceno(String collateralreferenceno) {
        this.collateralreferenceno = collateralreferenceno;
    }

    public String getCollateraltype() {
        return collateraltype;
    }

    public void setCollateraltype(String collateraltype) {
        this.collateraltype = collateraltype;
    }

    public Date getGroupdateadded() {
        return groupdateadded;
    }

    public void setGroupdateadded(Date groupdateadded) {
        this.groupdateadded = groupdateadded;
    }

    public String getGroupaddedby() {
        return groupaddedby;
    }

    public void setGroupaddedby(String groupaddedby) {
        this.groupaddedby = groupaddedby;
    }

    public String getGrouptype() {
        return grouptype;
    }

    public void setGrouptype(String grouptype) {
        this.grouptype = grouptype;
    }

    public String getGroupappraisalstatus() {
        return groupappraisalstatus;
    }

    public void setGroupappraisalstatus(String groupappraisalstatus) {
        this.groupappraisalstatus = groupappraisalstatus;
    }

    public Date getGroupdateupdated() {
        return groupdateupdated;
    }

    public void setGroupdateupdated(Date groupdateupdated) {
        this.groupdateupdated = groupdateupdated;
    }

    public String getGroupupdatedby() {
        return groupupdatedby;
    }

    public void setGroupupdatedby(String groupupdatedby) {
        this.groupupdatedby = groupupdatedby;
    }

}
