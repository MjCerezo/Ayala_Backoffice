
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbchecksforclearing
 *  08/27/2024 14:22:57
 * 
 */
public class Tbchecksforclearing {

    private Integer id;
    private String brstn;
    private BigDecimal checkamount;
    private Date checkdate;
    private String checknumber;
    private Integer checktype;
    private Date clearingdate;
    private Integer clearingdays;
    private Boolean islateclearing;
    private String status;
    private String accountnumber;
    private String txrefno;
    private String clearingtype;
    private Date txstatusdate;
    private String updatedby;
    private Date dateupdated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrstn() {
        return brstn;
    }

    public void setBrstn(String brstn) {
        this.brstn = brstn;
    }

    public BigDecimal getCheckamount() {
        return checkamount;
    }

    public void setCheckamount(BigDecimal checkamount) {
        this.checkamount = checkamount;
    }

    public Date getCheckdate() {
        return checkdate;
    }

    public void setCheckdate(Date checkdate) {
        this.checkdate = checkdate;
    }

    public String getChecknumber() {
        return checknumber;
    }

    public void setChecknumber(String checknumber) {
        this.checknumber = checknumber;
    }

    public Integer getChecktype() {
        return checktype;
    }

    public void setChecktype(Integer checktype) {
        this.checktype = checktype;
    }

    public Date getClearingdate() {
        return clearingdate;
    }

    public void setClearingdate(Date clearingdate) {
        this.clearingdate = clearingdate;
    }

    public Integer getClearingdays() {
        return clearingdays;
    }

    public void setClearingdays(Integer clearingdays) {
        this.clearingdays = clearingdays;
    }

    public Boolean getIslateclearing() {
        return islateclearing;
    }

    public void setIslateclearing(Boolean islateclearing) {
        this.islateclearing = islateclearing;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }

    public String getTxrefno() {
        return txrefno;
    }

    public void setTxrefno(String txrefno) {
        this.txrefno = txrefno;
    }

    public String getClearingtype() {
        return clearingtype;
    }

    public void setClearingtype(String clearingtype) {
        this.clearingtype = clearingtype;
    }

    public Date getTxstatusdate() {
        return txstatusdate;
    }

    public void setTxstatusdate(Date txstatusdate) {
        this.txstatusdate = txstatusdate;
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

}
