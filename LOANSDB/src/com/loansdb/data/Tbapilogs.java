
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbapilogs
 *  10/13/2020 10:21:35
 * 
 */
public class Tbapilogs {

    private Integer id;
    private String appno;
    private String apitype;
    private Boolean issuccess;
    private String requestbody;
    private String responsebody;
    private Date dateinvoked;
    private String invokedby;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getApitype() {
        return apitype;
    }

    public void setApitype(String apitype) {
        this.apitype = apitype;
    }

    public Boolean getIssuccess() {
        return issuccess;
    }

    public void setIssuccess(Boolean issuccess) {
        this.issuccess = issuccess;
    }

    public String getRequestbody() {
        return requestbody;
    }

    public void setRequestbody(String requestbody) {
        this.requestbody = requestbody;
    }

    public String getResponsebody() {
        return responsebody;
    }

    public void setResponsebody(String responsebody) {
        this.responsebody = responsebody;
    }

    public Date getDateinvoked() {
        return dateinvoked;
    }

    public void setDateinvoked(Date dateinvoked) {
        this.dateinvoked = dateinvoked;
    }

    public String getInvokedby() {
        return invokedby;
    }

    public void setInvokedby(String invokedby) {
        this.invokedby = invokedby;
    }

}
