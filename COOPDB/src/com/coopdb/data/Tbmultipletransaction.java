
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbmultipletransaction
 *  08/27/2024 14:22:57
 * 
 */
public class Tbmultipletransaction {

    private Integer id;
    private String multitxrefno;
    private String cifno;
    private String accountno;
    private String prodcode;
    private String subprodcode;
    private String txcode;
    private BigDecimal txamount;
    private String txmode;
    private String brstn;
    private String checkno;
    private String bankname;
    private Boolean islatecheck;
    private Date checkdate;
    private String clearingtype;
    private Integer clearingdays;
    private Date clearingdate;
    private String remarks;
    private Date txdate;
    private Date txvaldt;
    private String txstatus;
    private Date statusdate;
    private Date datecreated;
    private String createdby;
    private String feecode;
    private String txor;
    private String txrefno;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMultitxrefno() {
        return multitxrefno;
    }

    public void setMultitxrefno(String multitxrefno) {
        this.multitxrefno = multitxrefno;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getProdcode() {
        return prodcode;
    }

    public void setProdcode(String prodcode) {
        this.prodcode = prodcode;
    }

    public String getSubprodcode() {
        return subprodcode;
    }

    public void setSubprodcode(String subprodcode) {
        this.subprodcode = subprodcode;
    }

    public String getTxcode() {
        return txcode;
    }

    public void setTxcode(String txcode) {
        this.txcode = txcode;
    }

    public BigDecimal getTxamount() {
        return txamount;
    }

    public void setTxamount(BigDecimal txamount) {
        this.txamount = txamount;
    }

    public String getTxmode() {
        return txmode;
    }

    public void setTxmode(String txmode) {
        this.txmode = txmode;
    }

    public String getBrstn() {
        return brstn;
    }

    public void setBrstn(String brstn) {
        this.brstn = brstn;
    }

    public String getCheckno() {
        return checkno;
    }

    public void setCheckno(String checkno) {
        this.checkno = checkno;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public Boolean getIslatecheck() {
        return islatecheck;
    }

    public void setIslatecheck(Boolean islatecheck) {
        this.islatecheck = islatecheck;
    }

    public Date getCheckdate() {
        return checkdate;
    }

    public void setCheckdate(Date checkdate) {
        this.checkdate = checkdate;
    }

    public String getClearingtype() {
        return clearingtype;
    }

    public void setClearingtype(String clearingtype) {
        this.clearingtype = clearingtype;
    }

    public Integer getClearingdays() {
        return clearingdays;
    }

    public void setClearingdays(Integer clearingdays) {
        this.clearingdays = clearingdays;
    }

    public Date getClearingdate() {
        return clearingdate;
    }

    public void setClearingdate(Date clearingdate) {
        this.clearingdate = clearingdate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getTxstatus() {
        return txstatus;
    }

    public void setTxstatus(String txstatus) {
        this.txstatus = txstatus;
    }

    public Date getStatusdate() {
        return statusdate;
    }

    public void setStatusdate(Date statusdate) {
        this.statusdate = statusdate;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getFeecode() {
        return feecode;
    }

    public void setFeecode(String feecode) {
        this.feecode = feecode;
    }

    public String getTxor() {
        return txor;
    }

    public void setTxor(String txor) {
        this.txor = txor;
    }

    public String getTxrefno() {
        return txrefno;
    }

    public void setTxrefno(String txrefno) {
        this.txrefno = txrefno;
    }

}
