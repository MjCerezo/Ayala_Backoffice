
package com.loansdb.data;



/**
 *  LOANSDB.Tbdeskcidetails
 *  10/13/2020 10:21:35
 * 
 */
public class Tbdeskcidetails {

    private Integer id;
    private String cireportid;
    private String ciactivity;
    private String category;
    private String declaredvalue;
    private String findings;
    private String remarks;
    private Integer employmentid;
    private Integer businessid;
    private Integer deskciactivityid;

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

    public String getCiactivity() {
        return ciactivity;
    }

    public void setCiactivity(String ciactivity) {
        this.ciactivity = ciactivity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDeclaredvalue() {
        return declaredvalue;
    }

    public void setDeclaredvalue(String declaredvalue) {
        this.declaredvalue = declaredvalue;
    }

    public String getFindings() {
        return findings;
    }

    public void setFindings(String findings) {
        this.findings = findings;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getEmploymentid() {
        return employmentid;
    }

    public void setEmploymentid(Integer employmentid) {
        this.employmentid = employmentid;
    }

    public Integer getBusinessid() {
        return businessid;
    }

    public void setBusinessid(Integer businessid) {
        this.businessid = businessid;
    }

    public Integer getDeskciactivityid() {
        return deskciactivityid;
    }

    public void setDeskciactivityid(Integer deskciactivityid) {
        this.deskciactivityid = deskciactivityid;
    }

}
