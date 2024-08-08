
package com.cifsdb.data;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;


/**
 *  CIFSDB.Tbmembercompany
 *  09/26/2023 10:13:05
 * 
 */
public class Tbmembercompany {

    private TbmembercompanyId id;
    private String coopcode;
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
    private String psiccode;
    private String psiclevel1;
    private String psiclevel2;
    private String psiclevel3;
    private String psoccode;
    private String psoclevel1;
    private String psoclevel2;
    private String psoclevel3;
    private String psoclevel4;
    private String businesstype;
    private String faxareacode;
    private String faxphoneno;
    private String areacode;
    private String streetnoname;
    private String subdivision;
    private String barangay;
    private String stateprovince;
    private String city;
    private String region;
    private String country;
    private String postalcode;
    private String membershipclass;
    private BigDecimal maxloanableamount;

    public TbmembercompanyId getId() {
        return id;
    }

    public void setId(TbmembercompanyId id) {
        this.id = id;
    }

    public String getCoopcode() {
        return coopcode;
    }

    public void setCoopcode(String coopcode) {
        this.coopcode = coopcode;
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

    public String getPsiccode() {
        return psiccode;
    }

    public void setPsiccode(String psiccode) {
        this.psiccode = psiccode;
    }

    public String getPsiclevel1() {
        return psiclevel1;
    }

    public void setPsiclevel1(String psiclevel1) {
        this.psiclevel1 = psiclevel1;
    }

    public String getPsiclevel2() {
        return psiclevel2;
    }

    public void setPsiclevel2(String psiclevel2) {
        this.psiclevel2 = psiclevel2;
    }

    public String getPsiclevel3() {
        return psiclevel3;
    }

    public void setPsiclevel3(String psiclevel3) {
        this.psiclevel3 = psiclevel3;
    }

    public String getPsoccode() {
        return psoccode;
    }

    public void setPsoccode(String psoccode) {
        this.psoccode = psoccode;
    }

    public String getPsoclevel1() {
        return psoclevel1;
    }

    public void setPsoclevel1(String psoclevel1) {
        this.psoclevel1 = psoclevel1;
    }

    public String getPsoclevel2() {
        return psoclevel2;
    }

    public void setPsoclevel2(String psoclevel2) {
        this.psoclevel2 = psoclevel2;
    }

    public String getPsoclevel3() {
        return psoclevel3;
    }

    public void setPsoclevel3(String psoclevel3) {
        this.psoclevel3 = psoclevel3;
    }

    public String getPsoclevel4() {
        return psoclevel4;
    }

    public void setPsoclevel4(String psoclevel4) {
        this.psoclevel4 = psoclevel4;
    }

    public String getBusinesstype() {
        return businesstype;
    }

    public void setBusinesstype(String businesstype) {
        this.businesstype = businesstype;
    }

    public String getFaxareacode() {
        return faxareacode;
    }

    public void setFaxareacode(String faxareacode) {
        this.faxareacode = faxareacode;
    }

    public String getFaxphoneno() {
        return faxphoneno;
    }

    public void setFaxphoneno(String faxphoneno) {
        this.faxphoneno = faxphoneno;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public String getStreetnoname() {
        return streetnoname;
    }

    public void setStreetnoname(String streetnoname) {
        this.streetnoname = streetnoname;
    }

    public String getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(String subdivision) {
        this.subdivision = subdivision;
    }

    public String getBarangay() {
        return barangay;
    }

    public void setBarangay(String barangay) {
        this.barangay = barangay;
    }

    public String getStateprovince() {
        return stateprovince;
    }

    public void setStateprovince(String stateprovince) {
        this.stateprovince = stateprovince;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getMembershipclass() {
        return membershipclass;
    }

    public void setMembershipclass(String membershipclass) {
        this.membershipclass = membershipclass;
    }

    public BigDecimal getMaxloanableamount() {
        return maxloanableamount;
    }

    public void setMaxloanableamount(BigDecimal maxloanableamount) {
        this.maxloanableamount = maxloanableamount;
    }

}
