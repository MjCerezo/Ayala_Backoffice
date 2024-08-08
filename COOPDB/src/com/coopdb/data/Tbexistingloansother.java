
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbexistingloansother
 *  02/23/2023 13:04:33
 * 
 */
public class Tbexistingloansother {

    private Integer id;
    private String cifno;
    private String loantype;
    private BigDecimal originalamt;
    private BigDecimal intrate;
    private String term;
    private Date daterelease;
    private Date maturitydate;
    private BigDecimal principalamt;
    private BigDecimal prinintrate;
    private BigDecimal outstandingbal;
    private String status;
    private String bank;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date lastupdated;
    private Boolean hascreditcheck;
    private BigDecimal amortizationamt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getLoantype() {
        return loantype;
    }

    public void setLoantype(String loantype) {
        this.loantype = loantype;
    }

    public BigDecimal getOriginalamt() {
        return originalamt;
    }

    public void setOriginalamt(BigDecimal originalamt) {
        this.originalamt = originalamt;
    }

    public BigDecimal getIntrate() {
        return intrate;
    }

    public void setIntrate(BigDecimal intrate) {
        this.intrate = intrate;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Date getDaterelease() {
        return daterelease;
    }

    public void setDaterelease(Date daterelease) {
        this.daterelease = daterelease;
    }

    public Date getMaturitydate() {
        return maturitydate;
    }

    public void setMaturitydate(Date maturitydate) {
        this.maturitydate = maturitydate;
    }

    public BigDecimal getPrincipalamt() {
        return principalamt;
    }

    public void setPrincipalamt(BigDecimal principalamt) {
        this.principalamt = principalamt;
    }

    public BigDecimal getPrinintrate() {
        return prinintrate;
    }

    public void setPrinintrate(BigDecimal prinintrate) {
        this.prinintrate = prinintrate;
    }

    public BigDecimal getOutstandingbal() {
        return outstandingbal;
    }

    public void setOutstandingbal(BigDecimal outstandingbal) {
        this.outstandingbal = outstandingbal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
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

    public Date getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(Date lastupdated) {
        this.lastupdated = lastupdated;
    }

    public Boolean getHascreditcheck() {
        return hascreditcheck;
    }

    public void setHascreditcheck(Boolean hascreditcheck) {
        this.hascreditcheck = hascreditcheck;
    }

    public BigDecimal getAmortizationamt() {
        return amortizationamt;
    }

    public void setAmortizationamt(BigDecimal amortizationamt) {
        this.amortizationamt = amortizationamt;
    }

}
