
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbdocpertransaction
 *  07/06/2018 16:04:07
 * 
 */
public class Tbdocpertransaction {

    private Integer transid;
    private String txcode;
    private String documentcode;
    private String documentname;
    private String productcode;
    private Integer applicationstatus;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date dateupdated;
    private String remarks;
    private String subtypecode;
    private String realestatecode;
    private String reqtype;
    private String reqgroup;

    public Integer getTransid() {
        return transid;
    }

    public void setTransid(Integer transid) {
        this.transid = transid;
    }

    public String getTxcode() {
        return txcode;
    }

    public void setTxcode(String txcode) {
        this.txcode = txcode;
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

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
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

    public String getSubtypecode() {
        return subtypecode;
    }

    public void setSubtypecode(String subtypecode) {
        this.subtypecode = subtypecode;
    }

    public String getRealestatecode() {
        return realestatecode;
    }

    public void setRealestatecode(String realestatecode) {
        this.realestatecode = realestatecode;
    }

    public String getReqtype() {
        return reqtype;
    }

    public void setReqtype(String reqtype) {
        this.reqtype = reqtype;
    }

    public String getReqgroup() {
        return reqgroup;
    }

    public void setReqgroup(String reqgroup) {
        this.reqgroup = reqgroup;
    }

}
