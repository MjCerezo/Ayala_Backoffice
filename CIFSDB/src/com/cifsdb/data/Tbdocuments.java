
package com.cifsdb.data;

import java.util.Date;


/**
 *  CIFSDB.Tbdocuments
 *  09/26/2023 10:13:06
 * 
 */
public class Tbdocuments {

    private String documentcode;
    private String documentname;
    private String doccategory;
    private String doctype;
    private Integer applicationstatus;
    private String remarks;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date dateupdated;
    private Boolean iseditable;
    private Boolean iscifavailable;

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

    public String getDoccategory() {
        return doccategory;
    }

    public void setDoccategory(String doccategory) {
        this.doccategory = doccategory;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public Integer getApplicationstatus() {
        return applicationstatus;
    }

    public void setApplicationstatus(Integer applicationstatus) {
        this.applicationstatus = applicationstatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public Boolean getIseditable() {
        return iseditable;
    }

    public void setIseditable(Boolean iseditable) {
        this.iseditable = iseditable;
    }

    public Boolean getIscifavailable() {
        return iscifavailable;
    }

    public void setIscifavailable(Boolean iscifavailable) {
        this.iscifavailable = iscifavailable;
    }

}
