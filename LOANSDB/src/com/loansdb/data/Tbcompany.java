
package com.loansdb.data;

import java.sql.Blob;
import java.util.Date;


/**
 *  LOANSDB.Tbcompany
 *  10/13/2020 10:21:35
 * 
 */
public class Tbcompany {

    private TbcompanyId id;
    private String hcompanycode;
    private String cifno;
    private String address;
    private String phoneno;
    private String faxno;
    private String emailaddress;
    private String website;
    private Boolean companystatus;
    private Blob logo;
    private Date datecreated;
    private String createdby;
    private Date dateupdated;
    private String updatedby;
    private Boolean isloscompany;

    public TbcompanyId getId() {
        return id;
    }

    public void setId(TbcompanyId id) {
        this.id = id;
    }

    public String getHcompanycode() {
        return hcompanycode;
    }

    public void setHcompanycode(String hcompanycode) {
        this.hcompanycode = hcompanycode;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getFaxno() {
        return faxno;
    }

    public void setFaxno(String faxno) {
        this.faxno = faxno;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Boolean getCompanystatus() {
        return companystatus;
    }

    public void setCompanystatus(Boolean companystatus) {
        this.companystatus = companystatus;
    }

    public Blob getLogo() {
        return logo;
    }

    public void setLogo(Blob logo) {
        this.logo = logo;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Date getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public Boolean getIsloscompany() {
        return isloscompany;
    }

    public void setIsloscompany(Boolean isloscompany) {
        this.isloscompany = isloscompany;
    }

}
