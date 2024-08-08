
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbnotification
 *  05/06/2018 20:31:49
 * 
 */
public class Tbnotification {

    private Integer id;
    private Date timestamps;
    private String transrefno;
    private String username;
    private String details;
    private String status;
    private Date statusdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(Date timestamps) {
        this.timestamps = timestamps;
    }

    public String getTransrefno() {
        return transrefno;
    }

    public void setTransrefno(String transrefno) {
        this.transrefno = transrefno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStatusdate() {
        return statusdate;
    }

    public void setStatusdate(Date statusdate) {
        this.statusdate = statusdate;
    }

}
