
package com.loansdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  LOANSDB.Tbsubfacility
 *  10/13/2020 10:21:35
 * 
 */
public class Tbsubfacility {

    private Integer id;
    private String lineappno;
    private String mainfacilitycode;
    private String facilitycode;
    private String facilityname;
    private String facilitytype;
    private BigDecimal proposedlimit;
    private BigDecimal approvedlimit;
    private Boolean ishared;
    private String allocationtype;
    private String sharetype;
    private Date startdate;
    private Date expirydate;
    private BigDecimal interestrate;
    private String interestperiod;
    private String defaultrepaymenttype;
    private String currency;
    private BigDecimal availablefund;
    private BigDecimal amountutilized;
    private BigDecimal term;
    private String termperiod;
    private String linestatus;
    private String purpose;
    private String remarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLineappno() {
        return lineappno;
    }

    public void setLineappno(String lineappno) {
        this.lineappno = lineappno;
    }

    public String getMainfacilitycode() {
        return mainfacilitycode;
    }

    public void setMainfacilitycode(String mainfacilitycode) {
        this.mainfacilitycode = mainfacilitycode;
    }

    public String getFacilitycode() {
        return facilitycode;
    }

    public void setFacilitycode(String facilitycode) {
        this.facilitycode = facilitycode;
    }

    public String getFacilityname() {
        return facilityname;
    }

    public void setFacilityname(String facilityname) {
        this.facilityname = facilityname;
    }

    public String getFacilitytype() {
        return facilitytype;
    }

    public void setFacilitytype(String facilitytype) {
        this.facilitytype = facilitytype;
    }

    public BigDecimal getProposedlimit() {
        return proposedlimit;
    }

    public void setProposedlimit(BigDecimal proposedlimit) {
        this.proposedlimit = proposedlimit;
    }

    public BigDecimal getApprovedlimit() {
        return approvedlimit;
    }

    public void setApprovedlimit(BigDecimal approvedlimit) {
        this.approvedlimit = approvedlimit;
    }

    public Boolean getIshared() {
        return ishared;
    }

    public void setIshared(Boolean ishared) {
        this.ishared = ishared;
    }

    public String getAllocationtype() {
        return allocationtype;
    }

    public void setAllocationtype(String allocationtype) {
        this.allocationtype = allocationtype;
    }

    public String getSharetype() {
        return sharetype;
    }

    public void setSharetype(String sharetype) {
        this.sharetype = sharetype;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(Date expirydate) {
        this.expirydate = expirydate;
    }

    public BigDecimal getInterestrate() {
        return interestrate;
    }

    public void setInterestrate(BigDecimal interestrate) {
        this.interestrate = interestrate;
    }

    public String getInterestperiod() {
        return interestperiod;
    }

    public void setInterestperiod(String interestperiod) {
        this.interestperiod = interestperiod;
    }

    public String getDefaultrepaymenttype() {
        return defaultrepaymenttype;
    }

    public void setDefaultrepaymenttype(String defaultrepaymenttype) {
        this.defaultrepaymenttype = defaultrepaymenttype;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAvailablefund() {
        return availablefund;
    }

    public void setAvailablefund(BigDecimal availablefund) {
        this.availablefund = availablefund;
    }

    public BigDecimal getAmountutilized() {
        return amountutilized;
    }

    public void setAmountutilized(BigDecimal amountutilized) {
        this.amountutilized = amountutilized;
    }

    public BigDecimal getTerm() {
        return term;
    }

    public void setTerm(BigDecimal term) {
        this.term = term;
    }

    public String getTermperiod() {
        return termperiod;
    }

    public void setTermperiod(String termperiod) {
        this.termperiod = termperiod;
    }

    public String getLinestatus() {
        return linestatus;
    }

    public void setLinestatus(String linestatus) {
        this.linestatus = linestatus;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
