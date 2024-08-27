
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbtdc
 *  08/27/2024 14:22:57
 * 
 */
public class Tbtdc {

    private Integer id;
    private String accountno;
    private String tdcno;
    private BigDecimal placementamt;
    private Integer termindays;
    private BigDecimal interestamt;
    private BigDecimal wtaxamt;
    private BigDecimal docstamps;
    private Date bookingdate;
    private Date maturitydate;
    private BigDecimal maturityvalue;
    private Date issuedate;
    private String status;
    private String issuedby;
    private BigDecimal intrate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getTdcno() {
        return tdcno;
    }

    public void setTdcno(String tdcno) {
        this.tdcno = tdcno;
    }

    public BigDecimal getPlacementamt() {
        return placementamt;
    }

    public void setPlacementamt(BigDecimal placementamt) {
        this.placementamt = placementamt;
    }

    public Integer getTermindays() {
        return termindays;
    }

    public void setTermindays(Integer termindays) {
        this.termindays = termindays;
    }

    public BigDecimal getInterestamt() {
        return interestamt;
    }

    public void setInterestamt(BigDecimal interestamt) {
        this.interestamt = interestamt;
    }

    public BigDecimal getWtaxamt() {
        return wtaxamt;
    }

    public void setWtaxamt(BigDecimal wtaxamt) {
        this.wtaxamt = wtaxamt;
    }

    public BigDecimal getDocstamps() {
        return docstamps;
    }

    public void setDocstamps(BigDecimal docstamps) {
        this.docstamps = docstamps;
    }

    public Date getBookingdate() {
        return bookingdate;
    }

    public void setBookingdate(Date bookingdate) {
        this.bookingdate = bookingdate;
    }

    public Date getMaturitydate() {
        return maturitydate;
    }

    public void setMaturitydate(Date maturitydate) {
        this.maturitydate = maturitydate;
    }

    public BigDecimal getMaturityvalue() {
        return maturityvalue;
    }

    public void setMaturityvalue(BigDecimal maturityvalue) {
        this.maturityvalue = maturityvalue;
    }

    public Date getIssuedate() {
        return issuedate;
    }

    public void setIssuedate(Date issuedate) {
        this.issuedate = issuedate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIssuedby() {
        return issuedby;
    }

    public void setIssuedby(String issuedby) {
        this.issuedby = issuedby;
    }

    public BigDecimal getIntrate() {
        return intrate;
    }

    public void setIntrate(BigDecimal intrate) {
        this.intrate = intrate;
    }

}
