
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbloanapprovaldetails
 *  08/10/2024 21:24:57
 * 
 */
public class Tbloanapprovaldetails {

    private TbloanapprovaldetailsId id;
    private String decision;
    private Boolean readstatus;
    private Date lastreaddate;
    private Date assigneddate;
    private Date decisiondate;
    private String remarks;
    private Integer approvalsequence;

    public TbloanapprovaldetailsId getId() {
        return id;
    }

    public void setId(TbloanapprovaldetailsId id) {
        this.id = id;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public Boolean getReadstatus() {
        return readstatus;
    }

    public void setReadstatus(Boolean readstatus) {
        this.readstatus = readstatus;
    }

    public Date getLastreaddate() {
        return lastreaddate;
    }

    public void setLastreaddate(Date lastreaddate) {
        this.lastreaddate = lastreaddate;
    }

    public Date getAssigneddate() {
        return assigneddate;
    }

    public void setAssigneddate(Date assigneddate) {
        this.assigneddate = assigneddate;
    }

    public Date getDecisiondate() {
        return decisiondate;
    }

    public void setDecisiondate(Date decisiondate) {
        this.decisiondate = decisiondate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getApprovalsequence() {
        return approvalsequence;
    }

    public void setApprovalsequence(Integer approvalsequence) {
        this.approvalsequence = approvalsequence;
    }

}
