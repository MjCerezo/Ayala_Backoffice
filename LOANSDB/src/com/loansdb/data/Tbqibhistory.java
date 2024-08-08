
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbqibhistory
 *  06/21/2019 09:57:53
 * 
 */
public class Tbqibhistory {

    private Integer id;
    private String cifno;
    private Date activitydate;
    private String activitydesc;
    private String username;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public Date getActivitydate() {
        return activitydate;
    }

    public void setActivitydate(Date activitydate) {
        this.activitydate = activitydate;
    }

    public String getActivitydesc() {
        return activitydesc;
    }

    public void setActivitydesc(String activitydesc) {
        this.activitydesc = activitydesc;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
