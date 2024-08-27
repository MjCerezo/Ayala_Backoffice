
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbcollateraldeposits
 *  08/27/2024 14:22:57
 * 
 */
public class Tbcollateraldeposits {

    private Integer collateralid;
    private String referenceno;
    private String collateralstatus;
    private String appraisalstatus;
    private Date dateencoded;
    private String encodedby;
    private String registeredname;
    private BigDecimal amount;
    private BigDecimal intrate;
    private Date dateofplacement;
    private Date maturitydate;
    private String remarks;
    private String acctnumber;

    public Integer getCollateralid() {
        return collateralid;
    }

    public void setCollateralid(Integer collateralid) {
        this.collateralid = collateralid;
    }

    public String getReferenceno() {
        return referenceno;
    }

    public void setReferenceno(String referenceno) {
        this.referenceno = referenceno;
    }

    public String getCollateralstatus() {
        return collateralstatus;
    }

    public void setCollateralstatus(String collateralstatus) {
        this.collateralstatus = collateralstatus;
    }

    public String getAppraisalstatus() {
        return appraisalstatus;
    }

    public void setAppraisalstatus(String appraisalstatus) {
        this.appraisalstatus = appraisalstatus;
    }

    public Date getDateencoded() {
        return dateencoded;
    }

    public void setDateencoded(Date dateencoded) {
        this.dateencoded = dateencoded;
    }

    public String getEncodedby() {
        return encodedby;
    }

    public void setEncodedby(String encodedby) {
        this.encodedby = encodedby;
    }

    public String getRegisteredname() {
        return registeredname;
    }

    public void setRegisteredname(String registeredname) {
        this.registeredname = registeredname;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getIntrate() {
        return intrate;
    }

    public void setIntrate(BigDecimal intrate) {
        this.intrate = intrate;
    }

    public Date getDateofplacement() {
        return dateofplacement;
    }

    public void setDateofplacement(Date dateofplacement) {
        this.dateofplacement = dateofplacement;
    }

    public Date getMaturitydate() {
        return maturitydate;
    }

    public void setMaturitydate(Date maturitydate) {
        this.maturitydate = maturitydate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAcctnumber() {
        return acctnumber;
    }

    public void setAcctnumber(String acctnumber) {
        this.acctnumber = acctnumber;
    }

}
