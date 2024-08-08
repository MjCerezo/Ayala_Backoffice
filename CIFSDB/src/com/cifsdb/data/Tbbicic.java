
package com.cifsdb.data;

import java.util.Date;


/**
 *  CIFSDB.Tbbicic
 *  09/26/2023 10:13:05
 * 
 */
public class Tbbicic {

    private String bireportid;
    private String cifno;
    private String appno;
    private String dmsid;
    private String description;
    private String birequestid;
    private Date reportdate;

    public String getBireportid() {
        return bireportid;
    }

    public void setBireportid(String bireportid) {
        this.bireportid = bireportid;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getDmsid() {
        return dmsid;
    }

    public void setDmsid(String dmsid) {
        this.dmsid = dmsid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBirequestid() {
        return birequestid;
    }

    public void setBirequestid(String birequestid) {
        this.birequestid = birequestid;
    }

    public Date getReportdate() {
        return reportdate;
    }

    public void setReportdate(Date reportdate) {
        this.reportdate = reportdate;
    }

}
