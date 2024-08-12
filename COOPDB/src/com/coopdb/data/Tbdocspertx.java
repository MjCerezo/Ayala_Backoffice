
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbdocspertx
 *  08/10/2024 21:24:56
 * 
 */
public class Tbdocspertx {

    private Integer id;
    private Integer txrefno;
    private String txcode;
    private String documentcode;
    private String documentname;
    private Boolean issubmitted;
    private String uploadedby;
    private Date datesubmitted;
    private Boolean isreviewed;
    private Date datereviewed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTxrefno() {
        return txrefno;
    }

    public void setTxrefno(Integer txrefno) {
        this.txrefno = txrefno;
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

    public Boolean getIssubmitted() {
        return issubmitted;
    }

    public void setIssubmitted(Boolean issubmitted) {
        this.issubmitted = issubmitted;
    }

    public String getUploadedby() {
        return uploadedby;
    }

    public void setUploadedby(String uploadedby) {
        this.uploadedby = uploadedby;
    }

    public Date getDatesubmitted() {
        return datesubmitted;
    }

    public void setDatesubmitted(Date datesubmitted) {
        this.datesubmitted = datesubmitted;
    }

    public Boolean getIsreviewed() {
        return isreviewed;
    }

    public void setIsreviewed(Boolean isreviewed) {
        this.isreviewed = isreviewed;
    }

    public Date getDatereviewed() {
        return datereviewed;
    }

    public void setDatereviewed(Date datereviewed) {
        this.datereviewed = datereviewed;
    }

}
