
package com.cifsdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  CIFSDB.Tbnetamt
 *  09/26/2023 10:13:05
 * 
 */
public class Tbnetamt {

    private Integer id;
    private String userid;
    private String currency;
    private BigDecimal userbalance;
    private Date businessdate;
    private String transfertype;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getUserbalance() {
        return userbalance;
    }

    public void setUserbalance(BigDecimal userbalance) {
        this.userbalance = userbalance;
    }

    public Date getBusinessdate() {
        return businessdate;
    }

    public void setBusinessdate(Date businessdate) {
        this.businessdate = businessdate;
    }

    public String getTransfertype() {
        return transfertype;
    }

    public void setTransfertype(String transfertype) {
        this.transfertype = transfertype;
    }

}
