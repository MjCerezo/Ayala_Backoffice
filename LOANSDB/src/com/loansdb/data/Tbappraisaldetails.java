
package com.loansdb.data;



/**
 *  LOANSDB.Tbappraisaldetails
 *  10/13/2020 10:21:35
 * 
 */
public class Tbappraisaldetails {

    private Integer id;
    private String appraisalreportid;
    private String collateraltype;
    private String referenceno;
    private String collateralid;
    private String category;
    private String declaredvalue;
    private String findings;
    private String remarks;
    private Integer appraisalactivityid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppraisalreportid() {
        return appraisalreportid;
    }

    public void setAppraisalreportid(String appraisalreportid) {
        this.appraisalreportid = appraisalreportid;
    }

    public String getCollateraltype() {
        return collateraltype;
    }

    public void setCollateraltype(String collateraltype) {
        this.collateraltype = collateraltype;
    }

    public String getReferenceno() {
        return referenceno;
    }

    public void setReferenceno(String referenceno) {
        this.referenceno = referenceno;
    }

    public String getCollateralid() {
        return collateralid;
    }

    public void setCollateralid(String collateralid) {
        this.collateralid = collateralid;
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

    public Integer getAppraisalactivityid() {
        return appraisalactivityid;
    }

    public void setAppraisalactivityid(Integer appraisalactivityid) {
        this.appraisalactivityid = appraisalactivityid;
    }

}
