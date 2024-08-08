
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbcfinsurancedetails
 *  10/13/2020 10:21:35
 * 
 */
public class Tbcfinsurancedetails {

    private Integer id;
    private String appno;
    private String assuredname;
    private String policyno;
    private String typeinsurance;
    private String insurancecompany;
    private Date coveragefrom;
    private Date coverageto;
    private String propertyitem;
    private String propertydesc;
    private String locationrisk;

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

    public String getAssuredname() {
        return assuredname;
    }

    public void setAssuredname(String assuredname) {
        this.assuredname = assuredname;
    }

    public String getPolicyno() {
        return policyno;
    }

    public void setPolicyno(String policyno) {
        this.policyno = policyno;
    }

    public String getTypeinsurance() {
        return typeinsurance;
    }

    public void setTypeinsurance(String typeinsurance) {
        this.typeinsurance = typeinsurance;
    }

    public String getInsurancecompany() {
        return insurancecompany;
    }

    public void setInsurancecompany(String insurancecompany) {
        this.insurancecompany = insurancecompany;
    }

    public Date getCoveragefrom() {
        return coveragefrom;
    }

    public void setCoveragefrom(Date coveragefrom) {
        this.coveragefrom = coveragefrom;
    }

    public Date getCoverageto() {
        return coverageto;
    }

    public void setCoverageto(Date coverageto) {
        this.coverageto = coverageto;
    }

    public String getPropertyitem() {
        return propertyitem;
    }

    public void setPropertyitem(String propertyitem) {
        this.propertyitem = propertyitem;
    }

    public String getPropertydesc() {
        return propertydesc;
    }

    public void setPropertydesc(String propertydesc) {
        this.propertydesc = propertydesc;
    }

    public String getLocationrisk() {
        return locationrisk;
    }

    public void setLocationrisk(String locationrisk) {
        this.locationrisk = locationrisk;
    }

}
