
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbcidti
 *  10/13/2020 10:21:35
 * 
 */
public class Tbcidti {

    private Integer id;
    private String cireportid;
    private String appno;
    private String cifno;
    private String regno;
    private Date regdate;
    private Date expirydate;
    private String regowner;
    private String businesstype;
    private Date datecreated;
    private Date datelastupdated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCireportid() {
        return cireportid;
    }

    public void setCireportid(String cireportid) {
        this.cireportid = cireportid;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public Date getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(Date expirydate) {
        this.expirydate = expirydate;
    }

    public String getRegowner() {
        return regowner;
    }

    public void setRegowner(String regowner) {
        this.regowner = regowner;
    }

    public String getBusinesstype() {
        return businesstype;
    }

    public void setBusinesstype(String businesstype) {
        this.businesstype = businesstype;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public Date getDatelastupdated() {
        return datelastupdated;
    }

    public void setDatelastupdated(Date datelastupdated) {
        this.datelastupdated = datelastupdated;
    }

}
