
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbmerchant
 *  08/27/2024 14:22:56
 * 
 */
public class Tbmerchant {

    private Integer id;
    private String merchantcode;
    private String merchantname;
    private String accountno;
    private Date opendt;
    private String status;
    private String unit;
    private String instcode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMerchantcode() {
        return merchantcode;
    }

    public void setMerchantcode(String merchantcode) {
        this.merchantcode = merchantcode;
    }

    public String getMerchantname() {
        return merchantname;
    }

    public void setMerchantname(String merchantname) {
        this.merchantname = merchantname;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public Date getOpendt() {
        return opendt;
    }

    public void setOpendt(Date opendt) {
        this.opendt = opendt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getInstcode() {
        return instcode;
    }

    public void setInstcode(String instcode) {
        this.instcode = instcode;
    }

}
