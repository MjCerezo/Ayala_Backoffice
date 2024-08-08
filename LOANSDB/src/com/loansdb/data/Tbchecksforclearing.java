
package com.loansdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  LOANSDB.Tbchecksforclearing
 *  02/13/2019 19:49:11
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

}
