
package com.cifsdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  CIFSDB.Tbfintxjrnl
 *  09/26/2023 10:13:05
 * 
 */
public class Tbfintxjrnl {

    private Integer id;
    private String txcode;
    private String txref;
    private String txrefmain;
    private Date txvaldt;
    private Date txdate;
    private String username;
    private String accountno;
    private String acctname;
    private BigDecimal txamount;
    private String currency;
    private String checkacctno;
    private String checkno;
    private Date checkdate;
    private String cleartype;
    private String draweebank;
    private String brstnno;
    private String reasoncode;
    private String remarks;
    private String accountnoto;
    private Integer passbook;
    private Integer ledgerlineno;
    private String txby;
    private String txstatus;
    private Integer override;
    private String overrideby;
    private String terminalid;
    private String unit;
    private String instcode;
    private Boolean checklateind;
    private Integer pbookprintind;
    private String hostacceptind;
    private Integer errorcorrectind;
    private Integer bpind;
    private Integer chargeoverride;
    private String chargeoverrideby;
    private BigDecimal chargeamount;
    private BigDecimal amount;
    private String dateapproved;
    private String datereleased;
    private String daterequest;
    private String expiry;
    private String issuingbr;
    private Integer mccheckno;
    private String payee;
    private Integer status;
    private Integer batchid;
    private String txbatch;
    private Integer txoper;
    private String txmode;
    private String accountnotostatus;
    private String prevstatus;

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

    public String getTxref() {
        return txref;
    }

    public void setTxref(String txref) {
        this.txref = txref;
    }

    public String getTxrefmain() {
        return txrefmain;
    }

    public void setTxrefmain(String txrefmain) {
        this.txrefmain = txrefmain;
    }

    public Date getTxvaldt() {
        return txvaldt;
    }

    public void setTxvaldt(Date txvaldt) {
        this.txvaldt = txvaldt;
    }

    public Date getTxdate() {
        return txdate;
    }

    public void setTxdate(Date txdate) {
        this.txdate = txdate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getAcctname() {
        return acctname;
    }

    public void setAcctname(String acctname) {
        this.acctname = acctname;
    }

    public BigDecimal getTxamount() {
        return txamount;
    }

    public void setTxamount(BigDecimal txamount) {
        this.txamount = txamount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCheckacctno() {
        return checkacctno;
    }

    public void setCheckacctno(String checkacctno) {
        this.checkacctno = checkacctno;
    }

    public String getCheckno() {
        return checkno;
    }

    public void setCheckno(String checkno) {
        this.checkno = checkno;
    }

    public Date getCheckdate() {
        return checkdate;
    }

    public void setCheckdate(Date checkdate) {
        this.checkdate = checkdate;
    }

    public String getCleartype() {
        return cleartype;
    }

    public void setCleartype(String cleartype) {
        this.cleartype = cleartype;
    }

    public String getDraweebank() {
        return draweebank;
    }

    public void setDraweebank(String draweebank) {
        this.draweebank = draweebank;
    }

    public String getBrstnno() {
        return brstnno;
    }

    public void setBrstnno(String brstnno) {
        this.brstnno = brstnno;
    }

    public String getReasoncode() {
        return reasoncode;
    }

    public void setReasoncode(String reasoncode) {
        this.reasoncode = reasoncode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAccountnoto() {
        return accountnoto;
    }

    public void setAccountnoto(String accountnoto) {
        this.accountnoto = accountnoto;
    }

    public Integer getPassbook() {
        return passbook;
    }

    public void setPassbook(Integer passbook) {
        this.passbook = passbook;
    }

    public Integer getLedgerlineno() {
        return ledgerlineno;
    }

    public void setLedgerlineno(Integer ledgerlineno) {
        this.ledgerlineno = ledgerlineno;
    }

    public String getTxby() {
        return txby;
    }

    public void setTxby(String txby) {
        this.txby = txby;
    }

    public String getTxstatus() {
        return txstatus;
    }

    public void setTxstatus(String txstatus) {
        this.txstatus = txstatus;
    }

    public Integer getOverride() {
        return override;
    }

    public void setOverride(Integer override) {
        this.override = override;
    }

    public String getOverrideby() {
        return overrideby;
    }

    public void setOverrideby(String overrideby) {
        this.overrideby = overrideby;
    }

    public String getTerminalid() {
        return terminalid;
    }

    public void setTerminalid(String terminalid) {
        this.terminalid = terminalid;
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

    public Boolean getChecklateind() {
        return checklateind;
    }

    public void setChecklateind(Boolean checklateind) {
        this.checklateind = checklateind;
    }

    public Integer getPbookprintind() {
        return pbookprintind;
    }

    public void setPbookprintind(Integer pbookprintind) {
        this.pbookprintind = pbookprintind;
    }

    public String getHostacceptind() {
        return hostacceptind;
    }

    public void setHostacceptind(String hostacceptind) {
        this.hostacceptind = hostacceptind;
    }

    public Integer getErrorcorrectind() {
        return errorcorrectind;
    }

    public void setErrorcorrectind(Integer errorcorrectind) {
        this.errorcorrectind = errorcorrectind;
    }

    public Integer getBpind() {
        return bpind;
    }

    public void setBpind(Integer bpind) {
        this.bpind = bpind;
    }

    public Integer getChargeoverride() {
        return chargeoverride;
    }

    public void setChargeoverride(Integer chargeoverride) {
        this.chargeoverride = chargeoverride;
    }

    public String getChargeoverrideby() {
        return chargeoverrideby;
    }

    public void setChargeoverrideby(String chargeoverrideby) {
        this.chargeoverrideby = chargeoverrideby;
    }

    public BigDecimal getChargeamount() {
        return chargeamount;
    }

    public void setChargeamount(BigDecimal chargeamount) {
        this.chargeamount = chargeamount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDateapproved() {
        return dateapproved;
    }

    public void setDateapproved(String dateapproved) {
        this.dateapproved = dateapproved;
    }

    public String getDatereleased() {
        return datereleased;
    }

    public void setDatereleased(String datereleased) {
        this.datereleased = datereleased;
    }

    public String getDaterequest() {
        return daterequest;
    }

    public void setDaterequest(String daterequest) {
        this.daterequest = daterequest;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getIssuingbr() {
        return issuingbr;
    }

    public void setIssuingbr(String issuingbr) {
        this.issuingbr = issuingbr;
    }

    public Integer getMccheckno() {
        return mccheckno;
    }

    public void setMccheckno(Integer mccheckno) {
        this.mccheckno = mccheckno;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getBatchid() {
        return batchid;
    }

    public void setBatchid(Integer batchid) {
        this.batchid = batchid;
    }

    public String getTxbatch() {
        return txbatch;
    }

    public void setTxbatch(String txbatch) {
        this.txbatch = txbatch;
    }

    public Integer getTxoper() {
        return txoper;
    }

    public void setTxoper(Integer txoper) {
        this.txoper = txoper;
    }

    public String getTxmode() {
        return txmode;
    }

    public void setTxmode(String txmode) {
        this.txmode = txmode;
    }

    public String getAccountnotostatus() {
        return accountnotostatus;
    }

    public void setAccountnotostatus(String accountnotostatus) {
        this.accountnotostatus = accountnotostatus;
    }

    public String getPrevstatus() {
        return prevstatus;
    }

    public void setPrevstatus(String prevstatus) {
        this.prevstatus = prevstatus;
    }

}
