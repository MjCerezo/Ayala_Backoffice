
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbbicic
 *  08/04/2024 12:54:41
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
