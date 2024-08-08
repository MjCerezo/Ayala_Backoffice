
package com.loansdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  LOANSDB.Tbcisec
 *  10/13/2020 10:21:35
 * 
 */
public class Tbcisec {

    private Integer id;
    private String cireportid;
    private String appno;
    private String cifno;
    private String companyname;
    private String regno;
    private Date regdate;
    private String primarypurpose;
    private String secondarypurpose;
    private String businessaddress;
    private String termsofexistence;
    private BigDecimal capitalstock;
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

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
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

    public String getPrimarypurpose() {
        return primarypurpose;
    }

    public void setPrimarypurpose(String primarypurpose) {
        this.primarypurpose = primarypurpose;
    }

    public String getSecondarypurpose() {
        return secondarypurpose;
    }

    public void setSecondarypurpose(String secondarypurpose) {
        this.secondarypurpose = secondarypurpose;
    }

    public String getBusinessaddress() {
        return businessaddress;
    }

    public void setBusinessaddress(String businessaddress) {
        this.businessaddress = businessaddress;
    }

    public String getTermsofexistence() {
        return termsofexistence;
    }

    public void setTermsofexistence(String termsofexistence) {
        this.termsofexistence = termsofexistence;
    }

    public BigDecimal getCapitalstock() {
        return capitalstock;
    }

    public void setCapitalstock(BigDecimal capitalstock) {
        this.capitalstock = capitalstock;
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
