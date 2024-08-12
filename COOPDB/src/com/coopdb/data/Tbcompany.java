
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbcompany
 *  08/10/2024 21:24:57
 * 
 */
public class Tbcompany {

    private TbcompanyId id;
    private String hcompanycode;
    private String address;
    private String phoneno;
    private String faxno;
    private String emailaddress;
    private String website;
    private Boolean companystatus;
    private String logobasecode;
    private String logofilename;
    private Date datecreated;
    private String createdby;
    private Date dateupdated;
    private String updatedby;

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

    public String getLogobasecode() {
        return logobasecode;
    }

    public void setLogobasecode(String logobasecode) {
        this.logobasecode = logobasecode;
    }

    public String getLogofilename() {
        return logofilename;
    }

    public void setLogofilename(String logofilename) {
        this.logofilename = logofilename;
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

}
