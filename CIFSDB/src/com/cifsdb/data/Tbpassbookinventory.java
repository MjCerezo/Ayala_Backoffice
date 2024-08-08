
package com.cifsdb.data;

import java.util.Date;


/**
 *  CIFSDB.Tbpassbookinventory
 *  09/26/2023 10:13:05
 * 
 */
public class Tbpassbookinventory {

    private Integer id;
    private String seriesfrom;
    private String seriesto;
    private String branch;
    private String company;
    private String createdby;
    private Date datecreated;
    private String status;
    private String currentseriesno;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeriesfrom() {
        return seriesfrom;
    }

    public void setSeriesfrom(String seriesfrom) {
        this.seriesfrom = seriesfrom;
    }

    public String getSeriesto() {
        return seriesto;
    }

    public void setSeriesto(String seriesto) {
        this.seriesto = seriesto;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrentseriesno() {
        return currentseriesno;
    }

    public void setCurrentseriesno(String currentseriesno) {
        this.currentseriesno = currentseriesno;
    }

}
