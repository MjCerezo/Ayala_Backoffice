
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbmisctx
 *  08/27/2024 14:22:56
 * 
 */
public class Tbmisctx {

    private Integer id;
    private String txcode;
    private String mediatype;
    private String medianumber;
    private BigDecimal txamount;
    private String createdby;
    private Date datecreated;
    private String remarks;
    private String paymode;
    private Boolean islateclearing;
    private String txrefno;
    private Date txdate;
    private Date txvaldt;
    private String feecode;
    private String branchcode;
    private String cifno;
    private String errorcorrecttxrefno;
    private Boolean errorcorrectind;

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

    public String getMediatype() {
        return mediatype;
    }

    public void setMediatype(String mediatype) {
        this.mediatype = mediatype;
    }

    public String getMedianumber() {
        return medianumber;
    }

    public void setMedianumber(String medianumber) {
        this.medianumber = medianumber;
    }

    public BigDecimal getTxamount() {
        return txamount;
    }

    public void setTxamount(BigDecimal txamount) {
        this.txamount = txamount;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPaymode() {
        return paymode;
    }

    public void setPaymode(String paymode) {
        this.paymode = paymode;
    }

    public Boolean getIslateclearing() {
        return islateclearing;
    }

    public void setIslateclearing(Boolean islateclearing) {
        this.islateclearing = islateclearing;
    }

    public String getTxrefno() {
        return txrefno;
    }

    public void setTxrefno(String txrefno) {
        this.txrefno = txrefno;
    }

    public Date getTxdate() {
        return txdate;
    }

    public void setTxdate(Date txdate) {
        this.txdate = txdate;
    }

    public Date getTxvaldt() {
        return txvaldt;
    }

    public void setTxvaldt(Date txvaldt) {
        this.txvaldt = txvaldt;
    }

    public String getFeecode() {
        return feecode;
    }

    public void setFeecode(String feecode) {
        this.feecode = feecode;
    }

    public String getBranchcode() {
        return branchcode;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getErrorcorrecttxrefno() {
        return errorcorrecttxrefno;
    }

    public void setErrorcorrecttxrefno(String errorcorrecttxrefno) {
        this.errorcorrecttxrefno = errorcorrecttxrefno;
    }

    public Boolean getErrorcorrectind() {
        return errorcorrectind;
    }

    public void setErrorcorrectind(Boolean errorcorrectind) {
        this.errorcorrectind = errorcorrectind;
    }

}
