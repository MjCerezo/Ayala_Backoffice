
package com.loansdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  LOANSDB.Tbappraisaldeposits
 *  10/13/2020 10:21:35
 * 
 */
public class Tbappraisaldeposits {

    private String appraisalreportid;
    private String appno;
    private String collateralid;
    private String requestingofficer;
    private String department;
    private Date appraisaldate;
    private Date reportdate;
    private String appraiser;
    private String reviewedby;
    private Date revieweddate;
    private String status;
    private Date statusdate;
    private String acctnumber;
    private String registeredname;
    private BigDecimal amount;
    private BigDecimal intrate;
    private Date dateofplacement;
    private Date dateofmaturity;
    private String remarks;

    public String getAppraisalreportid() {
        return appraisalreportid;
    }

    public void setAppraisalreportid(String appraisalreportid) {
        this.appraisalreportid = appraisalreportid;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getCollateralid() {
        return collateralid;
    }

    public void setCollateralid(String collateralid) {
        this.collateralid = collateralid;
    }

    public String getRequestingofficer() {
        return requestingofficer;
    }

    public void setRequestingofficer(String requestingofficer) {
        this.requestingofficer = requestingofficer;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getAppraisaldate() {
        return appraisaldate;
    }

    public void setAppraisaldate(Date appraisaldate) {
        this.appraisaldate = appraisaldate;
    }

    public Date getReportdate() {
        return reportdate;
    }

    public void setReportdate(Date reportdate) {
        this.reportdate = reportdate;
    }

    public String getAppraiser() {
        return appraiser;
    }

    public void setAppraiser(String appraiser) {
        this.appraiser = appraiser;
    }

    public String getReviewedby() {
        return reviewedby;
    }

    public void setReviewedby(String reviewedby) {
        this.reviewedby = reviewedby;
    }

    public Date getRevieweddate() {
        return revieweddate;
    }

    public void setRevieweddate(Date revieweddate) {
        this.revieweddate = revieweddate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStatusdate() {
        return statusdate;
    }

    public void setStatusdate(Date statusdate) {
        this.statusdate = statusdate;
    }

    public String getAcctnumber() {
        return acctnumber;
    }

    public void setAcctnumber(String acctnumber) {
        this.acctnumber = acctnumber;
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

    public Date getDateofmaturity() {
        return dateofmaturity;
    }

    public void setDateofmaturity(Date dateofmaturity) {
        this.dateofmaturity = dateofmaturity;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
