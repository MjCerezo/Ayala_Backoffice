
package com.coopdb.data;

import java.math.BigDecimal;


/**
 *  COOPDB.Tbtransactioncode
 *  08/27/2024 14:22:56
 * 
 */
public class Tbtransactioncode {

    private Integer id;
    private String txcode;
    private String txname;
    private BigDecimal servicecharge;
    private String mnemonic;
    private Boolean isinteresttx;
    private Integer txoper;
    private Boolean wreason;
    private Boolean wcheck;
    private Boolean wcash;
    private Boolean wtransfer;

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

    public String getTxname() {
        return txname;
    }

    public void setTxname(String txname) {
        this.txname = txname;
    }

    public BigDecimal getServicecharge() {
        return servicecharge;
    }

    public void setServicecharge(BigDecimal servicecharge) {
        this.servicecharge = servicecharge;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public Boolean getIsinteresttx() {
        return isinteresttx;
    }

    public void setIsinteresttx(Boolean isinteresttx) {
        this.isinteresttx = isinteresttx;
    }

    public Integer getTxoper() {
        return txoper;
    }

    public void setTxoper(Integer txoper) {
        this.txoper = txoper;
    }

    public Boolean getWreason() {
        return wreason;
    }

    public void setWreason(Boolean wreason) {
        this.wreason = wreason;
    }

    public Boolean getWcheck() {
        return wcheck;
    }

    public void setWcheck(Boolean wcheck) {
        this.wcheck = wcheck;
    }

    public Boolean getWcash() {
        return wcash;
    }

    public void setWcash(Boolean wcash) {
        this.wcash = wcash;
    }

    public Boolean getWtransfer() {
        return wtransfer;
    }

    public void setWtransfer(Boolean wtransfer) {
        this.wtransfer = wtransfer;
    }

}
