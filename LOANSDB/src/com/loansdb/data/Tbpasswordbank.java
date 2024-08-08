
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbpasswordbank
 *  10/13/2020 10:21:35
 * 
 */
public class Tbpasswordbank {

    private Integer id;
    private String username;
    private String password;
    private Date datecreated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

}
