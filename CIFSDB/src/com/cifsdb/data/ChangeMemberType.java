
package com.cifsdb.data;

import java.util.Date;


/**
 *  CIFSDB.ChangeMemberType
 *  08/27/2024 14:22:04
 * 
 */
public class ChangeMemberType {

    private String transactionNumber;
    private String cifno;
    private String memberId;
    private String applicationStatus;
    private String remarks;
    private String currentMemberType;
    private String currentSubMemberType;
    private String newMemberType;
    private String newSubMemberType;
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

    public String getCurrentMemberType() {
        return currentMemberType;
    }

    public void setCurrentMemberType(String currentMemberType) {
        this.currentMemberType = currentMemberType;
    }

    public String getCurrentSubMemberType() {
        return currentSubMemberType;
    }

    public void setCurrentSubMemberType(String currentSubMemberType) {
        this.currentSubMemberType = currentSubMemberType;
    }

    public String getNewMemberType() {
        return newMemberType;
    }

    public void setNewMemberType(String newMemberType) {
        this.newMemberType = newMemberType;
    }

    public String getNewSubMemberType() {
        return newSubMemberType;
    }

    public void setNewSubMemberType(String newSubMemberType) {
        this.newSubMemberType = newSubMemberType;
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
