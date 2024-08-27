
package com.cifsdb.data;

import java.util.Date;


/**
 *  CIFSDB.ChangeMemberStatus
 *  08/27/2024 14:22:04
 * 
 */
public class ChangeMemberStatus {

    private String transactionNumber;
    private String cifno;
    private String memberId;
    private String applicationStatus;
    private String remarks;
    private String currentStatus;
    private String subCurrentStatus;
    private String newStatus;
    private String subNewStatus;
    private Date dateCreated;
    private String createdBy;
    private Date dateApproved;
    private String approvedBy;

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getSubCurrentStatus() {
        return subCurrentStatus;
    }

    public void setSubCurrentStatus(String subCurrentStatus) {
        this.subCurrentStatus = subCurrentStatus;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public String getSubNewStatus() {
        return subNewStatus;
    }

    public void setSubNewStatus(String subNewStatus) {
        this.subNewStatus = subNewStatus;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getDateApproved() {
        return dateApproved;
    }

    public void setDateApproved(Date dateApproved) {
        this.dateApproved = dateApproved;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

}
