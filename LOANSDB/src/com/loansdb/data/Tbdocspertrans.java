
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbdocspertrans
 *  10/13/2020 10:21:35
 * 
 */
public class Tbdocspertrans {

    private Integer id;
    private String txcode;
    private String doccategory;
    private String documentcode;
    private String documentname;
    private Integer applicationstatus;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date dateupdated;
    private String remarks;
    private Boolean isindivrequired;
    private Boolean iscorprequired;
    private Boolean issoleproprequired;
    private Boolean ispartnerrequired;
    private Boolean enablenotarialfee;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTxcode() {
        return txcode;
    }

    public void setTxcode(String txcode) {
        this.txcode = txcode;
    }

    public String getDoccategory() {
        return doccategory;
    }

    public void setDoccategory(String doccategory) {
        this.doccategory = doccategory;
    }

    public String getDocumentcode() {
        return documentcode;
    }

    public void setDocumentcode(String documentcode) {
        this.documentcode = documentcode;
    }

    public String getDocumentname() {
        return documentname;
    }

    public void setDocumentname(String documentname) {
        this.documentname = documentname;
    }

    public Integer getApplicationstatus() {
        return applicationstatus;
    }

    public void setApplicationstatus(Integer applicationstatus) {
        this.applicationstatus = applicationstatus;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public Date getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getIsindivrequired() {
        return isindivrequired;
    }

    public void setIsindivrequired(Boolean isindivrequired) {
        this.isindivrequired = isindivrequired;
    }

    public Boolean getIscorprequired() {
        return iscorprequired;
    }

    public void setIscorprequired(Boolean iscorprequired) {
        this.iscorprequired = iscorprequired;
    }

    public Boolean getIssoleproprequired() {
        return issoleproprequired;
    }

    public void setIssoleproprequired(Boolean issoleproprequired) {
        this.issoleproprequired = issoleproprequired;
    }

    public Boolean getIspartnerrequired() {
        return ispartnerrequired;
    }

    public void setIspartnerrequired(Boolean ispartnerrequired) {
        this.ispartnerrequired = ispartnerrequired;
    }

    public Boolean getEnablenotarialfee() {
        return enablenotarialfee;
    }

    public void setEnablenotarialfee(Boolean enablenotarialfee) {
        this.enablenotarialfee = enablenotarialfee;
    }

}
