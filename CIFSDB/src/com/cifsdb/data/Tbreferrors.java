
package com.cifsdb.data;

import java.util.Date;


/**
 *  CIFSDB.Tbreferrors
 *  08/06/2024 19:26:35
 * 
 */
public class Tbreferrors {

    private Integer id;
    private String referraltype;
    private String referrorname;
    private Date validuntil;
    private Date accreditationdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReferraltype() {
        return referraltype;
    }

    public void setReferraltype(String referraltype) {
        this.referraltype = referraltype;
    }

    public String getReferrorname() {
        return referrorname;
    }

    public void setReferrorname(String referrorname) {
        this.referrorname = referrorname;
    }

    public Date getValiduntil() {
        return validuntil;
    }

    public void setValiduntil(Date validuntil) {
        this.validuntil = validuntil;
    }

    public Date getAccreditationdate() {
        return accreditationdate;
    }

    public void setAccreditationdate(Date accreditationdate) {
        this.accreditationdate = accreditationdate;
    }

}
